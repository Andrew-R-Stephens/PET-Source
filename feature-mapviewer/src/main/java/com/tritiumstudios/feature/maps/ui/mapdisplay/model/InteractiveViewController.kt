package com.tritiumstudios.feature.maps.ui.mapdisplay.model

import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Size
import com.tritiumgaming.core.common.graphics.geometry.Point2D
import kotlin.math.max
import kotlin.math.min

/**
 * InteractiveViewControllerData class
 *
 * @author TritiumGamingStudios
 */
class InteractiveViewController {

    private val bitmapFactoryOptions: BitmapFactory.Options = BitmapFactory.Options()

    val matrix: Matrix = Matrix()
    val matrixValues: FloatArray
        get() {
            val values = FloatArray(9)
            matrix.getValues(values)
            values[Matrix.MSCALE_X] *= imageDim.width.toFloat()
            values[Matrix.MSCALE_Y] *= imageDim.height.toFloat()

            return values
        }

    private var imageDim = Size(0, 0)
    private var containerDim = Size(0, 0)

    private var pan = Point2D.Point2DFloat(x = 1f, y = 1f)

    private var canSetDefaultZoomLevel = true
    private var zoom: Float = 1f
    private var zoomMin = 1f
    private var zoomMax = 1f

    fun deepCopy(otherData: InteractiveViewController) {
        this.canSetDefaultZoomLevel = otherData.canSetDefaultZoomLevel
        this.zoom = otherData.zoom

        pan.setLocation(x = otherData.pan.x, y = otherData.pan.y)

        val matrixVals = FloatArray(9)
        otherData.matrix.getValues(matrixVals)
        matrix.setValues(matrixVals)

        bitmapFactoryOptions.inJustDecodeBounds = otherData.bitmapFactoryOptions.inJustDecodeBounds
        bitmapFactoryOptions.outWidth = otherData.bitmapFactoryOptions.outWidth
        bitmapFactoryOptions.outHeight = otherData.bitmapFactoryOptions.outHeight
        bitmapFactoryOptions.inSampleSize = otherData.bitmapFactoryOptions.inSampleSize

        setImageSize(otherData.imageDim.width, otherData.imageDim.height)
        setContainerSize(otherData.containerDim.width, otherData.containerDim.height)
        setZoomConstraints(otherData.zoomMin, otherData.zoomMax)
    }

    fun doZoom(zoom: Float) {
        val zoomSense = 1.5f
        val zoomDiff = (zoom - 1)
        val zoomNormal = zoomDiff * 2 * zoomSense

        this.zoom = (this.zoom + zoomNormal)
            .coerceIn(minimumValue = zoomMin, maximumValue = zoomMax)

        updateMatrix()
    }

    fun doPan(addX: Float, addY: Float) {
        val panSense = 1f

        pan.apply {
            setLocation(
                pan.x + (addX * panSense),
                pan.y + (addY * panSense)
            )
        }

        updateMatrix()
    }

    fun setPan(x: Float, y: Float) {
        pan.setLocation(x, y)

        updateMatrix()
    }

    fun updateMatrix() {
        setAutoInSampleSize(containerDim.width, containerDim.height)
        bitmapFactoryOptions.inJustDecodeBounds = false
    }

    fun postCenterTranslateMatrix(imgW: Float, imgH: Float, viewportW: Float, viewportH: Float) {
        if (canSetDefaultZoomLevel) {
            val zoomW = viewportW / imgW
            val zoomH = viewportH / imgH
            zoom = min(zoomW.toDouble(), zoomH.toDouble()).toFloat()

            canSetDefaultZoomLevel = false
        }

        matrix.setScale(zoom, zoom)
        matrix.postTranslate(
            (viewportW / 2f) - (imgW / 2f * zoom) + (pan.x.toFloat() * zoom),
            (viewportH / 2f) - (imgH / 2f * zoom) + (pan.y.toFloat() * zoom)
        )

        val vals = FloatArray(9)
        matrix.getValues(vals)

        var distance: Float

        //RIGHT
        val boundsPadding = .2f
        if ((((vals[2]) + (imgW * zoom)).also {
                distance = it
            }) < viewportW * boundsPadding) {
            pan.apply {
                x -= distance - (viewportW * boundsPadding)
            }
        }
        //LEFT
        if ((vals[2]) > viewportW - (viewportW * boundsPadding)) {
            pan.apply {
                x += ((viewportW) - (vals[2])) - (viewportW * boundsPadding)
            }
        }
        //BOTTOM
        if ((((vals[5]) + (imgH * zoom)).also {
                distance = it
            }) < viewportH * boundsPadding) {
            pan.apply {
                y -= distance - (viewportH * boundsPadding)
            }
        }
        //TOP
        if ((vals[5]) > viewportH - (viewportH * boundsPadding)) {
            pan.apply {
                y += ((viewportH) - (vals[5])) - (viewportH * boundsPadding)
            }
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
        matrix.postTranslate(
            pan.x.toFloat() - (imgW * scale * .5f),
            pan.y.toFloat() - (imgH * scale * .5f))
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

    fun setContainerSize(w: Int, h: Int) {
        containerDim = Size(w, h)

        updateZoomConstraints()
    }

    fun setImageSize(w: Int, h: Int) {
        imageDim = Size(w, h)

        updateZoomConstraints()
    }

    fun setZoomConstraints(min: Float, max: Float) {
        this.zoomMin = min
        this.zoomMax = max
    }

    private fun updateZoomConstraints() {

        val wConstr1 = containerDim.width.toFloat() / imageDim.width.toFloat()
        val wConstr2 = imageDim.width.toFloat() / containerDim.width.toFloat()

        val hConstr1 = containerDim.height.toFloat() / imageDim.height.toFloat()
        val hConstr2 = imageDim.height.toFloat() / containerDim.height.toFloat()

        zoomMin = min(
            min(wConstr1, wConstr2),
            min(hConstr1, hConstr2))
        zoomMax =
            max(
            max(wConstr1, hConstr1),
            max(hConstr2, wConstr2)).coerceAtLeast(zoomMin * 4f)

    }
}