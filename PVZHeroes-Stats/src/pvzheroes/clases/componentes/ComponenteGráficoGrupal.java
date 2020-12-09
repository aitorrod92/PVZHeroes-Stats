package pvzheroes.clases.componentes;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pvzheroes.clases.ModoGráficos;
import static pvzheroes.clases.ModoGráficos.ListadoCFG;
import pvzheroes.clases.constantes;
import pvzheroes.clases.efectosConcretos.CrearToolTip;
import pvzheroes.clases.métodosCompartidos.MétodosCompartidos;

/**
 * Componente Gráfico Grupal que permite seleccionar los parámetros de los
 * Gráficos de Barras, Gráficos de Barras Apiladas y Gráficos de Tarta. Incluye
 * un ComboBox para seleccionar el tipo de función, otro ComboBox para
 * seleccionar el tipo de variable y un botón para eliminar la serie.
 *
 */
public class ComponenteGráficoGrupal extends HBox {

    private ModoGráficos mg;
    private Boolean Primero;

    /**
     * Constructor sin parámetros, utilizado para poder crear instancias cuando
     * se requiere una comparación de tipos.
     */
    public ComponenteGráficoGrupal() {

    }

    /**
     * Constructor con parámetros
     *
     * @param mg Referencia a ModoGráficos.
     * @param primero Es el primer componente (true) o no (false)
     */
    public ComponenteGráficoGrupal(ModoGráficos mg, Boolean primero) {
        this.mg = mg;
        this.Primero = primero;
        CrearComponente();
        GenerarIDyToolTip();
    }

    private void CrearComponente() {
        ComboBox ComboTipoFunción = MétodosCompartidos.CrearComboBox(constantes.SENTENCIAS_GRUPALES.FUNCIONES.OpcionesCombo, 0);
        ComboBox ComboTipoVariable = MétodosCompartidos.CrearComboBox(constantes.SENTENCIAS_GRUPALES.VARIABLES.OpcionesCombo, 0);
        ComboTipoVariable.setDisable(true);

        ComboTipoFunción.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                Boolean valor = ComboTipoFunción.getSelectionModel().getSelectedItem().equals("Conteo");
                ComboTipoVariable.setDisable(valor);
            }

        });
        VBox ContenedorV = new VBox();
        ContenedorV.getChildren().addAll(ComboTipoFunción, ComboTipoVariable);

        Button BotónEliminar = new Button("-");
        BotónEliminar.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                MétodosCompartidos.EliminarColumnas(BotónEliminar, mg.getGridGráficos(), ListadoCFG);
            }
        });
        this.getChildren().addAll(ContenedorV, BotónEliminar);
                if (Primero) {
                    BotónEliminar.setDisable(true);
                }
    }

    private void GenerarIDyToolTip() {
        this.setId(constantes.NOMBRES_ELEMENTOS.CGRÁFICO_GRUPAL.Nombre);
        new CrearToolTip(this, mg.getPANEL_GRÁFICOS(), 6);
    }

}
