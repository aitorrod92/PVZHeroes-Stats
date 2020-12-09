package pvzheroes.clases.diccionarios;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.image.Image;
import pvzheroes.clases.constantes;

public class DiccionarioIm�genes {

    /**
     * Devuelve el ArrayList de im�genes que corresponde a una imagen concreta
     */
    public static HashMap<String, ArrayList<Image>> ImagenAArrayList = new HashMap();
    /**
     * Devuelve la posici�n en la que se encuentra un nombre de imagen concreto
     * dentro del ArrayList de im�genes
     */
    public static HashMap<String, Integer> ImagenAPosici�n = new HashMap();

    /**
     *
     */
    public static HashMap<String, Integer> ImagenAPosici�nTabla = new HashMap();

    public DiccionarioIm�genes() {
        // Equivalencias clase-lista imagenes peque�as (en ingl�s)
        for (String clase : constantes.CARTAS.PLANTAS.Clases) {
            String claseIngl�s = DiccionarioSQL.TextoASQL(clase);
            ImagenAArrayList.put(claseIngl�s, constantes.IM�GENES.CLASES.Plantas);
        }

        for (String clase : constantes.CARTAS.ZOMBIES.Clases) {
            String claseIngl�s = DiccionarioSQL.TextoASQL(clase);
            ImagenAArrayList.put(claseIngl�s, constantes.IM�GENES.CLASES.Zombies);
        }

        // Equivalencias gr�fico-lista
        ImagenAArrayList.put("Barras apiladas", constantes.IM�GENES.GR�FICOS.Comunes);
        ImagenAArrayList.put("Barras", constantes.IM�GENES.GR�FICOS.Comunes);
        ImagenAArrayList.put("Tarta", constantes.IM�GENES.GR�FICOS.Comunes);
        ImagenAArrayList.put("Dispersi�n", constantes.IM�GENES.GR�FICOS.Comunes);

        // Equivalencias habilidad-lista
        ImagenAArrayList.put("Double Strike", constantes.IM�GENES.ATRIBUTOS.Plantas);
        ImagenAArrayList.put("Frenzy", constantes.IM�GENES.ATRIBUTOS.Zombies);
        ImagenAArrayList.put("Deadly", constantes.IM�GENES.ATRIBUTOS.Zombies);
        ImagenAArrayList.put("Overshoot", constantes.IM�GENES.ATRIBUTOS.Zombies);
        ImagenAArrayList.put("Anti-Hero", constantes.IM�GENES.ATRIBUTOS.Comunes);
        ImagenAArrayList.put("Armored", constantes.IM�GENES.ATRIBUTOS.Comunes);
        ImagenAArrayList.put("Anti-Hero", constantes.IM�GENES.ATRIBUTOS.Comunes);
        ImagenAArrayList.put("Bullseye", constantes.IM�GENES.ATRIBUTOS.Comunes);
        ImagenAArrayList.put("Strikethrough", constantes.IM�GENES.ATRIBUTOS.Comunes);
        ImagenAArrayList.put("Untrickable", constantes.IM�GENES.ATRIBUTOS.Comunes);

        // Equivalencias clase-posici�n im�genes peque�as (solar ya est� incluido con el otro)
        ImagenAPosici�n.put("Guardian", 0);
        ImagenAPosici�n.put("Kabloom", 1);
        ImagenAPosici�n.put("Mega-Grow", 2);
        ImagenAPosici�n.put("Smarty", 3);
        ImagenAPosici�n.put("Solar", 4);
        ImagenAPosici�n.put("Beastly", 0);
        ImagenAPosici�n.put("Brainy", 1);
        ImagenAPosici�n.put("Crazy", 2);
        ImagenAPosici�n.put("Hearty", 3);
        ImagenAPosici�n.put("Sneaky", 4);

        // Equivalencias habilidad-posici�n
        ImagenAPosici�n.put("Anti-Hero", 0);
        ImagenAPosici�n.put("Bullseye", 2);
        ImagenAPosici�n.put("Armored", 1);
        ImagenAPosici�n.put("Strikethrough", 3);
        ImagenAPosici�n.put("Untrickable", 4);
        ImagenAPosici�n.put("Double Strike", 0);
        ImagenAPosici�n.put("Frenzy", 0);
        ImagenAPosici�n.put("Deadly", 1);
        ImagenAPosici�n.put("Overshoot", 2);

        // Equivalencias s�mbolo tabla-posici�n
        ImagenAPosici�nTabla.put("strength", 0);
        ImagenAPosici�nTabla.put("health", 1);
        ImagenAPosici�nTabla.put("suns", 2);
        ImagenAPosici�nTabla.put("brains", 3);
        ImagenAPosici�nTabla.put("freeze", 4);
        ImagenAPosici�nTabla.put("anti-hero", 5);
        ImagenAPosici�nTabla.put("armored", 6);
        ImagenAPosici�nTabla.put("bullseye", 7);
        ImagenAPosici�nTabla.put("untrickable", 8);
        ImagenAPosici�nTabla.put("double strike", 9);
        ImagenAPosici�nTabla.put("deadly", 10);
        ImagenAPosici�nTabla.put("frenzy", 11);
        ImagenAPosici�nTabla.put("overshoot", 12);
        ImagenAPosici�nTabla.put("strikethrough", 13);

        // Equivalencias gr�fico-posici�n
        ImagenAPosici�n.put("Barras", 0);
        ImagenAPosici�n.put("Barras apiladas", 1);
        ImagenAPosici�n.put("Tarta", 2);
        ImagenAPosici�n.put("Dispersi�n", 3);
    }
}
