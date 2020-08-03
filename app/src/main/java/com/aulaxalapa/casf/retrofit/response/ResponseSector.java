package com.aulaxalapa.casf.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseSector {
        @SerializedName("sector")
        @Expose
        private String sector;


        public ResponseSector(String sector) {
            this.sector = sector;
        }

        public String getSector() {
            return sector;
        }

        public void setSector(String sector) {
            this.sector = sector;
        }
}
