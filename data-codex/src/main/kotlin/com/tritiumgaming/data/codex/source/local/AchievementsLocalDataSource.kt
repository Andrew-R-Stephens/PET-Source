package com.tritiumgaming.data.codex.source.local

import com.tritiumgaming.data.codex.dto.AchievementTypeDto
import com.tritiumgaming.data.codex.dto.AchievementsTypeDto
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementCategory
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementContent
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementExclusivity
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementIcon
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementTitle
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementVisibility

class AchievementsLocalDataSource() {

    private val achievementsResources
        get() = listOf<AchievementTypeRes>(
            AchievementTypeRes(
                name = AchievementCategory.TRAINING_WHEELS,
                icon = AchievementIcon.TRAINING_WHEELS,
                items = listOf(
                    AchievementTypeTierRes(
                        name = AchievementTitle.TRAINING_WHEELS,
                        icon = AchievementIcon.TRAINING_WHEELS,
                        infoText = AchievementContent.TRAINING_WHEELS
                    )
                )
            ),
            AchievementTypeRes(
                name = AchievementCategory.COMPLETED_CONTRACTS,
                icon = AchievementIcon.BOSS,
                items = listOf(
                    AchievementTypeTierRes(
                        name = AchievementTitle.WORK_EXPERIENCE,
                        icon = AchievementIcon.WORK_EXPERIENCE,
                        visibility = AchievementVisibility.HIDDEN,
                        infoText = AchievementContent.WORK_EXPERIENCE
                    ),
                    AchievementTypeTierRes(
                    name = AchievementTitle.ROOKIE,
                    icon = AchievementIcon.ROOKIE,
                    infoText = AchievementContent.ROOKIE
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.PROFESSIONAL,
                        icon = AchievementIcon.PROFESSIONAL,
                        infoText = AchievementContent.PROFESSIONAL
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.BOSS,
                        icon = AchievementIcon.BOSS,
                        infoText = AchievementContent.BOSS
                    )
                )
            ),
            AchievementTypeRes(
                name = AchievementCategory.OBJECTIVES_BOARD,
                icon = AchievementIcon.EXTRA_MILE,
                items = listOf(
                    AchievementTypeTierRes(
                    name = AchievementTitle.EXTRA_MILE,
                    icon = AchievementIcon.EXTRA_MILE,
                    infoText = AchievementContent.EXTRA_MILE
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.DEDICATED,
                        icon = AchievementIcon.DEDICATED,
                        infoText = AchievementContent.DEDICATED
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.DEVOTED,
                        icon = AchievementIcon.DEVOTED,
                        infoText = AchievementContent.DEVOTED
                    )
                )
            ),
            AchievementTypeRes(
                name = AchievementCategory.WEEKLY_CHALLENGE,
                icon = AchievementIcon.TAKE_CHALLENGES,
                items = listOf(
                    AchievementTypeTierRes(
                        name = AchievementTitle.CHALLENGER,
                        icon = AchievementIcon.CHALLENGER,
                        infoText = AchievementContent.CHALLENGER
                    ),
                        AchievementTypeTierRes(
                        name = AchievementTitle.RISE_CHALLENGE,
                        icon = AchievementIcon.RISE_CHALLENGE,
                        infoText = AchievementContent.RISE_CHALLENGE
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.TAKE_CHALLENGES,
                        icon = AchievementIcon.TAKE_CHALLENGES,
                        infoText = AchievementContent.TAKE_CHALLENGES
                    )
                )
            ),
            AchievementTypeRes(
                name = AchievementCategory.SPEND_CASH,
                icon = AchievementIcon.BREAK_THE_BANK,
                items = listOf(
                    AchievementTypeTierRes(
                    name = AchievementTitle.CHUMP_CHANGE,
                    icon = AchievementIcon.CHUMP_CHANGE,
                    infoText = AchievementContent.CHUMP_CHANGE
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.FAT_STOCK,
                        icon = AchievementIcon.FAT_STOCK,
                        infoText = AchievementContent.FAT_STOCK
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.CASH_COW,
                        icon = AchievementIcon.CASH_COW,
                        infoText = AchievementContent.CASH_COW
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.BREAK_THE_BANK,
                        icon = AchievementIcon.BREAK_THE_BANK,
                        infoText = AchievementContent.BREAK_THE_BANK
                    )
                )
            ),
            AchievementTypeRes(
                name = AchievementCategory.UNLOCKED_ALL_EQUIPMENT_TIER,
                icon = AchievementIcon.FULLY_LOADED,
                items = listOf(
                    AchievementTypeTierRes(
                        name = AchievementTitle.BARE_ESSENTIALS,
                        icon = AchievementIcon.BARE_ESSENTIALS,
                        infoText = AchievementContent.BARE_ESSENTIALS
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.TOOLS_OF_THE_TRADE,
                        icon = AchievementIcon.TOOLS_OF_THE_TRADE,
                        infoText = AchievementContent.TOOLS_OF_THE_TRADE
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.FULLY_LOADED,
                        icon = AchievementIcon.FULLY_LOADED,
                        infoText = AchievementContent.FULLY_LOADED
                    )
                )
            ),
            AchievementTypeRes(
                name = AchievementCategory.DIRECTOR,
                icon = AchievementIcon.DIRECTOR,
                items = listOf(
                        AchievementTypeTierRes(
                        name = AchievementTitle.DIRECTOR,
                        icon = AchievementIcon.DIRECTOR,
                        infoText = AchievementContent.DIRECTOR
                    )
                )
            ),
            AchievementTypeRes(
                name = AchievementCategory.APOCALYPSE_TROPHY,
                icon = AchievementIcon.GOLD_HUNTER,
                items = listOf(
                    AchievementTypeTierRes(
                    name = AchievementTitle.BRONZE_HUNTER,
                    icon = AchievementIcon.BRONZE_HUNTER,
                    infoText = AchievementContent.BRONZE_HUNTER
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.SILVER_HUNTER,
                        icon = AchievementIcon.SILVER_HUNTER,
                        infoText = AchievementContent.SILVER_HUNTER
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.GOLD_HUNTER,
                        icon = AchievementIcon.GOLD_HUNTER,
                        infoText = AchievementContent.GOLD_HUNTER
                    )
                )
            ),
            AchievementTypeRes(
                name = AchievementCategory.GHOSTS,
                icon = AchievementIcon.BANSHEE,
                items = listOf(
                    AchievementTypeTierRes(
                        name = AchievementTitle.BANSHEE,
                        icon = AchievementIcon.BANSHEE,
                        infoText = AchievementContent.BANSHEE
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.DEMON,
                        icon = AchievementIcon.DEMON,
                        infoText = AchievementContent.DEMON
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.DEOGEN,
                        icon = AchievementIcon.DEOGEN,
                        infoText = AchievementContent.DEOGEN
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.GORYO,
                        icon = AchievementIcon.GORYO,
                        infoText = AchievementContent.GORYO
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.HANTU,
                        icon = AchievementIcon.HANTU,
                        infoText = AchievementContent.HANTU
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.JINN,
                        icon = AchievementIcon.JINN,
                        infoText = AchievementContent.JINN
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.MARE,
                        icon = AchievementIcon.MARE,
                        infoText = AchievementContent.MARE
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.MOROI,
                        icon = AchievementIcon.MOROI,
                        infoText = AchievementContent.MOROI
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.MYLING,
                        icon = AchievementIcon.MYLING,
                        infoText = AchievementContent.MYLING
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.OBAKE,
                        icon = AchievementIcon.OBAKE,
                        infoText = AchievementContent.OBAKE
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.ONI,
                        icon = AchievementIcon.ONI,
                        infoText = AchievementContent.ONI
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.ONRYO,
                        icon = AchievementIcon.ONRYO,
                        infoText = AchievementContent.ONRYO
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.PHANTOM,
                        icon = AchievementIcon.PHANTOM,
                        infoText = AchievementContent.PHANTOM
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.POLTERGEIST,
                        icon = AchievementIcon.POLTERGEIST,
                        infoText = AchievementContent.POLTERGEIST
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.RAIJU,
                        icon = AchievementIcon.RAIJU,
                        infoText = AchievementContent.RAIJU
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.REVENANT,
                        icon = AchievementIcon.REVENANT,
                        infoText = AchievementContent.REVENANT
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.SHADE,
                        icon = AchievementIcon.SHADE,
                        infoText = AchievementContent.SHADE
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.SPIRIT,
                        icon = AchievementIcon.SPIRIT,
                        infoText = AchievementContent.SPIRIT
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.THAYE,
                        icon = AchievementIcon.THAYE,
                        infoText = AchievementContent.THAYE
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.MIMIC,
                        icon = AchievementIcon.MIMIC,
                        infoText = AchievementContent.MIMIC
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.TWINS,
                        icon = AchievementIcon.TWINS,
                        infoText = AchievementContent.TWINS
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.WRAITH,
                        icon = AchievementIcon.WRAITH,
                        infoText = AchievementContent.WRAITH
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.YOKAI,
                        icon = AchievementIcon.YOKAI,
                        infoText = AchievementContent.YOKAI
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.YUREI,
                        icon = AchievementIcon.YUREI,
                        infoText = AchievementContent.YUREI
                    )
                )
            ),
            AchievementTypeRes(
                name = AchievementCategory.PRESTIGE,
                icon = AchievementIcon.PRESTIGE3,
                items = listOf(
                    AchievementTypeTierRes(
                        name = AchievementTitle.PRESTIGE1,
                        icon = AchievementIcon.PRESTIGE1,
                        infoText = AchievementContent.PRESTIGE1
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.PRESTIGE2,
                        icon = AchievementIcon.PRESTIGE2,
                        infoText = AchievementContent.PRESTIGE2
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.PRESTIGE3,
                        icon = AchievementIcon.PRESTIGE3,
                        infoText = AchievementContent.PRESTIGE3
                    )
                )
            ),
            AchievementTypeRes(
                name = AchievementCategory.HIDDEN_ACHIEVEMENT,
                icon = AchievementIcon.FLAWLESS_EXECUTION,
                items = listOf(
                    AchievementTypeTierRes(
                        name = AchievementTitle.WORK_EXPERIENCE,
                        icon = AchievementIcon.WORK_EXPERIENCE,
                        visibility = AchievementVisibility.HIDDEN,
                        infoText = AchievementContent.WORK_EXPERIENCE
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.FLAWLESS_EXECUTION,
                        icon = AchievementIcon.FLAWLESS_EXECUTION,
                        visibility = AchievementVisibility.HIDDEN,
                        infoText = AchievementContent.FLAWLESS_EXECUTION
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.THEYRE_HERE,
                        icon = AchievementIcon.THEYRE_HERE,
                        visibility = AchievementVisibility.HIDDEN,
                        infoText = AchievementContent.THEYRE_HERE
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.ESCAPE_ARTIST,
                        icon = AchievementIcon.ESCAPE_ARTIST,
                        visibility = AchievementVisibility.HIDDEN,
                        infoText = AchievementContent.ESCAPE_ARTIST
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.BAIT,
                        icon = AchievementIcon.BAIT,
                        infoText = AchievementContent.BAIT,
                        visibility = AchievementVisibility.HIDDEN
                    ),
                    AchievementTypeTierRes(
                        name = AchievementTitle.DOOM_SLAYED,
                        icon = AchievementIcon.DOOM_SLAYED,
                        infoText = AchievementContent.DOOM_SLAYED,
                        visibility = AchievementVisibility.HIDDEN,
                    )
                )
            ),
            AchievementTypeRes(
                name = AchievementCategory.EXCLUSIVE,
                icon = AchievementIcon.PARANORMAL_PERFECTIONIST,
                items = listOf(
                    AchievementTypeTierRes(
                        name = AchievementTitle.PARANORMAL_PERFECTIONIST,
                        icon = AchievementIcon.PARANORMAL_PERFECTIONIST,
                        infoText = AchievementContent.DOOM_SLAYED,
                        exclusivity = AchievementExclusivity.PLAYSTATION.value
                    )
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

    private data class AchievementTypeRes(
        val name: AchievementCategory,
        val icon: AchievementIcon,
        val items: List<AchievementTypeTierRes>
    )

    private data class AchievementTypeTierRes(
        val name: AchievementTitle,
        val infoText: AchievementContent,
        val icon: AchievementIcon,
        val visibility: AchievementVisibility = AchievementVisibility.SHOWN,
        val exclusivity: Int =
            AchievementExclusivity.STEAM.value
                    or AchievementExclusivity.XBOX.value
                    or AchievementExclusivity.PLAYSTATION.value,
    )

    private fun AchievementTypeRes.toLocal() = AchievementTypeDto(
        name = name,
        icon = icon,
        items = items.toLocal()
    )

    private fun AchievementTypeTierRes.toLocal() = AchievementsTypeDto(
        title = name,
        infoText = infoText,
        icon = icon,
        visibility = visibility,
        exclusivity = exclusivity
    )

    @JvmName("codexAchievementGroupResListToLocalCodexAchievementGroupRes")
    private fun List<AchievementTypeRes>.toLocal() = map { dto ->
        dto.toLocal()
    }

    @JvmName("CodexAchievementGroupItemResListToLocalCodexAchievementGroupItemRes")
    private fun List<AchievementTypeTierRes>.toLocal() = map { dto ->
        dto.toLocal()
    }

}
