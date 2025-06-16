package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.dto

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.ComplexWorldPoiType

enum class ComplexPoiTypeDto {
    FUSE_BOX,
    CAR_KEY, CABIN_KEY, BONE,
    MUSIC_BOX, HAUNTED_MIRROR, OUIJA_BOARD, SUMMONING_CIRCLE,
    TAROT_CARDS, VOODOO_DOLL, MONKEY_PAW_BASE, MONKEY_PAW_WISH,
    HIDING_LOCATION, MAIN_ENTRANCE,
    MATCH
}

fun ComplexPoiTypeDto.toDomain() = ComplexWorldPoiType.valueOf(name)