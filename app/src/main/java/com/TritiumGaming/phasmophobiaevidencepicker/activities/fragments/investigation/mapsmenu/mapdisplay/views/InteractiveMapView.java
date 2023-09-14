package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
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
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.core.view.GestureDetectorCompat;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.InvestigationActivity;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.data.MapData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.POISpinner;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.data.InteractiveMapData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.data.models.PoiModel;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.data.models.PoiType;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.data.models.RoomModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.BitmapUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.geometry.Point2D;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.geometry.Polygon;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MapMenuViewModel;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * InteractiveMapControlView class
 *
 * @author TritiumGamingStudios
 */
public class InteractiveMapView extends View {

    private MapMenuViewModel mapMenuViewModel;

    private InteractiveMapData interactiveMapData;
    private final InteractiveMapData interactivePoiData = new InteractiveMapData();

    private POISpinner roomSpinner;

    private BitmapUtils bitmapUtils = new BitmapUtils();

    private MapData mapData;
    private final ArrayList<Bitmap> mapImages = new ArrayList<>();
    private final HashMap<PoiType, Bitmap> poiImages = new HashMap<>();

    private Rect frameRect;
    private final Paint paint;
    private final PorterDuffColorFilter poiColorFilter;//, selectedBorderColorFilter, selectedFillColorFilter;
    @ColorInt int
            poiColor = Color.WHITE,
            selectedBorderColor = Color.WHITE, //Color.argb(150, 255, 0, 0),
            selectedFillColor = Color.WHITE; //Color.argb(25, 255, 255, 0);

    private RoomModel selectedRoomModel;
    private MapPointRunnable clickRunnable;

    private SparseArray<PointF> mActivePointers;

    private double pinchDistance = 0;
    private Point panOrigin;

    private GestureDetectorCompat mDetector;

    private float pulseAlpha = 1f;

    /**
     * InteractiveMapControlView parameterized constructor
     *
     * @param context
     * @param attrs
     */
    public InteractiveMapView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setColor(Color.LTGRAY);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        // COLORS
        TypedValue typedValue = new TypedValue();
        if (getContext() != null && getContext().getTheme() != null) {
            Resources.Theme theme = getContext().getTheme();

            theme.resolveAttribute(R.attr.poiFillColor, typedValue, true);
            poiColor = typedValue.data;

            theme.resolveAttribute(R.attr.roomBorderColor, typedValue, true);
            selectedBorderColor = typedValue.data;

            theme.resolveAttribute(R.attr.roomFillColor, typedValue, true);
            selectedFillColor = typedValue.data;
        }
        /*int r = Color.red(poiColor);
        int g = Color.green(poiColor);
        int b = Color.blue(poiColor);
        poiColor = Color.argb((int)(255*.75f), r, g, b);*/

        //poiColor = ColorUtils.setColor(poiColor, (int)(255 * .75f), -1, -1, -1);
        poiColorFilter = new PorterDuffColorFilter(
                poiColor, PorterDuff.Mode.MULTIPLY);
/*
        selectedBorderColorFilter = new PorterDuffColorFilter(
                selectedBorderColor, PorterDuff.Mode.MULTIPLY);

        selectedFillColorFilter = new PorterDuffColorFilter(
                selectedFillColor, PorterDuff.Mode.MULTIPLY);*/
    }

    public void init(MapMenuViewModel mapMenuViewModel, POISpinner roomSpinner) {
        this.mapMenuViewModel = mapMenuViewModel;

        interactiveMapData = new InteractiveMapData();
        mActivePointers = new SparseArray<>();
        mDetector = new GestureDetectorCompat(getContext(), new GestureTap());

        this.roomSpinner = roomSpinner;

        AdapterView.OnItemSelectedListener poiSpinnerListener = new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRoomModel =
                        mapMenuViewModel.getCurrentMapModel().getCurrentFloor().
                                getFloorRooms().get(position);

                invalidate();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        };
        roomSpinner.setOnItemSelectedListener(poiSpinnerListener);
        roomSpinner.populateAdapter(mapMenuViewModel.getCurrentMapModel().getCurrentFloor().getFloorRoomNames());
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
        public boolean onSingleTapConfirmed(MotionEvent e) {

            interactiveMapData.setPressedPoint(new Point2D.Float(e.getX(), e.getY()));
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
    public boolean onTouchEvent(MotionEvent event) {

        if (this.mDetector.onTouchEvent(event)) {
            return true;
        }

        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);
        int maskedAction = event.getActionMasked();
        boolean acceptedAction = true;

        switch (maskedAction) {

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                PointF f = new PointF();
                f.x = event.getX(pointerIndex);
                f.y = event.getY(pointerIndex);
                if (mActivePointers != null) {
                    mActivePointers.put(pointerId, f);
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {

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

                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL: {
                if (mActivePointers != null) {
                    mActivePointers.remove(pointerId);
                }

                pinchDistance = 0;
                panOrigin = null;
                break;
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
    public void setMapImages(Activity a) {

        if(bitmapUtils == null) {
            bitmapUtils = new BitmapUtils();
        }

        if (interactiveMapData != null && interactiveMapData.getBitmapFactoryOptions() != null) {

            ArrayList<ArrayList<Integer>> mapFloorLayers = mapData.getAllFloorLayers();
            if(mapFloorLayers == null)
                return;

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
                                bitmapUtils.compileNextBitmap(getContext(), mapImages.get(index)));
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
    public void setPoiImages(Activity a) {
        if(getContext() == null) { return; }

        TypedArray typedArray =
                a.getResources().obtainTypedArray(R.array.poi_resources_array);

        @DrawableRes ArrayList<Integer> resources = new ArrayList<>();
        for (int i = 0; i < typedArray.length(); i++) {
            resources.add(typedArray.getResourceId(i, 0));
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
    protected void onDraw(Canvas canvas) {

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
            if(selectedRoomModel != null) {
                ArrayList<PointF> points = selectedRoomModel.getRoomArea().getPoints();
                PointF firstPoint = points.get(0);
                Path wallpath = new Path();
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

            /*
            float radiusScaled = (scaleY / getHeight()) * 24;
            radiusScaled = Math.min((Math.max(12, radiusScaled)), 36);
            */
            float fontSize = (scaleX / getWidth()) * 24;
            paint.setTextSize(Math.min(36, Math.max(12, fontSize)));

            paint.setAntiAlias(true);
            paint.setColor(poiColor);
            paint.setColorFilter(poiColorFilter);
            for(PoiModel poi: mapMenuViewModel.getCurrentMapModel().getCurrentFloor().getFloorPOIs()) {

                float x = (panX) + poi.getPoint().x * scaleX;
                float y = (panY) + poi.getPoint().y * scaleY;

                Bitmap b = poiImages.get(poi.getType());
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
                } else {
                    Log.d("PoiImage", poi.getType().name() + " does not exist?");
                }

                /*
                paint.setColorFilter(null);
                //set color to yellow
                paint.setColor(Color.argb(25, 255, 255, 0));
                paint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(x, y, radiusScaled, paint);

                paint.setStrokeWidth(2);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawCircle(x, y, radiusScaled, paint);
                */
                /*
                if(scaleX / getWidth() > 1.5) {

                    String title = FontUtils.toTitle(poi.getType().name());
                    float width = paint.measureText(title);

                    paint.setStyle(Paint.Style.FILL_AND_STROKE);
                    paint.setStrokeWidth(2);
                    paint.setColor(Color.argb(255, 255, 255, 255));
                    canvas.drawText(title, x - (width * .5f), y + radiusScaled, paint);

                    paint.setStrokeWidth(0);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(Color.argb(255, 0, 0, 0));
                    canvas.drawText(title, x - (width * .5f), y + radiusScaled, paint);

                }
                */
            }
            paint.setAntiAlias(false);
        }

        if (frameRect == null) {
            frameRect = new Rect(1, 1, getWidth() - 1, getHeight() - 1);
        }

        if (paint != null) {
            paint.setColorFilter(null);
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.STROKE);
            if (frameRect != null) {
                canvas.drawRect(frameRect, paint);
            }
        }
    }

    public void handleClickRunnable() {
        ((InvestigationActivity)getContext()).runOnUiThread(new MapPointRunnable());
    }

    public class MapPointRunnable implements Runnable {

        @Override
        public void run() {

            if(interactiveMapData.getSelectedPoint() == null) { return; }

            float[] matrix = interactiveMapData.getMatrixValues();
            float scaleX = matrix[Matrix.MSCALE_X];
            float scaleY = matrix[Matrix.MSCALE_Y];
            float panX = matrix[Matrix.MTRANS_X];
            float panY = matrix[Matrix.MTRANS_Y];


            float touchX = interactiveMapData.getSelectedPoint().x;
            float touchY = interactiveMapData.getSelectedPoint().y;

            Log.d("Tap", "Input Conversion: " + touchX + " " + touchY);

            ArrayList<RoomModel> rooms = mapMenuViewModel.getCurrentMapModel().getCurrentFloor().getFloorRooms();
            for(RoomModel room: rooms) {

                Polygon shape = new Polygon();
                for(PointF p: room.getRoomArea().getPoints()) {
                    int x = (int)((p.x * scaleX) + (panX));
                    int y = (int)((p.y * scaleY) + (panY));
                    shape.addPoint(x, y);
                }

                if(shape.contains(new Point2D.Float(touchX, touchY))) {
                    Log.d("Tap", "setting temp room");

                    if(room != selectedRoomModel) {
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
