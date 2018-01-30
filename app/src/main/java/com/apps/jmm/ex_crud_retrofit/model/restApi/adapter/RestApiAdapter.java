package com.apps.jmm.ex_crud_retrofit.model.restApi.adapter;
import com.apps.jmm.ex_crud_retrofit.model.restApi.ConstantsRestApi;
import com.apps.jmm.ex_crud_retrofit.model.restApi.EndPointApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiAdapter {

    public EndPointApi configRestApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantsRestApi.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(EndPointApi.class);
    }
}
