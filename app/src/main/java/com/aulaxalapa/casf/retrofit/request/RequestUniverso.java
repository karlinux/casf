
package com.aulaxalapa.casf.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestUniverso {

    @SerializedName("ruta")
    @Expose
    private String ruta;
    @SerializedName("sector")
    @Expose
    private String sector;
    @SerializedName("delFolio")
    @Expose
    private int delFolio;
    @SerializedName("alFolio")
    @Expose
    private int alFolio;

    /**
     * No args constructor for use in serialization
     *
     */
    public RequestUniverso() {
    }

    public RequestUniverso(String ruta, String sector, int delFolio, int alFolio) {
        this.ruta = ruta;
        this.sector = sector;
        this.delFolio = delFolio;
        this.alFolio = alFolio;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public int getDelFolio() {
        return delFolio;
    }

    public void setDelFolio(int delFolio) {
        this.delFolio = delFolio;
    }

    public int getAlFolio() {
        return alFolio;
    }

    public void setAlFolio(int alFolio) {
        this.alFolio = alFolio;
    }
}
