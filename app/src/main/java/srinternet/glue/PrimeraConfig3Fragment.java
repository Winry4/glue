package srinternet.glue;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PrimeraConfig3Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PrimeraConfig3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrimeraConfig3Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PrimeraConfig3Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrimeraConfig3Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrimeraConfig3Fragment newInstance(String param1, String param2) {
        PrimeraConfig3Fragment fragment = new PrimeraConfig3Fragment();
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
        View view =  inflater.inflate(R.layout.fragment_primera_config3, container, false);
        boolean check = true;
        final Button cancelar = (Button)view.findViewById(R.id.btn_cancelar);
        final Button hecho = (Button)view.findViewById(R.id.btn_hecho);
        final Button cortar = (Button)view.findViewById(R.id.btn_cortar);

        ImageView im = (ImageView) view.findViewById(R.id.button2);
        im.setImageBitmap(Memoria.DataFotoPerfil);
            cancelar.setOnClickListener(new View.OnClickListener() {

                private boolean cup = false;
                public void onClick(View v) {
                    if (cup == false ) {
                        cancelar.setBackgroundColor(Color.parseColor("#46AAA6"));
                        cup = true;
                    }else if (cup == true){
                        cancelar.setBackgroundColor(Color.parseColor("#F1EEE5"));
                        cup = false;
                    }
                }
            });


        hecho.setOnClickListener(new View.OnClickListener(){
            private boolean enam = false;
            public void onClick(View v) {
                if(enam == false) {
                    hecho.setBackgroundColor(Color.parseColor("#46AAA6"));
                    enam = true;
                }else {
                    hecho.setBackgroundColor(Color.parseColor("#F1EEE5"));
                    enam=false;
                }
            }
        });
        cortar.setOnClickListener(new View.OnClickListener(){
            private boolean enam = false;
            public void onClick(View v) {
                if(enam == false) {
                    cortar.setBackgroundColor(Color.parseColor("#46AAA6"));
                    enam = true;
                }else {
                    cortar.setBackgroundColor(Color.parseColor("#F1EEE5"));
                    enam=false;
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
