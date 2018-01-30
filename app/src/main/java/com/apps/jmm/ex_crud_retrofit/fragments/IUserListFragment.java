package com.apps.jmm.ex_crud_retrofit.fragments;

import android.support.v7.widget.CardView;
import android.widget.ImageView;

import com.apps.jmm.ex_crud_retrofit.adapter.RecyclerAdapter;
import com.apps.jmm.ex_crud_retrofit.pojo.DataUser;

import java.util.ArrayList;

/**
 * Created by sath on 24/01/18.
 */

public interface IUserListFragment {

    void configLinearRecyclerView();

    RecyclerAdapter createAdapter(ArrayList<DataUser> folders);

    void initializeAdapter(RecyclerAdapter gAdapter);

    RecyclerAdapter loadRecyclerView(ArrayList<DataUser> userList);

    void insertCardInRecyclerView(DataUser user, int pos,RecyclerAdapter adapter);

    void toast(String message);

    void showProgress();

    void hideProgress();

    void goToItemPosition(int position);

    void clickItemEdit(int position, DataUser user, ImageView imageView);

    void clickItemSelect(ImageView imageView);

    void clickItemAdd(ImageView imageView);

    }
