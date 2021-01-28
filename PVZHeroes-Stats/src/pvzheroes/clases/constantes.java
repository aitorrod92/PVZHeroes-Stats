package pvzheroes.clases;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import pvzheroes.clases.métodosCompartidos.MétodosCompartidos;

public class constantes {

    public static final String NOMBRE_AYUDA = "SistemaDeAyuda";
    public static Boolean SonidosEImágenesCargados = false;
    public static int TIEMPO_ESPERA_TOOLTIPS = 1000;

    public static enum CARTAS {
        PLANTAS("Plantas"),
        ZOMBIES("Zombies"),
        COMUNES("Comunes");

        public String[] Clases;
        public String[] Atributos;

        CARTAS(String lista) {
            switch (lista) {
                case "Plantas":
                    Clases = new String[]{"Guardián", "Kabum", "Gigante", "Astuto", "Solar"};
                    Atributos = new String[]{"Doble Golpe", "Daño de área", "Equipo"};
                    break;
                case "Zombies":
                    Clases = new String[]{"Bestial", "Cerebral", "Loco", "Valeroso", "Furtivo"};
                    Atributos = new String[]{"Letal", "Frenesí", "Lápida", "Rebasamiento"};
                    break;
                case "Comunes":
                    Clases = new String[]{"Sin clase"};
                    Atributos = new String[]{"Anfibio", "Anti-Héroe", "Blindaje", "Diana", "Cazar", "Impacto perforante", "Intrucable", "Ninguno"};
                    break;
            }
        }
    }

    public static enum TIPOS_DIÁLOGO {
        SIN_FONDOS("SIN_FONDOS"),
        ACERCA_DE("ACERCA_DE"),
        FALTA_SONIDO("FALTA_SONIDO"),
        NUEVA_IMAGEN("NUEVA_IMAGEN"),
        FALTA_AYUDA("FALTA_AYUDA");

        public String Cadena;

        TIPOS_DIÁLOGO(String Cadena) {
            this.Cadena = Cadena;
        }
    }

    public static enum MODO_HIPERVÍNCULO {
        MODO_BÁSICO("Modo Básico"),
        MODO_AVANZADO("Modo Avanzado"),
        MODO_GRÁFICOS("Modo Gráficos"),
        SENTENCIA_INDIVIDUAL("Sentencia Individual"),
        SENTENCIAS_GRUPALES("Sentencias Grupales"),
        CGRÁFICO_DISPERSIÓN("C. Gráfico Dispersión"),
        CGRÁFICO_GRUPAL("C. Gráfico Grupal"),
        GRÁFICO_BARRAS("Gráfico de Barras"),
        GRÁFICO_BARRAS_APILADAS("Gráfico Barras Apiladas"),
        GRÁFICO_DISPERSIÓN("Gráfico de Dispersión"),
        GRÁFICO_TARTA("Gráfico de Tarta"),
        DIÁLOGO_FILTROS("Diálogo filtros"),
        DIÁLOGO_FG("Diálogo FG"),
        BARRA_MENÚS("Barra de Menús"),
        DIÁLOGO_OPCIONES("Diálogo Opciones");

        public String nombre;

        MODO_HIPERVÍNCULO(String nombre) {
            this.nombre = nombre;
        }
    }

    public static enum NOMBRES_ELEMENTOS {
        BARRA_MENÚS("Barra de Menús"),
        BOTÓN_TABLA("Botón \"Cambiar tipo de carta\""),
        BOTÓN_BUSCAR("Botón \"Buscar\""),
        LISTA_CHECKS("Lista de checks"),
        CONSULTA_SIMPLE("Consulta simple"),
        CONSULTAS_GRUPO("Consultas de grupo"),
        BOTÓN_BUSCAR_AV("Botón \"Buscar\" (modo avanzado)"),
        SELECCIÓN_BÚSQUEDA("Selección de búsqueda"),
        SENTENCIA_INDIVIDUAL("Sentencia Individual"),
        SENTENCIAS_GRUPALES("Sentencias Grupales"),
        CGRÁFICO_GRUPAL("C. Gráfico Grupal"),
        CGRÁFICO_DISPERSIÓN("C. Gráfico Dispersión"),
        TABLA("Tabla/Área de texto"),
        TABLA_AV("Tabla/Área de texto (modo avanzado)"),
        SELECCIÓN_TIPO_GRÁFICOS("Selección del tipo de gráfico"),
        BOTÓN_CREAR_GRÁFICO("Botón \"Crear gráfico\""),
        BOTÓN_NUEVA_SERIE_BARRAS("Botón \"Nueva Serie\""),
        BOTÓN_NUEVA_SERIE_DISPERSIÓN("Botón \"Nueva Serie\""),
        GRÁFICO_BARRAS("Gráfico de Barras"),
        GRÁFICO_BARRAS_APILADAS("Gráfico Barras Apiladas"),
        GRÁFICO_TARTA("Gráfico de Tarta"),
        GRÁFICO_DISPERSIÓN("Gráfico de Dispersión"),
        DF_SELECCIÓN_CATEGORÍA("Selección de categoría"),
        DF_SELECCIÓN_RELACIÓN("Selección de relación"),
        DF_SELECCIÓN_VALOR("Selección de valor"),
        DF_SELECCIÓN_RELACIÓN_CONDICIÓN("Selección de relación con condición anterior"),
        DFG_SELECCIÓN_FUNCIÓN("Selección de tipo de función"),
        DFG_SELECCION_VARIABLE("Selección de tipo de variable"),
        DFG_BOTÓN_CONSULTAR("Botón \"Consultar\""),
        DFG_BOTÓN_AÑADIR("Botón \"Añadir\""),
        DFG_INTRODUCCIÓN_IDENTIFICADOR("Introducción de identificador"),
        DO_SELECCIÓN_FONDO("Selección de fondo"),
        DO_ACTIVAR_SONIDOS("Activar/Desactivar sonidos"),
        DO_ACTIVAR_COLOREADO("Activar/Desactivar coloreado en filas"),
        DO_ACTIVAR_AYUDA("Activar/Desactivar ayuda");

        public String Nombre;

        NOMBRES_ELEMENTOS(String nombre) {
            Nombre = nombre;
        }

    }

    public static enum IDs_CSS {
        TEXTO_GRIS("textogris"),
        ETIQUETA_NEGRA_P("etiquetaNegraPequeña"),
        ETIQUETA_NEGRA_G("etiquetaNegraGrande"),
        NEGRITA("negrita"),
        STACKPANE("sp"),
        PANEL_PRINCIPAL("PP");

        public String identificador;

        IDs_CSS(String cadena) {
            identificador = cadena;
        }

    }

    public static enum RUTAS_EXTERNAS {
        RECURSOS("Recursos"),
        RECURSOS_AYUDA("Recursos ayuda"),
        TIPOS("iconosTipos"),
        ATRIBUTOS_P("iconosAtributos/Plantas"),
        ATRIBUTOS_Z("iconosAtributos/Zombies"),
        ATRIBUTOS_C("iconosAtributos/Comunes"),
        CLASES_P("iconosClases/Plantas"),
        CLASES_Z("iconosClases/Zombies"),
        ERRORES("iconosErrores"),
        GRÁFICOS("iconosGráficos"),
        TIPOS_PEQUEÑOS("iconosPZ"),
        SÍMBOLOS("iconosSímbolos"),
        CSS("CSS"),
        SONIDOS("sonidos"),
        FONDOS("fondos"),
        AYUDA_EXE("ayuda_exe"),
        AYUDA_JAR("ayuda_jar"),
        BBDD("PVZHeroes.db"),
        PROPIEDADES("propiedades.properties");

        public static final String RUTA_BASE_IMAGENES = "recursos/recursosPrograma/imagenes/";
        private final String RUTA_BASE = "recursos/recursosPrograma/";

        public String Ruta;

        RUTAS_EXTERNAS(String ruta) {
            String carpeta = ruta + "/";
            switch (ruta) {
                case "CSS":
                case "sonidos":
                    Ruta = RUTA_BASE + carpeta;
                    break;
                case "ayuda_exe":
                    Ruta = NOMBRE_AYUDA + ".exe";
                    break;
                case "ayuda_jar":
                    Ruta = NOMBRE_AYUDA + ".jar";
                    break;
                case "PVZHeroes.db":
                case "propiedades.properties":
                    Ruta = RUTA_BASE + ruta;
                    break;
                case "Recursos ayuda":
                    Ruta = "recursos/recursosAyuda/";
                    break;
                case "fondos":
                case "iconosTipos":
                case "iconosAtributos/Plantas":
                case "iconosAtributos/Zombies":
                case "iconosAtributos/Comunes":
                case "iconosClases/Plantas":
                case "iconosClases/Zombies":
                case "iconosErrores":
                case "iconosGráficos":
                case "iconosPZ":
                case "iconosSímbolos":
                    Ruta = RUTA_BASE_IMAGENES + carpeta;
                    break;
                default:
                    Ruta = RUTA_BASE;
                    break;
            }
        }
    }

    public static enum RUTAS_DENTRO_DE_JAR {
        TIPOS("métodosCompartidos/iconosTipos"),
        SÍMBOLOS("métodosCompartidos/iconosSímbolos"),
        GRÁFICOS("métodosCompartidos/iconosGráficos"),
        CLASES_PLANTAS("métodosCompartidos/iconosClases/Plantas"),
        CLASES_ZOMBIES("métodosCompartidos/iconosClases/Zombies"),
        ATRIBUTOS_PLANTAS("métodosCompartidos/iconosAtributos/Plantas"),
        ATRIBUTOS_ZOMBIES("métodosCompartidos/iconosAtributos/Zombies"),
        ATRIBUTOS_COMUNES("métodosCompartidos/iconosAtributos/Comunes"),
        TIPOSP("métodosCompartidos/iconosPZ"),
        ERRORES("métodosCompartidos/iconosErrores");

        public String ruta;

        RUTAS_DENTRO_DE_JAR(String cadena) {
            ruta = cadena;
        }
    }

    private static ArrayList<Image> ICONOS_ERRORES;
    private static ArrayList<Image> IMÁGENES_ATRIBUTOS_PLANTAS;
    private static ArrayList<Image> IMÁGENES_ATRIBUTOS_ZOMBIES;
    private static ArrayList<Image> IMÁGENES_ATRIBUTOS_COMUNES;
    private static ArrayList<Image> IMÁGENES_CLASES_PPequeñas;
    private static ArrayList<Image> IMÁGENES_CLASES_ZPequeñas;
    private static ArrayList<Image> ICONOS_TIPOS;
    private static ArrayList<Image> ICONOS_TIPOS_PEQUEÑOS;
    private static ArrayList<Image> ICONOS_GRÁFICOS;
    private static ArrayList<Image> ICONOS_SÍMBOLOS;

    private static Media botón;
    private static Media error;
    private static Media búsqueda;

    /**
     * Asigna los sonidos de la carpeta externa y las imágenes de dentro del
     * JAR.
     *
     * @return Valor booleano que indica si la carga de sonidos ha sido o no
     * satisfactoria (la carga de imágenes tiene que serlo necesariamente por
     * encontrarse dentro del exe).
     */
    public static void CargarSonidosEImágenes() {
        try {
            botón = new Media(new File(RUTAS_EXTERNAS.SONIDOS.Ruta + "botón.wav").toURI().toString());
            error = new Media(new File(RUTAS_EXTERNAS.SONIDOS.Ruta + "error.wav").toURI().toString());
            búsqueda = new Media(new File(RUTAS_EXTERNAS.SONIDOS.Ruta + "búsqueda.wav").toURI().toString());

            ICONOS_TIPOS = new MétodosCompartidos().AsignarImágenes(constantes.RUTAS_EXTERNAS.TIPOS.Ruta);
            ICONOS_TIPOS_PEQUEÑOS = new MétodosCompartidos().AsignarImágenes(constantes.RUTAS_EXTERNAS.TIPOS_PEQUEÑOS.Ruta);
            ICONOS_SÍMBOLOS = new MétodosCompartidos().AsignarImágenes(constantes.RUTAS_EXTERNAS.SÍMBOLOS.Ruta);
            ICONOS_GRÁFICOS = new MétodosCompartidos().AsignarImágenes(constantes.RUTAS_EXTERNAS.GRÁFICOS.Ruta);
            IMÁGENES_CLASES_PPequeñas = new MétodosCompartidos().AsignarImágenes(constantes.RUTAS_EXTERNAS.CLASES_P.Ruta);
            IMÁGENES_CLASES_ZPequeñas = new MétodosCompartidos().AsignarImágenes(constantes.RUTAS_EXTERNAS.CLASES_Z.Ruta);
            IMÁGENES_ATRIBUTOS_PLANTAS = new MétodosCompartidos().AsignarImágenes(constantes.RUTAS_EXTERNAS.ATRIBUTOS_P.Ruta);
            IMÁGENES_ATRIBUTOS_ZOMBIES = new MétodosCompartidos().AsignarImágenes(constantes.RUTAS_EXTERNAS.ATRIBUTOS_Z.Ruta);
            IMÁGENES_ATRIBUTOS_COMUNES = new MétodosCompartidos().AsignarImágenes(constantes.RUTAS_EXTERNAS.ATRIBUTOS_C.Ruta);
            ICONOS_ERRORES = new MétodosCompartidos().AsignarImágenes(constantes.RUTAS_EXTERNAS.ERRORES.Ruta);

        } catch (MediaException e) {
            SonidosEImágenesCargados = false;
        }
        SonidosEImágenesCargados = true;

    }

    public static enum IMÁGENES {
        ATRIBUTOS("Atributos"),
        CLASES("Clases"),
        ERRORES("Errores"),
        TIPOSP("TiposP"),
        TIPOSG("TiposG"),
        GRÁFICOS("Gráficos"),
        SÍMBOLOS("Símbolos");

        public ArrayList<Image> Plantas;
        public ArrayList<Image> Zombies;
        public ArrayList<Image> Comunes;

        IMÁGENES(String lista) {
            switch (lista) {
                case "Atributos":
                    Plantas = IMÁGENES_ATRIBUTOS_PLANTAS;
                    Zombies = IMÁGENES_ATRIBUTOS_ZOMBIES;
                    Comunes = IMÁGENES_ATRIBUTOS_COMUNES;
                    break;
                case "Clases":
                    Plantas = IMÁGENES_CLASES_PPequeñas;
                    Zombies = IMÁGENES_CLASES_ZPequeñas;
                    break;
                case "Errores":
                    Comunes = ICONOS_ERRORES;
                    break;
                case "Gráficos":
                    Comunes = ICONOS_GRÁFICOS;
                    break;
                case "TiposG":
                    Comunes = ICONOS_TIPOS;
                    break;
                case "TiposP":
                    Comunes = ICONOS_TIPOS_PEQUEÑOS;
                    break;
                case "Símbolos":
                    Comunes = ICONOS_SÍMBOLOS;
                    break;
            }
        }
    }

    public static enum SONIDOS {
        BOTÓN("Botón"),
        ERROR("Error"),
        BUSCAR_O_GRÁFICO("Buscar");

        public Media sonido;

        SONIDOS(String origen) {
            switch (origen) {
                case "Botón":
                    sonido = botón;
                    break;
                case "Error":
                    sonido = error;
                    break;
                case "Buscar":
                    sonido = búsqueda;
                    break;
                case "Cargar":
                    CargarSonidosEImágenes();
            }
        }

    }

    public static enum SENTENCIAS_GRUPALES {
        FUNCIONES("Funciones"),
        VARIABLES("Variables");

        public String[] OpcionesCombo;

        SENTENCIAS_GRUPALES(String Combobox) {
            if (Combobox.equals("Funciones")) {
                OpcionesCombo = new String[]{"Conteo", "Media", "Máximo", "Mínimo"};
            } else {
                OpcionesCombo = new String[]{"Ataque", "Defensa", "Coste", "NumeroAtributos"};
            }
        }
    }

    public static enum COMPONENTE_GRÁFICO_DISPERSIÓN {
        EJE_X("X"),
        EJE_Y("Y");

        public String[] OpcionesCombo;

        COMPONENTE_GRÁFICO_DISPERSIÓN(String eje) {
            OpcionesCombo = 
                    new String[]{"Eje " + eje + ": Ataque", "Eje " + eje + ": Defensa", "Eje " + eje + ": Coste", 
                        "Eje " + eje + ": NumeroAtributos"};
        }
    }

    // Define los campos que se muestran en la consulta
    public static final String[] COLUMNAS = {"Nombre", "Ataque", "Defensa", "Coste", "Clase", "Tribus", "Atributos", "Habilidades", "Rareza", "Mazo", "Tipo", "URL", "NumeroAtributos"};
    public static final String[] NOMBRE_SÍMBOLOS = {"strength", "health", "suns", "brains", "freeze", "anti-hero",
        "bullseye", "armored", "strikethrough", "untrickable", "double strike", "frenzy", "deadly", "overshoot"};
    public static final String[] TIPOS_CARTA = {"Tipo = \"plants\"", "Tipo = \"zombies\"", "(Tipo = \"plants\" OR Tipo = \"zombies\")"};
    public static final String[] FORMATOS_IMAGEN_ADMITIDOS = new String[]{".jpg", ".jpeg", ".png", ".bmp"};

    // Directorios a ocultar
    public static enum A_OCULTAR {
        RECURSOS("Recursos"),
        IMÁGENES("Imágenes");

        public String[] Directorios;

        A_OCULTAR(String cadena) {
            switch (cadena) {
                case "Recursos":
                    Directorios = new String[]{"librerías", "PVZHeroes.db"};
                    break;
                case "Imágenes":
                    Directorios = new String[]{"iconosAtributos", "iconosClases", "iconosErrores", "iconosGráficos",
                        "iconosPZ", "iconosSímbolos", "iconosTipos"};
                    break;
            }
        }
    }

}
