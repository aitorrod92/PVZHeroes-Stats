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
import pvzheroes.clases.clasesParaIm�genesEnCombos.IconosFactory;
import pvzheroes.clases.componentes.ComponenteGr�ficoDispersi�n;
import pvzheroes.clases.componentes.ComponenteGr�ficoGrupal;
import pvzheroes.clases.efectosConcretos.CrearToolTip;
import pvzheroes.clases.m�todosCompartidos.M�todosCompartidos;
import pvzheroes.clases.efectosConcretos.DestacarPorDatoGr�ficosPie;
import pvzheroes.clases.efectosConcretos.DestacarPorDatoGr�ficosXY;

public class ModoGr�ficos {

    public static Boolean MostrarFilasPorColor = true;

    private final Insets INSETS = new Insets(20, 0, 0, 20);
    private final String[] TIPOS_GR�FICOS = {"Barras", "Barras apiladas", "Dispersi�n", "Tarta"};

    private final Pane PANEL_GR�FICOS;
    private final GridPane GridGr�ficos;
    private final FXMLDocumentController DC;
    private final M�todosCompartidos MC; // PROBAR A ELIMINARLO
    public static ArrayList<SentenciasGrupales> ListadoCFG;
    public static ArrayList<SentenciaIndividual> ListadoCFil;
    public static ArrayList<ComponenteGr�ficoDispersi�n> ListadoCGD;
    private Chart Gr�fico;
    private Button Bot�nNuevaSerieBarras;
    private Button Bot�nNuevaSerieDispersi�n;
    private ComboBox Selecci�nTipoGr�fico;

    private ComboBox ComboEjeX;
    private ComboBox ComboEjeY;
    private Button Bot�nCrearGr�fico;

    private static int ValorM�ximoX = 0;
    private static int ValorM�nimoX = 11;
    private static int ValorM�ximoY = 0;
    private static int ValorM�nimoY = 11;

    public ModoGr�ficos(Pane Panel, FXMLDocumentController dc) {
        PANEL_GR�FICOS = Panel;
        GridGr�ficos = (GridPane) PANEL_GR�FICOS.getChildren().get(0);
        DC = dc;
        MC = new M�todosCompartidos();
        ListadoCFG = new ArrayList();
        ListadoCFil = new ArrayList();
        ListadoCGD = new ArrayList();
    }

    public void ConfigurarInterfaz() {
        GridGr�ficos.setId("GridGr�ficos");
        GridGr�ficos.add(new ComponenteGr�ficoGrupal(this, true), 1, 0);
        SentenciasGrupales cfgGr�ficos = new SentenciasGrupales(DC, this, 2);
        ListadoCFG.add(cfgGr�ficos);
        GridGr�ficos.add(cfgGr�ficos, 1, 1);

        ComponenteGr�ficoDispersi�n cgd = new ComponenteGr�ficoDispersi�n(this, 1, true);
        cgd.setVisible(false);
        GridGr�ficos.add(cgd, 1, 0);
        GridPane.setMargin(cgd, INSETS);
        SentenciaIndividual cf = new SentenciaIndividual(DC, this, 2);
        ListadoCFil.add(cf);
        ListadoCGD.add(cgd);
        cf.setVisible(false);
        GridGr�ficos.add(cf, 1, 1);

        Selecci�nTipoGr�fico = MC.CrearComboBox(TIPOS_GR�FICOS, 0);
        Selecci�nTipoGr�fico.setId(constantes.NOMBRES_ELEMENTOS.SELECCI�N_TIPO_GR�FICOS.Nombre);
        new CrearToolTip(Selecci�nTipoGr�fico, this.PANEL_GR�FICOS, 2);
        Selecci�nTipoGr�fico.setCellFactory(new IconosFactory());
        Selecci�nTipoGr�fico.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                String seleccionado = Selecci�nTipoGr�fico.getSelectionModel().getSelectedItem().toString();
                Boolean DeshabilitarCFG = false;
                if (seleccionado.equals("Dispersi�n")) {
                    DeshabilitarCFG = true;
                } else if (seleccionado.equals("Barras") || seleccionado.equals("Barras apiladas") || seleccionado.equals("Tarta")) {
                    DeshabilitarCFG = false;
                }
                for (Node nodo : GridGr�ficos.getChildren()) {
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
                                Bot�nNuevaSerieBarras.setVisible(true);
                                Bot�nNuevaSerieDispersi�n.setVisible(false);
                            } else {
                                Bot�nNuevaSerieBarras.setVisible(false);
                                Bot�nNuevaSerieDispersi�n.setVisible(true);
                            }
                        }
                    }
                }
            }
        }
        );

        Bot�nCrearGr�fico = new Button("Crear gr�fico");
        Bot�nCrearGr�fico.setId(constantes.NOMBRES_ELEMENTOS.BOT�N_CREAR_GR�FICO.Nombre);
        new CrearToolTip(Bot�nCrearGr�fico, this.PANEL_GR�FICOS, 2);
        Bot�nCrearGr�fico.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                if (DiccionarioPropiedades.PROP.getProperty("sonido").equals("ON")) {
                    new MediaPlayer(constantes.SONIDOS.BUSCAR_O_GR�FICO.sonido).play();
                }
                if (Selecci�nTipoGr�fico.getSelectionModel().getSelectedItem().toString().equals("Dispersi�n")) {
                    if (ModoGr�ficos.this.Comprobaci�nEjesDispersi�n() == false) {
                        event.consume();
                        Dialog di�logo = new Dialog();
                        di�logo.setContentText("Est�s a punto de crear una gr�fica en la que los ejes X e Y "
                                + "\nno son coherentes entre distintas series.\n�Deseas continuar?");
                        di�logo.setTitle("Confirmaci�n de creaci�n");
                        di�logo.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
                        Optional<ButtonType> confirmaci�n = di�logo.showAndWait();
                        if (confirmaci�n.isPresent()) {
                            if (confirmaci�n.get().equals(ButtonType.CANCEL)) {
                                return;
                            }
                        }
                    }
                }

                Chart Gr�ficoAEliminar = null;
                for (Node nodo : GridGr�ficos.getChildren()) {
                    if (nodo instanceof Chart) {
                        Gr�ficoAEliminar = (Chart) nodo;
                        break;
                    }
                }
                GridGr�ficos.getChildren().remove(Gr�ficoAEliminar);

                Boolean Gr�ficoDispersi�n = false;
                try {
                    ArrayList<ArrayList> ListasResultados = new ArrayList();
                    for (Node nodo : GridGr�ficos.getChildren()) {
                        if (nodo instanceof SentenciasGrupales && nodo.isVisible()) { // Barras normales, apiladas o Tarta
                            ArrayList<String> resultados = MC.B�squedaGrupal((SentenciasGrupales) nodo, conn);
                            ListasResultados.add(resultados);
                        } else if (nodo instanceof SentenciaIndividual && nodo.isVisible()) { // Dispersi�n
                            ArrayList<Carta> resultados = MC.B�squeda(((SentenciaIndividual) nodo).FiltrosCartas, this);
                            ListasResultados.add(resultados);
                            Gr�ficoDispersi�n = true;
                        }
                    }
                    String TipoDeGr�fico = Selecci�nTipoGr�fico.getSelectionModel().getSelectedItem().toString();
                    Gr�fico = CrearGr�fico(TipoDeGr�fico, ListasResultados);
                    int N�meroColumnas = M�todosCompartidos.ObtenerN�meroColumnas(GridGr�ficos);
                    GridGr�ficos.add(Gr�fico, 0, 2, N�meroColumnas, 1);
                    int N�meroDeGr�fico = Selecci�nTipoGr�fico.getSelectionModel().getSelectedIndex();
                    new CrearToolTip(Gr�fico, ModoGr�ficos.this.PANEL_GR�FICOS, CrearToolTip.InicioGr�ficos 
                            + N�meroDeGr�fico);

                    // Modificaciones posteriores a la adicci�n de los nodos del gr�fico
                    if (Gr�ficoDispersi�n) {
                        AsignarColoresDispersi�n();
                    }

                } catch (SQLException ex) {
                    System.out.println("Error en la b�squeda");
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                    System.out.println(ex.getMessage());
                }

            }
        }
        );

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                M�todosCompartidos.EstablecerAceleradorEnBot�n(Bot�nCrearGr�fico, new KeyCodeCombination(KeyCode.ENTER), null, null);
            }
        });

        VBox VBoxBotones = new VBox();
        VBoxBotones.getChildren().addAll(Selecci�nTipoGr�fico, Bot�nCrearGr�fico);
        GridGr�ficos.add(VBoxBotones, 0, 1);
        Platform.runLater(() -> Bot�nCrearGr�fico.setMinWidth(VBoxBotones.getWidth() - 10));

        Bot�nNuevaSerieBarras = new Button("Nueva serie");
        Bot�nNuevaSerieBarras.setId(constantes.NOMBRES_ELEMENTOS.BOT�N_NUEVA_SERIE_BARRAS.Nombre);
        new CrearToolTip(Bot�nNuevaSerieBarras, PANEL_GR�FICOS, 2);
        GridGr�ficos.add(Bot�nNuevaSerieBarras, 2, 0);
        Bot�nNuevaSerieBarras.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                if (DiccionarioPropiedades.PROP.getProperty("sonido").equals("ON")) {
                    new MediaPlayer(constantes.SONIDOS.BOT�N.sonido).play();
                }
                int N�meroColumna = GridPane.getColumnIndex((Node) Bot�nNuevaSerieBarras);
                ComponenteGr�ficoGrupal CGG = new ComponenteGr�ficoGrupal(ModoGr�ficos.this, false);
                GridGr�ficos.add(CGG, N�meroColumna, 0);

                SentenciasGrupales cfge = new SentenciasGrupales(null, ModoGr�ficos.this, -1);
                cfge.setDisable(false);
                ListadoCFG.add(cfge);
                GridGr�ficos.add(cfge, N�meroColumna, 1);

                GridPane.setConstraints(CGG, N�meroColumna, 0, 1, 1, HPos.LEFT, VPos.BASELINE, Priority.SOMETIMES, Priority.NEVER, INSETS);
                GridPane.setMargin(cfge, INSETS);
                GridPane.setFillWidth(cfge, false);

                try {
                    GridGr�ficos.add(Gr�fico, 0, 2, M�todosCompartidos.ObtenerN�meroColumnas(GridGr�ficos), 1);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NullPointerException | IllegalArgumentException ex) {
                    System.out.println(ex.getCause());
                }

                GridGr�ficos.getChildren().remove(Bot�nNuevaSerieBarras);
                GridGr�ficos.add(Bot�nNuevaSerieBarras, N�meroColumna + 1, 0);
                GridPane.setMargin(Bot�nNuevaSerieBarras, INSETS);
                GridGr�ficos.getChildren().remove(Gr�fico);
            }
        }
        );

        Bot�nNuevaSerieDispersi�n = new Button("Nueva serie");
        Bot�nNuevaSerieDispersi�n.setId(constantes.NOMBRES_ELEMENTOS.BOT�N_NUEVA_SERIE_DISPERSI�N.Nombre);
        new CrearToolTip(Bot�nNuevaSerieDispersi�n, PANEL_GR�FICOS, 2);
        Bot�nNuevaSerieDispersi�n.setVisible(false);
        GridGr�ficos.add(Bot�nNuevaSerieDispersi�n, 2, 0);
        Bot�nNuevaSerieDispersi�n.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                int N�meroColumna = GridPane.getColumnIndex((Node) Bot�nNuevaSerieDispersi�n);
                ComponenteGr�ficoDispersi�n NuevoCGD = new ComponenteGr�ficoDispersi�n(ModoGr�ficos.this, N�meroColumna, false);
                GridGr�ficos.add(NuevoCGD, N�meroColumna, 0);
                SentenciaIndividual cfe = new SentenciaIndividual(ModoGr�ficos.this.DC, ModoGr�ficos.this, -1);
                GridGr�ficos.add(cfe, N�meroColumna, 1);
                ListadoCFil.add(cfe);
                ListadoCGD.add(NuevoCGD);
                ((TextField) ((HBox) NuevoCGD.getChildren().get(0)).getChildren().get(0)).setPromptText("Nombre de la serie " + GridPane.getColumnIndex(NuevoCGD));
                GridPane.setMargin(NuevoCGD, INSETS);
                GridPane.setMargin(cfe, INSETS);
                GridPane.setFillWidth(cfe, Boolean.FALSE);

                GridGr�ficos.getChildren().remove(Bot�nNuevaSerieDispersi�n);
                GridGr�ficos.add(Bot�nNuevaSerieDispersi�n, N�meroColumna + 1, 0);
                GridPane.setMargin(Bot�nNuevaSerieDispersi�n, INSETS);
                GridGr�ficos.getChildren().remove(Gr�fico);
            }
        });

        Platform.runLater(
                () -> {
                    for (Node nodo : GridGr�ficos.getChildren()) {
                        GridPane.setMargin(nodo, INSETS);
                    }
                }
        );

    }

    public Chart CrearGr�fico(String TipoGr�fico, ArrayList<ArrayList> listadoresultados) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, SQLException {
        Chart Gr�ficoACrear = null;
        NumberAxis ejeY = new NumberAxis();
        if (!TipoGr�fico.equals("Tarta")) {
            if (TipoGr�fico.equals("Barras") || TipoGr�fico.equals("Barras apiladas")) {
                CategoryAxis ejeX = new CategoryAxis();
                if (TipoGr�fico.equals("Barras")) {
                    BarChart Gr�ficoDeBarras = new BarChart(ejeX, ejeY);
                    Gr�ficoACrear = CrearGr�ficoXY(Gr�ficoDeBarras, listadoresultados);
                    Gr�ficoACrear.setId(constantes.NOMBRES_ELEMENTOS.GR�FICO_BARRAS.Nombre);
                } else {
                    StackedBarChart Gr�ficoDeBarrasA = new StackedBarChart<>(ejeX, ejeY);
                    Gr�ficoACrear = CrearGr�ficoXY(Gr�ficoDeBarrasA, listadoresultados);
                    Gr�ficoACrear.setId(constantes.NOMBRES_ELEMENTOS.GR�FICO_BARRAS_APILADAS.Nombre);
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

                ScatterChart Gr�ficoDispersi�n = new ScatterChart<>(ejeX, ejeY);
                Gr�ficoACrear = CrearGr�ficoXY(Gr�ficoDispersi�n, listadoresultados);
                Gr�ficoACrear.setId(constantes.NOMBRES_ELEMENTOS.GR�FICO_DISPERSI�N.Nombre);
                ejeX.setUpperBound(ValorM�ximoX + 1);
                ejeX.setLowerBound(ValorM�nimoX - 1);
                ejeY.setUpperBound(ValorM�ximoY + 1);
                ejeY.setLowerBound(ValorM�nimoY - 1);
            }
        } else {
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for (int i = 0; i < listadoresultados.get(0).size(); i++) {
                String resultado = ((ArrayList<String>) listadoresultados.get(0)).get(i);
                String Identificador = ((Label) ListadoCFG.get(0).getChildren().get((i * 2) + 1)).getText();
                PieChart.Data dato = new PieChart.Data(Identificador, Double.valueOf(resultado.replace(",", ".")));
                pieChartData.add(dato);
                M�todosCompartidos.setToolTipDatoGr�fico(dato, null);
                Platform.runLater(new DestacarPorDatoGr�ficosPie(pieChartData, dato));

            }

            Gr�ficoACrear = new PieChart(pieChartData);
            Gr�ficoACrear.setId(constantes.NOMBRES_ELEMENTOS.GR�FICO_TARTA.Nombre);

        }
        return Gr�ficoACrear;
    }

    private Chart CrearGr�ficoXY(Chart Gr�fico, ArrayList<ArrayList> listadoresultados) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ArrayList<XYChart.Series> Series = new ArrayList();
        if (Gr�fico instanceof BarChart || Gr�fico instanceof StackedBarChart) {
            int x = 0;
            for (SentenciasGrupales sentenciasGrupales : ListadoCFG) {
                XYChart.Series serie = new XYChart.Series<>();
                String PrimeraSentencia = "";
                int y = 0;
                // S�lo los componentes que tienen al menos un filtro
                if (sentenciasGrupales.getChildren().size() > 1) {
                    for (Node nodo : sentenciasGrupales.getChildren()) {
                        if (nodo instanceof Label) {
                            double valor = MC.ConvertirStringADoble((String) listadoresultados.
                                    get(x).get(y));
                            PrimeraSentencia = ((Label) nodo).getText();
                            XYChart.Data dato = new XYChart.Data(((Label) nodo).getText(), valor);
                            serie.getData().add(dato);

                            M�todosCompartidos.setToolTipDatoGr�fico(dato, null);
                            Platform.runLater(new DestacarPorDatoGr�ficosXY(Series, dato));
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

            if (Gr�fico instanceof BarChart) {
                BarChart Gr�ficoB = (BarChart) Gr�fico;
                for (XYChart.Series s : Series) {
                    Gr�ficoB.getData().addAll(s);
                }
                return Gr�ficoB;
            } else if (Gr�fico instanceof StackedBarChart) {
                StackedBarChart Gr�ficoBA = (StackedBarChart) Gr�fico;
                for (XYChart.Series s : Series) {
                    Gr�ficoBA.getData().addAll(s);
                }
                return Gr�ficoBA;
            }

            return null;
        } else if (Gr�fico instanceof ScatterChart) {
            ValorM�ximoX = 0;
            ValorM�nimoX = 11;
            ValorM�ximoY = 0;
            ValorM�nimoY = 11;

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

                    if (ValorX > ValorM�ximoX) {
                        ValorM�ximoX = ValorX;
                    } else if (ValorX < ValorM�nimoX) {
                        ValorM�nimoX = ValorX;
                    }

                    if (ValorY > ValorM�ximoY) {
                        ValorM�ximoY = ValorY;
                    } else if (ValorY < ValorM�nimoY) {
                        ValorM�nimoY = ValorY;
                    }

                    XYChart.Data marca = new XYChart.Data(ValorX, ValorY);
                    serie.getData().add(marca);

                    Platform.runLater(new DestacarPorDatoGr�ficosXY(Series, marca));
                    // S�lo se crea para la �ltima serie
                    M�todosCompartidos.setToolTipDatoGr�fico(marca, listadoresultados);
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

                ComponenteGr�ficoDispersi�n CajaVertical = M�todosCompartidos.getObjetoPosici�nRelativaGrid(new ComponenteGr�ficoDispersi�n(), GridGr�ficos, ListadoCFil.get(i), 0, -1);
                HBox CajaHorizontal = M�todosCompartidos.getObjetoPosici�nRelativaBox(new HBox(), CajaVertical, 0);
                TextField Campo = M�todosCompartidos.getObjetoPosici�nRelativaBox(new TextField(), CajaHorizontal, 0);
                serie.setName(Campo.getText());
                x++;
            }
            ScatterChart Gr�ficoD = (ScatterChart) Gr�fico;
            Gr�ficoD.getData().addAll(Series);

            return Gr�ficoD;
        }
        return null;
    }

    /**
     * Comprueba que todos los ejes del gr�fico de dispersi�n son coherentes
     * entre s�
     *
     * @return Booleano confirm�ndolo o desminti�ndolo
     */
    private Boolean Comprobaci�nEjesDispersi�n() {
        Boolean EjesCoherentes = true;
        String ValorX = "";
        String ValorY = "";
        Boolean PrimerParCombos = true;
        for (Node nodo : GridGr�ficos.getChildrenUnmodifiable()) {
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
     * Asigna el color que corresponde a cada serie en el Gr�fico de Dispersi�n
     * seg�n el valor del ColorPicker de ComponenteGr�ficoDispersi�n
     */
    private void AsignarColoresDispersi�n() {
        ArrayList<Color> ListaColores = new ArrayList();
        for (Node nodo : GridGr�ficos.getChildren()) {
            if (nodo instanceof VBox) {
                if (((VBox) nodo).getChildren().size() == 3) {
                    HBox hb = M�todosCompartidos.getObjetoPosici�nRelativaBox(new HBox(), (VBox) nodo, 2);
                    ColorPicker selectorColor = M�todosCompartidos.getObjetoPosici�nRelativaBox(new ColorPicker(), hb, 0);
                    Color color = selectorColor.getValue();
                    ListaColores.add(color);
                }

            }
        }

        for (int i = 0; i < ListaColores.size(); i++) { // Hay tantas series como colores
            for (Node n : Gr�fico.lookupAll(".default-color" + i)) {
                n.setStyle("-fx-background-color: "
                        + M�todosCompartidos.AStringHex(ListaColores.get(i)) + ";");
            }
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                int a = 0;
                for (Node n : Gr�fico.lookupAll(".chart-legend-item-symbol")) {
                    n.setStyle("-fx-background-color: "
                            + M�todosCompartidos.AStringHex(ListaColores.get(a)) + ";");
                    a++;
                }
            }
        });
    }

    public Button getBot�nCrearGr�fico() {
        return Bot�nCrearGr�fico;
    }

    public ComboBox getComboEjeX() {
        return ComboEjeX;
    }

    public ComboBox getComboEjeY() {
        return ComboEjeY;
    }

    public GridPane getGridGr�ficos() {
        return GridGr�ficos;
    }

    public Pane getPANEL_GR�FICOS() {
        return PANEL_GR�FICOS;
    }

    public void setComboEjeX(ComboBox ComboEjeX) {
        this.ComboEjeX = ComboEjeX;
    }

    public void setComboEjeY(ComboBox ComboEjeY) {
        this.ComboEjeY = ComboEjeY;
    }

}
