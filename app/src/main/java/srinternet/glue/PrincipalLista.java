package srinternet.glue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.Timer;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PrincipalLista#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrincipalLista extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    RelativeLayout screenLayout;
    GenteAdapter gridAdapter;
    GridView gridView;

    private OnFragmentInteractionListener mListener;

    public PrincipalLista() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment principalLista.
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


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_principal_lista, container, false);

        final ImageView list = (ImageView) getActivity().findViewById(R.id.img_elegido_lista);
        final ImageView list1 = (ImageView) getActivity().findViewById(R.id.img_elegido_tabbed);
        final ImageView btn_fotos,btn_amigos,btn_favorito,btn_ocultar;
        final TextView tx_favorito,tx_amigos,tx_fotos,tx_ocultar;
        btn_fotos =(ImageView)getActivity().findViewById(R.id.btn_fotos_principal);
        btn_amigos = (ImageView)getActivity().findViewById(R.id.imageView26);
        btn_favorito =(ImageView)getActivity().findViewById(R.id.btn_favorito_principal);
        btn_ocultar =(ImageView)getActivity().findViewById(R.id.btn_ocultar_principal);

        tx_fotos =(TextView) getActivity().findViewById(R.id.tx_fotos_princi);
        tx_amigos = (TextView) getActivity().findViewById(R.id.tx_amigoscomun_princi);
        tx_favorito =(TextView) getActivity().findViewById(R.id.tx_favorito);
        tx_ocultar =(TextView) getActivity().findViewById(R.id.textView53);

        list1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_amigos.setVisibility(View.VISIBLE);
                btn_favorito.setVisibility(View.VISIBLE);
                btn_fotos.setVisibility(View.VISIBLE);
                btn_ocultar.setVisibility(View.VISIBLE);
                tx_favorito.setVisibility(View.VISIBLE);
                tx_fotos.setVisibility(View.VISIBLE);
                tx_ocultar.setVisibility(View.VISIBLE);
                tx_amigos.setVisibility(View.VISIBLE);

                ViewPager you= (ViewPager) getActivity().findViewById(R.id.container);
                ImageView uno,dos,tres;
                uno=(ImageView) getActivity().findViewById(R.id.btn_corazon_principal) ;
                dos=(ImageView) getActivity().findViewById(R.id.imageView26) ;
                tres=(ImageView) getActivity().findViewById(R.id.btn_fotos_principal) ;
                uno.clearAnimation();
                uno.setVisibility(View.VISIBLE);
                dos.setVisibility(View.VISIBLE);
                tres.setVisibility(View.VISIBLE);
                you.setVisibility(View.VISIBLE);
                list1.setImageResource(R.drawable.punto_principal);
                list.setImageResource(R.drawable.puntos3_principal_b);

                getFragmentManager().beginTransaction().remove(PrincipalLista.this).commit();
            }
        });


        screenLayout = (RelativeLayout) getActivity().findViewById(R.id.screenLayout);
        gridView = (GridView)view.findViewById(R.id.gridLista);
        gridAdapter = new GenteAdapter(view.getContext(), Memoria.peoplesT);
        gridView.setAdapter(gridAdapter);
        gridAdapter.notifyDataSetChanged();
        final boolean [] irvolver= new boolean[Memoria.peoplesT.size()];
        for(int o=0;o<irvolver.length;o++){
            irvolver[o]=true;
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {

                final ImageView imagen = (ImageView) view.findViewById(R.id.foto_lista);
                imagen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        btn_amigos.setVisibility(View.VISIBLE);
                        btn_favorito.setVisibility(View.VISIBLE);
                        btn_fotos.setVisibility(View.VISIBLE);
                        btn_ocultar.setVisibility(View.VISIBLE);
                        tx_favorito.setVisibility(View.VISIBLE);
                        tx_fotos.setVisibility(View.VISIBLE);
                        tx_ocultar.setVisibility(View.VISIBLE);
                        tx_amigos.setVisibility(View.VISIBLE);

                        ViewPager you= (ViewPager) getActivity().findViewById(R.id.container);
                        ImageView uno,dos,tres;
                        uno=(ImageView) getActivity().findViewById(R.id.btn_corazon_principal) ;
                        dos=(ImageView) getActivity().findViewById(R.id.imageView26) ;
                        tres=(ImageView) getActivity().findViewById(R.id.btn_fotos_principal) ;
                        uno.clearAnimation();
                        uno.setVisibility(View.VISIBLE);
                        dos.setVisibility(View.VISIBLE);
                        tres.setVisibility(View.VISIBLE);
                        you.setVisibility(View.VISIBLE);
                        list1.setImageResource(R.drawable.punto_principal);
                        list.setImageResource(R.drawable.puntos3_principal_b);

                        getFragmentManager().beginTransaction().remove(PrincipalLista.this).commit();

                        People act = Memoria.peoplesT.get(position);
                        for(int i=0; i< Memoria.peoples.size();i++){
                            if(Memoria.peoples.get(i).getIdFace().equals(act.getIdFace())){
                                Memoria.mViewPager.setCurrentItem(i);
                            }

                        }

                    }
                });

                ImageView el = (ImageView) view.findViewById(R.id.eliminar2);
                el.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    People u = Memoria.peoplesT.get(position);

                                    HttpURLConnection conn = (HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                                    conn.setRequestMethod("POST");
                                    conn.setDoOutput(true);
                                    conn.setDoInput(true);
                                    PrintWriter wr = new PrintWriter(
                                            conn.getOutputStream());
                                    String param = "funcion=f06&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension + "&idFbPers=" + u.getIdFace();
                                    wr.print(param);
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
                                            People p=new People(Memoria.genteTabla.getJSONObject(i), getActivity());
                                            Memoria.peoplesT.add(p);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    for (int i=0;i<Memoria.gente.length();i++){
                                        try {
                                            People p=new People(Memoria.gente.getJSONObject(i), getActivity());
                                            Memoria.peoples.add(p);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            for(int o=0;o<irvolver.length;o++){
                                                irvolver[o]=true;
                                            }

                                            gridAdapter = new GenteAdapter(view.getContext(), Memoria.peoplesT);
                                            gridView.setAdapter(gridAdapter);
                                            gridAdapter.notifyDataSetChanged();
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


                ImageView e2 = (ImageView) view.findViewById(R.id.guardar2);
                e2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    People u = Memoria.peoplesT.get(position);

                                    HttpURLConnection conn = (HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                                    conn.setRequestMethod("POST");
                                    conn.setDoOutput(true);
                                    conn.setDoInput(true);
                                    PrintWriter wr = new PrintWriter(
                                            conn.getOutputStream());
                                    String param = "funcion=f07&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension + "&idFbPers=" + u.getIdFace();
                                    wr.print(param);
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
                                            People p=new People(Memoria.genteTabla.getJSONObject(i), getActivity());
                                            Memoria.peoplesT.add(p);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    for (int i=0;i<Memoria.gente.length();i++){
                                        try {
                                            People p=new People(Memoria.gente.getJSONObject(i), getActivity());
                                            Memoria.peoples.add(p);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            for(int o=0;o<irvolver.length;o++){
                                                irvolver[o]=true;
                                            }
                                            gridAdapter = new GenteAdapter(view.getContext(), Memoria.peoplesT);
                                            gridView.setAdapter(gridAdapter);
                                            gridAdapter.notifyDataSetChanged();
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

                if (irvolver[position]){
                    Animation slide = AnimationUtils.loadAnimation(getActivity(), R.anim.translate3);
                    RelativeLayout l1= (RelativeLayout) view.findViewById(R.id.relativeF);
                    RelativeLayout l2,l3,l4,l5;
                    ImageView l6,barra;
                    barra=(ImageView) view.findViewById(R.id.imageView55);
                    l6= (ImageView) view.findViewById(R.id.imageView3);
                    l2= (RelativeLayout) view.findViewById(R.id.primeroL);
                    l3= (RelativeLayout) view.findViewById(R.id.relativeLayout7);
                    l4= (RelativeLayout) view.findViewById(R.id.segundoL);
                    l5= (RelativeLayout) view.findViewById(R.id.tercerL);


                    imagen.setClickable(false);

                    l1.startAnimation(slide);
                    l2.startAnimation(slide);
                    l3.startAnimation(slide);
                    l4.startAnimation(slide);
                    l5.startAnimation(slide);
                    l6.startAnimation(slide);
                    barra.startAnimation(slide);

                    el.setVisibility(View.VISIBLE);
                    e2.setVisibility(View.VISIBLE);
                    irvolver[position]=false;
                }else{
                    Animation slide = AnimationUtils.loadAnimation(getActivity(), R.anim.traslate4);
                    RelativeLayout l1= (RelativeLayout) view.findViewById(R.id.relativeF);
                    RelativeLayout l2,l3,l4,l5;
                    l2= (RelativeLayout) view.findViewById(R.id.primeroL);
                    l3= (RelativeLayout) view.findViewById(R.id.relativeLayout7);
                    l4= (RelativeLayout) view.findViewById(R.id.segundoL);
                    l5= (RelativeLayout) view.findViewById(R.id.tercerL);
                    ImageView l6,barra;
                    barra=(ImageView) view.findViewById(R.id.imageView55);
                    l6= (ImageView) view.findViewById(R.id.imageView3);

                    imagen.setClickable(true);

                    l1.startAnimation(slide);
                    l2.startAnimation(slide);
                    l3.startAnimation(slide);
                    l4.startAnimation(slide);
                    l5.startAnimation(slide);
                    l6.startAnimation(slide);
                    barra.startAnimation(slide);

                    el.setVisibility(View.INVISIBLE);
                    e2.setVisibility(View.INVISIBLE);
                    irvolver[position]=true;
                }

            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
