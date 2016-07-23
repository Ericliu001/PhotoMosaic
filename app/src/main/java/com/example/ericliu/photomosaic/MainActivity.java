package com.example.ericliu.photomosaic;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements HomePageContract.View {

    private static final String TAG = MainActivity.class.getSimpleName();
    private HomePageContract.HomePresenter mHomePresenter;

    private Button btnPickPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        if (savedInstanceState != null) {
            CacheFragment<HomePageContract.HomePresenter> cacheFragment = (CacheFragment) getFragmentManager().findFragmentByTag(TAG);
            if (cacheFragment != null) {
                mHomePresenter = cacheFragment.getData();
                setupPresenter(mHomePresenter, true);

            }
        } else {
            mHomePresenter = new HomePresenterImpl();
            setupPresenter(mHomePresenter, false);

            CacheFragment<HomePageContract.HomePresenter> cacheFragmentNew = new CacheFragment<>();
            cacheFragmentNew.setData(mHomePresenter);
            getFragmentManager().beginTransaction().add(cacheFragmentNew, TAG).commit();
        }
    }

    private void initViews() {
        btnPickPhoto = (Button) findViewById(R.id.btnPickPhoto);
        btnPickPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHomePresenter.onPickPhotoButtonClicked(v);
            }
        });
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

    @Override
    public Activity activity() {
        return this;
    }


}
