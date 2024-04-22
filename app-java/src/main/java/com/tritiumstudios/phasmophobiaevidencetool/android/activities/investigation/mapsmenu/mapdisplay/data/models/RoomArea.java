package com.tritiumstudios.phasmophobiaevidencetool.android.activities.investigation.mapsmenu.mapdisplay.data.models;

import android.graphics.PointF;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tritiumstudios.phasmophobiaevidencetool.android.activities.investigation.mapsmenu.mapdisplay.io.models.WorldMapWrapper;

import java.util.ArrayList;

public class RoomArea {

    @NonNull
    private ArrayList<PointF> points = new ArrayList<>();

    public RoomArea() { }

    public void setPoints(@NonNull ArrayList<PointF> tempPoints) {
        for(PointF tempPoint: tempPoints) {
            addPoint(new PointF(tempPoint.x, tempPoint.y));
        }
    }

    public void setPoints(@Nullable WorldMapWrapper.WorldMap.Floor.Room.RoomPoints tempPoints) {
        if(tempPoints == null || tempPoints.points == null) return;
        for(WorldMapWrapper.WorldMap.Floor.Room.RoomPoints.RoomPoint tempPoint: tempPoints.points) {
            addPoint(new PointF(tempPoint.x, tempPoint.y));
        }
    }

    @NonNull
    public PointF getCenter() {
        float x = 0f;
        float y = 0f;
        int pointCount = points.size();
        for (int i = 0;i < pointCount - 1;i++){
            final PointF point = points.get(i);
            x += point.x;
            y += point.y;
        }

        x = x/pointCount;
        y = y/pointCount;

        return new PointF(x, y);
    }

    public void reset() {
        points = new ArrayList<>();
    }

    public void addPoint(PointF point) {
        points.add(point);
    }

    public ArrayList<PointF> getPoints() {
        return points;
    }

    @Nullable
    public PointF getLastPoint() {
        if(points.size() == 0) {
            return null;
        }

        return points.get(points.size()-1);
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(PointF p: points) {
            s.append("\n\t\t[Point: x").append(p.x).append(" y").append(p.y).append("]");
        }
        return "[Points:" + s + "\t]";
    }

    public synchronized void print() {
        for(PointF p: points) {
            Log.d("Maps", "[Point: x" + p.x + " y" + p.y + "]");
        }
    }
}
