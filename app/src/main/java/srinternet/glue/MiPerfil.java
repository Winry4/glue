package srinternet.glue;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;

import org.w3c.dom.Text;

import java.io.File;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MiPerfil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MiPerfil extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean ini;
    private int edadMin;
    private int edadMax;
    private String modo;
    private String generoBus;

    private OnFragmentInteractionListener mListener;

    public MiPerfil() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MiPerfil.
     */
    // TODO: Rename and change types and number of parameters
    public static MiPerfil newInstance(String param1, String param2) {
        MiPerfil fragment = new MiPerfil();
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
        edadMin=Memoria.EdadMin;
        edadMax=Memoria.EdadMax;
        modo=Memoria.Modo+"";
        generoBus=Memoria.GenderFind;

    }


    public long getDirSize(File dir){
        long size = 0;
        for (File file : dir.listFiles()) {
            if (file != null && file.isDirectory()) {
                size += getDirSize(file);
            } else if (file != null && file.isFile()) {
                size += file.length();
            }
        }
        return size;
    }


    public static String readableFileSize(long size) {
        if (size <= 0) return "0 Bytes";
        final String[] units = new String[]{"Bytes", "kB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final TextView nombre,edad, ciudad,tvMin,tvMax, cache;
        final Button single,gluer,mujeres,hombres;
        final CrystalRangeSeekbar rangeSeekbar;
        ini=true;


        View view = inflater.inflate(R.layout.fragment_mi_perfil, container, false);
        View acView = inflater.inflate(R.layout.activity_perfil2, container,false);
        cache = (TextView) view.findViewById(R.id.textView21) ;
        File cacheDir = getContext().getCacheDir();
        long size = getDirSize(cacheDir);
        long size2 = getDirSize(getContext().getExternalCacheDir());
        long size3 = getDirSize(getContext().getFilesDir());

        cache.setText(Idioma.MPe_B08.substring(0,Idioma.MPe_B08.indexOf('(')) + "("+ readableFileSize(size+size2+size3) + ")");

        TextView i= (TextView) view.findViewById(R.id.textView22);
        i.setText(Idioma.MPe_T01);
        TextView i1= (TextView) view.findViewById(R.id.textView23);
        TextView i11= (TextView) view.findViewById(R.id.textView24);
        TextView i111= (TextView) view.findViewById(R.id.textView25);
        i1.setText(Idioma.MPe_T02);
        i11.setText(Idioma.MPe_T03);
        i11.setText(Idioma.MPe_T04);

        TextView d= (TextView) view.findViewById(R.id.tx_queBuscaas);
        d.setText(Idioma.RSA_T01);

        TextView d1= (TextView) view.findViewById(R.id.tx_queBuscas);
        d1.setText(Idioma.MPe_T06);

        TextView d11= (TextView) view.findViewById(R.id.tx_queEdadBuscaas);
        d11.setText(Idioma.MPe_T07);

        TextView i1111= (TextView) view.findViewById(R.id.textView42);
        i1111.setText(Idioma.MPe_B07);

        nombre = (TextView)view.findViewById(R.id.tx_nombre);
        nombre.setText(Memoria.PrimNomFB);

        edad = (TextView)view.findViewById(R.id.tx_edad);
        String edad_s = String.valueOf(Memoria.EdadUser);
        edad.setText(edad_s);

        ciudad = (TextView)view.findViewById(R.id.tx_ciudad);
        ciudad.setText(Memoria.CityFB);

        single= (Button)view.findViewById(R.id.btn_single);
        gluer= (Button)view.findViewById(R.id.btn_gluer);
        switch (Memoria.Modo){
            case 1: single.setBackgroundColor(Color.parseColor("#46AAA6"));
                break;
            case 2: gluer.setBackgroundColor(Color.parseColor("#46AAA6"));
                break;
        }
        mujeres = (Button)view.findViewById(R.id.btn_mujeres);
        hombres = (Button)view.findViewById(R.id.btn_hombres);

        mujeres.setText(Idioma.MPe_B06);
        hombres.setText(Idioma.MPe_B05);

        String que_buscas = Memoria.GenderFind;
        if(que_buscas.equals("female")){
            mujeres.setBackgroundColor(Color.parseColor("#46AAA6"));
        }else if (que_buscas.equals("male")){
            hombres.setBackgroundColor(Color.parseColor("#46AAA6"));
        }



// get min and max text view
        tvMin = (TextView) view.findViewById(R.id.minValue);
        tvMax = (TextView) view.findViewById(R.id.maxValue);
        tvMin.setText(Memoria.EdadMin+"");
        tvMax.setText(Memoria.EdadMax+"");
        rangeSeekbar = (CrystalRangeSeekbar) view.findViewById(R.id.rangeSeekbar3);
        rangeSeekbar.setMinStartValue(Memoria.EdadMin).apply();
        rangeSeekbar.setMaxStartValue(Memoria.EdadMax).apply();
// set listener


        //Listener del perfil
        single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView h = (TextView) getActivity().findViewById(R.id.textView49);
                h.setVisibility(View.VISIBLE);
                modo="1";
                Memoria.cambiar(edadMin,edadMax,modo,generoBus);
                //cambiar colores
                gluer.setBackgroundColor(Color.parseColor("#F1EEE0"));
                single.setBackgroundColor(Color.parseColor("#46AAA6"));
            }
        });
        gluer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView h = (TextView) getActivity().findViewById(R.id.textView49);
                h.setVisibility(View.VISIBLE);
                modo="2";
                Memoria.cambiar(edadMin,edadMax,modo,generoBus);
                gluer.setBackgroundColor(Color.parseColor("#46AAA6"));
                single.setBackgroundColor(Color.parseColor("#F1EEE0"));
            }
        });

        mujeres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView h = (TextView) getActivity().findViewById(R.id.textView49);
                h.setVisibility(View.VISIBLE);
                generoBus="female";
                Memoria.cambiar(edadMin,edadMax,modo,generoBus);
                mujeres.setBackgroundColor(Color.parseColor("#46AAA6"));
                hombres.setBackgroundColor(Color.parseColor("#F1EEE0"));
            }
        });

        hombres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView h = (TextView) getActivity().findViewById(R.id.textView49);
                h.setVisibility(View.VISIBLE);
                generoBus="male";
                Memoria.cambiar(edadMin,edadMax,modo,generoBus);
                hombres.setBackgroundColor(Color.parseColor("#46AAA6"));
                mujeres.setBackgroundColor(Color.parseColor("#F1EEE0"));
            }
        });

        rangeSeekbar.setMinStartValue(Memoria.EdadMin);
        rangeSeekbar.setMaxStartValue(Memoria.EdadMax);
        System.out.println("CAMBIADO");
        //aqui cambiar movidas

        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {

                if(ini){

                    rangeSeekbar.setMinStartValue(Memoria.EdadMin);
                    rangeSeekbar.setMaxStartValue(Memoria.EdadMax);
                    //aqui cambiar movidas

                    ini=false;
                }else{
                    edadMin=minValue.intValue();
                    tvMin.setText(String.valueOf(minValue));
                    edadMax=maxValue.intValue();
                    tvMax.setText(String.valueOf(maxValue));
                    TextView h = (TextView) getActivity().findViewById(R.id.textView49);
                    h.setVisibility(View.VISIBLE);
                    Memoria.cambiar(edadMin,edadMax,modo,generoBus);
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
