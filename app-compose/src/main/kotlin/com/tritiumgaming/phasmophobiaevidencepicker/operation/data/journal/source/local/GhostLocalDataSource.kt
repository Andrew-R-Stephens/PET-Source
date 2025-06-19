package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.source.local

import android.content.Context
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto.GhostDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.mapper.GhostResources.GhostDescription
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.mapper.GhostResources.GhostHuntInfo
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.mapper.GhostResources.GhostStrength
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.mapper.GhostResources.GhostTitle
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.mapper.GhostResources.GhostWeakness
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.source.GhostDataSource

class GhostLocalDataSource(
    private val applicationContext: Context
): GhostDataSource {

    private val ghostResourceDto: List<GhostResourceDto>
        get() = listOf(
            GhostResourceDto(
                id = R.string.ghost_id_banshee,
                name = GhostTitle.BANSHEE,
                info = GhostDescription.BANSHEE,
                strengthData = GhostStrength.BANSHEE,
                weaknessData = GhostWeakness.BANSHEE,
                huntData = GhostHuntInfo.BANSHEE,
                normalEvidence = listOf(
                    R.string.evidence_id_dots,
                    R.string.evidence_id_ultraviolet,
                    R.string.evidence_id_orbs,
                ),
                strictEvidence = emptyList()
            ),
            GhostResourceDto(
                id = R.string.ghost_id_demon,
                name = GhostTitle.DEMON,
                info = GhostDescription.DEMON,
                strengthData = GhostStrength.DEMON,
                weaknessData = GhostWeakness.DEMON,
                huntData = GhostHuntInfo.DEMON,
                normalEvidence = listOf(
                    R.string.evidence_id_ultraviolet,
                    R.string.evidence_id_temperatures,
                    R.string.evidence_id_book,
                ),
                strictEvidence = emptyList()
            ),
            GhostResourceDto(
                id = R.string.ghost_id_deogen,
                name = GhostTitle.DEOGEN,
                info = GhostDescription.DEOGEN,
                strengthData = GhostStrength.DEOGEN,
                weaknessData = GhostWeakness.DEOGEN,
                huntData = GhostHuntInfo.DEOGEN,
                normalEvidence = listOf(
                    R.string.evidence_id_book,
                    R.string.evidence_id_box,
                    R.string.evidence_id_dots,
                ),
                strictEvidence = listOf(
                    R.string.evidence_id_box
                )
            ),
            GhostResourceDto(
                id = R.string.ghost_id_goryo,
                name = GhostTitle.GORYO,
                info = GhostDescription.GORYO,
                strengthData = GhostStrength.GORYO,
                weaknessData = GhostWeakness.GORYO,
                huntData = GhostHuntInfo.GORYO,
                normalEvidence = listOf(
                    R.string.evidence_id_dots,
                    R.string.evidence_id_emf,
                    R.string.evidence_id_ultraviolet,
                ),
                strictEvidence = listOf(
                    R.string.evidence_id_dots
                )
            ),GhostResourceDto(
                id = R.string.ghost_id_hantu,
                name = GhostTitle.HANTU,
                info = GhostDescription.HANTU,
                strengthData = GhostStrength.HANTU,
                weaknessData = GhostWeakness.HANTU,
                huntData = GhostHuntInfo.HANTU,
                normalEvidence = listOf(
                    R.string.evidence_id_ultraviolet,
                    R.string.evidence_id_temperatures,
                    R.string.evidence_id_orbs,
                ),
                strictEvidence = listOf(
                    R.string.evidence_id_temperatures
                )
            ),
            GhostResourceDto(
                id = R.string.ghost_id_jinn,
                name = GhostTitle.JINN,
                info = GhostDescription.JINN,
                strengthData = GhostStrength.JINN,
                weaknessData = GhostWeakness.JINN,
                huntData = GhostHuntInfo.JINN,
                normalEvidence = listOf(
                    R.string.evidence_id_emf,
                    R.string.evidence_id_ultraviolet,
                    R.string.evidence_id_temperatures,
                ),
                strictEvidence = emptyList()
            ),GhostResourceDto(
                id = R.string.ghost_id_mare,
                name = GhostTitle.MARE,
                info = GhostDescription.MARE,
                strengthData = GhostStrength.MARE,
                weaknessData = GhostWeakness.MARE,
                huntData = GhostHuntInfo.MARE,
                normalEvidence = listOf(
                    R.string.evidence_id_orbs,
                    R.string.evidence_id_book,
                    R.string.evidence_id_box,
                ),
                strictEvidence = emptyList()
            ),
            GhostResourceDto(
                id = R.string.ghost_id_moroi,
                name = GhostTitle.MOROI,
                info = GhostDescription.MOROI,
                strengthData = GhostStrength.MOROI,
                weaknessData = GhostWeakness.MOROI,
                huntData = GhostHuntInfo.MOROI,
                normalEvidence = listOf(
                    R.string.evidence_id_box,
                    R.string.evidence_id_temperatures,
                    R.string.evidence_id_book,
                ),
                strictEvidence = listOf(
                    R.string.evidence_id_box
                )
            ),GhostResourceDto(
                id = R.string.ghost_id_myling,
                name = GhostTitle.MYLING,
                info = GhostDescription.MYLING,
                strengthData = GhostStrength.MYLING,
                weaknessData = GhostWeakness.MYLING,
                huntData = GhostHuntInfo.MYLING,
                normalEvidence = listOf(
                    R.string.evidence_id_emf,
                    R.string.evidence_id_ultraviolet,
                    R.string.evidence_id_book,
                ),
                strictEvidence = emptyList()
            ),
            GhostResourceDto(
                id = R.string.ghost_id_obake,
                name = GhostTitle.OBAKE,
                info = GhostDescription.OBAKE,
                strengthData = GhostStrength.OBAKE,
                weaknessData = GhostWeakness.OBAKE,
                huntData = GhostHuntInfo.OBAKE,
                normalEvidence = listOf(
                    R.string.evidence_id_emf,
                    R.string.evidence_id_ultraviolet,
                    R.string.evidence_id_orbs,
                ),
                strictEvidence = listOf(
                    R.string.evidence_id_ultraviolet
                )
            ),GhostResourceDto(
                id = R.string.ghost_id_oni,
                name = GhostTitle.ONI,
                info = GhostDescription.ONI,
                strengthData = GhostStrength.ONI,
                weaknessData = GhostWeakness.ONI,
                huntData = GhostHuntInfo.ONI,
                normalEvidence = listOf(
                    R.string.evidence_id_dots,
                    R.string.evidence_id_emf,
                    R.string.evidence_id_temperatures,
                ),
                strictEvidence = emptyList()
            ),
            GhostResourceDto(
                id = R.string.ghost_id_onryo,
                name = GhostTitle.ONRYO,
                info = GhostDescription.ONRYO,
                strengthData = GhostStrength.ONRYO,
                weaknessData = GhostWeakness.ONRYO,
                huntData = GhostHuntInfo.ONRYO,
                normalEvidence = listOf(
                    R.string.evidence_id_temperatures,
                    R.string.evidence_id_orbs,
                    R.string.evidence_id_box,
                ),
                strictEvidence = emptyList()
            ),GhostResourceDto(
                id = R.string.ghost_id_phantom,
                name = GhostTitle.PHANTOM,
                info = GhostDescription.PHANTOM,
                strengthData = GhostStrength.PHANTOM,
                weaknessData = GhostWeakness.PHANTOM,
                huntData = GhostHuntInfo.PHANTOM,
                normalEvidence = listOf(
                    R.string.evidence_id_dots,
                    R.string.evidence_id_ultraviolet,
                    R.string.evidence_id_box,
                ),
                strictEvidence = emptyList()
            ),
            GhostResourceDto(
                id = R.string.ghost_id_poltergeist,
                name = GhostTitle.POLTERGEIST,
                info = GhostDescription.POLTERGEIST,
                strengthData = GhostStrength.POLTERGEIST,
                weaknessData = GhostWeakness.POLTERGEIST,
                huntData = GhostHuntInfo.POLTERGEIST,
                normalEvidence = listOf(
                    R.string.evidence_id_ultraviolet,
                    R.string.evidence_id_book,
                    R.string.evidence_id_box,
                ),
                strictEvidence = emptyList()
            ),GhostResourceDto(
                id = R.string.ghost_id_raiju,
                name = GhostTitle.RAIJU,
                info = GhostDescription.RAIJU,
                strengthData = GhostStrength.RAIJU,
                weaknessData = GhostWeakness.RAIJU,
                huntData = GhostHuntInfo.RAIJU,
                normalEvidence = listOf(
                    R.string.evidence_id_dots,
                    R.string.evidence_id_emf,
                    R.string.evidence_id_orbs,
                ),
                strictEvidence = emptyList()
            ),
            GhostResourceDto(
                id = R.string.ghost_id_revenant,
                name = GhostTitle.REVENANT,
                info = GhostDescription.REVENANT,
                strengthData = GhostStrength.REVENANT,
                weaknessData = GhostWeakness.REVENANT,
                huntData = GhostHuntInfo.REVENANT,
                normalEvidence = listOf(
                    R.string.evidence_id_temperatures,
                    R.string.evidence_id_orbs,
                    R.string.evidence_id_book,
                ),
                strictEvidence = emptyList()
            ),GhostResourceDto(
                id = R.string.ghost_id_shade,
                name = GhostTitle.SHADE,
                info = GhostDescription.SHADE,
                strengthData = GhostStrength.SHADE,
                weaknessData = GhostWeakness.SHADE,
                huntData = GhostHuntInfo.SHADE,
                normalEvidence = listOf(
                    R.string.evidence_id_emf,
                    R.string.evidence_id_temperatures,
                    R.string.evidence_id_book,
                ),
                strictEvidence = emptyList()
            ),
            GhostResourceDto(
                id = R.string.ghost_id_spirit,
                name = GhostTitle.SPIRIT,
                info = GhostDescription.SPIRIT,
                strengthData = GhostStrength.SPIRIT,
                weaknessData = GhostWeakness.SPIRIT,
                huntData = GhostHuntInfo.SPIRIT,
                normalEvidence = listOf(
                    R.string.evidence_id_emf,
                    R.string.evidence_id_box,
                    R.string.evidence_id_book,
                ),
                strictEvidence = emptyList()
            ),GhostResourceDto(
                id = R.string.ghost_id_thaye,
                name = GhostTitle.THAYE,
                info = GhostDescription.THAYE,
                strengthData = GhostStrength.THAYE,
                weaknessData = GhostWeakness.THAYE,
                huntData = GhostHuntInfo.THAYE,
                normalEvidence = listOf(
                    R.string.evidence_id_book,
                    R.string.evidence_id_dots,
                    R.string.evidence_id_orbs,
                ),
                strictEvidence = emptyList()
            ),
            GhostResourceDto(
                id = R.string.ghost_id_thetwins,
                name = GhostTitle.THE_TWINS,
                info = GhostDescription.THE_TWINS,
                strengthData = GhostStrength.THE_TWINS,
                weaknessData = GhostWeakness.THE_TWINS,
                huntData = GhostHuntInfo.THE_TWINS,
                normalEvidence = listOf(
                    R.string.evidence_id_emf,
                    R.string.evidence_id_temperatures,
                    R.string.evidence_id_box,
                ),
                strictEvidence = emptyList()
            ),GhostResourceDto(
                id = R.string.ghost_id_themimic,
                name = GhostTitle.THE_MIMIC,
                info = GhostDescription.THE_MIMIC,
                strengthData = GhostStrength.THE_MIMIC,
                weaknessData = GhostWeakness.THE_MIMIC,
                huntData = GhostHuntInfo.THE_MIMIC,
                normalEvidence = listOf(
                    R.string.evidence_id_ultraviolet,
                    R.string.evidence_id_temperatures,
                    R.string.evidence_id_box,
                    R.string.evidence_id_orbs,
                ),
                strictEvidence = listOf(
                    R.string.evidence_id_orbs
                )
            ),
            GhostResourceDto(
                id = R.string.ghost_id_wraith,
                name = GhostTitle.WRAITH,
                info = GhostDescription.WRAITH,
                strengthData = GhostStrength.WRAITH,
                weaknessData = GhostWeakness.WRAITH,
                huntData = GhostHuntInfo.WRAITH,
                normalEvidence = listOf(
                    R.string.evidence_id_emf,
                    R.string.evidence_id_box,
                    R.string.evidence_id_dots,
                ),
                strictEvidence = emptyList()
            ),GhostResourceDto(
                id = R.string.ghost_id_yokai,
                name = GhostTitle.YOKAI,
                info = GhostDescription.YOKAI,
                strengthData = GhostStrength.YOKAI,
                weaknessData = GhostWeakness.YOKAI,
                huntData = GhostHuntInfo.YOKAI,
                normalEvidence = listOf(
                    R.string.evidence_id_dots,
                    R.string.evidence_id_orbs,
                    R.string.evidence_id_box,
                ),
                strictEvidence = emptyList()
            ),
            GhostResourceDto(
                id = R.string.ghost_id_yurei,
                name = GhostTitle.YUREI,
                info = GhostDescription.YUREI,
                strengthData = GhostStrength.YUREI,
                weaknessData = GhostWeakness.YUREI,
                huntData = GhostHuntInfo.YUREI,
                normalEvidence = listOf(
                    R.string.evidence_id_dots,
                    R.string.evidence_id_temperatures,
                    R.string.evidence_id_orbs,
                ),
                strictEvidence = emptyList()
            )
        )

    override fun get(): Result<List<GhostDto>> {

        val result: MutableList<GhostDto> = mutableListOf()

        ghostResourceDto.forEach { resDto ->
            result.add(resDto.toGhostDto())
        }
        return Result.success(emptyList())

    }

    private fun List<GhostResourceDto>.toGhostDto() = map { it.toGhostDto() }

    private fun GhostResourceDto.toGhostDto() = GhostDto(
        id = applicationContext.getString(id),
        name = name,
        info = info,
        strengthData = strengthData,
        weaknessData = weaknessData,
        huntData = huntData,
        normalEvidence = normalEvidence.map { applicationContext.getString(it) },
        strictEvidence = strictEvidence.map { applicationContext.getString(it) }
    )

    private data class GhostResourceDto(
        @StringRes val id: Int,
        val name: GhostTitle,
        val info: GhostDescription,
        val strengthData: GhostStrength,
        val weaknessData: GhostWeakness,
        val huntData: GhostHuntInfo,
        @StringRes val normalEvidence: List<Int>,
        @StringRes val strictEvidence: List<Int>,
    )

}