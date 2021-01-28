package pvzheroes.clases;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import pvzheroes.clases.m�todosCompartidos.M�todosCompartidos;

public class constantes {

    public static final String NOMBRE_AYUDA = "SistemaDeAyuda";
    public static Boolean SonidosEIm�genesCargados = false;
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
                    Clases = new String[]{"Guardi�n", "Kabum", "Gigante", "Astuto", "Solar"};
                    Atributos = new String[]{"Doble Golpe", "Da�o de �rea", "Equipo"};
                    break;
                case "Zombies":
                    Clases = new String[]{"Bestial", "Cerebral", "Loco", "Valeroso", "Furtivo"};
                    Atributos = new String[]{"Letal", "Frenes�", "L�pida", "Rebasamiento"};
                    break;
                case "Comunes":
                    Clases = new String[]{"Sin clase"};
                    Atributos = new String[]{"Anfibio", "Anti-H�roe", "Blindaje", "Diana", "Cazar", "Impacto perforante", "Intrucable", "Ninguno"};
                    break;
            }
        }
    }

    public static enum TIPOS_DI�LOGO {
        SIN_FONDOS("SIN_FONDOS"),
        ACERCA_DE("ACERCA_DE"),
        FALTA_SONIDO("FALTA_SONIDO"),
        NUEVA_IMAGEN("NUEVA_IMAGEN"),
        FALTA_AYUDA("FALTA_AYUDA");

        public String Cadena;

        TIPOS_DI�LOGO(String Cadena) {
            this.Cadena = Cadena;
        }
    }

    public static enum MODO_HIPERV�NCULO {
        MODO_B�SICO("Modo B�sico"),
        MODO_AVANZADO("Modo Avanzado"),
        MODO_GR�FICOS("Modo Gr�ficos"),
        SENTENCIA_INDIVIDUAL("Sentencia Individual"),
        SENTENCIAS_GRUPALES("Sentencias Grupales"),
        CGR�FICO_DISPERSI�N("C. Gr�fico Dispersi�n"),
        CGR�FICO_GRUPAL("C. Gr�fico Grupal"),
        GR�FICO_BARRAS("Gr�fico de Barras"),
        GR�FICO_BARRAS_APILADAS("Gr�fico Barras Apiladas"),
        GR�FICO_DISPERSI�N("Gr�fico de Dispersi�n"),
        GR�FICO_TARTA("Gr�fico de Tarta"),
        DI�LOGO_FILTROS("Di�logo filtros"),
        DI�LOGO_FG("Di�logo FG"),
        BARRA_MEN�S("Barra de Men�s"),
        DI�LOGO_OPCIONES("Di�logo Opciones");

        public String nombre;

        MODO_HIPERV�NCULO(String nombre) {
            this.nombre = nombre;
        }
    }

    public static enum NOMBRES_ELEMENTOS {
        BARRA_MEN�S("Barra de Men�s"),
        BOT�N_TABLA("Bot�n \"Cambiar tipo de carta\""),
        BOT�N_BUSCAR("Bot�n \"Buscar\""),
        LISTA_CHECKS("Lista de checks"),
        CONSULTA_SIMPLE("Consulta simple"),
        CONSULTAS_GRUPO("Consultas de grupo"),
        BOT�N_BUSCAR_AV("Bot�n \"Buscar\" (modo avanzado)"),
        SELECCI�N_B�SQUEDA("Selecci�n de b�squeda"),
        SENTENCIA_INDIVIDUAL("Sentencia Individual"),
        SENTENCIAS_GRUPALES("Sentencias Grupales"),
        CGR�FICO_GRUPAL("C. Gr�fico Grupal"),
        CGR�FICO_DISPERSI�N("C. Gr�fico Dispersi�n"),
        TABLA("Tabla/�rea de texto"),
        TABLA_AV("Tabla/�rea de texto (modo avanzado)"),
        SELECCI�N_TIPO_GR�FICOS("Selecci�n del tipo de gr�fico"),
        BOT�N_CREAR_GR�FICO("Bot�n \"Crear gr�fico\""),
        BOT�N_NUEVA_SERIE_BARRAS("Bot�n \"Nueva Serie\""),
        BOT�N_NUEVA_SERIE_DISPERSI�N("Bot�n \"Nueva Serie\""),
        GR�FICO_BARRAS("Gr�fico de Barras"),
        GR�FICO_BARRAS_APILADAS("Gr�fico Barras Apiladas"),
        GR�FICO_TARTA("Gr�fico de Tarta"),
        GR�FICO_DISPERSI�N("Gr�fico de Dispersi�n"),
        DF_SELECCI�N_CATEGOR�A("Selecci�n de categor�a"),
        DF_SELECCI�N_RELACI�N("Selecci�n de relaci�n"),
        DF_SELECCI�N_VALOR("Selecci�n de valor"),
        DF_SELECCI�N_RELACI�N_CONDICI�N("Selecci�n de relaci�n con condici�n anterior"),
        DFG_SELECCI�N_FUNCI�N("Selecci�n de tipo de funci�n"),
        DFG_SELECCION_VARIABLE("Selecci�n de tipo de variable"),
        DFG_BOT�N_CONSULTAR("Bot�n \"Consultar\""),
        DFG_BOT�N_A�ADIR("Bot�n \"A�adir\""),
        DFG_INTRODUCCI�N_IDENTIFICADOR("Introducci�n de identificador"),
        DO_SELECCI�N_FONDO("Selecci�n de fondo"),
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
        ETIQUETA_NEGRA_P("etiquetaNegraPeque�a"),
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
        GR�FICOS("iconosGr�ficos"),
        TIPOS_PEQUE�OS("iconosPZ"),
        S�MBOLOS("iconosS�mbolos"),
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
                case "iconosGr�ficos":
                case "iconosPZ":
                case "iconosS�mbolos":
                    Ruta = RUTA_BASE_IMAGENES + carpeta;
                    break;
                default:
                    Ruta = RUTA_BASE;
                    break;
            }
        }
    }

    public static enum RUTAS_DENTRO_DE_JAR {
        TIPOS("m�todosCompartidos/iconosTipos"),
        S�MBOLOS("m�todosCompartidos/iconosS�mbolos"),
        GR�FICOS("m�todosCompartidos/iconosGr�ficos"),
        CLASES_PLANTAS("m�todosCompartidos/iconosClases/Plantas"),
        CLASES_ZOMBIES("m�todosCompartidos/iconosClases/Zombies"),
        ATRIBUTOS_PLANTAS("m�todosCompartidos/iconosAtributos/Plantas"),
        ATRIBUTOS_ZOMBIES("m�todosCompartidos/iconosAtributos/Zombies"),
        ATRIBUTOS_COMUNES("m�todosCompartidos/iconosAtributos/Comunes"),
        TIPOSP("m�todosCompartidos/iconosPZ"),
        ERRORES("m�todosCompartidos/iconosErrores");

        public String ruta;

        RUTAS_DENTRO_DE_JAR(String cadena) {
            ruta = cadena;
        }
    }

    private static ArrayList<Image> ICONOS_ERRORES;
    private static ArrayList<Image> IM�GENES_ATRIBUTOS_PLANTAS;
    private static ArrayList<Image> IM�GENES_ATRIBUTOS_ZOMBIES;
    private static ArrayList<Image> IM�GENES_ATRIBUTOS_COMUNES;
    private static ArrayList<Image> IM�GENES_CLASES_PPeque�as;
    private static ArrayList<Image> IM�GENES_CLASES_ZPeque�as;
    private static ArrayList<Image> ICONOS_TIPOS;
    private static ArrayList<Image> ICONOS_TIPOS_PEQUE�OS;
    private static ArrayList<Image> ICONOS_GR�FICOS;
    private static ArrayList<Image> ICONOS_S�MBOLOS;

    private static Media bot�n;
    private static Media error;
    private static Media b�squeda;

    /**
     * Asigna los sonidos de la carpeta externa y las im�genes de dentro del
     * JAR.
     *
     * @return Valor booleano que indica si la carga de sonidos ha sido o no
     * satisfactoria (la carga de im�genes tiene que serlo necesariamente por
     * encontrarse dentro del exe).
     */
    public static void CargarSonidosEIm�genes() {
        try {
            bot�n = new Media(new File(RUTAS_EXTERNAS.SONIDOS.Ruta + "bot�n.wav").toURI().toString());
            error = new Media(new File(RUTAS_EXTERNAS.SONIDOS.Ruta + "error.wav").toURI().toString());
            b�squeda = new Media(new File(RUTAS_EXTERNAS.SONIDOS.Ruta + "b�squeda.wav").toURI().toString());

            ICONOS_TIPOS = new M�todosCompartidos().AsignarIm�genes(constantes.RUTAS_EXTERNAS.TIPOS.Ruta);
            ICONOS_TIPOS_PEQUE�OS = new M�todosCompartidos().AsignarIm�genes(constantes.RUTAS_EXTERNAS.TIPOS_PEQUE�OS.Ruta);
            ICONOS_S�MBOLOS = new M�todosCompartidos().AsignarIm�genes(constantes.RUTAS_EXTERNAS.S�MBOLOS.Ruta);
            ICONOS_GR�FICOS = new M�todosCompartidos().AsignarIm�genes(constantes.RUTAS_EXTERNAS.GR�FICOS.Ruta);
            IM�GENES_CLASES_PPeque�as = new M�todosCompartidos().AsignarIm�genes(constantes.RUTAS_EXTERNAS.CLASES_P.Ruta);
            IM�GENES_CLASES_ZPeque�as = new M�todosCompartidos().AsignarIm�genes(constantes.RUTAS_EXTERNAS.CLASES_Z.Ruta);
            IM�GENES_ATRIBUTOS_PLANTAS = new M�todosCompartidos().AsignarIm�genes(constantes.RUTAS_EXTERNAS.ATRIBUTOS_P.Ruta);
            IM�GENES_ATRIBUTOS_ZOMBIES = new M�todosCompartidos().AsignarIm�genes(constantes.RUTAS_EXTERNAS.ATRIBUTOS_Z.Ruta);
            IM�GENES_ATRIBUTOS_COMUNES = new M�todosCompartidos().AsignarIm�genes(constantes.RUTAS_EXTERNAS.ATRIBUTOS_C.Ruta);
            ICONOS_ERRORES = new M�todosCompartidos().AsignarIm�genes(constantes.RUTAS_EXTERNAS.ERRORES.Ruta);

        } catch (MediaException e) {
            SonidosEIm�genesCargados = false;
        }
        SonidosEIm�genesCargados = true;

    }

    public static enum IM�GENES {
        ATRIBUTOS("Atributos"),
        CLASES("Clases"),
        ERRORES("Errores"),
        TIPOSP("TiposP"),
        TIPOSG("TiposG"),
        GR�FICOS("Gr�ficos"),
        S�MBOLOS("S�mbolos");

        public ArrayList<Image> Plantas;
        public ArrayList<Image> Zombies;
        public ArrayList<Image> Comunes;

        IM�GENES(String lista) {
            switch (lista) {
                case "Atributos":
                    Plantas = IM�GENES_ATRIBUTOS_PLANTAS;
                    Zombies = IM�GENES_ATRIBUTOS_ZOMBIES;
                    Comunes = IM�GENES_ATRIBUTOS_COMUNES;
                    break;
                case "Clases":
                    Plantas = IM�GENES_CLASES_PPeque�as;
                    Zombies = IM�GENES_CLASES_ZPeque�as;
                    break;
                case "Errores":
                    Comunes = ICONOS_ERRORES;
                    break;
                case "Gr�ficos":
                    Comunes = ICONOS_GR�FICOS;
                    break;
                case "TiposG":
                    Comunes = ICONOS_TIPOS;
                    break;
                case "TiposP":
                    Comunes = ICONOS_TIPOS_PEQUE�OS;
                    break;
                case "S�mbolos":
                    Comunes = ICONOS_S�MBOLOS;
                    break;
            }
        }
    }

    public static enum SONIDOS {
        BOT�N("Bot�n"),
        ERROR("Error"),
        BUSCAR_O_GR�FICO("Buscar");

        public Media sonido;

        SONIDOS(String origen) {
            switch (origen) {
                case "Bot�n":
                    sonido = bot�n;
                    break;
                case "Error":
                    sonido = error;
                    break;
                case "Buscar":
                    sonido = b�squeda;
                    break;
                case "Cargar":
                    CargarSonidosEIm�genes();
            }
        }

    }

    public static enum SENTENCIAS_GRUPALES {
        FUNCIONES("Funciones"),
        VARIABLES("Variables");

        public String[] OpcionesCombo;

        SENTENCIAS_GRUPALES(String Combobox) {
            if (Combobox.equals("Funciones")) {
                OpcionesCombo = new String[]{"Conteo", "Media", "M�ximo", "M�nimo"};
            } else {
                OpcionesCombo = new String[]{"Ataque", "Defensa", "Coste", "NumeroAtributos"};
            }
        }
    }

    public static enum COMPONENTE_GR�FICO_DISPERSI�N {
        EJE_X("X"),
        EJE_Y("Y");

        public String[] OpcionesCombo;

        COMPONENTE_GR�FICO_DISPERSI�N(String eje) {
            OpcionesCombo = 
                    new String[]{"Eje " + eje + ": Ataque", "Eje " + eje + ": Defensa", "Eje " + eje + ": Coste", 
                        "Eje " + eje + ": NumeroAtributos"};
        }
    }

    // Define los campos que se muestran en la consulta
    public static final String[] COLUMNAS = {"Nombre", "Ataque", "Defensa", "Coste", "Clase", "Tribus", "Atributos", "Habilidades", "Rareza", "Mazo", "Tipo", "URL", "NumeroAtributos"};
    public static final String[] NOMBRE_S�MBOLOS = {"strength", "health", "suns", "brains", "freeze", "anti-hero",
        "bullseye", "armored", "strikethrough", "untrickable", "double strike", "frenzy", "deadly", "overshoot"};
    public static final String[] TIPOS_CARTA = {"Tipo = \"plants\"", "Tipo = \"zombies\"", "(Tipo = \"plants\" OR Tipo = \"zombies\")"};
    public static final String[] FORMATOS_IMAGEN_ADMITIDOS = new String[]{".jpg", ".jpeg", ".png", ".bmp"};

    // Directorios a ocultar
    public static enum A_OCULTAR {
        RECURSOS("Recursos"),
        IM�GENES("Im�genes");

        public String[] Directorios;

        A_OCULTAR(String cadena) {
            switch (cadena) {
                case "Recursos":
                    Directorios = new String[]{"librer�as", "PVZHeroes.db"};
                    break;
                case "Im�genes":
                    Directorios = new String[]{"iconosAtributos", "iconosClases", "iconosErrores", "iconosGr�ficos",
                        "iconosPZ", "iconosS�mbolos", "iconosTipos"};
                    break;
            }
        }
    }

}
