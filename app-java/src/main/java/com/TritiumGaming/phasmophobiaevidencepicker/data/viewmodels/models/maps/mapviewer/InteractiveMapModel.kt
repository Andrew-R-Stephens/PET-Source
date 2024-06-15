package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.mapviewer

import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Log
import com.TritiumGaming.phasmophobiaevidencepicker.utils.geometry.Point2D.Point2DFloat
import kotlin.math.max
import kotlin.math.min

/**
 * InteractiveViewControllerData class
 *
 * @author TritiumGamingStudios
 */
class InteractiveMapModel {
    private val bitmapFactoryOptions: BitmapFactory.Options = BitmapFactory.Options()

    val matrix: Matrix = Matrix()
    val matrixValues: FloatArray
        get() {
            val values = FloatArray(9)
            matrix.getValues(values)
            values[Matrix.MSCALE_X] *= imgW.toFloat()
            values[Matrix.MSCALE_Y] *= imgH.toFloat()

            return values
        }

    var selectedPoint: Point2DFloat? = null
        private set

    private var canSetDefaultZoomLevel = true
    private var zoomLevel: Float = 1f
    private var panX = 1f
    private var panY = 1f

    private var imgW = 0
    private var imgH = 0
    private var w = 0
    private var h = 0

    private var zoomMin = .8f
    private var zoomMax = 4f


    fun deepCopy(otherData: InteractiveMapModel) {
        this.canSetDefaultZoomLevel = otherData.canSetDefaultZoomLevel
        this.zoomLevel = otherData.zoomLevel
        this.panX = otherData.panX
        this.panY = otherData.panY

        val matrixVals = FloatArray(9)
        otherData.matrix.getValues(matrixVals)
        matrix.setValues(matrixVals)

        bitmapFactoryOptions.inJustDecodeBounds = otherData.bitmapFactoryOptions.inJustDecodeBounds
        bitmapFactoryOptions.outWidth = otherData.bitmapFactoryOptions.outWidth
        bitmapFactoryOptions.outHeight = otherData.bitmapFactoryOptions.outHeight
        bitmapFactoryOptions.inSampleSize = otherData.bitmapFactoryOptions.inSampleSize

        this.imgW = otherData.imgW
        this.imgH = otherData.imgH
        this.w = otherData.w
        this.h = otherData.h

        if (otherData.selectedPoint != null) {
            val selPoint = otherData.selectedPoint
            this.selectedPoint = Point2DFloat(selPoint!!.x.toFloat(), selPoint.y.toFloat())
        }

        this.zoomMin = otherData.zoomMin
        this.zoomMax = otherData.zoomMax
    }

    fun updateZoomLevel(zoom: Double) {
        val zoomSense = 2f //1.5f;

        zoomMin = w.toFloat() / imgW.toFloat()
        Log.d("Zoom", "$w $h $zoomMin")

        if (((zoom * zoomSense).toFloat()
                .let { this.zoomLevel += it; this.zoomLevel }) < zoomMin
        ) {
            this.zoomLevel = zoomMin
        } else if (zoomLevel > zoomMax) {
            this.zoomLevel = zoomMax
        }

        updateMatrix()
    }

    fun incrementPan(addX: Double, addY: Double) {
        val panSense = 1.5f

        this.panX += (-addX * panSense).toFloat()
        this.panY += (-addY * panSense).toFloat()

        updateMatrix()
    }

    fun setPan(x: Float, y: Float) {
        this.panX = x
        this.panY = y

        updateMatrix()
    }

    fun setDisplaySize(w: Int, h: Int) {
        this.w = w
        this.h = h
    }

    fun setPressedPoint(point: Point2DFloat?) {
        selectedPoint = point
    }

    fun updateMatrix() {
        setAutoInSampleSize(w, h)
        bitmapFactoryOptions.inJustDecodeBounds = false
    }

    fun postCenterTranslateMatrix(imgW: Float, imgH: Float, viewportW: Float, viewportH: Float) {
        if (canSetDefaultZoomLevel) {
            val zoomW = viewportW / imgW
            val zoomH = viewportH / imgH
            zoomLevel = min(zoomW.toDouble(), zoomH.toDouble()).toFloat()

            canSetDefaultZoomLevel = false
        }

        matrix.setScale(zoomLevel, zoomLevel)
        matrix.postTranslate(
            (viewportW / 2f) - (imgW / 2f * zoomLevel) + (panX * zoomLevel),
            (viewportH / 2f) - (imgH / 2f * zoomLevel) + (panY * zoomLevel)
        )

        val vals = FloatArray(9)
        matrix.getValues(vals)

        var distance: Float

        //RIGHT
        val boundsPadding = .2f
        if ((((vals[2]) + (imgW * zoomLevel)).also {
                distance = it
            }) < viewportW * boundsPadding) {
            panX -= distance - (viewportW * boundsPadding)
        }
        //LEFT
        if ((vals[2]) > viewportW - (viewportW * boundsPadding)) {
            panX += ((viewportW) - (vals[2])) - (viewportW * boundsPadding)
        }
        //BOTTOM
        if ((((vals[5]) + (imgH * zoomLevel)).also {
                distance = it
            }) < viewportH * boundsPadding) {
            panY -= distance - (viewportH * boundsPadding)
        }
        //TOP
        if ((vals[5]) > viewportH - (viewportH * boundsPadding)) {
            panY += ((viewportH) - (vals[5])) - (viewportH * boundsPadding)
        }
    }

    fun postTranslateOriginMatrix(imgW: Float, imgH: Float, viewportW: Float, viewportH: Float) {
        val values = FloatArray(9)
        matrix.getValues(values)

        var scale = 1f
        if (viewportW < viewportH) {
            scale = values[Matrix.MSCALE_X] * (viewportH * .1f) / viewportW
            scale = max(scale.toDouble(), (75 / viewportH).toDouble()).toFloat()
        }
        if (viewportH < viewportW) {
            scale = values[Matrix.MSCALE_Y] * (viewportH * .1f) / viewportW
            scale = max(scale.toDouble(), (75 / viewportH).toDouble()).toFloat()
        }
        matrix.setScale(scale, scale)
        matrix.postTranslate(panX - (imgW * scale * .5f), panY - (imgH * scale * .5f))
    }

    fun setAutoInSampleSize(reqWidth: Int, reqHeight: Int) {
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

    fun setImageSize(w: Int, h: Int) {
        this.imgW = w
        this.imgH = h
    }

}
