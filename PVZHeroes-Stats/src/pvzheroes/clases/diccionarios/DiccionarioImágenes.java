package pvzheroes.clases.diccionarios;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.image.Image;
import pvzheroes.clases.constantes;

public class DiccionarioImágenes {

    /**
     * Devuelve el ArrayList de imágenes que corresponde a una imagen concreta
     */
    public static HashMap<String, ArrayList<Image>> ImagenAArrayList = new HashMap();
    /**
     * Devuelve la posición en la que se encuentra un nombre de imagen concreto
     * dentro del ArrayList de imágenes
     */
    public static HashMap<String, Integer> ImagenAPosición = new HashMap();

    /**
     *
     */
    public static HashMap<String, Integer> ImagenAPosiciónTabla = new HashMap();

    public DiccionarioImágenes() {
        // Equivalencias clase-lista imagenes pequeñas (en inglés)
        for (String clase : constantes.CARTAS.PLANTAS.Clases) {
            String claseInglés = DiccionarioSQL.TextoASQL(clase);
            ImagenAArrayList.put(claseInglés, constantes.IMÁGENES.CLASES.Plantas);
        }

        for (String clase : constantes.CARTAS.ZOMBIES.Clases) {
            String claseInglés = DiccionarioSQL.TextoASQL(clase);
            ImagenAArrayList.put(claseInglés, constantes.IMÁGENES.CLASES.Zombies);
        }

        // Equivalencias gráfico-lista
        ImagenAArrayList.put("Barras apiladas", constantes.IMÁGENES.GRÁFICOS.Comunes);
        ImagenAArrayList.put("Barras", constantes.IMÁGENES.GRÁFICOS.Comunes);
        ImagenAArrayList.put("Tarta", constantes.IMÁGENES.GRÁFICOS.Comunes);
        ImagenAArrayList.put("Dispersión", constantes.IMÁGENES.GRÁFICOS.Comunes);

        // Equivalencias habilidad-lista
        ImagenAArrayList.put("Double Strike", constantes.IMÁGENES.ATRIBUTOS.Plantas);
        ImagenAArrayList.put("Frenzy", constantes.IMÁGENES.ATRIBUTOS.Zombies);
        ImagenAArrayList.put("Deadly", constantes.IMÁGENES.ATRIBUTOS.Zombies);
        ImagenAArrayList.put("Overshoot", constantes.IMÁGENES.ATRIBUTOS.Zombies);
        ImagenAArrayList.put("Anti-Hero", constantes.IMÁGENES.ATRIBUTOS.Comunes);
        ImagenAArrayList.put("Armored", constantes.IMÁGENES.ATRIBUTOS.Comunes);
        ImagenAArrayList.put("Anti-Hero", constantes.IMÁGENES.ATRIBUTOS.Comunes);
        ImagenAArrayList.put("Bullseye", constantes.IMÁGENES.ATRIBUTOS.Comunes);
        ImagenAArrayList.put("Strikethrough", constantes.IMÁGENES.ATRIBUTOS.Comunes);
        ImagenAArrayList.put("Untrickable", constantes.IMÁGENES.ATRIBUTOS.Comunes);

        // Equivalencias clase-posición imágenes pequeñas (solar ya está incluido con el otro)
        ImagenAPosición.put("Guardian", 0);
        ImagenAPosición.put("Kabloom", 1);
        ImagenAPosición.put("Mega-Grow", 2);
        ImagenAPosición.put("Smarty", 3);
        ImagenAPosición.put("Solar", 4);
        ImagenAPosición.put("Beastly", 0);
        ImagenAPosición.put("Brainy", 1);
        ImagenAPosición.put("Crazy", 2);
        ImagenAPosición.put("Hearty", 3);
        ImagenAPosición.put("Sneaky", 4);

        // Equivalencias habilidad-posición
        ImagenAPosición.put("Anti-Hero", 0);
        ImagenAPosición.put("Bullseye", 2);
        ImagenAPosición.put("Armored", 1);
        ImagenAPosición.put("Strikethrough", 3);
        ImagenAPosición.put("Untrickable", 4);
        ImagenAPosición.put("Double Strike", 0);
        ImagenAPosición.put("Frenzy", 0);
        ImagenAPosición.put("Deadly", 1);
        ImagenAPosición.put("Overshoot", 2);

        // Equivalencias símbolo tabla-posición
        ImagenAPosiciónTabla.put("strength", 0);
        ImagenAPosiciónTabla.put("health", 1);
        ImagenAPosiciónTabla.put("suns", 2);
        ImagenAPosiciónTabla.put("brains", 3);
        ImagenAPosiciónTabla.put("freeze", 4);
        ImagenAPosiciónTabla.put("anti-hero", 5);
        ImagenAPosiciónTabla.put("armored", 6);
        ImagenAPosiciónTabla.put("bullseye", 7);
        ImagenAPosiciónTabla.put("untrickable", 8);
        ImagenAPosiciónTabla.put("double strike", 9);
        ImagenAPosiciónTabla.put("deadly", 10);
        ImagenAPosiciónTabla.put("frenzy", 11);
        ImagenAPosiciónTabla.put("overshoot", 12);
        ImagenAPosiciónTabla.put("strikethrough", 13);

        // Equivalencias gráfico-posición
        ImagenAPosición.put("Barras", 0);
        ImagenAPosición.put("Barras apiladas", 1);
        ImagenAPosición.put("Tarta", 2);
        ImagenAPosición.put("Dispersión", 3);
    }
}
