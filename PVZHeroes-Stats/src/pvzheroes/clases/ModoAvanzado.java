package pvzheroes.clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.media.MediaPlayer;
import static pvzheroes.clases.FXMLDocumentController.conn;
import pvzheroes.clases.diccionarios.DiccionarioPropiedades;
import pvzheroes.clases.m�todosCompartidos.M�todosCompartidos;

public class ModoAvanzado {

    FXMLDocumentController c;

    ModoAvanzado(FXMLDocumentController c) {
        this.c = c;
    }

    protected Boolean B�squeda() throws SQLException {
        new M�todosCompartidos().ActivarTabla(true, c);
        String sentenciaSQL = c.Consultasimple.getText();
        if (ContieneFunci�nAgregado(sentenciaSQL)) {
            LanzarMensajeError("SentenciaGrupo");
            return false;
        }

        ResultSet rs = LanzarB�squeda(sentenciaSQL);
        if (rs == null) {
            return false;
        }

        String ColumnasString = "";
        if (!sentenciaSQL.toUpperCase().contains("DISTINCT")) { // POSIBLES PROBLEMAS CON MAY�SCULAS Y MIN�SCULAS
            ColumnasString = sentenciaSQL.toUpperCase().substring(sentenciaSQL.indexOf(" "), sentenciaSQL.toUpperCase().indexOf("FROM") - 1).trim();
        } else {
            System.out.println(sentenciaSQL);
            ColumnasString = sentenciaSQL.toUpperCase().substring(sentenciaSQL.toUpperCase().indexOf("DISTINCT ") + 9, sentenciaSQL.toUpperCase().indexOf("FROM") - 1);
            ColumnasString = ColumnasString.replace("(", "").replace(")", "").trim();
        }

        System.out.println(ColumnasString);
        String[] ColumnasBuscadas = ColumnasString.replace(" ", "").split(",");
        c.ConfigurarTabla(ColumnasBuscadas);
        c.data.clear();

        if (ColumnasString.equals("*")) {
            while (rs.next()) {
                c.data.add(new Carta(rs.getString(constantes.COLUMNAS[0]),
                        String.valueOf(rs.getInt(constantes.COLUMNAS[1])), String.valueOf(rs.getInt(constantes.COLUMNAS[2])),
                        String.valueOf(rs.getInt(constantes.COLUMNAS[3])), rs.getString(constantes.COLUMNAS[4]),
                        rs.getString(constantes.COLUMNAS[5]), rs.getString(constantes.COLUMNAS[6]),
                        rs.getString(constantes.COLUMNAS[7]), rs.getString(constantes.COLUMNAS[8]), rs.getString(constantes.COLUMNAS[9]),
                        rs.getString(constantes.COLUMNAS[10]), rs.getString(constantes.COLUMNAS[11])));
            }
        } else {
            while (rs.next()) {
                ArrayList<String> valoresConNulls = new ArrayList();
                // Se comparan los nombres est�ndares de columna con los de las buscadas
                for (String Columna : constantes.COLUMNAS) {
                    String valorColumna = null;
                    for (String columnabuscada : ColumnasBuscadas) {
                        // Si hay coincidencia, se a�ade el valor que corresponde a dicha columna
                        if (Columna.equalsIgnoreCase(columnabuscada)) {
                            valorColumna = rs.getString(Columna);
                            valoresConNulls.add(valorColumna);
                            break;
                        }
                    }
                    // Si no hay coincidencia (porque no se est� buscando esa columna), se a�ade null
                    if (valorColumna == null) {
                        valoresConNulls.add(valorColumna);
                    }
                }
                // La lista con nulls permite que no falte ning�n argumento para el constructor de "Carta"
                c.data.add(new Carta(valoresConNulls.get(0), String.valueOf(valoresConNulls.get(1)),
                        String.valueOf(valoresConNulls.get(2)), String.valueOf(valoresConNulls.get(3)),
                        valoresConNulls.get(4), valoresConNulls.get(5), valoresConNulls.get(6),
                        valoresConNulls.get(7), valoresConNulls.get(8), valoresConNulls.get(9),
                        valoresConNulls.get(10), valoresConNulls.get(11)));
            }
        }
        return true;
    }

    protected Boolean B�squedaGrupo() throws SQLException {
        new M�todosCompartidos().ActivarTabla(false, c);
        String sentencias = c.Consultasdegrupo.getText();
        String[] sentenciasSeparadas = sentencias.trim().split(";");
        String texto = ProcesarResultadosGrupo(sentenciasSeparadas);
        if (texto == null) {
            return false;
        }
        c.getAreaTextoGrupo().setText(texto);
        return true;
    }

    private String ProcesarResultadosGrupo(String[] sentenciasSeparadas) throws SQLException {
        String texto = "";
        for (int i = 0; i < sentenciasSeparadas.length; i++) {
            if (!ContieneFunci�nAgregado(sentenciasSeparadas[i])) {
                LanzarMensajeError("SentenciaNoGrupo");
                return null;
            }
            ResultSet rs = LanzarB�squeda(sentenciasSeparadas[i]);
            if (!rs.isClosed() && rs!=null) {
                String separador = "";
                if (i != 0) {
                    separador = "\n";
                }
                if (sentenciasSeparadas[i].contains(" AS ")) {
                    String NombreGrupo = sentenciasSeparadas[i].
                            substring(sentenciasSeparadas[i].indexOf(" AS ") 
                                    + 4, sentenciasSeparadas[i].indexOf(" FROM"));
                    texto = texto + separador + "- El valor de " + NombreGrupo
                            + " es " + new DecimalFormat("#.##").format(rs.getDouble(1));
                } else {
                    texto = texto + separador + "- El valor de la sentencia " 
                            + (i + 1) + " es " + new DecimalFormat("#.##").format(rs.getDouble(1));
                }
            } else {
                LanzarMensajeError("Sintaxis");
            }
        }
        return texto;
    }

    private ResultSet LanzarB�squeda(String sentenciaSQL) {
        c.getAreaTextoGrupo().clear();
        ResultSet rs;
        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery(sentenciaSQL);
        } catch (SQLException e) {
            LanzarMensajeError("Sintaxis");
            return null;
        }
        return rs;
    }

    private Boolean ContieneFunci�nAgregado(String sentencia) {
        String SentenciaEnMay�sculas = sentencia.toUpperCase();
        if (SentenciaEnMay�sculas.contains("AVG") || SentenciaEnMay�sculas.contains("SUM")
                || SentenciaEnMay�sculas.contains("COUNT") || SentenciaEnMay�sculas.contains("MAX")
                || SentenciaEnMay�sculas.contains("MIN")) {
            return true;
        } else {
            return false;
        }
    }

    private void LanzarMensajeError(String TipoDeError) {
        String textoError = "";
        if (TipoDeError.equals("Sintaxis")) {
            textoError = "Comprueba que la sintaxis de la consulta y el nombre de las columnas y tabla son correctos.";
        } else if (TipoDeError.equals("SentenciaNoGrupo")) {
            textoError = "Una o varias de la sentencias carecen de funciones de agregado. Utiliza el campo de texto superior";
        } else {
            textoError = "La sentencia tiene una funci�n de agregado. Utiliza el �rea de texto inferior.";
        }
        Alert a = new Alert(AlertType.ERROR, textoError, ButtonType.OK);
        a.setHeaderText("Error en la b�squeda");
        a.getDialogPane().setMaxWidth(220);
        a.show();
        if (DiccionarioPropiedades.PROP.getProperty("sonido").equals("ON")) {
            new MediaPlayer(constantes.SONIDOS.ERROR.sonido).play();
        }
    }

}
