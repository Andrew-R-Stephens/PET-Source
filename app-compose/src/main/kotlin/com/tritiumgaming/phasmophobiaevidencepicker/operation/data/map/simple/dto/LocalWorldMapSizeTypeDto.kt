package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.dto

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model.WorldMapSizeType

enum class WorldMapSizeTypeDto {
    SMALL, MEDIUM, LARGE
}

fun WorldMapSizeTypeDto.toDomain() = WorldMapSizeType.valueOf(name)