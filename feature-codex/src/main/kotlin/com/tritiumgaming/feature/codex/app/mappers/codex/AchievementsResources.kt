package com.tritiumgaming.feature.codex.app.mappers.codex

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementContent
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementExclusivity
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementIcon
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementTitle

@StringRes fun AchievementTitle.toStringResource(): Int =
    when (this) {
        AchievementTitle.TRAINING_WHEELS -> R.string.achievements_trainingwheels_name
        AchievementTitle.ROOKIE -> R.string.achievements_rookie_name
        AchievementTitle.PROFESSIONAL -> R.string.achievements_professional_name
        AchievementTitle.BOSS -> R.string.achievements_boss_name
        AchievementTitle.EXTRA_MILE -> R.string.achievements_extramile_name
        AchievementTitle.DEDICATED -> R.string.achievements_dedicated_name
        AchievementTitle.DEVOTED -> R.string.achievements_devoted_name
        AchievementTitle.CHALLENGER -> R.string.achievements_challenger_name
        AchievementTitle.RISE_CHALLENGE -> R.string.achievements_risechallenge_name
        AchievementTitle.TAKE_CHALLENGES -> R.string.achievements_takingchallenges_name
        AchievementTitle.CHUMP_CHANGE -> R.string.achievements_chumpchange_name
        AchievementTitle.FAT_STOCK -> R.string.achievements_fatstock_name
        AchievementTitle.CASH_COW -> R.string.achievements_cashcow_name
        AchievementTitle.BREAK_THE_BANK -> R.string.achievements_breakthebank_name
        AchievementTitle.BARE_ESSENTIALS -> R.string.achievements_bareessentials_name
        AchievementTitle.TOOLS_OF_THE_TRADE -> R.string.achievements_toolsofthetrade_name
        AchievementTitle.FULLY_LOADED -> R.string.achievements_fullyloaded_name
        AchievementTitle.DIRECTOR -> R.string.achievements_director_name
        AchievementTitle.BRONZE_HUNTER -> R.string.achievements_bronzehunter_name
        AchievementTitle.SILVER_HUNTER -> R.string.achievements_silverhunter_name
        AchievementTitle.GOLD_HUNTER -> R.string.achievements_goldhunter_name
        AchievementTitle.BANSHEE -> R.string.achievements_banshee_name
        AchievementTitle.DEMON -> R.string.achievements_demon_name
        AchievementTitle.DEOGEN -> R.string.achievements_deogen_name
        AchievementTitle.GORYO -> R.string.achievements_goryo_name
        AchievementTitle.HANTU -> R.string.achievements_hantu_name
        AchievementTitle.JINN -> R.string.achievements_jinn_name
        AchievementTitle.MARE -> R.string.achievements_mare_name
        AchievementTitle.MOROI -> R.string.achievements_moroi_name
        AchievementTitle.MYLING -> R.string.achievements_myling_name
        AchievementTitle.OBAKE -> R.string.achievements_obake_name
        AchievementTitle.ONI -> R.string.achievements_oni_name
        AchievementTitle.ONRYO -> R.string.achievements_onryo_name
        AchievementTitle.PHANTOM -> R.string.achievements_phantom_name
        AchievementTitle.POLTERGEIST -> R.string.achievements_poltergeist_name
        AchievementTitle.RAIJU -> R.string.achievements_raiju_name
        AchievementTitle.REVENANT -> R.string.achievements_revenant_name
        AchievementTitle.SHADE -> R.string.achievements_shade_name
        AchievementTitle.SPIRIT -> R.string.achievements_spirit_name
        AchievementTitle.THAYE -> R.string.achievements_thaye_name
        AchievementTitle.MIMIC -> R.string.achievements_mimic_name
        AchievementTitle.TWINS -> R.string.achievements_twins_name
        AchievementTitle.WRAITH -> R.string.achievements_wraith_name
        AchievementTitle.YOKAI -> R.string.achievements_yokai_name
        AchievementTitle.YUREI -> R.string.achievements_yurei_name
        AchievementTitle.PRESTIGE1 -> R.string.achievements_prestige1_name
        AchievementTitle.PRESTIGE2 -> R.string.achievements_prestige2_name
        AchievementTitle.PRESTIGE3 -> R.string.achievements_prestige3_name
        AchievementTitle.WORK_EXPERIENCE -> R.string.achievements_workexperience_name
        AchievementTitle.FLAWLESS_EXECUTION -> R.string.achievements_flawlessexecution_name
        AchievementTitle.THEYRE_HERE -> R.string.achievements_theyrehere_name
        AchievementTitle.ESCAPE_ARTIST -> R.string.achievements_escapeartist_name
        AchievementTitle.BAIT -> R.string.achievements_bait_name
        AchievementTitle.DOOM_SLAYED -> R.string.achievements_doomslayed_name
        AchievementTitle.PARANORMAL_PERFECTIONIST -> R.string.achievements_paranormal_perfectionist_name
    }

@StringRes fun AchievementContent.toStringResource(): Int =
    when (this) {
        AchievementContent.TRAINING_WHEELS -> R.string.achievements_trainingwheels_info
        AchievementContent.ROOKIE -> R.string.achievements_rookie_info
        AchievementContent.PROFESSIONAL -> R.string.achievements_professional_info
        AchievementContent.BOSS -> R.string.achievements_boss_info
        AchievementContent.EXTRA_MILE -> R.string.achievements_extramile_info
        AchievementContent.DEDICATED -> R.string.achievements_dedicated_info
        AchievementContent.DEVOTED -> R.string.achievements_devoted_info
        AchievementContent.CHALLENGER -> R.string.achievements_challenger_info
        AchievementContent.RISE_CHALLENGE -> R.string.achievements_risechallenge_info
        AchievementContent.TAKE_CHALLENGES -> R.string.achievements_takingchallenges_info
        AchievementContent.CHUMP_CHANGE -> R.string.achievements_chumpchange_info
        AchievementContent.FAT_STOCK -> R.string.achievements_fatstock_info
        AchievementContent.CASH_COW -> R.string.achievements_cashcow_info
        AchievementContent.BREAK_THE_BANK -> R.string.achievements_breakthebank_info
        AchievementContent.BARE_ESSENTIALS -> R.string.achievements_bareessentials_info
        AchievementContent.TOOLS_OF_THE_TRADE -> R.string.achievements_toolsofthetrade_info
        AchievementContent.FULLY_LOADED -> R.string.achievements_fullyloaded_info
        AchievementContent.DIRECTOR -> R.string.achievements_director_info
        AchievementContent.BRONZE_HUNTER -> R.string.achievements_bronzehunter_info
        AchievementContent.SILVER_HUNTER -> R.string.achievements_silverhunter_info
        AchievementContent.GOLD_HUNTER -> R.string.achievements_goldhunter_info
        AchievementContent.BANSHEE -> R.string.achievements_banshee_info
        AchievementContent.DEMON -> R.string.achievements_demon_info
        AchievementContent.DEOGEN -> R.string.achievements_deogen_info
        AchievementContent.GORYO -> R.string.achievements_goryo_info
        AchievementContent.HANTU -> R.string.achievements_hantu_info
        AchievementContent.JINN -> R.string.achievements_jinn_info
        AchievementContent.MARE -> R.string.achievements_mare_info
        AchievementContent.MOROI -> R.string.achievements_moroi_info
        AchievementContent.MYLING -> R.string.achievements_myling_info
        AchievementContent.OBAKE -> R.string.achievements_obake_info
        AchievementContent.ONI -> R.string.achievements_oni_info
        AchievementContent.ONRYO -> R.string.achievements_onryo_info
        AchievementContent.PHANTOM -> R.string.achievements_phantom_info
        AchievementContent.POLTERGEIST -> R.string.achievements_poltergeist_info
        AchievementContent.RAIJU -> R.string.achievements_raiju_info
        AchievementContent.REVENANT -> R.string.achievements_revenant_info
        AchievementContent.SHADE -> R.string.achievements_shade_info
        AchievementContent.SPIRIT -> R.string.achievements_spirit_info
        AchievementContent.THAYE -> R.string.achievements_thaye_info
        AchievementContent.MIMIC -> R.string.achievements_mimic_info
        AchievementContent.TWINS -> R.string.achievements_twins_info
        AchievementContent.WRAITH -> R.string.achievements_wraith_info
        AchievementContent.YOKAI -> R.string.achievements_yokai_info
        AchievementContent.YUREI -> R.string.achievements_yurei_info
        AchievementContent.PRESTIGE1 -> R.string.achievements_prestige1_info
        AchievementContent.PRESTIGE2 -> R.string.achievements_prestige2_info
        AchievementContent.PRESTIGE3 -> R.string.achievements_prestige3_info
        AchievementContent.WORK_EXPERIENCE -> R.string.achievements_workexperience_info
        AchievementContent.FLAWLESS_EXECUTION -> R.string.achievements_flawlessexecution_info
        AchievementContent.THEYRE_HERE -> R.string.achievements_theyrehere_info
        AchievementContent.ESCAPE_ARTIST -> R.string.achievements_escapeartist_info
        AchievementContent.BAIT -> R.string.achievements_bait_info
        AchievementContent.DOOM_SLAYED -> R.string.achievements_doomslayed_info
        AchievementContent.PARANORMAL_PERFECTIONIST -> R.string.achievements_paranormal_perfectionist_info
    }

@DrawableRes fun AchievementIcon.toDrawableResource(): Int =
    when (this) {
        AchievementIcon.TRAINING_WHEELS -> R.drawable.icon_achievement_no_more_training_wheels
        AchievementIcon.ROOKIE -> R.drawable.icon_achievement_rookie
        AchievementIcon.PROFESSIONAL -> R.drawable.icon_achievement_professional
        AchievementIcon.BOSS -> R.drawable.icon_achievement_boss
        AchievementIcon.EXTRA_MILE -> R.drawable.icon_achievement_extra_mile
        AchievementIcon.DEDICATED -> R.drawable.icon_achievement_dedicated
        AchievementIcon.DEVOTED -> R.drawable.icon_achievement_devoted
        AchievementIcon.CHALLENGER -> R.drawable.icon_achievement_challenger_1
        AchievementIcon.RISE_CHALLENGE -> R.drawable.icon_achievement_challenger_2
        AchievementIcon.TAKE_CHALLENGES -> R.drawable.icon_achievement_challenger_3
        AchievementIcon.CHUMP_CHANGE -> R.drawable.icon_achievement_chump_change
        AchievementIcon.FAT_STOCK -> R.drawable.icon_achievement_cash_1
        AchievementIcon.CASH_COW -> R.drawable.icon_achievement_cash_2
        AchievementIcon.BREAK_THE_BANK -> R.drawable.icon_achievement_cash_3
        AchievementIcon.BARE_ESSENTIALS -> R.drawable.icon_achievement_shop_1
        AchievementIcon.TOOLS_OF_THE_TRADE -> R.drawable.icon_achievement_shop_2
        AchievementIcon.FULLY_LOADED -> R.drawable.icon_achievement_shop_3
        AchievementIcon.DIRECTOR -> R.drawable.icon_achievement_director
        AchievementIcon.BRONZE_HUNTER -> R.drawable.icon_achievement_hunter_1
        AchievementIcon.SILVER_HUNTER -> R.drawable.icon_achievement_hunter_2
        AchievementIcon.GOLD_HUNTER -> R.drawable.icon_achievement_hunter_3
        AchievementIcon.BANSHEE -> R.drawable.icon_ghost_banshee
        AchievementIcon.DEMON -> R.drawable.icon_ghost_demon
        AchievementIcon.DEOGEN -> R.drawable.icon_ghost_deogen
        AchievementIcon.GORYO -> R.drawable.icon_ghost_goryo
        AchievementIcon.HANTU -> R.drawable.icon_ghost_hantu
        AchievementIcon.JINN -> R.drawable.icon_ghost_jinn
        AchievementIcon.MARE -> R.drawable.icon_ghost_mare
        AchievementIcon.MOROI -> R.drawable.icon_ghost_moroi
        AchievementIcon.MYLING -> R.drawable.icon_ghost_myling
        AchievementIcon.OBAKE -> R.drawable.icon_ghost_obake
        AchievementIcon.ONI -> R.drawable.icon_ghost_oni
        AchievementIcon.ONRYO -> R.drawable.icon_ghost_onryo
        AchievementIcon.PHANTOM -> R.drawable.icon_ghost_phantom
        AchievementIcon.POLTERGEIST -> R.drawable.icon_ghost_poltergeist
        AchievementIcon.RAIJU -> R.drawable.icon_ghost_raiju
        AchievementIcon.REVENANT -> R.drawable.icon_ghost_revenant
        AchievementIcon.SHADE -> R.drawable.icon_ghost_shade
        AchievementIcon.SPIRIT -> R.drawable.icon_ghost_spirit
        AchievementIcon.THAYE -> R.drawable.icon_ghost_thaye
        AchievementIcon.MIMIC -> R.drawable.icon_ghost_the_mimic
        AchievementIcon.TWINS -> R.drawable.icon_ghost_the_twins
        AchievementIcon.WRAITH -> R.drawable.icon_ghost_wraith
        AchievementIcon.YOKAI -> R.drawable.icon_ghost_yokai
        AchievementIcon.YUREI -> R.drawable.icon_ghost_yurei
        AchievementIcon.PRESTIGE1 -> R.drawable.icon_achievement_prestige_1
        AchievementIcon.PRESTIGE2 -> R.drawable.icon_achievement_prestige_2
        AchievementIcon.PRESTIGE3 -> R.drawable.icon_achievement_prestige_3
        AchievementIcon.WORK_EXPERIENCE -> R.drawable.icon_achievement_work_experience
        AchievementIcon.FLAWLESS_EXECUTION -> R.drawable.icon_achievement_flawless_execution
        AchievementIcon.THEYRE_HERE -> R.drawable.icon_achievement_theyre_here
        AchievementIcon.ESCAPE_ARTIST -> R.drawable.icon_achievement_escape_artist
        AchievementIcon.BAIT -> R.drawable.icon_achievement_the_bait
        AchievementIcon.DOOM_SLAYED -> R.drawable.icon_achievement_doom_slayed
        AchievementIcon.PARANORMAL_PERFECTIONIST -> R.drawable.icon_achievement_paranormal_perfectionist
    }

@DrawableRes fun AchievementExclusivity.toStringResource(): Int =
    when (this) {
        AchievementExclusivity.STEAM -> R.string.platform_steam
        AchievementExclusivity.XBOX -> R.string.platform_xbox
        AchievementExclusivity.PLAYSTATION -> R.string.platform_playstation
    }

@DrawableRes fun AchievementExclusivity.toDrawableResource(): Int =
    when (this) {
        AchievementExclusivity.STEAM -> R.drawable.ic_logo_steam
        AchievementExclusivity.XBOX -> R.drawable.ic_logo_xbox
        AchievementExclusivity.PLAYSTATION -> R.drawable.ic_logo_playstation
    }
