package com.tritiumgaming.data.map.complex.dto

import com.tritiumgaming.shared.data.map.poi.mappers.MapPoiResources

enum class ComplexPoiTypeDto {
    FUSE_BOX,
    CAR_KEY, CABIN_KEY, BONE,
    MUSIC_BOX, HAUNTED_MIRROR, OUIJA_BOARD, SUMMONING_CIRCLE,
    TAROT_CARDS, VOODOO_DOLL, MONKEY_PAW_BASE, MONKEY_PAW_WISH,
    HIDING_LOCATION, MAIN_ENTRANCE,
    MATCH
}

fun ComplexPoiTypeDto.toDomain() = MapPoiResources.Poi.valueOf(name)