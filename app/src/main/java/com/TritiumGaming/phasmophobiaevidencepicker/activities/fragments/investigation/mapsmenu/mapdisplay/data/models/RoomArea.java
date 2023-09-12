package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.data.models;

import android.graphics.PointF;
import android.util.Log;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.io.models.WorldMapWrapper;

import java.util.ArrayList;

public class RoomArea {

    private ArrayList<PointF> points = new ArrayList<>();

    public RoomArea() { }

    public RoomArea(RoomArea tempRoomArea) {
        setPoints(tempRoomArea.getPoints());
    }

    public void setPoints(ArrayList<PointF> tempPoints) {
        for(PointF tempPoint: tempPoints) {
            addPoint(new PointF(tempPoint.x, tempPoint.y));
        }
    }

    public void setPoints(WorldMapWrapper.WorldMap.Floor.Room.RoomPoints tempPoints) {
        if(tempPoints == null || tempPoints.points == null) return;
        for(WorldMapWrapper.WorldMap.Floor.Room.RoomPoints.RoomPoint tempPoint: tempPoints.points) {
            addPoint(new PointF(tempPoint.x, tempPoint.y));
        }
    }

    public void reset() {
        points = new ArrayList<>();
    }

    public void addPoint(float x, float y) {
        points.add(new PointF(x, y));
    }

    public void addPoint(PointF point) {
        points.add(point);
    }

    public ArrayList<PointF> getPoints() {
        return points;
    }

    public PointF getLastPoint() {
        if(points.size() == 0) {
            return null;
        }

        return points.get(points.size()-1);
    }

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
