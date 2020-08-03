package com.aulaxalapa.casf.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestRuta {
        @SerializedName("sector")
        @Expose
        private String sector;

        public RequestRuta(String sector) {
            this.sector = sector;
        }

        public String getSector() {
            return sector;
        }

        public void setSector(String sector) {
            this.sector = sector;
        }
}
