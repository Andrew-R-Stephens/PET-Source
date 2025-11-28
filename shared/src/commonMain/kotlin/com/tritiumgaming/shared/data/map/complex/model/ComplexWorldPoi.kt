package com.tritiumgaming.shared.data.map.complex.model

data class ComplexWorldPoi(
    val id: Int,
    val name: String,
    val type: com.tritiumgaming.shared.data.map.poi.mappers.MapPoiResources.Poi,
    val point: com.tritiumgaming.shared.data.map.complex.model.ComplexWorldPoint?
) {

    fun hasName(): Boolean = name.isNotEmpty()

    fun hasId(): Boolean = this.id >= -1

    val isReady: Boolean
        get() = point != null

    override fun toString(): String {
        return "\n\t\t[Room ID: $id] [Room Name: $name] [Room Name: $type] [Room points: $point]"
    }

}
