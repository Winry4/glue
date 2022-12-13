package srinternet.glue;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rebeca on 15/08/2017.
 */

public class MensajeAdapter extends RecyclerView.Adapter<MensajeAdapter.MensajesViewHolder> {
    private static List<MensajeDeTexto> mensajeDeTextos;

    public MensajeAdapter(List<MensajeDeTexto> mensajeDeTextos) {
        this.mensajeDeTextos = mensajeDeTextos;
    }

    @Override
    public MensajesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vi= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_mensajes,parent, false);
        return new MensajesViewHolder(vi);
    }

    @Override
    public void onBindViewHolder(MensajesViewHolder holder, int position) {
        RelativeLayout.LayoutParams rl=(RelativeLayout.LayoutParams)holder.cardView.getLayoutParams();
        FrameLayout.LayoutParams fl=(FrameLayout.LayoutParams)holder.layout.getLayoutParams();
        LinearLayout.LayoutParams rhora=(LinearLayout.LayoutParams)holder.textView.getLayoutParams();
        LinearLayout.LayoutParams rmensaje=(LinearLayout.LayoutParams)holder.textView2.getLayoutParams();
        if(mensajeDeTextos.get(position).getId()==1){
            holder.layout.setBackgroundResource(R.drawable.chat_holi_suyo);
            rl.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            rl.addRule(RelativeLayout.ALIGN_PARENT_END,0);
            fl.gravity= Gravity.LEFT;
            rhora.gravity=Gravity.LEFT;
            rmensaje.gravity=Gravity.LEFT;
            holder.textView.setGravity(Gravity.LEFT);

        }else if(mensajeDeTextos.get(position).getId()==2){
            holder.layout.setBackgroundResource(R.drawable.chat_holi_mio);
            rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            rl.addRule(RelativeLayout.ALIGN_PARENT_START,0);
            fl.gravity= Gravity.RIGHT;
            rhora.gravity=Gravity.RIGHT;
            rmensaje.gravity=Gravity.RIGHT;
            holder.textView.setGravity(Gravity.LEFT);


        }
        holder.cardView.setLayoutParams(rl);
        holder.layout.setLayoutParams(fl);
        holder.textView2.setLayoutParams(rmensaje);
        holder.textView.setLayoutParams(rhora);
        holder.textView.setText(mensajeDeTextos.get(position).getTexto());
        holder.textView2.setText(mensajeDeTextos.get(position).getHora());


    }

    @Override
    public int getItemCount() {
        return mensajeDeTextos.size();
    }

    static class MensajesViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView textView;
        TextView textView2;
        LinearLayout layout;
        MensajesViewHolder(View v){
            super(v);
            cardView=(CardView)v.findViewById(R.id.cardView);
            layout=(LinearLayout)v.findViewById(R.id.layoutMensaje);
            textView=(TextView)v.findViewById(R.id.textoMensaje);
            textView2=(TextView)v.findViewById(R.id.hora);


        }
    }
}
