package com.tritiumgaming.data.codex.source.local

import com.tritiumgaming.data.codex.dto.EquipmentTypeDto
import com.tritiumgaming.data.codex.dto.EquipmentTypeTierDto
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentAttribute
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentBuyCost
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIcon
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentIdentifier
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTierFlavorText
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTierImage
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTierInformation
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentTitle
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentUnlockLevel
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources.EquipmentUpgradeCost

class EquipmentLocalDataSource {

    private val equipmentResources: List<EquipmentTypeResourceDto>
        get() = listOf(
            EquipmentTypeResourceDto(
                id = EquipmentIdentifier.DOTS,
                name = EquipmentTitle.DOTS,
                icon = EquipmentIcon.DOTS,
                buyCost = EquipmentBuyCost.DOTS,
                items = listOf(
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.DOTS_1,
                        flavor = EquipmentTierFlavorText.DOTS_1,
                        info = EquipmentTierInformation.DOTS_1,
                        upgradeCost = EquipmentUpgradeCost.COST_0,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_0,
                        positiveAttributes = listOf(
                            EquipmentAttribute.SPOTLIGHT_NARROW,
                            EquipmentAttribute.RANGE_5,
                            EquipmentAttribute.HANDHELD
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.DOTS_2,
                        flavor = EquipmentTierFlavorText.DOTS_2,
                        info = EquipmentTierInformation.DOTS_2,
                        upgradeCost = EquipmentUpgradeCost.COST_3000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_27,
                        positiveAttributes = listOf(
                            EquipmentAttribute.SPOTLIGHT_AREA,
                            EquipmentAttribute.RANGE_2_5,
                            EquipmentAttribute.PLACEABLE
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.DOTS_3,
                        flavor = EquipmentTierFlavorText.DOTS_3,
                        info = EquipmentTierInformation.DOTS_3,
                        upgradeCost = EquipmentUpgradeCost.COST_3000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_49,
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
            EquipmentTypeResourceDto(
                id = EquipmentIdentifier.EMF,
                name = EquipmentTitle.EMF,
                icon = EquipmentIcon.EMF,
                buyCost = EquipmentBuyCost.EMF,
                items = listOf(
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.EMF_1,
                        flavor = EquipmentTierFlavorText.EMF_1,
                        info = EquipmentTierInformation.EMF_1,
                        upgradeCost = EquipmentUpgradeCost.COST_0,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_0,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_1_7,
                            EquipmentAttribute.ACCURACY_LOW
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.EMF_2,
                        flavor = EquipmentTierFlavorText.EMF_2,
                        info = EquipmentTierInformation.EMF_2,
                        upgradeCost = EquipmentUpgradeCost.COST_3000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_18,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_1_7,
                            EquipmentAttribute.ACCURACY_HIGH,
                            EquipmentAttribute.INDICATOR_AUDIO
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.EMF_3,
                        flavor = EquipmentTierFlavorText.EMF_3,
                        info = EquipmentTierInformation.EMF_3,
                        upgradeCost = EquipmentUpgradeCost.COST_4500,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_46,
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
            EquipmentTypeResourceDto(
                id = EquipmentIdentifier.FLASHLIGHT,
                name = EquipmentTitle.FLASHLIGHT,
                icon = EquipmentIcon.FLASHLIGHT,
                buyCost = EquipmentBuyCost.FLASHLIGHT,
                items = listOf(
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.FLASHLIGHT_1,
                        flavor = EquipmentTierFlavorText.FLASHLIGHT_1,
                        info = EquipmentTierInformation.FLASHLIGHT_1,
                        upgradeCost = EquipmentUpgradeCost.COST_0,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_0,
                        positiveAttributes = listOf(
                            EquipmentAttribute.SPOTLIGHT_NARROW
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.INTENSITY_LOW,
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.FLASHLIGHT_2,
                        flavor = EquipmentTierFlavorText.FLASHLIGHT_2,
                        info = EquipmentTierInformation.FLASHLIGHT_2,
                        upgradeCost = EquipmentUpgradeCost.COST_3000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_18,
                        positiveAttributes = listOf(
                            EquipmentAttribute.SPOTLIGHT_NARROW,
                            EquipmentAttribute.INTENSITY_MED
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.FLASHLIGHT_3,
                        flavor = EquipmentTierFlavorText.FLASHLIGHT_3,
                        info = EquipmentTierInformation.FLASHLIGHT_3,
                        upgradeCost = EquipmentUpgradeCost.COST_3000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_34,
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
            EquipmentTypeResourceDto(
                id = EquipmentIdentifier.PHOTO_CAMERA,
                name = EquipmentTitle.PHOTO_CAMERA,
                icon = EquipmentIcon.PHOTO_CAMERA,
                buyCost = EquipmentBuyCost.PHOTO_CAMERA,
                items = listOf(
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.PHOTO_CAMERA_1,
                        flavor = EquipmentTierFlavorText.PHOTO_CAMERA_1,
                        info = EquipmentTierInformation.PHOTO_CAMERA_1,
                        upgradeCost = EquipmentUpgradeCost.COST_0,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_2,
                        positiveAttributes = emptyList(/* NONE */),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PHOTO_TIME_HIGH
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.PHOTO_CAMERA_2,
                        flavor = EquipmentTierFlavorText.PHOTO_CAMERA_2,
                        info = EquipmentTierInformation.PHOTO_CAMERA_2,
                        upgradeCost = EquipmentUpgradeCost.COST_3000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_23,
                        positiveAttributes = listOf(
                            EquipmentAttribute.PHOTO_TIME_MED,
                            EquipmentAttribute.PROPERTY_DISPLAY_SCREEN
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.INTERFERENCE_MED,
                            EquipmentAttribute.PROPERTY_ELECTRONIC,
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.PHOTO_CAMERA_3,
                        flavor = EquipmentTierFlavorText.PHOTO_CAMERA_3,
                        info = EquipmentTierInformation.PHOTO_CAMERA_3,
                        upgradeCost = EquipmentUpgradeCost.COST_5000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_55,
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
            EquipmentTypeResourceDto(
                id = EquipmentIdentifier.IGNITER,
                name = EquipmentTitle.IGNITER,
                icon = EquipmentIcon.IGNITER,
                buyCost = EquipmentBuyCost.IGNITER,
                items = listOf(
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.IGNITER_1,
                        flavor = EquipmentTierFlavorText.IGNITER_1,
                        info = EquipmentTierInformation.IGNITER_1,
                        upgradeCost = EquipmentUpgradeCost.COST_0,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_12,
                        positiveAttributes = listOf(
                            EquipmentAttribute.DURATION_10_S,
                            EquipmentAttribute.USES_10
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_CONSUMABLE
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.IGNITER_2,
                        flavor = EquipmentTierFlavorText.IGNITER_2,
                        info = EquipmentTierInformation.IGNITER_2,
                        upgradeCost = EquipmentUpgradeCost.COST_500,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_37,
                        positiveAttributes = listOf(
                            EquipmentAttribute.DURATION_5_M
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_CONSUMABLE
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.IGNITER_3,
                        flavor = EquipmentTierFlavorText.IGNITER_3,
                        info = EquipmentTierInformation.IGNITER_3,
                        upgradeCost = EquipmentUpgradeCost.COST_750,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_52,
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
            EquipmentTypeResourceDto(
                id = EquipmentIdentifier.FIRELIGHT,
                name = EquipmentTitle.FIRELIGHT,
                icon = EquipmentIcon.FIRELIGHT,
                buyCost = EquipmentBuyCost.FIRELIGHT,
                items = listOf(
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.FIRELIGHT_1,
                        flavor = EquipmentTierFlavorText.FIRELIGHT_1,
                        info = EquipmentTierInformation.FIRELIGHT_1,
                        upgradeCost = EquipmentUpgradeCost.COST_0,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_12,
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
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.FIRELIGHT_2,
                        flavor = EquipmentTierFlavorText.FIRELIGHT_2,
                        info = EquipmentTierInformation.FIRELIGHT_2,
                        upgradeCost = EquipmentUpgradeCost.COST_3000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_37,
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
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.FIRELIGHT_3,
                        flavor = EquipmentTierFlavorText.FIRELIGHT_3,
                        info = EquipmentTierInformation.FIRELIGHT_3,
                        upgradeCost = EquipmentUpgradeCost.COST_10000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_75,
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
            EquipmentTypeResourceDto(
                id = EquipmentIdentifier.UV_LIGHT,
                name = EquipmentTitle.UV_LIGHT,
                icon = EquipmentIcon.UV_LIGHT,
                buyCost = EquipmentBuyCost.UV_LIGHT,
                items = listOf(
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.UV_LIGHT_1,
                        flavor = EquipmentTierFlavorText.UV_LIGHT_1,
                        info = EquipmentTierInformation.UV_LIGHT_1,
                        upgradeCost = EquipmentUpgradeCost.COST_0,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_0,
                        positiveAttributes = listOf(
                            EquipmentAttribute.DURATION_60_S,
                            EquipmentAttribute.UV_CHARGE_TIME_10,
                            EquipmentAttribute.SPOTLIGHT_AREA,
                        ),
                        negativeAttributes = emptyList(/* NONE */)
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.UV_LIGHT_2,
                        flavor = EquipmentTierFlavorText.UV_LIGHT_2,
                        info = EquipmentTierInformation.UV_LIGHT_2,
                        upgradeCost = EquipmentUpgradeCost.COST_3000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_18,
                        positiveAttributes = listOf(
                            EquipmentAttribute.UV_CHARGE_TIME_5,
                            EquipmentAttribute.SPOTLIGHT_NARROW,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.UV_LIGHT_3,
                        flavor = EquipmentTierFlavorText.UV_LIGHT_3,
                        info = EquipmentTierInformation.UV_LIGHT_3,
                        upgradeCost = EquipmentUpgradeCost.COST_2000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_46,
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
            EquipmentTypeResourceDto(
                id = EquipmentIdentifier.CRUCIFIX,
                name = EquipmentTitle.CRUCIFIX,
                icon = EquipmentIcon.CRUCIFIX,
                buyCost = EquipmentBuyCost.CRUCIFIX,
                items = listOf(
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.CRUCIFIX_1,
                        flavor = EquipmentTierFlavorText.CRUCIFIX_1,
                        info = EquipmentTierInformation.CRUCIFIX_1,
                        upgradeCost = EquipmentUpgradeCost.COST_0,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_7,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_3,
                            EquipmentAttribute.USES_1,
                            EquipmentAttribute.PLACEABLE,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_CONSUMABLE
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.CRUCIFIX_2,
                        flavor = EquipmentTierFlavorText.CRUCIFIX_2,
                        info = EquipmentTierInformation.CRUCIFIX_2,
                        upgradeCost = EquipmentUpgradeCost.COST_4000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_34,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_4,
                            EquipmentAttribute.USES_2,
                            EquipmentAttribute.PLACEABLE,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_CONSUMABLE
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.CRUCIFIX_3,
                        flavor = EquipmentTierFlavorText.CRUCIFIX_3,
                        info = EquipmentTierInformation.CRUCIFIX_3,
                        upgradeCost = EquipmentUpgradeCost.COST_20000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_80,
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
            EquipmentTypeResourceDto(
                id = EquipmentIdentifier.VIDEO_CAMERA,
                name = EquipmentTitle.VIDEO_CAMERA,
                icon = EquipmentIcon.VIDEO_CAMERA,
                buyCost = EquipmentBuyCost.VIDEO_CAMERA,
                items = listOf(
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.VIDEO_CAMERA_1,
                        flavor = EquipmentTierFlavorText.VIDEO_CAMERA_1,
                        info = EquipmentTierInformation.VIDEO_CAMERA_1,
                        upgradeCost = EquipmentUpgradeCost.COST_0,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_0,
                        positiveAttributes = emptyList(/* NONE */),
                        negativeAttributes = listOf(
                            EquipmentAttribute.INTERFERENCE_HIGH,
                            EquipmentAttribute.PROPERTY_ELECTRONIC,
                            EquipmentAttribute.KNOCKDOWN_CHANCE_HIGH,
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.VIDEO_CAMERA_2,
                        flavor = EquipmentTierFlavorText.VIDEO_CAMERA_2,
                        info = EquipmentTierInformation.VIDEO_CAMERA_2,
                        upgradeCost = EquipmentUpgradeCost.COST_3000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_27,
                        positiveAttributes = listOf(
                            EquipmentAttribute.IMAGE_QUALITY_MED
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.INTERFERENCE_MED,
                            EquipmentAttribute.PROPERTY_ELECTRONIC,
                            EquipmentAttribute.KNOCKDOWN_CHANCE_HIGH,
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.VIDEO_CAMERA_3,
                        flavor = EquipmentTierFlavorText.VIDEO_CAMERA_3,
                        info = EquipmentTierInformation.VIDEO_CAMERA_3,
                        upgradeCost = EquipmentUpgradeCost.COST_3000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_49,
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
            EquipmentTypeResourceDto(
                id = EquipmentIdentifier.SPIRIT_BOX,
                name = EquipmentTitle.SPIRIT_BOX,
                icon = EquipmentIcon.SPIRIT_BOX,
                buyCost = EquipmentBuyCost.SPIRIT_BOX,
                items = listOf(
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.SPIRIT_BOX_1,
                        flavor = EquipmentTierFlavorText.SPIRIT_BOX_1,
                        info = EquipmentTierInformation.SPIRIT_BOX_1,
                        upgradeCost = EquipmentUpgradeCost.COST_0,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_0,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_3
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.AUDIO_QUALITY_LOW,
                            EquipmentAttribute.RESPONSE_RATE_LOW,
                            EquipmentAttribute.PROPERTY_ELECTRONIC,
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.SPIRIT_BOX_2,
                        flavor = EquipmentTierFlavorText.SPIRIT_BOX_2,
                        info = EquipmentTierInformation.SPIRIT_BOX_2,
                        upgradeCost = EquipmentUpgradeCost.COST_3000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_23,
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
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.SPIRIT_BOX_3,
                        flavor = EquipmentTierFlavorText.SPIRIT_BOX_3,
                        info = EquipmentTierInformation.SPIRIT_BOX_3,
                        upgradeCost = EquipmentUpgradeCost.COST_3000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_46,
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
            EquipmentTypeResourceDto(
                id = EquipmentIdentifier.THERMOMETER,
                name = EquipmentTitle.THERMOMETER,
                icon = EquipmentIcon.THERMOMETER,
                buyCost = EquipmentBuyCost.THERMOMETER,
                items = listOf(
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.THERMOMETER_1,
                        flavor = EquipmentTierFlavorText.THERMOMETER_1,
                        info = EquipmentTierInformation.THERMOMETER_1,
                        upgradeCost = EquipmentUpgradeCost.COST_0,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_0,
                        positiveAttributes = listOf(
                            EquipmentAttribute.ACCURACY_MED
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.SAMPLE_SPEED_LOW
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.THERMOMETER_2,
                        flavor = EquipmentTierFlavorText.THERMOMETER_2,
                        info = EquipmentTierInformation.THERMOMETER_2,
                        upgradeCost = EquipmentUpgradeCost.COST_3000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_27,
                        positiveAttributes = listOf(
                            EquipmentAttribute.ACCURACY_HIGH,
                            EquipmentAttribute.SAMPLE_SPEED_HIGH
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.THERMOMETER_3,
                        flavor = EquipmentTierFlavorText.THERMOMETER_3,
                        info = EquipmentTierInformation.THERMOMETER_3,
                        upgradeCost = EquipmentUpgradeCost.COST_3000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_65,
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
            EquipmentTypeResourceDto(
                id = EquipmentIdentifier.SALT,
                name = EquipmentTitle.SALT,
                icon = EquipmentIcon.SALT,
                buyCost = EquipmentBuyCost.SALT,
                items = listOf(
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.SALT_1,
                        flavor = EquipmentTierFlavorText.SALT_1,
                        info = EquipmentTierInformation.SALT_1,
                        upgradeCost = EquipmentUpgradeCost.COST_0,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_8,
                        positiveAttributes = listOf(
                            EquipmentAttribute.USES_2
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_CONSUMABLE
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.SALT_2,
                        flavor = EquipmentTierFlavorText.SALT_2,
                        info = EquipmentTierInformation.SALT_2,
                        upgradeCost = EquipmentUpgradeCost.COST_2500,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_39,
                        positiveAttributes = listOf(
                            EquipmentAttribute.USES_3
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_CONSUMABLE
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.SALT_3,
                        flavor = EquipmentTierFlavorText.SALT_3,
                        info = EquipmentTierInformation.SALT_3,
                        upgradeCost = EquipmentUpgradeCost.COST_5000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_65,
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
            EquipmentTypeResourceDto(
                id = EquipmentIdentifier.INCENSE,
                name = EquipmentTitle.INCENSE,
                icon = EquipmentIcon.INCENSE,
                buyCost = EquipmentBuyCost.INCENSE,
                items = listOf(
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.INCENSE_1,
                        flavor = EquipmentTierFlavorText.INCENSE_1,
                        info = EquipmentTierInformation.INCENSE_1,
                        upgradeCost = EquipmentUpgradeCost.COST_0,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_14,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_3,
                            EquipmentAttribute.DURATION_5_S,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_CONSUMABLE
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.INCENSE_2,
                        flavor = EquipmentTierFlavorText.INCENSE_2,
                        info = EquipmentTierInformation.INCENSE_2,
                        upgradeCost = EquipmentUpgradeCost.COST_3500,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_37,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_4,
                            EquipmentAttribute.DURATION_6_S,
                            EquipmentAttribute.PROPERTY_GHOST_EFFECT_SLOWS,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_CONSUMABLE
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.INCENSE_3,
                        flavor = EquipmentTierFlavorText.INCENSE_3,
                        info = EquipmentTierInformation.INCENSE_3,
                        upgradeCost = EquipmentUpgradeCost.COST_15000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_80,
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
            EquipmentTypeResourceDto(
                id = EquipmentIdentifier.TRIPOD,
                name = EquipmentTitle.TRIPOD,
                icon = EquipmentIcon.TRIPOD,
                buyCost = EquipmentBuyCost.TRIPOD,
                items = listOf(
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.TRIPOD_1,
                        flavor = EquipmentTierFlavorText.TRIPOD_1,
                        info = EquipmentTierInformation.TRIPOD_1,
                        upgradeCost = EquipmentUpgradeCost.COST_0,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_9,
                        positiveAttributes = listOf(
                            EquipmentAttribute.PLACEABLE
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.KNOCKDOWN_CHANCE_MED
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.TRIPOD_2,
                        flavor = EquipmentTierFlavorText.TRIPOD_2,
                        info = EquipmentTierInformation.TRIPOD_2,
                        upgradeCost = EquipmentUpgradeCost.COST_5000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_34,
                        positiveAttributes = listOf(
                            EquipmentAttribute.PLACEABLE,
                            EquipmentAttribute.PROPERTY_ROTATION_CONTROL,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.KNOCKDOWN_CHANCE_MED
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.TRIPOD_3,
                        flavor = EquipmentTierFlavorText.TRIPOD_3,
                        info = EquipmentTierInformation.TRIPOD_3,
                        upgradeCost = EquipmentUpgradeCost.COST_3000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_60,
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
            EquipmentTypeResourceDto(
                id = EquipmentIdentifier.MOTION_SENSOR,
                name = EquipmentTitle.MOTION_SENSOR,
                icon = EquipmentIcon.MOTION_SENSOR,
                buyCost = EquipmentBuyCost.MOTION_SENSOR,
                items = listOf(
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.MOTION_SENSOR_1,
                        flavor = EquipmentTierFlavorText.MOTION_SENSOR_1,
                        info = EquipmentTierInformation.MOTION_SENSOR_1,
                        upgradeCost = EquipmentUpgradeCost.COST_0,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_3,
                        positiveAttributes = listOf(
                            EquipmentAttribute.SENSOR_SHAPE_LINE,
                            EquipmentAttribute.INDICATOR_LIGHT,
                            EquipmentAttribute.PLACEABLE,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.MOTION_SENSOR_2,
                        flavor = EquipmentTierFlavorText.MOTION_SENSOR_2,
                        info = EquipmentTierInformation.MOTION_SENSOR_2,
                        upgradeCost = EquipmentUpgradeCost.COST_2500,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_42,
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
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.MOTION_SENSOR_3,
                        flavor = EquipmentTierFlavorText.MOTION_SENSOR_3,
                        info = EquipmentTierInformation.MOTION_SENSOR_3,
                        upgradeCost = EquipmentUpgradeCost.COST_8000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_70,
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
            EquipmentTypeResourceDto(
                id = EquipmentIdentifier.SOUND_RECORDER,
                name = EquipmentTitle.SOUND_RECORDER,
                icon = EquipmentIcon.SOUND_RECORDER,
                buyCost = EquipmentBuyCost.SOUND_RECORDER,
                items = listOf(
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.SOUND_RECORDER_1,
                        flavor = EquipmentTierFlavorText.SOUND_RECORDER_1,
                        info = EquipmentTierInformation.SOUND_RECORDER_1,
                        upgradeCost = EquipmentUpgradeCost.COST_0,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_4,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_3,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.SOUND_RECORDER_2,
                        flavor = EquipmentTierFlavorText.SOUND_RECORDER_2,
                        info = EquipmentTierInformation.SOUND_RECORDER_2,
                        upgradeCost = EquipmentUpgradeCost.COST_3000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_39,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_5,
                            EquipmentAttribute.PROPERTY_DISPLAY_SCREEN,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.SOUND_RECORDER_3,
                        flavor = EquipmentTierFlavorText.SOUND_RECORDER_3,
                        info = EquipmentTierInformation.SOUND_RECORDER_3,
                        upgradeCost = EquipmentUpgradeCost.COST_5000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_60,
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
            EquipmentTypeResourceDto(
                id = EquipmentIdentifier.SOUND_SENSOR,
                name = EquipmentTitle.SOUND_SENSOR,
                icon = EquipmentIcon.SOUND_SENSOR,
                buyCost = EquipmentBuyCost.SOUND_SENSOR,
                items = listOf(
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.SOUND_SENSOR_1,
                        flavor = EquipmentTierFlavorText.SOUND_SENSOR_1,
                        info = EquipmentTierInformation.SOUND_SENSOR_1,
                        upgradeCost = EquipmentUpgradeCost.COST_0,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_10,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_5_10,
                            EquipmentAttribute.SENSOR_SHAPE_CIRCLE,
                            EquipmentAttribute.PLACEABLE,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.SOUND_SENSOR_2,
                        flavor = EquipmentTierFlavorText.SOUND_SENSOR_2,
                        info = EquipmentTierInformation.SOUND_SENSOR_2,
                        upgradeCost = EquipmentUpgradeCost.COST_3000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_32,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_5_10_15,
                            EquipmentAttribute.SENSOR_SHAPE_CIRCLE,
                            EquipmentAttribute.PLACEABLE,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.SOUND_SENSOR_3,
                        flavor = EquipmentTierFlavorText.SOUND_SENSOR_3,
                        info = EquipmentTierInformation.SOUND_SENSOR_3,
                        upgradeCost = EquipmentUpgradeCost.COST_1500,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_52,
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
            EquipmentTypeResourceDto(
                id = EquipmentIdentifier.SANITY_MEDICATION,
                name = EquipmentTitle.SANITY_MEDICATION,
                icon = EquipmentIcon.SANITY_MEDICATION,
                buyCost = EquipmentBuyCost.SANITY_MEDICATION,
                items = listOf(
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.SANITY_MEDICATION_1,
                        flavor = EquipmentTierFlavorText.SANITY_MEDICATION_1,
                        info = EquipmentTierInformation.SANITY_MEDICATION_1,
                        upgradeCost = EquipmentUpgradeCost.COST_0,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_14,
                        positiveAttributes = listOf(
                            EquipmentAttribute.SANITY_RESTORATION_30
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_CONSUMABLE
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.SANITY_MEDICATION_2,
                        flavor = EquipmentTierFlavorText.SANITY_MEDICATION_2,
                        info = EquipmentTierInformation.SANITY_MEDICATION_2,
                        upgradeCost = EquipmentUpgradeCost.COST_2000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_39,
                        positiveAttributes = listOf(
                            EquipmentAttribute.SANITY_RESTORATION_20
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_CONSUMABLE
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.SANITY_MEDICATION_3,
                        flavor = EquipmentTierFlavorText.SANITY_MEDICATION_3,
                        info = EquipmentTierInformation.SANITY_MEDICATION_3,
                        upgradeCost = EquipmentUpgradeCost.COST_5000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_75,
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
            EquipmentTypeResourceDto(
                id = EquipmentIdentifier.GHOST_WRITING_BOOK,
                name = EquipmentTitle.GHOST_WRITING_BOOK,
                icon = EquipmentIcon.GHOST_WRITING_BOOK,
                buyCost = EquipmentBuyCost.GHOST_WRITING_BOOK,
                items = listOf(
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.GHOST_WRITING_BOOK_1,
                        flavor = EquipmentTierFlavorText.GHOST_WRITING_BOOK_1,
                        info = EquipmentTierInformation.GHOST_WRITING_BOOK_1,
                        upgradeCost = EquipmentUpgradeCost.COST_0,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_0,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_3
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.INTERACTION_RATE_LOW
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.GHOST_WRITING_BOOK_2,
                        flavor = EquipmentTierFlavorText.GHOST_WRITING_BOOK_2,
                        info = EquipmentTierInformation.GHOST_WRITING_BOOK_2,
                        upgradeCost = EquipmentUpgradeCost.COST_3000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_23,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_4,
                            EquipmentAttribute.INTERACTION_RATE_MED,
                        ),
                        negativeAttributes = emptyList(/* NONE */)
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.GHOST_WRITING_BOOK_3,
                        flavor = EquipmentTierFlavorText.GHOST_WRITING_BOOK_3,
                        info = EquipmentTierInformation.GHOST_WRITING_BOOK_3,
                        upgradeCost = EquipmentUpgradeCost.COST_3000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_55,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_5,
                            EquipmentAttribute.INTERACTION_RATE_HIGH,
                        ),
                        negativeAttributes = emptyList(/* NONE */)
                    )
                )
            ),
            // Parabolic Microphone
            EquipmentTypeResourceDto(
                id = EquipmentIdentifier.PARABOLIC_MICROPHONE,
                name = EquipmentTitle.PARABOLIC_MICROPHONE,
                icon = EquipmentIcon.PARABOLIC_MICROPHONE,
                buyCost = EquipmentBuyCost.PARABOLIC_MICROPHONE,
                items = listOf(
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.PARABOLIC_MICROPHONE_1,
                        flavor = EquipmentTierFlavorText.PARABOLIC_MICROPHONE_1,
                        info = EquipmentTierInformation.PARABOLIC_MICROPHONE_1,
                        upgradeCost = EquipmentUpgradeCost.COST_0,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_5,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_20
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.PARABOLIC_MICROPHONE_2,
                        flavor = EquipmentTierFlavorText.PARABOLIC_MICROPHONE_2,
                        info = EquipmentTierInformation.PARABOLIC_MICROPHONE_2,
                        upgradeCost = EquipmentUpgradeCost.COST_3000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_32,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_30,
                            EquipmentAttribute.PROPERTY_DISPLAY_SCREEN,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.PARABOLIC_MICROPHONE_3,
                        flavor = EquipmentTierFlavorText.PARABOLIC_MICROPHONE_3,
                        info = EquipmentTierInformation.PARABOLIC_MICROPHONE_3,
                        upgradeCost = EquipmentUpgradeCost.COST_5000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_70,
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
            EquipmentTypeResourceDto(
                id = EquipmentIdentifier.HEAD_GEAR,
                name = EquipmentTitle.HEAD_GEAR,
                icon = EquipmentIcon.HEAD_GEAR,
                buyCost = EquipmentBuyCost.HEAD_GEAR,
                items = listOf(
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.HEAD_GEAR_1,
                        flavor = EquipmentTierFlavorText.HEAD_GEAR_1,
                        info = EquipmentTierInformation.HEAD_GEAR_1,
                        upgradeCost = EquipmentUpgradeCost.COST_0,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_13,
                        positiveAttributes = listOf(
                            EquipmentAttribute.PROPERTY_HEAD_SLOT,
                            EquipmentAttribute.IMAGE_QUALITY_MED,
                            EquipmentAttribute.INTERFERENCE_MED,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.HEAD_GEAR_2,
                        flavor = EquipmentTierFlavorText.HEAD_GEAR_2,
                        info = EquipmentTierInformation.HEAD_GEAR_2,
                        upgradeCost = EquipmentUpgradeCost.COST_10000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_42,
                        positiveAttributes = listOf(
                            EquipmentAttribute.PROPERTY_HEAD_SLOT,
                            EquipmentAttribute.INTENSITY_LOW,
                            EquipmentAttribute.SPOTLIGHT_NARROW,
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    EquipmentTypeTierResourceDto(
                        image = EquipmentTierImage.HEAD_GEAR_3,
                        flavor = EquipmentTierFlavorText.HEAD_GEAR_3,
                        info = EquipmentTierInformation.HEAD_GEAR_3,
                        upgradeCost = EquipmentUpgradeCost.COST_10000,
                        upgradeLevel = EquipmentUnlockLevel.LEVEL_80,
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

    fun fetchItems(): Result<List<EquipmentTypeDto>> {

        val cursedEquipmentGroupsDto = mutableListOf<EquipmentTypeDto>()

        equipmentResources.forEach { resDto ->
            cursedEquipmentGroupsDto.add(resDto.toLocal())
        }

        return Result.success(cursedEquipmentGroupsDto)

    }

    private data class EquipmentTypeResourceDto(
        val id: EquipmentIdentifier,
        val name: EquipmentTitle,
        val icon: EquipmentIcon,
        val buyCost: EquipmentBuyCost,
        val items: List<EquipmentTypeTierResourceDto>
    )

    private data class EquipmentTypeTierResourceDto(
        val image: EquipmentTierImage,
        val flavor: EquipmentTierFlavorText,
        val info: EquipmentTierInformation,
        var upgradeCost: EquipmentUpgradeCost,
        var upgradeLevel: EquipmentUnlockLevel,
        val positiveAttributes: List<EquipmentAttribute>,
        val negativeAttributes: List<EquipmentAttribute>
    )

    private fun EquipmentTypeResourceDto.toLocal() = EquipmentTypeDto(
        id = id,
        name = name,
        icon = icon,
        buyCost = buyCost,
        items = items.toLocal()
    )

    private fun EquipmentTypeTierResourceDto.toLocal() = EquipmentTypeTierDto(
        image = image,
        flavor = flavor,
        info = info,
        upgradeCost = upgradeCost,
        upgradeLevel = upgradeLevel,
        positiveAttributes = positiveAttributes,
        negativeAttributes = negativeAttributes
    )

    @JvmName("CodexEquipmentGroupResListToLocalCodexEquipmentGroupRes")
    private fun List<EquipmentTypeResourceDto>.toLocal() = map { dto ->
        dto.toLocal()
    }

    @JvmName("CodexEquipmentGroupItemResListToLocalCodexEquipmentGroupItemRes")
    private fun List<EquipmentTypeTierResourceDto>.toLocal() = map { dto ->
        dto.toLocal()
    }

}



