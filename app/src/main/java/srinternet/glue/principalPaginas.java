package srinternet.glue;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class principalPaginas extends Fragment implements View.OnClickListener {
    Programa_Principal programa_principal  = new Programa_Principal();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View view;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int posicion;
    private PrincipalLista.OnFragmentInteractionListener mListener;

    public principalPaginas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrincipalLista.
     */
    // TODO: Rename and change types and number of parameters
    public static PrincipalLista newInstance(String param1, String param2) {
        PrincipalLista fragment = new PrincipalLista();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setRetainInstance(true);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_principal_paginas, container, false);
        final ImageView list = (ImageView) getActivity().findViewById(R.id.img_elegido_lista);
        final ImageView list1 = (ImageView) getActivity().findViewById(R.id.img_elegido_tabbed);
        final ImageView btn_fotos,btn_amigos,btn_favorito,btn_ocultar;
        final TextView tx_favorito,tx_amigos,tx_fotos,tx_ocultar;
        final double nancho,nalto;
        FrameLayout frame;

        frame = (FrameLayout)view.findViewById(R.id.frame_foto);

        nalto = Memoria.alto*0.5;
        nancho = Memoria.ancho*0.83;
        frame.getLayoutParams().height=(int)nancho;
        frame.getLayoutParams().width=(int)nalto;

        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Memoria.clickVerFOtos){
                    int pagAct=Memoria.mViewPager.getCurrentItem();
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

                                for (int i = 0; i < json.getJSONArray("fotos").length(); i++) {
                                    String filename = json.getJSONArray("fotos").getString(i);
                                    if (!filename.equals("")) {
                                        Memoria.PerfilFOtNomUser[i] = filename;
                                    }
                                }
                                if(Memoria.clickVerFOtos){
                                    Intent intent = new Intent(getActivity().getBaseContext(), fotosPrincipal.class);
                                    startActivity(intent);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });

        final ImageView single,img_gluer;
        btn_fotos =(ImageView)getActivity().findViewById(R.id.btn_fotos_principal);
        btn_amigos = (ImageView)getActivity().findViewById(R.id.imageView26);
        btn_favorito =(ImageView)getActivity().findViewById(R.id.btn_favorito_principal);
        btn_ocultar =(ImageView)getActivity().findViewById(R.id.btn_ocultar_principal);

        tx_fotos =(TextView) getActivity().findViewById(R.id.tx_fotos_princi);
        tx_amigos = (TextView) getActivity().findViewById(R.id.tx_amigoscomun_princi);
        tx_favorito =(TextView) getActivity().findViewById(R.id.tx_favorito);
        tx_ocultar =(TextView) getActivity().findViewById(R.id.textView53);

        single = (ImageButton) getActivity().findViewById(R.id.img_single);
        img_gluer = (ImageButton)getActivity().findViewById(R.id.img_gluer);



        if(Memoria.Modo!=1){
            getFragmentManager().beginTransaction().replace(R.id.fragment_principal, new Fragment_emparejar()).addToBackStack(null).commit();
            btn_amigos.setVisibility(View.INVISIBLE);
            btn_favorito.setVisibility(View.INVISIBLE);
            btn_fotos.setVisibility(View.INVISIBLE);
            btn_ocultar.setVisibility(View.INVISIBLE);
            tx_favorito.setVisibility(View.INVISIBLE);
            tx_fotos.setVisibility(View.INVISIBLE);
            tx_ocultar.setVisibility(View.INVISIBLE);
            tx_amigos.setVisibility(View.INVISIBLE);

            ViewPager you= (ViewPager) getActivity().findViewById(R.id.container);
            ImageView uno,dos,tres;
            uno=(ImageView) getActivity().findViewById(R.id.btn_corazon_principal) ;
            dos=(ImageView) getActivity().findViewById(R.id.imageView26) ;
            tres=(ImageView) getActivity().findViewById(R.id.btn_fotos_principal) ;
            uno.setVisibility(View.INVISIBLE);
            dos.setVisibility(View.INVISIBLE);
            tres.setVisibility(View.INVISIBLE);
            you.setVisibility(View.INVISIBLE);
            uno.clearAnimation();
            single.setVisibility(View.INVISIBLE);
            img_gluer.setVisibility(View.INVISIBLE);

            list1.setVisibility(View.INVISIBLE);
            list.setVisibility(View.INVISIBLE);
        }

        img_gluer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_amigos.setVisibility(View.INVISIBLE);
                btn_favorito.setVisibility(View.INVISIBLE);
                btn_fotos.setVisibility(View.INVISIBLE);
                btn_ocultar.setVisibility(View.INVISIBLE);
                tx_favorito.setVisibility(View.INVISIBLE);
                tx_fotos.setVisibility(View.INVISIBLE);
                tx_ocultar.setVisibility(View.INVISIBLE);
                tx_amigos.setVisibility(View.INVISIBLE);

                ViewPager you= (ViewPager) getActivity().findViewById(R.id.container);
                ImageView uno,dos,tres;
                uno=(ImageView) getActivity().findViewById(R.id.btn_corazon_principal) ;
                dos=(ImageView) getActivity().findViewById(R.id.imageView26) ;
                tres=(ImageView) getActivity().findViewById(R.id.btn_fotos_principal) ;
                uno.setVisibility(View.INVISIBLE);
                dos.setVisibility(View.INVISIBLE);
                tres.setVisibility(View.INVISIBLE);
                you.setVisibility(View.INVISIBLE);
                uno.clearAnimation();
                single.setVisibility(View.INVISIBLE);
                img_gluer.setVisibility(View.INVISIBLE);

                list1.setVisibility(View.INVISIBLE);
                list.setVisibility(View.INVISIBLE);
                getFragmentManager().beginTransaction().replace(R.id.fragment_principal, new Fragment_emparejar()).addToBackStack(null).commit();
            }
        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_amigos.setVisibility(View.INVISIBLE);
                btn_favorito.setVisibility(View.INVISIBLE);
                btn_fotos.setVisibility(View.INVISIBLE);
                btn_ocultar.setVisibility(View.INVISIBLE);
                tx_favorito.setVisibility(View.INVISIBLE);
                tx_fotos.setVisibility(View.INVISIBLE);
                tx_ocultar.setVisibility(View.INVISIBLE);
                tx_amigos.setVisibility(View.INVISIBLE);

                ViewPager you= (ViewPager) getActivity().findViewById(R.id.container);
                ImageView uno,dos,tres;
                uno=(ImageView) getActivity().findViewById(R.id.btn_corazon_principal) ;
                dos=(ImageView) getActivity().findViewById(R.id.imageView26) ;
                tres=(ImageView) getActivity().findViewById(R.id.btn_fotos_principal) ;
                uno.clearAnimation();
                uno.setVisibility(View.INVISIBLE);
                dos.setVisibility(View.INVISIBLE);
                tres.setVisibility(View.INVISIBLE);
                you.setVisibility(View.INVISIBLE);
                list1.setImageResource(R.drawable.punto_principal_b);
                list.setImageResource(R.drawable.punto3_principal);

                getFragmentManager().beginTransaction().replace(R.id.fragment_principal, new PrincipalLista()).addToBackStack(null).commit();
            }
        });


        for (int i = 0;i<Memoria.peoples.size();i++) {
            if (i + 1 == posicion) {
                TextView amigos_comun = (TextView) view.findViewById(R.id.tx_amigos);
                amigos_comun.setText(String.valueOf(Memoria.peoples.get(i).getnAmigosComun()));
                Intent fotos = new Intent(getActivity(),FragmentFotos.class);
                fotos.putExtra("nfotos",Memoria.peoples.get(i).getNumFotos());
                TextView nfotos = (TextView) view.findViewById(R.id.tx_fotos);

                nfotos.setText(String.valueOf(Memoria.peoples.get(i).getNumFotos()));
                TextView nombre = (TextView) view.findViewById(R.id.tx_maria_pepita);
                nombre.setText(Memoria.peoples.get(i).getNombre() + " " + String.valueOf(Memoria.peoples.get(i).getEdad()));
                TextView localizacion = (TextView) view.findViewById(R.id.tx_localizacion);
                localizacion.setText(Memoria.peoples.get(i).getCity());
                final ImageView foto = (ImageView) view.findViewById(R.id.img_foto);
                foto.buildDrawingCache();

                if (Memoria.peoples.get(i).getPerfilFot() == null) {
                    final People bG = Memoria.peoples.get(i);
                    AsyncTask d = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] params) {
                            bG.cogerFoto(bG.getPerfilFotNom());
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Object o) {
                            try{
                                bG.setPerfilFot(redimensionarImagenMaximo(bG.getPerfilFot(), nancho, nalto));
                                foto.setImageBitmap(bG.getPerfilFot());
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    };
                    d.execute("");
                } else {
                    Memoria.peoples.get(i).setPerfilFot(redimensionarImagenMaximo(Memoria.peoples.get(i).getPerfilFot(), nancho, nalto));
                    foto.setImageBitmap(Memoria.peoples.get(i).getPerfilFot());
                }
            }
        }
        return view;
    }
    public Bitmap redimensionarImagenMaximo(Bitmap mBitmap, double newWidth, double newHeigth){
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        final ImageView list = (ImageView) getActivity().findViewById(R.id.img_elegido_lista);
        final ImageView list1 = (ImageView) getActivity().findViewById(R.id.img_elegido_tabbed);


        final ImageView single,img_gluer;

        single = (ImageButton) getActivity().findViewById(R.id.img_single);
        img_gluer = (ImageButton)getActivity().findViewById(R.id.img_gluer);

        if(Memoria.Modo==2){
            ViewPager you= (ViewPager) getActivity().findViewById(R.id.container);
            ImageView uno,dos,tres;
            uno=(ImageView) getActivity().findViewById(R.id.btn_corazon_principal) ;
            dos=(ImageView) getActivity().findViewById(R.id.imageView26) ;
            tres=(ImageView) getActivity().findViewById(R.id.btn_fotos_principal) ;
            uno.setVisibility(View.INVISIBLE);
            dos.setVisibility(View.INVISIBLE);
            tres.setVisibility(View.INVISIBLE);
            you.setVisibility(View.INVISIBLE);

            single.setVisibility(View.INVISIBLE);
            img_gluer.setVisibility(View.INVISIBLE);

            list1.setVisibility(View.INVISIBLE);
            list.setVisibility(View.INVISIBLE);

            getFragmentManager().beginTransaction().replace(R.id.fragment_principal, new Fragment_emparejar()).addToBackStack(null).commit();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PrincipalLista.OnFragmentInteractionListener) {
            mListener = (PrincipalLista.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        posicion=Memoria.posicionFragment;
        mListener = null;
    }

    @Override
    public void onClick(View v) {

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}




