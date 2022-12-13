package srinternet.glue;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Created by Rebeca on 15/08/2017.
 */

public class MensajeDeTexto {
    String texto;
    String hora;
    int id;

    public MensajeDeTexto(String texto, String hora, int id) {
        String amigo =texto;
        if(texto.contains("uD")){
            /*amigo = texto.replace("uD","\\uD");*/
        }
        if(texto.contains("u00F1")){
            amigo = amigo.replace("u00F1","ñ");
        }
        if(texto.contains("u263A")){
            /*amigo = amigo.replace("u263A","\\u263A");*/
        }

        this.texto=StringEscapeUtils.unescapeJava(amigo);
        this.hora = hora;
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
