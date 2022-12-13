package srinternet.glue;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EdgeEffect;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Carpetas extends AppCompatActivity {
    RelativeLayout screenLayout;
    CarpetasAdapter gridAdapter;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carpetas);
        TextView carpet=(TextView)findViewById(R.id.textView26);
        carpet.setText(Idioma.MFF_T01);

        ImageView img=(ImageView)findViewById(R.id.img_btn_atras_carpetas);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        screenLayout = (RelativeLayout) findViewById(R.id.screenLayout);
        gridView = (GridView) findViewById(R.id.Lista_Carpetas);
        gridAdapter = new CarpetasAdapter(this, Memoria.albumList);
        gridView.setAdapter(gridAdapter);
        gridAdapter.notifyDataSetChanged();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                gridView.setEnabled(false);
                Album al=Memoria.albumList.get(i);
                Memoria.fotosList=new ArrayList<>();
                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),"/"+al.getId()+"/photos",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
                                try {
                                    final int ok=response.getJSONObject().getJSONArray("data").length();
                                    for(int i=0;i<response.getJSONObject().getJSONArray("data").length();i++) {
                                        final int pos=i;
                                                try {
                                                    Memoria.fotosList.add(new Foto(response.getJSONObject().getJSONArray("data").getJSONObject(i).getString("id")));
                                                    if((pos+1)==ok){

                                                        Intent o=new Intent(getBaseContext(), Carga_fotos.class);
                                                        startActivity(o);
                                                        finish();
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                    }


                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }
                ).executeAsync();
            }
        });
    }
}
