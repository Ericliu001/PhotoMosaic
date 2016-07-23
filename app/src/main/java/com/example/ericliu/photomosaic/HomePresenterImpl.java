package com.example.ericliu.photomosaic;

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

    @Override
    public void setView(HomePageContract.View view) {

        mView = view;
    }

    @Override
    public void onViewCreated(boolean isConfigurationChange) {

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
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(mView.activity().getContentResolver(), fullPhotoUri);
            // Do work with photo saved at fullPhotoUri
                mView.displayImage(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
