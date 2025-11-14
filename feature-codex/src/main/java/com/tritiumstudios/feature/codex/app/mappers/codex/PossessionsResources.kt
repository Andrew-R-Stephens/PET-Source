package com.tritiumstudios.feature.codex.app.mappers.codex

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.operation.domain.codex.mappers.PossessionsResources.PossessionAttribute
import com.tritiumgaming.shared.operation.domain.codex.mappers.PossessionsResources.PossessionDescription
import com.tritiumgaming.shared.operation.domain.codex.mappers.PossessionsResources.PossessionDrawChance
import com.tritiumgaming.shared.operation.domain.codex.mappers.PossessionsResources.PossessionFlavor
import com.tritiumgaming.shared.operation.domain.codex.mappers.PossessionsResources.PossessionImage
import com.tritiumgaming.shared.operation.domain.codex.mappers.PossessionsResources.PossessionItemAltName
import com.tritiumgaming.shared.operation.domain.codex.mappers.PossessionsResources.PossessionSanityDrain
import com.tritiumgaming.shared.operation.domain.codex.mappers.PossessionsResources.PossessionTitle
import com.tritiumgaming.shared.operation.domain.codex.mappers.PossessionsResources.PossessionsIcon

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

@DrawableRes fun PossessionImage.toDrawableResource(): Int =
    when (this) {
        PossessionImage.HAUNTED_MIRROR -> R.drawable.icon_cursedpossessions_mirror
        PossessionImage.MONKEY_PAW -> R.drawable.icon_cursedpossessions_monkeyspawhand
        PossessionImage.MONKEY_PAW_WISH -> R.drawable.icon_cursedpossessions_monkeyspawwish
        PossessionImage.MUSIC_BOX -> R.drawable.icon_cursedpossessions_musicbox
        PossessionImage.OUIJA_BOARD -> R.drawable.icon_cursedpossessions_ouijaboard
        PossessionImage.SUMMONING_CIRCLE -> R.drawable.icon_cursedpossessions_summoningcircle
        PossessionImage.TAROT_CARDS_HANGED_MAN -> R.drawable.icon_cp_tarot_hangedman_alt
        PossessionImage.TAROT_CARDS_PRIESTESS -> R.drawable.icon_cp_tarot_priestess
        PossessionImage.TAROT_CARDS_DEATH -> R.drawable.icon_cp_tarot_death
        PossessionImage.TAROT_CARDS_DEVIL -> R.drawable.icon_cp_tarot_devil
        PossessionImage.TAROT_CARDS_FORTUNE -> R.drawable.icon_cp_tarot_fortune
        PossessionImage.TAROT_CARDS_HERMIT -> R.drawable.icon_cp_tarot_hermit
        PossessionImage.TAROT_CARDS_JOKER -> R.drawable.icon_cp_tarot_joker
        PossessionImage.TAROT_CARDS_MOON -> R.drawable.icon_cp_tarot_moon
        PossessionImage.TAROT_CARDS_SUN -> R.drawable.icon_cp_tarot_sun
        PossessionImage.TAROT_CARDS_TOWER -> R.drawable.icon_cp_tarot_tower
        PossessionImage.VOODOO_DOLL -> R.drawable.icon_cursedpossessions_voodoodoll
    }

@StringRes fun PossessionFlavor.toStringResource(): Int =
    when (this) {
        PossessionFlavor.HAUNTED_MIRROR -> R.string.shop_cursedpossessions_mirror_data_flavortext
        PossessionFlavor.MONKEY_PAW -> R.string.shop_cursedpossessions_monkeypaw_data_flavortext
        PossessionFlavor.MONKEY_PAW_WISH -> R.string.shop_cursedpossessions_monkeypawwish_data_flavortext
        PossessionFlavor.MUSIC_BOX -> R.string.shop_cursedpossessions_musicbox_data_flavortext
        PossessionFlavor.OUIJA_BOARD -> R.string.shop_cursedpossessions_ouijaboard_data_flavortext
        PossessionFlavor.SUMMONING_CIRCLE -> R.string.shop_cursedpossessions_summoningcircle_data_flavortext
        PossessionFlavor.TAROT_CARDS_HANGED_MAN -> R.string.shop_cursedpossessions_tarotcards_data_flavortext_hangedman
        PossessionFlavor.TAROT_CARDS_PRIESTESS -> R.string.shop_cursedpossessions_tarotcards_data_flavortext_priestess
        PossessionFlavor.TAROT_CARDS_DEATH -> R.string.shop_cursedpossessions_tarotcards_data_flavortext_death
        PossessionFlavor.TAROT_CARDS_DEVIL -> R.string.shop_cursedpossessions_tarotcards_data_flavortext_devil
        PossessionFlavor.TAROT_CARDS_FORTUNE -> R.string.shop_cursedpossessions_tarotcards_data_flavortext_fortune
        PossessionFlavor.TAROT_CARDS_HERMIT -> R.string.shop_cursedpossessions_tarotcards_data_flavortext_hermit
        PossessionFlavor.TAROT_CARDS_JOKER -> R.string.shop_cursedpossessions_tarotcards_data_flavortext_joker
        PossessionFlavor.TAROT_CARDS_MOON -> R.string.shop_cursedpossessions_tarotcards_data_flavortext_moon
        PossessionFlavor.TAROT_CARDS_SUN -> R.string.shop_cursedpossessions_tarotcards_data_flavortext_sun
        PossessionFlavor.TAROT_CARDS_TOWER -> R.string.shop_cursedpossessions_tarotcards_data_flavortext_tower
        PossessionFlavor.VOODOO_DOLL -> R.string.shop_cursedpossessions_voodoodoll_data_flavortext
    }

@StringRes fun PossessionDescription.toStringResource(): Int =
    when (this) {
        PossessionDescription.HAUNTED_MIRROR -> R.string.shop_cursedpossessions_mirror_data_info
        PossessionDescription.MONKEY_PAW -> R.string.shop_cursedpossessions_monkeypaw_data_info
        PossessionDescription.MONKEY_PAW_WISH -> R.string.shop_cursedpossessions_monkeypawwish_data_info
        PossessionDescription.MUSIC_BOX -> R.string.shop_cursedpossessions_musicbox_data_info
        PossessionDescription.OUIJA_BOARD -> R.string.shop_cursedpossessions_ouijaboard_data_info
        PossessionDescription.SUMMONING_CIRCLE -> R.string.shop_cursedpossessions_summoningcircle_data_info
        PossessionDescription.TAROT_CARDS_HANGED_MAN -> R.string.shop_cursedpossessions_tarotcards_data_info_hangedman
        PossessionDescription.TAROT_CARDS_PRIESTESS -> R.string.shop_cursedpossessions_tarotcards_data_info_priestess
        PossessionDescription.TAROT_CARDS_DEATH -> R.string.shop_cursedpossessions_tarotcards_data_info_death
        PossessionDescription.TAROT_CARDS_DEVIL -> R.string.shop_cursedpossessions_tarotcards_data_info_devil
        PossessionDescription.TAROT_CARDS_FORTUNE -> R.string.shop_cursedpossessions_tarotcards_data_info_fortune
        PossessionDescription.TAROT_CARDS_HERMIT -> R.string.shop_cursedpossessions_tarotcards_data_info_hermit
        PossessionDescription.TAROT_CARDS_JOKER -> R.string.shop_cursedpossessions_tarotcards_data_info_joker
        PossessionDescription.TAROT_CARDS_MOON -> R.string.shop_cursedpossessions_tarotcards_data_info_moon
        PossessionDescription.TAROT_CARDS_SUN -> R.string.shop_cursedpossessions_tarotcards_data_info_sun
        PossessionDescription.TAROT_CARDS_TOWER -> R.string.shop_cursedpossessions_tarotcards_data_info_tower
        PossessionDescription.VOODOO_DOLL -> R.string.shop_cursedpossessions_voodoodoll_data_info
    }

@StringRes fun PossessionAttribute.toStringResource(): Int =
    when (this) {
        PossessionAttribute.HAUNTED_MIRROR -> R.string.shop_cursedpossessions_mirror_data_attribute
        PossessionAttribute.MONKEY_PAW -> R.string.shop_cursedpossessions_monkeypaw_data_attribute
        PossessionAttribute.MONKEY_PAW_WISH -> R.string.shop_cursedpossessions_monkeypawwish_data_attribute
        PossessionAttribute.MUSIC_BOX -> R.string.shop_cursedpossessions_musicbox_data_attribute
        PossessionAttribute.OUIJA_BOARD -> R.string.shop_cursedpossessions_ouijaboard_data_attribute
        PossessionAttribute.SUMMONING_CIRCLE -> R.string.shop_cursedpossessions_summoningcircle_data_attribute
        PossessionAttribute.TAROT_CARDS_HANGED_MAN -> R.string.shop_cursedpossessions_tarotcards_data_attribute_hangedman
        PossessionAttribute.TAROT_CARDS_PRIESTESS -> R.string.shop_cursedpossessions_tarotcards_data_attribute_priestess
        PossessionAttribute.TAROT_CARDS_DEATH -> R.string.shop_cursedpossessions_tarotcards_data_attribute_death
        PossessionAttribute.TAROT_CARDS_DEVIL -> R.string.shop_cursedpossessions_tarotcards_data_attribute_devil
        PossessionAttribute.TAROT_CARDS_FORTUNE -> R.string.shop_cursedpossessions_tarotcards_data_attribute_fortune
        PossessionAttribute.TAROT_CARDS_HERMIT -> R.string.shop_cursedpossessions_tarotcards_data_attribute_hermit
        PossessionAttribute.TAROT_CARDS_JOKER -> R.string.shop_cursedpossessions_tarotcards_data_attribute_joker
        PossessionAttribute.TAROT_CARDS_MOON -> R.string.shop_cursedpossessions_tarotcards_data_attribute_moon
        PossessionAttribute.TAROT_CARDS_SUN -> R.string.shop_cursedpossessions_tarotcards_data_attribute_sun
        PossessionAttribute.TAROT_CARDS_TOWER -> R.string.shop_cursedpossessions_tarotcards_data_attribute_tower
        PossessionAttribute.VOODOO_DOLL -> R.string.shop_cursedpossessions_voodoodoll_data_attribute
    }

@StringRes fun PossessionSanityDrain.toStringResource(): Int =
    when (this) {
        PossessionSanityDrain.HAUNTED_MIRROR -> R.string.shop_cursedpossessions_mirror_data_sanitydrain
        PossessionSanityDrain.MONKEY_PAW -> R.string.shop_cursedpossessions_monkeypaw_data_sanitydrain
        PossessionSanityDrain.MONKEY_PAW_WISH -> R.string.shop_cursedpossessions_monkeypaw_data_sanitydrain
        PossessionSanityDrain.MUSIC_BOX -> R.string.shop_cursedpossessions_musicbox_data_sanitydrain
        PossessionSanityDrain.OUIJA_BOARD -> R.string.shop_cursedpossessions_ouijaboard_data_sanitydrain
        PossessionSanityDrain.SUMMONING_CIRCLE -> R.string.shop_cursedpossessions_summoningcircle_data_sanitydrain
        PossessionSanityDrain.TAROT_CARDS_HANGED_MAN -> R.string.shop_cursedpossessions_tarotcards_data_sanitydrain_hangedman
        PossessionSanityDrain.TAROT_CARDS_PRIESTESS -> R.string.shop_cursedpossessions_tarotcards_data_sanitydrain_priestess
        PossessionSanityDrain.TAROT_CARDS_DEATH -> R.string.shop_cursedpossessions_tarotcards_data_sanitydrain_death
        PossessionSanityDrain.TAROT_CARDS_DEVIL -> R.string.shop_cursedpossessions_tarotcards_data_sanitydrain_devil
        PossessionSanityDrain.TAROT_CARDS_FORTUNE -> R.string.shop_cursedpossessions_tarotcards_data_sanitydrain_fortune
        PossessionSanityDrain.TAROT_CARDS_HERMIT -> R.string.shop_cursedpossessions_tarotcards_data_sanitydrain_hermit
        PossessionSanityDrain.TAROT_CARDS_JOKER -> R.string.shop_cursedpossessions_tarotcards_data_sanitydrain_joker
        PossessionSanityDrain.TAROT_CARDS_MOON -> R.string.shop_cursedpossessions_tarotcards_data_sanitydrain_moon
        PossessionSanityDrain.TAROT_CARDS_SUN -> R.string.shop_cursedpossessions_tarotcards_data_sanitydrain_sun
        PossessionSanityDrain.TAROT_CARDS_TOWER -> R.string.shop_cursedpossessions_tarotcards_data_sanitydrain_tower
        PossessionSanityDrain.VOODOO_DOLL -> R.string.shop_cursedpossessions_voodoodoll_data_sanitydrain
    }

@StringRes fun PossessionDrawChance.toStringResource(): Int =
    when (this) {
        PossessionDrawChance.HAUNTED_MIRROR -> R.string.shop_cursedpossessions_mirror_data_drawchance
        PossessionDrawChance.MONKEY_PAW -> R.string.shop_cursedpossessions_monkeypaw_data_drawchance
        PossessionDrawChance.MONKEY_PAW_WISH -> R.string.shop_cursedpossessions_monkeypawwish_data_drawchance
        PossessionDrawChance.MUSIC_BOX -> R.string.shop_cursedpossessions_musicbox_data_drawchance
        PossessionDrawChance.OUIJA_BOARD -> R.string.shop_cursedpossessions_ouijaboard_data_drawchance
        PossessionDrawChance.SUMMONING_CIRCLE -> R.string.shop_cursedpossessions_summoningcircle_data_drawchance
        PossessionDrawChance.TAROT_CARDS_HANGED_MAN -> R.string.shop_cursedpossessions_tarotcards_data_drawchance_hangedman
        PossessionDrawChance.TAROT_CARDS_PRIESTESS -> R.string.shop_cursedpossessions_tarotcards_data_drawchance_priestess
        PossessionDrawChance.TAROT_CARDS_DEATH -> R.string.shop_cursedpossessions_tarotcards_data_drawchance_death
        PossessionDrawChance.TAROT_CARDS_DEVIL -> R.string.shop_cursedpossessions_tarotcards_data_drawchance_devil
        PossessionDrawChance.TAROT_CARDS_FORTUNE -> R.string.shop_cursedpossessions_tarotcards_data_drawchance_fortune
        PossessionDrawChance.TAROT_CARDS_HERMIT -> R.string.shop_cursedpossessions_tarotcards_data_drawchance_hermit
        PossessionDrawChance.TAROT_CARDS_JOKER -> R.string.shop_cursedpossessions_tarotcards_data_drawchance_joker
        PossessionDrawChance.TAROT_CARDS_MOON -> R.string.shop_cursedpossessions_tarotcards_data_drawchance_moon
        PossessionDrawChance.TAROT_CARDS_SUN -> R.string.shop_cursedpossessions_tarotcards_data_drawchance_sun
        PossessionDrawChance.TAROT_CARDS_TOWER -> R.string.shop_cursedpossessions_tarotcards_data_drawchance_tower
        PossessionDrawChance.VOODOO_DOLL -> R.string.shop_cursedpossessions_voodoodoll_data_drawchance
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