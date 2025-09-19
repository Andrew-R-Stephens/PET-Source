package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.dto.CodexAchievementsGroupDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.dto.CodexAchievementsGroupItemDto
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexAchievementsResources.AchievementContent
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexAchievementsResources.AchievementIcon
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexAchievementsResources.AchievementTitle

class CodexAchievementsLocalDataSource() {

    private val achievementsResources
        get() = listOf<CodexAchievementGroupResourceDto>(
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.TRAINING_WHEELS,
                icon = AchievementIcon.TRAINING_WHEELS,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.TRAINING_WHEELS
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.ROOKIE,
                icon = AchievementIcon.ROOKIE,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.ROOKIE
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.PROFESSIONAL,
                icon = AchievementIcon.PROFESSIONAL,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.PROFESSIONAL
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.BOSS,
                icon = AchievementIcon.BOSS,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.BOSS
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.EXTRA_MILE,
                icon = AchievementIcon.EXTRA_MILE,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.EXTRA_MILE
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.DEDICATED,
                icon = AchievementIcon.DEDICATED,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.DEDICATED
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.DEVOTED,
                icon = AchievementIcon.DEVOTED,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.DEVOTED
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.CHALLENGER,
                icon = AchievementIcon.CHALLENGER,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.CHALLENGER
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.RISE_CHALLENGE,
                icon = AchievementIcon.RISE_CHALLENGE,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.RISE_CHALLENGE
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.TAKE_CHALLENGES,
                icon = AchievementIcon.TAKE_CHALLENGES,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.TAKE_CHALLENGES
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.CHUMP_CHANGE,
                icon = AchievementIcon.CHUMP_CHANGE,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.CHUMP_CHANGE
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.FAT_STOCK,
                icon = AchievementIcon.FAT_STOCK,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.FAT_STOCK
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.CASH_COW,
                icon = AchievementIcon.CASH_COW,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.CASH_COW
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.BREAK_THE_BANK,
                icon = AchievementIcon.BREAK_THE_BANK,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.BREAK_THE_BANK
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.BARE_ESSENTIALS,
                icon = AchievementIcon.BARE_ESSENTIALS,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.BARE_ESSENTIALS
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.TOOLS_OF_THE_TRADE,
                icon = AchievementIcon.TOOLS_OF_THE_TRADE,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.TOOLS_OF_THE_TRADE
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.FULLY_LOADED,
                icon = AchievementIcon.FULLY_LOADED,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.FULLY_LOADED
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.DIRECTOR,
                icon = AchievementIcon.DIRECTOR,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.DIRECTOR
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.BRONZE_HUNTER,
                icon = AchievementIcon.BRONZE_HUNTER,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.BRONZE_HUNTER
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.SILVER_HUNTER,
                icon = AchievementIcon.SILVER_HUNTER,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.SILVER_HUNTER
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.GOLD_HUNTER,
                icon = AchievementIcon.GOLD_HUNTER,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.GOLD_HUNTER
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.BANSHEE,
                icon = AchievementIcon.BANSHEE,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.BANSHEE
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.DEMON,
                icon = AchievementIcon.DEMON,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.DEMON
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.DEOGEN,
                icon = AchievementIcon.DEOGEN,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.DEOGEN
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.GORYO,
                icon = AchievementIcon.GORYO,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.GORYO
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.HANTU,
                icon = AchievementIcon.HANTU,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.HANTU
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.JINN,
                icon = AchievementIcon.JINN,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.JINN
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.MARE,
                icon = AchievementIcon.MARE,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.MARE
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.MOROI,
                icon = AchievementIcon.MOROI,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.MOROI
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.MYLING,
                icon = AchievementIcon.MYLING,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.MYLING
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.OBAKE,
                icon = AchievementIcon.OBAKE,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.OBAKE
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.ONI,
                icon = AchievementIcon.ONI,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.ONI
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.ONRYO,
                icon = AchievementIcon.ONRYO,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.ONRYO
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.PHANTOM,
                icon = AchievementIcon.PHANTOM,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.PHANTOM
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.POLTERGEIST,
                icon = AchievementIcon.POLTERGEIST,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.POLTERGEIST
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.RAIJU,
                icon = AchievementIcon.RAIJU,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.RAIJU
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.REVENANT,
                icon = AchievementIcon.REVENANT,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.REVENANT
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.SHADE,
                icon = AchievementIcon.SHADE,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.SHADE
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.SPIRIT,
                icon = AchievementIcon.SPIRIT,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.SPIRIT
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.THAYE,
                icon = AchievementIcon.THAYE,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.THAYE
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.MIMIC,
                icon = AchievementIcon.MIMIC,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.MIMIC
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.TWINS,
                icon = AchievementIcon.TWINS,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.TWINS
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.WRAITH,
                icon = AchievementIcon.WRAITH,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.WRAITH
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.YOKAI,
                icon = AchievementIcon.YOKAI,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.YOKAI
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.YUREI,
                icon = AchievementIcon.YUREI,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.YUREI
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.PRESTIGE1,
                icon = AchievementIcon.PRESTIGE1,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.PRESTIGE1
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.PRESTIGE2,
                icon = AchievementIcon.PRESTIGE2,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.PRESTIGE2
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.PRESTIGE3,
                icon = AchievementIcon.PRESTIGE3,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.PRESTIGE3
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.WORK_EXPERIENCE,
                icon = AchievementIcon.WORK_EXPERIENCE,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.WORK_EXPERIENCE
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.FLAWLESS_EXECUTION,
                icon = AchievementIcon.FLAWLESS_EXECUTION,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.FLAWLESS_EXECUTION
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.THEYRE_HERE,
                icon = AchievementIcon.THEYRE_HERE,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.THEYRE_HERE
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.ESCAPE_ARTIST,
                icon = AchievementIcon.ESCAPE_ARTIST,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.ESCAPE_ARTIST
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.BAIT,
                icon = AchievementIcon.BAIT,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.BAIT
                )
            ),
            CodexAchievementGroupResourceDto(
                name = AchievementTitle.DOOM_SLAYED,
                icon = AchievementIcon.DOOM_SLAYED,
                item = CodexAchievementGroupItemResourceDto(
                    infoText = AchievementContent.DOOM_SLAYED
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

    private data class CodexAchievementGroupResourceDto(
        val name: AchievementTitle,
        val icon: AchievementIcon,
        val item: CodexAchievementGroupItemResourceDto
    )

    private data class CodexAchievementGroupItemResourceDto(
        val infoText: AchievementContent
    )

    private fun CodexAchievementGroupResourceDto.toLocal() = CodexAchievementsGroupDto(
        name = name,
        icon = icon,
        item = item.toLocal()
    )

    private fun CodexAchievementGroupItemResourceDto.toLocal() = CodexAchievementsGroupItemDto(
        infoText = infoText
    )

    @JvmName("codexAchievementGroupResListToLocalCodexAchievementGroupRes")
    private fun List<CodexAchievementGroupResourceDto>.toLocal() = map { dto ->
        dto.toLocal()
    }

    @JvmName("CodexAchievementGroupItemResListToLocalCodexAchievementGroupItemRes")
    private fun List<CodexAchievementGroupItemResourceDto>.toLocal() = map { dto ->
        dto.toLocal()
    }

}
