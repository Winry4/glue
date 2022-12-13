package srinternet.glue;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.icu.text.UnicodeFilter;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChatIndividual extends AppCompatActivity {
    Dialog customDialog = null;
    private ViewPager mViewPager;
    EditText mensaje;
    String mandar;
    RecyclerView chat;
    private List<MensajeDeTexto> mensajeDeTextoList=new ArrayList<>();
    private MensajeAdapter adapter;
    private String idenChat;
    RecyclerView recyclerView;

    public MensajeAdapter cargar() throws JSONException {
        mensajeDeTextoList.clear();
        System.out.println("gato");
        for (int i=1;i<Memoria.chatActual.getJSONArray("mensajes").length();i++){
            int id=0;
            try {

                if(Memoria.chatActual.getJSONArray("mensajes").getJSONObject(i).getString("IDFACEBO").equals(Memoria.UserFBId)){
                    id=2;
                }else {
                    id=1;
                }
                MensajeDeTexto msn=new MensajeDeTexto(Memoria.chatActual.getJSONArray("mensajes").getJSONObject(i).getString("MENSAJES"), Memoria.chatActual.getJSONArray("mensajes").getJSONObject(i).getString("FECHAREG"), id);
                mensajeDeTextoList.add(msn);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        adapter=new MensajeAdapter(mensajeDeTextoList);
        adapter.notifyDataSetChanged();
        return adapter;

    }
    public void redondear(Bitmap originalBitmap){
        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getBaseContext().getResources(), originalBitmap);
        roundedDrawable.setCornerRadius(originalBitmap.getHeight());
        ImageView imageView = (ImageView) findViewById(R.id.fotochat);
        imageView.setImageDrawable(roundedDrawable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_individual);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ImageView foto=(ImageView)findViewById(R.id.fotochat);
        TextView nombre=(TextView)findViewById(R.id.nombre);
        TextView lugar=(TextView)findViewById(R.id.lugar);
        TextView edad=(TextView)findViewById(R.id.edad);
        final int identificador=getIntent().getExtras().getInt("Posicion");
        int tipo=getIntent().getExtras().getInt("tipo");
        final String idfc;
        if(tipo==1){
            redondear(Memoria.listGrids.get(identificador).getImageId());
            nombre.setText(Memoria.listGrids.get(identificador).getNombre());
            idfc=Memoria.listGrids.get(identificador).getIdFb();
            idenChat=Memoria.listGrids.get(identificador).getIdentificador();
        }else{
            redondear(Memoria.listGridsGluer.get(identificador).getImageId());
            nombre.setText(Memoria.listGridsGluer.get(identificador).getNombre());
            idfc=Memoria.listGridsGluer.get(identificador).getIdFb();
            idenChat=Memoria.listGridsGluer.get(identificador).getIdentificador();
        }
        try {
            lugar.setText(Memoria.chatActual.getJSONArray("usuarioChat").getJSONObject(0).getString("CIUDADFB"));
            edad.setText(Memoria.chatActual.getJSONArray("usuarioChat").getJSONObject(0).getString("EDADUSER"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final RecyclerView recycler=(RecyclerView)findViewById(R.id.recycler);
        Memoria.recy=recycler;
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recycler.setLayoutManager(linearLayoutManager);
        try {
            adapter=cargar();
            recycler.setAdapter(adapter);
            recycler.scrollToPosition(adapter.getItemCount()-1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ImageView img=(ImageView)findViewById(R.id.btn_atras);
        mensaje=(EditText)findViewById(R.id.editText);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ImageView boton_enviar=(ImageView) findViewById(R.id.btn_enviar);
        boton_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mensaje.getText().toString().equals("")) {
                    mandar = mensaje.getText().toString();

                    mandar= StringEscapeUtils.escapeJava(mandar);
                    mandar= mandar.replace("\\","\\\\");
                    mensaje.setText("");
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                HttpURLConnection conn = (HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                                conn.setRequestMethod("POST");
                                conn.setDoOutput(true);
                                conn.setDoInput(true);
                                PrintWriter wr = new PrintWriter(
                                        conn.getOutputStream());
                                String response = "";
                                String param = "funcion=f18&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension + "&idenChat=" + idenChat + "&mensajes=" + mandar;
                                System.out.println(param);
                                wr.print(param);
                                wr.close();
                                conn.connect();
                                Scanner inStream = new Scanner(conn.getInputStream());
                                while (inStream.hasNextLine())
                                    response += (inStream.nextLine());
                                conn.disconnect();
                                JSONObject u = new JSONObject(response);
                                Memoria.chatActual = u;
                                System.out.println(u);
                                System.out.println("enviando chat" + u);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
                                        recycler.setLayoutManager(linearLayoutManager);
                                        adapter = cargar();
                                        recycler.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                        recycler.scrollToPosition(adapter.getItemCount() - 1);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });


                        }

                    });
                }
            }

        });


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


    }
    public void mostrar2(View view, String text, String msn)
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

