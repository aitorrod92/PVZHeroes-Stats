package pvzheroes.clases.diálogos;

import pvzheroes.clases.clasesParaImágenesEnCombos.IconosFactory;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.GridPane;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import pvzheroes.clases.componentes.SentenciaIndividual;
import pvzheroes.clases.diccionarios.DiccionarioSQL;
import pvzheroes.clases.FXMLDocumentController;
import pvzheroes.clases.constantes;
import pvzheroes.clases.diccionarios.DiccionarioPropiedades;
import pvzheroes.clases.efectosConcretos.CrearToolTip;
import pvzheroes.clases.métodosCompartidos.MétodosCompartidos;

public class DiálogoFiltros {

    String CategoríaSeleccionada;

    private final String[] RELACIONES_STRING = {"Contiene", "No contiene", "Es exactamente", "Es diferente de"};
    private final String[] RELACIONES_INTEGER = {"Mayor que", "Mayor o igual que", "Igual que", "Menor o igual que", "Menor que"};
    private final String[] RELACIONES_ELECCIÓN = {"Es", "No es"};

    protected static final String[] RAREZAS = {"Común", "Infrecuente", "Rara", "Superrara", "Legendaria", "Sin rareza"};
    protected static final String[] MAZOS = {"Básica", "Prémium", "Colosal", "Galáctica", "Triásica", "Token", "Evento", "Sin set"};

    private final String[] TIPOS_CARTAS = {"Planta", "Zombie"};
    private final String[] SUGERENCIAS_TRIBUS_ZOMBIES = {"Tonel", "Reloj", "Bailarín", "Zombistein", "Gourmet", "Historia", "Zombidito", "Mimo", "Monstruo", "Mostacho", "Fiesta", "Mascota", "Pirata", "Profesional", "Ciencias", "Deportista", "Truco", "Entorno", "Superpoder"};
    private final String[] SUGERENCIAS_TRIBUS_PLANTAS = {"Animal", "Banana", "Frijol", "Baya", "Cactus", "Maíz", "Dragón", "Atrapamoscas", "Flor", "Fruta", "Hoja", "Mimo", "Musgo", "Seta", "Nuez", "Guisante", "Piña", "Raíz", "Apisonaflor", "Árbol", "Semilla", "Truco", "Entorno", "Superpoder"};
    private final String[] SUGERENCIAS_HABILIDADES_COMUNES = {"Ataque extra", "Rebota", "Conjura", "Destruye", "Roba", "Congela", "Cura", "Crea", "Mueve", "No puede ser dañado", "Baraja", "Transforma", "Dinorrugido", "Evolución", "Fusión", "Sin habilidades", "Al entrar en juego", "Al ser destruido",
        "Antes del combate en esta línea", "Mientras esté en un Entorno", "Al sufrir daño", "Cuando juegas X", "Al comienzo del Turno", "Cuando juegas X en su línea", "Cuando X sea destruido", "Mientras esté en tu mano", "Cuando inflinge daño", "Cuando tus X sufran daño", "Al jugarse en Alturas",
        "Al jugarse al lado de X", "Cuando juegues un Truco", "Cuando X realice un Ataque extra", "Cuando inflinge daño a X", "Cuando inflinge daño al Héroe", "Al jugarse en un Entorno", "Cuando X es congelado", "Al final del Turno", "Cuando X inflinge daño a Y", "Cuando se juega un Truco X"};
    private final String[] SUGERENCIAS_HABILIDADES_PLANTAS = {"Después de combatir en su línea", "Cuando un Zombie le inflinge daño", "Cuando X sufra daño y sobreviva", "Cuando juegas X en Alturas", "Cuando juegas X en su línea o en las de al lado", "Al entrar en juego en el Suelo",
        "Cuando entra en juego detrás de una Planta", "Cuando X en esta línea inflinge daño al Héroe", "Cuando X de su línea inflinge daño y sobrevive", "Cuando X entra en esta línea", "Cuando otro X inflinja daño", "Cuando X o tu Héroe sea curado"};
    private final String[] SUGERENCIAS_HABILIDADES_ZOMBIES = {"Al comienzo de los Trucos", "Cuando X destruye una Planta", "Cuando X con [habilidad] destruye Y", "Cuando X sufra daño", "Cuando el Héroe Planta roba una carta", "Al jugarse en Alturas o en un Entorno",
        "Cuando se juegue una planta en esta línea", "Al poner en juego el primer Truco en cada Turno", "Cuando X entra o sale de esta línea", "Al revelarse", "Al revelarse en un entorno", "Cuando un zombie sea revelado en esta línea"};

    // Filtro que se aplica solo cuando la columna es numérica
    UnaryOperator<Change> cambio;

    private AnchorPane PanelPrincipal;
    private GridPane grid;

    private Dialog diálogo;
    private ButtonType BotónAceptar;
    private RadioButton RadioNecesaria;
    private ToggleGroup GrupoRadio;
    private ComboBox Relación;
    private TextField Valor;
    private ComboBox<String> ValorEnCombo;
    private ComboBox Categoría;

    private SuggestionProvider<String> provider;
    AutoCompletionBinding sugerencias;

    private FXMLDocumentController c;
    private DiálogoFunciónGrupo diálogoFG;
    private SentenciaIndividual CFDeOrigen;

    /**
     * Constructor con parámetros.
     *
     * @param controlador Instancia del controlador para acceder al método de
     * imágenes que no se deja cambiar de lugar (!!!!!)
     * @param diálogoFG Instancia de DiálogoFunciónGrupo en caso de que el
     * diálogo se haya originado ahí
     * @param CFDeOrigen SentenciaIndividual en caso de que el diálogo se haya
     * originado en un SentenciaIndividual
     */
    public DiálogoFiltros(FXMLDocumentController controlador, DiálogoFunciónGrupo diálogoFG, SentenciaIndividual CFDeOrigen) {
        c = controlador;
        this.diálogoFG = diálogoFG;
        this.CFDeOrigen = CFDeOrigen;

        DefinirVentana();
        ConfigurarComponentes();
        GenerarIDsYToolTips();
        Optional<ButtonType> result = diálogo.showAndWait();
        // Si es "Ok", guarda el contenido de los campos en una lista
        if (result.isPresent() && result.get().equals(BotónAceptar)) {
            String filtro = ObtenerFiltro();
            if (diálogoFG == null) {
                IncluirFiltroEnPrincipal(filtro);
            } else {
                diálogoFG.getFiltros().add(filtro);
            }
        }
    }

    /**
     * Crea la ventana y los botones
     */
    private void DefinirVentana() {
        diálogo = new Dialog();
        BotónAceptar = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        PanelPrincipal = new AnchorPane();
        diálogo.getDialogPane().setContent(PanelPrincipal);
        diálogo.getDialogPane().getButtonTypes().addAll(BotónAceptar, ButtonType.CANCEL);
        ((Stage) diálogo.getDialogPane().getScene().getWindow()).getIcons().
                add(constantes.IMÁGENES.TIPOSG.Comunes.get(0));
        
    }

    private void ConfigurarComponentes() { // AQUI NO ES NECESARIO EL CONTROLADOR EXCEPTO PARA LO DE LAS IMÁGENES
        provider = SuggestionProvider.create(Arrays.asList(SUGERENCIAS_TRIBUS_ZOMBIES));

        // Define los elementos de la ventana
        Categoría = new ComboBox(FXCollections.observableArrayList(constantes.COLUMNAS));
        Categoría.getItems().remove(constantes.COLUMNAS[11]);
        // Si estamos buscando entre todos, no tiene sentido que se incluya el tipo
        if (MétodosCompartidos.TipoSeleccionado != 2) {
            Categoría.getItems().remove(constantes.COLUMNAS[10]);
        }
        Categoría.getSelectionModel().select(0);
        Relación = new ComboBox(FXCollections.observableArrayList(RELACIONES_STRING));
        Relación.getSelectionModel().select(0);
        Valor = new TextField();
        Valor.setPromptText("Escribe un nombre");
        ValorEnCombo = new ComboBox();
        ValorEnCombo.setItems(FXCollections.observableArrayList(constantes.CARTAS.PLANTAS.Clases));
        ValorEnCombo.getSelectionModel().select(0);
        ValorEnCombo.setCellFactory(new IconosFactory());
        ValorEnCombo.setDisable(true);
        ValorEnCombo.setManaged(false);
        ValorEnCombo.setVisible(false);
        ValorEnCombo.setVisibleRowCount(5);
        GrupoRadio = new ToggleGroup();
        RadioNecesaria = new RadioButton("Necesaria");
        RadioButton RadioAlternativa = new RadioButton("Alternativa");
        RadioNecesaria.setToggleGroup(GrupoRadio);
        RadioAlternativa.setToggleGroup(GrupoRadio);

        // Formateador que sólo permite entradas numéricas en categorías numéricas
        UnaryOperator<Change> cambio = new UnaryOperator<Change>() {
            @Override
            public Change apply(Change t) {
                String input = t.getText();
                if (!input.matches("\\D")) {
                    return t;
                }
                return null;
            }
        };

        Categoría.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CategoríaSeleccionada = (String) Categoría.getSelectionModel().getSelectedItem();

                // Si la categoría es numérica, las relaciones son acordes y sólo se pueden introducir números
                if (CategoríaSeleccionada.equals("Ataque") || CategoríaSeleccionada.equals("Defensa") || CategoríaSeleccionada.equals("Coste")) {
                    Relación.setItems(FXCollections.observableArrayList(RELACIONES_INTEGER));
                    Valor.setTextFormatter(new TextFormatter(cambio));
                    Valor.setText("0");
                    Valor.requestFocus();
                    Valor.setPromptText("Escribe un valor numérico");
                    CambiarComponenteValor(true);
                    // Si la categoría no es int, no se aplica el TextFormatter
                } else {
                    Valor.setTextFormatter(null);
                    // Si la categoría es "Tipo", "Mazo" o "Rareza", las relaciones son de elección y se usa ComboBox
                    if (CategoríaSeleccionada.equals("Tipo") || CategoríaSeleccionada.equals("Mazo") || CategoríaSeleccionada.equals("Rareza")) {
                        Relación.setItems(FXCollections.observableArrayList(RELACIONES_ELECCIÓN));
                        CambiarComponenteValor(false);
                    } else { // En caso contrario, las relaciones son de STRING
                        Relación.setItems(FXCollections.observableArrayList(RELACIONES_STRING));
                        if (CategoríaSeleccionada.equals("Atributos") || CategoríaSeleccionada.equals("Clase")) {
                            CambiarComponenteValor(false);
                        } else { // Corresponde a "Nombre", "Tribus" y "Habilidades", que usan TextField
                            Valor.setText("");
                            CambiarComponenteValor(true);
                            if (CategoríaSeleccionada.equals("Nombre")) {
                                Valor.setPromptText("Escribe un nombre");
                            } else {
                                Valor.setPromptText("Escribe un valor");
                            }

                        }
                    }
                }

                Relación.getSelectionModel().select(0);
            }
        });

        grid = new GridPane();

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 40, 10, 10));

        // Añade los elementos a la ventana
        grid.add(new Label("Categoría"), 0, 0);
        grid.add(Categoría, 1, 0);
        grid.add(new Label("Relación"), 0, 1);
        grid.add(Relación, 1, 1);
        grid.add(new Label("Valor"), 0, 2);
        grid.add(Valor, 1, 2);
        grid.add(ValorEnCombo, 1, 2);
        Label EtiquetaRelaciónAnterior = new Label("Relación con\ncondición anterior");
        grid.add(EtiquetaRelaciónAnterior, 0, 3);
        HBox HBoxRadios = new HBox(RadioNecesaria, RadioAlternativa);
        HBoxRadios.setSpacing(4);
        grid.add(HBoxRadios, 1, 3);
        if ((diálogoFG == null && CFDeOrigen.FiltrosCartas.isEmpty()) || (diálogoFG != null && diálogoFG.getFiltros().isEmpty())) {
            RadioNecesaria.setDisable(true);
            RadioAlternativa.setDisable(true);
            EtiquetaRelaciónAnterior.setDisable(true);
        } else {
            RadioNecesaria.setSelected(true);
        }

        PanelPrincipal.getChildren().add(grid);
        diálogo.setTitle("Añadir filtro");
        diálogo.setHeaderText("Introduce la categoría, la relación y el valor");

    }

    private String ObtenerFiltro() {
        String SelecciónRadio = "AND (";
        if (!RadioNecesaria.isDisabled()) {
            SelecciónRadio = GrupoRadio.getSelectedToggle().toString().substring(GrupoRadio.getSelectedToggle().toString().indexOf("'") + 1, GrupoRadio.getSelectedToggle().toString().lastIndexOf("'"));
            SelecciónRadio = DiccionarioSQL.TextoASQL(SelecciónRadio);
        }

        String ValorDeRelación = Relación.getValue().toString();
        String Filtro = SelecciónRadio + Categoría.getValue().toString() + " "
                + DiccionarioSQL.TextoASQL(ValorDeRelación);
        // Si la relación es de tipo "int", no se ponen comillas
        if (Relación.getItems().get(0).equals(RELACIONES_INTEGER[0])) {
            Filtro = Filtro.concat(" " + Valor.getText());
        } else { // En caso contrario, se ponen y, dependiendo del tipo, también "%"
            String valor = "";
            if (Categoría.getValue().toString().equals("Nombre") || Categoría.getValue().toString().equals("Tribus") || Categoría.getValue().equals("Habilidades")) {
                valor = DiccionarioSQL.TextoASQL(Valor.getText());
                if (valor == (null)) {
                    valor = Valor.getText();
                }
            } else {
                valor = DiccionarioSQL.TextoASQL(ValorEnCombo.getSelectionModel().getSelectedItem());
                if (valor == null) {
                    valor = ValorEnCombo.getSelectionModel().getSelectedItem();
                }
            }
            if (ValorDeRelación.equals(RELACIONES_STRING[0]) || ValorDeRelación.equals(RELACIONES_STRING[1])) {
                Filtro = Filtro.concat(" \"%" + valor + "%\"");
            } else {
                Filtro = Filtro.concat(" \"" + valor + "\"");
            }
        }
        return Filtro;
    }

    private void IncluirFiltroEnPrincipal(String Filtro) {
        CFDeOrigen.FiltrosCartas.add(Filtro);
        MétodosCompartidos.DesplazarCeldas(true, 0, CFDeOrigen, true, false);
        // Inserta la fila con el nuevo filtro y el botón
        CFDeOrigen.addRow(0, new Label(CFDeOrigen.FiltrosCartas.get(CFDeOrigen.FiltrosCartas.size() - 1)));
        Button Botón = new Button("-");
        Botón.setMinWidth(CFDeOrigen.BotónNuevoFiltro.getWidth());
        Botón.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (DiccionarioPropiedades.PROP.getProperty("sonido").equals("ON")) {
                    new MediaPlayer(constantes.SONIDOS.BOTÓN.sonido).play();
                }
                MétodosCompartidos.EliminarFilas(Botón, CFDeOrigen, CFDeOrigen.FiltrosCartas);
            }
        });
        CFDeOrigen.addRow(0, Botón);
    }

    // Activa o desactiva el TextField Valor (y sus sugerencias) o el ComboBox ValorEnCombo según lo que esté seleccionado en Categoría
    private void CambiarComponenteValor(Boolean RequiereTextField) {
        Valor.setDisable(!RequiereTextField);
        Valor.setManaged(RequiereTextField);
        Valor.setVisible(RequiereTextField);
        ValorEnCombo.setDisable(RequiereTextField);
        ValorEnCombo.setManaged(!RequiereTextField);
        ValorEnCombo.setVisible(!RequiereTextField);

        // Activa o desactiva las sugerencias y las adapta al tipo de carta
        if (RequiereTextField) {
            if (!CategoríaSeleccionada.equals("Nombre")) {
                try {
                    sugerencias.dispose();
                } catch (NullPointerException ex) {
                    System.out.println("Sugerencias todavía no definidas.");
                }
                if (CategoríaSeleccionada.equals("Tribus")) {
                    switch (MétodosCompartidos.TipoSeleccionado) {
                        case 0:
                            VincularSugerenciasATextField(SUGERENCIAS_TRIBUS_PLANTAS);
                            break;
                        case 1:
                            VincularSugerenciasATextField(SUGERENCIAS_TRIBUS_ZOMBIES);
                            break;
                        default: // Si están los dos seleccionados, fusionamos los arrays
                            ArrayList<String> Plantas = new ArrayList(Arrays.asList(SUGERENCIAS_TRIBUS_PLANTAS));
                            ArrayList<String> Zombies = new ArrayList(Arrays.asList(SUGERENCIAS_TRIBUS_ZOMBIES));
                            ArrayList<String> SugerenciasComunes = Zombies;
                            // Para evitar duplicados
                            for (String s : Plantas) {
                                if (!Zombies.contains(s)) {
                                    SugerenciasComunes.add(s);
                                }
                            }
                            VincularSugerenciasATextField(SugerenciasComunes.toArray(new String[SugerenciasComunes.size()]));
                            break;
                    }
                } else if (CategoríaSeleccionada.equals("Habilidades")) {
                    ArrayList<String> SugerenciasComunes = new ArrayList(Arrays.asList(SUGERENCIAS_HABILIDADES_COMUNES));
                    ArrayList<String> SugerenciasPlantas = new ArrayList(Arrays.asList(SUGERENCIAS_HABILIDADES_PLANTAS));
                    ArrayList<String> SugerenciasZombies = new ArrayList(Arrays.asList(SUGERENCIAS_HABILIDADES_ZOMBIES));
                    switch (MétodosCompartidos.TipoSeleccionado) {
                        case 0:
                            SugerenciasComunes.addAll(SugerenciasPlantas);
                            break;
                        case 1:
                            SugerenciasComunes.addAll(SugerenciasZombies);
                            break;
                        default:
                            SugerenciasComunes.addAll(SugerenciasPlantas);
                            SugerenciasComunes.addAll(SugerenciasZombies);
                            break;
                    }
                    VincularSugerenciasATextField(SugerenciasComunes.toArray(new String[SugerenciasComunes.size()]));
                }
            } else {
                provider.clearSuggestions();
            }
        } else // Cambia las opciones del ComboBox disponibles dependiendo de la categoría y de si se está buscando entre plantas, zombies o ambos
        if (!RequiereTextField) {
            if (CategoríaSeleccionada.equals("Clase")) {
                switch (MétodosCompartidos.TipoSeleccionado) {
                    case 0:
                        ValorEnCombo.setItems(FXCollections.observableArrayList(constantes.CARTAS.PLANTAS.Clases));
                        ValorEnCombo.getItems().addAll(constantes.CARTAS.COMUNES.Clases);
                        break;
                    case 1:
                        ValorEnCombo.setItems(FXCollections.observableArrayList(constantes.CARTAS.ZOMBIES.Clases));
                        ValorEnCombo.getItems().addAll(constantes.CARTAS.COMUNES.Clases);
                        break;
                    default:
                        ValorEnCombo.setItems(FXCollections.observableArrayList(constantes.CARTAS.PLANTAS.Clases));
                        ValorEnCombo.getItems().addAll(constantes.CARTAS.ZOMBIES.Clases);
                        ValorEnCombo.getItems().addAll(constantes.CARTAS.COMUNES.Clases);
                        break;
                }
            } else if (CategoríaSeleccionada.equals("Atributos")) {
                switch (MétodosCompartidos.TipoSeleccionado) {
                    case 0:
                        ValorEnCombo.setItems(FXCollections.observableArrayList(constantes.CARTAS.PLANTAS.Atributos));
                        ValorEnCombo.getItems().addAll(constantes.CARTAS.COMUNES.Atributos);

                        break;
                    case 1:
                        ValorEnCombo.setItems(FXCollections.observableArrayList(constantes.CARTAS.ZOMBIES.Atributos));
                        ValorEnCombo.getItems().addAll(constantes.CARTAS.COMUNES.Atributos);
                        break;
                    default:
                        ValorEnCombo.setItems(FXCollections.observableArrayList(constantes.CARTAS.PLANTAS.Atributos));
                        ValorEnCombo.getItems().addAll(constantes.CARTAS.ZOMBIES.Atributos);
                        ValorEnCombo.getItems().addAll(constantes.CARTAS.COMUNES.Atributos);
                        break;
                }
            } else if (CategoríaSeleccionada.equals("Rareza")) {
                ValorEnCombo.setItems(FXCollections.observableArrayList(RAREZAS));
            } else if (CategoríaSeleccionada.equals("Mazo")) {
                ValorEnCombo.setItems(FXCollections.observableArrayList(MAZOS));
            } else if (CategoríaSeleccionada.equals("Tipo")) {
                ValorEnCombo.setItems(FXCollections.observableArrayList(TIPOS_CARTAS));
            }
            ValorEnCombo.getSelectionModel().select(0);
        }
    }

    /**
     * Vincula el TextField "Valor" a distintos grupos de sugerencias para
     * autocompletar, con la particularidad de que sólo se devuelven resultados
     * que empiecen por lo introducido por el usuario (sin distrinción entre
     * mayúsculas y minúsculas)
     *
     * @param ListaSugerencias La lista de sugerencias a la que se ha de asociar
     */
    private void VincularSugerenciasATextField(String[] ListaSugerencias) {
        sugerencias = TextFields.bindAutoCompletion(Valor,
                new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<String>>() {
            @Override
            public Collection<String> call(AutoCompletionBinding.ISuggestionRequest sr) {
                ArrayList<String> SugerenciasADevolver = new ArrayList();
                for (String sugerencia : ListaSugerencias) {
                    if (sugerencia.toLowerCase().startsWith(sr.getUserText().toLowerCase())) {
                        SugerenciasADevolver.add(sugerencia);
                    }
                }
                return SugerenciasADevolver;
            }
        });
        sugerencias.setVisibleRowCount(5);
    }

    private void GenerarIDsYToolTips() {
        Categoría.setId(constantes.NOMBRES_ELEMENTOS.DF_SELECCIÓN_CATEGORÍA.Nombre);
        new CrearToolTip(Categoría, this.PanelPrincipal, 11);
        Relación.setId(constantes.NOMBRES_ELEMENTOS.DF_SELECCIÓN_RELACIÓN.Nombre);
        new CrearToolTip(Relación, this.PanelPrincipal, 11);
        Valor.setId(constantes.NOMBRES_ELEMENTOS.DF_SELECCIÓN_VALOR.Nombre);
        new CrearToolTip(Valor, this.PanelPrincipal, 11);
        ((RadioButton) GrupoRadio.getToggles().get(0)).setId(constantes.NOMBRES_ELEMENTOS.DF_SELECCIÓN_RELACIÓN_CONDICIÓN.Nombre);
        new CrearToolTip((RadioButton) GrupoRadio.getToggles().get(0), this.PanelPrincipal, 11);
    }

}
