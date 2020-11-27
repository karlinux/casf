
package mx.com.aulaxalapa.capturaelec.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestRegistro {

    @SerializedName("usuario")
    @Expose
    private String usuario;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("perfil")
    @Expose
    private String perfil;

    @SerializedName("paterno")
    @Expose
    private String paterno;

    @SerializedName("materno")
    @Expose
    private String materno;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("iddistritos")
    @Expose
    private String iddistritos;

    @SerializedName("idmunicipios")
    @Expose
    private String idmunicipios;

    @SerializedName("idcoordinadores")
    @Expose
    private String idcoordinadores;

    @SerializedName("clave")
    @Expose
    private String clave;

    public RequestRegistro() {

    }

    public RequestRegistro(String usuario, String password, String perfil, String paterno, String materno, String nombre, String iddistritos, String idmunicipios, String idcoordinadores, String clave) {
        super();
        this.usuario = usuario;
        this.password = password;
        this.perfil = perfil;
        this.paterno = paterno;
        this.materno = materno;
        this.nombre = nombre;
        this.iddistritos = iddistritos;
        this.idmunicipios = idmunicipios;
        this.idcoordinadores = idcoordinadores;
        this.clave = clave;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIddistritos() {
        return iddistritos;
    }

    public void setIddistritos(String iddistritos) {
        this.iddistritos = iddistritos;
    }

    public String getIdmunicipios() {
        return idmunicipios;
    }

    public void setIdmunicipios(String idmunicipios) {
        this.idmunicipios = idmunicipios;
    }

    public String getIdcoordinadores() {
        return idcoordinadores;
    }

    public void setIdcoordinadores(String idcoordinadores) {
        this.idcoordinadores = idcoordinadores;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
