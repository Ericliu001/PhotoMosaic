package com.example.ericliu.photomosaic.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.ericliu.photomosaic.CacheFragment;
import com.example.ericliu.photomosaic.R;

public class MainActivity extends AppCompatActivity implements HomePageContract.View {

    private static final String TAG = MainActivity.class.getSimpleName();
    private HomePageContract.HomePresenter mHomePresenter;

    private Button btnPickPhoto, btnMosaic;
    private MosaicView ivMain;

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

        btnMosaic = (Button) findViewById(R.id.btnMosaic);
        btnMosaic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 25/07/2016  adds logic to draw mosaic
//                Bitmap mosaicBitmap = ivMain.drawMosaic();
//                mHomePresenter.refreshBitmap(mosaicBitmap);
//                ivMain.addSomeFutures();

                ivMain.renderVertically();
            }
        });
        ivMain = (MosaicView) findViewById(R.id.ivMain);
    }


    private void setupPresenter(HomePageContract.HomePresenter homePresenter, boolean isConfigurationChange) {
        if (homePresenter == null) {
            throw new IllegalStateException("Failed to retrieve Presenter.");
        }
        homePresenter.setView(this);
        homePresenter.onViewCreated(isConfigurationChange);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ivMain.resume();
        mHomePresenter.onViewResumed();
    }

    @Override
    protected void onPause() {
        ivMain.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mHomePresenter.onViewDestroyed();
        ivMain.cancelAllTasks();
        super.onDestroy();
    }

    @Override
    public Activity activity() {
        return this;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mHomePresenter.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void displayImage(Bitmap bitmap) {
        ivMain.setBackgroundBitmap(bitmap);
    }
}
