package com.aroos.metacom.activities.others;

/**
 * Created by murilo on 05/09/2016.
 */
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aroos.metacom.R;
import com.aroos.metacom.activities.ActivityDiarioForm;
import com.aroos.metacom.activities.models.Estacao;

import java.util.ArrayList;

/**
 * Created by murilo on 04/09/2016.
 */
public class EstacoesAdapter extends ArrayAdapter<Estacao> {
    public EstacoesAdapter(Context context, ArrayList<Estacao> estacoes) {
        super(context, 0, estacoes);
    }
    public View cview;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Estacao estacao = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.simple_estacao_list_item, parent, false);
        }
        cview=convertView;
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.friend_name);
        TextView tvHome = (TextView) convertView.findViewById(R.id.friend_state);
        TextView tvBairro = (TextView) convertView.findViewById(R.id.friend_bairro);

        Button btIcon = (Button) convertView.findViewById(R.id.friend_profile_picture);
        // Populate the data into the template view using the data object
        btIcon.setText(estacao.getClasse().substring(0,1));

        Button btIcon2 = (Button) convertView.findViewById(R.id.friend_profile_picture2);
        // Populate the data into the template view using the data object
        btIcon2.setText(Integer.toString(estacao.listDiarioItem.size()));

        GradientDrawable gd = (GradientDrawable) btIcon.getBackground().getCurrent();
        //gd.setColor(Color.parseColor(atividade.getCor()));

        tvName.setText(estacao.getFornecedor() +" - " + estacao.getEstacao()  );
        tvHome.setText(estacao.getDescricao());
        tvBairro.setText(estacao.getTipos_obra() + " ( " + estacao.getLocalidade() + " )");

        // Return the completed view to render on screen

        ImageView mButton = (ImageView) convertView.findViewById(R.id.friend_online_imageview);
        if (estacao.getData_encerrado()!= null){
            mButton.setVisibility(View.GONE);
        }
        else{
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //attemptLogin();
                    Intent intent = new Intent(getContext() ,ActivityDiarioForm.class);
                    intent.putExtra("estacao", estacao);
                    getContext().startActivity(intent);
                }
            });
        }

        ImageView mButtonEnd = (ImageView) convertView.findViewById(R.id.imageView4);
        mButtonEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        return convertView;
    }
    private AlertDialog alerta;
    private void confirmaSalvar(View convertView)
    { //Cria o gerador do AlertDialog

    }
}