package pvzheroes.clases.diálogos;

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
 * Crea diálogos genéricos.
 *
 */
public class DiálogoGenérico extends Dialog {

    private enum Textos {
        SIN_FONDOS("Sin fondos"),
        ACERCA_DE("Acerca de"),
        NUEVA_IMAGEN("Nueva imagen"),
        FALTA_SONIDO("Falta sonido");

        String TextoHeader;
        String TextoContenido;
        String Título;
        Image Icono;
        ImageView Imagen;
        Dialog d = new Dialog();

        Textos(String texto) {
            if (texto.equals("Acerca de")) {
                Título = "Acerca de";
                TextoHeader = "Aitor Rodríguez Lopezosa\nEstudiante de DAM\n2020\nContacto: aitorrod@ucm.es";
                Imagen = new ImageView(constantes.IMÁGENES.ERRORES.Comunes.get(2));
            } else if (texto.equals("Falta sonido")) {
                Título = "Problema en la carga de sonido";
                TextoHeader = "No se puede cargar cierto sonido";
                TextoContenido = "Uno o varios de los sonidos han sido borrado o tienen nombres incorrectos. Para solucionarlo, "
                        + "comprueba que todos los sonidos existen (en la carpeta \"" + constantes.RUTAS_EXTERNAS.SONIDOS.Ruta + "\") "
                        + "y tienen sus nombres correctos (botón.wav, búsqueda.wav y error.wav) y, si alguno no existe, sustitúyelo "
                        + "por otro de formato .wav.";
                Imagen = new ImageView(constantes.IMÁGENES.ERRORES.Comunes.get(3));
                Imagen.setFitWidth(96);
                Imagen.setPreserveRatio(true);
            } else if (texto.equals("Nueva imagen")) {
                Título = "Nueva imagen añadida";
                TextoHeader = "Imagen sin estilo asociado";
                TextoContenido = "Se ha detectado una imagen de fondo que no tiene estilo asociado. Esto puede deberse a:\n"
                        + "\n"
                        + "a) Has añadido una o varias nuevas imágenes en la carpeta \"" + constantes.RUTAS_EXTERNAS.FONDOS.Ruta + "\".\n"
                        + "b) Has borrado el CSS de una o varias imágenes de la carpeta \"" + constantes.RUTAS_EXTERNAS.CSS.Ruta + ". "
                        + "Ten en cuenta que las carpetas que están ocultas no deberían ser modificadas.\n"
                        + "\n"
                        + "El reinicio de la aplicación debería hacer que todas las imágenes generen su propio "
                        + "estilo y estén disponibles para su uso como fondo (si el formato es jpg, jpeg, bmp o png)."
                        + "\n\n"
                        + "Pulsa \"Aceptar\" para cerrar la aplicación.";
                Imagen = new ImageView(constantes.IMÁGENES.ERRORES.Comunes.get(0));
            } else if (texto.equals("Sin fondos")) {
                Título = "No hay fondos";
                TextoHeader = "No se encuentra la carpeta de fondos o está vacía";
                TextoContenido = "El programa ha sido incapaz de encontrar fondos. Esto puede deberse a:\n"
                        + "\n"
                        + "a) No existe la carpeta \"" + constantes.RUTAS_EXTERNAS.FONDOS.Ruta + "\".\n"
                        + "b) La carpeta existe pero está vacía o incompleta.\n"
                        + "\n"
                        + "\n1. Pulsa \"Aceptar\" para iniciar la aplicación sin fondos ni posibilidad de cambiarlos. "
                        + "Si quieres usar fondos, asegúrate de que \"" + constantes.RUTAS_EXTERNAS.FONDOS.Ruta
                        + "\" existe y tiene al menos una imagen jpeg, jpg, bmp o png. Después, vuelve a iniciar la aplicación.";
            }
            Icono = constantes.IMÁGENES.TIPOSG.Comunes.get(0);

        }
    }

    /**
     * Genera el tipo de diálogo indicado por su parámetro.
     *
     * @param TipoDiálogo String con el tipo de diálogo a generar, obtenido a
     * partir de "constantes.TIPOS_DIÁLOGO".
     */
    public DiálogoGenérico(String TipoDiálogo) {
        DialogPane d = this.getDialogPane();
        ArrayList<ButtonType> Botones = new ArrayList();
        ButtonType BotónAceptar = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        switch (TipoDiálogo) {
            case "ACERCA_DE":
                d.setHeaderText(Textos.ACERCA_DE.TextoHeader);
                d.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                Node header = d.lookup(".dialog-pane");
                header.setStyle("-fx-background-color: cadetblue;"
                        + "    -fx-font-style: italic;");
                d.setGraphic(Textos.ACERCA_DE.Imagen);
                ((Stage) d.getScene().getWindow()).getIcons().add(Textos.ACERCA_DE.Icono);
                this.setTitle(Textos.ACERCA_DE.Título);
                ButtonType BotónEstupendo = new ButtonType("Estupendo", ButtonBar.ButtonData.OK_DONE);
                ButtonType BotónDe10 = new ButtonType("De 10", ButtonBar.ButtonData.OK_DONE);
                ButtonType BotónBienHecho = new ButtonType("Bien hecho", ButtonBar.ButtonData.OK_DONE);
                ButtonType BotónGenial = new ButtonType("Genial", ButtonBar.ButtonData.OK_DONE);
                Botones.add(BotónEstupendo);
                Botones.add(BotónDe10);
                Botones.add(BotónBienHecho);
                Botones.add(BotónGenial);
                break;
            case "FALTA_SONIDO":
                d.setHeaderText(Textos.FALTA_SONIDO.TextoHeader);
                d.setContentText(Textos.FALTA_SONIDO.TextoContenido);
                d.setGraphic(Textos.FALTA_SONIDO.Imagen);
                ((Stage) d.getScene().getWindow()).getIcons().add(Textos.FALTA_SONIDO.Icono);
                this.setTitle(Textos.FALTA_SONIDO.Título);
                Botones.add(BotónAceptar);
                break;
            case "NUEVA_IMAGEN":
                d.setHeaderText(Textos.NUEVA_IMAGEN.TextoHeader);
                d.setContentText(Textos.NUEVA_IMAGEN.TextoContenido);
                d.setGraphic(Textos.NUEVA_IMAGEN.Imagen);
                ((Stage) d.getScene().getWindow()).getIcons().add(Textos.NUEVA_IMAGEN.Icono);
                this.setTitle(Textos.NUEVA_IMAGEN.Título);
                Botones.add(BotónAceptar);
                break;
            default:
                d.setHeaderText(Textos.SIN_FONDOS.TextoHeader);
                d.setContentText(Textos.SIN_FONDOS.TextoContenido);
                this.setTitle(Textos.SIN_FONDOS.Título);
                Botones.add(BotónAceptar);
                break;
        }
        this.getDialogPane().getButtonTypes().addAll(Botones);
        this.showAndWait();
    }

}
