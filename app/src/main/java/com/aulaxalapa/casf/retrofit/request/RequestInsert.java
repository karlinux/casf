
package com.aulaxalapa.casf.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestInsert {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("folioCont")
    @Expose
    private String folioCont;

    @SerializedName("lectura")
    @Expose
    private String lectura;

    @SerializedName("latitud")
    @Expose
    private String latitud;

    @SerializedName("longitud")
    @Expose
    private String longitud;

    @SerializedName("fotoMedidor")
    @Expose
    private String fotoMedidor;

    @SerializedName("fotoInconsistencia1")
    @Expose
    private String fotoInconsistencia1;

    @SerializedName("fotoInconsistencia2")
    @Expose
    private String fotoInconsistencia2;

    @SerializedName("fotoInconsistencia3")
    @Expose
    private String fotoInconsistencia3;

    @SerializedName("idInconsistencia1")
    @Expose
    private String idInconsistencia1;

    @SerializedName("idInconsistencia2")
    @Expose
    private String idInconsistencia2;

    @SerializedName("idInconsistencia3")
    @Expose
    private String idInconsistencia3;

    @SerializedName("fecha")
    @Expose
    private String fecha;

    @SerializedName("usuario")
    @Expose
    private String usuario;

    @SerializedName("lectAnt")
    @Expose
    private String lectAnt;

    @SerializedName("periodo")
    @Expose
    private String periodo;

    @SerializedName("anio")
    @Expose
    private String anio;

    /**
     * No args constructor for use in serialization
     *
     */
    public RequestInsert() {
    }

    public RequestInsert(String id, String folioCont, String lectura, String latitud, String longitud, String fotoMedidor,
                         String fotoInconsistencia1, String fotoInconsistencia2, String fotoInconsistencia3, String idInconsistencia1,
                         String idInconsistencia2, String idInconsistencia3, String fecha, String usuario, String lectAnt,
                         String periodo, String anio) {
        this.id = id;
        this.folioCont = folioCont;
        this.lectura = lectura;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fotoMedidor = fotoMedidor;
        this.fotoInconsistencia1 = fotoInconsistencia1;
        this.fotoInconsistencia2 = fotoInconsistencia2;
        this.fotoInconsistencia3 = fotoInconsistencia3;
        this.idInconsistencia1 = idInconsistencia1;
        this.idInconsistencia2 = idInconsistencia2;
        this.idInconsistencia3 = idInconsistencia3;
        this.fecha = fecha;
        this.usuario = usuario;
        this.lectAnt = lectAnt;
        this.periodo = periodo;
        this.anio = anio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFolioCont() {
        return folioCont;
    }

    public void setFolioCont(String folioCont) {
        this.folioCont = folioCont;
    }

    public String getLectura() {
        return lectura;
    }

    public void setLectura(String lectura) {
        this.lectura = lectura;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getFotoMedidor() {
        return fotoMedidor;
    }

    public void setFotoMedidor(String fotoMedidor) {
        this.fotoMedidor = fotoMedidor;
    }

    public String getFotoInconsistencia1() {
        return fotoInconsistencia1;
    }

    public void setFotoInconsistencia1(String fotoInconsistencia1) {
        this.fotoInconsistencia1 = fotoInconsistencia1;
    }

    public String getFotoInconsistencia2() {
        return fotoInconsistencia2;
    }

    public void setFotoInconsistencia2(String fotoInconsistencia2) {
        this.fotoInconsistencia2 = fotoInconsistencia2;
    }

    public String getFotoInconsistencia3() {
        return fotoInconsistencia3;
    }

    public void setFotoInconsistencia3(String fotoInconsistencia3) {
        this.fotoInconsistencia3 = fotoInconsistencia3;
    }

    public String getIdInconsistencia1() {
        return idInconsistencia1;
    }

    public void setIdInconsistencia1(String idInconsistencia1) {
        this.idInconsistencia1 = idInconsistencia1;
    }

    public String getIdInconsistencia2() {
        return idInconsistencia2;
    }

    public void setIdInconsistencia2(String idInconsistencia2) {
        this.idInconsistencia2 = idInconsistencia2;
    }

    public String getIdInconsistencia3() {
        return idInconsistencia3;
    }

    public void setIdInconsistencia3(String idInconsistencia3) {
        this.idInconsistencia3 = idInconsistencia3;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getLectAnt() {
        return lectAnt;
    }

    public void setLectAnt(String lectAnt) {
        this.lectAnt = lectAnt;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }
}
