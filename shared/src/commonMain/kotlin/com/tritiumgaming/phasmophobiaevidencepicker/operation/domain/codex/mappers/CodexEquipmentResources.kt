package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers

class CodexEquipmentResources {

    enum class EquipmentTitles {
        DOTS,
        EMF,
        FLASHLIGHT,
        PHOTO_CAMERA,
        IGNITER,
        FIRELIGHT,
        UV_LIGHT,
        CRUCIFIX,
        VIDEO_CAMERA,
        SPIRIT_BOX,
        THERMOMETER,
        SALT,
        INCENSE,
        TRIPOD,
        MOTION_SENSOR,
        SOUND_SENSOR,
        SANITY_MEDICATION,
        GHOST_WRITING_BOOK,
        PARABOLIC_MICROPHONE,
        HEAD_GEAR
    }

    enum class EquipmentIcon {
        DOTS,
        EMF,
        FLASHLIGHT,
        PHOTO_CAMERA,
        IGNITER,
        FIRELIGHT,
        UV_LIGHT,
        CRUCIFIX,
        VIDEO_CAMERA,
        SPIRIT_BOX,
        THERMOMETER,
        SALT,
        INCENSE,
        TRIPOD,
        MOTION_SENSOR,
        SOUND_SENSOR,
        SANITY_MEDICATION,
        GHOST_WRITING_BOOK,
        PARABOLIC_MICROPHONE,
        HEAD_GEAR
    }

    enum class EquipmentBuyCost {
        COST_10,
        COST_15,
        COST_20,
        COST_25,
        COST_30,
        COST_40,
        COST_45,
        COST_50,
        COST_60,
        COST_65,
        COST_80,
        COST_100
    }

    enum class EquipmentAttribute {
        SPOTLIGHT_NARROW,
        SPOTLIGHT_WIDE,
        SPOTLIGHT_AREA,
        INTENSITY_LOW,
        INTENSITY_MED,
        INTENSITY_HIGH,
        INTERACTION_RATE_LOW,
        INTERACTION_RATE_MED,
        INTERACTION_RATE_HIGH,
        AUDIO_QUALITY_LOW,
        AUDIO_QUALITY_MED,
        AUDIO_QUALITY_HIGH,
        RESPONSE_RATE_LOW,
        RESPONSE_RATE_MED,
        RESPONSE_RATE_HIGH,
        SAMPLE_SPEED_LOW,
        SAMPLE_SPEED_MED,
        SAMPLE_SPEED_HIGH,
        IMAGE_QUALITY_LOW,
        IMAGE_QUALITY_MED,
        IMAGE_QUALITY_HIGH,
        INTERFERENCE_LOW,
        INTERFERENCE_MED,
        INTERFERENCE_HIGH,
        KNOCKDOWN_CHANCE_LOW,
        KNOCKDOWN_CHANCE_MED,
        KNOCKDOWN_CHANCE_HIGH,
        ACCURACY_LOW,
        ACCURACY_MED,
        ACCURACY_HIGH,
        INDICATOR_AUDIO,
        INDICATOR_LIGHT,
        INDICATOR_DISTANCE,
        INDICATOR_DIRECTIONAL,
        RANGE_1_7,
        RANGE_2,
        RANGE_2_5,
        RANGE_3,
        RANGE_3_5,
        RANGE_4,
        RANGE_5,
        RANGE_7,
        RANGE_20,
        RANGE_30,
        RANGE_5_10,
        RANGE_5_10_15,
        UV_CHARGE_TIME_1_5,
        UV_CHARGE_TIME_5,
        UV_CHARGE_TIME_10,
        PHOTO_TIME_LOW,
        PHOTO_TIME_MED,
        PHOTO_TIME_HIGH,
        SANITY_DRAIN_REDUCTION_33,
        SANITY_DRAIN_REDUCTION_50,
        SANITY_DRAIN_REDUCTION_66,
        SANITY_RESTORATION_10,
        SANITY_RESTORATION_20,
        SANITY_RESTORATION_30,
        SENSOR_SHAPE_LINE,
        SENSOR_SHAPE_LINE_CONE,
        SENSOR_SHAPE_CIRCLE,
        SENSOR_SHAPE_CIRCLE_CONE_SIDES,
        DURATION_3_S,
        DURATION_5_S,
        DURATION_6_S,
        DURATION_7_S,
        DURATION_10_S,
        DURATION_60_S,
        DURATION_3_M,
        DURATION_5_M,
        DURATION_10_M,
        USES_1,
        USES_2,
        USES_3,
        USES_10,
        PLACEABLE,
        HANDHELD,
        PROPERTY_GHOST_EFFECT_SLOWS,
        PROPERTY_GHOST_EFFECT_HALTS,
        PROPERTY_DISPLAY_SCREEN,
        PROPERTY_PREVENT_CURSED_HUNT,
        PROPERTY_ELECTRONIC,
        PROPERTY_CONSUMABLE,
        PROPERTY_WATERPROOF,
        PROPERTY_HEAD_SLOT,
        PROPERTY_NIGHT_VISION,
        PROPERTY_SPRINT_BOOST,
        PROPERTY_ROTATION_CONTROL,
        PROPERTY_SCANNING
    }

    enum class TierInformation {
        DOTS_1,
        DOTS_2,
        DOTS_3,
        EMF_1,
        EMF_2,
        EMF_3,
        FLASHLIGHT_1,
        FLASHLIGHT_2,
        FLASHLIGHT_3,
        PHOTO_CAMERA_1,
        PHOTO_CAMERA_2,
        PHOTO_CAMERA_3,
        IGNITER_1,
        IGNITER_2,
        IGNITER_3,
        FIRELIGHT_1,
        FIRELIGHT_2,
        FIRELIGHT_3,
        UV_LIGHT_1,
        UV_LIGHT_2,
        UV_LIGHT_3,
        CRUCIFIX_1,
        CRUCIFIX_2,
        CRUCIFIX_3,
        VIDEO_CAMERA_1,
        VIDEO_CAMERA_2,
        VIDEO_CAMERA_3,
        SPIRIT_BOX_1,
        SPIRIT_BOX_2,
        SPIRIT_BOX_3,
        THERMOMETER_1,
        THERMOMETER_2,
        THERMOMETER_3,
        SALT_1,
        SALT_2,
        SALT_3,
        INCENSE_1,
        INCENSE_2,
        INCENSE_3,
        TRIPOD_1,
        TRIPOD_2,
        TRIPOD_3,
        MOTION_SENSOR_1,
        MOTION_SENSOR_2,
        MOTION_SENSOR_3,
        SOUND_SENSOR_1,
        SOUND_SENSOR_2,
        SOUND_SENSOR_3,
        SANITY_MEDICATION_1,
        SANITY_MEDICATION_2,
        SANITY_MEDICATION_3,
        GHOST_WRITING_BOOK_1,
        GHOST_WRITING_BOOK_2,
        GHOST_WRITING_BOOK_3,
        PARABOLIC_MICROPHONE_1,
        PARABOLIC_MICROPHONE_2,
        PARABOLIC_MICROPHONE_3,
        HEAD_GEAR_1,
        HEAD_GEAR_2,
        HEAD_GEAR_3
    }

    enum class TierImage {
        DOTS_1,
        DOTS_2,
        DOTS_3,
        EMF_1,
        EMF_2,
        EMF_3,
        FLASHLIGHT_1,
        FLASHLIGHT_2,
        FLASHLIGHT_3,
        PHOTO_CAMERA_1,
        PHOTO_CAMERA_2,
        PHOTO_CAMERA_3,
        IGNITER_1,
        IGNITER_2,
        IGNITER_3,
        FIRELIGHT_1,
        FIRELIGHT_2,
        FIRELIGHT_3,
        UV_LIGHT_1,
        UV_LIGHT_2,
        UV_LIGHT_3,
        CRUCIFIX_1,
        CRUCIFIX_2,
        CRUCIFIX_3,
        VIDEO_CAMERA_1,
        VIDEO_CAMERA_2,
        VIDEO_CAMERA_3,
        SPIRIT_BOX_1,
        SPIRIT_BOX_2,
        SPIRIT_BOX_3,
        THERMOMETER_1,
        THERMOMETER_2,
        THERMOMETER_3,
        SALT_1,
        SALT_2,
        SALT_3,
        INCENSE_1,
        INCENSE_2,
        INCENSE_3,
        TRIPOD_1,
        TRIPOD_2,
        TRIPOD_3,
        MOTION_SENSOR_1,
        MOTION_SENSOR_2,
        MOTION_SENSOR_3,
        SOUND_SENSOR_1,
        SOUND_SENSOR_2,
        SOUND_SENSOR_3,
        SANITY_MEDICATION_1,
        SANITY_MEDICATION_2,
        SANITY_MEDICATION_3,
        GHOST_WRITING_BOOK_1,
        GHOST_WRITING_BOOK_2,
        GHOST_WRITING_BOOK_3,
        PARABOLIC_MICROPHONE_1,
        PARABOLIC_MICROPHONE_2,
        PARABOLIC_MICROPHONE_3,
        HEAD_GEAR_1,
        HEAD_GEAR_2,
        HEAD_GEAR_3
    }

    enum class TierFlavorText {
        DOTS_1,
        DOTS_2,
        DOTS_3,
        EMF_1,
        EMF_2,
        EMF_3,
        FLASHLIGHT_1,
        FLASHLIGHT_2,
        FLASHLIGHT_3,
        PHOTO_CAMERA_1,
        PHOTO_CAMERA_2,
        PHOTO_CAMERA_3,
        IGNITER_1,
        IGNITER_2,
        IGNITER_3,
        FIRELIGHT_1,
        FIRELIGHT_2,
        FIRELIGHT_3,
        UV_LIGHT_1,
        UV_LIGHT_2,
        UV_LIGHT_3,
        CRUCIFIX_1,
        CRUCIFIX_2,
        CRUCIFIX_3,
        VIDEO_CAMERA_1,
        VIDEO_CAMERA_2,
        VIDEO_CAMERA_3,
        SPIRIT_BOX_1,
        SPIRIT_BOX_2,
        SPIRIT_BOX_3,
        THERMOMETER_1,
        THERMOMETER_2,
        THERMOMETER_3,
        SALT_1,
        SALT_2,
        SALT_3,
        INCENSE_1,
        INCENSE_2,
        INCENSE_3,
        TRIPOD_1,
        TRIPOD_2,
        TRIPOD_3,
        MOTION_SENSOR_1,
        MOTION_SENSOR_2,
        MOTION_SENSOR_3,
        SOUND_SENSOR_1,
        SOUND_SENSOR_2,
        SOUND_SENSOR_3,
        SANITY_MEDICATION_1,
        SANITY_MEDICATION_2,
        SANITY_MEDICATION_3,
        GHOST_WRITING_BOOK_1,
        GHOST_WRITING_BOOK_2,
        GHOST_WRITING_BOOK_3,
        PARABOLIC_MICROPHONE_1,
        PARABOLIC_MICROPHONE_2,
        PARABOLIC_MICROPHONE_3,
        HEAD_GEAR_1,
        HEAD_GEAR_2,
        HEAD_GEAR_3
    }

    enum class UpgradeCost {
        COST_0,
        COST_500,
        COST_750,
        COST_1500,
        COST_2000,
        COST_2500,
        COST_3000,
        COST_3500,
        COST_4000,
        COST_4500,
        COST_5000,
        COST_8000,
        COST_10000,
        COST_15000,
        COST_20000
    }

    enum class UnlockLevel {
        LEVEL_0,
        LEVEL_3,
        LEVEL_5,
        LEVEL_7,
        LEVEL_8,
        LEVEL_9,
        LEVEL_10,
        LEVEL_11,
        LEVEL_12,
        LEVEL_13,
        LEVEL_14,
        LEVEL_16,
        LEVEL_19,
        LEVEL_20,
        LEVEL_21,
        LEVEL_23,
        LEVEL_25,
        LEVEL_27,
        LEVEL_29,
        LEVEL_31,
        LEVEL_32,
        LEVEL_33,
        LEVEL_34,
        LEVEL_35,
        LEVEL_36,
        LEVEL_37,
        LEVEL_39,
        LEVEL_41,
        LEVEL_42,
        LEVEL_43,
        LEVEL_45,
        LEVEL_47,
        LEVEL_49,
        LEVEL_52,
        LEVEL_54,
        LEVEL_56,
        LEVEL_57,
        LEVEL_58,
        LEVEL_60,
        LEVEL_61,
        LEVEL_62,
        LEVEL_63,
        LEVEL_64,
        LEVEL_68,
        LEVEL_72,
        LEVEL_74,
        LEVEL_77,
        LEVEL_79,
        LEVEL_82,
        LEVEL_85,
        LEVEL_90
    }

}
