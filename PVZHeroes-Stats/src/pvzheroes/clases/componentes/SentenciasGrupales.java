package pvzheroes.clases.componentes;

import pvzheroes.clases.diálogos.DiálogoFunciónGrupo;
import java.util.HashMap;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;
import pvzheroes.clases.FXMLDocumentController;
import pvzheroes.clases.ModoGráficos;
import pvzheroes.clases.constantes;
import pvzheroes.clases.diccionarios.DiccionarioPropiedades;
import pvzheroes.clases.efectosConcretos.ToolTip;

public class SentenciasGrupales extends GridPane {

    private Button BotónNuevo;
    public HashMap<String, String> FuncionesDefinidas;

    private final FXMLDocumentController c;
    private final ModoGráficos g;
    private int Origen;

    /**
     * Constructor con parámetros.
     *
     * @param c Referencia al controlador de la vista. Sólo necesaria si el
     * componente se ha originado desde el modo Básico.
     * @param g Referencia a "ModoGráficos". Sólo necesaria si el componente se
     * ha originado desde el Modo Gráficos.
     * @param ModoOrigen Modo en el que se sitúa el componente.
     */
    public SentenciasGrupales(FXMLDocumentController c, ModoGráficos g, int ModoOrigen) {
        this.c = c;
        this.g = g;
        this.Origen = ModoOrigen;
        FuncionesDefinidas = new HashMap();
        CrearComponente();
        GenerarIDyToolTip();
    }

    private void CrearComponente() {
        BotónNuevo = new Button("Nuevo");
        this.add(BotónNuevo, 0, 0);
        BotónNuevo.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                if (DiccionarioPropiedades.PROP.getProperty("sonido").equals("ON")) {
                    new MediaPlayer(constantes.SONIDOS.BOTÓN.sonido).play();
                }
                new DiálogoFunciónGrupo(c, SentenciasGrupales.this);
            }
        });
        BotónNuevo.setMinWidth(148);
        GridPane.setMargin(BotónNuevo, new Insets(15, 2, 15, 2));
        this.setPrefWidth(150);
        ColumnConstraints col = new ColumnConstraints();
        col.setHalignment(HPos.CENTER);
        this.getColumnConstraints().add(col);
    }

    private void GenerarIDyToolTip() {
        this.setId(constantes.NOMBRES_ELEMENTOS.SENTENCIAS_GRUPALES.Nombre);
        ToolTip t;
        if (Origen == 0) {
            t = new ToolTip(this, c.getPanelBásico(), this.getId(), constantes.MODO_HIPERVÍNCULO.SENTENCIAS_GRUPALES.nombre, true);
        } else {
            t = new ToolTip(this, g.getPANEL_GRÁFICOS(), this.getId(), constantes.MODO_HIPERVÍNCULO.SENTENCIAS_GRUPALES.nombre, true);
        }
        t.setRedondeamientoBordes(20);
        t.setId(this.getId()+"ToolTip");
    }

    public Button getBotónNuevo() {
        return BotónNuevo;
    }

    public HashMap<String, String> getFuncionesDefinidas() {
        return FuncionesDefinidas;
    }

}