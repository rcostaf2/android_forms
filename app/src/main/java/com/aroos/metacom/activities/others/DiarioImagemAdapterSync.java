package com.aroos.metacom.activities.others;

/**
 * Created by murilo on 05/09/2016.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;


/**
 * Created by murilo on 04/09/2016.
 */
public class DiarioImagemAdapterSync extends ArrayAdapter<DiarioImagem> {
    private static final int ATTACH_REQUEST = 1234;
    public static final int RESULT_OK           = -1;
    public DiarioImagemAdapterSync(Context context, ArrayList<DiarioImagem> diarioImagems) {
        super(context, 0, diarioImagems);

    }
    Context mContext;
    View mconvertView;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        mContext = parent.getContext();
        mconvertView =convertView;

        final DiarioImagem diarioImagem = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.simple_diario_imagem_item_sync, parent, false);
            holder = new ViewHolder(convertView, mContext);
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

        try {
            // DB operation

        } catch (Exception e){
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
        final Context mContext;
        final View mView;
        final TextView mtextView1;
        final TextView mtextView2;

        void setDiarioImagem(DiarioImagem dserv) {
            mdiarioImagem = dserv;
            img.setImageBitmap(dserv.getPhoto());
            if( mdiarioImagem.getIs_correct() ){
                imgbt.setVisibility(View.GONE);
            }
            mtextView1.setText(dserv.getTipoAttach()); //set text for text view
            mtextView2.setText(dserv.getComplemento()); //set text for text view

        }
        public ViewHolder(View view, final Context vmContext) {
            this.mContext = vmContext;
            this.mView = view;
            // nome = (TextView) view.findViewById(R.id.servico_item);
            //valor = (EditText) view.findViewById(R.id.editText2);

            img = (ImageView) view.findViewById(R.id.imageView3);

            imgbt = (ImageButton) view.findViewById(R.id.main_line_delete);
            mtextView1 = (TextView)view.findViewById(R.id.text_view_id);

            mtextView2 = (TextView)view.findViewById(R.id.text_view_id2);



//            imgbt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    remove(mdiarioImagem);
//
//                }
//            });
            //Button getAttach = (Button) findViewById(R.id.attach);

            imgbt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    //intent.putExtra("position", getPosition(mdiarioImagem));
                    MainActivity.mMyAppsBundle.putString("position", String.valueOf(getPosition(mdiarioImagem)));
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    ((Activity) mContext).startActivityForResult(Intent.createChooser(intent,
                            "Escolha uma imagem"), ATTACH_REQUEST);

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