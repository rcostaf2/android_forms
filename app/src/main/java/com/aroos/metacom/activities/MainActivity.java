package com.aroos.metacom.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aroos.metacom.R;
import com.aroos.metacom.activities.apis.AtividadeApi;
import com.aroos.metacom.activities.apis.AtividadeEndcApi;
import com.aroos.metacom.activities.apis.AtividadeSyncApi;
import com.aroos.metacom.activities.apis.DcapaListSyncApi;
import com.aroos.metacom.activities.apis.DcapaSyncApi;
import com.aroos.metacom.activities.apis.DservicoSyncApi;
import com.aroos.metacom.activities.apis.EstacaoSyncApi;
import com.aroos.metacom.activities.apis.PontoEndcApi;
import com.aroos.metacom.activities.apis.VersaoSyncApi;
import com.aroos.metacom.activities.models.Atividade;
import com.aroos.metacom.activities.models.DbHelper;
import com.aroos.metacom.activities.models.Dcapa;
import com.aroos.metacom.activities.models.DcapaList;
import com.aroos.metacom.activities.models.Despacho;
import com.aroos.metacom.activities.models.Estacao;
import com.aroos.metacom.activities.models.Ponto;
import com.aroos.metacom.activities.models.Servico;
import com.aroos.metacom.activities.models.User;
import com.aroos.metacom.activities.others.AtividadesAdapter;
import com.aroos.metacom.activities.services.GPSTracker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    public User user = null;
    private DbHelper dbHelper;
    private Toolbar toolbar;
    private ListView mlview;
    private LinearLayout mllin_lay;
    private TextView txtmsg;
    private TextView splashmsgsync;
    private ProgressBar mProgressBar2;

    private static final int USER_FUNC = 5001;
    private static final int CLOCK_FUNC = 5002;
    private static final int DIARIO_FUNC = 5003;
    private static  int external_user_id;
    public static Bundle mMyAppsBundle = new Bundle();
    private static final String VERSAO = "alpha";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: Move this to where you establish a user session


        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);


        Bundle extras = getIntent().getExtras();
        //user = new User();

        Intent intentIncoming = getIntent();
        if (extras != null) {
           User user_gt = (User) getIntent().getSerializableExtra("user");// OK
            external_user_id = user_gt.getExternal_id();
            user =  dbHelper.getUserByExternalId(user_gt.getExternal_id());
        }
        else{
            user =  dbHelper.getUserByExternalId(external_user_id);
        }


        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(user.toString());
        //getSupportActionBar().setIcon(R.drawable.ico2);

        mlview= (ListView) findViewById(R.id.listView);
        mllin_lay= (LinearLayout) findViewById(R.id.lin_lay2);
        refreshListView("");
        ArrayList<Ponto> arrayOfPontos = dbHelper.selectPontos(user.getExternal_id(),false);
        if (arrayOfPontos.size() == 0){
            confirmaPonto();
        }
        txtmsg = (TextView) findViewById(R.id.splashmsg);
        if (user.getPerfil()== 11){
            txtmsg.setText("Bem Vindo ao Sistema AROOS");
        }
        splashmsgsync = (TextView) findViewById(R.id.splashmsgsync);
        mProgressBar2 = (ProgressBar) findViewById(R.id.progressBar2);

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Verifica se já mostramos o alerta e o usuário negou na 1ª vez.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Caso o usuário tenha negado a permissão anteriormente, e não tenha marcado o check "nunca mais mostre este alerta"
                // Podemos mostrar um alerta explicando para o usuário porque a permissão é importante.
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
            } else {
                // Solicita a permissão
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
            }
        } else {
            // Tudo OK, podemos prosseguir.
        }
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Verifica se já mostramos o alerta e o usuário negou na 1ª vez.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Caso o usuário tenha negado a permissão anteriormente, e não tenha marcado o check "nunca mais mostre este alerta"
                // Podemos mostrar um alerta explicando para o usuário porque a permissão é importante.
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            } else {
                // Solicita a permissão
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        } else {
            // Tudo OK, podemos prosseguir.
        }
        sincronizar();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)      {
                    // Usuário aceitou a permissão!
                } else {
                    // Usuário negou a permissão.
                    // Não podemos utilizar esta funcionalidade.
                    finish();
                }
                return;
            }
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)      {
                    // Usuário aceitou a permissão!
                } else {
                    // Usuário negou a permissão.
                    // Não podemos utilizar esta funcionalidade.
                    finish();
                }
                return;
            }
        }
    }
    private AlertDialog alerta;
    private void confirmaPonto()
    { //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Controle de Ponto");
        //define a mensagem
        String str = "Deseja abrir o Controle de Ponto para iniciar o dia?";
        builder.setMessage(str);
        //define um botão como positivo
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1)
            {
                Intent intent = new Intent(MainActivity.this, ClockTimeActivity.class);
                intent.putExtra("user", user);
                startActivityForResult(intent, CLOCK_FUNC );

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
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        refreshListView(newText);
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (user.getPerfil()==11)
        {
            getMenuInflater().inflate(R.menu.menu_main_clock, menu);

        }
        else{
            getMenuInflater().inflate(R.menu.menu_main, menu);

//            //Pega o Componente.
//            SearchView mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
//            LinearLayout ll = (LinearLayout)mSearchView.getChildAt(0);
//            LinearLayout ll2 = (LinearLayout)ll.getChildAt(2);
//            //ImageView searchHintIcon = (ImageView) ll.getChildAt(0);
//            //searchHintIcon.setImageResource(R.drawable.ic_youtube_searched_for_white_24dp);
//            ImageView searchHintIcon2 = (ImageView) ll.getChildAt(1);
//            searchHintIcon2.setImageResource(R.drawable.ic_youtube_searched_for_white_18dp);
//
//            LinearLayout ll3 = (LinearLayout)ll2.getChildAt(1);
//    //        SearchView.SearchAutoComplete autoComplete = (SearchView.SearchAutoComplete)ll3.getChildAt(0);
//            ImageView searchHintIcon3 = (ImageView) ll3.getChildAt(1);
//            searchHintIcon3.setImageResource(R.drawable.ic_close_white_24dp);

    //// set the hint text color
    //        autoComplete.setHintTextColor(Color.WHITE);
    //        autoComplete.setTextColor(Color.WHITE);

            //Define um texto de ajuda:
//            mSearchView.setQueryHint("Pesquisa");
//
//            mSearchView.setOnQueryTextListener(this);
        }
//        int searchHintBtnId = mSearchView.getContext().getResources()
//                .getIdentifier("app:id/search_mag_icon", null, null);
//
//        int closeBtnId = mSearchView.getContext()
//                .getResources()
//                .getIdentifier("app:id/search_close_btn", null, null);
//        // Set the search plate color to white
//
//
//        ImageView closeIcon = (ImageView) mSearchView.findViewById(closeBtnId);
//        closeIcon.setImageResource(R.drawable.ic_close_white_24dp);
//
//        ImageView searchHintIcon = (ImageView) mSearchView.findViewById(searchHintBtnId);
//        searchHintIcon.setImageResource(R.drawable.ic_youtube_searched_for_white_24dp);


        // exemplos de utilização:

        return true;
    }
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (NoSuchMethodException e) {
                    Log.e("Menu =>", "Error icon menu", e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    private void AtividadeSync(){
        //Here we will handle the http request to insert user to mysql db
        //Creating a RestAdapter
        System.setProperty("http.keepAlive", "false");
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(dbHelper.ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        AtividadeApi api = adapter.create(AtividadeApi.class);

        //Defining the method insertuser of our interface
        api.getAtividades(
                //Passing the values by getting it from editTexts
                Integer.toString(user.getExternal_id()),
                //Creating an anonymous callback
                new Callback<List<Atividade>>() {
                    @Override
                    public void success(List<Atividade> list, Response response) {
                        //Dismissing the loading progressbar
                        //loading.dismiss();

                        //Storing the data in our list
                        String atividades = "0";
                        int recebidos=0;
                        dbHelper.cleanStatusAtiv(user.getExternal_id());
                        for (int i = 0; i < list.size(); i++) {
                            //Storing names to string array
                            Atividade a = list.get(i);
                            Atividade ativUp = dbHelper.getAtividadeByUserandId(a.getId(),user.getExternal_id());
                            if(ativUp==null){
                                dbHelper.cleanAtividade(a.getId());
                                //a.setUser_id(user.getId());
                                dbHelper.insertAtividade(a);
                                atividades += "," + Integer.toString(a.getId());
                                recebidos += 1;
                            }

                        }
                        dbHelper.cleanStatusDelAtiv(user.getExternal_id());
                        //Calling a method to show the list
                        showAtividades("");
                        if (recebidos>0) {
                            Toast.makeText(getApplicationContext(), "Sincronizando  " + Integer.toString(recebidos) + " OSs",
                                    Toast.LENGTH_SHORT).show();
                        }
                        returnAtividades(atividades ,recebidos);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //you can handle the errors here
                        Toast.makeText(getApplicationContext(), "Problema de conexão com o servidor. Tente novamente. ",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
    private void VersaoSyncAtividade(){
        //Here we will handle the http request to insert user to mysql db
        //Creating a RestAdapter
        System.setProperty("http.keepAlive", "false");
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(dbHelper.ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        VersaoSyncApi api = adapter.create(VersaoSyncApi.class);

        //Defining the method insertuser of our interface
        api.testVersao(
                //Passing the values by getting it from editTexts

                Integer.toString(user.getExternal_id()),
                VERSAO,
                //Creating an anonymous callback
                new Callback<String>() {
                    @Override
                    public void success(String list, Response response) {
                        //Dismissing the loading progressbar
                        //loading.dismiss();
                        //Storing the data in our list
                        if (list.equals("Sincronizado")){

                            AtividadeSync();
                        }
                        else{
                            downloadApk();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //you can handle the errors here
                        Toast.makeText(getApplicationContext(), "Problema de conexão com o servidor. Tente novamente. ",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
    private void VersaoSync(){
        //Here we will handle the http request to insert user to mysql db
        //Creating a RestAdapter
        System.setProperty("http.keepAlive", "false");
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(dbHelper.ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        VersaoSyncApi api = adapter.create(VersaoSyncApi.class);

        //Defining the method insertuser of our interface
        api.testVersao(
                //Passing the values by getting it from editTexts

                Integer.toString(user.getExternal_id()),
                VERSAO,
                //Creating an anonymous callback
                new Callback<String>() {
                    @Override
                    public void success(String list, Response response) {
                        //Dismissing the loading progressbar
                        //loading.dismiss();
                        //Storing the data in our list
                        if (list.equals("Sincronizado")){
                            sincronizar();
                        }
                        else{
                            downloadApk();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //you can handle the errors here
                        Toast.makeText(getApplicationContext(), "Problema de conexão com o servidor. Tente novamente. ",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void PontoEndSync(final Ponto ponto){
        //Here we will handle the http request to insert user to mysql db
        //Creating a RestAdapter



        System.setProperty("http.keepAlive", "false");
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(dbHelper.ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        PontoEndcApi api = adapter.create(PontoEndcApi.class);

        //Defining the method insertuser of our interface
        api.send_data(
                //Passing the values by getting it from editTexts
                ponto.getData(),ponto.getHora(),
                Integer.toString(ponto.getTecnico_id()),
                ponto.getAcao_hora(),
                ponto.getLatitude(),
                ponto.getLongitude(),
                //Creating an anonymous callback
                new Callback<String>() {
                    @Override
                    public void success(String list, Response response) {
                        //Dismissing the loading progressbar
                        //loading.dismiss();
                        //Storing the data in our list
                        dbHelper.updatePonto(ponto.getId());


                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //you can handle the errors here
                        Toast.makeText(getApplicationContext(), "Problema de conexão com o servidor. Tente novamente. ",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void AtividadeEndSync(final Atividade atividade){
        //Here we will handle the http request to insert user to mysql db
        //Creating a RestAdapter
        user =  dbHelper.getUserByExternalId(user.getExternal_id());
        if ((user.getPath_assinatura() == null || user.getPath_assinatura().isEmpty()) ) {
            Toast.makeText(getApplicationContext(), "Favor preencher a assinatura do Usuário ",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            Despacho desp = dbHelper.getDespachoByAtividadeId(atividade.getId());
            System.setProperty("http.keepAlive", "false");
            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint(dbHelper.ROOT_URL) //Setting the Root URL
                    .build(); //Finally building the adapter

            //Creating object for our interface
            AtividadeEndcApi api = adapter.create(AtividadeEndcApi.class);

            //Defining the method insertuser of our interface
            api.send_data(
                    //Passing the values by getting it from editTexts
                    user.getEcondedFile(),
                    desp.getEcondedFile(),
                    Integer.toString(user.getExternal_id()),
                    Integer.toString(atividade.getId()),
                    atividade.getIp(),
                    atividade.getMac(),
                    atividade.getInterface_(),
                    atividade.getProp_equipamento(),
                    atividade.getConexao(),
                    atividade.getNr_cabo(),
                    atividade.getPar_eletrico(),
                    atividade.getServidor(),
                    atividade.getPop(),
                    atividade.getPorta(),
                    atividade.getSwit(),
                    atividade.getLat_porta(),
                    atividade.getLong_porta(),
                    atividade.getLat_switch(),
                    atividade.getLong_switch(),

                    desp.getAcao(),
                    desp.getAcao_hora(),
                    desp.getDocumento(),
                    desp.getObservacao(),
                    desp.getLatitude(),
                    desp.getLongitude(),
                    desp.getReparo_efetuado(),
                    Integer.toString(desp.getAtividade_id()),
                    desp.getContato_local(),
                    //Creating an anonymous callback
                    new Callback<String>() {
                        @Override
                        public void success(String list, Response response) {
                            //Dismissing the loading progressbar
                            //loading.dismiss();

                            //Storing the data in our list
                            Toast.makeText(getApplicationContext(), list + " " + atividade.getNr_os(),
                                    Toast.LENGTH_SHORT).show();
                            dbHelper.cleanAtividade(atividade.getId());
                            //Calling a method to show the list
                            showAtividades("");
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            //you can handle the errors here
                            Toast.makeText(getApplicationContext(), "Problema de conexão com o servidor. Tente novamente. ",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        }
    }
    public  void returnAtividades(String atividades, int tam) {
        if (tam==0){
            Toast.makeText(getApplicationContext(), "Não existe novas OSs no servidor para o seu Usuário. ",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            System.setProperty("http.keepAlive", "false");
            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint(dbHelper.ROOT_URL) //Setting the Root URL
                    .build(); //Finally building the adapter

            //Creating object for our interface
            AtividadeSyncApi api = adapter.create(AtividadeSyncApi.class);

            //Defining the method insertuser of our interface
            api.returnAtividades(
                    //Passing the values by getting it from editTexts
                    Integer.toString(user.getExternal_id()),
                    atividades,
                    //Creating an anonymous callback
                    new Callback<String>() {
                        @Override
                        public void success(String msg, Response response) {
                            //Dismissing the loading progressbar
                            //loading.dismiss();

                            //Storing the data in our list

                            Toast.makeText(getApplicationContext(), msg ,
                                    Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            //you can handle the errors here
                            Toast.makeText(getApplicationContext(), "Problema de conexão com o servidor. Tente novamente. ",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        }
    }
    public  void showAtividades(String newText){
        boolean show = false;
        // Construct the data source
        ArrayList<Atividade> arrayOfAtividades = dbHelper.selectAtividades(user.getExternal_id(),newText);
        //ArrayList<Atividade> arrayOfAtividades = dbHelper.selectAtividades(2,newText);
        /*
        ArrayList<Atividade> arrayOfAtividades = new ArrayList<Atividade>();
        // Create the adapter to convert the array to views
        Atividade ativ = new Atividade(0,0,"386474", "Reparo","Jão da Silva","05/09/2016","Tarde","#59d5fe");
        arrayOfAtividades.add(ativ);
        Atividade ativ2 = new Atividade(0,0,"386474", "Conserto","Antonio Masnafd","03/09/2016","Tarde","#59d5fe");
        arrayOfAtividades.add(ativ2);
        Atividade ativ3 = new Atividade(0,0,"386474", "Reparo","Jose Alves","03/09/2016","Manha","#ff0404");
        arrayOfAtividades.add(ativ3);*/
        AtividadesAdapter adapter = new AtividadesAdapter(this, arrayOfAtividades);
        if(arrayOfAtividades.size()>0){
            show = true;
        }
        // Attach the adapter to a ListView
        mlview.setAdapter(adapter);

        mlview.setVisibility(show ? View.VISIBLE : View.GONE);
        mllin_lay.setVisibility(show ? View.GONE : View.VISIBLE);
    }
    public void resetVisibleSync(){
        txtmsg.setVisibility(View.VISIBLE);
        splashmsgsync.setVisibility(View.GONE);
        mProgressBar2.setVisibility(View.GONE);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent;
        GPSTracker gps;
        switch (id) {

            case (R.id.action_sync):
                txtmsg.setVisibility(View.GONE);
                splashmsgsync.setVisibility(View.VISIBLE);
                mProgressBar2.setVisibility(View.VISIBLE);
                mProgressBar2.setProgress(0);
                EstacaoSync();
                DCapaSync();
                DCapaListSync();
                DServicoSync(); //SOMENTE METACOM

               // VersaoSyncAtividade(); //SOMENTE ALTAREDE
                return true;
            case (R.id.action_user):
                intent = new Intent(MainActivity.this, UsercreenActivity.class);
                intent.putExtra("user", user);
                startActivityForResult(intent, USER_FUNC );

                return true;

            case (R.id.action_send):
                //Toast.makeText(getApplicationContext(), " Sem OS para enviar para o Servidor",
                //        Toast.LENGTH_SHORT).show();
                 VersaoSync(); //SOMENTE ALTAREDE
                     return true;
            case (R.id.action_diario):
                intent = new Intent(MainActivity.this, ActivityDiario.class);
                intent.putExtra("user", user);
                startActivityForResult(intent, DIARIO_FUNC );
                return true;
            case (R.id.action_clock):
                intent = new Intent(MainActivity.this, ClockTimeActivity.class);
                intent.putExtra("user", user);
                startActivityForResult(intent, CLOCK_FUNC );
                return true;
            case (R.id.action_settings):
                //intent = new Intent(getBaseContext(),LoginActivity.class);
                //startActivity(intent);
                dbHelper.logoutUser();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    public void sincronizar(){
        ArrayList<Ponto> arrayOfPontos = dbHelper.selectPontos(user.getExternal_id(),true);
        for (int i = 0; i < arrayOfPontos.size(); i++) {
            //Storing names to string array
            Ponto p = arrayOfPontos.get(i);
            PontoEndSync(p);
        }
        if (user.getPerfil()== 11){
            Toast.makeText(getApplicationContext(), "Pontos Sincronizados. ",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            List<Atividade> atividades = dbHelper.selectAtividadesFechadas(user.getExternal_id());
            //List<Atividade> atividades = dbHelper.selectAtividadesFechadas(2);
            if (atividades.size() >0){
                //   makeHTTPCall(atividades,   0);
                for (int i = 0; i < atividades.size(); i++) {
                    //Storing names to string array
                    Atividade a = atividades.get(i);
                    AtividadeEndSync(a);
                }
            }
            else{
                Toast.makeText(MainActivity.this,
                        "Não Existe Os para enviar para o servidor!",
                        Toast.LENGTH_LONG).show();
            }
        }

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch(requestCode) {
            case USER_FUNC:
                if (resultCode == RESULT_OK) {
                    user =  dbHelper.getUserByExternalId(user.getExternal_id());
                }
                break;
        }


    }

    public void refreshListView(String newText){
        // cria os grupos
        showAtividades( newText);
       /* List<Equipe> lstGrupos = dbHelper.selectEquipes(user.getId(), newText, true);
        // cria o "relacionamento" dos grupos com seus itens
        HashMap<Equipe, List<Inspecao>> lstItensGrupo = new HashMap<>();
        for (int i = 0; i < lstGrupos.size(); i++) {

            Equipe e = lstGrupos.get(i);

            List<Inspecao> ls = dbHelper.selectInspecoes(user.getId(), e.getExternal_id(),newText, false);
            lstItensGrupo.put(e,ls);

        }


        // cria um adaptador (BaseExpandableListAdapter) com os dados acima

        Adaptador adaptador = new Adaptador(this, lstGrupos, lstItensGrupo, user);
        // define o apadtador do ExpandableListView
        lview.setAdapter(adaptador);*/
    }


    public void makeHTTPCall(final List<Atividade> atividades,  final int pos) {

        Atividade atividade = atividades.get(pos);
        RequestParams params = new RequestParams();
        String CurrentString = user.getPath_assinatura();
        String[] separated = CurrentString.split("/");
        int x = separated.length-1;
        params.put("image", separated[x]);
        params.put("user_id", user.getExternal_id());
        params.put("id_atividade", atividade.getId());
        params.put("a_ip", atividade.getIp());
        params.put("a_mac", atividade.getMac());
        params.put("a_interface",atividade.getInterface_());
        params.put("a_prop_equipamento",atividade.getProp_equipamento());
        params.put("a_conexao",atividade.getConexao());
        params.put("a_nr_cabo",atividade.getNr_cabo());
        params.put("a_par_ele",atividade.getPar_eletrico());
        params.put("a_servidor",atividade.getServidor());
        params.put("a_pop",atividade.getPop());
        params.put("a_porta",atividade.getPorta());
        params.put("a_switch",atividade.getSwit());
        params.put("a_lat_pop",atividade.getLat_porta());
        params.put("a_long_pop",atividade.getLong_porta());
        params.put("a_lat_switch",atividade.getLat_switch());
        params.put("a_long_switch",atividade.getLong_switch());


        Despacho desp = dbHelper.getDespachoByAtividadeId(atividade.getId());

        params.put("d_acao", desp.getAcao());
        params.put("d_acao_hora", desp.getAcao_hora());
        params.put("d_documento",desp.getDocumento());
        params.put("d_observacao",desp.getObservacao());
        params.put("d_latitude", desp.getLatitude());
        params.put("d_longitude", desp.getLongitude());
        params.put("d_reparo_efetuado", desp.getReparo_efetuado());
        params.put("d_atividade_id", desp.getAtividade_id());
        params.put("d_contato_local", desp.getContato_local());
        params.put("d_image", desp.getEcondedFile());
        int permissionCheck =0;
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            // requestPermissions(new String[]{Manifest.permission.CAMERA}, 2);
            permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.INTERNET);
        }
//        Toast.makeText(MainActivity.this,
//                "Permissão de Iternet-"+Integer.toString(permissionCheck),
//                Toast.LENGTH_LONG).show();
        AsyncHttpClient client = new AsyncHttpClient();

    }

    public void downloadApk() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nova versão disponível!");
        builder.setMessage("Para sincronizar o aplicativo, primeiro será necessário atualizar sua versão, deseja fazer isso agora?")
                .setCancelable(false)
                .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        ProgressDialog mProgressDialog = new ProgressDialog(MainActivity.this);
//                        mProgressDialog.setCancelable(true);
//                        mProgressDialog.setMessage("Atualizando Aplicativo");
//                        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//                        mProgressDialog.setProgress(0);
//                        mProgressDialog.setMax(100);
//                        mProgressDialog.show();
                       // Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://177.130.1.206/altarede/androide_download_apk.php"));
                        String aux = dbHelper.ROOT_URL+"/androide_download_apk.php";
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(aux));
                        startActivity(browserIntent);
//                        Intent intent = new Intent(getApplicationContext(), DownloadService.class);
//                        intent.putExtra("url", "http://177.130.1.206/altarede/osaltarede.apk");
//                        intent.putExtra("receiver", new DownloadReceiver(new Handler()));
//                        startService(intent);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void EstacaoSync(){
        //Here we will handle the http request to insert user to mysql db
        //Creating a RestAdapter
        System.setProperty("http.keepAlive", "false");
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(dbHelper.ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        EstacaoSyncApi api = adapter.create(EstacaoSyncApi.class);

        //Defining the method insertuser of our interface
        api.getEstacoes(
                //Passing the values by getting it from editTexts
                Integer.toString(user.getExternal_id()),
                //Creating an anonymous callback
                new Callback<List<Estacao>>() {

                    @Override
                    public void success(List<Estacao> list, Response response) {
                        String atividades = "0";
                        int recebidos=0;
                        dbHelper.cleanEstacoes(user.getExternal_id());
                        for (int i = 0; i < list.size(); i++) {
                            //Storing names to string array
                            Estacao a = list.get(i);
                            dbHelper.insertEstacao(a);
                            atividades += "," + Integer.toString(a.getId());
                            recebidos += 1;
                            if (recebidos < 25)
                            {
                                mProgressBar2.incrementProgressBy(1);
                            }
                        }
                        if (recebidos < 25)
                        {
                            mProgressBar2.incrementProgressBy(25 - recebidos);
                        }
                        //Calling a method to show the list
                        // showAtividades("");
                        if (recebidos>0) {
                          //  showEstacoes();
                            //Toast.makeText(getApplicationContext(), "Sincronizando  " + Integer.toString(recebidos) + "",
                            //       Toast.LENGTH_SHORT).show();
                        }
                        //returnAtividades(atividades ,recebidos);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //you can handle the errors here
                        Toast.makeText(getApplicationContext(), " Falha na busca de OS. Tente novamente. ",
                                Toast.LENGTH_SHORT).show();
                        resetVisibleSync();
                    }
                }
        );
    }
    private void DCapaSync(){
        //Here we will handle the http request to insert user to mysql db
        //Creating a RestAdapter
        System.setProperty("http.keepAlive", "false");
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(dbHelper.ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        DcapaSyncApi api = adapter.create(DcapaSyncApi.class);

        //Defining the method insertuser of our interface
        api.getDcapas(
                //Passing the values by getting it from editTexts
                Integer.toString(user.getExternal_id()),
                //Creating an anonymous callback
                new Callback<List<Dcapa>>() {

                    @Override
                    public void success(List<Dcapa> list, Response response) {
                        int recebidos=0;
                        dbHelper.cleanDcapas();
                        for (int i = 0; i < list.size(); i++) {
                            //Storing names to string array
                            Dcapa a = list.get(i);
                            dbHelper.insertDcapa(a);
                            recebidos += 1;
                            if (recebidos < 25)
                            {
                                mProgressBar2.incrementProgressBy(1);
                            }
                        }
                        if (recebidos < 25)
                        {
                            mProgressBar2.incrementProgressBy(25 - recebidos);
                        }
                        //Calling a method to show the list
                        // showAtividades("");
                        if (recebidos>0) {
                            // Toast.makeText(getApplicationContext(), "Sincronizando  " + Integer.toString(recebidos) + "",
                            //         Toast.LENGTH_SHORT).show();
                        }
                        //returnAtividades(atividades ,recebidos);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //you can handle the errors here
                        Toast.makeText(getApplicationContext(), "Falha na busca de formulários. Tente novamente. ",
                                Toast.LENGTH_SHORT).show();
                        resetVisibleSync();
                    }
                }
        );
    }
    private void DCapaListSync(){
        //Here we will handle the http request to insert user to mysql db
        //Creating a RestAdapter
        System.setProperty("http.keepAlive", "false");
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(dbHelper.ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        DcapaListSyncApi api = adapter.create(DcapaListSyncApi.class);

        //Defining the method insertuser of our interface
        api.getDcapas(
                //Passing the values by getting it from editTexts
                Integer.toString(user.getExternal_id()),
                //Creating an anonymous callback
                new Callback<List<DcapaList>>() {

                    @Override
                    public void success(List<DcapaList> list, Response response) {
                        int recebidos=0;
                        dbHelper.cleanDcapasList();
                        for (int i = 0; i < list.size(); i++) {
                            //Storing names to string array
                            DcapaList a = list.get(i);
                            dbHelper.insertDcapaList(a);
                            if (recebidos < 25)
                            {
                                mProgressBar2.incrementProgressBy(1);
                            }
                            recebidos += 1;
                        }
                        if (recebidos < 25)
                        {
                            mProgressBar2.incrementProgressBy(25 - recebidos);
                        }
                        //Calling a method to show the list
                        // showAtividades("");
                        if (recebidos>0) {
                            // Toast.makeText(getApplicationContext(), "Sincronizando  " + Integer.toString(recebidos) + "",
                            //         Toast.LENGTH_SHORT).show();
                        }
                        //returnAtividades(atividades ,recebidos);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //you can handle the errors here
                        Toast.makeText(getApplicationContext(), "Falha a busca de itens de formulários. Tente novamente. ",
                                Toast.LENGTH_SHORT).show();
                        resetVisibleSync();
                    }
                }
        );
    }
    private void DServicoSync(){
        //Here we will handle the http request to insert user to mysql db
        //Creating a RestAdapter
        System.setProperty("http.keepAlive", "false");
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(dbHelper.ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        DservicoSyncApi api = adapter.create(DservicoSyncApi.class);

        //Defining the method insertuser of our interface
        api.getDservicos(
                //Passing the values by getting it from editTexts
                Integer.toString(user.getExternal_id()),
                //Creating an anonymous callback
                new Callback<List<Servico>>() {

                    @Override
                    public void success(List<Servico> list, Response response) {
                        int recebidos=0;
                        dbHelper.cleanServicos();

                        for (int i = 0; i < list.size(); i++) {
                            //Storing names to string array
                            Servico a = list.get(i);
                            a.setTecnico_id(user.getExternal_id());
                            dbHelper.insertServico(a);
                            if (recebidos < 25)
                            {
                                mProgressBar2.incrementProgressBy(1);
                            }
                            recebidos += 1;
                        }
                        if (recebidos < 25)
                        {
                            mProgressBar2.incrementProgressBy(25 - recebidos);
                        }
                        //Calling a method to show the list
                        // showAtividades("");
                        if (recebidos>0) {
                              Toast.makeText(getApplicationContext(), "Diarios Sincronizados  ",
                                     Toast.LENGTH_SHORT).show();
                            resetVisibleSync();
                        }
                        //returnAtividades(atividades ,recebidos);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //you can handle the errors here
                        Toast.makeText(getApplicationContext(), "Falha na Busca de Materiais e Serviços. Tente novamente. ",
                                Toast.LENGTH_SHORT).show();
                        resetVisibleSync();
                    }
                }
        );
    }
}
