package com.example.ericliu.photomosaic;

/**
 * Created by ericliu on 23/07/2016.
 */

public class HomePresenterImpl implements HomePageContract.HomePresenter {

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
}
