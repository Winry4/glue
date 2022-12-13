package srinternet.glue;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.TimeZone;

import android.os.AsyncTask;

import android.support.v4.app.ActivityCompat;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Location;
import android.os.Build;
import android.Manifest;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.ViewFlipper;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends Activity {
    private final int DURACION_SPLASH = 3000;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;


    CallbackManager callbackManager;
    Button loginfacebook;
    Button enamorado;

    private FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;

    String response="null",params="null", nombreFb="null", PrApFB="null", ScApFb="null", gender="null", FechaNac="null", Ciudad="null", Emilio="null", GentedeZw="null", funcion="null", tokenfb="null", idFB="null", tokenpush="null", plat="null", mod="null", lan="null", posGeo="null", timeZone="null";
    String fotoPerfURL="null";
    Dialog customDialog=null;
    VideoView video;

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void idioma(){
        try{
            String idioma="es_ES";
            if(Locale.getDefault().getDisplayLanguage().equals("English")){
                idioma="en_US";
            }else if(Locale.getDefault().getDisplayLanguage().equals("español")){
                idioma="es_ES";
            }
            final String idio=idioma;


            HttpURLConnection conn = (HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            PrintWriter wr = new PrintWriter(
                    conn.getOutputStream());
            String param = "funcion=f31&language="+ idio; ;
            wr.print(param);
            wr.close();
            conn.connect();
            String ress="";
            Scanner Strea = new Scanner(conn.getInputStream());
            while(Strea.hasNextLine())
                ress+=(Strea.nextLine());

            conn.disconnect();
            JSONObject j= new JSONObject(ress);
            Idioma.AVi_B01=j.getJSONObject("textos").getString("AVi_B01");
            Idioma.AVi_B02=j.getJSONObject("textos").getString("AVi_B02");
            Idioma.AVi_B03=j.getJSONObject("textos").getString("AVi_B03");
            Idioma.AVi_B04=j.getJSONObject("textos").getString("AVi_B04");
            Idioma.AVi_B05=j.getJSONObject("textos").getString("AVi_B05");
            Idioma.AVi_B06=j.getJSONObject("textos").getString("AVi_B06");
            Idioma.AVi_B07=j.getJSONObject("textos").getString("AVi_B07");
            Idioma.AVi_M01=j.getJSONObject("textos").getString("AVi_M01");
            Idioma.AVi_M02=j.getJSONObject("textos").getString("AVi_M02");
            Idioma.AVi_M03=j.getJSONObject("textos").getString("AVi_M03");
            Idioma.AVi_M04=j.getJSONObject("textos").getString("AVi_M04");
            Idioma.AVi_M05=j.getJSONObject("textos").getString("AVi_M05");
            Idioma.AVi_M06=j.getJSONObject("textos").getString("AVi_M06");
            Idioma.AVi_M07=j.getJSONObject("textos").getString("AVi_M07");
            Idioma.AVi_M08=j.getJSONObject("textos").getString("AVi_M08");
            Idioma.AVi_M09=j.getJSONObject("textos").getString("AVi_M09");
            Idioma.AVi_M10=j.getJSONObject("textos").getString("AVi_M10");
            Idioma.AVi_M11=j.getJSONObject("textos").getString("AVi_M11");
            Idioma.AVi_M12=j.getJSONObject("textos").getString("AVi_M12");
            Idioma.AVi_M13=j.getJSONObject("textos").getString("AVi_M13");
            Idioma.AVi_M14=j.getJSONObject("textos").getString("AVi_M14");
            Idioma.AVi_M15=j.getJSONObject("textos").getString("AVi_M15");
            Idioma.AVi_M16=j.getJSONObject("textos").getString("AVi_M16");
            Idioma.AVi_M17=j.getJSONObject("textos").getString("AVi_M17");
            Idioma.AVi_M18=j.getJSONObject("textos").getString("AVi_M18");
            Idioma.AVi_M19=j.getJSONObject("textos").getString("AVi_M19");
            Idioma.AVi_T01=j.getJSONObject("textos").getString("AVi_T01");
            Idioma.AVi_T02=j.getJSONObject("textos").getString("AVi_T02");
            Idioma.AVi_T03=j.getJSONObject("textos").getString("AVi_T03");
            Idioma.AVi_T04=j.getJSONObject("textos").getString("AVi_T04");
            Idioma.AVi_T05=j.getJSONObject("textos").getString("AVi_T05");
            Idioma.AVi_T06=j.getJSONObject("textos").getString("AVi_T06");
            Idioma.AVi_T07=j.getJSONObject("textos").getString("AVi_T07");
            Idioma.AVi_T08=j.getJSONObject("textos").getString("AVi_T08");
            Idioma.AVi_T09=j.getJSONObject("textos").getString("AVi_T09");
            Idioma.AVi_T10=j.getJSONObject("textos").getString("AVi_T10");
            Idioma.AVi_T11=j.getJSONObject("textos").getString("AVi_T11");
            Idioma.AVi_T12=j.getJSONObject("textos").getString("AVi_T12");
            Idioma.AVi_T13=j.getJSONObject("textos").getString("AVi_T13");
            Idioma.AVi_T14=j.getJSONObject("textos").getString("AVi_T14");
            Idioma.AVi_T15=j.getJSONObject("textos").getString("AVi_T15");
            Idioma.AVi_T16=j.getJSONObject("textos").getString("AVi_T16");
            Idioma.AVi_T17=j.getJSONObject("textos").getString("AVi_T17");
            Idioma.AVi_T18=j.getJSONObject("textos").getString("AVi_T18");
            Idioma.Cha_B01=j.getJSONObject("textos").getString("Cha_B01");
            Idioma.Cha_B02=j.getJSONObject("textos").getString("Cha_B02");
            Idioma.Cha_B03=j.getJSONObject("textos").getString("Cha_B03");
            Idioma.Cha_B04=j.getJSONObject("textos").getString("Cha_B04");
            Idioma.Cha_B05=j.getJSONObject("textos").getString("Cha_B05");
            Idioma.Cha_B06=j.getJSONObject("textos").getString("Cha_B06");
            Idioma.Cha_T01=j.getJSONObject("textos").getString("Cha_T01");
            Idioma.Cha_T02=j.getJSONObject("textos").getString("Cha_T02");
            Idioma.CMe_B01=j.getJSONObject("textos").getString("CMe_B01");
            Idioma.Glu_B01=j.getJSONObject("textos").getString("Glu_B01");
            Idioma.Glu_T01=j.getJSONObject("textos").getString("Glu_T01");
            Idioma.Glu_T02=j.getJSONObject("textos").getString("Glu_T02");
            Idioma.Glu_T03=j.getJSONObject("textos").getString("Glu_T03");
            Idioma.Glu_T04=j.getJSONObject("textos").getString("Glu_T04");
            Idioma.Glu_T05=j.getJSONObject("textos").getString("Glu_T05");
            Idioma.Glu_T06=j.getJSONObject("textos").getString("Glu_T06");
            Idioma.Glu_T07=j.getJSONObject("textos").getString("Glu_T07");
            Idioma.Glu_T08=j.getJSONObject("textos").getString("Glu_T08");
            Idioma.Lau_T01=j.getJSONObject("textos").getString("Lau_T01");
            Idioma.Log_B01=j.getJSONObject("textos").getString("Log_B01");
            Idioma.Log_B02=j.getJSONObject("textos").getString("Log_B02");
            Idioma.Log_L01=j.getJSONObject("textos").getString("Log_L01");
            Idioma.Log_T01=j.getJSONObject("textos").getString("Log_T01");
            Idioma.LSA_M01=j.getJSONObject("textos").getString("LSA_M01");
            Idioma.LSA_T01=j.getJSONObject("textos").getString("LSA_T01");
            Idioma.LSB_M01=j.getJSONObject("textos").getString("LSB_M01");
            Idioma.LSB_T01=j.getJSONObject("textos").getString("LSB_T01");
            Idioma.LSC_M01=j.getJSONObject("textos").getString("LSC_M01");
            Idioma.LSC_T01=j.getJSONObject("textos").getString("LSC_T01");
            Idioma.MAj_B01=j.getJSONObject("textos").getString("MAj_B01");
            Idioma.MAj_T01=j.getJSONObject("textos").getString("MAj_T01");
            Idioma.MAj_T02=j.getJSONObject("textos").getString("MAj_T02");
            Idioma.MAj_T03=j.getJSONObject("textos").getString("MAj_T03");
            Idioma.MAj_T04=j.getJSONObject("textos").getString("MAj_T04");
            Idioma.MAj_T05=j.getJSONObject("textos").getString("MAj_T05");
            Idioma.MBa_T01=j.getJSONObject("textos").getString("MBa_T01");
            Idioma.MCA_B01=j.getJSONObject("textos").getString("MCA_B01");
            Idioma.MCA_M01=j.getJSONObject("textos").getString("MCA_M01");
            Idioma.MCA_T01=j.getJSONObject("textos").getString("MCA_T01");
            Idioma.MCB_B01=j.getJSONObject("textos").getString("MCB_B01");
            Idioma.MCB_M01=j.getJSONObject("textos").getString("MCB_M01");
            Idioma.MCB_T01=j.getJSONObject("textos").getString("MCB_T01");
            Idioma.MCC_B01=j.getJSONObject("textos").getString("MCC_B01");
            Idioma.MCC_M01=j.getJSONObject("textos").getString("MCC_M01");
            Idioma.MCC_T01=j.getJSONObject("textos").getString("MCC_T01");
            Idioma.MCF_B01=j.getJSONObject("textos").getString("MCF_B01");
            Idioma.MCF_B02=j.getJSONObject("textos").getString("MCF_B02");
            Idioma.MCF_B03=j.getJSONObject("textos").getString("MCF_B03");
            Idioma.MCF_T01=j.getJSONObject("textos").getString("MCF_T01");
            Idioma.MCo_B01=j.getJSONObject("textos").getString("MCo_B01");
            Idioma.MCo_T01=j.getJSONObject("textos").getString("MCo_T01");
            Idioma.MCo_T02=j.getJSONObject("textos").getString("MCo_T02");
            Idioma.MCo_T03=j.getJSONObject("textos").getString("MCo_T03");
            Idioma.MCo_T04=j.getJSONObject("textos").getString("MCo_T04");
            Idioma.MCo_T05=j.getJSONObject("textos").getString("MCo_T05");
            Idioma.MCo_T06=j.getJSONObject("textos").getString("MCo_T06");
            Idioma.MCo_T07=j.getJSONObject("textos").getString("MCo_T07");
            Idioma.MCo_T08=j.getJSONObject("textos").getString("MCo_T08");
            Idioma.MCo_T09=j.getJSONObject("textos").getString("MCo_T09");
            Idioma.Men_B01=j.getJSONObject("textos").getString("Men_B01");
            Idioma.Men_B02=j.getJSONObject("textos").getString("Men_B02");
            Idioma.Men_B03=j.getJSONObject("textos").getString("Men_B03");
            Idioma.Men_B04=j.getJSONObject("textos").getString("Men_B04");
            Idioma.Men_B05=j.getJSONObject("textos").getString("Men_B05");
            Idioma.Men_B06=j.getJSONObject("textos").getString("Men_B06");
            Idioma.Men_B07=j.getJSONObject("textos").getString("Men_B07");
            Idioma.Men_B08=j.getJSONObject("textos").getString("Men_B08");
            Idioma.Men_B09=j.getJSONObject("textos").getString("Men_B09");
            Idioma.Men_B10=j.getJSONObject("textos").getString("Men_B10");
            Idioma.MFF_T01=j.getJSONObject("textos").getString("MFF_T01");
            Idioma.MFF_T02=j.getJSONObject("textos").getString("MFF_T02");
            Idioma.MFF_T03=j.getJSONObject("textos").getString("MFF_T03");
            Idioma.MNo_T01=j.getJSONObject("textos").getString("MNo_T01");
            Idioma.MNo_T02=j.getJSONObject("textos").getString("MNo_T02");
            Idioma.MOc_T01=j.getJSONObject("textos").getString("MOc_T01");
            Idioma.MPe_B01=j.getJSONObject("textos").getString("MPe_B01");
            Idioma.MPe_B02=j.getJSONObject("textos").getString("MPe_B02");
            Idioma.MPe_B03=j.getJSONObject("textos").getString("MPe_B03");
            Idioma.MPe_B04=j.getJSONObject("textos").getString("MPe_B04");
            Idioma.MPe_B05=j.getJSONObject("textos").getString("MPe_B05");
            Idioma.MPe_B06=j.getJSONObject("textos").getString("MPe_B06");
            Idioma.MPe_B07=j.getJSONObject("textos").getString("MPe_B07");
            Idioma.MPe_B08=j.getJSONObject("textos").getString("MPe_B08");
            Idioma.MPe_B09=j.getJSONObject("textos").getString("MPe_B09");
            Idioma.MPe_B10=j.getJSONObject("textos").getString("MPe_B10");
            Idioma.MPe_T01=j.getJSONObject("textos").getString("MPe_T01");
            Idioma.MPe_T02=j.getJSONObject("textos").getString("MPe_T02");
            Idioma.MPe_T03=j.getJSONObject("textos").getString("MPe_T03");
            Idioma.MPe_T04=j.getJSONObject("textos").getString("MPe_T04");
            Idioma.MPe_T05=j.getJSONObject("textos").getString("MPe_T05");
            Idioma.MPe_T06=j.getJSONObject("textos").getString("MPe_T06");
            Idioma.MPe_T07=j.getJSONObject("textos").getString("MPe_T07");
            Idioma.MPe_T08=j.getJSONObject("textos").getString("MPe_T08");
            Idioma.MPe_T09=j.getJSONObject("textos").getString("MPe_T09");
            Idioma.MPe_T10=j.getJSONObject("textos").getString("MPe_T10");
            Idioma.MPe_T11=j.getJSONObject("textos").getString("MPe_T11");
            Idioma.MPr_L01=j.getJSONObject("textos").getString("MPr_L01");
            Idioma.MPr_T01=j.getJSONObject("textos").getString("MPr_T01");
            Idioma.MTu_T01=j.getJSONObject("textos").getString("MTu_T01");
            Idioma.Not_B01=j.getJSONObject("textos").getString("Not_B01");
            Idioma.Not_B02=j.getJSONObject("textos").getString("Not_B02");
            Idioma.Not_B03=j.getJSONObject("textos").getString("Not_B03");
            Idioma.Not_B04=j.getJSONObject("textos").getString("Not_B04");
            Idioma.Not_B05=j.getJSONObject("textos").getString("Not_B05");
            Idioma.Not_B06=j.getJSONObject("textos").getString("Not_B06");
            Idioma.Not_B07=j.getJSONObject("textos").getString("Not_B07");
            Idioma.Pet_B01=j.getJSONObject("textos").getString("Pet_B01");
            Idioma.Pet_B02=j.getJSONObject("textos").getString("Pet_B02");
            Idioma.Pet_T01=j.getJSONObject("textos").getString("Pet_T01");
            Idioma.Pet_T02=j.getJSONObject("textos").getString("Pet_T02");
            Idioma.RSA_B01=j.getJSONObject("textos").getString("RSA_B01");
            Idioma.RSA_B02=j.getJSONObject("textos").getString("RSA_B02");
            Idioma.RSA_T01=j.getJSONObject("textos").getString("RSA_T01");
            Idioma.RSB_B01=j.getJSONObject("textos").getString("RSB_B01");
            Idioma.RSB_B02=j.getJSONObject("textos").getString("RSB_B02");
            Idioma.RSB_T01=j.getJSONObject("textos").getString("RSB_T01");
            Idioma.RSC_B01=j.getJSONObject("textos").getString("RSC_B01");
            Idioma.RSC_T01=j.getJSONObject("textos").getString("RSC_T01");
            Idioma.Sin_B01=j.getJSONObject("textos").getString("Sin_B01");
            Idioma.Sin_B02=j.getJSONObject("textos").getString("Sin_B02");
            Idioma.Sin_T01=j.getJSONObject("textos").getString("Sin_T01");
            Idioma.Sin_T02=j.getJSONObject("textos").getString("Sin_T02");
            Idioma.Sin_T03=j.getJSONObject("textos").getString("Sin_T03");
            Idioma.Sin_T04=j.getJSONObject("textos").getString("Sin_T04");
            Idioma.Sin_T05=j.getJSONObject("textos").getString("Sin_T05");


        }catch(Exception e){
            customDialog = new Dialog(MainActivity.this,R.style.Theme_Dialog_Translucent);
            //deshabilitamos el título por defecto
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //obligamos al usuario a pulsar los botones para cerrarlo
            customDialog.setCancelable(false);
            //establecemos el contenido de nuestro dialog
            customDialog.setContentView(R.layout.alert_conexion);

            TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
            titulo.setText(getString(R.string.errorconexion));

            TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
            contenido.setText("lore irsum");

            LinearLayout con=(LinearLayout) customDialog.findViewById(R.id.contenedor);

            con.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.exit(0);
                }
            });

            customDialog.show();

        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        video = (VideoView)findViewById(R.id.video_splash);
        TextView c=(TextView)findViewById(R.id.textView8);
        c.setText(Idioma.Lau_T01);
        Uri path = null;
        try {
            path = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.animacion_final);
            video.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    return true;
                }
            });
            video.setVideoURI(path);
            video.setMediaController(new MediaController(getApplicationContext()));
            video.requestFocus();
            video.start();
        }catch(Exception e){
            Toast.makeText(MainActivity.this,"NO VIDEO",Toast.LENGTH_SHORT).show();
        }

        System.out.println("idioma: "+Locale.getDefault().getDisplayLanguage());


        new AsyncTask<Object,Object,Object>(){
            @Override
            protected Object doInBackground(Object... params) {
                idioma();
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);

                if(!isOnline()){
                    customDialog = new Dialog(MainActivity.this,R.style.Theme_Dialog_Translucent);
                    //deshabilitamos el título por defecto
                    customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    //obligamos al usuario a pulsar los botones para cerrarlo
                    customDialog.setCancelable(false);
                    //establecemos el contenido de nuestro dialog
                    customDialog.setContentView(R.layout.alert_conexion);

                    TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
                    titulo.setText(getString(R.string.errorconexion));

                    TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
                    contenido.setText("lore irsum");

                    Button con=(Button) customDialog.findViewById(R.id.button9);

                    con.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            System.exit(0);
                        }
                    });

                    customDialog.show();
                }else{

                    FacebookSdk.sdkInitialize(getApplicationContext());
                    callbackManager = CallbackManager.Factory.create();



                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {

                            if (!Memoria.compVersion()) {
                                System.exit(0);
                            }

                        }
                    });


                    if(AccessToken.getCurrentAccessToken() != null){
                        if(!AccessToken.getCurrentAccessToken().getPermissions().containsAll(Arrays.asList("public_profile", "user_friends", "email", "user_birthday","user_photos","user_location"))){
                            Toast.makeText(MainActivity.this,"FALTAN PERMISOS EN FACEBOOK",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, InicioActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            getfacebook();
                        }
                    }else{
                        Intent intent = new Intent(MainActivity.this, InicioActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }.execute("");


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void getfacebook(){
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try{
                            try{
                                nombreFb=object.getString("name");

                            }catch(Exception e){
                                nombreFb="Null";

                            }
                            try{
                                PrApFB=object.getString("first_name");

                            }catch(Exception e){
                                PrApFB="Null";

                            }
                            try{
                                ScApFb=object.getString("last_name");

                            }catch(Exception e){
                                ScApFb="Null";

                            }
                            try{
                                gender=object.getString("gender");

                            }catch(Exception e){
                                customDialog = new Dialog(MainActivity.this,R.style.Theme_Dialog_Translucent);
                                //deshabilitamos el título por defecto
                                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                //obligamos al usuario a pulsar los botones para cerrarlo
                                customDialog.setCancelable(false);
                                //establecemos el contenido de nuestro dialog
                                customDialog.setContentView(R.layout.alert_conexion);

                                TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
                                titulo.setText("NO HAS DEFINIDO EL GENERO EN FACEBOOK PARA LA APP");

                                TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
                                contenido.setText("lore irsum");

                                Button con=(Button) customDialog.findViewById(R.id.button9);

                                con.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        System.exit(0);
                                    }
                                });

                                customDialog.show();

                            }
                            try{
                                FechaNac=object.getString("birthday");

                            }catch(Exception e){
                                FechaNac="Null";

                            }
                            try{
                                Ciudad=object.getJSONObject("location").getString("name").split(",")[0];//

                            }catch(Exception e){
                                Ciudad="Null";//

                            }
                            try{
                                Emilio=object.getString("email");

                            }catch(Exception e){
                                Emilio="Null";

                            }
                            try{
                                GentedeZw=object.getJSONObject("friends").getJSONArray("data").getJSONObject(0).getString("id");
                                for(int i=1;i<object.getJSONObject("friends").getJSONArray("data").length();i++){
                                    GentedeZw+=("," + object.getJSONObject("friends").getJSONArray("data").getJSONObject(i).getString("id"));
                                }
                            }catch(Exception e){
                                GentedeZw="Null";
                            }
                            try{
                                fotoPerfURL=object.getJSONObject("picture").getJSONObject("data").getString("url");
                                AsyncTask.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            URL fb_url = new URL(fotoPerfURL);
                                            HttpURLConnection conn1 = (HttpURLConnection) fb_url.openConnection();
                                            HttpURLConnection.setFollowRedirects(true);
                                            conn1.setInstanceFollowRedirects(true);


                                            ByteArrayOutputStream out=new ByteArrayOutputStream();
                                            InputStream in=conn1.getInputStream();



                                            Bitmap fb_img = BitmapFactory.decodeStream(conn1.getInputStream());
                                        /*
                                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                        fb_img.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

                                        byte[] byteArray = new byte[4096];
                                        int count;
                                        while ((count = in.read(byteArray)) != -1) {
                                            out.write(byteArray, 0, count);
                                        }

                                        Base64.encodeToString(byteArray, Base64.URL_SAFE)
                                        */
                                            Memoria.DataFotoPerfil=fb_img;
                                            Memoria.nombrefotoPerfil=Math.abs((int)(System.currentTimeMillis()*10000000))+"";


                                        }catch(Exception e){
                                            System.out.println("fallo al descargar la foto");
                                        }
                                    }
                                });
                            }catch(Exception e){
                                fotoPerfURL="Null";

                            }

                            loginServer();


                        }catch(Exception e){
                            System.out.println("lel");
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,first_name,last_name,email,gender,friends,picture.width(500).height(500),birthday,location");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            DecimalFormat dec=new DecimalFormat("0.000000");
                            mLastLocation = task.getResult();

                            String lat = dec.format(mLastLocation.getLatitude());

                            String log = dec.format(mLastLocation.getLongitude());

                            posGeo = lat + "," + log;

                        } else {
                            Log.w(TAG,"getLastLocation:exception", task.getException());
                            posGeo="0.000000,0.000000";
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        loginServer();
    }

    private void loginServer() {


        if (!checkPermissions()) {
            startLocationPermissionRequest();
        } else {
            getLastLocation();


        tokenfb = AccessToken.getCurrentAccessToken().getToken();
        idFB = AccessToken.getCurrentAccessToken().getUserId();
        plat = "and";
        lan = "es_ES";
        funcion = "f02";
        mod = Build.MANUFACTURER + " " + Build.MODEL;

        TimeZone tz = TimeZone.getDefault();
        Date now = new Date();
        //Import part : x.0 for double number
        double offsetFromUtc = tz.getOffset(now.getTime()) / 3600000.0;
        timeZone = (int) offsetFromUtc + "";


        new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... paramss) {

                MyFirebaseInstanceIDService u = new MyFirebaseInstanceIDService();
                u.onTokenRefresh();
                tokenpush = u.getToken();
                try {
                    params = "funcion=f02&tokenFac=" + tokenfb + "&idFacebo=" + idFB + "&nombreFb=" + nombreFb + "&priNomFb=" + PrApFB + "&lasNomFb=" + ScApFb + "&generoFb=" + gender + "&fNacimFb=" + FechaNac + "&ciudadFb=" + Ciudad + "&emailFac=" + Emilio + "&amigosFb=" + GentedeZw + "&tokenPus=" + tokenpush + "&platafor=and&modMovil=" + mod + "&language=" + lan + "&geolocal=" + posGeo + "&timeZone=" + timeZone;
                    HttpURLConnection conn = (HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    PrintWriter wr = new PrintWriter(
                            conn.getOutputStream());
                    wr.print(params);
                    wr.close();
                    conn.connect();
                    Scanner inStream = new Scanner(conn.getInputStream());
                    while (inStream.hasNextLine())
                        response += (inStream.nextLine());

                    conn.disconnect();


                    JSONObject json = new JSONObject(response.substring(4));
                    Memoria.setLogin(json, MainActivity.this);

                    for (int i = 0; i < Memoria.genteTabla.length(); i++) {
                        try {
                            People p = new People(Memoria.genteTabla.getJSONObject(i), MainActivity.this);
                            Memoria.peoplesT.add(p);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    for (int i = 0; i < Memoria.gente.length(); i++) {
                        try {
                            People p = new People(Memoria.gente.getJSONObject(i), MainActivity.this);
                            Memoria.peoples.add(p);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    String para = "funcion=f22&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension;
                    HttpURLConnection con = (HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    PrintWriter wri = new PrintWriter(
                            con.getOutputStream());
                    wri.print(para);
                    wri.close();
                    con.connect();
                    Scanner Stream = new Scanner(con.getInputStream());
                    String hola="";
                    while (Stream.hasNextLine())
                        hola += (Stream.nextLine());

                    con.disconnect();

                    JSONObject uva = new JSONObject(hola);

                    Memoria.setTotal(uva);

                    if(Memoria.gente.length()==0){
                        MainActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                customDialog = new Dialog(MainActivity.this, R.style.Theme_Dialog_Translucent);
                                //deshabilitamos el título por defecto
                                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                //obligamos al usuario a pulsar los botones para cerrarlo
                                customDialog.setCancelable(false);
                                //establecemos el contenido de nuestro dialog
                                customDialog.setContentView(R.layout.alert_conexion);

                                TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
                                titulo.setText("AVISO");

                                TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
                                contenido.setText("No tienes gente en tu circulo GLUE");

                                LinearLayout con = (LinearLayout) customDialog.findViewById(R.id.contenedor);

                                con.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        System.exit(0);
                                    }
                                });

                                customDialog.show();
                            }
                        });
                    }

                } catch (Exception e) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            customDialog = new Dialog(MainActivity.this, R.style.Theme_Dialog_Translucent);
                            //deshabilitamos el título por defecto
                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            //obligamos al usuario a pulsar los botones para cerrarlo
                            customDialog.setCancelable(false);
                            //establecemos el contenido de nuestro dialog
                            customDialog.setContentView(R.layout.alert_conexion);

                            TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
                            titulo.setText(getString(R.string.errorservidor));

                            TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
                            contenido.setText(getString(R.string.error2));

                            LinearLayout con = (LinearLayout) customDialog.findViewById(R.id.contenedor);

                            con.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    System.exit(0);
                                }
                            });

                            customDialog.show();
                        }
                    });

                }

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {

                if (Memoria.Modo == 0) {
                    Intent in = new Intent(MainActivity.this, Principal.class);
                    startActivity(in);
                    finish();
                } else {
                    Intent in = new Intent(MainActivity.this, Programa_Principal.class);
                    startActivity(in);
                    finish();
                }
                super.onPostExecute(o);
            }
        }.execute("");


    }
    }

}