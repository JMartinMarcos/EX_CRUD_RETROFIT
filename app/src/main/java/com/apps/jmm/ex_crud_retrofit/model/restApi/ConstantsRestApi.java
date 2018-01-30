package com.apps.jmm.ex_crud_retrofit.model.restApi;

public class ConstantsRestApi {

    public  static final String ROOT_URL            = "https://hello-world.innocv.com/api/user/";
    private static final String KEY_ID              = "{id}";
    static final         String URL_GET_ALL         = "getall";
    private static final String URL_GET             = "get/";
    static final         String URL_CREATE          = "create";
    static final         String URL_UPDATE          = "update";
    private static final String URL_REMOVE          = "remove/";
    static final         String URL_GET_USER_BY_ID  =  URL_GET + KEY_ID;
    static final         String URL_REMOVE_BY_ID    =  URL_REMOVE + KEY_ID;
    static final         String HEADER_CONNEXION    = "Content-Type: application/json;charset=UTF-8";
}
