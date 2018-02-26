package com.aroos.metacom.activities.others;

/**
 * Created by murilo on 05/09/2016.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.aroos.metacom.R;
import com.aroos.metacom.activities.models.DiarioImagem;

import java.util.ArrayList;

/**
 * Created by murilo on 04/09/2016.
 */
public class ImagemAdapter extends ArrayAdapter<DiarioImagem> {
    public ImagemAdapter(Context context, ArrayList<DiarioImagem> dimagens) {
        super(context, 0, dimagens);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final DiarioImagem dimagens = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.simple_diario_imagem_item, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        // Lookup view for data population
        //TextView tvName = (TextView) convertView.findViewById(R.id.servico_item);

        //tvName.setText(servico.getCodigo() +"-" + servico.getDescricao() +" ("+ servico.getUnidade() + ")" );

        //holder.mWatcher.setActive(false);
        //Aluno aluno = (Aluno) getItem(position);

        Bitmap myBitmap = BitmapFactory.decodeFile(dimagens.getPath_file());
        holder.imagem.setImageBitmap(myBitmap);

       // holder.mWatcher.setActive(true);

        return convertView;
    }
    public class ViewHolder {

        final ImageView imagem;

        public ViewHolder(View view) {
            imagem = (ImageView) view.findViewById(R.id.imageView3);
            //valor = (EditText) view.findViewById(R.id.editText2);
        }

    }
}