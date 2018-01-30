package com.apps.jmm.ex_crud_retrofit.adapter.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.apps.jmm.ex_crud_retrofit.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddUserHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.mCardViewItemAdd)
    public
    CardView mCardViewItemAdd;
    @BindView(R.id.mUserIcon)
    public ImageView mUserIcon;

    public AddUserHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
