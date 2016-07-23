package com.example.ericliu.photomosaic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements HomePageContract.View {

    private static final String TAG = MainActivity.class.getSimpleName();
    private HomePageContract.HomePresenter mHomePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            CacheFragment<HomePageContract.HomePresenter> cacheFragment = (CacheFragment) getFragmentManager().findFragmentByTag(TAG);
            if (cacheFragment != null) {
                mHomePresenter = cacheFragment.getData();
                setupPresenter(mHomePresenter, true);

            }
        }else {
            mHomePresenter = new HomePresenterImpl();
            setupPresenter(mHomePresenter, false);

            CacheFragment<HomePageContract.HomePresenter> cacheFragmentNew = new CacheFragment<>();
            cacheFragmentNew.setData(mHomePresenter);
            getFragmentManager().beginTransaction().add(cacheFragmentNew, TAG).commit();
        }
    }


    private void setupPresenter(HomePageContract.HomePresenter homePresenter, boolean isConfigurationChange) {
        if (homePresenter == null) {
            throw new IllegalStateException("Failed to retrieve Presenter.");
        }
        homePresenter.setView(this);
        homePresenter.onViewCreated(isConfigurationChange);
    }



    @Override
    protected void onDestroy() {
        mHomePresenter.onViewDestroyed();
        super.onDestroy();
    }
}
