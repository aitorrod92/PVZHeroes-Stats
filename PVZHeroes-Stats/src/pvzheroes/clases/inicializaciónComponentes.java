package pvzheroes.clases;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Toggle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import static pvzheroes.clases.FXMLDocumentController.conn;
import pvzheroes.clases.componentes.SentenciaIndividual;
import pvzheroes.clases.componentes.SentenciasGrupales;
import pvzheroes.clases.diccionarios.DiccionarioIm�genes;
import pvzheroes.clases.diccionarios.DiccionarioSQL;
import pvzheroes.clases.di�logos.Di�logoGen�rico;
import pvzheroes.clases.efectosConcretos.CrearToolTip;
import pvzheroes.clases.m�todosCompartidos.M�todosCompartidos;

/**
 * *********************************************************
 * M�todos que se ejecutan al inicio y configuran los componentes
 * **********************************************************
 */
public class inicializaci�nComponentes {

    FXMLDocumentController c;

    public inicializaci�nComponentes(FXMLDocumentController controlador) throws IOException {
        c = controlador;
        if (!constantes.SonidosEIm�genesCargados) {
            new Di�logoGen�rico(constantes.TIPOS_DI�LOGO.FALTA_SONIDO.Cadena);
        }
        ConectarBD();
        new DiccionarioSQL();
        c.MC = new M�todosCompartidos();
        new DiccionarioIm�genes();

        AsignarIDs();
        AsignarToolTips();

        c.RadioB�sico.setSelected(true);
        ConfigurarPanelFiltros();
        ConfigurarBot�n();
        ConfigurarCheckList();
        ConfigurarComponenteFuncionesGrupo();

        c.PanelB�sico.setVisible(true);
        c.TablaYAreaTexto.setVisible(true);
        c.Tabla.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ModoGr�ficos MG = new ModoGr�ficos(c.PanelGr�ficos, c);
        MG.ConfigurarInterfaz();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                inicializaci�nComponentes.this.c.Ventana = (Stage) c.PanelGr�ficos.getScene().getWindow();
                inicializaci�nComponentes.this.c.Ventana.setMaximized(true);
            }
        }
        );
        ConfigurarAceleradores();
        OcultarArchivos();
    }

    private static void ConectarBD() {
        try {
            String url = "jdbc:sqlite:" + constantes.RUTAS_EXTERNAS.BBDD.Ruta;
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getCause());
        }
    }

    private void ConfigurarPanelFiltros() {
        c.sentenciaIndividual = new SentenciaIndividual(c, null, 0);
        c.PanelB�sico.getChildren().add(c.sentenciaIndividual);
        c.sentenciaIndividual.setLayoutX(287);
        c.sentenciaIndividual.setLayoutY(37);
    }

    private void ConfigurarBot�n() {
        c.ImagenesBoton = constantes.IM�GENES.TIPOSG.Comunes;
        c.ImagenBot�n.setImage(c.ImagenesBoton.get(0));
        c.Bot�nTabla.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                c.Acci�nBot�nTabla(1);
            }
        });

    }

    private void ConfigurarCheckList() {
        // A�ade las opciones del check en funci�n de los nombres oficiales de columna
        c.itemsDisponibles = FXCollections.observableArrayList();
        c.itemsDisponibles.addAll(constantes.COLUMNAS);
        c.itemsDisponibles.remove(constantes.COLUMNAS[10]);
        c.itemsDisponibles.remove(constantes.COLUMNAS[11]);
        c.checkListView.setItems(c.itemsDisponibles);

        // Marca los 3 primeros checks
        c.checkListView.getCheckModel().check(0);
        c.checkListView.getCheckModel().check(1);
        c.checkListView.getCheckModel().check(2);
        c.itemsMarcados = c.checkListView.getCheckModel().getCheckedItems();
        c.ConfigurarTabla(null);

        // Define el listener que cambiar� la tabla cada vez que se marque o desmarque un check
        c.checkListView.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
            public void onChanged(ListChangeListener.Change<? extends String> c) {
                if (inicializaci�nComponentes.this.c.modo == 0) {
                    inicializaci�nComponentes.this.c.ConfigurarTabla(null);
                    inicializaci�nComponentes.this.c.itemsMarcados = inicializaci�nComponentes.this.c.checkListView.getCheckModel().getCheckedItems();
                }
            }
        });

    }

    private void ConfigurarAceleradores() {
        c.Men�Cerrar.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        c.RadioB�sico.setAccelerator(new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN));
        c.RadioAvanzado.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));
        c.RadioGr�ficos.setAccelerator(new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN));
        c.Men�Opciones.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        c.Men�Ayuda.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
        // Se hacen desde runLater
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                M�todosCompartidos.EstablecerAceleradorEnBot�n(c.Bot�nB�squeda, new KeyCodeCombination(KeyCode.ENTER), null, (Object) null);
                try {
                    Method m�todoBot�n = inicializaci�nComponentes.this.c.getClass().getDeclaredMethod("Acci�nBot�nTabla", Integer.class);
                    M�todosCompartidos.EstablecerAceleradorEnBot�n(c.Bot�nTabla, new KeyCodeCombination(KeyCode.RIGHT), m�todoBot�n, inicializaci�nComponentes.this.c, 1);
                    M�todosCompartidos.EstablecerAceleradorEnBot�n(c.Bot�nTabla, new KeyCodeCombination(KeyCode.LEFT), m�todoBot�n, inicializaci�nComponentes.this.c, -1);
                } catch (NoSuchMethodException | SecurityException ex) {
                    System.out.println("El m�todo no existe o no se puede acceder al mismo: " + ex);
                }

            }
        });
        c.Men�AcercaDe.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
    }

    private void ConfigurarComponenteFuncionesGrupo() {
        // Establece el componente de funciones de grupo en la ventana b�sica
        c.opcionesB�squeda.selectToggle(c.opcionesB�squeda.getToggles().get(0));
        c.sentenciasGrupales = new SentenciasGrupales(c, null, 0);
        c.PanelB�sico.getChildren().add(c.sentenciasGrupales);
        c.sentenciasGrupales.setLayoutX(c.checkListView.getLayoutX());
        c.sentenciasGrupales.setLayoutY(c.checkListView.getLayoutY());
        //  Se ejecuta as� para que d� tiempo a que el checkListView se dibuje y se puedan obtener sus valores
        Platform.runLater(() -> {
            inicializaci�nComponentes.this.c.sentenciasGrupales.setMinSize(c.checkListView.getWidth(), c.checkListView.getHeight());
            inicializaci�nComponentes.this.c.sentenciasGrupales.getBot�nNuevo().setMinWidth(c.checkListView.getWidth());
        });
        c.sentenciasGrupales.setVisible(false);
        c.sentenciasGrupales.setDisable(true);
    }

    private void AsignarIDs() {
        c.PanelPrincipal.setId(constantes.IDs_CSS.PANEL_PRINCIPAL.identificador);
        c.Bot�nTabla.setId(constantes.NOMBRES_ELEMENTOS.BOT�N_TABLA.Nombre);
        c.Bot�nB�squeda.setId(constantes.NOMBRES_ELEMENTOS.BOT�N_BUSCAR.Nombre);
        c.checkListView.setId(constantes.NOMBRES_ELEMENTOS.LISTA_CHECKS.Nombre);
        c.Tabla.setId(constantes.NOMBRES_ELEMENTOS.TABLA.Nombre);
        c.AreaTextoGrupo.setId(constantes.NOMBRES_ELEMENTOS.TABLA.Nombre);
        for (Toggle t : c.opcionesB�squeda.getToggles()) {
            ((RadioButton) t).setId(constantes.NOMBRES_ELEMENTOS.SELECCI�N_B�SQUEDA.Nombre);
        }
        c.Consultasimple.setId(constantes.NOMBRES_ELEMENTOS.CONSULTA_SIMPLE.Nombre);
        c.Consultasdegrupo.setId(constantes.NOMBRES_ELEMENTOS.CONSULTAS_GRUPO.Nombre);
        c.Bot�nB�squedaGrupo.setId(constantes.NOMBRES_ELEMENTOS.BOT�N_BUSCAR_AV.Nombre);
        c.Consultasdegrupo.setId(constantes.NOMBRES_ELEMENTOS.CONSULTAS_GRUPO.Nombre);
        c.Barrademen�s.setId(constantes.NOMBRES_ELEMENTOS.BARRA_MEN�S.Nombre);
    }

    private void AsignarToolTips() {
        //A�ade el ToolTip de la Barra de Men�s
        new CrearToolTip(c.Barrademen�s, c.PanelPrincipal, 13);

        // A�ade los ToolTips del Modo B�sico
        ArrayList<Node> ConToolTipB�sico = new ArrayList();
        for (Node ElementoConToolTip : c.PanelB�sico.getChildren()) {
            ConToolTipB�sico.add(ElementoConToolTip);
        }
        for (Node n : ConToolTipB�sico) {
            new CrearToolTip(n, c.PanelB�sico, -1);
        }

        // A�ade el ToolTip de la tabla y el �rea de texto
        ConToolTipB�sico.clear();
        for (Node ElementoConToolTip : c.TablaYAreaTexto.getChildren()) {
            ConToolTipB�sico.add(ElementoConToolTip);
        }
        for (Node n : ConToolTipB�sico) {
            new CrearToolTip(n, c.TablaYAreaTexto, -1);
        }

        // A�ade los ToolTips del ModoAvanzado
        ArrayList<Node> ConToolTipAvanzado = new ArrayList();
        for (Node ElementoConToolTip : c.PanelSentenciasGrupo.getChildren()) {
            if (!(ElementoConToolTip instanceof Label)) {
                ConToolTipAvanzado.add(ElementoConToolTip);
            }
        }

        for (Node n : ConToolTipAvanzado) {
            new CrearToolTip(n, c.PanelAvanzado, 1);
        }

    }

    /**
     * Oculta archivos y directorios de la carpeta de recursos que pueden
     * comprometer la integridad de la aplicaci�n sin son modificados por el
     * usuario.
     */
    private void OcultarArchivos() throws IOException {
        // Oculta carpetas dentro de "recursosPrograma"
        File directorioRecursos = new File(constantes.RUTAS_EXTERNAS.RECURSOS.Ruta);
        String[] AOcultar = constantes.A_OCULTAR.RECURSOS.Directorios;
        Path ruta;
        for (File archivo : directorioRecursos.listFiles()) {
            for (String archivoAOcultar : AOcultar) {
                if (archivo.getName().equals(archivoAOcultar)) {
                    ruta = Paths.get(archivo.getPath());
                    Files.setAttribute(ruta, "dos:hidden", Boolean.TRUE);
                }
            }
        }

        // Oculta carpetas dentro de "recursosPrograma/imagenes"
        File directorioImagenes = new File(constantes.RUTAS_EXTERNAS.RUTA_BASE_IMAGENES);
        AOcultar = constantes.A_OCULTAR.IM�GENES.Directorios;
        for (File archivo : directorioImagenes.listFiles()) {
            for (String archivoAOcultar : AOcultar) {
                if (archivo.getName().equals(archivoAOcultar)) {
                    ruta = Paths.get(archivo.getPath());
                    Files.setAttribute(ruta, "dos:hidden", Boolean.TRUE);
                }
            }
        }

        // Oculta la carpeta "recursosAyuda"
        File recursosAyuda = new File(constantes.RUTAS_EXTERNAS.RECURSOS_AYUDA.Ruta);
        ruta = Paths.get(recursosAyuda.getPath());
        Files.setAttribute(ruta, "dos:hidden", Boolean.TRUE);
    }

}
