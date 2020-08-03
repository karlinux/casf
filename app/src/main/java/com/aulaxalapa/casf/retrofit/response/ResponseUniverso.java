
package com.aulaxalapa.casf.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseUniverso {

    @SerializedName("folioCont")
    @Expose
    private String folioCont;
    @SerializedName("paterno")
    @Expose
    private String paterno;
    @SerializedName("materno")
    @Expose
    private String materno;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("calle")
    @Expose
    private String calle;
    @SerializedName("num_ext")
    @Expose
    private String numExt;
    @SerializedName("sector")
    @Expose
    private String sector;
    @SerializedName("ruta")
    @Expose
    private String ruta;
    @SerializedName("folioReparto")
    @Expose
    private String folioReparto;
    @SerializedName("colonia")
    @Expose
    private String colonia;
    @SerializedName("idMedidor")
    @Expose
    private String idMedidor;
    @SerializedName("medidor")
    @Expose
    private String medidor;
    @SerializedName("diametro")
    @Expose
    private String diametro;
    @SerializedName("marca")
    @Expose
    private String marca;
    @SerializedName("lect_ant")
    @Expose
    private String lectAnt;
    @SerializedName("anio")
    @Expose
    private String anio;
    @SerializedName("periodo")
    @Expose
    private String periodo;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponseUniverso() {
    }

    public ResponseUniverso(String folioCont, String paterno, String materno, String nombre, String calle, String numExt,
                            String sector, String ruta, String folioReparto, String colonia, String idMedidor, String medidor,
                            String diametro, String marca, String lectAnt, String anio, String periodo) {
        super();
        this.folioCont = folioCont;
        this.paterno = paterno;
        this.materno = materno;
        this.nombre = nombre;
        this.calle = calle;
        this.numExt = numExt;
        this.sector = sector;
        this.ruta = ruta;
        this.folioReparto = folioReparto;
        this.colonia = colonia;
        this.idMedidor = idMedidor;
        this.medidor = medidor;
        this.diametro = diametro;
        this.marca = marca;
        this.lectAnt = lectAnt;
        this.anio = anio;
        this.periodo = periodo;
    }

    public String getFolioCont() {
        return folioCont;
    }

    public void setFolioCont(String folioCont) {
        this.folioCont = folioCont;
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

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumExt() {
        return numExt;
    }

    public void setNumExt(String numExt) {
        this.numExt = numExt;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getFolioReparto() {
        return folioReparto;
    }

    public void setFolioReparto(String folioReparto) {
        this.folioReparto = folioReparto;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getIdMedidor() {
        return idMedidor;
    }

    public void setIdMedidor(String idMedidor) {
        this.idMedidor = idMedidor;
    }

    public String getMedidor() {
        return medidor;
    }

    public void setMedidor(String medidor) {
        this.medidor = medidor;
    }

    public String getDiametro() {
        return diametro;
    }

    public void setDiametro(String diametro) {
        this.diametro = diametro;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getLectAnt() {
        return lectAnt;
    }

    public void setLectAnt(String lectAnt) {
        this.lectAnt = lectAnt;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
}
