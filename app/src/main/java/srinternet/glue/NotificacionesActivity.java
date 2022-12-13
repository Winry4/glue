package srinternet.glue;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NotificacionesActivity extends AppCompatActivity {
    RelativeLayout screenLayout;
    //NotificacionesAdapter listAdapter;
    GridView listView;
    ImageView atras;
    Boolean click;
    private float firstTouchX;
    Dialog customDialog;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        atras = (ImageView)findViewById(R.id.img_btn_atras_notificaciones);
        TextView not=(TextView)findViewById(R.id.textView26);
        not.setText(Idioma.MNo_T01);
        screenLayout = (RelativeLayout)findViewById(R.id.screenLayout);


        Memoria.notiadapter = new NotificacionesAdapter(this,Memoria.notifiList, getBaseContext());
        listView = (GridView) findViewById(R.id.Lista);
        listView.setAdapter(Memoria.notiadapter);
        Memoria.notiadapter.notifyDataSetChanged();
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        screenLayout = (RelativeLayout)findViewById(R.id.screenLayout);
        Memoria.notiadapter = new NotificacionesAdapter(this,Memoria.notifiList, getBaseContext());
        listView = (GridView) findViewById(R.id.Lista);
        listView.setAdapter(Memoria.notiadapter);
        Memoria.notiadapter.notifyDataSetChanged();
    }

}
