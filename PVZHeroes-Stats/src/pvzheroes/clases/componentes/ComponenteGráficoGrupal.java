package pvzheroes.clases.componentes;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pvzheroes.clases.ModoGr�ficos;
import static pvzheroes.clases.ModoGr�ficos.ListadoCFG;
import pvzheroes.clases.constantes;
import pvzheroes.clases.efectosConcretos.CrearToolTip;
import pvzheroes.clases.m�todosCompartidos.M�todosCompartidos;

/**
 * Componente Gr�fico Grupal que permite seleccionar los par�metros de los
 * Gr�ficos de Barras, Gr�ficos de Barras Apiladas y Gr�ficos de Tarta. Incluye
 * un ComboBox para seleccionar el tipo de funci�n, otro ComboBox para
 * seleccionar el tipo de variable y un bot�n para eliminar la serie.
 *
 */
public class ComponenteGr�ficoGrupal extends HBox {

    private ModoGr�ficos mg;
    private Boolean Primero;

    /**
     * Constructor sin par�metros, utilizado para poder crear instancias cuando
     * se requiere una comparaci�n de tipos.
     */
    public ComponenteGr�ficoGrupal() {

    }

    /**
     * Constructor con par�metros
     *
     * @param mg Referencia a ModoGr�ficos.
     * @param primero Es el primer componente (true) o no (false)
     */
    public ComponenteGr�ficoGrupal(ModoGr�ficos mg, Boolean primero) {
        this.mg = mg;
        this.Primero = primero;
        CrearComponente();
        GenerarIDyToolTip();
    }

    private void CrearComponente() {
        ComboBox ComboTipoFunci�n = M�todosCompartidos.CrearComboBox(constantes.SENTENCIAS_GRUPALES.FUNCIONES.OpcionesCombo, 0);
        ComboBox ComboTipoVariable = M�todosCompartidos.CrearComboBox(constantes.SENTENCIAS_GRUPALES.VARIABLES.OpcionesCombo, 0);
        ComboTipoVariable.setDisable(true);

        ComboTipoFunci�n.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                Boolean valor = ComboTipoFunci�n.getSelectionModel().getSelectedItem().equals("Conteo");
                ComboTipoVariable.setDisable(valor);
            }

        });
        VBox ContenedorV = new VBox();
        ContenedorV.getChildren().addAll(ComboTipoFunci�n, ComboTipoVariable);

        Button Bot�nEliminar = new Button("-");
        Bot�nEliminar.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                M�todosCompartidos.EliminarColumnas(Bot�nEliminar, mg.getGridGr�ficos(), ListadoCFG);
            }
        });
        this.getChildren().addAll(ContenedorV, Bot�nEliminar);
                if (Primero) {
                    Bot�nEliminar.setDisable(true);
                }
    }

    private void GenerarIDyToolTip() {
        this.setId(constantes.NOMBRES_ELEMENTOS.CGR�FICO_GRUPAL.Nombre);
        new CrearToolTip(this, mg.getPANEL_GR�FICOS(), 6);
    }

}
