package com.idnp_trabajo_final.views;

import android.app.Notification;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.idnp_trabajo_final.dao.daoUsuario;
import com.idnp_trabajo_final.entities.Recorrido;
import com.idnp_trabajo_final.viewmodels.LoginViewModel;
import com.idnp_trabajo_final.viewmodels.RegistrarViewModel;

public class RegistrarActivity extends AppCompatActivity {
    Button btnRegistro, btnIngresar, btnAnonimo;
    EditText regNombre, regMail, regPass;
    daoUsuario dao;
    private RegistrarViewModel viewModel;
    private LoginViewModel viewModel1;
    private final static String CHANNEL_ID= "Notification";
    private final static int  NOTIFICATION_ID= 0;


    String text=" ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        configView();

    }
    private void configView(){
        dao=new daoUsuario(this);
        viewModel= ViewModelProviders.of(this).get(RegistrarViewModel.class);
        viewModel1= ViewModelProviders.of(this).get(LoginViewModel.class);
        regNombre=(EditText)findViewById(R.id.regNombre);
        regMail=(EditText)findViewById(R.id.regMail);
        regPass=(EditText)findViewById(R.id.regPass);
        btnIngresar=(Button)findViewById(R.id.btnRegLogin);
        btnAnonimo=(Button)findViewById(R.id.btnRegAnonimo);
        btnRegistro=(Button)findViewById(R.id.btnRegRegistro);
        btnRegistro.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                viewModel.registrarMessage(dao,regMail.getText().toString(),regNombre.getText().toString(),regPass.getText().toString());
                Toast toast=Toast.makeText(RegistrarActivity.this,text, Toast.LENGTH_SHORT);
                toast.show();
                if(text.equals("Registro Exitoso!!!")){
                    Intent i2= new Intent(RegistrarActivity.this,LoginActivity.class);
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
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(RegistrarActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
        btnAnonimo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createNotification();

                int idE= viewModel.loginAnonimo(dao);
                Toast toast=Toast.makeText(RegistrarActivity.this,text, Toast.LENGTH_SHORT);
                toast.show();
                if(idE!=-1){
                    Intent i2= new Intent(RegistrarActivity.this, CallerActivity.class);
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
        builder.setContentText("No olvides registrarte!!");
        builder.setColor(Color.BLACK);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);
        NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());

    }
}
