package srinternet.glue;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Rebeca on 17/08/2017.
 */

public class Carga_fotos extends AppCompatActivity {
    RelativeLayout screenLayout;
    CargaFotos_Adapter gridAdapter;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carga_fotos);

        ImageView img=(ImageView)findViewById(R.id.img_btn_atras_carpetas);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        screenLayout = (RelativeLayout) findViewById(R.id.screenLayout);
        TextView t=(TextView)findViewById(R.id.textView26);
        t.setText(Idioma.MFF_T01);
        gridView = (GridView) findViewById(R.id.Lista_carga);
        gridAdapter = new CargaFotos_Adapter(this, Memoria.fotosList);
        gridView.setAdapter(gridAdapter);
        gridAdapter.notifyDataSetChanged();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int pos = i;
                Memoria.fotoAct.setImageBitmap(Memoria.fotosList.get(pos).getImageId());
                Memoria.botonAct.setImageResource(R.drawable.foto_menos);
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        String bondu="------VohpleBoundary4QuqLuM1cE5lMwCy";
                        //Pasamos la foto a data
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        Memoria.fotosList.get(pos).getImageId().compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] imageData = baos.toByteArray();

                        //Creamso a conection y fijamos los aprametros
                        try{
                            int nombre=Math.abs((int)(System.currentTimeMillis()*10000000));
                            if(Memoria.PosAct>0){
                                Memoria.PerfilFOt[Memoria.PosAct-1]=Memoria.fotosList.get(pos).getImageId();
                                Memoria.PerfilFOtNom[Memoria.PosAct-1]=nombre+"";
                            }else{
                                Memoria.DataFotoPerfil=Memoria.fotosList.get(pos).getImageId();
                                Memoria.nombrefotoPerfil=nombre+"";
                            }

                            HttpURLConnection conn =(HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("Connection", "Keep-Alive");
                            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + bondu);
                            conn.setDoOutput(true);
                            conn.setDoInput(true);
                            DataOutputStream wr = new DataOutputStream(
                                    conn.getOutputStream());
                            //creamos la cadena y la insertamos
                            String params="";
                            HashMap<String, String> h = new HashMap<String, String>();
                            h.put("funcion","f09");
                            h.put("idFacebo",Memoria.UserFBId);
                            h.put("idSesion",Memoria.idSension);
                            h.put("posicion",""+(Memoria.PosAct+1));
                            h.put("fotograf",""+nombre);

                            for (HashMap.Entry<String, String> entry : h.entrySet()){
                                params+=("--"+bondu+"\r\n");
                                params+=("Content-Disposition:form-data; name=\""+entry.getKey()+"\"\r\n\r\n");
                                params+=(entry.getValue()+"\r\n");
                            }

                            params+="\r\n--"+bondu+"\r\n";
                            params+="Content-Disposition:form-data; name=\"uploadedfile\"; filename=\""+nombre+".jpg\"\r\n";
                            params+="Content-Type:application/octet-stream\r\n\r\n";
                            wr.writeBytes(params);
                            wr.write(imageData);
                            wr.writeBytes("\r\n--"+bondu+"--\r\n");
                            //La insertamos en el stream

                            wr.close();
                            conn.connect();

                            String response="";
                            Scanner inStream = new Scanner(conn.getInputStream());
                            while(inStream.hasNextLine())
                                response+=(inStream.nextLine());

                            conn.disconnect();

                            JSONObject json= new JSONObject(response);

                            Memoria.Fotos=json.getJSONArray("fotos");

                            File f = new File(getBaseContext().getFilesDir(), nombre+"");
                            FileOutputStream outputStream = openFileOutput(nombre+"", Programa_Principal.MODE_PRIVATE);
                            ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
                            Memoria.fotosList.get(pos).getImageId().compress(Bitmap.CompressFormat.JPEG, 100, baos1);
                            byte[] imageData1 = baos.toByteArray();
                            outputStream.write(imageData1);
                            outputStream.close();



                        }catch(Exception e){
                            System.out.println(e.toString());
                        }
                    }
                });
                finish();
            }
        });

    }
}
