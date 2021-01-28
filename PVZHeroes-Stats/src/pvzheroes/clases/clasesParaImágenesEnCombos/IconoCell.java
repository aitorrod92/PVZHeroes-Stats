package pvzheroes.clases.clasesParaImágenesEnCombos;

import java.util.ArrayList;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pvzheroes.clases.diccionarios.DiccionarioImágenes;
import pvzheroes.clases.diccionarios.DiccionarioSQL;

public class IconoCell extends ListCell<String> {

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(item);
            setGraphic(new ImageView(obtenerImagen(item)));
        }
    }

    public Image obtenerImagen(String nombreImagen) {
        if (DiccionarioSQL.FiltroASQL.containsKey(nombreImagen)) { // Se comprueba si es necesario traducir o adaptar
            System.out.println("Contiene key");
            nombreImagen = DiccionarioSQL.FiltroASQL.get(nombreImagen);
        }
        if (DiccionarioImágenes.ImagenAArrayList.containsKey(nombreImagen)) { // Se comprueba que existe imagen asociada
            ArrayList<Image> listaImágenes = DiccionarioImágenes.ImagenAArrayList.get(nombreImagen);
            int índiceImagen = DiccionarioImágenes.ImagenAPosición.get(nombreImagen);
                Image imagen = listaImágenes.get(índiceImagen);
                return imagen;
        }
        return null;
    }
}
