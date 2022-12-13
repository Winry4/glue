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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by JAVIER OLIVER ASUS on 05/09/2017.
 */

public class AmigosAdapter extends BaseAdapter {
    Activity a;
    Context c;
    private LayoutInflater inflater;
    private List<AmigosGrid> listGrids;
    GridView gridView;

    public AmigosAdapter( Context c, List<AmigosGrid>listGrids, Activity ac){
        this.c=c;
        this.listGrids=listGrids;
        a=ac;
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
            convertView=inflater.inflate(R.layout.list_amigos,null);
        }
        LinearLayout relativeLayout=(LinearLayout) inflater.inflate(R.layout.fragment_gluer,null);
        gridView=(GridView)relativeLayout.findViewById(R.id.Amigos);

        final ImageView grid_item_image = (ImageView) convertView.findViewById(R.id.Foto_amigo);
        TextView nombre=(TextView) convertView.findViewById(R.id.Nombre);

        final AmigosGrid bG=listGrids.get(position);
        final View v= convertView;
        if(bG.getImageDataOri()==null){
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    bG.cogerFoto(bG.getImageId());
                    try {
                        bG.setImageData(redimensionarImagenMaximo(bG.getImageDataOri(), 100, 100));
                        a.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                grid_item_image.setImageBitmap(bG.getImageData());
                                redondear(bG.getImageData(), v);
                            }
                        });
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });
        }else{
            bG.setImageData(redimensionarImagenMaximo(bG.getImageDataOri(), 100,100));
            grid_item_image.setImageBitmap(bG.getImageData());
            redondear(bG.getImageData(), v);
        }


        nombre.setText(bG.getNombre());
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
