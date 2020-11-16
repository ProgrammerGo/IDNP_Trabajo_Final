package com.idnp_trabajo_final.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.idnp_trabajo_final.dao.daoUsuario;
import com.idnp_trabajo_final.entities.Usuario;
import com.idnp_trabajo_final.viewmodels.EditarViewModel;

public class EditarActivity extends AppCompatActivity {
    private static final String TAG = EditarActivity.class.getSimpleName();

    Button btnActualizar, btnEliminar, btnCancelar;
    EditText editNombre , editMail, editPass;
    daoUsuario dao;
    private EditarViewModel viewModel;
    String text=" ";
    int id;
    Usuario u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        configView();

    }
    private void configView(){
        Log.d(TAG, "Llego a Editar");
        dao=new daoUsuario(this);
        viewModel= ViewModelProviders.of(this).get(EditarViewModel.class);
        editNombre=(EditText)findViewById(R.id.editNombre);
        editMail=(EditText)findViewById(R.id.editMail);
        editPass=(EditText)findViewById(R.id.editPass);
        btnActualizar=(Button)findViewById(R.id.btnEdActualizar);
        btnCancelar=(Button)findViewById(R.id.btnEdCancelar);
        btnEliminar=(Button)findViewById(R.id.btnEdEliminar);
        //Set data
        Bundle b= getIntent().getExtras();
        id=b.getInt("Id");
        u=dao.getUsuarioById(id);
        editNombre.setText(u.getNombre());
        editMail.setText(u.getMail());
        editPass.setText(u.getPassword());

        btnActualizar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                int idE= viewModel.editarUsuario(u,dao,editMail.getText().toString(),editNombre.getText().toString(),editPass.getText().toString());
                Toast toast=Toast.makeText(EditarActivity.this,text, Toast.LENGTH_SHORT);
                toast.show();
                if(idE!=-1){
                    Intent i2= new Intent(EditarActivity.this,PerfilActivity.class);
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
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(EditarActivity.this,PerfilFragment.class);
                startActivity(i);
                finish();
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}