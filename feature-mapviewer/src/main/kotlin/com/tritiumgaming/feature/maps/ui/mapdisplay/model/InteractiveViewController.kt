package com.tritiumgaming.feature.maps.ui.mapdisplay.model

import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Size
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    var matrix: Matrix by mutableStateOf(Matrix())
        private set

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

    private var viewportW = 0f
    private var viewportH = 0f

    private var pan by mutableStateOf(Point2D.Point2DFloat(x = 0f, y = 0f))

    private var canSetDefaultZoomLevel = true
    var zoom: Float by mutableStateOf(1f)
        private set
    var zoomMin by mutableStateOf(1f)
        private set
    var zoomMax by mutableStateOf(1f)
        private set

    val zoomProgress: Float
        get() = if (zoomMax > zoomMin) (zoom - zoomMin) / (zoomMax - zoomMin) else 0f

    fun deepCopy(otherData: InteractiveViewController) {
        this.canSetDefaultZoomLevel = otherData.canSetDefaultZoomLevel
        this.zoom = otherData.zoom

        pan = Point2D.Point2DFloat(otherData.pan.x.toFloat(), otherData.pan.y.toFloat())

        val matrixVals = FloatArray(9)
        otherData.matrix.getValues(matrixVals)
        val newMatrix = Matrix()
        newMatrix.setValues(matrixVals)
        matrix = newMatrix

        bitmapFactoryOptions.inJustDecodeBounds = otherData.bitmapFactoryOptions.inJustDecodeBounds
        bitmapFactoryOptions.outWidth = otherData.bitmapFactoryOptions.outWidth
        bitmapFactoryOptions.outHeight = otherData.bitmapFactoryOptions.outHeight
        bitmapFactoryOptions.inSampleSize = otherData.bitmapFactoryOptions.inSampleSize

        imageDim = Size(otherData.imageDim.width, otherData.imageDim.height)
        containerDim = Size(otherData.containerDim.width, otherData.containerDim.height)
        viewportW = otherData.viewportW
        viewportH = otherData.viewportH

        updateZoomConstraints()
        setZoomConstraints(otherData.zoomMin, otherData.zoomMax)
    }

    fun doZoom(zoom: Float) {
        val zoomSense = 1.5f
        val zoomDiff = (zoom - 1)
        val zoomNormal = zoomDiff * 2 * zoomSense

        this.zoom = (this.zoom + zoomNormal)
            .coerceIn(minimumValue = zoomMin, maximumValue = zoomMax)

        refreshMatrix()
    }

    fun doPan(addX: Float, addY: Float) {
        val panSense = 1f

        pan = Point2D.Point2DFloat(
            (pan.x + (addX * panSense)).toFloat(),
            (pan.y + (addY * panSense)).toFloat()
        )

        refreshMatrix()
    }

    fun setPan(x: Float, y: Float) {
        pan = Point2D.Point2DFloat(x, y)

        refreshMatrix()
    }

    fun updateMatrix() {
        setAutoInSampleSize(containerDim.width, containerDim.height)
        bitmapFactoryOptions.inJustDecodeBounds = false
        refreshMatrix()
    }

    fun refreshMatrix() {
        val imgW = imageDim.width.toFloat()
        val imgH = imageDim.height.toFloat()

        if (imgW <= 0f || imgH <= 0f || viewportW <= 0f || viewportH <= 0f) return

        val newMatrix = Matrix()
        if (canSetDefaultZoomLevel) {
            val zoomW = viewportW / imgW
            val zoomH = viewportH / imgH
            zoom = min(zoomW.toDouble(), zoomH.toDouble()).toFloat()

            canSetDefaultZoomLevel = false
        }

        newMatrix.setScale(zoom, zoom)
        newMatrix.postTranslate(
            (viewportW / 2f) - (imgW / 2f * zoom) + (pan.x.toFloat() * zoom),
            (viewportH / 2f) - (imgH / 2f * zoom) + (pan.y.toFloat() * zoom)
        )

        val vals = FloatArray(9)
        newMatrix.getValues(vals)

        var distance: Float
        var newPanX = pan.x
        var newPanY = pan.y

        //RIGHT
        val boundsPadding = .2f
        if ((((vals[2]) + (imgW * zoom)).also {
                distance = it
            }) < viewportW * boundsPadding) {
            newPanX -= (distance - (viewportW * boundsPadding)) / zoom
        }
        //LEFT
        if ((vals[2]) > viewportW - (viewportW * boundsPadding)) {
            newPanX += ((viewportW) - (vals[2]) - (viewportW * boundsPadding)) / zoom
        }
        //BOTTOM
        if ((((vals[5]) + (imgH * zoom)).also {
                distance = it
            }) < viewportH * boundsPadding) {
            newPanY -= (distance - (viewportH * boundsPadding)) / zoom
        }
        //TOP
        if ((vals[5]) > viewportH - (viewportH * boundsPadding)) {
            newPanY += ((viewportH) - (vals[5]) - (viewportH * boundsPadding)) / zoom
        }

        if (newPanX != pan.x || newPanY != pan.y) {
            pan = Point2D.Point2DFloat(newPanX.toFloat(), newPanY.toFloat())
            // Re-calculate matrix with new pan
            newMatrix.setScale(zoom, zoom)
            newMatrix.postTranslate(
                (viewportW / 2f) - (imgW / 2f * zoom) + (pan.x.toFloat() * zoom),
                (viewportH / 2f) - (imgH / 2f * zoom) + (pan.y.toFloat() * zoom)
            )
        }

        matrix = newMatrix
    }

    fun postTranslateOriginMatrix(scale: Float, imgW: Float, imgH: Float) {
        val newMatrix = Matrix()
        newMatrix.setScale(scale, scale)
        newMatrix.postTranslate(
            pan.x.toFloat() - (imgW * scale * .5f),
            pan.y.toFloat() - (imgH * scale * .5f))
        matrix = newMatrix
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
        viewportW = w.toFloat()
        viewportH = h.toFloat()

        updateZoomConstraints()
        refreshMatrix()
    }

    fun setImageSize(w: Int, h: Int) {
        imageDim = Size(w, h)

        updateZoomConstraints()
        refreshMatrix()
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
