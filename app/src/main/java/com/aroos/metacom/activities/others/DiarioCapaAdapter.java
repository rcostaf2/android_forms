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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.aroos.metacom.R;
import com.aroos.metacom.activities.models.Dcapa;

import java.util.ArrayList;

/**
 * Created by murilo on 04/09/2016.
 */
public class DiarioCapaAdapter extends ArrayAdapter<Dcapa> {
    public DiarioCapaAdapter(Context context, ArrayList<Dcapa> dcapas) {
        super(context, 0, dcapas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Dcapa dcapa = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.simple_diario_capa_item, parent, false);
            holder = new ViewHolder(convertView);

            holder.mWatcher = new MyTextWatcher();
            holder.valor.addTextChangedListener(holder.mWatcher);
            convertView.setTag(holder);
            holder.valor.setEnabled(true);
            if (dcapa.getDescricao().equals("ID")|| dcapa.getDescricao().equals("Endereço") || dcapa.getDescricao().equals("Obs") || dcapa.getDescricao().equals("OS/IONIX/INSTANCIA")  ){
                holder.valor.setEnabled(false);
            }
            if (dcapa.getDescricao().equals("Tipo de Obra")){
                holder.valor.setEnabled(false);
            }else {
                if (dcapa.getDescricao().equals("Armário (ARD/ARDO)") &&  dcapa.getValor().length()>0 ){
                    holder.valor.setEnabled(false);
                }
            }
        }
        else{
           // view = convertView;
            holder = (ViewHolder) convertView.getTag();
            holder.valor.setEnabled(true);
            if (dcapa.getDescricao().equals("ID")|| dcapa.getDescricao().equals("Endereço") || dcapa.getDescricao().equals("Obs") || dcapa.getDescricao().equals("OS/IONIX/INSTANCIA")){
                holder.valor.setEnabled(false);
            }
            if (dcapa.getDescricao().equals("Tipo de Obra")){
                holder.valor.setEnabled(false);
            }else {
                if (dcapa.getDescricao().equals("Armário (ARD/ARDO)") &&  dcapa.getValor().length()>0 ){
                    holder.valor.setEnabled(false);
                }

            }
        }
        holder.dcapa = dcapa;
        holder.mWatcher.setActive(false);
        holder.nome.setText(dcapa.getDescricao());
        holder.valor.setText(dcapa.getValor());
        holder.mWatcher.setDcapa(dcapa);
        holder.mWatcher.setActive(true);


        if(holder.dcapa.list.size()>0){
            holder.mShow=false;
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, dcapa.list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.list.setAdapter(dataAdapter);
            if (dcapa.getValor()!= null){
                for (int i = 0; i < holder.dcapa.list.size(); i++) {
                    String auxServ = holder.dcapa.list.get(i);
                    if (dcapa.getValor().toString().equals(auxServ)){
                        holder.list.setSelection(i);
                    }
                }
            }
        }
        else{
            holder.mShow = true;
        }
        holder.valor.setVisibility(holder.mShow ? View.VISIBLE : View.GONE);
        holder.list.setVisibility(holder.mShow ? View.GONE : View.VISIBLE);

        return convertView;
    }
    public class ViewHolder {

        final TextView nome;
        final TextView valor;
        final Spinner list;
        public Dcapa dcapa;
        public MyTextWatcher mWatcher;
        public boolean mShow = true;
        public ViewHolder(View view) {
            nome = (TextView) view.findViewById(R.id.capaItem);
            valor = (EditText) view.findViewById(R.id.editText);
            list = (Spinner) view.findViewById(R.id.editspinner);
            list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    dcapa.setValor(parentView.getItemAtPosition(position).toString());
                }

                public void onNothingSelected(AdapterView<?> parentView) {
                    // To do ...
                }

            });
        }

    }
    private class MyTextWatcher implements TextWatcher {

        private Dcapa mdcapa;
        private boolean mActive;

        void setActive(boolean active) {
            mActive = active;
        }

        void setDcapa(Dcapa dcapa) {
            mdcapa = dcapa;
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
                mdcapa.setValor(s.toString());
            }
        }
    }
}