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
import com.idnp_trabajo_final.utils.PreferenceUtilsLog;
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
        View root= inflater.inflate(R.layout.fragment_perfil, container, false);
        configView(root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        // TODO: Use the ViewModel
    }
    private void configView(View root) {
        textWelcome = (TextView) root.findViewById(R.id.perWelcome);
        textNombre = (TextView) root.findViewById(R.id.perNom);
        textMail = (TextView) root.findViewById(R.id.perMail);
        btnEdit = (Button) root.findViewById(R.id.btnPerEdit);
        btnLogout = (Button) root.findViewById(R.id.btnPerLogout);
        Bundle b = getActivity().getIntent().getExtras();
        Intent intent = getActivity().getIntent();
        if (intent.hasExtra("Id")) {
            id = b.getInt("Id");
        } else {
            int idAuto = PreferenceUtilsLog.getId(getActivity());
            id = idAuto;
        }

        dao = new daoUsuario(getActivity());
        u = dao.getUsuarioById(id);
        textWelcome.setText("Hola " + u.getNombre());
        textNombre.setText(u.getNombre());
        textMail.setText(u.getMail());
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceUtilsLog.saveId(0, getActivity());
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ia = new Intent(getActivity(), EditarActivity.class);
                ia.putExtra("Id", id);
                startActivity(ia);
                getActivity().finish();
            }
        });
    }

}