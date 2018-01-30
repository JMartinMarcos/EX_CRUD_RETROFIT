package com.apps.jmm.ex_crud_retrofit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.apps.jmm.ex_crud_retrofit.FormUser;
import com.apps.jmm.ex_crud_retrofit.R;
import com.apps.jmm.ex_crud_retrofit.adapter.RecyclerAdapter;
import com.apps.jmm.ex_crud_retrofit.bases.BaseFragment;
import com.apps.jmm.ex_crud_retrofit.constants.NumericConstants;
import com.apps.jmm.ex_crud_retrofit.constants.StringConstants;
import com.apps.jmm.ex_crud_retrofit.pojo.DataUser;
import com.apps.jmm.ex_crud_retrofit.prsenter.IUserPresenter;
import com.apps.jmm.ex_crud_retrofit.prsenter.UserPresenter;
import java.util.ArrayList;
import butterknife.BindView;

public class UserListFragment extends BaseFragment implements IUserListFragment {
    @BindView(R.id.login_progress)
    ProgressBar progressBar;
    @BindView(R.id.mRecycler)
    RecyclerView mRecycler;
    IUserPresenter mPresenter;

    public UserListFragment() {
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_user_list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showProgress();
        mPresenter = new UserPresenter(this, getActivity());
    }

    @Override
    public RecyclerAdapter loadRecyclerView(ArrayList<DataUser> userList){
        RecyclerAdapter adapter = createAdapter(userList);
        initializeAdapter(adapter);
        hideProgress();
        configLinearRecyclerView();
        return adapter;
    }

    @Override
    public void configLinearRecyclerView() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(llm);
    }

    @Override
    public RecyclerAdapter createAdapter(ArrayList<DataUser> dataUser) {
        return new RecyclerAdapter(dataUser, mPresenter);
    }

    @Override
    public void initializeAdapter(RecyclerAdapter gAdapter) {
        mRecycler.setAdapter(gAdapter);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        mRecycler.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        mRecycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void goToItemPosition(int position) {
        mRecycler.smoothScrollToPosition(position);
    }

    @Override
    public void insertCardInRecyclerView(DataUser user, int pos,RecyclerAdapter adapter) {
        RecyclerView.ViewHolder cardView = mRecycler.findViewHolderForAdapterPosition(pos);
        cardView.itemView.setVisibility(View.INVISIBLE);
        adapter.updateCard(user,pos);
    }

    @Override
    public void toast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void clickItemEdit(int position, DataUser user,ImageView imageView){
        Intent intent = new Intent(getActivity(), FormUser.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(StringConstants.DATA_USER, user);
        bundle.putInt(StringConstants.POSITION, position);
        bundle.putString(StringConstants.FORM_CASE, StringConstants.SET_EDIT_FORM);
        intent.putExtra(StringConstants.BUNDLE, bundle);
        ActivityOptionsCompat optionsCompat = configPairTransition(imageView);
        startActivityForResult(intent, NumericConstants.RESULT_OK_UPDATE, optionsCompat.toBundle());
    }

    @Override
    public void clickItemAdd(ImageView imageView){
        Intent intent = new Intent(getActivity(), FormUser.class);
        Bundle bundle = new Bundle();
        bundle.putString(StringConstants.FORM_CASE, StringConstants.SET_NEW_FORM);
        intent.putExtra(StringConstants.BUNDLE, bundle);
        ActivityOptionsCompat optionsCompat = configPairTransition(imageView);
        startActivityForResult(intent, NumericConstants.RESULT_OK_NEW, optionsCompat.toBundle());
    }

    @Override
    public void clickItemSelect(ImageView imageView){
        Intent intent = new Intent(getActivity(), FormUser.class);
        Bundle bundle = new Bundle();
        bundle.putString(StringConstants.FORM_CASE, StringConstants.SET_FILTER_FORM);
        intent.putExtra(StringConstants.BUNDLE, bundle);
        ActivityOptionsCompat optionsCompat = configPairTransition(imageView);
        startActivityForResult(intent, NumericConstants.RESULT_OK_FILTER,optionsCompat.toBundle());
    }

    public ActivityOptionsCompat configPairTransition(ImageView imageView){
        Pair pair = new Pair<View, String>(imageView, getString(R.string.TRANSITION_CIRCLE));
        return ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pair);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.applyResult(resultCode,data);
    }
}
