package com.aroos.metacom.activities.others;

/**
 * Created by murilo on 05/09/2016.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.aroos.metacom.R;
import com.aroos.metacom.activities.MainActivity;
import com.aroos.metacom.activities.models.DiarioImagem;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;



/**
 * Created by murilo on 04/09/2016.
 */
public class DiarioImagemAdapter extends ArrayAdapter<DiarioImagem> {
    public DiarioImagemAdapter(Context context, ArrayList<DiarioImagem> diarioImagems) {
        super(context, 0, diarioImagems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final DiarioImagem diarioImagem = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.simple_diario_imagem_item, parent, false);
            holder = new ViewHolder(convertView);
            //holder.mWatcher = new MyTextWatcher();
            //holder.valor.addTextChangedListener(holder.mWatcher);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        // Lookup view for data population
        //TextView tvName = (TextView) convertView.findViewById(R.id.servico_item);

        //tvName.setText(servico.getCodigo() +"-" + servico.getDescricao() +" ("+ servico.getUnidade() + ")" );

//        holder.mWatcher.setActive(false);
        //Aluno aluno = (Aluno) getItem(position);
        //holder.nome.setText(servico.getCodigo() +"-" + servico.getDescricao() +" ("+ servico.getUnidade() + ")" );
        //holder.valor.setText(servico.getValor());
        holder.setDiarioImagem(diarioImagem);
       // holder.img.setImageBitmap(diarioImagem.getPhoto());
        try {
            // DB operation
            holder.img.setImageBitmap(diarioImagem.getPhoto());
        } catch (Exception e){
           // remove(diarioImagem);
            // throw new RuntimeException(e);
        }
        //holder.mWatcher.setDiarioImagem(diarioImagem);
        // holder.mWatcher.setActive(true);

        return convertView;
    }
    public class ViewHolder {

        //final TextView nome;
        //final TextView valor;
        final int pos=0;
        private DiarioImagem mdiarioImagem;
        final ImageView img;
        final ImageButton imgbt;
        public MyTextWatcher mWatcher;
        void setDiarioImagem(DiarioImagem dserv) {
            mdiarioImagem = dserv;
        }
        public ViewHolder(View view) {
            // nome = (TextView) view.findViewById(R.id.servico_item);
            //valor = (EditText) view.findViewById(R.id.editText2);

            img = (ImageView) view.findViewById(R.id.imageView3);
            imgbt = (ImageButton) view.findViewById(R.id.main_line_delete);
            imgbt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remove(mdiarioImagem);

                }
            });
        }

    }
    private class MyTextWatcher implements TextWatcher {
        private DiarioImagem mdiarioImagem;
        private boolean mActive;

        void setActive(boolean active) {
            mActive = active;
        }

        void setDiarioImagem(DiarioImagem dserv) {
            mdiarioImagem = dserv;
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //do nothing
        }
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //do nothing
            // BootstrapEditText txt = (BootstrapEditText) view.findViewById(R.id.text1);

            //return
        }
        public void afterTextChanged(Editable s) {
            // ;
            if(mActive) {
                //mdiarioImagem.setValor(s.toString());
            }
        }
    }
}