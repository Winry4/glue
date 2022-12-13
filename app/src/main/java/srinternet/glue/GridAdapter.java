package srinternet.glue;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

/**
 * Created  on 3/26/2017.
 */

public class GridAdapter extends BaseAdapter {
    public Activity activity;
    private LayoutInflater inflater;
    private List<BasisGrid> basisGrids;
    Bitmap cover;
    String nombre;
    String count;
    GridView gridView;

    public GridAdapter(Perfil activity, List<BasisGrid>basisGrids){
        this.activity=activity;
        this.basisGrids=basisGrids;

    }
    @Override
    public int getCount() {
        return basisGrids.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos=position;
        final int hola=position;
        if (inflater==null){
            inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView==null){
            convertView=inflater.inflate(R.layout.grid_item,null);
        }
        RelativeLayout relativeLayout=(RelativeLayout) inflater.inflate(R.layout.activity_drag_drop,null);
        gridView=(GridView)relativeLayout.findViewById(R.id.gridImage);
        final ImageView grid_item_image = (ImageView) convertView.findViewById(R.id.grid_item_image);
        final BasisGrid bG=basisGrids.get(position);
        final FloatingActionButton boton = (FloatingActionButton) convertView.findViewById(R.id.grid_item_btn);

        if(bG.getImageId()==null){
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    bG.cogerFoto(bG.getImageName(), activity);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            grid_item_image.setImageBitmap(bG.getImageId());
                            if(bG.getImageId()!=null){
                                boton.setImageResource(R.drawable.foto_menos);

                            }
                        }
                    });

                }
            });
        }else{
            grid_item_image.setImageBitmap(bG.getImageId());
            if(bG.getImageId()!=null){
                boton.setImageResource(R.drawable.foto_menos);

            }
        }



        boton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                Memoria.fotoAct=grid_item_image;
                Memoria.botonAct=boton;
                Memoria.PosAct=pos;
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            HttpURLConnection conn = (HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                            conn.setRequestMethod("POST");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);
                            PrintWriter wr = new PrintWriter(
                                    conn.getOutputStream());
                            String params = "funcion=f32&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension + "&posicion=" + (hola+1);
                            wr.print(params);
                            wr.close();
                            conn.connect();

                            String res = "";
                            Scanner Stream = new Scanner(conn.getInputStream());
                            while (Stream.hasNextLine())
                                res += (Stream.nextLine());

                            conn.disconnect();
                            JSONObject json= new JSONObject(res);
                            String accion =json.getString("accion");
                            if(accion.equals("eliminar") && hola!=0){

                                HttpURLConnection con =(HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                                con.setRequestMethod("POST");
                                con.setDoOutput(true);
                                con.setDoInput(true);
                                PrintWriter wr1 = new PrintWriter (
                                        con.getOutputStream ());

                                String response="";
                                String param="funcion=f10&idFacebo="+Memoria.UserFBId+"&idSesion="+Memoria.idSension+"&posicion="+(hola+1);
                                wr1.print(param);
                                wr1.close();
                                con.connect();
                                Scanner inStream = new Scanner(con.getInputStream());
                                while(inStream.hasNextLine())
                                    response+=(inStream.nextLine());

                                con.disconnect();

                                JSONObject json1= new JSONObject(response);

                                Memoria.Fotos=json1.getJSONArray("fotos");

                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        grid_item_image.setImageBitmap(null);
                                        boton.setImageResource(R.drawable.foto_mas);
                                    }
                                });

                            }else if(Memoria.albumList.size()==0){

                                GraphRequest request = GraphRequest.newMeRequest(
                                        AccessToken.getCurrentAccessToken(),
                                        new GraphRequest.GraphJSONObjectCallback() {
                                            @Override
                                            public void onCompleted(JSONObject object, GraphResponse response) {
                                                try {
                                                    for (int i = 0; i < object.getJSONObject("albums").getJSONArray("data").length(); i++){

                                                        try {
                                                            final String nomFoto = object.getJSONObject("albums").getJSONArray("data").getJSONObject(i).getJSONObject("cover_photo").getString("id");
                                                            final String nombre=object.getJSONObject("albums").getJSONArray("data").getJSONObject(i).getString("name");
                                                            final String count=object.getJSONObject("albums").getJSONArray("data").getJSONObject(i).getString("count");
                                                            final String id=object.getJSONObject("albums").getJSONArray("data").getJSONObject(i).getString("id");
                                                            final int pos=i;
                                                            final int fin= object.getJSONObject("albums").getJSONArray("data").length();


                                                            Album a=new Album(count, nombre, nomFoto, id);
                                                            Memoria.albumList.add(a);




                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                    Intent intent = new Intent(activity, Carpetas.class);
                                                    activity.startActivity(intent);


                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }



                                            }
                                        });
                                Bundle parameters = new Bundle();
                                parameters.putString("fields", "albums{id,cover_photo,name,count}");
                                request.setParameters(parameters);
                                request.executeAsync();
                            }else if(Memoria.albumList.size()>0){
                                Intent intent = new Intent(activity, Carpetas.class);
                                activity.startActivity(intent);
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });




            }
        });

        return convertView;
    }
}
