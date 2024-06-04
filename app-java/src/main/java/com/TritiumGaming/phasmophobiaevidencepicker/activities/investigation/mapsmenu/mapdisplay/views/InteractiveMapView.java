package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.InvestigationActivity;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.data.MapData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.POISpinner;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.data.InteractiveMapModel;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.data.models.PoiModel;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.data.models.PoiType;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.data.models.RoomModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.BitmapUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.ColorUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.geometry.Point2D;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.geometry.Polygon;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MapMenuViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * InteractiveMapControlView class
 *
 * @author TritiumGamingStudios
 */
public class InteractiveMapView extends View {

    private MapMenuViewModel mapMenuViewModel;

    private InteractiveMapModel interactiveMapData;
    private final InteractiveMapModel interactivePoiData = new InteractiveMapModel();

    private POISpinner roomSpinner;

    @Nullable
    private BitmapUtils bitmapUtils = new BitmapUtils();

    private MapData mapData;
    private final ArrayList<Bitmap> mapImages = new ArrayList<>();
    private final HashMap<PoiType, Bitmap> poiImages = new HashMap<>();

    private final Path wallpath = new Path();
    private Rect frameRect;
    @NonNull
    private final Paint paint;

    @NonNull private final PorterDuffColorFilter poiColorFilter;
    @ColorInt int mapBorderColor, poiColor, selectedBorderColor, selectedFillColor;

    @Nullable
    private RoomModel selectedRoomModel;

    private SparseArray<PointF> mActivePointers;

    private double pinchDistance = 0;
    @Nullable
    private Point panOrigin;

    private GestureDetector mDetector;

    private final float pulseAlpha = 1f;

    /**
     * InteractiveMapControlView parameterized constructor
     */
    public InteractiveMapView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setColor(Color.LTGRAY);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        // COLORS
        mapBorderColor = ColorUtils.getColorFromAttribute(getContext(), R.attr.textColorBody);
        poiColor = ColorUtils.getColorFromAttribute(getContext(), R.attr.mapPoiFillColor);
        selectedBorderColor = ColorUtils.getColorFromAttribute(getContext(), R.attr.mapRoomBorderColor);
        selectedFillColor = ColorUtils.getColorFromAttribute(getContext(), R.attr.mapRoomFillColor);

        poiColorFilter = new PorterDuffColorFilter(
                poiColor, PorterDuff.Mode.MULTIPLY);
    }

    public void init(@NonNull MapMenuViewModel mapMenuViewModel, @NonNull POISpinner roomSpinner) {
        this.mapMenuViewModel = mapMenuViewModel;

        interactiveMapData = new InteractiveMapModel();
        mActivePointers = new SparseArray<>();
        mDetector = new GestureDetector(getContext(), new GestureTap());

        this.roomSpinner = roomSpinner;

        AdapterView.OnItemSelectedListener poiSpinnerListener = new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRoomModel =
                        mapMenuViewModel.getCurrentMapModel().getCurrentFloor().floorRooms.get(position);

                invalidate();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        };
        roomSpinner.setOnItemSelectedListener(poiSpinnerListener);
        if(mapMenuViewModel.getCurrentMapModel() != null) {
            roomSpinner.populateAdapter(
                    mapMenuViewModel.getCurrentMapModel().getCurrentFloor().getFloorRoomNames());
        }
    }

    public void resetRoomSelection() {
        interactiveMapData.setPressedPoint(null);
        selectedRoomModel = null;
    }

    class GestureTap extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {

            interactiveMapData.setPressedPoint(new Point2D.Point2DFloat(e.getX(), e.getY()));
            handleClickRunnable();

            return true;
        }
    }

    /**
     * onTouchEvent listener
     *
     * @param event
     * @return
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {

        if (this.mDetector.onTouchEvent(event)) {
            return true;
        }

        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);
        int maskedAction = event.getActionMasked();
        boolean acceptedAction = true;

        switch (maskedAction) {
            case MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                PointF f = new PointF();
                f.x = event.getX(pointerIndex);
                f.y = event.getY(pointerIndex);
                if (mActivePointers != null) {
                    mActivePointers.put(pointerId, f);
                }
            }
            case MotionEvent.ACTION_MOVE -> {

                for (int size = event.getPointerCount(), i = 0; i < size; i++) {
                    if (mActivePointers != null) {
                        PointF point = mActivePointers.get(event.getPointerId(i));
                        if (point != null) {
                            point.x = event.getX(i);
                            point.y = event.getY(i);
                        }
                    }
                }

                if (event.getPointerCount() == 1) {
                    doPanAction();
                } else if (event.getPointerCount() == 2) {
                    doZoomAction();
                } else {
                    acceptedAction = false;
                }

            }
            case MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_CANCEL -> {
                if (mActivePointers != null) {
                    mActivePointers.remove(pointerId);
                }

                pinchDistance = 0;
                panOrigin = null;
            }
        }

        if (acceptedAction) {
            interactiveMapData.setDisplaySize(getWidth(), getHeight());
            invalidate();
        }

        return true;
    }

    /**
     * doZoomAction method
     */
    public void doZoomAction() {

        PointF p1 = mActivePointers.get(0);
        PointF p2 = mActivePointers.get(1);

        if (p1 != null && p2 != null) {

            double distance = Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
            double delta = distance - pinchDistance;

            pinchDistance = distance;

            if (Math.abs(delta) > 2) {
                double zoomSense = .02;
                int zoomDir = 1;
                if (delta < 0) {
                    zoomDir = -1;
                }

                if (interactiveMapData != null) {
                    interactiveMapData.updateZoomLevel(zoomSense * zoomDir);
                }
            }
        }
    }

    /**
     * doPanAction method
     */
    public void doPanAction() {

        PointF p = mActivePointers.get(0);

        if (p != null && panOrigin != null) {
            double dX = panOrigin.x - p.x;
            double dY = panOrigin.y - p.y;

            if (interactiveMapData != null) {
                interactiveMapData.incrementPan(dX, dY);
            }
        }

        if (p != null) {
            panOrigin = new Point((int) p.x, (int) p.y);
        }

    }

    /**
     * setMapData method
     *
     * @param mapData
     */
    public void setMapData(MapData mapData) {
        this.mapData = mapData;
    }

    /**
     * setMapImages method
     *
     * @param a
     */
    public void setMapImages(@NonNull Activity a) {

        if(bitmapUtils == null) {
            bitmapUtils = new BitmapUtils();
        }

        if (interactiveMapData != null) {

            ArrayList<ArrayList<Integer>> mapFloorLayers = mapData.getAllFloorLayers();

            for (int i = 0; i < mapFloorLayers.size(); i++) {
                mapImages.add(null);
            }

            for (int i = 0; i < mapFloorLayers.size(); i++) {
                int index = i + mapData.getDefaultFloor();
                if (mapFloorLayers.size() <= index) {
                    index = 0;
                }

                // IMAGE LOADING ----
                //
                ArrayList<Integer> floor = mapFloorLayers.get(index);
                for (int j = 0; j < floor.size(); j++) {
                    if(bitmapUtils != null) {
                        bitmapUtils.setResources(floor);
                    }
                    while (bitmapUtils != null && bitmapUtils.hasNextBitmap()) {
                        mapImages.set(
                                index,
                                bitmapUtils.compileNextBitmap(
                                        getContext(),
                                        mapImages.get(index)
                                )
                        );
                        a.runOnUiThread(this::invalidate);
                    }
                }
                if(bitmapUtils != null) {
                    bitmapUtils.clearResources();
                }

                a.runOnUiThread(this::invalidate);
            }
            a.runOnUiThread(this::invalidate);
        }

        bitmapUtils = null;
    }

    /**
     * setPoiImages method
     *
     * @param a
     */
    public void setPoiImages(@NonNull Activity a) {
        if(getContext() == null) { return; }

        TypedArray typedArray =
                a.getResources().obtainTypedArray(R.array.poi_resources_array);

        @DrawableRes ArrayList<Integer> resources = new ArrayList<>();
        for (int i = 0; i < typedArray.length(); i++) {
            Integer resourceId = typedArray.getResourceId(i, 0);
            Log.d("ResourceId", resourceId + "");
            resources.add(resourceId);
        }

        for(int i = 0; i < resources.size(); i++) {
            Bitmap b = new BitmapUtils().setResource(resources.get(i)).compileBitmaps(getContext());
            poiImages.put(PoiType.values()[i], b);
        }

        typedArray.recycle();

        a.runOnUiThread(this::invalidate);

        bitmapUtils = null;
    }

    /**
     * onDraw method
     *
     * @param canvas
     */
    @Override
    protected void onDraw(@NonNull Canvas canvas) {

        super.onDraw(canvas);

        if (interactiveMapData != null) {
            interactiveMapData.updateMatrix();
            if (mapImages != null && mapData != null && mapData.getCurrentFloor() < mapImages.size()) {
                Bitmap b = mapImages.get(mapData.getCurrentFloor());
                if (BitmapUtils.bitmapExists(b)) {
                    interactiveMapData.setImageSize(b.getWidth(), b.getHeight());
                    interactiveMapData.postCenterTranslateMatrix(
                            b.getWidth(),
                            b.getHeight(),
                            getWidth(),
                            getHeight());
                    canvas.drawBitmap(b, interactiveMapData.getMatrix(), paint);
                }
            }

            float scaleX = interactiveMapData.getMatrixValues()[Matrix.MSCALE_X];
            float scaleY = interactiveMapData.getMatrixValues()[Matrix.MSCALE_Y];
            float panX = interactiveMapData.getMatrixValues()[Matrix.MTRANS_X];
            float panY = interactiveMapData.getMatrixValues()[Matrix.MTRANS_Y];

            paint.setStrokeWidth(3);
            wallpath.reset();
            if(selectedRoomModel != null) {
                List<PointF> points = selectedRoomModel.roomArea.getPoints();
                PointF firstPoint = points.get(0);
                wallpath.moveTo(
                        (panX) + (firstPoint.x * scaleX),
                        (panY) + (firstPoint.y * scaleY)
                ); // used for first point
                for (int i = 1; i < points.size(); i++) {
                    wallpath.lineTo(
                            (panX) + points.get(i).x * scaleX,
                            (panY) + points.get(i).y * scaleY);
                }
                wallpath.lineTo(
                        (panX) + (firstPoint.x * scaleX),
                        (panY) + (firstPoint.y * scaleY)
                );

                paint.setColor(selectedFillColor);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawPath(wallpath, paint);

                paint.setColor(selectedBorderColor);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawPath(wallpath, paint);
            }

            float fontSize = (scaleX / getWidth()) * 24;
            paint.setTextSize(Math.min(36, Math.max(12, fontSize)));

            paint.setAntiAlias(true);
            paint.setColor(poiColor);
            paint.setColorFilter(poiColorFilter);

            if(mapMenuViewModel != null && mapMenuViewModel.getCurrentMapModel() != null) {
                for (PoiModel poi : mapMenuViewModel.getCurrentMapModel().getCurrentFloor().floorPOIs) {
                    float x = (panX) + poi.point.x * scaleX;
                    float y = (panY) + poi.point.y * scaleY;

                    Bitmap b = poiImages.get(poi.type);
                    if (BitmapUtils.bitmapExists(b)) {
                        interactivePoiData.deepCopy(interactiveMapData);
                        interactivePoiData.setPan(
                                x,
                                y
                        );
                        interactivePoiData.postTranslateOriginMatrix(
                                b.getWidth(),
                                b.getHeight(),
                                getWidth(),
                                getHeight()
                        );
                        canvas.drawBitmap(b, interactivePoiData.getMatrix(), paint);
                    }

                }
            }

            paint.setAntiAlias(false);
        }

        if (frameRect == null) {
            frameRect = new Rect(1, 1, getWidth() - 1, getHeight() - 1);
        } else {
            frameRect.bottom = getHeight() -1;
        }

        paint.setColorFilter(null);
        paint.setColor(mapBorderColor);
        paint.setStyle(Paint.Style.STROKE);
        if (frameRect != null) {
            canvas.drawRect(frameRect, paint);
        }
    }

    public void handleClickRunnable() {
        ((InvestigationActivity)getContext()).runOnUiThread(new MapPointRunnable());
    }

    public class MapPointRunnable implements Runnable {

        @Override
        public void run() {

            if(interactiveMapData.getSelectedPoint() == null) { return; }
            if(mapMenuViewModel == null || mapMenuViewModel.getCurrentMapModel() == null) {
                return;
            }

            float[] matrix = interactiveMapData.getMatrixValues();
            float scaleX = matrix[Matrix.MSCALE_X];
            float scaleY = matrix[Matrix.MSCALE_Y];
            float panX = matrix[Matrix.MTRANS_X];
            float panY = matrix[Matrix.MTRANS_Y];


            float touchX = (float)interactiveMapData.getSelectedPoint().getX();
            float touchY = (float)interactiveMapData.getSelectedPoint().getY();

            Log.d("Tap", "Input Conversion: " + touchX + " " + touchY);

            if(mapMenuViewModel != null &&
                    mapMenuViewModel.getCurrentMapModel() != null) {

                ArrayList<RoomModel> rooms = mapMenuViewModel.getCurrentMapModel().getCurrentFloor().floorRooms;
                for (RoomModel room : rooms) {

                    Polygon shape = new Polygon();
                    for (PointF p : room.roomArea.getPoints()) {
                        int x = (int) ((p.x * scaleX) + (panX));
                        int y = (int) ((p.y * scaleY) + (panY));
                        shape.addPoint(x, y);
                    }

                    if (shape.contains(new Point2D.Point2DFloat(touchX, touchY))) {
                        Log.d("Tap", "setting temp room");

                        if (room != selectedRoomModel) {
                            selectedRoomModel = room;
                            selectedRoomModel.print();
                        } else {
                            resetRoomSelection();
                        }

                        roomSpinner.setSelection(rooms.indexOf(room));

                        invalidate();
                        return;
                    }
                }
                resetRoomSelection();
                invalidate();
            }
        }
    }

    /**
     * recycleBitmaps method
     */
    public void recycleBitmaps() {

        if (mapImages != null)
            for (Bitmap b : mapImages) {
                if (BitmapUtils.bitmapExists(b)) {
                    b.recycle();
                }
            }

        System.gc();
    }

}
