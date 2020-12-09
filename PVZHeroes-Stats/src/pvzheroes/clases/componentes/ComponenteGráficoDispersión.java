package pvzheroes.clases.componentes;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import pvzheroes.clases.ModoGráficos;
import static pvzheroes.clases.ModoGráficos.ListadoCFil;
import pvzheroes.clases.constantes;
import pvzheroes.clases.efectosConcretos.CrearToolTip;
import pvzheroes.clases.métodosCompartidos.MétodosCompartidos;

/**
 * Componente Gráfico Dispersión que permite seleccionar los parámetros del
 * Gráfico de Dispersión. Incluye un TextField para introducir el nombre de la
 * serie, un botón para eliminar la serie, dos Comboboxes para seleccionar cada
 * uno de los ejes, un ColorPicker para asignar un color y un StackPane con un
 * CheckBox dentro para mostrar/esconder la serie.
 *
 */
public class ComponenteGráficoDispersión extends VBox {

    private int NúmeroColumna;
    private ModoGráficos mg;
    private Boolean Primero;

    /**
     * Constructor sin parámetros, utilizado para poder crear instancias cuando
     * se requiere una comparación de tipos.
     */
    public ComponenteGráficoDispersión() {

    }

    /**
     * Constructor con parámetros
     *
     * @param mg Referencia a ModoGráficos.
     * @param NúmeroColumna Número de columna en la que se ha de crear.
     * @param Boolean Primer componente (true) o no (false)
     */
    public ComponenteGráficoDispersión(ModoGráficos mg, int NúmeroColumna, Boolean primero) {
        this.NúmeroColumna = NúmeroColumna;
        this.mg = mg;
        this.Primero = primero;

        CrearComponente();
        GenerarIDyToolTip();
    }

    private void CrearComponente() {
        this.setId(constantes.NOMBRES_ELEMENTOS.CGRÁFICO_DISPERSIÓN.Nombre);
        TextField NombreSerieDispersión = new TextField();
        NombreSerieDispersión.setPromptText("Nombre de la serie " + NúmeroColumna);
        HBox ContenedorH1 = new HBox();
        
            Button BotónEliminar = new Button("-");
            ContenedorH1.getChildren().setAll(NombreSerieDispersión, BotónEliminar);
            MétodosCompartidos.CrecerEquitativamente(ContenedorH1);
            BotónEliminar.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    MétodosCompartidos.EliminarColumnas(BotónEliminar, mg.getGridGráficos(), ListadoCFil);
                    mg.getBotónCrearGráfico().fire(); // Se crea un nuevo gráfico para reiniciar los tooltips
                }
            });
        
            if (Primero){
                BotónEliminar.setDisable(true);
            }

        HBox ContenedorH2 = new HBox();
        mg.setComboEjeX(MétodosCompartidos.CrearComboBox(constantes.COMPONENTE_GRÁFICO_DISPERSIÓN.EJE_X.OpcionesCombo, 0));
        mg.setComboEjeY(MétodosCompartidos.CrearComboBox(constantes.COMPONENTE_GRÁFICO_DISPERSIÓN.EJE_Y.OpcionesCombo, 1));
        ContenedorH2.getChildren().addAll(mg.getComboEjeX(), mg.getComboEjeY());
        if (NúmeroColumna > 1) { // El nuevo VBox tiene inicialmente los mismos valores en sus Combos que el primero
            for (Node nodo : mg.getGridGráficos().getChildrenUnmodifiable()) {
                if (nodo instanceof VBox && GridPane.getColumnIndex(nodo) == 1 && nodo.isVisible()) {
                    HBox CajaHorizontal = ((HBox) ((VBox) nodo).getChildren().get(1));
                    String ValorPrimerCombo = ((ComboBox) CajaHorizontal.getChildrenUnmodifiable().get(0)).getValue().toString();
                    String ValorSegundoCombo = ((ComboBox) CajaHorizontal.getChildrenUnmodifiable().get(1)).getValue().toString();
                    mg.getComboEjeX().setValue(ValorPrimerCombo);
                    mg.getComboEjeY().setValue(ValorSegundoCombo);
                }
            }
        }

        ColorPicker seleccionadorColor = new ColorPicker();
        seleccionadorColor.setPrefWidth(117);
        StackPane sp = new StackPane();
        sp.setId(constantes.IDs_CSS.STACKPANE.identificador);
        CheckBox checkMostrarSerie = new CheckBox("Mostrar serie");
        checkMostrarSerie.setSelected(true);
        sp.getChildren().add(checkMostrarSerie);
        HBox ContenedorH3 = new HBox();
        ContenedorH3.getChildren().setAll(seleccionadorColor, sp);

        MétodosCompartidos.CrecerEquitativamente(ContenedorH3);

        this.getChildren().addAll(ContenedorH1, ContenedorH2, ContenedorH3);
    }

    private void GenerarIDyToolTip() {
        this.setId(constantes.NOMBRES_ELEMENTOS.CGRÁFICO_DISPERSIÓN.Nombre);
        new CrearToolTip(this, mg.getPANEL_GRÁFICOS(), 5);
    }

}
