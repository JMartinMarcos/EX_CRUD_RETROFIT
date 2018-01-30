package com.apps.jmm.ex_crud_retrofit.director;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.apps.jmm.ex_crud_retrofit.IFormUser;
import com.apps.jmm.ex_crud_retrofit.R;
import com.apps.jmm.ex_crud_retrofit.constants.NumericConstants;
import com.apps.jmm.ex_crud_retrofit.constants.StringConstants;
import com.apps.jmm.ex_crud_retrofit.pojo.DataUser;

public class FormUserDir {

    private Intent intent;
    private Activity activity;
    private IFormUser iFormUser;
    private int position;
    private String switcher = "";

    public FormUserDir(IFormUser iFormUser, Intent intent, Activity activity) {
        this.intent = intent;
        this.iFormUser = iFormUser;
        this.activity = activity;
        requestIntent();
    }

    private void requestIntent(){
        Bundle bundle = intent.getBundleExtra(StringConstants.BUNDLE);
        switcher = bundle.getString(StringConstants.FORM_CASE);
        DataUser userModel;

        assert switcher != null;
        switch (switcher){
            case StringConstants.SET_EDIT_FORM:
                position = bundle.getInt(StringConstants.POSITION,0);
                userModel = bundle.getParcelable(StringConstants.DATA_USER);
                iFormUser.loadFrom(userModel,activity.getResources().getString(R.string.title_update_user));
                iFormUser.configViewInUpdateUser();
                break;
            case StringConstants.SET_FILTER_FORM:
                iFormUser.loadFrom(new DataUser(),activity.getResources().getString(R.string.title_filter_user));
                iFormUser.configViewInFilterListUser();
                break;
            default:
                iFormUser.loadFrom(new DataUser(),activity.getResources().getString(R.string.title_new_user));
                iFormUser.configViewInAddUser();
                break;
        }
    }

    public void backToMainOK(){
        DataUser userModel = iFormUser.getFormData();
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt(StringConstants.POSITION,position);
        bundle.putParcelable(StringConstants.DATA_USER,userModel);
        int action=0;
        switch (switcher) {
            case StringConstants.SET_FILTER_FORM:
                if(iFormUser.switchIsChecked()) {
                    action = NumericConstants.RESULT_OK_ID;
                }else{
                    action = NumericConstants.RESULT_OK_FILTER;
                    bundle.putString(StringConstants.FEC_UNTIL,iFormUser.getBirthDateUntil());
                }
                break;
            case StringConstants.SET_EDIT_FORM:   action = NumericConstants.RESULT_OK_UPDATE; break;
            default:                              action = NumericConstants.RESULT_OK_NEW;    break;
        }
        intent.putExtra(StringConstants.BUNDLE, bundle);
        activity.setResult(action, intent);
    }

    public boolean validateForm(int id, String name, String dateTime) {
                if(iFormUser.switchIsChecked()) return id!=0;
                if(switcher.equals(StringConstants.SET_NEW_FORM)||switcher.equals(StringConstants.SET_EDIT_FORM))
                    return !TextUtils.isEmpty(name) && !TextUtils.isEmpty(dateTime);
                else return !TextUtils.isEmpty(name) || !TextUtils.isEmpty(dateTime);
    }
}
