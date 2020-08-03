package com.aulaxalapa.casf.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestGps {

    @SerializedName("id")
    @Expose
    private String id;

   @SerializedName("latitud")
    @Expose
    private String latitud;

   @SerializedName("longitud")
    @Expose
    private String longitud;

    public RequestGps() {

    }

    public RequestGps(String id, String latitud, String longitud) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
