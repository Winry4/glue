package srinternet.glue;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.facebook.AccessToken;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Rebeca on 16/08/2017.
 */

public class Album {
    String numero;
    String nombre;
    String foto;
    String id;

    public Bitmap getFotoData() {
        return fotoData;
    }

    public void setFotoData(Bitmap fotoData) {
        this.fotoData = fotoData;
    }

    Bitmap fotoData;

    public String getId() {
        return id;
    }

    public Album(String numero, String nombre, String foto, String id) {
        this.numero = numero;
        this.nombre = nombre;
        this.foto = foto;
        this.id=id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void cogerFoto(String id, Activity activity){

        try {
            String filename = id;
            File f = new File(activity.getBaseContext().getFilesDir(), filename);
            if(f.exists()){
                fotoData = BitmapFactory.decodeFile(f.getAbsolutePath());
            }else{
                try {
                    URL img_value = new URL("https://graph.facebook.com/" + id + "/picture?type=album&access_token=" + AccessToken.getCurrentAccessToken().getToken());

                    fotoData = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());

                    FileOutputStream outputStream = activity.openFileOutput(filename, Programa_Principal.MODE_PRIVATE);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    fotoData.compress(Bitmap.CompressFormat.JPEG, 100, baos);
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
