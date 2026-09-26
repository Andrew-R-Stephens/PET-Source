package com.tritiumgaming.data.typography.source.local

import com.tritiumgaming.shared.data.market.typography.mappers.TypographyResources.TypographyType
import com.tritiumgaming.shared.data.market.typography.mappers.asUuid
import com.tritiumgaming.shared.data.market.typography.source.LocalTypographyDataSource

class TypographyLocalDataSourceImpl:
    LocalTypographyDataSource<List<TypographyLocalDataSourceImpl.LocalTypography>> {

    private val localTypographies: List<LocalTypographyDto> = listOf(
        LocalTypographyDto(
            uuid = TypographyType.CLASSIC.asUuid(),
            unlocked = true,
            priority = -1,
            typography = TypographyType.CLASSIC
        ),
        LocalTypographyDto(
            uuid = TypographyType.JETBRAINS_MONO.asUuid(),
            unlocked = true,
            priority = -1,
            typography = TypographyType.JETBRAINS_MONO
        ),
        LocalTypographyDto(
            uuid = TypographyType.LONG_CANG.asUuid(),
            unlocked = true,
            priority = -1,
            typography = TypographyType.LONG_CANG
        ),
        LocalTypographyDto(
            uuid = TypographyType.NEW_TEGOMIN.asUuid(),
            unlocked = true,
            priority = -1,
            typography = TypographyType.NEW_TEGOMIN
        ),
        LocalTypographyDto(
            uuid = TypographyType.ANDROID.asUuid(),
            unlocked = true,
            priority = -1,
            typography = TypographyType.ANDROID
        ),
        LocalTypographyDto(
            uuid = TypographyType.BRICK.asUuid(),
            unlocked = true,
            priority = -1,
            typography = TypographyType.BRICK
        ),
        LocalTypographyDto(
            uuid = TypographyType.CLEAN.asUuid(),
            unlocked = true,
            priority = -1,
            typography = TypographyType.CLEAN
        ),
        LocalTypographyDto(
            uuid = TypographyType.JOURNAL.asUuid(),
            unlocked = true,
            priority = -1,
            typography = TypographyType.JOURNAL
        ),
        LocalTypographyDto(
            uuid = TypographyType.NEUCHA.asUuid(),
            unlocked = true,
            priority = -1,
            typography = TypographyType.NEUCHA
        ),
    )

    override fun getTypographies(): Result<List<LocalTypography>> =
        Result.success(localTypographies.toLocal())

    data class LocalTypographyDto(
        val uuid: String,
        val unlocked: Boolean,
        val priority: Long = 0L,
        val typography: TypographyType
    )

    data class LocalTypography(
        val uuid: String,
        val unlocked: Boolean,
        val priority: Long = 0L,
        val typography: TypographyType
    )

    fun List<LocalTypographyDto>.toLocal() = map { it.toLocal() }

    fun LocalTypographyDto.toLocal() =
        LocalTypography(
            uuid = uuid,
            unlocked = unlocked,
            priority = priority,
            typography = typography
        )

}
