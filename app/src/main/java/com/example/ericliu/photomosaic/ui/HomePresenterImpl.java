package com.example.ericliu.photomosaic.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ericliu on 23/07/2016.
 */

public class HomePresenterImpl implements HomePageContract.HomePresenter {

    private static final int REQUEST_IMAGE_GET = 10;
    private HomePageContract.View mView;
    private Bitmap mBitmap;

    @Override
    public void setView(HomePageContract.View view) {

        mView = view;
    }

    @Override
    public void onViewCreated(boolean isConfigurationChange) {
        if (isConfigurationChange) {
            if (mBitmap != null) {
                mView.displayImage(mBitmap);
            }
        }
    }

    @Override
    public void onViewDestroyed() {
        mView = new HomePageContract.StubView();
    }

    @Override
    public void onPickPhotoButtonClicked(View view) {
        selectImage();
    }

    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(mView.activity().getPackageManager()) != null) {
            mView.startActivityForResult(intent, REQUEST_IMAGE_GET);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
            Bitmap thumbnail = data.getParcelableExtra("data");
            Uri fullPhotoUri = data.getData();

            try {
                mBitmap = MediaStore.Images.Media.getBitmap(mView.activity().getContentResolver(), fullPhotoUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void refreshBitmap(Bitmap mosaicBitmap) {
        mBitmap = mosaicBitmap;
    }

    @Override
    public void onViewResumed() {
        if (mBitmap != null) {
            mView.displayImage(mBitmap);
        }
    }
}
