package srinternet.glue;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class MiHistoria extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RelativeLayout screenLayout;
    HistoryAdapter listAdapter;
    GridView listView;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Dialog customDialog = null;

    private OnFragmentInteractionListener mListener;

    public MiHistoria() {
        // Required empty public constructor
    }
    public static MiHistoria newInstance(String param1, String param2) {
        MiHistoria fragment = new MiHistoria();
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
        View view = inflater.inflate(R.layout.fragment_mi_historia, container, false);
        int j;
        listView=(GridView)view.findViewById(R.id.listaHistory);
        screenLayout = (RelativeLayout)view.findViewById(R.id.ventana);
        listAdapter = new HistoryAdapter(getContext(),Memoria.historyList, getActivity());
        listView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("posi"+position);
                mostrar(Memoria.historyList.get(position).getPregunta(), position);
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void mostrar(String text, final int position)
    {
        // con este tema personalizado evitamos los bordes por defecto
        customDialog = new Dialog(getContext(),R.style.Theme_Dialog_Translucent);
        //deshabilitamos el título por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.dialog_historia);


        TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
        titulo.setText(text);
;

        ((Button) customDialog.findViewById(R.id.cancelar)).setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view)
        {
            customDialog.dismiss();
        }
    });
        ((Button) customDialog.findViewById(R.id.aceptar)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                EditText texto=(EditText)customDialog.findViewById(R.id.editTexto) ;
                Memoria.historyList.get(position).setResp(texto.getText().toString());
                Memoria.historyList.get(position).setEstad("revision");
                listAdapter = new HistoryAdapter(getContext(),Memoria.historyList, getActivity());
                listView.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();


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
                            String param = "funcion=f38&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension+ "&codHisto="+Memoria.historyList.get(position).getCodmensa()+"&historia="+Memoria.historyList.get(position).getResp();
                            wr.print(param);
                            wr.close();
                            conn.connect();
                            String ress="";
                            Scanner Strea = new Scanner(conn.getInputStream());
                            while(Strea.hasNextLine())
                                ress+=(Strea.nextLine());

                            conn.disconnect();
                            JSONObject j= new JSONObject(ress);

                        }catch(Exception e){
                            System.out.println(e.toString());

                        }
                        customDialog.dismiss();
                    }


                });
            }
        });



        customDialog.show();
    }
}
