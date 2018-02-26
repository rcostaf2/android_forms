package com.aroos.metacom.activities.others;

/**
 * Created by murilo on 05/09/2016.
 */
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.aroos.metacom.R;
import com.aroos.metacom.activities.models.Servico;

import java.util.ArrayList;

/**
 * Created by murilo on 04/09/2016.
 */
public class ServicosAdapter extends ArrayAdapter<Servico> {
    public ServicosAdapter(Context context, ArrayList<Servico> servicos) {
        super(context, 0, servicos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        final Servico servico = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.simple_diario_servico_item, parent, false);
            holder = new ViewHolder(convertView);
            holder.mWatcher = new MyTextWatcher();
            holder.valor.addTextChangedListener(holder.mWatcher);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        // Lookup view for data population
        //TextView tvName = (TextView) convertView.findViewById(R.id.servico_item);

        //tvName.setText(servico.getCodigo() +"-" + servico.getDescricao() +" ("+ servico.getUnidade() + ")" );

        holder.mWatcher.setActive(false);
        //Aluno aluno = (Aluno) getItem(position);
        holder.nome.setText(servico.getCodigo() +"-" + servico.getDescricao() +" ("+ servico.getUnidade() + ")" );
        holder.valor.setText(servico.getValor());
        holder.mWatcher.setServico(servico);
        holder.mWatcher.setActive(true);

        return convertView;
    }
    public class ViewHolder {

        final TextView nome;
        final TextView valor;
        public MyTextWatcher mWatcher;
        public ViewHolder(View view) {
            nome = (TextView) view.findViewById(R.id.servico_item);
            valor = (EditText) view.findViewById(R.id.editText2);
        }

    }

    private class MyTextWatcher implements TextWatcher {
        private Servico mservico;
        private boolean mActive;

        void setActive(boolean active) {
            mActive = active;
        }

        void setServico(Servico dserv) {
            mservico = dserv;
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
                mservico.setValor(s.toString());
            }
        }
    }

}