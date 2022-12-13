package srinternet.glue;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by JAVIER OLIVER ASUS on 15/08/2017.
 */

public class People {
    public String getIdFace() {
        return idFace;
    }

    public Bitmap getPerfilFot() {
        return PerfilFot;
    }

    public String getPerfilFotNom() {
        return PerfilFotNom;
    }

    public int getnAmigosComun() {
        return nAmigosComun;
    }

    public int getNumFotos() {
        return numFotos;
    }

    public JSONArray getAmigosComun() {
        return amigosComun;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getCity() {
        return city;
    }

    public boolean isFav() {
        return fav;
    }

    private String idFace;
    private Bitmap PerfilFot;
    private String  PerfilFotNom;
    private int nAmigosComun;
    private int numFotos;
    private JSONArray amigosComun;
    private String nombre;
    private int edad;
    private String city;
    private boolean fav;
    private Activity activity;

    public void setIdFace(String idFace) {
        this.idFace = idFace;
    }

    public void setPerfilFot(Bitmap perfilFot) {
        PerfilFot = perfilFot;
    }

    public void setPerfilFotNom(String perfilFotNom) {
        PerfilFotNom = perfilFotNom;
    }

    public void setnAmigosComun(int nAmigosComun) {
        this.nAmigosComun = nAmigosComun;
    }

    public void setNumFotos(int numFotos) {
        this.numFotos = numFotos;
    }

    public void setAmigosComun(JSONArray amigosComun) {
        this.amigosComun = amigosComun;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public People(JSONObject ini, Activity ac){
        try {
            activity=ac;
            idFace = ini.getString("IDFACEBO");
            numFotos = Integer.parseInt(ini.getString("TOTALFOTOS"));

            PerfilFotNom=ini.getString("FOTOGRAF");

            nAmigosComun= Integer.parseInt(ini.getString("TOTCOMUN"));
            nombre=ini.getString("PRINOMFB");
            edad=Integer.parseInt(ini.getString("EDADUSER"));
            city=ini.getString("CIUDADFB");
            fav=ini.getString("FAVORITO").equals("1");
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        String params="funcion=f08&idFacebo="+Memoria.UserFBId+"&idSesion="+Memoria.idSension+"&idFbPers="+idFace;
                        String response="";
                        HttpURLConnection conn =(HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                        conn.setRequestMethod("POST");
                        conn.setDoOutput(true);
                        conn.setDoInput(true);
                        PrintWriter wr = new PrintWriter (
                                conn.getOutputStream ());
                        wr.print(params);
                        wr.close();
                        conn.connect();
                        Scanner inStream = new Scanner(conn.getInputStream());
                        while(inStream.hasNextLine())
                            response+=(inStream.nextLine());

                        conn.disconnect();

                        JSONObject json= new JSONObject(response);
                        amigosComun=json.getJSONArray("amigosComun");

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void cogerAmigos(){
        try {
            int amigos = amigosComun.length();

            for (int i = 0; i < amigos; i++) {
                String sesion = amigosComun.getJSONObject(i).getString("IDFACEBO");
                String nombre = amigosComun.getJSONObject(i).getString("PRINOMFB");
                String foto = amigosComun.getJSONObject(i).getString("FOTOGRAF");
                String oculto = amigosComun.getJSONObject(i).getString("ACTIVO");


                Princi bg = new Princi(sesion, nombre, oculto, foto);
                Memoria.princiList.add(bg);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void cogerFoto(final String id){

        try {
            String filename = id;
            PerfilFotNom=id;
            File f = new File(activity.getBaseContext().getFilesDir(), filename);
            if(f.exists()){

                PerfilFot = BitmapFactory.decodeFile(f.getAbsolutePath());
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
                            String params = "funcion=f00&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension + "&idFbPers=" + idFace + "&fotograf=" + filenam;
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
                            PerfilFot = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                            FileOutputStream outputStream = activity.openFileOutput(filenam, Programa_Principal.MODE_PRIVATE);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            PerfilFot.compress(Bitmap.CompressFormat.JPEG, 100, baos);
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

    @Override
    public String toString() {
        return "People{" +
                "idFace='" + idFace + '\'' +
                ", PerfilFotNom=" + PerfilFotNom +
                ", nAmigosComun=" + nAmigosComun +
                ", numFotos=" + numFotos +
                ", amigosComun=" + amigosComun +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", city='" + city + '\'' +
                ", fav=" + fav +
                ", activity=" + activity +
                '}';
    }
}