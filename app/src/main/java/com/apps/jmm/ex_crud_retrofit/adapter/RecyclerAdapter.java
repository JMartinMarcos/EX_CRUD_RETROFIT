package com.apps.jmm.ex_crud_retrofit.adapter;

import android.animation.Animator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;

import com.apps.jmm.ex_crud_retrofit.R;
import com.apps.jmm.ex_crud_retrofit.adapter.holder.AddUserHolder;
import com.apps.jmm.ex_crud_retrofit.adapter.holder.FilterListHolder;
import com.apps.jmm.ex_crud_retrofit.adapter.holder.UserDataHolder;
import com.apps.jmm.ex_crud_retrofit.constants.StringConstants;
import com.apps.jmm.ex_crud_retrofit.pojo.DataUser;
import com.apps.jmm.ex_crud_retrofit.prsenter.IUserPresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<DataUser> dataUserList;
    private IUserPresenter mPresenter;

    public RecyclerAdapter(ArrayList<DataUser> dataUserList, IUserPresenter mPresenter) {
        this.dataUserList = dataUserList;
        this.mPresenter = mPresenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            case VIEW_TYPES.NewUser:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_add_user, parent, false);
                return new AddUserHolder(v);
            case VIEW_TYPES.SelectUser:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_filter_users, parent, false);
                return new FilterListHolder(v);
            default:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
                return new UserDataHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case VIEW_TYPES.Normal:
                final int index = position;
                String date = dataUserList.get(position).getBirthDate();
                final UserDataHolder item1 = (UserDataHolder) holder;
                if (!date.isEmpty()) {
                    String year = date.substring(0, 4);
                    String month = date.substring(5, 7);
                    String day = date.substring(8, 10);
                    String formatDate = year + '-' + month + '-' + day;
                    dataUserList.get(index).setBirthDate(formatDate);
                    item1.mUserBirthDay.setText(formatDate);
                }
                item1.mUserId.setText(String.valueOf(dataUserList.get(position).getId()));
                item1.mUserName.setText(dataUserList.get(position).getName());

                item1.mCardViewItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.onItemClick(index, dataUserList.get(index), item1.mUserIcon);
                    }
                });
                item1.mDeleteIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.deleteUserRest(dataUserList.get(index).getId(), index);
                    }
                });
                break;
            case VIEW_TYPES.NewUser:
                final AddUserHolder userAdd = (AddUserHolder) holder;
                userAdd.mCardViewItemAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.onItemClickAdd(userAdd.mUserIcon);
                    }
                });
                break;
            case VIEW_TYPES.SelectUser:
                final FilterListHolder listHolder = (FilterListHolder) holder;
                listHolder.mCardViewItemSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.onItemClickSelect(listHolder.mIcon);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPES.NewUser;
        } else if (position == 1) {
            return VIEW_TYPES.SelectUser;
        } else return VIEW_TYPES.Normal;
    }

    @Override
    public int getItemCount() {
        return dataUserList.size();
    }


    public void deleteCard(int position) {
        dataUserList.remove(position);
        notifyItemRemoved(position);
    }

    public void updateCard(DataUser model, int pos) {
        dataUserList.get(pos).setBirthDate(model.getBirthDate());
        dataUserList.get(pos).setName(model.getName());
        notifyItemChanged(pos);
    }

    public void addCardUser(DataUser model) {
        dataUserList.add(model);
        notifyItemInserted(getItemCount());
        mPresenter.goToItemPosition(getItemCount()+1);
    }

    private void animateCircularReveal(View v) {
        int centerX = 0;
        int centerY = 0;
        int startRadius = 0;
        int endRadius = Math.max(v.getWidth(), v.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(v, centerX, centerY, startRadius, endRadius);
        animator.setDuration(700);
        v.setVisibility(View.VISIBLE);
        animator.start();
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        animateCircularReveal(holder.itemView);
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    public void applyFilter(String name, String dateSince, String dateUntil) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(StringConstants.FORMAT_DATE_BASIC, Locale.getDefault());
        Date dateSinceD;
        Date dateUntilD;
        Date userBirthDate;
        String dateS, dateB, dateU;
        int i = 0;
        Iterator<DataUser> it = dataUserList.iterator();
        while (it.hasNext()) {
            DataUser user = it.next();
            boolean voidDates = dateSince.isEmpty() || dateUntil.isEmpty() || user.getBirthDate().isEmpty();
            boolean userNameOk = user.getName().toLowerCase().contains(name.toLowerCase()) || name.isEmpty();
            if (!voidDates && userNameOk) {
                dateS = dateSince.substring(0, 10);
                dateU = dateUntil.substring(0, 10);
                dateB = user.getBirthDate().substring(0, 10);
                dateSinceD    = dateFormat.parse(dateS);
                userBirthDate = dateFormat.parse(dateB);
                dateUntilD    = dateFormat.parse(dateU);
                boolean betweenDates = dateSinceD.before(userBirthDate) && dateUntilD.after(userBirthDate);
                if (!betweenDates && userNameOk) it.remove();

            } else if (!userNameOk) {
                if (i > 1) it.remove();
            }
            i++;
        }
        notifyDataSetChanged();
    }

    private class VIEW_TYPES {
        private static final int NewUser    = 1;
        private static final int SelectUser = 2;
        private static final int Normal     = 3;
    }

}
