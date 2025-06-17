package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostbox.source.local

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostbox.dto.GhostBoxResponseDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostbox.mapper.GhostBoxResources.Response
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostbox.source.GhostBoxDataSource

class GhostBoxLocalDataSource: GhostBoxDataSource {

    val generalRequestResources
        get() = listOf(
            GhostBoxResourceDto(content = Response.Q_20),
            GhostBoxResourceDto(content = Response.Q_19),
            GhostBoxResourceDto(content = Response.Q_25),
            GhostBoxResourceDto(content = Response.Q_26),
            GhostBoxResourceDto(content = Response.Q_27),
            GhostBoxResourceDto(content = Response.Q_28),
            GhostBoxResourceDto(content = Response.Q_22),
            GhostBoxResourceDto(content = Response.Q_23),
            GhostBoxResourceDto(content = Response.Q_24),
            GhostBoxResourceDto(content = Response.Q_17),
            GhostBoxResourceDto(content = Response.Q_02),
            GhostBoxResourceDto(content = Response.Q_03),
            GhostBoxResourceDto(content = Response.Q_21),
            GhostBoxResourceDto(content = Response.Q_04),
            GhostBoxResourceDto(content = Response.Q_05),
            GhostBoxResourceDto(content = Response.Q_01)
        )

    val spiritBoxRequestResources
        get() = listOf(
            GhostBoxResourceDto(content =Response.Q_16),
            GhostBoxResourceDto(content =Response.Q_12),
            GhostBoxResourceDto(content =Response.Q_14),
            GhostBoxResourceDto(content =Response.Q_06),
            GhostBoxResourceDto(content =Response.Q_07),
            GhostBoxResourceDto(content =Response.Q_08),
            GhostBoxResourceDto(content =Response.Q_09),
            GhostBoxResourceDto(content =Response.Q_10),
            GhostBoxResourceDto(content =Response.Q_18),
            GhostBoxResourceDto(content =Response.Q_31),
            GhostBoxResourceDto(content =Response.Q_32)
        )

    val ouijaBoardRequestResources
        get() = listOf(
            GhostBoxResourceDto(content = Response.Q_16),
            GhostBoxResourceDto(content = Response.Q_18),
            GhostBoxResourceDto(content = Response.Q_25),
            GhostBoxResourceDto(content = Response.Q_17),
            GhostBoxResourceDto(content = Response.Q_21),
            GhostBoxResourceDto(content = Response.Q_06),
            GhostBoxResourceDto(content = Response.Q_07),
            GhostBoxResourceDto(content = Response.Q_08),
            GhostBoxResourceDto(content = Response.Q_09),
            GhostBoxResourceDto(content = Response.Q_10),
            GhostBoxResourceDto(content = Response.Q_11),
            GhostBoxResourceDto(content = Response.Q_12),
            GhostBoxResourceDto(content = Response.Q_14),
            GhostBoxResourceDto(content = Response.Q_19),
            GhostBoxResourceDto(content = Response.Q_20),
            GhostBoxResourceDto(content = Response.Q_26),
            GhostBoxResourceDto(content = Response.Q_27),
            GhostBoxResourceDto(content = Response.Q_28),
            GhostBoxResourceDto(content = Response.Q_22),
            GhostBoxResourceDto(content = Response.Q_23),
            GhostBoxResourceDto(content = Response.Q_24),
            GhostBoxResourceDto(content = Response.Q_30),
            GhostBoxResourceDto(content = Response.Q_31),
            GhostBoxResourceDto(content = Response.Q_32),
            GhostBoxResourceDto(content = Response.Q_33),
            GhostBoxResourceDto(content = Response.Q_34),
            GhostBoxResourceDto(content = Response.Q_35),
            GhostBoxResourceDto(content = Response.Q_36)
        )

    override fun fetchGeneralRequests(): Result<List<GhostBoxResponseDto>> =
        Result.success(generalRequestResources.map { it.toGhostBoxResponseDto() })

    override fun fetchSpiritBoxRequests(): Result<List<GhostBoxResponseDto>> =
        Result.success(spiritBoxRequestResources.map { it.toGhostBoxResponseDto() })

    override fun fetchOuijaBoardRequests(): Result<List<GhostBoxResponseDto>> =
        Result.success(ouijaBoardRequestResources.map { it.toGhostBoxResponseDto() })

}

fun GhostBoxResourceDto.toGhostBoxResponseDto() = GhostBoxResponseDto(
    content = content
)

data class GhostBoxResourceDto(
    val content: Response
)