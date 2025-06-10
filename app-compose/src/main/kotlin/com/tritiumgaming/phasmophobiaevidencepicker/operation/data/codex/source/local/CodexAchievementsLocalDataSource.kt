package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.dto.CodexAchievementsGroupDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.dto.CodexAchievementsGroupItemDto

class CodexAchievementsLocalDataSource() {

    private val achievementsResources
        get() = listOf<CodexAchievementGroupRes>(
            CodexAchievementGroupRes(
                name = R.string.achievements_trainingwheels_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_trainingwheels_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_rookie_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_rookie_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_professional_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_professional_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_boss_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_boss_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_trainingwheels_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_trainingwheels_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_extramile_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_extramile_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_dedicated_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_dedicated_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_devoted_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_devoted_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_challenger_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_challenger_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_risechallenge_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_risechallenge_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_takingchallenges_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_takingchallenges_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_chumpchange_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_chumpchange_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_fatstock_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_fatstock_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_cashcow_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_cashcow_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_breakthebank_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_breakthebank_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_bareessentials_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_bareessentials_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_toolsofthetrade_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_toolsofthetrade_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_fullyloaded_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_fullyloaded_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_director_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_director_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_bronzehunter_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_bronzehunter_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_silverhunter_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_silverhunter_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_goldhunter_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_goldhunter_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_banshee_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_banshee_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_demon_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_demon_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_deogen_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_deogen_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_goryo_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_goryo_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_hantu_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_hantu_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_jinn_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_jinn_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_mare_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_mare_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_moroi_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_moroi_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_myling_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_myling_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_obake_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_obake_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_oni_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_oni_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_onryo_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_onryo_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_phantom_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_phantom_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_poltergeist_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_poltergeist_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_raiju_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_raiju_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_revenant_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_revenant_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_shade_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_shade_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_spirit_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_spirit_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_thaye_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_thaye_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_mimic_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_mimic_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_twins_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_twins_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_wraith_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_wraith_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_yokai_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_yokai_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_yurei_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_yurei_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_prestige1_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_prestige1_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_prestige2_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_prestige2_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_prestige3_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_prestige3_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_workexperience_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_workexperience_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_flawlessexecution_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_flawlessexecution_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_theyrehere_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_theyrehere_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_escapeartist_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_escapeartist_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_bait_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_bait_info
                )
            ),
            CodexAchievementGroupRes(
                name = R.string.achievements_doomslayed_name,
                icon = R.drawable.ic_achievement_banshee,
                item = CodexAchievementGroupItemRes(
                    infoText = R.string.achievements_doomslayed_info
                )
            )
        )

    fun fetchItems(): Result<List<CodexAchievementsGroupDto>> {

        val groupsDto = mutableListOf<CodexAchievementsGroupDto>()

        achievementsResources.forEach { resDto ->
            groupsDto.add(resDto.toLocal())
        }

        return Result.success(groupsDto)
    }

    private data class CodexAchievementGroupRes(
        @StringRes val name: Int,
        @DrawableRes val icon: Int,
        val item: CodexAchievementGroupItemRes
    )

    private data class CodexAchievementGroupItemRes(
        @StringRes val infoText: Int
    )

    private fun CodexAchievementGroupRes.toLocal() = CodexAchievementsGroupDto(
        name = name,
        icon = icon,
        item = item.toLocal()
    )

    private fun CodexAchievementGroupItemRes.toLocal() = CodexAchievementsGroupItemDto(
        infoText = infoText
    )

    @JvmName("codexAchievementGroupResListToLocalCodexAchievementGroupRes")
    private fun List<CodexAchievementGroupRes>.toLocal() = map { dto ->
        dto.toLocal()
    }

    @JvmName("CodexAchievementGroupItemResListToLocalCodexAchievementGroupItemRes")
    private fun List<CodexAchievementGroupItemRes>.toLocal() = map { dto ->
        dto.toLocal()
    }

}
