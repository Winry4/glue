package srinternet.glue;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
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
 * Created by Rebeca on 13/09/2017.
 */

public class HistoryAdapter extends BaseAdapter {
    Context c;
    private LayoutInflater inflater;
    private List<History> listGrids;
    Activity activity;
    GridView gridView;

    public HistoryAdapter(Context c, List<History> listGrids, Activity activity) {
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
            convertView=inflater.inflate(R.layout.item_history,null);
        }
        TextView nombre=(TextView) convertView.findViewById(R.id.vacaciones1);
        TextView mensaje=(TextView)convertView.findViewById(R.id.vacaciones3);
        TextView fecha=(TextView)convertView.findViewById(R.id.revision);
        History bG=listGrids.get(position);
        nombre.setText(bG.getPregunta());
        mensaje.setText(bG.getResp());
        fecha.setText(bG.getEstad());

        if(bG.getEstad().equals("Rechazada")){
            fecha.setTextColor(Color.RED);
        }

        return convertView;
    }
}
