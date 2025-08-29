package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.dto.CodexEquipmentGroupDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.dto.CodexEquipmentGroupItemDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.EquipmentAttribute
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.EquipmentBuyCost
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.EquipmentIcon
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.EquipmentTitles
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.TierFlavorText
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.TierImage
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.TierInformation
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.UnlockLevel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.UpgradeCost

class CodexEquipmentLocalDataSource {

    private val equipmentResources: List<CodexEquipmentGroupResourceDto>
        get() = listOf(
            CodexEquipmentGroupResourceDto(
                name = EquipmentTitles.DOTS,
                icon = EquipmentIcon.DOTS,
                buyCostData = EquipmentBuyCost.COST_65,
                items = listOf(
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.DOTS_1,
                        flavor = TierFlavorText.DOTS_1,
                        info = TierInformation.DOTS_1,
                        upgradeCostData = UpgradeCost.COST_0,
                        upgradeLevelData = UnlockLevel.LEVEL_0,
                        positiveAttributes = listOf(
                            EquipmentAttribute.SPOTLIGHT_NARROW,
                            EquipmentAttribute.RANGE_5,
                            EquipmentAttribute.HANDHELD
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.DOTS_2,
                        flavor = TierFlavorText.DOTS_2,
                        info = TierInformation.DOTS_2,
                        upgradeCostData = UpgradeCost.COST_3000,
                        upgradeLevelData = UnlockLevel.LEVEL_29,
                        positiveAttributes = listOf(
                            EquipmentAttribute.SPOTLIGHT_AREA,
                            EquipmentAttribute.RANGE_2_5,
                            EquipmentAttribute.PLACEABLE
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.DOTS_3,
                        flavor = TierFlavorText.DOTS_3,
                        info = TierInformation.DOTS_3,
                        upgradeCostData = UpgradeCost.COST_3000,
                        upgradeLevelData = UnlockLevel.LEVEL_60,
                        positiveAttributes = listOf(
                            EquipmentAttribute.SPOTLIGHT_WIDE,
                            EquipmentAttribute.RANGE_7,
                            EquipmentAttribute.PLACEABLE,
                            EquipmentAttribute.PROPERTY_SCANNING
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    )
                )
            ),
            // EMF
            CodexEquipmentGroupResourceDto(
                name = EquipmentTitles.EMF,
                icon = EquipmentIcon.EMF,
                buyCostData = EquipmentBuyCost.COST_45,
                items = listOf(
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.EMF_1,
                        flavor = TierFlavorText.EMF_1,
                        info = TierInformation.EMF_1,
                        upgradeCostData = UpgradeCost.COST_0,
                        upgradeLevelData = UnlockLevel.LEVEL_0,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_1_7,
                            EquipmentAttribute.ACCURACY_LOW
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.EMF_2,
                        flavor = TierFlavorText.EMF_2,
                        info = TierInformation.EMF_2,
                        upgradeCostData = UpgradeCost.COST_3000,
                        upgradeLevelData = UnlockLevel.LEVEL_20,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_1_7,
                            EquipmentAttribute.ACCURACY_HIGH,
                            EquipmentAttribute.INDICATOR_AUDIO
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.EMF_3,
                        flavor = TierFlavorText.EMF_3,
                        info = TierInformation.EMF_3,
                        upgradeCostData = UpgradeCost.COST_4500,
                        upgradeLevelData = UnlockLevel.LEVEL_52,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_3_5,
                            EquipmentAttribute.ACCURACY_HIGH,
                            EquipmentAttribute.PROPERTY_DISPLAY_SCREEN,
                            EquipmentAttribute.INDICATOR_AUDIO,
                            EquipmentAttribute.INDICATOR_DISTANCE,
                            EquipmentAttribute.INDICATOR_DIRECTIONAL
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    )
                )
            ),
            // Flashlight
            CodexEquipmentGroupResourceDto(
                name = EquipmentTitles.FLASHLIGHT,
                icon = EquipmentIcon.FLASHLIGHT,
                buyCostData = EquipmentBuyCost.COST_30,
                items = listOf(
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.FLASHLIGHT_1,
                        flavor = TierFlavorText.FLASHLIGHT_1,
                        info = TierInformation.FLASHLIGHT_1,
                        upgradeCostData = UpgradeCost.COST_0,
                        upgradeLevelData = UnlockLevel.LEVEL_0,
                        positiveAttributes = listOf(
                            EquipmentAttribute.SPOTLIGHT_NARROW
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.INTENSITY_LOW,
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.FLASHLIGHT_2,
                        flavor = TierFlavorText.FLASHLIGHT_2,
                        info = TierInformation.FLASHLIGHT_2,
                        upgradeCostData = UpgradeCost.COST_3000,
                        upgradeLevelData = UnlockLevel.LEVEL_19,
                        positiveAttributes = listOf(
                            EquipmentAttribute.SPOTLIGHT_NARROW,
                            EquipmentAttribute.INTENSITY_MED
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.FLASHLIGHT_3,
                        flavor = TierFlavorText.FLASHLIGHT_3,
                        info = TierInformation.FLASHLIGHT_3,
                        upgradeCostData = UpgradeCost.COST_3000,
                        upgradeLevelData = UnlockLevel.LEVEL_35,
                        positiveAttributes = listOf(
                            EquipmentAttribute.SPOTLIGHT_WIDE,
                            EquipmentAttribute.INTENSITY_HIGH
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    )
                )
            ),
            // Photo Camera
            CodexEquipmentGroupResourceDto(
                name = EquipmentTitles.PHOTO_CAMERA,
                icon = EquipmentIcon.PHOTO_CAMERA,
                buyCostData = EquipmentBuyCost.COST_40,
                items = listOf(
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.PHOTO_CAMERA_1,
                        flavor = TierFlavorText.PHOTO_CAMERA_1,
                        info = TierInformation.PHOTO_CAMERA_1,
                        upgradeCostData = UpgradeCost.COST_0,
                        upgradeLevelData = UnlockLevel.LEVEL_3,
                        positiveAttributes = emptyList(/* NONE */),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PHOTO_TIME_HIGH
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.PHOTO_CAMERA_2,
                        flavor = TierFlavorText.PHOTO_CAMERA_2,
                        info = TierInformation.PHOTO_CAMERA_2,
                        upgradeCostData = UpgradeCost.COST_3000,
                        upgradeLevelData = UnlockLevel.LEVEL_25,
                        positiveAttributes = listOf(
                            EquipmentAttribute.PHOTO_TIME_MED,
                            EquipmentAttribute.PROPERTY_DISPLAY_SCREEN
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.INTERFERENCE_MED,
                            EquipmentAttribute.PROPERTY_ELECTRONIC,
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.PHOTO_CAMERA_3,
                        flavor = TierFlavorText.PHOTO_CAMERA_3,
                        info = TierInformation.PHOTO_CAMERA_3,
                        upgradeCostData = UpgradeCost.COST_5000,
                        upgradeLevelData = UnlockLevel.LEVEL_57,
                        positiveAttributes = listOf(
                            EquipmentAttribute.PHOTO_TIME_LOW,
                            EquipmentAttribute.PROPERTY_DISPLAY_SCREEN
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.INTERFERENCE_MED,
                            EquipmentAttribute.PROPERTY_ELECTRONIC,
                        )
                    )
                )
            ),
            // Igniter
            CodexEquipmentGroupResourceDto(
                name = EquipmentTitles.IGNITER,
                icon = EquipmentIcon.IGNITER,
                buyCostData = EquipmentBuyCost.COST_10,
                items = listOf(
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.IGNITER_1,
                        flavor = TierFlavorText.IGNITER_1,
                        info = TierInformation.IGNITER_1,
                        upgradeCostData = UpgradeCost.COST_0,
                        upgradeLevelData = UnlockLevel.LEVEL_12,
                        positiveAttributes = listOf(
                            EquipmentAttribute.DURATION_10_S,
                            EquipmentAttribute.USES_10
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_CONSUMABLE
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.IGNITER_2,
                        flavor = TierFlavorText.IGNITER_2,
                        info = TierInformation.IGNITER_2,
                        upgradeCostData = UpgradeCost.COST_500,
                        upgradeLevelData = UnlockLevel.LEVEL_41,
                        positiveAttributes = listOf(
                            EquipmentAttribute.DURATION_5_M
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_CONSUMABLE
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.IGNITER_3,
                        flavor = TierFlavorText.IGNITER_3,
                        info = TierInformation.IGNITER_3,
                        upgradeCostData = UpgradeCost.COST_750,
                        upgradeLevelData = UnlockLevel.LEVEL_57,
                        positiveAttributes = listOf(
                            EquipmentAttribute.DURATION_10_M,
                            EquipmentAttribute.PROPERTY_WATERPROOF
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_CONSUMABLE
                        )
                    )
                )
            ),
            // Firelight
            CodexEquipmentGroupResourceDto(
                name = EquipmentTitles.FIRELIGHT,
                icon = EquipmentIcon.FIRELIGHT,
                buyCostData = EquipmentBuyCost.COST_15,
                items = listOf(
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.FIRELIGHT_1,
                        flavor = TierFlavorText.FIRELIGHT_1,
                        info = TierInformation.FIRELIGHT_1,
                        upgradeCostData = UpgradeCost.COST_0,
                        upgradeLevelData = UnlockLevel.LEVEL_12,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_2,
                            EquipmentAttribute.DURATION_3_M,
                            EquipmentAttribute.SANITY_DRAIN_REDUCTION_33,
                            EquipmentAttribute.PLACEABLE
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_CONSUMABLE
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.FIRELIGHT_2,
                        flavor = TierFlavorText.FIRELIGHT_2,
                        info = TierInformation.FIRELIGHT_2,
                        upgradeCostData = UpgradeCost.COST_3000,
                        upgradeLevelData = UnlockLevel.LEVEL_47,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_2,
                            EquipmentAttribute.DURATION_5_M,
                            EquipmentAttribute.SANITY_DRAIN_REDUCTION_50,
                            EquipmentAttribute.PLACEABLE
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_CONSUMABLE
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.FIRELIGHT_3,
                        flavor = TierFlavorText.FIRELIGHT_3,
                        info = TierInformation.FIRELIGHT_3,
                        upgradeCostData = UpgradeCost.COST_10000,
                        upgradeLevelData = UnlockLevel.LEVEL_79,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_2,
                            EquipmentAttribute.SANITY_DRAIN_REDUCTION_66,
                            EquipmentAttribute.PROPERTY_WATERPROOF,
                            EquipmentAttribute.PLACEABLE
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_CONSUMABLE
                        )
                    )
                )
            ),
            // UV Light
            CodexEquipmentGroupResourceDto(
                name = EquipmentTitles.UV_LIGHT,
                icon = EquipmentIcon.UV_LIGHT,
                buyCostData = EquipmentBuyCost.COST_20,
                items = listOf(
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.UV_LIGHT_1,
                        flavor = TierFlavorText.UV_LIGHT_1,
                        info = TierInformation.UV_LIGHT_1,
                        upgradeCostData = UpgradeCost.COST_0,
                        upgradeLevelData = UnlockLevel.LEVEL_0,
                        positiveAttributes = listOf(
                            EquipmentAttribute.DURATION_60_S,
                            EquipmentAttribute.UV_CHARGE_TIME_10,
                            EquipmentAttribute.SPOTLIGHT_AREA,
                        ),
                        negativeAttributes = emptyList(/* NONE */)
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.UV_LIGHT_2,
                        flavor = TierFlavorText.UV_LIGHT_2,
                        info = TierInformation.UV_LIGHT_2,
                        upgradeCostData = UpgradeCost.COST_3000,
                        upgradeLevelData = UnlockLevel.LEVEL_21,
                        positiveAttributes = listOf(
                            EquipmentAttribute.UV_CHARGE_TIME_5,
                            EquipmentAttribute.SPOTLIGHT_NARROW,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.UV_LIGHT_3,
                        flavor = TierFlavorText.UV_LIGHT_3,
                        info = TierInformation.UV_LIGHT_3,
                        upgradeCostData = UpgradeCost.COST_3000,
                        upgradeLevelData = UnlockLevel.LEVEL_56,
                        positiveAttributes = listOf(
                            EquipmentAttribute.UV_CHARGE_TIME_1_5,
                            EquipmentAttribute.SPOTLIGHT_WIDE,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    )
                )
            ),
            // Crucifix
            CodexEquipmentGroupResourceDto(
                name = EquipmentTitles.CRUCIFIX,
                icon = EquipmentIcon.CRUCIFIX,
                buyCostData = EquipmentBuyCost.COST_30,
                items = listOf(
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.CRUCIFIX_1,
                        flavor = TierFlavorText.CRUCIFIX_1,
                        info = TierInformation.CRUCIFIX_1,
                        upgradeCostData = UpgradeCost.COST_0,
                        upgradeLevelData = UnlockLevel.LEVEL_8,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_3,
                            EquipmentAttribute.USES_1,
                            EquipmentAttribute.PLACEABLE,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_CONSUMABLE
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.CRUCIFIX_2,
                        flavor = TierFlavorText.CRUCIFIX_2,
                        info = TierInformation.CRUCIFIX_2,
                        upgradeCostData = UpgradeCost.COST_4000,
                        upgradeLevelData = UnlockLevel.LEVEL_37,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_4,
                            EquipmentAttribute.USES_2,
                            EquipmentAttribute.PLACEABLE,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_CONSUMABLE
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.CRUCIFIX_3,
                        flavor = TierFlavorText.CRUCIFIX_3,
                        info = TierInformation.CRUCIFIX_3,
                        upgradeCostData = UpgradeCost.COST_20000,
                        upgradeLevelData = UnlockLevel.LEVEL_90,
                        positiveAttributes = listOf(
                            EquipmentAttribute.PROPERTY_PREVENT_CURSED_HUNT,
                            EquipmentAttribute.RANGE_4,
                            EquipmentAttribute.USES_2,
                            EquipmentAttribute.PLACEABLE,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_CONSUMABLE
                        )
                    )
                )
            ),
            // Video Camera
            CodexEquipmentGroupResourceDto(
                name = EquipmentTitles.VIDEO_CAMERA,
                icon = EquipmentIcon.VIDEO_CAMERA,
                buyCostData = EquipmentBuyCost.COST_50,
                items = listOf(
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.VIDEO_CAMERA_1,
                        flavor = TierFlavorText.VIDEO_CAMERA_1,
                        info = TierInformation.VIDEO_CAMERA_1,
                        upgradeCostData = UpgradeCost.COST_0,
                        upgradeLevelData = UnlockLevel.LEVEL_0,
                        positiveAttributes = emptyList(/* NONE */),
                        negativeAttributes = listOf(
                            EquipmentAttribute.INTERFERENCE_HIGH,
                            EquipmentAttribute.PROPERTY_ELECTRONIC,
                            EquipmentAttribute.KNOCKDOWN_CHANCE_HIGH,
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.VIDEO_CAMERA_2,
                        flavor = TierFlavorText.VIDEO_CAMERA_2,
                        info = TierInformation.VIDEO_CAMERA_2,
                        upgradeCostData = UpgradeCost.COST_3000,
                        upgradeLevelData = UnlockLevel.LEVEL_33,
                        positiveAttributes = listOf(
                            EquipmentAttribute.IMAGE_QUALITY_MED
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.INTERFERENCE_MED,
                            EquipmentAttribute.PROPERTY_ELECTRONIC,
                            EquipmentAttribute.KNOCKDOWN_CHANCE_HIGH,
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.VIDEO_CAMERA_3,
                        flavor = TierFlavorText.VIDEO_CAMERA_3,
                        info = TierInformation.VIDEO_CAMERA_3,
                        upgradeCostData = UpgradeCost.COST_3000,
                        upgradeLevelData = UnlockLevel.LEVEL_61,
                        positiveAttributes = listOf(
                            EquipmentAttribute.IMAGE_QUALITY_HIGH
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.INTERFERENCE_LOW,
                            EquipmentAttribute.PROPERTY_ELECTRONIC,
                            EquipmentAttribute.KNOCKDOWN_CHANCE_HIGH,
                        )
                    )
                )
            ),
            // Spirit Box
            CodexEquipmentGroupResourceDto(
                name = EquipmentTitles.SPIRIT_BOX,
                icon = EquipmentIcon.SPIRIT_BOX,
                buyCostData = EquipmentBuyCost.COST_50,
                items = listOf(
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.SPIRIT_BOX_1,
                        flavor = TierFlavorText.SPIRIT_BOX_1,
                        info = TierInformation.SPIRIT_BOX_1,
                        upgradeCostData = UpgradeCost.COST_0,
                        upgradeLevelData = UnlockLevel.LEVEL_0,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_3
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.AUDIO_QUALITY_LOW,
                            EquipmentAttribute.RESPONSE_RATE_LOW,
                            EquipmentAttribute.PROPERTY_ELECTRONIC,
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.SPIRIT_BOX_2,
                        flavor = TierFlavorText.SPIRIT_BOX_2,
                        info = TierInformation.SPIRIT_BOX_2,
                        upgradeCostData = UpgradeCost.COST_3000,
                        upgradeLevelData = UnlockLevel.LEVEL_27,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_4,
                            EquipmentAttribute.AUDIO_QUALITY_MED,
                            EquipmentAttribute.RESPONSE_RATE_MED,
                            EquipmentAttribute.PROPERTY_DISPLAY_SCREEN,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.SPIRIT_BOX_3,
                        flavor = TierFlavorText.SPIRIT_BOX_3,
                        info = TierInformation.SPIRIT_BOX_3,
                        upgradeCostData = UpgradeCost.COST_3000,
                        upgradeLevelData = UnlockLevel.LEVEL_54,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_5,
                            EquipmentAttribute.AUDIO_QUALITY_HIGH,
                            EquipmentAttribute.RESPONSE_RATE_HIGH
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    )
                )
            ),
            // Thermometer
            CodexEquipmentGroupResourceDto(
                name = EquipmentTitles.THERMOMETER,
                icon = EquipmentIcon.THERMOMETER,
                buyCostData = EquipmentBuyCost.COST_30,
                items = listOf(
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.THERMOMETER_1,
                        flavor = TierFlavorText.THERMOMETER_1,
                        info = TierInformation.THERMOMETER_1,
                        upgradeCostData = UpgradeCost.COST_0,
                        upgradeLevelData = UnlockLevel.LEVEL_0,
                        positiveAttributes = listOf(
                            EquipmentAttribute.ACCURACY_MED
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.SAMPLE_SPEED_LOW
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.THERMOMETER_2,
                        flavor = TierFlavorText.THERMOMETER_2,
                        info = TierInformation.THERMOMETER_2,
                        upgradeCostData = UpgradeCost.COST_3000,
                        upgradeLevelData = UnlockLevel.LEVEL_36,
                        positiveAttributes = listOf(
                            EquipmentAttribute.ACCURACY_HIGH,
                            EquipmentAttribute.SAMPLE_SPEED_HIGH
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.THERMOMETER_3,
                        flavor = TierFlavorText.THERMOMETER_3,
                        info = TierInformation.THERMOMETER_3,
                        upgradeCostData = UpgradeCost.COST_3000,
                        upgradeLevelData = UnlockLevel.LEVEL_64,
                        positiveAttributes = listOf(
                            EquipmentAttribute.ACCURACY_HIGH,
                            EquipmentAttribute.SAMPLE_SPEED_HIGH
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    )
                )
            ),
            // Salt
            CodexEquipmentGroupResourceDto(
                name = EquipmentTitles.SALT,
                icon = EquipmentIcon.SALT,
                buyCostData = EquipmentBuyCost.COST_15,
                items = listOf(
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.SALT_1,
                        flavor = TierFlavorText.SALT_1,
                        info = TierInformation.SALT_1,
                        upgradeCostData = UpgradeCost.COST_0,
                        upgradeLevelData = UnlockLevel.LEVEL_9,
                        positiveAttributes = listOf(
                            EquipmentAttribute.USES_2
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_CONSUMABLE
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.SALT_2,
                        flavor = TierFlavorText.SALT_2,
                        info = TierInformation.SALT_2,
                        upgradeCostData = UpgradeCost.COST_2500,
                        upgradeLevelData = UnlockLevel.LEVEL_43,
                        positiveAttributes = listOf(
                            EquipmentAttribute.USES_3
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_CONSUMABLE
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.SALT_3,
                        flavor = TierFlavorText.SALT_3,
                        info = TierInformation.SALT_3,
                        upgradeCostData = UpgradeCost.COST_5000,
                        upgradeLevelData = UnlockLevel.LEVEL_68,
                        positiveAttributes = listOf(
                            EquipmentAttribute.USES_3,
                            EquipmentAttribute.PROPERTY_GHOST_EFFECT_SLOWS
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_CONSUMABLE
                        )
                    )
                )
            ),
            // Incense
            CodexEquipmentGroupResourceDto(
                name = EquipmentTitles.INCENSE,
                icon = EquipmentIcon.INCENSE,
                buyCostData = EquipmentBuyCost.COST_15,
                items = listOf(
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.INCENSE_1,
                        flavor = TierFlavorText.INCENSE_1,
                        info = TierInformation.INCENSE_1,
                        upgradeCostData = UpgradeCost.COST_0,
                        upgradeLevelData = UnlockLevel.LEVEL_14,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_3,
                            EquipmentAttribute.DURATION_5_S,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_CONSUMABLE
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.INCENSE_2,
                        flavor = TierFlavorText.INCENSE_2,
                        info = TierInformation.INCENSE_2,
                        upgradeCostData = UpgradeCost.COST_3500,
                        upgradeLevelData = UnlockLevel.LEVEL_42,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_4,
                            EquipmentAttribute.DURATION_6_S,
                            EquipmentAttribute.PROPERTY_GHOST_EFFECT_SLOWS,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_CONSUMABLE
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.INCENSE_3,
                        flavor = TierFlavorText.INCENSE_3,
                        info = TierInformation.INCENSE_3,
                        upgradeCostData = UpgradeCost.COST_15000,
                        upgradeLevelData = UnlockLevel.LEVEL_85,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_5,
                            EquipmentAttribute.DURATION_7_S,
                            EquipmentAttribute.PROPERTY_GHOST_EFFECT_HALTS,
                        ),
                        negativeAttributes = emptyList( /* NONE */)
                    )
                )
            ),
            // Tripod
            CodexEquipmentGroupResourceDto(
                name = EquipmentTitles.TRIPOD,
                icon = EquipmentIcon.TRIPOD,
                buyCostData = EquipmentBuyCost.COST_25,
                items = listOf(
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.TRIPOD_1,
                        flavor = TierFlavorText.TRIPOD_1,
                        info = TierInformation.TRIPOD_1,
                        upgradeCostData = UpgradeCost.COST_0,
                        upgradeLevelData = UnlockLevel.LEVEL_10,
                        positiveAttributes = listOf(
                            EquipmentAttribute.PLACEABLE
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.KNOCKDOWN_CHANCE_MED
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.TRIPOD_2,
                        flavor = TierFlavorText.TRIPOD_2,
                        info = TierInformation.TRIPOD_2,
                        upgradeCostData = UpgradeCost.COST_5000,
                        upgradeLevelData = UnlockLevel.LEVEL_34,
                        positiveAttributes = listOf(
                            EquipmentAttribute.PLACEABLE,
                            EquipmentAttribute.PROPERTY_ROTATION_CONTROL,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.KNOCKDOWN_CHANCE_MED
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.TRIPOD_3,
                        flavor = TierFlavorText.TRIPOD_3,
                        info = TierInformation.TRIPOD_3,
                        upgradeCostData = UpgradeCost.COST_3000,
                        upgradeLevelData = UnlockLevel.LEVEL_62,
                        positiveAttributes = listOf(
                            EquipmentAttribute.PLACEABLE,
                            EquipmentAttribute.PROPERTY_ROTATION_CONTROL,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.KNOCKDOWN_CHANCE_LOW
                        )
                    )
                )
            ),
            // Motion Sensor
            CodexEquipmentGroupResourceDto(
                name = EquipmentTitles.MOTION_SENSOR,
                icon = EquipmentIcon.MOTION_SENSOR,
                buyCostData = EquipmentBuyCost.COST_100,
                items = listOf(
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.MOTION_SENSOR_1,
                        flavor = TierFlavorText.MOTION_SENSOR_1,
                        info = TierInformation.MOTION_SENSOR_1,
                        upgradeCostData = UpgradeCost.COST_0,
                        upgradeLevelData = UnlockLevel.LEVEL_5,
                        positiveAttributes = listOf(
                            EquipmentAttribute.SENSOR_SHAPE_LINE,
                            EquipmentAttribute.INDICATOR_LIGHT,
                            EquipmentAttribute.PLACEABLE,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.MOTION_SENSOR_2,
                        flavor = TierFlavorText.MOTION_SENSOR_2,
                        info = TierInformation.MOTION_SENSOR_2,
                        upgradeCostData = UpgradeCost.COST_2500,
                        upgradeLevelData = UnlockLevel.LEVEL_45,
                        positiveAttributes = listOf(
                            EquipmentAttribute.SENSOR_SHAPE_LINE_CONE,
                            EquipmentAttribute.INDICATOR_LIGHT,
                            EquipmentAttribute.INDICATOR_AUDIO,
                            EquipmentAttribute.PLACEABLE,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.MOTION_SENSOR_3,
                        flavor = TierFlavorText.MOTION_SENSOR_3,
                        info = TierInformation.MOTION_SENSOR_3,
                        upgradeCostData = UpgradeCost.COST_8000,
                        upgradeLevelData = UnlockLevel.LEVEL_74,
                        positiveAttributes = listOf(
                            EquipmentAttribute.SENSOR_SHAPE_CIRCLE,
                            EquipmentAttribute.PROPERTY_SCANNING,
                            EquipmentAttribute.INDICATOR_LIGHT,
                            EquipmentAttribute.INDICATOR_AUDIO,
                            EquipmentAttribute.PLACEABLE,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    )
                )
            ),
            // Sound Recorder
            CodexEquipmentGroupResourceDto(
                name = EquipmentTitles.SOUND_RECORDER,
                icon = EquipmentIcon.SOUND_RECORDER,
                buyCostData = EquipmentBuyCost.COST_30,
                items = listOf(
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.SOUND_RECORDER_1,
                        flavor = TierFlavorText.SOUND_RECORDER_1,
                        info = TierInformation.SOUND_RECORDER_1,
                        upgradeCostData = UpgradeCost.COST_0,
                        upgradeLevelData = UnlockLevel.LEVEL_4,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_3,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.SOUND_RECORDER_2,
                        flavor = TierFlavorText.SOUND_RECORDER_2,
                        info = TierInformation.SOUND_RECORDER_2,
                        upgradeCostData = UpgradeCost.COST_3000,
                        upgradeLevelData = UnlockLevel.LEVEL_39,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_5,
                            EquipmentAttribute.PROPERTY_DISPLAY_SCREEN,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.SOUND_RECORDER_3,
                        flavor = TierFlavorText.SOUND_RECORDER_3,
                        info = TierInformation.SOUND_RECORDER_3,
                        upgradeCostData = UpgradeCost.COST_5000,
                        upgradeLevelData = UnlockLevel.LEVEL_60,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_5,
                            EquipmentAttribute.PROPERTY_DISPLAY_SCREEN,
                            EquipmentAttribute.INDICATOR_DISTANCE,
                            EquipmentAttribute.INDICATOR_DIRECTIONAL,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    )
                )
            ),
            // Sound Sensor
            CodexEquipmentGroupResourceDto(
                name = EquipmentTitles.SOUND_SENSOR,
                icon = EquipmentIcon.SOUND_SENSOR,
                buyCostData = EquipmentBuyCost.COST_80,
                items = listOf(
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.SOUND_SENSOR_1,
                        flavor = TierFlavorText.SOUND_SENSOR_1,
                        info = TierInformation.SOUND_SENSOR_1,
                        upgradeCostData = UpgradeCost.COST_0,
                        upgradeLevelData = UnlockLevel.LEVEL_11,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_5_10,
                            EquipmentAttribute.SENSOR_SHAPE_CIRCLE,
                            EquipmentAttribute.PLACEABLE,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.SOUND_SENSOR_2,
                        flavor = TierFlavorText.SOUND_SENSOR_2,
                        info = TierInformation.SOUND_SENSOR_2,
                        upgradeCostData = UpgradeCost.COST_3000,
                        upgradeLevelData = UnlockLevel.LEVEL_32,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_5_10_15,
                            EquipmentAttribute.SENSOR_SHAPE_CIRCLE,
                            EquipmentAttribute.PLACEABLE,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.SOUND_SENSOR_3,
                        flavor = TierFlavorText.SOUND_SENSOR_3,
                        info = TierInformation.SOUND_SENSOR_3,
                        upgradeCostData = UpgradeCost.COST_1500,
                        upgradeLevelData = UnlockLevel.LEVEL_58,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_5_10_15,
                            EquipmentAttribute.SENSOR_SHAPE_CIRCLE_CONE_SIDES,
                            EquipmentAttribute.PLACEABLE,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    )
                )
            ),
            // Sanity Medication
            CodexEquipmentGroupResourceDto(
                name = EquipmentTitles.SANITY_MEDICATION,
                icon = EquipmentIcon.SANITY_MEDICATION,
                buyCostData = EquipmentBuyCost.COST_20,
                items = listOf(
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.SANITY_MEDICATION_1,
                        flavor = TierFlavorText.SANITY_MEDICATION_1,
                        info = TierInformation.SANITY_MEDICATION_1,
                        upgradeCostData = UpgradeCost.COST_0,
                        upgradeLevelData = UnlockLevel.LEVEL_16,
                        positiveAttributes = listOf(
                            EquipmentAttribute.SANITY_RESTORATION_30
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_CONSUMABLE
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.SANITY_MEDICATION_2,
                        flavor = TierFlavorText.SANITY_MEDICATION_2,
                        info = TierInformation.SANITY_MEDICATION_2,
                        upgradeCostData = UpgradeCost.COST_2000,
                        upgradeLevelData = UnlockLevel.LEVEL_39,
                        positiveAttributes = listOf(
                            EquipmentAttribute.SANITY_RESTORATION_20
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_CONSUMABLE
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.SANITY_MEDICATION_3,
                        flavor = TierFlavorText.SANITY_MEDICATION_3,
                        info = TierInformation.SANITY_MEDICATION_3,
                        upgradeCostData = UpgradeCost.COST_5000,
                        upgradeLevelData = UnlockLevel.LEVEL_77,
                        positiveAttributes = listOf(
                            EquipmentAttribute.SANITY_RESTORATION_10,
                            EquipmentAttribute.PROPERTY_SPRINT_BOOST,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_CONSUMABLE
                        )
                    )
                )
            ),
            // Ghost Writing Book
            CodexEquipmentGroupResourceDto(
                name = EquipmentTitles.GHOST_WRITING_BOOK,
                icon = EquipmentIcon.GHOST_WRITING_BOOK,
                buyCostData = EquipmentBuyCost.COST_40,
                items = listOf(
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.GHOST_WRITING_BOOK_1,
                        flavor = TierFlavorText.GHOST_WRITING_BOOK_1,
                        info = TierInformation.GHOST_WRITING_BOOK_1,
                        upgradeCostData = UpgradeCost.COST_0,
                        upgradeLevelData = UnlockLevel.LEVEL_0,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_3
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.INTERACTION_RATE_LOW
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.GHOST_WRITING_BOOK_2,
                        flavor = TierFlavorText.GHOST_WRITING_BOOK_2,
                        info = TierInformation.GHOST_WRITING_BOOK_2,
                        upgradeCostData = UpgradeCost.COST_3000,
                        upgradeLevelData = UnlockLevel.LEVEL_23,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_4,
                            EquipmentAttribute.INTERACTION_RATE_MED,
                        ),
                        negativeAttributes = emptyList(/* NONE */)
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.GHOST_WRITING_BOOK_3,
                        flavor = TierFlavorText.GHOST_WRITING_BOOK_3,
                        info = TierInformation.GHOST_WRITING_BOOK_3,
                        upgradeCostData = UpgradeCost.COST_3000,
                        upgradeLevelData = UnlockLevel.LEVEL_63,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_5,
                            EquipmentAttribute.INTERACTION_RATE_HIGH,
                        ),
                        negativeAttributes = emptyList(/* NONE */)
                    )
                )
            ),
            // Parabolic Microphone
            CodexEquipmentGroupResourceDto(
                name = EquipmentTitles.PARABOLIC_MICROPHONE,
                icon = EquipmentIcon.PARABOLIC_MICROPHONE,
                buyCostData = EquipmentBuyCost.COST_50,
                items = listOf(
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.PARABOLIC_MICROPHONE_1,
                        flavor = TierFlavorText.PARABOLIC_MICROPHONE_1,
                        info = TierInformation.PARABOLIC_MICROPHONE_1,
                        upgradeCostData = UpgradeCost.COST_0,
                        upgradeLevelData = UnlockLevel.LEVEL_7,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_20
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.PARABOLIC_MICROPHONE_2,
                        flavor = TierFlavorText.PARABOLIC_MICROPHONE_2,
                        info = TierInformation.PARABOLIC_MICROPHONE_2,
                        upgradeCostData = UpgradeCost.COST_3000,
                        upgradeLevelData = UnlockLevel.LEVEL_31,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_30,
                            EquipmentAttribute.PROPERTY_DISPLAY_SCREEN,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.PARABOLIC_MICROPHONE_3,
                        flavor = TierFlavorText.PARABOLIC_MICROPHONE_3,
                        info = TierInformation.PARABOLIC_MICROPHONE_3,
                        upgradeCostData = UpgradeCost.COST_5000,
                        upgradeLevelData = UnlockLevel.LEVEL_72,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_30,
                            EquipmentAttribute.PROPERTY_DISPLAY_SCREEN,
                            EquipmentAttribute.INDICATOR_DISTANCE,
                            EquipmentAttribute.INDICATOR_DIRECTIONAL,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    )
                )
            ),
            // Headgear
            CodexEquipmentGroupResourceDto(
                name = EquipmentTitles.HEAD_GEAR,
                icon = EquipmentIcon.HEAD_GEAR,
                buyCostData = EquipmentBuyCost.COST_60,
                items = listOf(
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.HEAD_GEAR_1,
                        flavor = TierFlavorText.HEAD_GEAR_1,
                        info = TierInformation.HEAD_GEAR_1,
                        upgradeCostData = UpgradeCost.COST_0,
                        upgradeLevelData = UnlockLevel.LEVEL_13,
                        positiveAttributes = listOf(
                            EquipmentAttribute.PROPERTY_HEAD_SLOT,
                            EquipmentAttribute.IMAGE_QUALITY_MED,
                            EquipmentAttribute.INTERFERENCE_MED,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.HEAD_GEAR_2,
                        flavor = TierFlavorText.HEAD_GEAR_2,
                        info = TierInformation.HEAD_GEAR_2,
                        upgradeCostData = UpgradeCost.COST_10000,
                        upgradeLevelData = UnlockLevel.LEVEL_49,
                        positiveAttributes = listOf(
                            EquipmentAttribute.PROPERTY_HEAD_SLOT,
                            EquipmentAttribute.INTENSITY_LOW,
                            EquipmentAttribute.SPOTLIGHT_NARROW,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    CodexEquipmentGroupItemResourceDto(
                        image = TierImage.HEAD_GEAR_3,
                        flavor = TierFlavorText.HEAD_GEAR_3,
                        info = TierInformation.HEAD_GEAR_3,
                        upgradeCostData = UpgradeCost.COST_10000,
                        upgradeLevelData = UnlockLevel.LEVEL_82,
                        positiveAttributes = listOf(
                            EquipmentAttribute.PROPERTY_HEAD_SLOT,
                            EquipmentAttribute.PROPERTY_NIGHT_VISION,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.INTERFERENCE_MED,
                            EquipmentAttribute.PROPERTY_ELECTRONIC,
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

    private data class CodexEquipmentGroupResourceDto(
        val name: EquipmentTitles,
        val icon: EquipmentIcon,
        val buyCostData: EquipmentBuyCost,
        val items: List<CodexEquipmentGroupItemResourceDto>
    )

    private data class CodexEquipmentGroupItemResourceDto(
        val image: TierImage,
        val flavor: TierFlavorText,
        val info: TierInformation,
        var upgradeCostData: UpgradeCost,
        var upgradeLevelData: UnlockLevel,
        val positiveAttributes: List<EquipmentAttribute>,
        val negativeAttributes: List<EquipmentAttribute>
    )

    private fun CodexEquipmentGroupResourceDto.toLocal() = CodexEquipmentGroupDto(
        name = name,
        icon = icon,
        buyCostData = buyCostData,
        items = items.toLocal()
    )

    private fun CodexEquipmentGroupItemResourceDto.toLocal() = CodexEquipmentGroupItemDto(
        image = image,
        flavor = flavor,
        info = info,
        upgradeCostData = upgradeCostData,
        upgradeLevelData = upgradeLevelData,
        positiveAttributes = positiveAttributes,
        negativeAttributes = negativeAttributes
    )

    @JvmName("CodexEquipmentGroupResListToLocalCodexEquipmentGroupRes")
    private fun List<CodexEquipmentGroupResourceDto>.toLocal() = map { dto ->
        dto.toLocal()
    }

    @JvmName("CodexEquipmentGroupItemResListToLocalCodexEquipmentGroupItemRes")
    private fun List<CodexEquipmentGroupItemResourceDto>.toLocal() = map { dto ->
        dto.toLocal()
    }

}



