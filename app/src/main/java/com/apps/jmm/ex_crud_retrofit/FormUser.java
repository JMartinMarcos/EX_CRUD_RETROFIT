package com.apps.jmm.ex_crud_retrofit;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.jmm.ex_crud_retrofit.bases.BaseActivity;
import com.apps.jmm.ex_crud_retrofit.constants.StringConstants;
import com.apps.jmm.ex_crud_retrofit.director.FormUserDir;
import com.apps.jmm.ex_crud_retrofit.fragments.DatePickerFragment;
import com.apps.jmm.ex_crud_retrofit.pojo.DataUser;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class FormUser extends BaseActivity implements IFormUser {
    @BindView(R.id.nToolbar)
    Toolbar nToolbar;
    @BindView(R.id.nUserActionTitle)
    TextView nUserActionTitle;
    @BindView(R.id.nIdUser)
    AutoCompleteTextView nIdUser;
    @BindView(R.id.nUserName)
    AutoCompleteTextView nUserName;
    @BindView(R.id.nBirthDate)
    EditText nBirthDate;
    @BindView(R.id.nBirthDateUntil)
    EditText nBirthDateUntil;
    @BindView(R.id.nBirthDateUntilHint)
    TextInputLayout nBirthDateUntilHint;
    @BindView(R.id.nBirthDateSinceHint)
    TextInputLayout nBirthDateSinceHint;
    @BindView(R.id.nIdUserHint)
    TextInputLayout nIdUserHint;
    @BindView(R.id.nSwitch)
    Switch nSwitch;

    FormUserDir formUserDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(nToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        formUserDir = new FormUserDir(this, intent, this);
    }

    @Override
    public void configViewInAddUser() {
        nIdUser.setVisibility(View.INVISIBLE);
        nIdUserHint.setVisibility(View.INVISIBLE);
        nSwitch.setVisibility(View.INVISIBLE);
        nBirthDateUntil.setVisibility(View.INVISIBLE);
        nBirthDateUntilHint.setVisibility(View.INVISIBLE);
        nUserName.requestFocus();
    }

    @Override
    public void configViewInUpdateUser() {
        nIdUser.setEnabled(false);
        nSwitch.setVisibility(View.INVISIBLE);
        nBirthDateUntil.setVisibility(View.INVISIBLE);
        nBirthDateUntilHint.setVisibility(View.INVISIBLE);
        nBirthDateUntilHint.setHint(getResources().getText(R.string.birth_data_since));
        nBirthDateSinceHint.setHint(getResources().getText(R.string.birth_data_until));
        nUserName.requestFocus();
    }

    @Override
    public void configViewInFilterListUser() {
        nSwitch.setVisibility(View.VISIBLE);
        nBirthDateUntil.setVisibility(View.VISIBLE);
        nBirthDateUntilHint.setVisibility(View.VISIBLE);
        nBirthDateUntilHint.setHint(getResources().getText(R.string.birth_data_since));
        nBirthDateSinceHint.setHint(getResources().getText(R.string.birth_data_until));
        nSwitch.setChecked(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_user;
    }

    @Override
    public void loadFrom(DataUser user, String title) {
        nUserName.setText(user.getName());
        nBirthDate.setText(user.getBirthDate());
        nUserActionTitle.setText(title);
        nIdUser.setText(String.valueOf(user.getId()));
    }


    @Override
    public DataUser getFormData() {
        return new DataUser(Integer.parseInt(nIdUser.getText().toString()), nUserName.getText().toString(), nBirthDate.getText().toString());
    }

    @Override
    public String getBirthDateUntil() {
        return nBirthDateUntil.getText().toString();
    }

    @OnClick(R.id.nOkButton)
    public void clickButtonOk() {
        nSwitch.isEnabled();
        if (formUserDir.validateForm(Integer.parseInt(nIdUser.getText().toString()), nUserName.getText().toString(), nBirthDate.getText().toString())) {
            formUserDir.backToMainOK();
            supportFinishAfterTransition();
        } else {
            Toast.makeText(this, getResources().getText(R.string.error_form_empty), Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.nCancelButton)
    public void clickButtonCancel() {
        finishAfterTransition();
    }

    @OnClick(R.id.nBirthDate)
    public void touchBirthDateInput() {
        dataPickerLaunch(nBirthDate);
    }

    @OnClick(R.id.nBirthDateUntil)
    public void touchBirthDateUntilInput() {
        dataPickerLaunch(nBirthDateUntil);
    }


    public void dataPickerLaunch(final EditText editText) {
        DialogFragment newFragment = new DatePickerFragment().newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = String.format(Locale.getDefault(), StringConstants.FORMAT_DATE_SHORT, year, (month + 1), day);
                editText.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), StringConstants.DATE_PICKER);
    }


    @Override
    public boolean switchIsChecked() {
        return nSwitch.isChecked();
    }

    private void showIdForQuery() {
        nIdUser.setEnabled(true);
        nUserName.setEnabled(false);
        nBirthDate.setEnabled(false);
        nBirthDateUntil.setEnabled(false);
    }

    private void showNameDateForQuery() {
        nIdUser.setEnabled(false);
        nUserName.setEnabled(true);
        nBirthDate.setEnabled(true);
        nBirthDateUntil.setEnabled(true);
        nUserName.requestFocus();
    }

    @OnCheckedChanged(R.id.nSwitch)
    public void checkedSwitch() {
        if (nSwitch.isChecked()) {
            showIdForQuery();
        } else {
            showNameDateForQuery();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finishAfterTransition();
        return true;
    }
}
