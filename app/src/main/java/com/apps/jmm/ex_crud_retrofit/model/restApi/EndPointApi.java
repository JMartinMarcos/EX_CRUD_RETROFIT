package com.apps.jmm.ex_crud_retrofit.model.restApi;
import com.apps.jmm.ex_crud_retrofit.pojo.DataUser;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EndPointApi {
    @Headers({ConstantsRestApi.HEADER_CONNEXION})

    @POST(ConstantsRestApi.URL_CREATE)
    Call<DataUser> newUserCreate(@Body JsonObject params);

    @POST(ConstantsRestApi.URL_UPDATE)
    Call<DataUser> userUpdate(@Body JsonObject params);

    @GET(ConstantsRestApi.URL_GET_ALL)
    Call<ArrayList<DataUser>> getAllUsers();

    @GET(ConstantsRestApi.URL_GET_USER_BY_ID)
    Call<DataUser> getUserById(@Path(JsonKeys.USER_ID) int id);

    @GET(ConstantsRestApi.URL_REMOVE_BY_ID)
    Call<Void> deleteUserById(@Path(JsonKeys.USER_ID) int id);
}
