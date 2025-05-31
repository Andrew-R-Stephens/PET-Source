package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.worldmaps

class MapListModel() {

    private var mapModels: ArrayList<MapModel> = ArrayList()

    fun addMapModel(model: MapModel) {
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

    fun getMapById(id: String): MapModel? {
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
