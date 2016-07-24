package com.example.ericliu.photomosaic.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;

import com.example.ericliu.photomosaic.mvp.DisplayView;
import com.example.ericliu.photomosaic.mvp.Presenter;

/**
 * Created by ericliu on 23/07/2016.
 */

public interface HomePageContract {

    interface View extends DisplayView<HomePresenter> {
        void startActivityForResult(Intent intent, int requestCode);

        void displayImage(Bitmap thumbnail);
    }


    interface HomePresenter extends Presenter<View> {

        void onPickPhotoButtonClicked(android.view.View view);

        void onActivityResult(int requestCode, int resultCode, Intent data);
    }


     class StubView implements View{


         @Override
         public void startActivityForResult(Intent intent, int requestCode) {

         }

         @Override
         public void displayImage(Bitmap thumbnail) {

         }

         @Override
         public Activity activity() {
             return null;
         }
     }
}
