package com.aroos.metacom.activities.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by murilo on 02/04/2016.
 */
public class DataHelper implements Serializable {



    private String data_completa;
    private String hora_atual;

    public String getData_full() {
        return data_full;
    }

    public void setData_full(String data_full) {
        this.data_full = data_full;
    }

    private String data_resumida;
    private String data_full;
    private String data_key;
    private String data_hora_key;

    public String getData_hora_key() {
        return data_hora_key;
    }

    public void setData_hora_key(String data_hora_key) {
        this.data_hora_key = data_hora_key;
    }

    public String getData_key() {
        return data_key;
    }

    public void setData_key(String data_key) {
        this.data_key = data_key;
    }

    public DataHelper(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFormat_data = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormat_full = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat dateFormat_key = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat dateFormat_keyhora = new SimpleDateFormat("HHmmss");
        Date data = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        Date data_atual = cal.getTime();
        this.data_completa = dateFormat.format(data_atual);
        this.hora_atual = dateFormat_hora.format(data_atual);
        this.data_resumida = dateFormat_data.format(data_atual);
        this.data_full = dateFormat_full.format(data_atual);
        this.data_key = dateFormat_key.format(data_atual);
        this.data_hora_key = dateFormat_keyhora.format(data_atual);

    }
    public String getHora_atual() {
        return hora_atual;
    }

    public void setHora_atual(String hora_atual) {
        this.hora_atual = hora_atual;
    }

    public String getData_resumida() {
        return data_resumida;
    }

    public void setData_resumida(String data_resumida) {
        this.data_resumida = data_resumida;
    }
    public String getData_completa() {
        return data_completa;
    }

    public void setData_completa(String data_completa) {
        this.data_completa = data_completa;
    }

}
