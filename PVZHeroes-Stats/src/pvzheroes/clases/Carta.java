package pvzheroes.clases;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import pvzheroes.clases.diccionarios.DiccionarioIm�genes;
import pvzheroes.clases.m�todosCompartidos.M�todosCompartidos;

public class Carta {

    private Hyperlink Nombre;
    private SimpleStringProperty Ataque;
    private SimpleStringProperty Defensa;
    private SimpleStringProperty Coste;
    private SimpleStringProperty Clase;
    private SimpleStringProperty Tribus;
    private SimpleStringProperty Atributos;
    private SimpleStringProperty Habilidades;
    private SimpleStringProperty Rareza;
    private SimpleStringProperty Mazo;
    private SimpleStringProperty Tipo;
    private SimpleStringProperty NumeroAtributos;

    public Carta(String Nombre, String Ataque, String Defensa, String Coste, String Clase, String Tribus, String Atributos, String Habilidades, String Rareza, String Mazo, String Tipo, String URL, String N�meroAtributos) {
        this.Nombre = new Hyperlink();
        this.Nombre.setText(Nombre);
        this.Nombre.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(URL));
                } catch (URISyntaxException ex) {
                    System.out.println("El String no se ha podido convertir a URI");
                } catch (IOException ex) {
                    System.out.println("No se ha encontrado la p�gina");
                }
            }
        }
        );
        this.Ataque = new SimpleStringProperty(Ataque);

        this.Defensa = new SimpleStringProperty(Defensa);

        this.Coste = new SimpleStringProperty(Coste);

        this.Clase = new SimpleStringProperty(Clase);

        this.Tribus = new SimpleStringProperty(Tribus);

        this.Atributos = new SimpleStringProperty(Atributos);

        this.Habilidades = new SimpleStringProperty(Habilidades);

        this.Rareza = new SimpleStringProperty(Rareza);

        this.Mazo = new SimpleStringProperty(Mazo);

        this.Tipo = new SimpleStringProperty(Tipo);

        this.NumeroAtributos = new SimpleStringProperty(N�meroAtributos);
    }

    public Hyperlink getNombre() {
        return this.Nombre;
    }

    public String getAtaque() {
        return Ataque.get();
    }

    public String getDefensa() {
        return Defensa.get();
    }

    public String getCoste() {
        return Coste.get();
    }

    public HBox getClase() {
        HBox Caja = new HBox();
        ImageView imagen = null;
        Label etiqueta = new Label(Clase.get());
        String[] ListaSubsecciones;

        if (Clase.get().equals("Sin clase")) {
            etiqueta.setId(constantes.IDs_CSS.TEXTO_GRIS.identificador);
            Caja.getChildren().add(etiqueta);
        } else if (DiccionarioIm�genes.ImagenAArrayList.containsKey(Clase.get())) {
            ArrayList<Image> lista = DiccionarioIm�genes.ImagenAArrayList.get(Clase.get());
            int �ndiceImagen = DiccionarioIm�genes.ImagenAPosici�n.get(Clase.get());
            imagen = new ImageView(lista.get(�ndiceImagen));
            Caja.getChildren().addAll(imagen, etiqueta);
        } else if (Clase.get().contains(",")) {
            ArrayList<String> listaAtributos = new ArrayList();
            ArrayList<Image> listaIm�genes = new ArrayList();
            ListaSubsecciones = Clase.get().split(", ");
            for (String subsecci�n : ListaSubsecciones) {
                listaAtributos.add(subsecci�n);
                ArrayList<Image> lista = DiccionarioIm�genes.ImagenAArrayList.get(subsecci�n);
                int �ndiceImagen = DiccionarioIm�genes.ImagenAPosici�n.get(subsecci�n);
                listaIm�genes.add(lista.get(�ndiceImagen));
            }
            for (int i = 0; i < listaAtributos.size(); i++) {
                Caja.getChildren().addAll(new ImageView(listaIm�genes.get(i)),
                        new Label(listaAtributos.get(i)));
            }
        } else {
            Caja.getChildren().add(etiqueta);
        }

        Caja.setSpacing(5);
        return Caja;
    }

    public String getTribus() {
        return Tribus.get();
    }

    public HBox getAtributos() {
        HBox Caja = new HBox();
        ImageView imagen = null;
        String atributo = Atributos.get();
        Label etiqueta = new Label(atributo);
        String[] listaSubsecciones;

        if (atributo.contains(" ") && !atributo.equals("Sin atributos") && !atributo.equals("Double Strike")) { // Se obtienen atributos, valores e im�genes
            listaSubsecciones = atributo.split(" ");
            ArrayList<String> listaAtributos = new ArrayList();
            ArrayList<Image> listaIm�genes = new ArrayList();
            ArrayList<String> listaN�meros = new ArrayList();
            for (int i = 0; i < listaSubsecciones.length; i++) {
                if (listaSubsecciones[i].matches("Damage")) {
                    listaAtributos.remove("Splash");
                    listaAtributos.add("Splash Damage");
                } else if (listaSubsecciones[i].matches("\\d{1}")) {
                    listaN�meros.add(listaSubsecciones[i]);
                } else {
                    listaAtributos.add(listaSubsecciones[i]);
                    if (DiccionarioIm�genes.ImagenAArrayList.containsKey(listaSubsecciones[i])) {
                        ArrayList<Image> lista = DiccionarioIm�genes.ImagenAArrayList.get(listaSubsecciones[i]);
                        int �ndiceImagen = DiccionarioIm�genes.ImagenAPosici�n.get(listaSubsecciones[i]);
                        listaIm�genes.add(lista.get(�ndiceImagen));
                    }
                }
            }

            int N�merosAsignados = 0;
            for (String atributop : listaAtributos) {
                System.out.println("Atributo : " + atributop);
            }
            int atributosSinImagen = 0;
            for (int i = 0; i < listaAtributos.size(); i++) { // Se a�aden los elementos al HBox en orden
                if (DiccionarioIm�genes.ImagenAArrayList.containsKey(listaAtributos.get(i))) {
                    System.out.println("Buscando la imagen correspondiente a atributo " + listaAtributos.get(i));
                    Caja.getChildren().add(new ImageView(listaIm�genes.get(i - atributosSinImagen)));
                } else {
                    atributosSinImagen++; // Esto se utiliza para evitar que los atributos sin imagen aumenten el �ndice de imagen a buscar
                }
                Label etiquetaAtributo = new Label(listaAtributos.get(i));
                etiquetaAtributo.setId(constantes.IDs_CSS.NEGRITA.identificador);
                Caja.getChildren().add(etiquetaAtributo);
                if (listaAtributos.get(i).equals("Anti-Hero") || listaAtributos.get(i).equals("Armored")
                        || listaAtributos.get(i).equals("Overshoot") || listaAtributos.get(i).equals("Splash Damage")) {
                    Label etiquetaN�mero = new Label(listaN�meros.get(N�merosAsignados));
                    N�merosAsignados++;
                    etiquetaN�mero.setId(constantes.IDs_CSS.NEGRITA.identificador);
                    Caja.getChildren().add(etiquetaN�mero);
                }
                Caja.setSpacing(5);
            }

        } else {
            if (DiccionarioIm�genes.ImagenAArrayList.containsKey(atributo)) {
                etiqueta.setId(constantes.IDs_CSS.NEGRITA.identificador);
                ArrayList<Image> lista = DiccionarioIm�genes.ImagenAArrayList.get(atributo);
                int �ndiceImagen = DiccionarioIm�genes.ImagenAPosici�n.get(atributo);
                imagen = new ImageView(lista.get(�ndiceImagen));
                Caja.getChildren().addAll(imagen, etiqueta);
                Caja.setSpacing(5);
            } else {
                if (atributo.equals("Sin atributos")) {
                    etiqueta.setId(constantes.IDs_CSS.TEXTO_GRIS.identificador);
                } else {
                    etiqueta.setId(constantes.IDs_CSS.NEGRITA.identificador);
                }
                Caja.getChildren().add(etiqueta);
            }

        }
        return Caja;
    }

    public ImageView getTipo() {
        ImageView imagen;
        if (Tipo.get().equals("plants")) {
            imagen = new ImageView(constantes.IM�GENES.TIPOSP.Comunes.get(0));
        } else {
            imagen = new ImageView(constantes.IM�GENES.TIPOSP.Comunes.get(1));
        }
        return imagen;
    }

    public HBox getHabilidades() {
        HBox Caja = new HBox();
        String habilidad = Habilidades.get();
        ArrayList<Node> Nodos = new ArrayList();

        // Contea el n�mero de coincidencias que se tendr�n que convertir en im�genes
        int coincidencias = 0;
        for (String posibilidad : constantes.NOMBRE_S�MBOLOS) {
            coincidencias = coincidencias + M�todosCompartidos.ContarCoincidenciasEnString(habilidad, posibilidad);
        }

        if (coincidencias > 0) {
            // Determina la posici�n de las im�genes en el String completo y las guarda en un HashMap
            HashMap<Integer, String> Posici�nImagen = new HashMap();
            for (String palabra : constantes.NOMBRE_S�MBOLOS) {
                String habilidadRecortada = habilidad;
                int recortado = 0;
                while (habilidadRecortada.contains(palabra)) {
                    int posici�nPalabra = habilidadRecortada.indexOf(palabra);
                    recortado = habilidad.length() - habilidadRecortada.length();
                    habilidadRecortada = habilidadRecortada.substring(posici�nPalabra + palabra.length());
                    Posici�nImagen.put(posici�nPalabra + recortado, palabra);
                    /*System.out.println("Posici�n " + palabra + " es " + (posici�nPalabra + recortado) + " en"
                            + " el String sin recortar, siendo " + posici�nPalabra + " en el recortado (se recort� "
                            + recortado + ")");*/
                }
            }

            // Crea una lista con todas los pares de valores (entradas) del mapa
            List<Map.Entry<Integer, String>> lista
                    = new LinkedList<>(Posici�nImagen.entrySet());

            // Ordena la lista en funci�n de las key de las entradas (las posiciones)
            Collections.sort(lista, new Comparator<Map.Entry<Integer, String>>() {
                @Override
                public int compare(Map.Entry<Integer, String> entrada1,
                        Map.Entry<Integer, String> entrada2) {
                    return (entrada1.getKey()).compareTo(entrada2.getKey());
                }
            });

            // Utilizamos la lista con el tipo de imagen y su posici�n para ir recorriendo el String y creando los nodos
            int inicioImagen = 0;
            int recortado = 0;
            String habilidadRecortada = habilidad;
            for (Map.Entry<Integer, String> entrada : lista) {
                // System.out.println("coincidencias restantes " + coincidencias);
                // La posici�n de inicio de la imagen ha de tener en cuenta que el String se va recortando
                inicioImagen = entrada.getKey() - recortado;
                /*System.out.println("Buscamos la imagen " + entrada.getValue()
                        + " que se tiene que situar en la posici�n " + entrada.getKey() + ", pero como hemos recortado "
                        + recortado + " letras, y la frase ahora es " + habilidadRecortada + " en realidad est� en la posici�n "
                        + inicioImagen);*/
                Nodos.add(new Label(habilidadRecortada.substring(0, inicioImagen)));

                Nodos.add(new ImageView((constantes.IM�GENES.S�MBOLOS.Comunes.get(DiccionarioIm�genes.ImagenAPosici�nTabla.get(entrada.getValue())))));

                // El String de la habilidad se va recortando para ir avanzando en la parte que se compara
                habilidadRecortada = habilidadRecortada.substring(inicioImagen + entrada.getValue().length());
                recortado = habilidad.length() - habilidadRecortada.length();
                coincidencias--;
                // Cuando no quedan coincidencias, a�adimos el trozo de texto restante
                if (coincidencias == 0) {
                    Nodos.add(new Label(habilidadRecortada));
                    break;
                }

            }
        } else {
            Label etiqueta = new Label(habilidad);
            Nodos.add(etiqueta);
            if (habilidad.equals("Sin habilidades")) {
                etiqueta.setId("textogris");
            }

        }
        Caja.getChildren().addAll(Nodos);
        return Caja;
    }

    public HBox getRareza() {
        HBox Caja = new HBox();
        String rareza = Rareza.get();
        Label etiqueta = new Label(rareza);
        if (rareza.equals("Sin rareza")) {
            etiqueta.setId(constantes.IDs_CSS.TEXTO_GRIS.identificador);
        }
        Caja.getChildren().add(etiqueta);
        return Caja;
    }

    public Label getMazo() {
        Label etiqueta = new Label(Mazo.get());
        if (Mazo.get().equals("Sin mazo")) {
            etiqueta.setId(constantes.IDs_CSS.TEXTO_GRIS.identificador);
        }
        return etiqueta;
    }

    public String getNumeroAtributos() {
        /*HBox Caja = new HBox();
        Label etiqueta = new Label(NumeroAtributos.get());
        if (NumeroAtributos.get().equals("0")) {
            etiqueta.setId(constantes.IDs_CSS.TEXTO_GRIS.identificador);
        } else if (NumeroAtributos.get().equals("1")) {
            Caja.setStyle("-fx-background-color: yellow");
        } else {
            Caja.setStyle("-fx-background-color: green");
        }
        Caja.getChildren().add(etiqueta);*/
        return NumeroAtributos.get();
    }
}
