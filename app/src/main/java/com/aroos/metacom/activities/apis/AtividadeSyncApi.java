package com.aroos.metacom.activities.apis;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by murilo on 07/09/2016.
 */
public interface AtividadeSyncApi {

    /*Retrofit get annotation with our URL
       And our method that will return us the list ob Book
    */
    @FormUrlEncoded
    @POST("/androide_sync_ativ.php")
    public void returnAtividades(@Field("user_id") String user_id,@Field("atividades") String atividades, Callback<String> response);
}