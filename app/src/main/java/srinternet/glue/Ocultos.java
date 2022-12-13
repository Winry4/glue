package srinternet.glue;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class Ocultos extends AppCompatActivity {
    RelativeLayout screenLayout;
    OcultoAdapter listAdapter;
    GridView listView;
    Dialog customDialog = null;
    ImageView atras,can;
    JSONObject j;
    String titulo, mensaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocultos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        screenLayout = (RelativeLayout)findViewById(R.id.ventana);
        listAdapter = new OcultoAdapter(getBaseContext(),Memoria.ocultosList, this);
        TextView oc=(TextView)findViewById(R.id.textView26);
        oc.setText(Idioma.MOc_T01);
        listView = (GridView) findViewById(R.id.listaOcultos);
        listView.setAdapter(listAdapter);
        atras = (ImageView)findViewById(R.id.img_btn_atras_ocultos);
        listAdapter.notifyDataSetChanged();
        final boolean [] irvolver= new boolean[Memoria.ocultosList.size()];

        for(int o=0;o<irvolver.length;o++){
            irvolver[o]=true;
        }

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                ImageView eliminar=(ImageView) view.findViewById(R.id.eliminar2);
                if( irvolver[position]){
                    Animation slide = AnimationUtils.loadAnimation(getBaseContext(), R.anim.translate5);
                    RelativeLayout l1= (RelativeLayout) view.findViewById(R.id.Cuad);
                    l1.startAnimation(slide);
                    eliminar.setVisibility(View.VISIBLE);
                    irvolver[position]=false;
                }else{
                    Animation slide = AnimationUtils.loadAnimation(getBaseContext(), R.anim.translate6);
                    RelativeLayout l1= (RelativeLayout) view.findViewById(R.id.Cuad);
                    l1.startAnimation(slide);
                    eliminar.setVisibility(View.INVISIBLE);
                    irvolver[position]=true;
                }

                eliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    //Notifi not = Memoria.notifiList.get(position);
                                    HttpURLConnection conn = (HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                                    conn.setRequestMethod("POST");
                                    conn.setDoOutput(true);
                                    conn.setDoInput(true);
                                    PrintWriter wr = new PrintWriter(
                                            conn.getOutputStream());
                                    String param = "funcion=f30&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension + "&tipoProd=p3";
                                    wr.print(param);
                                    System.out.println("entra aaaa");
                                    wr.close();
                                    conn.connect();
                                    String ress="";
                                    Scanner Strea = new Scanner(conn.getInputStream());
                                    while(Strea.hasNextLine())
                                        ress+=(Strea.nextLine());

                                    j=new JSONObject(ress);
                                    conn.disconnect();
                                    mensaje=j.getString("precio");
                                    System.out.println("estoooooo "+j);
                                    Ocultos.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mostrar1(v,Memoria.ocultosList.get(position).getNombre(), position, mensaje);
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
        });

    }
    public void mostrar1(View view, String nombre, final int position, String precio)
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
        titulo.setText("QUITAR DE OCULTOS");

        TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
        String texto="¿Deseas volver a ver "+nombre+" por "+precio+" Coins?";
        contenido.setText(texto);
        Button cancelar=(Button)customDialog.findViewById(R.id.cancelar);
        cancelar.setText("NO");
        Button aceptar=(Button)customDialog.findViewById(R.id.aceptar);
        aceptar.setText("SI");

        cancelar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                customDialog.dismiss();

            }
        });
        aceptar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view)
            {
                customDialog.dismiss();
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {

                        try{
                            HttpURLConnection conn =(HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                            conn.setRequestMethod("POST");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);
                            PrintWriter wr = new PrintWriter (
                                    conn.getOutputStream ());
                            String response="";
                            String param="funcion=f28&idFacebo="+Memoria.UserFBId+"&idSesion="+Memoria.idSension+"&idFbPers="+ Memoria.ocultosList.get(position).getIdFb();;
                            wr.print(param);
                            wr.close();
                            conn.connect();
                            Scanner inStream = new Scanner(conn.getInputStream());
                            while(inStream.hasNextLine())
                                response+=(inStream.nextLine());
                            conn.disconnect();
                            JSONObject u = new JSONObject(response);
                            if (u.getString("estado").compareTo("E")==0){
                                Ocultos.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mostrar(view, "SIN COINS", "No tienes coins suficientes para esta accion");
                                    }
                                });

                            }else{
                                Memoria.ocultosList.clear();
                                for (int i=0;i<u.getJSONArray("ocultos").length();i++){
                                    String fecha=u.getJSONArray("ocultos").getJSONObject(i).getString("FECHAREG");
                                    String mensaje=u.getJSONArray("ocultos").getJSONObject(i).getString("CIUDADFB");
                                    String nombre=u.getJSONArray("ocultos").getJSONObject(i).getString("PRINOMFB");
                                    String id= u.getJSONArray("ocultos").getJSONObject(i).getString("FOTOGRAF");
                                    String idfb=u.getJSONArray("ocultos").getJSONObject(i).getString("IDFACEBO");
                                    String edad= u.getJSONArray("ocultos").getJSONObject(i).getString("EDADUSER");//CAMBIAR ESTO POR A PORK SON UNOS PATANES
                                    OcultosClase bg = new OcultosClase(nombre,fecha, mensaje,idfb, id, edad);
                                    Memoria.ocultosList.add(bg);
                                }
                                listAdapter.notifyDataSetChanged();
                            }

                        }catch(Exception e){
                            System.out.println(e.toString());
                        }

                    }

                });

            }
        });


        customDialog.show();
    }
    public void mostrar(View view, String text, String msn)
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
