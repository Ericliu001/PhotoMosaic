package com.example.ericliu.photomosaic;

import android.content.Intent;
import android.view.View;

/**
 * Created by ericliu on 23/07/2016.
 */

public class HomePresenterImpl implements HomePageContract.HomePresenter {

    private static final int REQUESTO_CODE_PICK_IMAGE = 10;
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
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        mView.startActivityForResult(chooserIntent, REQUESTO_CODE_PICK_IMAGE);
    }
}
