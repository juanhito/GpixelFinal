package com.gpixel.Usuario;

import android.app.Application;

public class Aplicacion extends Application {
    private String usuario;
    private String idusuario;

    public String getUsuario() {
        return usuario;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }
}
