package com.aroos.metacom.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.aroos.metacom.R;
import com.aroos.metacom.activities.models.DbHelper;
import com.aroos.metacom.activities.models.Estacao;
import com.aroos.metacom.activities.models.User;
import com.aroos.metacom.activities.others.EstacoesAdapter;

import java.util.ArrayList;

public class ActivityDiario extends AppCompatActivity {
    public User user = null;
    private DbHelper dbHelper;
    private ListView mlview;

    private static final int SYNC_FUNC = 7654;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_diario);

        //what can i do with AnalogClock?
        Bundle extras = getIntent().getExtras();
        //user = new User();
        dbHelper = new DbHelper(this);
        Intent intentIncoming = getIntent();
        if (extras != null) {
            User user_gt = (User) getIntent().getSerializableExtra("user");// OK
            user =  dbHelper.getUserByExternalId(user_gt.getExternal_id());
        }
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        mlview= (ListView) findViewById(R.id.listView);
        showEstacoes();


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    protected void onResume() {

        super.onResume();
        this.showEstacoes();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        menu.add(0, 0, 0, "Settings").setIcon(R.drawable.ic_menu_share)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return true;
    }
    private AlertDialog alerta;
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
        if (id == 0) {
           // if (ImgIndex==0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //define o titulo
                builder.setTitle("Diário de obras");
                //define a mensagem
                String str = "Deseja enviar a medição para o servidor ?";
                builder.setMessage(str);
                //define um botão como positivo
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        //startSync();
                        Intent intent = new Intent(ActivityDiario.this, DiarioSync.class);
                        intent.putExtra("user", user);
                        startActivityForResult(intent,SYNC_FUNC);

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
        //}
        return super.onOptionsItemSelected(item);
    }



    public  void showEstacoes(){
        // Construct the data source
        ArrayList<Estacao> arrayOfEstacoes = dbHelper.selectEstacoes(user.getExternal_id());

        EstacoesAdapter adapter = new EstacoesAdapter(this, arrayOfEstacoes);
        // Attach the adapter to a ListView
        mlview.setAdapter(adapter);


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch(requestCode) {
            case SYNC_FUNC:
                if (resultCode == RESULT_OK) {
//                    Bundle bundle = data.getExtras();
//                    String status  = bundle.getString("status");
//                    if(status.equalsIgnoreCase("done")){
//                        String path = bundle.getString("path");
//                        Bitmap myBitmap = getbitpam(path);
//                        desp.setAssinatura(path);
//                        //Bitmap myBitmap = BitmapFactory.decodeFile(path);
//                        imgCliente.setImageBitmap(myBitmap);
//                    }
                }
                break;



        }


    }
}
