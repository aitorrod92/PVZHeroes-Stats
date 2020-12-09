package pvzheroes.clases.efectosConcretos;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;

/**
 * Permite implementar un manejador en los gráficos XY, el cual destaca un DATO
 * cuando el cursor pasa por encima, difumina el resto y retorna a la situación
 * inicial cuando sale.
 */
public class DestacarPorDatoGráficosXY implements 
        Runnable, ClaseBaseDestacarGráficos  {
    private final ArrayList<XYChart.Series> SERIES;
    private final XYChart.Data DATO;

    /**
     * Constructor único
     * @param Series Cada una de las series del Gráfico XY
     * @param dato Cada uno de los datos de las series del Gráfico XY
     */
    public DestacarPorDatoGráficosXY(ArrayList<XYChart.Series> Series, XYChart.Data dato) {
        this.SERIES = Series;
        this.DATO = dato;
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
                for (Object seriee : SERIES) {
                    for (Object marca : ((XYChart.Series< String, Number>) seriee).getData()) {
                        if (marca instanceof XYChart.Data && !marca.equals(DATO)) {
                            ((XYChart.Data) marca).getNode().setOpacity(OPACIDAD_BAJA);
                        }
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
            public void handle(MouseEvent event
            ) {
                DATO.getNode().setOpacity(OPACIDAD_NORMAL);
                for (Object seriee : SERIES) {
                    for (Object marca : ((XYChart.Series< String, Number>) seriee).getData()) {
                        if (marca instanceof XYChart.Data && !marca.equals(DATO)) {
                            ((XYChart.Data) marca).getNode().setOpacity(OPACIDAD_NORMAL);
                        }
                    }
                }
            }
        }
        );
    }
}
