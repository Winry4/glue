package srinternet.glue;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rebeca on 12/09/2017.
 */

public class OcultoAdapter extends BaseAdapter {
    Context c;
    private LayoutInflater inflater;
    private List<OcultosClase> listGrids;
    Activity activity;
    GridView gridView;

    public OcultoAdapter(Context c, List<OcultosClase> listGrids, Activity activity) {
        this.c = c;
        this.listGrids = listGrids;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listGrids.size();
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
        if (inflater==null){
            inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView==null){
            convertView=inflater.inflate(R.layout.item_oculto,null);
        }
        RelativeLayout relativeLayout=(RelativeLayout) inflater.inflate(R.layout.activity_ocultos,null);
        gridView=(GridView)relativeLayout.findViewById(R.id.listaOcultos);

        final ImageView grid_item_image = (ImageView) convertView.findViewById(R.id.Foto_amigo);
        TextView nombre=(TextView) convertView.findViewById(R.id.nombre);
        TextView mensaje=(TextView)convertView.findViewById(R.id.mensaje);
        TextView fecha=(TextView)convertView.findViewById(R.id.fecha);
        ImageView cancelar = (ImageView) convertView.findViewById(R.id.eliminar2);
        cancelar.setVisibility(View.INVISIBLE);
        final View c= convertView;
        final OcultosClase bG=listGrids.get(position);
        if(bG.getImageId()==null){
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    bG.cogerFoto(bG.getIdFoto(),activity);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bG.setImageId(redimensionarImagenMaximo(bG.getImageId(), 150,150));
                            grid_item_image.setImageBitmap(bG.getImageId());
                            redondear(bG.getImageId(), c);
                        }
                    });
                }
            });

        }else{
            bG.setImageId(redimensionarImagenMaximo(bG.getImageId(), 150,150));
            grid_item_image.setImageBitmap(bG.getImageId());
            redondear(bG.getImageId(), convertView);
        }

        nombre.setText(bG.getNombre());
        mensaje.setText(bG.getMensaje());
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
    public void redondear(Bitmap originalBitmap, View view){
        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(c.getResources(), originalBitmap);
        roundedDrawable.setCornerRadius(originalBitmap.getHeight());
        ImageView imageView = (ImageView) view.findViewById(R.id.Foto_amigo);
        imageView.setImageDrawable(roundedDrawable);
    }

}
