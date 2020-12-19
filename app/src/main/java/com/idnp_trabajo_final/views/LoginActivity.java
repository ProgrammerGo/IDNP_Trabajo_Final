package com.idnp_trabajo_final.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.idnp_trabajo_final.dao.daoUsuario;
import com.idnp_trabajo_final.utils.PreferenceUtilsLog;
import com.idnp_trabajo_final.viewmodels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    Button btnRegistro, btnIngresar, btnAnonimo;
    EditText logMail, logPass;
    daoUsuario dao;
    private LoginViewModel viewModel;
    String text=" ";
    /* **************************** Cambios dar *************************************/
    private PendingIntent pendingIntent;
    private final static String CHANNEL_ID= "Notification";
    private final static int  NOTIFICATION_ID= 0;
    /* **************************** Cambios dar *************************************/

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        configView();

    }
    private void configView(){
        dao=new daoUsuario(this);
        viewModel= ViewModelProviders.of(this).get(LoginViewModel.class);
        logMail=(EditText)findViewById(R.id.logMail);
        logPass=(EditText)findViewById(R.id.logPass);
        btnIngresar=(Button)findViewById(R.id.btnLogIngresar);
        btnAnonimo=(Button)findViewById(R.id.btnLogAnonimo);
        btnRegistro=(Button)findViewById(R.id.btnLogRegistro);
        PreferenceUtilsLog utils = new PreferenceUtilsLog();
        if (utils.getId(this) != 0 ){
            Intent intent = new Intent(LoginActivity.this, CallerActivity.class);
            startActivity(intent);
        }
        btnIngresar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                int idE= viewModel.login(dao,logMail.getText().toString(),logPass.getText().toString());
                Toast toast=Toast.makeText(LoginActivity.this,text, Toast.LENGTH_SHORT);
                toast.show();
                if(idE!=-1){

                    PreferenceUtilsLog.saveId(idE, LoginActivity.this);
                    Intent i2= new Intent(LoginActivity.this,CallerActivity.class);
                    i2.putExtra("Id",idE);
                    startActivity(i2);
                    finish();
                }
            }
        });
        final Observer<String> observer = new Observer<String>(){
            @Override
            public void onChanged(String s) {
                text=s;
            }
        };
        viewModel.getMessage().observe(this,observer);
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(LoginActivity.this,RegistrarActivity.class);
                startActivity(i);
            }
        });
        btnAnonimo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                /* ******************************* cambios dar ******************************************/
                  createNotification();
                /* ******************************* cambios dar ******************************************/
                int idE= viewModel.loginAnonimo(dao);
                Toast toast=Toast.makeText(LoginActivity.this,text, Toast.LENGTH_SHORT);
                toast.show();
                if(idE!=-1){
                    //Intent i2= new Intent(LoginActivity.this,PerfilActivity.class);
                    Intent i2= new Intent(LoginActivity.this,CallerActivity.class);
                    i2.putExtra("Id",idE);
                    startActivity(i2);
                    finish();
                }


            }
        });
    }
    public void createNotification(){
        NotificationCompat.Builder builder= new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_baseline_directions_run_24);
        builder.setContentTitle("Goo");
        builder.setContentText("No te olvides de registrarte!!");
        builder.setColor(Color.BLACK);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);
        NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());

    }
}