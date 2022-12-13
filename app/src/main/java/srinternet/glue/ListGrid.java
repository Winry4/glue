package srinternet.glue;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Rebeca on 08/08/2017.
 */

public class ListGrid {
    private String nombre;
    private Bitmap imageId;
    private String fecha;
    private String mensaje;
    private String identificador;

    private String idFb;
    private String idFoto;

    public String getIdFb() {
        return idFb;
    }

    public void setIdFb(String idFb) {
        this.idFb = idFb;
    }

    public String getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(String idFoto) {
        this.idFoto = idFoto;
    }


    public ListGrid(String nombre, String imageId, String fecha, String mensaje, String identificador, String fb) {
        this.nombre = nombre;
        this.idFoto = imageId;
        this.fecha = fecha;
        this.mensaje = mensaje;
        this.identificador=identificador;
        idFb=fb;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Bitmap getImageId() {
        return imageId;
    }

    public void setImageId(Bitmap imageId) {
        this.imageId = imageId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void cogerFoto(String id, Activity activity){

        try {
            String filename = id;
            File f = new File(activity.getBaseContext().getFilesDir(), filename);
            if(f.exists()){
                imageId = BitmapFactory.decodeFile(f.getAbsolutePath());
            }else{
                try {
                    String filenam=id;
                    String response = "";
                    HttpURLConnection conn = (HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    PrintWriter wr = new PrintWriter(
                            conn.getOutputStream());
                    String params = "funcion=f00&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension + "&idFbPers=" + idFb + "&fotograf=" + filenam;
                    wr.print(params);
                    wr.close();
                    conn.connect();
                    String res="";
                    Scanner Stream = new Scanner(conn.getInputStream());
                    while(Stream.hasNextLine())
                        res+=(Stream.nextLine());

                    conn.disconnect();

                    JSONObject json= new JSONObject(res);
                    byte[] decodedString = Base64.decode(json.getString("foto"), Base64.DEFAULT);
                    imageId = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    FileOutputStream outputStream = activity.openFileOutput(filenam, Programa_Principal.MODE_PRIVATE);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imageId.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageData = baos.toByteArray();
                    outputStream.write(imageData);
                    outputStream.close();
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        }catch(Exception e){
            System.out.println(e.toString());
        }

    }
}
