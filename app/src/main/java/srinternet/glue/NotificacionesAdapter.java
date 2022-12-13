package srinternet.glue;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Rebeca on 17/08/2017.
 */

public class NotificacionesAdapter extends BaseAdapter {
    Activity activity;
    Context c;
    JSONObject j;
    private LayoutInflater inflater;
    private List<Notifi> notifiList;
    GridView gridView;
    private float firstTouchX;

    public NotificacionesAdapter(Activity activity, List<Notifi> notifiList, Context c) {
        this.activity = activity;
        this.notifiList = notifiList;
        this.c=c;
    }

    @Override
    public int getCount() {
        return notifiList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int hola=position;
        if (inflater==null){
            inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView==null){
            convertView=inflater.inflate(R.layout.tabla_notificaciones,null);
        }
        RelativeLayout relativeLayout=(RelativeLayout) inflater.inflate(R.layout.activity_notificaciones,null);
        gridView=(GridView)relativeLayout.findViewById(R.id.Lista);

        RelativeLayout r = (RelativeLayout) convertView.findViewById(R.id.relativeLayout10);

        final boolean [] irvolver= new boolean[Memoria.notifiList.size()];
        final ImageView fotoC = (ImageView) convertView.findViewById(R.id.Foto_amigo);
        TextView mensaje=(TextView)convertView.findViewById(R.id.texto_notifi);
        TextView fecha=(TextView)convertView.findViewById(R.id.nombre);
        final Notifi bG=notifiList.get(position);
        for(int o=0;o<irvolver.length;o++){
            irvolver[o]=true;
        }
        final View vi = convertView;
        ImageView fotico=(ImageView)convertView.findViewById(R.id.Foto_amigo);
        fotico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bG.getTipo().equals("1")){
                    Intent intent = new Intent(c, NotifiPantalla.class);
                    intent.putExtra("posi", position);
                    activity.startActivity(intent);
                }

            }
        });
        ImageView cancelar = (ImageView) vi.findViewById(R.id.rechazar);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int nu;
                if(bG.getTipo().equals("1")){
                    //rechazo
                    nu=3;
                }else{
                    nu=5;
                }
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Notifi not = Memoria.notifiList.get(position);
                            HttpURLConnection conn = (HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                            conn.setRequestMethod("POST");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);
                            PrintWriter wr = new PrintWriter(
                                    conn.getOutputStream());
                            String param = "funcion=f15&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension + "&idNotifi=" + not.getIdNot() + "&numBoton="+nu;
                            wr.print(param);
                            wr.close();
                            conn.connect();
                            String ress="";
                            Scanner Strea = new Scanner(conn.getInputStream());
                            while(Strea.hasNextLine())
                                ress+=(Strea.nextLine());
                            System.out.println(ress);
                            Memoria.notifiList.clear();
                            Memoria.Notificaciones=j.getJSONArray("notificaciones");
                            Memoria.Notifis=j.getJSONArray("notifiMostrar");

                            for (int i=0; i<Memoria.Notifis.length(); i++) {
                                String fecha=Memoria.Notifis.getJSONObject(i).getString("FECHAREG");
                                String mensaje=Memoria.Notifis.getJSONObject(i).getString("NOTIMENS");
                                String id= Memoria.Notifis.getJSONObject(i).getString("IDNOTIFI");
                                String idfbb=Memoria.Notifis.getJSONObject(i).getString("IDFOTOGB");
                                String idfba=Memoria.Notifis.getJSONObject(i).getString("IDFOTOGA");
                                String idfotoa= Memoria.Notifis.getJSONObject(i).getString("FOTOGRAA");
                                String idfotob= Memoria.Notifis.getJSONObject(i).getString("FOTOGRAB");//CAMBIAR ESTO POR A PORK SON UNOS PATANES
                                Notifi bg = new Notifi(id,fecha, mensaje, idfba, idfbb,idfotoa, idfotob ,"1", "1");
                                Memoria.notifiList.add(bg);
                            }
                            for (int i=0;i<Memoria.Notificaciones.length();i++){
                                String fecha=Memoria.Notificaciones.getJSONObject(i).getString("FECHAREG");
                                String mensaje=Memoria.Notificaciones.getJSONObject(i).getString("NOTIMENS");
                                String id= Memoria.Notificaciones.getJSONObject(i).getString("IDNOTIFI");
                                String idfbb=Memoria.Notificaciones.getJSONObject(i).getString("IDFOTOGB");
                                String idfba=Memoria.Notificaciones.getJSONObject(i).getString("IDFOTOGA");
                                String idfotoa= Memoria.Notificaciones.getJSONObject(i).getString("FOTOGRAA");
                                String idfotob= Memoria.Notificaciones.getJSONObject(i).getString("FOTOGRAB");//CAMBIAR ESTO POR A PORK SON UNOS PATANES
                                String tipo=Memoria.Notificaciones.getJSONObject(i).getString("TIPALERT");
                                Notifi bg = new Notifi(id,fecha, mensaje, idfba, idfbb,idfotoa, idfotob ,tipo, "0");
                                Memoria.notifiList.add(bg);

                            }


                            conn.disconnect();
                            Memoria.notiadapter.notifyDataSetChanged();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        r.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int i = event.getActionIndex();
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        //Aqui guardas en una variable privada de clase las coordenadas del primer toque:
                        firstTouchX = event.getX();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        if (firstTouchX > event.getX()) {
                            //Hacia la izquierda
                            if (irvolver[i]) {
                                Animation slide = AnimationUtils.loadAnimation(c, R.anim.translate7);
                                RelativeLayout l1 = (RelativeLayout) v.findViewById(R.id.relativeLayout10);
                                ImageView cancelar = (ImageView) vi.findViewById(R.id.rechazar);
                                l1.startAnimation(slide);
                                cancelar.setVisibility(View.VISIBLE);
                                irvolver[i] = false;
                            }

                        }else{
                            if(irvolver[i]==false) {
                                Animation slide = AnimationUtils.loadAnimation(c, R.anim.translate8);
                                RelativeLayout l1 = (RelativeLayout) v.findViewById(R.id.relativeLayout10);
                                ImageView cancelar = (ImageView) vi.findViewById(R.id.rechazar);
                                l1.startAnimation(slide);
                                cancelar.setVisibility(View.INVISIBLE);
                                irvolver[i] = true;
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (firstTouchX > event.getX()) {
                            //Hacia la izquierda
                            if (irvolver[i]) {
                                Animation slide = AnimationUtils.loadAnimation(c, R.anim.translate7);
                                RelativeLayout l1 = (RelativeLayout) v.findViewById(R.id.relativeLayout10);
                                ImageView cancelar = (ImageView) vi.findViewById(R.id.rechazar);
                                l1.startAnimation(slide);
                                cancelar.setVisibility(View.VISIBLE);
                                irvolver[i] = false;
                            }

                        }else{
                            if(irvolver[i]==false) {
                                Animation slide = AnimationUtils.loadAnimation(c, R.anim.translate8);
                                RelativeLayout l1 = (RelativeLayout) v.findViewById(R.id.relativeLayout10);
                                ImageView cancelar = (ImageView) vi.findViewById(R.id.rechazar);
                                l1.startAnimation(slide);
                                cancelar.setVisibility(View.INVISIBLE);
                                irvolver[i] = true;
                            }
                        }
                        break;

                }
                return true;
            }

        });
        if(bG.getFotoB()==null){
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    bG.cogerFotoB(bG.getFotoidB(), activity);
                    try {
                        bG.setFotoB(redimensionarImagenMaximo(bG.getFotoB(), 150, 150));
                        bG.setFotoBRedondear(redondear(bG.getFotoB(), vi));
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }

        if(bG.getFotoA()==null){
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    bG.cogerFoto(bG.getFotoidA(),activity);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(bG.getTipo().equals("1")){
                                RoundedBitmapDrawable roundedDrawable=redondear(redimensionarImagenMaximo(bG.getFotoA(), 150,150), vi);
                                fotoC.setImageDrawable(roundedDrawable);
                            }else{
                                Drawable originalDrawable = c.getResources().getDrawable(R.drawable.boton_corazon);
                                Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();
                                originalBitmap=redimensionarImagenMaximo(originalBitmap, 150,150);
                                fotoC.setImageDrawable(redondear(originalBitmap, vi));
                            }

                        }
                    });
                }
            });
        }else{
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(bG.getTipo().equals("1")){
                        RoundedBitmapDrawable roundedDrawable=redondear(redimensionarImagenMaximo(bG.getFotoA(), 150,150), vi);
                        fotoC.setImageDrawable(roundedDrawable);
                    }else{
                        Drawable originalDrawable = c.getResources().getDrawable(R.drawable.boton_corazon);
                        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();
                        originalBitmap=redimensionarImagenMaximo(originalBitmap, 150,150);
                        fotoC.setImageDrawable(redondear(originalBitmap, vi));
                    }

                }
            });
        }
        mensaje.setText(bG.getText());
        fecha.setText(bG.getFecha());
        return convertView;
    }
    public Bitmap redimensionarImagenMaximo(Bitmap mBitmap, float newWidth, float newHeigth){
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
    public RoundedBitmapDrawable redondear(Bitmap originalBitmap, View view){
        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(activity.getResources(), originalBitmap);
        roundedDrawable.setCornerRadius(originalBitmap.getHeight());
        return  roundedDrawable;
    }
}
