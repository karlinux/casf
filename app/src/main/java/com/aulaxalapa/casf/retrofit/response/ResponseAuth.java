
package com.aulaxalapa.casf.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseAuth {

    @SerializedName("idUsuario")
    @Expose
    private String idUsuario;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("email")
    @Expose
    private String email;

    public ResponseAuth() {
    }

    public ResponseAuth(String idUsuario, String token, String email, String nombre) {
        super();
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.token = token;
        this.email = email;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
