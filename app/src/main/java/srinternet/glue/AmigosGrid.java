package srinternet.glue;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
 * Created by JAVIER OLIVER ASUS on 05/09/2017.
 */

public class AmigosGrid {
    private String nombre;
    private String imageId;

    public Bitmap getImageData() {
        return ImageData;
    }

    public void setImageData(Bitmap imageData) {
        ImageData = imageData;
    }

    private Bitmap ImageData;
    private String identificador;
    private Activity activity;

    public Bitmap getImageDataOri() {
        return ImageDataOri;
    }

    public void setImageDataOri(Bitmap imageDataOri) {
        ImageDataOri = imageDataOri;
    }

    private Bitmap ImageDataOri;


    public AmigosGrid(String nombre, String imageId, String identificador, Activity acti) {
        this.nombre = nombre;
        this.imageId = imageId;
        this.identificador=identificador;
        activity=acti;

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

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }


    public void cogerFoto(final String id){

        try {
            String filename = id;
            File f = new File(activity.getBaseContext().getFilesDir(), filename);
            if(f.exists()){
                ImageDataOri = BitmapFactory.decodeFile(f.getAbsolutePath());
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
                            String params = "funcion=f00&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension + "&idFbPers=" + identificador + "&fotograf=" + filenam;
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
                            ImageDataOri = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                            FileOutputStream outputStream = activity.openFileOutput(filenam, Programa_Principal.MODE_PRIVATE);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            ImageDataOri.compress(Bitmap.CompressFormat.JPEG, 100, baos);
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
