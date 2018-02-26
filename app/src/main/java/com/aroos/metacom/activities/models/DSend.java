package com.aroos.metacom.activities.models;

import java.io.Serializable;

/**
 * Created by murilo on 03/04/2016.
 */
public class DSend implements Serializable {

    private String infogson;
    private String field;
    private String i;
    private String tam;

    public String getTam() {
        return tam;
    }

    public void setTam(String tam) {
        this.tam = tam;
    }

    public DSend(){}

    public String getInfogson() {
        return infogson;
    }

    public void setInfogson(String infogson) {
        this.infogson = infogson;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

}
