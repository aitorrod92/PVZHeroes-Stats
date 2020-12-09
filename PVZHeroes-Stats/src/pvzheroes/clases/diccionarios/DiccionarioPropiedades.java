package pvzheroes.clases.diccionarios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import pvzheroes.clases.PVZHeroesStats;
import pvzheroes.clases.constantes;

/**
 * Clase que se encarga de cargar las propiedades desde un ARCHIVO de texto y
 * corregir errores en el mismo, ya sea añadiendo propiedades que falten o
 * limpiando trozos de texto que no tengan sentido
 *
 * @author Setito
 */
public class DiccionarioPropiedades {

    public static final String[] LISTADO_PROPIEDADES
            = {"imagenDeFondo", "sonido", "filasEnColores", "ayuda", "retardoAyuda"};
    public static final File ARCHIVO
            = new File(constantes.RUTAS_EXTERNAS.PROPIEDADES.Ruta);

    private static final HashMap<String, String> PROPIEDADESPORDEFECTO
            = new HashMap();
    public static final Properties PROP = new Properties();

    /**
     * Método que se encarga de cargar las propiedades desde ARCHIVO
     */
    public static void CargarPropiedades() {
        try {
            AñadirPropiedadesPorDefecto();
            if (!ARCHIVO.exists() || ARCHIVO.length() == 0) {
                RellenarCompletamente();
            } else {
                ComprobarPropiedades();
                ArrayList<String> StringLimpio = Limpiar();
                ArrayList<String> StringFinal = LimpiarDuplicados(StringLimpio);
                CrearNuevo(StringFinal);
            }
        } catch (IOException ex) {
            System.out.println("Problema de I/O en el archivo de propiedades. Asegúrate de que no está en uso + CargarPropiedades");
        }
    }

    /**
     * Si falta alguno de los valores, se le asigna un valor por defecto.
     */
    private static void AñadirPropiedadesPorDefecto() {
        PROPIEDADESPORDEFECTO.put("imagenDeFondo", PVZHeroesStats.NOMBRE_PRIMERA_IMAGEN);
        PROPIEDADESPORDEFECTO.put("sonido", "ON");
        PROPIEDADESPORDEFECTO.put("filasEnColores", "true");
        PROPIEDADESPORDEFECTO.put("ayuda", "true");
        PROPIEDADESPORDEFECTO.put("retardoAyuda", "2000");
    }

    /**
     * Crea un nuevo archivo de propiedades desde cero
     *
     * @throws IOException
     */
    private static void RellenarCompletamente() throws IOException {
        ARCHIVO.createNewFile();
        FileWriter fw = new FileWriter(ARCHIVO);
        for (String nombrePropiedadNecesario : LISTADO_PROPIEDADES) {
            PROP.setProperty(nombrePropiedadNecesario,
                    PROPIEDADESPORDEFECTO.get((nombrePropiedadNecesario)));
            fw.write(nombrePropiedadNecesario + " = "
                    + PROPIEDADESPORDEFECTO.get(nombrePropiedadNecesario) + "\n");
        }
        fw.close();
    }

    /**
     * Comprueba que todas las propiedades necesarias se encuentran en el
     * ARCHIVO. Si alguna no está, le asigna su valor por defecto.
     */
    private static void ComprobarPropiedades() {
        try {
            FileReader fr = new FileReader(ARCHIVO);
            PROP.load(fr);
            Set<String> PropiedadesArchivo = PROP.stringPropertyNames();
            for (String nombrePropiedadNecesario : LISTADO_PROPIEDADES) {
                if (!PropiedadesArchivo.contains(nombrePropiedadNecesario)) {
                    PROP.setProperty(nombrePropiedadNecesario,
                            PROPIEDADESPORDEFECTO.get((nombrePropiedadNecesario)));
                    FileWriter fw = new FileWriter(ARCHIVO, true);
                    fw.write(nombrePropiedadNecesario + " = "
                            + PROPIEDADESPORDEFECTO.get(nombrePropiedadNecesario) + "\n");
                    fw.close();
                }
            }
        } catch (IOException ex) {
            System.out.println("Problema de I/O en el archivo de propiedades. Asegúrate de que no está en uso");
        }
    }

    /**
     * Examina si existen: 1) líneas que no estén definiendo ninguna propiedad y
     * 2) propiedades sin valores. Elimina las primeras y asigna los valores por
     * defecto a las segundas.
     *
     * @return ArrayList de String con formato "propiedad = valor"
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static ArrayList<String> Limpiar() throws FileNotFoundException, IOException {
        // Lee el ARCHIVO de texto y lo convierte a un String
        BufferedReader br = new BufferedReader(new FileReader(ARCHIVO));
        StringBuilder sb = new StringBuilder();
        String línea;
        while ((línea = br.readLine()) != null) {
            sb.append(línea);
            sb.append(System.lineSeparator());
        }
        br.close();

        // Examina cada línea y compara con el listado de propiedades
        ArrayList<String> LíneasAEliminar = new ArrayList();
        String[] líneas = sb.toString().split("\n");
        for (String líneaArchivo : líneas) {
            for (int i = 0; i < LISTADO_PROPIEDADES.length; i++) {
                if (líneaArchivo.trim().matches("\\b" + LISTADO_PROPIEDADES[i] + "\\b\\s*[=].*\\S+.*")) {
                    break;
                } else if (i == LISTADO_PROPIEDADES.length - 1) {
                    if (líneaArchivo.contains(LISTADO_PROPIEDADES[i])) { // Propiedad sin valor
                        PROP.setProperty(LISTADO_PROPIEDADES[i], PROPIEDADESPORDEFECTO.get((LISTADO_PROPIEDADES[i])));
                    }
                    LíneasAEliminar.add(líneaArchivo);
                }
            }
        }

        // Elimina las líneas incorrectas y su archivo y crea un nuevo array con todas las correctas
        if (!LíneasAEliminar.isEmpty()) {
            List lista = Arrays.asList(líneas);
            ArrayList<String> arraylist = new ArrayList(lista);
            arraylist.removeAll(LíneasAEliminar);
            ARCHIVO.delete();

            for (String propiedadNecesaria : LISTADO_PROPIEDADES) {
                for (int i = 0; i < arraylist.size(); i++) {
                    if (arraylist.get(i).contains(propiedadNecesaria)) {
                        break;
                    } else if (i == arraylist.size() - 1) {
                        arraylist.add(propiedadNecesaria + " = " + PROPIEDADESPORDEFECTO.get(propiedadNecesaria));
                    }
                }
            }
            return arraylist;
        }
        return new ArrayList(Arrays.asList(líneas));
    }

    /**
     * Limpia posibles duplicados de propiedades que puedan existir.
     *
     * @param StringLimpio ArrayList con las líneas de propiedades tras ser
     * eliminados las no-propiedades y dotado de valor a las propiedades que
     * carecían de ellos.
     * @return Un ArrayList de String con todas las propiedades, listas para ser
     * guardadas en ARCHIVO.
     */
    private static ArrayList<String> LimpiarDuplicados(ArrayList<String> StringLimpio) {
        // Se crea un array sin líneas duplicadas
        ArrayList<String> StringFinal = new ArrayList();
        for (String línea : StringLimpio) {
            if (!StringFinal.contains(línea)) {
                StringFinal.add(línea);
            }
        }

        // Se comparan las propiedades y, si hay alguna duplicada, se escoge el par para borrarlo
        ArrayList<String> AEliminarEnFinal = new ArrayList();
        for (int i = 0; i < StringFinal.size(); i++) {
            for (int x = 0; x < StringFinal.size(); x++) {
                if (i != x) {
                    String propiedad1 = StringFinal.get(i).substring
                        (0, StringFinal.get(i).indexOf("=")).replace(" ", "");
                    String propiedad2 = StringFinal.get(x).substring
                        (0, StringFinal.get(x).indexOf("=")).replace(" ", "");
                    if (propiedad1.equals(propiedad2)) {
                        AEliminarEnFinal.add(StringFinal.get(x));
                    }
                }
            }
        }

        // Se elimina una de las propiedades de cada par duplicado
        ArrayList<String> AEliminarEnFinalSinDuplicados = new ArrayList();
        for (int i = 0; i < AEliminarEnFinal.size(); i++) {
            if (i % 2 == 0) {
                AEliminarEnFinalSinDuplicados.add(AEliminarEnFinal.get(i));
            }
        }
        StringFinal.removeAll(AEliminarEnFinalSinDuplicados);

        return StringFinal;
    }

    /**
     * Crea un nuevo archivo de propiedades que sustituye al anterior.
     *
     * @param arraylist ArrayList<String> con las líneas que definen el texto
     * tras ser limpiado y los duplicados eliminados.
     * @throws IOException
     */
    private static void CrearNuevo(ArrayList<String> arraylist) throws IOException {
        ARCHIVO.createNewFile();
        FileWriter fw = new FileWriter(ARCHIVO, false);
        for (String líneaArchivo : arraylist) {
            fw.write(líneaArchivo + "\n");
        }
        fw.close();
    }

    /**
     * Método de conveniencia para guardar propiedades en el archivo. Genera
     * automáticamente el FileWriter que apunta al archivo de propiedades.
     *
     */
    public static void GuardarPropiedades() throws IOException {
        FileWriter fw = new FileWriter(ARCHIVO);
        PROP.store(fw, null);
        fw.close();
    }

}
