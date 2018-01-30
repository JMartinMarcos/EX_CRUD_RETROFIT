package com.apps.jmm.ex_crud_retrofit.prsenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.widget.ImageView;

import com.apps.jmm.ex_crud_retrofit.R;
import com.apps.jmm.ex_crud_retrofit.adapter.RecyclerAdapter;
import com.apps.jmm.ex_crud_retrofit.constants.NumericConstants;
import com.apps.jmm.ex_crud_retrofit.constants.StringConstants;
import com.apps.jmm.ex_crud_retrofit.fragments.IUserListFragment;
import com.apps.jmm.ex_crud_retrofit.model.restApi.EndPointApi;
import com.apps.jmm.ex_crud_retrofit.model.restApi.JsonKeys;
import com.apps.jmm.ex_crud_retrofit.model.restApi.adapter.RestApiAdapter;
import com.apps.jmm.ex_crud_retrofit.pojo.DataUser;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.util.ArrayList;

import javax.annotation.ParametersAreNonnullByDefault;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.apps.jmm.ex_crud_retrofit.model.restApi.JsonKeys.RESPONSE_OK;


public class UserPresenter implements IUserPresenter {

    private Activity activity;
    private IUserListFragment iUserListFragment;
    private RecyclerAdapter adapter;

    public UserPresenter(IUserListFragment iUserListFragment, Activity activity) {
        this.activity = activity;
        this.iUserListFragment = iUserListFragment;
        getUsersList();
    }

    @Override
    @ParametersAreNonnullByDefault
    public void getUsersList() {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        EndPointApi endPointApi = restApiAdapter.configRestApi();
        Call<ArrayList<DataUser>> userCall = endPointApi.getAllUsers();

        userCall.enqueue(new Callback<ArrayList<DataUser>>() {
            @Override
            public void onResponse(Call<ArrayList<DataUser>> call, @Nullable Response<ArrayList<DataUser>> response) {
                if (response != null && response.isSuccessful()) {
                    ArrayList<DataUser> dataUserList = initializeDataUserList();
                    dataUserList.addAll(response.body());
                    adapter = iUserListFragment.loadRecyclerView(dataUserList);
                } else {
                    iUserListFragment.toast(activity.getResources().getString(R.string.error_get_all_users));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DataUser>> call, Throwable t) {
                iUserListFragment.toast(activity.getResources().getString(R.string.error_get_all_users));
            }
        });
    }

    @Override
    @ParametersAreNonnullByDefault
    public void getUserById(int id) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        EndPointApi endPointApi = restApiAdapter.configRestApi();
        Call<DataUser> findUser = endPointApi.getUserById(id);
        findUser.enqueue(new Callback<DataUser>() {
            @Override
            public void onResponse(Call<DataUser> call, @Nullable Response<DataUser> response) {
                if (response != null && response.isSuccessful() && response.body() != null) {
                    ArrayList<DataUser> model = initializeDataUserList();
                    model.add(response.body());
                    iUserListFragment.loadRecyclerView(model);
                } else {
                    iUserListFragment.toast(activity.getResources().getString(R.string.error_request_id));
                }
            }

            @Override
            public void onFailure(Call<DataUser> call, Throwable t) {
                iUserListFragment.toast(activity.getResources().getString(R.string.error_request_id));
            }
        });
    }

    @Override
    @ParametersAreNonnullByDefault
    public void newUserRest(final DataUser userModel) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        EndPointApi endPointApi = restApiAdapter.configRestApi();
        Call<DataUser> createUser = endPointApi.newUserCreate(userModelJson(userModel));
        createUser.enqueue(new Callback<DataUser>() {
            @Override
            public void onResponse(Call<DataUser> call, @Nullable Response<DataUser> response) {
                if (response != null && response.isSuccessful()) {
                    adapter.addCardUser(response.body());
                } else {
                    iUserListFragment.toast(activity.getResources().getString(R.string.error_add_usr));
                }
            }

            @Override
            public void onFailure(Call<DataUser> call, Throwable t) {
                iUserListFragment.toast(activity.getResources().getString(R.string.error_add_usr));
            }
        });
    }

    @Override
    @ParametersAreNonnullByDefault
    public void updateUser(DataUser user, final int pos) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        EndPointApi endPointApi = restApiAdapter.configRestApi();
        Call<DataUser> users = endPointApi.userUpdate(userModelJson(user));
        users.enqueue(new Callback<DataUser>() {
            @Override
            public void onResponse(Call<DataUser> call, @Nullable Response<DataUser> response) {
                if (response != null && response.isSuccessful()) {
                    if (RESPONSE_OK == response.code()) {
                        iUserListFragment.insertCardInRecyclerView(response.body(), pos, adapter);
                    } else {

                        iUserListFragment.toast(activity.getResources().getString(R.string.error_user_update));
                    }
                }
            }

            @Override
            public void onFailure(Call<DataUser> call, Throwable t) {
                iUserListFragment.toast(activity.getResources().getString(R.string.error_user_update));
            }
        });
    }

    @Override
    @ParametersAreNonnullByDefault
    public void deleteUserRest(final int id, final int pos) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        EndPointApi endPointApi = restApiAdapter.configRestApi();
        Call<Void> delUser = endPointApi.deleteUserById(id);
        delUser.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, @Nullable Response<Void> response) {
                if (response != null && response.isSuccessful()) {
                    adapter.deleteCard(pos);
                } else {
                    iUserListFragment.toast(activity.getResources().getString(R.string.error_delete_user));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                iUserListFragment.toast(activity.getResources().getString(R.string.error_delete_user));
            }
        });
    }

    private JsonObject userModelJson(DataUser userModel) {
        JsonObject jsonObjectUser = new JsonObject();
        jsonObjectUser.addProperty(JsonKeys.USER_ID, userModel.getId());
        jsonObjectUser.addProperty(JsonKeys.USER_NAME, userModel.getName());
        jsonObjectUser.addProperty(JsonKeys.USER_BIRD_DATE, userModel.getBirthDate() + StringConstants.FORMAT_TIME);
        return jsonObjectUser;
    }

    @Override
    public void onItemClick(int position, DataUser user, ImageView imageVie) {
        iUserListFragment.clickItemEdit(position, user, imageVie);
    }

    @Override
    public void onItemClickAdd(ImageView imageVie) {
        iUserListFragment.clickItemAdd(imageVie);
    }

    @Override
    public void onItemClickSelect(ImageView imageVie) {
        iUserListFragment.clickItemSelect(imageVie);
    }

    public void goToItemPosition(int position) {
        iUserListFragment.goToItemPosition(position);
    }

    public void applyResult(int resultCode, Intent data) {
        if (data != null) {
            Bundle bundle = data.getBundleExtra(StringConstants.BUNDLE);
            DataUser model = bundle.getParcelable(StringConstants.DATA_USER);
            switch (resultCode) {
                case NumericConstants.RESULT_OK_UPDATE:
                    int pos = bundle.getInt(StringConstants.POSITION, 0);
                    updateUser(model, pos);
                    break;
                case NumericConstants.RESULT_OK_NEW:
                    newUserRest(model);
                    break;
                case NumericConstants.RESULT_OK_ID:
                    if (model != null) {
                        getUserById(model.getId());
                    }
                    break;
                case NumericConstants.RESULT_OK_FILTER:
                    String name = null;
                    String dateSince = null;
                    if (model != null) {
                        name = model.getName();
                        dateSince = model.getBirthDate();
                    }
                    String dateUntil = bundle.getString(StringConstants.FEC_UNTIL);
                    try {
                        adapter.applyFilter(name, dateSince, dateUntil);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private ArrayList<DataUser> initializeDataUserList() {
        ArrayList<DataUser> arrayListDataUser = new ArrayList<>();
        DataUser dataUserItem1 = new DataUser(0, "",StringConstants.DATE_MIN_CONSTANT);
        DataUser dataUserItem2 = new DataUser(0, "",StringConstants.DATE_MIN_CONSTANT);
        arrayListDataUser.add(dataUserItem1);
        arrayListDataUser.add(dataUserItem2);
        return arrayListDataUser;
    }
}
