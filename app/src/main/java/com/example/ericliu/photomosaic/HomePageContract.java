package com.example.ericliu.photomosaic;

import com.example.ericliu.photomosaic.mvp.DisplayView;
import com.example.ericliu.photomosaic.mvp.Presenter;

/**
 * Created by ericliu on 23/07/2016.
 */

public interface HomePageContract {

    interface View extends DisplayView<HomePresenter> {}


    interface HomePresenter extends Presenter<View> {

    }


     class StubView implements View{


    }
}
