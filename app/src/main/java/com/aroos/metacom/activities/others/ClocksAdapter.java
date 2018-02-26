package com.aroos.metacom.activities.others;

/**
 * Created by murilo on 05/09/2016.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aroos.metacom.R;
import com.aroos.metacom.activities.models.Ponto;

import java.util.ArrayList;

/**
 * Created by murilo on 04/09/2016.
 */
public class ClocksAdapter extends ArrayAdapter<Ponto> {
    public ClocksAdapter(Context context, ArrayList<Ponto> pontos) {
        super(context, 0, pontos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Ponto ponto = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.simple_clock_list_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.friend_name);
        TextView tvHome = (TextView) convertView.findViewById(R.id.friend_state);

        tvName.setText( ponto.getData());
        tvHome.setText(ponto.getHora());
        if (ponto.getEnviado()!= null && ponto.getEnviado().equals("Sim")){
            tvHome.setText(ponto.getHora() + " (Sincronizado)");
        }


        // Return the completed view to render on screen


        return convertView;
    }
}