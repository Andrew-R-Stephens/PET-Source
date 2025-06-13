package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.dto

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.worldmaps.FloorLayerType

enum class FloorLayerTypeDto {
    BASEMENT, FIRST_FLOOR, SECOND_FLOOR, THIRD_FLOOR, // BETA RELEASE FLOORS
    FOURTH_FLOOR, FIFTH_FLOOR, SIXTH_FLOOR, SEVENTH_FLOOR, EIGHTH_FLOOR, NINTH_FLOOR,
    TENTH_FLOOR // POINT HOPE RELEASE FLOORS
}

fun FloorLayerTypeDto.toDomain() = FloorLayerType.valueOf(name)