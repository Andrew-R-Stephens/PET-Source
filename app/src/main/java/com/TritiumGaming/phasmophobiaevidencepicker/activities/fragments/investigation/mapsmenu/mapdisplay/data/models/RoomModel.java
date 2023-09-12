package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.data.models;

import android.util.Log;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.io.models.WorldMapWrapper;

public class RoomModel {

    private int id = -1;
    private String name = "";

    private RoomArea roomArea = new RoomArea();

    public RoomModel() {
        setId(-1);
        setName("");
    }

    /**
     * Deep copy data from another RoomData
     * @param tempRoomData
     */
    public RoomModel(RoomModel tempRoomData) {
        if(tempRoomData == null) { return; }

        setId(tempRoomData.getId());
        setName(tempRoomData.getName());
        setRoomArea(tempRoomData.getRoomArea());
    }

    public void setRoomArea(RoomArea tempRoomArea) {
        this.roomArea = new RoomArea(tempRoomArea);
        System.out.println("Setting room area " + roomArea);
    }

    public RoomArea getRoomArea() {
        return roomArea;
    }

    public RoomModel(int id, String name, WorldMapWrapper.WorldMap.Floor.Room.RoomPoints points) {
        setId(id);
        setName(name);

        roomArea.setPoints(points);
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

    public boolean hasName() {
        return !this.name.equals("");
    }

    public boolean hasId() {
        return this.id >= -1;
    }

    public String toString() {
        return "\n\t\t[Room ID: " + id + "] [Room Name: " + name + "] [Room points: " + roomArea + "]";
    }

    public boolean isReady() {
        return roomArea != null && roomArea.getPoints().size() >= 3;
    }

    public synchronized void print() {
        Log.d("Maps", id + " " + name);
        roomArea.print();
    }
}
