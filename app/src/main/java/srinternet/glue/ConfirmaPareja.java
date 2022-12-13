package srinternet.glue;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ConfirmaPareja extends AppCompatActivity {
    Dialog customDialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirma_pareja);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ImageView ima1=(ImageView)findViewById(R.id.imageView49);
        ImageView ima2=(ImageView)findViewById(R.id.imageView47);
        TextView quieres=(TextView)findViewById(R.id.textView13);
        String []q=Idioma.Pet_T02.split("%");
        quieres.setText(q[0]);
        Button aceptar=(Button) findViewById(R.id.button6);
        aceptar.setText(Idioma.Pet_B01);
        Button no=(Button)findViewById(R.id.button7);
        no.setText(Idioma.Pet_B02);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
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
                            String param="funcion=f20&idFacebo="+Memoria.UserFBId+"&idSesion="+Memoria.idSension+"&idFace01="+Memoria.pareja1.getIdentificador()+"&idFace03="+Memoria.pareja2.getIdentificador();
                            wr.print(param);
                            wr.close();
                            conn.connect();
                            Scanner inStream = new Scanner(conn.getInputStream());
                            while(inStream.hasNextLine())
                                response+=(inStream.nextLine());
                            conn.disconnect();
                            final JSONObject u = new JSONObject(response);
                            System.out.println(response);
                            if (u.getString("estado").compareTo("E")==0){
                                ConfirmaPareja.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mostrar(v,u);
                                    }
                                });

                            }else{
                                finish();
                            }

                        }catch(Exception e){
                            System.out.println(e.toString());
                        }

                    }

                });

            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ima1.setImageBitmap( redimensionarImagenMaximo(Memoria.pareja1.getImageDataOri(), 350, 350) );
        ima2.setImageBitmap(redimensionarImagenMaximo(Memoria.pareja2.getImageDataOri(), 350, 350));
        TextView t=(TextView)findViewById(R.id.textView47);
        String []qu=Idioma.Pet_T02.split("%");
        String texto2=Memoria.pareja1.getNombre()+ " "+ q[1]+" "+Memoria.pareja2.getNombre()+"?";
        t.setText(texto2);
    }
    public void mostrar(View view, JSONObject u)
    {
        // con este tema personalizado evitamos los bordes por defecto
        customDialog = new Dialog(this,R.style.Theme_Dialog_Translucent);
        //deshabilitamos el título por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.alert_conexion);

        try {
            TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
            titulo.setText(u.getString("titulo"));

            TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
            contenido.setText(u.getString("mensaje"));
        }catch (Exception e){
            e.printStackTrace();
        }
        ((Button) customDialog.findViewById(R.id.button9)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                customDialog.dismiss();
                finish();
            }
        });



        customDialog.show();
    }
    public Bitmap redimensionarImagenMaximo(Bitmap mBitmap, float newWidth, float newHeigth){
        //Redimensionamos
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeigth) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
    }
}
