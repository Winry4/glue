package srinternet.glue;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.*;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.util.Arrays;

import java.util.HashMap;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Scanner;
import android.graphics.Bitmap;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PrimeraConfig4Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PrimeraConfig4Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrimeraConfig4Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PrimeraConfig4Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrimeraConfig4Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrimeraConfig4Fragment newInstance(String param1, String param2) {
        PrimeraConfig4Fragment fragment = new PrimeraConfig4Fragment();
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

    Button empezamos;
    CrystalRangeSeekbar rangeSeekbar;
    TextView tvMin;
    TextView tvMax;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_primera_config4, container, false);


        TextView hgs= (TextView) view.findViewById(R.id.textView10);
        hgs.setText(Idioma.MPe_T07);
// get seekbar from view
        rangeSeekbar = (CrystalRangeSeekbar) view.findViewById(R.id.rangeSeekbar3);

// get min and max text view
        tvMin = (TextView) view.findViewById(R.id.minValue);
        tvMax = (TextView) view.findViewById(R.id.maxValue);

// set listener
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                Memoria.EdadMin=minValue.intValue();
                tvMin.setText(String.valueOf(minValue));
                Memoria.EdadMax=maxValue.intValue();
                tvMax.setText(String.valueOf(maxValue));
            }
        });



        empezamos = (Button)view.findViewById(R.id.button4);
        empezamos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        String bondu="------VohpleBoundary4QuqLuM1cE5lMwCy";
                        //Pasamos la foto a data
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        Memoria.DataFotoPerfil.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] imageData = baos.toByteArray();

                        //Creamso a conection y fijamos los aprametros
                        try{
                            HttpURLConnection conn =(HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("Connection", "Keep-Alive");
                            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + bondu);
                            conn.setDoOutput(true);
                            conn.setDoInput(true);
                            DataOutputStream wr = new DataOutputStream(
                                    conn.getOutputStream());
                            //creamos la cadena y la insertamos
                            String params="";
                            HashMap<String, String> h = new HashMap<String, String>();
                            h.put("funcion","f04");
                            h.put("idFacebo",Memoria.UserFBId);
                            h.put("idSesion",Memoria.idSension);
                            h.put("modoEleg",""+Memoria.Modo);
                            h.put("generoBu",Memoria.GenderFind);
                            h.put("edadMini",""+Memoria.EdadMin);
                            h.put("edadMaxi",""+Memoria.EdadMax);
                            h.put("fotograf",""+Memoria.nombrefotoPerfil);

                            for (HashMap.Entry<String, String> entry : h.entrySet()){
                                params+=("--"+bondu+"\r\n");
                                params+=("Content-Disposition:form-data; name=\""+entry.getKey()+"\"\r\n\r\n");
                                params+=(entry.getValue()+"\r\n");
                            }

                            params+="\r\n--"+bondu+"\r\n";
                            params+="Content-Disposition:form-data; name=\"uploadedfile\"; filename=\""+Memoria.nombrefotoPerfil+".jpg\"\r\n";
                            params+="Content-Type:application/octet-stream\r\n\r\n";
                            wr.writeBytes(params);
                            wr.write(imageData);
                            wr.writeBytes("\r\n--"+bondu+"--\r\n");
                            //La insertamos en el stream

                            wr.close();
                            conn.connect();

                            String response="";
                            Scanner inStream = new Scanner(conn.getInputStream());
                            while(inStream.hasNextLine())
                                response+=(inStream.nextLine());

                            conn.disconnect();

                            JSONObject json= new JSONObject(response);

                            Memoria.setLogin(json, getActivity());

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

                        }catch(Exception e){
                            System.out.println(e.toString());
                        }
                    }
                });



                if(Memoria.Modo>0 && !Memoria.GenderFind.equals("")) {
                    Intent in = new Intent(getActivity(), Programa_Principal.class);
                    startActivity(in);
                    getActivity().finish();
                }

            }
        });
        return view;
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
