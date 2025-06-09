package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostbox.source.local

import android.content.Context
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostbox.dto.GhostBoxResponseDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostbox.source.GhostBoxDataSource

class GhostBoxLocalDataSource(
    private val applicationContext: Context
): GhostBoxDataSource {

    val generalRequestResources
        get() = listOf(
            GhostBoxResource(content = R.string.spiritbox_entry_q6_8),
            GhostBoxResource(content = R.string.spiritbox_entry_q6_7),
            GhostBoxResource(content = R.string.spiritbox_entry_q6_9),
            GhostBoxResource(content = R.string.spiritbox_entry_q6_12),
            GhostBoxResource(content = R.string.spiritbox_entry_q6_13),
            GhostBoxResource(content = R.string.spiritbox_entry_q6_14),
            GhostBoxResource(content = R.string.spiritbox_entry_q6_16),
            GhostBoxResource(content = R.string.spiritbox_entry_q6_17),
            GhostBoxResource(content = R.string.spiritbox_entry_q6_18),
            GhostBoxResource(content = R.string.spiritbox_entry_q0_2),
            GhostBoxResource(content = R.string.spiritbox_entry_q0_5),
            GhostBoxResource(content = R.string.spiritbox_entry_q0_6),
            GhostBoxResource(content = R.string.spiritbox_entry_q0_7),
            GhostBoxResource(content = R.string.spiritbox_entry_q0_8),
            GhostBoxResource(content = R.string.spiritbox_entry_q0_9),
            GhostBoxResource(content = R.string.spiritbox_entry_q0_3)
        )

    val spiritBoxRequestResources
        get() = listOf(
            GhostBoxResource(content = R.string.spiritbox_entry_q5_7),
            GhostBoxResource(content = R.string.spiritbox_entry_q3_5),
            GhostBoxResource(content = R.string.spiritbox_entry_q5_1),
            GhostBoxResource(content = R.string.spiritbox_entry_q1_1),
            GhostBoxResource(content = R.string.spiritbox_entry_q1_2),
            GhostBoxResource(content = R.string.spiritbox_entry_q1_3),
            GhostBoxResource(content = R.string.spiritbox_entry_q1_6),
            GhostBoxResource(content = R.string.spiritbox_entry_q1_7),
            GhostBoxResource(content = R.string.spiritbox_entry_q6_5),
            GhostBoxResource(content = R.string.spiritbox_entry_q7_9),
            GhostBoxResource(content = R.string.spiritbox_entry_q7_11)
        )

    val ouijaBoardRequestResources
        get() = listOf(
            GhostBoxResource(content = R.string.spiritbox_entry_q5_7),
            GhostBoxResource(content = R.string.spiritbox_entry_q6_5),
            GhostBoxResource(content = R.string.spiritbox_entry_q6_9),
            GhostBoxResource(content = R.string.spiritbox_entry_q0_2),
            GhostBoxResource(content = R.string.spiritbox_entry_q0_7),
            GhostBoxResource(content = R.string.spiritbox_entry_q1_1),
            GhostBoxResource(content = R.string.spiritbox_entry_q1_2),
            GhostBoxResource(content = R.string.spiritbox_entry_q1_3),
            GhostBoxResource(content = R.string.spiritbox_entry_q1_6),
            GhostBoxResource(content = R.string.spiritbox_entry_q1_7),
            GhostBoxResource(content = R.string.spiritbox_entry_q2_1),
            GhostBoxResource(content = R.string.spiritbox_entry_q3_5),
            GhostBoxResource(content = R.string.spiritbox_entry_q5_1),
            GhostBoxResource(content = R.string.spiritbox_entry_q6_7),
            GhostBoxResource(content = R.string.spiritbox_entry_q6_8),
            GhostBoxResource(content = R.string.spiritbox_entry_q6_12),
            GhostBoxResource(content = R.string.spiritbox_entry_q6_13),
            GhostBoxResource(content = R.string.spiritbox_entry_q6_14),
            GhostBoxResource(content = R.string.spiritbox_entry_q6_16),
            GhostBoxResource(content = R.string.spiritbox_entry_q6_17),
            GhostBoxResource(content = R.string.spiritbox_entry_q6_18),
            GhostBoxResource(content = R.string.spiritbox_entry_q7_3),
            GhostBoxResource(content = R.string.spiritbox_entry_q7_9),
            GhostBoxResource(content = R.string.spiritbox_entry_q7_11),
            GhostBoxResource(content = R.string.spiritbox_entry_q8_1),
            GhostBoxResource(content = R.string.spiritbox_entry_q8_2),
            GhostBoxResource(content = R.string.spiritbox_entry_q8_3),
            GhostBoxResource(content = R.string.spiritbox_entry_q8_4)
        )

    override fun fetchGeneralRequests(): Result<List<GhostBoxResponseDto>> {
        val entries = mutableListOf<GhostBoxResponseDto>()
        generalRequestResources.forEach { resDto ->
            entries.add(GhostBoxResponseDto(content = resDto.content))
        }

        return Result.success(entries)
    }

    override fun fetchSpiritBoxRequests(): Result<List<GhostBoxResponseDto>> {
        val entries = mutableListOf<GhostBoxResponseDto>()
        spiritBoxRequestResources.forEach { resDto ->
            entries.add(GhostBoxResponseDto(content = resDto.content))
        }

        return Result.success(entries)
    }

    override fun fetchOuijaBoardRequests(): Result<List<GhostBoxResponseDto>> {
        val entries = mutableListOf<GhostBoxResponseDto>()
        ouijaBoardRequestResources.forEach { resDto ->
            entries.add(GhostBoxResponseDto(content = resDto.content))
        }

        return Result.success(entries)
    }

}

data class GhostBoxResource(
    @StringRes val content: Int
)