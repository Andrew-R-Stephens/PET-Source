package com.tritiumgaming.feature.investigation.app.mappers.ghosttraits

import androidx.annotation.StringRes
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitCategory
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitDescription

@StringRes fun TraitDescription.toStringResource(): Int =
    when(this) {
        TraitDescription.ALWAYS_FINDS_PLAYERS_IF_HUNT -> R.string.evidence_trait_description_always_finds_players_if_hunt
        TraitDescription.AUDIBLE_LONG_RANGE_IF_HUNT -> R.string.evidence_trait_description_audible_long_range_if_hunt
        TraitDescription.HUNT_START_DISTANT_FROM_FAVORITE_ROOM -> R.string.evidence_trait_description_hunt_start_distant_from_favorite_room
        TraitDescription.CANDLE_WORKS_LIKE_CRUCIFIX_IF_HUNT -> R.string.evidence_trait_description_candle_works_like_crucifix_if_hunt
        TraitDescription.AGE_CHANGE -> R.string.evidence_trait_description_age_change
        TraitDescription.CHANGES_FAVORITE_ROOM -> R.string.evidence_trait_description_changes_favorite_room
        TraitDescription.INTERACTION_MOROI_CURSE -> R.string.evidence_trait_description_interaction_moroi_curse
        TraitDescription.INTERACTION_POLTERGEIST_EXPLOSION -> R.string.evidence_trait_description_interaction_poltergeist_explosion
        TraitDescription.INTERACTION_DISTURB_SALT -> R.string.evidence_trait_description_interaction_disturb_salt
        TraitDescription.EVENT_GHOST_BALL -> R.string.evidence_trait_description_event_ghost_ball
        TraitDescription.INTERACTION_DOES_NOT_DISTURB_SALT -> R.string.evidence_trait_description_interaction_does_not_disturb_salt
        TraitDescription.EVENT_SINGING -> R.string.evidence_trait_description_event_singing
        TraitDescription.INTERACTION_SANITY_DRAIN_IF_EVENT_DOOR_SLAM -> R.string.evidence_trait_description_interaction_sanity_drain_if_event_door_slam
        TraitDescription.INTERACTION_DOTS_VISIBLE_IF_WITHOUT_CAMERA -> R.string.evidence_trait_description_interaction_dots_visible_if_without_camera
        TraitDescription.INTERACTION_DRAIN_SANITY_IF_EMF_AT_CIRCUIT_BREAKER -> R.string.evidence_trait_description_interaction_drain_sanity_if_emf_at_circuit_breaker
        TraitDescription.INTERACTION_HEAVY_BREATHING_IN_SPIRIT_BOX -> R.string.evidence_trait_description_interaction_heavy_breathing_in_spirit_box
        TraitDescription.INTERACTION_EMITS_WAIL_INTO_PARABOLIC -> R.string.evidence_trait_description_interaction_emits_wail_into_parabolic
        TraitDescription.INTERACTION_UV_FINGERPRINT_LIGHT_SWITCH -> R.string.evidence_trait_description_interaction_uv_fingerprint_light_switch
        TraitDescription.INTERACTION_UV_FOOTPRINT -> R.string.evidence_trait_description_interaction_uv_footprint
        TraitDescription.SPEED_WANDER_EXTREMELY_SLOW_IF_HUNT -> R.string.evidence_trait_description_speed_wander_extremely_slow_if_hunt
        TraitDescription.EVENT_LIGHT_BULB_EXPLOSION -> R.string.evidence_trait_description_event_light_bulb_explosion
        TraitDescription.EVENT_SHADOW -> R.string.evidence_trait_description_event_shadow
        TraitDescription.INTERACTION_NOT_VISIBLE_IN_GHOST_PHOTO -> R.string.evidence_trait_description_interaction_not_visible_in_ghost_photo
        TraitDescription.FREEZING_GHOST_BREATH_IF_HUNT -> R.string.evidence_trait_description_freezing_ghost_breath_if_hunt
        TraitDescription.SPEED_HIGH_IF_CHASE_AND_BREAKER_ACTIVE -> R.string.evidence_trait_description_speed_high_if_chase_and_breaker_active
        TraitDescription.VISIBILITY_HIGH_IF_HUNT -> R.string.evidence_trait_description_visibility_high_if_hunt
        TraitDescription.SEEK_SPECIFIC_PLAYER_IF_HUNT -> R.string.evidence_trait_description_seek_specific_player_if_hunt
        TraitDescription.HUNT_AFTER_SMUDGE_180 -> R.string.evidence_trait_description_hunt_after_smudge_180
        TraitDescription.HUNT_EARLY -> R.string.evidence_trait_description_hunt_early
        TraitDescription.HUNT_BEFORE_SMUDGE_60 -> R.string.evidence_trait_description_hunt_before_smudge_60
        TraitDescription.INTERACTION_IGNITE_FLAME -> R.string.evidence_trait_description_interaction_ignite_flame
        TraitDescription.INTERACTION_UV_INCONSISTENT -> R.string.evidence_trait_description_interaction_uv_inconsistent
        TraitDescription.INTERACTION_PARABOLIC_EMISSION_INCREASE -> R.string.evidence_trait_description_interaction_parabolic_emission_increase
        TraitDescription.ACTIVITY_INCREASE_IF_CLOSE_TO_PLAYER_VOICE -> R.string.evidence_trait_description_activity_increase_if_close_to_player_voice
        TraitDescription.SPEED_INCREASE_IF_HUNT_IN_LOW_TEMPERATURE -> R.string.evidence_trait_description_speed_increase_if_hunt_in_low_temperature
        TraitDescription.INTERACTION_DOOR_OPEN_SLIGHTLY -> R.string.evidence_trait_description_interaction_door_open_slightly
        TraitDescription.IS_MALE -> R.string.evidence_trait_description_is_male
        TraitDescription.SANITY_DRAIN_IF_PLAYER_LOOK_AT -> R.string.evidence_trait_description_sanity_drain_if_player_look_at
        TraitDescription.ACTIVITY_LOW_IF_PLAYER_NEARBY -> R.string.evidence_trait_description_activity_low_if_player_nearby
        TraitDescription.VISIBILITY_LOW_IF_HUNT -> R.string.evidence_trait_description_visibility_low_if_hunt
        TraitDescription.INTERACTION_CLOSE_EXIT_DOOR -> R.string.evidence_trait_description_interaction_close_exit_door
        TraitDescription.SIMULTANEOUS_FAVORITE_ROOMS -> R.string.evidence_trait_description_simultaneous_favorite_rooms
        TraitDescription.SHAPESHIFT_IF_HUNT -> R.string.evidence_trait_description_shapeshift_if_hunt
        TraitDescription.SEEK_ELECTRONICS_AND_VOICE_RANGE_LIMITED_IF_HUNT -> R.string.evidence_trait_description_seek_electronics_and_voice_range_limited_if_hunt
        TraitDescription.SPEED_INCREASE_IF_ACTIVE_ELECTRONICS_NEARBY -> R.string.evidence_trait_description_speed_increase_if_active_electronics_nearby
        TraitDescription.FAVORS_DARK_OF_FAVORITE_ROOM -> R.string.evidence_trait_description_favors_dark_of_favorite_room
        TraitDescription.INTERACTION_EMF_IF_TELEPORTS_TO_PLAYER -> R.string.evidence_trait_description_interaction_emf_if_teleports_to_player
        TraitDescription.INTERACTION_ITEM_THROWN_HARDER -> R.string.evidence_trait_description_interaction_item_thrown_harder
        TraitDescription.INTERACTION_TURN_BREAKER_OFF -> R.string.evidence_trait_description_interaction_turn_breaker_off
        TraitDescription.INTERACTION_TURN_BREAKER_ON -> R.string.evidence_trait_description_interaction_turn_breaker_on
        TraitDescription.INTERACTION_TURN_LIGHT_SWITCH_OFF_IF_PLAYER_TURNS_ON -> R.string.evidence_trait_description_interaction_turn_light_switch_off_if_player_turns_on
        TraitDescription.INTERACTION_TURN_LIGHT_SWITCH_ON -> R.string.evidence_trait_description_interaction_turn_light_switch_on
        TraitDescription.INTERACTION_UV_SHORT_LIFETIME -> R.string.evidence_trait_description_interaction_uv_short_lifetime
        TraitDescription.INTERACTION_UV_FINGERPRINT_2_FINGERS -> R.string.evidence_trait_description_interaction_uv_fingerprint_2_fingers
        TraitDescription.INTERACTION_UV_FOOTPRINT_2_FEET_PER_AUDIO -> R.string.evidence_trait_description_interaction_uv_footprint_2_feet_per_audio
        TraitDescription.INTERACTION_UV_HANDPRINT_6_FINGERS -> R.string.evidence_trait_description_interaction_uv_handprint_6_fingers
        TraitDescription.INTERACTION_VANISH_IF_PHOTO_TAKEN -> R.string.evidence_trait_description_interaction_vanish_if_photo_taken
    }


@StringRes fun TraitCategory.toStringResource() = when(this) {
    TraitCategory.BEHAVIOR -> R.string.evidence_trait_category_behavior
    TraitCategory.CHARACTERISTIC -> R.string.evidence_trait_category_characteristic
    TraitCategory.HUNT -> R.string.evidence_trait_category_hunt
    TraitCategory.INTERACTION -> R.string.evidence_trait_category_interaction
}

enum class TraitState {
    CONFIRM,
    REJECT
}

enum class TraitWeight {
    DEFINITIVE,
    PROBABLE
}

enum class TraitTag {
    ROOM,
    ACTIVITY,
    PLAYER,
    LIGHT,
    SANITY,
    AGE,
    GENDER,
    SPEED,
    APPEARANCE,
    SMUDGE,
    BREATH,
    CANDLE,
    ELECTRONICS,
    PREVENTION,
    BREAKER,
    TEMPERATURE,
    VISIBILITY,
    AUDIO,
    DOOR,
    PARABOLIC_MICROPHONE,
    SPIRIT_BOX,
    ULTRAVIOLET,
    DOTS,
    EVENT,
    PHOTO_CAMERA,
    SALT,
    ITEMS,
    TELEPORT,
    VOICE,
    EMF,
    CURSE,
    FIRELIGHT
}
