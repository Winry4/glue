package srinternet.glue;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;

import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.util.Locale;
import java.net.URL;
import java.util.Scanner;

public class SettingsActivity extends AppCompatActivity {
    private Locale locale;
    Dialog customDialog = null;
    private Configuration config = new Configuration();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ImageView im = (ImageView) findViewById(R.id.imageView53);
        TextView uno=(TextView)findViewById(R.id.textView14);
        TextView dos=(TextView)findViewById(R.id.textView15);
        TextView tre=(TextView)findViewById(R.id.textView16);
        TextView cua=(TextView)findViewById(R.id.textView17);
        TextView cin=(TextView)findViewById(R.id.textView20);
        TextView six=(TextView)findViewById(R.id.textView18);
        TextView sept=(TextView)findViewById(R.id.textView19);
        TextView huit=(TextView)findViewById(R.id.textView40);
        TextView nine=(TextView)findViewById(R.id.textView41);
        TextView dies=(TextView)findViewById(R.id.tx_cerrarSesion);


        uno.setText(Idioma.Men_B01);
        dos.setText(Idioma.Men_B02);
        tre.setText(Idioma.Men_B03);
        cua.setText(Idioma.Men_B04);
        cin.setText(Idioma.Men_B05);
        six.setText(Idioma.Men_B06);
        sept.setText(Idioma.Men_B07);
        huit.setText(Idioma.Men_B08);
        nine.setText(Idioma.Men_B09);
        dies.setText(Idioma.Men_B10);

        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });




        ImageButton perfil=(ImageButton)findViewById(R.id.img_btn_perfil_settings);
        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{

                            HttpURLConnection conn = (HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                            conn.setRequestMethod("POST");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);
                            PrintWriter wr = new PrintWriter(
                                    conn.getOutputStream());
                            String param = "funcion=f37&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension;
                            wr.print(param);
                            wr.close();
                            conn.connect();
                            String ress="";
                            Scanner Strea = new Scanner(conn.getInputStream());
                            while(Strea.hasNextLine())
                                ress+=(Strea.nextLine());

                            conn.disconnect();
                            JSONObject j= new JSONObject(ress);
                            System.out.println("json: "+j);
                            Memoria.historyList.clear();
                            for (int i=0; i<j.getJSONArray("historia").length();i++){
                                String pre=j.getJSONArray("historia").getJSONObject(i).getString("PREGUNTA");
                                String cod=j.getJSONArray("historia").getJSONObject(i).getString("CODMENSA");
                                String re=j.getJSONArray("historia").getJSONObject(i).getString("RESPUEST");
                                String est=j.getJSONArray("historia").getJSONObject(i).getString("ESTADOPR");
                                History h=new History(pre, cod, re, est);
                                Memoria.historyList.add(h);
                            }


                        }catch(Exception e){
                            e.printStackTrace();

                        }
                        Intent intent=new Intent(getBaseContext(), PerfilActivity.class);
                        startActivity(intent);
                    }


                });

            }
        });


        ImageButton notificaciones = (ImageButton)findViewById(R.id.img_btn_notificaciones_settings);
        notificaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Memoria.notifiList.clear();
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{

                            HttpURLConnection conn = (HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                            conn.setRequestMethod("POST");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);
                            PrintWriter wr = new PrintWriter(
                                    conn.getOutputStream());
                            String param = "funcion=f13&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension;
                            wr.print(param);
                            wr.close();
                            conn.connect();
                            String ress="";
                            Scanner Strea = new Scanner(conn.getInputStream());
                            while(Strea.hasNextLine())
                                ress+=(Strea.nextLine());

                            conn.disconnect();
                            JSONObject j= new JSONObject(ress);
                            System.out.println("jsoncitoos: "+j);

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
                                System.out.println("nuevaaa");
                            }

                            HttpURLConnection con = (HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                            con.setRequestMethod("POST");
                            con.setDoOutput(true);
                            con.setDoInput(true);
                            PrintWriter w = new PrintWriter(
                                    con.getOutputStream());
                            String para = "funcion=f22&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension;
                            w.print(para);
                            w.close();
                            con.connect();
                            String res="";
                            Scanner Stream = new Scanner(con.getInputStream());
                            while(Stream.hasNextLine())
                                res+=(Stream.nextLine());

                            con.disconnect();
                            JSONObject y= new JSONObject(ress);

                            System.out.println(y);

                        }catch(Exception e){
                            System.out.println(e.toString());

                        }

                        Intent intent = new Intent(getBaseContext(),NotificacionesActivity.class);
                        startActivity(intent);
                    }


                });


            }
        });
        ImageButton tutorial = (ImageButton)findViewById(R.id.img_btn_tutorial_settings);
        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),TutorialActivity.class);
                startActivity(intent);
            }
        });
        ImageButton ocultos=(ImageButton)findViewById(R.id.img_btn_usuOcultos_settings) ;
        ocultos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Memoria.ocultosList.clear();
                            HttpURLConnection conn = (HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                            conn.setRequestMethod("POST");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);
                            PrintWriter wr = new PrintWriter(
                                    conn.getOutputStream());
                            String param = "funcion=f36&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension;
                            wr.print(param);
                            wr.close();
                            conn.connect();
                            String ress="";
                            Scanner Strea = new Scanner(conn.getInputStream());
                            while(Strea.hasNextLine())
                                ress+=(Strea.nextLine());

                            conn.disconnect();
                            JSONObject j= new JSONObject(ress);
                            System.out.println("json de ocultos "+j);
                            for (int i=0;i<j.getJSONArray("ocultos").length();i++){
                                String fecha=j.getJSONArray("ocultos").getJSONObject(i).getString("FECHAREG");
                                String mensaje=j.getJSONArray("ocultos").getJSONObject(i).getString("CIUDADFB");
                                String nombre=j.getJSONArray("ocultos").getJSONObject(i).getString("PRINOMFB");
                                String id= j.getJSONArray("ocultos").getJSONObject(i).getString("FOTOGRAF");
                                String idfb=j.getJSONArray("ocultos").getJSONObject(i).getString("IDFACEBO");
                                String edad= j.getJSONArray("ocultos").getJSONObject(i).getString("EDADUSER");//CAMBIAR ESTO POR A PORK SON UNOS PATANES

                                OcultosClase bg = new OcultosClase(nombre,fecha, mensaje,idfb, id, edad);
                                Memoria.ocultosList.add(bg);
                            }



                        }catch(Exception e){
                            System.out.println(e.toString());

                        }
                        Intent intent=new Intent(getBaseContext(), Ocultos.class);
                        startActivity(intent);
                    }
                });

            }
        });
        ImageButton preguntasfrecuentes = (ImageButton)findViewById(R.id.img_btn_preguntasfrecuentes_settings);
        preguntasfrecuentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),PreguntasFrecuentesActivity.class);
                startActivity(intent);
            }
        });

        ImageButton compras = (ImageButton)findViewById(R.id.img_btn_compras_settings);
        compras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),ComprasActivity.class);
                startActivity(intent);
            }
        });
        ImageButton ajustes = (ImageButton)findViewById(R.id.img_btn_ajustes_settings);
        ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),AjustesActivity.class);
                startActivity(intent);
            }
        });
        ImageButton bases=(ImageButton)findViewById(R.id.img_btn_basesLegales_settings);
        bases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), BasesLegales.class);
                startActivity(intent);
            }
        });
        ImageButton compartr = (ImageButton) findViewById(R.id.img_btn_comAplicacion_settings);
        compartr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getBaseContext(), CompartirApp.class);
                startActivity(intent);
            }
        });



    }
    private void showDialog(){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(getResources().getString(R.string.idioma));
        //obtiene los idiomas del array de string.xml
        String[] types = getResources().getStringArray(R.array.languages);
        b.setItems(types, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                switch(which){
                    case 0:
                        locale = new Locale("en");
                        config.locale =locale;
                        break;
                    case 1:
                        locale = new Locale("es");
                        config.locale =locale;
                        break;

                }
                getResources().updateConfiguration(config, null);
                Intent refresh = new Intent(SettingsActivity.this, SettingsActivity.class);
                startActivity(refresh);
                finish();
            }

        });

        b.show();
    }
    public void mostrar(View view)
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
        titulo.setText("CERRAR SESIÓN");

        TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
        contenido.setText("¿Estás seguro de que deseas cerrar la sesión?");

        ((Button) customDialog.findViewById(R.id.aceptar)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                customDialog.dismiss();
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            HttpURLConnection conn = (HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                            conn.setRequestMethod("POST");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);
                            PrintWriter wr = new PrintWriter(
                                    conn.getOutputStream());
                            String params="funcion=f03&idFacebo="+Memoria.UserFBId+"&idSesion="+Memoria.idSension;
                            wr.print(params);
                            wr.close();
                            conn.connect();
                            AccessToken.setCurrentAccessToken(null);
                            Intent intent = new Intent(SettingsActivity.this, InicioActivity.class);
                            startActivity(intent);
                            finish();

                        }catch(Exception e){
                            System.out.println(e.toString());
                        }
                    }
                });

            }
        });

        ((Button) customDialog.findViewById(R.id.cancelar)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                customDialog.dismiss();

            }
        });

        customDialog.show();
    }
}