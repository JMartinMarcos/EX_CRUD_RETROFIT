package com.apps.jmm.ex_crud_retrofit.adapter.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.jmm.ex_crud_retrofit.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UserDataHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.mDeleteIcon)
    public
    ImageView mDeleteIcon;
    @BindView(R.id.mUserName)
    public
    TextView mUserName;
    @BindView(R.id.mUserBirthDay)
    public
    TextView mUserBirthDay;
    @BindView(R.id.mUserId)
    public
    TextView mUserId;
    @BindView(R.id.mCardViewItem)
    public
    CardView mCardViewItem;
    @BindView(R.id.mUserIcon)
    public
    ImageView mUserIcon;

    public UserDataHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }
}
