package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.dto

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.ComplexWorldMapFloorLayerType

enum class ComplexFloorLayerTypeDto {
    BASEMENT, FIRST_FLOOR, SECOND_FLOOR, THIRD_FLOOR, // BETA RELEASE FLOORS
    FOURTH_FLOOR, FIFTH_FLOOR, SIXTH_FLOOR, SEVENTH_FLOOR, EIGHTH_FLOOR, NINTH_FLOOR,
    TENTH_FLOOR // POINT HOPE RELEASE FLOORS
}

fun ComplexFloorLayerTypeDto.toDomain() = ComplexWorldMapFloorLayerType.valueOf(name)