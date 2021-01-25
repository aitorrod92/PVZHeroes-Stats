package pvzheroes.clases.m�todosCompartidos;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.security.CodeSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import pvzheroes.clases.Carta;
import pvzheroes.clases.componentes.SentenciaIndividual;
import pvzheroes.clases.componentes.SentenciasGrupales;
import pvzheroes.clases.FXMLDocumentController;
import static pvzheroes.clases.ModoGr�ficos.ListadoCFil;
import pvzheroes.clases.componentes.ComponenteGr�ficoDispersi�n;
import pvzheroes.clases.componentes.ComponenteGr�ficoGrupal;
import pvzheroes.clases.constantes;
import pvzheroes.clases.diccionarios.DiccionarioPropiedades;
import pvzheroes.clases.di�logos.Di�logoGen�rico;

public class M�todosCompartidos {

    public static int TipoSeleccionado = 0;

    /**
     * Opciones imagen di�logo opciones
     */
    public static final ArrayList<String> IM�GENES_FONDO = new ArrayList();

    /**
     * Desplaza las filas de un GridPane al a�adir/quitar un elemento
     *
     * @param Aumentar Booleano que especifica si se quiere aumentar (true) o
     * reducir (false)
     * @param N�meroOrigen N�mero de fila a partir del cual se produce el
     * desplazamiento
     * @param Panel GridPane en cuesti�n
     * @param Vertical El desplazamiento es Vertical (true) u Horizontal (false)
     * @param MoverNoVisibles Booleano que especifica si se quieren mover o no
     * los nodos no visibles
     */
    public static void DesplazarCeldas(Boolean Aumentar, Integer N�meroOrigen, GridPane Panel, Boolean Vertical, Boolean MoverNoVisibles) {
        Integer N�meroNodo = 0;
        for (int i = 0; i < Panel.getChildren().size(); i++) {
            if (Vertical) {
                N�meroNodo = GridPane.getRowIndex(Panel.getChildren().get(i));
                if (Aumentar) {
                    if (MoverNoVisibles || (!MoverNoVisibles && Panel.getChildren().get(i).isVisible())) {
                        GridPane.setRowIndex(Panel.getChildren().get(i), N�meroNodo == null ? 1 : 1 + N�meroNodo);
                    }
                } else {
                    // En caso de borrado, s�lo se mueven las filas posteriores al bot�n pulsado
                    if (MoverNoVisibles || (!MoverNoVisibles && Panel.getChildren().get(i).isVisible())) {
                        if (N�meroNodo > N�meroOrigen) {
                            GridPane.setRowIndex(Panel.getChildren().get(i), N�meroNodo == null ? 1 : N�meroNodo - 1);
                        }
                    }
                }
            } else {
                N�meroNodo = GridPane.getColumnIndex(Panel.getChildren().get(i));
                if (Aumentar) {
                    if (MoverNoVisibles || (!MoverNoVisibles && Panel.getChildren().get(i).isVisible())) {
                        GridPane.setColumnIndex(Panel.getChildren().get(i), N�meroNodo == null ? 1 : 1 + N�meroNodo);
                    }
                } else {
                    // En caso de borrado, s�lo se mueven las columnas posteriores al bot�n pulsado
                    if (MoverNoVisibles || (!MoverNoVisibles && Panel.getChildren().get(i).isVisible())) {
                        if (N�meroNodo > N�meroOrigen) {
                            GridPane.setColumnIndex(Panel.getChildren().get(i), N�meroNodo == null ? 1 : N�meroNodo - 1);
                        }
                    }
                }
            }
        }
    }

    /**
     * Elimina la fila en la que se ha pulsado un bot�n de un GridPane y
     * desplaza las siguientes atr�s. En caso de que exista un filtro asociado a
     * dicha fila, tambi�n se elimina. Se asume que el bot�n se encuentra
     * directamente insertado en la celda, no dentro de alg�n contenedor tipo
     * HBox o VBox.
     *
     * @param Bot�nOrigen Bot�n "-" de la fila que se quiere eliminar
     * @param Panel GridPane en cuesti�n
     * @param ListaFiltros Lista de filtros ya existentes (para eliminar el
     * asociado a la fila que se elimina)
     */
    public static void EliminarFilas(Button Bot�nOrigen, GridPane Panel, ArrayList<String> ListaFiltros) {
        // Itera por las celdas para obtener el n�mero de fila del bot�n
        ObservableList<Node> componentesDeCelda = Panel.getChildren();
        Integer N�meroFila = 0;
        ArrayList<String> AEliminar = new ArrayList<>();
        for (int i = 0; i < componentesDeCelda.size(); i++) {
            if (componentesDeCelda.get(i).equals(Bot�nOrigen)) {
                N�meroFila = GridPane.getRowIndex(componentesDeCelda.get(i));
                /* Se elimina el filtro/funci�n del ArrayLists/HashMap correspondiente */
                if (Panel instanceof SentenciaIndividual) { // Proviene de un SentenciaIndividual
                    SentenciaIndividual c = (SentenciaIndividual) Panel;
                    for (String filtro : c.FiltrosCartas) {
                        if (filtro.equals(((Label) componentesDeCelda.get(i - 1)).getText())) {
                            AEliminar.add(filtro);
                        }
                    }
                } else {
                    // Proviene del di�logo de funciones de grupo o del di�logo de consulta de filtro de funciones de grupo
                    String Funci�nAEliminar = ((Label) Panel.getChildren().get(i - 1)).getText();
                    if (ListaFiltros != null) {
                        ListaFiltros.remove(Funci�nAEliminar);
                    }

                }
                /* Se eliminan el bot�n y la etiqueta. */
                componentesDeCelda.remove(componentesDeCelda.get(i));
                componentesDeCelda.remove(componentesDeCelda.get(i - 1));
            }
        }
        DesplazarCeldas(false, N�meroFila, Panel, true, false);

        /*Si el panel sobre el que se est� operando es ComponentesFuncionesGrupo, SentenciaIndividual Di�logoFuncionesGrupo
            no hace falta eliminar los filtros porque se eliminan autom�ticamente al cerrar el di�logo.*/
        if (Panel instanceof SentenciaIndividual) {
            SentenciaIndividual c = (SentenciaIndividual) Panel;
            /* Se hace de este modo para evitar ConcurrentModificationException resultado 
            de modificar un bucle que se est� iterando */
            c.FiltrosCartas.removeAll(AEliminar);

            // Comprobamos que el primer filtro tiene AND y el par�ntesis y, en caso contrario, lo a�adimos
            for (int i = 0; i < c.FiltrosCartas.size(); i++) {
                if (!c.FiltrosCartas.get(0).matches("AND\\s{1}\\(.*")) {
                    String FiltroAdaptadoAPrimeraPosici�n = c.FiltrosCartas.get(0).replace(c.FiltrosCartas.get(0).substring(0, 4), "AND (");
                    c.FiltrosCartas.set(0, FiltroAdaptadoAPrimeraPosici�n);
                    // Cambiamos tambi�n la etiqueta que corresponde a la primera fila
                    for (Node nodo : c.getChildren()) {
                        try {
                            if (GridPane.getColumnIndex(nodo) == 0 && GridPane.getRowIndex(nodo) == 0) {
                                ((Label) nodo).setText(FiltroAdaptadoAPrimeraPosici�n);
                            }
                        } catch (NullPointerException e) { // Este catch es necesario porque las columnas y filas pueden arrojar valores nulos
                            System.out.println("Celda con valor de columna/fila nulo");
                        }
                    }
                }
            }
        }
    }

    /**
     * Elimina la columna en la que se ha pulsado un bot�n de un GridPane y
     * desplaza las siguientes atr�s. Se asume que el bot�n se encuentra dentro
     * de un contenedor "ComponenteGr�ficoGrupal" o
     * "ComponenteGr�ficoDispersi�n". Los nodos que no son visibles no se
     * eliminan.
     *
     * @param Bot�nOrigen Bot�n de la fila que se quiere eliminar
     * @param Panel GridPane en el que se realiza la modificaci�n
     * @param Listado Listado de ComponentesFuncionesGrupo o listado de
     * ComponentesFiltros (para eliminar la referencia del que se elimina)
     */
    public static <T> void EliminarColumnas(Button Bot�nOrigen, GridPane Panel, ArrayList<T> Listado) {
        ObservableList<Node> componentesDeCelda = Panel.getChildren();
        ArrayList<Node> AEliminar = new ArrayList();
        int ColumnaBot�n = 0;
        for (int i = 0; i < componentesDeCelda.size(); i++) { // ADAPTAR A PARTIR DE AQU� PARA BORRAR TAMBI�N CFIL, en ese caso es un VBOX
            Boolean CGG = componentesDeCelda.get(i).getClass() == ComponenteGr�ficoGrupal.class;
            Boolean EsCGD = componentesDeCelda.get(i).getClass() == ComponenteGr�ficoDispersi�n.class;

            if (CGG) {
                Boolean CGGContieneBot�n = ((HBox) componentesDeCelda.get(i)).getChildren().get(1).equals(Bot�nOrigen);
                if (CGGContieneBot�n) {
                    ColumnaBot�n = GridPane.getColumnIndex(componentesDeCelda.get(i));
                    AEliminar.add(componentesDeCelda.get(i));
                }
            } else if (EsCGD && ((VBox) componentesDeCelda.get(i)).getChildren().size() == 3) {
                Boolean CGDContieneBot�n = ((HBox) ((VBox) componentesDeCelda.get(i)).getChildren().get(0)).getChildren().get(1).equals(Bot�nOrigen);
                if (CGDContieneBot�n) {
                    ColumnaBot�n = GridPane.getColumnIndex(componentesDeCelda.get(i));
                    AEliminar.add(componentesDeCelda.get(i));
                }
            }
        }

        for (Node nodo : componentesDeCelda) {
            if (GridPane.getColumnIndex(nodo) == ColumnaBot�n && nodo.isVisible()) {
                if (nodo instanceof SentenciasGrupales || nodo instanceof SentenciaIndividual) {
                    Listado.remove(nodo);
                }
                AEliminar.add(nodo);
            }
        }

        for (Node nodoAEliminar : AEliminar) {
            Panel.getChildren().remove(nodoAEliminar);
        }
        // S�lo se ejecuta una vez porque afecta a toda la columna
        M�todosCompartidos.DesplazarCeldas(false, ColumnaBot�n, Panel, false, false);

    }

    /**
     * Activa/desactiva la tabla/�rea de texto en funci�n de la b�squeda
     * realizada/que se vaya a realizar
     *
     * @param TablaNecesaria Especifica si se requiere la tabla (y, por tanto,
     * no el TextArea) o no
     * @param controlador Referencia al controlador de la vista
     */
    public void ActivarTabla(Boolean TablaNecesaria, FXMLDocumentController controlador) {
        controlador.getAreaTextoGrupo().setDisable(TablaNecesaria);
        controlador.getAreaTextoGrupo().setVisible(!TablaNecesaria);
        controlador.getTabla().setDisable(!TablaNecesaria);
        controlador.getTabla().setVisible(TablaNecesaria);
    }

    /**
     * M�todo que realiza las b�squedas grupales que parten de un
     * SentenciasGrupales (modo b�sico y de gr�ficos)
     *
     * @param sentenciasGrupales SentenciasGrupales con las funciones de grupo a
     * buscar
     * @param conn Conexi�n a la base de datos
     * @return Un ArrayList<String> con los resultados de las b�squedas
     * @throws SQLException Excepci�n derivada de problemas en la b�squeda
     */
    public ArrayList<String> B�squedaGrupal(SentenciasGrupales sentenciasGrupales, Connection conn) throws SQLException {
        Statement st = conn.createStatement();
        /* Busca en el HashMap "Funciones definidas" todas las claves
        guardadas en forma de etiquetas. Utiliza esas claves para obtener las 
        sentencias e ir ejecut�ndolas y a�adiendo los resultados*/
        ArrayList<String> resultados = new ArrayList();

        for (int i = 0; i < sentenciasGrupales.getChildren().size(); i++) {
            if (sentenciasGrupales.getChildren().get(i) instanceof Label) {
                String textoEtiqueta = ((Label) sentenciasGrupales.getChildren().get(i)).getText();
                String sentenciaSQL = sentenciasGrupales.getFuncionesDefinidas().get(textoEtiqueta);
                ResultSet rs = st.executeQuery(sentenciaSQL);
                resultados.add(new DecimalFormat("#.##").format(rs.getDouble(1)));
            }
        }
        return resultados;
    }

    /**
     * Devuelve el n�mero de columnas del GridPane que se le pasa como argumento
     *
     * @param panel GridPane que se quiere analizar
     * @return N�mero de columnas del GridPane
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Integer ObtenerN�meroColumnas(GridPane panel) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = panel.getClass().getDeclaredMethod("getNumberOfColumns");
        method.setAccessible(true);
        Integer columnas = (Integer) method.invoke(panel);
        return columnas;
    }

    /**
     * Convierte String con coma a double
     *
     * @param cadena String a transformar
     * @return Valor de la cadena parseado en double
     */
    public static double ConvertirStringADoble(String cadena) {
        double valor = Double.valueOf(cadena.replace(",", "."));
        return valor;
    }

    /**
     * Crea un ComboBox
     *
     * @param valores Array con los valores a utilizar
     * @param seleccionado Valor seleccionado por defecto
     * @return ComboBox con los valores y el valor seleccionado por defecto
     */
    public static ComboBox CrearComboBox(String[] valores, int seleccionado) {
        List<String> opciones = new ArrayList();
        ObservableList<String> datosCombo = FXCollections.observableList(opciones);
        datosCombo.addAll(Arrays.asList(valores));
        ComboBox Combo = new ComboBox();
        Combo.setItems(datosCombo);
        Combo.setValue(datosCombo.get(seleccionado));
        return Combo;
    }

    /**
     * M�todo que realiza la b�squeda normal en el modo b�sico y en el de
     * gr�ficos (para el Scattered)
     *
     * @param FiltrosCartas Filtros ya existentes
     * @return Una lista con las cartas resultado de la consulta
     * @throws SQLException
     */
    public ArrayList<Carta> B�squeda(ArrayList<String> FiltrosCartas, Object ObjetoOrigen) throws SQLException {
        ArrayList<Carta> CartasDevueltas = new ArrayList();

        // Establece la sentencia base
        String Sentencia = "";
        for (int i = 0; i < FiltrosCartas.size(); i++) {
            if (FiltrosCartas.get(i).matches(".*Atributos = \"Anti-Hero\"") || FiltrosCartas.get(i).matches(".*Atributos = \"Overshoot\"")
                    || FiltrosCartas.get(i).matches(".*Atributos = \"Splash Damage\"") || FiltrosCartas.get(i).matches(".*Atributos = \"Armored\"")) {
                Sentencia = Conversi�nFiltrosAtributosNum�ricos(FiltrosCartas.get(i), true);
            } else if (FiltrosCartas.get(i).matches(".*Atributos <> \"Anti-Hero\"") || FiltrosCartas.get(i).matches(".*Atributos <> \"Overshoot\"")
                    || FiltrosCartas.get(i).matches(".*Atributos <> \"Splash Damage\"") || FiltrosCartas.get(i).matches(".*Atributos <> \"Armored\"")) {
                Sentencia = Conversi�nFiltrosAtributosNum�ricos(FiltrosCartas.get(i), false);
            } else {
                Sentencia = Sentencia.concat(FiltrosCartas.get(i));
            }
            if (i == FiltrosCartas.size() - 1) {
                Sentencia = Sentencia.concat(");");
            }
        }
        String sentenciaSQL = "SELECT * FROM Cartas WHERE "
                + constantes.TIPOS_CARTA[TipoSeleccionado]
                + Sentencia;

        // Establece la conexi�n y ejecuta la sentencia
        Statement st = FXMLDocumentController.conn.createStatement();
        ResultSet rs = st.executeQuery(sentenciaSQL);

        if (ObjetoOrigen instanceof FXMLDocumentController) {
            FXMLDocumentController Controlador = (FXMLDocumentController) ObjetoOrigen;
            // Limpia los resultados previos
            try {
                Controlador.Tabla.getItems().clear();
            } catch (NullPointerException e) {
                System.out.println("Tabla vac�a");
            }
            Controlador.data.clear();
        }

        // Itera por los resultados y a�ade la carta. No es necesario usar "getInt" para los int
        // Tampoco es necesario utilizar "setItems" porque los cambios en la ObservableList se registran solos
        while (rs.next()) {
            CartasDevueltas.add(new Carta(rs.getString(constantes.COLUMNAS[0]),
                    rs.getString(constantes.COLUMNAS[1]), rs.getString(constantes.COLUMNAS[2]),
                    rs.getString(constantes.COLUMNAS[3]), rs.getString(constantes.COLUMNAS[4]),
                    rs.getString(constantes.COLUMNAS[5]), rs.getString(constantes.COLUMNAS[6]),
                    rs.getString(constantes.COLUMNAS[7]), rs.getString(constantes.COLUMNAS[8]),
                    rs.getString(constantes.COLUMNAS[9]), rs.getString(constantes.COLUMNAS[10]),
                    rs.getString(constantes.COLUMNAS[11]), rs.getString(constantes.COLUMNAS[12])));
        }
        st.close();
        return CartasDevueltas;
    }

    /**
     * Obtiene el primer componente de un tipo espec�fico que se encuentre en un
     * GridPane, a una distancia concreta en celdas de otro componente. El
     * componente buscado debe ser visible para ser devuelto. OJO: NO devuelve
     * StackPane para evitar conflictos con los ToolTips.
     *
     * @param <T> Componente que se quiere obtener
     * @param t Componente que se quiere obtener
     * @param panel GridPane en el que se encuentra el componente
     * @param nodoPartida Nodo desde el que se quiere contar
     * @param posicionesX Distancia horizontal (columnas) en celdas (-X hacia la
     * izquierda, +X hacia la derecha)
     * @param posicionesY Distancia horizontal (filas) en celdas (-Y hacia
     * arriba, +Y hacia abajo)
     * @return El componente buscado, en caso de existir
     */
    public static <T> T getObjetoPosici�nRelativaGrid(T t, GridPane panel, Node nodoPartida, int posicionesX, int posicionesY) {
        for (Node nodo : panel.getChildrenUnmodifiable()) {
            if (!(nodo instanceof StackPane)) {
                Boolean ColumnaCoincide = (int) GridPane.getColumnIndex(nodo) == ((int) GridPane.getColumnIndex(nodoPartida) + posicionesX);
                Boolean FilaCoincide = (int) GridPane.getRowIndex(nodo) == ((int) GridPane.getRowIndex(nodoPartida) + posicionesY);
                if (ColumnaCoincide && FilaCoincide && nodo.getClass() == t.getClass() && nodo.isVisible()) {
                    t = (T) nodo;
                }
            }
        }
        return t;
    }

    /**
     * Obtiene el componente n�mero @Posici�n de un tipo espec�fico que se
     * encuentre en un HBox o VBox. Por ejemplo, una posici�n 2 devolver� el
     * tercer hijo del HBox/VBox que coincida con el tipo. El componente ha de
     * ser visible para ser devuelto.
     *
     * @param <T> Componente que se quiere obtener
     * @param t Componente que se quiere obtener
     * @param box VBox o HBox en la que se encuentra el componente
     * @param Posici�n Posici�n del elemento, empezando por 0.
     * @return El componente buscado, en caso de existir
     */
    public static <T> T getObjetoPosici�nRelativaBox(T t, Pane box, int Posici�n) {
        for (Node nodo : box.getChildrenUnmodifiable()) {
            if (nodo.getClass() == t.getClass() && nodo.isVisible()) {
                if (Posici�n != 0) {
                    Posici�n--;
                } else {
                    t = (T) nodo;
                    return t;
                }
            }
        }
        return null;
    }

    /**
     * M�todos para obtener un c�digo Hex a partir de un color RGB
     *
     * @param color Color RGB
     * @return C�digo Hex
     */
    public static String AStringHex(Color color) {
        int r = ((int) Math.round(color.getRed() * 255)) << 24;
        int g = ((int) Math.round(color.getGreen() * 255)) << 16;
        int b = ((int) Math.round(color.getBlue() * 255)) << 8;
        int a = ((int) Math.round(color.getOpacity() * 255));

        return String.format("#%08X", (r + g + b + a));
    }

    /**
     * Cambia las relaciones "=" por "LIKE" y "<>" por "NOT LIKE" para atributos
     * que contienen n�meros, para que funcionen correctamente
     *
     * @param filtro Filtro sobre el que se desea realizar la conversi�n
     * @param EsExactamente Booleano que indica si se trata de "=" (true) o "<>"
     * (false)
     * @return Filtro con la equivalencia "= "Atributo"" cambiada por "LIKE
     * "%Atributo%"" o "<> "Atributo"" cambiado por "NOT LIKE "%Atributo%"
     */
    private String Conversi�nFiltrosAtributosNum�ricos(String Sentencia, Boolean EsExactamente) {
        if (EsExactamente) {
            Sentencia = Sentencia.replace("= \"", "LIKE \"");
        } else {
            Sentencia = Sentencia.replace("<> \"", "NOT LIKE \"");
        }
        Sentencia = Sentencia.replace(String.valueOf(Sentencia.charAt(Sentencia.length() - 2) + "\""),
                String.valueOf(Sentencia.charAt(Sentencia.length() - 2)) + " _\"");
        return Sentencia;
    }

    /**
     * Extrae el nodo contenido en una celda
     *
     * @param gridPane GridPane del que se desea extraer el nodo
     * @param columna Columna de la que se desea extraer el nodo
     * @param fila Fila de la que se desea extraer el nodo
     * @param Visible Boolean que define si el nodo es visible (true) o no
     * (false)
     * @return Nodo contenido en la celda correspondiente
     */
    public static Node getNodoCelda(GridPane gridPane, int columna, int fila, Boolean Visible) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == columna && GridPane.getRowIndex(node) == fila && node.isVisible() == Visible) {
                return node;
            }
        }
        return null;
    }

    /**
     * Clase auxiliar para que setToolTipDatoGr�ficoXy sepa si la etiqueta est�
     * siendo o no arrastrada
     */
    static class BooleanoArrastrando {

        Boolean Arrastrando = false;
    }

    /**
     * Hace que al pasar el rat�n por encima del dato en cuesti�n, aparezca una
     * etiqueta arrastrable con su valor y el listado de datos que coinciden con
     * el mismo. Al salir del dato, su aspecto retorna al estado inicial. En los
     * gr�ficos de dispersi�n, adem�s, se genera un encabezado que muestra las
     * coordenadas del punto.
     *
     * @param <T> El dato sobre el que aplicar la etiqueta. Puede ser
     * XYChart.Data o PieChart.Data
     * @param dato El dato sobre el que aplicar la etiqueta. Puede ser
     * XYChart.Data o PieChart.Data
     */
    public static <T> void setToolTipDatoGr�fico(T dato,
            ArrayList<ArrayList> listadoResultados)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {

        BooleanoArrastrando BA = new BooleanoArrastrando();
        dato = (T) dato;

        StackPane contenedor = new StackPane();

        Rectangle r = CrearRect�ngulo(10, 10, Color.WHITE, "black", 0);

        VBox contenedorSecundario = new VBox();
        //ArrayList<Hyperlink> ListaLinks = new ArrayList();
        HashMap<Hyperlink, String> ListaLinksColores = new HashMap();

        // Si no se ponen como dimensiones m�ximas, se extienden por todo el nodo
        contenedor.setMaxSize(30, 20);
        contenedorSecundario.setMaxSize(30, 20);

        final Label primeraEtiqueta = new Label();
        final Label encabezadoCoordenadas = new Label("Coordenadas");

        double AlturaFinal;
        double alturaPalabra = 0;
        double anchuraPalabraM�sLarga = 0;

        if (dato instanceof XYChart.Data) {
            XYChart.Data datoXY = (XYChart.Data) dato;
            if (listadoResultados == null) {
                primeraEtiqueta.setText(String.valueOf(datoXY.getYValue()));
                primeraEtiqueta.setId(constantes.IDs_CSS.ETIQUETA_NEGRA_G.identificador);
                anchuraPalabraM�sLarga = CalcularDimensionesTexto(primeraEtiqueta)[1];
                AlturaFinal = CalcularDimensionesTexto(primeraEtiqueta)[0];
            } else { // Se trata de un gr�fico de dispersi�n
                int x = 0;
                double alturaEncabezado = CalcularDimensionesTexto(encabezadoCoordenadas)[0];
                AlturaFinal = alturaEncabezado;
                for (SentenciaIndividual cofil : ListadoCFil) {
                    String[] ValoresCombos = cofil.ObtenerValoresComboBoxes();
                    String ValorColor = M�todosCompartidos.AStringHex(cofil.ObtenerColorPicker().getValue());
                    for (int y = 0; y < listadoResultados.get(x).size(); y++) {
                        Carta carta = ((Carta) listadoResultados.get(x).get(y));
                        Method getterX = carta.getClass().getMethod("get" + ValoresCombos[0]);
                        Method getterY = carta.getClass().getMethod("get" + ValoresCombos[1]);
                        Integer ValorX = Integer.valueOf((String) getterX.invoke(carta));
                        Integer ValorY = Integer.valueOf((String) getterY.invoke(carta));
                        encabezadoCoordenadas.setText("(" + datoXY.getXValue() + " , " + datoXY.getYValue() + ")");
                        // Se comparan los valores del resultado con el punto concreto en el que estamos
                        if (Objects.equals(ValorX, datoXY.getXValue()) && Objects.equals(ValorY, datoXY.getYValue())) {
                            Hyperlink link = carta.getNombre();
                            ListaLinksColores.put(link, ValorColor);
                            //ListaLinks.add(link, ValorColor);
                            alturaPalabra = CalcularDimensionesTexto(link)[0];
                            if (ListaLinksColores.size() != 1) {
                                double SumaExtra = 23 - (y * 0.08); // Cada vez se a�ade menos para evitar el desfase
                                if (SumaExtra > 0) {
                                    AlturaFinal = AlturaFinal + alturaPalabra + SumaExtra;
                                } else {
                                    AlturaFinal = AlturaFinal + alturaPalabra;
                                }

                            } else {
                                AlturaFinal = alturaPalabra + 23;
                            }
                            double longitudPalabra = CalcularDimensionesTexto(link)[1];
                            if (anchuraPalabraM�sLarga < longitudPalabra) {
                                anchuraPalabraM�sLarga = longitudPalabra;
                            }

                        }
                    }
                    x++;
                }
                primeraEtiqueta.setId(constantes.IDs_CSS.ETIQUETA_NEGRA_P.identificador);
            }

            datoXY.setNode(contenedor);
        } else {
            PieChart.Data datoPie = (PieChart.Data) dato;
            primeraEtiqueta.setText(String.valueOf(datoPie.getPieValue()));
            primeraEtiqueta.setId(constantes.IDs_CSS.ETIQUETA_NEGRA_G.identificador);
            Method m�todo = datoPie.getClass().getDeclaredMethod("setNode", Node.class);
            m�todo.setAccessible(true);
            m�todo.invoke(datoPie, contenedor);
            anchuraPalabraM�sLarga = CalcularDimensionesTexto(primeraEtiqueta)[1];
            AlturaFinal = CalcularDimensionesTexto(primeraEtiqueta)[0];
        }

        double AnchuraFinal = anchuraPalabraM�sLarga + 30;
        contenedor.setMinWidth(AnchuraFinal);
        contenedorSecundario.setMinWidth(AnchuraFinal);
        // Se les a�ade un poco m�s de tama�o para que el resto de componentes no tapen los bordes
        r.setWidth(AnchuraFinal + 3);

        if (listadoResultados == null) {
            contenedorSecundario.getChildren().addAll(primeraEtiqueta);
            primeraEtiqueta.setMinWidth(AnchuraFinal);
            primeraEtiqueta.setAlignment(Pos.CENTER);
            r.setHeight(AlturaFinal + 10);
        } else {
            contenedorSecundario.getChildren().add(encabezadoCoordenadas);
            encabezadoCoordenadas.setMinWidth(AnchuraFinal);
            encabezadoCoordenadas.setAlignment(Pos.CENTER);
            encabezadoCoordenadas.setId(constantes.IDs_CSS.NEGRITA.identificador);
            contenedorSecundario.getChildren().addAll(ListaLinksColores.keySet());
            for (Hyperlink link : ListaLinksColores.keySet()) {
                link.setMinWidth(AnchuraFinal);
                link.setAlignment(Pos.CENTER);
            }
            r.setHeight(AlturaFinal + 3);
        }

        contenedor.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                contenedor.getChildren().setAll(r, contenedorSecundario);
                for (int i = 1; i < contenedorSecundario.getChildren().size(); i++) {
                    if (DiccionarioPropiedades.PROP.getProperty("filasEnColores") != null) {
                        Hyperlink link = (Hyperlink) contenedorSecundario.getChildren().get(i);
                        if (DiccionarioPropiedades.PROP.getProperty("filasEnColores").equals("false")) {
                            if (i % 2 == 0) {
                                link.setStyle("-fx-background-color: white;");
                            } else {
                                link.setStyle("-fx-background-color: lightgrey;");
                            }
                        } else {
                            link.setStyle("-fx-background-color: " + ListaLinksColores.get(link) + ";");
                        }
                    }
                }
                contenedor.toFront();
            }
        });

        contenedor.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!BA.Arrastrando) {
                    contenedor.getChildren().clear();
                }
            }
        });

        contenedor.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                BA.Arrastrando = true;
            }
        });

        contenedor.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                BA.Arrastrando = false;
            }
        });
        permitirArrastrar(r, contenedorSecundario);
    }

    /**
     * Clase auxiliar para guardar las distancias iniciales en permitirArrastrar
     */
    static class Delta {

        double x, y;
    }

    /**
     * Hace que uno o varios nodos se puedan mover arrastr�ndolo con el cursor
     *
     * @param nodos Nodos que se quiere poder arrastrar
     */
    public static void permitirArrastrar(final Node... nodos) {
        final Delta dragDelta = new Delta();
        for (Node nodo : nodos) {
            nodo.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    // Guarda la distancia entre el nodo y el rat�n
                    for (Node n : nodos) {
                        dragDelta.x = n.getTranslateX() - mouseEvent.getX();
                        dragDelta.y = n.getTranslateY() - mouseEvent.getY();
                        nodo.getScene().setCursor(Cursor.MOVE);
                    }
                }
            });
            nodo.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    for (Node n : nodos) {
                        n.getScene().setCursor(Cursor.DEFAULT);
                    }
                }
            });
            nodo.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    /* Mueve el nodo en funci�n de la localizaci�n del rat�n y de
                    la distancia con el nodo que hab�a inicialmente */
                    for (Node n : nodos) {
                        n.setTranslateX(mouseEvent.getX() + dragDelta.x);
                        n.setTranslateY(mouseEvent.getY() + dragDelta.y);
                    }
                }
            });
        }
    }

    /**
     * Calcula las dimensionesde una etiqueta. Los estilos que se hayan aplicado
     * con CSS no se tienen en cuenta en el c�lculo (esto significa que, si
     * quieres que se utilice la negrita, has de incluir la en la propia fuente
     * de la etiqueta). Los links arrojan un resultado ligeramente menor al que
     * les corresponde, por lo que se ha aplicado internamente un corrector del
     * valor.
     *
     * @param etiqueta Etiqueta (label, hyperlink...) de la que se quiere
     * calcular la anchura.
     * @return Array con altura [0] y anchura [1]
     */
    public static double[] CalcularDimensionesTexto(Labeled etiqueta) {
        Text texto = new Text(etiqueta.getText());
        texto.setFont(etiqueta.getFont());
        double altura = texto.getBoundsInLocal().getHeight();
        double anchura = texto.getBoundsInLocal().getWidth();
        if (etiqueta instanceof Hyperlink) {
            anchura = anchura + 5;
        }
        double[] dimensiones = {altura, anchura};
        return dimensiones;
    }

    /**
     * Crea un rect�ngulo con los par�metros indicados, aunque la sombra es
     * predeterminada.
     *
     * @param Tama�oX Anchura del rect�ngulo
     * @param Tama�oY Altura del rect�ngulo
     * @param Color Color de fondo
     * @param ColorBorde Color de borde (String, v�ase documentaci�n de JavaFX)
     * @param tama�oCurva Tama�o de la curva, para hacer bordes redondeados. Un
     * valor de 0 crea un rect�ngulo puro.
     * @return
     */
    public static Rectangle CrearRect�ngulo(double Tama�oX, double Tama�oY, Color Color, String ColorBorde, double tama�oCurva) {
        Rectangle r = new Rectangle(Tama�oX, Tama�oY, Color);
        r.setStyle("-fx-stroke: " + ColorBorde + ";");
        DropShadow s = new DropShadow();
        s.setWidth(20);
        s.setHeight(20);
        s.setOffsetX(10);
        s.setOffsetY(10);
        s.setRadius(10);
        r.setEffect(s);
        r.setArcHeight(tama�oCurva);
        r.setArcWidth(tama�oCurva);
        return r;
    }

    /**
     * Cuenta el n�mero de coincidencias de una palabra dentro de un string
     *
     * @param string El string a analizar
     * @param palabra La palabra que se busca
     * @return Entero con el n�mero de coincidencias
     */
    public static int ContarCoincidenciasEnString(String string, String palabra) {
        int posici�n = string.indexOf(palabra);
        int coincidencias = 0;
        while (posici�n != -1) {
            coincidencias++;
            string = string.substring(posici�n + 1);
            posici�n = string.indexOf(palabra);
        }
        return coincidencias;
    }

    /**
     * Hace que todos los componentes de un HBox crezcan equitativamente de
     * manera horizontal cuando su tama�o est� por encima de su PrefWidth. Si no
     * se ha definido PrefWidth, esto supone que se expandan hasta ocupar todo
     * el espacio disponible.
     *
     * @param hbox El HBox que contiene los distintos elementos.
     */
    public static void CrecerEquitativamente(HBox hbox) {
        for (Node nodo : hbox.getChildrenUnmodifiable()) {
            HBox.setHgrow(nodo, Priority.ALWAYS);
        }
    }

    /**
     * Establece un atajo de teclado en un bot�n.
     *
     * @param Bot�n Bot�n sobre el que se quiere establecer el atajo
     * @param Combinaci�nTeclas KeyCodeCombination que ejercer� de atajo
     * @param m�todo M�todo que se quiere ejecutar. Un valor "null"
     * desencadenar� el m�todo "fire()" del bot�n
     * @param ObjetoFuente Objeto en el que se encuentra el m�todo a invocar. Si
     * el m�todo es "fire()", dejarlo en "null".
     * @param Par�metros N�mero variable de par�metros del m�todo a invocar. Si
     * el m�todo es "fire()", dejarlo en "null".
     * @return Booleano indicando si la inserci�n del atajo se ha realizado
     */
    public static Boolean EstablecerAceleradorEnBot�n(Button Bot�n,
            KeyCodeCombination Combinaci�nTeclas, Method m�todo, Object ObjetoFuente, Object... Par�metros) {
        try {
            Bot�n.getScene().getAccelerators().put(Combinaci�nTeclas, new Runnable() {
                @Override
                public void run() {
                    if (m�todo == null) {
                        Bot�n.fire();
                    } else {
                        try {
                            m�todo.invoke(ObjetoFuente, Par�metros);
                        } catch (IllegalAccessException ex) {
                            System.out.println("No se puede acceder al m�todo");
                        } catch (IllegalArgumentException ex) {
                            System.out.println("Uno o varios par�metros "
                                    + "pasados al m�todo no son v�lidos");
                        } catch (InvocationTargetException ex) {
                            System.out.println("Error en la invocaci�n del m�todo");
                        }
                    }
                }
            });
            Bot�n.requestFocus();
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Compara la anchura de dos textos y devuelve el valor m�s alto. Requiere
     * el m�todo "CalcularDimensionesTexto" para funcionar.
     *
     * @param etiquetado1 Primer Label, Hyperlink, etc.
     * @param etiquetado2 Segundo Label, Hyperlink, etc.
     * @return Valor double con la anchura mayor.
     */
    public static double TextoM�sAncho(Labeled etiquetado1, Labeled etiquetado2) {
        double[] dimensiones1 = M�todosCompartidos.CalcularDimensionesTexto(etiquetado1);
        double[] dimensiones2 = M�todosCompartidos.CalcularDimensionesTexto(etiquetado2);
        if (dimensiones1[1] > dimensiones2[1]) {
            return (dimensiones1[1]);
        } else {
            return dimensiones2[1];
        }
    }

    /**
     * M�todo para obtener las im�genes de un directorio dentro del JAR.
     * Requiere que se incluya "ObtenerNombresDirectorioJAR". Ten cuidado de que
     * los nombres no sean demasiado similares o puede dar lugar a problemas.
     *
     * No se utiliza realmente en el programa, puesto que hubo problemas en la
     * obtenci�n de im�genes al convertir a .exe.
     *
     * @param ruta Ruta del directorio partiendo desde la clase indicada en el
     * par�metro "clase". Si el directorio comparte directorio con la clase, ten
     * cuidado de no poner "/" al principio (y tampoco lo pongas al final, pues
     * el procesamiento interno del m�todo lo requiere). Por ejemplo, si el
     * directorio X est� en la misma carpeta que Y y quieres obtener el
     * contenido de X, utiliza simplemente "X" como ruta.
     * @param clase Clase desde la que parte la ruta para localizar el
     * directorio.
     * @return ArrayList de im�genes
     */
    public static ArrayList<Image> AsignarIm�genesJAR(String ruta, Class clase) throws IOException {
        ArrayList<String> ArrayNombres = ObtenerNombresDirectorioJAR(ruta, clase);
        ArrayList<Image> ArrayIm�genes = new ArrayList<>();

        /**
         * Rellenamos ArrayIm�genes con las im�genes
         */
        try {
            for (int i = 0; i < ArrayNombres.size(); i++) {
                Image imagen = new Image(M�todosCompartidos.class.getResourceAsStream(ArrayNombres.get(i)));
                ArrayIm�genes.add(imagen);
            }
        } catch (NullPointerException e) {
            new Di�logoGen�rico(constantes.TIPOS_DI�LOGO.SIN_FONDOS.Cadena);
            System.exit(0);
        }
        return ArrayIm�genes;
    }

    /**
     * Obtiene todos los nombres de archivo dentro de un directorio espec�fico
     * de un JAR, partiendo de una clase en concreto.
     *
     * @param ruta Ruta del archivo partiendo desde la clase.
     * @param clase Clase desde la que parte la ruta.
     * @return ArrayList<String> con el listado de nombres de archivo dentro del
     * directorio indicado
     * @throws IOException
     */
    public static ArrayList<String> ObtenerNombresDirectorioJAR(String ruta, Class clase) throws IOException {
        CodeSource src = clase.getProtectionDomain().getCodeSource();
        ArrayList<String> ArrayNombres = new ArrayList<>();
        if (src != null) {
            URL jar = src.getLocation();
            ZipInputStream zip = new ZipInputStream(jar.openStream());
            // Itera por cada uno de los archivos del JAR
            while (true) {
                ZipEntry e = zip.getNextEntry();
                if (e == null) {
                    break;
                }
                if (e.getName().contains(ruta) && !e.getName().endsWith(ruta + "/")) {
                    ArrayNombres.add(e.getName().substring(e.getName().indexOf(ruta.substring(ruta.indexOf("/") + 1))));
                }

            }
        } else {
            System.out.println("No se ha podido acceder al directorio");
        }
        return ArrayNombres;
    }

    /**
     *
     * M�todo para obtener las im�genes de un directorio
     *
     * @param ruta Ruta del directorio
     * @return ArrayList de im�genes Sustiuido por "AsignarIm�genesJAR", ya que
     * todos los archivos de imagen se obtiene de dentro del mismo.
     */
    public ArrayList<Image> AsignarIm�genes(String ruta) {
        File archivo = new File(ruta);
        File[] imagenesDirectorio = archivo.listFiles();
        ArrayList<Image> ArrayIm�genes = new ArrayList<>();
        try {
            for (int i = 0; i < imagenesDirectorio.length; i++) {
                Image imagen = new Image(new File(ruta + imagenesDirectorio[i].getName()).toURI().toString());
                ArrayIm�genes.add(imagen);
            }
        } catch (NullPointerException e) {
            new Di�logoGen�rico(constantes.TIPOS_DI�LOGO.SIN_FONDOS.Cadena);
            System.exit(0);
        }
        return ArrayIm�genes;
    }

}
