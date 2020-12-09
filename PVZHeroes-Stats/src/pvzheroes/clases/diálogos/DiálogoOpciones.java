package pvzheroes.clases.diálogos;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.controlsfx.control.ToggleSwitch;
import pvzheroes.clases.FXMLDocumentController;
import pvzheroes.clases.constantes;
import pvzheroes.clases.diccionarios.DiccionarioPropiedades;
import pvzheroes.clases.efectosConcretos.CrearToolTip;
import pvzheroes.clases.efectosConcretos.ToolTip;
import pvzheroes.clases.métodosCompartidos.MétodosCompartidos;

public class DiálogoOpciones extends Dialog {

    private String[] etiquetas = {"Imagen de fondo", "Sonidos", "Coloreado en filas", "Ayuda emergente", "Retardo de ayuda"};
    private static HashMap<String, String> PropiedadesSinGuardar = new HashMap();

    private ComboBox comboImagen;
    private ToggleSwitch toogleSonido;
    private ToggleSwitch toogleColores;
    private ToggleSwitch toogleAyuda;
    private TextField fieldRetardo;

    private AnchorPane panelPrincipal;
    private GridPane panel;

    private FXMLDocumentController c;

    public DiálogoOpciones(FXMLDocumentController controlador) throws IOException {
        this.c = controlador;
        DefinirVentana();
        RellenarVentana();
        GenerarIDsYToolTips();
        ButtonType BotónAceptar = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(BotónAceptar, ButtonType.CANCEL);
        Optional<ButtonType> confirmación = this.showAndWait();
        if (confirmación.isPresent()) {
            if (confirmación.get().equals(BotónAceptar)) {
                for (Map.Entry<String, String> par : PropiedadesSinGuardar.entrySet()) {
                    DiccionarioPropiedades.PROP.setProperty(par.getKey(), par.getValue());
                }
                DiccionarioPropiedades.GuardarPropiedades();
                ToolTip.setTemporizador(Integer.parseInt(DiccionarioPropiedades.PROP.getProperty("retardoAyuda")));
            } else {
                c.CambiarFondo(DiccionarioPropiedades.PROP.getProperty("imagenDeFondo"));
            }
        }
    }

    /**
     * Crea la ventana y los botones
     */
    private void DefinirVentana() throws IOException {
        this.setWidth(200);
        panelPrincipal = new AnchorPane();
        panel = new GridPane();
        panelPrincipal.getChildren().add(panel);
        panel.setHgap(10);
        panel.setVgap(10);
        this.getDialogPane().setContent(panelPrincipal);
        this.setTitle("Opciones generales");
        ((Stage) this.getDialogPane().getScene().getWindow()).getIcons().
                add(constantes.IMÁGENES.TIPOSG.Comunes.get(0));
        this.getDialogPane().setGraphic(new ImageView(constantes.IMÁGENES.ERRORES.Comunes.get(1)));
        panel.setPadding(new Insets(20, 40, 10, 10));

    }

    private void RellenarVentana() {
        int NúmeroEtiqueta = 0;
        for (int i = 0; i < etiquetas.length * 2 + 2; i++) {
            if (i % 2 == 0 && i > 1) {
                Label encabezadoSección = new Label();
                encabezadoSección.setMinWidth(130);
                encabezadoSección.setText(etiquetas[NúmeroEtiqueta]);
                encabezadoSección.setStyle("-fx-font-weight: bold;");
                panel.add(encabezadoSección, 0, i / 2);
                NúmeroEtiqueta++;
            } else {
                switch (i) {
                    case 1:
                        String TextoExplicativo = "Utiliza esta ventana para personalizar distintas\nopciones."
                                + " Posiciona el puntero encima de sus\nnombres para saber a qué se refieren.\n"
                                + "Pulsa \"Aceptar\" para que los cambios se guarden\nincluso cuando el programa se cierre.";
                        TextFlow TA = new TextFlow(new Label(TextoExplicativo));
                        panel.add(TA, 0, 0);
                        GridPane.setColumnSpan(TA, 2);
                        break;
                    case 3:
                        int TamañoArray = MétodosCompartidos.IMÁGENES_FONDO.size();
                        String[] OpcionesCombo = MétodosCompartidos.IMÁGENES_FONDO.toArray(new String[TamañoArray]);
                        comboImagen = MétodosCompartidos.CrearComboBox(OpcionesCombo, 0);
                        comboImagen.getSelectionModel().select(DiccionarioPropiedades.PROP.getProperty("imagenDeFondo"));
                        comboImagen.setMaxWidth(100);
                        comboImagen.setOnAction(new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                String seleccionado = comboImagen.getSelectionModel().getSelectedItem().toString();
                                PropiedadesSinGuardar.put("imagenDeFondo", seleccionado);
                                try {
                                    c.CambiarFondo(seleccionado);
                                } catch (IOException ex) {
                                    System.out.println("No se ha podido cambiar el fondo porque no se puede leer el CSS");
                                }
                            }
                        });
                        panel.add(comboImagen, 1, 1);
                        break;
                    case 5:
                        toogleSonido = new ToggleSwitch();
                        toogleSonido.setSelected(DiccionarioPropiedades.PROP.getProperty("sonido").equals("ON"));
                        toogleSonido.setOnMouseClicked(new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                Label etiqueta = MétodosCompartidos.getObjetoPosiciónRelativaGrid(new Label(), panel, toogleSonido, -1, 0);
                                if (toogleSonido.isSelected()) {
                                    etiqueta.setText("Sonido activado");
                                    PropiedadesSinGuardar.put("sonido", "ON");
                                } else {
                                    etiqueta.setText("Sonido desactivado");
                                    PropiedadesSinGuardar.put("sonido", "OFF");
                                }
                            }
                        });
                        panel.add(toogleSonido, 1, 2);
                        break;

                    case 7:
                        toogleColores = new ToggleSwitch();
                        toogleColores.setSelected(DiccionarioPropiedades.PROP.getProperty("filasEnColores").equals("true"));
                        toogleColores.setOnMouseClicked(new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                Label etiqueta = MétodosCompartidos.getObjetoPosiciónRelativaGrid(new Label(), panel, toogleColores, -1, 0);
                                if (toogleColores.isSelected()) {
                                    etiqueta.setText("Filas coloreadas");
                                    PropiedadesSinGuardar.put("filasEnColores", "true");
                                } else {
                                    etiqueta.setText("Filas sin colorear");
                                    PropiedadesSinGuardar.put("filasEnColores", "false");
                                }
                            }
                        });
                        panel.add(toogleColores, 1, 3);
                        break;

                    case 9:
                        toogleAyuda = new ToggleSwitch();
                        toogleAyuda.setSelected(DiccionarioPropiedades.PROP.getProperty("ayuda").equals("true"));
                        toogleAyuda.setOnMouseClicked(new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                Label etiqueta = MétodosCompartidos.getObjetoPosiciónRelativaGrid(new Label(), panel, toogleAyuda, -1, 0);
                                if (toogleAyuda.isSelected()) {
                                    etiqueta.setText("Ayuda activada");
                                    PropiedadesSinGuardar.put("ayuda", "true");
                                } else {
                                    etiqueta.setText("Ayuda desactivada");
                                    PropiedadesSinGuardar.put("ayuda", "false");
                                }
                            }
                        });
                        panel.add(toogleAyuda, 1, 4);
                        break;
                    case 11:
                        fieldRetardo = new TextField();
                        fieldRetardo.setPromptText("0-9 segundos");
                        fieldRetardo.setMaxWidth(100);
                        fieldRetardo.setOnKeyTyped(new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                Platform.runLater(() -> PropiedadesSinGuardar.put("retardoAyuda", String.valueOf(Integer.parseInt(fieldRetardo.getText()) * 1000)));
                            }
                        });
                        panel.add(fieldRetardo, 1, 5);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void GenerarIDsYToolTips() {
        comboImagen.setId(constantes.NOMBRES_ELEMENTOS.DO_SELECCIÓN_FONDO.Nombre);
        new CrearToolTip(comboImagen, this.panelPrincipal, 14);
        toogleSonido.setId(constantes.NOMBRES_ELEMENTOS.DO_ACTIVAR_SONIDOS.Nombre);
        new CrearToolTip(toogleSonido, this.panel, 14);
        toogleColores.setId(constantes.NOMBRES_ELEMENTOS.DO_ACTIVAR_COLOREADO.Nombre);
        new CrearToolTip(toogleColores, this.panel, 14);
        toogleAyuda.setId(constantes.NOMBRES_ELEMENTOS.DO_ACTIVAR_AYUDA.Nombre);
        new CrearToolTip(toogleAyuda, this.panel, 14);
    }

    public static HashMap<String, String> getPropiedadesSinGuardar() {
        return PropiedadesSinGuardar;
    }

}
