package pvzheroes.clases.clasesParaIm�genesEnCombos;

import java.util.ArrayList;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pvzheroes.clases.diccionarios.DiccionarioIm�genes;
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
        if (DiccionarioIm�genes.ImagenAArrayList.containsKey(nombreImagen)) { // Se comprueba que existe imagen asociada
            ArrayList<Image> listaIm�genes = DiccionarioIm�genes.ImagenAArrayList.get(nombreImagen);
            int �ndiceImagen = DiccionarioIm�genes.ImagenAPosici�n.get(nombreImagen);
                Image imagen = listaIm�genes.get(�ndiceImagen);
                return imagen;
        }
        return null;
    }
}
