package srinternet.glue;

import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BasesLegales extends AppCompatActivity {

    ImageView atras;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bases_legales);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        atras = (ImageView)findViewById(R.id.img_btn_atras_b);
        TextView bases=(TextView)findViewById(R.id.textView26);
        bases.setText(Idioma.MBa_T01);
        WebView webView=(WebView)findViewById(R.id.web);
        try {
            AssetManager assetManager = getBaseContext().getAssets();
            InputStream stream = assetManager.open("BasesLegales.html");
            BufferedReader r = new BufferedReader(new InputStreamReader(stream));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line).append("\n");
            }
            webView.loadDataWithBaseURL(null, total.toString(), "text/html", "UTF-8", null);
        } catch (Exception xxx) {
            System.out.println("Error al cargar bases legales"+xxx.getMessage());
        }

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
