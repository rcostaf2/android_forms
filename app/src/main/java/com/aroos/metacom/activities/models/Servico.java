package com.aroos.metacom.activities.models;

import java.io.Serializable;

/**
 * Created by murilo on 02/04/2016.
 */
public class Servico implements Serializable {
    private int id;
    private int tecnico_id;
    private String fornecedor;
    private String codigo;
    private int ordem;
    private String descricao;
    private String unidade;
    private String tipo;
    private String valor;
    private String classe;

    public String getValor() {
        return valor;
    }

    public String getTipo() {
        return tipo;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public int getTecnico_id() {
        return tecnico_id;
    }

    public void setTecnico_id(int tecnico_id) {
        this.tecnico_id = tecnico_id;
    }




    public Servico(){

    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

 }
