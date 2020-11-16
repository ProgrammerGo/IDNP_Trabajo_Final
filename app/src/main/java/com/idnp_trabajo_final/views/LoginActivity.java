package com.idnp_trabajo_final.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.idnp_trabajo_final.dao.daoUsuario;
import com.idnp_trabajo_final.viewmodels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    Button btnRegistro, btnIngresar, btnAnonimo;
    EditText logMail, logPass;
    daoUsuario dao;
    private LoginViewModel viewModel;
    String text=" ";
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
        btnIngresar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                /*
                viewModel.login(dao,logMail.getText().toString(),logPass.getText().toString());
                Toast toast=Toast.makeText(LoginActivity.this,text, Toast.LENGTH_SHORT);
                toast.show();
                if(text.equals("Bienvenido")){
                    Intent i2 = new Intent(LoginActivity.this,PerfilActivity.class);
                    int a = viewModel.login(dao,logMail.getText().toString(),logPass.getText().toString());
                    i2.putExtra("Id",a);
                    startActivity(i2);
                    finish();
                }
                 */
                int idE= viewModel.login(dao,logMail.getText().toString(),logPass.getText().toString());
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
}