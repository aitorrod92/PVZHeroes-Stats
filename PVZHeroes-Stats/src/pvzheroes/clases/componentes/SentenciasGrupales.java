package pvzheroes.clases.componentes;

import pvzheroes.clases.di�logos.Di�logoFunci�nGrupo;
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
import pvzheroes.clases.ModoGr�ficos;
import pvzheroes.clases.constantes;
import pvzheroes.clases.diccionarios.DiccionarioPropiedades;
import pvzheroes.clases.efectosConcretos.ToolTip;

public class SentenciasGrupales extends GridPane {

    private Button Bot�nNuevo;
    public HashMap<String, String> FuncionesDefinidas;

    private final FXMLDocumentController c;
    private final ModoGr�ficos g;
    private int Origen;

    /**
     * Constructor con par�metros.
     *
     * @param c Referencia al controlador de la vista. S�lo necesaria si el
     * componente se ha originado desde el modo B�sico.
     * @param g Referencia a "ModoGr�ficos". S�lo necesaria si el componente se
     * ha originado desde el Modo Gr�ficos.
     * @param ModoOrigen Modo en el que se sit�a el componente.
     */
    public SentenciasGrupales(FXMLDocumentController c, ModoGr�ficos g, int ModoOrigen) {
        this.c = c;
        this.g = g;
        this.Origen = ModoOrigen;
        FuncionesDefinidas = new HashMap();
        CrearComponente();
        GenerarIDyToolTip();
    }

    private void CrearComponente() {
        Bot�nNuevo = new Button("Nuevo");
        this.add(Bot�nNuevo, 0, 0);
        Bot�nNuevo.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                if (DiccionarioPropiedades.PROP.getProperty("sonido").equals("ON")) {
                    new MediaPlayer(constantes.SONIDOS.BOT�N.sonido).play();
                }
                new Di�logoFunci�nGrupo(c, SentenciasGrupales.this);
            }
        });
        Bot�nNuevo.setMinWidth(148);
        GridPane.setMargin(Bot�nNuevo, new Insets(15, 2, 15, 2));
        this.setPrefWidth(150);
        ColumnConstraints col = new ColumnConstraints();
        col.setHalignment(HPos.CENTER);
        this.getColumnConstraints().add(col);
    }

    private void GenerarIDyToolTip() {
        this.setId(constantes.NOMBRES_ELEMENTOS.SENTENCIAS_GRUPALES.Nombre);
        ToolTip t;
        if (Origen == 0) {
            t = new ToolTip(this, c.getPanelB�sico(), this.getId(), constantes.MODO_HIPERV�NCULO.SENTENCIAS_GRUPALES.nombre, true);
        } else {
            t = new ToolTip(this, g.getPANEL_GR�FICOS(), this.getId(), constantes.MODO_HIPERV�NCULO.SENTENCIAS_GRUPALES.nombre, true);
        }
        t.setRedondeamientoBordes(20);
        t.setId(this.getId()+"ToolTip");
    }

    public Button getBot�nNuevo() {
        return Bot�nNuevo;
    }

    public HashMap<String, String> getFuncionesDefinidas() {
        return FuncionesDefinidas;
    }

}