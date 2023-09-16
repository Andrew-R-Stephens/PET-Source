package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.data.models;

import android.util.Log;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.io.models.WorldMapWrapper;

public class RoomModel {

    private int id = -1;
    private String name = "";

    private RoomArea roomArea = new RoomArea();

    public RoomArea getRoomArea() {
        return roomArea;
    }

    public RoomModel(int id, String name, WorldMapWrapper.WorldMap.Floor.Room.RoomPoints points) {
        setId(id);
        setName(name);

        roomArea.setPoints(points);
    }

    public RoomModel(WorldMapWrapper.WorldMap.Floor.Room room) {
        setId(room.room_iD);
        setName(room.room_name);

        roomArea.setPoints(room.room_points);
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

    @Override
    public String toString() {
        return "\n\t\t[Room ID: " + id + "] [Room Name: " + name + "] [Room points: " + roomArea + "]";
    }

    public synchronized void print() {
        Log.d("Maps", id + " " + name);
        roomArea.print();
    }
}
