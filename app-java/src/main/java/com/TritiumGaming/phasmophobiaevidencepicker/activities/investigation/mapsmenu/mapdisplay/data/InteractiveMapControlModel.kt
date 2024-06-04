package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.data

import android.graphics.BitmapFactory
import android.graphics.Matrix
import kotlin.math.min

/**
 * InteractiveViewControllerData class
 *
 * @author TritiumGamingStudios
 */
class InteractiveMapControlModel {
    private val bitmapFactoryOptions: BitmapFactory.Options = BitmapFactory.Options()

    val matrix: Matrix = Matrix()

    private var canSetDefaultZoomLevel = true

    var zoomLevel: Float = 1f
        private set
    private var panX = 1f
    private var panY = 1f

    private var w = 0
    private var h = 0

    var pressedPointX: Float = 0f
        private set
    var pressedPointY: Float = 0f
        private set

    fun incrementZoomLevel(addZoom: Double) {
        val zoomSense = 2f //1.5f;
        val min = .8f
        val max = 4f

        if (((addZoom * zoomSense).toFloat()
                .let { this.zoomLevel += it; this.zoomLevel }) < min
        ) {
            this.zoomLevel = min
        } else if (zoomLevel > max) {
            this.zoomLevel = max
        }

        updateMatrix()
    }

    fun incrementPan(addX: Double, addY: Double) {
        val panSense = 1.5f

        this.panX += (-addX * panSense).toFloat()
        this.panY += (-addY * panSense).toFloat()

        updateMatrix()
    }

    fun setDisplaySize(w: Int, h: Int) {
        this.w = w
        this.h = h
    }

    fun setPressedPoint(x: Float, y: Float) {
        this.pressedPointX = x
        this.pressedPointY = y
    }

    fun updateMatrix() {
        setAutoInSampleSize(w, h)
        bitmapFactoryOptions.inJustDecodeBounds = false
    }

    fun postTranslateMatrix(imgW: Float, imgH: Float, viewW: Float, viewH: Float) {
        if (canSetDefaultZoomLevel) {
            val zoomW = viewW / imgW
            val zoomH = viewH / imgH
            zoomLevel = min(zoomW.toDouble(), zoomH.toDouble()).toFloat()

            canSetDefaultZoomLevel = false
        }

        matrix.setScale(zoomLevel, zoomLevel)
        matrix.postTranslate(
            (viewW / 2f) - (imgW / 2f * zoomLevel) + (panX * zoomLevel),
            (viewH / 2f) - (imgH / 2f * zoomLevel) + (panY * zoomLevel)
        )

        val matrixVals = FloatArray(9)
        matrix.getValues(matrixVals)

        var distance: Float

        //RIGHT
        val BOUNDS_PADDING = .2f
        if ((((matrixVals[2]) + (imgW * zoomLevel)).also { distance = it }) < viewW * BOUNDS_PADDING) {
            panX -= distance - (viewW * BOUNDS_PADDING)
        }
        //LEFT
        if ((matrixVals[2]) > viewW - (viewW * BOUNDS_PADDING)) {
            panX += ((viewW) - (matrixVals[2])) - (viewW * BOUNDS_PADDING)
        }
        //BOTTOM
        if ((((matrixVals[5]) + (imgH * zoomLevel)).also { distance = it }) < viewH * BOUNDS_PADDING) {
            panY -= distance - (viewH * BOUNDS_PADDING)
        }
        //TOP
        if ((matrixVals[5]) > viewH - (viewH * BOUNDS_PADDING)) {
            panY += ((viewH) - (matrixVals[5])) - (viewH * BOUNDS_PADDING)
        }
    }

    private fun setAutoInSampleSize(reqWidth: Int, reqHeight: Int) {
        // Raw height and width of image
        val height = bitmapFactoryOptions.outHeight
        val width = bitmapFactoryOptions.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (inSampleSize != 0 && ((halfHeight / inSampleSize) >= reqHeight
                        && (halfWidth / inSampleSize) >= reqWidth)
            ) {
                inSampleSize *= 2
            }
        }
        bitmapFactoryOptions.inSampleSize = inSampleSize
    }
}
