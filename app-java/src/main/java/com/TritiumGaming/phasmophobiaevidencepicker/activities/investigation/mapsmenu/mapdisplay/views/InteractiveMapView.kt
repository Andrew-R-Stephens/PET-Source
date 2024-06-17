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
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MapMenuViewModel
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

    private var interactiveMapData: InteractiveMapModel? = null
    private val interactivePoiData = InteractiveMapModel()

    private var roomSpinner: POISpinner? = null

    private var bitmapUtils: BitmapUtils? = BitmapUtils()

    private var mapData: MapViewerModel? = null
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

        interactiveMapData = InteractiveMapModel()
        mActivePointers = SparseArray()
        mDetector = GestureDetector(context, GestureTap())

        this.roomSpinner = roomSpinner

        val poiSpinnerListener: AdapterView.OnItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    selectedRoomModel =
                        mapMenuViewModel.currentMapModel!!.currentFloor.floorRooms[position]

                    invalidate()
                }

                override fun onNothingSelected(arg0: AdapterView<*>?) {
                    // TODO Auto-generated method stub
                }
            }
        roomSpinner.onItemSelectedListener = poiSpinnerListener
        if (mapMenuViewModel.currentMapModel != null) {
            roomSpinner.populateAdapter(
                mapMenuViewModel.currentMapModel!!.currentFloor.floorRoomNames
            )
        }
    }

    fun resetRoomSelection() {
        interactiveMapData!!.setPressedPoint(null)
        selectedRoomModel = null
    }

    internal inner class GestureTap : SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent): Boolean {
            return true
        }

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            interactiveMapData!!.setPressedPoint(Point2DFloat(e.x, e.y))
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
                if (mActivePointers != null) {
                    mActivePointers!!.put(pointerId, f)
                }
            }

            MotionEvent.ACTION_MOVE -> {
                val size = event.pointerCount
                var i = 0
                while (i < size) {
                    if (mActivePointers != null) {
                        val point = mActivePointers!![event.getPointerId(i)]
                        if (point != null) {
                            point.x = event.getX(i)
                            point.y = event.getY(i)
                        }
                    }
                    i++
                }

                if (event.pointerCount == 1) {
                    doPanAction()
                } else if (event.pointerCount == 2) {
                    doZoomAction()
                } else {
                    acceptedAction = false
                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_CANCEL -> {
                if (mActivePointers != null) {
                    mActivePointers!!.remove(pointerId)
                }

                pinchDistance = 0f
                panOrigin = null
            }
        }
        if (acceptedAction) {
            interactiveMapData!!.setDisplaySize(width, height)
            invalidate()
        }

        return true
    }

    fun doZoomAction() {
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

                if (interactiveMapData != null) {
                    interactiveMapData!!.updateZoomLevel(zoomSense * zoomDir)
                }
            }
        }
    }

    fun doPanAction() {
        val p = mActivePointers!![0]

        if (p != null && panOrigin != null) {
            val dX = (panOrigin!!.x - p.x).toDouble()
            val dY = (panOrigin!!.y - p.y).toDouble()

            if (interactiveMapData != null) {
                interactiveMapData!!.incrementPan(dX, dY)
            }
        }

        if (p != null) {
            panOrigin = Point(p.x.toInt(), p.y.toInt())
        }
    }

    fun setMapData(mapData: MapViewerModel?) {
        this.mapData = mapData
    }

    fun setMapImages(a: Activity) {
        if (bitmapUtils == null) {
            bitmapUtils = BitmapUtils()
        }

        if (interactiveMapData != null) {
            val mapFloorLayers = mapData!!.allFloorLayers

            for (i in mapFloorLayers.indices) {
                mapImages!!.add(null)
            }

            for (i in mapFloorLayers.indices) {
                var index = i + mapData!!.defaultFloor
                if (mapFloorLayers.size <= index) {
                    index = 0
                }

                // IMAGE LOADING ----
                //
                val floor = mapFloorLayers[index]
                for (j in floor.indices) {
                    if (bitmapUtils != null) {
                        bitmapUtils!!.resources = floor
                    }
                    while (bitmapUtils != null && bitmapUtils!!.hasNextBitmap()) {
                        mapImages!![index] = bitmapUtils!!.compileNextBitmap(
                            context,
                            mapImages[index]
                        )
                        a.runOnUiThread { this.invalidate() }
                    }
                }
                if (bitmapUtils != null) {
                    bitmapUtils!!.clearResources()
                }

                a.runOnUiThread { this.invalidate() }
            }
            a.runOnUiThread { this.invalidate() }
        }

        bitmapUtils = null
    }

    fun setPoiImages(a: Activity) {
        if (context == null) {
            return
        }

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

        if (interactiveMapData != null) {
            interactiveMapData!!.updateMatrix()
            if (mapImages != null && mapData != null && mapData!!.currentFloor < mapImages.size) {
                val b = mapImages[mapData!!.currentFloor]
                if (bitmapExists(b)) {
                    interactiveMapData!!.setImageSize(b!!.width, b.height)
                    interactiveMapData!!.postCenterTranslateMatrix(
                        b.width.toFloat(),
                        b.height.toFloat(),
                        width.toFloat(),
                        height.toFloat()
                    )
                    canvas.drawBitmap(b, interactiveMapData!!.matrix, paint)
                }
            }

            val scaleX = interactiveMapData!!.matrixValues[Matrix.MSCALE_X]
            val scaleY = interactiveMapData!!.matrixValues[Matrix.MSCALE_Y]
            val panX = interactiveMapData!!.matrixValues[Matrix.MTRANS_X]
            val panY = interactiveMapData!!.matrixValues[Matrix.MTRANS_Y]

            paint.strokeWidth = 3f
            wallpath.reset()
            if (selectedRoomModel != null) {
                val points: List<PointF> = selectedRoomModel!!.roomArea.points
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

            if (mapMenuViewModel != null && mapMenuViewModel!!.currentMapModel != null) {
                for (poi in mapMenuViewModel!!.currentMapModel!!.currentFloor.floorPOIs) {
                    val x = (panX) + poi.point!!.x * scaleX
                    val y = (panY) + poi.point!!.y * scaleY

                    val b = poiImages[poi.type]
                    if (bitmapExists(b)) {
                        interactivePoiData.deepCopy(interactiveMapData!!)
                        interactivePoiData.setPan(
                            x,
                            y
                        )
                        interactivePoiData.postTranslateOriginMatrix(
                            b!!.width.toFloat(),
                            b.height.toFloat(),
                            width.toFloat(),
                            height.toFloat()
                        )
                        canvas.drawBitmap(b, interactivePoiData.matrix, paint)
                    }
                }
            }

            paint.isAntiAlias = false
        }

        if (frameRect == null) {
            frameRect = Rect(1, 1, width - 1, height - 1)
        } else {
            frameRect!!.bottom = height - 1
        }

        paint.setColorFilter(null)
        paint.color = mapBorderColor
        paint.style = Paint.Style.STROKE
        if (frameRect != null) {
            canvas.drawRect(frameRect!!, paint)
        }
    }

    fun handleClickRunnable() {
        (context as InvestigationActivity).runOnUiThread(MapPointRunnable())
    }

    inner class MapPointRunnable : Runnable {
        override fun run() {
            if (interactiveMapData!!.selectedPoint == null) {
                return
            }
            if (mapMenuViewModel == null || mapMenuViewModel!!.currentMapModel == null) {
                return
            }

            val matrix = interactiveMapData!!.matrixValues
            val scaleX = matrix[Matrix.MSCALE_X]
            val scaleY = matrix[Matrix.MSCALE_Y]
            val panX = matrix[Matrix.MTRANS_X]
            val panY = matrix[Matrix.MTRANS_Y]


            val touchX = interactiveMapData!!.selectedPoint!!.x.toFloat()
            val touchY = interactiveMapData!!.selectedPoint!!.y.toFloat()

            Log.d("Tap", "Input Conversion: $touchX $touchY")

            if (mapMenuViewModel != null &&
                mapMenuViewModel!!.currentMapModel != null
            ) {
                val rooms = mapMenuViewModel!!.currentMapModel!!.currentFloor.floorRooms
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

                        roomSpinner!!.setSelection(rooms.indexOf(room))

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
