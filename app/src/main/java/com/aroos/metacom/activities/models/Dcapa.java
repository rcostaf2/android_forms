package com.aroos.metacom.activities.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by murilo on 02/04/2016.
 */
public class Dcapa implements Serializable {
    private int id;
    private int ordem;

    private String descricao;
    private String classe;
    private String valor;

    public List<String> list;



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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Dcapa(){}

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

 }
