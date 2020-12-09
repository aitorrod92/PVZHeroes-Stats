package pvzheroes.clases;

import pvzheroes.clases.componentes.SentenciasGrupales;
import pvzheroes.clases.componentes.SentenciaIndividual;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import static pvzheroes.clases.FXMLDocumentController.conn;
import pvzheroes.clases.diccionarios.DiccionarioPropiedades;
import pvzheroes.clases.clasesParaImágenesEnCombos.IconosFactory;
import pvzheroes.clases.componentes.ComponenteGráficoDispersión;
import pvzheroes.clases.componentes.ComponenteGráficoGrupal;
import pvzheroes.clases.efectosConcretos.CrearToolTip;
import pvzheroes.clases.métodosCompartidos.MétodosCompartidos;
import pvzheroes.clases.efectosConcretos.DestacarPorDatoGráficosPie;
import pvzheroes.clases.efectosConcretos.DestacarPorDatoGráficosXY;

public class ModoGráficos {

    public static Boolean MostrarFilasPorColor = true;

    private final Insets INSETS = new Insets(20, 0, 0, 20);
    private final String[] TIPOS_GRÁFICOS = {"Barras", "Barras apiladas", "Dispersión", "Tarta"};

    private final Pane PANEL_GRÁFICOS;
    private final GridPane GridGráficos;
    private final FXMLDocumentController DC;
    private final MétodosCompartidos MC; // PROBAR A ELIMINARLO
    public static ArrayList<SentenciasGrupales> ListadoCFG;
    public static ArrayList<SentenciaIndividual> ListadoCFil;
    public static ArrayList<ComponenteGráficoDispersión> ListadoCGD;
    private Chart Gráfico;
    private Button BotónNuevaSerieBarras;
    private Button BotónNuevaSerieDispersión;
    private ComboBox SelecciónTipoGráfico;

    private ComboBox ComboEjeX;
    private ComboBox ComboEjeY;
    private Button BotónCrearGráfico;

    private static int ValorMáximoX = 0;
    private static int ValorMínimoX = 11;
    private static int ValorMáximoY = 0;
    private static int ValorMínimoY = 11;

    public ModoGráficos(Pane Panel, FXMLDocumentController dc) {
        PANEL_GRÁFICOS = Panel;
        GridGráficos = (GridPane) PANEL_GRÁFICOS.getChildren().get(0);
        DC = dc;
        MC = new MétodosCompartidos();
        ListadoCFG = new ArrayList();
        ListadoCFil = new ArrayList();
        ListadoCGD = new ArrayList();
    }

    public void ConfigurarInterfaz() {
        GridGráficos.setId("GridGráficos");
        GridGráficos.add(new ComponenteGráficoGrupal(this, true), 1, 0);
        SentenciasGrupales cfgGráficos = new SentenciasGrupales(DC, this, 2);
        ListadoCFG.add(cfgGráficos);
        GridGráficos.add(cfgGráficos, 1, 1);

        ComponenteGráficoDispersión cgd = new ComponenteGráficoDispersión(this, 1, true);
        cgd.setVisible(false);
        GridGráficos.add(cgd, 1, 0);
        GridPane.setMargin(cgd, INSETS);
        SentenciaIndividual cf = new SentenciaIndividual(DC, this, 2);
        ListadoCFil.add(cf);
        ListadoCGD.add(cgd);
        cf.setVisible(false);
        GridGráficos.add(cf, 1, 1);

        SelecciónTipoGráfico = MC.CrearComboBox(TIPOS_GRÁFICOS, 0);
        SelecciónTipoGráfico.setId(constantes.NOMBRES_ELEMENTOS.SELECCIÓN_TIPO_GRÁFICOS.Nombre);
        new CrearToolTip(SelecciónTipoGráfico, this.PANEL_GRÁFICOS, 2);
        SelecciónTipoGráfico.setCellFactory(new IconosFactory());
        SelecciónTipoGráfico.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                String seleccionado = SelecciónTipoGráfico.getSelectionModel().getSelectedItem().toString();
                Boolean DeshabilitarCFG = false;
                if (seleccionado.equals("Dispersión")) {
                    DeshabilitarCFG = true;
                } else if (seleccionado.equals("Barras") || seleccionado.equals("Barras apiladas") || seleccionado.equals("Tarta")) {
                    DeshabilitarCFG = false;
                }
                for (Node nodo : GridGráficos.getChildren()) {
                    if (GridPane.getColumnIndex(nodo) > 0 && GridPane.getRowIndex(nodo) < 2 && ((nodo instanceof SentenciasGrupales)
                            || ((!(nodo instanceof SentenciaIndividual) && !(nodo instanceof Button))) && (!ListadoCGD.contains(nodo)))) {
                        nodo.setDisable(DeshabilitarCFG);
                        nodo.setVisible(!DeshabilitarCFG);
                    } else if (nodo instanceof SentenciaIndividual || (ListadoCGD.contains(nodo))) {
                        nodo.setDisable(!DeshabilitarCFG);
                        nodo.setVisible(DeshabilitarCFG);
                    } else if (nodo instanceof Button) {
                        if (((Button) nodo).getText().equals("Nueva serie")) {
                            if (seleccionado.equals("Tarta")) {
                                nodo.setVisible(false);
                            } else if (seleccionado.equals("Barras") || seleccionado.equals("Barras apiladas")) {
                                BotónNuevaSerieBarras.setVisible(true);
                                BotónNuevaSerieDispersión.setVisible(false);
                            } else {
                                BotónNuevaSerieBarras.setVisible(false);
                                BotónNuevaSerieDispersión.setVisible(true);
                            }
                        }
                    }
                }
            }
        }
        );

        BotónCrearGráfico = new Button("Crear gráfico");
        BotónCrearGráfico.setId(constantes.NOMBRES_ELEMENTOS.BOTÓN_CREAR_GRÁFICO.Nombre);
        new CrearToolTip(BotónCrearGráfico, this.PANEL_GRÁFICOS, 2);
        BotónCrearGráfico.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                if (DiccionarioPropiedades.PROP.getProperty("sonido").equals("ON")) {
                    new MediaPlayer(constantes.SONIDOS.BUSCAR_O_GRÁFICO.sonido).play();
                }
                if (SelecciónTipoGráfico.getSelectionModel().getSelectedItem().toString().equals("Dispersión")) {
                    if (ModoGráficos.this.ComprobaciónEjesDispersión() == false) {
                        event.consume();
                        Dialog diálogo = new Dialog();
                        diálogo.setContentText("Estás a punto de crear una gráfica en la que los ejes X e Y "
                                + "\nno son coherentes entre distintas series.\n¿Deseas continuar?");
                        diálogo.setTitle("Confirmación de creación");
                        diálogo.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
                        Optional<ButtonType> confirmación = diálogo.showAndWait();
                        if (confirmación.isPresent()) {
                            if (confirmación.get().equals(ButtonType.CANCEL)) {
                                return;
                            }
                        }
                    }
                }

                Chart GráficoAEliminar = null;
                for (Node nodo : GridGráficos.getChildren()) {
                    if (nodo instanceof Chart) {
                        GráficoAEliminar = (Chart) nodo;
                        break;
                    }
                }
                GridGráficos.getChildren().remove(GráficoAEliminar);

                Boolean GráficoDispersión = false;
                try {
                    ArrayList<ArrayList> ListasResultados = new ArrayList();
                    for (Node nodo : GridGráficos.getChildren()) {
                        if (nodo instanceof SentenciasGrupales && nodo.isVisible()) { // Barras normales, apiladas o Tarta
                            ArrayList<String> resultados = MC.BúsquedaGrupal((SentenciasGrupales) nodo, conn);
                            ListasResultados.add(resultados);
                        } else if (nodo instanceof SentenciaIndividual && nodo.isVisible()) { // Dispersión
                            ArrayList<Carta> resultados = MC.Búsqueda(((SentenciaIndividual) nodo).FiltrosCartas, this);
                            ListasResultados.add(resultados);
                            GráficoDispersión = true;
                        }
                    }
                    String TipoDeGráfico = SelecciónTipoGráfico.getSelectionModel().getSelectedItem().toString();
                    Gráfico = CrearGráfico(TipoDeGráfico, ListasResultados);
                    int NúmeroColumnas = MétodosCompartidos.ObtenerNúmeroColumnas(GridGráficos);
                    GridGráficos.add(Gráfico, 0, 2, NúmeroColumnas, 1);
                    int NúmeroDeGráfico = SelecciónTipoGráfico.getSelectionModel().getSelectedIndex();
                    new CrearToolTip(Gráfico, ModoGráficos.this.PANEL_GRÁFICOS, CrearToolTip.InicioGráficos 
                            + NúmeroDeGráfico);

                    // Modificaciones posteriores a la adicción de los nodos del gráfico
                    if (GráficoDispersión) {
                        AsignarColoresDispersión();
                    }

                } catch (SQLException ex) {
                    System.out.println("Error en la búsqueda");
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                    System.out.println(ex.getMessage());
                }

            }
        }
        );

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                MétodosCompartidos.EstablecerAceleradorEnBotón(BotónCrearGráfico, new KeyCodeCombination(KeyCode.ENTER), null, null);
            }
        });

        VBox VBoxBotones = new VBox();
        VBoxBotones.getChildren().addAll(SelecciónTipoGráfico, BotónCrearGráfico);
        GridGráficos.add(VBoxBotones, 0, 1);
        Platform.runLater(() -> BotónCrearGráfico.setMinWidth(VBoxBotones.getWidth() - 10));

        BotónNuevaSerieBarras = new Button("Nueva serie");
        BotónNuevaSerieBarras.setId(constantes.NOMBRES_ELEMENTOS.BOTÓN_NUEVA_SERIE_BARRAS.Nombre);
        new CrearToolTip(BotónNuevaSerieBarras, PANEL_GRÁFICOS, 2);
        GridGráficos.add(BotónNuevaSerieBarras, 2, 0);
        BotónNuevaSerieBarras.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                if (DiccionarioPropiedades.PROP.getProperty("sonido").equals("ON")) {
                    new MediaPlayer(constantes.SONIDOS.BOTÓN.sonido).play();
                }
                int NúmeroColumna = GridPane.getColumnIndex((Node) BotónNuevaSerieBarras);
                ComponenteGráficoGrupal CGG = new ComponenteGráficoGrupal(ModoGráficos.this, false);
                GridGráficos.add(CGG, NúmeroColumna, 0);

                SentenciasGrupales cfge = new SentenciasGrupales(null, ModoGráficos.this, -1);
                cfge.setDisable(false);
                ListadoCFG.add(cfge);
                GridGráficos.add(cfge, NúmeroColumna, 1);

                GridPane.setConstraints(CGG, NúmeroColumna, 0, 1, 1, HPos.LEFT, VPos.BASELINE, Priority.SOMETIMES, Priority.NEVER, INSETS);
                GridPane.setMargin(cfge, INSETS);
                GridPane.setFillWidth(cfge, false);

                try {
                    GridGráficos.add(Gráfico, 0, 2, MétodosCompartidos.ObtenerNúmeroColumnas(GridGráficos), 1);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NullPointerException | IllegalArgumentException ex) {
                    System.out.println(ex.getCause());
                }

                GridGráficos.getChildren().remove(BotónNuevaSerieBarras);
                GridGráficos.add(BotónNuevaSerieBarras, NúmeroColumna + 1, 0);
                GridPane.setMargin(BotónNuevaSerieBarras, INSETS);
                GridGráficos.getChildren().remove(Gráfico);
            }
        }
        );

        BotónNuevaSerieDispersión = new Button("Nueva serie");
        BotónNuevaSerieDispersión.setId(constantes.NOMBRES_ELEMENTOS.BOTÓN_NUEVA_SERIE_DISPERSIÓN.Nombre);
        new CrearToolTip(BotónNuevaSerieDispersión, PANEL_GRÁFICOS, 2);
        BotónNuevaSerieDispersión.setVisible(false);
        GridGráficos.add(BotónNuevaSerieDispersión, 2, 0);
        BotónNuevaSerieDispersión.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                int NúmeroColumna = GridPane.getColumnIndex((Node) BotónNuevaSerieDispersión);
                ComponenteGráficoDispersión NuevoCGD = new ComponenteGráficoDispersión(ModoGráficos.this, NúmeroColumna, false);
                GridGráficos.add(NuevoCGD, NúmeroColumna, 0);
                SentenciaIndividual cfe = new SentenciaIndividual(ModoGráficos.this.DC, ModoGráficos.this, -1);
                GridGráficos.add(cfe, NúmeroColumna, 1);
                ListadoCFil.add(cfe);
                ListadoCGD.add(NuevoCGD);
                ((TextField) ((HBox) NuevoCGD.getChildren().get(0)).getChildren().get(0)).setPromptText("Nombre de la serie " + GridPane.getColumnIndex(NuevoCGD));
                GridPane.setMargin(NuevoCGD, INSETS);
                GridPane.setMargin(cfe, INSETS);
                GridPane.setFillWidth(cfe, Boolean.FALSE);

                GridGráficos.getChildren().remove(BotónNuevaSerieDispersión);
                GridGráficos.add(BotónNuevaSerieDispersión, NúmeroColumna + 1, 0);
                GridPane.setMargin(BotónNuevaSerieDispersión, INSETS);
                GridGráficos.getChildren().remove(Gráfico);
            }
        });

        Platform.runLater(
                () -> {
                    for (Node nodo : GridGráficos.getChildren()) {
                        GridPane.setMargin(nodo, INSETS);
                    }
                }
        );

    }

    public Chart CrearGráfico(String TipoGráfico, ArrayList<ArrayList> listadoresultados) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, SQLException {
        Chart GráficoACrear = null;
        NumberAxis ejeY = new NumberAxis();
        if (!TipoGráfico.equals("Tarta")) {
            if (TipoGráfico.equals("Barras") || TipoGráfico.equals("Barras apiladas")) {
                CategoryAxis ejeX = new CategoryAxis();
                if (TipoGráfico.equals("Barras")) {
                    BarChart GráficoDeBarras = new BarChart(ejeX, ejeY);
                    GráficoACrear = CrearGráficoXY(GráficoDeBarras, listadoresultados);
                    GráficoACrear.setId(constantes.NOMBRES_ELEMENTOS.GRÁFICO_BARRAS.Nombre);
                } else {
                    StackedBarChart GráficoDeBarrasA = new StackedBarChart<>(ejeX, ejeY);
                    GráficoACrear = CrearGráficoXY(GráficoDeBarrasA, listadoresultados);
                    GráficoACrear.setId(constantes.NOMBRES_ELEMENTOS.GRÁFICO_BARRAS_APILADAS.Nombre);
                }

            } else {
                NumberAxis ejeX = new NumberAxis();
                ArrayList<String> ValoresX = new ArrayList();
                ArrayList<String> ValoresY = new ArrayList();
                for (SentenciaIndividual cofil : ListadoCFil) {
                    String[] ValoresCombos = cofil.ObtenerValoresComboBoxes();
                    if (!ValoresX.contains(ValoresCombos[0])) {
                        ValoresX.add(ValoresCombos[0]);
                    }

                    if (!ValoresY.contains(ValoresCombos[1])) {
                        ValoresY.add(ValoresCombos[1]);
                    }

                }

                String etiquetaX = "";
                for (int i = 0; i < ValoresX.size(); i++) {
                    if (i == 0) {
                        etiquetaX = ValoresX.get(0);
                    } else {
                        etiquetaX = etiquetaX + "-" + ValoresX.get(i);
                    }
                }
                ejeX.setLabel(etiquetaX.trim());
                ejeX.setAutoRanging(false);
                ejeX.setLowerBound(-1);

                ejeX.setTickUnit(1);
                ejeX.setMinorTickVisible(false);

                String etiquetaY = "";
                for (int i = 0; i < ValoresY.size(); i++) {
                    if (i == 0) {
                        etiquetaY = ValoresY.get(0);
                    } else {
                        etiquetaY = etiquetaY + "-" + ValoresY.get(i);
                    }
                }
                ejeY.setLabel(etiquetaY.trim());
                ejeY.setAutoRanging(false);
                ejeY.setLowerBound(-1);
                ejeY.setTickUnit(1);
                ejeY.setMinorTickVisible(false);

                ScatterChart GráficoDispersión = new ScatterChart<>(ejeX, ejeY);
                GráficoACrear = CrearGráficoXY(GráficoDispersión, listadoresultados);
                GráficoACrear.setId(constantes.NOMBRES_ELEMENTOS.GRÁFICO_DISPERSIÓN.Nombre);
                ejeX.setUpperBound(ValorMáximoX + 1);
                ejeX.setLowerBound(ValorMínimoX - 1);
                ejeY.setUpperBound(ValorMáximoY + 1);
                ejeY.setLowerBound(ValorMínimoY - 1);
            }
        } else {
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for (int i = 0; i < listadoresultados.get(0).size(); i++) {
                String resultado = ((ArrayList<String>) listadoresultados.get(0)).get(i);
                String Identificador = ((Label) ListadoCFG.get(0).getChildren().get((i * 2) + 1)).getText();
                PieChart.Data dato = new PieChart.Data(Identificador, Double.valueOf(resultado.replace(",", ".")));
                pieChartData.add(dato);
                MétodosCompartidos.setToolTipDatoGráfico(dato, null);
                Platform.runLater(new DestacarPorDatoGráficosPie(pieChartData, dato));

            }

            GráficoACrear = new PieChart(pieChartData);
            GráficoACrear.setId(constantes.NOMBRES_ELEMENTOS.GRÁFICO_TARTA.Nombre);

        }
        return GráficoACrear;
    }

    private Chart CrearGráficoXY(Chart Gráfico, ArrayList<ArrayList> listadoresultados) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ArrayList<XYChart.Series> Series = new ArrayList();
        if (Gráfico instanceof BarChart || Gráfico instanceof StackedBarChart) {
            int x = 0;
            for (SentenciasGrupales sentenciasGrupales : ListadoCFG) {
                XYChart.Series serie = new XYChart.Series<>();
                String PrimeraSentencia = "";
                int y = 0;
                // Sólo los componentes que tienen al menos un filtro
                if (sentenciasGrupales.getChildren().size() > 1) {
                    for (Node nodo : sentenciasGrupales.getChildren()) {
                        if (nodo instanceof Label) {
                            double valor = MC.ConvertirStringADoble((String) listadoresultados.
                                    get(x).get(y));
                            PrimeraSentencia = ((Label) nodo).getText();
                            XYChart.Data dato = new XYChart.Data(((Label) nodo).getText(), valor);
                            serie.getData().add(dato);

                            MétodosCompartidos.setToolTipDatoGráfico(dato, null);
                            Platform.runLater(new DestacarPorDatoGráficosXY(Series, dato));
                            y++;
                        }
                    }
                    Series.add(serie);
                    PrimeraSentencia = sentenciasGrupales.getFuncionesDefinidas().get(PrimeraSentencia);
                    serie.setName(PrimeraSentencia.substring(PrimeraSentencia.indexOf("SELECT") + 7,
                            PrimeraSentencia.indexOf("FROM") - 1).trim());
                    x++;
                }
            }

            if (Gráfico instanceof BarChart) {
                BarChart GráficoB = (BarChart) Gráfico;
                for (XYChart.Series s : Series) {
                    GráficoB.getData().addAll(s);
                }
                return GráficoB;
            } else if (Gráfico instanceof StackedBarChart) {
                StackedBarChart GráficoBA = (StackedBarChart) Gráfico;
                for (XYChart.Series s : Series) {
                    GráficoBA.getData().addAll(s);
                }
                return GráficoBA;
            }

            return null;
        } else if (Gráfico instanceof ScatterChart) {
            ValorMáximoX = 0;
            ValorMínimoX = 11;
            ValorMáximoY = 0;
            ValorMínimoY = 11;

            int x = 0;
            for (int i = 0; i < ListadoCFil.size(); i++) {
                XYChart.Series serie = new XYChart.Series();
                String[] ValoresCombos = ListadoCFil.get(i).ObtenerValoresComboBoxes();
                String ComboX = ValoresCombos[0];
                String ComboY = ValoresCombos[1];

                for (int y = 0; y < listadoresultados.get(x).size(); y++) {
                    Carta carta = ((Carta) listadoresultados.get(x).get(y));
                    int ValorX = 0;
                    int ValorY = 0;
                    switch (ComboX) {
                        case "Ataque":
                            ValorX = Integer.valueOf(carta.getAtaque());
                            break;
                        case "Defensa":
                            ValorX = Integer.valueOf(carta.getDefensa());
                            break;
                        case "Coste":
                            ValorX = Integer.valueOf(carta.getCoste());
                            break;
                    }
                    switch (ComboY) {
                        case "Ataque":
                            ValorY = Integer.valueOf(carta.getAtaque());
                            break;
                        case "Defensa":
                            ValorY = Integer.valueOf(carta.getDefensa());
                            break;
                        case "Coste":
                            ValorY = Integer.valueOf(carta.getCoste());
                            break;
                    }

                    if (ValorX > ValorMáximoX) {
                        ValorMáximoX = ValorX;
                    } else if (ValorX < ValorMínimoX) {
                        ValorMínimoX = ValorX;
                    }

                    if (ValorY > ValorMáximoY) {
                        ValorMáximoY = ValorY;
                    } else if (ValorY < ValorMínimoY) {
                        ValorMínimoY = ValorY;
                    }

                    XYChart.Data marca = new XYChart.Data(ValorX, ValorY);
                    serie.getData().add(marca);

                    Platform.runLater(new DestacarPorDatoGráficosXY(Series, marca));
                    // Sólo se crea para la última serie
                    MétodosCompartidos.setToolTipDatoGráfico(marca, listadoresultados);
                }
                Series.add(serie);
                CheckBox checkAsociado = ListadoCFil.get(i).ObtenerCheck();
                checkAsociado.setOnAction(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        Boolean seleccionado = checkAsociado.isSelected();
                        for (Object marca : serie.getData()) {
                            Node nodoMarca = ((XYChart.Data) marca).getNode();
                            if (seleccionado) {
                                nodoMarca.setOpacity(1);
                            } else {
                                nodoMarca.setOpacity(0);
                            }
                        }
                    }
                });

                ComponenteGráficoDispersión CajaVertical = MétodosCompartidos.getObjetoPosiciónRelativaGrid(new ComponenteGráficoDispersión(), GridGráficos, ListadoCFil.get(i), 0, -1);
                HBox CajaHorizontal = MétodosCompartidos.getObjetoPosiciónRelativaBox(new HBox(), CajaVertical, 0);
                TextField Campo = MétodosCompartidos.getObjetoPosiciónRelativaBox(new TextField(), CajaHorizontal, 0);
                serie.setName(Campo.getText());
                x++;
            }
            ScatterChart GráficoD = (ScatterChart) Gráfico;
            GráficoD.getData().addAll(Series);

            return GráficoD;
        }
        return null;
    }

    /**
     * Comprueba que todos los ejes del gráfico de dispersión son coherentes
     * entre sí
     *
     * @return Booleano confirmándolo o desmintiéndolo
     */
    private Boolean ComprobaciónEjesDispersión() {
        Boolean EjesCoherentes = true;
        String ValorX = "";
        String ValorY = "";
        Boolean PrimerParCombos = true;
        for (Node nodo : GridGráficos.getChildrenUnmodifiable()) {
            if (nodo instanceof VBox && GridPane.getRowIndex(nodo) == 0 && nodo.isVisible()) {
                HBox CajaHorizontal = ((HBox) ((VBox) nodo).getChildren().get(1));
                String ValorPrimerCombo = ((ComboBox) CajaHorizontal.getChildrenUnmodifiable().get(0)).getValue().toString();
                String ValorSegundoCombo = ((ComboBox) CajaHorizontal.getChildrenUnmodifiable().get(1)).getValue().toString();
                if (PrimerParCombos) {
                    ValorX = ValorPrimerCombo;
                    ValorY = ValorSegundoCombo;
                    PrimerParCombos = false;
                } else {
                    if (!ValorX.equals(ValorPrimerCombo) || !ValorY.equals(ValorSegundoCombo)) {
                        EjesCoherentes = false;
                        return EjesCoherentes;
                    }
                }

            }

        }
        return EjesCoherentes;
    }

    /**
     * Asigna el color que corresponde a cada serie en el Gráfico de Dispersión
     * según el valor del ColorPicker de ComponenteGráficoDispersión
     */
    private void AsignarColoresDispersión() {
        ArrayList<Color> ListaColores = new ArrayList();
        for (Node nodo : GridGráficos.getChildren()) {
            if (nodo instanceof VBox) {
                if (((VBox) nodo).getChildren().size() == 3) {
                    HBox hb = MétodosCompartidos.getObjetoPosiciónRelativaBox(new HBox(), (VBox) nodo, 2);
                    ColorPicker selectorColor = MétodosCompartidos.getObjetoPosiciónRelativaBox(new ColorPicker(), hb, 0);
                    Color color = selectorColor.getValue();
                    ListaColores.add(color);
                }

            }
        }

        for (int i = 0; i < ListaColores.size(); i++) { // Hay tantas series como colores
            for (Node n : Gráfico.lookupAll(".default-color" + i)) {
                n.setStyle("-fx-background-color: "
                        + MétodosCompartidos.AStringHex(ListaColores.get(i)) + ";");
            }
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                int a = 0;
                for (Node n : Gráfico.lookupAll(".chart-legend-item-symbol")) {
                    n.setStyle("-fx-background-color: "
                            + MétodosCompartidos.AStringHex(ListaColores.get(a)) + ";");
                    a++;
                }
            }
        });
    }

    public Button getBotónCrearGráfico() {
        return BotónCrearGráfico;
    }

    public ComboBox getComboEjeX() {
        return ComboEjeX;
    }

    public ComboBox getComboEjeY() {
        return ComboEjeY;
    }

    public GridPane getGridGráficos() {
        return GridGráficos;
    }

    public Pane getPANEL_GRÁFICOS() {
        return PANEL_GRÁFICOS;
    }

    public void setComboEjeX(ComboBox ComboEjeX) {
        this.ComboEjeX = ComboEjeX;
    }

    public void setComboEjeY(ComboBox ComboEjeY) {
        this.ComboEjeY = ComboEjeY;
    }

}
