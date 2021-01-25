package pvzheroes.clases.métodosCompartidos;

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
import static pvzheroes.clases.ModoGráficos.ListadoCFil;
import pvzheroes.clases.componentes.ComponenteGráficoDispersión;
import pvzheroes.clases.componentes.ComponenteGráficoGrupal;
import pvzheroes.clases.constantes;
import pvzheroes.clases.diccionarios.DiccionarioPropiedades;
import pvzheroes.clases.diálogos.DiálogoGenérico;

public class MétodosCompartidos {

    public static int TipoSeleccionado = 0;

    /**
     * Opciones imagen diálogo opciones
     */
    public static final ArrayList<String> IMÁGENES_FONDO = new ArrayList();

    /**
     * Desplaza las filas de un GridPane al añadir/quitar un elemento
     *
     * @param Aumentar Booleano que especifica si se quiere aumentar (true) o
     * reducir (false)
     * @param NúmeroOrigen Número de fila a partir del cual se produce el
     * desplazamiento
     * @param Panel GridPane en cuestión
     * @param Vertical El desplazamiento es Vertical (true) u Horizontal (false)
     * @param MoverNoVisibles Booleano que especifica si se quieren mover o no
     * los nodos no visibles
     */
    public static void DesplazarCeldas(Boolean Aumentar, Integer NúmeroOrigen, GridPane Panel, Boolean Vertical, Boolean MoverNoVisibles) {
        Integer NúmeroNodo = 0;
        for (int i = 0; i < Panel.getChildren().size(); i++) {
            if (Vertical) {
                NúmeroNodo = GridPane.getRowIndex(Panel.getChildren().get(i));
                if (Aumentar) {
                    if (MoverNoVisibles || (!MoverNoVisibles && Panel.getChildren().get(i).isVisible())) {
                        GridPane.setRowIndex(Panel.getChildren().get(i), NúmeroNodo == null ? 1 : 1 + NúmeroNodo);
                    }
                } else {
                    // En caso de borrado, sólo se mueven las filas posteriores al botón pulsado
                    if (MoverNoVisibles || (!MoverNoVisibles && Panel.getChildren().get(i).isVisible())) {
                        if (NúmeroNodo > NúmeroOrigen) {
                            GridPane.setRowIndex(Panel.getChildren().get(i), NúmeroNodo == null ? 1 : NúmeroNodo - 1);
                        }
                    }
                }
            } else {
                NúmeroNodo = GridPane.getColumnIndex(Panel.getChildren().get(i));
                if (Aumentar) {
                    if (MoverNoVisibles || (!MoverNoVisibles && Panel.getChildren().get(i).isVisible())) {
                        GridPane.setColumnIndex(Panel.getChildren().get(i), NúmeroNodo == null ? 1 : 1 + NúmeroNodo);
                    }
                } else {
                    // En caso de borrado, sólo se mueven las columnas posteriores al botón pulsado
                    if (MoverNoVisibles || (!MoverNoVisibles && Panel.getChildren().get(i).isVisible())) {
                        if (NúmeroNodo > NúmeroOrigen) {
                            GridPane.setColumnIndex(Panel.getChildren().get(i), NúmeroNodo == null ? 1 : NúmeroNodo - 1);
                        }
                    }
                }
            }
        }
    }

    /**
     * Elimina la fila en la que se ha pulsado un botón de un GridPane y
     * desplaza las siguientes atrás. En caso de que exista un filtro asociado a
     * dicha fila, también se elimina. Se asume que el botón se encuentra
     * directamente insertado en la celda, no dentro de algún contenedor tipo
     * HBox o VBox.
     *
     * @param BotónOrigen Botón "-" de la fila que se quiere eliminar
     * @param Panel GridPane en cuestión
     * @param ListaFiltros Lista de filtros ya existentes (para eliminar el
     * asociado a la fila que se elimina)
     */
    public static void EliminarFilas(Button BotónOrigen, GridPane Panel, ArrayList<String> ListaFiltros) {
        // Itera por las celdas para obtener el número de fila del botón
        ObservableList<Node> componentesDeCelda = Panel.getChildren();
        Integer NúmeroFila = 0;
        ArrayList<String> AEliminar = new ArrayList<>();
        for (int i = 0; i < componentesDeCelda.size(); i++) {
            if (componentesDeCelda.get(i).equals(BotónOrigen)) {
                NúmeroFila = GridPane.getRowIndex(componentesDeCelda.get(i));
                /* Se elimina el filtro/función del ArrayLists/HashMap correspondiente */
                if (Panel instanceof SentenciaIndividual) { // Proviene de un SentenciaIndividual
                    SentenciaIndividual c = (SentenciaIndividual) Panel;
                    for (String filtro : c.FiltrosCartas) {
                        if (filtro.equals(((Label) componentesDeCelda.get(i - 1)).getText())) {
                            AEliminar.add(filtro);
                        }
                    }
                } else {
                    // Proviene del diálogo de funciones de grupo o del diálogo de consulta de filtro de funciones de grupo
                    String FunciónAEliminar = ((Label) Panel.getChildren().get(i - 1)).getText();
                    if (ListaFiltros != null) {
                        ListaFiltros.remove(FunciónAEliminar);
                    }

                }
                /* Se eliminan el botón y la etiqueta. */
                componentesDeCelda.remove(componentesDeCelda.get(i));
                componentesDeCelda.remove(componentesDeCelda.get(i - 1));
            }
        }
        DesplazarCeldas(false, NúmeroFila, Panel, true, false);

        /*Si el panel sobre el que se está operando es ComponentesFuncionesGrupo, SentenciaIndividual DiálogoFuncionesGrupo
            no hace falta eliminar los filtros porque se eliminan automáticamente al cerrar el diálogo.*/
        if (Panel instanceof SentenciaIndividual) {
            SentenciaIndividual c = (SentenciaIndividual) Panel;
            /* Se hace de este modo para evitar ConcurrentModificationException resultado 
            de modificar un bucle que se está iterando */
            c.FiltrosCartas.removeAll(AEliminar);

            // Comprobamos que el primer filtro tiene AND y el paréntesis y, en caso contrario, lo añadimos
            for (int i = 0; i < c.FiltrosCartas.size(); i++) {
                if (!c.FiltrosCartas.get(0).matches("AND\\s{1}\\(.*")) {
                    String FiltroAdaptadoAPrimeraPosición = c.FiltrosCartas.get(0).replace(c.FiltrosCartas.get(0).substring(0, 4), "AND (");
                    c.FiltrosCartas.set(0, FiltroAdaptadoAPrimeraPosición);
                    // Cambiamos también la etiqueta que corresponde a la primera fila
                    for (Node nodo : c.getChildren()) {
                        try {
                            if (GridPane.getColumnIndex(nodo) == 0 && GridPane.getRowIndex(nodo) == 0) {
                                ((Label) nodo).setText(FiltroAdaptadoAPrimeraPosición);
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
     * Elimina la columna en la que se ha pulsado un botón de un GridPane y
     * desplaza las siguientes atrás. Se asume que el botón se encuentra dentro
     * de un contenedor "ComponenteGráficoGrupal" o
     * "ComponenteGráficoDispersión". Los nodos que no son visibles no se
     * eliminan.
     *
     * @param BotónOrigen Botón de la fila que se quiere eliminar
     * @param Panel GridPane en el que se realiza la modificación
     * @param Listado Listado de ComponentesFuncionesGrupo o listado de
     * ComponentesFiltros (para eliminar la referencia del que se elimina)
     */
    public static <T> void EliminarColumnas(Button BotónOrigen, GridPane Panel, ArrayList<T> Listado) {
        ObservableList<Node> componentesDeCelda = Panel.getChildren();
        ArrayList<Node> AEliminar = new ArrayList();
        int ColumnaBotón = 0;
        for (int i = 0; i < componentesDeCelda.size(); i++) { // ADAPTAR A PARTIR DE AQUÍ PARA BORRAR TAMBIÉN CFIL, en ese caso es un VBOX
            Boolean CGG = componentesDeCelda.get(i).getClass() == ComponenteGráficoGrupal.class;
            Boolean EsCGD = componentesDeCelda.get(i).getClass() == ComponenteGráficoDispersión.class;

            if (CGG) {
                Boolean CGGContieneBotón = ((HBox) componentesDeCelda.get(i)).getChildren().get(1).equals(BotónOrigen);
                if (CGGContieneBotón) {
                    ColumnaBotón = GridPane.getColumnIndex(componentesDeCelda.get(i));
                    AEliminar.add(componentesDeCelda.get(i));
                }
            } else if (EsCGD && ((VBox) componentesDeCelda.get(i)).getChildren().size() == 3) {
                Boolean CGDContieneBotón = ((HBox) ((VBox) componentesDeCelda.get(i)).getChildren().get(0)).getChildren().get(1).equals(BotónOrigen);
                if (CGDContieneBotón) {
                    ColumnaBotón = GridPane.getColumnIndex(componentesDeCelda.get(i));
                    AEliminar.add(componentesDeCelda.get(i));
                }
            }
        }

        for (Node nodo : componentesDeCelda) {
            if (GridPane.getColumnIndex(nodo) == ColumnaBotón && nodo.isVisible()) {
                if (nodo instanceof SentenciasGrupales || nodo instanceof SentenciaIndividual) {
                    Listado.remove(nodo);
                }
                AEliminar.add(nodo);
            }
        }

        for (Node nodoAEliminar : AEliminar) {
            Panel.getChildren().remove(nodoAEliminar);
        }
        // Sólo se ejecuta una vez porque afecta a toda la columna
        MétodosCompartidos.DesplazarCeldas(false, ColumnaBotón, Panel, false, false);

    }

    /**
     * Activa/desactiva la tabla/área de texto en función de la búsqueda
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
     * Método que realiza las búsquedas grupales que parten de un
     * SentenciasGrupales (modo básico y de gráficos)
     *
     * @param sentenciasGrupales SentenciasGrupales con las funciones de grupo a
     * buscar
     * @param conn Conexión a la base de datos
     * @return Un ArrayList<String> con los resultados de las búsquedas
     * @throws SQLException Excepción derivada de problemas en la búsqueda
     */
    public ArrayList<String> BúsquedaGrupal(SentenciasGrupales sentenciasGrupales, Connection conn) throws SQLException {
        Statement st = conn.createStatement();
        /* Busca en el HashMap "Funciones definidas" todas las claves
        guardadas en forma de etiquetas. Utiliza esas claves para obtener las 
        sentencias e ir ejecutándolas y añadiendo los resultados*/
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
     * Devuelve el número de columnas del GridPane que se le pasa como argumento
     *
     * @param panel GridPane que se quiere analizar
     * @return Número de columnas del GridPane
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Integer ObtenerNúmeroColumnas(GridPane panel) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
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
     * Método que realiza la búsqueda normal en el modo básico y en el de
     * gráficos (para el Scattered)
     *
     * @param FiltrosCartas Filtros ya existentes
     * @return Una lista con las cartas resultado de la consulta
     * @throws SQLException
     */
    public ArrayList<Carta> Búsqueda(ArrayList<String> FiltrosCartas, Object ObjetoOrigen) throws SQLException {
        ArrayList<Carta> CartasDevueltas = new ArrayList();

        // Establece la sentencia base
        String Sentencia = "";
        for (int i = 0; i < FiltrosCartas.size(); i++) {
            if (FiltrosCartas.get(i).matches(".*Atributos = \"Anti-Hero\"") || FiltrosCartas.get(i).matches(".*Atributos = \"Overshoot\"")
                    || FiltrosCartas.get(i).matches(".*Atributos = \"Splash Damage\"") || FiltrosCartas.get(i).matches(".*Atributos = \"Armored\"")) {
                Sentencia = ConversiónFiltrosAtributosNuméricos(FiltrosCartas.get(i), true);
            } else if (FiltrosCartas.get(i).matches(".*Atributos <> \"Anti-Hero\"") || FiltrosCartas.get(i).matches(".*Atributos <> \"Overshoot\"")
                    || FiltrosCartas.get(i).matches(".*Atributos <> \"Splash Damage\"") || FiltrosCartas.get(i).matches(".*Atributos <> \"Armored\"")) {
                Sentencia = ConversiónFiltrosAtributosNuméricos(FiltrosCartas.get(i), false);
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

        // Establece la conexión y ejecuta la sentencia
        Statement st = FXMLDocumentController.conn.createStatement();
        ResultSet rs = st.executeQuery(sentenciaSQL);

        if (ObjetoOrigen instanceof FXMLDocumentController) {
            FXMLDocumentController Controlador = (FXMLDocumentController) ObjetoOrigen;
            // Limpia los resultados previos
            try {
                Controlador.Tabla.getItems().clear();
            } catch (NullPointerException e) {
                System.out.println("Tabla vacía");
            }
            Controlador.data.clear();
        }

        // Itera por los resultados y añade la carta. No es necesario usar "getInt" para los int
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
     * Obtiene el primer componente de un tipo específico que se encuentre en un
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
    public static <T> T getObjetoPosiciónRelativaGrid(T t, GridPane panel, Node nodoPartida, int posicionesX, int posicionesY) {
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
     * Obtiene el componente número @Posición de un tipo específico que se
     * encuentre en un HBox o VBox. Por ejemplo, una posición 2 devolverá el
     * tercer hijo del HBox/VBox que coincida con el tipo. El componente ha de
     * ser visible para ser devuelto.
     *
     * @param <T> Componente que se quiere obtener
     * @param t Componente que se quiere obtener
     * @param box VBox o HBox en la que se encuentra el componente
     * @param Posición Posición del elemento, empezando por 0.
     * @return El componente buscado, en caso de existir
     */
    public static <T> T getObjetoPosiciónRelativaBox(T t, Pane box, int Posición) {
        for (Node nodo : box.getChildrenUnmodifiable()) {
            if (nodo.getClass() == t.getClass() && nodo.isVisible()) {
                if (Posición != 0) {
                    Posición--;
                } else {
                    t = (T) nodo;
                    return t;
                }
            }
        }
        return null;
    }

    /**
     * Métodos para obtener un código Hex a partir de un color RGB
     *
     * @param color Color RGB
     * @return Código Hex
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
     * que contienen números, para que funcionen correctamente
     *
     * @param filtro Filtro sobre el que se desea realizar la conversión
     * @param EsExactamente Booleano que indica si se trata de "=" (true) o "<>"
     * (false)
     * @return Filtro con la equivalencia "= "Atributo"" cambiada por "LIKE
     * "%Atributo%"" o "<> "Atributo"" cambiado por "NOT LIKE "%Atributo%"
     */
    private String ConversiónFiltrosAtributosNuméricos(String Sentencia, Boolean EsExactamente) {
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
     * Clase auxiliar para que setToolTipDatoGráficoXy sepa si la etiqueta está
     * siendo o no arrastrada
     */
    static class BooleanoArrastrando {

        Boolean Arrastrando = false;
    }

    /**
     * Hace que al pasar el ratón por encima del dato en cuestión, aparezca una
     * etiqueta arrastrable con su valor y el listado de datos que coinciden con
     * el mismo. Al salir del dato, su aspecto retorna al estado inicial. En los
     * gráficos de dispersión, además, se genera un encabezado que muestra las
     * coordenadas del punto.
     *
     * @param <T> El dato sobre el que aplicar la etiqueta. Puede ser
     * XYChart.Data o PieChart.Data
     * @param dato El dato sobre el que aplicar la etiqueta. Puede ser
     * XYChart.Data o PieChart.Data
     */
    public static <T> void setToolTipDatoGráfico(T dato,
            ArrayList<ArrayList> listadoResultados)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {

        BooleanoArrastrando BA = new BooleanoArrastrando();
        dato = (T) dato;

        StackPane contenedor = new StackPane();

        Rectangle r = CrearRectángulo(10, 10, Color.WHITE, "black", 0);

        VBox contenedorSecundario = new VBox();
        //ArrayList<Hyperlink> ListaLinks = new ArrayList();
        HashMap<Hyperlink, String> ListaLinksColores = new HashMap();

        // Si no se ponen como dimensiones máximas, se extienden por todo el nodo
        contenedor.setMaxSize(30, 20);
        contenedorSecundario.setMaxSize(30, 20);

        final Label primeraEtiqueta = new Label();
        final Label encabezadoCoordenadas = new Label("Coordenadas");

        double AlturaFinal;
        double alturaPalabra = 0;
        double anchuraPalabraMásLarga = 0;

        if (dato instanceof XYChart.Data) {
            XYChart.Data datoXY = (XYChart.Data) dato;
            if (listadoResultados == null) {
                primeraEtiqueta.setText(String.valueOf(datoXY.getYValue()));
                primeraEtiqueta.setId(constantes.IDs_CSS.ETIQUETA_NEGRA_G.identificador);
                anchuraPalabraMásLarga = CalcularDimensionesTexto(primeraEtiqueta)[1];
                AlturaFinal = CalcularDimensionesTexto(primeraEtiqueta)[0];
            } else { // Se trata de un gráfico de dispersión
                int x = 0;
                double alturaEncabezado = CalcularDimensionesTexto(encabezadoCoordenadas)[0];
                AlturaFinal = alturaEncabezado;
                for (SentenciaIndividual cofil : ListadoCFil) {
                    String[] ValoresCombos = cofil.ObtenerValoresComboBoxes();
                    String ValorColor = MétodosCompartidos.AStringHex(cofil.ObtenerColorPicker().getValue());
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
                                double SumaExtra = 23 - (y * 0.08); // Cada vez se añade menos para evitar el desfase
                                if (SumaExtra > 0) {
                                    AlturaFinal = AlturaFinal + alturaPalabra + SumaExtra;
                                } else {
                                    AlturaFinal = AlturaFinal + alturaPalabra;
                                }

                            } else {
                                AlturaFinal = alturaPalabra + 23;
                            }
                            double longitudPalabra = CalcularDimensionesTexto(link)[1];
                            if (anchuraPalabraMásLarga < longitudPalabra) {
                                anchuraPalabraMásLarga = longitudPalabra;
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
            Method método = datoPie.getClass().getDeclaredMethod("setNode", Node.class);
            método.setAccessible(true);
            método.invoke(datoPie, contenedor);
            anchuraPalabraMásLarga = CalcularDimensionesTexto(primeraEtiqueta)[1];
            AlturaFinal = CalcularDimensionesTexto(primeraEtiqueta)[0];
        }

        double AnchuraFinal = anchuraPalabraMásLarga + 30;
        contenedor.setMinWidth(AnchuraFinal);
        contenedorSecundario.setMinWidth(AnchuraFinal);
        // Se les añade un poco más de tamaño para que el resto de componentes no tapen los bordes
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
     * Hace que uno o varios nodos se puedan mover arrastrándolo con el cursor
     *
     * @param nodos Nodos que se quiere poder arrastrar
     */
    public static void permitirArrastrar(final Node... nodos) {
        final Delta dragDelta = new Delta();
        for (Node nodo : nodos) {
            nodo.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    // Guarda la distancia entre el nodo y el ratón
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
                    /* Mueve el nodo en función de la localización del ratón y de
                    la distancia con el nodo que había inicialmente */
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
     * con CSS no se tienen en cuenta en el cálculo (esto significa que, si
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
     * Crea un rectángulo con los parámetros indicados, aunque la sombra es
     * predeterminada.
     *
     * @param TamañoX Anchura del rectángulo
     * @param TamañoY Altura del rectángulo
     * @param Color Color de fondo
     * @param ColorBorde Color de borde (String, véase documentación de JavaFX)
     * @param tamañoCurva Tamaño de la curva, para hacer bordes redondeados. Un
     * valor de 0 crea un rectángulo puro.
     * @return
     */
    public static Rectangle CrearRectángulo(double TamañoX, double TamañoY, Color Color, String ColorBorde, double tamañoCurva) {
        Rectangle r = new Rectangle(TamañoX, TamañoY, Color);
        r.setStyle("-fx-stroke: " + ColorBorde + ";");
        DropShadow s = new DropShadow();
        s.setWidth(20);
        s.setHeight(20);
        s.setOffsetX(10);
        s.setOffsetY(10);
        s.setRadius(10);
        r.setEffect(s);
        r.setArcHeight(tamañoCurva);
        r.setArcWidth(tamañoCurva);
        return r;
    }

    /**
     * Cuenta el número de coincidencias de una palabra dentro de un string
     *
     * @param string El string a analizar
     * @param palabra La palabra que se busca
     * @return Entero con el número de coincidencias
     */
    public static int ContarCoincidenciasEnString(String string, String palabra) {
        int posición = string.indexOf(palabra);
        int coincidencias = 0;
        while (posición != -1) {
            coincidencias++;
            string = string.substring(posición + 1);
            posición = string.indexOf(palabra);
        }
        return coincidencias;
    }

    /**
     * Hace que todos los componentes de un HBox crezcan equitativamente de
     * manera horizontal cuando su tamaño está por encima de su PrefWidth. Si no
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
     * Establece un atajo de teclado en un botón.
     *
     * @param Botón Botón sobre el que se quiere establecer el atajo
     * @param CombinaciónTeclas KeyCodeCombination que ejercerá de atajo
     * @param método Método que se quiere ejecutar. Un valor "null"
     * desencadenará el método "fire()" del botón
     * @param ObjetoFuente Objeto en el que se encuentra el método a invocar. Si
     * el método es "fire()", dejarlo en "null".
     * @param Parámetros Número variable de parámetros del método a invocar. Si
     * el método es "fire()", dejarlo en "null".
     * @return Booleano indicando si la inserción del atajo se ha realizado
     */
    public static Boolean EstablecerAceleradorEnBotón(Button Botón,
            KeyCodeCombination CombinaciónTeclas, Method método, Object ObjetoFuente, Object... Parámetros) {
        try {
            Botón.getScene().getAccelerators().put(CombinaciónTeclas, new Runnable() {
                @Override
                public void run() {
                    if (método == null) {
                        Botón.fire();
                    } else {
                        try {
                            método.invoke(ObjetoFuente, Parámetros);
                        } catch (IllegalAccessException ex) {
                            System.out.println("No se puede acceder al método");
                        } catch (IllegalArgumentException ex) {
                            System.out.println("Uno o varios parámetros "
                                    + "pasados al método no son válidos");
                        } catch (InvocationTargetException ex) {
                            System.out.println("Error en la invocación del método");
                        }
                    }
                }
            });
            Botón.requestFocus();
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Compara la anchura de dos textos y devuelve el valor más alto. Requiere
     * el método "CalcularDimensionesTexto" para funcionar.
     *
     * @param etiquetado1 Primer Label, Hyperlink, etc.
     * @param etiquetado2 Segundo Label, Hyperlink, etc.
     * @return Valor double con la anchura mayor.
     */
    public static double TextoMásAncho(Labeled etiquetado1, Labeled etiquetado2) {
        double[] dimensiones1 = MétodosCompartidos.CalcularDimensionesTexto(etiquetado1);
        double[] dimensiones2 = MétodosCompartidos.CalcularDimensionesTexto(etiquetado2);
        if (dimensiones1[1] > dimensiones2[1]) {
            return (dimensiones1[1]);
        } else {
            return dimensiones2[1];
        }
    }

    /**
     * Método para obtener las imágenes de un directorio dentro del JAR.
     * Requiere que se incluya "ObtenerNombresDirectorioJAR". Ten cuidado de que
     * los nombres no sean demasiado similares o puede dar lugar a problemas.
     *
     * No se utiliza realmente en el programa, puesto que hubo problemas en la
     * obtención de imágenes al convertir a .exe.
     *
     * @param ruta Ruta del directorio partiendo desde la clase indicada en el
     * parámetro "clase". Si el directorio comparte directorio con la clase, ten
     * cuidado de no poner "/" al principio (y tampoco lo pongas al final, pues
     * el procesamiento interno del método lo requiere). Por ejemplo, si el
     * directorio X está en la misma carpeta que Y y quieres obtener el
     * contenido de X, utiliza simplemente "X" como ruta.
     * @param clase Clase desde la que parte la ruta para localizar el
     * directorio.
     * @return ArrayList de imágenes
     */
    public static ArrayList<Image> AsignarImágenesJAR(String ruta, Class clase) throws IOException {
        ArrayList<String> ArrayNombres = ObtenerNombresDirectorioJAR(ruta, clase);
        ArrayList<Image> ArrayImágenes = new ArrayList<>();

        /**
         * Rellenamos ArrayImágenes con las imágenes
         */
        try {
            for (int i = 0; i < ArrayNombres.size(); i++) {
                Image imagen = new Image(MétodosCompartidos.class.getResourceAsStream(ArrayNombres.get(i)));
                ArrayImágenes.add(imagen);
            }
        } catch (NullPointerException e) {
            new DiálogoGenérico(constantes.TIPOS_DIÁLOGO.SIN_FONDOS.Cadena);
            System.exit(0);
        }
        return ArrayImágenes;
    }

    /**
     * Obtiene todos los nombres de archivo dentro de un directorio específico
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
     * Método para obtener las imágenes de un directorio
     *
     * @param ruta Ruta del directorio
     * @return ArrayList de imágenes Sustiuido por "AsignarImágenesJAR", ya que
     * todos los archivos de imagen se obtiene de dentro del mismo.
     */
    public ArrayList<Image> AsignarImágenes(String ruta) {
        File archivo = new File(ruta);
        File[] imagenesDirectorio = archivo.listFiles();
        ArrayList<Image> ArrayImágenes = new ArrayList<>();
        try {
            for (int i = 0; i < imagenesDirectorio.length; i++) {
                Image imagen = new Image(new File(ruta + imagenesDirectorio[i].getName()).toURI().toString());
                ArrayImágenes.add(imagen);
            }
        } catch (NullPointerException e) {
            new DiálogoGenérico(constantes.TIPOS_DIÁLOGO.SIN_FONDOS.Cadena);
            System.exit(0);
        }
        return ArrayImágenes;
    }

}
