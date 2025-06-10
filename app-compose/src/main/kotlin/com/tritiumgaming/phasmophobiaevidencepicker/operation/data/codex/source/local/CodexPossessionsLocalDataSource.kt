package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.dto.CodexPossessionGroupDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.dto.CodexPossessionGroupItemDto

class CodexPossessionsLocalDataSource {

    private val possessionsResources
        get() = listOf(
            // Mirror
            CodexPossessionGroupRes(
                name = R.string.cursedpossessions_info_name_mirror,
                icon = R.drawable.ic_map_cp_mirror,
                items = listOf(
                    CodexPossessionGroupItemRes(
                        image = R.drawable.icon_cursedpossessions_mirror,
                        flavorText = R.string.shop_cursedpossessions_mirror_data_flavortext,
                        infoText = R.string.shop_cursedpossessions_mirror_data_info,
                        attributesText = R.string.shop_cursedpossessions_mirror_data_attribute,
                        sanityDrain = R.string.shop_cursedpossessions_mirror_data_sanitydrain,
                        drawChance = R.string.shop_cursedpossessions_mirror_data_drawchance
                    )
                )
            ),
            // Monkey Paw
            CodexPossessionGroupRes(
                name = R.string.cursedpossessions_info_name_monkeypaw,
                icon = R.drawable.ic_map_cp_paw,
                items = listOf(

                    CodexPossessionGroupItemRes(
                        image = R.drawable.icon_cursedpossessions_monkeyspawhand,
                        flavorText = R.string.shop_cursedpossessions_monkeypaw_data_flavortext,
                        infoText = R.string.shop_cursedpossessions_monkeypaw_data_info,
                        attributesText = R.string.shop_cursedpossessions_monkeypaw_data_attribute,
                        sanityDrain = R.string.shop_cursedpossessions_monkeypaw_data_sanitydrain,
                        drawChance = R.string.shop_cursedpossessions_monkeypaw_data_drawchance,
                        altName = R.string.shop_cursedpossessions_monkeypaw_data_altname
                    ),
                    CodexPossessionGroupItemRes(
                        image = R.drawable.icon_cursedpossessions_monkeyspawwish,
                        flavorText = R.string.shop_cursedpossessions_monkeypawwish_data_flavortext,
                        infoText = R.string.shop_cursedpossessions_monkeypawwish_data_info,
                        attributesText = R.string.shop_cursedpossessions_monkeypaw_data_attribute,
                        sanityDrain = R.string.shop_cursedpossessions_monkeypaw_data_sanitydrain,
                        drawChance = R.string.shop_cursedpossessions_monkeypaw_data_drawchance,
                        altName = R.string.shop_cursedpossessions_monkeypawwish_data_altname
                    )
                )
            ),
            // Music Box
            CodexPossessionGroupRes(
                name = R.string.cursedpossessions_info_name_musicbox,
                icon = R.drawable.ic_map_cp_musicbox,
                items = listOf(
                    CodexPossessionGroupItemRes(
                        image = R.drawable.icon_cursedpossessions_musicbox,
                        flavorText = R.string.shop_cursedpossessions_musicbox_data_flavortext,
                        infoText = R.string.shop_cursedpossessions_musicbox_data_info,
                        attributesText = R.string.shop_cursedpossessions_musicbox_data_attribute,
                        sanityDrain = R.string.shop_cursedpossessions_musicbox_data_sanitydrain,
                        drawChance = R.string.shop_cursedpossessions_musicbox_data_drawchance
                    )

                )
            ),
            // Ouija Board
            CodexPossessionGroupRes(
                name = R.string.cursedpossessions_info_name_ouijaboard,
                icon = R.drawable.ic_map_cp_ouija,
                items = listOf(
                    CodexPossessionGroupItemRes(
                        image = R.drawable.icon_cursedpossessions_ouijaboard,
                        flavorText = R.string.shop_cursedpossessions_ouijaboard_data_flavortext,
                        infoText = R.string.shop_cursedpossessions_ouijaboard_data_info,
                        attributesText = R.string.shop_cursedpossessions_ouijaboard_data_attribute,
                        sanityDrain = R.string.shop_cursedpossessions_ouijaboard_data_sanitydrain,
                        drawChance = R.string.shop_cursedpossessions_ouijaboard_data_drawchance
                    )
                )
            ),
            // Summoning  Circle
            CodexPossessionGroupRes(
                name = R.string.cursedpossessions_info_name_summoningcircle,
                icon = R.drawable.ic_map_cp_candle,
                items = listOf(
                    CodexPossessionGroupItemRes(
                        image = R.drawable.icon_cursedpossessions_summoningcircle,
                        flavorText = R.string.shop_cursedpossessions_summoningcircle_data_flavortext,
                        infoText = R.string.shop_cursedpossessions_summoningcircle_data_info,
                        attributesText = R.string.shop_cursedpossessions_summoningcircle_data_attribute,
                        sanityDrain = R.string.shop_cursedpossessions_summoningcircle_data_sanitydrain,
                        drawChance = R.string.shop_cursedpossessions_summoningcircle_data_drawchance
                    )
                )
            ),
            // Tarot Cards
            CodexPossessionGroupRes(
                name = R.string.cursedpossessions_info_name_tarotcards,
                icon = R.drawable.ic_map_cp_tarot,
                items = listOf(
                    CodexPossessionGroupItemRes(
                        image = R.drawable.icon_cp_tarot_hangedman_alt,
                        flavorText = R.string.shop_cursedpossessions_tarotcards_data_flavortext_hangedman,
                        infoText = R.string.shop_cursedpossessions_tarotcards_data_info_hangedman,
                        attributesText = R.string.shop_cursedpossessions_tarotcards_data_attribute_hangedman,
                        sanityDrain = R.string.shop_cursedpossessions_tarotcards_data_sanitydrain_hangedman,
                        drawChance = R.string.shop_cursedpossessions_tarotcards_data_drawchance_hangedman,
                        altName = R.string.shop_cursedpossessions_tarotcards_data_altname_hangedman
                    ),
                    CodexPossessionGroupItemRes(
                        image = R.drawable.icon_cp_tarot_priestess,
                        flavorText = R.string.shop_cursedpossessions_tarotcards_data_flavortext_priestess,
                        infoText = R.string.shop_cursedpossessions_tarotcards_data_info_priestess,
                        attributesText = R.string.shop_cursedpossessions_tarotcards_data_attribute_priestess,
                        sanityDrain = R.string.shop_cursedpossessions_tarotcards_data_sanitydrain_priestess,
                        drawChance = R.string.shop_cursedpossessions_tarotcards_data_drawchance_priestess,
                        altName = R.string.shop_cursedpossessions_tarotcards_data_altname_priestess
                    ),
                    CodexPossessionGroupItemRes(
                        image = R.drawable.icon_cp_tarot_death,
                        flavorText = R.string.shop_cursedpossessions_tarotcards_data_flavortext_death,
                        infoText = R.string.shop_cursedpossessions_tarotcards_data_info_death,
                        attributesText = R.string.shop_cursedpossessions_tarotcards_data_attribute_death,
                        sanityDrain = R.string.shop_cursedpossessions_tarotcards_data_sanitydrain_death,
                        drawChance = R.string.shop_cursedpossessions_tarotcards_data_drawchance_death,
                        altName = R.string.shop_cursedpossessions_tarotcards_data_altname_death
                    ),
                    CodexPossessionGroupItemRes(
                        image = R.drawable.icon_cp_tarot_devil,
                        flavorText = R.string.shop_cursedpossessions_tarotcards_data_flavortext_devil,
                        infoText = R.string.shop_cursedpossessions_tarotcards_data_info_devil,
                        attributesText = R.string.shop_cursedpossessions_tarotcards_data_attribute_devil,
                        sanityDrain = R.string.shop_cursedpossessions_tarotcards_data_sanitydrain_devil,
                        drawChance = R.string.shop_cursedpossessions_tarotcards_data_drawchance_devil,
                        altName = R.string.shop_cursedpossessions_tarotcards_data_altname_devil
                    ),
                    CodexPossessionGroupItemRes(
                        image = R.drawable.icon_cp_tarot_fortune,
                        flavorText = R.string.shop_cursedpossessions_tarotcards_data_flavortext_fortune,
                        infoText = R.string.shop_cursedpossessions_tarotcards_data_info_fortune,
                        attributesText = R.string.shop_cursedpossessions_tarotcards_data_attribute_fortune,
                        sanityDrain = R.string.shop_cursedpossessions_tarotcards_data_sanitydrain_fortune,
                        drawChance = R.string.shop_cursedpossessions_tarotcards_data_drawchance_fortune,
                        altName = R.string.shop_cursedpossessions_tarotcards_data_altname_fortune
                    ),
                    CodexPossessionGroupItemRes(
                        image = R.drawable.icon_cp_tarot_hermit,
                        flavorText = R.string.shop_cursedpossessions_tarotcards_data_flavortext_hermit,
                        infoText = R.string.shop_cursedpossessions_tarotcards_data_info_hermit,
                        attributesText = R.string.shop_cursedpossessions_tarotcards_data_attribute_hermit,
                        sanityDrain = R.string.shop_cursedpossessions_tarotcards_data_sanitydrain_hermit,
                        drawChance = R.string.shop_cursedpossessions_tarotcards_data_drawchance_hermit,
                        altName = R.string.shop_cursedpossessions_tarotcards_data_altname_hermit
                    ),
                    CodexPossessionGroupItemRes(
                        image = R.drawable.icon_cp_tarot_joker,
                        flavorText = R.string.shop_cursedpossessions_tarotcards_data_flavortext_joker,
                        infoText = R.string.shop_cursedpossessions_tarotcards_data_info_joker,
                        attributesText = R.string.shop_cursedpossessions_tarotcards_data_attribute_joker,
                        sanityDrain = R.string.shop_cursedpossessions_tarotcards_data_sanitydrain_joker,
                        drawChance = R.string.shop_cursedpossessions_tarotcards_data_drawchance_joker,
                        altName = R.string.shop_cursedpossessions_tarotcards_data_altname_joker
                    ),
                    CodexPossessionGroupItemRes(
                        image = R.drawable.icon_cp_tarot_moon,
                        flavorText = R.string.shop_cursedpossessions_tarotcards_data_flavortext_moon,
                        infoText = R.string.shop_cursedpossessions_tarotcards_data_info_moon,
                        attributesText = R.string.shop_cursedpossessions_tarotcards_data_attribute_moon,
                        sanityDrain = R.string.shop_cursedpossessions_tarotcards_data_sanitydrain_moon,
                        drawChance = R.string.shop_cursedpossessions_tarotcards_data_drawchance_moon,
                        altName = R.string.shop_cursedpossessions_tarotcards_data_altname_moon
                    ),
                    CodexPossessionGroupItemRes(
                        image = R.drawable.icon_cp_tarot_sun,
                        flavorText = R.string.shop_cursedpossessions_tarotcards_data_flavortext_sun,
                        infoText = R.string.shop_cursedpossessions_tarotcards_data_info_sun,
                        attributesText = R.string.shop_cursedpossessions_tarotcards_data_attribute_sun,
                        sanityDrain = R.string.shop_cursedpossessions_tarotcards_data_sanitydrain_sun,
                        drawChance = R.string.shop_cursedpossessions_tarotcards_data_drawchance_sun,
                        altName = R.string.shop_cursedpossessions_tarotcards_data_altname_sun
                    ),
                    CodexPossessionGroupItemRes(
                        image = R.drawable.icon_cp_tarot_tower,
                        flavorText = R.string.shop_cursedpossessions_tarotcards_data_flavortext_tower,
                        infoText = R.string.shop_cursedpossessions_tarotcards_data_info_tower,
                        attributesText = R.string.shop_cursedpossessions_tarotcards_data_attribute_tower,
                        sanityDrain = R.string.shop_cursedpossessions_tarotcards_data_sanitydrain_tower,
                        drawChance = R.string.shop_cursedpossessions_tarotcards_data_drawchance_tower,
                        altName = R.string.shop_cursedpossessions_tarotcards_data_altname_tower
                    )

                )
            ),
            // Voodoo Doll
            CodexPossessionGroupRes(
                name = R.string.cursedpossessions_info_name_voodoodoll,
                icon = R.drawable.ic_map_cp_doll,
                items = listOf(
                    CodexPossessionGroupItemRes(
                        image = R.drawable.icon_cursedpossessions_voodoodoll,
                        flavorText =  R.string.shop_cursedpossessions_voodoodoll_data_flavortext,
                        infoText =  R.string.shop_cursedpossessions_voodoodoll_data_info,
                        attributesText = R.string.shop_cursedpossessions_voodoodoll_data_attribute,
                        sanityDrain = R.string.shop_cursedpossessions_voodoodoll_data_sanitydrain,
                        drawChance = R.string.shop_cursedpossessions_voodoodoll_data_drawchance
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
        @StringRes val name: Int,
        @DrawableRes val icon: Int,
        val items: List<CodexPossessionGroupItemRes>
    )

    private data class CodexPossessionGroupItemRes(
        @DrawableRes val image: Int,
        @StringRes val flavorText: Int,
        @StringRes val infoText: Int,
        @StringRes val attributesText: Int,
        @StringRes val sanityDrain: Int,
        @StringRes val drawChance: Int,
        @StringRes val altName: Int? = null,
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
