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

import java.util.List;

/**
 * Created by Rebeca on 17/08/2017.
 */

public class CargaFotos_Adapter extends BaseAdapter {
    public Activity activity;
    private LayoutInflater inflater;
    private List<Foto> listaFotos;
    GridView gridView;

    public CargaFotos_Adapter(Activity activity, List<Foto> listaFotos) {
        this.activity = activity;
        this.listaFotos = listaFotos;
    }

    @Override
    public int getCount() {
        return listaFotos.size();
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
            inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView==null){
            convertView=inflater.inflate(R.layout.list_carga_fotos,null);
        }
        RelativeLayout relativeLayout=(RelativeLayout) inflater.inflate(R.layout.carga_fotos,null);
        gridView=(GridView)relativeLayout.findViewById(R.id.Lista_carga);


        final ImageView imagen = (ImageView) convertView.findViewById(R.id.foto_carga);
        final int pos=position;
        if(listaFotos.get(position).getImageId()==null){
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    listaFotos.get(pos).cogerfoto(listaFotos.get(pos).getNombre(),activity);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imagen.setImageBitmap(redimensionarImagenMaximo(listaFotos.get(pos).getImageId(), 130,130));
                        }
                    });
                }
            });

        }else{
            imagen.setImageBitmap(redimensionarImagenMaximo(listaFotos.get(position).getImageId(), 130,130));
        }

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
}
