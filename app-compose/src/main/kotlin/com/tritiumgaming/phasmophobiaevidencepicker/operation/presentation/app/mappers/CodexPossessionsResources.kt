package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemAltName
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemAttribute
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemDrawChance
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemFlavorText
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemImage
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemInfoText
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemSanityDrain
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexPossessionsResources.PossessionTitle
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexPossessionsResources.PossessionsIcon

@StringRes fun PossessionTitle.toStringResource(): Int =
    when (this) {
        PossessionTitle.HAUNTED_MIRROR -> R.string.cursedpossessions_info_name_mirror
        PossessionTitle.MONKEY_PAW -> R.string.cursedpossessions_info_name_monkeypaw
        PossessionTitle.MUSIC_BOX -> R.string.cursedpossessions_info_name_musicbox
        PossessionTitle.OUIJA_BOARD -> R.string.cursedpossessions_info_name_ouijaboard
        PossessionTitle.SUMMONING_CIRCLE -> R.string.cursedpossessions_info_name_summoningcircle
        PossessionTitle.TAROT_CARDS -> R.string.cursedpossessions_info_name_tarotcards
        PossessionTitle.VOODOO_DOLL -> R.string.cursedpossessions_info_name_voodoodoll
    }

@DrawableRes fun PossessionsIcon.toDrawableResource(): Int =
    when (this) {
        PossessionsIcon.HAUNTED_MIRROR -> R.drawable.ic_map_cp_mirror
        PossessionsIcon.MONKEY_PAW -> R.drawable.ic_map_cp_paw
        PossessionsIcon.MUSIC_BOX -> R.drawable.ic_map_cp_musicbox
        PossessionsIcon.OUIJA_BOARD -> R.drawable.ic_map_cp_ouija
        PossessionsIcon.SUMMONING_CIRCLE -> R.drawable.ic_map_cp_candle
        PossessionsIcon.TAROT_CARDS -> R.drawable.ic_map_cp_tarot
        PossessionsIcon.VOODOO_DOLL -> R.drawable.ic_map_cp_doll
    }

@DrawableRes fun PossessionItemImage.toDrawableResource(): Int =
    when (this) {
        PossessionItemImage.HAUNTED_MIRROR -> R.drawable.icon_cursedpossessions_mirror
        PossessionItemImage.MONKEY_PAW -> R.drawable.icon_cursedpossessions_monkeyspawhand
        PossessionItemImage.MONKEY_PAW_WISH -> R.drawable.icon_cursedpossessions_monkeyspawwish
        PossessionItemImage.MUSIC_BOX -> R.drawable.icon_cursedpossessions_musicbox
        PossessionItemImage.OUIJA_BOARD -> R.drawable.icon_cursedpossessions_ouijaboard
        PossessionItemImage.SUMMONING_CIRCLE -> R.drawable.icon_cursedpossessions_summoningcircle
        PossessionItemImage.TAROT_CARDS_HANGED_MAN -> R.drawable.icon_cp_tarot_hangedman_alt
        PossessionItemImage.TAROT_CARDS_PRIESTESS -> R.drawable.icon_cp_tarot_priestess
        PossessionItemImage.TAROT_CARDS_DEATH -> R.drawable.icon_cp_tarot_death
        PossessionItemImage.TAROT_CARDS_DEVIL -> R.drawable.icon_cp_tarot_devil
        PossessionItemImage.TAROT_CARDS_FORTUNE -> R.drawable.icon_cp_tarot_fortune
        PossessionItemImage.TAROT_CARDS_HERMIT -> R.drawable.icon_cp_tarot_hermit
        PossessionItemImage.TAROT_CARDS_JOKER -> R.drawable.icon_cp_tarot_joker
        PossessionItemImage.TAROT_CARDS_MOON -> R.drawable.icon_cp_tarot_moon
        PossessionItemImage.TAROT_CARDS_SUN -> R.drawable.icon_cp_tarot_sun
        PossessionItemImage.TAROT_CARDS_TOWER -> R.drawable.icon_cp_tarot_tower
        PossessionItemImage.VOODOO_DOLL -> R.drawable.icon_cursedpossessions_voodoodoll
    }

@StringRes fun PossessionItemFlavorText.toStringResource(): Int =
    when (this) {
        PossessionItemFlavorText.HAUNTED_MIRROR -> R.string.shop_cursedpossessions_mirror_data_flavortext
        PossessionItemFlavorText.MONKEY_PAW -> R.string.shop_cursedpossessions_monkeypaw_data_flavortext
        PossessionItemFlavorText.MONKEY_PAW_WISH -> R.string.shop_cursedpossessions_monkeypawwish_data_flavortext
        PossessionItemFlavorText.MUSIC_BOX -> R.string.shop_cursedpossessions_musicbox_data_flavortext
        PossessionItemFlavorText.OUIJA_BOARD -> R.string.shop_cursedpossessions_ouijaboard_data_flavortext
        PossessionItemFlavorText.SUMMONING_CIRCLE -> R.string.shop_cursedpossessions_summoningcircle_data_flavortext
        PossessionItemFlavorText.TAROT_CARDS_HANGED_MAN -> R.string.shop_cursedpossessions_tarotcards_data_flavortext_hangedman
        PossessionItemFlavorText.TAROT_CARDS_PRIESTESS -> R.string.shop_cursedpossessions_tarotcards_data_flavortext_priestess
        PossessionItemFlavorText.TAROT_CARDS_DEATH -> R.string.shop_cursedpossessions_tarotcards_data_flavortext_death
        PossessionItemFlavorText.TAROT_CARDS_DEVIL -> R.string.shop_cursedpossessions_tarotcards_data_flavortext_devil
        PossessionItemFlavorText.TAROT_CARDS_FORTUNE -> R.string.shop_cursedpossessions_tarotcards_data_flavortext_fortune
        PossessionItemFlavorText.TAROT_CARDS_HERMIT -> R.string.shop_cursedpossessions_tarotcards_data_flavortext_hermit
        PossessionItemFlavorText.TAROT_CARDS_JOKER -> R.string.shop_cursedpossessions_tarotcards_data_flavortext_joker
        PossessionItemFlavorText.TAROT_CARDS_MOON -> R.string.shop_cursedpossessions_tarotcards_data_flavortext_moon
        PossessionItemFlavorText.TAROT_CARDS_SUN -> R.string.shop_cursedpossessions_tarotcards_data_flavortext_sun
        PossessionItemFlavorText.TAROT_CARDS_TOWER -> R.string.shop_cursedpossessions_tarotcards_data_flavortext_tower
        PossessionItemFlavorText.VOODOO_DOLL -> R.string.shop_cursedpossessions_voodoodoll_data_flavortext
    }

@StringRes fun PossessionItemInfoText.toStringResource(): Int =
    when (this) {
        PossessionItemInfoText.HAUNTED_MIRROR -> R.string.shop_cursedpossessions_mirror_data_info
        PossessionItemInfoText.MONKEY_PAW -> R.string.shop_cursedpossessions_monkeypaw_data_info
        PossessionItemInfoText.MONKEY_PAW_WISH -> R.string.shop_cursedpossessions_monkeypawwish_data_info
        PossessionItemInfoText.MUSIC_BOX -> R.string.shop_cursedpossessions_musicbox_data_info
        PossessionItemInfoText.OUIJA_BOARD -> R.string.shop_cursedpossessions_ouijaboard_data_info
        PossessionItemInfoText.SUMMONING_CIRCLE -> R.string.shop_cursedpossessions_summoningcircle_data_info
        PossessionItemInfoText.TAROT_CARDS_HANGED_MAN -> R.string.shop_cursedpossessions_tarotcards_data_info_hangedman
        PossessionItemInfoText.TAROT_CARDS_PRIESTESS -> R.string.shop_cursedpossessions_tarotcards_data_info_priestess
        PossessionItemInfoText.TAROT_CARDS_DEATH -> R.string.shop_cursedpossessions_tarotcards_data_info_death
        PossessionItemInfoText.TAROT_CARDS_DEVIL -> R.string.shop_cursedpossessions_tarotcards_data_info_devil
        PossessionItemInfoText.TAROT_CARDS_FORTUNE -> R.string.shop_cursedpossessions_tarotcards_data_info_fortune
        PossessionItemInfoText.TAROT_CARDS_HERMIT -> R.string.shop_cursedpossessions_tarotcards_data_info_hermit
        PossessionItemInfoText.TAROT_CARDS_JOKER -> R.string.shop_cursedpossessions_tarotcards_data_info_joker
        PossessionItemInfoText.TAROT_CARDS_MOON -> R.string.shop_cursedpossessions_tarotcards_data_info_moon
        PossessionItemInfoText.TAROT_CARDS_SUN -> R.string.shop_cursedpossessions_tarotcards_data_info_sun
        PossessionItemInfoText.TAROT_CARDS_TOWER -> R.string.shop_cursedpossessions_tarotcards_data_info_tower
        PossessionItemInfoText.VOODOO_DOLL -> R.string.shop_cursedpossessions_voodoodoll_data_info
    }

@StringRes fun PossessionItemAttribute.toStringResource(): Int =
    when (this) {
        PossessionItemAttribute.HAUNTED_MIRROR -> R.string.shop_cursedpossessions_mirror_data_attribute
        PossessionItemAttribute.MONKEY_PAW -> R.string.shop_cursedpossessions_monkeypaw_data_attribute
        PossessionItemAttribute.MONKEY_PAW_WISH -> R.string.shop_cursedpossessions_monkeypawwish_data_attribute
        PossessionItemAttribute.MUSIC_BOX -> R.string.shop_cursedpossessions_musicbox_data_attribute
        PossessionItemAttribute.OUIJA_BOARD -> R.string.shop_cursedpossessions_ouijaboard_data_attribute
        PossessionItemAttribute.SUMMONING_CIRCLE -> R.string.shop_cursedpossessions_summoningcircle_data_attribute
        PossessionItemAttribute.TAROT_CARDS_HANGED_MAN -> R.string.shop_cursedpossessions_tarotcards_data_attribute_hangedman
        PossessionItemAttribute.TAROT_CARDS_PRIESTESS -> R.string.shop_cursedpossessions_tarotcards_data_attribute_priestess
        PossessionItemAttribute.TAROT_CARDS_DEATH -> R.string.shop_cursedpossessions_tarotcards_data_attribute_death
        PossessionItemAttribute.TAROT_CARDS_DEVIL -> R.string.shop_cursedpossessions_tarotcards_data_attribute_devil
        PossessionItemAttribute.TAROT_CARDS_FORTUNE -> R.string.shop_cursedpossessions_tarotcards_data_attribute_fortune
        PossessionItemAttribute.TAROT_CARDS_HERMIT -> R.string.shop_cursedpossessions_tarotcards_data_attribute_hermit
        PossessionItemAttribute.TAROT_CARDS_JOKER -> R.string.shop_cursedpossessions_tarotcards_data_attribute_joker
        PossessionItemAttribute.TAROT_CARDS_MOON -> R.string.shop_cursedpossessions_tarotcards_data_attribute_moon
        PossessionItemAttribute.TAROT_CARDS_SUN -> R.string.shop_cursedpossessions_tarotcards_data_attribute_sun
        PossessionItemAttribute.TAROT_CARDS_TOWER -> R.string.shop_cursedpossessions_tarotcards_data_attribute_tower
        PossessionItemAttribute.VOODOO_DOLL -> R.string.shop_cursedpossessions_voodoodoll_data_attribute
    }

@StringRes fun PossessionItemSanityDrain.toStringResource(): Int =
    when (this) {
        PossessionItemSanityDrain.HAUNTED_MIRROR -> R.string.shop_cursedpossessions_mirror_data_sanitydrain
        PossessionItemSanityDrain.MONKEY_PAW -> R.string.shop_cursedpossessions_monkeypaw_data_sanitydrain
        PossessionItemSanityDrain.MONKEY_PAW_WISH -> R.string.shop_cursedpossessions_monkeypaw_data_sanitydrain
        PossessionItemSanityDrain.MUSIC_BOX -> R.string.shop_cursedpossessions_musicbox_data_sanitydrain
        PossessionItemSanityDrain.OUIJA_BOARD -> R.string.shop_cursedpossessions_ouijaboard_data_sanitydrain
        PossessionItemSanityDrain.SUMMONING_CIRCLE -> R.string.shop_cursedpossessions_summoningcircle_data_sanitydrain
        PossessionItemSanityDrain.TAROT_CARDS_HANGED_MAN -> R.string.shop_cursedpossessions_tarotcards_data_sanitydrain_hangedman
        PossessionItemSanityDrain.TAROT_CARDS_PRIESTESS -> R.string.shop_cursedpossessions_tarotcards_data_sanitydrain_priestess
        PossessionItemSanityDrain.TAROT_CARDS_DEATH -> R.string.shop_cursedpossessions_tarotcards_data_sanitydrain_death
        PossessionItemSanityDrain.TAROT_CARDS_DEVIL -> R.string.shop_cursedpossessions_tarotcards_data_sanitydrain_devil
        PossessionItemSanityDrain.TAROT_CARDS_FORTUNE -> R.string.shop_cursedpossessions_tarotcards_data_sanitydrain_fortune
        PossessionItemSanityDrain.TAROT_CARDS_HERMIT -> R.string.shop_cursedpossessions_tarotcards_data_sanitydrain_hermit
        PossessionItemSanityDrain.TAROT_CARDS_JOKER -> R.string.shop_cursedpossessions_tarotcards_data_sanitydrain_joker
        PossessionItemSanityDrain.TAROT_CARDS_MOON -> R.string.shop_cursedpossessions_tarotcards_data_sanitydrain_moon
        PossessionItemSanityDrain.TAROT_CARDS_SUN -> R.string.shop_cursedpossessions_tarotcards_data_sanitydrain_sun
        PossessionItemSanityDrain.TAROT_CARDS_TOWER -> R.string.shop_cursedpossessions_tarotcards_data_sanitydrain_tower
        PossessionItemSanityDrain.VOODOO_DOLL -> R.string.shop_cursedpossessions_voodoodoll_data_sanitydrain
    }

@StringRes fun PossessionItemDrawChance.toStringResource(): Int =
    when (this) {
        PossessionItemDrawChance.HAUNTED_MIRROR -> R.string.shop_cursedpossessions_mirror_data_drawchance
        PossessionItemDrawChance.MONKEY_PAW -> R.string.shop_cursedpossessions_monkeypaw_data_drawchance
        PossessionItemDrawChance.MONKEY_PAW_WISH -> R.string.shop_cursedpossessions_monkeypawwish_data_drawchance
        PossessionItemDrawChance.MUSIC_BOX -> R.string.shop_cursedpossessions_musicbox_data_drawchance
        PossessionItemDrawChance.OUIJA_BOARD -> R.string.shop_cursedpossessions_ouijaboard_data_drawchance
        PossessionItemDrawChance.SUMMONING_CIRCLE -> R.string.shop_cursedpossessions_summoningcircle_data_drawchance
        PossessionItemDrawChance.TAROT_CARDS_HANGED_MAN -> R.string.shop_cursedpossessions_tarotcards_data_drawchance_hangedman
        PossessionItemDrawChance.TAROT_CARDS_PRIESTESS -> R.string.shop_cursedpossessions_tarotcards_data_drawchance_priestess
        PossessionItemDrawChance.TAROT_CARDS_DEATH -> R.string.shop_cursedpossessions_tarotcards_data_drawchance_death
        PossessionItemDrawChance.TAROT_CARDS_DEVIL -> R.string.shop_cursedpossessions_tarotcards_data_drawchance_devil
        PossessionItemDrawChance.TAROT_CARDS_FORTUNE -> R.string.shop_cursedpossessions_tarotcards_data_drawchance_fortune
        PossessionItemDrawChance.TAROT_CARDS_HERMIT -> R.string.shop_cursedpossessions_tarotcards_data_drawchance_hermit
        PossessionItemDrawChance.TAROT_CARDS_JOKER -> R.string.shop_cursedpossessions_tarotcards_data_drawchance_joker
        PossessionItemDrawChance.TAROT_CARDS_MOON -> R.string.shop_cursedpossessions_tarotcards_data_drawchance_moon
        PossessionItemDrawChance.TAROT_CARDS_SUN -> R.string.shop_cursedpossessions_tarotcards_data_drawchance_sun
        PossessionItemDrawChance.TAROT_CARDS_TOWER -> R.string.shop_cursedpossessions_tarotcards_data_drawchance_tower
        PossessionItemDrawChance.VOODOO_DOLL -> R.string.shop_cursedpossessions_voodoodoll_data_drawchance
    }

@StringRes fun PossessionItemAltName.toStringResource(): Int? =
    when (this) {
        PossessionItemAltName.HAUNTED_MIRROR -> null
        PossessionItemAltName.MONKEY_PAW -> R.string.shop_cursedpossessions_monkeypaw_data_altname
        PossessionItemAltName.MONKEY_PAW_WISH -> R.string.shop_cursedpossessions_monkeypawwish_data_altname
        PossessionItemAltName.MUSIC_BOX -> null
        PossessionItemAltName.OUIJA_BOARD -> null
        PossessionItemAltName.SUMMONING_CIRCLE -> null
        PossessionItemAltName.TAROT_CARDS_HANGED_MAN -> R.string.shop_cursedpossessions_tarotcards_data_altname_hangedman
        PossessionItemAltName.TAROT_CARDS_PRIESTESS -> R.string.shop_cursedpossessions_tarotcards_data_altname_priestess
        PossessionItemAltName.TAROT_CARDS_DEATH -> R.string.shop_cursedpossessions_tarotcards_data_altname_death
        PossessionItemAltName.TAROT_CARDS_DEVIL -> R.string.shop_cursedpossessions_tarotcards_data_altname_devil
        PossessionItemAltName.TAROT_CARDS_FORTUNE -> R.string.shop_cursedpossessions_tarotcards_data_altname_fortune
        PossessionItemAltName.TAROT_CARDS_HERMIT -> R.string.shop_cursedpossessions_tarotcards_data_altname_hermit
        PossessionItemAltName.TAROT_CARDS_JOKER -> R.string.shop_cursedpossessions_tarotcards_data_altname_joker
        PossessionItemAltName.TAROT_CARDS_MOON -> R.string.shop_cursedpossessions_tarotcards_data_altname_moon
        PossessionItemAltName.TAROT_CARDS_SUN -> R.string.shop_cursedpossessions_tarotcards_data_altname_sun
        PossessionItemAltName.TAROT_CARDS_TOWER -> R.string.shop_cursedpossessions_tarotcards_data_altname_tower
        PossessionItemAltName.VOODOO_DOLL -> null
    }