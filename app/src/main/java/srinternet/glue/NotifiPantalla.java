package srinternet.glue;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NotifiPantalla extends AppCompatActivity {
    Dialog customDialog = null;
    JSONObject j;
    String titulo, mensaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifi_pantalla);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ImageView image=(ImageView)findViewById(R.id.imageView27);
        ImageView image2=(ImageView)findViewById(R.id.imageView40);
        final int posi=getIntent().getExtras().getInt("posi");
        image.setImageBitmap(Memoria.notifiList.get(posi).getFotoA());
        TextView t=(TextView)findViewById(R.id.texto_no);
        t.setText(Memoria.notifiList.get(posi).getText());
        image2.setImageDrawable(Memoria.notifiList.get(posi).getFotoBRedondear());
        Button chat=(Button)findViewById(R.id.chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Notifi not = Memoria.notifiList.get(posi);
                            HttpURLConnection conn = (HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                            conn.setRequestMethod("POST");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);
                            PrintWriter wr = new PrintWriter(
                                    conn.getOutputStream());
                            String param = "funcion=f15&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension + "&idNotifi=" + not.getIdNot() + "&numBoton=2";
                            wr.print(param);
                            wr.close();
                            conn.connect();
                            String ress="";
                            Scanner Strea = new Scanner(conn.getInputStream());
                            while(Strea.hasNextLine())
                                ress+=(Strea.nextLine());

                            j= new JSONObject(ress);
                            Memoria.notifiList.clear();
                            Memoria.Notificaciones=j.getJSONArray("notificaciones");
                            Memoria.Notifis=j.getJSONArray("notifiMostrar");


                            for (int i=0;i<Memoria.Notificaciones.length();i++){
                                String fecha=Memoria.Notificaciones.getJSONObject(i).getString("FECHAREG");
                                String mensaje=Memoria.Notificaciones.getJSONObject(i).getString("NOTIMENS");
                                String id= Memoria.Notificaciones.getJSONObject(i).getString("IDNOTIFI");

                                String idfbb=Memoria.Notificaciones.getJSONObject(i).getString("IDFOTOGB");
                                String idfba=Memoria.Notificaciones.getJSONObject(i).getString("IDFOTOGA");
                                String idfotoa= Memoria.Notificaciones.getJSONObject(i).getString("FOTOGRAA");
                                String idfotob= Memoria.Notificaciones.getJSONObject(i).getString("FOTOGRAB");//CAMBIAR ESTO POR A PORK SON UNOS PATANES
                                String tipo=Memoria.Notificaciones.getJSONObject(i).getString("TIPALERT");
                                Notifi bg = new Notifi(id,fecha, mensaje, idfba, idfbb,idfotoa, idfotob ,tipo, "0");
                                Memoria.notifiList.add(bg);
                            }
                            for (int i=0; i<Memoria.Notifis.length(); i++) {
                                String fecha=Memoria.Notifis.getJSONObject(i).getString("FECHAREG");
                                String mensaje=Memoria.Notifis.getJSONObject(i).getString("NOTIMENS");
                                String id= Memoria.Notifis.getJSONObject(i).getString("IDNOTIFI");
                                String idfbb=Memoria.Notifis.getJSONObject(i).getString("IDFOTOGB");
                                String idfba=Memoria.Notifis.getJSONObject(i).getString("IDFOTOGA");
                                String idfotoa= Memoria.Notifis.getJSONObject(i).getString("FOTOGRAA");
                                String idfotob= Memoria.Notifis.getJSONObject(i).getString("FOTOGRAB");//CAMBIAR ESTO POR A PORK SON UNOS PATANES
                                Notifi bg = new Notifi(id,fecha, mensaje, idfba, idfbb,idfotoa, idfotob ,"1", "1");
                                Memoria.notifiList.add(bg);
                            }
                            conn.disconnect();
                            finish();

                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        Button tarde=(Button)findViewById(R.id.tarde);
        tarde.setText(Idioma.Not_B01);
        tarde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Notifi not = Memoria.notifiList.get(posi);
                            HttpURLConnection conn = (HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                            conn.setRequestMethod("POST");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);
                            PrintWriter wr = new PrintWriter(
                                    conn.getOutputStream());
                            String param = "funcion=f15&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension + "&idNotifi=" + not.getIdNot() + "&numBoton=1";
                            wr.print(param);
                            wr.close();
                            conn.connect();
                            String ress="";
                            Scanner Strea = new Scanner(conn.getInputStream());
                            while(Strea.hasNextLine())
                                ress+=(Strea.nextLine());


                            conn.disconnect();
                            finish();

                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        Button hecho=(Button)findViewById(R.id.hecho);
        hecho.setText(Idioma.Not_B04);
        hecho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Notifi not = Memoria.notifiList.get(posi);
                            HttpURLConnection conn = (HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                            conn.setRequestMethod("POST");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);
                            PrintWriter wr = new PrintWriter(
                                    conn.getOutputStream());
                            String param = "funcion=f30&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension + "&tipoProd=p2";
                            wr.print(param);
                            wr.close();
                            conn.connect();
                            String ress="";
                            Scanner Strea = new Scanner(conn.getInputStream());
                            while(Strea.hasNextLine())
                                ress+=(Strea.nextLine());

                            j=new JSONObject(ress);
                            conn.disconnect();
                            mensaje=j.getString("precio");
                            NotifiPantalla.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mostrar(v,mensaje, posi);
                                }
                            });


                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

    }
    public void mostrar(View view, String msn, final int posi)
    {
        // con este tema personalizado evitamos los bordes por defecto
        customDialog = new Dialog(this,R.style.Theme_Dialog_Translucent);
        //deshabilitamos el título por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.alert_dialog);


        TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
        titulo.setText(getString(R.string.aceptarP));

        TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
        final String mensaje=getString(R.string.aceptarPor)+msn+getString(R.string.aceptarP2);
        contenido.setText(mensaje);
        ((Button) customDialog.findViewById(R.id.cancelar)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                customDialog.dismiss();
                finish();

            }
        });
        ((Button)customDialog.findViewById(R.id.aceptar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Notifi not = Memoria.notifiList.get(posi);
                            HttpURLConnection conn = (HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                            conn.setRequestMethod("POST");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);
                            PrintWriter wr = new PrintWriter(
                                    conn.getOutputStream());
                            String param = "funcion=f15&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension + "&idNotifi=" + not.getIdNot() + "&numBoton=4";
                            wr.print(param);
                            wr.close();
                            conn.connect();
                            String ress="";
                            Scanner Strea = new Scanner(conn.getInputStream());
                            while(Strea.hasNextLine())
                                ress+=(Strea.nextLine());

                            j=new JSONObject(ress);
                            final String titulo=j.getString("titulo");
                            final String men=j.getString("mensaje");
                            if(j.getString("estado").equals("E")){
                                NotifiPantalla.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        customDialog.dismiss();
                                        mostrar2(v,titulo, men);
                                    }
                                });

                            }

                            conn.disconnect();

                            System.out.println(ress);

                            // ¿ que hacemos con al respuesta???? me cago en dios vamso a ver que es y hacemos cosas

                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        customDialog.show();
    }
    public void mostrar2(View view, String text, String msn)
    {
        // con este tema personalizado evitamos los bordes por defecto
        customDialog = new Dialog(this,R.style.Theme_Dialog_Translucent);
        //deshabilitamos el título por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.alert_conexion);


        TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
        titulo.setText(text);

        TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
        contenido.setText(msn);

        ((Button) customDialog.findViewById(R.id.button9)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                customDialog.dismiss();
            }
        });

        customDialog.show();
    }
}
