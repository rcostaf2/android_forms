package com.aroos.metacom.activities.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

/**
 * Created by murilo on 02/04/2016.
 */
public class Despacho implements Serializable {
    private int id;
    private int atividade_id;
    private String acao;
    private String acao_hora;
    private int tecnico_id;
    private String assinatura;
    private String data_enceramento;
    private String reparo_efetuado;

    public String getAcao_hora() {
        return acao_hora;
    }

    public void setAcao_hora(String acao_hora) {
        this.acao_hora = acao_hora;
    }

    private String observacao;

    public int getAtividade_id() {
        return atividade_id;
    }

    public void setAtividade_id(int atividade_id) {
        this.atividade_id = atividade_id;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public int getTecnico_id() {
        return tecnico_id;
    }

    public void setTecnico_id(int tecnico_id) {
        this.tecnico_id = tecnico_id;
    }

    public String getAssinatura() {
        return assinatura;
    }

    public void setAssinatura(String assinatura) {
        this.assinatura = assinatura;
    }

    public String getData_enceramento() {
        return data_enceramento;
    }

    public void setData_enceramento(String data_enceramento) {
        this.data_enceramento = data_enceramento;
    }

    public String getReparo_efetuado() {
        return reparo_efetuado;
    }

    public void setReparo_efetuado(String reparo_efetuado) {
        this.reparo_efetuado = reparo_efetuado;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getContato_local() {
        return contato_local;
    }

    public void setContato_local(String contato_local) {
        this.contato_local = contato_local;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getAssinatura_1() {
        return assinatura_1;
    }

    public void setAssinatura_1(String assinatura_1) {
        this.assinatura_1 = assinatura_1;
    }

    public String getAssinatura_2() {
        return assinatura_2;
    }

    public void setAssinatura_2(String assinatura_2) {
        this.assinatura_2 = assinatura_2;
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

    private String contato_local;
    private String documento;
    private String assinatura_1;

    private String assinatura_2;
    private String latitude;
    private String longitude;




    public Despacho(){}

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getEcondedFile() {
        BitmapFactory.Options options = null;
        options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeFile(getAssinatura(),
                options);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Must compress the Image to reduce image size to make upload easy
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byte_arr = stream.toByteArray();
        // Encode Image to String
        return  Base64.encodeToString(byte_arr, 0);

    }
}
