package srinternet.glue;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by bersus96 on 12/9/17.
 */

public class Princi {
    private String sesion;
    private String nombre;
    private Bitmap ImageData;
    private String idfoto;
    private String oculto;

    public Princi(String sesion, String nombre, String oculto,String idfoto) {
        this.sesion = sesion;
        this.nombre = nombre;
        this.oculto = oculto;
        this.idfoto = idfoto;
    }

    public String getIdfoto() {
        return idfoto;
    }

    public void setIdfoto(String idfoto) {
        this.idfoto = idfoto;
    }

    public String getSesion() {
        return sesion;
    }

    public void setSesion(String sesion) {
        this.sesion = sesion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOculto() {
        return oculto;
    }

    public void setOculto(String oculto) {
        this.oculto = oculto;
    }

    public Bitmap getImageData() {
        return ImageData;
    }

    public void setImageData(Bitmap imageData) {
        ImageData = imageData;
    }



    public void cogerFoto(final String id, Activity activity){

        try {
            String filename = id;
            File f = new File(activity.getBaseContext().getFilesDir(), filename);
            if(f.exists()){
                ImageData = BitmapFactory.decodeFile(f.getAbsolutePath());
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
                    String params = "funcion=f00&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension + "&idFbPers=" + sesion + "&fotograf=" + filenam;


                    wr.print(params);
                    wr.close();
                    conn.connect();
                    String res="";
                    Scanner Stream = new Scanner(conn.getInputStream());
                    while(Stream.hasNextLine())
                        res+=(Stream.nextLine());

                    conn.disconnect();

                    JSONObject json= new JSONObject(res);

                    System.out.println("SSSSSS" + res);

                    byte[] decodedString = Base64.decode(json.getString("foto"), Base64.DEFAULT);
                    ImageData = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    FileOutputStream outputStream = activity.openFileOutput(filenam, Programa_Principal.MODE_PRIVATE);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageData.compress(Bitmap.CompressFormat.JPEG, 100, baos);
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
