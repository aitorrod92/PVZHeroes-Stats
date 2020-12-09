package pvzheroes.clases.efectosConcretos;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import java.io.IOException;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import pvzheroes.clases.diccionarios.DiccionarioPropiedades;

/**
 * Generar un rectángulo informativo en un nodo, con un encabezado en negrita y
 * una descripción debajo, que puede o no ser un enlace. En caso de que sea
 * enlace, ha de definirse la funcionalidad dentro del método
 * "AñadirAcciónLink".
 *
 * @param nodo El nodo en cuestión.
 * @param Panel El Pane que define dónde aparecerá el rectángulo. Puede ser el
 * panel que contiene al nodo o él mismo. Si se trata de un GridPane, VBox o
 * HBox, es preferible utilizar un AnchorPane que sea su padre en su lugar, pues
 * los ToolTips se insertarán en las celdas, ocupando espacio. En dicho
 * AnchorPane, el nodo debe de estar posicionado con los métodos "setLayout" en
 * lugar de "setTranslate" para un posicionamiento adecuado.
 * @param textoEncabezado La cadena de texto a mostrar en el encabezado.
 * @param textoLink La cadena de texto a mostrar como descripción.
 * @param EsLink Booleano que indica si la descripción es o no un enlace.
 *
 * Parámetros opcionales:
 * @param colorFondo Color de fondo del rectángulo
 * @param colorBorde Color de borde del rectángulo
 *
 * Requiere la clase "Temporizador" si se quiere establecer un retardo o, en su
 * defecto, comentar las porciones relevantes de código.
 *
 */
public class ToolTip extends Node {

    public static Boolean EnRectángulo = false;
    public static Boolean EnComponente = false;
    public static Boolean YaEntrado = false;

    private Boolean activación = true;

    private final Node nodo;
    private final Pane panel;
    private final String textoEncabezado;
    private final String textoDescripción;
    private final Boolean EsLink;
    private String colorFondo = "";
    private String colorBorde = "";
    private static final int TIEMPOPORDEFECTO = 2000;

    private static int milisegundos = TIEMPOPORDEFECTO;
    private Temporizador temp;
    public Boolean CampoFalso;

    private Rectangle r;
    private StackPane sp;

    public ToolTip(Node nodo, Pane panel, String textoEncabezado, String textoDescripción, Boolean EsLink) {
        this.nodo = nodo;
        this.panel = panel;
        this.textoEncabezado = textoEncabezado;
        this.textoDescripción = textoDescripción;
        this.EsLink = EsLink;

        //AQUÍ
        milisegundos = Integer.parseInt(DiccionarioPropiedades.PROP.getProperty("retardoAyuda"));

        sp = GenerarToolTip();
        AñadirHandlers(sp);
    }

    public ToolTip(Node nodo, Pane panel, String textoEncabezado, String textoDescripción, Boolean EsLink,
            String colorFondo, String colorBorde) {
        this.nodo = nodo;
        this.panel = panel;
        this.textoEncabezado = textoEncabezado;
        this.textoDescripción = textoDescripción;
        this.EsLink = EsLink;
        this.colorFondo = colorFondo;
        this.colorBorde = colorBorde;

        sp = GenerarToolTip();
        AñadirHandlers(sp);
    }

    public void setActivación(Boolean activación) {
        this.activación = activación;
    }

    public void setColorFondo(String colorFondo) {
        this.colorFondo = colorFondo;
        CambiarColor();
    }

    private void CambiarColor() {
        if (colorFondo.equals("")) {
            colorFondo = "white";
        } else if (colorBorde.equals("")) {
            colorBorde = "black";
        }
        r.setStyle("-fx-fill: " + colorFondo + ";" + "-fx-stroke: " + colorBorde + ";");
    }

    public void setColorBorde(String colorBorde) {
        this.colorBorde = colorBorde;
        CambiarColor();
    }

    public static void setTemporizador(int milisegundos) {
        ToolTip.milisegundos = milisegundos;
    }

    /**
     * Define el desplazamiento lateral respecto al Nodo (a) o la posición del
     * ToolTip (b). La situación "a" sólo sucede si se encuentra dentro de un
     * GridPane, VBox o HBox del que es el único elemento. La situación "b"
     * corresponde a cuando se encuentra dentro de un AnchorPane (obligatorio
     * crearlo como padre de los Pane anteriores cuando no es el único
     * elemento). En tal caso, el valor que pasemos será el desplazamiento
     * lateral respecto al origen del AnchorPane, es decir, la posición X
     * absoluta.
     */
    public void setDesplazamientoLateral(int desplazamientoLateral) {
        sp.setTranslateX(desplazamientoLateral);
    }

    /**
     * Define el desplazamiento vertical respecto al Nodo (a) o la posición del
     * ToolTip (b). La situación "a" sólo sucede si se encuentra dentro de un
     * GridPane, VBox o HBox del que es el único elemento. La situación "b"
     * corresponde a cuando se encuentra dentro de un AnchorPane (obligatorio
     * crearlo como padre de los Pane anteriores cuando no es el único
     * elemento). En tal caso, el valor que pasemos será el desplazamiento
     * vertical respecto al origen del AnchorPane, es decir, la posición Y
     * absoluta.
     */
    public void setDesplazamientoVertical(int desplazamientoVertical) {
        sp.setTranslateY(desplazamientoVertical);
    }

    /**
     * Define si los bordes son redondeados.
     *
     * @param redondeamiento Grado de redondeamiento, siendo 0 un rectángulo
     * puro.
     */
    public void setRedondeamientoBordes(int redondeamiento) {
        r.setArcHeight(redondeamiento);
        r.setArcWidth(redondeamiento);
    }

    private StackPane GenerarToolTip() {
        Label encabezado = new Label(" " + textoEncabezado);
        encabezado.setFont(Font.font(null, FontWeight.BOLD, 12));
        Labeled descripción;
        if (EsLink) {
            descripción = new Hyperlink();
            AñadirAcciónLink((Hyperlink) descripción);
        } else {
            descripción = new Label();
        }
        descripción.setFont(Font.font(null, FontWeight.BOLD, 12));

        descripción.setText(textoDescripción);

        r = CrearRectángulo(TextoMásAncho(descripción, encabezado) + 5, 40);
        VBox vbox = new VBox(encabezado, descripción);

        StackPane sp = new StackPane(r, vbox);
        panel.getChildren().add(sp);
        sp.setVisible(false);
        return sp;
    }

    private void AñadirAcciónLink(Hyperlink link) {
        link.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                Runtime r = Runtime.getRuntime();
                try {
                    r.exec("Programa.exe");
                } catch (IOException ex) {
                    System.out.println("No se puede ejecutar \"Programa.exe\"");
                }
            }
        });
    }

    private void AñadirHandlers(StackPane sp) {
        /* Si el ratón ha estado ya en el StackPane, entra en componente desde él, y por tanto, se oculta. 
        Si el ratón no ha estado en el StackPane, entra en el componente desde fuera y, por tanto, se muestra. */
        nodo.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (milisegundos != 0) {

                    temp = new Temporizador(milisegundos, 1, ToolTip.this, "MostrarToolTip");
                    temp.start();

                } else {
                    MostrarToolTip();
                }


                /* Salir del componente implica que el StackPane se oculte 
                (aunque puede ser cambiado por el propio StackPane si se está
                entrando en él, como se ve más abajo)*/
                nodo.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (milisegundos != 0) {
                            temp.interrumpirEspera();
                        }
                        EnComponente = false;
                        YaEntrado = false;
                        sp.toBack();
                        sp.setVisible(false);
                    }
                });

                // El StackPane se autoactiva al entrar el puntero en él
                sp.setOnMouseEntered(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        EnRectángulo = true;
                        YaEntrado = true;
                        sp.toFront();
                        sp.setVisible(true);
                    }
                });

                // El StackPane se auto-oculta al salir el puntero de él
                sp.setOnMouseExited(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        temp.interrumpirEspera();
                        EnRectángulo = false;
                        sp.toBack();
                        sp.setVisible(false);

                    }
                });
            }
        });
    }

    /**
     * Método que unifica la generación de ToolTips con y sin temporizador
     *
     * @param argumentos
     */
    public void MostrarToolTip(String... argumentos) {
        /* Únicamente activos cuando la propiedad correspondiente es true */
        if (activación) {
            EnComponente = true;
            if (YaEntrado) {
                sp.toBack();
                sp.setVisible(false);
            } else {
                sp.toFront();
                sp.setVisible(true);
                Platform.runLater(() -> sp.relocate(nodo.getLayoutX(), nodo.getLayoutY() + 10));
                // Esto es clave a la hora de hacer que los tooltips se centren
                sp.setMaxSize(r.getWidth(), r.getHeight());
            }
        }
    }

    /**
     * Compara la anchura de dos textos y devuelve el valor más alto. Requiere
     * el método "CalcularDimensionesTexto" para funcionar.
     *
     * @param etiquetado1 Primer Label, Hyperlink, etc.
     * @param etiquetado2 Segundo Label, Hyperlink, etc.
     * @return Valor double con la anchura mayor.
     */
    public static double TextoMásAncho(Labeled etiquetado1, Labeled etiquetado2) {
        double[] dimensiones1 = CalcularDimensionesTexto(etiquetado1);
        double[] dimensiones2 = CalcularDimensionesTexto(etiquetado2);
        if (dimensiones1[1] > dimensiones2[1]) {
            return (dimensiones1[1]);
        } else {
            return dimensiones2[1];
        }
    }

    /**
     * Calcula las dimensionesde una etiqueta. Los estilos que se hayan aplicado
     * con CSS no se tienen en cuenta en el cálculo (esto significa que, si
     * quieres que se utilice la negrita, has de incluir la en la propia fuente
     * de la etiqueta). Los links arrojan un resultado ligeramente menor al que
     * les corresponde, por lo que se ha aplicado internamente un corrector del
     * valor.
     *
     * @param etiqueta Etiqueta (label, hyperlink...) de la que se quiere
     * calcular la anchura.
     * @return Array con altura [0] y anchura [1]
     */
    public static double[] CalcularDimensionesTexto(Labeled etiqueta) {
        Text texto = new Text(etiqueta.getText());
        texto.setFont(etiqueta.getFont());
        double altura = texto.getBoundsInLocal().getHeight();
        double anchura = texto.getBoundsInLocal().getWidth();
        if (etiqueta instanceof Hyperlink) {
            anchura = anchura + 5;
        }
        double[] dimensiones = {altura, anchura};
        return dimensiones;
    }

    /**
     * Crea un rectángulo con los parámetros indicados, aunque la sombra es
     * predeterminada.
     *
     * @param TamañoX Anchura del rectángulo
     * @param TamañoY Altura del rectángulo
     * @param colorFondo Color de fondo
     * @param colorBorde Color de borde
     * @return Rectángulo con los parámetros indicados
     */
    public Rectangle CrearRectángulo(double TamañoX, double TamañoY) {
        r = new Rectangle(TamañoX, TamañoY);
        if (colorFondo.equals("")) {
            r.setStyle("-fx-fill: white; -fx-stroke: black;");
        } else {
            CambiarColor();
        }
        DropShadow s = new DropShadow();
        s.setWidth(20);
        s.setHeight(20);
        s.setOffsetX(10);
        s.setOffsetY(10);
        s.setRadius(10);
        r.setEffect(s);
        return r;
    }

    @Override
    protected NGNode impl_createPeer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BaseBounds impl_computeGeomBounds(BaseBounds bounds, BaseTransform tx) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected boolean impl_computeContains(double localX, double localY) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object impl_processMXNode(MXNodeAlgorithm alg, MXNodeAlgorithmContext ctx) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
