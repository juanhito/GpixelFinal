package com.gpixel.Usuario;

public class UsuarioPojo {
    private String email;
    private String usuario;
    private String idusuario;
    public UsuarioPojo(){

    }
    public UsuarioPojo(String email, String usuario,String idusuario) {
        this.email = email;
        this.usuario = usuario;
        this.idusuario=idusuario;
    }

    public String getEmail() {
        return email;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getIdusuario() {
        return idusuario;
    }
}
