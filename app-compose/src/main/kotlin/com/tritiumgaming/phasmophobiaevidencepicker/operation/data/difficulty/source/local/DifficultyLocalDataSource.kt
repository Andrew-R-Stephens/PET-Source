package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.source.local

import android.content.Context
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.dto.DifficultyModelDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.source.DifficultyDataSource

class DifficultyLocalDataSource(
    private val applicationContext: Context
): DifficultyDataSource {

    val difficultyResourceDto
        get() = listOf(
            DifficultyResourceDto(
                index = 0,
                name = R.string.difficulty_title_amateur,
                time = R.string.difficulty_time_amateur,
                modifier = R.string.difficulty_modifier_amateur,
                initialSanity = R.string.difficulty_initialSanity_amateur
            ),
            DifficultyResourceDto(
                index = 1,
                name = R.string.difficulty_title_intermediate,
                time = R.string.difficulty_time_intermediate,
                modifier = R.string.difficulty_modifier_intermediate,
                initialSanity = R.string.difficulty_initialSanity_intermediate
            ),
            DifficultyResourceDto(
                index = 2,
                name = R.string.difficulty_title_professional,
                time = R.string.difficulty_time_professional,
                modifier = R.string.difficulty_modifier_professional,
                initialSanity = R.string.difficulty_initialSanity_professional
            ),
            DifficultyResourceDto(
                index = 3,
                name = R.string.difficulty_title_nightmare,
                time = R.string.difficulty_time_nightmare,
                modifier = R.string.difficulty_modifier_nightmare,
                initialSanity = R.string.difficulty_initialSanity_nightmare
            ),
            DifficultyResourceDto(
                index = 4,
                name = R.string.difficulty_title_insanity,
                time = R.string.difficulty_time_insanity,
                modifier = R.string.difficulty_modifier_insanity,
                initialSanity = R.string.difficulty_initialSanity_insanity
            ),
        )

    override fun fetchDifficulties(): Result<List<DifficultyModelDto>> {

        val resources = applicationContext.resources

        val difficulties = mutableListOf<DifficultyModelDto>()

        difficultyResourceDto.forEach { resDto ->

            difficulties.add(
                DifficultyModelDto(
                    resDto.index,
                    resDto.name,
                    resources.getString(resDto.time).toLong(),
                    resources.getString(resDto.modifier).toFloat(),
                    resources.getString(resDto.initialSanity).toFloat()
                )
            )

        }

        return Result.success(difficulties)
    }

}

data class DifficultyResourceDto(
    val index: Int,
    @StringRes val name: Int,
    @StringRes val time: Int,
    @StringRes val modifier: Int,
    @StringRes val initialSanity: Int
)