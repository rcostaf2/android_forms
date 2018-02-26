package com.aroos.metacom.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.aroos.metacom.R;
import com.aroos.metacom.activities.apis.PontoEndcApi;
import com.aroos.metacom.activities.models.DataHelper;
import com.aroos.metacom.activities.models.DbHelper;
import com.aroos.metacom.activities.models.Ponto;
import com.aroos.metacom.activities.models.User;
import com.aroos.metacom.activities.others.ClocksAdapter;
import com.aroos.metacom.activities.services.GPSTracker;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ClockTimeActivity extends AppCompatActivity {
    private ListView mlview;
    public User user = null;
    private DbHelper dbHelper;
    public TextView editTextData;
    public EditText editTextLogin;
    public Button mButton;
    public String UltData = "2017-02-09 00:35:00";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_time);

        //what can i do with AnalogClock?
        Bundle extras = getIntent().getExtras();
        //user = new User();
        dbHelper = new DbHelper(this);
        Intent intentIncoming = getIntent();
        if (extras != null) {
            User user_gt = (User) getIntent().getSerializableExtra("user");// OK
            user =  dbHelper.getUserByExternalId(user_gt.getExternal_id());
        }
        TextClock dc = (TextClock) findViewById(R.id.digitalClock1);
        editTextData = (TextView) findViewById(R.id.textView3);
        editTextLogin = (EditText) findViewById(R.id.editTextLogin);


        DataHelper dh = new DataHelper();
        editTextData.setText(dh.getData_resumida());
        mlview= (ListView) findViewById(R.id.listView);
        showPontos();
        mButton = (Button) findViewById(R.id.end_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //attemptLogin();
                if(editTextLogin.getText().toString().equals(user.getLogin())){
                    Boolean res = dbHelper.test10minPontos(user.getExternal_id(),UltData);
                    if( !res)
                    {
                        confirmSave();

                    }
                    else {
                       // Toast toast = Toast.makeText(getBaseContext(), "", Toast.LENGTH_SHORT);
                       // toast.setGravity(Gravity.TOP, 105, 50);
                       // toast.show();
                       msgFunction();

                    }
                }
                else{
                    Toast toast = Toast.makeText(getBaseContext(), "Login não confere com usuário!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 105, 50);
                    toast.show();
                }


            }
        });

    }
    public AlertDialog alerta;
    public void msgFunction(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ClockTimeActivity.this);
        //define o titulo
        builder.setTitle("Batida Ponto");
        //define a mensagem
        String str = "Deseja confirmar a batida de ponto com Intervalo de lançamento inferior a 10 minutos?";

        builder.setMessage(str);
        //define um botão como positivo
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1)
            {
                confirmSave();
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
    public void confirmSave(){
        GPSTracker gps = new GPSTracker(getBaseContext());
        if(gps.canGetLocation()){
            mButton.setEnabled(false);
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            Ponto ponto = new Ponto();
            ponto.setTecnico_id(user.getExternal_id());
            ponto.setLatitude(Double.toString((latitude)));
            ponto.setLongitude(Double.toString((longitude)));
            DataHelper dh = new DataHelper();
            ponto.setAcao_hora(dh.getData_completa());
            ponto.setData(dh.getData_resumida());
            ponto.setHora(dh.getHora_atual());
            Ponto pontoAux = dbHelper.insertPonto(ponto);

            ponto.setId(pontoAux.getId());
            PontoEndSync(ponto);

            // \n is for new line
            //Toast.makeText(getApplicationContext(), "Voce esta em - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }
    public  void ShowResult(){

        editTextLogin.setText("");
        showPontos();
        Toast toast = Toast.makeText(getBaseContext(), "Ponto Salvo!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 105, 50);
        toast.show();
        mButton.setEnabled(true);
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
                        UltData = ponto.getAcao_hora();
                        ShowResult();

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        ShowResult();
                        //you can handle the errors here
                      //  Toast.makeText(getApplicationContext(), "Problema de conexão com o servidor. Tente novamente. ",
                        //        Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
    public  void showPontos(){
        boolean show = false;

        ArrayList<Ponto> arrayOfPontos = dbHelper.selectPontos(user.getExternal_id(), false);

        ClocksAdapter adapter = new ClocksAdapter(this, arrayOfPontos);
        if(arrayOfPontos.size()>0){
            show = true;
        }
        // Attach the adapter to a ListView
        mlview.setAdapter(adapter);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button.
            Intent intent = new Intent();
            setResult(RESULT_OK,intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
