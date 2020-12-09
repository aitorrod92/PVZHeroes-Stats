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
import pvzheroes.clases.diálogos.DiálogoGenérico;
import pvzheroes.clases.diálogos.DiálogoOpciones;
import pvzheroes.clases.efectosConcretos.CrearToolTip;
import pvzheroes.clases.métodosCompartidos.MétodosCompartidos;

public class FXMLDocumentController implements Initializable {

    /**
     * Conexión con la BBDD
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
    protected AnchorPane PanelBásico, PanelAvanzado, PanelGráficos;

    /**
     * Elementos de los menús
     */
    @FXML
    protected MenuBar Barrademenús;

    @FXML
    protected RadioMenuItem RadioBásico, RadioAvanzado, RadioGráficos;

    @FXML
    protected MenuItem MenúCerrar, MenúOpciones, MenúAyuda, MenúAcercaDe;

    /**
     * Botón Búsqueda
     */
    @FXML
    protected Button BotónBúsqueda;

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
     * GridPane que agrupa la tabla y el área de texto
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
    public Button BotónNuevoFiltro;

    //****** COMPONENTES COMUNES ******
    /**
     * Todo lo relacionado con el botón "Cambiar Tabla"
     */
    @FXML
    protected ImageView ImagenBotón;
    @FXML
    protected Button BotónTabla;
    protected ArrayList<Image> ImagenesBoton;

    /**
     * Diálogo de funciones de grupo
     */
    protected SentenciasGrupales sentenciasGrupales;

    // ****** MODO BÁSICO ******
    /**
     * Todo lo relacionado con el ToggleGroup
     */
    @FXML
    protected ToggleGroup opcionesBúsqueda;

    /**
     * Todo lo relacionado con el checkList
     */
    @FXML
    protected CheckListView checkListView;
    ObservableList<String> itemsMarcados;
    ObservableList<String> itemsDisponibles;

    /**
     * Componente Sentencia Individual del Modo Básico
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
     * Botón de búsqueda grupal
     */
    @FXML
    protected Button BotónBúsquedaGrupo;

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
     * Define el modo (0-Básico, 1-Avanzado, 2-Gráficos)
     */
    public static int modo = 0;

    //****** REFERENCIAS ******
    // Punto de acceso a los métodos compartidos
    public MétodosCompartidos MC;

    @FXML
    protected void AcciónBotónBuscar(ActionEvent event) throws SQLException {
        Boolean sonidoON = DiccionarioPropiedades.PROP.getProperty("sonido").equals("ON");
        if (modo != 0) {
            Boolean BúsquedaExitosa = new ModoAvanzado(this).Búsqueda();
            if (sonidoON && BúsquedaExitosa) {
                new MediaPlayer(constantes.SONIDOS.BUSCAR_O_GRÁFICO.sonido).play();
            }
        } else {
            if (sonidoON) {
                new MediaPlayer(constantes.SONIDOS.BUSCAR_O_GRÁFICO.sonido).play();
            }
            if (!checkListView.isDisable()) {
                MC.ActivarTabla(true, this);
                data.addAll(MC.Búsqueda(sentenciaIndividual.FiltrosCartas, this));
            } else {
                MC.ActivarTabla(false, this);
                ArrayList<String> resultados = MC.BúsquedaGrupal(sentenciasGrupales, conn);
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
    protected void AcciónBotónBuscarGrupo(ActionEvent event) throws SQLException {
        Boolean BúsquedaExitosa = new ModoAvanzado(this).BúsquedaGrupo();
        if (DiccionarioPropiedades.PROP.getProperty("sonido").equals("ON") && BúsquedaExitosa) {
            new MediaPlayer(constantes.SONIDOS.BUSCAR_O_GRÁFICO.sonido).play();
        }
    }

    /**
     *
     * Cambia la imagen del botón (planta/zombie/ambos) y el tipo seleccionado
     * sobre el que se busca.
     *
     * @param Dirección La dirección de selección de imagen y tipo (usando las
     * flechas de dirección se puede ir en una u otra)
     */
    public void AcciónBotónTabla(Integer Dirección) {
        if (DiccionarioPropiedades.PROP.getProperty("sonido").equals("ON")) {
            MediaPlayer reproductor = new MediaPlayer(constantes.SONIDOS.BOTÓN.sonido);
            reproductor.play();
        }

        try {
            ImagenBotón.setImage(ImagenesBoton.get(MétodosCompartidos.TipoSeleccionado + Dirección));
            MétodosCompartidos.TipoSeleccionado = MétodosCompartidos.TipoSeleccionado + Dirección;
        } catch (IndexOutOfBoundsException e) {
            if (Dirección == 1) {
                ImagenBotón.setImage(ImagenesBoton.get(0));
                MétodosCompartidos.TipoSeleccionado = 0;
            } else {
                ImagenBotón.setImage(ImagenesBoton.get(2));
                MétodosCompartidos.TipoSeleccionado = 2;
            }
        } finally { // Se modifican los checks disponibles en el CheckListView
            itemsDisponibles.clear();
            itemsDisponibles.addAll(constantes.COLUMNAS);
            itemsDisponibles.remove(constantes.COLUMNAS[11]);
            if (MétodosCompartidos.TipoSeleccionado != 2) { // Si no está seleccionada la tercera opción, no se puede marcar "Tipo"
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
                    System.out.println("\"Tipo\" estaba marcado cuando se cambió a un "
                            + "TipoSeleccionado en cuyo checkListView no está disponible");
                }
            }
        }
    }

    @FXML
    private void AbrirOpciones() throws IOException {
        new DiálogoOpciones(this);
    }

    @FXML
    private void MenúCerrar() throws SQLException {
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
        new DiálogoGenérico(constantes.TIPOS_DIÁLOGO.ACERCA_DE.Cadena);
    }

    protected void ConfigurarTabla(String[] Columnas) {
        // Limpia las columnas existentes
        ListaColumnas.clear();
        Tabla.getColumns().clear();

        // Añade las nuevas columnas en función de las marcadas (modo básico) o de las buscadas (modo avanzado)
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
        } else { // Se tiene que comprobar que la columna escrita corresponde a una real antes de añadir
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
        // Añade el BotónBúsqueda al PanelAvanzado
        PanelSentenciasGrupo.getChildren().remove(BotónBúsqueda);
        PanelSentenciasGrupo.add(BotónBúsqueda, 2, 0);
        BotónBúsqueda.setId(constantes.NOMBRES_ELEMENTOS.BOTÓN_BUSCAR_AV.Nombre);
        new CrearToolTip(BotónBúsqueda, PanelAvanzado, 1);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                MétodosCompartidos.EstablecerAceleradorEnBotón(BotónBúsqueda, new KeyCodeCombination(KeyCode.ENTER), null, (Object) null);
            }
        });

        // Oculta todo los componentes del modo básico y cambia el ToolTip de la tabla compartidos con este
        PanelBásico.setVisible(false);
        PanelBásico.setManaged(false);
        ArrayList<Node> AEliminar = new ArrayList();
        PanelBásico.getChildren().forEach(i -> {
            try {
                if (i.getId().equals(constantes.NOMBRES_ELEMENTOS.BOTÓN_BUSCAR.Nombre + "ToolTip")
                        || i.getId().equals(constantes.NOMBRES_ELEMENTOS.TABLA.Nombre + "ToolTip")) {
                    AEliminar.add(i);
                }
            } catch (NullPointerException e) {
                System.out.println("ID no existe");
            }
        });

        PanelBásico.getChildren().remove(AEliminar.get(0));
        Tabla.setId(constantes.NOMBRES_ELEMENTOS.TABLA_AV.Nombre);
        AreaTextoGrupo.setId(constantes.NOMBRES_ELEMENTOS.TABLA_AV.Nombre);
        new CrearToolTip(Tabla, this.TablaYAreaTexto, 1);
        new CrearToolTip(AreaTextoGrupo, this.TablaYAreaTexto, 1);

        // Oculta los componentes del modo gráficos
        PanelGráficos.setVisible(false);
        PanelGráficos.setManaged(false);

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

        GridPane.setMargin(BotónBúsqueda, new Insets(0, 0, 0, 20));
        TablaYAreaTexto.setVisible(true);
        TablaYAreaTexto.setManaged(true);

        modo = 1;
    }

    @FXML
    private void PasarAModoBasico() {
        // Eliminar el botón "Buscar" del modo avanzado para que no sea duplicado al añadirlo al básico
        if (modo == 1) {
            PanelSentenciasGrupo.getChildren().remove(BotónBúsqueda);
        }

        // Si viene del modo gráficos, elimina el botón para seleccionar las tablas
        if (modo == 2) {
            for (Node Panel : PanelGráficos.getChildren()) {
                if (Panel instanceof GridPane) {
                    ((GridPane) Panel).getChildren().remove(BotónTabla);
                }
            }
        }
        /* En lugar de mover los nodos que están en un GridPane al PanelBásico y al revés, se crean de nuevo.
         Sin embargo, es necesario eliminarlos cada vez porque no pueden aparecer duplicados*/
        PanelBásico.getChildren().remove(BotónBúsqueda);
        PanelBásico.getChildren().add(BotónBúsqueda);
        PanelBásico.getChildren().remove(BotónTabla);
        PanelBásico.getChildren().add(BotónTabla);

        // Posiciona BotónBúsqueda y BotónTabla y establece sus ToolTips
        BotónBúsqueda.setLayoutX(31.0);
        BotónBúsqueda.setLayoutY(114.0);
        BotónTabla.setLayoutX(31);
        BotónTabla.setLayoutY(22);
        BotónBúsqueda.setId(constantes.NOMBRES_ELEMENTOS.BOTÓN_BUSCAR.Nombre);
        new CrearToolTip(BotónBúsqueda, PanelBásico, 0);
        new CrearToolTip(BotónTabla, PanelBásico, 0); // POSIBLE ACUMULACIÓN DE TOOLTIPS CON CADA CAMBIO

        // Muestra todos los componentes del modo básico
        PanelBásico.setVisible(true);
        PanelBásico.setManaged(true);
        PanelBásico.getChildren().forEach(i -> {
            if (!i.isDisabled() && !(i instanceof StackPane)) {
                i.setVisible(true);
                i.setManaged(true);
            }
        });

        // Oculta los componentes del modo avanzado
        PanelAvanzado.setVisible(false);
        PanelAvanzado.setManaged(false);

        // Oculta los componentes del modo gráficos
        PanelGráficos.setVisible(false);
        PanelGráficos.setManaged(false);
        PanelGráficos.getChildren().forEach(i -> {
            i.setVisible(false);
            i.setManaged(false);
        });

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                MétodosCompartidos.EstablecerAceleradorEnBotón(BotónBúsqueda, new KeyCodeCombination(KeyCode.ENTER), null, (Object) null);
            }
        });

        TablaYAreaTexto.setVisible(true);
        TablaYAreaTexto.setManaged(true);

        modo = 0;
    }

    @FXML
    private void PasarAModoGráficos() {
        // Oculta todos los componentes de los otros dos modos
        PanelBásico.setVisible(false);
        PanelBásico.setManaged(false);
        PanelBásico.getChildren().forEach(i -> {
            if (!i.isDisabled()) {
                i.setVisible(false);
                i.setManaged(false);
            }
        });
        PanelBásico.getChildren().remove(BotónTabla);
        GridPane Panel = null;
        for (Node nodo : PanelGráficos.getChildren()) {
            if (nodo instanceof GridPane) {
                Panel = (GridPane) nodo;
            }
        }
        Panel.getChildren().remove(BotónTabla);
        Panel.add(BotónTabla, 0, 0);
        new CrearToolTip(BotónTabla, PanelGráficos, 2);
        BotónTabla.setVisible(true);
        BotónTabla.setManaged(true);

        GridPane.setMargin(BotónTabla, new Insets(0, 0, 0, 20));

        PanelAvanzado.setVisible(false);
        PanelAvanzado.setManaged(false);
        PanelAvanzado.getChildren().forEach(i -> {
            i.setVisible(false);
            i.setManaged(false);
        });
        TablaYAreaTexto.setVisible(false);
        TablaYAreaTexto.setManaged(false);

        // Elimina el botón de búsqueda de los otros dos modos
        Node BotónAEliminar = null;
        if (modo == 1) {
            for (Node nodo : PanelSentenciasGrupo.getChildren()) {
                if (nodo instanceof Button && nodo.getId().equals("BotónAmbosModos")) {
                    BotónAEliminar = nodo;
                }
            }
            PanelSentenciasGrupo.getChildren().remove(BotónAEliminar);
        } else {
            for (Node nodo : PanelBásico.getChildren()) {
                if (nodo instanceof Button && nodo.getId().equals("BotónAmbosModos")) {
                    BotónAEliminar = nodo;
                }
            }
            PanelBásico.getChildren().remove(BotónAEliminar);
        }

        // Muestra los componentes del modo gráficos
        PanelGráficos.setVisible(true);
        PanelGráficos.setManaged(true);
        PanelGráficos.getChildren().forEach(i -> {
            if (!(i instanceof StackPane)) {
                i.setVisible(true);
                i.setManaged(true);
            }
            if (i instanceof GridPane) {
                for (Node nodo : ((GridPane) i).getChildren()) {
                    if (nodo instanceof VBox) {
                        Button botónCrearGráfico = MétodosCompartidos.getObjetoPosiciónRelativaBox(new Button(), (VBox) nodo, 0);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                MétodosCompartidos.EstablecerAceleradorEnBotón(botónCrearGráfico, new KeyCodeCombination(KeyCode.ENTER), null, (Object) null);
                            }
                        });

                    }
                }
            }
        });
        modo = 2;
    }

    // Adapta la opción de búsqueda (búsqueda, conteo o media)
    @FXML
    private void CambiarToggle() {
        String opciónSeleccionada = ((RadioButton) opcionesBúsqueda.getSelectedToggle()).getText();
        if (opciónSeleccionada.equals("Búsqueda")) {
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
            new inicializaciónComponentes(this);
        } catch (IOException ex) {
            System.out.println("No se han encontrado los directorios que se deberían ocultar");
        }
    }

    public TextArea getAreaTextoGrupo() {
        return AreaTextoGrupo;
    }

    public TableView getTabla() {
        return Tabla;
    }

    public Pane getPanelBásico() {
        return PanelBásico;
    }

}
