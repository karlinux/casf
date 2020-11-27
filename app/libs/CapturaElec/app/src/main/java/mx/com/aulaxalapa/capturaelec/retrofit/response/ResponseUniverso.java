
package mx.com.aulaxalapa.capturaelec.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseUniverso {

    @SerializedName("claveElectoral")
    @Expose
    private String claveElectoral;

    @SerializedName("paterno")
    @Expose
    private String paterno;

    @SerializedName("materno")
    @Expose
    private String materno;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("seccion")
    @Expose
    private String seccion;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponseUniverso() {
    }

    public ResponseUniverso(String claveElectoral, String paterno, String materno, String nombre, String seccion) {
        super();
        this.claveElectoral = claveElectoral;
        this.paterno = paterno;
        this.materno = materno;
        this.nombre = nombre;
        this.seccion = seccion;
    }

    public String getClaveElectoral() {
        return claveElectoral;
    }

    public void setClaveElectoral(String claveElectoral) {
        this.claveElectoral = claveElectoral;
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

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }
}
