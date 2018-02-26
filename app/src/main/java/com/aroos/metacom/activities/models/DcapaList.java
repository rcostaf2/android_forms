package com.aroos.metacom.activities.models;

import java.io.Serializable;

/**
 * Created by murilo on 02/04/2016.
 */
public class DcapaList implements Serializable {
    private int id;
    private int ordem;

    private String group_name;

    private String valor;


    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }



    public DcapaList(){}

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

 }
