package com.example.ericliu.photomosaic;

import android.app.Activity;
import android.content.Intent;

import com.example.ericliu.photomosaic.mvp.DisplayView;
import com.example.ericliu.photomosaic.mvp.Presenter;

/**
 * Created by ericliu on 23/07/2016.
 */

public interface HomePageContract {

    interface View extends DisplayView<HomePresenter> {
        void startActivityForResult(Intent intent, int requestCode);
    }


    interface HomePresenter extends Presenter<View> {

        void onPickPhotoButtonClicked(android.view.View view);
    }


     class StubView implements View{


         @Override
         public void startActivityForResult(Intent intent, int requestCode) {

         }

         @Override
         public Activity activity() {
             return null;
         }
     }
}
