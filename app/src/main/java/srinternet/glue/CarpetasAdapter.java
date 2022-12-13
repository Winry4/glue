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

import srinternet.glue.Album;
import srinternet.glue.R;

/**
 * Created by Rebeca on 16/08/2017.
 */

public class CarpetasAdapter extends BaseAdapter {
    public Activity activity;
    private LayoutInflater inflater;
    private List<Album> carpetasL;
    GridView gridView;

    public CarpetasAdapter(Activity activity, List<Album> carpetasL) {
        this.activity = activity;
        this.carpetasL = carpetasL;
    }

    @Override
    public int getCount() {
        return carpetasL.size();
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
    public View getView(int position,View convertView, ViewGroup parent) {
        final int hola=position;
        if (inflater==null){
            inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView==null){
            convertView=inflater.inflate(R.layout.list_carpetas,null);
        }
        RelativeLayout relativeLayout=(RelativeLayout) inflater.inflate(R.layout.activity_carpetas,null);
        gridView=(GridView)relativeLayout.findViewById(R.id.Lista_Carpetas);

        final ImageView fotoC = (ImageView) convertView.findViewById(R.id.foto_carpetas);
        final TextView nombreC=(TextView)convertView.findViewById(R.id.nombre_carpeta);
        final TextView numC=(TextView) convertView.findViewById(R.id.num_carpeta);
        final Album bG= carpetasL.get(position);
        final View c = convertView;

        if(bG.getFotoData()==null){
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    bG.cogerFoto(bG.getFoto(),activity);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bG.setFotoData(redimensionarImagenMaximo(bG.getFotoData(), 120,120));
                            fotoC.setImageBitmap(bG.getFotoData());
                            redondear(bG.getFotoData(), c);
                            nombreC.setText(bG.getNombre());
                            numC.setText(bG.getNumero());
                        }
                    });
                }
            });
        }else{
            bG.setFotoData(redimensionarImagenMaximo(bG.getFotoData(), 120,120));
            fotoC.setImageBitmap(bG.getFotoData());
            redondear(bG.getFotoData(), convertView);
            nombreC.setText(bG.getNombre());
            numC.setText(bG.getNumero());
        }



        //fotoC.setImageBitmap(bG.getFoto());
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
        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(activity.getResources(), originalBitmap);
        roundedDrawable.setCornerRadius(originalBitmap.getHeight());
        ImageView imageView = (ImageView) view.findViewById(R.id.foto_carpetas);
        imageView.setImageDrawable(roundedDrawable);
    }
}
