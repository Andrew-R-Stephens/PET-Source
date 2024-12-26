package com.tritiumgaming.phasmophobiaevidencepicker.data.model.maps.map

import com.google.gson.JsonSyntaxException
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.maps.io.factory.MapDesBlueprint
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.maps.io.factory.MapDesFactory

class MapListModel(
    worldMapDeserializer: MapDesBlueprint
) {

    private var mapModels: ArrayList<MapModel> = ArrayList()

    init {
        try { MapDesFactory.parseMinified(worldMapDeserializer, mapModels) }
        catch (e: JsonSyntaxException) {
            e.printStackTrace()
            mapModels.clear()
            MapDesFactory.parseUnMinified(worldMapDeserializer, mapModels)
        }
    }

    val shortenedMapNames: ArrayList<String>
        get() {
            val names = ArrayList<String>(mapModels.size)
            for (mapModel in mapModels) {
                names.add(mapModel.mapNameShort)
            }

            return names
        }

    fun getMapById(id: Int): MapModel? {
        for (m in mapModels) {
            if (m.mapId == id) {
                return m
            }
        }

        return null
    }

    @Synchronized
    fun print() {
        for (m in mapModels) {
            m.print()
        }
    }

    fun orderRooms() {
        for (m in mapModels) {
            m.orderRooms()
        }
    }
}
