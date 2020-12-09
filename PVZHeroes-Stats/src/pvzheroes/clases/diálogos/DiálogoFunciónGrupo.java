package pvzheroes.clases.di�logos;

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
import pvzheroes.clases.ModoGr�ficos;
import pvzheroes.clases.componentes.ComponenteGr�ficoGrupal;
import pvzheroes.clases.constantes;
import pvzheroes.clases.diccionarios.DiccionarioPropiedades;
import pvzheroes.clases.efectosConcretos.CrearToolTip;
import pvzheroes.clases.m�todosCompartidos.M�todosCompartidos;

public class Di�logoFunci�nGrupo extends Dialog {

    private GridPane Panel;
    private AnchorPane PanelPrincipal;

    private ButtonType Bot�nAceptar;
    private FXMLDocumentController c;
    private SentenciasGrupales sentenciasGrupales;
    private ArrayList<String> Filtros;
    private M�todosCompartidos MC;

    private ComboBox ComboTipoFunci�n;
    private Label EtiquetaTipoFunci�n;
    private Label EtiquetaTipoVariable;
    private ComboBox ComboVariable;
    private TextField introducci�nIdentificador;
    private Button bot�nConsultar;
    private Button bot�nA�adir;

    private SuggestionProvider sugestor;
    private ArrayList<String> Sugerencias;

    public Di�logoFunci�nGrupo(FXMLDocumentController c, SentenciasGrupales sentenciasGrupales) {
        this.c = c;
        this.sentenciasGrupales = sentenciasGrupales;
        this.MC = new M�todosCompartidos();
        Filtros = new ArrayList();

        if (sentenciasGrupales.getParent().getId().equals("GridGr�ficos")) {
            Boolean ColumnaInicial = GridPane.getColumnIndex(sentenciasGrupales) == 1;
            if (!ColumnaInicial) { // S�lo se muestran sugerencias a partir del 2� CFG
                Sugerencias = new ArrayList();
                for (Node nodo : ModoGr�ficos.ListadoCFG.get(0).getChildren()) {
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

        Button Bot�nOkay = (Button) this.getDialogPane().lookupButton(Bot�nAceptar);
        Bot�nOkay.addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Boolean ColumnaInicial = GridPane.getColumnIndex(sentenciasGrupales) == 1;
                    if (!ColumnaInicial && !Sugerencias.contains(introducci�nIdentificador.getText())) {
                        event.consume();
                        Dialog di�logo = new Dialog();
                        di�logo.setContentText("Est�s a punto de asignar un nombre no vinculado a ninguna serie, creando una nueva que carecer� de valores en algunas categor�as �Deseas continuar?");
                        di�logo.setTitle("Confirmaci�n de creaci�n");
                        di�logo.getDialogPane().getButtonTypes().addAll(Bot�nAceptar, ButtonType.CANCEL);
                        Optional<ButtonType> confirmaci�n = di�logo.showAndWait();
                        if (confirmaci�n.isPresent()) {
                            if (confirmaci�n.get().equals(Bot�nAceptar)) {
                                Di�logoFunci�nGrupo.this.close();
                                IncluirFiltroEnComponente();
                            }
                        }
                    } else {
                        IncluirFiltroEnComponente();
                    }
                } catch (NullPointerException ex) { // No nos encontramos en el modo gr�ficos
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

        Bot�nAceptar = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(Bot�nAceptar, ButtonType.CANCEL);
        this.setTitle("Define la funci�n de grupo");
        ((Stage) this.getDialogPane().getScene().getWindow()).getIcons().
                add(constantes.IM�GENES.TIPOSG.Comunes.get(0));
    }

    private void ConfigurarComponentes() {
        // Definimos el GridPane y lo a�adimos al AnchorPane
        Panel = new GridPane();
        Panel.setHgap(10);
        Panel.setVgap(10);
        Panel.setPadding(new Insets(20, 40, 0, 10));
        Panel.setMinSize(this.getDialogPane().getWidth(), this.getDialogPane().getHeight());
        PanelPrincipal.getChildren().add(Panel);

        // A�adimos los componentes al panel
        Label EtiquetaTipo = new Label("Tipo de funci�n");
        Panel.add(EtiquetaTipo, 0, 0);
        Label etiqueta = new Label("Variable");
        etiqueta.setDisable(true);
        Panel.add(etiqueta, 0, 1);

        ComboVariable = MC.CrearComboBox(constantes.SENTENCIAS_GRUPALES.VARIABLES.OpcionesCombo, 0);
        ComboVariable.setDisable(true);
        Panel.add(ComboVariable, 1, 1);

        if (!sentenciasGrupales.getParent().getId().equals("GridGr�ficos")) {
            ComboTipoFunci�n = MC.CrearComboBox(constantes.SENTENCIAS_GRUPALES.FUNCIONES.OpcionesCombo, 0);
            Panel.add(ComboTipoFunci�n, 1, 0);
        } else { // Adicci�n de filtros condicionada en el panel de gr�ficos (los valores de cada serie tienen que ser coherentes)
            // S�lo el HBox de la columna del CFG desde el que se cre� el di�logo
            ComponenteGr�ficoGrupal CajaHorizontal = M�todosCompartidos.getObjetoPosici�nRelativaGrid(new ComponenteGr�ficoGrupal(), (GridPane) sentenciasGrupales.getParent(), sentenciasGrupales, 0, -1);
            Boolean PrimerCombo = false;
            VBox CajaVertical = (VBox) CajaHorizontal.getChildren().get(0);
            for (Node nodo : CajaVertical.getChildren()) {
                if (nodo instanceof ComboBox) {
                    if (!PrimerCombo) {
                        EtiquetaTipoFunci�n = new Label(((ComboBox) nodo).getSelectionModel().getSelectedItem().toString());
                        Panel.add(EtiquetaTipoFunci�n, 1, 0);
                        PrimerCombo = true;
                        if (!EtiquetaTipoFunci�n.getText().equals("Conteo")) {
                            ComboVariable.setVisible(false);
                            ComboVariable.setManaged(false);
                        }
                    } else {
                        if (!EtiquetaTipoFunci�n.getText().equals("Conteo")) {
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
        bot�nA�adir = new Button("A�adir");
        bot�nConsultar = new Button("Consultar");
        bot�nConsultar.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                Dialog di�logoConsultaFiltros = new Dialog();
                GridPane panelFiltros = new GridPane();
                di�logoConsultaFiltros.getDialogPane().setContent(panelFiltros);
                for (int i = 0; i < Filtros.size(); i++) {
                    panelFiltros.add(new Label(Filtros.get(i)), 0, i);
                    Button Bot�n = new Button("-");
                    panelFiltros.add(Bot�n, 1, i);
                    Bot�n.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            MC.EliminarFilas(Bot�n, panelFiltros, Di�logoFunci�nGrupo.this.getFiltros());

                        }
                    });
                }
                ButtonType Bot�nAceptar = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
                di�logoConsultaFiltros.getDialogPane().getButtonTypes().addAll(Bot�nAceptar);
                di�logoConsultaFiltros.setTitle("Filtros guardados para la funci�n de grupo");
                di�logoConsultaFiltros.show();

            }
        }
        );
        hbox.getChildren().addAll(bot�nA�adir, bot�nConsultar);
        Panel.add(hbox, 1, 2);

        Label etiqueta3 = new Label("Identificador");
        Panel.add(etiqueta3, 0, 3);
        // Cuenta el n�mero de sentencias ya existentes para adaptar el identificador
        int n�meroIdentificador = 0;
        for (Object objeto : sentenciasGrupales.getChildren()) {
            if (objeto instanceof Label) {
                n�meroIdentificador++;
            }
        }
        introducci�nIdentificador = new TextField();
        introducci�nIdentificador.setText("Identificador " + n�meroIdentificador);
        new AutoCompletionTextFieldBinding<>(introducci�nIdentificador, sugestor);
        Platform.runLater(() -> {
            introducci�nIdentificador.requestFocus();
        });
        introducci�nIdentificador.setOnKeyPressed(new EventHandler() {
            @Override
            public void handle(Event event) {
                Button bot�n = (Button) getDialogPane().lookupButton(Bot�nAceptar);
                Platform.runLater(() -> {
                    if (sentenciasGrupales.getFuncionesDefinidas().containsKey(introducci�nIdentificador.getText().trim())) {
                        bot�n.setDisable(true);
                    } else {
                        bot�n.setDisable(false);
                    }
                });
            }
        }
        );
        Panel.add(introducci�nIdentificador, 1, 3);

        // Listener que activa y desactiva el segundo ComboBox en funci�n del primero
        if (ComboTipoFunci�n != null) {
            ComboTipoFunci�n.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event
                ) {
                    if (ComboTipoFunci�n.getSelectionModel().getSelectedItem().equals("Conteo")) {
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
            if (!EtiquetaTipoFunci�n.getText().equals("Conteo")) {
                ComboVariable.setDisable(false);
            }
        }

        // Si se pulsa "A�adir", se abre el di�logo de creaci�n de filtros
        bot�nA�adir.setOnAction(
                new EventHandler() {
            @Override
            public void handle(Event event
            ) {
                new Di�logoFiltros(c, Di�logoFunci�nGrupo.this, null);
            }
        }
        );
    }

    private void IncluirFiltroEnComponente() {
        String TipoFunci�n = "";
        String Variable = "";
        if (!sentenciasGrupales.getParent().getId().equals("GridGr�ficos")) { // CFG pertenece al modo b�sico
            TipoFunci�n = ComboTipoFunci�n.getSelectionModel().getSelectedItem().toString();
            if (!ComboVariable.isDisabled()) {
                Variable = ComboVariable.getSelectionModel().getSelectedItem().toString();
            } else {
                Variable = "*";
            }
        } else { // CFG pertenece al modo gr�ficos
            TipoFunci�n = EtiquetaTipoFunci�n.getText();
            if (!TipoFunci�n.equals("Conteo")) {
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
        String Sentencia = "SELECT " + DiccionarioSQL.TextoASQL(TipoFunci�n) + "(" + Variable + ") " + " FROM Cartas WHERE " + constantes.TIPOS_CARTA[M�todosCompartidos.TipoSeleccionado] + " " + FiltrosEnString;
        System.out.println(Sentencia);

        String nombreAA�adir = introducci�nIdentificador.getText();
        sentenciasGrupales.getFuncionesDefinidas().put(nombreAA�adir, Sentencia);
        M�todosCompartidos.DesplazarCeldas(true, 0, sentenciasGrupales, true, false);
        Button Bot�n = new Button("-");
        Bot�n.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (DiccionarioPropiedades.PROP.getProperty("sonido").equals("ON")) {
                    new MediaPlayer(constantes.SONIDOS.BOT�N.sonido).play();
                }
                sentenciasGrupales.getFuncionesDefinidas().remove(nombreAA�adir);
                MC.EliminarFilas(Bot�n, sentenciasGrupales, null);
            }
        });
        Label etiqueta = new Label(nombreAA�adir);
        sentenciasGrupales.addRow(0, etiqueta, Bot�n);
        sentenciasGrupales.setMinWidth(170);
    }

    private void GenerarIDsYToolTips() {
        if (ComboTipoFunci�n != null) {
            ComboTipoFunci�n.setId(constantes.NOMBRES_ELEMENTOS.DFG_SELECCI�N_FUNCI�N.Nombre);
            new CrearToolTip(ComboTipoFunci�n, PanelPrincipal, 12);
        }
        ComboVariable.setId(constantes.NOMBRES_ELEMENTOS.DFG_SELECCION_VARIABLE.Nombre);
        new CrearToolTip(ComboVariable, PanelPrincipal, 12);
        introducci�nIdentificador.setId(constantes.NOMBRES_ELEMENTOS.DFG_INTRODUCCI�N_IDENTIFICADOR.Nombre);
        new CrearToolTip(introducci�nIdentificador, PanelPrincipal, 12);
        bot�nConsultar.setId(constantes.NOMBRES_ELEMENTOS.DFG_BOT�N_CONSULTAR.Nombre);
        new CrearToolTip(bot�nConsultar, PanelPrincipal, 12);
        bot�nA�adir.setId(constantes.NOMBRES_ELEMENTOS.DFG_BOT�N_A�ADIR.Nombre);
        new CrearToolTip(bot�nA�adir, PanelPrincipal, 12);
    }

    public SentenciasGrupales getSentenciasGrupales() {
        return sentenciasGrupales;
    }

    public ArrayList<String> getFiltros() {
        return Filtros;
    }
}
