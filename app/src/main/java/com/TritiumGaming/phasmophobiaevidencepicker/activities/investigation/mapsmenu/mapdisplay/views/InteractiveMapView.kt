package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.views

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.graphics.PointF
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.util.SparseArray
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.InvestigationActivity
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.POISpinner
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.sharedpreferences.MapMenuViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.map.PoiType
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.map.RoomModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.mapviewer.InteractiveMapModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.mapviewer.MapViewerModel
import com.TritiumGaming.phasmophobiaevidencepicker.utils.BitmapUtils
import com.TritiumGaming.phasmophobiaevidencepicker.utils.BitmapUtils.Companion.bitmapExists
import com.TritiumGaming.phasmophobiaevidencepicker.utils.ColorUtils.getColorFromAttribute
import com.TritiumGaming.phasmophobiaevidencepicker.utils.geometry.Point2D.Point2DFloat
import com.TritiumGaming.phasmophobiaevidencepicker.utils.geometry.Polygon
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * InteractiveMapControlView class
 *
 * @author TritiumGamingStudios
 */
class InteractiveMapView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var mapMenuViewModel: MapMenuViewModel? = null

    private var interactiveMapModel: InteractiveMapModel? = null
    private val interactivePoiModel = InteractiveMapModel()

    private var roomSpinner: POISpinner? = null

    private var bitmapUtils: BitmapUtils? = BitmapUtils()

    private var mapViewerModel: MapViewerModel? = null
    private val mapImages: ArrayList<Bitmap?> = ArrayList()
    private val poiImages = HashMap<PoiType, Bitmap?>()

    private val wallpath = Path()
    private var frameRect: Rect? = null
    private val paint = Paint()

    private val poiColorFilter: PorterDuffColorFilter

    @ColorInt
    var mapBorderColor: Int

    @ColorInt
    var poiColor: Int

    @ColorInt
    var selectedBorderColor: Int

    @ColorInt
    var selectedFillColor: Int

    private var selectedRoomModel: RoomModel? = null

    private var mActivePointers: SparseArray<PointF>? = null

    private var pinchDistance = 0f
    private var panOrigin: Point? = null

    private var mDetector: GestureDetector? = null

    private val pulseAlpha = 1f

    /**
     * InteractiveMapControlView parameterized constructor
     */
    init {
        paint.color = Color.LTGRAY
        paint.strokeWidth = 5f
        paint.style = Paint.Style.STROKE

        // COLORS
        mapBorderColor = getColorFromAttribute(getContext(), R.attr.textColorBody)
        poiColor = getColorFromAttribute(getContext(), R.attr.mapPoiFillColor)
        selectedBorderColor = getColorFromAttribute(getContext(), R.attr.mapRoomBorderColor)
        selectedFillColor = getColorFromAttribute(getContext(), R.attr.mapRoomFillColor)

        poiColorFilter = PorterDuffColorFilter(
            poiColor, PorterDuff.Mode.MULTIPLY
        )
    }

    fun init(mapMenuViewModel: MapMenuViewModel, roomSpinner: POISpinner) {
        this.mapMenuViewModel = mapMenuViewModel

        interactiveMapModel = InteractiveMapModel()
        mActivePointers = SparseArray()
        mDetector = GestureDetector(context, GestureTap())

        this.roomSpinner = roomSpinner

        val poiSpinnerListener: AdapterView.OnItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?, position: Int, id: Long
                ) {
                    selectedRoomModel =
                        mapMenuViewModel.currentMapModel?.currentFloor?.floorRooms?.get(position)

                    invalidate()
                }

                override fun onNothingSelected(arg0: AdapterView<*>?) {
                    // TODO Auto-generated method stub
                }
            }
        roomSpinner.onItemSelectedListener = poiSpinnerListener
        mapMenuViewModel.currentMapModel?.let { currentMap ->
            roomSpinner.populateAdapter(
                currentMap.currentFloor.floorRoomNames
            )
        }
    }

    fun resetRoomSelection() {
        interactiveMapModel?.setPressedPoint(null)
        selectedRoomModel = null
    }

    internal inner class GestureTap : SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent): Boolean {
            return true
        }

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            interactiveMapModel?.setPressedPoint(Point2DFloat(e.x, e.y))
            handleClickRunnable()

            return true
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (mDetector!!.onTouchEvent(event)) {
            return true
        }

        val pointerIndex = event.actionIndex
        val pointerId = event.getPointerId(pointerIndex)
        val maskedAction = event.actionMasked
        var acceptedAction = true

        when (maskedAction) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                val f = PointF()
                f.x = event.getX(pointerIndex)
                f.y = event.getY(pointerIndex)
                mActivePointers?.put(pointerId, f)
            }

            MotionEvent.ACTION_MOVE -> {
                val size = event.pointerCount
                var i = 0
                while (i < size) {
                    mActivePointers?.let { mActivePointers ->
                        val point = mActivePointers[event.getPointerId(i)]
                        point?.x = event.getX(i)
                        point?.y = event.getY(i)
                    }
                    i++
                }

                when(event.pointerCount) {
                    1 -> doPanAction()
                    2 -> doZoomAction()
                    else -> acceptedAction = false
                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_CANCEL -> {
                mActivePointers?.remove(pointerId)

                pinchDistance = 0f
                panOrigin = null
            }
        }
        if (acceptedAction) {
            interactiveMapModel?.setDisplaySize(width, height)
            invalidate()
        }

        return true
    }

    private fun doZoomAction() {
        val p1 = mActivePointers!![0]
        val p2 = mActivePointers!![1]

        if (p1 != null && p2 != null) {
            val distance = sqrt((p2.x - p1.x).pow(2f) + (p2.y - p1.y).pow(2f))
            val delta = distance - pinchDistance

            pinchDistance = distance

            if (abs(delta) > 2) {
                val zoomSense = .02
                var zoomDir = 1
                if (delta < 0) {
                    zoomDir = -1
                }

                interactiveMapModel?.updateZoomLevel(zoomSense * zoomDir)
            }
        }
    }

    private fun doPanAction() {
        val p = mActivePointers!![0]

        p?.let { point ->
            panOrigin?.let { panOrigin ->
                val dX = (panOrigin.x - point.x).toDouble()
                val dY = (panOrigin.y - point.y).toDouble()

                interactiveMapModel?.incrementPan(dX, dY)
            }
            panOrigin = Point(point.x.toInt(), point.y.toInt())
        }
    }

    fun setMapData(mapData: MapViewerModel?) {
        this.mapViewerModel = mapData
    }

    fun setMapImages(a: Activity) {
        if (bitmapUtils == null) {
            bitmapUtils = BitmapUtils()
        }

        mapViewerModel?.let { mapData ->
            val mapFloorLayers = mapData.allFloorLayers

            for (i in mapFloorLayers.indices) {
                mapImages.add(null)
            }

            for (i in mapFloorLayers.indices) {
                var index = i + mapData.defaultFloor
                if (mapFloorLayers.size <= index) {
                    index = 0
                }

                // IMAGE LOADING ----
                //
                val floor = mapFloorLayers[index]
                for (j in floor.indices) {
                    floor.forEach { floorItem ->
                        bitmapUtils?.layers?.add(BitmapUtils.FilteredImage(floorItem, null))
                    }

                    while (bitmapUtils?.hasNextBitmap() == true) {
                        mapImages[index] = bitmapUtils?.compileNextBitmap(
                            context, mapImages[index])
                        a.runOnUiThread { this.invalidate() }
                    }
                }
                bitmapUtils?.clearResources()

                a.runOnUiThread { this.invalidate() }
            }
        }

        bitmapUtils = null
    }

    @Throws(Exception::class)
    fun setPoiImages(a: Activity) {
        val typedArray =
            a.resources.obtainTypedArray(R.array.poi_resources_array)

        @DrawableRes val resources = ArrayList<Int>()
        for (i in 0 until typedArray.length()) {
            val resourceId = typedArray.getResourceId(i, 0)
            Log.d("ResourceId", resourceId.toString() + "")
            resources.add(resourceId)
        }

        for (i in resources.indices) {
            val b = BitmapUtils().setResource(
                resources[i]
            ).compileBitmaps(context)
            poiImages[PoiType.entries[i]] = b
        }

        typedArray.recycle()

        a.runOnUiThread { this.invalidate() }

        bitmapUtils = null
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        interactiveMapModel?.let { interactiveMapData ->
            interactiveMapData.updateMatrix()

            mapViewerModel?.let { mapViewerModel ->
                if (mapViewerModel.currentFloor < mapImages.size) {
                    val b = mapImages[mapViewerModel.currentFloor]
                    if (bitmapExists(b)) {
                        b?.let {
                            interactiveMapData.setImageSize(b.width, b.height)
                            interactiveMapData.postCenterTranslateMatrix(
                                b.width.toFloat(),
                                b.height.toFloat(),
                                width.toFloat(),
                                height.toFloat()
                            )
                            canvas.drawBitmap(b, interactiveMapData.matrix, paint)
                        }
                    }
                }
            }

            val scaleX = interactiveMapData.matrixValues[Matrix.MSCALE_X]
            val scaleY = interactiveMapData.matrixValues[Matrix.MSCALE_Y]
            val panX = interactiveMapData.matrixValues[Matrix.MTRANS_X]
            val panY = interactiveMapData.matrixValues[Matrix.MTRANS_Y]

            paint.strokeWidth = 3f
            wallpath.reset()

            selectedRoomModel?.let { selectedRoomModel ->
                val points: List<PointF> = selectedRoomModel.roomArea.points
                val firstPoint = points[0]
                wallpath.moveTo(
                    (panX) + (firstPoint.x * scaleX),
                    (panY) + (firstPoint.y * scaleY)
                ) // used for first point
                for (i in 1 until points.size) {
                    wallpath.lineTo(
                        (panX) + points[i].x * scaleX,
                        (panY) + points[i].y * scaleY
                    )
                }
                wallpath.lineTo(
                    (panX) + (firstPoint.x * scaleX),
                    (panY) + (firstPoint.y * scaleY)
                )

                paint.color = selectedFillColor
                paint.style = Paint.Style.FILL
                canvas.drawPath(wallpath, paint)

                paint.color = selectedBorderColor
                paint.style = Paint.Style.STROKE
                canvas.drawPath(wallpath, paint)
            }

            val fontSize = (scaleX / width) * 24
            paint.textSize = min(36.0, max(12.0, fontSize.toDouble())).toFloat()

            paint.isAntiAlias = true
            paint.color = poiColor
            paint.setColorFilter(poiColorFilter)

            mapMenuViewModel?.currentMapModel?.let { currentMapModel ->
                for (poi in currentMapModel.currentFloor.floorPOIs) {
                    var x = panX
                    var y = panY
                    poi.point?.let { point ->
                        x = (panX) + point.x * scaleX
                        y = (panY) + point.y * scaleY
                    }

                    val b = poiImages[poi.type]
                    if (bitmapExists(b)) {
                        b?.let {
                            interactivePoiModel.deepCopy(interactiveMapData)
                            interactivePoiModel.setPan(x, y)
                            interactivePoiModel.postTranslateOriginMatrix(
                                b.width.toFloat(), b.height.toFloat(),
                                width.toFloat(), height.toFloat()
                            )
                            canvas.drawBitmap(b, interactivePoiModel.matrix, paint)
                        }

                    }
                }
            }

            paint.isAntiAlias = false
        }

        if (frameRect == null) {
            frameRect = Rect(1, 1, width - 1, height - 1)
        } else {
            frameRect?.bottom = height - 1
        }

        paint.setColorFilter(null)
        paint.color = mapBorderColor
        paint.style = Paint.Style.STROKE
        frameRect?.let { frameRect ->
            canvas.drawRect(frameRect, paint)
        }
    }

    fun handleClickRunnable() {
        (context as InvestigationActivity).runOnUiThread(MapPointRunnable())
    }

    inner class MapPointRunnable : Runnable {
        override fun run() {

            var matrix: FloatArray
            var scaleX = 1f
            var scaleY = 1f
            var panX = 0f
            var panY = 0f
            var touchX = 0f
            var touchY = 0f

            interactiveMapModel?.let { interactiveMapModel ->
                matrix = interactiveMapModel.matrixValues
                scaleX = matrix[Matrix.MSCALE_X]
                scaleY = matrix[Matrix.MSCALE_Y]
                panX = matrix[Matrix.MTRANS_X]
                panY = matrix[Matrix.MTRANS_Y]

                interactiveMapModel.selectedPoint?.let { selectedPoint ->
                    touchX = selectedPoint.x.toFloat()
                    touchY = selectedPoint.y.toFloat()
                } ?: return
            } ?: return

            Log.d("Tap", "Input Conversion: $touchX $touchY")

            mapMenuViewModel?.currentMapModel?.let { currentMapModel ->
                val rooms = currentMapModel.currentFloor.floorRooms
                for (room in rooms) {
                    val shape = Polygon()
                    for (p in room.roomArea.points) {
                        val x = ((p.x * scaleX) + (panX)).toInt()
                        val y = ((p.y * scaleY) + (panY)).toInt()
                        shape.addPoint(x, y)
                    }

                    if (shape.contains(Point2DFloat(touchX, touchY))) {
                        Log.d("Tap", "setting temp room")

                        if (room != selectedRoomModel) {
                            selectedRoomModel = room
                            selectedRoomModel!!.print()
                        } else {
                            resetRoomSelection()
                        }

                        roomSpinner?.setSelection(rooms.indexOf(room))

                        invalidate()
                        return
                    }
                }
                resetRoomSelection()
                invalidate()
            }
        }
    }
}
