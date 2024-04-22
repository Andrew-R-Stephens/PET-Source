package com.tritiumstudios.phasmophobiaevidencetool.android.data.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

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
    @NonNull
    public ArrayList<PorterDuff.Mode> filters = new ArrayList<>();

    /**
     *
     */
    public BitmapUtils() {
        setMaxTextureSize();
    }

    /**
     * @param resource
     * @return
     */
    @NonNull
    public BitmapUtils setResource(@DrawableRes int resource) {
        clearResources();
        addResource(resource);

        return this;
    }

    /**
     * @param resources
     */
    public void setResources(ArrayList<Integer> resources) {
        this.resources = resources;
    }

    /**
     * @param resource
     */
    @NonNull
    public BitmapUtils addResource(@DrawableRes int resource) {
        this.resources.add(resource);
        this.filters.add(null);

        return this;
    }

    /**
     * @param resource
     */
    @NonNull
    public BitmapUtils addResource(@DrawableRes int resource, PorterDuff.Mode filterMode) {
        this.resources.add(resource);
        this.filters.add(filterMode);

        return this;
    }

    /**
     *
     */
    public void clearResources() {
        resources = new ArrayList<>();
        filters = new ArrayList<>();
        currentLayer = -1;
    }

    /**
     *
     */
    private void setMaxTextureSize() {
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
        egl.eglGetConfigs(
                display,
                configurationsList,
                totalConfigurations[0],
                totalConfigurations);

        int[] textureSize = new int[1];
        int maximumTextureSize = 0;

        // Iterate through all the configurations to located the maximum texture size
        for (int i = 0; i < totalConfigurations[0]; i++) {
            // Only need to check for width since opengl textures are always squared
            egl.eglGetConfigAttrib(
                    display,
                    configurationsList[i],
                    EGL10.EGL_MAX_PBUFFER_WIDTH,
                    textureSize);

            // Keep track of the maximum texture size
            if (maximumTextureSize < textureSize[0]) {
                maximumTextureSize = textureSize[0];
            }
        }
        // Release
        egl.eglTerminate(display);

        maxTextureSize = Math.max(maximumTextureSize, IMAGE_MAX_BITMAP_DIMENSION);
    }

    /**
     * @param context
     * @return
     */
    @Nullable
    public Bitmap compileBitmaps(@NonNull Context context) {

        if(resources == null || filters == null) {
             return null;
        }

        Bitmap bitmap = null;
        //@DrawableRes int resource : resources

        for (int i = 0; i < resources.size(); i++) {
            //bitmap = createBitmap(context, bitmap, resource);
            int drawableRes = resources.get(i);
            PorterDuff.Mode filter = filters.get(i);

            bitmap = createBitmap(context, bitmap, drawableRes, filter);
        }
        return bitmap;

    }

    /**
     * @param context
     * @param previousBitmap
     * @return
     */
    public Bitmap compileNextBitmap(@NonNull Context context, @NonNull Bitmap previousBitmap) {
        currentLayer++;
        return createBitmap(context, previousBitmap, resources.get(currentLayer), null);
    }

    /**
     * @return
     */
    public boolean hasNextBitmap() {
        return currentLayer < resources.size() - 1;
    }

    /**
     * @param c
     * @param baseLayer
     * @param id
     * @return
     */
    /*
    private Bitmap createBitmap(Context c, Bitmap baseLayer, int id) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(c.getResources(), id, options);

        // Raw height and width of image with respect to starting sampleSize
        final int height = options.outHeight, width = options.outWidth;
        //Get biggest image dimension between both width and height
        int highestDim = Math.max(height, width);
        double dimScale = (double) maxTextureSize / (double) highestDim * options.inDensity;
        if (dimScale < 1) {
            options.inSampleSize += (int) Math.ceil(Math.abs(dimScale));
        }
        options.inJustDecodeBounds = false;

        Bitmap bitmapToAdd = BitmapFactory.decodeResource(c.getResources(), id, options);
        if(bitmapToAdd == null) {
            bitmapToAdd = getBitmapFromVector(c, id, options);
        }

        return addLayer(baseLayer, bitmapToAdd);
    }
    */

    /**
     * @param c
     * @param baseLayer
     * @param id
     * @return
     */
    private Bitmap createBitmap(@NonNull Context c, @NonNull Bitmap baseLayer, int id, PorterDuff.Mode mode) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(c.getResources(), id, options);

        // Raw height and width of image with respect to starting sampleSize
        final int height = options.outHeight, width = options.outWidth;
        //Get biggest image dimension between both width and height
        int highestDim = Math.max(height, width);
        double dimScale = (double) maxTextureSize / (double) highestDim * options.inDensity;
        if (dimScale < 1) {
            options.inSampleSize += (int) Math.ceil(Math.abs(dimScale));
        }
        options.inJustDecodeBounds = false;

        Bitmap bitmapToAdd = BitmapFactory.decodeResource(c.getResources(), id, options);
        if(bitmapToAdd == null) {
            //Temp Options
            BitmapFactory.Options optionsTemp = new BitmapFactory.Options();
            optionsTemp.inSampleSize = 1;
            optionsTemp.inJustDecodeBounds = true;
            optionsTemp.outWidth = baseLayer.getWidth();
            optionsTemp.outHeight = baseLayer.getHeight();
            optionsTemp.inSampleSize = options.inSampleSize;
            optionsTemp.inJustDecodeBounds = false;

            bitmapToAdd = getBitmapFromVector(c, id, optionsTemp);
        }

        return addLayer(baseLayer, bitmapToAdd, mode);
    }

    /**
     * @param baseLayer
     * @param topLayer
     * @return
     * @throws OutOfMemoryError
     */
    /*
    private Bitmap addLayer(Bitmap baseLayer, Bitmap topLayer) throws OutOfMemoryError {
        if (baseLayer == null && BitmapUtils.bitmapExists(topLayer)) {
            baseLayer = Bitmap.createBitmap(
                    topLayer.getWidth(),
                    topLayer.getHeight(),
                    topLayer.getConfig());
        }
        if (baseLayer != null && !baseLayer.isRecycled()) {
            Canvas canvas = new Canvas(baseLayer);
            if (BitmapUtils.bitmapExists(topLayer)) {
                canvas.drawBitmap(topLayer, new Matrix(), null);
                topLayer.recycle();
            }
        }
        System.gc();

        return baseLayer;
    }
    */

    /**
     * @param baseLayer
     * @param topLayer
     * @return
     * @throws OutOfMemoryError
     */
    @Nullable
    private Bitmap addLayer(@Nullable Bitmap baseLayer, @NonNull Bitmap topLayer, @Nullable PorterDuff.Mode mode) throws OutOfMemoryError {
        if (baseLayer == null && BitmapUtils.bitmapExists(topLayer)) {
            baseLayer = Bitmap.createBitmap(
                    topLayer.getWidth(),
                    topLayer.getHeight(),
                    topLayer.getConfig());
        }
        if (baseLayer != null && !baseLayer.isRecycled()) {
            Canvas canvas = new Canvas(baseLayer);
            if (BitmapUtils.bitmapExists(topLayer)) {
                Paint paint = null;
                if(mode != null) {
                    paint = new Paint();
                    paint.setXfermode(new PorterDuffXfermode(mode));
                }
                canvas.drawBitmap(topLayer, new Matrix(), paint);
                topLayer.recycle();
            }
        }
        System.gc();

        return baseLayer;
    }

    /**
     * @param b
     * @return
     */
    public static boolean bitmapExists(@Nullable Bitmap b) {
        return b != null && !b.isRecycled();
    }

    public static void destroyBitmap(@NonNull Bitmap b) {
        if (bitmapExists(b)) {
            b.recycle();
        }
    }

    @Nullable
    public static Bitmap getBitmapFromVector(
            @NonNull Context context, int drawableId, @NonNull BitmapFactory.Options options) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if(drawable == null) { return null; }

        int width = drawable.getIntrinsicWidth() == 0 ?
                options.outWidth : drawable.getIntrinsicWidth();
        width = Math.max(width, options.outWidth);

        int height = drawable.getIntrinsicHeight() == 0 ?
                options.outHeight : drawable.getIntrinsicHeight();
        height = Math.max(height, options.outHeight);

        Log.d("BitmapDims",
                width + " " + height + "->" +
                        options.outWidth + " " + options.outHeight + "->" +
                        drawable.getIntrinsicWidth() + " " + drawable.getIntrinsicHeight());

        Bitmap bitmap = Bitmap.createBitmap(
                width,
                height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0,
                width,    //use dimensions of Drawable
                height
        );

        drawable.draw(canvas);
        return bitmap;
    }

}
