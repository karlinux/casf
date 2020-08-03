package com.aulaxalapa.casf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aulaxalapa.casf.R;

import java.util.ArrayList;


public class Adaptador extends BaseAdapter {
    private Context context;
    private ArrayList<Entidad> listItems;

    public Adaptador(Context context, ArrayList<Entidad> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        Entidad item = (Entidad) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.listview_item, null);
        TextView tvId = (TextView) convertView.findViewById(R.id.tvId);
        TextView tvIde = (TextView) convertView.findViewById(R.id.tvIde);
        TextView tvTitulo = (TextView) convertView.findViewById(R.id.tvTitulo);
        TextView tvContenido = (TextView) convertView.findViewById(R.id.tvContenido);
        ImageView ivClock = (ImageView) convertView.findViewById(R.id.ivClock);

        tvId.setVisibility(View.GONE);
        tvId.setText(item.getId());

        tvIde.setVisibility(View.GONE);
        tvIde.setText(item.getIde());

        tvTitulo.setText(item.getTitulo());
        tvContenido.setText(item.getContenido());
        String color = item.getColor();

        if( item.getColor() == null ){
            color = "2";
        }
            if(color.equals("0")){
                //tvContenido.setBackgroundColor(Color.parseColor("#AA983F"));
                //tvTitulo.setBackgroundColor(Color.parseColor("#AA983F"));
                ivClock.setImageResource(R.drawable.checkmark);
            }else if(color.equals("1")){
                ivClock.setImageResource(R.drawable.clock);
            }else{
                //tvContenido.setBackgroundColor(Color.parseColor("#FFFFFF"));
                //tvTitulo.setBackgroundColor(Color.parseColor("#FFFFFF"));
                ivClock.setVisibility(View.INVISIBLE);
            }

        return convertView;
    }
}
