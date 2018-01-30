package com.apps.jmm.ex_crud_retrofit.bases;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = injectViews();

    }

    protected final void addFragment(@IdRes int containerViewId, Fragment fragment, Activity activity) {
        activity.getFragmentManager()
                .beginTransaction()
                .add(containerViewId, fragment)
                .commit();
    }

    private Unbinder injectViews() {
        return ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public abstract int getLayoutId();

}