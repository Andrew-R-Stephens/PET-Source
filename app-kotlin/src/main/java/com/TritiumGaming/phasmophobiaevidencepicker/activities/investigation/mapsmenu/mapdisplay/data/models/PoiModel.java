package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.data.models;

import android.graphics.PointF;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PoiModel {

    private int id = -1;
    private String name = "";
    private PoiType type = PoiType.values()[0];
    @Nullable
    private PointF point = null;

    public PoiModel() {
        setId(-1);
        setName("");
    }

    public PoiModel(@Nullable PoiModel tempPoiData) {
        if(tempPoiData == null) { return; }

        setId(tempPoiData.getId());
        setName(tempPoiData.getName());
        setType(tempPoiData.getType());
        setPoint(tempPoiData.getPoint());
    }

    public void setPoint(PointF tempPoiData) {
        this.point = tempPoiData;
    }

    public PointF getPoint() {
        return point;
    }

    public PoiModel(int id, String name, int type, float x, float y) {
        setId(id);
        setName(name);
        setType(type);
        setPoint(new PointF(x, y));
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setType(PoiType type) {
        this.type = type;
    }

    public void setType(int type) {
        setType(PoiType.values()[type]);
    }

    public PoiType getType() {
        return type;
    }

    public boolean hasName() {
        return !this.name.equals("");
    }

    public boolean hasId() {
        return this.id >= -1;
    }

    @NonNull
    public String toString() {
        return "\n\t\t[Room ID: " + id + "] [Room Name: " + name + "] [Room Name: " + type + "] [Room points: " + point + "]";
    }

    public boolean isReady() {
        return point != null;
    }

    public void print() {
        Log.d("Maps", id + " " + name + " " + type + " " + point);
    }
}
