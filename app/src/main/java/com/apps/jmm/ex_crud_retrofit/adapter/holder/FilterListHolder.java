package com.apps.jmm.ex_crud_retrofit.adapter.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.apps.jmm.ex_crud_retrofit.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FilterListHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.mCardViewItemSelect)
    public
    CardView mCardViewItemSelect;
    @BindView(R.id.mUserIcon)
    public ImageView mIcon;

    public FilterListHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
