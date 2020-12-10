package aplicaciontemporal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class SistemaDeAyuda extends Application {

    static String StringDeInicio = "";

    private final String[] CONTENIDOS_PRINCIPALES = {"Temas por categoría", "Barra de Menús", "Modo Básico",
        "Modo Avanzado", "Modo Gráficos", "Sentencia Individual", "Sentencias Grupales", "C. Gráfico Grupal",
        "C. Gráfico Dispersión", "Diálogo filtros", "Diálogo FG", "Gráfico de Barras", "Gráfico Barras Apiladas",
        "Gráfico de Tarta", "Gráfico de Dispersión", "Ayuda emergente", "Estructura de la BBDD", "Atajos de teclado",
        "Opciones", "Fondos Personalizados", "Sonidos Personalizados", "Problemas comunes", "Contacto"};
    private final double POSICIÓN_INICIAL_DIVIDER = 0.185;
    private final double TAMAÑO_MÍNIMO_PANEL_IZQUIERDA = 0.185;
    private final double TAMAÑO_MÁXIMO_PANEL_IZQUIERDA = 0.185;
    private final double OPACIDAD_MÍNIMA = 0.1;
    private final double OPACIDAD_MEDIA = 0.3;
    private final double OPACIDAD_ALTA = 0.6;

    private VBox Vbox;

    ArrayList<Image> Imagengarg = new ArrayList();

    private ArrayList<ArrayList<Text>> TextosPosteriores = new ArrayList();
    private final HashMap<String, ArrayList<String>> TextosRectángulos = new LinkedHashMap();

    private enum PROPIEDADES_RECTÁNGULOS {
        TEMAS("Temas"),
        MENÚ("Menú"),
        MODO_BÁSICO("Básico"),
        MODO_AVANZADO("Avanzado"),
        MODO_GRÁFICOS("Gráficos"),
        DIÁLOGO_FILTROS("Diálogo Filtros"),
        DIÁLOGO_FG("Diálogo FG"),
        FILTROS_NORMALES("Sentencia Individual"),
        FILTROS_GRUPALES("Sentencias Grupales"),
        CGRÁFICO_GRUPAL("CGráfico Grupal"),
        CGRÁFICO_DISPERSIÓN("CGráfico Dispersión"),
        GRÁFICO_BARRAS("Gráfico de Barras"),
        GRÁFICO_BARRAS_APILADAS("Gráfico Barras Apiladas"),
        GRÁFICO_TARTA("Gráfico de Tarta"),
        GRÁFICO_DISPERSIÓN("Gráfico de Dispersión"),
        AYUDA_EMERGENTE("Ayuda emergente"),
        DIÁLOGO_OPCIONES("Opciones"),
        ESTRUCTURA_BBDD("Estructura de la BBDD"),
        ATAJOS_TECLADO("Atajos de teclado"),
        FONDOS_PERSONALIZADOS("Fondos Personalizados"),
        SONIDOS_PERSONALIZADOS("Sonidos Personalizados"),
        PROBLEMAS_COMUNES("Problemas comunes");

        Integer[][] ListadoPosiciones;
        Integer[][] ListadoTamaños;

        PROPIEDADES_RECTÁNGULOS(String Pantalla) {
            switch (Pantalla) {
                case "Temas":
                    this.ListadoPosiciones = new Integer[][]{{125, 26}, {125, 52}, {125, 78}, {125, 104}, {125, 129}, {125, 156}, {125, -1}, {125, -26}, {125, -52}, {125, -77}, {125, -104}, {125, -129}, {125, -154}};
                    this.ListadoTamaños = new Integer[][]{{239, 25}, {239, 25}, {239, 25}, {239, 25}, {239, 25}, {239, 25}, {239, 25}, {239, 25}, {239, 25}, {239, 25}, {239, 25}, {239, 25}, {239, 25}};
                    break;
                case "Básico":
                    this.ListadoPosiciones = new Integer[][]{{-401, -168}, {0, 80}, {-402, -122}, {-317, -118}, {-318, -186}, {-205, -175}};
                    this.ListadoTamaños = new Integer[][]{{56, 50}, {860, 282}, {56, 17}, {98, 102}, {98, 20}, {113, 18}};
                    break;
                case "Gráficos":
                    this.ListadoPosiciones = new Integer[][]{{-410, -163}, {-401, -80}, {-402, -98}, {-321, -175}, {-300, -69}, {11, 79}, {424, -155}};
                    this.ListadoTamaños = new Integer[][]{{56, 50}, {75, 17}, {75, 17}, {74, 36}, {116, 75}, {860, 203}, {50, 17}};
                    break;
                case "Avanzado":
                    this.ListadoPosiciones = new Integer[][]{{-126, -179}, {0, 80}, {-128, -114}, {110, -113}, {109, -180}};
                    this.ListadoTamaños = new Integer[][]{{400, 17}, {860, 282}, {395, 80}, {52, 17}, {52, 17}};
                    break;
                case "Menú":
                    this.ListadoPosiciones = new Integer[][]{{-110, 0}, {-40, 0}, {30, 0}, {100, 0}};
                    this.ListadoTamaños = new Integer[][]{{60, 25}, {60, 25}, {60, 25}, {60, 25}};
                    break;
                case "Sentencia Individual":
                    this.ListadoPosiciones = new Integer[][]{{-15, -9}, {66, 0}, {65, 50}};
                    this.ListadoTamaños = new Integer[][]{{129, 100}, {25, 25}, {25, 25}};
                    break;
                case "Sentencias Grupales":
                    this.ListadoPosiciones = new Integer[][]{{-13, -27}, {75, -2}, {-12, 39}};
                    this.ListadoTamaños = new Integer[][]{{150, 75}, {21, 25}, {150, 25}};
                    break;
                case "CGráfico Grupal":
                    this.ListadoPosiciones = new Integer[][]{{-11, -13}, {47, -13}, {-10, 12}};
                    this.ListadoTamaños = new Integer[][]{{93, 25}, {21, 25}, {93, 26}};
                    break;
                case "CGráfico Dispersión":
                    this.ListadoPosiciones = new Integer[][]{{-9, -26}, {108, -26}, {-55, 0}, {62, 0}, {-56, 24}, {61, 24}};
                    this.ListadoTamaños = new Integer[][]{{212, 25}, {20, 25}, {119, 26}, {115, 26}, {117, 26}, {117, 26}};
                    break;
                case "Diálogo Filtros":
                    this.ListadoPosiciones = new Integer[][]{{16, -40}, {24, -6}, {39, 30}, {40, 60}};
                    this.ListadoTamaños = new Integer[][]{{107, 25}, {128, 25}, {153, 25}, {159, 25}};
                    break;
                case "Diálogo FG":
                    this.ListadoPosiciones = new Integer[][]{{-2, -57}, {-3, -22}, {38, 13}, {-21, 13}, {28, 48}};
                    this.ListadoTamaños = new Integer[][]{{90, 25}, {90, 25}, {66, 25}, {50, 25}, {149, 25}};
                    break;
                case "Gráfico de Barras":
                    this.ListadoPosiciones = new Integer[][]{{-485, -25}, {-3, 128}, {3, 22}, {4, 100}};
                    this.ListadoTamaños = new Integer[][]{{25, 233}, {230, 24}, {100, 131}, {51, 20}};
                    break;
                case "Gráfico Barras Apiladas":
                    this.ListadoPosiciones = new Integer[][]{{-360, -32}, {-2, 176}, {10, 35}, {11, 142}};
                    this.ListadoTamaños = new Integer[][]{{25, 322}, {213, 30}, {222, 172}, {60, 20}};
                    break;
                case "Gráfico de Tarta":
                    this.ListadoPosiciones = new Integer[][]{{6, -32}, {5, 162}, {207, 50}};
                    this.ListadoTamaños = new Integer[][]{{57, 28}, {570, 30}, {150, 30}};
                    break;
                case "Gráfico de Dispersión":
                    this.ListadoPosiciones = new Integer[][]{{-480, -42}, {-9, 176}, {-132, -85}, {20, 122}, {284, -37}};
                    this.ListadoTamaños = new Integer[][]{{55, 300}, {280, 30}, {116, 120}, {958, 65}, {15, 15}};
                    break;
                case "Ayuda emergente":
                    this.ListadoPosiciones = new Integer[][]{{-25, -38}, {-28, -20}};
                    this.ListadoTamaños = new Integer[][]{{93, 19}, {80, 22}};
                    break;
                case "Opciones":
                    this.ListadoPosiciones = new Integer[][]{{140, -10}, {137, 21}, {137, 49}, {137, 77}};
                    this.ListadoTamaños = new Integer[][]{{80, 25}, {34, 20}, {34, 20}, {34, 20}};
                    break;
                case "Estructura de la BBDD":
                    this.ListadoPosiciones = new Integer[][]{{-208, -15}, {-71, -15}, {68, -15}, {207, -15}, {-209, 12}, {-72, 12}, {67, 12}, {206, 12}};
                    this.ListadoTamaños = new Integer[][]{{138, 28}, {139, 28}, {139, 28}, {139, 28}, {139, 28}, {139, 28}, {139, 28}, {139, 28}};
                    break;
                case "Atajos de teclado":
                    this.ListadoPosiciones = new Integer[][]{{-238, 0}, {-79, 0}, {80, 0}, {239, 0}};
                    this.ListadoTamaños = new Integer[][]{{159, 35}, {159, 35}, {159, 35}, {159, 35}};
                    break;
                case "Fondos Personalizados":
                    this.ListadoPosiciones = new Integer[][]{{0, 0}};
                    this.ListadoTamaños = new Integer[][]{{358, 430}};
                    break;
                case "Sonidos Personalizados":
                    this.ListadoPosiciones = new Integer[][]{{0, 0}};
                    this.ListadoTamaños = new Integer[][]{{359, 302}};
                    break;
                case "Problemas comunes":
                    this.ListadoPosiciones = new Integer[][]{{0, -65}, {0, -33}, {0, -1}, {0, 32}, {0, 64}};
                    this.ListadoTamaños = new Integer[][]{{690, 32}, {690, 32}, {690, 32}, {690, 32}, {690, 32}};
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            StringDeInicio = args[0];
        }
        launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {
        Vbox = new VBox();
        VBox Vbox2 = new VBox();
        String image = SistemaDeAyuda.class.getResource("recursosAyuda/fondo/FondoLateral.jpg").toExternalForm();
        Vbox.setStyle("-fx-background-image: url('" + image + "');" + "-fx-background-size: cover;");

        // Se lee el contenido de los archivos
        for (String Contenido : CONTENIDOS_PRINCIPALES) {
            Hyperlink enlace = new Hyperlink(Contenido);
            enlace.setId("enlacekhaki");
            String nombreEnlace = enlace.getText();
            File archivo = new File("./recursos/recursosAyuda/archivosAyuda/" + nombreEnlace + ".txt");
            ArrayList<String> TextoPrevioImagen = new ArrayList();
            try {
                FileInputStream fis = new FileInputStream(archivo);
                InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
                BufferedReader br = new BufferedReader(isr);

                // AQUI HABRÍA QUE COPIAR TODO EL TEXTO //
                try {
                    String línea;
                    int NúmeroTexto = -1;
                    while ((línea = br.readLine()) != null) {
                        /* "***" ejerce de separador, primero entre el primer TextFlow y la imagen y después
                        entre los distintos TextFlow que se generan al pulsar los rectángulos. Cada vez que se
                        detecta un "***", se genera un arrayList al que se le añade la siguiente línea, así como
                        una entrada de HashMap que permitir´á acceder a dicho ArrayList. Cada línea que no sea
                        la inmediatamente posterior a "***" será añadida a su ArrayList correspondiente, que es precisamente
                        el que corresponde a la última entrada del mapa*/
                        // ACLARAR QUE NO UTILIZAMOS SIMPLEMENTE TEXT POR SI HAY DISTINTAS LÍNEAS PARA UN MISMO TEXTFLOW

                        if (línea.equals("***")) {
                            ArrayList<String> nuevaOpción = new ArrayList();
                            String nombreOpción = br.readLine();
                            nuevaOpción.add(nombreOpción);
                            if (!TextosRectángulos.containsKey(nombreOpción)) {
                                TextosRectángulos.put(nombreOpción, nuevaOpción);
                            }
                            NúmeroTexto++;
                        } else if (NúmeroTexto == -1) {
                            TextoPrevioImagen.add(línea);
                        } else {
                            // Obtener última Key
                            int x = 1;
                            String últimaKey = "";
                            for (String key : TextosRectángulos.keySet()) {
                                if (x == TextosRectángulos.keySet().size()) {
                                    últimaKey = key;
                                }
                                x++;
                            }
                            TextosRectángulos.get(últimaKey).add(línea);

                        }

                    }
                    fis.close();
                    isr.close();
                    br.close();
                } catch (IOException ex) {
                    //Logger.getLogger(AplicacionTemporal.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                //Logger.getLogger(AplicacionTemporal.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Se procesa el texto obtenido para añadir enlaces
            final TextFlow tf = new TextFlow();
            final TextFlow tf2 = new TextFlow();

            // Añade los enlaces y el formato al TextFlow superior
            ArrayList<Text> ListAnterior = new ArrayList();
            ArrayList<Text> textsSinEnlaces = new ArrayList();
            String TextoPlano = "";
            for (int i = 0; i < TextoPrevioImagen.size(); i++) {
                TextoPlano = TextoPlano.concat(TextoPrevioImagen.get(i));
                textsSinEnlaces.add(new Text(TextoPrevioImagen.get(i)));
                if (i == 0) {
                    TextoPlano = TextoPlano.concat("\n");
                    textsSinEnlaces.add(new Text("\n"));
                }
            }
            ListAnterior = ObtenerPosicionesEnlaces(TextoPlano, ListAnterior);
            MostrarTextoFinal(ListAnterior, textsSinEnlaces, tf);

            // Se añade la imagen, los rectángulos asociadas a la misma y la lógica de ambos
            StackPane SP = new StackPane();
            ImageView iv = AñadirImagen(Contenido);
            Platform.runLater(() -> SP.setMaxWidth(iv.getFitWidth()));
            String ContenidoSinEspacio = Contenido.replace(" ", "").replace(".", "");
            if (!ContenidoSinEspacio.equals("Contacto")) {
                Method método = this.getClass().getDeclaredMethod("AñadirRectángulos"
                        + ContenidoSinEspacio, StackPane.class, TextFlow.class);
                método.invoke(this, SP, tf2);
            }
            SP.getChildren().add(iv);

            enlace.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    Vbox2.getChildren().clear();
                    Vbox2.getChildren().addAll(tf, new Label(""), SP, new Label(""),
                            tf2, new Label(""));
                }
            });
            Vbox.getChildren().add(enlace);
        }

        /* Se crea un SplitPane y dos ScrollPane, se vincula la posición del delimitador al ScrollIzquierdo 
        y se añaden los respectivos VBox a sus ScrollPane */
        SplitPane SP = new SplitPane();
        ScrollPane ScrollIzquierdo = new ScrollPane();
        ScrollIzquierdo.setContent(Vbox);
        ScrollIzquierdo.maxWidthProperty().bind(SP.widthProperty().multiply(TAMAÑO_MÍNIMO_PANEL_IZQUIERDA));
        ScrollIzquierdo.minWidthProperty().bind(SP.widthProperty().multiply(TAMAÑO_MÁXIMO_PANEL_IZQUIERDA));

        ScrollPane ScrollDerecho = new ScrollPane();
        ScrollDerecho.setContent(Vbox2);
        ScrollDerecho.setFitToHeight(true);
        Vbox2.setPadding(new Insets(10, 10, 10, 15));
        Vbox2.setId("PanelDerecho");
        ScrollDerecho.setFitToWidth(true);
        ScrollDerecho.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        ScrollDerecho.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ScrollBar sb = (ScrollBar) ScrollDerecho.lookup(".scroll-bar:vertical");
                sb.setPrefWidth(20);
                sb = (ScrollBar) ScrollIzquierdo.lookup(".scroll-bar:vertical");
                sb.setPrefWidth(20);
            }
        });

        /* Se añaden las dos mitades al SplitPane y se pulsa el primer hipervínculo 
        para que ejerza de pantalla de inicio*/
        SP.setDividerPositions(POSICIÓN_INICIAL_DIVIDER);
        SP.getItems().addAll(ScrollIzquierdo, ScrollDerecho);
        if (!StringDeInicio.equals("")) {
            for (int i = 0; i < CONTENIDOS_PRINCIPALES.length; i++) {
                if (CONTENIDOS_PRINCIPALES[i].equals(StringDeInicio)) {
                    ((Hyperlink) Vbox.getChildren().get(i)).fire();
                }
            }
        } else {
            ((Hyperlink) Vbox.getChildren().get(0)).fire();
        }

        // Se configura la ventana
        Scene scene = new Scene(new BorderPane(SP), 800, 600);
        stage.setScene(scene);
        stage.setTitle("Ayuda");
        stage.setMaximized(true);
        stage.setResizable(false);

        File estilo = new File("recursos/recursosAyuda/CSS/CSS.css");
        scene.getStylesheets().add("file:///" + estilo.getAbsolutePath().replace("\\", "/"));

        stage.show();
        MostrarDiálogoBienvenida();
    }

    private void MostrarDiálogoBienvenida() {
        Dialog d = new Dialog();
        d.setTitle("Ayuda");
        d.setHeaderText("Ayuda de PVZ Héroes Stats");
        d.setContentText("Bienvenido a la ayuda de PVZ Héroes Stats. Pulsa en los vínculos de la parte izquierda "
                + "para acceder a las diferentes secciones y, una vez en ellas, en las áreas de las "
                + "imágenes para examinar sus componentes. Puesto que el programa se terminó tras el programa de ayuda, "
                + "es posible que haya ligeras discrepancias de formato entre las imágenes mostradas y la apariencia real");
        d.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("recursosAyuda/diálogos/Gargologistpequeño.png"))));
        Stage stage = (Stage) d.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("recursosAyuda/diálogos/plantspequeño.png")));
        d.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        Optional<ButtonType> confirmación = d.showAndWait();
    }

    private ImageView AñadirImagen(String nombreArchivo) {
        ImageView iv = null;
        try {
            iv = new ImageView(new Image(getClass().getResourceAsStream("recursosAyuda/imágenes/" + nombreArchivo.replace(" ", "") + ".JPG")));
        } catch (NullPointerException e) {
            iv = new ImageView(new Image(getClass().getResourceAsStream("recursosAyuda/imágenes/" + nombreArchivo.replace(" ", "") + ".GIF")));
        }
        iv.setPreserveRatio(true);
        if (nombreArchivo.equals("Barra de Menús")) {
            iv.setFitWidth(300);
        } else if (nombreArchivo.equals("Modo Básico") || nombreArchivo.equals("Modo Avanzado") || nombreArchivo.equals("Modo Gráficos")) {
            iv.setFitWidth(900);
        } else if (nombreArchivo.equals("Gráfico de Barras")) {
            iv.setFitWidth(1000);
        }
        return iv;
    }

    private void AñadirRectángulosTemasporcategoría(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rectángulos = new ArrayList();
        Integer[][] ListadoTamaños = PROPIEDADES_RECTÁNGULOS.TEMAS.ListadoTamaños;
        for (int i = 0; i < ListadoTamaños.length; i++) {
            Rectángulos.add(new Rectangle(ListadoTamaños[i][0], ListadoTamaños[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECTÁNGULOS.TEMAS.ListadoPosiciones;
        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                for (int númeroRectángulo = 0; númeroRectángulo < Rectángulos.size(); númeroRectángulo++) {
                    Rectangle r = DefinirRectángulo(númeroRectángulo, Rectángulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    AñadirHandlersRectángulos(r, Rectángulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cuál es a partir de la posición y buscamos en el HashMap
                            int posición = (int) r.getTranslateY();
                            String TextoCombinado = "";
                            switch (posición) {
                                case 156:
                                    for (String text : TextosRectángulos.get("Contacto")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 129:
                                    for (String text : TextosRectángulos.get("Problemas comunes")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 104:
                                    for (String text : TextosRectángulos.get("Sonidos Personalizados")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 78:
                                    for (String text : TextosRectángulos.get("Fondos Personalizados")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 52:
                                    for (String text : TextosRectángulos.get("Opciones")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 26:
                                    for (String text : TextosRectángulos.get("Atajos de teclado")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -1:
                                    for (String text : TextosRectángulos.get("Estructura de la Base de Datos")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -26:
                                    for (String text : TextosRectángulos.get("Ayuda emergente")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;

                                case -52:
                                    for (String text : TextosRectángulos.get("Gráficos")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -77:
                                    for (String text : TextosRectángulos.get("Diálogos")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -104:
                                    for (String text : TextosRectángulos.get("Componentes")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -129:
                                    for (String text : TextosRectángulos.get("Modos")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -154:
                                    for (String text : TextosRectángulos.get("Barra de Menús")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                            }
                            ArrayList<Text> ListPosterior = new ArrayList();
                            ArrayList<Text> TextoConEnlaces = ObtenerPosicionesEnlaces(TextoCombinado, ListPosterior);
                            MostrarTextoFinal(TextoConEnlaces, ListPosterior, tf);
                        }

                    });
                }

            }
        }
        );
        DefinirHandlerSalidaRectángulos(SP, Rectángulos);
    }

    private void AñadirRectángulosModoBásico(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rectángulos = new ArrayList();
        Integer[][] ListadoTamaños = PROPIEDADES_RECTÁNGULOS.MODO_BÁSICO.ListadoTamaños;
        for (int i = 0; i < ListadoTamaños.length; i++) {
            Rectángulos.add(new Rectangle(ListadoTamaños[i][0], ListadoTamaños[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECTÁNGULOS.MODO_BÁSICO.ListadoPosiciones;
        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                for (int númeroRectángulo = 0; númeroRectángulo < Rectángulos.size(); númeroRectángulo++) {
                    Rectangle r = DefinirRectángulo(númeroRectángulo, Rectángulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    AñadirHandlersRectángulos(r, Rectángulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cuál es a partir de la posición y buscamos en el HashMap
                            int posición = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posición) {
                                case -401:
                                    for (String text : TextosRectángulos.get("Botón \"Cambiar tipo de carta\"")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 0:
                                    for (String text : TextosRectángulos.get("Tabla/Área de texto")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -402:
                                    for (String text : TextosRectángulos.get("Botón \"Buscar\"")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -317:
                                    for (String text : TextosRectángulos.get("Lista de checks/Sentencias Grupales")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -318:
                                    for (String text : TextosRectángulos.get("Selección de búsqueda")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -205:
                                    for (String text : TextosRectángulos.get("Sentencia Individual")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                            }
                            ArrayList<Text> ListPosterior = new ArrayList();
                            ArrayList<Text> TextoConEnlaces = ObtenerPosicionesEnlaces(TextoCombinado, ListPosterior);
                            MostrarTextoFinal(TextoConEnlaces, ListPosterior, tf);
                        }

                    });
                }

            }
        }
        );
        DefinirHandlerSalidaRectángulos(SP, Rectángulos);
    }

    private void AñadirRectángulosModoAvanzado(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rectángulos = new ArrayList();
        Integer[][] ListadoTamaños = PROPIEDADES_RECTÁNGULOS.MODO_AVANZADO.ListadoTamaños;
        for (int i = 0; i < ListadoTamaños.length; i++) {
            Rectángulos.add(new Rectangle(ListadoTamaños[i][0], ListadoTamaños[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECTÁNGULOS.MODO_AVANZADO.ListadoPosiciones;
        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                for (int númeroRectángulo = 0; númeroRectángulo < Rectángulos.size(); númeroRectángulo++) {
                    Rectangle r = DefinirRectángulo(númeroRectángulo, Rectángulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    AñadirHandlersRectángulos(r, Rectángulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cuál es a partir de la posición y buscamos en el HashMap
                            int posición = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posición) {
                                case -126:
                                    for (String text : TextosRectángulos.get("Consulta simple")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 0:
                                    for (String text : TextosRectángulos.get("Tabla/Área de texto (modo avanzado)")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 109:
                                case 110:
                                    for (String text : TextosRectángulos.get("Botón \"Buscar\" (modo avanzado)")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -128:
                                    for (String text : TextosRectángulos.get("Consultas de grupo")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                            }
                            ArrayList<Text> ListPosterior = new ArrayList();
                            ArrayList<Text> TextoConEnlaces = ObtenerPosicionesEnlaces(TextoCombinado, ListPosterior);
                            MostrarTextoFinal(TextoConEnlaces, ListPosterior, tf);
                        }

                    });
                }

            }
        }
        );
        DefinirHandlerSalidaRectángulos(SP, Rectángulos);
    }

    private void AñadirRectángulosModoGráficos(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rectángulos = new ArrayList();
        Integer[][] ListadoTamaños = PROPIEDADES_RECTÁNGULOS.MODO_GRÁFICOS.ListadoTamaños;
        for (int i = 0; i < ListadoTamaños.length; i++) {
            Rectángulos.add(new Rectangle(ListadoTamaños[i][0], ListadoTamaños[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECTÁNGULOS.MODO_GRÁFICOS.ListadoPosiciones;
        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                for (int númeroRectángulo = 0; númeroRectángulo < Rectángulos.size(); númeroRectángulo++) {
                    Rectangle r = DefinirRectángulo(númeroRectángulo, Rectángulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    AñadirHandlersRectángulos(r, Rectángulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cuál es a partir de la posición y buscamos en el HashMap
                            int posición = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posición) {
                                case -410:
                                    for (String text : TextosRectángulos.get("Botón \"Cambiar tipo de carta\"")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -402:
                                    for (String text : TextosRectángulos.get("Selección del tipo de gráfico")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -401:
                                    for (String text : TextosRectángulos.get("Botón \"Crear gráfico\"")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -300:
                                    for (String text : TextosRectángulos.get("Sentencia Individual/Sentencias Grupales")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -321:
                                    for (String text : TextosRectángulos.get("C. Gráfico Grupal/C. Gráfico Dispersión")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 11:
                                    for (String text : TextosRectángulos.get("Gráfico")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 424:
                                    for (String text : TextosRectángulos.get("Botón \"Nueva serie\"")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                            }
                            ArrayList<Text> ListPosterior = new ArrayList();
                            ArrayList<Text> TextoConEnlaces = ObtenerPosicionesEnlaces(TextoCombinado, ListPosterior);
                            MostrarTextoFinal(TextoConEnlaces, ListPosterior, tf);
                        }

                    });
                }

            }
        }
        );
        DefinirHandlerSalidaRectángulos(SP, Rectángulos);
    }

    private void AñadirRectángulosBarradeMenús(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rectángulos = new ArrayList();
        Integer[][] ListadoTamaños = PROPIEDADES_RECTÁNGULOS.MENÚ.ListadoTamaños;
        for (int i = 0; i < ListadoTamaños.length; i++) {
            Rectángulos.add(new Rectangle(ListadoTamaños[i][0], ListadoTamaños[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECTÁNGULOS.MENÚ.ListadoPosiciones;

        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                int i = 0;
                for (int númeroRectángulo = 0; númeroRectángulo < Rectángulos.size(); númeroRectángulo++) {
                    Rectangle r = DefinirRectángulo(númeroRectángulo, Rectángulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    AñadirHandlersRectángulos(r, Rectángulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cuál es a partir de la posición y buscamos en el HashMap
                            int posición = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posición) {
                                case -110:
                                    for (String text : TextosRectángulos.get("Submenú \"Archivo\"")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -40:
                                    for (String text : TextosRectángulos.get("Submenú \"Modos\"")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 30:
                                    for (String text : TextosRectángulos.get("Submenú \"Opciones\"")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 100:
                                    for (String text : TextosRectángulos.get("Submenú \"Ayuda\"")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                            }

                            ArrayList<Text> ListPosterior = new ArrayList();
                            ArrayList<Text> TextoConEnlaces = ObtenerPosicionesEnlaces(TextoCombinado, ListPosterior);
                            MostrarTextoFinal(TextoConEnlaces, ListPosterior, tf);
                        }
                    });
                    i++;
                }

            }
        }
        );
        DefinirHandlerSalidaRectángulos(SP, Rectángulos);
    }

    private void AñadirRectángulosGráficodeBarras(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rectángulos = new ArrayList();
        Integer[][] ListadoTamaños = PROPIEDADES_RECTÁNGULOS.GRÁFICO_BARRAS.ListadoTamaños;
        for (int i = 0; i < ListadoTamaños.length; i++) {
            Rectángulos.add(new Rectangle(ListadoTamaños[i][0], ListadoTamaños[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECTÁNGULOS.GRÁFICO_BARRAS.ListadoPosiciones;
        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                for (int númeroRectángulo = 0; númeroRectángulo < Rectángulos.size(); númeroRectángulo++) {
                    Rectangle r = DefinirRectángulo(númeroRectángulo, Rectángulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    AñadirHandlersRectángulos(r, Rectángulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cuál es a partir de la posición y buscamos en el HashMap
                            int posición = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posición) {
                                case -3:
                                    for (String text : TextosRectángulos.get("Legenda")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -485:
                                    for (String text : TextosRectángulos.get("Eje Y")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 3:
                                    for (String text : TextosRectángulos.get("Barras")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 4:
                                    for (String text : TextosRectángulos.get("Categorías")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                            }
                            ArrayList<Text> ListPosterior = new ArrayList();
                            ArrayList<Text> TextoConEnlaces = ObtenerPosicionesEnlaces(TextoCombinado, ListPosterior);
                            MostrarTextoFinal(TextoConEnlaces, ListPosterior, tf);
                        }

                    });
                }

            }
        }
        );
        DefinirHandlerSalidaRectángulos(SP, Rectángulos);
    }

    private void AñadirRectángulosGráficoBarrasApiladas(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rectángulos = new ArrayList();
        Integer[][] ListadoTamaños = PROPIEDADES_RECTÁNGULOS.GRÁFICO_BARRAS_APILADAS.ListadoTamaños;
        for (int i = 0; i < ListadoTamaños.length; i++) {
            Rectángulos.add(new Rectangle(ListadoTamaños[i][0], ListadoTamaños[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECTÁNGULOS.GRÁFICO_BARRAS_APILADAS.ListadoPosiciones;

        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                for (int númeroRectángulo = 0; númeroRectángulo < Rectángulos.size(); númeroRectángulo++) {
                    Rectangle r = DefinirRectángulo(númeroRectángulo, Rectángulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    AñadirHandlersRectángulos(r, Rectángulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cuál es a partir de la posición y buscamos en el HashMap
                            int posición = (int) r.getTranslateX();
                            String TextoCombinado = "";
                            switch (posición) {
                                case -360:
                                    for (String text : TextosRectángulos.get("Eje Y (GBA)")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -2:
                                    for (String text : TextosRectángulos.get("Legenda (GBA)")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 10:
                                    for (String text : TextosRectángulos.get("Barras apiladas")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 11:
                                    for (String text : TextosRectángulos.get("Categorías (GBA)")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                            }
                            ArrayList<Text> ListPosterior = new ArrayList();
                            ArrayList<Text> TextoConEnlaces = ObtenerPosicionesEnlaces(TextoCombinado, ListPosterior);
                            MostrarTextoFinal(TextoConEnlaces, ListPosterior, tf);
                        }

                    });
                }

            }
        }
        );
        DefinirHandlerSalidaRectángulos(SP, Rectángulos);
    }

    private void AñadirRectángulosGráficodeTarta(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rectángulos = new ArrayList();
        Integer[][] ListadoTamaños = PROPIEDADES_RECTÁNGULOS.GRÁFICO_TARTA.ListadoTamaños;
        for (int i = 0; i < ListadoTamaños.length; i++) {
            Rectángulos.add(new Rectangle(ListadoTamaños[i][0], ListadoTamaños[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECTÁNGULOS.GRÁFICO_TARTA.ListadoPosiciones;
        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                for (int númeroRectángulo = 0; númeroRectángulo < Rectángulos.size(); númeroRectángulo++) {
                    Rectangle r = DefinirRectángulo(númeroRectángulo, Rectángulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    AñadirHandlersRectángulos(r, Rectángulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cuál es a partir de la posición y buscamos en el HashMap
                            int posición = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posición) {
                                case 6:
                                    for (String text : TextosRectángulos.get("Etiqueta")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 5:
                                    for (String text : TextosRectángulos.get("Legenda (GT)")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 207:
                                    for (String text : TextosRectángulos.get("Porción")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                            }
                            ArrayList<Text> ListPosterior = new ArrayList();
                            ArrayList<Text> TextoConEnlaces = ObtenerPosicionesEnlaces(TextoCombinado, ListPosterior);
                            MostrarTextoFinal(TextoConEnlaces, ListPosterior, tf);
                        }

                    });
                }

            }
        }
        );
        DefinirHandlerSalidaRectángulos(SP, Rectángulos);
    }

    private void AñadirRectángulosGráficodeDispersión(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rectángulos = new ArrayList();
        Integer[][] ListadoTamaños = PROPIEDADES_RECTÁNGULOS.GRÁFICO_DISPERSIÓN.ListadoTamaños;
        for (int i = 0; i < ListadoTamaños.length; i++) {
            Rectángulos.add(new Rectangle(ListadoTamaños[i][0], ListadoTamaños[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECTÁNGULOS.GRÁFICO_DISPERSIÓN.ListadoPosiciones;
        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                for (int númeroRectángulo = 0; númeroRectángulo < Rectángulos.size(); númeroRectángulo++) {
                    Rectangle r = DefinirRectángulo(númeroRectángulo, Rectángulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    AñadirHandlersRectángulos(r, Rectángulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cuál es a partir de la posición y buscamos en el HashMap
                            int posición = (int) r.getTranslateX();
                            String TextoCombinado = "";
                            switch (posición) {
                                case -480:
                                    for (String text : TextosRectángulos.get("Ejes")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -9:
                                    for (String text : TextosRectángulos.get("Legenda (GD)")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -132:
                                    for (String text : TextosRectángulos.get("Etiquetas emergentes")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 20:
                                    for (String text : TextosRectángulos.get("Ejes")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 284:
                                    for (String text : TextosRectángulos.get("Registros")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                            }
                            ArrayList<Text> ListPosterior = new ArrayList();
                            ArrayList<Text> TextoConEnlaces = ObtenerPosicionesEnlaces(TextoCombinado, ListPosterior);
                            MostrarTextoFinal(TextoConEnlaces, ListPosterior, tf);
                        }

                    });
                }

            }
        }
        );
        DefinirHandlerSalidaRectángulos(SP, Rectángulos);
    }

    private void AñadirRectángulosAyudaemergente(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rectángulos = new ArrayList();
        Integer[][] ListadoTamaños = PROPIEDADES_RECTÁNGULOS.AYUDA_EMERGENTE.ListadoTamaños;
        for (int i = 0; i < ListadoTamaños.length; i++) {
            Rectángulos.add(new Rectangle(ListadoTamaños[i][0], ListadoTamaños[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECTÁNGULOS.AYUDA_EMERGENTE.ListadoPosiciones;
        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                for (int númeroRectángulo = 0; númeroRectángulo < Rectángulos.size(); númeroRectángulo++) {
                    Rectangle r = DefinirRectángulo(númeroRectángulo, Rectángulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    AñadirHandlersRectángulos(r, Rectángulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cuál es a partir de la posición y buscamos en el HashMap
                            int posición = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posición) {
                                case -25:
                                    for (String text : TextosRectángulos.get("Nombre del componente")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -28:
                                    for (String text : TextosRectángulos.get("Enlace a la ayuda")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                            }
                            ArrayList<Text> ListPosterior = new ArrayList();
                            ArrayList<Text> TextoConEnlaces = ObtenerPosicionesEnlaces(TextoCombinado, ListPosterior);
                            MostrarTextoFinal(TextoConEnlaces, ListPosterior, tf);
                        }

                    });
                }

            }
        }
        );
        DefinirHandlerSalidaRectángulos(SP, Rectángulos);
    }

    private void AñadirRectángulosOpciones(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rectángulos = new ArrayList();
        Integer[][] ListadoTamaños = PROPIEDADES_RECTÁNGULOS.DIÁLOGO_OPCIONES.ListadoTamaños;
        for (int i = 0; i < ListadoTamaños.length; i++) {
            Rectángulos.add(new Rectangle(ListadoTamaños[i][0], ListadoTamaños[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECTÁNGULOS.DIÁLOGO_OPCIONES.ListadoPosiciones;

        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                int i = 0;
                for (int númeroRectángulo = 0; númeroRectángulo < Rectángulos.size(); númeroRectángulo++) {
                    Rectangle r = DefinirRectángulo(númeroRectángulo, Rectángulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    AñadirHandlersRectángulos(r, Rectángulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cuál es a partir de la posición y buscamos en el HashMap
                            int posición = (int) r.getTranslateY();

                            String TextoCombinado = "";

                            switch (posición) {
                                case -10:
                                    for (String text : TextosRectángulos.get("Selección de fondo")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 21:
                                    for (String text : TextosRectángulos.get("Activar/desactivar sonidos")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 49:
                                    for (String text : TextosRectángulos.get("Activar/desactivar coloreado en filas")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 77:
                                    for (String text : TextosRectángulos.get("Activar/desactivar ayuda")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;

                            }

                            ArrayList<Text> ListPosterior = new ArrayList();
                            ArrayList<Text> TextoConEnlaces = ObtenerPosicionesEnlaces(TextoCombinado, ListPosterior);
                            MostrarTextoFinal(TextoConEnlaces, ListPosterior, tf);
                        }
                    });
                    i++;
                }
            }
        }
        );
        DefinirHandlerSalidaRectángulos(SP, Rectángulos);
    }

    private void AñadirRectángulosEstructuradelaBBDD(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rectángulos = new ArrayList();
        Integer[][] ListadoTamaños = PROPIEDADES_RECTÁNGULOS.ESTRUCTURA_BBDD.ListadoTamaños;
        for (int i = 0; i < ListadoTamaños.length; i++) {
            Rectángulos.add(new Rectangle(ListadoTamaños[i][0], ListadoTamaños[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECTÁNGULOS.ESTRUCTURA_BBDD.ListadoPosiciones;

        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                int i = 0;
                for (int númeroRectángulo = 0; númeroRectángulo < Rectángulos.size(); númeroRectángulo++) {
                    Rectangle r = DefinirRectángulo(númeroRectángulo, Rectángulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    AñadirHandlersRectángulos(r, Rectángulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cuál es a partir de la posición y buscamos en el HashMap
                            int posición = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posición) {
                                case -208:
                                    for (String text : TextosRectángulos.get("Nombres")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -71:
                                    for (String text : TextosRectángulos.get("Clases")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 68:
                                    for (String text : TextosRectángulos.get("Tribus")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 207:
                                    for (String text : TextosRectángulos.get("Atributos")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -209:
                                    for (String text : TextosRectángulos.get("Habilidades")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -72:
                                    for (String text : TextosRectángulos.get("Rarezas")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 67:
                                    for (String text : TextosRectángulos.get("Mazos")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 206:
                                    for (String text : TextosRectángulos.get("Tipos")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                            }

                            ArrayList<Text> ListPosterior = new ArrayList();
                            ArrayList<Text> TextoConEnlaces = ObtenerPosicionesEnlaces(TextoCombinado, ListPosterior);
                            MostrarTextoFinal(TextoConEnlaces, ListPosterior, tf);
                        }
                    });
                    i++;
                }
            }
        }
        );
        DefinirHandlerSalidaRectángulos(SP, Rectángulos);
    }

    private void AñadirRectángulosSentenciaIndividual(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rectángulos = new ArrayList();
        Integer[][] ListadoTamaños = PROPIEDADES_RECTÁNGULOS.FILTROS_NORMALES.ListadoTamaños;
        for (int i = 0; i < ListadoTamaños.length; i++) {
            Rectángulos.add(new Rectangle(ListadoTamaños[i][0], ListadoTamaños[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECTÁNGULOS.FILTROS_NORMALES.ListadoPosiciones;

        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                int i = 0;
                for (int númeroRectángulo = 0; númeroRectángulo < Rectángulos.size(); númeroRectángulo++) {
                    Rectangle r = DefinirRectángulo(númeroRectángulo, Rectángulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    AñadirHandlersRectángulos(r, Rectángulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cuál es a partir de la posición y buscamos en el HashMap
                            int posición = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posición) {
                                case -15:
                                    for (String text : TextosRectángulos.get("Etiquetas de filtros")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 65:
                                    for (String text : TextosRectángulos.get("Botón \"+\"")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 66:
                                    for (String text : TextosRectángulos.get("Botón \"-\"")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;

                            }

                            ArrayList<Text> ListPosterior = new ArrayList();
                            ArrayList<Text> TextoConEnlaces = ObtenerPosicionesEnlaces(TextoCombinado, ListPosterior);
                            MostrarTextoFinal(TextoConEnlaces, ListPosterior, tf);
                        }
                    });
                    i++;
                }
            }
        }
        );
        DefinirHandlerSalidaRectángulos(SP, Rectángulos);
    }

    private void AñadirRectángulosSentenciasGrupales(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rectángulos = new ArrayList();
        Integer[][] ListadoTamaños = PROPIEDADES_RECTÁNGULOS.FILTROS_GRUPALES.ListadoTamaños;
        for (int i = 0; i < ListadoTamaños.length; i++) {
            Rectángulos.add(new Rectangle(ListadoTamaños[i][0], ListadoTamaños[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECTÁNGULOS.FILTROS_GRUPALES.ListadoPosiciones;

        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                int i = 0;
                for (int númeroRectángulo = 0; númeroRectángulo < Rectángulos.size(); númeroRectángulo++) {
                    Rectangle r = DefinirRectángulo(númeroRectángulo, Rectángulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    AñadirHandlersRectángulos(r, Rectángulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cuál es a partir de la posición y buscamos en el HashMap
                            int posición = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posición) {
                                case -13:
                                    for (String text : TextosRectángulos.get("Etiquetas de sentencias grupales")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -12:
                                    for (String text : TextosRectángulos.get("Botón \"Nuevo\"")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 75:
                                    for (String text : TextosRectángulos.get("Botón \"-\"")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;

                            }

                            ArrayList<Text> ListPosterior = new ArrayList();
                            ArrayList<Text> TextoConEnlaces = ObtenerPosicionesEnlaces(TextoCombinado, ListPosterior);
                            MostrarTextoFinal(TextoConEnlaces, ListPosterior, tf);
                        }
                    });
                    i++;
                }
            }
        }
        );
        DefinirHandlerSalidaRectángulos(SP, Rectángulos);
    }

    private void AñadirRectángulosCGráficoGrupal(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rectángulos = new ArrayList();
        Integer[][] ListadoTamaños = PROPIEDADES_RECTÁNGULOS.CGRÁFICO_GRUPAL.ListadoTamaños;
        for (int i = 0; i < ListadoTamaños.length; i++) {
            Rectángulos.add(new Rectangle(ListadoTamaños[i][0], ListadoTamaños[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECTÁNGULOS.CGRÁFICO_GRUPAL.ListadoPosiciones;

        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                int i = 0;
                for (int númeroRectángulo = 0; númeroRectángulo < Rectángulos.size(); númeroRectángulo++) {
                    Rectangle r = DefinirRectángulo(númeroRectángulo, Rectángulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    AñadirHandlersRectángulos(r, Rectángulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cuál es a partir de la posición y buscamos en el HashMap
                            int posición = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posición) {
                                case -11:
                                    for (String text : TextosRectángulos.get("Selección de función")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -10:
                                    for (String text : TextosRectángulos.get("Selección de variable")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 47:
                                    for (String text : TextosRectángulos.get("Botón \"-\" ")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;

                            }
                            ArrayList<Text> ListPosterior = new ArrayList();
                            ArrayList<Text> TextoConEnlaces = ObtenerPosicionesEnlaces(TextoCombinado, ListPosterior);
                            MostrarTextoFinal(TextoConEnlaces, ListPosterior, tf);
                        }
                    });
                    i++;
                }
            }
        }
        );
        DefinirHandlerSalidaRectángulos(SP, Rectángulos);
    }

    private void AñadirRectángulosCGráficoDispersión(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rectángulos = new ArrayList();
        Integer[][] ListadoTamaños = PROPIEDADES_RECTÁNGULOS.CGRÁFICO_DISPERSIÓN.ListadoTamaños;
        for (int i = 0; i < ListadoTamaños.length; i++) {
            Rectángulos.add(new Rectangle(ListadoTamaños[i][0], ListadoTamaños[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECTÁNGULOS.CGRÁFICO_DISPERSIÓN.ListadoPosiciones;

        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                int i = 0;
                for (int númeroRectángulo = 0; númeroRectángulo < Rectángulos.size(); númeroRectángulo++) {
                    Rectangle r = DefinirRectángulo(númeroRectángulo, Rectángulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    AñadirHandlersRectángulos(r, Rectángulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cuál es a partir de la posición y buscamos en el HashMap
                            int posición = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posición) {
                                case -9:
                                    for (String text : TextosRectángulos.get("Nombre de la serie")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 108:
                                    for (String text : TextosRectángulos.get("Botón \"-\"  ")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -55:
                                    for (String text : TextosRectángulos.get("Selección de eje X")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 62:
                                    for (String text : TextosRectángulos.get("Selección de eje Y")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -56:
                                    for (String text : TextosRectángulos.get("Selección de color")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 61:
                                    for (String text : TextosRectángulos.get("Mostrar/Ocultar serie")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                            }

                            ArrayList<Text> ListPosterior = new ArrayList();
                            ArrayList<Text> TextoConEnlaces = ObtenerPosicionesEnlaces(TextoCombinado, ListPosterior);
                            MostrarTextoFinal(TextoConEnlaces, ListPosterior, tf);
                        }
                    });
                    i++;
                }
            }
        }
        );
        DefinirHandlerSalidaRectángulos(SP, Rectángulos);
    }

    private void AñadirRectángulosDiálogofiltros(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rectángulos = new ArrayList();
        Integer[][] ListadoTamaños = PROPIEDADES_RECTÁNGULOS.DIÁLOGO_FILTROS.ListadoTamaños;
        for (int i = 0; i < ListadoTamaños.length; i++) {
            Rectángulos.add(new Rectangle(ListadoTamaños[i][0], ListadoTamaños[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECTÁNGULOS.DIÁLOGO_FILTROS.ListadoPosiciones;

        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                int i = 0;
                for (int númeroRectángulo = 0; númeroRectángulo < Rectángulos.size(); númeroRectángulo++) {
                    Rectangle r = DefinirRectángulo(númeroRectángulo, Rectángulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    AñadirHandlersRectángulos(r, Rectángulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cuál es a partir de la posición y buscamos en el HashMap
                            int posición = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posición) {
                                case 16:
                                    for (String text : TextosRectángulos.get("Selección de categoría")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 24:
                                    for (String text : TextosRectángulos.get("Selección de relación")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 39:
                                    for (String text : TextosRectángulos.get("Selección de valor")) {

                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 40:
                                    for (String text : TextosRectángulos.get("Selección de relación con condición anterior")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;

                            }

                            ArrayList<Text> ListPosterior = new ArrayList();
                            ArrayList<Text> TextoConEnlaces = ObtenerPosicionesEnlaces(TextoCombinado, ListPosterior);
                            MostrarTextoFinal(TextoConEnlaces, ListPosterior, tf);
                        }
                    });
                    i++;
                }
            }
        }
        );
        DefinirHandlerSalidaRectángulos(SP, Rectángulos);
    }

    private void AñadirRectángulosDiálogoFG(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rectángulos = new ArrayList();
        Integer[][] ListadoTamaños = PROPIEDADES_RECTÁNGULOS.DIÁLOGO_FG.ListadoTamaños;
        for (int i = 0; i < ListadoTamaños.length; i++) {
            Rectángulos.add(new Rectangle(ListadoTamaños[i][0], ListadoTamaños[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECTÁNGULOS.DIÁLOGO_FG.ListadoPosiciones;

        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                int i = 0;
                for (int númeroRectángulo = 0; númeroRectángulo < Rectángulos.size(); númeroRectángulo++) {
                    Rectangle r = DefinirRectángulo(númeroRectángulo, Rectángulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    AñadirHandlersRectángulos(r, Rectángulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cuál es a partir de la posición y buscamos en el HashMap
                            int posición = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posición) {
                                case -2:
                                    for (String text : TextosRectángulos.get("Selección de tipo de función")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -3:
                                    for (String text : TextosRectángulos.get("Selección de tipo de variable")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 38:
                                    for (String text : TextosRectángulos.get("Botón \"Consultar\"")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -21:
                                    for (String text : TextosRectángulos.get("Botón \"Añadir\"")) {

                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 28:
                                    for (String text : TextosRectángulos.get("Introducción de identificador")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                            }

                            ArrayList<Text> ListPosterior = new ArrayList();
                            ArrayList<Text> TextoConEnlaces = ObtenerPosicionesEnlaces(TextoCombinado, ListPosterior);
                            MostrarTextoFinal(TextoConEnlaces, ListPosterior, tf);
                        }
                    });
                    i++;
                }
            }
        }
        );
        DefinirHandlerSalidaRectángulos(SP, Rectángulos);
    }

    private void AñadirRectángulosAtajosdeteclado(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rectángulos = new ArrayList();
        Integer[][] ListadoTamaños = PROPIEDADES_RECTÁNGULOS.ATAJOS_TECLADO.ListadoTamaños;
        for (int i = 0; i < ListadoTamaños.length; i++) {
            Rectángulos.add(new Rectangle(ListadoTamaños[i][0], ListadoTamaños[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECTÁNGULOS.ATAJOS_TECLADO.ListadoPosiciones;
        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {

                for (int númeroRectángulo = 0; númeroRectángulo < Rectángulos.size(); númeroRectángulo++) {
                    Rectangle r = DefinirRectángulo(númeroRectángulo, Rectángulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    AñadirHandlersRectángulos(r, Rectángulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cuál es a partir de la posición y buscamos en el HashMap
                            int posición = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posición) {
                                case -238:
                                    for (String text : TextosRectángulos.get("Atajos de la Barra de Menús")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -79:
                                    for (String text : TextosRectángulos.get("Atajos del Modo Básico")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 80:
                                    for (String text : TextosRectángulos.get("Atajos del Modo Avanzado")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 239:
                                    for (String text : TextosRectángulos.get("Atajos del Modo Gráficos")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                            }
                            ArrayList<Text> ListPosterior = new ArrayList();
                            ArrayList<Text> TextoConEnlaces = ObtenerPosicionesEnlaces(TextoCombinado, ListPosterior);
                            MostrarTextoFinal(TextoConEnlaces, ListPosterior, tf);
                        }

                    });
                }

            }
        }
        );
        DefinirHandlerSalidaRectángulos(SP, Rectángulos);
    }

    private void AñadirRectángulosFondosPersonalizados(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rectángulos = new ArrayList();
        Integer[][] ListadoTamaños = PROPIEDADES_RECTÁNGULOS.FONDOS_PERSONALIZADOS.ListadoTamaños;
        for (int i = 0; i < ListadoTamaños.length; i++) {
            Rectángulos.add(new Rectangle(ListadoTamaños[i][0], ListadoTamaños[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECTÁNGULOS.FONDOS_PERSONALIZADOS.ListadoPosiciones;

        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                int i = 0;
                for (int númeroRectángulo = 0; númeroRectángulo < Rectángulos.size(); númeroRectángulo++) {
                    Rectangle r = DefinirRectángulo(númeroRectángulo, Rectángulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    AñadirHandlersRectángulos(r, Rectángulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cuál es a partir de la posición y buscamos en el HashMap
                            int posición = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posición) {
                                case 0:
                                    for (String text : TextosRectángulos.get("Procedimiento")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                            }

                            ArrayList<Text> ListPosterior = new ArrayList();
                            ArrayList<Text> TextoConEnlaces = ObtenerPosicionesEnlaces(TextoCombinado, ListPosterior);
                            MostrarTextoFinal(TextoConEnlaces, ListPosterior, tf);
                        }
                    });
                    i++;
                }
            }
        }
        );
        DefinirHandlerSalidaRectángulos(SP, Rectángulos);
    }

    private void AñadirRectángulosSonidosPersonalizados(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rectángulos = new ArrayList();
        Integer[][] ListadoTamaños = PROPIEDADES_RECTÁNGULOS.SONIDOS_PERSONALIZADOS.ListadoTamaños;
        for (int i = 0; i < ListadoTamaños.length; i++) {
            Rectángulos.add(new Rectangle(ListadoTamaños[i][0], ListadoTamaños[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECTÁNGULOS.SONIDOS_PERSONALIZADOS.ListadoPosiciones;

        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                int i = 0;
                for (int númeroRectángulo = 0; númeroRectángulo < Rectángulos.size(); númeroRectángulo++) {
                    Rectangle r = DefinirRectángulo(númeroRectángulo, Rectángulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    AñadirHandlersRectángulos(r, Rectángulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cuál es a partir de la posición y buscamos en el HashMap
                            int posición = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posición) {
                                case 0:
                                    for (String text : TextosRectángulos.get("Pasos")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                            }

                            ArrayList<Text> ListPosterior = new ArrayList();
                            ArrayList<Text> TextoConEnlaces = ObtenerPosicionesEnlaces(TextoCombinado, ListPosterior);
                            MostrarTextoFinal(TextoConEnlaces, ListPosterior, tf);
                        }
                    });
                    i++;
                }
            }
        }
        );
        DefinirHandlerSalidaRectángulos(SP, Rectángulos);
    }

    private void AñadirRectángulosProblemascomunes(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rectángulos = new ArrayList();
        Integer[][] ListadoTamaños = PROPIEDADES_RECTÁNGULOS.PROBLEMAS_COMUNES.ListadoTamaños;
        for (int i = 0; i < ListadoTamaños.length; i++) {
            Rectángulos.add(new Rectangle(ListadoTamaños[i][0], ListadoTamaños[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECTÁNGULOS.PROBLEMAS_COMUNES.ListadoPosiciones;
        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {

                for (int númeroRectángulo = 0; númeroRectángulo < Rectángulos.size(); númeroRectángulo++) {
                    Rectangle r = DefinirRectángulo(númeroRectángulo, Rectángulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    AñadirHandlersRectángulos(r, Rectángulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cuál es a partir de la posición y buscamos en el HashMap
                            int posición = (int) r.getTranslateY();

                            String TextoCombinado = "";
                            switch (posición) {
                                case -65:
                                    for (String text : TextosRectángulos.get("Diálogo \"Problema en la carga de sonido\"")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -33:
                                    for (String text : TextosRectángulos.get("Diálogo \"Nueva imagen añadida\"")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -1:
                                    for (String text : TextosRectángulos.get("Diálogo \"Sin fondos\"")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;

                                case 32:
                                    for (String text : TextosRectángulos.get("La aplicación tiene un fondo azul genérico")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 64:
                                    for (String text : TextosRectángulos.get("El sistema de ayuda no se abre desde la aplicación")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                            }
                            ArrayList<Text> ListPosterior = new ArrayList();
                            ArrayList<Text> TextoConEnlaces = ObtenerPosicionesEnlaces(TextoCombinado, ListPosterior);
                            MostrarTextoFinal(TextoConEnlaces, ListPosterior, tf);
                        }

                    });
                }

            }
        }
        );
        DefinirHandlerSalidaRectángulos(SP, Rectángulos);
    }

    private void DefinirHandlerSalidaRectángulos(StackPane SP, ArrayList<Rectangle> Rectángulos) {
        SP.setOnMouseExited(new EventHandler() {
            @Override
            public void handle(Event event
            ) {
                for (Rectangle r : Rectángulos) {
                    SP.getChildren().remove(r);
                }
            }
        }
        );
    }

    private Rectangle DefinirRectángulo(int númeroRectángulo, ArrayList<Rectangle> Rectángulos, Integer[][] ListadoPosiciones) {
        Rectangle r = Rectángulos.get(númeroRectángulo);
        r.setOpacity(OPACIDAD_MEDIA);
        r.setStyle("-fx-fill: blue;");
        r.setTranslateX(ListadoPosiciones[númeroRectángulo][0]);
        r.setTranslateY(ListadoPosiciones[númeroRectángulo][1]);
        return r;
    }

    /**
     * Genera el encabezado, obtiene las posiciones de los enlaces del TextFlow
     * y devuelve un ArrayList con los distintos Text ordenados (encabezado y
     * cuerpo). Esta ordenación permitirá que se pueda inferir cuáles
     * corresponden a enlaces y cuáles no.
     *
     * @param TextoCombinado El texto completo asociado al botón que se ha
     * pulsado
     * @param ListOrdenado El ArrayList que incluirá Text ordenados.
     * @return ArrayList<Text> con los Text ordenados.
     */
    private ArrayList<Text> ObtenerPosicionesEnlaces(String TextoCombinado, ArrayList<Text> ListOrdenado) {
        String[] EncabezadoYResto = TextoCombinado.split("\n", 2);
        Text encabezado = new Text(EncabezadoYResto[0]);
        Text cuerpo = new Text(EncabezadoYResto[1]);
        encabezado.setStyle("-fx-font-size: 30 px;"
                + "-fx-font-weight: bold;");
        ListOrdenado.add(encabezado);
        ListOrdenado.add(new Text("\n"));
        ListOrdenado.add(cuerpo);

        // Contea el número de coincidencias que se tendrán que convertir en enlaces
        int coincidencias = 0;
        for (String ContenidoPrincipal : CONTENIDOS_PRINCIPALES) {
            coincidencias = coincidencias + ContarCoincidenciasEnString(cuerpo.getText(), ContenidoPrincipal);
        }

        if (coincidencias > 0) {
            ListOrdenado.remove(cuerpo);
            // Determina la posición de los enlaces en el String completo y las guarda en un HashMap
            HashMap<Integer, String> PosiciónImagen = new HashMap();
            for (String palabra : CONTENIDOS_PRINCIPALES) {
                String textoRecortado = cuerpo.getText();
                int recortado = 0;
                while (textoRecortado.contains(palabra)) {
                    int posiciónPalabra = textoRecortado.indexOf(palabra);
                    recortado = cuerpo.getText().length() - textoRecortado.length();
                    textoRecortado = textoRecortado.substring(posiciónPalabra + palabra.length());
                    PosiciónImagen.put(posiciónPalabra + recortado, palabra);
                }
            }

            // Crea una lista con todas los pares de valores (entradas) del mapa
            List<Map.Entry<Integer, String>> lista
                    = new LinkedList<>(PosiciónImagen.entrySet());

            // Ordena la lista en función de las key de las entradas (las posiciones)
            Collections.sort(lista, new Comparator<Map.Entry<Integer, String>>() {
                @Override
                public int compare(Map.Entry<Integer, String> entrada1,
                        Map.Entry<Integer, String> entrada2) {
                    return (entrada1.getKey()).compareTo(entrada2.getKey());
                }
            });

            // Utilizamos la lista con el tipo de imagen y su posición para ir recorriendo el String y creando los nodos
            int inicioImagen = 0;
            int recortado = 0;
            String textoRecortado = cuerpo.getText();
            for (Map.Entry<Integer, String> entrada : lista) {
                // La posición de inicio del enlace  ha de tener en cuenta que el String se va recortando
                inicioImagen = entrada.getKey() - recortado;

                ListOrdenado.add(new Text(textoRecortado.substring(0, inicioImagen)));
                ListOrdenado.add(new Text(entrada.getValue()));

                // El String de la habilidad se va recortando para ir avanzando en la parte que se compara
                textoRecortado = textoRecortado.substring(inicioImagen + entrada.getValue().length());
                recortado = cuerpo.getText().length() - textoRecortado.length();
                coincidencias--;
                // Cuando no quedan coincidencias, añadimos el trozo de texto restante
                if (coincidencias == 0) {
                    ListOrdenado.add(new Text(textoRecortado));
                    break;
                }

            }
            return ListOrdenado;
        } else {
            return null;
        }

    }

    /**
     * Añade handlers de entrada y salida del cursor a los rectángulos para
     * cambiar su opacidad.
     *
     * @param r Rectángulo concreto en el que se entra o sale.
     * @param Rectángulos Todos los rectángulos de la imagen. disminuye
     */
    private void AñadirHandlersRectángulos(Rectangle r, ArrayList<Rectangle> Rectángulos) {
        r.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                r.setOpacity(OPACIDAD_ALTA);
                for (Rectangle rec : Rectángulos) {
                    if (!rec.equals(r)) {
                        rec.setOpacity(OPACIDAD_MÍNIMA);
                    }
                }
            }
        });
        r.setOnMouseExited(new EventHandler() {
            @Override
            public void handle(Event event) {
                for (Rectangle rec : Rectángulos) {
                    rec.setOpacity(OPACIDAD_MEDIA);
                }
            }
        });
    }

    /**
     * Cuenta el número de coincidencias de una palabra dentro de un string
     *
     * @param string El string a analizar
     * @param palabra La palabra que se busca
     * @return Entero con el número de coincidencias
     */
    private int ContarCoincidenciasEnString(String string, String palabra) {
        int posición = string.indexOf(palabra);
        int coincidencias = 0;
        while (posición != -1) {
            coincidencias++;
            string = string.substring(posición + 1);
            posición = string.indexOf(palabra);
        }
        return coincidencias;
    }

    /**
     * Define el texto a mostrar cuando se selecciona un hipervínculo o un
     * rectángulo, dando un tratamiento diferente según si contiene o no
     * enlaces.
     *
     * @param TextoConEnlaces ArrayList<Text> obtenido del método
     * ObtenerPosicionesEnlaces. Se utiliza en caso de que no sea "null" y, por
     * tanto, se hayan detectado enlaces.
     * @param ListAlternativa ArrayList<Text> que se utiliza cuando
     * TextoConEnlaces es "null" y, por tanto, no se han detectado enlaces.
     * @param tf El TextFlow que contendrá TextoConEnlaces o SistemaDeAyuda
     */
    private void MostrarTextoFinal(ArrayList<Text> TextoConEnlaces, ArrayList<Text> ListAlternativa, TextFlow tf) {
        if (TextoConEnlaces != null) {
            TextosPosteriores.add(TextoConEnlaces);
            tf.getChildren().addAll(TextoConEnlaces);

            // Los enlaces ocuparán posiciones 3, 5, 7....luego (númeroRectángulo-2)%2=0
            for (int i = 0; i < TextoConEnlaces.size(); i++) {
                if (i != 0 && ((i - 1) % 2 != 0)) {
                    TextoConEnlaces.get(i).setStyle("-fx-font-size: 20 px;");
                } else if ((i - 1) % 2 == 0) {
                    TextoConEnlaces.get(i).setStyle("-fx-font-size: 20 px;"
                            + "-fx-fill: blue;");
                    AñadirHandlersEnlaces(TextoConEnlaces.get(i));
                } else if (i == 0) {
                    TextoConEnlaces.get(i).setStyle("-fx-font-size: 40 px; -fx-font-weight: bold;");
                }
            }
        } else {
            TextosPosteriores.add(ListAlternativa);
            ListAlternativa.add(ListAlternativa.size(), new Text("\n"));
            tf.getChildren().addAll(ListAlternativa);

            for (int i = 0; i < ListAlternativa.size(); i++) {
                if (i != 0 && i != 3) {
                    ListAlternativa.get(i).setStyle("-fx-font-size: 20 px;");
                } else if (i == 0) {
                    ListAlternativa.get(i).setStyle("-fx-font-size: 40 px; -fx-font-weight: bold;");
                }
            }
        }
    }

    private void AñadirHandlersEnlaces(Text TextoLink) {
        TextoLink.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                TextoLink.setOpacity(0.5);
                TextoLink.setStyle("-fx-underline: true;"
                        + "-fx-font-size: 20 px;");
            }
        });

        TextoLink.setOnMouseExited(new EventHandler() {
            @Override
            public void handle(Event event) {
                TextoLink.setOpacity(1.0);
                TextoLink.setStyle("-fx-underline: false;"
                        + "-fx-font-size: 20 px;");
            }
        });
        TextoLink.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                for (Node nodo : Vbox.getChildren()) {
                    if (nodo instanceof Hyperlink && ((Hyperlink) nodo).getText().equals(TextoLink.getText())) {
                        ((Hyperlink) nodo).fire();
                    }
                }
            }
        });
        TextoLink.setFill(javafx.scene.paint.Color.BLUE);
    }
}
