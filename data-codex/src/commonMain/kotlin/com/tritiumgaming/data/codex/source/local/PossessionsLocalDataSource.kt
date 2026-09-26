package com.tritiumgaming.data.codex.source.local

import com.tritiumgaming.data.codex.dto.PossessionTypeDto
import com.tritiumgaming.data.codex.dto.PossessionTypeMemberDto
import com.tritiumgaming.shared.data.codex.mappers.PossessionsResources.PossessionAttribute
import com.tritiumgaming.shared.data.codex.mappers.PossessionsResources.PossessionDescription
import com.tritiumgaming.shared.data.codex.mappers.PossessionsResources.PossessionDrawChance
import com.tritiumgaming.shared.data.codex.mappers.PossessionsResources.PossessionFlavor
import com.tritiumgaming.shared.data.codex.mappers.PossessionsResources.PossessionImage
import com.tritiumgaming.shared.data.codex.mappers.PossessionsResources.PossessionItemAltName
import com.tritiumgaming.shared.data.codex.mappers.PossessionsResources.PossessionSanityDrain
import com.tritiumgaming.shared.data.codex.mappers.PossessionsResources.PossessionTitle
import com.tritiumgaming.shared.data.codex.mappers.PossessionsResources.PossessionsIcon

class PossessionsLocalDataSource {

    private val possessionsResources
        get() = listOf(
            // Mirror
            PossessionTypeRes(
                name = PossessionTitle.HAUNTED_MIRROR,
                icon = PossessionsIcon.HAUNTED_MIRROR,
                items = listOf(
                    PossessionTypeMemberRes(
                        image = PossessionImage.HAUNTED_MIRROR,
                        flavorText = PossessionFlavor.HAUNTED_MIRROR,
                        infoText = PossessionDescription.HAUNTED_MIRROR,
                        attributesText = PossessionAttribute.HAUNTED_MIRROR,
                        sanityDrain = PossessionSanityDrain.HAUNTED_MIRROR,
                        drawChance = PossessionDrawChance.HAUNTED_MIRROR
                    )
                )
            ),
            // Monkey Paw
            PossessionTypeRes(
                name = PossessionTitle.MONKEY_PAW,
                icon = PossessionsIcon.MONKEY_PAW,
                items = listOf(

                    PossessionTypeMemberRes(
                        image = PossessionImage.MONKEY_PAW,
                        flavorText = PossessionFlavor.MONKEY_PAW,
                        infoText = PossessionDescription.MONKEY_PAW,
                        attributesText = PossessionAttribute.MONKEY_PAW,
                        sanityDrain = PossessionSanityDrain.MONKEY_PAW,
                        drawChance = PossessionDrawChance.MONKEY_PAW,
                        altName = PossessionItemAltName.MONKEY_PAW
                    ),
                    PossessionTypeMemberRes(
                        image = PossessionImage.MONKEY_PAW_WISH,
                        flavorText = PossessionFlavor.MONKEY_PAW_WISH,
                        infoText = PossessionDescription.MONKEY_PAW_WISH,
                        attributesText = PossessionAttribute.MONKEY_PAW_WISH,
                        sanityDrain = PossessionSanityDrain.MONKEY_PAW_WISH,
                        drawChance = PossessionDrawChance.MONKEY_PAW_WISH,
                        altName = PossessionItemAltName.MONKEY_PAW_WISH
                    )
                )
            ),
            // Music Box
            PossessionTypeRes(
                name = PossessionTitle.MUSIC_BOX,
                icon = PossessionsIcon.MUSIC_BOX,
                items = listOf(
                    PossessionTypeMemberRes(
                        image = PossessionImage.MUSIC_BOX,
                        flavorText = PossessionFlavor.MUSIC_BOX,
                        infoText = PossessionDescription.MUSIC_BOX,
                        attributesText = PossessionAttribute.MUSIC_BOX,
                        sanityDrain = PossessionSanityDrain.MUSIC_BOX,
                        drawChance = PossessionDrawChance.MUSIC_BOX
                    )

                )
            ),
            // Ouija Board
            PossessionTypeRes(
                name = PossessionTitle.OUIJA_BOARD,
                icon = PossessionsIcon.OUIJA_BOARD,
                items = listOf(
                    PossessionTypeMemberRes(
                        image = PossessionImage.OUIJA_BOARD,
                        flavorText = PossessionFlavor.OUIJA_BOARD,
                        infoText = PossessionDescription.OUIJA_BOARD,
                        attributesText = PossessionAttribute.OUIJA_BOARD,
                        sanityDrain = PossessionSanityDrain.OUIJA_BOARD,
                        drawChance = PossessionDrawChance.OUIJA_BOARD
                    )
                )
            ),
            // Summoning  Circle
            PossessionTypeRes(
                name = PossessionTitle.SUMMONING_CIRCLE,
                icon = PossessionsIcon.SUMMONING_CIRCLE,
                items = listOf(
                    PossessionTypeMemberRes(
                        image = PossessionImage.SUMMONING_CIRCLE,
                        flavorText = PossessionFlavor.SUMMONING_CIRCLE,
                        infoText = PossessionDescription.SUMMONING_CIRCLE,
                        attributesText = PossessionAttribute.SUMMONING_CIRCLE,
                        sanityDrain = PossessionSanityDrain.SUMMONING_CIRCLE,
                        drawChance = PossessionDrawChance.SUMMONING_CIRCLE
                    )
                )
            ),
            // Tarot Cards
            PossessionTypeRes(
                name = PossessionTitle.TAROT_CARDS,
                icon = PossessionsIcon.TAROT_CARDS,
                items = listOf(
                    PossessionTypeMemberRes(
                        image = PossessionImage.TAROT_CARDS_HANGED_MAN,
                        flavorText = PossessionFlavor.TAROT_CARDS_HANGED_MAN,
                        infoText = PossessionDescription.TAROT_CARDS_HANGED_MAN,
                        attributesText = PossessionAttribute.TAROT_CARDS_HANGED_MAN,
                        sanityDrain = PossessionSanityDrain.TAROT_CARDS_HANGED_MAN,
                        drawChance = PossessionDrawChance.TAROT_CARDS_HANGED_MAN,
                        altName = PossessionItemAltName.TAROT_CARDS_HANGED_MAN
                    ),
                    PossessionTypeMemberRes(
                        image = PossessionImage.TAROT_CARDS_PRIESTESS,
                        flavorText = PossessionFlavor.TAROT_CARDS_PRIESTESS,
                        infoText = PossessionDescription.TAROT_CARDS_PRIESTESS,
                        attributesText = PossessionAttribute.TAROT_CARDS_PRIESTESS,
                        sanityDrain = PossessionSanityDrain.TAROT_CARDS_PRIESTESS,
                        drawChance = PossessionDrawChance.TAROT_CARDS_PRIESTESS,
                        altName = PossessionItemAltName.TAROT_CARDS_PRIESTESS
                    ),
                    PossessionTypeMemberRes(
                        image = PossessionImage.TAROT_CARDS_DEATH,
                        flavorText = PossessionFlavor.TAROT_CARDS_DEATH,
                        infoText = PossessionDescription.TAROT_CARDS_DEATH,
                        attributesText = PossessionAttribute.TAROT_CARDS_DEATH,
                        sanityDrain = PossessionSanityDrain.TAROT_CARDS_DEATH,
                        drawChance = PossessionDrawChance.TAROT_CARDS_DEATH,
                        altName = PossessionItemAltName.TAROT_CARDS_DEATH
                    ),
                    PossessionTypeMemberRes(
                        image = PossessionImage.TAROT_CARDS_DEVIL,
                        flavorText = PossessionFlavor.TAROT_CARDS_DEVIL,
                        infoText = PossessionDescription.TAROT_CARDS_DEVIL,
                        attributesText = PossessionAttribute.TAROT_CARDS_DEVIL,
                        sanityDrain = PossessionSanityDrain.TAROT_CARDS_DEVIL,
                        drawChance = PossessionDrawChance.TAROT_CARDS_DEVIL,
                        altName = PossessionItemAltName.TAROT_CARDS_DEVIL
                    ),
                    PossessionTypeMemberRes(
                        image = PossessionImage.TAROT_CARDS_FORTUNE,
                        flavorText = PossessionFlavor.TAROT_CARDS_FORTUNE,
                        infoText = PossessionDescription.TAROT_CARDS_FORTUNE,
                        attributesText = PossessionAttribute.TAROT_CARDS_FORTUNE,
                        sanityDrain = PossessionSanityDrain.TAROT_CARDS_FORTUNE,
                        drawChance = PossessionDrawChance.TAROT_CARDS_FORTUNE,
                        altName = PossessionItemAltName.TAROT_CARDS_FORTUNE
                    ),
                    PossessionTypeMemberRes(
                        image = PossessionImage.TAROT_CARDS_HERMIT,
                        flavorText = PossessionFlavor.TAROT_CARDS_HERMIT,
                        infoText = PossessionDescription.TAROT_CARDS_HERMIT,
                        attributesText = PossessionAttribute.TAROT_CARDS_HERMIT,
                        sanityDrain = PossessionSanityDrain.TAROT_CARDS_HERMIT,
                        drawChance = PossessionDrawChance.TAROT_CARDS_HERMIT,
                        altName = PossessionItemAltName.TAROT_CARDS_HERMIT
                    ),
                    PossessionTypeMemberRes(
                        image = PossessionImage.TAROT_CARDS_JOKER,
                        flavorText = PossessionFlavor.TAROT_CARDS_JOKER,
                        infoText = PossessionDescription.TAROT_CARDS_JOKER,
                        attributesText = PossessionAttribute.TAROT_CARDS_JOKER,
                        sanityDrain = PossessionSanityDrain.TAROT_CARDS_JOKER,
                        drawChance = PossessionDrawChance.TAROT_CARDS_JOKER,
                        altName = PossessionItemAltName.TAROT_CARDS_JOKER
                    ),
                    PossessionTypeMemberRes(
                        image = PossessionImage.TAROT_CARDS_MOON,
                        flavorText = PossessionFlavor.TAROT_CARDS_MOON,
                        infoText = PossessionDescription.TAROT_CARDS_MOON,
                        attributesText = PossessionAttribute.TAROT_CARDS_MOON,
                        sanityDrain = PossessionSanityDrain.TAROT_CARDS_MOON,
                        drawChance = PossessionDrawChance.TAROT_CARDS_MOON,
                        altName = PossessionItemAltName.TAROT_CARDS_MOON
                    ),
                    PossessionTypeMemberRes(
                        image = PossessionImage.TAROT_CARDS_SUN,
                        flavorText = PossessionFlavor.TAROT_CARDS_SUN,
                        infoText = PossessionDescription.TAROT_CARDS_SUN,
                        attributesText = PossessionAttribute.TAROT_CARDS_SUN,
                        sanityDrain = PossessionSanityDrain.TAROT_CARDS_SUN,
                        drawChance = PossessionDrawChance.TAROT_CARDS_SUN,
                        altName = PossessionItemAltName.TAROT_CARDS_SUN
                    ),
                    PossessionTypeMemberRes(
                        image = PossessionImage.TAROT_CARDS_TOWER,
                        flavorText = PossessionFlavor.TAROT_CARDS_TOWER,
                        infoText = PossessionDescription.TAROT_CARDS_TOWER,
                        attributesText = PossessionAttribute.TAROT_CARDS_TOWER,
                        sanityDrain = PossessionSanityDrain.TAROT_CARDS_TOWER,
                        drawChance = PossessionDrawChance.TAROT_CARDS_TOWER,
                        altName = PossessionItemAltName.TAROT_CARDS_TOWER
                    )

                )
            ),
            // Voodoo Doll
            PossessionTypeRes(
                name = PossessionTitle.VOODOO_DOLL,
                icon = PossessionsIcon.VOODOO_DOLL,
                items = listOf(
                    PossessionTypeMemberRes(
                        image = PossessionImage.VOODOO_DOLL,
                        flavorText = PossessionFlavor.VOODOO_DOLL,
                        infoText = PossessionDescription.VOODOO_DOLL,
                        attributesText = PossessionAttribute.VOODOO_DOLL,
                        sanityDrain = PossessionSanityDrain.VOODOO_DOLL,
                        drawChance = PossessionDrawChance.VOODOO_DOLL
                    )
                )
            )
        )

    fun fetchItems(): Result<List<PossessionTypeDto>> {
        val items = possessionsResources.map { resDto -> resDto.toLocal() }

        return Result.success(items)
    }

    private data class PossessionTypeRes(
        val name: PossessionTitle,
        val icon: PossessionsIcon,
        val items: List<PossessionTypeMemberRes>
    )

    private data class PossessionTypeMemberRes(
        val image: PossessionImage,
        val flavorText: PossessionFlavor,
        val infoText: PossessionDescription,
        val attributesText: PossessionAttribute,
        val sanityDrain: PossessionSanityDrain,
        val drawChance: PossessionDrawChance,
        val altName: PossessionItemAltName? = null
    )

    private fun PossessionTypeRes.toLocal() = PossessionTypeDto(
        name = name,
        icon = icon,
        items = items.toLocal()
    )

    private fun PossessionTypeMemberRes.toLocal() = PossessionTypeMemberDto(
        image = image,
        flavorText = flavorText,
        infoText = infoText,
        attributesText = attributesText,
        sanityDrain = sanityDrain,
        drawChance = drawChance,
        altName = altName
    )

    @JvmName("CodexPossessionGroupResListToLocalCodexPossessionGroupRes")
    private fun List<PossessionTypeRes>.toLocal() = map { dto ->
        dto.toLocal()
    }

    @JvmName("CodexPossessionGroupItemResListToLocalCodexPossessionGroupItemRes")
    private fun List<PossessionTypeMemberRes>.toLocal() = map { dto ->
        dto.toLocal()
    }
}
