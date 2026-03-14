package com.tritiumgaming.data.trait.source.local

import com.tritiumgaming.data.trait.dto.GhostTraitDto
import com.tritiumgaming.data.trait.source.GhostTraitDataSource
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostIdentifier
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitCategory
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitDescription
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitIdentifier
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitState
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitTag
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitWeight
import com.tritiumgaming.shared.data.ghosttrait.model.GhostTrait

class GhostTraitLocalDataSource: GhostTraitDataSource {

    val ghostTraits: List<GhostTraitDto> = listOf(
        GhostTraitDto(
            id = TraitIdentifier.ALWAYS_FINDS_PLAYERS_IF_HUNT,
            description = TraitDescription.ALWAYS_FINDS_PLAYERS_IF_HUNT,
            affectedGhosts = listOf(GhostIdentifier.DEOGEN),
            state = TraitState.CONFIRM,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.HUNT,
            tags = listOf(TraitTag.PLAYER)
        ),
        GhostTraitDto(
            id = TraitIdentifier.AUDIBLE_LONG_RANGE_IF_HUNT,
            description = TraitDescription.AUDIBLE_LONG_RANGE_IF_HUNT,
            affectedGhosts = listOf(GhostIdentifier.MYLING),
            state = TraitState.REJECT,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.HUNT,
            tags = listOf(TraitTag.AUDIO)
        ),
        GhostTraitDto(
            id = TraitIdentifier.HUNT_START_DISTANT_FROM_FAVORITE_ROOM,
            description = TraitDescription.HUNT_START_DISTANT_FROM_FAVORITE_ROOM,
            affectedGhosts = listOf(GhostIdentifier.WRAITH),
            state = TraitState.CONFIRM,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.HUNT,
            tags = listOf(TraitTag.ROOM)
        ),
        GhostTraitDto(
            id = TraitIdentifier.CANDLE_WORKS_LIKE_CRUCIFIX_IF_HUNT,
            description = TraitDescription.CANDLE_WORKS_LIKE_CRUCIFIX_IF_HUNT,
            affectedGhosts = listOf(GhostIdentifier.ONRYO),
            state = TraitState.CONFIRM,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.CANDLE)
        ),
        GhostTraitDto(
            id = TraitIdentifier.AGE_CHANGE,
            description = TraitDescription.AGE_CHANGE,
            affectedGhosts = listOf(GhostIdentifier.THAYE),
            state = TraitState.CONFIRM,
            weight = TraitWeight.DEFINITIVE,
            category = TraitCategory.BEHAVIOR,
            tags = listOf()
        ),
        GhostTraitDto(
            id = TraitIdentifier.CHANGES_FAVORITE_ROOM,
            description = TraitDescription.CHANGES_FAVORITE_ROOM,
            affectedGhosts = listOf(GhostIdentifier.GORYO),
            state = TraitState.REJECT,
            weight = TraitWeight.DEFINITIVE,
            category = TraitCategory.BEHAVIOR,
            tags = listOf(TraitTag.ROOM)
        ),
        GhostTraitDto(
            id = TraitIdentifier.INTERACTION_MOROI_CURSE,
            description = TraitDescription.INTERACTION_MOROI_CURSE,
            affectedGhosts = listOf(GhostIdentifier.MOROI),
            state = TraitState.CONFIRM,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.SPIRIT_BOX)
        ),
        GhostTraitDto(
            id = TraitIdentifier.INTERACTION_POLTERGEIST_EXPLOSION,
            description = TraitDescription.INTERACTION_POLTERGEIST_EXPLOSION,
            affectedGhosts = listOf(GhostIdentifier.POLTERGEIST),
            state = TraitState.CONFIRM,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.ITEMS)
        ),
        GhostTraitDto(
            id = TraitIdentifier.INTERACTION_DISTURB_SALT,
            description = TraitDescription.INTERACTION_DISTURB_SALT,
            affectedGhosts = listOf(GhostIdentifier.WRAITH),
            state = TraitState.REJECT,
            weight = TraitWeight.DEFINITIVE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.SALT)
        ),
        GhostTraitDto(
            id = TraitIdentifier.EVENT_GHOST_BALL,
            description = TraitDescription.EVENT_GHOST_BALL,
            affectedGhosts = listOf(GhostIdentifier.ONI),
            state = TraitState.REJECT,
            weight = TraitWeight.DEFINITIVE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.EVENT)
        ),
        GhostTraitDto(
            id = TraitIdentifier.INTERACTION_DOES_NOT_DISTURB_SALT,
            description = TraitDescription.INTERACTION_DOES_NOT_DISTURB_SALT,
            affectedGhosts = listOf(GhostIdentifier.GALLU, GhostIdentifier.WRAITH),
            state = TraitState.CONFIRM,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.SALT)
        ),
        GhostTraitDto(
            id = TraitIdentifier.EVENT_SINGING,
            description = TraitDescription.EVENT_SINGING,
            affectedGhosts = listOf(GhostIdentifier.SHADE),
            state = TraitState.REJECT,
            weight = TraitWeight.DEFINITIVE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.EVENT)
        ),
        GhostTraitDto(
            id = TraitIdentifier.INTERACTION_SANITY_DRAIN_IF_EVENT_DOOR_SLAM,
            description = TraitDescription.INTERACTION_SANITY_DRAIN_IF_EVENT_DOOR_SLAM,
            affectedGhosts = listOf(GhostIdentifier.YUREI),
            state = TraitState.CONFIRM,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.DOOR, TraitTag.SANITY)
        ),
        GhostTraitDto(
            id = TraitIdentifier.INTERACTION_DOTS_VISIBLE_IF_WITHOUT_CAMERA,
            description = TraitDescription.INTERACTION_DOTS_VISIBLE_IF_WITHOUT_CAMERA,
            affectedGhosts = listOf(GhostIdentifier.GORYO),
            state = TraitState.REJECT,
            weight = TraitWeight.DEFINITIVE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.DOTS)
        ),
        GhostTraitDto(
            id = TraitIdentifier.INTERACTION_DRAIN_SANITY_IF_EMF_AT_CIRCUIT_BREAKER,
            description = TraitDescription.INTERACTION_DRAIN_SANITY_IF_EMF_AT_CIRCUIT_BREAKER,
            affectedGhosts = listOf(GhostIdentifier.JINN),
            state = TraitState.CONFIRM,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.EVENT, TraitTag.BREAKER)
        ),
        GhostTraitDto(
            id = TraitIdentifier.INTERACTION_HEAVY_BREATHING_IN_SPIRIT_BOX,
            description = TraitDescription.INTERACTION_HEAVY_BREATHING_IN_SPIRIT_BOX,
            affectedGhosts = listOf(GhostIdentifier.DEOGEN),
            state = TraitState.CONFIRM,
            weight = TraitWeight.DEFINITIVE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.SPIRIT_BOX)
        ),
        GhostTraitDto(
            id = TraitIdentifier.INTERACTION_EMITS_WAIL_INTO_PARABOLIC,
            description = TraitDescription.INTERACTION_EMITS_WAIL_INTO_PARABOLIC,
            affectedGhosts = listOf(GhostIdentifier.BANSHEE),
            state = TraitState.CONFIRM,
            weight = TraitWeight.DEFINITIVE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.PARABOLIC_MICROPHONE)
        ),
        GhostTraitDto(
            id = TraitIdentifier.INTERACTION_UV_FINGERPRINT_LIGHT_SWITCH,
            description = TraitDescription.INTERACTION_UV_FINGERPRINT_LIGHT_SWITCH,
            affectedGhosts = listOf(GhostIdentifier.WRAITH),
            state = TraitState.REJECT,
            weight = TraitWeight.DEFINITIVE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.ULTRAVIOLET)
        ),
        GhostTraitDto(
            id = TraitIdentifier.INTERACTION_UV_FOOTPRINT,
            description = TraitDescription.INTERACTION_UV_FOOTPRINT,
            affectedGhosts = listOf(GhostIdentifier.WRAITH),
            state = TraitState.REJECT,
            weight = TraitWeight.DEFINITIVE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.ULTRAVIOLET, TraitTag.SALT)
        ),
        GhostTraitDto(
            id = TraitIdentifier.SPEED_WANDER_EXTREMELY_SLOW_IF_HUNT,
            description = TraitDescription.SPEED_WANDER_EXTREMELY_SLOW_IF_HUNT,
            affectedGhosts = listOf(GhostIdentifier.REVENANT),
            state = TraitState.CONFIRM,
            weight = TraitWeight.DEFINITIVE,
            category = TraitCategory.HUNT,
            tags = listOf()
        ),
        GhostTraitDto(
            id = TraitIdentifier.EVENT_LIGHT_BULB_EXPLOSION,
            description = TraitDescription.EVENT_LIGHT_BULB_EXPLOSION,
            affectedGhosts = listOf(GhostIdentifier.MARE),
            state = TraitState.CONFIRM,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.EVENT, TraitTag.LIGHT)
        ),
        GhostTraitDto(
            id = TraitIdentifier.EVENT_SHADOW,
            description = TraitDescription.EVENT_SHADOW,
            affectedGhosts = listOf(GhostIdentifier.SHADE),
            state = TraitState.CONFIRM,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.EVENT)
        ),
        GhostTraitDto(
            id = TraitIdentifier.INTERACTION_NOT_VISIBLE_IN_GHOST_PHOTO,
            description = TraitDescription.INTERACTION_NOT_VISIBLE_IN_GHOST_PHOTO,
            affectedGhosts = listOf(GhostIdentifier.PHANTOM),
            state = TraitState.REJECT,
            weight = TraitWeight.DEFINITIVE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.PHOTO_CAMERA)
        ),
        GhostTraitDto(
            id = TraitIdentifier.FREEZING_GHOST_BREATH_IF_HUNT,
            description = TraitDescription.FREEZING_GHOST_BREATH_IF_HUNT,
            affectedGhosts = listOf(GhostIdentifier.HANTU),
            state = TraitState.CONFIRM,
            weight = TraitWeight.DEFINITIVE,
            category = TraitCategory.HUNT,
            tags = listOf()
        ),
        GhostTraitDto(
            id = TraitIdentifier.SPEED_HIGH_IF_CHASE_AND_BREAKER_ACTIVE,
            description = TraitDescription.SPEED_HIGH_IF_CHASE_AND_BREAKER_ACTIVE,
            affectedGhosts = listOf(GhostIdentifier.REVENANT),
            state = TraitState.CONFIRM,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.HUNT,
            tags = listOf(TraitTag.BREAKER, TraitTag.SPEED)
        ),
        GhostTraitDto(
            id = TraitIdentifier.VISIBILITY_HIGH_IF_HUNT,
            description = TraitDescription.VISIBILITY_HIGH_IF_HUNT,
            affectedGhosts = listOf(GhostIdentifier.ONI, GhostIdentifier.DEOGEN),
            state = TraitState.CONFIRM,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.HUNT,
            tags = listOf(TraitTag.VISIBILITY)
        ),
        GhostTraitDto(
            id = TraitIdentifier.SEEK_SPECIFIC_PLAYER_IF_HUNT,
            description = TraitDescription.SEEK_SPECIFIC_PLAYER_IF_HUNT,
            affectedGhosts = listOf(GhostIdentifier.BANSHEE),
            state = TraitState.CONFIRM,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.HUNT,
            tags = listOf(TraitTag.PLAYER)
        ),
        GhostTraitDto(
            id = TraitIdentifier.HUNT_AFTER_SMUDGE_180,
            description = TraitDescription.HUNT_AFTER_SMUDGE_180,
            affectedGhosts = listOf(GhostIdentifier.SPIRIT),
            state = TraitState.REJECT,
            weight = TraitWeight.DEFINITIVE,
            category = TraitCategory.HUNT,
            tags = listOf(
                TraitTag.SMUDGE,
                TraitTag.PREVENTION
            )
        ),
        GhostTraitDto(
            id = TraitIdentifier.HUNT_EARLY,
            description = TraitDescription.HUNT_EARLY,
            affectedGhosts = listOf(
                GhostIdentifier.DEMON,
                GhostIdentifier.OBAMBO,
                GhostIdentifier.RAIJU,
                GhostIdentifier.THAYE
            ),
            state = TraitState.CONFIRM,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.HUNT,
            tags = listOf(TraitTag.PREVENTION)
        ),
        GhostTraitDto(
            id = TraitIdentifier.INTERACTION_IGNITE_FLAME,
            description = TraitDescription.INTERACTION_IGNITE_FLAME,
            affectedGhosts = listOf(GhostIdentifier.ONRYO),
            state = TraitState.REJECT,
            weight = TraitWeight.DEFINITIVE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.FIRELIGHT)
        ),
        GhostTraitDto(
            id = TraitIdentifier.INTERACTION_UV_INCONSISTENT,
            description = TraitDescription.INTERACTION_UV_INCONSISTENT,
            affectedGhosts = listOf(GhostIdentifier.OBAKE),
            state = TraitState.CONFIRM,
            weight = TraitWeight.DEFINITIVE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.ULTRAVIOLET)
        ),
        GhostTraitDto(
            id = TraitIdentifier.INTERACTION_PARABOLIC_EMISSION_INCREASE,
            description = TraitDescription.INTERACTION_PARABOLIC_EMISSION_INCREASE,
            affectedGhosts = listOf(GhostIdentifier.MYLING),
            state = TraitState.CONFIRM,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.PARABOLIC_MICROPHONE)
        ),
        GhostTraitDto(
            id = TraitIdentifier.ACTIVITY_INCREASE_IF_CLOSE_TO_PLAYER_VOICE,
            description = TraitDescription.ACTIVITY_INCREASE_IF_CLOSE_TO_PLAYER_VOICE,
            affectedGhosts = listOf(GhostIdentifier.YOKAI),
            state = TraitState.CONFIRM,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.BEHAVIOR,
            tags = listOf(TraitTag.ACTIVITY)
        ),
        GhostTraitDto(
            id = TraitIdentifier.SPEED_INCREASE_IF_HUNT_IN_LOW_TEMPERATURE,
            description = TraitDescription.SPEED_INCREASE_IF_HUNT_IN_LOW_TEMPERATURE,
            affectedGhosts = listOf(GhostIdentifier.HANTU),
            state = TraitState.CONFIRM,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.HUNT,
            tags = listOf(TraitTag.SPEED, TraitTag.TEMPERATURE)
        ),
        GhostTraitDto(
            id = TraitIdentifier.INTERACTION_DOOR_OPEN_SLIGHTLY,
            description = TraitDescription.INTERACTION_DOOR_OPEN_SLIGHTLY,
            affectedGhosts = listOf(GhostIdentifier.YUREI),
            state = TraitState.REJECT,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.DOOR)
        ),
        GhostTraitDto(
            id = TraitIdentifier.IS_MALE,
            description = TraitDescription.IS_MALE,
            affectedGhosts = listOf(GhostIdentifier.BANSHEE, GhostIdentifier.DAYAN),
            state = TraitState.REJECT,
            weight = TraitWeight.DEFINITIVE,
            category = TraitCategory.CHARACTERISTIC,
            tags = listOf(TraitTag.GENDER)
        ),
        GhostTraitDto(
            id = TraitIdentifier.SANITY_DRAIN_IF_PLAYER_LOOK_AT,
            description = TraitDescription.SANITY_DRAIN_IF_PLAYER_LOOK_AT,
            affectedGhosts = listOf(GhostIdentifier.PHANTOM),
            state = TraitState.CONFIRM,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.BEHAVIOR,
            tags = listOf(TraitTag.SANITY)
        ),
        GhostTraitDto(
            id = TraitIdentifier.ACTIVITY_LOW_IF_PLAYER_NEARBY,
            description = TraitDescription.ACTIVITY_LOW_IF_PLAYER_NEARBY,
            affectedGhosts = listOf(GhostIdentifier.SHADE),
            state = TraitState.CONFIRM,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.BEHAVIOR,
            tags = listOf(TraitTag.ACTIVITY, TraitTag.PLAYER)
        ),
        GhostTraitDto(
            id = TraitIdentifier.VISIBILITY_LOW_IF_HUNT,
            description = TraitDescription.VISIBILITY_LOW_IF_HUNT,
            affectedGhosts = listOf(GhostIdentifier.PHANTOM),
            state = TraitState.CONFIRM,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.HUNT,
            tags = listOf(TraitTag.VISIBILITY)
        ),
        GhostTraitDto(
            id = TraitIdentifier.INTERACTION_CLOSE_EXIT_DOOR,
            description = TraitDescription.INTERACTION_CLOSE_EXIT_DOOR,
            affectedGhosts = listOf(GhostIdentifier.YUREI),
            state = TraitState.CONFIRM,
            weight = TraitWeight.DEFINITIVE,
            category = TraitCategory.INTERACTION,
            tags = listOf()
        ),
        GhostTraitDto(
            id = TraitIdentifier.SIMULTANEOUS_FAVORITE_ROOMS,
            description = TraitDescription.SIMULTANEOUS_FAVORITE_ROOMS,
            affectedGhosts = listOf(GhostIdentifier.THE_TWINS),
            state = TraitState.CONFIRM,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.BEHAVIOR,
            tags = listOf(TraitTag.ROOM)
        ),
        GhostTraitDto(
            id = TraitIdentifier.SHAPESHIFT_IF_HUNT,
            description = TraitDescription.SHAPESHIFT_IF_HUNT,
            affectedGhosts = listOf(GhostIdentifier.OBAKE),
            state = TraitState.CONFIRM,
            weight = TraitWeight.DEFINITIVE,
            category = TraitCategory.HUNT,
            tags = listOf()
        ),
        GhostTraitDto(
            id = TraitIdentifier.SEEK_ELECTRONICS_AND_VOICE_RANGE_LIMITED_IF_HUNT,
            description = TraitDescription.SEEK_ELECTRONICS_AND_VOICE_RANGE_LIMITED_IF_HUNT,
            affectedGhosts = listOf(GhostIdentifier.YOKAI),
            state = TraitState.CONFIRM,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.HUNT,
            tags = listOf(TraitTag.ELECTRONICS, TraitTag.VOICE)
        ),
        GhostTraitDto(
            id = TraitIdentifier.SPEED_INCREASE_IF_ACTIVE_ELECTRONICS_NEARBY,
            description = TraitDescription.SPEED_INCREASE_IF_ACTIVE_ELECTRONICS_NEARBY,
            affectedGhosts = listOf(GhostIdentifier.RAIJU),
            state = TraitState.CONFIRM,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.HUNT,
            tags = listOf(TraitTag.ELECTRONICS, TraitTag.SPEED)
        ),
        GhostTraitDto(
            id = TraitIdentifier.FAVORS_DARK_OF_FAVORITE_ROOM,
            description = TraitDescription.FAVORS_DARK_OF_FAVORITE_ROOM,
            affectedGhosts = listOf(GhostIdentifier.WRAITH),
            state = TraitState.CONFIRM,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.BEHAVIOR,
            tags = listOf(TraitTag.LIGHT)
        ),
        GhostTraitDto(
            id = TraitIdentifier.INTERACTION_EMF_IF_TELEPORTS_TO_PLAYER,
            description = TraitDescription.INTERACTION_EMF_IF_TELEPORTS_TO_PLAYER,
            affectedGhosts = listOf(GhostIdentifier.WRAITH),
            state = TraitState.CONFIRM,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.TELEPORT, TraitTag.EMF)
        ),
        GhostTraitDto(
            id = TraitIdentifier.INTERACTION_ITEM_THROWN_HARDER,
            description = TraitDescription.INTERACTION_ITEM_THROWN_HARDER,
            affectedGhosts = listOf(GhostIdentifier.POLTERGEIST),
            state = TraitState.CONFIRM,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.ITEMS)
        ),
        GhostTraitDto(
            id = TraitIdentifier.INTERACTION_TURN_BREAKER_OFF,
            description = TraitDescription.INTERACTION_TURN_BREAKER_OFF,
            affectedGhosts = listOf(GhostIdentifier.JINN),
            state = TraitState.REJECT,
            weight = TraitWeight.DEFINITIVE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.BREAKER)
        ),
        GhostTraitDto(
            id = TraitIdentifier.INTERACTION_TURN_BREAKER_ON,
            description = TraitDescription.INTERACTION_TURN_BREAKER_ON,
            affectedGhosts = listOf(GhostIdentifier.HANTU),
            state = TraitState.REJECT,
            weight = TraitWeight.DEFINITIVE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.BREAKER)
        ),
        GhostTraitDto(
            id = TraitIdentifier.INTERACTION_TURN_LIGHT_SWITCH_OFF_IF_PLAYER_TURNS_ON,
            description = TraitDescription.INTERACTION_TURN_LIGHT_SWITCH_OFF_IF_PLAYER_TURNS_ON,
            affectedGhosts = listOf(GhostIdentifier.MARE),
            state = TraitState.CONFIRM,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.EVENT, TraitTag.LIGHT)
        ),
        GhostTraitDto(
            id = TraitIdentifier.INTERACTION_TURN_LIGHT_SWITCH_ON,
            description = TraitDescription.INTERACTION_TURN_LIGHT_SWITCH_ON,
            affectedGhosts = listOf(GhostIdentifier.MARE),
            state = TraitState.REJECT,
            weight = TraitWeight.DEFINITIVE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.LIGHT)
        ),
        GhostTraitDto(
            id = TraitIdentifier.INTERACTION_UV_SHORT_LIFETIME,
            description = TraitDescription.INTERACTION_UV_SHORT_LIFETIME,
            affectedGhosts = listOf(GhostIdentifier.OBAKE),
            state = TraitState.CONFIRM,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.ULTRAVIOLET)
        ),
        GhostTraitDto(
            id = TraitIdentifier.INTERACTION_UV_FINGERPRINT_2_FINGERS,
            description = TraitDescription.INTERACTION_UV_FINGERPRINT_2_FINGERS,
            affectedGhosts = listOf(GhostIdentifier.OBAKE),
            state = TraitState.CONFIRM,
            weight = TraitWeight.DEFINITIVE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.ULTRAVIOLET)
        ),
        GhostTraitDto(
            id = TraitIdentifier.INTERACTION_UV_HANDPRINT_6_FINGERS,
            description = TraitDescription.INTERACTION_UV_HANDPRINT_6_FINGERS,
            affectedGhosts = listOf(GhostIdentifier.OBAKE),
            state = TraitState.CONFIRM,
            weight = TraitWeight.DEFINITIVE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.ULTRAVIOLET)
        ),
        GhostTraitDto(
            id = TraitIdentifier.INTERACTION_VANISH_IF_PHOTO_TAKEN,
            description = TraitDescription.INTERACTION_VANISH_IF_PHOTO_TAKEN,
            affectedGhosts = listOf(GhostIdentifier.PHANTOM),
            state = TraitState.CONFIRM,
            weight = TraitWeight.PROBABLE,
            category = TraitCategory.INTERACTION,
            tags = listOf(TraitTag.PHOTO_CAMERA)
        )
    )

    override fun get(): List<GhostTraitDto> = ghostTraits

}
