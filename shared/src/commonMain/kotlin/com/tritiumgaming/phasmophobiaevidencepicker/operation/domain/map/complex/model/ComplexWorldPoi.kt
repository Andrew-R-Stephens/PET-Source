package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.poi.mappers.MapPoiResources

data class ComplexWorldPoi(
    val id: Int,
    val name: String,
    val type: MapPoiResources.Poi,
    val point: ComplexWorldPoint?
) {

    fun hasName(): Boolean = name.isNotEmpty()

    fun hasId(): Boolean = this.id >= -1

    val isReady: Boolean
        get() = point != null

    override fun toString(): String {
        return "\n\t\t[Room ID: $id] [Room Name: $name] [Room Name: $type] [Room points: $point]"
    }

}
