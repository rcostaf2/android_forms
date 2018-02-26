package com.aroos.metacom.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aroos.metacom.R;
import com.aroos.metacom.activities.models.DbHelper;
import com.aroos.metacom.activities.models.User;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class UsercreenActivity extends AppCompatActivity {


    public static final int SIGNATURE_ACTIVITY = 1;
    private static User user = null;
    private ImageView mImageView;
    private DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_usercreen);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        dbHelper = new DbHelper(this);
            Bundle extras = getIntent().getExtras();
            //user = new User();
            //Intent intentIncoming = getIntent();
            if(extras != null) {
                User user_gt = (User) getIntent().getSerializableExtra("user");// OK
                user =  dbHelper.getUserByExternalId(user_gt.getExternal_id());
            }
            TextView tvlogin = (TextView) findViewById(R.id.login);
            TextView tvnome = (TextView) findViewById(R.id.nome);
            tvlogin.setText(user.getLogin());
            tvnome.setText(user.getNome());
            mImageView=(ImageView)findViewById(R.id.imageView2);
            if(user.getPath_assinatura()!= ""){
                Bitmap myBitmap = BitmapFactory.decodeFile(user.getPath_assinatura());
                mImageView.setImageBitmap(myBitmap);
            }
            Button getSignature = (Button) findViewById(R.id.signature);
            getSignature.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(UsercreenActivity.this, Signature.class);
                    Bundle b = new Bundle();
                    b.putString("nome", user.getNome());
                    intent.putExtras(b);
                    startActivityForResult(intent,SIGNATURE_ACTIVITY);
                }
            });

        }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch(requestCode) {
            case SIGNATURE_ACTIVITY:
                if (resultCode == RESULT_OK) {

                    Bundle bundle = data.getExtras();
                    String status  = bundle.getString("status");

                    if(status.equalsIgnoreCase("done")){
                        String path = bundle.getString("path");
                        //String file = bundle.getString("file");
                        user.setPath_assinatura(path);
                        user.setFile_name_assinatura(path);

                        Bitmap myBitmap = BitmapFactory.decodeFile(path);
                        mImageView.setImageBitmap(myBitmap);
                        dbHelper = new DbHelper(this);
                        user = dbHelper.insertUser(user);

                        Toast toast = Toast.makeText(this, "Assinatura Salva!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP, 105, 50);
                        toast.show();
                    }
                }
                break;
        }

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
