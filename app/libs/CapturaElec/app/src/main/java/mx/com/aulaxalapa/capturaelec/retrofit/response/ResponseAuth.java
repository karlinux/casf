
package mx.com.aulaxalapa.capturaelec.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseAuth {

    @SerializedName("correo")
    @Expose
    private String correo;

    @SerializedName("idUsuario")
    @Expose
    private String idUsuario;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("perfil")
    @Expose
    private String perfil;

    @SerializedName("idCoordinador")
    @Expose
    private String idCoordinador;


    @SerializedName("idJefe")
    @Expose
    private String idJefe;


    @SerializedName("idLider")
    @Expose
    private String idLider;


    public ResponseAuth() {
    }

    public ResponseAuth(String correo, String idUsuario, String nombre, String perfil, String idCoordinador, String idJefe, String idLider) {
        super();
        this.correo = correo;
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.perfil = perfil;
        this.idCoordinador = idCoordinador;
        this.idJefe = idJefe;
        this.idLider = idLider;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String email) {
        this.correo = correo;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdCoordinador() {
        return idCoordinador;
    }

    public void setIdCoordinador(String idCoordinador) {
        this.idCoordinador = idCoordinador;
    }

    public String getIdJefe() {
        return idJefe;
    }

    public void setIdJefe(String idJefe) {
        this.idJefe = idJefe;
    }

    public String getIdLider() {
        return idLider;
    }

    public void setIdLider(String idLider) {
        this.idLider = idLider;
    }
}
