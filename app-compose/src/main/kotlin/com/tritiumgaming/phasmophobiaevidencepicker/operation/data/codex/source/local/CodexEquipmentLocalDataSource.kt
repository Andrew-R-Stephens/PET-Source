package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.dto.CodexEquipmentGroupDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.dto.CodexEquipmentGroupItemDto
import com.tritiumgaming.phasmophobiaevidencepicker.R
import kotlin.collections.List

class CodexEquipmentLocalDataSource(
    val applicationContext: Context
) {

    private val equipmentResources: List<CodexEquipmentGroupRes>
        get() = listOf(
            CodexEquipmentGroupRes(
                name = R.string.equipment_info_name_dots,
                icon = R.drawable.icon_sh_dotsprojector,
                buyCostData = R.integer.equipment_requirement_buycost_dots,
                items = listOf(
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_dots_1,
                        flavor = R.string.shop_equipment_dots_data_flavortext_1,
                        info = R.string.shop_equipment_dots_data_info_1,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_dots_1,
                        upgradeLevelData = R.integer.equipment_requirement_level_dots_1,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_spotight_narrow,
                            R.string.shop_equipment_attribute_range_5,
                            R.string.shop_equipment_attribute_handheld
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_electronic
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_dots_2,
                        flavor = R.string.shop_equipment_dots_data_flavortext_2,
                        info = R.string.shop_equipment_dots_data_info_2,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_dots_2,
                        upgradeLevelData = R.integer.equipment_requirement_level_dots_2,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_spotight_area,
                            R.string.shop_equipment_attribute_range_2_5,
                            R.string.shop_equipment_attribute_placeable
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_electronic
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_dots_3,
                        flavor = R.string.shop_equipment_dots_data_flavortext_3,
                        info = R.string.shop_equipment_dots_data_info_3,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_dots_3,
                        upgradeLevelData = R.integer.equipment_requirement_level_dots_3,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_spotight_wide,
                            R.string.shop_equipment_attribute_range_7,
                            R.string.shop_equipment_attribute_placeable,
                            R.string.shop_equipment_attribute_property_scanning
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_electronic
                        )
                    )
                )
            ),
            // EMF
            CodexEquipmentGroupRes(
                name = R.string.equipment_info_name_emf,
                icon = R.drawable.icon_sh_emf,
                buyCostData = R.integer.equipment_requirement_buycost_emf,
                items = listOf(
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_emf_1,
                        flavor = R.string.shop_equipment_emf_data_flavortext_1,
                        info = R.string.shop_equipment_emf_data_info_1,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_emf_1,
                        upgradeLevelData = R.integer.equipment_requirement_level_emf_1,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_range_1_7,
                            R.string.shop_equipment_attribute_accuracy_low
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_electronic
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_emf_2,
                        flavor = R.string.shop_equipment_emf_data_flavortext_2,
                        info = R.string.shop_equipment_emf_data_info_2,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_emf_2,
                        upgradeLevelData = R.integer.equipment_requirement_level_emf_2,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_range_1_7,
                            R.string.shop_equipment_attribute_accuracy_high,
                            R.string.shop_equipment_attribute_indicator_audio
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_electronic
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_emf_3,
                        flavor = R.string.shop_equipment_emf_data_flavortext_3,
                        info = R.string.shop_equipment_emf_data_info_3,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_emf_3,
                        upgradeLevelData = R.integer.equipment_requirement_level_emf_3,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_range_3_5,
                            R.string.shop_equipment_attribute_accuracy_high,
                            R.string.shop_equipment_attribute_property_displayscreen,
                            R.string.shop_equipment_attribute_indicator_audio,
                            R.string.shop_equipment_attribute_indicator_distance,
                            R.string.shop_equipment_attribute_indicator_directional
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_electronic
                        )
                    )
                )
            ),
            // Flashlight
            CodexEquipmentGroupRes(
                name = R.string.equipment_info_name_flashlight,
                icon = R.drawable.icon_sh_flashlight,
                buyCostData = R.integer.equipment_requirement_buycost_flashlight,
                items = listOf(
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_flashlight_1,
                        flavor = R.string.shop_equipment_flashlight_data_flavortext_1,
                        info = R.string.shop_equipment_flashlight_data_info_1,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_flashlight_1,
                        upgradeLevelData = R.integer.equipment_requirement_level_flashlight_1,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_spotight_narrow
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_intensity_low,
                            R.string.shop_equipment_attribute_property_electronic
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_flashlight_2,
                        flavor = R.string.shop_equipment_flashlight_data_flavortext_2,
                        info = R.string.shop_equipment_flashlight_data_info_2,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_flashlight_2,
                        upgradeLevelData = R.integer.equipment_requirement_level_flashlight_2,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_spotight_narrow,
                            R.string.shop_equipment_attribute_intensity_med
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_electronic
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_flashlight_3,
                        flavor = R.string.shop_equipment_flashlight_data_flavortext_3,
                        info = R.string.shop_equipment_flashlight_data_info_3,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_flashlight_3,
                        upgradeLevelData = R.integer.equipment_requirement_level_flashlight_3,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_spotight_wide,
                            R.string.shop_equipment_attribute_intensity_high
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_electronic
                        )
                    )
                )
            ),
            // Photo Camera
            CodexEquipmentGroupRes(
                name = R.string.equipment_info_name_photocamera,
                icon = R.drawable.icon_sh_photocamera,
                buyCostData = R.integer.equipment_requirement_buycost_photocamera,
                items = listOf(
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_photocam_1,
                        flavor = R.string.shop_equipment_photocamera_data_flavortext_1,
                        info = R.string.shop_equipment_photocamera_data_info_1,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_photocamera_1,
                        upgradeLevelData = R.integer.equipment_requirement_level_photocamera_1,
                        positiveAttributes = listOf(/* NONE */),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_phototime_high
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_photocam_2,
                        flavor = R.string.shop_equipment_photocamera_data_flavortext_2,
                        info = R.string.shop_equipment_photocamera_data_info_2,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_photocamera_2,
                        upgradeLevelData = R.integer.equipment_requirement_level_photocamera_2,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_phototime_med,
                            R.string.shop_equipment_attribute_property_displayscreen
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_interference_med,
                            R.string.shop_equipment_attribute_property_electronic,
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_photocam_3,
                        flavor = R.string.shop_equipment_photocamera_data_flavortext_3,
                        info = R.string.shop_equipment_photocamera_data_info_3,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_photocamera_3,
                        upgradeLevelData = R.integer.equipment_requirement_level_photocamera_3,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_phototime_low,
                            R.string.shop_equipment_attribute_property_displayscreen
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_interference_med,
                            R.string.shop_equipment_attribute_property_electronic,
                        )
                    )
                )
            ),
            // Igniter
            CodexEquipmentGroupRes(
                name = R.string.equipment_info_name_igniter,
                icon = R.drawable.icon_sh_igniter,
                buyCostData = R.integer.equipment_requirement_buycost_igniter,
                items = listOf(
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_igniter_1,
                        flavor = R.string.shop_equipment_igniter_data_flavortext_1,
                        info = R.string.shop_equipment_igniter_data_info_1,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_igniter_1,
                        upgradeLevelData = R.integer.equipment_requirement_level_igniter_1,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_duration_10_s,
                            R.string.shop_equipment_attribute_uses_10
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_consumable
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_igniter_2,
                        flavor = R.string.shop_equipment_igniter_data_flavortext_2,
                        info = R.string.shop_equipment_igniter_data_info_2,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_igniter_2,
                        upgradeLevelData = R.integer.equipment_requirement_level_igniter_2,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_duration_5_m
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_consumable
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_igniter_3,
                        flavor = R.string.shop_equipment_igniter_data_flavortext_3,
                        info = R.string.shop_equipment_igniter_data_info_3,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_igniter_3,
                        upgradeLevelData = R.integer.equipment_requirement_level_igniter_3,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_duration_10_m,
                            R.string.shop_equipment_attribute_property_waterproof
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_consumable
                        )
                    )
                )
            ),
            // Firelight
            CodexEquipmentGroupRes(
                name = R.string.equipment_info_name_firelight,
                icon = R.drawable.icon_sh_firelight,
                buyCostData = R.integer.equipment_requirement_buycost_firelight,
                items = listOf(
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_firelight_1,
                        flavor = R.string.shop_equipment_firelight_data_flavortext_1,
                        info = R.string.shop_equipment_firelight_data_info_1,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_firelight_1,
                        upgradeLevelData = R.integer.equipment_requirement_level_firelight_1,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_range_2,
                            R.string.shop_equipment_attribute_duration_3_m,
                            R.string.shop_equipment_attribute_sanitydrainreduction_33,
                            R.string.shop_equipment_attribute_placeable
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_consumable
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_firelight_2,
                        flavor = R.string.shop_equipment_firelight_data_flavortext_2,
                        info = R.string.shop_equipment_firelight_data_info_2,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_firelight_2,
                        upgradeLevelData = R.integer.equipment_requirement_level_firelight_2,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_range_2,
                            R.string.shop_equipment_attribute_duration_5_m,
                            R.string.shop_equipment_attribute_sanitydrainreduction_50,
                            R.string.shop_equipment_attribute_placeable
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_consumable
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_firelight_3,
                        flavor = R.string.shop_equipment_firelight_data_flavortext_3,
                        info = R.string.shop_equipment_firelight_data_info_3,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_firelight_3,
                        upgradeLevelData = R.integer.equipment_requirement_level_firelight_3,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_range_2,
                            R.string.shop_equipment_attribute_sanitydrainreduction_66,
                            R.string.shop_equipment_attribute_property_waterproof,
                            R.string.shop_equipment_attribute_placeable
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_consumable
                        )
                    )
                )
            ),
            // UV Light
            CodexEquipmentGroupRes(
                name = R.string.equipment_info_name_uvlight,
                icon = R.drawable.icon_sh_uvlight,
                buyCostData = R.integer.equipment_requirement_buycost_ultraviolet,
                items = listOf(
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_uv_1,
                        flavor = R.string.shop_equipment_uvlight_data_flavortext_1,
                        info = R.string.shop_equipment_uvlight_data_info_1,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_ultraviolet_1,
                        upgradeLevelData = R.integer.equipment_requirement_level_ultraviolet_1,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_duration_60_s,
                            R.string.shop_equipment_attribute_uvchargetime_10,
                            R.string.shop_equipment_attribute_spotight_area,
                        ),
                        negativeAttributes = listOf(/* NONE */)
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_uv_2,
                        flavor = R.string.shop_equipment_uvlight_data_flavortext_2,
                        info = R.string.shop_equipment_uvlight_data_info_2,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_ultraviolet_2,
                        upgradeLevelData = R.integer.equipment_requirement_level_ultraviolet_2,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_uvchargetime_5,
                            R.string.shop_equipment_attribute_spotight_narrow,
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_electronic
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_uv_3,
                        flavor = R.string.shop_equipment_uvlight_data_flavortext_3,
                        info = R.string.shop_equipment_uvlight_data_info_3,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_ultraviolet_3,
                        upgradeLevelData = R.integer.equipment_requirement_level_ultraviolet_3,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_uvchargetime_1_5,
                            R.string.shop_equipment_attribute_spotight_wide,
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_electronic
                        )
                    )
                )
            ),
            // Crucifix
            CodexEquipmentGroupRes(
                name = R.string.equipment_info_name_crucifix,
                icon = R.drawable.icon_sh_crucifix,
                buyCostData = R.integer.equipment_requirement_buycost_crucifix,
                items = listOf(
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_crucifix_1,
                        flavor = R.string.shop_equipment_crucifix_data_flavortext_1,
                        info = R.string.shop_equipment_crucifix_data_info_1,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_crucifix_1,
                        upgradeLevelData = R.integer.equipment_requirement_level_crucifix_1,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_range_3,
                            R.string.shop_equipment_attribute_uses_1,
                            R.string.shop_equipment_attribute_placeable,
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_consumable
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_crucifix_2,
                        flavor = R.string.shop_equipment_crucifix_data_flavortext_2,
                        info = R.string.shop_equipment_crucifix_data_info_2,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_crucifix_2,
                        upgradeLevelData = R.integer.equipment_requirement_level_crucifix_2,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_range_4,
                            R.string.shop_equipment_attribute_uses_2,
                            R.string.shop_equipment_attribute_placeable,
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_consumable
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_crucifix_3,
                        flavor = R.string.shop_equipment_crucifix_data_flavortext_3,
                        info = R.string.shop_equipment_crucifix_data_info_3,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_crucifix_3,
                        upgradeLevelData = R.integer.equipment_requirement_level_crucifix_3,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_property_preventscursedhunt,
                            R.string.shop_equipment_attribute_range_4,
                            R.string.shop_equipment_attribute_uses_2,
                            R.string.shop_equipment_attribute_placeable,
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_consumable
                        )
                    )
                )
            ),
            // Video Camera
            CodexEquipmentGroupRes(
                name = R.string.equipment_info_name_videocamera,
                icon = R.drawable.icon_sh_videocamera,
                buyCostData = R.integer.equipment_requirement_buycost_vcam,
                items = listOf(
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_vidcam_1,
                        flavor = R.string.shop_equipment_videocamera_data_flavortext_1,
                        info = R.string.shop_equipment_videocamera_data_info_1,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_vcam_1,
                        upgradeLevelData = R.integer.equipment_requirement_level_vcam_1,
                        positiveAttributes = listOf(/* NONE */),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_interference_high,
                            R.string.shop_equipment_attribute_property_electronic,
                            R.string.shop_equipment_attribute_knockdownchance_high,
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_vidcam_2,
                        flavor = R.string.shop_equipment_videocamera_data_flavortext_2,
                        info = R.string.shop_equipment_videocamera_data_info_2,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_vcam_2,
                        upgradeLevelData = R.integer.equipment_requirement_level_vcam_2,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_imagequality_med
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_interference_med,
                            R.string.shop_equipment_attribute_property_electronic,
                            R.string.shop_equipment_attribute_knockdownchance_high,
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_vidcam_3,
                        flavor = R.string.shop_equipment_videocamera_data_flavortext_3,
                        info = R.string.shop_equipment_videocamera_data_info_3,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_vcam_3,
                        upgradeLevelData = R.integer.equipment_requirement_level_vcam_3,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_imagequality_high
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_interference_low,
                            R.string.shop_equipment_attribute_property_electronic,
                            R.string.shop_equipment_attribute_knockdownchance_high,
                        )
                    )
                )
            ),
            // Spirit Box
            CodexEquipmentGroupRes(
                name = R.string.equipment_info_name_spiritbox,
                icon = R.drawable.icon_sh_spiritbox,
                buyCostData = R.integer.equipment_requirement_buycost_box,
                items = listOf(
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_spiritbox_1,
                        flavor = R.string.shop_equipment_spiritbox_data_flavortext_1,
                        info = R.string.shop_equipment_spiritbox_data_info_1,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_box_1,
                        upgradeLevelData = R.integer.equipment_requirement_level_box_1,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_range_3
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_audioquality_low,
                            R.string.shop_equipment_attribute_responserate_low,
                            R.string.shop_equipment_attribute_property_electronic,
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_spiritbox_2,
                        flavor = R.string.shop_equipment_spiritbox_data_flavortext_2,
                        info = R.string.shop_equipment_spiritbox_data_info_2,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_box_2,
                        upgradeLevelData = R.integer.equipment_requirement_level_box_2,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_range_4,
                            R.string.shop_equipment_attribute_audioquality_med,
                            R.string.shop_equipment_attribute_responserate_med,
                            R.string.shop_equipment_attribute_property_displayscreen,
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_electronic
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_spiritbox_3,
                        flavor = R.string.shop_equipment_spiritbox_data_flavortext_3,
                        info = R.string.shop_equipment_spiritbox_data_info_3,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_box_3,
                        upgradeLevelData = R.integer.equipment_requirement_level_box_3,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_range_5,
                            R.string.shop_equipment_attribute_audioquality_high,
                            R.string.shop_equipment_attribute_responserate_high
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_electronic
                        )
                    )
                )
            ),
            // Thermometer
            CodexEquipmentGroupRes(
                name = R.string.equipment_info_name_thermometer,
                icon = R.drawable.icon_sh_thermometer,
                buyCostData = R.integer.equipment_requirement_buycost_thermometer,
                items = listOf(
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_thermo_1,
                        flavor = R.string.shop_equipment_thermometer_data_flavortext_1,
                        info = R.string.shop_equipment_thermometer_data_info_1,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_thermometer_1,
                        upgradeLevelData = R.integer.equipment_requirement_level_thermometer_1,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_accuracy_med
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_samplespeed_low
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_thermo_2,
                        flavor = R.string.shop_equipment_thermometer_data_flavortext_2,
                        info = R.string.shop_equipment_thermometer_data_info_2,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_thermometer_2,
                        upgradeLevelData = R.integer.equipment_requirement_level_thermometer_2,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_accuracy_high,
                            R.string.shop_equipment_attribute_samplespeed_med
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_electronic
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_thermo_3,
                        flavor = R.string.shop_equipment_thermometer_data_flavortext_3,
                        info = R.string.shop_equipment_thermometer_data_info_3,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_thermometer_3,
                        upgradeLevelData = R.integer.equipment_requirement_level_thermometer_3,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_accuracy_high,
                            R.string.shop_equipment_attribute_samplespeed_high
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_electronic
                        )
                    )
                )
            ),
            // Salt
            CodexEquipmentGroupRes(
                name = R.string.equipment_info_name_salt,
                icon = R.drawable.icon_sh_salt,
                buyCostData = R.integer.equipment_requirement_buycost_salt,
                items = listOf(
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_salt_1,
                        flavor = R.string.shop_equipment_salt_data_flavortext_1,
                        info = R.string.shop_equipment_salt_data_info_1,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_salt_1,
                        upgradeLevelData = R.integer.equipment_requirement_level_salt_1,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_uses_2
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_consumable
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_salt_2,
                        flavor = R.string.shop_equipment_salt_data_flavortext_2,
                        info = R.string.shop_equipment_salt_data_info_2,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_salt_2,
                        upgradeLevelData = R.integer.equipment_requirement_level_salt_2,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_uses_3
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_consumable
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_salt_3,
                        flavor = R.string.shop_equipment_salt_data_flavortext_3,
                        info = R.string.shop_equipment_salt_data_info_3,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_salt_3,
                        upgradeLevelData = R.integer.equipment_requirement_level_salt_3,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_uses_3,
                            R.string.shop_equipment_attribute_property_ghosteffect_slows
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_consumable
                        )
                    )
                )
            ),
            // Incense
            CodexEquipmentGroupRes(
                name = R.string.equipment_info_name_incense,
                icon = R.drawable.icon_sh_smudge,
                buyCostData = R.integer.equipment_requirement_buycost_incense,
                items = listOf(
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_repellant_1,
                        flavor = R.string.shop_equipment_incense_data_flavortext_1,
                        info = R.string.shop_equipment_incense_data_info_1,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_incense_1,
                        upgradeLevelData = R.integer.equipment_requirement_level_incense_1,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_range_3,
                            R.string.shop_equipment_attribute_duration_5_s,
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_consumable
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_repellant_2,
                        flavor = R.string.shop_equipment_incense_data_flavortext_2,
                        info = R.string.shop_equipment_incense_data_info_2,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_incense_2,
                        upgradeLevelData = R.integer.equipment_requirement_level_incense_2,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_range_4,
                            R.string.shop_equipment_attribute_duration_6_s,
                            R.string.shop_equipment_attribute_property_ghosteffect_slows,
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_consumable
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_repellant_3,
                        flavor = R.string.shop_equipment_incense_data_flavortext_3,
                        info = R.string.shop_equipment_incense_data_info_3,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_incense_3,
                        upgradeLevelData = R.integer.equipment_requirement_level_incense_3,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_range_5,
                            R.string.shop_equipment_attribute_duration_7_s,
                            R.string.shop_equipment_attribute_property_ghosteffect_halts,
                        ),
                        negativeAttributes = listOf( /* NONE */)
                    )
                )
            ),
            // Tripod
            CodexEquipmentGroupRes(
                name = R.string.equipment_info_name_tripod,
                icon = R.drawable.icon_sh_tripod,
                buyCostData = R.integer.equipment_requirement_buycost_tripod,
                items = listOf(
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_tripod_1,
                        flavor = R.string.shop_equipment_tripod_data_flavortext_1,
                        info = R.string.shop_equipment_tripod_data_info_1,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_tripod_1,
                        upgradeLevelData = R.integer.equipment_requirement_level_tripod_1,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_placeable
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_knockdownchance_med
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_tripod_2,
                        flavor = R.string.shop_equipment_tripod_data_flavortext_2,
                        info = R.string.shop_equipment_tripod_data_info_2,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_tripod_2,
                        upgradeLevelData = R.integer.equipment_requirement_level_tripod_2,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_placeable,
                            R.string.shop_equipment_attribute_property_rotationcontrol,
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_knockdownchance_med
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_tripod_3,
                        flavor = R.string.shop_equipment_tripod_data_flavortext_3,
                        info = R.string.shop_equipment_tripod_data_info_3,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_tripod_3,
                        upgradeLevelData = R.integer.equipment_requirement_level_tripod_3,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_placeable,
                            R.string.shop_equipment_attribute_property_rotationcontrol,
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_knockdownchance_low
                        )
                    )
                )
            ),
            // Motion Sensor
            CodexEquipmentGroupRes(
                name = R.string.equipment_info_name_motionsensor,
                icon = R.drawable.icon_sh_motionsensor,
                buyCostData = R.integer.equipment_requirement_buycost_motionsensor,
                items = listOf(
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_motion_1,
                        flavor = R.string.shop_equipment_motionsensor_data_flavortext_1,
                        info = R.string.shop_equipment_motionsensor_data_info_1,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_motionsensor_1,
                        upgradeLevelData = R.integer.equipment_requirement_level_motionsensor_1,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_sensorshape_line,
                            R.string.shop_equipment_attribute_indicator_light,
                            R.string.shop_equipment_attribute_placeable,
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_electronic
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_motion_2,
                        flavor = R.string.shop_equipment_motionsensor_data_flavortext_2,
                        info = R.string.shop_equipment_motionsensor_data_info_2,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_motionsensor_2,
                        upgradeLevelData = R.integer.equipment_requirement_level_motionsensor_2,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_sensorshape_line_cone,
                            R.string.shop_equipment_attribute_indicator_light,
                            R.string.shop_equipment_attribute_indicator_audio,
                            R.string.shop_equipment_attribute_placeable,
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_electronic
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_motion_3,
                        flavor = R.string.shop_equipment_motionsensor_data_flavortext_3,
                        info = R.string.shop_equipment_motionsensor_data_info_3,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_motionsensor_3,
                        upgradeLevelData = R.integer.equipment_requirement_level_motionsensor_3,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_sensorshape_circle,
                            R.string.shop_equipment_attribute_property_scanning,
                            R.string.shop_equipment_attribute_indicator_light,
                            R.string.shop_equipment_attribute_indicator_audio,
                            R.string.shop_equipment_attribute_placeable,
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_electronic
                        )
                    )
                )
            ),
            // Sound Sensor
            CodexEquipmentGroupRes(
                name = R.string.equipment_info_name_soundsensor,
                icon = R.drawable.icon_sh_soundsensor,
                buyCostData = R.integer.equipment_requirement_buycost_soundsensor,
                items = listOf(
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_sound_1,
                        flavor = R.string.shop_equipment_soundsensor_data_flavortext_1,
                        info = R.string.shop_equipment_soundsensor_data_info_1,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_soundsensor_1,
                        upgradeLevelData = R.integer.equipment_requirement_level_soundsensor_1,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_range_5_10,
                            R.string.shop_equipment_attribute_sensorshape_circle,
                            R.string.shop_equipment_attribute_placeable,
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_electronic
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_sound_2,
                        flavor = R.string.shop_equipment_soundsensor_data_flavortext_2,
                        info = R.string.shop_equipment_soundsensor_data_info_2,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_soundsensor_2,
                        upgradeLevelData = R.integer.equipment_requirement_level_soundsensor_2,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_range_5_10_15,
                            R.string.shop_equipment_attribute_sensorshape_circle,
                            R.string.shop_equipment_attribute_placeable,
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_electronic
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_sound_3,
                        flavor = R.string.shop_equipment_soundsensor_data_flavortext_3,
                        info = R.string.shop_equipment_soundsensor_data_info_3,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_soundsensor_3,
                        upgradeLevelData = R.integer.equipment_requirement_level_soundsensor_3,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_range_5_10_15,
                            R.string.shop_equipment_attribute_sensorshape_circle_cone_sides,
                            R.string.shop_equipment_attribute_placeable,
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_electronic
                        )
                    )
                )
            ),
            // Sanity Medication
            CodexEquipmentGroupRes(
                name = R.string.equipment_info_name_sanitymedication,
                icon = R.drawable.icon_sh_sanitymedication,
                buyCostData = R.integer.equipment_requirement_buycost_sanitymedication,
                items = listOf(
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_sanity_1,
                        flavor = R.string.shop_equipment_sanitymedication_data_flavortext_1,
                        info = R.string.shop_equipment_sanitymedication_data_info_1,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_sanitymedication_1,
                        upgradeLevelData = R.integer.equipment_requirement_level_sanitymedication_1,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_sanityrestoration_30
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_consumable
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_sanity_2,
                        flavor = R.string.shop_equipment_sanitymedication_data_flavortext_2,
                        info = R.string.shop_equipment_sanitymedication_data_info_2,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_sanitymedication_2,
                        upgradeLevelData = R.integer.equipment_requirement_level_sanitymedication_2,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_sanityrestoration_20
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_consumable
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_sanity_3,
                        flavor = R.string.shop_equipment_sanitymedication_data_flavortext_3,
                        info = R.string.shop_equipment_sanitymedication_data_info_3,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_sanitymedication_3,
                        upgradeLevelData = R.integer.equipment_requirement_level_sanitymedication_3,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_sanityrestoration_10,
                            R.string.shop_equipment_attribute_property_sprintboost,
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_consumable
                        )
                    )
                )
            ),
            // Ghost Writing Book
            CodexEquipmentGroupRes(
                name = R.string.equipment_info_name_ghostwritingbook,
                icon = R.drawable.icon_sh_ghostwritingbook,
                buyCostData = R.integer.equipment_requirement_buycost_book,
                items = listOf(
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_writing_1,
                        flavor = R.string.shop_equipment_ghostwritingbook_data_flavortext_1,
                        info = R.string.shop_equipment_ghostwritingbook_data_info_1,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_book_1,
                        upgradeLevelData = R.integer.equipment_requirement_level_book_1,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_range_3
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_interactionrate_low
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_writing_2,
                        flavor = R.string.shop_equipment_ghostwritingbook_data_flavortext_2,
                        info = R.string.shop_equipment_ghostwritingbook_data_info_2,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_book_2,
                        upgradeLevelData = R.integer.equipment_requirement_level_book_2,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_range_4,
                            R.string.shop_equipment_attribute_interactionrate_med,
                        ),
                        negativeAttributes = listOf(/* NONE */)
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_writing_3,
                        flavor = R.string.shop_equipment_ghostwritingbook_data_flavortext_3,
                        info = R.string.shop_equipment_ghostwritingbook_data_info_3,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_book_3,
                        upgradeLevelData = R.integer.equipment_requirement_level_book_3,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_range_5,
                            R.string.shop_equipment_attribute_interactionrate_high,
                        ),
                        negativeAttributes = listOf(/* NONE */)
                    )
                )
            ),
            // Parabolic Microphone
            CodexEquipmentGroupRes(
                name = R.string.equipment_info_name_parabolicmicrophone,
                icon = R.drawable.icon_sh_parabolicmicrophone,
                buyCostData = R.integer.equipment_requirement_buycost_parabolicmicrophone,
                items = listOf(
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_para_1,
                        flavor = R.string.shop_equipment_parabolicmicrophone_data_flavortext_1,
                        info = R.string.shop_equipment_parabolicmicrophone_data_info_1,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_parabolicmicrophone_1,
                        upgradeLevelData = R.integer.equipment_requirement_level_parabolicmicrophone_1,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_range_20
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_electronic
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_para_2,
                        flavor = R.string.shop_equipment_parabolicmicrophone_data_flavortext_2,
                        info = R.string.shop_equipment_parabolicmicrophone_data_info_2,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_parabolicmicrophone_2,
                        upgradeLevelData = R.integer.equipment_requirement_level_parabolicmicrophone_2,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_range_30,
                            R.string.shop_equipment_attribute_property_displayscreen,
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_electronic
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_para_3,
                        flavor = R.string.shop_equipment_parabolicmicrophone_data_flavortext_3,
                        info = R.string.shop_equipment_parabolicmicrophone_data_info_3,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_parabolicmicrophone_3,
                        upgradeLevelData = R.integer.equipment_requirement_level_parabolicmicrophone_3,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_range_30,
                            R.string.shop_equipment_attribute_property_displayscreen,
                            R.string.shop_equipment_attribute_indicator_distance,
                            R.string.shop_equipment_attribute_indicator_directional,
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_electronic
                        )
                    )
                )
            ),
            // Headgear
            CodexEquipmentGroupRes(
                name = R.string.equipment_info_name_headgear,
                icon = R.drawable.icon_sh_headgear,
                buyCostData = R.integer.equipment_requirement_buycost_headgear,
                items = listOf(
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_headgear_1,
                        flavor = R.string.shop_equipment_headgear_data_flavortext_1,
                        info = R.string.shop_equipment_headgear_data_info_1,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_headgear_1,
                        upgradeLevelData = R.integer.equipment_requirement_level_headgear_1,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_property_headslot,
                            R.string.shop_equipment_attribute_imagequality_med,
                            R.string.shop_equipment_attribute_interference_med,
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_electronic
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_headgear_2,
                        flavor = R.string.shop_equipment_headgear_data_flavortext_2,
                        info = R.string.shop_equipment_headgear_data_info_2,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_headgear_2,
                        upgradeLevelData = R.integer.equipment_requirement_level_headgear_2,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_property_headslot,
                            R.string.shop_equipment_attribute_intensity_low,
                            R.string.shop_equipment_attribute_spotight_narrow,
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_property_electronic
                        )
                    ),
                    CodexEquipmentGroupItemRes(
                        image = R.drawable.icon_shop_headgear_3,
                        flavor = R.string.shop_equipment_headgear_data_flavortext_3,
                        info = R.string.shop_equipment_headgear_data_info_3,
                        upgradeCostData = R.integer.equipment_requirement_upgradecost_headgear_3,
                        upgradeLevelData = R.integer.equipment_requirement_level_headgear_3,
                        positiveAttributes = listOf(
                            R.string.shop_equipment_attribute_property_headslot,
                            R.string.shop_equipment_attribute_property_nightvision,
                        ),
                        negativeAttributes = listOf(
                            R.string.shop_equipment_attribute_interference_med,
                            R.string.shop_equipment_attribute_property_electronic,
                        )
                    )
                )
            )
        )

    fun fetchItems(): Result<List<CodexEquipmentGroupDto>> {

        val cursedEquipmentGroupsDto = mutableListOf<CodexEquipmentGroupDto>()

        equipmentResources.forEach { resDto ->
            cursedEquipmentGroupsDto.add(resDto.toLocal())
        }

        return Result.success(cursedEquipmentGroupsDto)

    }

    private data class CodexEquipmentGroupRes(
        @StringRes val name: Int,
        @DrawableRes val icon: Int,
        @IntegerRes var buyCostData: Int,
        val items: List<CodexEquipmentGroupItemRes>
    )

    private data class CodexEquipmentGroupItemRes(
        @DrawableRes val image: Int,
        @StringRes val flavor: Int,
        @StringRes val info: Int,
        @IntegerRes var upgradeCostData: Int,
        @IntegerRes var upgradeLevelData: Int,
        @StringRes val positiveAttributes: List<Int>,
        @StringRes val negativeAttributes: List<Int>
    )

    private fun CodexEquipmentGroupRes.toLocal() = CodexEquipmentGroupDto(
        name = name,
        icon = icon,
        buyCostData = buyCostData,
        items = items.toLocal()
    )

    private fun CodexEquipmentGroupItemRes.toLocal() = CodexEquipmentGroupItemDto(
        image = image,
        flavor = flavor,
        info = info,
        upgradeCostData = upgradeCostData,
        upgradeLevelData = upgradeLevelData,
        positiveAttributes = positiveAttributes,
        negativeAttributes = negativeAttributes
    )

    @JvmName("CodexEquipmentGroupResListToLocalCodexEquipmentGroupRes")
    private fun List<CodexEquipmentGroupRes>.toLocal() = map { dto ->
        dto.toLocal()
    }

    @JvmName("CodexEquipmentGroupItemResListToLocalCodexEquipmentGroupItemRes")
    private fun List<CodexEquipmentGroupItemRes>.toLocal() = map { dto ->
        dto.toLocal()
    }

}



