package com.aroos.metacom.activities.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

/**
 * Created by murilo on 02/04/2016.
 */
public class User implements Serializable {
    private int id;
    private int external_id;
    private String nome;
    private String login;
    private String email;
    private String path_assinatura;
    private String file_name_assinatura;
    private int in_use;
    private int perfil;

    public int getPerfil() {
        return perfil;
    }

    public void setPerfil(int perfil) {
        this.perfil = perfil;
    }

    private String senha;


    public User(){}
    public User(int id, int external_id, String nome, String email, String senha, String login, int perfil){
        this.id = id;
        this.external_id = external_id;
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public int getIn_use() {
        return in_use;
    }

    public void setIn_use(int in_use) {
        this.in_use = in_use;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPath_assinatura() {
        return path_assinatura;
    }

    public void setPath_assinatura(String path_assinatura) {
        this.path_assinatura = path_assinatura;
    }

    public String getFile_name_assinatura() {
        return file_name_assinatura;
    }

    public void setFile_name_assinatura(String file_name_assinatura) {
        this.file_name_assinatura = file_name_assinatura;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    public int getExternal_id() {
        return external_id;
    }

    public void setExternal_id(int external_id) {
        this.external_id = external_id;
    }

    @Override
    public String toString() {
        return getNome()+"("+ getLogin()+")";
    }
    public String getEcondedFile() {
        BitmapFactory.Options options = null;
        options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeFile(getPath_assinatura(),
                options);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Must compress the Image to reduce image size to make upload easy
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byte_arr = stream.toByteArray();
        // Encode Image to String
        return  Base64.encodeToString(byte_arr, 0);

    }
}
