package srinternet.glue;

/**
 * Created by Rebeca on 13/09/2017.
 */

public class History {

String pregunta;
    String codmensa;
    String resp;
    String estad;

    public History(String pregunta, String codmensa, String resp, String estad) {
        this.estad="";
        if(estad.equals("0")){
            this.estad="En revisión";
        }else if(estad.equals("2")){
            this.estad="Rechazada";
        }
        this.pregunta = pregunta;
        this.codmensa = codmensa;
        this.resp = resp;
    }

    public String getEstad() {
        return estad;
    }

    public void setEstad(String estad) {
        this.estad = estad;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getCodmensa() {
        return codmensa;
    }

    public void setCodmensa(String codmensa) {
        this.codmensa = codmensa;
    }

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    @Override
    public String toString() {
        return "History{" +
                "pregunta='" + pregunta + '\'' +
                ", codmensa='" + codmensa + '\'' +
                ", resp='" + resp + '\'' +
                ", estad='" + estad + '\'' +
                '}';
    }
}
