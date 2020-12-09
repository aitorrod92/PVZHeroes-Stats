package pvzheroes.clases.di�logos;

import java.util.ArrayList;
import java.util.Optional;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pvzheroes.clases.constantes;

/**
 * Crea di�logos gen�ricos.
 *
 */
public class Di�logoGen�rico extends Dialog {

    private enum Textos {
        SIN_FONDOS("Sin fondos"),
        ACERCA_DE("Acerca de"),
        NUEVA_IMAGEN("Nueva imagen"),
        FALTA_SONIDO("Falta sonido");

        String TextoHeader;
        String TextoContenido;
        String T�tulo;
        Image Icono;
        ImageView Imagen;
        Dialog d = new Dialog();

        Textos(String texto) {
            if (texto.equals("Acerca de")) {
                T�tulo = "Acerca de";
                TextoHeader = "Aitor Rodr�guez Lopezosa\nEstudiante de DAM\n2020\nContacto: aitorrod@ucm.es";
                Imagen = new ImageView(constantes.IM�GENES.ERRORES.Comunes.get(2));
            } else if (texto.equals("Falta sonido")) {
                T�tulo = "Problema en la carga de sonido";
                TextoHeader = "No se puede cargar cierto sonido";
                TextoContenido = "Uno o varios de los sonidos han sido borrado o tienen nombres incorrectos. Para solucionarlo, "
                        + "comprueba que todos los sonidos existen (en la carpeta \"" + constantes.RUTAS_EXTERNAS.SONIDOS.Ruta + "\") "
                        + "y tienen sus nombres correctos (bot�n.wav, b�squeda.wav y error.wav) y, si alguno no existe, sustit�yelo "
                        + "por otro de formato .wav.";
                Imagen = new ImageView(constantes.IM�GENES.ERRORES.Comunes.get(3));
                Imagen.setFitWidth(96);
                Imagen.setPreserveRatio(true);
            } else if (texto.equals("Nueva imagen")) {
                T�tulo = "Nueva imagen a�adida";
                TextoHeader = "Imagen sin estilo asociado";
                TextoContenido = "Se ha detectado una imagen de fondo que no tiene estilo asociado. Esto puede deberse a:\n"
                        + "\n"
                        + "a) Has a�adido una o varias nuevas im�genes en la carpeta \"" + constantes.RUTAS_EXTERNAS.FONDOS.Ruta + "\".\n"
                        + "b) Has borrado el CSS de una o varias im�genes de la carpeta \"" + constantes.RUTAS_EXTERNAS.CSS.Ruta + ". "
                        + "Ten en cuenta que las carpetas que est�n ocultas no deber�an ser modificadas.\n"
                        + "\n"
                        + "El reinicio de la aplicaci�n deber�a hacer que todas las im�genes generen su propio "
                        + "estilo y est�n disponibles para su uso como fondo (si el formato es jpg, jpeg, bmp o png)."
                        + "\n\n"
                        + "Pulsa \"Aceptar\" para cerrar la aplicaci�n.";
                Imagen = new ImageView(constantes.IM�GENES.ERRORES.Comunes.get(0));
            } else if (texto.equals("Sin fondos")) {
                T�tulo = "No hay fondos";
                TextoHeader = "No se encuentra la carpeta de fondos o est� vac�a";
                TextoContenido = "El programa ha sido incapaz de encontrar fondos. Esto puede deberse a:\n"
                        + "\n"
                        + "a) No existe la carpeta \"" + constantes.RUTAS_EXTERNAS.FONDOS.Ruta + "\".\n"
                        + "b) La carpeta existe pero est� vac�a o incompleta.\n"
                        + "\n"
                        + "\n1. Pulsa \"Aceptar\" para iniciar la aplicaci�n sin fondos ni posibilidad de cambiarlos. "
                        + "Si quieres usar fondos, aseg�rate de que \"" + constantes.RUTAS_EXTERNAS.FONDOS.Ruta
                        + "\" existe y tiene al menos una imagen jpeg, jpg, bmp o png. Despu�s, vuelve a iniciar la aplicaci�n.";
            }
            Icono = constantes.IM�GENES.TIPOSG.Comunes.get(0);

        }
    }

    /**
     * Genera el tipo de di�logo indicado por su par�metro.
     *
     * @param TipoDi�logo String con el tipo de di�logo a generar, obtenido a
     * partir de "constantes.TIPOS_DI�LOGO".
     */
    public Di�logoGen�rico(String TipoDi�logo) {
        DialogPane d = this.getDialogPane();
        ArrayList<ButtonType> Botones = new ArrayList();
        ButtonType Bot�nAceptar = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        switch (TipoDi�logo) {
            case "ACERCA_DE":
                d.setHeaderText(Textos.ACERCA_DE.TextoHeader);
                d.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                Node header = d.lookup(".dialog-pane");
                header.setStyle("-fx-background-color: cadetblue;"
                        + "    -fx-font-style: italic;");
                d.setGraphic(Textos.ACERCA_DE.Imagen);
                ((Stage) d.getScene().getWindow()).getIcons().add(Textos.ACERCA_DE.Icono);
                this.setTitle(Textos.ACERCA_DE.T�tulo);
                ButtonType Bot�nEstupendo = new ButtonType("Estupendo", ButtonBar.ButtonData.OK_DONE);
                ButtonType Bot�nDe10 = new ButtonType("De 10", ButtonBar.ButtonData.OK_DONE);
                ButtonType Bot�nBienHecho = new ButtonType("Bien hecho", ButtonBar.ButtonData.OK_DONE);
                ButtonType Bot�nGenial = new ButtonType("Genial", ButtonBar.ButtonData.OK_DONE);
                Botones.add(Bot�nEstupendo);
                Botones.add(Bot�nDe10);
                Botones.add(Bot�nBienHecho);
                Botones.add(Bot�nGenial);
                break;
            case "FALTA_SONIDO":
                d.setHeaderText(Textos.FALTA_SONIDO.TextoHeader);
                d.setContentText(Textos.FALTA_SONIDO.TextoContenido);
                d.setGraphic(Textos.FALTA_SONIDO.Imagen);
                ((Stage) d.getScene().getWindow()).getIcons().add(Textos.FALTA_SONIDO.Icono);
                this.setTitle(Textos.FALTA_SONIDO.T�tulo);
                Botones.add(Bot�nAceptar);
                break;
            case "NUEVA_IMAGEN":
                d.setHeaderText(Textos.NUEVA_IMAGEN.TextoHeader);
                d.setContentText(Textos.NUEVA_IMAGEN.TextoContenido);
                d.setGraphic(Textos.NUEVA_IMAGEN.Imagen);
                ((Stage) d.getScene().getWindow()).getIcons().add(Textos.NUEVA_IMAGEN.Icono);
                this.setTitle(Textos.NUEVA_IMAGEN.T�tulo);
                Botones.add(Bot�nAceptar);
                break;
            default:
                d.setHeaderText(Textos.SIN_FONDOS.TextoHeader);
                d.setContentText(Textos.SIN_FONDOS.TextoContenido);
                this.setTitle(Textos.SIN_FONDOS.T�tulo);
                Botones.add(Bot�nAceptar);
                break;
        }
        this.getDialogPane().getButtonTypes().addAll(Botones);
        this.showAndWait();
    }

}
