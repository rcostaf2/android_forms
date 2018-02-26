package com.aroos.metacom.activities.others;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ImageButton;

import com.aroos.metacom.R;
import com.aroos.metacom.activities.ActivityFinishOs;
import com.aroos.metacom.activities.models.Atividade;
import com.aroos.metacom.activities.models.DbHelper;
import com.aroos.metacom.activities.services.GPSTracker;

import java.util.List;
import java.util.Vector;

public class MainActivityOsDetail extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    static private SectionsPagerAdapter mSectionsPagerAdapter;
    public List<String> fragments = new Vector<String>();
    private Context context;
    static private DbHelper dbHelper  ;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    static public Atividade atividade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_os_detail);
        Bundle extras = getIntent().getExtras();
        atividade = new Atividade();
        dbHelper = new DbHelper(this);
        Intent intentIncoming = getIntent();
        if (extras != null) {
            atividade = (Atividade) getIntent().getSerializableExtra("atividade");// OK
        }
        atividade = dbHelper.getAtividadeById(atividade.getId());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        this.context = this;
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmaSalvar();
               // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
               //         .setAction("Action", null).show();
            }
        });

    }

    private AlertDialog alerta;
    private void confirmaSalvar()
    { //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Encerrar OS");
        //define a mensagem
        String str = "Deseja concluir a OS?";
        builder.setMessage(str);
        //define um botão como positivo
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1)
            {
                    Intent intent = new Intent(getBaseContext(), ActivityFinishOs.class);
                    intent.putExtra("atividade", atividade);
                    startActivity(intent);

            } });
        //define um botão como negativo.
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener()
                { public void onClick(DialogInterface arg0, int arg1) {
                } }
        );
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_os_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_activity_os_detail, container, false);
            EditText editTextOS = (EditText) rootView.findViewById(R.id.editTextOs);
            editTextOS.setText(atividade.getNr_os());
            EditText editTextDesign = (EditText) rootView.findViewById(R.id.editTextDesign);
            editTextDesign.setText(atividade.getDesiguinacao());
            EditText editTextTipo = (EditText) rootView.findViewById(R.id.editTextTipo);
            editTextTipo.setText(atividade.getTipo());
            EditText editTextDefeito = (EditText) rootView.findViewById(R.id.editTextDefeito);
            editTextDefeito.setText(atividade.getDefeito_reclamado());
            EditText editTextAbertura = (EditText) rootView.findViewById(R.id.editTextAbertura);
            editTextAbertura.setText(atividade.getAbertura());
            EditText editTextAgendamento = (EditText) rootView.findViewById(R.id.editTextAgendamento);
            editTextAgendamento.setText(atividade.getAgendamento());
            EditText editTextPeriodo = (EditText) rootView.findViewById(R.id.editTextPeriodo);
            editTextPeriodo.setText(atividade.getPeriodo());
            EditText editTextSolicitante = (EditText) rootView.findViewById(R.id.editTextSolicitante);
            editTextSolicitante.setText(atividade.getSolicitante());
            EditText editTextCliente = (EditText) rootView.findViewById(R.id.editTextCliente);
            editTextCliente.setText(atividade.getCliente());
            EditText editTextTelefone = (EditText) rootView.findViewById(R.id.editTextTelefone);
            editTextTelefone.setText(atividade.getTelefone());
            EditText editTextCelular = (EditText) rootView.findViewById(R.id.editTextCelular);
            editTextCelular.setText(atividade.getCelular());
            EditText editTextObsA = (EditText) rootView.findViewById(R.id.editTextObservacaoAbertura);
            editTextObsA.setText(atividade.getObservacao_abertura());
            EditText editTextAtendente = (EditText) rootView.findViewById(R.id.editTextObservacaoAtendente);
            editTextAtendente.setText(atividade.getAtendente());

            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            //textView.setText("112131");
            return rootView;
        }
    }

    public static class PlaceholderEndFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderEndFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderEndFragment newInstance(int sectionNumber) {
            PlaceholderEndFragment fragment = new PlaceholderEndFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_activity_os_detail2, container, false);
            //EditText editTextTipo = (EditText) rootView.findViewById(R.id.editTextTipo);
           // editTextTipo.setText(atividade.getTipo_logr());
            EditText editTextLogr = (EditText) rootView.findViewById(R.id.editTextLogr);
            editTextLogr.setText(atividade.getLogradouro());

          //  EditText editTextNumero = (EditText) rootView.findViewById(R.id.editTextNumero);
         //   editTextNumero.setText(atividade.getNumero());
         //   EditText editTextQuadra = (EditText) rootView.findViewById(R.id.editTextQuadra);
         //   editTextQuadra.setText(atividade.getQuadra());
         //   EditText editTextComplemento = (EditText) rootView.findViewById(R.id.editTextComplemento);
          //  editTextComplemento.setText(atividade.getComplemento());
            EditText editTextBairro = (EditText) rootView.findViewById(R.id.editTextBairro);
            editTextBairro.setText(atividade.getBairro());
            EditText editTextCidade = (EditText) rootView.findViewById(R.id.editTextCidade);
            editTextCidade.setText(atividade.getCidade());
            EditText editTextUf = (EditText) rootView.findViewById(R.id.editTextUf);
            editTextUf.setText(atividade.getUf());
            EditText editTextreferencia = (EditText) rootView.findViewById(R.id.editTextreferencia);
            editTextreferencia.setText(atividade.getReferencia());

            return rootView;
        }
    }

    public static class PlaceholderDTFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderDTFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderDTFragment newInstance(int sectionNumber) {
            PlaceholderDTFragment fragment = new PlaceholderDTFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_main_activity_os_detail3, container, false);
            EditText editTextPlano = (EditText) rootView.findViewById(R.id.editTextPlano);
            editTextPlano.setText(atividade.getPlano());


            final EditText editTextIP = (EditText) rootView.findViewById(R.id.editTextIP);
            editTextIP.setText(atividade.getIp());
            editTextIP.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus) {
                        //SAVE THE DATA
                        atividade.setIp(editTextIP.getText().toString());
                      //  dbHelper.insertAtividade(atividade);
                    }

                }
            });
            final EditText editTextMAC = (EditText) rootView.findViewById(R.id.editTextMAC);
            editTextMAC.setText(atividade.getMac());
            editTextMAC.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus) {
                        //SAVE THE DATA
                        atividade.setIp(editTextMAC.getText().toString());
                       // dbHelper.insertAtividade(atividade);
                    }

                }
            });
            EditText editTextLogin = (EditText) rootView.findViewById(R.id.editTextLogin);
            editTextLogin.setText(atividade.getLogin());
            EditText editTextSenha = (EditText) rootView.findViewById(R.id.editTextSenha);
            editTextSenha.setText(atividade.getSenha());
            final EditText editTextInterface = (EditText) rootView.findViewById(R.id.editTextInterface);
            editTextInterface.setText(atividade.getInterface_());
            editTextInterface.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus) {
                        //SAVE THE DATA
                        atividade.setIp(editTextInterface.getText().toString());
                        //dbHelper.insertAtividade(atividade);
                    }

                }
            });
            final EditText editTextpropEquip = (EditText) rootView.findViewById(R.id.editTextpropEquip);
            editTextpropEquip.setText(atividade.getProp_equipamento());
            editTextpropEquip.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus) {
                        //SAVE THE DATA
                        atividade.setIp(editTextpropEquip.getText().toString());
                        //dbHelper.insertAtividade(atividade);
                    }

                }
            });
            final EditText editTextconexao = (EditText) rootView.findViewById(R.id.editTextconexao);
            editTextconexao.setText(atividade.getConexao());
            editTextconexao.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus) {
                        //SAVE THE DATA
                        atividade.setIp(editTextconexao.getText().toString());
                        //dbHelper.insertAtividade(atividade);
                    }

                }
            });
            final EditText editTextcabo = (EditText) rootView.findViewById(R.id.editTextcabo);
            editTextcabo.setText(atividade.getNr_cabo());
            editTextcabo.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus) {
                        //SAVE THE DATA
                        atividade.setIp(editTextcabo.getText().toString());
                        //dbHelper.insertAtividade(atividade);
                    }

                }
            });
            final EditText editTexteletrico = (EditText) rootView.findViewById(R.id.editTexteletrico);
            editTexteletrico.setText(atividade.getPar_eletrico());
            editTexteletrico.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus) {
                        //SAVE THE DATA
                        atividade.setIp(editTexteletrico.getText().toString());
                        //dbHelper.insertAtividade(atividade);
                    }

                }
            });
            final EditText editTextServidor = (EditText) rootView.findViewById(R.id.editTextServidor);
            editTextServidor.setText(atividade.getServidor());
            editTextServidor.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus) {
                        //SAVE THE DATA
                        atividade.setIp(editTextServidor.getText().toString());
                        //dbHelper.insertAtividade(atividade);
                    }

                }
            });
            final EditText editTextpop = (EditText) rootView.findViewById(R.id.editTextpop);
            editTextpop.setText(atividade.getPop());
            editTextpop.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus) {
                        //SAVE THE DATA
                        atividade.setIp(editTextpop.getText().toString());
                        //dbHelper.insertAtividade(atividade);
                    }

                }
            });
            final EditText editTextSwitch = (EditText) rootView.findViewById(R.id.editTextSwitch);
            editTextSwitch.setText(atividade.getPop());
            editTextSwitch.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus) {
                        //SAVE THE DATA
                        atividade.setIp(editTextSwitch.getText().toString());
                        //dbHelper.insertAtividade(atividade);
                    }

                }
            });
            final EditText editTextpopLat = (EditText) rootView.findViewById(R.id.editTextpopLat);
            editTextpopLat.setText(atividade.getLat_porta());

            final EditText editTextpopLong = (EditText) rootView.findViewById(R.id.editTextpopLong);
            editTextpopLong.setText(atividade.getLong_porta());
            final EditText editTextswitchLat = (EditText) rootView.findViewById(R.id.editTextswitchLat);
            editTextswitchLat.setText(atividade.getLat_switch());
            final EditText editTextswitchLong = (EditText) rootView.findViewById(R.id.editTextswitchLong);
            editTextswitchLong.setText(atividade.getLong_switch());


            final EditText editTextPorta = (EditText) rootView.findViewById(R.id.editTextPorta);
            editTextPorta.setText(atividade.getPop());
            editTextPorta.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus) {
                        //SAVE THE DATA
                        atividade.setIp(editTextPorta.getText().toString());
                        dbHelper.insertAtividade(atividade);
                    }

                }
            });
            ImageButton popB = (ImageButton) rootView.findViewById(R.id.imageButtonPop);
            popB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GPSTracker gps = new GPSTracker(getContext());

                    if(gps.canGetLocation()){

                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();
                        editTextpopLat.setText(Double.toString((latitude)));
                        editTextpopLong.setText(Double.toString((longitude)));
                        //atividade.setLat_porta(editTextpopLat.getText().toString());
                        //atividade.setLong_porta(editTextpopLong.getText().toString());
                        //dbHelper.insertAtividade(atividade);
                        // \n is for new line
                        //Toast.makeText(getApplicationContext(), "Voce esta em - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                    }else{
                        // can't get location
                        // GPS or Network is not enabled
                        // Ask user to enable GPS/network in settings
                        gps.showSettingsAlert();
                    }
                }
            });
            ImageButton switchB = (ImageButton) rootView.findViewById(R.id.imageButtonSwitch);
            switchB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GPSTracker gps = new GPSTracker(getContext());

                    if(gps.canGetLocation()){

                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();
                        editTextswitchLat.setText(Double.toString((latitude)));
                        editTextswitchLong.setText(Double.toString((longitude)));
                       // atividade.setLat_switch(editTextswitchLat.getText().toString());
                        //atividade.setLong_switch(editTextswitchLong.getText().toString());
                        //dbHelper.insertAtividade(atividade);
                        // \n is for new line
                        //Toast.makeText(getApplicationContext(), "Voce esta em - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                    }else{
                        // can't get location
                        // GPS or Network is not enabled
                        // Ask user to enable GPS/network in settings
                        gps.showSettingsAlert();
                    }
                }
            });
            FloatingActionButton fabsave = (FloatingActionButton) rootView.findViewById(R.id.fabsave);
            fabsave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    atividade.setIp(editTextIP.getText().toString());
                    atividade.setMac(editTextMAC.getText().toString());
                    atividade.setInterface_(editTextInterface.getText().toString());
                    atividade.setProp_equipamento(editTextpropEquip.getText().toString());
                    atividade.setConexao(editTextconexao.getText().toString());
                    atividade.setNr_cabo(editTextcabo.getText().toString());
                    atividade.setPar_eletrico(editTexteletrico.getText().toString());
                    atividade.setServidor(editTextServidor.getText().toString());
                    atividade.setPop(editTextpop.getText().toString());
                    atividade.setSwit(editTextSwitch.getText().toString());
                    atividade.setLat_porta(editTextpopLat.getText().toString());
                    atividade.setLong_porta(editTextpopLong.getText().toString());
                    atividade.setLat_switch(editTextswitchLat.getText().toString());
                    atividade.setLong_switch(editTextswitchLong.getText().toString());
                    atividade.setPorta(editTextPorta.getText().toString());
                    confirmaSalvarAtividade();
                    // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    //         .setAction("Action", null).show();
                }
            });
            return rootView;
        }
        private AlertDialog alerta2;
        private void confirmaSalvarAtividade()
        { //Cria o gerador do AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            //define o titulo
            builder.setTitle("Salvar OS");
            //define a mensagem
            String str = "Deseja Salvar Dados Técnicos da OS?";
            builder.setMessage(str);
            //define um botão como positivo
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1)
                {

                    dbHelper.insertAtividade(atividade);
                } });
            //define um botão como negativo.
            builder.setNegativeButton("Não", new DialogInterface.OnClickListener()
                    { public void onClick(DialogInterface arg0, int arg1) {
                    } }
            );
            //cria o AlertDialog
            alerta2 = builder.create();
            //Exibe
            alerta2.show();
        }

    }
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public List<String> fragmentsA;
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentsA = fragments;
            fragments.add(PlaceholderFragment.class.getName());
            fragments.add(PlaceholderEndFragment.class.getName());
            fragments.add(PlaceholderDTFragment.class.getName());
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return Fragment.instantiate(getBaseContext(), fragments.get(position));


            //return Fragment.instantiate(context, fragmentsA.get(position));
           // return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "DADOS DA OS";
                case 1:
                    return "ENDEREÇO";
                case 2:
                    return "DADOS TÉCNICOS";
                //case 3:
                 //   return "ENCERRAMENTO";
            }
            return null;
        }
    }
}
