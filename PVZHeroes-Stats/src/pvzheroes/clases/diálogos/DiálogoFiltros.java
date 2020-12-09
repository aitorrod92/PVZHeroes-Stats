package pvzheroes.clases.di�logos;

import pvzheroes.clases.clasesParaIm�genesEnCombos.IconosFactory;
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
import pvzheroes.clases.m�todosCompartidos.M�todosCompartidos;

public class Di�logoFiltros {

    String Categor�aSeleccionada;

    private final String[] RELACIONES_STRING = {"Contiene", "No contiene", "Es exactamente", "Es diferente de"};
    private final String[] RELACIONES_INTEGER = {"Mayor que", "Mayor o igual que", "Igual que", "Menor o igual que", "Menor que"};
    private final String[] RELACIONES_ELECCI�N = {"Es", "No es"};

    protected static final String[] RAREZAS = {"Com�n", "Infrecuente", "Rara", "Superrara", "Legendaria", "Sin rareza"};
    protected static final String[] MAZOS = {"B�sica", "Pr�mium", "Colosal", "Gal�ctica", "Tri�sica", "Token", "Evento", "Sin set"};

    private final String[] TIPOS_CARTAS = {"Planta", "Zombie"};
    private final String[] SUGERENCIAS_TRIBUS_ZOMBIES = {"Tonel", "Reloj", "Bailar�n", "Zombistein", "Gourmet", "Historia", "Zombidito", "Mimo", "Monstruo", "Mostacho", "Fiesta", "Mascota", "Pirata", "Profesional", "Ciencias", "Deportista", "Truco", "Entorno", "Superpoder"};
    private final String[] SUGERENCIAS_TRIBUS_PLANTAS = {"Animal", "Banana", "Frijol", "Baya", "Cactus", "Ma�z", "Drag�n", "Atrapamoscas", "Flor", "Fruta", "Hoja", "Mimo", "Musgo", "Seta", "Nuez", "Guisante", "Pi�a", "Ra�z", "Apisonaflor", "�rbol", "Semilla", "Truco", "Entorno", "Superpoder"};
    private final String[] SUGERENCIAS_HABILIDADES_COMUNES = {"Ataque extra", "Rebota", "Conjura", "Destruye", "Roba", "Congela", "Cura", "Crea", "Mueve", "No puede ser da�ado", "Baraja", "Transforma", "Dinorrugido", "Evoluci�n", "Fusi�n", "Sin habilidades", "Al entrar en juego", "Al ser destruido",
        "Antes del combate en esta l�nea", "Mientras est� en un Entorno", "Al sufrir da�o", "Cuando juegas X", "Al comienzo del Turno", "Cuando juegas X en su l�nea", "Cuando X sea destruido", "Mientras est� en tu mano", "Cuando inflinge da�o", "Cuando tus X sufran da�o", "Al jugarse en Alturas",
        "Al jugarse al lado de X", "Cuando juegues un Truco", "Cuando X realice un Ataque extra", "Cuando inflinge da�o a X", "Cuando inflinge da�o al H�roe", "Al jugarse en un Entorno", "Cuando X es congelado", "Al final del Turno", "Cuando X inflinge da�o a Y", "Cuando se juega un Truco X"};
    private final String[] SUGERENCIAS_HABILIDADES_PLANTAS = {"Despu�s de combatir en su l�nea", "Cuando un Zombie le inflinge da�o", "Cuando X sufra da�o y sobreviva", "Cuando juegas X en Alturas", "Cuando juegas X en su l�nea o en las de al lado", "Al entrar en juego en el Suelo",
        "Cuando entra en juego detr�s de una Planta", "Cuando X en esta l�nea inflinge da�o al H�roe", "Cuando X de su l�nea inflinge da�o y sobrevive", "Cuando X entra en esta l�nea", "Cuando otro X inflinja da�o", "Cuando X o tu H�roe sea curado"};
    private final String[] SUGERENCIAS_HABILIDADES_ZOMBIES = {"Al comienzo de los Trucos", "Cuando X destruye una Planta", "Cuando X con [habilidad] destruye Y", "Cuando X sufra da�o", "Cuando el H�roe Planta roba una carta", "Al jugarse en Alturas o en un Entorno",
        "Cuando se juegue una planta en esta l�nea", "Al poner en juego el primer Truco en cada Turno", "Cuando X entra o sale de esta l�nea", "Al revelarse", "Al revelarse en un entorno", "Cuando un zombie sea revelado en esta l�nea"};

    // Filtro que se aplica solo cuando la columna es num�rica
    UnaryOperator<Change> cambio;

    private AnchorPane PanelPrincipal;
    private GridPane grid;

    private Dialog di�logo;
    private ButtonType Bot�nAceptar;
    private RadioButton RadioNecesaria;
    private ToggleGroup GrupoRadio;
    private ComboBox Relaci�n;
    private TextField Valor;
    private ComboBox<String> ValorEnCombo;
    private ComboBox Categor�a;

    private SuggestionProvider<String> provider;
    AutoCompletionBinding sugerencias;

    private FXMLDocumentController c;
    private Di�logoFunci�nGrupo di�logoFG;
    private SentenciaIndividual CFDeOrigen;

    /**
     * Constructor con par�metros.
     *
     * @param controlador Instancia del controlador para acceder al m�todo de
     * im�genes que no se deja cambiar de lugar (!!!!!)
     * @param di�logoFG Instancia de Di�logoFunci�nGrupo en caso de que el
     * di�logo se haya originado ah�
     * @param CFDeOrigen SentenciaIndividual en caso de que el di�logo se haya
     * originado en un SentenciaIndividual
     */
    public Di�logoFiltros(FXMLDocumentController controlador, Di�logoFunci�nGrupo di�logoFG, SentenciaIndividual CFDeOrigen) {
        c = controlador;
        this.di�logoFG = di�logoFG;
        this.CFDeOrigen = CFDeOrigen;

        DefinirVentana();
        ConfigurarComponentes();
        GenerarIDsYToolTips();
        Optional<ButtonType> result = di�logo.showAndWait();
        // Si es "Ok", guarda el contenido de los campos en una lista
        if (result.isPresent() && result.get().equals(Bot�nAceptar)) {
            String filtro = ObtenerFiltro();
            if (di�logoFG == null) {
                IncluirFiltroEnPrincipal(filtro);
            } else {
                di�logoFG.getFiltros().add(filtro);
            }
        }
    }

    /**
     * Crea la ventana y los botones
     */
    private void DefinirVentana() {
        di�logo = new Dialog();
        Bot�nAceptar = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        PanelPrincipal = new AnchorPane();
        di�logo.getDialogPane().setContent(PanelPrincipal);
        di�logo.getDialogPane().getButtonTypes().addAll(Bot�nAceptar, ButtonType.CANCEL);
        ((Stage) di�logo.getDialogPane().getScene().getWindow()).getIcons().
                add(constantes.IM�GENES.TIPOSG.Comunes.get(0));
        
    }

    private void ConfigurarComponentes() { // AQUI NO ES NECESARIO EL CONTROLADOR EXCEPTO PARA LO DE LAS IM�GENES
        provider = SuggestionProvider.create(Arrays.asList(SUGERENCIAS_TRIBUS_ZOMBIES));

        // Define los elementos de la ventana
        Categor�a = new ComboBox(FXCollections.observableArrayList(constantes.COLUMNAS));
        Categor�a.getItems().remove(constantes.COLUMNAS[11]);
        // Si estamos buscando entre todos, no tiene sentido que se incluya el tipo
        if (M�todosCompartidos.TipoSeleccionado != 2) {
            Categor�a.getItems().remove(constantes.COLUMNAS[10]);
        }
        Categor�a.getSelectionModel().select(0);
        Relaci�n = new ComboBox(FXCollections.observableArrayList(RELACIONES_STRING));
        Relaci�n.getSelectionModel().select(0);
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

        // Formateador que s�lo permite entradas num�ricas en categor�as num�ricas
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

        Categor�a.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Categor�aSeleccionada = (String) Categor�a.getSelectionModel().getSelectedItem();

                // Si la categor�a es num�rica, las relaciones son acordes y s�lo se pueden introducir n�meros
                if (Categor�aSeleccionada.equals("Ataque") || Categor�aSeleccionada.equals("Defensa") || Categor�aSeleccionada.equals("Coste")) {
                    Relaci�n.setItems(FXCollections.observableArrayList(RELACIONES_INTEGER));
                    Valor.setTextFormatter(new TextFormatter(cambio));
                    Valor.setText("0");
                    Valor.requestFocus();
                    Valor.setPromptText("Escribe un valor num�rico");
                    CambiarComponenteValor(true);
                    // Si la categor�a no es int, no se aplica el TextFormatter
                } else {
                    Valor.setTextFormatter(null);
                    // Si la categor�a es "Tipo", "Mazo" o "Rareza", las relaciones son de elecci�n y se usa ComboBox
                    if (Categor�aSeleccionada.equals("Tipo") || Categor�aSeleccionada.equals("Mazo") || Categor�aSeleccionada.equals("Rareza")) {
                        Relaci�n.setItems(FXCollections.observableArrayList(RELACIONES_ELECCI�N));
                        CambiarComponenteValor(false);
                    } else { // En caso contrario, las relaciones son de STRING
                        Relaci�n.setItems(FXCollections.observableArrayList(RELACIONES_STRING));
                        if (Categor�aSeleccionada.equals("Atributos") || Categor�aSeleccionada.equals("Clase")) {
                            CambiarComponenteValor(false);
                        } else { // Corresponde a "Nombre", "Tribus" y "Habilidades", que usan TextField
                            Valor.setText("");
                            CambiarComponenteValor(true);
                            if (Categor�aSeleccionada.equals("Nombre")) {
                                Valor.setPromptText("Escribe un nombre");
                            } else {
                                Valor.setPromptText("Escribe un valor");
                            }

                        }
                    }
                }

                Relaci�n.getSelectionModel().select(0);
            }
        });

        grid = new GridPane();

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 40, 10, 10));

        // A�ade los elementos a la ventana
        grid.add(new Label("Categor�a"), 0, 0);
        grid.add(Categor�a, 1, 0);
        grid.add(new Label("Relaci�n"), 0, 1);
        grid.add(Relaci�n, 1, 1);
        grid.add(new Label("Valor"), 0, 2);
        grid.add(Valor, 1, 2);
        grid.add(ValorEnCombo, 1, 2);
        Label EtiquetaRelaci�nAnterior = new Label("Relaci�n con\ncondici�n anterior");
        grid.add(EtiquetaRelaci�nAnterior, 0, 3);
        HBox HBoxRadios = new HBox(RadioNecesaria, RadioAlternativa);
        HBoxRadios.setSpacing(4);
        grid.add(HBoxRadios, 1, 3);
        if ((di�logoFG == null && CFDeOrigen.FiltrosCartas.isEmpty()) || (di�logoFG != null && di�logoFG.getFiltros().isEmpty())) {
            RadioNecesaria.setDisable(true);
            RadioAlternativa.setDisable(true);
            EtiquetaRelaci�nAnterior.setDisable(true);
        } else {
            RadioNecesaria.setSelected(true);
        }

        PanelPrincipal.getChildren().add(grid);
        di�logo.setTitle("A�adir filtro");
        di�logo.setHeaderText("Introduce la categor�a, la relaci�n y el valor");

    }

    private String ObtenerFiltro() {
        String Selecci�nRadio = "AND (";
        if (!RadioNecesaria.isDisabled()) {
            Selecci�nRadio = GrupoRadio.getSelectedToggle().toString().substring(GrupoRadio.getSelectedToggle().toString().indexOf("'") + 1, GrupoRadio.getSelectedToggle().toString().lastIndexOf("'"));
            Selecci�nRadio = DiccionarioSQL.TextoASQL(Selecci�nRadio);
        }

        String ValorDeRelaci�n = Relaci�n.getValue().toString();
        String Filtro = Selecci�nRadio + Categor�a.getValue().toString() + " "
                + DiccionarioSQL.TextoASQL(ValorDeRelaci�n);
        // Si la relaci�n es de tipo "int", no se ponen comillas
        if (Relaci�n.getItems().get(0).equals(RELACIONES_INTEGER[0])) {
            Filtro = Filtro.concat(" " + Valor.getText());
        } else { // En caso contrario, se ponen y, dependiendo del tipo, tambi�n "%"
            String valor = "";
            if (Categor�a.getValue().toString().equals("Nombre") || Categor�a.getValue().toString().equals("Tribus") || Categor�a.getValue().equals("Habilidades")) {
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
            if (ValorDeRelaci�n.equals(RELACIONES_STRING[0]) || ValorDeRelaci�n.equals(RELACIONES_STRING[1])) {
                Filtro = Filtro.concat(" \"%" + valor + "%\"");
            } else {
                Filtro = Filtro.concat(" \"" + valor + "\"");
            }
        }
        return Filtro;
    }

    private void IncluirFiltroEnPrincipal(String Filtro) {
        CFDeOrigen.FiltrosCartas.add(Filtro);
        M�todosCompartidos.DesplazarCeldas(true, 0, CFDeOrigen, true, false);
        // Inserta la fila con el nuevo filtro y el bot�n
        CFDeOrigen.addRow(0, new Label(CFDeOrigen.FiltrosCartas.get(CFDeOrigen.FiltrosCartas.size() - 1)));
        Button Bot�n = new Button("-");
        Bot�n.setMinWidth(CFDeOrigen.Bot�nNuevoFiltro.getWidth());
        Bot�n.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (DiccionarioPropiedades.PROP.getProperty("sonido").equals("ON")) {
                    new MediaPlayer(constantes.SONIDOS.BOT�N.sonido).play();
                }
                M�todosCompartidos.EliminarFilas(Bot�n, CFDeOrigen, CFDeOrigen.FiltrosCartas);
            }
        });
        CFDeOrigen.addRow(0, Bot�n);
    }

    // Activa o desactiva el TextField Valor (y sus sugerencias) o el ComboBox ValorEnCombo seg�n lo que est� seleccionado en Categor�a
    private void CambiarComponenteValor(Boolean RequiereTextField) {
        Valor.setDisable(!RequiereTextField);
        Valor.setManaged(RequiereTextField);
        Valor.setVisible(RequiereTextField);
        ValorEnCombo.setDisable(RequiereTextField);
        ValorEnCombo.setManaged(!RequiereTextField);
        ValorEnCombo.setVisible(!RequiereTextField);

        // Activa o desactiva las sugerencias y las adapta al tipo de carta
        if (RequiereTextField) {
            if (!Categor�aSeleccionada.equals("Nombre")) {
                try {
                    sugerencias.dispose();
                } catch (NullPointerException ex) {
                    System.out.println("Sugerencias todav�a no definidas.");
                }
                if (Categor�aSeleccionada.equals("Tribus")) {
                    switch (M�todosCompartidos.TipoSeleccionado) {
                        case 0:
                            VincularSugerenciasATextField(SUGERENCIAS_TRIBUS_PLANTAS);
                            break;
                        case 1:
                            VincularSugerenciasATextField(SUGERENCIAS_TRIBUS_ZOMBIES);
                            break;
                        default: // Si est�n los dos seleccionados, fusionamos los arrays
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
                } else if (Categor�aSeleccionada.equals("Habilidades")) {
                    ArrayList<String> SugerenciasComunes = new ArrayList(Arrays.asList(SUGERENCIAS_HABILIDADES_COMUNES));
                    ArrayList<String> SugerenciasPlantas = new ArrayList(Arrays.asList(SUGERENCIAS_HABILIDADES_PLANTAS));
                    ArrayList<String> SugerenciasZombies = new ArrayList(Arrays.asList(SUGERENCIAS_HABILIDADES_ZOMBIES));
                    switch (M�todosCompartidos.TipoSeleccionado) {
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
        } else // Cambia las opciones del ComboBox disponibles dependiendo de la categor�a y de si se est� buscando entre plantas, zombies o ambos
        if (!RequiereTextField) {
            if (Categor�aSeleccionada.equals("Clase")) {
                switch (M�todosCompartidos.TipoSeleccionado) {
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
            } else if (Categor�aSeleccionada.equals("Atributos")) {
                switch (M�todosCompartidos.TipoSeleccionado) {
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
            } else if (Categor�aSeleccionada.equals("Rareza")) {
                ValorEnCombo.setItems(FXCollections.observableArrayList(RAREZAS));
            } else if (Categor�aSeleccionada.equals("Mazo")) {
                ValorEnCombo.setItems(FXCollections.observableArrayList(MAZOS));
            } else if (Categor�aSeleccionada.equals("Tipo")) {
                ValorEnCombo.setItems(FXCollections.observableArrayList(TIPOS_CARTAS));
            }
            ValorEnCombo.getSelectionModel().select(0);
        }
    }

    /**
     * Vincula el TextField "Valor" a distintos grupos de sugerencias para
     * autocompletar, con la particularidad de que s�lo se devuelven resultados
     * que empiecen por lo introducido por el usuario (sin distrinci�n entre
     * may�sculas y min�sculas)
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
        Categor�a.setId(constantes.NOMBRES_ELEMENTOS.DF_SELECCI�N_CATEGOR�A.Nombre);
        new CrearToolTip(Categor�a, this.PanelPrincipal, 11);
        Relaci�n.setId(constantes.NOMBRES_ELEMENTOS.DF_SELECCI�N_RELACI�N.Nombre);
        new CrearToolTip(Relaci�n, this.PanelPrincipal, 11);
        Valor.setId(constantes.NOMBRES_ELEMENTOS.DF_SELECCI�N_VALOR.Nombre);
        new CrearToolTip(Valor, this.PanelPrincipal, 11);
        ((RadioButton) GrupoRadio.getToggles().get(0)).setId(constantes.NOMBRES_ELEMENTOS.DF_SELECCI�N_RELACI�N_CONDICI�N.Nombre);
        new CrearToolTip((RadioButton) GrupoRadio.getToggles().get(0), this.PanelPrincipal, 11);
    }

}
