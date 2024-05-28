package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.data.InteractiveMapControlData;

/**
 * InteractiveMapControlView class
 *
 * @author TritiumGamingStudios
 */
public class InteractiveMapControlView extends View {

    @Nullable private InteractiveMapControlData controllerData;

    @Nullable private SparseArray<PointF> mActivePointers;

    private double pinchDistance = 0;
    @Nullable private Point panOrigin;
    @Nullable private View recipient;

    private GestureDetector mDetector;

    public InteractiveMapControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(InteractiveMapControlData controllerData, View recipient) {
        this.controllerData = controllerData;
        this.recipient = recipient;

        mActivePointers = new SparseArray<>();

        mDetector = new GestureDetector(getContext(), new GestureTap());
    }

    class GestureTap extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(@NonNull MotionEvent e) {
            Log.d("Tap", "Double " + e.getAction());
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {
            Log.d("Tap", "Single " + e.getAction());
            controllerData.setPressedPoint(e.getX(), e.getY());
            float pX = controllerData.getPressedPointX();
            float pY = controllerData.getPressedPointY();
            Log.d("Touch", pX + " " + pY);
            return true;
        }
    }

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
            if (recipient != null) {
                controllerData.setDisplaySize(recipient.getWidth(), recipient.getHeight());
                recipient.invalidate();
            }
        }

        return true;
    }

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

                if (controllerData != null) {
                    controllerData.incrementZoomLevel(zoomSense * zoomDir);
                }
            }
        }
    }

    public void doPanAction() {

        PointF p = mActivePointers.get(0);

        if (p != null && panOrigin != null) {
            double dX = panOrigin.x - p.x;
            double dY = panOrigin.y - p.y;

            if (controllerData != null) {
                controllerData.incrementPan(dX, dY);
            }
        }

        if (p != null) {
            panOrigin = new Point((int) p.x, (int) p.y);
        }

    }

}
