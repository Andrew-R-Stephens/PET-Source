package com.TritiumGaming.phasmophobiaevidencepicker.data.utilities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.egl.EGLContext
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.max

/**
 * BitmapUtils class
 *
 * @author TritiumGamingStudios
 */
class BitmapUtils {
    var resources: ArrayList<Int> = ArrayList()
    private var currentLayer = -1

    private var filters: ArrayList<PorterDuff.Mode?> = ArrayList()

    private var maxTextureSize = 0

    init {
        setMaxTextureSize()
    }

    fun setResource(@DrawableRes resource: Int): BitmapUtils {
        clearResources()
        addResource(resource)

        return this
    }

    private fun addResource(@DrawableRes resource: Int): BitmapUtils {
        resources.add(resource)
        filters.add(null)

        return this
    }

    fun addResource(@DrawableRes resource: Int, filterMode: PorterDuff.Mode?): BitmapUtils {
        resources.add(resource)
        filters.add(filterMode)

        return this
    }

    fun clearResources() {
        resources = ArrayList()
        filters = ArrayList()
        currentLayer = -1
    }

    private fun setMaxTextureSize() {
        // Safe minimum default size
        val IMAGE_MAX_BITMAP_DIMENSION = 2048

        // Get EGL Display
        val egl = EGLContext.getEGL() as EGL10
        val display = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY)

        // Initialise
        val version = IntArray(2)
        egl.eglInitialize(display, version)

        // Query total number of configurations
        val totalConfigurations = IntArray(1)
        egl.eglGetConfigs(display, null, 0, totalConfigurations)

        // Query actual list configurations
        val configurationsList = arrayOfNulls<EGLConfig>(
            totalConfigurations[0]
        )
        egl.eglGetConfigs(
            display,
            configurationsList,
            totalConfigurations[0],
            totalConfigurations
        )

        val textureSize = IntArray(1)
        var maximumTextureSize = 0

        // Iterate through all the configurations to located the maximum texture size
        for (i in 0 until totalConfigurations[0]) {
            // Only need to check for width since opengl textures are always squared
            egl.eglGetConfigAttrib(
                display,
                configurationsList[i],
                EGL10.EGL_MAX_PBUFFER_WIDTH,
                textureSize
            )

            // Keep track of the maximum texture size
            if (maximumTextureSize < textureSize[0]) {
                maximumTextureSize = textureSize[0]
            }
        }
        // Release
        egl.eglTerminate(display)

        maxTextureSize = max(maximumTextureSize.toDouble(), IMAGE_MAX_BITMAP_DIMENSION.toDouble())
            .toInt()
    }

    fun compileBitmaps(context: Context): Bitmap? {

        var bitmap: Bitmap? = null

        for (i in resources.indices) {
            val drawableRes = resources[i]
            val filter = filters[i]

            bitmap = createBitmap(context, bitmap, drawableRes, filter)
        }
        return bitmap
    }

    /**
     * @param previousBitmap Can be null. If bitmap is null, an empty layer will propagate
     * in its place. If not null, the bitmap will be the layer.
     */
    fun compileNextBitmap(context: Context, previousBitmap: Bitmap?): Bitmap? {
        currentLayer++
        return createBitmap(context, previousBitmap, resources[currentLayer], null)
    }

    fun hasNextBitmap(): Boolean {
        return currentLayer < resources.size - 1
    }

    private fun createBitmap(
        c: Context,
        baseLayer: Bitmap?,
        drawableResId: Int,
        mode: PorterDuff.Mode?
    ): Bitmap? {
        val options = BitmapFactory.Options()
        options.inSampleSize = 1
        options.inJustDecodeBounds = true

        // Raw height and width of image with respect to starting sampleSize
        val height = options.outHeight
        val width = options.outWidth
        //Get biggest image dimension between both width and height
        val highestDim = max(height.toDouble(), width.toDouble()).toInt()
        val dimScale = maxTextureSize.toDouble() / highestDim.toDouble() * options.inDensity
        if (dimScale < 1) {
            options.inSampleSize += ceil(abs(dimScale)).toInt()
        }
        options.inJustDecodeBounds = false

        var bitmapToAdd = BitmapFactory.decodeResource(c.resources, drawableResId, options)
        if (bitmapToAdd == null) {
            //Temp Options
            val optionsTemp = BitmapFactory.Options()
            optionsTemp.inSampleSize = 1
            optionsTemp.inJustDecodeBounds = true
            if (baseLayer != null) {
                optionsTemp.outWidth = baseLayer.width
                optionsTemp.outHeight = baseLayer.height
            }
            optionsTemp.inSampleSize = options.inSampleSize
            optionsTemp.inJustDecodeBounds = false

            bitmapToAdd = getBitmapFromVector(c, drawableResId, optionsTemp)
        }

        return addLayer(baseLayer, bitmapToAdd!!, mode)
    }

    @Throws(OutOfMemoryError::class)
    private fun addLayer(baseLayer: Bitmap?, topLayer: Bitmap, mode: PorterDuff.Mode?): Bitmap? {
        var baseLayer = baseLayer
        if (baseLayer == null && bitmapExists(topLayer)) {
            baseLayer = Bitmap.createBitmap(
                topLayer.width,
                topLayer.height,
                topLayer.config
            )
        }
        if (baseLayer != null && !baseLayer.isRecycled) {
            val canvas = Canvas(baseLayer)
            if (bitmapExists(topLayer)) {
                var paint: Paint? = null
                if (mode != null) {
                    paint = Paint()
                    paint.setXfermode(PorterDuffXfermode(mode))
                }
                canvas.drawBitmap(topLayer, Matrix(), paint)
                topLayer.recycle()
            }
        }
        System.gc()

        return baseLayer
    }

    companion object {
        @JvmStatic
        fun bitmapExists(b: Bitmap?): Boolean {
            return b != null && !(b.isRecycled)
        }

        fun destroyBitmap(b: Bitmap?) {
            if (bitmapExists(b)) {
                b?.recycle()
            }
        }

        fun getBitmapFromVector(
            context: Context, drawableId: Int, options: BitmapFactory.Options
        ): Bitmap? {
            val drawable = ContextCompat.getDrawable(context, drawableId) ?: return null

            var width =
                if (drawable.intrinsicWidth == 0) options.outWidth else drawable.intrinsicWidth
            width = max(width.toDouble(), options.outWidth.toDouble()).toInt()

            var height =
                if (drawable.intrinsicHeight == 0) options.outHeight else drawable.intrinsicHeight
            height = max(height.toDouble(), options.outHeight.toDouble()).toInt()

            Log.d(
                "BitmapDims",
                width.toString() + " " + height + "->" +
                        options.outWidth + " " + options.outHeight + "->" +
                        drawable.intrinsicWidth + " " + drawable.intrinsicHeight
            )

            val bitmap = Bitmap.createBitmap(
                width,
                height,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(
                0, 0,
                width,  //use dimensions of Drawable
                height
            )

            drawable.draw(canvas)
            return bitmap
        }
    }
}
