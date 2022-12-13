package srinternet.glue;

import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by JAVIER OLIVER ASUS on 05/09/2017.
 */

public class Fragment_emparejar extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    RelativeLayout screenLayout;
    AmigosAdapter gridAdapter;
    GridView gridView;

    private int goes;
    private String id1, id3;
    private AmigosGrid a1,a2;
    private int a1i,a2i;

    public Fragment_emparejar(){

    }
    public static Fragment_emparejar newInstance(String param1, String param2) {
        Fragment_emparejar fragment = new Fragment_emparejar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("ONNNN CREATEEE");
        goes=0;
        Memoria.vuelta=0;
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
    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gluer, container, false);
        final ImageView list = (ImageView) getActivity().findViewById(R.id.img_elegido_lista);
        final ImageView list1 = (ImageView) getActivity().findViewById(R.id.img_elegido_tabbed);
        final ImageView single,img_gluer;
        final ImageView im1= (ImageView) view.findViewById(R.id.imageView45);
        final ImageView im2= (ImageView) view.findViewById(R.id.imageView31);
        final TextView a=(TextView)view.findViewById(R.id.textView11);
        final TextView aa=(TextView)view.findViewById(R.id.textView12);
        TextView aaa=(TextView)view.findViewById(R.id.textView30);
        TextView aaaa=(TextView)view.findViewById(R.id.textView46);
        TextView aaaaa=(TextView)view.findViewById(R.id.textView48);
        a.setText(Idioma.Glu_T01);
        aa.setText(Idioma.Glu_T02);
        aaa.setText(Idioma.Glu_T03);
        aaaa.setText(Idioma.Glu_T04);
        aaaaa.setText(Idioma.Glu_T05);

        final Button bb= (Button) view.findViewById(R.id.button5);
        bb.setText(Idioma.Glu_B01);
        single = (ImageButton) getActivity().findViewById(R.id.img_single);
        img_gluer = (ImageButton)getActivity().findViewById(R.id.img_gluer);

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


        single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Memoria.Modo==1){
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
                    uno.setVisibility(View.VISIBLE);
                    dos.setVisibility(View.VISIBLE);
                    tres.setVisibility(View.VISIBLE);
                    you.setVisibility(View.VISIBLE);
                    list1.setVisibility(View.VISIBLE);
                    list.setVisibility(View.VISIBLE);
                    single.setVisibility(View.INVISIBLE);
                    img_gluer.setVisibility(View.INVISIBLE);

                    list1.setImageResource(R.drawable.punto_principal);
                    list.setImageResource(R.drawable.puntos3_principal_b);

                    getFragmentManager().beginTransaction().remove(Fragment_emparejar.this).commit();
                }else{
                    Toast.makeText(getContext(),"No puedes acceder siendo Gluer",Toast.LENGTH_LONG).show();
                }

            }
        });

        gridView = (GridView)view.findViewById(R.id.Amigos);
        gridAdapter = new AmigosAdapter(view.getContext(), Memoria.Amigos, getActivity());
        gridView.setAdapter(gridAdapter);
        gridAdapter.notifyDataSetChanged();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(goes==0){
                    im1.setImageDrawable(redondear(gridAdapter.redimensionarImagenMaximo(Memoria.Amigos.get(i).getImageDataOri(),150,150)));
                    a1=Memoria.Amigos.get(i);
                    a1i=i;
                    a.setText(a1.getNombre());
                    Memoria.Amigos.remove(i);
                    goes++;
                }else if(goes==1){

                    im2.setImageDrawable(redondear(gridAdapter.redimensionarImagenMaximo(Memoria.Amigos.get(i).getImageDataOri(),150,150)));
                    a2=Memoria.Amigos.get(i);
                    a2i=i;
                    aa.setText(a2.getNombre());
                    Memoria.Amigos.remove(i);
                    goes++;
                    bb.setVisibility(View.VISIBLE);
                }
                gridAdapter = new AmigosAdapter(view.getContext(), Memoria.Amigos, getActivity());
                gridView.setAdapter(gridAdapter);
                gridAdapter.notifyDataSetChanged();
            }
        });

        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ConfirmaPareja.class);
                Memoria.pareja1=a1;
                Memoria.pareja2=a2;
                Memoria.posiPareja1=a1i;
                Memoria.posiPareja2=a2i;
                startActivity(intent);

            }
        });

        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(goes==1){
                    im1.setImageResource(R.drawable.cambiar_corazon);
                    goes--;
                    Memoria.Amigos.add(a1i,a1);
                    gridAdapter = new AmigosAdapter(view.getContext(), Memoria.Amigos, getActivity());
                    gridView.setAdapter(gridAdapter);
                    gridAdapter.notifyDataSetChanged();
                    a.setText(Idioma.Glu_T01);

                }else{
                    goes=0;
                    bb.setVisibility(View.INVISIBLE);
                    im2.setImageResource(R.drawable.cambiar_corazon);
                    im1.setImageResource(R.drawable.cambiar_corazon);
                    a.setText(Idioma.Glu_T01);
                    aa.setText(Idioma.Glu_T02);
                    Memoria.Amigos.add(a2i,a2);
                    Memoria.Amigos.add(a1i,a1);
                    gridAdapter = new AmigosAdapter(view.getContext(), Memoria.Amigos, getActivity());
                    gridView.setAdapter(gridAdapter);
                    gridAdapter.notifyDataSetChanged();
                }
            }
        });

        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(goes==2){
                    aa.setText(Idioma.Glu_T02);
                    bb.setVisibility(View.INVISIBLE);
                    im2.setImageResource(R.drawable.cambiar_corazon);
                    goes--;
                    Memoria.Amigos.add(a2i,a2);
                    gridAdapter = new AmigosAdapter(view.getContext(), Memoria.Amigos, getActivity());
                    gridView.setAdapter(gridAdapter);
                    gridAdapter.notifyDataSetChanged();
                }
            }
        });

        return view;


    }

    public RoundedBitmapDrawable redondear(Bitmap originalBitmap){

        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getContext().getResources(), originalBitmap);
        roundedDrawable.setCornerRadius(originalBitmap.getHeight());
        return roundedDrawable;
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
