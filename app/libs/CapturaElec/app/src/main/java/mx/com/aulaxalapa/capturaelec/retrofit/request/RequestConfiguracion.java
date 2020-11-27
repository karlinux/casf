
package mx.com.aulaxalapa.capturaelec.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestConfiguracion {

    @SerializedName("idUsuario")
    @Expose
    private String idUsuario;

    /**
     * No args constructor for use in serialization
     *
     */
    public RequestConfiguracion() {
    }

    /**
     *
     */
    public RequestConfiguracion(String idUsuario) {
        super();
        this.idUsuario = idUsuario;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
