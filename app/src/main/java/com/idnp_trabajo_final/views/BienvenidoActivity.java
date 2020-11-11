package com.idnp_trabajo_final.views;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BienvenidoActivity extends AppCompatActivity{
    private Button btnEmpezar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);
        configView();

    }
    private void configView(){
        btnEmpezar=(Button)findViewById(R.id.btnEmpezar);
        btnEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BienvenidoActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
