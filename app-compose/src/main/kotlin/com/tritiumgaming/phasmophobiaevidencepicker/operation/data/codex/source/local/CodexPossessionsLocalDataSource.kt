package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.dto.CodexPossessionGroupDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.dto.CodexPossessionGroupItemDto
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemAltName
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemAttribute
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemDrawChance
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemFlavorText
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemImage
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemInfoText
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemSanityDrain
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexPossessionsResources.PossessionTitle
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexPossessionsResources.PossessionsIcon

class CodexPossessionsLocalDataSource {

    private val possessionsResources
        get() = listOf(
            // Mirror
            CodexPossessionGroupRes(
                name = PossessionTitle.HAUNTED_MIRROR,
                icon = PossessionsIcon.HAUNTED_MIRROR,
                items = listOf(
                    CodexPossessionGroupItemRes(
                        image = PossessionItemImage.HAUNTED_MIRROR,
                        flavorText = PossessionItemFlavorText.HAUNTED_MIRROR,
                        infoText = PossessionItemInfoText.HAUNTED_MIRROR,
                        attributesText = PossessionItemAttribute.HAUNTED_MIRROR,
                        sanityDrain = PossessionItemSanityDrain.HAUNTED_MIRROR,
                        drawChance = PossessionItemDrawChance.HAUNTED_MIRROR
                    )
                )
            ),
            // Monkey Paw
            CodexPossessionGroupRes(
                name = PossessionTitle.MONKEY_PAW,
                icon = PossessionsIcon.MONKEY_PAW,
                items = listOf(

                    CodexPossessionGroupItemRes(
                        image = PossessionItemImage.MONKEY_PAW,
                        flavorText = PossessionItemFlavorText.MONKEY_PAW,
                        infoText = PossessionItemInfoText.MONKEY_PAW,
                        attributesText = PossessionItemAttribute.MONKEY_PAW,
                        sanityDrain = PossessionItemSanityDrain.MONKEY_PAW,
                        drawChance = PossessionItemDrawChance.MONKEY_PAW,
                        altName = PossessionItemAltName.MONKEY_PAW
                    ),
                    CodexPossessionGroupItemRes(
                        image = PossessionItemImage.MONKEY_PAW_WISH,
                        flavorText = PossessionItemFlavorText.MONKEY_PAW_WISH,
                        infoText = PossessionItemInfoText.MONKEY_PAW_WISH,
                        attributesText = PossessionItemAttribute.MONKEY_PAW_WISH,
                        sanityDrain = PossessionItemSanityDrain.MONKEY_PAW_WISH,
                        drawChance = PossessionItemDrawChance.MONKEY_PAW_WISH,
                        altName = PossessionItemAltName.MONKEY_PAW_WISH
                    )
                )
            ),
            // Music Box
            CodexPossessionGroupRes(
                name = PossessionTitle.MUSIC_BOX,
                icon = PossessionsIcon.MUSIC_BOX,
                items = listOf(
                    CodexPossessionGroupItemRes(
                        image = PossessionItemImage.MUSIC_BOX,
                        flavorText = PossessionItemFlavorText.MUSIC_BOX,
                        infoText = PossessionItemInfoText.MUSIC_BOX,
                        attributesText = PossessionItemAttribute.MUSIC_BOX,
                        sanityDrain = PossessionItemSanityDrain.MUSIC_BOX,
                        drawChance = PossessionItemDrawChance.MUSIC_BOX
                    )

                )
            ),
            // Ouija Board
            CodexPossessionGroupRes(
                name = PossessionTitle.OUIJA_BOARD,
                icon = PossessionsIcon.OUIJA_BOARD,
                items = listOf(
                    CodexPossessionGroupItemRes(
                        image = PossessionItemImage.OUIJA_BOARD,
                        flavorText = PossessionItemFlavorText.OUIJA_BOARD,
                        infoText = PossessionItemInfoText.OUIJA_BOARD,
                        attributesText = PossessionItemAttribute.OUIJA_BOARD,
                        sanityDrain = PossessionItemSanityDrain.OUIJA_BOARD,
                        drawChance = PossessionItemDrawChance.OUIJA_BOARD
                    )
                )
            ),
            // Summoning  Circle
            CodexPossessionGroupRes(
                name = PossessionTitle.SUMMONING_CIRCLE,
                icon = PossessionsIcon.SUMMONING_CIRCLE,
                items = listOf(
                    CodexPossessionGroupItemRes(
                        image = PossessionItemImage.SUMMONING_CIRCLE,
                        flavorText = PossessionItemFlavorText.SUMMONING_CIRCLE,
                        infoText = PossessionItemInfoText.SUMMONING_CIRCLE,
                        attributesText = PossessionItemAttribute.SUMMONING_CIRCLE,
                        sanityDrain = PossessionItemSanityDrain.SUMMONING_CIRCLE,
                        drawChance = PossessionItemDrawChance.SUMMONING_CIRCLE
                    )
                )
            ),
            // Tarot Cards
            CodexPossessionGroupRes(
                name = PossessionTitle.TAROT_CARDS,
                icon = PossessionsIcon.TAROT_CARDS,
                items = listOf(
                    CodexPossessionGroupItemRes(
                        image = PossessionItemImage.TAROT_CARDS_HANGED_MAN,
                        flavorText = PossessionItemFlavorText.TAROT_CARDS_HANGED_MAN,
                        infoText = PossessionItemInfoText.TAROT_CARDS_HANGED_MAN,
                        attributesText = PossessionItemAttribute.TAROT_CARDS_HANGED_MAN,
                        sanityDrain = PossessionItemSanityDrain.TAROT_CARDS_HANGED_MAN,
                        drawChance = PossessionItemDrawChance.TAROT_CARDS_HANGED_MAN,
                        altName = PossessionItemAltName.TAROT_CARDS_HANGED_MAN
                    ),
                    CodexPossessionGroupItemRes(
                        image = PossessionItemImage.TAROT_CARDS_PRIESTESS,
                        flavorText = PossessionItemFlavorText.TAROT_CARDS_PRIESTESS,
                        infoText = PossessionItemInfoText.TAROT_CARDS_PRIESTESS,
                        attributesText = PossessionItemAttribute.TAROT_CARDS_PRIESTESS,
                        sanityDrain = PossessionItemSanityDrain.TAROT_CARDS_PRIESTESS,
                        drawChance = PossessionItemDrawChance.TAROT_CARDS_PRIESTESS,
                        altName = PossessionItemAltName.TAROT_CARDS_PRIESTESS
                    ),
                    CodexPossessionGroupItemRes(
                        image = PossessionItemImage.TAROT_CARDS_DEATH,
                        flavorText = PossessionItemFlavorText.TAROT_CARDS_DEATH,
                        infoText = PossessionItemInfoText.TAROT_CARDS_DEATH,
                        attributesText = PossessionItemAttribute.TAROT_CARDS_DEATH,
                        sanityDrain = PossessionItemSanityDrain.TAROT_CARDS_DEATH,
                        drawChance = PossessionItemDrawChance.TAROT_CARDS_DEATH,
                        altName = PossessionItemAltName.TAROT_CARDS_DEATH
                    ),
                    CodexPossessionGroupItemRes(
                        image = PossessionItemImage.TAROT_CARDS_DEVIL,
                        flavorText = PossessionItemFlavorText.TAROT_CARDS_DEVIL,
                        infoText = PossessionItemInfoText.TAROT_CARDS_DEVIL,
                        attributesText = PossessionItemAttribute.TAROT_CARDS_DEVIL,
                        sanityDrain = PossessionItemSanityDrain.TAROT_CARDS_DEVIL,
                        drawChance = PossessionItemDrawChance.TAROT_CARDS_DEVIL,
                        altName = PossessionItemAltName.TAROT_CARDS_DEVIL
                    ),
                    CodexPossessionGroupItemRes(
                        image = PossessionItemImage.TAROT_CARDS_FORTUNE,
                        flavorText = PossessionItemFlavorText.TAROT_CARDS_FORTUNE,
                        infoText = PossessionItemInfoText.TAROT_CARDS_FORTUNE,
                        attributesText = PossessionItemAttribute.TAROT_CARDS_FORTUNE,
                        sanityDrain = PossessionItemSanityDrain.TAROT_CARDS_FORTUNE,
                        drawChance = PossessionItemDrawChance.TAROT_CARDS_FORTUNE,
                        altName = PossessionItemAltName.TAROT_CARDS_FORTUNE
                    ),
                    CodexPossessionGroupItemRes(
                        image = PossessionItemImage.TAROT_CARDS_HERMIT,
                        flavorText = PossessionItemFlavorText.TAROT_CARDS_HERMIT,
                        infoText = PossessionItemInfoText.TAROT_CARDS_HERMIT,
                        attributesText = PossessionItemAttribute.TAROT_CARDS_HERMIT,
                        sanityDrain = PossessionItemSanityDrain.TAROT_CARDS_HERMIT,
                        drawChance = PossessionItemDrawChance.TAROT_CARDS_HERMIT,
                        altName = PossessionItemAltName.TAROT_CARDS_HERMIT
                    ),
                    CodexPossessionGroupItemRes(
                        image = PossessionItemImage.TAROT_CARDS_JOKER,
                        flavorText = PossessionItemFlavorText.TAROT_CARDS_JOKER,
                        infoText = PossessionItemInfoText.TAROT_CARDS_JOKER,
                        attributesText = PossessionItemAttribute.TAROT_CARDS_JOKER,
                        sanityDrain = PossessionItemSanityDrain.TAROT_CARDS_JOKER,
                        drawChance = PossessionItemDrawChance.TAROT_CARDS_JOKER,
                        altName = PossessionItemAltName.TAROT_CARDS_JOKER
                    ),
                    CodexPossessionGroupItemRes(
                        image = PossessionItemImage.TAROT_CARDS_MOON,
                        flavorText = PossessionItemFlavorText.TAROT_CARDS_MOON,
                        infoText = PossessionItemInfoText.TAROT_CARDS_MOON,
                        attributesText = PossessionItemAttribute.TAROT_CARDS_MOON,
                        sanityDrain = PossessionItemSanityDrain.TAROT_CARDS_MOON,
                        drawChance = PossessionItemDrawChance.TAROT_CARDS_MOON,
                        altName = PossessionItemAltName.TAROT_CARDS_MOON
                    ),
                    CodexPossessionGroupItemRes(
                        image = PossessionItemImage.TAROT_CARDS_SUN,
                        flavorText = PossessionItemFlavorText.TAROT_CARDS_SUN,
                        infoText = PossessionItemInfoText.TAROT_CARDS_SUN,
                        attributesText = PossessionItemAttribute.TAROT_CARDS_SUN,
                        sanityDrain = PossessionItemSanityDrain.TAROT_CARDS_SUN,
                        drawChance = PossessionItemDrawChance.TAROT_CARDS_SUN,
                        altName = PossessionItemAltName.TAROT_CARDS_SUN
                    ),
                    CodexPossessionGroupItemRes(
                        image = PossessionItemImage.TAROT_CARDS_TOWER,
                        flavorText = PossessionItemFlavorText.TAROT_CARDS_TOWER,
                        infoText = PossessionItemInfoText.TAROT_CARDS_TOWER,
                        attributesText = PossessionItemAttribute.TAROT_CARDS_TOWER,
                        sanityDrain = PossessionItemSanityDrain.TAROT_CARDS_TOWER,
                        drawChance = PossessionItemDrawChance.TAROT_CARDS_TOWER,
                        altName = PossessionItemAltName.TAROT_CARDS_TOWER
                    )

                )
            ),
            // Voodoo Doll
            CodexPossessionGroupRes(
                name = PossessionTitle.VOODOO_DOLL,
                icon = PossessionsIcon.VOODOO_DOLL,
                items = listOf(
                    CodexPossessionGroupItemRes(
                        image = PossessionItemImage.VOODOO_DOLL,
                        flavorText = PossessionItemFlavorText.VOODOO_DOLL,
                        infoText = PossessionItemInfoText.VOODOO_DOLL,
                        attributesText = PossessionItemAttribute.VOODOO_DOLL,
                        sanityDrain = PossessionItemSanityDrain.VOODOO_DOLL,
                        drawChance = PossessionItemDrawChance.VOODOO_DOLL
                    )
                )
            ),
        )

    fun fetchItems(): Result<List<CodexPossessionGroupDto>> {

        val cursedPossessionGroupsDto = mutableListOf<CodexPossessionGroupDto>()

        possessionsResources.forEach { resDto ->
            cursedPossessionGroupsDto.add(resDto.toLocal())
        }

        return Result.success(cursedPossessionGroupsDto)

    }

    private data class CodexPossessionGroupRes(
        val name: PossessionTitle,
        val icon: PossessionsIcon,
        val items: List<CodexPossessionGroupItemRes>
    )

    private data class CodexPossessionGroupItemRes(
        val image: PossessionItemImage,
        val flavorText: PossessionItemFlavorText,
        val infoText: PossessionItemInfoText,
        val attributesText: PossessionItemAttribute,
        val sanityDrain: PossessionItemSanityDrain,
        val drawChance: PossessionItemDrawChance,
        val altName: PossessionItemAltName? = null
    )

    private fun CodexPossessionGroupRes.toLocal() = CodexPossessionGroupDto(
        name = name,
        icon = icon,
        items = items.toLocal()
    )

    private fun CodexPossessionGroupItemRes.toLocal() = CodexPossessionGroupItemDto(
        image = image,
        flavorText = flavorText,
        infoText = infoText,
        attributesText = attributesText,
        sanityDrain = sanityDrain,
        drawChance = drawChance,
        altName = altName
    )

    @JvmName("CodexPossessionGroupResListToLocalCodexPossessionGroupRes")
    private fun List<CodexPossessionGroupRes>.toLocal() = map { dto ->
        dto.toLocal()
    }

    @JvmName("CodexPossessionGroupItemResListToLocalCodexPossessionGroupItemRes")
    private fun List<CodexPossessionGroupItemRes>.toLocal() = map { dto ->
        dto.toLocal()
    }
}
