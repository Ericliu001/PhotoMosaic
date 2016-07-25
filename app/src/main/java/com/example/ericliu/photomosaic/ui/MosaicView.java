package com.example.ericliu.photomosaic.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by ericliu on 25/07/2016.
 */

public class MosaicView extends SurfaceView implements SurfaceHolder.Callback {

    private final SurfaceHolder holder;
    AtomicBoolean render = new AtomicBoolean();
    private int mImageWidth;
    private int mImageHeight;

    public enum DrawType {
        ORIGINAL_PHOTO, COLOR_MOSAIC;
    }

    /**
     * The drawable to draw the original photo onto canvas
     */
    private Bitmap mOriginalPhoto;

    class MosaicThread extends Thread {

        private DrawType mDrawType = DrawType.ORIGINAL_PHOTO;

        /**
         * Handle to the surface manager object we interact with
         */
        private SurfaceHolder mSurfaceHolder;


        private final Object mRunLock = new Object();

        /**
         * Indicate whether the surface has been created & is ready to draw
         */
        private boolean mRun = false;


        public MosaicThread(SurfaceHolder surfaceHolder, Context context,
                            Handler handler) {

            // get handles to some important objects
            mSurfaceHolder = surfaceHolder;
        }

        @Override
        public void run() {
            while (mRun) {
                while (!render.get()) {
                    continue;
                }

                Canvas c = null;
                try {
                    c = mSurfaceHolder.lockCanvas(null);
                    synchronized (mSurfaceHolder) {
                        // Critical section. Do not allow mRun to be set false until
                        // we are sure all canvas draw operations are complete.
                        //
                        // If mRun has been toggled false, inhibit canvas operations.
                        synchronized (mRunLock) {
                            if (mRun) doDraw(c);
                        }
                    }
                } finally {
                    // do this in a finally so that if an exception is thrown
                    // during the above, we don't leave the Surface in an
                    // inconsistent state
                    if (c != null) {
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }

        }

        /**
         * Draws the photo to the provided
         * Canvas.
         */
        private void doDraw(Canvas canvas) {
            if (mDrawType == DrawType.ORIGINAL_PHOTO && mOriginalPhoto != null) {
                canvas.drawBitmap(mOriginalPhoto, 0, 0, null);
            }
        }


        /**
         * Used to signal the thread whether it should be running or not.
         * Passing true allows the thread to run; passing false will shut it
         * down if it's already running. Calling start() after this was most
         * recently called with false will result in an immediate shutdown.
         *
         * @param b true to run, false to shut down
         */
        public void setRunning(boolean b) {
            // Do not allow mRun to be modified while any canvas operations
            // are potentially in-flight. See doDraw().
            synchronized (mRunLock) {
                mRun = b;
            }
        }


    }


    /**
     * The thread that actually draws the animation
     */
    private MosaicThread thread;

    public MosaicView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // register our interest in hearing about changes to our surface
        holder = getHolder();
        holder.addCallback(this);


    }


    public void resume() {
        if (thread == null) {
            thread = new MosaicThread(holder, getContext(), null);
            thread.setRunning(true);
            thread.start();
        }
    }

    public void pause() {

        thread.setRunning(false);

        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                thread = null;
                retry = false;
            } catch (InterruptedException e) {
            }
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        render.set(false);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        render.compareAndSet(false, true);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        render.set(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mImageWidth == 0 || mImageHeight == 0) {
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        } else {
            setMeasuredDimension(mImageWidth, mImageHeight);
        }
    }


    /**
     * Fetches the animation thread corresponding to this LunarView.
     *
     * @return the animation thread
     */
    public MosaicThread getThread() {
        return thread;
    }

    public void setOriginalPhoto(final Bitmap bm) {
        post(new Runnable() {
            @Override
            public void run() {
                float ratio = bm.getHeight() / (float) bm.getWidth();
                mImageWidth = getWidth();
                mImageHeight = Math.round(mImageWidth * ratio);

                mOriginalPhoto = Bitmap.createScaledBitmap(bm, mImageWidth, mImageHeight, false);
                requestLayout();
                invalidate();
            }
        });
    }
}
