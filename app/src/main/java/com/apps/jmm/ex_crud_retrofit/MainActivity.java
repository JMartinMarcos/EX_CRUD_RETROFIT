package com.apps.jmm.ex_crud_retrofit;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.apps.jmm.ex_crud_retrofit.bases.BaseActivity;
import com.apps.jmm.ex_crud_retrofit.fragments.UserListFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.mToolbarId)
    Toolbar mToolbarId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(mToolbarId);
        loadUserListFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    public void loadUserListFragment() {
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mFragment, new UserListFragment());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.mReload:
                loadUserListFragment();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
