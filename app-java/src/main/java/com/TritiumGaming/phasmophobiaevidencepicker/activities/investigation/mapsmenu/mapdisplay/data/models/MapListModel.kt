package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.data.models

import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.io.models.MapDesBlueprint
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.io.models.MapDesFactory
import com.google.gson.JsonSyntaxException

class MapListModel(worldMapDeserializer: MapDesBlueprint) {
    var mapModels: ArrayList<MapModel> = ArrayList()

    init {
        try {
            MapDesFactory.parseMinified(worldMapDeserializer, mapModels)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
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
