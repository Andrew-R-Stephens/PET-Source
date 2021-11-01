package com.TritiumGaming.phasmophobiaevidencepicker.data.data;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

import androidx.annotation.DrawableRes;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

/**
 * BitmapUtils class
 *
 * @author TritiumGamingStudios
 */
public class BitmapUtils {

    private int currentLayer = -1;

    private int maxTextureSize;
    public ArrayList<Integer> resources = new ArrayList<>();

    /**
     *
     */
    public BitmapUtils(){
        setMaxTextureSize();
    }

    /**
     *
     * @param resource
     * @return
     */
    public BitmapUtils setResource(@DrawableRes int resource){
        clearResources();
        addResource(resource);

        return this;
    }

    /**
     *
     * @param resources
     */
    public void setResources(ArrayList<Integer> resources){
        this.resources = resources;
    }

    /**
     *
     * @param resource
     */
    public void addResource(@DrawableRes int resource){
        this.resources.add(resource);
    }

    /**
     *
     */
    public void clearResources(){
        resources = new ArrayList<>();
        currentLayer = -1;
    }

    /**
     *
     */
    private void setMaxTextureSize(){
        // Safe minimum default size
        final int IMAGE_MAX_BITMAP_DIMENSION = 2048;

        // Get EGL Display
        EGL10 egl = (EGL10) EGLContext.getEGL();
        EGLDisplay display = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);

        // Initialise
        int[] version = new int[2];
        egl.eglInitialize(display, version);

        // Query total number of configurations
        int[] totalConfigurations = new int[1];
        egl.eglGetConfigs(display, null, 0, totalConfigurations);

        // Query actual list configurations
        EGLConfig[] configurationsList = new EGLConfig[totalConfigurations[0]];
        egl.eglGetConfigs(display, configurationsList, totalConfigurations[0], totalConfigurations);

        int[] textureSize = new int[1];
        int maximumTextureSize = 0;

        // Iterate through all the configurations to located the maximum texture size
        for (int i = 0; i < totalConfigurations[0]; i++) {
            // Only need to check for width since opengl textures are always squared
            egl.eglGetConfigAttrib(display, configurationsList[i], EGL10.EGL_MAX_PBUFFER_WIDTH, textureSize);

            // Keep track of the maximum texture size
            if (maximumTextureSize < textureSize[0])
                maximumTextureSize = textureSize[0];
        }
        // Release
        egl.eglTerminate(display);

        maxTextureSize = Math.max(maximumTextureSize, IMAGE_MAX_BITMAP_DIMENSION);
    }

    /**
     *
     * @param c
     * @return
     */
    public Bitmap compileBitmaps(Context c){
        Bitmap b = null;
        for(@DrawableRes int r: resources)
            b = createBitmap(c, b, r);
        return b;
    }

    /**
     *
     * @param c
     * @param previousBitmap
     * @return
     */
    public Bitmap compileNextBitmap(Context c, Bitmap previousBitmap) {
        currentLayer++;
        return createBitmap(c, previousBitmap, resources.get(currentLayer));
    }

    /**
     *
     * @return
     */
    public boolean hasNextBitmap(){
        return currentLayer < resources.size()-1;
    }

    /**
     *
     * @param c
     * @param baseLayer
     * @param id
     * @return
     */
    private Bitmap createBitmap(Context c, Bitmap baseLayer, int id){
        final int screenW = Resources.getSystem().getDisplayMetrics().widthPixels,
                screenH = Resources.getSystem().getDisplayMetrics().heightPixels;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(c.getResources(), id, options);
        //Log.d(getClass().getName(), "Image Before: InSampleSize- " + options.inSampleSize + ", OW- " + options.outWidth + ", OH- " + options.outHeight + ", SW- " + screenW + ", SH- " + screenH);

        // Raw height and width of image with respect to starting sampleSize
        final int height = options.outHeight, width = options.outWidth;
        int highestDim = Math.max(height, width); //Get biggest image dimension between both width and height
        double dimScale = (double)maxTextureSize/(double)highestDim*options.inDensity;
        //Log.d(getClass().getName(), "Scaling: DimScale: " + dimScale + " MaxDim: " + maxTextureSize + " highestDim: " + highestDim);
        if(dimScale < 1)
            options.inSampleSize += (int)Math.ceil(Math.abs(dimScale));
        //Log.d(getClass().getName(), "Image After: InSampleSize- " + options.inSampleSize + ", OW- " + options.outWidth + ", OH- " + options.outHeight + ", SW- " + screenW + ", SH- " + screenH);

        options.inJustDecodeBounds = false;

        return addLayer(baseLayer, BitmapFactory.decodeResource(c.getResources(), id, options));
    }

    /**
     *
     * @param baseLayer
     * @param topLayer
     * @return
     * @throws OutOfMemoryError
     */
    private Bitmap addLayer(Bitmap baseLayer, Bitmap topLayer) throws OutOfMemoryError {
        if(baseLayer == null && BitmapUtils.bitmapExists(topLayer))
            baseLayer = Bitmap.createBitmap(topLayer.getWidth(), topLayer.getHeight(), topLayer.getConfig());
        if(baseLayer != null && !baseLayer.isRecycled()) {
            Canvas canvas = new Canvas(baseLayer);
            if (topLayer != null) {
                canvas.drawBitmap(topLayer, new Matrix(), null);
                topLayer.recycle();
            }
        }
        System.gc();

        return baseLayer;
    }

    /**
     *
     * @param b
     * @return
     */
    public static boolean bitmapExists(Bitmap b){
        return b != null && !b.isRecycled();
    }

    public static void destroyBitmap(Bitmap b) {
        if(bitmapExists(b)) {
            b.recycle();
            b = null;
        }
    }

}
