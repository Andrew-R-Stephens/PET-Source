package com.tritiumgaming.data.ghost.source.local


import android.content.Context
import androidx.annotation.StringRes
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.data.ghost.source.GhostDataSource
import com.tritiumgaming.data.ghost.dto.GhostDto
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources

class GhostLocalDataSource(
    private val applicationContext: Context
): GhostDataSource {

    private val ghostResourceDto: List<GhostResourceDto>
        get() = listOf(
            GhostResourceDto(
                id = R.string.ghost_id_banshee,
                name = GhostResources.GhostTitle.BANSHEE,
                info = GhostResources.GhostDescription.BANSHEE,
                strengthData = GhostResources.GhostStrength.BANSHEE,
                weaknessData = GhostResources.GhostWeakness.BANSHEE,
                huntData = GhostResources.GhostHuntInfo.BANSHEE,
                normalEvidence = listOf(
                    R.string.evidence_id_dots,
                    R.string.evidence_id_ultraviolet,
                    R.string.evidence_id_orbs,
                ),
                strictEvidence = emptyList()
            ),
            GhostResourceDto(
                id = R.string.ghost_id_demon,
                name = GhostResources.GhostTitle.DEMON,
                info = GhostResources.GhostDescription.DEMON,
                strengthData = GhostResources.GhostStrength.DEMON,
                weaknessData = GhostResources.GhostWeakness.DEMON,
                huntData = GhostResources.GhostHuntInfo.DEMON,
                normalEvidence = listOf(
                    R.string.evidence_id_ultraviolet,
                    R.string.evidence_id_temperatures,
                    R.string.evidence_id_book,
                ),
                strictEvidence = emptyList()
            ),
            GhostResourceDto(
                id = R.string.ghost_id_deogen,
                name = GhostResources.GhostTitle.DEOGEN,
                info = GhostResources.GhostDescription.DEOGEN,
                strengthData = GhostResources.GhostStrength.DEOGEN,
                weaknessData = GhostResources.GhostWeakness.DEOGEN,
                huntData = GhostResources.GhostHuntInfo.DEOGEN,
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
                name = GhostResources.GhostTitle.GORYO,
                info = GhostResources.GhostDescription.GORYO,
                strengthData = GhostResources.GhostStrength.GORYO,
                weaknessData = GhostResources.GhostWeakness.GORYO,
                huntData = GhostResources.GhostHuntInfo.GORYO,
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
                name = GhostResources.GhostTitle.HANTU,
                info = GhostResources.GhostDescription.HANTU,
                strengthData = GhostResources.GhostStrength.HANTU,
                weaknessData = GhostResources.GhostWeakness.HANTU,
                huntData = GhostResources.GhostHuntInfo.HANTU,
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
                name = GhostResources.GhostTitle.JINN,
                info = GhostResources.GhostDescription.JINN,
                strengthData = GhostResources.GhostStrength.JINN,
                weaknessData = GhostResources.GhostWeakness.JINN,
                huntData = GhostResources.GhostHuntInfo.JINN,
                normalEvidence = listOf(
                    R.string.evidence_id_emf,
                    R.string.evidence_id_ultraviolet,
                    R.string.evidence_id_temperatures,
                ),
                strictEvidence = emptyList()
            ),GhostResourceDto(
                id = R.string.ghost_id_mare,
                name = GhostResources.GhostTitle.MARE,
                info = GhostResources.GhostDescription.MARE,
                strengthData = GhostResources.GhostStrength.MARE,
                weaknessData = GhostResources.GhostWeakness.MARE,
                huntData = GhostResources.GhostHuntInfo.MARE,
                normalEvidence = listOf(
                    R.string.evidence_id_orbs,
                    R.string.evidence_id_book,
                    R.string.evidence_id_box,
                ),
                strictEvidence = emptyList()
            ),
            GhostResourceDto(
                id = R.string.ghost_id_moroi,
                name = GhostResources.GhostTitle.MOROI,
                info = GhostResources.GhostDescription.MOROI,
                strengthData = GhostResources.GhostStrength.MOROI,
                weaknessData = GhostResources.GhostWeakness.MOROI,
                huntData = GhostResources.GhostHuntInfo.MOROI,
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
                name = GhostResources.GhostTitle.MYLING,
                info = GhostResources.GhostDescription.MYLING,
                strengthData = GhostResources.GhostStrength.MYLING,
                weaknessData = GhostResources.GhostWeakness.MYLING,
                huntData = GhostResources.GhostHuntInfo.MYLING,
                normalEvidence = listOf(
                    R.string.evidence_id_emf,
                    R.string.evidence_id_ultraviolet,
                    R.string.evidence_id_book,
                ),
                strictEvidence = emptyList()
            ),
            GhostResourceDto(
                id = R.string.ghost_id_obake,
                name = GhostResources.GhostTitle.OBAKE,
                info = GhostResources.GhostDescription.OBAKE,
                strengthData = GhostResources.GhostStrength.OBAKE,
                weaknessData = GhostResources.GhostWeakness.OBAKE,
                huntData = GhostResources.GhostHuntInfo.OBAKE,
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
                name = GhostResources.GhostTitle.ONI,
                info = GhostResources.GhostDescription.ONI,
                strengthData = GhostResources.GhostStrength.ONI,
                weaknessData = GhostResources.GhostWeakness.ONI,
                huntData = GhostResources.GhostHuntInfo.ONI,
                normalEvidence = listOf(
                    R.string.evidence_id_dots,
                    R.string.evidence_id_emf,
                    R.string.evidence_id_temperatures,
                ),
                strictEvidence = emptyList()
            ),
            GhostResourceDto(
                id = R.string.ghost_id_onryo,
                name = GhostResources.GhostTitle.ONRYO,
                info = GhostResources.GhostDescription.ONRYO,
                strengthData = GhostResources.GhostStrength.ONRYO,
                weaknessData = GhostResources.GhostWeakness.ONRYO,
                huntData = GhostResources.GhostHuntInfo.ONRYO,
                normalEvidence = listOf(
                    R.string.evidence_id_temperatures,
                    R.string.evidence_id_orbs,
                    R.string.evidence_id_box,
                ),
                strictEvidence = emptyList()
            ),GhostResourceDto(
                id = R.string.ghost_id_phantom,
                name = GhostResources.GhostTitle.PHANTOM,
                info = GhostResources.GhostDescription.PHANTOM,
                strengthData = GhostResources.GhostStrength.PHANTOM,
                weaknessData = GhostResources.GhostWeakness.PHANTOM,
                huntData = GhostResources.GhostHuntInfo.PHANTOM,
                normalEvidence = listOf(
                    R.string.evidence_id_dots,
                    R.string.evidence_id_ultraviolet,
                    R.string.evidence_id_box,
                ),
                strictEvidence = emptyList()
            ),
            GhostResourceDto(
                id = R.string.ghost_id_poltergeist,
                name = GhostResources.GhostTitle.POLTERGEIST,
                info = GhostResources.GhostDescription.POLTERGEIST,
                strengthData = GhostResources.GhostStrength.POLTERGEIST,
                weaknessData = GhostResources.GhostWeakness.POLTERGEIST,
                huntData = GhostResources.GhostHuntInfo.POLTERGEIST,
                normalEvidence = listOf(
                    R.string.evidence_id_ultraviolet,
                    R.string.evidence_id_book,
                    R.string.evidence_id_box,
                ),
                strictEvidence = emptyList()
            ),GhostResourceDto(
                id = R.string.ghost_id_raiju,
                name = GhostResources.GhostTitle.RAIJU,
                info = GhostResources.GhostDescription.RAIJU,
                strengthData = GhostResources.GhostStrength.RAIJU,
                weaknessData = GhostResources.GhostWeakness.RAIJU,
                huntData = GhostResources.GhostHuntInfo.RAIJU,
                normalEvidence = listOf(
                    R.string.evidence_id_dots,
                    R.string.evidence_id_emf,
                    R.string.evidence_id_orbs,
                ),
                strictEvidence = emptyList()
            ),
            GhostResourceDto(
                id = R.string.ghost_id_revenant,
                name = GhostResources.GhostTitle.REVENANT,
                info = GhostResources.GhostDescription.REVENANT,
                strengthData = GhostResources.GhostStrength.REVENANT,
                weaknessData = GhostResources.GhostWeakness.REVENANT,
                huntData = GhostResources.GhostHuntInfo.REVENANT,
                normalEvidence = listOf(
                    R.string.evidence_id_temperatures,
                    R.string.evidence_id_orbs,
                    R.string.evidence_id_book,
                ),
                strictEvidence = emptyList()
            ),GhostResourceDto(
                id = R.string.ghost_id_shade,
                name = GhostResources.GhostTitle.SHADE,
                info = GhostResources.GhostDescription.SHADE,
                strengthData = GhostResources.GhostStrength.SHADE,
                weaknessData = GhostResources.GhostWeakness.SHADE,
                huntData = GhostResources.GhostHuntInfo.SHADE,
                normalEvidence = listOf(
                    R.string.evidence_id_emf,
                    R.string.evidence_id_temperatures,
                    R.string.evidence_id_book,
                ),
                strictEvidence = emptyList()
            ),
            GhostResourceDto(
                id = R.string.ghost_id_spirit,
                name = GhostResources.GhostTitle.SPIRIT,
                info = GhostResources.GhostDescription.SPIRIT,
                strengthData = GhostResources.GhostStrength.SPIRIT,
                weaknessData = GhostResources.GhostWeakness.SPIRIT,
                huntData = GhostResources.GhostHuntInfo.SPIRIT,
                normalEvidence = listOf(
                    R.string.evidence_id_emf,
                    R.string.evidence_id_box,
                    R.string.evidence_id_book,
                ),
                strictEvidence = emptyList()
            ),GhostResourceDto(
                id = R.string.ghost_id_thaye,
                name = GhostResources.GhostTitle.THAYE,
                info = GhostResources.GhostDescription.THAYE,
                strengthData = GhostResources.GhostStrength.THAYE,
                weaknessData = GhostResources.GhostWeakness.THAYE,
                huntData = GhostResources.GhostHuntInfo.THAYE,
                normalEvidence = listOf(
                    R.string.evidence_id_book,
                    R.string.evidence_id_dots,
                    R.string.evidence_id_orbs,
                ),
                strictEvidence = emptyList()
            ),
            GhostResourceDto(
                id = R.string.ghost_id_thetwins,
                name = GhostResources.GhostTitle.THE_TWINS,
                info = GhostResources.GhostDescription.THE_TWINS,
                strengthData = GhostResources.GhostStrength.THE_TWINS,
                weaknessData = GhostResources.GhostWeakness.THE_TWINS,
                huntData = GhostResources.GhostHuntInfo.THE_TWINS,
                normalEvidence = listOf(
                    R.string.evidence_id_emf,
                    R.string.evidence_id_temperatures,
                    R.string.evidence_id_box,
                ),
                strictEvidence = emptyList()
            ),GhostResourceDto(
                id = R.string.ghost_id_themimic,
                name = GhostResources.GhostTitle.THE_MIMIC,
                info = GhostResources.GhostDescription.THE_MIMIC,
                strengthData = GhostResources.GhostStrength.THE_MIMIC,
                weaknessData = GhostResources.GhostWeakness.THE_MIMIC,
                huntData = GhostResources.GhostHuntInfo.THE_MIMIC,
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
                name = GhostResources.GhostTitle.WRAITH,
                info = GhostResources.GhostDescription.WRAITH,
                strengthData = GhostResources.GhostStrength.WRAITH,
                weaknessData = GhostResources.GhostWeakness.WRAITH,
                huntData = GhostResources.GhostHuntInfo.WRAITH,
                normalEvidence = listOf(
                    R.string.evidence_id_emf,
                    R.string.evidence_id_box,
                    R.string.evidence_id_dots,
                ),
                strictEvidence = emptyList()
            ),GhostResourceDto(
                id = R.string.ghost_id_yokai,
                name = GhostResources.GhostTitle.YOKAI,
                info = GhostResources.GhostDescription.YOKAI,
                strengthData = GhostResources.GhostStrength.YOKAI,
                weaknessData = GhostResources.GhostWeakness.YOKAI,
                huntData = GhostResources.GhostHuntInfo.YOKAI,
                normalEvidence = listOf(
                    R.string.evidence_id_dots,
                    R.string.evidence_id_orbs,
                    R.string.evidence_id_box,
                ),
                strictEvidence = emptyList()
            ),
            GhostResourceDto(
                id = R.string.ghost_id_yurei,
                name = GhostResources.GhostTitle.YUREI,
                info = GhostResources.GhostDescription.YUREI,
                strengthData = GhostResources.GhostStrength.YUREI,
                weaknessData = GhostResources.GhostWeakness.YUREI,
                huntData = GhostResources.GhostHuntInfo.YUREI,
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
        return Result.success(result)

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
        @field:StringRes val id: Int,
        val name: GhostResources.GhostTitle,
        val info: GhostResources.GhostDescription,
        val strengthData: GhostResources.GhostStrength,
        val weaknessData: GhostResources.GhostWeakness,
        val huntData: GhostResources.GhostHuntInfo,
        @field:StringRes val normalEvidence: List<Int>,
        @field:StringRes val strictEvidence: List<Int>,
    )

}