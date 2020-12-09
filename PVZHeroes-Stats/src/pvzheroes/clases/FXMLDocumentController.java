package pvzheroes.clases;

import pvzheroes.clases.componentes.SentenciasGrupales;
import pvzheroes.clases.componentes.SentenciaIndividual;
import pvzheroes.clases.diccionarios.DiccionarioSQL;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.security.CodeSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;
import pvzheroes.clases.diccionarios.DiccionarioPropiedades;
import pvzheroes.clases.di�logos.Di�logoGen�rico;
import pvzheroes.clases.di�logos.Di�logoOpciones;
import pvzheroes.clases.efectosConcretos.CrearToolTip;
import pvzheroes.clases.m�todosCompartidos.M�todosCompartidos;

public class FXMLDocumentController implements Initializable {

    /**
     * Conexi�n con la BBDD
     */
    public static Connection conn = null;

    //************ Todos los componentes que deben ser referenciados ************//
    //****** ELEMENTOS COMUNES QUE NO SON COMPONENTES ******
    /**
     * La ventana
     */
    Stage Ventana;

    /**
     * Paneles
     */
    @FXML
    protected AnchorPane PanelPrincipal;

    @FXML
    protected AnchorPane PanelB�sico, PanelAvanzado, PanelGr�ficos;

    /**
     * Elementos de los men�s
     */
    @FXML
    protected MenuBar Barrademen�s;

    @FXML
    protected RadioMenuItem RadioB�sico, RadioAvanzado, RadioGr�ficos;

    @FXML
    protected MenuItem Men�Cerrar, Men�Opciones, Men�Ayuda, Men�AcercaDe;

    /**
     * Bot�n B�squeda
     */
    @FXML
    protected Button Bot�nB�squeda;

    /**
     * Todo lo relacionado con la tabla y el TextArea
     */
    @FXML
    public TableView Tabla;
    public final ObservableList<Carta> data = FXCollections.observableArrayList();
    ArrayList<TableColumn> ListaColumnas = new ArrayList();
    @FXML
    protected TextArea AreaTextoGrupo;

    /**
     * GridPane que agrupa la tabla y el �rea de texto
     */
    @FXML
    protected GridPane TablaYAreaTexto;

    /**
     * Define los filtros que se muestran en la consulta
     */
    public ArrayList<String> FiltrosCartas = new ArrayList();
    @FXML
    public GridPane PanelFiltros;
    @FXML
    public Label EtiquetaNuevoFiltro;
    @FXML
    public Button Bot�nNuevoFiltro;

    //****** COMPONENTES COMUNES ******
    /**
     * Todo lo relacionado con el bot�n "Cambiar Tabla"
     */
    @FXML
    protected ImageView ImagenBot�n;
    @FXML
    protected Button Bot�nTabla;
    protected ArrayList<Image> ImagenesBoton;

    /**
     * Di�logo de funciones de grupo
     */
    protected SentenciasGrupales sentenciasGrupales;

    // ****** MODO B�SICO ******
    /**
     * Todo lo relacionado con el ToggleGroup
     */
    @FXML
    protected ToggleGroup opcionesB�squeda;

    /**
     * Todo lo relacionado con el checkList
     */
    @FXML
    protected CheckListView checkListView;
    ObservableList<String> itemsMarcados;
    ObservableList<String> itemsDisponibles;

    /**
     * Componente Sentencia Individual del Modo B�sico
     */
    protected SentenciaIndividual sentenciaIndividual;

    //****** MODO AVANZADO ******
    /**
     * Espacios para escribir consultas
     */
    @FXML
    protected TextField Consultasimple;
    @FXML
    protected TextArea Consultasdegrupo;

    /**
     * Bot�n de b�squeda grupal
     */
    @FXML
    protected Button Bot�nB�squedaGrupo;

    /**
     * Etiquetas
     */
    @FXML
    protected Label Etiqueta1, Etiqueta2;

    /**
     * GridPane del Modo Avanzado
     */
    @FXML
    protected GridPane PanelSentenciasGrupo;

    //****** VARIABLES GLOBALES ******
    /**
     * Define el modo (0-B�sico, 1-Avanzado, 2-Gr�ficos)
     */
    public static int modo = 0;

    //****** REFERENCIAS ******
    // Punto de acceso a los m�todos compartidos
    public M�todosCompartidos MC;

    @FXML
    protected void Acci�nBot�nBuscar(ActionEvent event) throws SQLException {
        Boolean sonidoON = DiccionarioPropiedades.PROP.getProperty("sonido").equals("ON");
        if (modo != 0) {
            Boolean B�squedaExitosa = new ModoAvanzado(this).B�squeda();
            if (sonidoON && B�squedaExitosa) {
                new MediaPlayer(constantes.SONIDOS.BUSCAR_O_GR�FICO.sonido).play();
            }
        } else {
            if (sonidoON) {
                new MediaPlayer(constantes.SONIDOS.BUSCAR_O_GR�FICO.sonido).play();
            }
            if (!checkListView.isDisable()) {
                MC.ActivarTabla(true, this);
                data.addAll(MC.B�squeda(sentenciaIndividual.FiltrosCartas, this));
            } else {
                MC.ActivarTabla(false, this);
                ArrayList<String> resultados = MC.B�squedaGrupal(sentenciasGrupales, conn);
                int contadorResultado = 0;
                String texto = "";
                for (int x = 0; x < sentenciasGrupales.getChildren().size(); x++) {
                    if (sentenciasGrupales.getChildren().get(x) instanceof Label) {
                        String textoEtiqueta = ((Label) sentenciasGrupales.getChildren().
                                get(x)).getText();
                        String separador = "\n";
                        if (texto.equals("")) {
                            separador = "";
                        }
                        texto = texto + separador
                                + "- El valor de la sentencia \"" + textoEtiqueta
                                + "\" es " + resultados.get(contadorResultado);
                        contadorResultado++;
                    }
                }
                AreaTextoGrupo.setText(texto);
            }
        }
    }

    @FXML
    protected void Acci�nBot�nBuscarGrupo(ActionEvent event) throws SQLException {
        Boolean B�squedaExitosa = new ModoAvanzado(this).B�squedaGrupo();
        if (DiccionarioPropiedades.PROP.getProperty("sonido").equals("ON") && B�squedaExitosa) {
            new MediaPlayer(constantes.SONIDOS.BUSCAR_O_GR�FICO.sonido).play();
        }
    }

    /**
     *
     * Cambia la imagen del bot�n (planta/zombie/ambos) y el tipo seleccionado
     * sobre el que se busca.
     *
     * @param Direcci�n La direcci�n de selecci�n de imagen y tipo (usando las
     * flechas de direcci�n se puede ir en una u otra)
     */
    public void Acci�nBot�nTabla(Integer Direcci�n) {
        if (DiccionarioPropiedades.PROP.getProperty("sonido").equals("ON")) {
            MediaPlayer reproductor = new MediaPlayer(constantes.SONIDOS.BOT�N.sonido);
            reproductor.play();
        }

        try {
            ImagenBot�n.setImage(ImagenesBoton.get(M�todosCompartidos.TipoSeleccionado + Direcci�n));
            M�todosCompartidos.TipoSeleccionado = M�todosCompartidos.TipoSeleccionado + Direcci�n;
        } catch (IndexOutOfBoundsException e) {
            if (Direcci�n == 1) {
                ImagenBot�n.setImage(ImagenesBoton.get(0));
                M�todosCompartidos.TipoSeleccionado = 0;
            } else {
                ImagenBot�n.setImage(ImagenesBoton.get(2));
                M�todosCompartidos.TipoSeleccionado = 2;
            }
        } finally { // Se modifican los checks disponibles en el CheckListView
            itemsDisponibles.clear();
            itemsDisponibles.addAll(constantes.COLUMNAS);
            itemsDisponibles.remove(constantes.COLUMNAS[11]);
            if (M�todosCompartidos.TipoSeleccionado != 2) { // Si no est� seleccionada la tercera opci�n, no se puede marcar "Tipo"
                itemsDisponibles.remove(constantes.COLUMNAS[10]);
            }
            checkListView.setItems(itemsDisponibles);

            // Si alguno de los itemsMarcados coincide con los itemsDisponibles, se selecciona
            for (int i = 0; i < itemsMarcados.size(); i++) {
                try {
                    for (int x = 0; x < itemsDisponibles.size(); x++) {
                        if (itemsMarcados.get(i).equals(itemsDisponibles.get(x))) {
                            checkListView.getCheckModel().check(x);
                        }
                    }
                } catch (NullPointerException e) {
                    System.out.println("\"Tipo\" estaba marcado cuando se cambi� a un "
                            + "TipoSeleccionado en cuyo checkListView no est� disponible");
                }
            }
        }
    }

    @FXML
    private void AbrirOpciones() throws IOException {
        new Di�logoOpciones(this);
    }

    @FXML
    private void Men�Cerrar() throws SQLException {
        conn.close();
        System.exit(0);
    }

    @FXML
    private void AbrirAyuda() throws Exception {
        Runtime r = Runtime.getRuntime();
        r.exec(constantes.RUTAS_EXTERNAS.AYUDA.Ruta);
    }

    @FXML
    private void AbrirAcercaDe() throws IOException {
        new Di�logoGen�rico(constantes.TIPOS_DI�LOGO.ACERCA_DE.Cadena);
    }

    protected void ConfigurarTabla(String[] Columnas) {
        // Limpia las columnas existentes
        ListaColumnas.clear();
        Tabla.getColumns().clear();

        // A�ade las nuevas columnas en funci�n de las marcadas (modo b�sico) o de las buscadas (modo avanzado)
        if (modo == 0) {
            for (String columna : itemsMarcados) {
                ListaColumnas.add(new TableColumn(columna));
            }
        } else if (Columnas[0].equals("*")) {
            for (String columna : constantes.COLUMNAS) {
                if (!columna.equals("URL")) {
                    ListaColumnas.add(new TableColumn(columna));
                }
            }
        } else { // Se tiene que comprobar que la columna escrita corresponde a una real antes de a�adir
            for (String columnaBuscada : Columnas) {
                for (String columna : constantes.COLUMNAS) {
                    if (columnaBuscada.equalsIgnoreCase(columna)) {
                        ListaColumnas.add(new TableColumn(columna));
                    }
                }

            }
        }
        Tabla.getColumns().addAll(ListaColumnas);

        // Rellena las celdas
        for (TableColumn columna : ListaColumnas) {
            columna.setCellValueFactory(new PropertyValueFactory<>(columna.getText()));
        }
        Tabla.setItems(data);
    }

    @FXML
    private void PasarAModoAvanzado() {
        // A�ade el Bot�nB�squeda al PanelAvanzado
        PanelSentenciasGrupo.getChildren().remove(Bot�nB�squeda);
        PanelSentenciasGrupo.add(Bot�nB�squeda, 2, 0);
        Bot�nB�squeda.setId(constantes.NOMBRES_ELEMENTOS.BOT�N_BUSCAR_AV.Nombre);
        new CrearToolTip(Bot�nB�squeda, PanelAvanzado, 1);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                M�todosCompartidos.EstablecerAceleradorEnBot�n(Bot�nB�squeda, new KeyCodeCombination(KeyCode.ENTER), null, (Object) null);
            }
        });

        // Oculta todo los componentes del modo b�sico y cambia el ToolTip de la tabla compartidos con este
        PanelB�sico.setVisible(false);
        PanelB�sico.setManaged(false);
        ArrayList<Node> AEliminar = new ArrayList();
        PanelB�sico.getChildren().forEach(i -> {
            try {
                if (i.getId().equals(constantes.NOMBRES_ELEMENTOS.BOT�N_BUSCAR.Nombre + "ToolTip")
                        || i.getId().equals(constantes.NOMBRES_ELEMENTOS.TABLA.Nombre + "ToolTip")) {
                    AEliminar.add(i);
                }
            } catch (NullPointerException e) {
                System.out.println("ID no existe");
            }
        });

        PanelB�sico.getChildren().remove(AEliminar.get(0));
        Tabla.setId(constantes.NOMBRES_ELEMENTOS.TABLA_AV.Nombre);
        AreaTextoGrupo.setId(constantes.NOMBRES_ELEMENTOS.TABLA_AV.Nombre);
        new CrearToolTip(Tabla, this.TablaYAreaTexto, 1);
        new CrearToolTip(AreaTextoGrupo, this.TablaYAreaTexto, 1);

        // Oculta los componentes del modo gr�ficos
        PanelGr�ficos.setVisible(false);
        PanelGr�ficos.setManaged(false);

        // Muestra todos los componentes del modo avanzado excepto los ToolTips
        PanelAvanzado.setVisible(true);
        PanelAvanzado.setManaged(true);
        PanelAvanzado.getChildren().forEach(i -> {
            if (!(i instanceof StackPane)) {
                i.setVisible(true);
                i.setManaged(true);
                for (Node nodo : ((GridPane) i).getChildren()) {
                    nodo.setDisable(false);
                    nodo.setVisible(true);
                    nodo.setManaged(true);
                }
            }
        });

        GridPane.setMargin(Bot�nB�squeda, new Insets(0, 0, 0, 20));
        TablaYAreaTexto.setVisible(true);
        TablaYAreaTexto.setManaged(true);

        modo = 1;
    }

    @FXML
    private void PasarAModoBasico() {
        // Eliminar el bot�n "Buscar" del modo avanzado para que no sea duplicado al a�adirlo al b�sico
        if (modo == 1) {
            PanelSentenciasGrupo.getChildren().remove(Bot�nB�squeda);
        }

        // Si viene del modo gr�ficos, elimina el bot�n para seleccionar las tablas
        if (modo == 2) {
            for (Node Panel : PanelGr�ficos.getChildren()) {
                if (Panel instanceof GridPane) {
                    ((GridPane) Panel).getChildren().remove(Bot�nTabla);
                }
            }
        }
        /* En lugar de mover los nodos que est�n en un GridPane al PanelB�sico y al rev�s, se crean de nuevo.
         Sin embargo, es necesario eliminarlos cada vez porque no pueden aparecer duplicados*/
        PanelB�sico.getChildren().remove(Bot�nB�squeda);
        PanelB�sico.getChildren().add(Bot�nB�squeda);
        PanelB�sico.getChildren().remove(Bot�nTabla);
        PanelB�sico.getChildren().add(Bot�nTabla);

        // Posiciona Bot�nB�squeda y Bot�nTabla y establece sus ToolTips
        Bot�nB�squeda.setLayoutX(31.0);
        Bot�nB�squeda.setLayoutY(114.0);
        Bot�nTabla.setLayoutX(31);
        Bot�nTabla.setLayoutY(22);
        Bot�nB�squeda.setId(constantes.NOMBRES_ELEMENTOS.BOT�N_BUSCAR.Nombre);
        new CrearToolTip(Bot�nB�squeda, PanelB�sico, 0);
        new CrearToolTip(Bot�nTabla, PanelB�sico, 0); // POSIBLE ACUMULACI�N DE TOOLTIPS CON CADA CAMBIO

        // Muestra todos los componentes del modo b�sico
        PanelB�sico.setVisible(true);
        PanelB�sico.setManaged(true);
        PanelB�sico.getChildren().forEach(i -> {
            if (!i.isDisabled() && !(i instanceof StackPane)) {
                i.setVisible(true);
                i.setManaged(true);
            }
        });

        // Oculta los componentes del modo avanzado
        PanelAvanzado.setVisible(false);
        PanelAvanzado.setManaged(false);

        // Oculta los componentes del modo gr�ficos
        PanelGr�ficos.setVisible(false);
        PanelGr�ficos.setManaged(false);
        PanelGr�ficos.getChildren().forEach(i -> {
            i.setVisible(false);
            i.setManaged(false);
        });

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                M�todosCompartidos.EstablecerAceleradorEnBot�n(Bot�nB�squeda, new KeyCodeCombination(KeyCode.ENTER), null, (Object) null);
            }
        });

        TablaYAreaTexto.setVisible(true);
        TablaYAreaTexto.setManaged(true);

        modo = 0;
    }

    @FXML
    private void PasarAModoGr�ficos() {
        // Oculta todos los componentes de los otros dos modos
        PanelB�sico.setVisible(false);
        PanelB�sico.setManaged(false);
        PanelB�sico.getChildren().forEach(i -> {
            if (!i.isDisabled()) {
                i.setVisible(false);
                i.setManaged(false);
            }
        });
        PanelB�sico.getChildren().remove(Bot�nTabla);
        GridPane Panel = null;
        for (Node nodo : PanelGr�ficos.getChildren()) {
            if (nodo instanceof GridPane) {
                Panel = (GridPane) nodo;
            }
        }
        Panel.getChildren().remove(Bot�nTabla);
        Panel.add(Bot�nTabla, 0, 0);
        new CrearToolTip(Bot�nTabla, PanelGr�ficos, 2);
        Bot�nTabla.setVisible(true);
        Bot�nTabla.setManaged(true);

        GridPane.setMargin(Bot�nTabla, new Insets(0, 0, 0, 20));

        PanelAvanzado.setVisible(false);
        PanelAvanzado.setManaged(false);
        PanelAvanzado.getChildren().forEach(i -> {
            i.setVisible(false);
            i.setManaged(false);
        });
        TablaYAreaTexto.setVisible(false);
        TablaYAreaTexto.setManaged(false);

        // Elimina el bot�n de b�squeda de los otros dos modos
        Node Bot�nAEliminar = null;
        if (modo == 1) {
            for (Node nodo : PanelSentenciasGrupo.getChildren()) {
                if (nodo instanceof Button && nodo.getId().equals("Bot�nAmbosModos")) {
                    Bot�nAEliminar = nodo;
                }
            }
            PanelSentenciasGrupo.getChildren().remove(Bot�nAEliminar);
        } else {
            for (Node nodo : PanelB�sico.getChildren()) {
                if (nodo instanceof Button && nodo.getId().equals("Bot�nAmbosModos")) {
                    Bot�nAEliminar = nodo;
                }
            }
            PanelB�sico.getChildren().remove(Bot�nAEliminar);
        }

        // Muestra los componentes del modo gr�ficos
        PanelGr�ficos.setVisible(true);
        PanelGr�ficos.setManaged(true);
        PanelGr�ficos.getChildren().forEach(i -> {
            if (!(i instanceof StackPane)) {
                i.setVisible(true);
                i.setManaged(true);
            }
            if (i instanceof GridPane) {
                for (Node nodo : ((GridPane) i).getChildren()) {
                    if (nodo instanceof VBox) {
                        Button bot�nCrearGr�fico = M�todosCompartidos.getObjetoPosici�nRelativaBox(new Button(), (VBox) nodo, 0);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                M�todosCompartidos.EstablecerAceleradorEnBot�n(bot�nCrearGr�fico, new KeyCodeCombination(KeyCode.ENTER), null, (Object) null);
                            }
                        });

                    }
                }
            }
        });
        modo = 2;
    }

    // Adapta la opci�n de b�squeda (b�squeda, conteo o media)
    @FXML
    private void CambiarToggle() {
        String opci�nSeleccionada = ((RadioButton) opcionesB�squeda.getSelectedToggle()).getText();
        if (opci�nSeleccionada.equals("B�squeda")) {
            checkListView.setDisable(false);
            checkListView.setVisible(true);
            sentenciasGrupales.setDisable(true);
            sentenciasGrupales.setVisible(false);
            MC.ActivarTabla(true, this);
            sentenciaIndividual.setVisible(true);
            sentenciaIndividual.setDisable(false);
        } else {
            checkListView.setDisable(true);
            checkListView.setVisible(false);
            sentenciasGrupales.setDisable(false);
            sentenciasGrupales.setVisible(true);
            MC.ActivarTabla(false, this);
            sentenciaIndividual.setVisible(false);
            sentenciaIndividual.setDisable(true);
        }
    }

    public void CambiarFondo(String nombreFondo) throws IOException {
        PVZHeroesStats.scene.getStylesheets().clear();
        File estilo = new File(constantes.RUTAS_EXTERNAS.CSS.Ruta + "CSS" + nombreFondo + ".css");
        PVZHeroesStats.scene.getStylesheets().add("file:///" + estilo.getAbsolutePath().replace("\\", "/"));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            new inicializaci�nComponentes(this);
        } catch (IOException ex) {
            System.out.println("No se han encontrado los directorios que se deber�an ocultar");
        }
    }

    public TextArea getAreaTextoGrupo() {
        return AreaTextoGrupo;
    }

    public TableView getTabla() {
        return Tabla;
    }

    public Pane getPanelB�sico() {
        return PanelB�sico;
    }

}
