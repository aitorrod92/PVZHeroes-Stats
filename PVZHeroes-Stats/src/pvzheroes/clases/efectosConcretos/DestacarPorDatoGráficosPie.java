package pvzheroes.clases.efectosConcretos;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Permite implementar un manejador en los gráficos de tarta, el cual destaca un
 * DATO cuando el cursor pasa por encima, difumina el resto y retorna a la
 * situación inicial cuando sale.
 */
public class DestacarPorDatoGráficosPie implements Runnable, ClaseBaseDestacarGráficos  {
    private final ObservableList<PieChart.Data> TODOS_LOS_DATOS;
    private final PieChart.Data DATO;

    /**
     * Constructor único
     * @param TodosLosDatos El resto de datos del gráfico
     * @param dato El dato en cuestión
     */
    public DestacarPorDatoGráficosPie(ObservableList<PieChart.Data> TodosLosDatos,
            PieChart.Data dato) {
        TODOS_LOS_DATOS = TodosLosDatos;
        DATO = dato;
    }

    @Override
    public void run() {
        FijarOpacidadNormal();
        AñadirHandlerEntrada();
        AñadirHandlerSalida();
    }

    @Override
    public void FijarOpacidadNormal() {
        DATO.getNode().setOpacity(OPACIDAD_NORMAL);
    }

    @Override
    public void AñadirHandlerEntrada() {
        DATO.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                DATO.getNode().setOpacity(OPACIDAD_DESTACADO);
                for (PieChart.Data datoo : TODOS_LOS_DATOS) {
                    if (!datoo.equals(DATO)) {
                        datoo.getNode().setOpacity(OPACIDAD_BAJA);
                    }
                }
            }
        }
        );

    }

    @Override
    public void AñadirHandlerSalida() {
        DATO.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                DATO.getNode().setOpacity(OPACIDAD_NORMAL);
                for (PieChart.Data datoo : TODOS_LOS_DATOS) {
                    if (!datoo.equals(DATO)) {
                        datoo.getNode().setOpacity(OPACIDAD_NORMAL);
                    }
                }
            }
        }
        );
    }

}
