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
import pvzheroes.clases.ModoGr�ficos;
import static pvzheroes.clases.ModoGr�ficos.ListadoCFil;
import pvzheroes.clases.constantes;
import pvzheroes.clases.efectosConcretos.CrearToolTip;
import pvzheroes.clases.m�todosCompartidos.M�todosCompartidos;

/**
 * Componente Gr�fico Dispersi�n que permite seleccionar los par�metros del
 * Gr�fico de Dispersi�n. Incluye un TextField para introducir el nombre de la
 * serie, un bot�n para eliminar la serie, dos Comboboxes para seleccionar cada
 * uno de los ejes, un ColorPicker para asignar un color y un StackPane con un
 * CheckBox dentro para mostrar/esconder la serie.
 *
 */
public class ComponenteGr�ficoDispersi�n extends VBox {

    private int N�meroColumna;
    private ModoGr�ficos mg;
    private Boolean Primero;

    /**
     * Constructor sin par�metros, utilizado para poder crear instancias cuando
     * se requiere una comparaci�n de tipos.
     */
    public ComponenteGr�ficoDispersi�n() {

    }

    /**
     * Constructor con par�metros
     *
     * @param mg Referencia a ModoGr�ficos.
     * @param N�meroColumna N�mero de columna en la que se ha de crear.
     * @param Boolean Primer componente (true) o no (false)
     */
    public ComponenteGr�ficoDispersi�n(ModoGr�ficos mg, int N�meroColumna, Boolean primero) {
        this.N�meroColumna = N�meroColumna;
        this.mg = mg;
        this.Primero = primero;

        CrearComponente();
        GenerarIDyToolTip();
    }

    private void CrearComponente() {
        this.setId(constantes.NOMBRES_ELEMENTOS.CGR�FICO_DISPERSI�N.Nombre);
        TextField NombreSerieDispersi�n = new TextField();
        NombreSerieDispersi�n.setPromptText("Nombre de la serie " + N�meroColumna);
        HBox ContenedorH1 = new HBox();
        
            Button Bot�nEliminar = new Button("-");
            ContenedorH1.getChildren().setAll(NombreSerieDispersi�n, Bot�nEliminar);
            M�todosCompartidos.CrecerEquitativamente(ContenedorH1);
            Bot�nEliminar.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    M�todosCompartidos.EliminarColumnas(Bot�nEliminar, mg.getGridGr�ficos(), ListadoCFil);
                    mg.getBot�nCrearGr�fico().fire(); // Se crea un nuevo gr�fico para reiniciar los tooltips
                }
            });
        
            if (Primero){
                Bot�nEliminar.setDisable(true);
            }

        HBox ContenedorH2 = new HBox();
        mg.setComboEjeX(M�todosCompartidos.CrearComboBox(constantes.COMPONENTE_GR�FICO_DISPERSI�N.EJE_X.OpcionesCombo, 0));
        mg.setComboEjeY(M�todosCompartidos.CrearComboBox(constantes.COMPONENTE_GR�FICO_DISPERSI�N.EJE_Y.OpcionesCombo, 1));
        ContenedorH2.getChildren().addAll(mg.getComboEjeX(), mg.getComboEjeY());
        if (N�meroColumna > 1) { // El nuevo VBox tiene inicialmente los mismos valores en sus Combos que el primero
            for (Node nodo : mg.getGridGr�ficos().getChildrenUnmodifiable()) {
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

        M�todosCompartidos.CrecerEquitativamente(ContenedorH3);

        this.getChildren().addAll(ContenedorH1, ContenedorH2, ContenedorH3);
    }

    private void GenerarIDyToolTip() {
        this.setId(constantes.NOMBRES_ELEMENTOS.CGR�FICO_DISPERSI�N.Nombre);
        new CrearToolTip(this, mg.getPANEL_GR�FICOS(), 5);
    }

}
