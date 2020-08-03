package com.aulaxalapa.casf.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseRuta {
        @SerializedName("ruta")
        @Expose
        private String ruta;

        public ResponseRuta() {

        }

        public ResponseRuta(String ruta) {
            this.ruta = ruta;
        }

        public String getRuta() {
            return ruta;
        }

        public void setRuta(String ruta) {
            this.ruta = ruta;
        }
}
