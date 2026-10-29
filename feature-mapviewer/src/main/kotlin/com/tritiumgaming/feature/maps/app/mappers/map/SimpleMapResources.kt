package com.tritiumgaming.feature.maps.app.mappers.map

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.data.map.mappers.SimpleMapResources.MapFloorImage
import com.tritiumgaming.data.map.mappers.SimpleMapResources.MapFloorTitle
import com.tritiumgaming.data.map.mappers.SimpleMapResources.MapId
import com.tritiumgaming.data.map.mappers.SimpleMapResources.MapThumbnail
import com.tritiumgaming.data.map.mappers.SimpleMapResources.MapTitle
import com.tritiumgaming.data.map.mappers.SimpleMapResources.MapTitleLength
import com.tritiumgaming.feature.maps.R

@StringRes fun MapTitle.toStringResource(length: MapTitleLength = MapTitleLength.FULL): Int =
    when (length) {
        MapTitleLength.FULL -> this.toFullNameStringResource()
        MapTitleLength.ABBREVIATED -> this.toAbbreviatedNameStringResource()
    }

@StringRes private fun MapId.toStringResource(): Int =
    when (this) {
        MapId.SUNNY_MEADOWS -> com.tritiumgaming.core.resources.R.string.map_id_meadows
        MapId.SUNNY_MEADOWS_RESTRICTED -> com.tritiumgaming.core.resources.R.string.map_id_meadows_r
        MapId.POINT_HOPE -> com.tritiumgaming.core.resources.R.string.map_id_pointhope
        MapId.CAMP_WOODWIND -> com.tritiumgaming.core.resources.R.string.map_id_campwoodwind
        MapId.CAMP_MAPLE -> com.tritiumgaming.core.resources.R.string.map_id_campmaple
        MapId.BLEASDALE_FARMHOUSE -> com.tritiumgaming.core.resources.R.string.map_id_bleasdale
        MapId.GRAFTON_FARMHOUSE -> com.tritiumgaming.core.resources.R.string.map_id_grafton
        MapId.BROWNSTONE_HIGHSCHOOL -> com.tritiumgaming.core.resources.R.string.map_id_brownstone
        MapId.TANGLEWOOD -> com.tritiumgaming.core.resources.R.string.map_id_tanglewood
        MapId.EDGEFIELD -> com.tritiumgaming.core.resources.R.string.map_id_edgefield
        MapId.WILLOW -> com.tritiumgaming.core.resources.R.string.map_id_willow
        MapId.RIDGEVIEW -> com.tritiumgaming.core.resources.R.string.map_id_ridgeview
        MapId.PRISON -> com.tritiumgaming.core.resources.R.string.map_id_prison
        MapId.DINER -> com.tritiumgaming.core.resources.R.string.map_id_diner
    }

@StringRes private fun MapTitle.toFullNameStringResource(): Int {
    return when (this) {
        MapTitle.SUNNY_MEADOWS -> com.tritiumgaming.core.resources.R.string.map_name_full_meadows
        MapTitle.SUNNY_MEADOWS_RESTRICTED -> com.tritiumgaming.core.resources.R.string.map_name_full_meadows_r
        MapTitle.POINT_HOPE -> com.tritiumgaming.core.resources.R.string.map_name_full_pointhope
        MapTitle.CAMP_WOODWIND -> com.tritiumgaming.core.resources.R.string.map_name_full_campwoodwind
        MapTitle.CAMP_MAPLE -> com.tritiumgaming.core.resources.R.string.map_name_full_campmaple
        MapTitle.BLEASDALE_FARMHOUSE -> com.tritiumgaming.core.resources.R.string.map_name_full_bleasdale
        MapTitle.GRAFTON_FARMHOUSE -> com.tritiumgaming.core.resources.R.string.map_name_full_grafton
        MapTitle.BROWNSTONE_HIGHSCHOOL -> com.tritiumgaming.core.resources.R.string.map_name_full_brownstone
        MapTitle.TANGLEWOOD -> com.tritiumgaming.core.resources.R.string.map_name_full_tanglewood
        MapTitle.EDGEFIELD -> com.tritiumgaming.core.resources.R.string.map_name_full_edgefield
        MapTitle.WILLOW -> com.tritiumgaming.core.resources.R.string.map_name_full_willow
        MapTitle.RIDGEVIEW -> com.tritiumgaming.core.resources.R.string.map_name_full_ridgeview
        MapTitle.PRISON -> com.tritiumgaming.core.resources.R.string.map_name_full_prison
        MapTitle.DINER -> com.tritiumgaming.core.resources.R.string.map_name_full_diner
    }
}

@StringRes private fun MapTitle.toAbbreviatedNameStringResource(): Int {
    return when (this) {
        MapTitle.SUNNY_MEADOWS -> com.tritiumgaming.core.resources.R.string.map_name_short_meadows
        MapTitle.SUNNY_MEADOWS_RESTRICTED -> com.tritiumgaming.core.resources.R.string.map_name_short_meadows_r
        MapTitle.POINT_HOPE -> com.tritiumgaming.core.resources.R.string.map_name_short_pointhope
        MapTitle.CAMP_WOODWIND -> com.tritiumgaming.core.resources.R.string.map_name_short_campwoodwind
        MapTitle.CAMP_MAPLE -> com.tritiumgaming.core.resources.R.string.map_name_short_campmaple
        MapTitle.BLEASDALE_FARMHOUSE -> com.tritiumgaming.core.resources.R.string.map_name_short_bleasdale
        MapTitle.GRAFTON_FARMHOUSE -> com.tritiumgaming.core.resources.R.string.map_name_short_grafton
        MapTitle.BROWNSTONE_HIGHSCHOOL -> com.tritiumgaming.core.resources.R.string.map_name_short_brownstone
        MapTitle.TANGLEWOOD -> com.tritiumgaming.core.resources.R.string.map_name_short_tanglewood
        MapTitle.EDGEFIELD -> com.tritiumgaming.core.resources.R.string.map_name_short_edgefield
        MapTitle.WILLOW -> com.tritiumgaming.core.resources.R.string.map_name_short_willow
        MapTitle.RIDGEVIEW -> com.tritiumgaming.core.resources.R.string.map_name_short_ridgeview
        MapTitle.PRISON -> com.tritiumgaming.core.resources.R.string.map_name_short_prison
        MapTitle.DINER -> com.tritiumgaming.core.resources.R.string.map_name_short_diner
    }
}

@StringRes fun MapFloorTitle.toStringResource(): Int {
    return when (this) {
        MapFloorTitle.BASEMENT -> com.tritiumgaming.core.resources.R.string.map_floor_name_basement
        MapFloorTitle.GROUND_FLOOR -> com.tritiumgaming.core.resources.R.string.map_floor_name_ground
        MapFloorTitle.FIRST_FLOOR -> com.tritiumgaming.core.resources.R.string.map_floor_name_first
        MapFloorTitle.SECOND_FLOOR -> com.tritiumgaming.core.resources.R.string.map_floor_name_second
        MapFloorTitle.THIRD_FLOOR -> com.tritiumgaming.core.resources.R.string.map_floor_name_third
        MapFloorTitle.FOURTH_FLOOR -> com.tritiumgaming.core.resources.R.string.map_floor_name_fourth
        MapFloorTitle.FIFTH_FLOOR -> com.tritiumgaming.core.resources.R.string.map_floor_name_fifth
        MapFloorTitle.SIXTH_FLOOR -> com.tritiumgaming.core.resources.R.string.map_floor_name_sixth
        MapFloorTitle.SEVENTH_FLOOR -> com.tritiumgaming.core.resources.R.string.map_floor_name_seventh
        MapFloorTitle.EIGHTH_FLOOR -> com.tritiumgaming.core.resources.R.string.map_floor_name_eighth
        MapFloorTitle.NINTH_FLOOR -> com.tritiumgaming.core.resources.R.string.map_floor_name_ninth
        MapFloorTitle.TENTH_FLOOR -> com.tritiumgaming.core.resources.R.string.map_floor_name_tenth
        MapFloorTitle.ATTIC -> com.tritiumgaming.core.resources.R.string.map_floor_name_attic
    }
}

@DrawableRes fun MapThumbnail.toDrawableResource(): Int {
    return when (this) {
        MapThumbnail.SUNNY_MEADOWS -> R.drawable.thumbnail_meadows
        MapThumbnail.SUNNY_MEADOWS_RESTRICTED -> R.drawable.thumbnail_meadows
        MapThumbnail.POINT_HOPE -> R.drawable.thumbnail_pointhope
        MapThumbnail.CAMP_WOODWIND -> R.drawable.thumbnail_woodwind
        MapThumbnail.CAMP_MAPLE -> R.drawable.thumbnail_campmaple
        MapThumbnail.BLEASDALE_FARMHOUSE -> R.drawable.thumbnail_bleasedale
        MapThumbnail.GRAFTON_FARMHOUSE -> R.drawable.thumbnail_grafton
        MapThumbnail.BROWNSTONE_HIGHSCHOOL -> R.drawable.thumbnail_highschool
        MapThumbnail.TANGLEWOOD -> R.drawable.thumbnail_tanglewood
        MapThumbnail.EDGEFIELD -> R.drawable.thumbnail_edgefield
        MapThumbnail.WILLOW -> R.drawable.thumbnail_willow
        MapThumbnail.RIDGEVIEW -> R.drawable.thumbnail_ridgeview
        MapThumbnail.PRISON -> R.drawable.thumbnail_prison
        MapThumbnail.DINER -> R.drawable.thumbnail_diner
    }
}

@DrawableRes fun MapFloorImage.toDrawableResource(): Int {
    return when (this) {
        MapFloorImage.SUNNY_MEADOWS_BASEMENT -> R.drawable.map_sunnymeadows_basement
        MapFloorImage.SUNNY_MEADOWS_FIRST_FLOOR -> R.drawable.map_sunnymeadows_firstfloor
        MapFloorImage.SUNNY_MEADOWS_RESTRICTED_BASEMENT -> R.drawable.map_sunnymeadowsrestricted_basement
        MapFloorImage.SUNNY_MEADOWS_RESTRICTED_FIRST_FLOOR -> R.drawable.map_sunnymeadowsrestricted_firstfloor
        MapFloorImage.POINT_HOPE_GROUND_FLOOR -> R.drawable.map_pointhope_firstfloor
        MapFloorImage.POINT_HOPE_SECOND_FLOOR -> R.drawable.map_pointhope_secondfloor
        MapFloorImage.POINT_HOPE_THIRD_FLOOR -> R.drawable.map_pointhope_thirdfloor
        MapFloorImage.POINT_HOPE_FOURTH_FLOOR -> R.drawable.map_pointhope_fourthfloor
        MapFloorImage.POINT_HOPE_FIFTH_FLOOR -> R.drawable.map_pointhope_fifthfloor
        MapFloorImage.POINT_HOPE_SIXTH_FLOOR -> R.drawable.map_pointhope_sixthfloor
        MapFloorImage.POINT_HOPE_SEVENTH_FLOOR -> R.drawable.map_pointhope_seventhfloor
        MapFloorImage.POINT_HOPE_EIGHTH_FLOOR -> R.drawable.map_pointhope_eighthfloor
        MapFloorImage.POINT_HOPE_NINTH_FLOOR -> R.drawable.map_pointhope_ninthfloor
        MapFloorImage.POINT_HOPE_TENTH_FLOOR -> R.drawable.map_pointhope_tenthfloor
        MapFloorImage.CAMP_WOODWIND_GROUND_FLOOR -> R.drawable.map_woodwind_firstfloor
        MapFloorImage.CAMP_MAPLE_BASEMENT -> R.drawable.map_maple_basement
        MapFloorImage.CAMP_MAPLE_FIRST_FLOOR -> R.drawable.map_maple_firstfloor
        MapFloorImage.CAMP_MAPLE_SECOND_FLOOR -> R.drawable.map_maple_secondfloor
        MapFloorImage.BLEASDALE_FARMHOUSE_FIRST_FLOOR -> R.drawable.map_bleasdale_firstfloor
        MapFloorImage.BLEASDALE_FARMHOUSE_SECOND_FLOOR -> R.drawable.map_bleasdale_secondfloor
        MapFloorImage.BLEASDALE_FARMHOUSE_ATTIC -> R.drawable.map_bleasdale_thirdfloor
        MapFloorImage.GRAFTON_FARMHOUSE_FIRST_FLOOR -> R.drawable.map_grafton_firstfloor
        MapFloorImage.GRAFTON_FARMHOUSE_SECOND_FLOOR -> R.drawable.map_grafton_secondfloor
        MapFloorImage.GRAFTON_FARMHOUSE_THIRD_FLOOR -> R.drawable.map_grafton_thirdfloor
        MapFloorImage.BROWNSTONE_HIGHSCHOOL_FIRST_FLOOR -> R.drawable.map_brownstone_firstfloor
        MapFloorImage.BROWNSTONE_HIGHSCHOOL_SECOND_FLOOR -> R.drawable.map_brownstone_secondfloor
        MapFloorImage.EDGEFIELD_BASEMENT -> R.drawable.map_edgefield_basement
        MapFloorImage.EDGEFIELD_FIRST_FLOOR -> R.drawable.map_edgefield_firstfloor
        MapFloorImage.EDGEFIELD_SECOND_FLOOR -> R.drawable.map_edgefield_secondfloor
        MapFloorImage.TANGLEWOOD_BASEMENT -> R.drawable.map_tanglewood_basement
        MapFloorImage.TANGLEWOOD_FIRST_FLOOR -> R.drawable.map_tanglewood_firstfloor
        MapFloorImage.WILLOW_BASEMENT -> R.drawable.map_willow_basement
        MapFloorImage.WILLOW_FIRST_FLOOR -> R.drawable.map_willow_firstfloor
        MapFloorImage.RIDGEVIEW_BASEMENT -> R.drawable.map_ridgeview_basement
        MapFloorImage.RIDGEVIEW_FIRST_FLOOR -> R.drawable.map_ridgeview_firstfloor
        MapFloorImage.RIDGEVIEW_SECOND_FLOOR -> R.drawable.map_ridgeview_secondfloor
        MapFloorImage.PRISON_FIRST_FLOOR -> R.drawable.map_prison_firstfloor
        MapFloorImage.PRISON_SECOND_FLOOR -> R.drawable.map_prison_secondfloor
        MapFloorImage.DINER_FIRST_FLOOR -> R.drawable.map_diner_firstfloor
    }

}