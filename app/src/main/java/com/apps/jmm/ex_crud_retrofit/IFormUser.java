package com.apps.jmm.ex_crud_retrofit;

import com.apps.jmm.ex_crud_retrofit.pojo.DataUser;

public interface IFormUser {
    void configViewInAddUser();

    void configViewInUpdateUser();

    void configViewInFilterListUser();

    void loadFrom(DataUser user, String title);

    DataUser getFormData();

    boolean switchIsChecked();

    String getBirthDateUntil();
}
