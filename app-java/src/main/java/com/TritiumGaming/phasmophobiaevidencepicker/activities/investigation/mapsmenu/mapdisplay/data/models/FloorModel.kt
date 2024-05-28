package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.data.models;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.io.models.WorldMapWrapper;

import java.util.ArrayList;
import java.util.Comparator;

public class FloorModel {

    public int floorId;
    private String floorName;
    private String floorImage;

    private FloorLayer floorLayer;

    private final ArrayList<RoomModel> floorRooms = new ArrayList<>();
    private final ArrayList<PoiModel> floorPOIs = new ArrayList<>();

    public FloorModel(@NonNull WorldMapWrapper.WorldMap.Floor floor) {
        floorImage = floor.image_file;
        floorId = floor.floor_id;
        floorName = floor.floor_name;
        floorLayer = FloorLayer.getEntries().get(floor.floor_number);

        for(WorldMapWrapper.WorldMap.Floor.Room r: floor.floor_rooms) {
            floorRooms.add(new RoomModel(r.room_iD, r.room_name, r.room_points));
        }
        for(WorldMapWrapper.WorldMap.Floor.POI p: floor.floor_pois) {
            floorPOIs.add(new PoiModel(p.poi_iD, p.poi_name, p.poi_type, p.x, p.y));
        }
    }

    public FloorModel(FloorLayer layer) {
        this.floorLayer = layer;
    }

    public void setFloorLayer(FloorLayer layer) {
        this.floorLayer = layer;
    }

    @NonNull
    public ArrayList<RoomModel> getFloorRooms() {
        return floorRooms;
    }

    @NonNull
    public ArrayList<String> getFloorRoomNames() {
        ArrayList<String> names = new ArrayList<>();
        for(RoomModel r: getFloorRooms()) {
            names.add(r.getName());
        }
        return names;
    }

    public String getFloorName() {
        return floorName;
    }

    @Nullable
    public RoomModel getLastRoom() {
        if(floorRooms.isEmpty()) {
            return null;
        }
        return floorRooms.get(floorRooms.size()-1);
    }

    @Nullable
    public RoomModel getRoomById(int id) {
        for(RoomModel room: floorRooms) {
            if(room.id == id)
                return room;
        }

        return null;
    }

    public int getRoomIndexById(int id) {
        int i = 0;
        for(; i < floorRooms.size(); i++) {
            if(floorRooms.get(i).id == id)
                return i;
        }
        return ++i;
    }

    public FloorLayer getFloorLayer() {
        return floorLayer;
    }

    @NonNull
    public ArrayList<PoiModel> getFloorPOIs() {
        return floorPOIs;
    }

    @NonNull
    public String toString() {
        return "\n\t[Floor ID: " + floorId + "] [Floor Name: " + floorName + "] [ Assigned Layer: " + floorLayer + " ] [Image File: "  + floorImage + "] Rooms:"  + floorRooms + "\n";
    }

    public synchronized void print() {
        Log.d("Maps", "[Floor ID: " + floorId + "] [Floor Name: " + floorName + "] [ Assigned Layer: " + floorLayer + " ] [Image File: "  + floorImage + "]");
        for(RoomModel r: floorRooms) {
            r.print();
        }

        for(PoiModel p: floorPOIs) {
            p.print();
        }
    }

    public void orderRooms() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            floorRooms.sort(Comparator.comparing(RoomModel::getName));
        }
    }

    public void addRoomModels(@NonNull ArrayList<RoomModel> roomModels) {
        this.floorRooms.addAll(roomModels);
    }
}
