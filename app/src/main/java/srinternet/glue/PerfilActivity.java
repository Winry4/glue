package srinternet.glue;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class PerfilActivity extends AppCompatActivity implements MiPerfil.OnFragmentInteractionListener, MiHistoria.OnFragmentInteractionListener  {



    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    Dialog customDialog = null;

    ImageView atras;
    ImageView foto;
    int i;
    public void guardar(View view){
        TextView h = (TextView) findViewById(R.id.textView49);
        h.setVisibility(View.INVISIBLE);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String response="";
                    HttpURLConnection conn = (HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    PrintWriter wr = new PrintWriter(
                            conn.getOutputStream());
                    String params="funcion=f05&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension + Memoria.getCambios();
                    wr.print(params);

                    wr.close();
                    conn.connect();
                    Scanner inStream = new Scanner(conn.getInputStream());
                    while(inStream.hasNextLine())
                        response+=(inStream.nextLine());

                    conn.disconnect();
                    JSONObject json= new JSONObject(response);
                    Memoria.setCambios(json);

                    Memoria.peoples.clear();
                    Memoria.peoplesT.clear();
                    for (int i=0;i<Memoria.genteTabla.length();i++){
                        try {
                            People p=new People(Memoria.genteTabla.getJSONObject(i), PerfilActivity.this);
                            Memoria.peoplesT.add(p);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    for (int i=0;i<Memoria.gente.length();i++){
                        try {
                            People p=new People(Memoria.gente.getJSONObject(i), PerfilActivity.this);
                            Memoria.peoples.add(p);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });


    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        foto = (ImageView) findViewById(R.id.img_fotoperfil);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        atras = (ImageView) findViewById(R.id.img_btn_atras);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBackPressed();
            }
        });

        if(Memoria.DataFotoPerfil==null){
            try {
                String filename = Memoria.Fotos.getJSONObject(0).getString("FOTOGRAF");
                File f = new File(getBaseContext().getFilesDir(), filename);
                if(f.exists()){

                    Memoria.DataFotoPerfil=BitmapFactory.decodeFile(f.getAbsolutePath());

                    Intent intent=new Intent(getBaseContext(), PerfilActivity.class);
                    startActivity(intent);
                }else{

                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String filenam=Memoria.Fotos.getJSONObject(0).getString("FOTOGRAF");
                                String response = "";
                                HttpURLConnection conn = (HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                                conn.setRequestMethod("POST");
                                conn.setDoOutput(true);
                                conn.setDoInput(true);
                                PrintWriter wr = new PrintWriter(
                                        conn.getOutputStream());
                                String params = "funcion=f00&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension + "&idFbPers=" + Memoria.UserFBId + "&fotograf=" + filenam;
                                wr.print(params);
                                wr.close();
                                conn.connect();

                                String res="";
                                Scanner Stream = new Scanner(conn.getInputStream());
                                while(Stream.hasNextLine())
                                    res+=(Stream.nextLine());

                                conn.disconnect();

                                JSONObject json= new JSONObject(res);

                                byte[] decodedString = Base64.decode(json.getString("foto"), Base64.DEFAULT);
                                Memoria.DataFotoPerfil = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);



                                FileOutputStream outputStream = openFileOutput(filenam, Programa_Principal.MODE_PRIVATE);
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                Memoria.DataFotoPerfil.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                byte[] imageData = baos.toByteArray();
                                outputStream.write(imageData);
                                outputStream.close();

                                PerfilActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Bitmap originalBitmap = Memoria.DataFotoPerfil;
                                        //creamos el drawable redondeado
                                        RoundedBitmapDrawable roundedDrawable =
                                                RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);
                                        //asignamos el CornerRadius
                                        roundedDrawable.setCornerRadius(originalBitmap.getHeight());
                                        foto.setImageDrawable(roundedDrawable);
                                    }
                                });

                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    });

                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            Bitmap originalBitmap = Memoria.DataFotoPerfil;
            //creamos el drawable redondeado
            RoundedBitmapDrawable roundedDrawable =
                    RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);
            //asignamos el CornerRadius
            roundedDrawable.setCornerRadius(originalBitmap.getHeight());


            //button1=(Button) findViewById(R.id.grid_item_btn);
            foto.setImageDrawable(roundedDrawable);
        }

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                            String params = "funcion=f34&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension + "&idFbPers=" + Memoria.UserFBId;
                            wr.print(params);
                            wr.close();
                            conn.connect();

                            String res="";
                            Scanner Stream = new Scanner(conn.getInputStream());
                            while(Stream.hasNextLine())
                                res+=(Stream.nextLine());

                            conn.disconnect();

                            JSONObject json= new JSONObject(res);



                            for(i=1;i<json.getJSONArray("fotos").length();i++) {
                                String filename = json.getJSONArray("fotos").getString(i);
                                if(!filename.equals("")) {
                                    Memoria.PerfilFOtNom[i-1]=filename;
                                }
                            }
                            Intent intent = new Intent(getBaseContext(),Perfil.class);
                            startActivity(intent);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });

            }
        });




    }

    public void borrarcache(View view)
    {
        customDialog = new Dialog(PerfilActivity.this,R.style.Theme_Dialog_Translucent);
        //deshabilitamos el título por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.alert_dialog);

        TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
        titulo.setText("BORRAR CACHE");

        TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
        contenido.setText("Si vacias el cache, la primera vez que cargues una foto, tendrá que descargarse de nuevo");
        (customDialog.findViewById(R.id.aceptar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                File cacheDir = getCacheDir();
                for (File f : cacheDir.listFiles()){
                    f.delete();
                }
                File extCacheDir = getExternalCacheDir();
                for (File f : extCacheDir.listFiles()){
                    f.delete();
                }
                File dir = getFilesDir();
                for (File f : dir.listFiles()){
                    f.delete();
                }
                Intent intent = new Intent(PerfilActivity.this, InicioActivity.class);
                startActivity(intent);
                finish();

            }
        });
        ((Button) customDialog.findViewById(R.id.cancelar)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                customDialog.dismiss();
                Toast.makeText(PerfilActivity.this, R.string.cancelar, Toast.LENGTH_SHORT).show();

            }
        });

        customDialog.show();
    }

    public void borrarcuenta(View view)
    {
        customDialog = new Dialog(PerfilActivity.this,R.style.Theme_Dialog_Translucent);
        //deshabilitamos el título por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.alert_dialog);

        TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
        titulo.setText("BORRAR CUENTA");

        TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
        contenido.setText("¿Estas seguro que deseas cerrar tu cuenta y revocar los permisos de Facebook?");
        (customDialog.findViewById(R.id.aceptar)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                customDialog.dismiss();
                Toast.makeText(PerfilActivity.this, R.string.aceptar, Toast.LENGTH_SHORT).show();
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            HttpURLConnection conn = (HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                            conn.setRequestMethod("POST");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);
                            PrintWriter wr = new PrintWriter(
                                    conn.getOutputStream());
                            String params="funcion=f12&idFacebo="+Memoria.UserFBId+"&idSesion="+Memoria.idSension;
                            wr.print(params);
                            wr.close();
                            conn.connect();

                            Intent intent = new Intent(PerfilActivity.this, InicioActivity.class);
                            startActivity(intent);
                            finish();

                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

        ((Button) customDialog.findViewById(R.id.cancelar)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                customDialog.dismiss();
                Toast.makeText(PerfilActivity.this, R.string.cancelar, Toast.LENGTH_SHORT).show();

            }
        });

        customDialog.show();
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

    @Override
    public void onFragmentInteraction(Uri uri) {

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
        public static Fragment newInstance(int sectionNumber) {
            Fragment fragment = null;
            switch (sectionNumber){
                case 1:
                    fragment= new MiPerfil();
                    break;
                case 2:
                    fragment = new MiHistoria();
                    break;
            }
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Mi perfil";
                case 1:
                    return "Mi Historia";
            }
            return null;
        }
    }
}
