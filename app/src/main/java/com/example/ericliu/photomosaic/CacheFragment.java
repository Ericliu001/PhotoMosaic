package com.example.ericliu.photomosaic;

import android.app.Fragment;
import android.os.Bundle;

/**
 * A simple {@link Fragment} subclass. To be used to save data across configuration changes
 */
public class CacheFragment<T> extends Fragment {



    private T data;

    public CacheFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
