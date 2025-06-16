package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model

import android.graphics.PointF
import android.util.Log

data class ComplexWorldPoi(
    val id: Int,
    val name: String,
    val type: ComplexWorldPoiType,
    val point: PointF?
) {

    fun hasName(): Boolean = name.isNotEmpty()

    fun hasId(): Boolean = this.id >= -1

    val isReady: Boolean
        get() = point != null

    override fun toString(): String {
        return "\n\t\t[Room ID: $id] [Room Name: $name] [Room Name: $type] [Room points: $point]"
    }

    fun print() {
        Log.d("Maps", "$id $name $type $point")
    }
}
