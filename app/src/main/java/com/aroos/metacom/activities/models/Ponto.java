package com.aroos.metacom.activities.models;

import java.io.Serializable;

/**
 * Created by murilo on 02/04/2016.
 */
public class Ponto implements Serializable {
    private int id;
    private int tecnico_id;
    private String latitude;
    private String longitude;
    private String acao_hora;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private String data;
    private String hora;

    public String getEnviado() {
        return enviado;
    }

    public void setEnviado(String enviado) {
        this.enviado = enviado;
    }

    private String enviado;

    public int getTecnico_id() {
        return tecnico_id;
    }

    public void setTecnico_id(int tecnico_id) {
        this.tecnico_id = tecnico_id;
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

    public String getAcao_hora() {
        return acao_hora;
    }

    public void setAcao_hora(String acao_hora) {
        this.acao_hora = acao_hora;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Ponto(){}

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

 }
