package srinternet.glue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentFotos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentFotos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentFotos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View view;
    private Bitmap imagen;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int posicion,nfotos;
    private OnFragmentInteractionListener mListener;

    public FragmentFotos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentFotos.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentFotos newInstance(String param1, String param2) {
        FragmentFotos fragment = new FragmentFotos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int fotos;
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_fragment_fotos, container, false);


        ImageView punto1,punto2,punto3,punto4;

        punto1 = (ImageView)getActivity().findViewById(R.id.punto_foto_1);
        punto2 = (ImageView)getActivity().findViewById(R.id.punto_foto_2);
        punto3 = (ImageView)getActivity().findViewById(R.id.punto_foto_3);
        punto4 = (ImageView)getActivity().findViewById(R.id.punto_foto_4);

        for(int i =0;i<Memoria.peoples.size();i++){
            if(i+1==Memoria.posicionFragment) nfotos=Memoria.peoples.get(i).getNumFotos();
        }

        for (int i = 0;i<Memoria.PerfilFOtNomUser.length;i++){
            if(i+1==posicion) {
                final double nalto,nancho;
                nalto = Memoria.alto*0.5;
                nancho = Memoria.ancho*0.83;
                final ImageView foto= (ImageView)  view.findViewById(R.id.fotos_fragment_principal);
                /*
                */
                if(imagen==null){
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            cogerfoto();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    foto.setImageBitmap(redimensionarImagenMaximo(imagen,nancho,nalto));
                                }
                            });
                        }
                    });
                }else{
                    foto.setImageBitmap(redimensionarImagenMaximo(imagen,nancho,nalto)
                    );
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
    private void cogerfoto(){
        try {
            String filename = Memoria.PerfilFOtNomUser[posicion-1];
            File f = new File(getActivity().getBaseContext().getFilesDir(), filename);
            if(f.exists()){
                imagen = BitmapFactory.decodeFile(f.getAbsolutePath());
            }else{
                try {
                    String filenam=Memoria.PerfilFOtNomUser[posicion-1];
                    String response = "";
                    HttpURLConnection conn = (HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    PrintWriter wr = new PrintWriter(
                            conn.getOutputStream());
                    String params = "funcion=f00&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension + "&idFbPers=" + Memoria.idFaceFotos + "&fotograf=" + filenam;
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
                    imagen = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    FileOutputStream outputStream = getActivity().openFileOutput(filenam, Programa_Principal.MODE_PRIVATE);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagen.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageData = baos.toByteArray();
                    outputStream.write(imageData);
                    outputStream.close();
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        }catch(Exception e){
            System.out.println(e.toString());
        }
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
        posicion=Memoria.posicionFragmentFot;
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
