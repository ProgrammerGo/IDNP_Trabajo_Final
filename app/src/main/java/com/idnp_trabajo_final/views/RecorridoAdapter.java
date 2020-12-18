package com.idnp_trabajo_final.views;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.idnp_trabajo_final.entities.Recorrido;
import com.idnp_trabajo_final.utils.Constants;

import java.util.ArrayList;
public class RecorridoAdapter extends RecyclerView.Adapter<RecorridoAdapter.RecorridosViewHolder> implements View.OnClickListener{
    ArrayList<Recorrido> listaRecorridos;
    private View.OnClickListener listener;

    public RecorridoAdapter(ArrayList<Recorrido> listaRecorridos) {
        this.listaRecorridos = listaRecorridos;
    }

    @Override
    public RecorridosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recor_list,null,false);
        RecyclerView.LayoutParams layParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layParams);

        view.setOnClickListener(this);

        return new RecorridosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecorridosViewHolder holder, int position) {
        Log.d("Prueba holder", "onBindViewHolder: "+holder);
        holder.txtModo.setText("Modo: "+ String.valueOf((listaRecorridos.get(position).getModo())));
        holder.txtFecha.setText("Fecha: "+listaRecorridos.get(position).getFecha());
        holder.txtDist.setText("Distancia: "+Float.toString(listaRecorridos.get(position).getDistancia()));
        if (Constants.PORTRAIT==true){
            //holder.txtInformacion.setText(listaRecorridos.get(position).getInfo());
        }

        //holder.foto.setImageResource(listaRecorridos.get(position).getImagenId());
    }

    @Override
    public int getItemCount() {
        return listaRecorridos.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

    public class RecorridosViewHolder extends RecyclerView.ViewHolder {
        TextView txtModo, txtFecha, txtDist;
        ImageView foto;

        public RecorridosViewHolder(View itemView) {
            super(itemView);
            txtModo= (TextView) itemView.findViewById(R.id.txtModo);
            txtFecha= (TextView) itemView.findViewById(R.id.txtFecha);
            txtDist= (TextView) itemView.findViewById(R.id.txtDist);
            if (Constants.PORTRAIT==true){
                //txtInformacion= (TextView) itemView.findViewById(R.id.idInfo);
            }

            foto= (ImageView) itemView.findViewById(R.id.idImagen);
        }
    }
}
