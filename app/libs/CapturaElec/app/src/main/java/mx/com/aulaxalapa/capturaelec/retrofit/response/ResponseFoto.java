
package mx.com.aulaxalapa.capturaelec.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseFoto {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("nombreOriginal")
    @Expose
    private String nombreOriginal;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResponseFoto() {
    }


    public ResponseFoto(String status, String nombreOriginal) {
        super();
        this.status = status;
        this.nombreOriginal = nombreOriginal;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNombreOriginal() {
        return nombreOriginal;
    }

    public void setNombreOriginal(String nombreOriginal) {
        this.nombreOriginal = nombreOriginal;
    }
}
