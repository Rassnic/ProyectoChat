package com.example.proyectochat;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class HolderMensaje extends RecyclerView.ViewHolder {

    private TextView nombre;
    private TextView mensaje;
    private TextView hora;
    private CircleImageView fotomensajePerfil;
    private ImageView fotoMensaje;

    public TextView getNombre() {
        return nombre;
    }

    public void setNombre(TextView nombre) {
        this.nombre = nombre;
    }

    public TextView getMensaje() {
        return mensaje;
    }

    public void setMensaje(TextView mensaje) {
        this.mensaje = mensaje;
    }

    public TextView getHora() {
        return hora;
    }

    public void setHora(TextView hora) {
        this.hora = hora;
    }

    public CircleImageView getFotomensajePerfil() {
        return fotomensajePerfil;
    }

    public void setFotomensajePerfil(CircleImageView fotomensajePerfil) {
        this.fotomensajePerfil = fotomensajePerfil;
    }

    public ImageView getFotoMensaje() {
        return fotoMensaje;
    }

    public void setFotoMensaje(ImageView fotoMensaje) {
        this.fotoMensaje = fotoMensaje;
    }

    public HolderMensaje(@NonNull View itemView) {
        super(itemView);

        nombre = (TextView) itemView.findViewById(R.id.nombreMensaje);
        mensaje = (TextView) itemView.findViewById(R.id.mensajeMensaje);
        hora = (TextView) itemView.findViewById(R.id.horaMensaje);
        fotomensajePerfil = (CircleImageView) itemView.findViewById(R.id.fotoPerfilMensaje);
        fotoMensaje =(ImageView) itemView.findViewById(R.id.mensajeFoto);

    }
}
