package com.example.proyectochat;

public class MensajeRecivir extends Mensaje {
    private Long hora;

    public MensajeRecivir() {
    }

    public MensajeRecivir(Long hora) {
        this.hora = hora;
    }

    public MensajeRecivir(String mensaje, String urlFoto, String nombre, String fotoperfil, String type_mensaje, Long hora) {
        super(mensaje, urlFoto, nombre, fotoperfil, type_mensaje);
        this.hora = hora;
    }

    public Long getHora() {
        return hora;
    }

    public void setHora(Long hora) {
        this.hora = hora;
    }
}
