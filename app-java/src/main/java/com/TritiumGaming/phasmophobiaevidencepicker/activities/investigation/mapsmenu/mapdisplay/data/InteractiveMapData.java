package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.data;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import androidx.annotation.NonNull;

import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.geometry.Point2D;

/**
 * InteractiveViewControllerData class
 *
 * @author TritiumGamingStudios
 */
public class InteractiveMapData {

    private boolean canSetDefaultZoomLevel = true;

    private float zoomLevel = 1f;
    private float panX = 1f, panY = 1f;

    private final Matrix matrix = new Matrix();
    private final BitmapFactory.Options options = new BitmapFactory.Options();

    private int imgW, imgH;
    private int w, h;

    private Point2D.Point2DFloat selectedPoint;

    private float ZOOM_MIN = .8f, ZOOM_MAX = 4f;

    public InteractiveMapData() {}

    public void deepCopy(@NonNull InteractiveMapData otherData) {
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
            Point2D.Point2DFloat selPoint = otherData.getSelectedPoint();
            this.selectedPoint = new Point2D.Point2DFloat((float)selPoint.getX(), (float)selPoint.getY());
        }

        this.ZOOM_MIN = otherData.ZOOM_MIN;
        this.ZOOM_MAX = otherData.ZOOM_MAX;
    }

    public void updateZoomLevel(double zoom) {

        float ZOOM_SENSE = 2f;//1.5f;

        ZOOM_MIN = (float)w / (float)imgW;
        Log.d("Zoom", w + " " + h + " "  + ZOOM_MIN);

        if ((this.zoomLevel += (float) (zoom * ZOOM_SENSE)) < ZOOM_MIN) {
            this.zoomLevel = ZOOM_MIN;
        }
        else if (zoomLevel > ZOOM_MAX) {
            this.zoomLevel = ZOOM_MAX;
        }

        updateMatrix();

    }

    public void incrementPan(double addX, double addY) {

        float PAN_SENSE = 1.5f;

        this.panX += (float) (-addX * PAN_SENSE);
        this.panY += (float) (-addY * PAN_SENSE);

        updateMatrix();

    }

    public void setPan(float x, float y) {

        this.panX = x;
        this.panY = y;

        updateMatrix();

    }

    public void setDisplaySize(int w, int h) {
        this.w = w;
        this.h = h;
    }

    public void setPressedPoint(Point2D.Point2DFloat point) {
        selectedPoint = point;
    }

    public void updateMatrix() {
        setAutoInSampleSize(w, h);
        options.inJustDecodeBounds = false;
    }

    @NonNull
    public BitmapFactory.Options getBitmapFactoryOptions() {
        return options;
    }

    public void postCenterTranslateMatrix(float imgW, float imgH, float viewportW, float viewportH) {

        if (canSetDefaultZoomLevel) {
            float zoomW = viewportW / imgW;
            float zoomH = viewportH / imgH;
            zoomLevel = Math.min(zoomW, zoomH);

            canSetDefaultZoomLevel = false;
        }

        matrix.setScale(zoomLevel, zoomLevel);
        matrix.postTranslate(
                (viewportW / 2f) - (imgW / 2f * zoomLevel) + (panX * zoomLevel),
                (viewportH / 2f) - (imgH / 2f * zoomLevel) + (panY * zoomLevel));

        float[] vals = new float[9];
        matrix.getValues(vals);

        float distance;

        //RIGHT
        float BOUNDS_PADDING = .2f;
        if ((distance = ((vals[2]) + (imgW * zoomLevel))) < viewportW * BOUNDS_PADDING) {
            panX -= distance - (viewportW * BOUNDS_PADDING);
        }
        //LEFT
        if ((vals[2]) > viewportW - (viewportW * BOUNDS_PADDING)) {
            panX += ((viewportW) - (vals[2])) - (viewportW * BOUNDS_PADDING);
        }
        //BOTTOM
        if ((distance = ((vals[5]) + (imgH * zoomLevel))) < viewportH * BOUNDS_PADDING) {
            panY -= distance - (viewportH * BOUNDS_PADDING);
        }
        //TOP
        if ((vals[5]) > viewportH - (viewportH * BOUNDS_PADDING)) {
            panY += ((viewportH) - (vals[5])) - (viewportH * BOUNDS_PADDING);
        }

    }

    public void postTranslateOriginMatrix(float imgW, float imgH, float viewportW, float viewportH) {

        float[] values = new float[9];
        matrix.getValues(values);

        float scale = 1;
        if(viewportW < viewportH) {
            scale = values[Matrix.MSCALE_X] * (viewportH * .1f) / viewportW;
            scale = Math.max(scale, 75 / viewportH);
        }
        if(viewportH < viewportW) {
            scale = values[Matrix.MSCALE_Y] * (viewportH * .1f) / viewportW;
            scale = Math.max(scale, 75 / viewportH);
        }
        matrix.setScale(scale, scale);
        matrix.postTranslate(panX - (imgW * scale * .5f), panY - (imgH * scale * .5f));
    }

    @NonNull
    public Matrix getMatrix() {
        return matrix;
    }

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

    public Point2D.Point2DFloat getSelectedPoint() {
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

    public float getImgW() {
        return imgW;
    }

    public float getImgH() {
        return imgH;
    }

}
