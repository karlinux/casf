package mx.com.aulaxalapa.capturaelec.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseInsert {

    @SerializedName("uid")
    @Expose
    private String uid;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("apaterno")
    @Expose
    private String apaterno;

    @SerializedName("amaterno")
    @Expose
    private String amaterno;

    @SerializedName("idcapturista")
    @Expose
    private String idcapturista;

    public ResponseInsert( ) {

    }

    public ResponseInsert(String uid, String nombre, String apaterno, String amaterno, String idcapturista) {
        this.uid = uid;
        this.nombre = nombre;
        this.apaterno = apaterno;
        this.amaterno = amaterno;
        this.idcapturista = idcapturista;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApaterno() {
        return apaterno;
    }

    public void setApaterno(String apaterno) {
        this.apaterno = apaterno;
    }

    public String getAmaterno() {
        return amaterno;
    }

    public void setAmaterno(String amaterno) {
        this.amaterno = amaterno;
    }

    public String getIdcapturista() {
        return idcapturista;
    }

    public void setIdcapturista(String idcapturista) {
        this.idcapturista = idcapturista;
    }
}
