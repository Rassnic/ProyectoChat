package com.example.proyectochat;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

public class AdaterMensajes extends RecyclerView.Adapter<HolderMensaje> {

    private List<MensajeRecivir> Listmensaje = new ArrayList<>();
    private Context c;

    public AdaterMensajes( Context c) {
        this.c = c;
    }

    public void Addmensaje(MensajeRecivir m){
        Listmensaje.add(m);
        notifyItemInserted(Listmensaje.size());

    }

    @NonNull
    @Override
    public HolderMensaje onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.card_view_mensajes,parent,false);
        return new HolderMensaje(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMensaje holder, int position) {
     holder.getNombre().setText(Listmensaje.get(position).getNombre());
     holder.getMensaje().setText(Listmensaje.get(position).getMensaje());

     if(Listmensaje.get(position).getType_mensaje().equals("2")){
         holder.getFotoMensaje().setVisibility(View.VISIBLE);
         holder.getMensaje().setVisibility(View.VISIBLE);
         Glide.with(c).load(Listmensaje.get(position).getUrlFoto()).into(holder.getFotoMensaje());

     }
         else if (Listmensaje.get(position).getType_mensaje().equals("1")){
             holder.getFotoMensaje().setVisibility(View.GONE);
             holder.getMensaje().setVisibility(View.VISIBLE);

     }

         if(Listmensaje.get(position).getFotoperfil().isEmpty()){
             holder.getFotomensajePerfil().setImageResource(R.mipmap.ic_launcher);
             }else{

             Glide.with(c).load(Listmensaje.get(position).getFotoperfil()).into(holder.getFotomensajePerfil());
         }

         Long codigoHora = Listmensaje.get(position).getHora();
         Date d = new Date(codigoHora);
         SimpleDateFormat adf = new SimpleDateFormat("hh:mm:ss");
         holder.getHora().setText(adf.format(d));

     }

    @Override
    public int getItemCount() {
        return Listmensaje.size();
    }
}
