package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.dto

import android.graphics.PointF
import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.ComplexWorldPoi

data class ComplexPoiDto(
    var id: Int,
    var name: String,
    var type: ComplexPoiTypeDto,
    var point: PointF
) {

    constructor(id: Int, name: String, type: Int, x: Float, y: Float) : this(
        id = id,
        name = name,
        type = ComplexPoiTypeDto.entries[type],
        point = PointF(x, y)
    )

    override fun toString(): String {
        return "\n\t\t[Room ID: $id] [Room Name: $name] [Room Name: $type] [Room points: $point]"
    }

    fun print() {
        Log.d("Maps", "$id $name $type $point")
    }
}

fun List<ComplexPoiDto>.toDomain() = map { poiDto -> poiDto.toDomain() }

fun ComplexPoiDto.toDomain() = ComplexWorldPoi(
    id = id,
    name = name,
    type = type.toDomain(),
    point = point
)
