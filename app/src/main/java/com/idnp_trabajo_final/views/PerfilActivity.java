package com.idnp_trabajo_final.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.idnp_trabajo_final.dao.daoUsuario;
import com.idnp_trabajo_final.entiities.Usuario;

public class PerfilActivity extends AppCompatActivity{
    private static final String TAG = PerfilActivity.class.getSimpleName();
    Button btnEdit, btnLogout;
    TextView textWelcome, textNombre, textMail;
    int id=0;
    Usuario u;
    daoUsuario dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        configView();

    }
    private void configView(){
        textWelcome=(TextView)findViewById(R.id.perWelcome);
        textNombre=(TextView)findViewById(R.id.perNom);
        textMail=(TextView)findViewById(R.id.perMail);
        btnEdit=(Button)findViewById(R.id.btnPerEdit);
        btnLogout=(Button)findViewById(R.id.btnPerLogout);
        Bundle b=getIntent().getExtras();
        id=b.getInt("Id");

        dao=new daoUsuario(this);
        u=dao.getUsuarioById(id);
        textWelcome.setText("Hola "+ u.getNombre());
        textNombre.setText(u.getNombre());
        textMail.setText(u.getMail());
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(PerfilActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ia= new Intent(PerfilActivity.this,EditarActivity.class);
                ia.putExtra("Id",id);
                startActivity(ia);
                finish();
            }
        });
    }

}
