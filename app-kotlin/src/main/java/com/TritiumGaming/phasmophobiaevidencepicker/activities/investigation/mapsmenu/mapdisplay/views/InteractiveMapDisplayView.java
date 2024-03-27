package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.data.MapData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.data.InteractiveMapControlData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.data.models.FloorModel;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.data.models.RoomModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.BitmapUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.geometry.Point2D;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.geometry.Polygon;

import java.util.ArrayList;

/**
 * InteractiveMapDisplayView class
 *
 * @author TritiumGamingStudios
 */
public class InteractiveMapDisplayView extends View {

    @Nullable
    private BitmapUtils bitmapUtils = new BitmapUtils();

    private InteractiveMapControlData controllerData;

    private MapData mapData;
    private final ArrayList<Bitmap> mapImages = new ArrayList<>();
    private FloorModel floorModel;

    private Rect frameRect;
    @NonNull
    private final Paint paint;

    private RoomModel selectedRoomModel;
    private MapPointRunnable clickRunnable;

    /**
     * InteractiveMapDisplayView parameterized constructor
     *
     * @param context
     * @param attrs
     */
    public InteractiveMapDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setColor(Color.LTGRAY);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
    }

    /**
     * init method
     *
     * @param controllerData
     */
    public void init(InteractiveMapControlData controllerData) {
        this.controllerData = controllerData;
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

        if (controllerData != null && controllerData.getBitmapFactoryOptions() != null) {

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
                    bitmapUtils.setResources(floor);
                    while (bitmapUtils.hasNextBitmap()) {
                        mapImages.set(
                                index,
                                bitmapUtils.compileNextBitmap(getContext(), mapImages.get(index)));
                        a.runOnUiThread(this::invalidate);
                    }
                }
                bitmapUtils.clearResources();

                a.runOnUiThread(this::invalidate);
            }
            a.runOnUiThread(this::invalidate);
        }

        bitmapUtils = null;
    }

    public void setFloorModel(FloorModel floorModel) {
        this.floorModel = floorModel;
    }

    /**
     * onDraw method
     *
     * @param canvas
     */
    @Override
    protected void onDraw(@NonNull Canvas canvas) {

        super.onDraw(canvas);

        if (controllerData != null) {
            controllerData.updateMatrix();
            if (mapImages != null && mapData != null && mapData.getCurrentFloor() < mapImages.size()) {
                Bitmap b = mapImages.get(mapData.getCurrentFloor());
                if (BitmapUtils.bitmapExists(b)) {
                    controllerData.postTranslateMatrix(
                            b.getWidth(),
                            b.getHeight(),
                            getWidth(),
                            getHeight());
                    canvas.drawBitmap(b, controllerData.getMatrix(), paint);
                }
            }

            if(selectedRoomModel != null) {
                Polygon polygon = new Polygon();
                for(PointF p: selectedRoomModel.getRoomArea().getPoints()) {
                    polygon.addPoint((int)(p.x * controllerData.getZoomLevel()), (int)(p.y * controllerData.getZoomLevel()));
                }
            }

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

    public class MapPointRunnable implements Runnable {

        @Override
        public void run() {

            ArrayList<RoomModel> rooms = floorModel.getFloorRooms();
            for(RoomModel room: rooms) {

                Polygon shape = new Polygon();
                for(PointF p: room.getRoomArea().getPoints()) {
                    shape.addPoint((int)(p.x * getWidth()) , (int)(p.y * getHeight()));
                }
                if(shape.contains(new Point2D.Float((int)controllerData.getPressedPointX(), (int)controllerData.getPressedPointY()))) {
                    System.out.println("setting temp room");
                    selectedRoomModel = room;
                    selectedRoomModel.print();
                }
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
