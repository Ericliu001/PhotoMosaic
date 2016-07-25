package com.example.ericliu.photomosaic.ui;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

/**
 * Created by ericliu on 25/07/2016.
 */

public class MosaicView extends SurfaceView implements SurfaceHolder.Callback {
    class MosaicThread extends Thread {

        /** Handle to the surface manager object we interact with */
        private SurfaceHolder mSurfaceHolder;


        public MosaicThread(SurfaceHolder surfaceHolder, Context context,
                            Handler handler){

            // get handles to some important objects
            mSurfaceHolder = surfaceHolder;
        }

        @Override
        public void run() {

        }
    }


    /** The thread that actually draws the animation */
    private MosaicThread thread;

    public MosaicView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        // create thread only; it's started in surfaceCreated()
        thread = new MosaicThread(holder, context, new Handler());

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }
}
