
package com.aulaxalapa.casf.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseInsert {

    @SerializedName("id")
    @Expose
    private String id;

    public ResponseInsert() {
    }

    public ResponseInsert(String id) {
        super();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
