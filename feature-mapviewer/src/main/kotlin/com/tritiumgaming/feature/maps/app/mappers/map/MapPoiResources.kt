package com.tritiumgaming.feature.maps.app.mappers.map

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.data.map.poi.mappers.MapPoiResources.Poi

@StringRes
fun Poi.toStringResource(): Int {
    return when (this) {
        Poi.FUSE_BOX -> 0
        Poi.CAR_KEY -> 0
        Poi.CABIN_KEY -> 0
        Poi.BONE -> 0
        Poi.MUSIC_BOX -> 0
        Poi.HAUNTED_MIRROR -> 0
        Poi.OUIJA_BOARD -> 0
        Poi.SUMMONING_CIRCLE -> 0
        Poi.TAROT_CARDS -> 0
        Poi.VOODOO_DOLL -> 0
        Poi.MONKEY_PAW_BASE -> 0
        Poi.MONKEY_PAW_WISH -> 0
        Poi.HIDING_LOCATION -> 0
        Poi.MAIN_ENTRANCE -> 0
        Poi.MATCH -> 0
    }
}

@DrawableRes
fun Poi.toDrawableResource(): Int {
    return when (this) {
        Poi.FUSE_BOX -> R.drawable.ic_map_power
        Poi.CAR_KEY -> R.drawable.ic_map_key_car
        Poi.CABIN_KEY -> R.drawable.ic_map_key_cabin
        Poi.BONE -> R.drawable.icon_difficulties
        Poi.MUSIC_BOX -> R.drawable.ic_map_cp_musicbox
        Poi.HAUNTED_MIRROR -> R.drawable.ic_map_cp_mirror
        Poi.OUIJA_BOARD -> R.drawable.ic_map_cp_ouija
        Poi.SUMMONING_CIRCLE -> R.drawable.ic_map_cp_candle
        Poi.TAROT_CARDS -> R.drawable.ic_map_cp_tarot
        Poi.VOODOO_DOLL -> R.drawable.ic_map_cp_doll
        Poi.MONKEY_PAW_BASE -> R.drawable.ic_map_cp_paw
        Poi.MONKEY_PAW_WISH -> R.drawable.ic_map_cp_pawwish
        Poi.HIDING_LOCATION -> R.drawable.icon_difficulties
        Poi.MAIN_ENTRANCE -> R.drawable.ic_map_truck
        Poi.MATCH -> R.drawable.icon_difficulties
    }
}
