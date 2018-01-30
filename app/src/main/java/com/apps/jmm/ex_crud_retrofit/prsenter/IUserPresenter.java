package com.apps.jmm.ex_crud_retrofit.prsenter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.widget.ImageView;

import com.apps.jmm.ex_crud_retrofit.pojo.DataUser;

public interface IUserPresenter {

    void getUsersList();

    void updateUser(DataUser user, final int pos);

    void deleteUserRest(final int id, final int pos);

    void newUserRest(final DataUser userModel);

    void getUserById(int id);

    void applyResult(int resultCode, Intent data);

    void onItemClick(int position, DataUser user, ImageView imageVie);

    void onItemClickAdd( ImageView imageVie);

    void onItemClickSelect(ImageView imageView);

    void goToItemPosition(int position);
}
