package com.aroos.metacom.activities.apis;

import com.aroos.metacom.activities.models.Atividade;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by murilo on 07/09/2016.
 */
public interface AtividadeApi {

    /*Retrofit get annotation with our URL
       And our method that will return us the list ob Book
    */
    @FormUrlEncoded
    @POST("/androide_send_ativ.php")
    public void getAtividades(@Field("user_id") String user_id, Callback<List<Atividade>> response);
}