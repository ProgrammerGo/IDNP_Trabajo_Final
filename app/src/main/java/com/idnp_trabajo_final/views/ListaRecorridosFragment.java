package com.idnp_trabajo_final.views;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.idnp_trabajo_final.dao.daoModo;
import com.idnp_trabajo_final.dao.daoRecorrido;
import com.idnp_trabajo_final.entities.Recorrido;
import com.idnp_trabajo_final.utils.IComunicaFragments;
import com.idnp_trabajo_final.utils.PreferenceUtilsLog;
import com.idnp_trabajo_final.utils.Utilidades;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaRecorridosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaRecorridosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static daoRecorrido dao;

    private OnFragmentInteractionListener mListener;

    ArrayList<Recorrido> listaRecorrido;
    RecyclerView recyclerRecorridos;

    Activity actividad;
    IComunicaFragments interfaceComunicaFragments;

    public ListaRecorridosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaRecorridosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public  ListaRecorridosFragment newInstance(String param1, String param2) {
        ListaRecorridosFragment fragment = new ListaRecorridosFragment();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_lista_recorridos, container, false);
        configView(vista);
        return vista;
    }
    public void configView(View vista){
        listaRecorrido=new ArrayList<>();
        recyclerRecorridos= (RecyclerView) vista.findViewById(R.id.recyclerId);
        recyclerRecorridos.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarListaRecorridos(vista);

        RecorridoAdapter adapter=new RecorridoAdapter(listaRecorrido);
        recyclerRecorridos.setAdapter(adapter);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Toast.makeText(getContext(),"Seleccion: "+
                        listaRecorrido.get(recyclerRecorridos.
                                getChildAdapterPosition(view)).getFecha(),Toast.LENGTH_SHORT).show();

                interfaceComunicaFragments.enviarRecorrido(listaRecorrido.get(recyclerRecorridos.getChildAdapterPosition(view)));
            }
        });
    }
    private void llenarListaRecorridos(View root) {
        dao=new daoRecorrido(getContext());
//        dao.onCrear();
        PreferenceUtilsLog utils = new PreferenceUtilsLog();
        dao.connect();
        /*
        dao.insertRecorrido(new Recorrido(1,2,"20/12/2020",54.3, 40));
        dao.insertRecorrido(new Recorrido(1,2,"20/13/2020",54.3, 40));
         */
        int u =utils.getId(getContext());
        listaRecorrido= dao.selectRecorridobyUser(u);
        /*
        Log.d("Prueba", "llenarListaRecorridos: "+ listaRecorrido.get(0).toString());
        Log.d("Prueba", "llenarListaRecorridos: "+ listaRecorrido.get(1).toString());*/
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof Activity){
            this.actividad= (Activity) context;
            interfaceComunicaFragments= (IComunicaFragments) this.actividad;
        }

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}