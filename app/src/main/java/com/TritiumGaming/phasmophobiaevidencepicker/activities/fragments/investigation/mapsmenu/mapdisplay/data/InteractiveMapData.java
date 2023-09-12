package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.data;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.geometry.Point2D;

import java.util.Arrays;

/**
 * InteractiveViewControllerData class
 *
 * @author TritiumGamingStudios
 */
public class InteractiveMapData {

    private boolean canSetDefaultZoomLevel = true;

    private float zoomLevel = 1f;
    private float panX = 1f, panY = 1f;

    private Matrix matrix = new Matrix();
    private BitmapFactory.Options options = new BitmapFactory.Options();

    private int imgW, imgH;
    private int w, h;

    private Point2D.Float selectedPoint;

    private float ZOOM_MIN = .8f, ZOOM_MAX = 4f;

    public InteractiveMapData() {}

    public void deepCopy(InteractiveMapData otherData) {
        this.canSetDefaultZoomLevel = otherData.canSetDefaultZoomLevel;
        this.zoomLevel = otherData.zoomLevel;
        this.panX = otherData.panX;
        this.panY = otherData.panY;

        float[] vals = new float[9];
        otherData.matrix.getValues(vals);
        this.matrix.setValues(vals);

        this.options.inJustDecodeBounds = otherData.options.inJustDecodeBounds;
        this.options.outWidth = otherData.options.outWidth;
        this.options.outHeight = otherData.options.outHeight;
        this.options.inSampleSize = otherData.options.inSampleSize;

        this.imgW = otherData.imgW;
        this.imgH = otherData.imgH;
        this.w = otherData.w;
        this.h = otherData.h;

        if(otherData.getSelectedPoint() != null) {
            Point2D.Float selPoint = otherData.getSelectedPoint();
            this.selectedPoint = new Point2D.Float(selPoint.x, selPoint.y);
        }

        this.ZOOM_MIN = otherData.ZOOM_MIN;
        this.ZOOM_MAX = otherData.ZOOM_MAX;
    }

    /**
     * @param zoom
     */
    public void updateZoomLevel(double zoom) {

        float ZOOM_SENSE = 2f;//1.5f;

        ZOOM_MIN = (float)w / (float)imgW;
        Log.d("Zoom", w + " " + h + " "  + ZOOM_MIN);

        if ((this.zoomLevel += zoom * ZOOM_SENSE) < ZOOM_MIN) {
            this.zoomLevel = ZOOM_MIN;
        }
        else if (zoomLevel > ZOOM_MAX) {
            this.zoomLevel = ZOOM_MAX;
        }

        updateMatrix();

    }

    /**
     * @param addX
     * @param addY
     */
    public void incrementPan(double addX, double addY) {

        float PAN_SENSE = 1.5f;

        this.panX += -addX * PAN_SENSE;
        this.panY += -addY * PAN_SENSE;

        updateMatrix();

    }

    public void setPan(float x, float y) {

        this.panX = x;
        this.panY = y;

        updateMatrix();

    }

    /**
     * @param w
     * @param h
     */
    public void setDisplaySize(int w, int h) {
        this.w = w;
        this.h = h;
    }

    public void setPressedPoint(Point2D.Float point) {
        selectedPoint = point;
    }

    /**
     *
     */
    public void updateMatrix() {
        setAutoInSampleSize(w, h);
        options.inJustDecodeBounds = false;
    }

    /**
     * @return
     */
    public BitmapFactory.Options getBitmapFactoryOptions() {
        return options;
    }

    /**
     * @param imgW
     * @param imgH
     * @param viewW
     * @param viewH
     */
    public void postCenterTranslateMatrix(float imgW, float imgH, float viewW, float viewH) {

        if (canSetDefaultZoomLevel) {
            float zoomW = viewW / imgW;
            float zoomH = viewH / imgH;
            zoomLevel = Math.min(zoomW, zoomH);

            canSetDefaultZoomLevel = false;
        }

        matrix.setScale(zoomLevel, zoomLevel);
        matrix.postTranslate(
                (viewW / 2f) - (imgW / 2f * zoomLevel) + (panX * zoomLevel),
                (viewH / 2f) - (imgH / 2f * zoomLevel) + (panY * zoomLevel));

        float[] vals = new float[9];
        matrix.getValues(vals);

        float distance;

        //RIGHT
        float BOUNDS_PADDING = .2f;
        if ((distance = ((vals[2]) + (imgW * zoomLevel))) < viewW * BOUNDS_PADDING) {
            panX -= distance - (viewW * BOUNDS_PADDING);
        }
        //LEFT
        if ((vals[2]) > viewW - (viewW * BOUNDS_PADDING)) {
            panX += ((viewW) - (vals[2])) - (viewW * BOUNDS_PADDING);
        }
        //BOTTOM
        if ((distance = ((vals[5]) + (imgH * zoomLevel))) < viewH * BOUNDS_PADDING) {
            panY -= distance - (viewH * BOUNDS_PADDING);
        }
        //TOP
        if ((vals[5]) > viewH - (viewH * BOUNDS_PADDING)) {
            panY += ((viewH) - (vals[5])) - (viewH * BOUNDS_PADDING);
        }

    }

    /**
     * @param imgW
     * @param imgH
     * @param viewW
     * @param viewH
     */
    public void postTranslateMatrix(float imgW, float imgH, float viewW, float viewH) {

        float widthRatio = 75f / imgW;
        //matrix.setScale(widthRatio, (imgW / imgH) * widthRatio);
        float[] values = new float[9];
        matrix.getValues(values);
        float scale = values[Matrix.MSCALE_X] * (viewW * .05f) / imgW;
        scale = Math.max(scale, 75 / viewW);
        matrix.setScale(scale, scale);
        matrix.postTranslate(panX - (imgW * scale * .5f), panY - (imgH * scale * .5f));

    }

    /**
     * @return
     */
    public Matrix getMatrix() {
        return matrix;
    }

    /**
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

    public Point2D.Float getSelectedPoint() {
        return selectedPoint;
    }

    public float getZoomLevel() {
        return zoomLevel;
    }

    public float[] getMatrixValues() {
        float[] values = new float[9];
        matrix.getValues(values);
        values[Matrix.MSCALE_X] *= imgW;
        values[Matrix.MSCALE_Y] *= imgH;

        return values;
    }

    public void setImageSize(int w, int h) {
        this.imgW = w;
        this.imgH = h;
    }

}
