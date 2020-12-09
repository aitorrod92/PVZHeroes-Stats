package pvzheroes.clases.efectosConcretos;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;

/**
 * Permite implementar un manejador en los gr�ficos XY, el cual destaca un DATO
 * cuando el cursor pasa por encima, difumina el resto y retorna a la situaci�n
 * inicial cuando sale.
 */
public class DestacarPorDatoGr�ficosXY implements 
        Runnable, ClaseBaseDestacarGr�ficos  {
    private final ArrayList<XYChart.Series> SERIES;
    private final XYChart.Data DATO;

    /**
     * Constructor �nico
     * @param Series Cada una de las series del Gr�fico XY
     * @param dato Cada uno de los datos de las series del Gr�fico XY
     */
    public DestacarPorDatoGr�ficosXY(ArrayList<XYChart.Series> Series, XYChart.Data dato) {
        this.SERIES = Series;
        this.DATO = dato;
    }

    @Override
    public void run() {
        FijarOpacidadNormal();
        A�adirHandlerEntrada();
        A�adirHandlerSalida();
    }

    @Override
    public void FijarOpacidadNormal() {
        DATO.getNode().setOpacity(OPACIDAD_NORMAL);
    }

    @Override
    public void A�adirHandlerEntrada() {
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
    public void A�adirHandlerSalida() {
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
