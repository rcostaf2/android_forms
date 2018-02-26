package com.aroos.metacom.activities.apis;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by murilo on 07/09/2016.
 */
public interface DiarioEndApi {

    /*Retrofit get annotation with our URL
       And our method that will return us the list ob Book
    */
    @FormUrlEncoded
    @POST("/androide_diario_json.php")
    public void send_data(@Field("key") String key,
                          @Field("user_id") String user_id, @Field("field") String field, @Field("i") String i,
                          @Field("abertura_id") String abertura_id,@Field("infojson") String infojson,
                          Callback<String> response);
}