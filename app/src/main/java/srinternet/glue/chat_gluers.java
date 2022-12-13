package srinternet.glue;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Rebeca on 08/08/2017.
 */

public class chat_gluers extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    RelativeLayout screenLayout;
    ListAdapter listAdapter;

    GridView listView;
    public chat_gluers(){

    }
    public static chat_gluers newInstance(String param1, String param2) {
        chat_gluers fragment = new chat_gluers();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.chat_gluers, container, false);

        screenLayout = (RelativeLayout)view.findViewById(R.id.ventana);
        listAdapter = new ListAdapter(view.getContext(),Memoria.listGridsGluer, getActivity());
        listView = (GridView) view.findViewById(R.id.lista);
        listView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,final int position, long id) {

                final int posicion=parent.getSelectedItemPosition();

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            HttpURLConnection conn =(HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                            conn.setRequestMethod("POST");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);
                            PrintWriter wr = new PrintWriter (
                                    conn.getOutputStream ());

                            String response="";
                            Memoria.idchatAct=Memoria.listGridsGluer.get(posicion+1).getIdentificador();
                            String param="funcion=f17&idFacebo="+Memoria.UserFBId+"&idSesion="+Memoria.idSension+"&idenChat="+Memoria.listGridsGluer.get(posicion+1).getIdentificador()+"&mensInic=0";
                            wr.print(param);
                            wr.close();
                            conn.connect();
                            Scanner inStream = new Scanner(conn.getInputStream());
                            while(inStream.hasNextLine())
                                response+=(inStream.nextLine());

                            conn.disconnect();
                            JSONObject u = new JSONObject(response);
                            Memoria.chatActual=u;

                            Intent intent=new Intent(getActivity(),ChatIndividual.class);
                            intent.putExtra("tipo", 2);
                            intent.putExtra("Posicion", position);
                            startActivity(intent);


                        }catch(Exception e){
                            System.out.println(e.toString());
                        }


                    }


                });

            }
        });

        return view;


    }
}
