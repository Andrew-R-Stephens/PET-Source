package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.mappers.SimpleMapResources

@StringRes fun SimpleMapResources.MapTitle.toStringResource(): Int {
    return when (this) {
        SimpleMapResources.MapTitle.SUNNY_MEADOWS -> R.string.map_name_meadows
        SimpleMapResources.MapTitle.SUNNY_MEADOWS_RESTRICTED -> R.string.map_name_meadows_r
        SimpleMapResources.MapTitle.POINT_HOPE -> R.string.map_name_pointhope
        SimpleMapResources.MapTitle.CAMP_WOODWIND -> R.string.map_name_campwoodwind
        SimpleMapResources.MapTitle.CAMP_MAPLE -> R.string.map_name_campmaple
        SimpleMapResources.MapTitle.BLEASDALE_FARMHOUSE -> R.string.map_name_bleasdale
        SimpleMapResources.MapTitle.GRAFTON_FARMHOUSE -> R.string.map_name_grafton
        SimpleMapResources.MapTitle.BROWNSTONE_HIGHSCHOOL -> R.string.map_name_brownstone
        SimpleMapResources.MapTitle.TANGLEWOOD -> R.string.map_name_tanglewood
        SimpleMapResources.MapTitle.EDGEFIELD -> R.string.map_name_edgefield
        SimpleMapResources.MapTitle.WILLOW -> R.string.map_name_willow
        SimpleMapResources.MapTitle.RIDGEVIEW -> R.string.map_name_ridgeview
        SimpleMapResources.MapTitle.PRISON -> R.string.map_name_prison
    }
}

@DrawableRes fun SimpleMapResources.MapThumbnail.toDrawableResource(): Int {
    return when (this) {
        SimpleMapResources.MapThumbnail.SUNNY_MEADOWS -> R.drawable.thumbnail_meadows
        SimpleMapResources.MapThumbnail.SUNNY_MEADOWS_RESTRICTED -> R.drawable.thumbnail_meadows
        SimpleMapResources.MapThumbnail.POINT_HOPE -> R.drawable.thumbnail_pointhope
        SimpleMapResources.MapThumbnail.CAMP_WOODWIND -> R.drawable.thumbnail_campmaple
        SimpleMapResources.MapThumbnail.CAMP_MAPLE -> R.drawable.thumbnail_campmaple
        SimpleMapResources.MapThumbnail.BLEASDALE_FARMHOUSE -> R.drawable.thumbnail_bleasedale
        SimpleMapResources.MapThumbnail.GRAFTON_FARMHOUSE -> R.drawable.thumbnail_grafton
        SimpleMapResources.MapThumbnail.BROWNSTONE_HIGHSCHOOL -> R.drawable.thumbnail_highschool
        SimpleMapResources.MapThumbnail.TANGLEWOOD -> R.drawable.thumbnail_tanglewood
        SimpleMapResources.MapThumbnail.EDGEFIELD -> R.drawable.thumbnail_edgefield
        SimpleMapResources.MapThumbnail.WILLOW -> R.drawable.thumbnail_willow
        SimpleMapResources.MapThumbnail.RIDGEVIEW -> R.drawable.thumbnail_ridgeview
        SimpleMapResources.MapThumbnail.PRISON -> R.drawable.thumbnail_prison
    }
}

@StringRes fun SimpleMapResources.MapFloorTitle.toStringResource(): Int {
    return when (this) {
        SimpleMapResources.MapFloorTitle.BASEMENT -> R.string.map_floor_name_basement
        SimpleMapResources.MapFloorTitle.GROUND_FLOOR -> R.string.map_floor_name_ground
        SimpleMapResources.MapFloorTitle.FIRST_FLOOR -> R.string.map_floor_name_first
        SimpleMapResources.MapFloorTitle.SECOND_FLOOR -> R.string.map_floor_name_second
        SimpleMapResources.MapFloorTitle.THIRD_FLOOR -> R.string.map_floor_name_third
        SimpleMapResources.MapFloorTitle.FOURTH_FLOOR -> R.string.map_floor_name_fourth
        SimpleMapResources.MapFloorTitle.FIFTH_FLOOR -> R.string.map_floor_name_fifth
        SimpleMapResources.MapFloorTitle.SIXTH_FLOOR -> R.string.map_floor_name_sixth
        SimpleMapResources.MapFloorTitle.SEVENTH_FLOOR -> R.string.map_floor_name_seventh
        SimpleMapResources.MapFloorTitle.EIGHTH_FLOOR -> R.string.map_floor_name_eighth
        SimpleMapResources.MapFloorTitle.NINTH_FLOOR -> R.string.map_floor_name_ninth
        SimpleMapResources.MapFloorTitle.TENTH_FLOOR -> R.string.map_floor_name_tenth
        SimpleMapResources.MapFloorTitle.ATTIC -> R.string.map_floor_name_attic
    }
}

@DrawableRes fun SimpleMapResources.MapFloorImage.toDrawableResource(): Int {
    return when (this) {
        SimpleMapResources.MapFloorImage.SUNNY_MEADOWS_BASEMENT -> R.drawable.map_sunnymeadows_basement
        SimpleMapResources.MapFloorImage.SUNNY_MEADOWS_FIRST_FLOOR -> R.drawable.map_sunnymeadows_firstfloor
        SimpleMapResources.MapFloorImage.SUNNY_MEADOWS_RESTRICTED_BASEMENT -> R.drawable.map_sunnymeadowsrestricted_basement
        SimpleMapResources.MapFloorImage.SUNNY_MEADOWS_RESTRICTED_FIRST_FLOOR -> R.drawable.map_sunnymeadowsrestricted_firstfloor
        SimpleMapResources.MapFloorImage.POINT_HOPE_GROUND_FLOOR -> R.drawable.map_pointhope_firstfloor
        SimpleMapResources.MapFloorImage.POINT_HOPE_SECOND_FLOOR -> R.drawable.map_pointhope_secondfloor
        SimpleMapResources.MapFloorImage.POINT_HOPE_THIRD_FLOOR -> R.drawable.map_pointhope_thirdfloor
        SimpleMapResources.MapFloorImage.POINT_HOPE_FOURTH_FLOOR -> R.drawable.map_pointhope_fourthfloor
        SimpleMapResources.MapFloorImage.POINT_HOPE_FIFTH_FLOOR -> R.drawable.map_pointhope_fifthfloor
        SimpleMapResources.MapFloorImage.POINT_HOPE_SIXTH_FLOOR -> R.drawable.map_pointhope_sixthfloor
        SimpleMapResources.MapFloorImage.POINT_HOPE_SEVENTH_FLOOR -> R.drawable.map_pointhope_seventhfloor
        SimpleMapResources.MapFloorImage.POINT_HOPE_EIGHTH_FLOOR -> R.drawable.map_pointhope_eighthfloor
        SimpleMapResources.MapFloorImage.POINT_HOPE_NINTH_FLOOR -> R.drawable.map_pointhope_ninthfloor
        SimpleMapResources.MapFloorImage.POINT_HOPE_TENTH_FLOOR -> R.drawable.map_pointhope_tenthfloor
        SimpleMapResources.MapFloorImage.CAMP_WOODWIND_GROUND_FLOOR -> R.drawable.map_woodwind_firstfloor
        SimpleMapResources.MapFloorImage.CAMP_MAPLE_BASEMENT -> R.drawable.map_maple_basement
        SimpleMapResources.MapFloorImage.CAMP_MAPLE_FIRST_FLOOR -> R.drawable.map_maple_firstfloor
        SimpleMapResources.MapFloorImage.CAMP_MAPLE_SECOND_FLOOR -> R.drawable.map_maple_secondfloor
        SimpleMapResources.MapFloorImage.BLEASDALE_FARMHOUSE_FIRST_FLOOR -> R.drawable.map_bleasdale_firstfloor
        SimpleMapResources.MapFloorImage.BLEASDALE_FARMHOUSE_SECOND_FLOOR -> R.drawable.map_bleasdale_secondfloor
        SimpleMapResources.MapFloorImage.BLEASDALE_FARMHOUSE_ATTIC -> R.drawable.map_bleasdale_thirdfloor
        SimpleMapResources.MapFloorImage.GRAFTON_FARMHOUSE_FIRST_FLOOR -> R.drawable.map_grafton_firstfloor
        SimpleMapResources.MapFloorImage.GRAFTON_FARMHOUSE_SECOND_FLOOR -> R.drawable.map_grafton_secondfloor
        SimpleMapResources.MapFloorImage.BROWNSTONE_HIGHSCHOOL_FIRST_FLOOR -> R.drawable.map_brownstone_firstfloor
        SimpleMapResources.MapFloorImage.BROWNSTONE_HIGHSCHOOL_SECOND_FLOOR -> R.drawable.map_brownstone_secondfloor
        SimpleMapResources.MapFloorImage.EDGEFIELD_BASEMENT -> R.drawable.map_edgefield_basement
        SimpleMapResources.MapFloorImage.EDGEFIELD_FIRST_FLOOR -> R.drawable.map_edgefield_firstfloor
        SimpleMapResources.MapFloorImage.EDGEFIELD_SECOND_FLOOR -> R.drawable.map_edgefield_secondfloor
        SimpleMapResources.MapFloorImage.TANGLEWOOD_BASEMENT -> R.drawable.map_tanglewood_basement
        SimpleMapResources.MapFloorImage.TANGLEWOOD_FIRST_FLOOR -> R.drawable.map_tanglewood_firstfloor
        SimpleMapResources.MapFloorImage.WILLOW_BASEMENT -> R.drawable.map_willow_basement
        SimpleMapResources.MapFloorImage.WILLOW_FIRST_FLOOR -> R.drawable.map_willow_firstfloor
        SimpleMapResources.MapFloorImage.RIDGEVIEW_BASEMENT -> R.drawable.map_ridgeview_basement
        SimpleMapResources.MapFloorImage.RIDGEVIEW_FIRST_FLOOR -> R.drawable.map_ridgeview_firstfloor
        SimpleMapResources.MapFloorImage.RIDGEVIEW_SECOND_FLOOR -> R.drawable.map_ridgeview_secondfloor
        SimpleMapResources.MapFloorImage.PRISON_FIRST_FLOOR -> R.drawable.map_prison_firstfloor
        SimpleMapResources.MapFloorImage.PRISON_SECOND_FLOOR -> R.drawable.map_prison_secondfloor
    }

}