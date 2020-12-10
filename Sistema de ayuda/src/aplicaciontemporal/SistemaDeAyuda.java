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

    private final String[] CONTENIDOS_PRINCIPALES = {"Temas por categor�a", "Barra de Men�s", "Modo B�sico",
        "Modo Avanzado", "Modo Gr�ficos", "Sentencia Individual", "Sentencias Grupales", "C. Gr�fico Grupal",
        "C. Gr�fico Dispersi�n", "Di�logo filtros", "Di�logo FG", "Gr�fico de Barras", "Gr�fico Barras Apiladas",
        "Gr�fico de Tarta", "Gr�fico de Dispersi�n", "Ayuda emergente", "Estructura de la BBDD", "Atajos de teclado",
        "Opciones", "Fondos Personalizados", "Sonidos Personalizados", "Problemas comunes", "Contacto"};
    private final double POSICI�N_INICIAL_DIVIDER = 0.185;
    private final double TAMA�O_M�NIMO_PANEL_IZQUIERDA = 0.185;
    private final double TAMA�O_M�XIMO_PANEL_IZQUIERDA = 0.185;
    private final double OPACIDAD_M�NIMA = 0.1;
    private final double OPACIDAD_MEDIA = 0.3;
    private final double OPACIDAD_ALTA = 0.6;

    private VBox Vbox;

    ArrayList<Image> Imagengarg = new ArrayList();

    private ArrayList<ArrayList<Text>> TextosPosteriores = new ArrayList();
    private final HashMap<String, ArrayList<String>> TextosRect�ngulos = new LinkedHashMap();

    private enum PROPIEDADES_RECT�NGULOS {
        TEMAS("Temas"),
        MEN�("Men�"),
        MODO_B�SICO("B�sico"),
        MODO_AVANZADO("Avanzado"),
        MODO_GR�FICOS("Gr�ficos"),
        DI�LOGO_FILTROS("Di�logo Filtros"),
        DI�LOGO_FG("Di�logo FG"),
        FILTROS_NORMALES("Sentencia Individual"),
        FILTROS_GRUPALES("Sentencias Grupales"),
        CGR�FICO_GRUPAL("CGr�fico Grupal"),
        CGR�FICO_DISPERSI�N("CGr�fico Dispersi�n"),
        GR�FICO_BARRAS("Gr�fico de Barras"),
        GR�FICO_BARRAS_APILADAS("Gr�fico Barras Apiladas"),
        GR�FICO_TARTA("Gr�fico de Tarta"),
        GR�FICO_DISPERSI�N("Gr�fico de Dispersi�n"),
        AYUDA_EMERGENTE("Ayuda emergente"),
        DI�LOGO_OPCIONES("Opciones"),
        ESTRUCTURA_BBDD("Estructura de la BBDD"),
        ATAJOS_TECLADO("Atajos de teclado"),
        FONDOS_PERSONALIZADOS("Fondos Personalizados"),
        SONIDOS_PERSONALIZADOS("Sonidos Personalizados"),
        PROBLEMAS_COMUNES("Problemas comunes");

        Integer[][] ListadoPosiciones;
        Integer[][] ListadoTama�os;

        PROPIEDADES_RECT�NGULOS(String Pantalla) {
            switch (Pantalla) {
                case "Temas":
                    this.ListadoPosiciones = new Integer[][]{{125, 26}, {125, 52}, {125, 78}, {125, 104}, {125, 129}, {125, 156}, {125, -1}, {125, -26}, {125, -52}, {125, -77}, {125, -104}, {125, -129}, {125, -154}};
                    this.ListadoTama�os = new Integer[][]{{239, 25}, {239, 25}, {239, 25}, {239, 25}, {239, 25}, {239, 25}, {239, 25}, {239, 25}, {239, 25}, {239, 25}, {239, 25}, {239, 25}, {239, 25}};
                    break;
                case "B�sico":
                    this.ListadoPosiciones = new Integer[][]{{-401, -168}, {0, 80}, {-402, -122}, {-317, -118}, {-318, -186}, {-205, -175}};
                    this.ListadoTama�os = new Integer[][]{{56, 50}, {860, 282}, {56, 17}, {98, 102}, {98, 20}, {113, 18}};
                    break;
                case "Gr�ficos":
                    this.ListadoPosiciones = new Integer[][]{{-410, -163}, {-401, -80}, {-402, -98}, {-321, -175}, {-300, -69}, {11, 79}, {424, -155}};
                    this.ListadoTama�os = new Integer[][]{{56, 50}, {75, 17}, {75, 17}, {74, 36}, {116, 75}, {860, 203}, {50, 17}};
                    break;
                case "Avanzado":
                    this.ListadoPosiciones = new Integer[][]{{-126, -179}, {0, 80}, {-128, -114}, {110, -113}, {109, -180}};
                    this.ListadoTama�os = new Integer[][]{{400, 17}, {860, 282}, {395, 80}, {52, 17}, {52, 17}};
                    break;
                case "Men�":
                    this.ListadoPosiciones = new Integer[][]{{-110, 0}, {-40, 0}, {30, 0}, {100, 0}};
                    this.ListadoTama�os = new Integer[][]{{60, 25}, {60, 25}, {60, 25}, {60, 25}};
                    break;
                case "Sentencia Individual":
                    this.ListadoPosiciones = new Integer[][]{{-15, -9}, {66, 0}, {65, 50}};
                    this.ListadoTama�os = new Integer[][]{{129, 100}, {25, 25}, {25, 25}};
                    break;
                case "Sentencias Grupales":
                    this.ListadoPosiciones = new Integer[][]{{-13, -27}, {75, -2}, {-12, 39}};
                    this.ListadoTama�os = new Integer[][]{{150, 75}, {21, 25}, {150, 25}};
                    break;
                case "CGr�fico Grupal":
                    this.ListadoPosiciones = new Integer[][]{{-11, -13}, {47, -13}, {-10, 12}};
                    this.ListadoTama�os = new Integer[][]{{93, 25}, {21, 25}, {93, 26}};
                    break;
                case "CGr�fico Dispersi�n":
                    this.ListadoPosiciones = new Integer[][]{{-9, -26}, {108, -26}, {-55, 0}, {62, 0}, {-56, 24}, {61, 24}};
                    this.ListadoTama�os = new Integer[][]{{212, 25}, {20, 25}, {119, 26}, {115, 26}, {117, 26}, {117, 26}};
                    break;
                case "Di�logo Filtros":
                    this.ListadoPosiciones = new Integer[][]{{16, -40}, {24, -6}, {39, 30}, {40, 60}};
                    this.ListadoTama�os = new Integer[][]{{107, 25}, {128, 25}, {153, 25}, {159, 25}};
                    break;
                case "Di�logo FG":
                    this.ListadoPosiciones = new Integer[][]{{-2, -57}, {-3, -22}, {38, 13}, {-21, 13}, {28, 48}};
                    this.ListadoTama�os = new Integer[][]{{90, 25}, {90, 25}, {66, 25}, {50, 25}, {149, 25}};
                    break;
                case "Gr�fico de Barras":
                    this.ListadoPosiciones = new Integer[][]{{-485, -25}, {-3, 128}, {3, 22}, {4, 100}};
                    this.ListadoTama�os = new Integer[][]{{25, 233}, {230, 24}, {100, 131}, {51, 20}};
                    break;
                case "Gr�fico Barras Apiladas":
                    this.ListadoPosiciones = new Integer[][]{{-360, -32}, {-2, 176}, {10, 35}, {11, 142}};
                    this.ListadoTama�os = new Integer[][]{{25, 322}, {213, 30}, {222, 172}, {60, 20}};
                    break;
                case "Gr�fico de Tarta":
                    this.ListadoPosiciones = new Integer[][]{{6, -32}, {5, 162}, {207, 50}};
                    this.ListadoTama�os = new Integer[][]{{57, 28}, {570, 30}, {150, 30}};
                    break;
                case "Gr�fico de Dispersi�n":
                    this.ListadoPosiciones = new Integer[][]{{-480, -42}, {-9, 176}, {-132, -85}, {20, 122}, {284, -37}};
                    this.ListadoTama�os = new Integer[][]{{55, 300}, {280, 30}, {116, 120}, {958, 65}, {15, 15}};
                    break;
                case "Ayuda emergente":
                    this.ListadoPosiciones = new Integer[][]{{-25, -38}, {-28, -20}};
                    this.ListadoTama�os = new Integer[][]{{93, 19}, {80, 22}};
                    break;
                case "Opciones":
                    this.ListadoPosiciones = new Integer[][]{{140, -10}, {137, 21}, {137, 49}, {137, 77}};
                    this.ListadoTama�os = new Integer[][]{{80, 25}, {34, 20}, {34, 20}, {34, 20}};
                    break;
                case "Estructura de la BBDD":
                    this.ListadoPosiciones = new Integer[][]{{-208, -15}, {-71, -15}, {68, -15}, {207, -15}, {-209, 12}, {-72, 12}, {67, 12}, {206, 12}};
                    this.ListadoTama�os = new Integer[][]{{138, 28}, {139, 28}, {139, 28}, {139, 28}, {139, 28}, {139, 28}, {139, 28}, {139, 28}};
                    break;
                case "Atajos de teclado":
                    this.ListadoPosiciones = new Integer[][]{{-238, 0}, {-79, 0}, {80, 0}, {239, 0}};
                    this.ListadoTama�os = new Integer[][]{{159, 35}, {159, 35}, {159, 35}, {159, 35}};
                    break;
                case "Fondos Personalizados":
                    this.ListadoPosiciones = new Integer[][]{{0, 0}};
                    this.ListadoTama�os = new Integer[][]{{358, 430}};
                    break;
                case "Sonidos Personalizados":
                    this.ListadoPosiciones = new Integer[][]{{0, 0}};
                    this.ListadoTama�os = new Integer[][]{{359, 302}};
                    break;
                case "Problemas comunes":
                    this.ListadoPosiciones = new Integer[][]{{0, -65}, {0, -33}, {0, -1}, {0, 32}, {0, 64}};
                    this.ListadoTama�os = new Integer[][]{{690, 32}, {690, 32}, {690, 32}, {690, 32}, {690, 32}};
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

                // AQUI HABR�A QUE COPIAR TODO EL TEXTO //
                try {
                    String l�nea;
                    int N�meroTexto = -1;
                    while ((l�nea = br.readLine()) != null) {
                        /* "***" ejerce de separador, primero entre el primer TextFlow y la imagen y despu�s
                        entre los distintos TextFlow que se generan al pulsar los rect�ngulos. Cada vez que se
                        detecta un "***", se genera un arrayList al que se le a�ade la siguiente l�nea, as� como
                        una entrada de HashMap que permitir�� acceder a dicho ArrayList. Cada l�nea que no sea
                        la inmediatamente posterior a "***" ser� a�adida a su ArrayList correspondiente, que es precisamente
                        el que corresponde a la �ltima entrada del mapa*/
                        // ACLARAR QUE NO UTILIZAMOS SIMPLEMENTE TEXT POR SI HAY DISTINTAS L�NEAS PARA UN MISMO TEXTFLOW

                        if (l�nea.equals("***")) {
                            ArrayList<String> nuevaOpci�n = new ArrayList();
                            String nombreOpci�n = br.readLine();
                            nuevaOpci�n.add(nombreOpci�n);
                            if (!TextosRect�ngulos.containsKey(nombreOpci�n)) {
                                TextosRect�ngulos.put(nombreOpci�n, nuevaOpci�n);
                            }
                            N�meroTexto++;
                        } else if (N�meroTexto == -1) {
                            TextoPrevioImagen.add(l�nea);
                        } else {
                            // Obtener �ltima Key
                            int x = 1;
                            String �ltimaKey = "";
                            for (String key : TextosRect�ngulos.keySet()) {
                                if (x == TextosRect�ngulos.keySet().size()) {
                                    �ltimaKey = key;
                                }
                                x++;
                            }
                            TextosRect�ngulos.get(�ltimaKey).add(l�nea);

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

            // Se procesa el texto obtenido para a�adir enlaces
            final TextFlow tf = new TextFlow();
            final TextFlow tf2 = new TextFlow();

            // A�ade los enlaces y el formato al TextFlow superior
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

            // Se a�ade la imagen, los rect�ngulos asociadas a la misma y la l�gica de ambos
            StackPane SP = new StackPane();
            ImageView iv = A�adirImagen(Contenido);
            Platform.runLater(() -> SP.setMaxWidth(iv.getFitWidth()));
            String ContenidoSinEspacio = Contenido.replace(" ", "").replace(".", "");
            if (!ContenidoSinEspacio.equals("Contacto")) {
                Method m�todo = this.getClass().getDeclaredMethod("A�adirRect�ngulos"
                        + ContenidoSinEspacio, StackPane.class, TextFlow.class);
                m�todo.invoke(this, SP, tf2);
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

        /* Se crea un SplitPane y dos ScrollPane, se vincula la posici�n del delimitador al ScrollIzquierdo 
        y se a�aden los respectivos VBox a sus ScrollPane */
        SplitPane SP = new SplitPane();
        ScrollPane ScrollIzquierdo = new ScrollPane();
        ScrollIzquierdo.setContent(Vbox);
        ScrollIzquierdo.maxWidthProperty().bind(SP.widthProperty().multiply(TAMA�O_M�NIMO_PANEL_IZQUIERDA));
        ScrollIzquierdo.minWidthProperty().bind(SP.widthProperty().multiply(TAMA�O_M�XIMO_PANEL_IZQUIERDA));

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

        /* Se a�aden las dos mitades al SplitPane y se pulsa el primer hiperv�nculo 
        para que ejerza de pantalla de inicio*/
        SP.setDividerPositions(POSICI�N_INICIAL_DIVIDER);
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
        MostrarDi�logoBienvenida();
    }

    private void MostrarDi�logoBienvenida() {
        Dialog d = new Dialog();
        d.setTitle("Ayuda");
        d.setHeaderText("Ayuda de PVZ H�roes Stats");
        d.setContentText("Bienvenido a la ayuda de PVZ H�roes Stats. Pulsa en los v�nculos de la parte izquierda "
                + "para acceder a las diferentes secciones y, una vez en ellas, en las �reas de las "
                + "im�genes para examinar sus componentes. Puesto que el programa se termin� tras el programa de ayuda, "
                + "es posible que haya ligeras discrepancias de formato entre las im�genes mostradas y la apariencia real");
        d.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("recursosAyuda/di�logos/Gargologistpeque�o.png"))));
        Stage stage = (Stage) d.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("recursosAyuda/di�logos/plantspeque�o.png")));
        d.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        Optional<ButtonType> confirmaci�n = d.showAndWait();
    }

    private ImageView A�adirImagen(String nombreArchivo) {
        ImageView iv = null;
        try {
            iv = new ImageView(new Image(getClass().getResourceAsStream("recursosAyuda/im�genes/" + nombreArchivo.replace(" ", "") + ".JPG")));
        } catch (NullPointerException e) {
            iv = new ImageView(new Image(getClass().getResourceAsStream("recursosAyuda/im�genes/" + nombreArchivo.replace(" ", "") + ".GIF")));
        }
        iv.setPreserveRatio(true);
        if (nombreArchivo.equals("Barra de Men�s")) {
            iv.setFitWidth(300);
        } else if (nombreArchivo.equals("Modo B�sico") || nombreArchivo.equals("Modo Avanzado") || nombreArchivo.equals("Modo Gr�ficos")) {
            iv.setFitWidth(900);
        } else if (nombreArchivo.equals("Gr�fico de Barras")) {
            iv.setFitWidth(1000);
        }
        return iv;
    }

    private void A�adirRect�ngulosTemasporcategor�a(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rect�ngulos = new ArrayList();
        Integer[][] ListadoTama�os = PROPIEDADES_RECT�NGULOS.TEMAS.ListadoTama�os;
        for (int i = 0; i < ListadoTama�os.length; i++) {
            Rect�ngulos.add(new Rectangle(ListadoTama�os[i][0], ListadoTama�os[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECT�NGULOS.TEMAS.ListadoPosiciones;
        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                for (int n�meroRect�ngulo = 0; n�meroRect�ngulo < Rect�ngulos.size(); n�meroRect�ngulo++) {
                    Rectangle r = DefinirRect�ngulo(n�meroRect�ngulo, Rect�ngulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    A�adirHandlersRect�ngulos(r, Rect�ngulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cu�l es a partir de la posici�n y buscamos en el HashMap
                            int posici�n = (int) r.getTranslateY();
                            String TextoCombinado = "";
                            switch (posici�n) {
                                case 156:
                                    for (String text : TextosRect�ngulos.get("Contacto")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 129:
                                    for (String text : TextosRect�ngulos.get("Problemas comunes")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 104:
                                    for (String text : TextosRect�ngulos.get("Sonidos Personalizados")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 78:
                                    for (String text : TextosRect�ngulos.get("Fondos Personalizados")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 52:
                                    for (String text : TextosRect�ngulos.get("Opciones")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 26:
                                    for (String text : TextosRect�ngulos.get("Atajos de teclado")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -1:
                                    for (String text : TextosRect�ngulos.get("Estructura de la Base de Datos")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -26:
                                    for (String text : TextosRect�ngulos.get("Ayuda emergente")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;

                                case -52:
                                    for (String text : TextosRect�ngulos.get("Gr�ficos")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -77:
                                    for (String text : TextosRect�ngulos.get("Di�logos")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -104:
                                    for (String text : TextosRect�ngulos.get("Componentes")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -129:
                                    for (String text : TextosRect�ngulos.get("Modos")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -154:
                                    for (String text : TextosRect�ngulos.get("Barra de Men�s")) {
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
        DefinirHandlerSalidaRect�ngulos(SP, Rect�ngulos);
    }

    private void A�adirRect�ngulosModoB�sico(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rect�ngulos = new ArrayList();
        Integer[][] ListadoTama�os = PROPIEDADES_RECT�NGULOS.MODO_B�SICO.ListadoTama�os;
        for (int i = 0; i < ListadoTama�os.length; i++) {
            Rect�ngulos.add(new Rectangle(ListadoTama�os[i][0], ListadoTama�os[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECT�NGULOS.MODO_B�SICO.ListadoPosiciones;
        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                for (int n�meroRect�ngulo = 0; n�meroRect�ngulo < Rect�ngulos.size(); n�meroRect�ngulo++) {
                    Rectangle r = DefinirRect�ngulo(n�meroRect�ngulo, Rect�ngulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    A�adirHandlersRect�ngulos(r, Rect�ngulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cu�l es a partir de la posici�n y buscamos en el HashMap
                            int posici�n = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posici�n) {
                                case -401:
                                    for (String text : TextosRect�ngulos.get("Bot�n \"Cambiar tipo de carta\"")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 0:
                                    for (String text : TextosRect�ngulos.get("Tabla/�rea de texto")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -402:
                                    for (String text : TextosRect�ngulos.get("Bot�n \"Buscar\"")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -317:
                                    for (String text : TextosRect�ngulos.get("Lista de checks/Sentencias Grupales")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -318:
                                    for (String text : TextosRect�ngulos.get("Selecci�n de b�squeda")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -205:
                                    for (String text : TextosRect�ngulos.get("Sentencia Individual")) {
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
        DefinirHandlerSalidaRect�ngulos(SP, Rect�ngulos);
    }

    private void A�adirRect�ngulosModoAvanzado(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rect�ngulos = new ArrayList();
        Integer[][] ListadoTama�os = PROPIEDADES_RECT�NGULOS.MODO_AVANZADO.ListadoTama�os;
        for (int i = 0; i < ListadoTama�os.length; i++) {
            Rect�ngulos.add(new Rectangle(ListadoTama�os[i][0], ListadoTama�os[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECT�NGULOS.MODO_AVANZADO.ListadoPosiciones;
        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                for (int n�meroRect�ngulo = 0; n�meroRect�ngulo < Rect�ngulos.size(); n�meroRect�ngulo++) {
                    Rectangle r = DefinirRect�ngulo(n�meroRect�ngulo, Rect�ngulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    A�adirHandlersRect�ngulos(r, Rect�ngulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cu�l es a partir de la posici�n y buscamos en el HashMap
                            int posici�n = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posici�n) {
                                case -126:
                                    for (String text : TextosRect�ngulos.get("Consulta simple")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 0:
                                    for (String text : TextosRect�ngulos.get("Tabla/�rea de texto (modo avanzado)")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 109:
                                case 110:
                                    for (String text : TextosRect�ngulos.get("Bot�n \"Buscar\" (modo avanzado)")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -128:
                                    for (String text : TextosRect�ngulos.get("Consultas de grupo")) {
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
        DefinirHandlerSalidaRect�ngulos(SP, Rect�ngulos);
    }

    private void A�adirRect�ngulosModoGr�ficos(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rect�ngulos = new ArrayList();
        Integer[][] ListadoTama�os = PROPIEDADES_RECT�NGULOS.MODO_GR�FICOS.ListadoTama�os;
        for (int i = 0; i < ListadoTama�os.length; i++) {
            Rect�ngulos.add(new Rectangle(ListadoTama�os[i][0], ListadoTama�os[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECT�NGULOS.MODO_GR�FICOS.ListadoPosiciones;
        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                for (int n�meroRect�ngulo = 0; n�meroRect�ngulo < Rect�ngulos.size(); n�meroRect�ngulo++) {
                    Rectangle r = DefinirRect�ngulo(n�meroRect�ngulo, Rect�ngulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    A�adirHandlersRect�ngulos(r, Rect�ngulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cu�l es a partir de la posici�n y buscamos en el HashMap
                            int posici�n = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posici�n) {
                                case -410:
                                    for (String text : TextosRect�ngulos.get("Bot�n \"Cambiar tipo de carta\"")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -402:
                                    for (String text : TextosRect�ngulos.get("Selecci�n del tipo de gr�fico")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -401:
                                    for (String text : TextosRect�ngulos.get("Bot�n \"Crear gr�fico\"")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -300:
                                    for (String text : TextosRect�ngulos.get("Sentencia Individual/Sentencias Grupales")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -321:
                                    for (String text : TextosRect�ngulos.get("C. Gr�fico Grupal/C. Gr�fico Dispersi�n")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 11:
                                    for (String text : TextosRect�ngulos.get("Gr�fico")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 424:
                                    for (String text : TextosRect�ngulos.get("Bot�n \"Nueva serie\"")) {
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
        DefinirHandlerSalidaRect�ngulos(SP, Rect�ngulos);
    }

    private void A�adirRect�ngulosBarradeMen�s(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rect�ngulos = new ArrayList();
        Integer[][] ListadoTama�os = PROPIEDADES_RECT�NGULOS.MEN�.ListadoTama�os;
        for (int i = 0; i < ListadoTama�os.length; i++) {
            Rect�ngulos.add(new Rectangle(ListadoTama�os[i][0], ListadoTama�os[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECT�NGULOS.MEN�.ListadoPosiciones;

        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                int i = 0;
                for (int n�meroRect�ngulo = 0; n�meroRect�ngulo < Rect�ngulos.size(); n�meroRect�ngulo++) {
                    Rectangle r = DefinirRect�ngulo(n�meroRect�ngulo, Rect�ngulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    A�adirHandlersRect�ngulos(r, Rect�ngulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cu�l es a partir de la posici�n y buscamos en el HashMap
                            int posici�n = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posici�n) {
                                case -110:
                                    for (String text : TextosRect�ngulos.get("Submen� \"Archivo\"")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -40:
                                    for (String text : TextosRect�ngulos.get("Submen� \"Modos\"")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 30:
                                    for (String text : TextosRect�ngulos.get("Submen� \"Opciones\"")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 100:
                                    for (String text : TextosRect�ngulos.get("Submen� \"Ayuda\"")) {
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
        DefinirHandlerSalidaRect�ngulos(SP, Rect�ngulos);
    }

    private void A�adirRect�ngulosGr�ficodeBarras(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rect�ngulos = new ArrayList();
        Integer[][] ListadoTama�os = PROPIEDADES_RECT�NGULOS.GR�FICO_BARRAS.ListadoTama�os;
        for (int i = 0; i < ListadoTama�os.length; i++) {
            Rect�ngulos.add(new Rectangle(ListadoTama�os[i][0], ListadoTama�os[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECT�NGULOS.GR�FICO_BARRAS.ListadoPosiciones;
        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                for (int n�meroRect�ngulo = 0; n�meroRect�ngulo < Rect�ngulos.size(); n�meroRect�ngulo++) {
                    Rectangle r = DefinirRect�ngulo(n�meroRect�ngulo, Rect�ngulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    A�adirHandlersRect�ngulos(r, Rect�ngulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cu�l es a partir de la posici�n y buscamos en el HashMap
                            int posici�n = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posici�n) {
                                case -3:
                                    for (String text : TextosRect�ngulos.get("Legenda")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -485:
                                    for (String text : TextosRect�ngulos.get("Eje Y")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 3:
                                    for (String text : TextosRect�ngulos.get("Barras")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 4:
                                    for (String text : TextosRect�ngulos.get("Categor�as")) {
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
        DefinirHandlerSalidaRect�ngulos(SP, Rect�ngulos);
    }

    private void A�adirRect�ngulosGr�ficoBarrasApiladas(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rect�ngulos = new ArrayList();
        Integer[][] ListadoTama�os = PROPIEDADES_RECT�NGULOS.GR�FICO_BARRAS_APILADAS.ListadoTama�os;
        for (int i = 0; i < ListadoTama�os.length; i++) {
            Rect�ngulos.add(new Rectangle(ListadoTama�os[i][0], ListadoTama�os[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECT�NGULOS.GR�FICO_BARRAS_APILADAS.ListadoPosiciones;

        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                for (int n�meroRect�ngulo = 0; n�meroRect�ngulo < Rect�ngulos.size(); n�meroRect�ngulo++) {
                    Rectangle r = DefinirRect�ngulo(n�meroRect�ngulo, Rect�ngulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    A�adirHandlersRect�ngulos(r, Rect�ngulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cu�l es a partir de la posici�n y buscamos en el HashMap
                            int posici�n = (int) r.getTranslateX();
                            String TextoCombinado = "";
                            switch (posici�n) {
                                case -360:
                                    for (String text : TextosRect�ngulos.get("Eje Y (GBA)")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -2:
                                    for (String text : TextosRect�ngulos.get("Legenda (GBA)")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 10:
                                    for (String text : TextosRect�ngulos.get("Barras apiladas")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 11:
                                    for (String text : TextosRect�ngulos.get("Categor�as (GBA)")) {
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
        DefinirHandlerSalidaRect�ngulos(SP, Rect�ngulos);
    }

    private void A�adirRect�ngulosGr�ficodeTarta(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rect�ngulos = new ArrayList();
        Integer[][] ListadoTama�os = PROPIEDADES_RECT�NGULOS.GR�FICO_TARTA.ListadoTama�os;
        for (int i = 0; i < ListadoTama�os.length; i++) {
            Rect�ngulos.add(new Rectangle(ListadoTama�os[i][0], ListadoTama�os[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECT�NGULOS.GR�FICO_TARTA.ListadoPosiciones;
        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                for (int n�meroRect�ngulo = 0; n�meroRect�ngulo < Rect�ngulos.size(); n�meroRect�ngulo++) {
                    Rectangle r = DefinirRect�ngulo(n�meroRect�ngulo, Rect�ngulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    A�adirHandlersRect�ngulos(r, Rect�ngulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cu�l es a partir de la posici�n y buscamos en el HashMap
                            int posici�n = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posici�n) {
                                case 6:
                                    for (String text : TextosRect�ngulos.get("Etiqueta")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 5:
                                    for (String text : TextosRect�ngulos.get("Legenda (GT)")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 207:
                                    for (String text : TextosRect�ngulos.get("Porci�n")) {
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
        DefinirHandlerSalidaRect�ngulos(SP, Rect�ngulos);
    }

    private void A�adirRect�ngulosGr�ficodeDispersi�n(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rect�ngulos = new ArrayList();
        Integer[][] ListadoTama�os = PROPIEDADES_RECT�NGULOS.GR�FICO_DISPERSI�N.ListadoTama�os;
        for (int i = 0; i < ListadoTama�os.length; i++) {
            Rect�ngulos.add(new Rectangle(ListadoTama�os[i][0], ListadoTama�os[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECT�NGULOS.GR�FICO_DISPERSI�N.ListadoPosiciones;
        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                for (int n�meroRect�ngulo = 0; n�meroRect�ngulo < Rect�ngulos.size(); n�meroRect�ngulo++) {
                    Rectangle r = DefinirRect�ngulo(n�meroRect�ngulo, Rect�ngulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    A�adirHandlersRect�ngulos(r, Rect�ngulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cu�l es a partir de la posici�n y buscamos en el HashMap
                            int posici�n = (int) r.getTranslateX();
                            String TextoCombinado = "";
                            switch (posici�n) {
                                case -480:
                                    for (String text : TextosRect�ngulos.get("Ejes")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -9:
                                    for (String text : TextosRect�ngulos.get("Legenda (GD)")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -132:
                                    for (String text : TextosRect�ngulos.get("Etiquetas emergentes")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 20:
                                    for (String text : TextosRect�ngulos.get("Ejes")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 284:
                                    for (String text : TextosRect�ngulos.get("Registros")) {
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
        DefinirHandlerSalidaRect�ngulos(SP, Rect�ngulos);
    }

    private void A�adirRect�ngulosAyudaemergente(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rect�ngulos = new ArrayList();
        Integer[][] ListadoTama�os = PROPIEDADES_RECT�NGULOS.AYUDA_EMERGENTE.ListadoTama�os;
        for (int i = 0; i < ListadoTama�os.length; i++) {
            Rect�ngulos.add(new Rectangle(ListadoTama�os[i][0], ListadoTama�os[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECT�NGULOS.AYUDA_EMERGENTE.ListadoPosiciones;
        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                for (int n�meroRect�ngulo = 0; n�meroRect�ngulo < Rect�ngulos.size(); n�meroRect�ngulo++) {
                    Rectangle r = DefinirRect�ngulo(n�meroRect�ngulo, Rect�ngulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    A�adirHandlersRect�ngulos(r, Rect�ngulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cu�l es a partir de la posici�n y buscamos en el HashMap
                            int posici�n = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posici�n) {
                                case -25:
                                    for (String text : TextosRect�ngulos.get("Nombre del componente")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -28:
                                    for (String text : TextosRect�ngulos.get("Enlace a la ayuda")) {
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
        DefinirHandlerSalidaRect�ngulos(SP, Rect�ngulos);
    }

    private void A�adirRect�ngulosOpciones(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rect�ngulos = new ArrayList();
        Integer[][] ListadoTama�os = PROPIEDADES_RECT�NGULOS.DI�LOGO_OPCIONES.ListadoTama�os;
        for (int i = 0; i < ListadoTama�os.length; i++) {
            Rect�ngulos.add(new Rectangle(ListadoTama�os[i][0], ListadoTama�os[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECT�NGULOS.DI�LOGO_OPCIONES.ListadoPosiciones;

        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                int i = 0;
                for (int n�meroRect�ngulo = 0; n�meroRect�ngulo < Rect�ngulos.size(); n�meroRect�ngulo++) {
                    Rectangle r = DefinirRect�ngulo(n�meroRect�ngulo, Rect�ngulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    A�adirHandlersRect�ngulos(r, Rect�ngulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cu�l es a partir de la posici�n y buscamos en el HashMap
                            int posici�n = (int) r.getTranslateY();

                            String TextoCombinado = "";

                            switch (posici�n) {
                                case -10:
                                    for (String text : TextosRect�ngulos.get("Selecci�n de fondo")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 21:
                                    for (String text : TextosRect�ngulos.get("Activar/desactivar sonidos")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 49:
                                    for (String text : TextosRect�ngulos.get("Activar/desactivar coloreado en filas")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 77:
                                    for (String text : TextosRect�ngulos.get("Activar/desactivar ayuda")) {
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
        DefinirHandlerSalidaRect�ngulos(SP, Rect�ngulos);
    }

    private void A�adirRect�ngulosEstructuradelaBBDD(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rect�ngulos = new ArrayList();
        Integer[][] ListadoTama�os = PROPIEDADES_RECT�NGULOS.ESTRUCTURA_BBDD.ListadoTama�os;
        for (int i = 0; i < ListadoTama�os.length; i++) {
            Rect�ngulos.add(new Rectangle(ListadoTama�os[i][0], ListadoTama�os[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECT�NGULOS.ESTRUCTURA_BBDD.ListadoPosiciones;

        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                int i = 0;
                for (int n�meroRect�ngulo = 0; n�meroRect�ngulo < Rect�ngulos.size(); n�meroRect�ngulo++) {
                    Rectangle r = DefinirRect�ngulo(n�meroRect�ngulo, Rect�ngulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    A�adirHandlersRect�ngulos(r, Rect�ngulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cu�l es a partir de la posici�n y buscamos en el HashMap
                            int posici�n = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posici�n) {
                                case -208:
                                    for (String text : TextosRect�ngulos.get("Nombres")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -71:
                                    for (String text : TextosRect�ngulos.get("Clases")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 68:
                                    for (String text : TextosRect�ngulos.get("Tribus")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 207:
                                    for (String text : TextosRect�ngulos.get("Atributos")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -209:
                                    for (String text : TextosRect�ngulos.get("Habilidades")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -72:
                                    for (String text : TextosRect�ngulos.get("Rarezas")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 67:
                                    for (String text : TextosRect�ngulos.get("Mazos")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 206:
                                    for (String text : TextosRect�ngulos.get("Tipos")) {
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
        DefinirHandlerSalidaRect�ngulos(SP, Rect�ngulos);
    }

    private void A�adirRect�ngulosSentenciaIndividual(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rect�ngulos = new ArrayList();
        Integer[][] ListadoTama�os = PROPIEDADES_RECT�NGULOS.FILTROS_NORMALES.ListadoTama�os;
        for (int i = 0; i < ListadoTama�os.length; i++) {
            Rect�ngulos.add(new Rectangle(ListadoTama�os[i][0], ListadoTama�os[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECT�NGULOS.FILTROS_NORMALES.ListadoPosiciones;

        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                int i = 0;
                for (int n�meroRect�ngulo = 0; n�meroRect�ngulo < Rect�ngulos.size(); n�meroRect�ngulo++) {
                    Rectangle r = DefinirRect�ngulo(n�meroRect�ngulo, Rect�ngulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    A�adirHandlersRect�ngulos(r, Rect�ngulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cu�l es a partir de la posici�n y buscamos en el HashMap
                            int posici�n = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posici�n) {
                                case -15:
                                    for (String text : TextosRect�ngulos.get("Etiquetas de filtros")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 65:
                                    for (String text : TextosRect�ngulos.get("Bot�n \"+\"")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 66:
                                    for (String text : TextosRect�ngulos.get("Bot�n \"-\"")) {
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
        DefinirHandlerSalidaRect�ngulos(SP, Rect�ngulos);
    }

    private void A�adirRect�ngulosSentenciasGrupales(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rect�ngulos = new ArrayList();
        Integer[][] ListadoTama�os = PROPIEDADES_RECT�NGULOS.FILTROS_GRUPALES.ListadoTama�os;
        for (int i = 0; i < ListadoTama�os.length; i++) {
            Rect�ngulos.add(new Rectangle(ListadoTama�os[i][0], ListadoTama�os[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECT�NGULOS.FILTROS_GRUPALES.ListadoPosiciones;

        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                int i = 0;
                for (int n�meroRect�ngulo = 0; n�meroRect�ngulo < Rect�ngulos.size(); n�meroRect�ngulo++) {
                    Rectangle r = DefinirRect�ngulo(n�meroRect�ngulo, Rect�ngulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    A�adirHandlersRect�ngulos(r, Rect�ngulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cu�l es a partir de la posici�n y buscamos en el HashMap
                            int posici�n = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posici�n) {
                                case -13:
                                    for (String text : TextosRect�ngulos.get("Etiquetas de sentencias grupales")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -12:
                                    for (String text : TextosRect�ngulos.get("Bot�n \"Nuevo\"")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 75:
                                    for (String text : TextosRect�ngulos.get("Bot�n \"-\"")) {
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
        DefinirHandlerSalidaRect�ngulos(SP, Rect�ngulos);
    }

    private void A�adirRect�ngulosCGr�ficoGrupal(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rect�ngulos = new ArrayList();
        Integer[][] ListadoTama�os = PROPIEDADES_RECT�NGULOS.CGR�FICO_GRUPAL.ListadoTama�os;
        for (int i = 0; i < ListadoTama�os.length; i++) {
            Rect�ngulos.add(new Rectangle(ListadoTama�os[i][0], ListadoTama�os[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECT�NGULOS.CGR�FICO_GRUPAL.ListadoPosiciones;

        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                int i = 0;
                for (int n�meroRect�ngulo = 0; n�meroRect�ngulo < Rect�ngulos.size(); n�meroRect�ngulo++) {
                    Rectangle r = DefinirRect�ngulo(n�meroRect�ngulo, Rect�ngulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    A�adirHandlersRect�ngulos(r, Rect�ngulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cu�l es a partir de la posici�n y buscamos en el HashMap
                            int posici�n = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posici�n) {
                                case -11:
                                    for (String text : TextosRect�ngulos.get("Selecci�n de funci�n")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -10:
                                    for (String text : TextosRect�ngulos.get("Selecci�n de variable")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 47:
                                    for (String text : TextosRect�ngulos.get("Bot�n \"-\" ")) {
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
        DefinirHandlerSalidaRect�ngulos(SP, Rect�ngulos);
    }

    private void A�adirRect�ngulosCGr�ficoDispersi�n(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rect�ngulos = new ArrayList();
        Integer[][] ListadoTama�os = PROPIEDADES_RECT�NGULOS.CGR�FICO_DISPERSI�N.ListadoTama�os;
        for (int i = 0; i < ListadoTama�os.length; i++) {
            Rect�ngulos.add(new Rectangle(ListadoTama�os[i][0], ListadoTama�os[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECT�NGULOS.CGR�FICO_DISPERSI�N.ListadoPosiciones;

        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                int i = 0;
                for (int n�meroRect�ngulo = 0; n�meroRect�ngulo < Rect�ngulos.size(); n�meroRect�ngulo++) {
                    Rectangle r = DefinirRect�ngulo(n�meroRect�ngulo, Rect�ngulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    A�adirHandlersRect�ngulos(r, Rect�ngulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cu�l es a partir de la posici�n y buscamos en el HashMap
                            int posici�n = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posici�n) {
                                case -9:
                                    for (String text : TextosRect�ngulos.get("Nombre de la serie")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 108:
                                    for (String text : TextosRect�ngulos.get("Bot�n \"-\"  ")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -55:
                                    for (String text : TextosRect�ngulos.get("Selecci�n de eje X")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 62:
                                    for (String text : TextosRect�ngulos.get("Selecci�n de eje Y")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -56:
                                    for (String text : TextosRect�ngulos.get("Selecci�n de color")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 61:
                                    for (String text : TextosRect�ngulos.get("Mostrar/Ocultar serie")) {
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
        DefinirHandlerSalidaRect�ngulos(SP, Rect�ngulos);
    }

    private void A�adirRect�ngulosDi�logofiltros(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rect�ngulos = new ArrayList();
        Integer[][] ListadoTama�os = PROPIEDADES_RECT�NGULOS.DI�LOGO_FILTROS.ListadoTama�os;
        for (int i = 0; i < ListadoTama�os.length; i++) {
            Rect�ngulos.add(new Rectangle(ListadoTama�os[i][0], ListadoTama�os[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECT�NGULOS.DI�LOGO_FILTROS.ListadoPosiciones;

        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                int i = 0;
                for (int n�meroRect�ngulo = 0; n�meroRect�ngulo < Rect�ngulos.size(); n�meroRect�ngulo++) {
                    Rectangle r = DefinirRect�ngulo(n�meroRect�ngulo, Rect�ngulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    A�adirHandlersRect�ngulos(r, Rect�ngulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cu�l es a partir de la posici�n y buscamos en el HashMap
                            int posici�n = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posici�n) {
                                case 16:
                                    for (String text : TextosRect�ngulos.get("Selecci�n de categor�a")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 24:
                                    for (String text : TextosRect�ngulos.get("Selecci�n de relaci�n")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 39:
                                    for (String text : TextosRect�ngulos.get("Selecci�n de valor")) {

                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 40:
                                    for (String text : TextosRect�ngulos.get("Selecci�n de relaci�n con condici�n anterior")) {
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
        DefinirHandlerSalidaRect�ngulos(SP, Rect�ngulos);
    }

    private void A�adirRect�ngulosDi�logoFG(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rect�ngulos = new ArrayList();
        Integer[][] ListadoTama�os = PROPIEDADES_RECT�NGULOS.DI�LOGO_FG.ListadoTama�os;
        for (int i = 0; i < ListadoTama�os.length; i++) {
            Rect�ngulos.add(new Rectangle(ListadoTama�os[i][0], ListadoTama�os[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECT�NGULOS.DI�LOGO_FG.ListadoPosiciones;

        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                int i = 0;
                for (int n�meroRect�ngulo = 0; n�meroRect�ngulo < Rect�ngulos.size(); n�meroRect�ngulo++) {
                    Rectangle r = DefinirRect�ngulo(n�meroRect�ngulo, Rect�ngulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    A�adirHandlersRect�ngulos(r, Rect�ngulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cu�l es a partir de la posici�n y buscamos en el HashMap
                            int posici�n = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posici�n) {
                                case -2:
                                    for (String text : TextosRect�ngulos.get("Selecci�n de tipo de funci�n")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -3:
                                    for (String text : TextosRect�ngulos.get("Selecci�n de tipo de variable")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 38:
                                    for (String text : TextosRect�ngulos.get("Bot�n \"Consultar\"")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -21:
                                    for (String text : TextosRect�ngulos.get("Bot�n \"A�adir\"")) {

                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 28:
                                    for (String text : TextosRect�ngulos.get("Introducci�n de identificador")) {
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
        DefinirHandlerSalidaRect�ngulos(SP, Rect�ngulos);
    }

    private void A�adirRect�ngulosAtajosdeteclado(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rect�ngulos = new ArrayList();
        Integer[][] ListadoTama�os = PROPIEDADES_RECT�NGULOS.ATAJOS_TECLADO.ListadoTama�os;
        for (int i = 0; i < ListadoTama�os.length; i++) {
            Rect�ngulos.add(new Rectangle(ListadoTama�os[i][0], ListadoTama�os[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECT�NGULOS.ATAJOS_TECLADO.ListadoPosiciones;
        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {

                for (int n�meroRect�ngulo = 0; n�meroRect�ngulo < Rect�ngulos.size(); n�meroRect�ngulo++) {
                    Rectangle r = DefinirRect�ngulo(n�meroRect�ngulo, Rect�ngulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    A�adirHandlersRect�ngulos(r, Rect�ngulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cu�l es a partir de la posici�n y buscamos en el HashMap
                            int posici�n = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posici�n) {
                                case -238:
                                    for (String text : TextosRect�ngulos.get("Atajos de la Barra de Men�s")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -79:
                                    for (String text : TextosRect�ngulos.get("Atajos del Modo B�sico")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 80:
                                    for (String text : TextosRect�ngulos.get("Atajos del Modo Avanzado")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 239:
                                    for (String text : TextosRect�ngulos.get("Atajos del Modo Gr�ficos")) {
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
        DefinirHandlerSalidaRect�ngulos(SP, Rect�ngulos);
    }

    private void A�adirRect�ngulosFondosPersonalizados(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rect�ngulos = new ArrayList();
        Integer[][] ListadoTama�os = PROPIEDADES_RECT�NGULOS.FONDOS_PERSONALIZADOS.ListadoTama�os;
        for (int i = 0; i < ListadoTama�os.length; i++) {
            Rect�ngulos.add(new Rectangle(ListadoTama�os[i][0], ListadoTama�os[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECT�NGULOS.FONDOS_PERSONALIZADOS.ListadoPosiciones;

        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                int i = 0;
                for (int n�meroRect�ngulo = 0; n�meroRect�ngulo < Rect�ngulos.size(); n�meroRect�ngulo++) {
                    Rectangle r = DefinirRect�ngulo(n�meroRect�ngulo, Rect�ngulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    A�adirHandlersRect�ngulos(r, Rect�ngulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cu�l es a partir de la posici�n y buscamos en el HashMap
                            int posici�n = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posici�n) {
                                case 0:
                                    for (String text : TextosRect�ngulos.get("Procedimiento")) {
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
        DefinirHandlerSalidaRect�ngulos(SP, Rect�ngulos);
    }

    private void A�adirRect�ngulosSonidosPersonalizados(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rect�ngulos = new ArrayList();
        Integer[][] ListadoTama�os = PROPIEDADES_RECT�NGULOS.SONIDOS_PERSONALIZADOS.ListadoTama�os;
        for (int i = 0; i < ListadoTama�os.length; i++) {
            Rect�ngulos.add(new Rectangle(ListadoTama�os[i][0], ListadoTama�os[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECT�NGULOS.SONIDOS_PERSONALIZADOS.ListadoPosiciones;

        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                int i = 0;
                for (int n�meroRect�ngulo = 0; n�meroRect�ngulo < Rect�ngulos.size(); n�meroRect�ngulo++) {
                    Rectangle r = DefinirRect�ngulo(n�meroRect�ngulo, Rect�ngulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    A�adirHandlersRect�ngulos(r, Rect�ngulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cu�l es a partir de la posici�n y buscamos en el HashMap
                            int posici�n = (int) r.getTranslateX();

                            String TextoCombinado = "";
                            switch (posici�n) {
                                case 0:
                                    for (String text : TextosRect�ngulos.get("Pasos")) {
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
        DefinirHandlerSalidaRect�ngulos(SP, Rect�ngulos);
    }

    private void A�adirRect�ngulosProblemascomunes(StackPane SP, TextFlow tf) {
        ArrayList<Rectangle> Rect�ngulos = new ArrayList();
        Integer[][] ListadoTama�os = PROPIEDADES_RECT�NGULOS.PROBLEMAS_COMUNES.ListadoTama�os;
        for (int i = 0; i < ListadoTama�os.length; i++) {
            Rect�ngulos.add(new Rectangle(ListadoTama�os[i][0], ListadoTama�os[i][1]));
        }
        Integer[][] ListadoPosiciones = PROPIEDADES_RECT�NGULOS.PROBLEMAS_COMUNES.ListadoPosiciones;
        SP.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {

                for (int n�meroRect�ngulo = 0; n�meroRect�ngulo < Rect�ngulos.size(); n�meroRect�ngulo++) {
                    Rectangle r = DefinirRect�ngulo(n�meroRect�ngulo, Rect�ngulos, ListadoPosiciones);
                    SP.getChildren().add(r);
                    A�adirHandlersRect�ngulos(r, Rect�ngulos);
                    r.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            tf.getChildren().clear();
                            // Averiguamos cu�l es a partir de la posici�n y buscamos en el HashMap
                            int posici�n = (int) r.getTranslateY();

                            String TextoCombinado = "";
                            switch (posici�n) {
                                case -65:
                                    for (String text : TextosRect�ngulos.get("Di�logo \"Problema en la carga de sonido\"")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -33:
                                    for (String text : TextosRect�ngulos.get("Di�logo \"Nueva imagen a�adida\"")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case -1:
                                    for (String text : TextosRect�ngulos.get("Di�logo \"Sin fondos\"")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;

                                case 32:
                                    for (String text : TextosRect�ngulos.get("La aplicaci�n tiene un fondo azul gen�rico")) {
                                        TextoCombinado = TextoCombinado + text + "\n";
                                    }
                                    break;
                                case 64:
                                    for (String text : TextosRect�ngulos.get("El sistema de ayuda no se abre desde la aplicaci�n")) {
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
        DefinirHandlerSalidaRect�ngulos(SP, Rect�ngulos);
    }

    private void DefinirHandlerSalidaRect�ngulos(StackPane SP, ArrayList<Rectangle> Rect�ngulos) {
        SP.setOnMouseExited(new EventHandler() {
            @Override
            public void handle(Event event
            ) {
                for (Rectangle r : Rect�ngulos) {
                    SP.getChildren().remove(r);
                }
            }
        }
        );
    }

    private Rectangle DefinirRect�ngulo(int n�meroRect�ngulo, ArrayList<Rectangle> Rect�ngulos, Integer[][] ListadoPosiciones) {
        Rectangle r = Rect�ngulos.get(n�meroRect�ngulo);
        r.setOpacity(OPACIDAD_MEDIA);
        r.setStyle("-fx-fill: blue;");
        r.setTranslateX(ListadoPosiciones[n�meroRect�ngulo][0]);
        r.setTranslateY(ListadoPosiciones[n�meroRect�ngulo][1]);
        return r;
    }

    /**
     * Genera el encabezado, obtiene las posiciones de los enlaces del TextFlow
     * y devuelve un ArrayList con los distintos Text ordenados (encabezado y
     * cuerpo). Esta ordenaci�n permitir� que se pueda inferir cu�les
     * corresponden a enlaces y cu�les no.
     *
     * @param TextoCombinado El texto completo asociado al bot�n que se ha
     * pulsado
     * @param ListOrdenado El ArrayList que incluir� Text ordenados.
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

        // Contea el n�mero de coincidencias que se tendr�n que convertir en enlaces
        int coincidencias = 0;
        for (String ContenidoPrincipal : CONTENIDOS_PRINCIPALES) {
            coincidencias = coincidencias + ContarCoincidenciasEnString(cuerpo.getText(), ContenidoPrincipal);
        }

        if (coincidencias > 0) {
            ListOrdenado.remove(cuerpo);
            // Determina la posici�n de los enlaces en el String completo y las guarda en un HashMap
            HashMap<Integer, String> Posici�nImagen = new HashMap();
            for (String palabra : CONTENIDOS_PRINCIPALES) {
                String textoRecortado = cuerpo.getText();
                int recortado = 0;
                while (textoRecortado.contains(palabra)) {
                    int posici�nPalabra = textoRecortado.indexOf(palabra);
                    recortado = cuerpo.getText().length() - textoRecortado.length();
                    textoRecortado = textoRecortado.substring(posici�nPalabra + palabra.length());
                    Posici�nImagen.put(posici�nPalabra + recortado, palabra);
                }
            }

            // Crea una lista con todas los pares de valores (entradas) del mapa
            List<Map.Entry<Integer, String>> lista
                    = new LinkedList<>(Posici�nImagen.entrySet());

            // Ordena la lista en funci�n de las key de las entradas (las posiciones)
            Collections.sort(lista, new Comparator<Map.Entry<Integer, String>>() {
                @Override
                public int compare(Map.Entry<Integer, String> entrada1,
                        Map.Entry<Integer, String> entrada2) {
                    return (entrada1.getKey()).compareTo(entrada2.getKey());
                }
            });

            // Utilizamos la lista con el tipo de imagen y su posici�n para ir recorriendo el String y creando los nodos
            int inicioImagen = 0;
            int recortado = 0;
            String textoRecortado = cuerpo.getText();
            for (Map.Entry<Integer, String> entrada : lista) {
                // La posici�n de inicio del enlace  ha de tener en cuenta que el String se va recortando
                inicioImagen = entrada.getKey() - recortado;

                ListOrdenado.add(new Text(textoRecortado.substring(0, inicioImagen)));
                ListOrdenado.add(new Text(entrada.getValue()));

                // El String de la habilidad se va recortando para ir avanzando en la parte que se compara
                textoRecortado = textoRecortado.substring(inicioImagen + entrada.getValue().length());
                recortado = cuerpo.getText().length() - textoRecortado.length();
                coincidencias--;
                // Cuando no quedan coincidencias, a�adimos el trozo de texto restante
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
     * A�ade handlers de entrada y salida del cursor a los rect�ngulos para
     * cambiar su opacidad.
     *
     * @param r Rect�ngulo concreto en el que se entra o sale.
     * @param Rect�ngulos Todos los rect�ngulos de la imagen. disminuye
     */
    private void A�adirHandlersRect�ngulos(Rectangle r, ArrayList<Rectangle> Rect�ngulos) {
        r.setOnMouseEntered(new EventHandler() {
            @Override
            public void handle(Event event) {
                r.setOpacity(OPACIDAD_ALTA);
                for (Rectangle rec : Rect�ngulos) {
                    if (!rec.equals(r)) {
                        rec.setOpacity(OPACIDAD_M�NIMA);
                    }
                }
            }
        });
        r.setOnMouseExited(new EventHandler() {
            @Override
            public void handle(Event event) {
                for (Rectangle rec : Rect�ngulos) {
                    rec.setOpacity(OPACIDAD_MEDIA);
                }
            }
        });
    }

    /**
     * Cuenta el n�mero de coincidencias de una palabra dentro de un string
     *
     * @param string El string a analizar
     * @param palabra La palabra que se busca
     * @return Entero con el n�mero de coincidencias
     */
    private int ContarCoincidenciasEnString(String string, String palabra) {
        int posici�n = string.indexOf(palabra);
        int coincidencias = 0;
        while (posici�n != -1) {
            coincidencias++;
            string = string.substring(posici�n + 1);
            posici�n = string.indexOf(palabra);
        }
        return coincidencias;
    }

    /**
     * Define el texto a mostrar cuando se selecciona un hiperv�nculo o un
     * rect�ngulo, dando un tratamiento diferente seg�n si contiene o no
     * enlaces.
     *
     * @param TextoConEnlaces ArrayList<Text> obtenido del m�todo
     * ObtenerPosicionesEnlaces. Se utiliza en caso de que no sea "null" y, por
     * tanto, se hayan detectado enlaces.
     * @param ListAlternativa ArrayList<Text> que se utiliza cuando
     * TextoConEnlaces es "null" y, por tanto, no se han detectado enlaces.
     * @param tf El TextFlow que contendr� TextoConEnlaces o SistemaDeAyuda
     */
    private void MostrarTextoFinal(ArrayList<Text> TextoConEnlaces, ArrayList<Text> ListAlternativa, TextFlow tf) {
        if (TextoConEnlaces != null) {
            TextosPosteriores.add(TextoConEnlaces);
            tf.getChildren().addAll(TextoConEnlaces);

            // Los enlaces ocupar�n posiciones 3, 5, 7....luego (n�meroRect�ngulo-2)%2=0
            for (int i = 0; i < TextoConEnlaces.size(); i++) {
                if (i != 0 && ((i - 1) % 2 != 0)) {
                    TextoConEnlaces.get(i).setStyle("-fx-font-size: 20 px;");
                } else if ((i - 1) % 2 == 0) {
                    TextoConEnlaces.get(i).setStyle("-fx-font-size: 20 px;"
                            + "-fx-fill: blue;");
                    A�adirHandlersEnlaces(TextoConEnlaces.get(i));
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

    private void A�adirHandlersEnlaces(Text TextoLink) {
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
