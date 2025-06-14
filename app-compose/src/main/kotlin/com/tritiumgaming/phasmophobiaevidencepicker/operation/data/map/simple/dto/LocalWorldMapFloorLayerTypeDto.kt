package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.dto

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model.WorldMapFloorLayerType

enum class LocalWorldFloorLayerTypeDto {
    BASEMENT, FIRST_FLOOR, SECOND_FLOOR, THIRD_FLOOR,
    FOURTH_FLOOR, FIFTH_FLOOR, SIXTH_FLOOR, SEVENTH_FLOOR, EIGHTH_FLOOR, NINTH_FLOOR, TENTH_FLOOR
}

fun LocalWorldFloorLayerTypeDto.toDomain() = WorldMapFloorLayerType.valueOf(name)