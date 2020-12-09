package pvzheroes.clases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pvzheroes.clases.diccionarios.DiccionarioPropiedades;
import pvzheroes.clases.diálogos.DiálogoGenérico;
import pvzheroes.clases.métodosCompartidos.MétodosCompartidos;

public class PVZHeroesStats extends Application {

    public static Scene scene;
    public static String NOMBRE_PRIMERA_IMAGEN;

    @Override
    public void start(Stage stage) throws Exception {
        constantes.CargarSonidosEImágenes();
        AñadirImágenesDisponibles();
        DiccionarioPropiedades.CargarPropiedades();

        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        scene = new Scene(root);
        File estilo = new File(constantes.RUTAS_EXTERNAS.CSS.Ruta + "CSS" 
                + DiccionarioPropiedades.PROP.getProperty("imagenDeFondo") + ".css");
        scene.getStylesheets().clear();
        scene.getStylesheets().add("file:///" + estilo.getAbsolutePath().replace("\\", "/"));

        stage.setTitle("PVZHeroes-Stats");
        stage.getIcons().add(constantes.IMÁGENES.TIPOSG.Comunes.get(0));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Extrae los nombres de las imágenes disponibles en la carpeta
     * "images/fondos" para su uso en la aplicación.
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void AñadirImágenesDisponibles() throws FileNotFoundException, IOException {
        File directorioImágenes = new File(constantes.RUTAS_EXTERNAS.FONDOS.Ruta);
        try {
            if (directorioImágenes.listFiles().length > 0) {
                for (int i = 0; i < directorioImágenes.listFiles().length; i++) {
                    String nombreImagen = directorioImágenes.listFiles()[i].getName();
                    // Si la imagen tiene el formato adecuado, se añade a las posibles opciones
                    for (String formato : constantes.FORMATOS_IMAGEN_ADMITIDOS) { 
                        if (nombreImagen.toLowerCase().endsWith(formato)) {
                            String nombreImagenSinFormato = nombreImagen.replace
                                (nombreImagen.substring(nombreImagen.indexOf(".")), "");
                            if (i == 0) {
                                NOMBRE_PRIMERA_IMAGEN = nombreImagenSinFormato;
                            }
                            MétodosCompartidos.IMÁGENES_FONDO.add(nombreImagenSinFormato);
                            File css = new File(constantes.RUTAS_EXTERNAS.CSS.Ruta + "CSS" 
                                    + nombreImagenSinFormato + ".css");
                            // Si el CSS asociado a la imagen no existe, se crea a partir de otro
                            if (!css.exists()) {
                                CrearNuevoCSS(css, nombreImagen);
                            }
                            break;
                        }
                    }
                }
            } else {
                new DiálogoGenérico(constantes.TIPOS_DIÁLOGO.SIN_FONDOS.Cadena);
            }
        } catch (NullPointerException e) {
            new DiálogoGenérico(constantes.TIPOS_DIÁLOGO.SIN_FONDOS.Cadena);
        }
    }

    /**
     * Crea un CSS propio para una imagen que carece de él
     *
     * @param css CSS a crear
     * @param nombreImagen Nombre de la imagen a la que se asocia el CSS
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void CrearNuevoCSS(File css, String nombreImagen) 
            throws FileNotFoundException, IOException {
        File CSSBase = new File(constantes.RUTAS_EXTERNAS.CSS.Ruta).listFiles()[0];
        FileReader fr = new FileReader(CSSBase);
        BufferedReader br = new BufferedReader(fr);
        String texto = "";
        String línea = "";
        while ((línea = br.readLine()) != null) {
            texto = texto.concat(línea + "\n");
        }
        br.close();
        fr.close();

        css.createNewFile();
        FileWriter fw = new FileWriter(css);
        String textoAdaptado = texto.replace(texto.substring(texto.indexOf("/fondos/") 
                + 8), nombreImagen + "\");}");
        fw.write(textoAdaptado);
        fw.close();
        new DiálogoGenérico(constantes.TIPOS_DIÁLOGO.NUEVA_IMAGEN.Cadena);
        System.exit(0);
    }
}
