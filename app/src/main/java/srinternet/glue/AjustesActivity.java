package srinternet.glue;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class AjustesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ImageView atras = (ImageView)findViewById(R.id.img_btn_atras_ajustes);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        TextView ajustes=(TextView)findViewById(R.id.textView26);
        ajustes.setText(Idioma.MAj_T01);
        TextView sonido=(TextView)findViewById(R.id.tx_ajustes_sonido);
        sonido.setText(Idioma.MAj_T02);

        TextView notificacioens=(TextView)findViewById(R.id.textView43);
        notificacioens.setText(Idioma.MAj_T04);

        TextView vibracion=(TextView)findViewById(R.id.textView44);
        vibracion.setText(Idioma.MAj_T03);

        TextView texto=(TextView)findViewById(R.id.textView45);
        texto.setText(Idioma.MAj_T05);


        Switch switchS=(Switch)findViewById(R.id.switch1);
        switchS.setChecked(Memoria.sonidos==1);
        switchS.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0)
            {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String response="";
                            HttpURLConnection conn = (HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                            conn.setRequestMethod("POST");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);
                            PrintWriter wr = new PrintWriter(
                                    conn.getOutputStream());
                            String params="funcion=f21&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension + "&ajustes=" + 1;
                            wr.print(params);

                            wr.close();
                            conn.connect();
                            Scanner inStream = new Scanner(conn.getInputStream());
                            while(inStream.hasNextLine())
                                response+=(inStream.nextLine());

                            conn.disconnect();
                            JSONObject u = new JSONObject(response);
                            Memoria.setAjustes(u);

                        }catch(Exception e){
                            System.out.println(e.toString());
                        }
                    }
                });

            }
        });

        Switch switchV=(Switch)findViewById(R.id.switch2);
        switchV.setChecked(Memoria.vibracion==1);
        switchV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0)
            {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String response="";
                            HttpURLConnection conn = (HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                            conn.setRequestMethod("POST");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);
                            PrintWriter wr = new PrintWriter(
                                    conn.getOutputStream());
                            String params="funcion=f21&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension + "&ajustes=" + 2;
                            wr.print(params);

                            wr.close();
                            conn.connect();
                            Scanner inStream = new Scanner(conn.getInputStream());
                            while(inStream.hasNextLine())
                                response+=(inStream.nextLine());

                            conn.disconnect();
                            JSONObject u = new JSONObject(response);
                            Memoria.setAjustes(u);

                        }catch(Exception e){
                            System.out.println(e.toString());
                        }
                    }
                });

            }
        });

        Switch switchN=(Switch)findViewById(R.id.switch3);
        switchN.setChecked(Memoria.notificaciones==1);
        switchN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0)
            {

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String response="";
                            HttpURLConnection conn = (HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                            conn.setRequestMethod("POST");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);
                            PrintWriter wr = new PrintWriter(
                                    conn.getOutputStream());
                            String params="funcion=f21&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension + "&ajustes=" + 3;
                            wr.print(params);

                            wr.close();
                            conn.connect();
                            Scanner inStream = new Scanner(conn.getInputStream());
                            while(inStream.hasNextLine())
                                response+=(inStream.nextLine());

                            conn.disconnect();
                            JSONObject u = new JSONObject(response);
                            Memoria.setAjustes(u);

                        }catch(Exception e){
                            System.out.println(e.toString());
                        }
                    }
                });
            }
        });
    }
}
