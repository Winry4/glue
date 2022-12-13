package srinternet.glue;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Inicio3Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Inicio3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Inicio3Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Dialog customDialog = null;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    CallbackManager callbackManager;
    Button loginfacebook;
    boolean boton;
    Activity ac;
    private OnFragmentInteractionListener mListener;

    public Inicio3Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Inicio3Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Inicio3Fragment newInstance(String param1, String param2) {
        Inicio3Fragment fragment = new Inicio3Fragment();
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
        boton=true;
        ac=getActivity();
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();




        View v=inflater.inflate(R.layout.fragment_inicio3, container, false);



        TextView u2 = (TextView) v.findViewById(R.id.textView4);
        TextView u3 = (TextView) v.findViewById(R.id.textView5);
        TextView u4 = (TextView) v.findViewById(R.id.textView999);



        u2.setText(Idioma.LSC_T01);
        u3.setText(Idioma.LSC_M01);
        u4.setText(Idioma.Log_T01);



        loginfacebook = (Button) v.findViewById(R.id.button3);
        loginfacebook.setText(Idioma.Log_B01);

        loginfacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        loginWithFace();
            }
        });



        return v;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    private void loginWithFace(){
        LoginManager.getInstance().setLoginBehavior(LoginBehavior.WEB_VIEW_ONLY);
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends", "email", "user_birthday","user_photos","user_location"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent intent = new Intent(ac, MainActivity.class);
                startActivity(intent);
                ac.finish();
            }

            @Override
            public void onCancel() {


                customDialog = new Dialog(getActivity(),R.style.Theme_Dialog_Translucent);
                //deshabilitamos el título por defecto
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                //obligamos al usuario a pulsar los botones para cerrarlo
                customDialog.setCancelable(false);
                //establecemos el contenido de nuestro dialog
                customDialog.setContentView(R.layout.alert_conexion);

                TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
                titulo.setText("Logeo en Facebook Cancelado");

                TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
                contenido.setText("Se ha cancelado el inicio en facebook");

                Button b = (Button) customDialog.findViewById(R.id.button9);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customDialog.dismiss();
                    }
                });

                customDialog.show();
            }

            @Override
            public void onError(FacebookException error) {

                customDialog = new Dialog(getActivity(),R.style.Theme_Dialog_Translucent);
                //deshabilitamos el título por defecto
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                //obligamos al usuario a pulsar los botones para cerrarlo
                customDialog.setCancelable(false);
                //establecemos el contenido de nuestro dialog
                customDialog.setContentView(R.layout.alert_conexion);

                TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
                titulo.setText("Error al Logear Facebook");

                TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
                contenido.setText(error.getMessage());


                Button b = (Button) customDialog.findViewById(R.id.button9);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customDialog.dismiss();
                    }
                });

                customDialog.show();
            }
        });

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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
