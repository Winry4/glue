package srinternet.glue;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;


public class Programa_Principal extends AppCompatActivity implements FragmentFotos.OnFragmentInteractionListener,principalPaginas.OnFragmentInteractionListener, View.OnClickListener, PrincipalLista.OnFragmentInteractionListener{
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    RelativeLayout screenLayout;
    PrincipalAdapter listAdapter;
    GridView listView;
    ImageView atras;
    Dialog customDialog;
    ImageView glueer,chat,config,foto,lista,cerrar,fotos,amigos;
    ImageView fav, ocul;
    ImageView single,img_gluer;
    ImageView corazon,corazon_anim;
    TextView tx;
    boolean modo = true;
    boolean corazoon = true;
    int i,resume;



    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(Memoria.Modo==1){
            mViewPager=(ViewPager) findViewById(R.id.container);
            mSectionsPagerAdapter=new Programa_Principal.SectionsPagerAdapter(getSupportFragmentManager());;
            mViewPager.setAdapter(mSectionsPagerAdapter);
            Memoria.mViewPager=mViewPager;
            Memoria.mSectionsPagerAdapter=mSectionsPagerAdapter;
            Memoria.mViewPager.setCurrentItem(resume);
        }


    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_programa__principal);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_principal, new principalPaginas()).addToBackStack(null).commit();
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mViewPager=(ViewPager) findViewById(R.id.container);
        mSectionsPagerAdapter=new Programa_Principal.SectionsPagerAdapter(getSupportFragmentManager());;
        mViewPager.setAdapter(mSectionsPagerAdapter);
        resume=mViewPager.getCurrentItem();
        TextView oc=(TextView)findViewById(R.id.textView53);
        TextView fab=(TextView)findViewById(R.id.tx_favorito);
        TextView amig=(TextView)findViewById(R.id.tx_amigoscomun_princi);
        TextView fot=(TextView)findViewById(R.id.tx_fotos_princi);
        oc.setText(Idioma.Sin_B01);
        fab.setText(Idioma.Sin_B02);
        amig.setText(Idioma.Sin_T01);
        fot.setText(Idioma.Sin_T02);


        ImageView circulo=(ImageView) findViewById(R.id.imageView58);
        TextView vv = (TextView) findViewById(R.id.textView6);

        circulo.setColorFilter(Color.RED);
        vv.setText(String.valueOf(Memoria.TotNotif));

        if(Memoria.TotNotif<=0){
            circulo.setVisibility(View.INVISIBLE);
            vv.setVisibility(View.INVISIBLE);
        }

        Memoria.mViewPager=mViewPager;
        Memoria.mSectionsPagerAdapter=mSectionsPagerAdapter;



        screenLayout = (RelativeLayout)findViewById(R.id.fragment_principal);

        listView = (GridView) findViewById(R.id.grid_principal);
        listView.setVisibility(View.INVISIBLE);

        // Set up the ViewPager with the sections adapter.
        tx = (TextView)findViewById(R.id.textView52);
        tx.setVisibility(View.INVISIBLE);
        config = (ImageView) findViewById(R.id.img_btn_config);
        chat = (ImageView) findViewById(R.id.img_btn_chat);




        lista = (ImageView)findViewById(R.id.img_elegido_lista);
        fotos = (ImageView)findViewById(R.id.btn_fotos_principal);
        single = (ImageButton)findViewById(R.id.img_single);
        img_gluer = (ImageButton)findViewById(R.id.img_gluer);
        glueer = (ImageView) findViewById(R.id.img_btn_gluer);
        single.setVisibility(View.INVISIBLE);
        img_gluer.setVisibility(View.INVISIBLE);
        amigos = (ImageView) findViewById(R.id.imageView26);

///AQUIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII


        final ImageView fav = (ImageView) findViewById(R.id.btn_favorito_principal);
        final TextView txfav = (TextView) findViewById(R.id.tx_favorito);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                resume=Memoria.mViewPager.getCurrentItem();
                if(Memoria.peoples.get(i).isFav()){
                    fav.setColorFilter(Color.CYAN);
                    txfav.setTextColor(Color.CYAN);
                }else{
                    fav.setColorFilter(getResources().getColor(R.color.colorBackgroundTrash));
                    txfav.setTextColor(getResources().getColor(R.color.colorBackgroundTrash));
                }
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pagAct=mViewPager.getCurrentItem();
                final People u = Memoria.peoples.get(pagAct);
                if(u.isFav()){
                    fav.setColorFilter(Color.CYAN);
                    txfav.setTextColor(Color.CYAN);
                }else{
                    fav.setColorFilter(getResources().getColor(R.color.colorBackgroundTrash));
                    txfav.setTextColor(getResources().getColor(R.color.colorBackgroundTrash));
                }
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
                            String param = "funcion=f07&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension + "&idFbPers=" + u.getIdFace();                            wr.print(param);
                            wr.close();
                            conn.connect();
                            String ress="";
                            Scanner Strea = new Scanner(conn.getInputStream());
                            while(Strea.hasNextLine())
                                ress+=(Strea.nextLine());

                            conn.disconnect();

                            JSONObject obj= new JSONObject(ress);
                            Memoria.genteTabla=obj.getJSONArray("genteTabla");
                            Memoria.gente=obj.getJSONArray("gente");
                            Memoria.peoples.clear();
                            Memoria.peoplesT.clear();
                            for (int i=0;i<Memoria.genteTabla.length();i++){
                                try {
                                    People p=new People(Memoria.genteTabla.getJSONObject(i), Programa_Principal.this);
                                    Memoria.peoplesT.add(p);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            for (int i=0;i<Memoria.gente.length();i++){
                                try {
                                    People p=new People(Memoria.gente.getJSONObject(i), Programa_Principal.this);
                                    Memoria.peoples.add(p);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Memoria.mViewPager.setAdapter(Memoria.mSectionsPagerAdapter);

                                    Memoria.mSectionsPagerAdapter.notifyDataSetChanged();

                                }
                            });



                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


        final ImageView ocu = (ImageView) findViewById(R.id.btn_ocultar_principal);
        ocu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pagAct=mViewPager.getCurrentItem();
                final People u = Memoria.peoplesT.get(pagAct);

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
                            String param = "funcion=f06&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension + "&idFbPers=" + u.getIdFace();                            wr.print(param);
                            wr.close();
                            conn.connect();
                            String ress="";
                            Scanner Strea = new Scanner(conn.getInputStream());
                            while(Strea.hasNextLine())
                                ress+=(Strea.nextLine());

                            conn.disconnect();

                            JSONObject obj= new JSONObject(ress);
                            Memoria.genteTabla=obj.getJSONArray("genteTabla");
                            Memoria.gente=obj.getJSONArray("gente");
                            Memoria.peoples.clear();
                            Memoria.peoplesT.clear();
                            for (int i=0;i<Memoria.genteTabla.length();i++){
                                try {
                                    People p=new People(Memoria.genteTabla.getJSONObject(i), Programa_Principal.this);
                                    Memoria.peoplesT.add(p);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            for (int i=0;i<Memoria.gente.length();i++){
                                try {
                                    People p=new People(Memoria.gente.getJSONObject(i), Programa_Principal.this);
                                    Memoria.peoples.add(p);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Memoria.mViewPager.setAdapter(Memoria.mSectionsPagerAdapter);
                                    Memoria.mSectionsPagerAdapter.notifyDataSetChanged();
                                }
                            });



                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
//HASTA AQUIIIIIIIIIIIIIIIIIIIIIII


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int ancho = metrics.widthPixels; // ancho absoluto en pixels
        int alto = metrics.heightPixels; // alto absoluto en pixels
        Memoria.alto = alto;
        Memoria.ancho = ancho;
        Intent qwerty = new Intent(getBaseContext(),principalPaginas.class);
        qwerty.putExtra("ancho",ancho);
        qwerty.putExtra("alto",alto);

        fotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pagAct=mViewPager.getCurrentItem();
                Memoria.posicionFragment=pagAct;
                final String idFace = Memoria.peoples.get(pagAct).getIdFace();
                Memoria.idFaceFotos = Memoria.peoples.get(pagAct).getIdFace();

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
                            String params = "funcion=f34&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension + "&idFbPers=" + idFace;
                            wr.print(params);
                            wr.close();
                            conn.connect();

                            String res = "";
                            Scanner Stream = new Scanner(conn.getInputStream());
                            while (Stream.hasNextLine())
                                res += (Stream.nextLine());

                            conn.disconnect();

                            JSONObject json = new JSONObject(res);

                            for(int j=0;j<Memoria.PerfilFOtNomUser.length;j++){
                                Memoria.PerfilFOtNomUser[j] = null;
                            }

                            for (i = 0; i < json.getJSONArray("fotos").length(); i++) {
                                String filename = json.getJSONArray("fotos").getString(i);
                                if (!filename.equals("")) {
                                    Memoria.PerfilFOtNomUser[i] = filename;
                                }
                            }
                            if(Memoria.clickVerFOtos){
                                Intent intent = new Intent(getBaseContext(), fotosPrincipal.class);
                                startActivity(intent);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
        glueer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(single.getVisibility()==View.INVISIBLE){
                    single.setVisibility(View.VISIBLE);
                    img_gluer.setVisibility(View.VISIBLE);
                    modo = false;
                }else{
                    single.setVisibility(View.INVISIBLE);
                    img_gluer.setVisibility(View.INVISIBLE);
                    modo = true;
                }

            }
        });
        Thread thread;

        cerrar = (ImageView)findViewById(R.id.btn_cerrar_principal);
        cerrar.setVisibility(View.INVISIBLE);
        corazon =(ImageView)findViewById(R.id.btn_corazon_principal);
        final Resources resources = getResources();


        View.OnClickListener accionCor=new View.OnClickListener() {
            public void onClick(View v) {


                Animation anim = AnimationUtils.loadAnimation(getBaseContext(), R.anim.alpha);
                anim .setFillAfter(true);

                int pagAct=mViewPager.getCurrentItem();
                if (corazoon) {
                    Memoria.princiList.clear();
                    Memoria.peoples.get(pagAct).cogerAmigos();
                    listAdapter = new PrincipalAdapter(getBaseContext(),Memoria.princiList,Programa_Principal.this);
                    listView = (GridView) findViewById(R.id.grid_principal);
                    listView.setAdapter(listAdapter);
                    listAdapter.notifyDataSetChanged();
                    corazon.setImageDrawable(resources.getDrawable(R.drawable.boton_corazon_animacion));
                    Animation logoMoveAnimation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.animacion);
                    corazon.startAnimation(logoMoveAnimation);
                    cerrar.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.VISIBLE);
                    corazoon = false;

                    Memoria.clickVerFOtos=false;

                    glueer.setClickable(false);
                    chat.setClickable(false);
                    config.setClickable(false);
                    ocu.setClickable(false);
                    fav.setClickable(false);
                    amigos.setClickable(false);
                    fotos.setClickable(false);

                    findViewById(R.id.img_elegido_lista).setClickable(false);


                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            int pagAct=mViewPager.getCurrentItem();
                            final String idFace = Memoria.peoples.get(pagAct).getIdFace();
                            Princi princi = Memoria.princiList.get(position);
                            if(princi.getOculto().equals("1")){
                                Memoria.clickVerFOtos=true;
                                glueer.setClickable(true);
                                chat.setClickable(true);
                                config.setClickable(true);
                                ocu.setClickable(true);
                                fav.setClickable(true);
                                amigos.setClickable(true);
                                fotos.setClickable(true);
                                Intent intent = new Intent(getBaseContext(),LigoteoPrincipal.class);
                                intent.putExtra("acomun",position);
                                intent.putExtra("perfil",pagAct);
                                startActivity(intent);
                                listView.setVisibility(View.INVISIBLE);
                                cerrar.setVisibility(View.INVISIBLE);
                                Animation logoMoveAnimation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.animacion_revertir);
                                logoMoveAnimation.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {

                                        corazon.setImageDrawable(resources.getDrawable(R.drawable.boton_corazon));
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });
                                corazon.startAnimation(logoMoveAnimation);



                                corazoon = true;
                            }

                        }
                    });
                }
            }
        };
        corazon.setOnClickListener(accionCor);
        amigos.setOnClickListener(accionCor);

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Memoria.clickVerFOtos=true;
                glueer.setClickable(true);
                chat.setClickable(true);
                config.setClickable(true);
                ocu.setClickable(true);
                fav.setClickable(true);
                amigos.setClickable(true);
                fotos.setClickable(true);

                findViewById(R.id.img_elegido_lista).setClickable(true);


                listView.setVisibility(View.INVISIBLE);
                cerrar.setVisibility(View.INVISIBLE);
                Animation logoMoveAnimation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.animacion_revertir);
                logoMoveAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        corazon.setImageDrawable(resources.getDrawable(R.drawable.boton_corazon));
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                corazon.startAnimation(logoMoveAnimation);



                corazoon = true;
            }
        });
        config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            String param="funcion=f16&idFacebo="+Memoria.UserFBId+"&idSesion="+Memoria.idSension+"&tipoChat=1";
                            wr.print(param);
                            wr.close();
                            conn.connect();
                            Scanner inStream = new Scanner(conn.getInputStream());
                            while(inStream.hasNextLine())
                                response+=(inStream.nextLine());

                            conn.disconnect();
                            Memoria.listGrids.clear();
                            JSONObject u = new JSONObject(response);
                            int chats=u.getJSONArray("chats").length();
                            for(int i =0;i<chats;i++) {
                                String nombre = u.getJSONArray("chats").getJSONObject(i).getString("PRINOMFB");
                                String mensaje = u.getJSONArray("chats").getJSONObject(i).getString("MENSAJES");
                                String fecha = u.getJSONArray("chats").getJSONObject(i).getString("FECHAREG");
                                String identificador=u.getJSONArray("chats").getJSONObject(i).getString("IDENCHAT");

                                String filename= u.getJSONArray("chats").getJSONObject(i).getString("FOTOGRAF");
                                String fbId=u.getJSONArray("chats").getJSONObject(i).getString("IDFACEBO");

                                ListGrid bg = new ListGrid(nombre, filename, fecha, mensaje, identificador, fbId);
                                Memoria.listGrids.add(bg);

                            }


                        }catch(Exception e){
                            e.printStackTrace();
                        }


                    }

                });
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
                            String param="funcion=f16&idFacebo="+Memoria.UserFBId+"&idSesion="+Memoria.idSension+"&tipoChat=2";
                            wr.print(param);
                            wr.close();
                            conn.connect();
                            Scanner inStream = new Scanner(conn.getInputStream());
                            while(inStream.hasNextLine())
                                response+=(inStream.nextLine());

                            conn.disconnect();
                            Memoria.listGridsGluer.clear();
                            JSONObject u = new JSONObject(response);
                            int chats=u.getJSONArray("chats").length();
                            for(int i =0;i<chats;i++) {
                                String nombre = u.getJSONArray("chats").getJSONObject(i).getString("PRINOMFB");
                                String mensaje = u.getJSONArray("chats").getJSONObject(i).getString("MENSAJES");
                                String fecha = u.getJSONArray("chats").getJSONObject(i).getString("FECHAREG");
                                String identificador=u.getJSONArray("chats").getJSONObject(i).getString("IDENCHAT");
                                String filename= u.getJSONArray("chats").getJSONObject(i).getString("FOTOGRAF");
                                String fbId=u.getJSONArray("chats").getJSONObject(i).getString("IDFACEBO");

                                ListGrid bg = new ListGrid(nombre, filename, fecha, mensaje, identificador, fbId);

                                Memoria.listGridsGluer.add(bg);


                            }


                        }catch(Exception e){
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(getBaseContext(), Chat.class);
                        startActivity(intent);

                    }

                });

            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(View v) {

    }

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

        public int getPos(){
            return pos;
        }
        public static int pos;
        public static Fragment newInstance(int sectionNumber) {
            Fragment fragment = null;
                for(int i = 0;i<Memoria.peoples.size();i++){
                    if(i+1 == sectionNumber) {
                        Memoria.posicionFragment = sectionNumber;
                        fragment = new principalPaginas();
                        fragment.onDetach();
                    }
                }
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
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

            return Programa_Principal.PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.

            return Memoria.peoples.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
          for(int i = 0;i<Memoria.peoples.size();i++){
              if(position == i) {
                  return "SECTION " + i+1;
              }
          }
            return null;
        }
    }
}