package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.dto

import android.graphics.PointF
import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.worldmaps.Poi

class PoiDto(
    var id: Int,
    var name: String,
    var type: PoiTypeDto,
    var point: PointF
) {

    constructor(id: Int, name: String, type: Int, x: Float, y: Float) : this(
        id = id,
        name = name,
        type = PoiTypeDto.entries[type],
        point = PointF(x, y)
    )

    override fun toString(): String {
        return "\n\t\t[Room ID: $id] [Room Name: $name] [Room Name: $type] [Room points: $point]"
    }

    fun print() {
        Log.d("Maps", "$id $name $type $point")
    }
}

fun List<PoiDto>.toDomain() = map { poiDto -> poiDto.toDomain() }

fun PoiDto.toDomain() = Poi(
    id = id,
    name = name,
    type = type.toDomain(),
    point = point
)
