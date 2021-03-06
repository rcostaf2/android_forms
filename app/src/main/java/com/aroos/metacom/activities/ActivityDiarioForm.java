package com.aroos.metacom.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.aroos.metacom.R;
import com.aroos.metacom.activities.models.DImagem;
import com.aroos.metacom.activities.models.DataHelper;
import com.aroos.metacom.activities.models.DbHelper;
import com.aroos.metacom.activities.models.Dcapa;
import com.aroos.metacom.activities.models.DiarioImagem;
import com.aroos.metacom.activities.models.DiarioItem;
import com.aroos.metacom.activities.models.Estacao;
import com.aroos.metacom.activities.models.Servico;
import com.aroos.metacom.activities.models.User;
import com.aroos.metacom.activities.others.DiarioCapaAdapter;
import com.aroos.metacom.activities.others.DiarioImagemAdapter;
import com.aroos.metacom.activities.others.ExifUtils;
import com.aroos.metacom.activities.others.ServicosAdapter;
import com.aroos.metacom.activities.services.GPSTracker;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit.RestAdapter;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ActivityDiarioForm extends AppCompatActivity {

    public User user = null;
    public Estacao estacao = null;
    private DbHelper dbHelper;
    private ListView mlview;
    private ListView mlviewServico;
    public TextView editTextData;
    public Switch mySwitch;
    public ImageButton ibtnCapa;
    public ImageButton ibtnServico;
    public ImageButton ibtnMaterial;
    public ImageButton ibtnCamera;
    public ScrollView sview1;
    public Map fornecedores;
    public Map fornecedoresMat;
    public ArrayList<Dcapa> arrayOfDcapas;
    public String keySave;
    public int selectedTab = 0;
    private static final int CAMERA_REQUEST = 1888;
    public boolean hasImage = false;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_activity_diario_form);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

//what can i do with AnalogClock?
        Bundle extras = getIntent().getExtras();
        //user = new User();
        dbHelper = new DbHelper(this);
        Intent intentIncoming = getIntent();

        if (extras != null) {
            estacao = (Estacao) getIntent().getSerializableExtra("estacao");// OK
            user = dbHelper.getUserByExternalId(estacao.getTecnico_id());
        }
        actionBar.setTitle("Diário de Obras - CLASSE " + estacao.getClasse());
        resetFields();
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        fornecedores = new HashMap();

        fornecedoresMat = new HashMap();
        mlview = (ListView) findViewById(R.id.listView);
        //mlviewServico = (ListView) findViewById(R.id.listViewServico);
        arrayOfDcapas = dbHelper.selectDcapas(estacao.getClasse(), estacao);
        arrayOfDiarioImagem = new  ArrayList<DiarioImagem>();
        showDcapas();
//        Spinner spinner = (Spinner) findViewById(R.id.fornecedor_spinner);
        TextView forn = (TextView) findViewById(R.id.fornecedor_spinner);
        forn.setText("Operadora: " + estacao.getFornecedor());
        showServicoItems(estacao.getFornecedor());
        List<String> list = dbHelper.selectFornecedores(user.getExternal_id());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(dataAdapter);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                showServicoItems(parentView.getItemAtPosition(position).toString());
//            }
//
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // To do ...
//            }
//
//        });

        ibtnCapa = (ImageButton) findViewById(R.id.imageButton);
        ibtnCapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //attemptLogin();
                selectedTab = 0;
                mlview.setAdapter(dcapaAdapter);


            }
        });
        ibtnServico = (ImageButton) findViewById(R.id.imageButton2);
        ibtnServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //attemptLogin();
                selectedTab = 1;
                mlview.setAdapter(servAdapter);


            }
        });
        ibtnMaterial = (ImageButton) findViewById(R.id.imageButton3);
        ibtnMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //attemptLogin();
                selectedTab = 2;
                mlview.setAdapter(matAdapter);


            }
        });
        ibtnCamera = (ImageButton) findViewById(R.id.imageButton4);
        ibtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTab = 3;
                mlview.setAdapter(imgAdapter);
                try {
                    GPSTracker gps = new GPSTracker(getBaseContext());
                    if (gps.canGetLocation()) {

                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();


                        estacao.setLatitude(Double.toString((latitude)));
                        estacao.setLongitude(Double.toString((longitude)));
                        //Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        //startActivityForResult(cameraIntent, CAMERA_REQUEST);
                        EasyImage.openCamera(ActivityDiarioForm.this, 0);

                        // \n is for new line
                        //Toast.makeText(getApplicationContext(), "Voce esta em - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                    } else {
                        // can't get location
                        // GPS or Network is not enabled
                        // Ask user to enable GPS/network in settings
                       // gps.showSettingsAlert();
                        Toast.makeText(ActivityDiarioForm.this, "Favor ativar o GPS do Aparelho!", Toast.LENGTH_LONG).show();

                    }
                }
                finally {
                    //Toast.makeText(getApplicationContext(), "Não foi possivel ativar o GPS por favor ativar manualmente e reiniciar o aplicativo. ", Toast.LENGTH_LONG).show();
                }

            }
        });
        // mySwitch = (Switch) findViewById(R.id.switch1);
        sview1 = (ScrollView) findViewById(R.id.listScroll);
        mlview.setRecyclerListener(new AbsListView.RecyclerListener() {
            @Override
            public void onMovedToScrapHeap(View view) {
                if ( view.hasFocus()){
                    view.clearFocus(); //we can put it inside the second if as well, but it makes sense to do it to all scraped views
                    //Optional: also hide keyboard in that case
                    if ( view instanceof EditText) {
                        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            }
        });
        mlview.setAdapter(dcapaAdapter);
        //set the switch to ON
        //mySwitch.setChecked(false);
        //attach a listener to check for changes in state
//        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView,
//                                         boolean isChecked) {
//
//                if(isChecked){
////                    switchStatus.setText("Switch is currently ON");
//                    mlview.setAdapter(servAdapter);
//                }else{
////                    switchStatus.setText("Switch is currently OFF");
//                    mlview.setAdapter(dcapaAdapter);
//                }
//
//            }
//        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
       // client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }



    public void resetFields(){
        DataHelper data = new DataHelper();
        keySave = data.getData_key();
        estacao.setData(data.getData_resumida());
        estacao.setHora_inicio(data.getHora_atual());
        editTextData = (TextView) findViewById(R.id.estacao_descricao);
        editTextData.setText(estacao.getDescricao());
    }
    public DiarioCapaAdapter dcapaAdapter;

    public ServicosAdapter servAdapter;
    public ServicosAdapter matAdapter;

    public DiarioImagemAdapter imgAdapter;
    public ArrayList<Servico> arrayOfServicos;
    public ArrayList<Servico> arrayOfMaterias;
    public ArrayList<DiarioImagem> arrayOfDiarioImagem;


    public void showServicoItems(String fornecedor) {
// Construct the data source
        estacao.setFornecedor(fornecedor);
        if (fornecedores.containsKey(fornecedor)) {
            arrayOfServicos = (ArrayList<Servico>) fornecedores.get(fornecedor);
            arrayOfMaterias = (ArrayList<Servico>) fornecedoresMat.get(fornecedor);
        } else {
            arrayOfServicos = dbHelper.selectServicos(fornecedor, user.getExternal_id(), "Serviço", estacao.getClasse());
            fornecedores.put(fornecedor, arrayOfServicos);
            arrayOfMaterias = dbHelper.selectServicos(fornecedor, user.getExternal_id(), "Material", estacao.getClasse());
            fornecedoresMat.put(fornecedor, arrayOfServicos);

        }
        servAdapter = new ServicosAdapter(this, arrayOfServicos);
        matAdapter = new ServicosAdapter(this, arrayOfMaterias);
        //if (mySwitch.isChecked()){
        if (selectedTab == 1) {
            mlview.setAdapter(servAdapter);
        }
        if (selectedTab == 2) {
            mlview.setAdapter(matAdapter);
        }
        // Attach the adapter to a ListView

    }

    public void showDcapas() {
        // Construct the data source

        dcapaAdapter = new DiarioCapaAdapter(this, arrayOfDcapas);
        // Attach the adapter to a ListView

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, 0, 0, "Settings").setIcon(R.drawable.ic_menu_send)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button.
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            intent.putExtra("user", user);
            finish();
            return true;
        }
        if (id == 0) {
            // This ID represents the Home or Up button.
            if (hasImage) {
                confirmaSalvar();
            } else {
                Toast.makeText(this, "Favor capturar uma fotografia!", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, ActivityDiarioForm.this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {

                Toast.makeText(ActivityDiarioForm.this, "Foto não obtida.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {

//            }
//
//            @Override
//            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                BitmapFactory.Options options = new BitmapFactory.Options();

                if (imageFiles.size()>0) {
                    File imageFile = imageFiles.get(0);
                    options.inSampleSize = 10;
                     DiarioImagem diarioImagem = new DiarioImagem();
                    Bitmap photo = ExifUtils.rotateBitmap(imageFile.getAbsolutePath(), BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options));
                    diarioImagem.setPhoto(photo);
                    // Bitmap photo = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
                    //Bitmap photo = (Bitmap) data.getExtras().get("data");
                    // ImageView foto = (ImageView) findViewById(R.id.imageView2);
                    //foto.setImageURI(outputFileUri);
                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                    Uri tempUri = getImageUri(getApplicationContext(), photo);
                    String selectedImagePath = getRealPathFromURI(tempUri);

                    diarioImagem.setPath_file(selectedImagePath);
                    int idx = selectedImagePath.lastIndexOf("/");
                    diarioImagem.setFile_name(selectedImagePath.substring(idx + 1));
                    //ArrayList<DiarioImagem> lstImagens = new ArrayList<DiarioImagem>() ;
                    //lstImagens.add(r);
                    //mlview.setAdapter(new ImagemAdapter(this, lstImagens));
                    arrayOfDiarioImagem.add(diarioImagem);
                    //ltfotos.setText("Fotos - " + Integer.toString(lstImagens.size()));
                    hasImage = true;
                    imgAdapter = new DiarioImagemAdapter(ActivityDiarioForm.this, arrayOfDiarioImagem);
                    mlview.setAdapter(imgAdapter);
                    Toast.makeText(ActivityDiarioForm.this, "Imagem Salva com sucesso!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private AlertDialog alerta;
    private void confirmaSalvar() { //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Diário de obras");
        //define a mensagem
        String str = "Deseja salvar a medição ?";
        builder.setMessage(str);
        //define um botão como positivo
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                ArrayList<Servico> listSend = new ArrayList<Servico>();
                for (int i = 0; i < arrayOfServicos.size(); i++) {
                    Servico auxServ = arrayOfServicos.get(i);
                    if (auxServ.getValor()!= null){
                        listSend.add(listSend.size(),auxServ);
                    }
                }
                for (int i = 0; i < arrayOfMaterias.size(); i++) {
                    Servico auxServ = arrayOfMaterias.get(i);
                    if (auxServ.getValor()!= null){
                        listSend.add(listSend.size(),auxServ);
                    }
                }
                if (listSend.size() > 0) {
                    //Creating object for our interface
                    DataHelper data = new DataHelper();
                    estacao.setHora_termino(data.getHora_atual());
                    keySave += (data.getData_hora_key());
                    Gson gson1 = new Gson();
                    String servicos = gson1.toJson(listSend);
                    Gson gson2 = new Gson();
                    String info = gson2.toJson(estacao);
                    Gson gson3 = new Gson();
                    String capa = gson3.toJson(arrayOfDcapas);
                    DiarioItem ditem = new DiarioItem();
                    ditem.setKeysave(keySave);
                    ditem.setInfo(info);
                    ditem.setCapa(capa);
                    ditem.setServicos(servicos);
                    ArrayList<DImagem> listImagem = new ArrayList<DImagem>();
                    for (int i = 0; i < arrayOfDiarioImagem.size(); i++) {
                        DiarioImagem auxDimg = arrayOfDiarioImagem.get(i);
                        DImagem di = new DImagem();
                        di.setImagem(auxDimg.getPath_file());
                        listImagem.add(listImagem.size(), di);
                    }
                    Gson gson4 = new Gson();
                    String imagens = gson4.toJson(listImagem);
                    ditem.setImagem(imagens);
                    ditem.setEstacao_id(estacao.getId());
                    ditem.setTecnico_id(user.getExternal_id());
                    dbHelper.insertDiarioItem(ditem);
                    resetFields();
                    Toast.makeText(getApplicationContext(), "Medição salva com sucesso! ",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Favor cadastrar informações de serviços ou materiais! ",
                            Toast.LENGTH_SHORT).show();
                }
//                Intent intent = new Intent();
//                setResult(RESULT_OK, intent);
//                intent.putExtra("user", user);
//                finish();

            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                }
        );
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

    private void confirmaSalvarold() { //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Diário de obras");
        //define a mensagem
        String str = "Deseja enviar a medição para o servidor ?";
        builder.setMessage(str);
        //define um botão como positivo
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
//                Intent intent = new Intent(MainActivity.this, ClockTimeActivity.class);
//                intent.putExtra("user", user);
//                startActivityForResult(intent, CLOCK_FUNC );


                System.setProperty("http.keepAlive", "false");
                RestAdapter adapter = new RestAdapter.Builder()
                        .setEndpoint(dbHelper.ROOT_URL) //Setting the Root URL
                        .build(); //Finally building the adapter

                //Creating object for our interface
                DataHelper data = new DataHelper();
                estacao.setHora_termino(data.getHora_atual());
                keySave += (data.getData_hora_key());
                Gson gson1 = new Gson();
                String servicos = gson1.toJson(arrayOfServicos);
                Gson gson2 = new Gson();
                String info = gson2.toJson(estacao);
                Gson gson3 = new Gson();
                String capa = gson3.toJson(arrayOfDcapas);


                /*DiarioEndApi api = adapter.create(DiarioEndApi.class);

                //Defining the method insertuser of our interface
                api.send_data(
                        //Passing the values by getting it from editTexts
                        keySave,
                        info,
                        Integer.toString(user.getExternal_id()),
                        capa,
                        servicos, "enviar  imagens" ,//diarioImagem.getEcondedFile(),
                        Integer.toString(estacao.getId()),
                        //Creating an anonymous callback
                        new Callback<String>() {
                            @Override
                            public void success(String list, Response response) {
                                //Dismissing the loading progressbar
                                //loading.dismiss();

                                //Storing the data in our list
                                Toast.makeText(getApplicationContext(), "Diario Salvo com sucesso! ",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                setResult(RESULT_OK, intent);
                                intent.putExtra("user", user);
                                finish();

                            }

                            @Override
                            public void failure(RetrofitError error) {
                                //you can handle the errors here
                                Toast.makeText(getApplicationContext(), "Problema de conexão com o servidor. Tente novamente. ",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                );
*/
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                }
        );
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

}
