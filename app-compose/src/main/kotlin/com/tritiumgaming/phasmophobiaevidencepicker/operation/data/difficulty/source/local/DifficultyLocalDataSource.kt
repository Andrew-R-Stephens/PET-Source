package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.source.local

import android.content.Context
import androidx.annotation.IntegerRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.dto.DifficultyModelDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.source.DifficultyDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.mapper.DifficultyResources.DifficultyResponseType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.mapper.DifficultyResources.DifficultyTitle

class DifficultyLocalDataSource(
    private val applicationContext: Context
): DifficultyDataSource {

    private val difficultyResourceDto
        get() = listOf(
            DifficultyResourceDto(
                index = 0,
                name = DifficultyTitle.AMATEUR,
                time = R.integer.difficulty_time_amateur,
                modifier = R.fraction.difficulty_modifier_amateur,
                initialSanity = R.fraction.difficulty_initialSanity_amateur,
                responseType = DifficultyResponseType.KNOWN
            ),
            DifficultyResourceDto(
                index = 1,
                name = DifficultyTitle.INTERMEDIATE,
                time = R.integer.difficulty_time_intermediate,
                modifier = R.fraction.difficulty_modifier_intermediate,
                initialSanity = R.fraction.difficulty_initialSanity_intermediate,
                responseType = DifficultyResponseType.KNOWN
            ),
            DifficultyResourceDto(
                index = 2,
                name = DifficultyTitle.PROFESSIONAL,
                time = R.integer.difficulty_time_professional,
                modifier = R.fraction.difficulty_modifier_professional,
                initialSanity = R.fraction.difficulty_initialSanity_professional,
                responseType = DifficultyResponseType.UNKNOWN
            ),
            DifficultyResourceDto(
                index = 3,
                name = DifficultyTitle.NIGHTMARE,
                time = R.integer.difficulty_time_nightmare,
                modifier = R.fraction.difficulty_modifier_nightmare,
                initialSanity = R.fraction.difficulty_initialSanity_nightmare,
                responseType = DifficultyResponseType.UNKNOWN
            ),
            DifficultyResourceDto(
                index = 4,
                name = DifficultyTitle.INSANITY,
                time = R.integer.difficulty_time_insanity,
                modifier = R.fraction.difficulty_modifier_insanity,
                initialSanity = R.fraction.difficulty_initialSanity_insanity,
                responseType = DifficultyResponseType.UNKNOWN
            ),
        )

    override fun fetchDifficulties(): Result<List<DifficultyModelDto>> =
        Result.success(difficultyResourceDto.toDifficultyModelDto())

    private fun List<DifficultyResourceDto>.toDifficultyModelDto() = map{ it.toDifficultyModelDto() }

    private fun DifficultyResourceDto.toDifficultyModelDto() = DifficultyModelDto(
        index = index,
        name = name,
        time = applicationContext.resources.getInteger(time).toLong(),
        modifier = applicationContext.resources.getFraction(modifier, 1, 1).toFloat(),
        initialSanity = applicationContext.resources.getFraction(initialSanity, 1, 1).toFloat(),
        responseType = responseType
    )

    private data class DifficultyResourceDto(
        val index: Int,
        val name: DifficultyTitle,
        @IntegerRes val time: Int,
        @IntegerRes val modifier: Int,
        @IntegerRes  val initialSanity: Int,
        val responseType: DifficultyResponseType
    )

}
