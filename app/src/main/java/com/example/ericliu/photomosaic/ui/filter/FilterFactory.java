package com.example.ericliu.photomosaic.ui.filter;

/**
 * Created by ericliu on 30/07/2016.
 */

public final class FilterFactory {
    private FilterFactory(){}

    public enum FilterType{
        MOSAIC, ONLINE;

    }


    public static ImageFilter getImageFilter(FilterType filterType){
        switch (filterType) {
            case MOSAIC:
                return new MosaicFilter();

            case ONLINE:
                return new OnlineFilter();

            default:
                return null;
        }

    }
}
