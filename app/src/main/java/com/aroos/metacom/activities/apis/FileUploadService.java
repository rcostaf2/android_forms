package com.aroos.metacom.activities.apis;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by murilo on 07/09/2016.
 */
public interface FileUploadService {


    @Multipart
    @POST("/images/assinaturas/upload.php")
    void upload(@Part("myfile") TypedFile file,
                @Part("description") String description,
                Callback<String> cb);
}
