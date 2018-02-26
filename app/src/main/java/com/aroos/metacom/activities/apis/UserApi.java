package com.aroos.metacom.activities.apis;

import com.aroos.metacom.activities.models.User;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by murilo on 07/09/2016.
 */
public interface UserApi {
    @FormUrlEncoded
    @POST("/androide_send_login.php")
    public void getUser(@Field("user") String user, @Field("pass") String pass, Callback<List<User>> response);
}
