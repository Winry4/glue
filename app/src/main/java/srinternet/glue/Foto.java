package srinternet.glue;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.facebook.AccessToken;

import java.net.URL;

/**
 * Created by Ordenador on 09/09/2017.
 */

public class Foto {
    public Foto(String id){
        nombre=id;
    }


    private String nombre;


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


    private Bitmap imageId;


    public void cogerfoto(String id, Activity activity){
        try{
             URL img_value = new URL("https://graph.facebook.com/" + nombre + "/picture?access_token=" + AccessToken.getCurrentAccessToken().getToken());
            imageId= BitmapFactory.decodeStream(img_value.openConnection().getInputStream());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
