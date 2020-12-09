package pvzheroes.clases.componentes;

import java.util.ArrayList;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import pvzheroes.clases.FXMLDocumentController;
import pvzheroes.clases.ModoGráficos;
import pvzheroes.clases.constantes;
import pvzheroes.clases.diccionarios.DiccionarioPropiedades;
import pvzheroes.clases.diálogos.DiálogoFiltros;
import pvzheroes.clases.métodosCompartidos.MétodosCompartidos;
import pvzheroes.clases.efectosConcretos.CrearToolTip;

public class SentenciaIndividual extends GridPane {

    public Button BotónNuevoFiltro;
    public ArrayList<String> FiltrosCartas;

    private FXMLDocumentController c;
    private ModoGráficos g;
    private int modoOrigen;

    /**
     * Constructor con parámetros.
     *
     * @param c Referencia al controlador de la vista.
     * @param ModoOrigen Modo en el que se sitúa el componente.
     */
    public SentenciaIndividual(FXMLDocumentController c, ModoGráficos g, int ModoOrigen) {
        modoOrigen = ModoOrigen;
        this.c = c;
        this.g = g;
        FiltrosCartas = new ArrayList();
        CrearComponente();
        GenerarIDyToolTip();

    }

    private void CrearComponente() {
        this.setPadding(new Insets(0, 0, 0, 10));
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPrefWidth(130);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPrefWidth(29);
        this.getColumnConstraints().addAll(col1, col2);
        Label EtiquetaNuevoFiltro = new Label("Nuevo filtro...");
        this.add(EtiquetaNuevoFiltro, 0, 0);
        BotónNuevoFiltro = new Button("+");
        this.add(BotónNuevoFiltro, 1, 0);
        BotónNuevoFiltro.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                if (DiccionarioPropiedades.PROP.getProperty("sonido").equals("ON")) {
                    new MediaPlayer(constantes.SONIDOS.BOTÓN.sonido).play();
                }
                new DiálogoFiltros(c, null, SentenciaIndividual.this); // PROVISIONAL
            }
        });
    }

    /**
     * Obtiene los valores de los comboboxes a los que se asocia en la opción
     * "dispersión" de la pantalla de gráficos
     *
     * @return Array con los valores del combo asociado al eje X [0] y a Y [1]
     */
    public String[] ObtenerValoresComboBoxes() {
        GridPane GridGráficos = (GridPane) this.getParent();
        VBox CajaVertical = MétodosCompartidos.getObjetoPosiciónRelativaGrid(new ComponenteGráficoDispersión(), GridGráficos, this, 0, -1);
        HBox CajaHorizontal = MétodosCompartidos.getObjetoPosiciónRelativaBox(new HBox(), CajaVertical, 1);
        String ComboX = ((ComboBox) CajaHorizontal.getChildren().get(0)).getValue().toString();
        ComboX = ComboX.substring(ComboX.indexOf(":") + 2);
        String ComboY = ((ComboBox) CajaHorizontal.getChildren().get(1)).getValue().toString();
        ComboY = ComboY.substring(ComboY.indexOf(":") + 2);
        String[] ValoresCombos = {ComboX, ComboY};
        return ValoresCombos;
    }

    /**
     * Obtiene el checkbox al que se asocia en la opción "dispersión" de la
     * pantalla de gráficos
     *
     * @return CheckBox asociado
     */
    public CheckBox ObtenerCheck() {
        GridPane GridGráficos = (GridPane) this.getParent();
        VBox CajaVertical = MétodosCompartidos.getObjetoPosiciónRelativaGrid(new ComponenteGráficoDispersión(), GridGráficos, this, 0, -1);
        HBox CajaHorizontal = MétodosCompartidos.getObjetoPosiciónRelativaBox(new HBox(), CajaVertical, 2);
        CheckBox cb = ((CheckBox) ((StackPane) CajaHorizontal.getChildren().get(1)).getChildren().get(0));
        return cb;
    }

    /**
     * Obtiene el ColorPicker al que se asocia en la opción "dispersión" de la
     * pantalla de gráficos
     *
     * @return ColorPicker asociado
     */
    public ColorPicker ObtenerColorPicker() {
        GridPane GridGráficos = (GridPane) this.getParent();
        VBox CajaVertical = MétodosCompartidos.getObjetoPosiciónRelativaGrid(new ComponenteGráficoDispersión(), GridGráficos, this, 0, -1);
        HBox CajaHorizontal = MétodosCompartidos.getObjetoPosiciónRelativaBox(new HBox(), CajaVertical, 2);
        ColorPicker cp = ((ColorPicker) CajaHorizontal.getChildren().get(0));
        return cp;
    }

    private void GenerarIDyToolTip() {
        this.setId(constantes.NOMBRES_ELEMENTOS.SENTENCIA_INDIVIDUAL.Nombre);
        if (modoOrigen == 0) {
            new CrearToolTip(this, c.getPanelBásico(), 3);
        } else if (modoOrigen == 2 || modoOrigen ==-1) {
            new CrearToolTip(this, g.getPANEL_GRÁFICOS(), 3);
        }
    }

}
