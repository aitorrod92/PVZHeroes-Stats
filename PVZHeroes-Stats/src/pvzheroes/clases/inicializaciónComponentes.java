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
import pvzheroes.clases.diccionarios.DiccionarioImágenes;
import pvzheroes.clases.diccionarios.DiccionarioSQL;
import pvzheroes.clases.diálogos.DiálogoGenérico;
import pvzheroes.clases.efectosConcretos.CrearToolTip;
import pvzheroes.clases.métodosCompartidos.MétodosCompartidos;

/**
 * *********************************************************
 * Métodos que se ejecutan al inicio y configuran los componentes
 * **********************************************************
 */
public class inicializaciónComponentes {

    FXMLDocumentController c;

    public inicializaciónComponentes(FXMLDocumentController controlador) throws IOException {
        c = controlador;
        if (!constantes.SonidosEImágenesCargados) {
            new DiálogoGenérico(constantes.TIPOS_DIÁLOGO.FALTA_SONIDO.Cadena);
        }
        ConectarBD();
        new DiccionarioSQL();
        c.MC = new MétodosCompartidos();
        new DiccionarioImágenes();

        AsignarIDs();
        AsignarToolTips();

        c.RadioBásico.setSelected(true);
        ConfigurarPanelFiltros();
        ConfigurarBotón();
        ConfigurarCheckList();
        ConfigurarComponenteFuncionesGrupo();

        c.PanelBásico.setVisible(true);
        c.TablaYAreaTexto.setVisible(true);
        c.Tabla.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ModoGráficos MG = new ModoGráficos(c.PanelGráficos, c);
        MG.ConfigurarInterfaz();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                inicializaciónComponentes.this.c.Ventana = (Stage) c.PanelGráficos.getScene().getWindow();
                inicializaciónComponentes.this.c.Ventana.setMaximized(true);
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
        c.PanelBásico.getChildren().add(c.sentenciaIndividual);
        c.sentenciaIndividual.setLayoutX(287);
        c.sentenciaIndividual.setLayoutY(37);
    }

    private void ConfigurarBotón() {
        c.ImagenesBoton = constantes.IMÁGENES.TIPOSG.Comunes;
        c.ImagenBotón.setImage(c.ImagenesBoton.get(0));
        c.BotónTabla.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                c.AcciónBotónTabla(1);
            }
        });

    }

    private void ConfigurarCheckList() {
        // Añade las opciones del check en función de los nombres oficiales de columna
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

        // Define el listener que cambiará la tabla cada vez que se marque o desmarque un check
        c.checkListView.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
            public void onChanged(ListChangeListener.Change<? extends String> c) {
                if (inicializaciónComponentes.this.c.modo == 0) {
                    inicializaciónComponentes.this.c.ConfigurarTabla(null);
                    inicializaciónComponentes.this.c.itemsMarcados = inicializaciónComponentes.this.c.checkListView.getCheckModel().getCheckedItems();
                }
            }
        });

    }

    private void ConfigurarAceleradores() {
        c.MenúCerrar.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        c.RadioBásico.setAccelerator(new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN));
        c.RadioAvanzado.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));
        c.RadioGráficos.setAccelerator(new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN));
        c.MenúOpciones.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        c.MenúAyuda.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
        // Se hacen desde runLater
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                MétodosCompartidos.EstablecerAceleradorEnBotón(c.BotónBúsqueda, new KeyCodeCombination(KeyCode.ENTER), null, (Object) null);
                try {
                    Method métodoBotón = inicializaciónComponentes.this.c.getClass().getDeclaredMethod("AcciónBotónTabla", Integer.class);
                    MétodosCompartidos.EstablecerAceleradorEnBotón(c.BotónTabla, new KeyCodeCombination(KeyCode.RIGHT), métodoBotón, inicializaciónComponentes.this.c, 1);
                    MétodosCompartidos.EstablecerAceleradorEnBotón(c.BotónTabla, new KeyCodeCombination(KeyCode.LEFT), métodoBotón, inicializaciónComponentes.this.c, -1);
                } catch (NoSuchMethodException | SecurityException ex) {
                    System.out.println("El método no existe o no se puede acceder al mismo: " + ex);
                }

            }
        });
        c.MenúAcercaDe.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
    }

    private void ConfigurarComponenteFuncionesGrupo() {
        // Establece el componente de funciones de grupo en la ventana básica
        c.opcionesBúsqueda.selectToggle(c.opcionesBúsqueda.getToggles().get(0));
        c.sentenciasGrupales = new SentenciasGrupales(c, null, 0);
        c.PanelBásico.getChildren().add(c.sentenciasGrupales);
        c.sentenciasGrupales.setLayoutX(c.checkListView.getLayoutX());
        c.sentenciasGrupales.setLayoutY(c.checkListView.getLayoutY());
        //  Se ejecuta así para que dé tiempo a que el checkListView se dibuje y se puedan obtener sus valores
        Platform.runLater(() -> {
            inicializaciónComponentes.this.c.sentenciasGrupales.setMinSize(c.checkListView.getWidth(), c.checkListView.getHeight());
            inicializaciónComponentes.this.c.sentenciasGrupales.getBotónNuevo().setMinWidth(c.checkListView.getWidth());
        });
        c.sentenciasGrupales.setVisible(false);
        c.sentenciasGrupales.setDisable(true);
    }

    private void AsignarIDs() {
        c.PanelPrincipal.setId(constantes.IDs_CSS.PANEL_PRINCIPAL.identificador);
        c.BotónTabla.setId(constantes.NOMBRES_ELEMENTOS.BOTÓN_TABLA.Nombre);
        c.BotónBúsqueda.setId(constantes.NOMBRES_ELEMENTOS.BOTÓN_BUSCAR.Nombre);
        c.checkListView.setId(constantes.NOMBRES_ELEMENTOS.LISTA_CHECKS.Nombre);
        c.Tabla.setId(constantes.NOMBRES_ELEMENTOS.TABLA.Nombre);
        c.AreaTextoGrupo.setId(constantes.NOMBRES_ELEMENTOS.TABLA.Nombre);
        for (Toggle t : c.opcionesBúsqueda.getToggles()) {
            ((RadioButton) t).setId(constantes.NOMBRES_ELEMENTOS.SELECCIÓN_BÚSQUEDA.Nombre);
        }
        c.Consultasimple.setId(constantes.NOMBRES_ELEMENTOS.CONSULTA_SIMPLE.Nombre);
        c.Consultasdegrupo.setId(constantes.NOMBRES_ELEMENTOS.CONSULTAS_GRUPO.Nombre);
        c.BotónBúsquedaGrupo.setId(constantes.NOMBRES_ELEMENTOS.BOTÓN_BUSCAR_AV.Nombre);
        c.Consultasdegrupo.setId(constantes.NOMBRES_ELEMENTOS.CONSULTAS_GRUPO.Nombre);
        c.Barrademenús.setId(constantes.NOMBRES_ELEMENTOS.BARRA_MENÚS.Nombre);
    }

    private void AsignarToolTips() {
        //Añade el ToolTip de la Barra de Menús
        new CrearToolTip(c.Barrademenús, c.PanelPrincipal, 13);

        // Añade los ToolTips del Modo Básico
        ArrayList<Node> ConToolTipBásico = new ArrayList();
        for (Node ElementoConToolTip : c.PanelBásico.getChildren()) {
            ConToolTipBásico.add(ElementoConToolTip);
        }
        for (Node n : ConToolTipBásico) {
            new CrearToolTip(n, c.PanelBásico, -1);
        }

        // Añade el ToolTip de la tabla y el área de texto
        ConToolTipBásico.clear();
        for (Node ElementoConToolTip : c.TablaYAreaTexto.getChildren()) {
            ConToolTipBásico.add(ElementoConToolTip);
        }
        for (Node n : ConToolTipBásico) {
            new CrearToolTip(n, c.TablaYAreaTexto, -1);
        }

        // Añade los ToolTips del ModoAvanzado
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
     * comprometer la integridad de la aplicación sin son modificados por el
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
        AOcultar = constantes.A_OCULTAR.IMÁGENES.Directorios;
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
