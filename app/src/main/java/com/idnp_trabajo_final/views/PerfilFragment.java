package com.idnp_trabajo_final.views;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.idnp_trabajo_final.dao.daoUsuario;
import com.idnp_trabajo_final.entities.Usuario;
import com.idnp_trabajo_final.viewmodels.PerfilViewModel;

public class PerfilFragment extends Fragment {

    private PerfilViewModel mViewModel;
    Button btnEdit, btnLogout;
    TextView textWelcome, textNombre, textMail;
    int id=0;
    Usuario u;
    daoUsuario dao;

    public static PerfilFragment newInstance() {
        return new PerfilFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(PerfilViewModel.class);
        View root= inflater.inflate(R.layout.perfil_fragment, container, false);
        configView(root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        // TODO: Use the ViewModel
    }
    private void configView(View root){
        textWelcome=(TextView)root.findViewById(R.id.perWelcome);
        textNombre=(TextView)root.findViewById(R.id.perNom);
        textMail=(TextView)root.findViewById(R.id.perMail);
        btnEdit=(Button)root.findViewById(R.id.btnPerEdit);
        btnLogout=(Button)root.findViewById(R.id.btnPerLogout);
        Bundle b=getActivity().getIntent().getExtras();
        id=b.getInt("Id");

        dao=new daoUsuario(getActivity());
        u=dao.getUsuarioById(id);
        textWelcome.setText("Hola "+ u.getNombre());
        textNombre.setText(u.getNombre());
        textMail.setText(u.getMail());
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getActivity(),LoginActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ia= new Intent(getActivity(),EditarActivity.class);
                ia.putExtra("Id",id);
                startActivity(ia);
                getActivity().finish();
            }
        });
    }
    /*
    public class EntrenamientoFragment extends Fragment {

    private EntrenamientoViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(EntrenamientoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_entrenamiento, container, false);
        final TextView textView = root.findViewById(R.id.text_entrenamiento);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
     */

    /*
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

     */

}