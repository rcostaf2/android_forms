package com.aroos.metacom.activities.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by murilo on 02/04/2016.
 */
public class Estacao implements Serializable {
    private int id;
    private int tecnico_id;
    private String localidade;
    private String estacao;
    private String descricao;
    private String classe;
    private String data;
    private String hora_inicio;
    private String hora_termino;
    private String fornecedor;
    private String latitude;
    private String longitude;
    private String tipos_obra;
    private String armario;
    private String data_encerrado;
    private String obs;
    private String endereco;

    public String getId_obra() {
        return id_obra;
    }

    public void setId_obra(String id_obra) {
        this.id_obra = id_obra;
    }

    private String id_obra;
    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getData_encerrado() {
        return data_encerrado;
    }

    public void setData_encerrado(String data_encerrado) {
        this.data_encerrado = data_encerrado;
    }

    public ArrayList<DiarioItem> listDiarioItem = new ArrayList<DiarioItem>();

    public String getTipos_obra() {
        return tipos_obra;
    }

    public void setTipos_obra(String tipos_obra) {
        this.tipos_obra = tipos_obra;
    }

    public String getArmario() {
        return armario;
    }

    public void setArmario(String armario) {
        this.armario = armario;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_termino() {
        return hora_termino;
    }

    public void setHora_termino(String hora_termino) {
        this.hora_termino = hora_termino;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public int getTecnico_id() {
        return tecnico_id;
    }

    public void setTecnico_id(int tecnico_id) {
        this.tecnico_id = tecnico_id;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getEstacao() {
        return estacao;
    }

    public void setEstacao(String estacao) {
        this.estacao = estacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public Estacao(){}

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

 }
