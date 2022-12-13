package srinternet.glue;

import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PreguntasFrecuentesActivity extends AppCompatActivity {

    ImageView atras;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas_frecuentes);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        atras = (ImageView)findViewById(R.id.img_btn_atras_preguntasfrecuentes);
        TextView p=(TextView)findViewById(R.id.textView26);
        p.setText(Idioma.Men_B08);
        WebView webView=(WebView)findViewById(R.id.web);
        try {
            AssetManager assetManager = getBaseContext().getAssets();
            InputStream stream = assetManager.open("PreguntasFrecuentes.html");
            BufferedReader r = new BufferedReader(new InputStreamReader(stream));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line).append("\n");
            }
            webView.loadDataWithBaseURL(null, total.toString(), "text/html", "UTF-8", null);
        } catch (Exception xxx) {
            System.out.println("Error al cargar preguntas"+xxx.getMessage());
        }
        //File file = new File("./PreguntasFrecuentes.html");
        //webView.loadUrl("www.google.com");
        //webView.loadUrl("file:///PreguntasFrecuentes.html");
        //webView.setWebViewClient(new WebViewClient());

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
