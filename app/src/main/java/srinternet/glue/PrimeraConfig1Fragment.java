package srinternet.glue;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class PrimeraConfig1Fragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button enamorado,cupido;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PrimeraConfig1Fragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PrimeraConfig1Fragment newInstance(String param1, String param2) {
        PrimeraConfig1Fragment fragment = new PrimeraConfig1Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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

        boolean check = true;
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_primera_config1, container, false);
        enamorado = (Button) view.findViewById(R.id.btn_enam);
        cupido = (Button) view.findViewById(R.id.btn_cupido);
        TextView u = (TextView) view.findViewById(R.id.textView3) ;
        u.setText(Idioma.RSA_T01);
            cupido.setOnClickListener(new View.OnClickListener() {

                private boolean cup = false;

                public void onClick(View v) {
                    if (!cup) {
                        cupido.setBackgroundColor(Color.parseColor("#46AAA6"));
                        enamorado.setBackgroundColor(Color.parseColor("#F1EEE5"));
                        Memoria.Modo=2;
                        cup = true;
                    } else if (cup) {
                        cupido.setBackgroundColor(Color.parseColor("#F1EEE5"));
                        Memoria.Modo=0;
                        cup = false;
                    }
                }
            });


        enamorado.setOnClickListener(new View.OnClickListener() {
            private boolean enam = false;

            public void onClick(View v) {
                if (enam == false) {
                    enamorado.setBackgroundColor(Color.parseColor("#46AAA6"));
                    cupido.setBackgroundColor(Color.parseColor("#F1EEE5"));
                    Memoria.Modo=1;
                    enam = true;
                } else if (enam) {
                    enamorado.setBackgroundColor(Color.parseColor("#F1EEE5"));
                    Memoria.Modo=0;
                    enam = false;
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
