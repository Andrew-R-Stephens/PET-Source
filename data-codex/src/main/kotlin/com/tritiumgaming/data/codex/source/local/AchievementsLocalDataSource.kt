package com.tritiumgaming.data.codex.source.local

import com.tritiumgaming.data.codex.dto.AchievementTypeDto
import com.tritiumgaming.data.codex.dto.AchievementsTypeDto
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementContent
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementExclusivity
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementIcon
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementTitle
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementVisibility

class AchievementsLocalDataSource() {

    private val achievementsResources
        get() = listOf<AchievementTypeResourceDto>(
            AchievementTypeResourceDto(
                name = AchievementTitle.TRAINING_WHEELS,
                icon = AchievementIcon.TRAINING_WHEELS,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.TRAINING_WHEELS
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.ROOKIE,
                icon = AchievementIcon.ROOKIE,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.ROOKIE
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.PROFESSIONAL,
                icon = AchievementIcon.PROFESSIONAL,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.PROFESSIONAL
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.BOSS,
                icon = AchievementIcon.BOSS,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.BOSS
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.EXTRA_MILE,
                icon = AchievementIcon.EXTRA_MILE,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.EXTRA_MILE
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.DEDICATED,
                icon = AchievementIcon.DEDICATED,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.DEDICATED
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.DEVOTED,
                icon = AchievementIcon.DEVOTED,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.DEVOTED
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.CHALLENGER,
                icon = AchievementIcon.CHALLENGER,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.CHALLENGER
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.RISE_CHALLENGE,
                icon = AchievementIcon.RISE_CHALLENGE,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.RISE_CHALLENGE
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.TAKE_CHALLENGES,
                icon = AchievementIcon.TAKE_CHALLENGES,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.TAKE_CHALLENGES
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.CHUMP_CHANGE,
                icon = AchievementIcon.CHUMP_CHANGE,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.CHUMP_CHANGE
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.FAT_STOCK,
                icon = AchievementIcon.FAT_STOCK,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.FAT_STOCK
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.CASH_COW,
                icon = AchievementIcon.CASH_COW,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.CASH_COW
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.BREAK_THE_BANK,
                icon = AchievementIcon.BREAK_THE_BANK,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.BREAK_THE_BANK
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.BARE_ESSENTIALS,
                icon = AchievementIcon.BARE_ESSENTIALS,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.BARE_ESSENTIALS
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.TOOLS_OF_THE_TRADE,
                icon = AchievementIcon.TOOLS_OF_THE_TRADE,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.TOOLS_OF_THE_TRADE
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.FULLY_LOADED,
                icon = AchievementIcon.FULLY_LOADED,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.FULLY_LOADED
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.DIRECTOR,
                icon = AchievementIcon.DIRECTOR,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.DIRECTOR
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.BRONZE_HUNTER,
                icon = AchievementIcon.BRONZE_HUNTER,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.BRONZE_HUNTER
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.SILVER_HUNTER,
                icon = AchievementIcon.SILVER_HUNTER,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.SILVER_HUNTER
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.GOLD_HUNTER,
                icon = AchievementIcon.GOLD_HUNTER,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.GOLD_HUNTER
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.BANSHEE,
                icon = AchievementIcon.BANSHEE,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.BANSHEE
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.DEMON,
                icon = AchievementIcon.DEMON,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.DEMON
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.DEOGEN,
                icon = AchievementIcon.DEOGEN,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.DEOGEN
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.GORYO,
                icon = AchievementIcon.GORYO,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.GORYO
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.HANTU,
                icon = AchievementIcon.HANTU,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.HANTU
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.JINN,
                icon = AchievementIcon.JINN,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.JINN
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.MARE,
                icon = AchievementIcon.MARE,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.MARE
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.MOROI,
                icon = AchievementIcon.MOROI,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.MOROI
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.MYLING,
                icon = AchievementIcon.MYLING,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.MYLING
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.OBAKE,
                icon = AchievementIcon.OBAKE,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.OBAKE
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.ONI,
                icon = AchievementIcon.ONI,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.ONI
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.ONRYO,
                icon = AchievementIcon.ONRYO,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.ONRYO
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.PHANTOM,
                icon = AchievementIcon.PHANTOM,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.PHANTOM
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.POLTERGEIST,
                icon = AchievementIcon.POLTERGEIST,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.POLTERGEIST
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.RAIJU,
                icon = AchievementIcon.RAIJU,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.RAIJU
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.REVENANT,
                icon = AchievementIcon.REVENANT,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.REVENANT
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.SHADE,
                icon = AchievementIcon.SHADE,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.SHADE
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.SPIRIT,
                icon = AchievementIcon.SPIRIT,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.SPIRIT
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.THAYE,
                icon = AchievementIcon.THAYE,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.THAYE
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.MIMIC,
                icon = AchievementIcon.MIMIC,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.MIMIC
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.TWINS,
                icon = AchievementIcon.TWINS,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.TWINS
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.WRAITH,
                icon = AchievementIcon.WRAITH,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.WRAITH
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.YOKAI,
                icon = AchievementIcon.YOKAI,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.YOKAI
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.YUREI,
                icon = AchievementIcon.YUREI,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.YUREI
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.PRESTIGE1,
                icon = AchievementIcon.PRESTIGE1,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.PRESTIGE1
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.PRESTIGE2,
                icon = AchievementIcon.PRESTIGE2,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.PRESTIGE2
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.PRESTIGE3,
                icon = AchievementIcon.PRESTIGE3,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.PRESTIGE3
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.WORK_EXPERIENCE,
                icon = AchievementIcon.WORK_EXPERIENCE,
                visibility = AchievementVisibility.HIDDEN,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.WORK_EXPERIENCE
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.FLAWLESS_EXECUTION,
                icon = AchievementIcon.FLAWLESS_EXECUTION,
                visibility = AchievementVisibility.HIDDEN,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.FLAWLESS_EXECUTION
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.THEYRE_HERE,
                icon = AchievementIcon.THEYRE_HERE,
                visibility = AchievementVisibility.HIDDEN,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.THEYRE_HERE
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.ESCAPE_ARTIST,
                icon = AchievementIcon.ESCAPE_ARTIST,
                visibility = AchievementVisibility.HIDDEN,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.ESCAPE_ARTIST
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.BAIT,
                icon = AchievementIcon.BAIT,
                visibility = AchievementVisibility.HIDDEN,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.BAIT
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.DOOM_SLAYED,
                icon = AchievementIcon.DOOM_SLAYED,
                visibility = AchievementVisibility.HIDDEN,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.DOOM_SLAYED
                )
            ),
            AchievementTypeResourceDto(
                name = AchievementTitle.PARANORMAL_PERFECTIONIST,
                icon = AchievementIcon.PARANORMAL_PERFECTIONIST,
                exclusivity = AchievementExclusivity.PLAYSTATION.value,
                item = AchievementTypeTierResourceDto(
                    infoText = AchievementContent.DOOM_SLAYED
                )
            )
        )

    fun fetchItems(): Result<List<AchievementTypeDto>> {

        val groupsDto = mutableListOf<AchievementTypeDto>()

        achievementsResources.forEach { resDto ->
            groupsDto.add(resDto.toLocal())
        }

        return Result.success(groupsDto)
    }

    private data class AchievementTypeResourceDto(
        val name: AchievementTitle,
        val icon: AchievementIcon,
        val visibility: AchievementVisibility = AchievementVisibility.SHOWN,
        val exclusivity: Int =
            AchievementExclusivity.STEAM.value
            or AchievementExclusivity.XBOX.value
            or AchievementExclusivity.PLAYSTATION.value,
        val item: AchievementTypeTierResourceDto
    )

    private data class AchievementTypeTierResourceDto(
        val infoText: AchievementContent
    )

    private fun AchievementTypeResourceDto.toLocal() = AchievementTypeDto(
        name = name,
        icon = icon,
        visibility = visibility,
        exclusivity = exclusivity,
        item = item.toLocal()
    )

    private fun AchievementTypeTierResourceDto.toLocal() = AchievementsTypeDto(
        infoText = infoText
    )

    @JvmName("codexAchievementGroupResListToLocalCodexAchievementGroupRes")
    private fun List<AchievementTypeResourceDto>.toLocal() = map { dto ->
        dto.toLocal()
    }

    @JvmName("CodexAchievementGroupItemResListToLocalCodexAchievementGroupItemRes")
    private fun List<AchievementTypeTierResourceDto>.toLocal() = map { dto ->
        dto.toLocal()
    }

}
