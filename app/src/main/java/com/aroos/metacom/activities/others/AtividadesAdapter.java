package com.aroos.metacom.activities.others;

/**
 * Created by murilo on 05/09/2016.
 */
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aroos.metacom.R;
import com.aroos.metacom.activities.models.Atividade;

import java.util.ArrayList;

/**
 * Created by murilo on 04/09/2016.
 */
public class AtividadesAdapter extends ArrayAdapter<Atividade> {
    public AtividadesAdapter(Context context, ArrayList<Atividade> atividades) {
        super(context, 0, atividades);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Atividade atividade = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.simple_main_list_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.friend_name);
        TextView tvHome = (TextView) convertView.findViewById(R.id.friend_state);
        TextView tvBairro = (TextView) convertView.findViewById(R.id.friend_bairro);
        TextView tvDate = (TextView) convertView.findViewById(R.id.friend_date);
        TextView tvTurno = (TextView) convertView.findViewById(R.id.friend_turno);

        Button btIcon = (Button) convertView.findViewById(R.id.friend_profile_picture);
        // Populate the data into the template view using the data object
        btIcon.setText(atividade.getTipo().substring(0,1));
        GradientDrawable gd = (GradientDrawable) btIcon.getBackground().getCurrent();
        gd.setColor(Color.parseColor(atividade.getCor()));

        tvName.setText("OS" + atividade.getNr_os() + " - " + atividade.getTipo() );
        tvHome.setText(atividade.getCliente() + " " + atividade.getCelular());
        tvBairro.setText(atividade.getBairro() + " - "  + atividade.getCidade());
        tvDate.setText(atividade.getAgendamento());
        tvTurno.setText(atividade.getPeriodo());
        // Return the completed view to render on screen

        ImageView mButton = (ImageView) convertView.findViewById(R.id.friend_online_imageview);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //attemptLogin();

               Intent intent = new Intent(getContext() ,MainActivityOsDetail.class);
               intent.putExtra("atividade", atividade);

                getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}