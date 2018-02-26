package com.aroos.metacom.activities.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

/**
 * Created by murilo on 03/04/2016.
 */
public class DiarioImagem implements Serializable {
    private int id;

    private String complemento;
    private String path_file;
    private String file_name;
    private String econdedFile;
    private String tipoAttach;
    private Bitmap photo;
    private Boolean is_correct;
    public Boolean getIs_correct() {
        return is_correct;
    }

    public void setIs_correct(Boolean is_correct) {
        this.is_correct = is_correct;
    }


    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public DiarioImagem(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getPath_file() {
        return path_file;
    }

    public void setPath_file(String path_file) {
        this.path_file = path_file;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getEcondedFile() {
        BitmapFactory.Options options = null;
        options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeFile(getPath_file(),null);
                //options);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Must compress the Image to reduce image size to make upload easy
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byte_arr = stream.toByteArray();
      // byte[] byte_arr = bitmap.toByteArray();
        // Encode Image to String
        return  Base64.encodeToString(byte_arr, 0);

    }
    public String getEcondedFilebyPath(String pathFile) {
        BitmapFactory.Options options = null;
        options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeFile(pathFile,null);
        //options);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Must compress the Image to reduce image size to make upload easy
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byte_arr = stream.toByteArray();
        // byte[] byte_arr = bitmap.toByteArray();
        // Encode Image to String
        return  Base64.encodeToString(byte_arr, 0);

    }
    public String getTipoAttach() {
        return tipoAttach;
    }

    public void setTipoAttach(String tipoAttach) {
        this.tipoAttach = tipoAttach;
    }
}
