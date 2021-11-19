package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.data.MapData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.data.InteractiveMapControlData;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.BitmapUtils;

import java.util.ArrayList;

/**
 * InteractiveMapDisplayView class
 *
 * @author TritiumGamingStudios
 */
public class InteractiveMapDisplayView extends View {

    private BitmapUtils bitmapUtils = new BitmapUtils();

    private InteractiveMapControlData controllerData = null;

    private MapData mapData = null;
    private final ArrayList<Bitmap> mapImages = new ArrayList<>();

    private Rect frameRect;
    private final Paint paint;

    /**
     * InteractiveMapDisplayView parameterized constructor
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
     * @param controllerData
     */
    public void init(InteractiveMapControlData controllerData){
        this.controllerData = controllerData;

        //updateLoadingText();
    }

    /**
     * setMapData method
     * @param mapData
     */
    public void setMapData(MapData mapData){
        this.mapData = mapData;
    }

    /**
     * setMapImages method
     * @param a
     */
    public void setMapImages(Activity a){

        if(controllerData != null && controllerData.getBitmapFactoryOptions() != null) {

            for(int i = 0; i < mapData.getAllFloorLayers().size(); i++)
                mapImages.add(null);

            for(int i = 0; i < mapData.getAllFloorLayers().size(); i++){
                int index = i + mapData.getDefaultFloor();
                if(mapData.getAllFloorLayers().size() <= index)
                    index = 0;

                // IMAGE LOADING ----
                //
                ArrayList<Integer> floor = mapData.getAllFloorLayers().get(index);
                for(int j = 0; j < floor.size(); j++) {
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

    /**
     * onDraw method
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        if(controllerData != null) {
            controllerData.updateMatrix();
            if(mapImages != null && mapData != null && mapData.getCurrentFloor() < mapImages.size()){
                Bitmap b = mapImages.get(mapData.getCurrentFloor());
                if(BitmapUtils.bitmapExists(b)) {
                    controllerData.postTranslateMatrix(
                            b.getWidth(),
                            b.getHeight(),
                            getWidth(),
                            getHeight());
                    canvas.drawBitmap(b, controllerData.getMatrix(), paint);
                }
            }
        }

        if(frameRect == null)
            frameRect = new Rect(1, 1, getWidth()-1, getHeight()-1);

        if(paint != null) {
            paint.setColorFilter(null);
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.STROKE);
            if(frameRect != null)
                canvas.drawRect(frameRect, paint);
        }

    }

    /**
     * recycleBitmaps method
     */
    public void recycleBitmaps(){

        if(mapImages != null)
            for(Bitmap b: mapImages){
                if(BitmapUtils.bitmapExists(b)) {
                    b.recycle();
                }
            }

        System.gc();
    }

}
