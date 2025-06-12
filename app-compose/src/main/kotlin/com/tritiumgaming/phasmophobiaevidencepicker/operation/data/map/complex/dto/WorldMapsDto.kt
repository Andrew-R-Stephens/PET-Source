package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.dto

class WorldMapsDto() {

    private var mapModels: ArrayList<WorldMapDto> = ArrayList()

    fun addMapModel(model: WorldMapDto) {
        mapModels.add(model)
    }

    val shortenedMapNames: ArrayList<String>
        get() {
            val names = ArrayList<String>(mapModels.size)
            for (mapModel in mapModels) {
                names.add(mapModel.mapNameShort)
            }

            return names
        }

    fun getMapById(id: String): WorldMapDto? {
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
