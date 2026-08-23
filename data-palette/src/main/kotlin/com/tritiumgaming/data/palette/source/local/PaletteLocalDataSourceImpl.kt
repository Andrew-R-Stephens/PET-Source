package com.tritiumgaming.data.palette.source.local

import com.tritiumgaming.shared.data.market.palette.mappers.PaletteResources
import com.tritiumgaming.shared.data.market.palette.mappers.PaletteResources.PaletteType
import com.tritiumgaming.shared.data.market.palette.mappers.asUuid
import com.tritiumgaming.shared.data.market.palette.source.LocalPaletteDataSource

class PaletteLocalDataSourceImpl :
    LocalPaletteDataSource<List<PaletteLocalDataSourceImpl.LocalPalette>> {

    private val localPaletteResources: List<PaletteType> = PaletteType.entries


    private val localPalettes: List<LocalPaletteDto> = listOf(
        LocalPaletteDto(
            uuid = PaletteType.CLASSIC.asUuid(),
            unlocked = true,
            priority = -1,
            palette = PaletteType.CLASSIC
        ),
        LocalPaletteDto(
            uuid = PaletteType.MONOCHROMACY.asUuid(),
            unlocked = true,
            priority = -1,
            palette = PaletteType.MONOCHROMACY
        ),
        LocalPaletteDto(
            uuid = PaletteType.DEUTERANOMALY.asUuid(),
            unlocked = true,
            priority = -1,
            palette = PaletteType.DEUTERANOMALY
        ),
        LocalPaletteDto(
            uuid = PaletteType.PROTANOMALY.asUuid(),
            unlocked = true,
            priority = -1,
            palette = PaletteType.PROTANOMALY
        ),
        LocalPaletteDto(
            uuid = PaletteType.TRITANOMALY.asUuid(),
            unlocked = true,
            priority = -1,
            palette = PaletteType.TRITANOMALY
        ),
        LocalPaletteDto(
            uuid = PaletteType.WHITEBOARD.asUuid(),
            unlocked = true,
            priority = -1,
            palette = PaletteType.WHITEBOARD
        ),
        LocalPaletteDto(
            uuid = PaletteType.STRATAGEM_HERO.asUuid(),
            unlocked = true,
            priority = -1,
            palette = PaletteType.STRATAGEM_HERO
        ),
        LocalPaletteDto(
            uuid = PaletteType.RECRUIT.asUuid(),
            unlocked = false,
            palette = PaletteType.RECRUIT
        ),
        LocalPaletteDto(
            uuid = PaletteType.INVESTIGATOR.asUuid(),
            unlocked = false,
            palette = PaletteType.INVESTIGATOR
        ),
        LocalPaletteDto(
            uuid = PaletteType.PRIVATE_INVESTIGATOR.asUuid(),
            unlocked = false,
            palette = PaletteType.PRIVATE_INVESTIGATOR
        ),
        LocalPaletteDto(
            uuid = PaletteType.DETECTIVE.asUuid(),
            unlocked = false,
            palette = PaletteType.DETECTIVE
        ),
        LocalPaletteDto(
            uuid = PaletteType.TECHNICIAN.asUuid(),
            unlocked = false,
            palette = PaletteType.TECHNICIAN
        ),
        LocalPaletteDto(
            uuid = PaletteType.SPECIALIST.asUuid(),
            unlocked = false,
            palette = PaletteType.SPECIALIST
        ),
        LocalPaletteDto(
            uuid = PaletteType.ANALYST.asUuid(),
            unlocked = false,
            palette = PaletteType.ANALYST
        ),
        LocalPaletteDto(
            uuid = PaletteType.AGENT.asUuid(),
            unlocked = false,
            palette = PaletteType.AGENT
        ),
        LocalPaletteDto(
            uuid = PaletteType.OPERATOR.asUuid(),
            unlocked = false,
            palette = PaletteType.OPERATOR
        ),
        LocalPaletteDto(
            uuid = PaletteType.COMMISSIONER.asUuid(),
            unlocked = false,
            palette = PaletteType.COMMISSIONER
        ),
        LocalPaletteDto(
            uuid = PaletteType.EASTER.asUuid(),
            unlocked = false,
            palette = PaletteType.EASTER
        ),
        LocalPaletteDto(
            uuid = PaletteType.HALLOWEEN_23.asUuid(),
            unlocked = false,
            palette = PaletteType.HALLOWEEN_23
        ),
        LocalPaletteDto(
            uuid = PaletteType.HOLIDAY_22.asUuid(),
            unlocked = false,
            palette = PaletteType.HOLIDAY_22
        ),
        LocalPaletteDto(
            uuid = PaletteType.HOLIDAY_23.asUuid(),
            unlocked = false,
            palette = PaletteType.HOLIDAY_23
        ),
        LocalPaletteDto(
            uuid = PaletteType.DISCORD.asUuid(),
            unlocked = false,
            palette = PaletteType.DISCORD
        ),
        LocalPaletteDto(
            uuid = PaletteType.CONTENT_CREATOR.asUuid(),
            unlocked = false,
            palette = PaletteType.CONTENT_CREATOR
        ),
        LocalPaletteDto(
            uuid = PaletteType.DEVELOPER.asUuid(),
            unlocked = false,
            palette = PaletteType.DEVELOPER
        ),
        LocalPaletteDto(
            uuid = PaletteType.TRANSLATOR.asUuid(),
            unlocked = false,
            palette = PaletteType.TRANSLATOR
        ),
        LocalPaletteDto(
            uuid = PaletteType.WINNER.asUuid(),
            unlocked = false,
            palette = PaletteType.WINNER
        ),
        LocalPaletteDto(
            uuid = PaletteType.ARTIST.asUuid(),
            unlocked = false,
            palette = PaletteType.ARTIST
        ),
    )

    override fun getPalettes(): Result<List<LocalPalette>> =
        Result.success(localPalettes.toLocal())

    data class LocalPaletteDto(
        val uuid: String,
        val unlocked: Boolean,
        val priority: Long = 0L,
        val palette: PaletteType
    )

    data class LocalPalette(
        val uuid: String,
        val unlocked: Boolean,
        val priority: Long = 0L,
        val palette: PaletteType
    )

    fun List<LocalPaletteDto>.toLocal() = map { it.toLocal() }

    fun LocalPaletteDto.toLocal() =
        LocalPalette(
            uuid = uuid,
            unlocked = unlocked,
            priority = priority,
            palette = palette
        )

}