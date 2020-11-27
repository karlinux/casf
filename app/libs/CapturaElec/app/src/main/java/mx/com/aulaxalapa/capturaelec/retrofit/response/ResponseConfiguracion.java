
package mx.com.aulaxalapa.capturaelec.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseConfiguracion {

    @SerializedName("idUsuario")
    @Expose
    private String idUsuario;

    @SerializedName("deshabilitado")
    @Expose
    private String deshabilitado;

    @SerializedName("finalizado")
    @Expose
    private String finalizado;

    @SerializedName("versione")
    @Expose
    private int versione;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResponseConfiguracion() {
    }

    /**
     *
     */
    public ResponseConfiguracion( String idUsuario, String deshabilitado, String finalizado, int versione) {
        super();
        this.idUsuario = idUsuario;
        this.deshabilitado = deshabilitado;
        this.finalizado = finalizado;
        this.versione = versione;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDeshabilitado() {
        return deshabilitado;
    }

    public void setDeshabilitado(String deshabilitado) {
        this.deshabilitado = deshabilitado;
    }

    public String getFinalizado() {
        return finalizado;
    }

    public void setFinalizado(String finalizado) {
        this.finalizado = finalizado;
    }

    public int getVersione() {
        return versione;
    }

    public void setVersione(int versione) {
        this.versione = versione;
    }
}
