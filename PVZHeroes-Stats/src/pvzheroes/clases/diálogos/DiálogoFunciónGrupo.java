package pvzheroes.clases.diálogos;

import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import java.util.ArrayList;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import pvzheroes.clases.componentes.SentenciasGrupales;
import pvzheroes.clases.diccionarios.DiccionarioSQL;
import pvzheroes.clases.FXMLDocumentController;
import pvzheroes.clases.ModoGráficos;
import pvzheroes.clases.componentes.ComponenteGráficoGrupal;
import pvzheroes.clases.constantes;
import pvzheroes.clases.diccionarios.DiccionarioPropiedades;
import pvzheroes.clases.efectosConcretos.CrearToolTip;
import pvzheroes.clases.métodosCompartidos.MétodosCompartidos;

public class DiálogoFunciónGrupo extends Dialog {

    private GridPane Panel;
    private AnchorPane PanelPrincipal;

    private ButtonType BotónAceptar;
    private FXMLDocumentController c;
    private SentenciasGrupales sentenciasGrupales;
    private ArrayList<String> Filtros;
    private MétodosCompartidos MC;

    private ComboBox ComboTipoFunción;
    private Label EtiquetaTipoFunción;
    private Label EtiquetaTipoVariable;
    private ComboBox ComboVariable;
    private TextField introducciónIdentificador;
    private Button botónConsultar;
    private Button botónAñadir;

    private SuggestionProvider sugestor;
    private ArrayList<String> Sugerencias;

    public DiálogoFunciónGrupo(FXMLDocumentController c, SentenciasGrupales sentenciasGrupales) {
        this.c = c;
        this.sentenciasGrupales = sentenciasGrupales;
        this.MC = new MétodosCompartidos();
        Filtros = new ArrayList();

        if (sentenciasGrupales.getParent().getId().equals("GridGráficos")) {
            Boolean ColumnaInicial = GridPane.getColumnIndex(sentenciasGrupales) == 1;
            if (!ColumnaInicial) { // Sólo se muestran sugerencias a partir del 2º CFG
                Sugerencias = new ArrayList();
                for (Node nodo : ModoGráficos.ListadoCFG.get(0).getChildren()) {
                    if (nodo instanceof Label) {
                        Sugerencias.add(((Label) nodo).getText());
                    }
                }
                sugestor = SuggestionProvider.create(Sugerencias);
            }
        }

        DefinirVentana();
        ConfigurarComponentes();
        GenerarIDsYToolTips();

        Button BotónOkay = (Button) this.getDialogPane().lookupButton(BotónAceptar);
        BotónOkay.addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Boolean ColumnaInicial = GridPane.getColumnIndex(sentenciasGrupales) == 1;
                    if (!ColumnaInicial && !Sugerencias.contains(introducciónIdentificador.getText())) {
                        event.consume();
                        Dialog diálogo = new Dialog();
                        diálogo.setContentText("Estás a punto de asignar un nombre no vinculado a ninguna serie, creando una nueva que carecerá de valores en algunas categorías ¿Deseas continuar?");
                        diálogo.setTitle("Confirmación de creación");
                        diálogo.getDialogPane().getButtonTypes().addAll(BotónAceptar, ButtonType.CANCEL);
                        Optional<ButtonType> confirmación = diálogo.showAndWait();
                        if (confirmación.isPresent()) {
                            if (confirmación.get().equals(BotónAceptar)) {
                                DiálogoFunciónGrupo.this.close();
                                IncluirFiltroEnComponente();
                            }
                        }
                    } else {
                        IncluirFiltroEnComponente();
                    }
                } catch (NullPointerException ex) { // No nos encontramos en el modo gráficos
                    IncluirFiltroEnComponente();
                }
            }
        });

        this.showAndWait();

    }

    private void DefinirVentana() {
        this.getDialogPane().setMinSize(280, 150);
        this.getDialogPane().setMaxSize(280, 250);
        PanelPrincipal = new AnchorPane();
        this.getDialogPane().setContent(PanelPrincipal);

        BotónAceptar = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(BotónAceptar, ButtonType.CANCEL);
        this.setTitle("Define la función de grupo");
        ((Stage) this.getDialogPane().getScene().getWindow()).getIcons().
                add(constantes.IMÁGENES.TIPOSG.Comunes.get(0));
    }

    private void ConfigurarComponentes() {
        // Definimos el GridPane y lo añadimos al AnchorPane
        Panel = new GridPane();
        Panel.setHgap(10);
        Panel.setVgap(10);
        Panel.setPadding(new Insets(20, 40, 0, 10));
        Panel.setMinSize(this.getDialogPane().getWidth(), this.getDialogPane().getHeight());
        PanelPrincipal.getChildren().add(Panel);

        // Añadimos los componentes al panel
        Label EtiquetaTipo = new Label("Tipo de función");
        Panel.add(EtiquetaTipo, 0, 0);
        Label etiqueta = new Label("Variable");
        etiqueta.setDisable(true);
        Panel.add(etiqueta, 0, 1);

        ComboVariable = MC.CrearComboBox(constantes.SENTENCIAS_GRUPALES.VARIABLES.OpcionesCombo, 0);
        ComboVariable.setDisable(true);
        Panel.add(ComboVariable, 1, 1);

        if (!sentenciasGrupales.getParent().getId().equals("GridGráficos")) {
            ComboTipoFunción = MC.CrearComboBox(constantes.SENTENCIAS_GRUPALES.FUNCIONES.OpcionesCombo, 0);
            Panel.add(ComboTipoFunción, 1, 0);
        } else { // Adicción de filtros condicionada en el panel de gráficos (los valores de cada serie tienen que ser coherentes)
            // Sólo el HBox de la columna del CFG desde el que se creó el diálogo
            ComponenteGráficoGrupal CajaHorizontal = MétodosCompartidos.getObjetoPosiciónRelativaGrid(new ComponenteGráficoGrupal(), (GridPane) sentenciasGrupales.getParent(), sentenciasGrupales, 0, -1);
            Boolean PrimerCombo = false;
            VBox CajaVertical = (VBox) CajaHorizontal.getChildren().get(0);
            for (Node nodo : CajaVertical.getChildren()) {
                if (nodo instanceof ComboBox) {
                    if (!PrimerCombo) {
                        EtiquetaTipoFunción = new Label(((ComboBox) nodo).getSelectionModel().getSelectedItem().toString());
                        Panel.add(EtiquetaTipoFunción, 1, 0);
                        PrimerCombo = true;
                        if (!EtiquetaTipoFunción.getText().equals("Conteo")) {
                            ComboVariable.setVisible(false);
                            ComboVariable.setManaged(false);
                        }
                    } else {
                        if (!EtiquetaTipoFunción.getText().equals("Conteo")) {
                            EtiquetaTipoVariable = new Label(((ComboBox) nodo).getSelectionModel().getSelectedItem().toString());
                            Panel.add(EtiquetaTipoVariable, 1, 1);
                            etiqueta.setDisable(false);
                        }
                    }
                }
            }

        }

        Label etiqueta2 = new Label("Filtro");
        Panel.add(etiqueta2, 0, 2);
        HBox hbox = new HBox();
        botónAñadir = new Button("Añadir");
        botónConsultar = new Button("Consultar");
        botónConsultar.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                Dialog diálogoConsultaFiltros = new Dialog();
                GridPane panelFiltros = new GridPane();
                diálogoConsultaFiltros.getDialogPane().setContent(panelFiltros);
                for (int i = 0; i < Filtros.size(); i++) {
                    panelFiltros.add(new Label(Filtros.get(i)), 0, i);
                    Button Botón = new Button("-");
                    panelFiltros.add(Botón, 1, i);
                    Botón.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            MC.EliminarFilas(Botón, panelFiltros, DiálogoFunciónGrupo.this.getFiltros());

                        }
                    });
                }
                ButtonType BotónAceptar = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
                diálogoConsultaFiltros.getDialogPane().getButtonTypes().addAll(BotónAceptar);
                diálogoConsultaFiltros.setTitle("Filtros guardados para la función de grupo");
                diálogoConsultaFiltros.show();

            }
        }
        );
        hbox.getChildren().addAll(botónAñadir, botónConsultar);
        Panel.add(hbox, 1, 2);

        Label etiqueta3 = new Label("Identificador");
        Panel.add(etiqueta3, 0, 3);
        // Cuenta el número de sentencias ya existentes para adaptar el identificador
        int númeroIdentificador = 0;
        for (Object objeto : sentenciasGrupales.getChildren()) {
            if (objeto instanceof Label) {
                númeroIdentificador++;
            }
        }
        introducciónIdentificador = new TextField();
        introducciónIdentificador.setText("Identificador " + númeroIdentificador);
        new AutoCompletionTextFieldBinding<>(introducciónIdentificador, sugestor);
        Platform.runLater(() -> {
            introducciónIdentificador.requestFocus();
        });
        introducciónIdentificador.setOnKeyPressed(new EventHandler() {
            @Override
            public void handle(Event event) {
                Button botón = (Button) getDialogPane().lookupButton(BotónAceptar);
                Platform.runLater(() -> {
                    if (sentenciasGrupales.getFuncionesDefinidas().containsKey(introducciónIdentificador.getText().trim())) {
                        botón.setDisable(true);
                    } else {
                        botón.setDisable(false);
                    }
                });
            }
        }
        );
        Panel.add(introducciónIdentificador, 1, 3);

        // Listener que activa y desactiva el segundo ComboBox en función del primero
        if (ComboTipoFunción != null) {
            ComboTipoFunción.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event
                ) {
                    if (ComboTipoFunción.getSelectionModel().getSelectedItem().equals("Conteo")) {
                        ComboVariable.setDisable(true);
                        etiqueta.setDisable(true);
                    } else {
                        ComboVariable.setDisable(false);
                        etiqueta.setDisable(false);
                    }
                }
            }
            );
        } else {
            if (!EtiquetaTipoFunción.getText().equals("Conteo")) {
                ComboVariable.setDisable(false);
            }
        }

        // Si se pulsa "Añadir", se abre el diálogo de creación de filtros
        botónAñadir.setOnAction(
                new EventHandler() {
            @Override
            public void handle(Event event
            ) {
                new DiálogoFiltros(c, DiálogoFunciónGrupo.this, null);
            }
        }
        );
    }

    private void IncluirFiltroEnComponente() {
        String TipoFunción = "";
        String Variable = "";
        if (!sentenciasGrupales.getParent().getId().equals("GridGráficos")) { // CFG pertenece al modo básico
            TipoFunción = ComboTipoFunción.getSelectionModel().getSelectedItem().toString();
            if (!ComboVariable.isDisabled()) {
                Variable = ComboVariable.getSelectionModel().getSelectedItem().toString();
            } else {
                Variable = "*";
            }
        } else { // CFG pertenece al modo gráficos
            TipoFunción = EtiquetaTipoFunción.getText();
            if (!TipoFunción.equals("Conteo")) {
                Variable = EtiquetaTipoVariable.getText();
            }
        }

        // Define el/los filtro(s) en String
        String FiltrosEnString = "";
        for (int i = 0; i < Filtros.size(); i++) {
            FiltrosEnString = FiltrosEnString + " " + Filtros.get(i);
            if (i == Filtros.size() - 1) {
                FiltrosEnString = FiltrosEnString + ");";
            }
        }

        // Utiliza el/los filtro(s) para formar una sentencia
        String Sentencia = "SELECT " + DiccionarioSQL.TextoASQL(TipoFunción) + "(" + Variable + ") " + " FROM Cartas WHERE " + constantes.TIPOS_CARTA[MétodosCompartidos.TipoSeleccionado] + " " + FiltrosEnString;
        System.out.println(Sentencia);

        String nombreAAñadir = introducciónIdentificador.getText();
        sentenciasGrupales.getFuncionesDefinidas().put(nombreAAñadir, Sentencia);
        MétodosCompartidos.DesplazarCeldas(true, 0, sentenciasGrupales, true, false);
        Button Botón = new Button("-");
        Botón.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (DiccionarioPropiedades.PROP.getProperty("sonido").equals("ON")) {
                    new MediaPlayer(constantes.SONIDOS.BOTÓN.sonido).play();
                }
                sentenciasGrupales.getFuncionesDefinidas().remove(nombreAAñadir);
                MC.EliminarFilas(Botón, sentenciasGrupales, null);
            }
        });
        Label etiqueta = new Label(nombreAAñadir);
        sentenciasGrupales.addRow(0, etiqueta, Botón);
        sentenciasGrupales.setMinWidth(170);
    }

    private void GenerarIDsYToolTips() {
        if (ComboTipoFunción != null) {
            ComboTipoFunción.setId(constantes.NOMBRES_ELEMENTOS.DFG_SELECCIÓN_FUNCIÓN.Nombre);
            new CrearToolTip(ComboTipoFunción, PanelPrincipal, 12);
        }
        ComboVariable.setId(constantes.NOMBRES_ELEMENTOS.DFG_SELECCION_VARIABLE.Nombre);
        new CrearToolTip(ComboVariable, PanelPrincipal, 12);
        introducciónIdentificador.setId(constantes.NOMBRES_ELEMENTOS.DFG_INTRODUCCIÓN_IDENTIFICADOR.Nombre);
        new CrearToolTip(introducciónIdentificador, PanelPrincipal, 12);
        botónConsultar.setId(constantes.NOMBRES_ELEMENTOS.DFG_BOTÓN_CONSULTAR.Nombre);
        new CrearToolTip(botónConsultar, PanelPrincipal, 12);
        botónAñadir.setId(constantes.NOMBRES_ELEMENTOS.DFG_BOTÓN_AÑADIR.Nombre);
        new CrearToolTip(botónAñadir, PanelPrincipal, 12);
    }

    public SentenciasGrupales getSentenciasGrupales() {
        return sentenciasGrupales;
    }

    public ArrayList<String> getFiltros() {
        return Filtros;
    }
}
