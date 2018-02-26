package com.aroos.metacom.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aroos.metacom.R;
import com.aroos.metacom.activities.apis.DiarioEndApi;
import com.aroos.metacom.activities.models.DImagem;
import com.aroos.metacom.activities.models.DSend;
import com.aroos.metacom.activities.models.DbHelper;
import com.aroos.metacom.activities.models.DiarioImagem;
import com.aroos.metacom.activities.models.DiarioItem;
import com.aroos.metacom.activities.models.Estacao;
import com.aroos.metacom.activities.models.User;
import com.aroos.metacom.activities.others.DiarioImagemAdapter;
import com.aroos.metacom.activities.others.DiarioImagemAdapterSync;
import com.aroos.metacom.activities.others.ExifUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DiarioSync extends AppCompatActivity {

    private DbHelper dbHelper;
    public User user = null;
    private DiarioItem di;
    private int ImgIndex =0 ;
    private ArrayList<DSend> items;
    private ListView mlview;
    Button buttonStart;
    TextView textInfoA, textInfoB, textInfoC, textInfoD;
    TextView textDuration1, textDuration2;
    TextView textDuration3, textDuration4;
    ProgressBar pb;

    String infoMsgA;
    String infoMsgB;
    String infoMsgC;
    String infoMsgD;
    public ArrayList<DiarioImagem> arrayOfDiarioImagem;
    public DiarioImagemAdapterSync imgAdapter;
    ShareClass shareObj = new ShareClass(10);
    long startingTime;
    private static final int ATTACH_REQUEST = 1234;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diario_sync);//see xml layout
        Bundle extras = getIntent().getExtras();
        //user = new User();
        dbHelper = new DbHelper(this);
        Intent intentIncoming = getIntent();
        if (extras != null) {
            User user_gt = (User) getIntent().getSerializableExtra("user");// OK
            user =  dbHelper.getUserByExternalId(user_gt.getExternal_id());
        }
        buttonStart = (Button) findViewById(R.id.buttonstart);
        textInfoA = (TextView) findViewById(R.id.infoa);
        textInfoB = (TextView) findViewById(R.id.infob);
        textInfoC = (TextView) findViewById(R.id.infoc);
        textInfoD = (TextView) findViewById(R.id.infod);
        textDuration1 = (TextView) findViewById(R.id.duration1);
        textDuration2 = (TextView) findViewById(R.id.duration2);
        textDuration3 = (TextView) findViewById(R.id.duration3);
        textDuration4 = (TextView) findViewById(R.id.duration4);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        buttonStart.setVisibility(View.VISIBLE);
        mlview = (ListView) findViewById(R.id.listView);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testSync();

            }
        });

        //    @Override
        //    public void onClick(View arg0) {
                //pb.setVisibility(View.VISIBLE);
                infoMsgA = "Item\n";
                infoMsgB = "Fotos\n";
                //infoMsgC = "Materias e Serviços\n";
               // infoMsgD = "Capas\n";
                textInfoA.setText(infoMsgA);
                textInfoB.setText(infoMsgB);
                //textInfoC.setText(infoMsgC);
              //  textInfoD.setText(infoMsgD);
                //buttonStart.setVisibility(View.GONE);
                arrayOfDiarioImagem = new  ArrayList<DiarioImagem>();
                ArrayList<DiarioItem> listDiarioItem =dbHelper.selectDiarioItemsAll(user.getExternal_id());
                for (int k=0; k < listDiarioItem.size(); k++) {
                    DiarioItem di = listDiarioItem.get(k);
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<DImagem>>() {
                    }.getType();
                    ArrayList<DImagem> listImagem = gson.fromJson(di.getImagem(), type);
                    Gson gson2 = new Gson();
                    Type type2 = new TypeToken<Estacao>() {
                    }.getType();
                    Estacao est = gson2.fromJson(di.getInfo(), type2);
                    items = new ArrayList<DSend>();
                    ImgIndex = 0;
                    for (int i = 0; i < listImagem.size(); i++) {
                        DImagem auxDimg = listImagem.get(i);
                        DiarioImagem diarioImagem = new DiarioImagem();
                        diarioImagem.setIs_correct(false);
                        try {
                            diarioImagem.setComplemento(est.getFornecedor()+"-"+est.getHora_inicio());
                            diarioImagem.setTipoAttach(est.getId_obra()+":" + est.getEndereco());

                            diarioImagem.setPath_file(auxDimg.getImagem());
                            String aux = diarioImagem.getEcondedFilebyPath(auxDimg.getImagem());
                            Bitmap photo = ExifUtils.rotateBitmap(auxDimg.getImagem(), BitmapFactory.decodeFile(auxDimg.getImagem(), null));
                            diarioImagem.setPhoto(photo);
                            diarioImagem.setIs_correct(true);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        diarioImagem.setId(di.getId());
                        arrayOfDiarioImagem.add(diarioImagem);
                    }
                }



                imgAdapter = new DiarioImagemAdapterSync(DiarioSync.this, arrayOfDiarioImagem);
                mlview.setAdapter(imgAdapter);
                testSync();
              // teste
              //



         //   }
      //  });

    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        try {
//            imgAdapter.onActivityResult(requestCode, resultCode, data);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)  {
        String str = MainActivity.mMyAppsBundle.getString("position");
        //  int user_gt = (int) data.getSerializableExtra("position");
        switch(requestCode) {

            case ATTACH_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    // ImageView imageView = (ImageView) findViewById(R.id.imgView);
                    // imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    //ImageView img = (ImageView) mconvertView.findViewById(R.id.imageView3);
                    //mdiarioImagem
                    Bitmap photo = ExifUtils.rotateBitmap(picturePath, BitmapFactory.decodeFile(picturePath, null));
                    DiarioImagem diarioImagem = arrayOfDiarioImagem.get(Integer.parseInt(str));
                    diarioImagem.setPath_file(picturePath);
                    try{
                        String aux =  diarioImagem.getEcondedFilebyPath(diarioImagem.getPath_file());
                        diarioImagem.setPhoto(photo);
                        diarioImagem.setIs_correct(true);
                    } catch (Exception e) {
                    // TODO Auto-generated catch block
                        Toast.makeText(getApplicationContext(), "Falha no uso da imagem, favor selecionar outra. Atenção use somente imagens armazenadas no seu aparelho",
                                Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    }

                    arrayOfDiarioImagem.set(Integer.parseInt(str),diarioImagem);
                    imgAdapter = new DiarioImagemAdapterSync(DiarioSync.this, arrayOfDiarioImagem);
                    mlview.setAdapter(imgAdapter);
                    //img.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                   // DiarioImagemAdapter.ViewHolder mholder = (DiarioImagemAdapter.ViewHolder) mconvertView.getTag();
                   // mholder.mdiarioImagem.setPhoto(photo);
                   // mholder.setDiarioImagem(mholder.mdiarioImagem);
                    // mholder.img.setImageBitmap(mholder.mdiarioImagem.getPhoto());
                    //img.setImageBitmap(photo);
                }
                break;
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sync, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button.
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
               // intent.putExtra("user", user);
                finish();
                return true;

            case R.id.action_info:
                InfoDialog();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void InfoDialog(){
        Intent intent = new Intent();
        setResult(RESULT_CANCELED,intent);
        finish();
    }
    private void testSync(){
        Boolean b = true;
        for (int i=0; i < arrayOfDiarioImagem.size(); i++) {
            DiarioImagem diarioImagem = arrayOfDiarioImagem.get(i);
            if (b){
                b = diarioImagem.getIs_correct();
            }
        }
        if (b){
            pb.setVisibility(View.VISIBLE);
            buttonStart.setVisibility(View.GONE);
            startSync();
        }
        else{
            Toast.makeText(getApplicationContext(), "Favor verificar as imagens ",
                    Toast.LENGTH_SHORT).show();
        }

    }
    private void startSync() { //Cria o gerador do AlertDialog
        di = dbHelper.selectNextDiarioItem(user.getExternal_id());
        DiarioImagem auxDimagem = new DiarioImagem();
        if(di.getId() != 0){

            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<DImagem>>() {}.getType();
            //ArrayList<DImagem> listImagem = gson.fromJson(di.getImagem(), type);
            items = new ArrayList<DSend>();
            ImgIndex=0;
            int k=0;
            for (int i=0; i < arrayOfDiarioImagem.size(); i++) {
                DiarioImagem diarioImagem = arrayOfDiarioImagem.get(i);
                if (di.getId() == diarioImagem.getId()){
                    DSend aux = new DSend();
                    aux.setInfogson(diarioImagem.getEcondedFilebyPath(diarioImagem.getPath_file()));
                    aux.setField("IMAGEM");
                    aux.setI(Integer.toString(k));
                    k=k+1;
                    items.add(items.size(),aux);
                }
            }
            ///antes
//            for (int i=0; i < listImagem.size(); i++) {
//                DImagem auxDimg = listImagem.get(i);
//                DSend aux = new DSend();
//                aux.setInfogson(auxDimagem.getEcondedFilebyPath(auxDimg.getImagem()));
//                aux.setField("IMAGEM");
//                aux.setI(Integer.toString(i));
//                items.add(items.size(),aux);
//            }
            DSend aux1 = new DSend();
            aux1.setInfogson(di.getInfo());
            aux1.setField("DIARIO");
            aux1.setTam(Integer.toString(di.getInfo().length()));
            aux1.setI("0");
            items.add(items.size(),aux1);
            DSend aux2 = new DSend();
            aux2.setInfogson(di.getCapa());
            aux2.setField("CABEÇALHO");
            aux2.setTam(Integer.toString(di.getCapa().length()));
            aux2.setI("0");
            items.add(items.size(),aux2);
            DSend aux3 = new DSend();
            aux3.setInfogson(di.getServicos());
            aux3.setField("SERVIÇOS");
            aux3.setTam(Integer.toString(di.getServicos().length()));
            aux3.setI("0");
            items.add(items.size(),aux3);
            infoMsgA += Integer.toString(di.getId()) ;
            textInfoA.setText(infoMsgA);
            sincronizarItens();

        }
        else{
            //showEstacoes();
            ImgIndex=0;
            pb.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Sincronização concluida! ",
                    Toast.LENGTH_SHORT).show();
            InfoDialog();
        }
    }
    public void refreshItems(DSend aux3){
        if (aux3.getField().equals("IMAGEM")){
            infoMsgA += "\n";
            textInfoA.setText(infoMsgA);
            infoMsgB += "1 de " + Integer.toString(ImgIndex+1)  + "\n";
            textInfoB.setText(infoMsgB);
           // infoMsgC +=  "\n";
          //  textInfoC.setText(infoMsgC);
           // infoMsgD += "\n";
           // textInfoD.setText(infoMsgD);
        }
        if (aux3.getField().equals("SERVIÇOS")){
           // infoMsgA += "\n";
           // textInfoA.setText(infoMsgA);
            //infoMsgC +=  aux3.getTam()  + "\n";
           // textInfoC.setText(infoMsgC);
          //  infoMsgD += "\n";
          //  textInfoD.setText(infoMsgD);
        }
        if (aux3.getField().equals("CABEÇALHO")){
//            infoMsgA += "\n";
//            textInfoA.setText(infoMsgA);
//            infoMsgB += "\n";
//            textInfoB.setText(infoMsgB);
//            infoMsgC +=  "\n";
//            textInfoC.setText(infoMsgC);
//            infoMsgD +=  aux3.getTam()  + "\n";
//            textInfoD.setText(infoMsgD);
        }
        if (aux3.getField().equals("DIARIO")){
//            infoMsgA += "\n";
//            textInfoA.setText(infoMsgA);
//            infoMsgB += "\n";
//            textInfoB.setText(infoMsgB);
//            infoMsgC +=  "\n";
//            textInfoC.setText(infoMsgC);
//            infoMsgD +=  aux3.getTam().length()  + "\n";
//            textInfoD.setText(infoMsgD);
        }
    }
    private void sincronizarItens(){

        if (items.size() == ImgIndex){
            infoMsgA += "-------\n";
            textInfoA.setText(infoMsgA);
            infoMsgB += "-------\n";
            textInfoB.setText(infoMsgB);
//            infoMsgC +=  "------\n";
//            textInfoC.setText(infoMsgC);
//            infoMsgD +=  "------\n";
//            textInfoD.setText(infoMsgD);
            dbHelper.cleanDiarioItems(di.getId());
            startSync();
        }else{
            DSend aux3;
            aux3 = items.get(ImgIndex);
            refreshItems(aux3);

            confirmaSincronizar(aux3.getInfogson(),aux3.getField(),aux3.getI());
        }

    }

    private void confirmaSincronizar(String infojson, String field, String i) { //Cria o gerador do AlertDialog
        System.setProperty("http.keepAlive", "false");
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(dbHelper.ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter
        DiarioEndApi api = adapter.create(DiarioEndApi.class);
        //Defining the method insertuser of our interface
        api.send_data(
                //Passing the values by getting it from editTexts
                di.getKeysave(),
                //di.getInfo(),
                Integer.toString(user.getExternal_id()),
                field,i,
                //di.getCapa(),
                // di.getServicos(), di.getImagem(),
                Integer.toString(di.getEstacao_id()),infojson,
                //Creating an anonymous callback
                new Callback<String>() {
                    @Override
                    public void success(String list, Response response) {
                        //Dismissing the loading progressbar
                        //loading.dismiss();
                        ImgIndex += 1;
                        sincronizarItens();
                    }
                    @Override
                    public void failure(RetrofitError error) {
                        //you can handle the errors here
                        Toast.makeText(getApplicationContext(), "Problema de conexão com o servidor. Tente novamente. ",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
        );
    }
    public class ShareClass {

        int counter1;
        int counter2;

        Object lock1;
        Object lock2;

        ShareClass(int c) {
            counter1 = c;
            counter2 = c;
            lock1 = new Object();
            lock2 = new Object();
        }

        public int getCounter1() {
            return counter1;
        }

        public int getCounter2() {
            return counter2;
        }

        public int delayDecCounter1(int delay) {

            //do something not access the share obj
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            synchronized (this) {
                int tmpCounter = counter1;

                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                tmpCounter--;
                counter1 = tmpCounter;

                return counter1;
            }

        }

        public int delayDecCounter2(int delay) {

            //do something not access the share obj
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            synchronized (this) {
                int tmpCounter = counter2;

                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                tmpCounter--;
                counter2 = tmpCounter;

                return counter2;
            }

        }
    }
}