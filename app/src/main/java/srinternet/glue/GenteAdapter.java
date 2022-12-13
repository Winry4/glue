package srinternet.glue;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rebeca on 15/08/2017.
 */

public class GenteAdapter extends BaseAdapter {
    public Context c;
    private LayoutInflater inflater;
    private List<People> peoples;
    GridView gridView;

    public GenteAdapter(Context c, List<People> peoples) {
        this.c = c;
        this.peoples = peoples;
    }

    @Override
    public int getCount() {
        return peoples.size();
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
            convertView=inflater.inflate(R.layout.tabla_gente,null);
        }
        RelativeLayout relativeLayout=(RelativeLayout) inflater.inflate(R.layout.fragment_principal_lista,null);
        gridView=(GridView)relativeLayout.findViewById(R.id.gridLista);
        final ImageView imagen = (ImageView) convertView.findViewById(R.id.foto_lista);
        final TextView amigosC=(TextView) convertView.findViewById(R.id.amigos);
        final TextView nfotos=(TextView)convertView.findViewById(R.id.fotos);
        final TextView pais=(TextView)convertView.findViewById(R.id.Pais);
        final TextView nombre_Edad=(TextView)convertView.findViewById(R.id.nombre_Edad);

        final People bG=peoples.get(position);


        if(bG.isFav()){

            ImageView e2 = (ImageView) convertView.findViewById(R.id.guardar2);
            e2.setColorFilter(new LightingColorFilter(Color.DKGRAY, Color.DKGRAY));
        }else{
            ImageView e2 = (ImageView) convertView.findViewById(R.id.imageView55);
            e2.setColorFilter(Color.DKGRAY);
        }



        if(bG.getPerfilFot()==null){
            AsyncTask d = new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    bG.cogerFoto(bG.getPerfilFotNom());
                    return null;
                }

                @Override
                protected void onPostExecute(Object o) {
                    Bitmap b=bG.getPerfilFot();

                    try {
                        bG.setPerfilFot(redimensionarImagenMaximo(b, 150, 150));
                        imagen.setImageBitmap(bG.getPerfilFot());
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            };
            d.execute("");

        }else{
            Bitmap b=bG.getPerfilFot();

            bG.setPerfilFot(redimensionarImagenMaximo(b, 150,150));
            imagen.setImageBitmap(bG.getPerfilFot());
        }


        amigosC.setText(String.valueOf(bG.getnAmigosComun()));
        nfotos.setText(String.valueOf(bG.getNumFotos()));
        pais.setText(bG.getCity());
        nombre_Edad.setText(bG.getNombre()+", "+String.valueOf(bG.getEdad()));
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
