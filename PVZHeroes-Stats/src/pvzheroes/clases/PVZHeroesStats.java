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
import pvzheroes.clases.di�logos.Di�logoGen�rico;
import pvzheroes.clases.m�todosCompartidos.M�todosCompartidos;

public class PVZHeroesStats extends Application {

    public static Scene scene;
    public static String NOMBRE_PRIMERA_IMAGEN;

    @Override
    public void start(Stage stage) throws Exception {
        constantes.CargarSonidosEIm�genes();
        A�adirIm�genesDisponibles();
        DiccionarioPropiedades.CargarPropiedades();

        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        scene = new Scene(root);
        File estilo = new File(constantes.RUTAS_EXTERNAS.CSS.Ruta + "CSS" 
                + DiccionarioPropiedades.PROP.getProperty("imagenDeFondo") + ".css");
        scene.getStylesheets().clear();
        scene.getStylesheets().add("file:///" + estilo.getAbsolutePath().replace("\\", "/"));

        stage.setTitle("PVZHeroes-Stats");
        stage.getIcons().add(constantes.IM�GENES.TIPOSG.Comunes.get(0));
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
     * Extrae los nombres de las im�genes disponibles en la carpeta
     * "images/fondos" para su uso en la aplicaci�n.
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void A�adirIm�genesDisponibles() throws FileNotFoundException, IOException {
        File directorioIm�genes = new File(constantes.RUTAS_EXTERNAS.FONDOS.Ruta);
        try {
            if (directorioIm�genes.listFiles().length > 0) {
                for (int i = 0; i < directorioIm�genes.listFiles().length; i++) {
                    String nombreImagen = directorioIm�genes.listFiles()[i].getName();
                    // Si la imagen tiene el formato adecuado, se a�ade a las posibles opciones
                    for (String formato : constantes.FORMATOS_IMAGEN_ADMITIDOS) { 
                        if (nombreImagen.toLowerCase().endsWith(formato)) {
                            String nombreImagenSinFormato = nombreImagen.replace
                                (nombreImagen.substring(nombreImagen.indexOf(".")), "");
                            if (i == 0) {
                                NOMBRE_PRIMERA_IMAGEN = nombreImagenSinFormato;
                            }
                            M�todosCompartidos.IM�GENES_FONDO.add(nombreImagenSinFormato);
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
                new Di�logoGen�rico(constantes.TIPOS_DI�LOGO.SIN_FONDOS.Cadena);
            }
        } catch (NullPointerException e) {
            new Di�logoGen�rico(constantes.TIPOS_DI�LOGO.SIN_FONDOS.Cadena);
        }
    }

    /**
     * Crea un CSS propio para una imagen que carece de �l
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
        String l�nea = "";
        while ((l�nea = br.readLine()) != null) {
            texto = texto.concat(l�nea + "\n");
        }
        br.close();
        fr.close();

        css.createNewFile();
        FileWriter fw = new FileWriter(css);
        String textoAdaptado = texto.replace(texto.substring(texto.indexOf("/fondos/") 
                + 8), nombreImagen + "\");}");
        fw.write(textoAdaptado);
        fw.close();
        new Di�logoGen�rico(constantes.TIPOS_DI�LOGO.NUEVA_IMAGEN.Cadena);
        System.exit(0);
    }
}
