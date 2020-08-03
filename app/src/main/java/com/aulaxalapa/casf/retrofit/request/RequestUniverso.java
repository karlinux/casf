
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

    /**
     * No args constructor for use in serialization
     *
     */
    public RequestUniverso() {
    }

    public RequestUniverso(String ruta, String sector) {
        super();

        this.ruta = ruta;
        this.sector = sector;
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
}
