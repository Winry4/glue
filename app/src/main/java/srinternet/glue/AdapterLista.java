package srinternet.glue;

import android.app.Activity;
import android.content.Context;
import android.provider.Contacts;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;

/**
 * Created by Rebeca on 15/08/2017.
 */

public class AdapterLista extends BaseAdapter {
    public Activity activity;
    private LayoutInflater inflater;
    private List<People> peoples;
    GridView gridView;
    public AdapterLista(Activity activity, List<People>peoples){
        this.activity=activity;
        this.peoples=peoples;
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater==null){
            inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView==null){
            convertView=inflater.inflate(R.layout.tabla_gente,null);
        }
        RelativeLayout relativeLayout=(RelativeLayout) inflater.inflate(R.layout.fragment_principal_lista,null);
        gridView=(GridView)relativeLayout.findViewById(R.id.gridLista);
        ImageView grid_item_image = (ImageView) convertView.findViewById(R.id.foto_lista);

        final People bG=peoples.get(position);
        //grid_item_image.setImageBitmap(bG.getFoto());
        return convertView;
    }
}
