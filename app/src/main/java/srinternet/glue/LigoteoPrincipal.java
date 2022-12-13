package srinternet.glue;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import static java.security.AccessController.getContext;

public class LigoteoPrincipal extends AppCompatActivity {
    Dialog customDialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ligoteo_principal);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button no,si;
        final int posicionamigos;
        final int posperfil;
        TextView tx;
        ImageView fotop,fotoli;
        String celes, follable;

        fotop=(ImageView)findViewById(R.id.imageView27);
        fotoli=(ImageView)findViewById(R.id.imageView40);
        posicionamigos = getIntent().getExtras().getInt("acomun");
        posperfil = getIntent().getExtras().getInt("perfil");
        no = (Button)findViewById(R.id.btn_ligoteo_no);
        si = (Button) findViewById(R.id.btn_ligoteo_si);
        fotop.setImageBitmap(Memoria.peoples.get(posperfil).getPerfilFot());
        fotoli.setImageDrawable(redondear(redimensionarImagenMaximo(Memoria.princiList.get(posicionamigos).getImageData(),200,200)));
        tx = (TextView)findViewById(R.id.textView52);
        celes = Memoria.princiList.get(posicionamigos).getNombre();
        follable = Memoria.peoples.get(posperfil).getNombre();
        tx.setText(getString(R.string.QuieresAmigo)+celes+getString(R.string.presente)+follable+"?");

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                System.out.println(Memoria.peoples.get(posperfil).getNombre()+" "+Memoria.peoples.get(posperfil).getIdFace());
                System.out.println(Memoria.princiList.get(posicionamigos).getNombre()+" "+Memoria.princiList.get(posicionamigos).getSesion());

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("LOLA");
                            HttpURLConnection conn = (HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                            conn.setRequestMethod("POST");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);
                            PrintWriter wr = new PrintWriter(
                                    conn.getOutputStream());
                            String response = "";
                            String param = "funcion=f14&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension + "&idFace02=" + Memoria.princiList.get(posicionamigos).getSesion() + "&idFace03=" + Memoria.peoples.get(posperfil).getIdFace();
                            wr.print(param);
                            wr.close();
                            conn.connect();
                            Scanner inStream = new Scanner(conn.getInputStream());
                            while (inStream.hasNextLine())
                                response += (inStream.nextLine());
                            conn.disconnect();
                            final JSONObject u = new JSONObject(response);
                            System.out.println(response);
                            if (u.getString("estado").compareTo("E") == 0) {
                                LigoteoPrincipal.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mostrar(v,u);
                                    }
                                });

                            }else{
                                finish();
                            }
                        } catch (Exception e) {
                            System.out.println(e.toString());
                        }

                    }

                });
            }
        });
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
    public RoundedBitmapDrawable redondear(Bitmap originalBitmap){

        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);
        roundedDrawable.setCornerRadius(originalBitmap.getHeight());
        return roundedDrawable;
    }

}
