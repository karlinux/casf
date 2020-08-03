package com.aulaxalapa.casf.data;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.aulaxalapa.casf.retrofit.response.ResponseSector;

import java.util.List;

public class MySpinnerAdapter extends ArrayAdapter<ResponseSector> {

    private Context context;
    private List<ResponseSector> objects;
    private int resource;

    public MySpinnerAdapter(@NonNull Context context, int resource, List<ResponseSector> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
        this.resource = resource;
    }

    @Override
    public void add(ResponseSector object) {
        this.objects.add(object);
    }


    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public ResponseSector getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setText(objects.get(position).getSector());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setText(objects.get(position).getSector());
        return label;
    }
}
