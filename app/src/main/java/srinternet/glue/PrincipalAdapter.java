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
 * Created by bersus96 on 12/9/17.
 */

public class PrincipalAdapter extends BaseAdapter {
    Context c;
    private LayoutInflater inflater;
    private List<Princi> princiGrids;
    Activity activity;
    GridView gridView;

    public PrincipalAdapter(Context c, List<Princi> princiGrids, Activity ac){
        this.c=c;
        this.princiGrids=princiGrids;
        activity=ac;
    }

    public PrincipalAdapter( ) {
    }

    @Override
    public int getCount() {
        return princiGrids.size();
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
            convertView=inflater.inflate(R.layout.grid_item_amigos,null);
        }
        RelativeLayout relativeLayout=(RelativeLayout) inflater.inflate(R.layout.activity_programa__principal,null);
        gridView=(GridView)relativeLayout.findViewById(R.id.grid_principal);

        final ImageView grid_item_image = (ImageView) convertView.findViewById(R.id.img_amigosComun_principal);
        TextView nombre=(TextView) convertView.findViewById(R.id.tx_amigosComun_principal);
        final View c= convertView;
        final Princi bG=princiGrids.get(position);
        if(bG.getOculto().equals("0")){
            grid_item_image.setAlpha((float) 0.40);
            nombre.setAlpha((float) 0.40);
        }
        final View v= convertView;
        if(bG.getImageData()==null){
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    bG.cogerFoto(bG.getIdfoto(), activity);
                    try{
                        bG.setImageData(redimensionarImagenMaximo(bG.getImageData(), 100,100));
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                grid_item_image.setImageBitmap(bG.getImageData());
                                redondear(bG.getImageData(), v);
                            }
                        });
                    }catch(Exception e){
                        e.printStackTrace();
                    }


                }
            });
        }else{
            bG.setImageData(redimensionarImagenMaximo(bG.getImageData(), 100,100));
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
        ImageView imageView = (ImageView) view.findViewById(R.id.img_amigosComun_principal);
        imageView.setImageDrawable(roundedDrawable);
    }
}





