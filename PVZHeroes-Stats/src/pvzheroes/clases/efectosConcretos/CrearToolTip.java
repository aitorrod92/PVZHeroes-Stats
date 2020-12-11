package pvzheroes.clases.efectosConcretos;

import java.io.IOException;
import java.util.HashMap;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import pvzheroes.clases.FXMLDocumentController;
import pvzheroes.clases.constantes;
import pvzheroes.clases.diccionarios.DiccionarioPropiedades;
import pvzheroes.clases.di�logos.Di�logoOpciones;
import pvzheroes.clases.m�todosCompartidos.M�todosCompartidos;

/**
 * Crea un rect�ngulo informativo (ToolTip) en un nodo.
 *
 */
public class CrearToolTip {

    private static HashMap<String, StackPane> ListaToolTips = new HashMap();

    public static Boolean EnRect�ngulo = false;
    public static Boolean EnComponente = false;
    public static Boolean YaEntrado = false;

    private String[] ModosHiperV�nculo = {constantes.MODO_HIPERV�NCULO.MODO_B�SICO.nombre, 
        constantes.MODO_HIPERV�NCULO.MODO_AVANZADO.nombre, constantes.MODO_HIPERV�NCULO.MODO_GR�FICOS.nombre,
        constantes.MODO_HIPERV�NCULO.SENTENCIA_INDIVIDUAL.nombre, constantes.MODO_HIPERV�NCULO.SENTENCIAS_GRUPALES.nombre,
        constantes.MODO_HIPERV�NCULO.CGR�FICO_DISPERSI�N.nombre, constantes.MODO_HIPERV�NCULO.CGR�FICO_GRUPAL.nombre, 
        constantes.MODO_HIPERV�NCULO.GR�FICO_BARRAS.nombre, constantes.MODO_HIPERV�NCULO.GR�FICO_BARRAS_APILADAS.nombre,
        constantes.MODO_HIPERV�NCULO.GR�FICO_DISPERSI�N.nombre, constantes.MODO_HIPERV�NCULO.GR�FICO_TARTA.nombre,
        constantes.MODO_HIPERV�NCULO.DI�LOGO_FILTROS.nombre, constantes.MODO_HIPERV�NCULO.DI�LOGO_FG.nombre,
        constantes.MODO_HIPERV�NCULO.BARRA_MEN�S.nombre, constantes.MODO_HIPERV�NCULO.DI�LOGO_OPCIONES.nombre};
    public static final int InicioGr�ficos = 7;

    /**
     * Generar un rect�ngulo informativo en un nodo. La informaci�n ser� el id
     * del nodo que se pasa como argumento y, el hiperv�nculo, el panel en el
     * que se encuentre.
     *
     * @param nodo El nodo en cuesti�n.
     * @param Panel El Pane que define d�nde aparecer� el rect�ngulo. Puede ser
     * el panel que contiene al nodo o �l mismo. Si se trata de un GridPane, es
     * preferible utilizar un AnchorPane que sea su padre en su lugar, pues los
     * ToolTips se insertar�n en las celdas, ocupando espacio. En dicho
     * AnchorPane, el nodo debe de estar posicionado con los m�todos "setLayout"
     * en lugar de "setTranslate" para un posicionamiento adecuado.
     *
     * @param modoOrigen El modo en el que se sit�a el nodo, utilizado para
     * definir el hiperv�nculo. Si el hiperv�nculo tiene un valor que
     * corresponde al modo en el que se origina, se debe pasar "-1" y utilizar�
     * el valor correspondiente al modo de FXMLDocumentController. Si el
     * hiperv�nculo ha de tener un valor distinto al modo en el que se origina,
     * se puede pasar "X" y utlizar� el valor correspondiente a
     * ModosHiperV�nculo[X].
     *
     */
    public CrearToolTip(Node nodo, Pane Panel, int modoOrigen) {
        Label etiqueta = new Label(" " + nodo.getId());
        etiqueta.setFont(Font.font(null, FontWeight.BOLD, 12));
        etiqueta.setAlignment(Pos.CENTER);
        Hyperlink link = new Hyperlink();
        link.setFont(Font.font(null, FontWeight.BOLD, 12));
        if (modoOrigen == -1) {
            link.setText(ModosHiperV�nculo[FXMLDocumentController.modo]);
        } else {
            link.setText(ModosHiperV�nculo[modoOrigen]);
        }

        Rectangle r = M�todosCompartidos.
                CrearRect�ngulo(M�todosCompartidos.TextoM�sAncho(link, etiqueta) 
                        + 5, 40, Color.WHITE, "black", 10);
        VBox vbox = new VBox(etiqueta, link);
        link.setId(constantes.IDs_CSS.NEGRITA.identificador);

        StackPane sp = new StackPane(r, vbox);
        Panel.getChildren().add(sp);
        sp.setVisible(false);
        link.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                Runtime r = Runtime.getRuntime();
                try {
                    r.exec(constantes.RUTAS_EXTERNAS.AYUDA_EXE.Ruta 
                            + " \"" + link.getText() + "\"");
                } catch (IOException ex) {
                    System.out.println("No se ha podido abrir la ayuda");
                }
            }
        });

        /* Si el rat�n ha estado ya en el StackPane, entra en componente desde �l, y por tanto, se oculta. 
        Si el rat�n no ha estado en el StackPane, entra en el componente desde fuera y, por tanto, se muestra. */
        nodo.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                /* Esto se hace para poder verificar en el di�logo opciones que 
                la propiedad se ha cambiado, antes incluso de que se guarde */
                Boolean ActivadoEnDi�logo = true;
                try {
                    ActivadoEnDi�logo = Di�logoOpciones.getPropiedadesSinGuardar().get("ayuda").equals("true");
                } catch (NullPointerException e) {
                    // No hace falta gestionarlo
                }
                /* �nicamente activos cuando la propiedad correspondiente es true */
                if (DiccionarioPropiedades.PROP.getProperty("ayuda").equals("true") && ActivadoEnDi�logo) {
                    EnComponente = true;
                    if (YaEntrado) {
                        sp.toBack();
                        sp.setVisible(false);
                    } else {
                        sp.toFront();
                        sp.setVisible(true);
                        Platform.runLater(() -> sp.relocate(nodo.getLayoutX(), nodo.getLayoutY() + 10));
                        // Esto es clave a la hora de hacer que los tooltips se centren
                        sp.setMaxSize(100, 40);
                        AlterarPosici�n(nodo, sp);
                    }
                }
            }
        });

        /* Salir del componente implica que el StackPane se oculte 
        (aunque puede ser cambiado por el propio StackPane si se est�
        entrando en �l, como se ve m�s abajo)*/
        nodo.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                EnComponente = false;
                YaEntrado = false;
                sp.toBack();
                sp.setVisible(false);
            }
        });

        // El StackPane se autoactiva al entrar el puntero en �l
        sp.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                EnRect�ngulo = true;
                YaEntrado = true;
                sp.toFront();
                sp.setVisible(true);
            }
        });

        // El StackPane se auto-oculta al salir el puntero de �l
        sp.setOnMouseExited(new EventHandler() {
            @Override
            public void handle(Event event) {
                EnRect�ngulo = false;
                sp.toBack();
                sp.setVisible(false);

            }
        });

        sp.setId(nodo.getId() + "ToolTip");
    }

    /**
     * Algunos ToolTips necesitan ser colocados manualmente por encontrarse en
     * GridPane o porque aparecen en un di�logo que se les queda estrecho. El
     * resto se mueven ligeramente hacia abajo para no estorbar en la pulsaci�n
     * del elemento al que corresponden.
     */
    private void AlterarPosici�n(Node nodo, StackPane sp) {
        if /* Para que no aparezca en el borde la tabla */ (nodo.getId().equals(constantes.NOMBRES_ELEMENTOS.TABLA.Nombre)
                || nodo.getId().equals(constantes.NOMBRES_ELEMENTOS.TABLA_AV.Nombre)) {
            sp.setTranslateX(400);
        } else if (nodo.getId().equals(constantes.NOMBRES_ELEMENTOS.SELECCI�N_TIPO_GR�FICOS.Nombre)) {
            sp.setTranslateY(100);
        } else if (nodo.getId().equals(constantes.NOMBRES_ELEMENTOS.DF_SELECCI�N_RELACI�N_CONDICI�N.Nombre)) {
            sp.setTranslateX(10);
            sp.setTranslateY(126);
        } else if (nodo.getId().equals(constantes.NOMBRES_ELEMENTOS.DFG_BOT�N_A�ADIR.Nombre)
                || nodo.getId().equals(constantes.NOMBRES_ELEMENTOS.DFG_BOT�N_CONSULTAR.Nombre)) {
            sp.setTranslateX(80);
            sp.setTranslateY(100);
        } else if /* Para evitar ensanchar el di�logo */ (nodo.getId().equals(constantes.NOMBRES_ELEMENTOS.DFG_INTRODUCCI�N_IDENTIFICADOR.Nombre)
                || nodo.getId().equals(constantes.NOMBRES_ELEMENTOS.DFG_SELECCI�N_FUNCI�N.Nombre)
                || nodo.getId().equals(constantes.NOMBRES_ELEMENTOS.DFG_SELECCION_VARIABLE.Nombre)) {
            sp.setTranslateX(-30);
        } else if (nodo.getId().equals(constantes.NOMBRES_ELEMENTOS.DO_ACTIVAR_AYUDA.Nombre)) {
            sp.setTranslateX(150);
            sp.setTranslateY(176);
        } else if (nodo.getId().equals(constantes.NOMBRES_ELEMENTOS.DO_ACTIVAR_SONIDOS.Nombre)) {
            sp.setTranslateX(150);
            sp.setTranslateY(120);
        } else if (nodo.getId().equals(constantes.NOMBRES_ELEMENTOS.DO_ACTIVAR_COLOREADO.Nombre)) {
            sp.setTranslateX(150);
            sp.setTranslateY(155);
        } else if /* Para evitar ocultar el texto */ (nodo.getId().equals(constantes.NOMBRES_ELEMENTOS.SENTENCIAS_GRUPALES.Nombre)
                || nodo.getId().equals(constantes.NOMBRES_ELEMENTOS.CONSULTA_SIMPLE.Nombre)
                || nodo.getId().equals(constantes.NOMBRES_ELEMENTOS.CONSULTAS_GRUPO.Nombre)) {
            sp.setTranslateY(15);

        } else if (nodo.getId().equals(constantes.NOMBRES_ELEMENTOS.BOT�N_CREAR_GR�FICO.Nombre)) {
            sp.setTranslateY(170);
        }else if (nodo.getId().equals(constantes.NOMBRES_ELEMENTOS.BOT�N_TABLA.Nombre)){
            sp.setTranslateY(30);
            
        } else /* Para no ocultar el nodo */ {
            sp.setTranslateY(5);
        }
    }

}
