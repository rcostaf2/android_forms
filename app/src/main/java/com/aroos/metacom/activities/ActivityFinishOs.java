package com.aroos.metacom.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.aroos.metacom.R;
import com.aroos.metacom.activities.models.Atividade;
import com.aroos.metacom.activities.models.DbHelper;
import com.aroos.metacom.activities.models.Despacho;
import com.aroos.metacom.activities.others.MainActivityOsDetail;
import com.aroos.metacom.activities.services.GPSTracker;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.Calendar;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ActivityFinishOs extends AppCompatActivity {


    static public Atividade atividade;
    EditText editTextLatitude;
    EditText editTextLongitude;
    EditText editTextData;
    Spinner staticSpinner;
    EditText editTextObs;
    EditText editTextrecebidoPor;
    EditText editTextdocumento;
    GPSTracker gps;
    ImageView imgCliente;
    private DbHelper dbHelper;
    Despacho desp = new Despacho();
    private static final int SIGNATURE_FUNC = 5678;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_activity_finish_os);
        Bundle extras = getIntent().getExtras();
        atividade = new Atividade();

        Intent intentIncoming = getIntent();
        if (extras != null) {
            atividade = (Atividade) getIntent().getSerializableExtra("atividade");// OK
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        dbHelper = new DbHelper(this);


        staticSpinner = (Spinner) findViewById(R.id.editTextReparo);

        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.motivo_encerramento,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        staticSpinner.setAdapter(staticAdapter);

        editTextLatitude = (EditText) findViewById(R.id.editTextLatitude);

        editTextLongitude = (EditText) findViewById(R.id.editTextLongitude);
        editTextData = (EditText) findViewById(R.id.editTextData);

        editTextObs = (EditText)findViewById(R.id.textViewObs);
        editTextrecebidoPor = (EditText)findViewById(R.id.editTextServico);
        editTextdocumento  = (EditText)findViewById(R.id.editTextDocumento);
        gps = new GPSTracker(ActivityFinishOs.this);
        final Calendar c = Calendar.getInstance();
        editTextData.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(c.get(Calendar.DAY_OF_MONTH)).append("/").append(c.get(Calendar.MONTH) + 1).append("/")
                .append( c.get(Calendar.YEAR)).append(" "));
        // check if GPS enabled
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            editTextLatitude.setText(Double.toString((latitude)));
            editTextLongitude.setText(Double.toString((longitude)));

            // \n is for new line
            //Toast.makeText(getApplicationContext(), "Voce esta em - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
        imgCliente = (ImageView) findViewById(R.id.imageCliente);
        Button mSignInButton = (Button) findViewById(R.id.assFunc);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(ActivityFinishOs.this, Signature.class);
                Bundle b = new Bundle();
                //b.putString("nome", inspecao.getNome_funcionarios());
                intent.putExtras(b);
                startActivityForResult(intent,SIGNATURE_FUNC);
            }
        });
        Button mEndButton = (Button) findViewById(R.id.end_button);
        mEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String documento = editTextdocumento.getText().toString();
                String obs = editTextObs.getText().toString();
                String contato = editTextrecebidoPor.getText().toString();
                boolean cancel = false;
                View focusView = null;

                // Check for a valid password, if the user entered one.
                if (TextUtils.isEmpty(documento)) {
                    editTextdocumento.setError("Campo Obrigatório");
                    focusView = editTextdocumento;
                    cancel = true;
                }
                if (TextUtils.isEmpty(obs)) {
                    editTextObs.setError("Campo Obrigatório");
                    focusView = editTextObs;
                    cancel = true;
                }
                if (TextUtils.isEmpty(contato)) {
                    editTextrecebidoPor.setError("Campo Obrigatório");
                    focusView = editTextrecebidoPor;
                    cancel = true;
                }
                if (TextUtils.isEmpty(desp.getAssinatura())){
                    Toast.makeText(getApplicationContext(), "Assinatura não preenchida. ",
                            Toast.LENGTH_SHORT).show();
                    cancel = true;
                }
                if (!cancel){
                    confirmaEncerrarrAtividade();
                }

            }
        });

        // Set up the user interaction to manually show or hide the system UI.
//        mContentView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                toggle();
//            }
//        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
//        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }
    private AlertDialog alerta2;
    private void confirmaEncerrarrAtividade()
    { //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Salvar OS");
        //define a mensagem
        String str = "Deseja Encerrar a OS " + atividade.getNr_os() + " ?";
        builder.setMessage(str);
        //define um botão como positivo
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1)
            {

                desp.setAcao("Fechado");
                Calendar c = Calendar.getInstance();
                desp.setAcao_hora((new StringBuilder()
                        // Month is 0 based, just add 1
                        .append(c.get(Calendar.YEAR)).append("-").append(c.get(Calendar.MONTH) + 1).append("-")
                        .append( c.get(Calendar.DAY_OF_MONTH)).append(" ")).toString());
                desp.setDocumento(editTextdocumento.getText().toString());
                desp.setObservacao(editTextObs.getText().toString());
                desp.setLatitude(editTextLatitude.getText().toString());
                desp.setLongitude(editTextLongitude.getText().toString());
                desp.setReparo_efetuado(staticSpinner.getSelectedItem().toString());
                desp.setAtividade_id(atividade.getId());
                desp.setTecnico_id(atividade.getTecnico_id());
                desp.setContato_local(editTextrecebidoPor.getText().toString());
                dbHelper.insertDespacho(desp);

                atividade.setStatus_fechamento("FECHADO");
                dbHelper.insertAtividade(atividade);
                Toast.makeText(getApplicationContext(), "OS Encerrada",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
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
    public Bitmap getbitpam(String path){
        Bitmap imgthumBitmap=null;
        try
        {

            final int THUMBNAIL_SIZE = 64;

            FileInputStream fis = new FileInputStream(path);
            imgthumBitmap = BitmapFactory.decodeStream(fis);

            imgthumBitmap = Bitmap.createScaledBitmap(imgthumBitmap,
                    THUMBNAIL_SIZE, THUMBNAIL_SIZE, false);

            ByteArrayOutputStream bytearroutstream = new ByteArrayOutputStream();
            imgthumBitmap.compress(Bitmap.CompressFormat.JPEG, 100,bytearroutstream);


        }
        catch(Exception ex) {

        }
        return imgthumBitmap;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case (android.R.id.home):
                Intent intent = new Intent(getBaseContext(),MainActivityOsDetail.class);
                intent.putExtra("atividade", atividade);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch(requestCode) {
            case SIGNATURE_FUNC:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String status  = bundle.getString("status");
                    if(status.equalsIgnoreCase("done")){
                        String path = bundle.getString("path");
                        Bitmap myBitmap = getbitpam(path);
                        desp.setAssinatura(path);
                        //Bitmap myBitmap = BitmapFactory.decodeFile(path);
                        imgCliente.setImageBitmap(myBitmap);
                    }
                }
                break;



        }


    }
}
