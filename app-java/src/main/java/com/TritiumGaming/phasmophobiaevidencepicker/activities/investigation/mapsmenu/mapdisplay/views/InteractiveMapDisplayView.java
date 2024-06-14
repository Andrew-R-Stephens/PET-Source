package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.views;

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

import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.data.MapViewerModel;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.data.InteractiveMapControlModel;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.data.models.FloorModel;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.data.models.RoomModel;
import com.TritiumGaming.phasmophobiaevidencepicker.utils.BitmapUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.utils.geometry.Polygon;

import java.util.ArrayList;

/**
 * InteractiveMapDisplayView class
 *
 * @author TritiumGamingStudios
 */
public class InteractiveMapDisplayView extends View {

    private InteractiveMapControlModel controllerData;

    private MapViewerModel mapData;
    private final ArrayList<Bitmap> mapImages = new ArrayList<>();

    private Rect frameRect;
    @NonNull
    private final Paint paint;

    private RoomModel selectedRoomModel;

    private final Polygon polygon = new Polygon();

    public InteractiveMapDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setColor(Color.LTGRAY);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
    }

    public void init(InteractiveMapControlModel controllerData) {
        this.controllerData = controllerData;
    }

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
                polygon.reset();
                for(PointF p: selectedRoomModel.getRoomArea().getPoints()) {
                    polygon.addPoint((int)(p.x * controllerData.getZoomLevel()), (int)(p.y * controllerData.getZoomLevel()));
                }
            }

        }

        if (frameRect == null) {
            frameRect = new Rect(1, 1, getWidth() - 1, getHeight() - 1);
        }

        paint.setColorFilter(null);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        if (frameRect != null) {
            canvas.drawRect(frameRect, paint);
        }
    }

}
