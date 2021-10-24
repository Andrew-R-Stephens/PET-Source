package com.TritiumGaming.phasmophobiaevidencepicker.data.data;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;

/**
 * InteractiveViewControllerData class
 *
 * TODO
 *
 * @author TritiumGamingStudios
 */
public class InteractiveViewControllerData {

    private boolean canSetDefaultZoomLevel = true;

    private float zoomLevel = 1f;
    private float panX = 1f, panY = 1f;

    private final Matrix matrix = new Matrix();
    private final BitmapFactory.Options options = new BitmapFactory.Options();

    private int w, h;

    /**
     * incrementZoomLevel
     *
     * TODO
     *
     * @param addZoom
     */
    public void incrementZoomLevel(double addZoom){

        float ZOOM_SENSE = 2f;//1.5f;
        float MIN = .8f, MAX = 4f;

        if((this.zoomLevel += addZoom* ZOOM_SENSE) < MIN)
            this.zoomLevel = MIN;
        else
            if(zoomLevel > MAX)
                this.zoomLevel = MAX;

        updateMatrix();

    }

    /**
     * incrementPan
     *
     * TODO
     *
     * @param addX
     * @param addY
     */
    public void incrementPan(double addX, double addY){

        float PAN_SENSE = 1.5f;

        this.panX += -addX* PAN_SENSE;
        this.panY += -addY* PAN_SENSE;

        updateMatrix();

    }

    /**
     * setDisplaySize
     *
     * TODO
     *
     * @param w
     * @param h
     */
    public void setDisplaySize(int w, int h){
        this.w = w;
        this.h = h;
    }

    /**
     * updateMatrix
     *
     * TODO
     */
    public void updateMatrix(){
        setAutoInSampleSize(w, h);
        options.inJustDecodeBounds = false;
    }

    /**
     * getBitmapFactoryOptions
     *
     * TODO
     *
     * @return
     */
    public BitmapFactory.Options getBitmapFactoryOptions(){
        return options;
    }

    /**
     * postTranslateMatrix
     *
     * TODO
     *
     * @param imgW
     * @param imgH
     * @param viewW
     * @param viewH
     */
    public void postTranslateMatrix(float imgW, float imgH, float viewW, float viewH) {

        if(canSetDefaultZoomLevel){
            float zoomW = viewW/imgW;
            float zoomH = viewH/imgH;
            zoomLevel = Math.min(zoomW, zoomH);

            canSetDefaultZoomLevel = false;
        }

        matrix.setScale(zoomLevel, zoomLevel);
        matrix.postTranslate((viewW / 2f) - (imgW / 2f * zoomLevel) + (panX * zoomLevel), (viewH / 2f) - (imgH / 2f * zoomLevel) + (panY * zoomLevel));

        float[] vals = new float[9];
        matrix.getValues(vals);

        float distance;

        //RIGHT
        float BOUNDS_PADDING = .2f;
        if ((distance = ((vals[2]) + (imgW*zoomLevel))) < viewW* BOUNDS_PADDING) {
            panX -= distance - (viewW* BOUNDS_PADDING);
        }
        //LEFT
        if ((vals[2]) > viewW-(viewW* BOUNDS_PADDING)) {
            panX += ((viewW) - (vals[2]))-(viewW* BOUNDS_PADDING);
        }
        //BOTTOM
        if ((distance = ((vals[5]) + (imgH*zoomLevel))) < viewH* BOUNDS_PADDING) {
            panY -= distance - (viewH* BOUNDS_PADDING);
        }
        //TOP
        if ((vals[5]) > viewH-(viewH* BOUNDS_PADDING)) {
            panY += ((viewH) - (vals[5]))-(viewH* BOUNDS_PADDING);
        }

    }

    /**
     * getMatrix
     *
     * TODO
     *
     * @return
     */
    public Matrix getMatrix(){
        return matrix;
    }

    /**
     * setAutoInSampleSize
     *
     * TODO
     *
     * @param reqWidth
     * @param reqHeight
     */
    public void setAutoInSampleSize(int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (inSampleSize != 0 && ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth)) {
                inSampleSize *= 2;
            }
        }
        options.inSampleSize = inSampleSize;
    }
}
