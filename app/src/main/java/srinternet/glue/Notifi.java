package srinternet.glue;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.util.Base64;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class Notifi {
    private RoundedBitmapDrawable fotoBRedondear;
    private Bitmap fotoA;
    private Bitmap fotoB;
    private String fotoidA;
    private String fotoidB;
    private String fecha;
    private String text;
    private String idNot;
    private String idFbA;
    private String idFbB;
    private String tipo;
    private String nueva;

    public RoundedBitmapDrawable getFotoBRedondear() {
        return fotoBRedondear;
    }

    public void setFotoBRedondear(RoundedBitmapDrawable fotoBRedondear) {
        this.fotoBRedondear = fotoBRedondear;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIdNot() {
        return idNot;
    }

    public void setIdNot(String idNot) {
        this.idNot = idNot;
    }

    public Notifi(String id, String fecha, String text, String idFbA, String idFbB,String idfotoa, String idfotob, String tipo, String nueva) {
        this.idNot=id;
        this.fecha = fecha;
        this.text = text;
        this.idFbA=idFbA;
        this.idFbB=idFbB;
        this.fotoidA=idfotoa;
        this.fotoidB=idfotob;
        this.tipo=tipo;
        this.nueva=nueva;
    }

    public String getNueva() {
        return nueva;
    }

    public void setNueva(String nueva) {
        this.nueva = nueva;
    }

    public Bitmap getFotoA() {
        return fotoA;
    }

    public void setFotoA(Bitmap fotoA) {
        this.fotoA = fotoA;
    }

    public Bitmap getFotoB() {
        return fotoB;
    }

    public void setFotoB(Bitmap fotoB) {
        this.fotoB = fotoB;
    }

    public String getFotoidA() {
        return fotoidA;
    }

    public void setFotoidA(String fotoidA) {
        this.fotoidA = fotoidA;
    }

    public String getFotoidB() {
        return fotoidB;
    }

    public void setFotoidB(String fotoidB) {
        this.fotoidB = fotoidB;
    }

    public String getIdFbA() {
        return idFbA;
    }

    public void setIdFbA(String idFbA) {
        this.idFbA = idFbA;
    }

    public String getIdFbB() {
        return idFbB;
    }

    public void setIdFbB(String idFbB) {
        this.idFbB = idFbB;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getText() {
        return text;
    }

    public Notifi() {
    }

    public void setText(String text) {
        this.text = text;
    }
    public void cogerFotoB(String id, Activity activity){

        try {
            String filename = id;
            File f = new File(activity.getBaseContext().getFilesDir(), filename);
            if(f.exists()){
                fotoB = BitmapFactory.decodeFile(f.getAbsolutePath());
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
                    String params = "funcion=f00&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension + "&idFbPers=" +idFbB + "&fotograf=" + filenam;
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
                    fotoA = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    FileOutputStream outputStream = activity.openFileOutput(filenam, Programa_Principal.MODE_PRIVATE);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    fotoA.compress(Bitmap.CompressFormat.JPEG, 100, baos);
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

    public void cogerFoto(final String id, Activity activity){

        try {
            String filename = id;
            File f = new File(activity.getBaseContext().getFilesDir(), filename);
            if(f.exists()){
                fotoA = BitmapFactory.decodeFile(f.getAbsolutePath());
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
                    String params = "funcion=f00&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension + "&idFbPers=" +idFbA + "&fotograf=" + filenam;
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
                    fotoA = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    FileOutputStream outputStream = activity.openFileOutput(filenam, Programa_Principal.MODE_PRIVATE);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    fotoA.compress(Bitmap.CompressFormat.JPEG, 100, baos);
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
