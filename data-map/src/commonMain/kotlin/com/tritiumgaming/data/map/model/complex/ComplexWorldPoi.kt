package com.tritiumgaming.data.map.model.complex

import com.tritiumgaming.data.map.mappers.MapPoiResources.Poi

data class ComplexWorldPoi(
    val id: Int,
    val name: String,
    val type: Poi,
    val point: ComplexWorldPoint?
) {

    //fun hasName(): Boolean = name.isNotEmpty()

    //fun hasId(): Boolean = this.id >= -1

    /*val isReady: Boolean
        get() = point != null*/

    override fun toString(): String {
        return "\n\t\t[Room ID: $id] [Room Name: $name] [Room Name: $type] [Room points: $point]"
    }

}
