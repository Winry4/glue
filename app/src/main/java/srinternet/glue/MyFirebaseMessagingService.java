package srinternet.glue;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;

import com.google.firebase.database.Transaction;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        try{
            System.out.println(remoteMessage.getNotification().getBody());

            String idchat = new JSONObject(remoteMessage.getNotification().getBody()).getString("idchat");


            if(!Memoria.idchatAct.equals(idchat)){
                if(Memoria.notificaciones==1){
                    sendPushNotification(remoteMessage.getNotification());
                }
            }else{
                try{
                    HttpURLConnection conn =(HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    PrintWriter wr = new PrintWriter (
                            conn.getOutputStream ());

                    String response="";

                    String param="funcion=f17&idFacebo="+Memoria.UserFBId+"&idSesion="+Memoria.idSension+"&idenChat="+idchat+"&mensInic=0";
                    wr.print(param);
                    wr.close();
                    conn.connect();
                    Scanner inStream = new Scanner(conn.getInputStream());
                    while(inStream.hasNextLine())
                        response+=(inStream.nextLine());

                    conn.disconnect();
                    JSONObject u = new JSONObject(response);
                    Memoria.chatActual=u;

                    List<MensajeDeTexto> mensajeDeTextoList=new ArrayList<>();
                    mensajeDeTextoList.clear();
                    mensajeDeTextoList.clear();
                    System.out.println("gato");
                    for (int i=1;i<Memoria.chatActual.getJSONArray("mensajes").length();i++){
                        int id=0;
                        try {

                            if(Memoria.chatActual.getJSONArray("mensajes").getJSONObject(i).getString("IDFACEBO").equals(Memoria.UserFBId)){
                                id=2;
                            }else {
                                id=1;
                            }
                            MensajeDeTexto msn=new MensajeDeTexto(Memoria.chatActual.getJSONArray("mensajes").getJSONObject(i).getString("MENSAJES"), Memoria.chatActual.getJSONArray("mensajes").getJSONObject(i).getString("FECHAREG"), id);
                            mensajeDeTextoList.add(msn);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    Memoria.ma=new MensajeAdapter(mensajeDeTextoList);
                    Memoria.ma.notifyDataSetChanged();

                    Handler han = new Handler(Looper.getMainLooper());
                    han.post(new Runnable() {
                        @Override
                        public void run() {

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
                            Memoria.recy.setLayoutManager(linearLayoutManager);
                            Memoria.recy.setAdapter(Memoria.ma);
                            Memoria.ma.notifyDataSetChanged();
                            Memoria.recy.scrollToPosition(Memoria.ma.getItemCount() - 1);
                        }
                    });



                }catch(Exception e){
                    System.out.println(e.toString());
                }



            }

        }catch(Exception e){
            System.out.println(e.toString());
        }


    }

    private void sendPushNotification(RemoteMessage.Notification json) {
        try {
            //getting the json data
            String title = json.getTitle();
            String message="";

            try{
                message = new JSONObject(json.getBody()).getString("message");
            }catch (Exception e){
                e.printStackTrace();
            }


            //creating MyNotificationManager object
            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());

            //creating an intent for the notification
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

            mNotificationManager.showSmallNotification(title, message, intent);

        }catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}