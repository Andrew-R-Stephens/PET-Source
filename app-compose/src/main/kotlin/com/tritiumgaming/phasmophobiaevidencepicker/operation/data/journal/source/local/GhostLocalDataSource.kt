package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.source.local

import android.content.Context
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto.GhostDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.source.GhostDataSource

class GhostLocalDataSource(
    private val applicationContext: Context
): GhostDataSource {

    private val ghostResource: List<GhostResource>
        get() = listOf(
            GhostResource(
                id = R.string.ghost_id_banshee,
                name = R.string.ghost_type_banshee,
                info = R.string.ghost_info_banshee,
                strengthData = R.string.ghost_strengths_banshee,
                weaknessData = R.string.ghost_weaknesses_banshee,
                huntData = R.string.ghost_huntingdata_banshee,
                normalEvidence = listOf(
                    R.string.evidence_id_dots,
                    R.string.evidence_id_ultraviolet,
                    R.string.evidence_id_orbs,
                ),
                strictEvidence = emptyList()
            ),
            GhostResource(
                id = R.string.ghost_id_demon,
                name = R.string.ghost_type_demon,
                info = R.string.ghost_info_demon,
                strengthData = R.string.ghost_strengths_demon,
                weaknessData = R.string.ghost_weaknesses_demon,
                huntData = R.string.ghost_huntingdata_demon,
                normalEvidence = listOf(
                    R.string.evidence_id_ultraviolet,
                    R.string.evidence_id_temperatures,
                    R.string.evidence_id_book,
                ),
                strictEvidence = emptyList()
            ),
            GhostResource(
                id = R.string.ghost_id_deogen,
                name = R.string.ghost_type_deogen,
                info = R.string.ghost_info_deogen,
                strengthData = R.string.ghost_strengths_deogen,
                weaknessData = R.string.ghost_weaknesses_deogen,
                huntData = R.string.ghost_huntingdata_deogen,
                normalEvidence = listOf(
                    R.string.evidence_id_book,
                    R.string.evidence_id_box,
                    R.string.evidence_id_dots,
                ),
                strictEvidence = listOf(
                    R.string.evidence_id_box
                )
            ),
            GhostResource(
                id = R.string.ghost_id_goryo,
                name = R.string.ghost_type_goryo,
                info = R.string.ghost_info_goryo,
                strengthData = R.string.ghost_strengths_goryo,
                weaknessData = R.string.ghost_weaknesses_goryo,
                huntData = R.string.ghost_huntingdata_goryo,
                normalEvidence = listOf(
                    R.string.evidence_id_dots,
                    R.string.evidence_id_emf,
                    R.string.evidence_id_ultraviolet,
                ),
                strictEvidence = listOf(
                    R.string.evidence_id_dots
                )
            ),GhostResource(
                id = R.string.ghost_id_hantu,
                name = R.string.ghost_type_hantu,
                info = R.string.ghost_info_hantu,
                strengthData = R.string.ghost_strengths_hantu,
                weaknessData = R.string.ghost_weaknesses_hantu,
                huntData = R.string.ghost_huntingdata_hantu,
                normalEvidence = listOf(
                    R.string.evidence_id_ultraviolet,
                    R.string.evidence_id_temperatures,
                    R.string.evidence_id_orbs,
                ),
                strictEvidence = listOf(
                    R.string.evidence_id_temperatures
                )
            ),
            GhostResource(
                id = R.string.ghost_id_jinn,
                name = R.string.ghost_type_jinn,
                info = R.string.ghost_info_jinn,
                strengthData = R.string.ghost_strengths_jinn,
                weaknessData = R.string.ghost_weaknesses_jinn,
                huntData = R.string.ghost_huntingdata_jinn,
                normalEvidence = listOf(
                    R.string.evidence_id_emf,
                    R.string.evidence_id_ultraviolet,
                    R.string.evidence_id_temperatures,
                ),
                strictEvidence = emptyList()
            ),GhostResource(
                id = R.string.ghost_id_mare,
                name = R.string.ghost_type_mare,
                info = R.string.ghost_info_mare,
                strengthData = R.string.ghost_strengths_mare,
                weaknessData = R.string.ghost_weaknesses_mare,
                huntData = R.string.ghost_huntingdata_mare,
                normalEvidence = listOf(
                    R.string.evidence_id_orbs,
                    R.string.evidence_id_book,
                    R.string.evidence_id_box,
                ),
                strictEvidence = emptyList()
            ),
            GhostResource(
                id = R.string.ghost_id_moroi,
                name = R.string.ghost_type_moroi,
                info = R.string.ghost_info_moroi,
                strengthData = R.string.ghost_strengths_moroi,
                weaknessData = R.string.ghost_weaknesses_moroi,
                huntData = R.string.ghost_huntingdata_moroi,
                normalEvidence = listOf(
                    R.string.evidence_id_box,
                    R.string.evidence_id_temperatures,
                    R.string.evidence_id_book,
                ),
                strictEvidence = listOf(
                    R.string.evidence_id_box
                )
            ),GhostResource(
                id = R.string.ghost_id_myling,
                name = R.string.ghost_type_myling,
                info = R.string.ghost_info_myling,
                strengthData = R.string.ghost_strengths_myling,
                weaknessData = R.string.ghost_weaknesses_myling,
                huntData = R.string.ghost_huntingdata_myling,
                normalEvidence = listOf(
                    R.string.evidence_id_emf,
                    R.string.evidence_id_ultraviolet,
                    R.string.evidence_id_book,
                ),
                strictEvidence = emptyList()
            ),
            GhostResource(
                id = R.string.ghost_id_obake,
                name = R.string.ghost_type_obake,
                info = R.string.ghost_info_obake,
                strengthData = R.string.ghost_strengths_obake,
                weaknessData = R.string.ghost_weaknesses_obake,
                huntData = R.string.ghost_huntingdata_obake,
                normalEvidence = listOf(
                    R.string.evidence_id_emf,
                    R.string.evidence_id_ultraviolet,
                    R.string.evidence_id_orbs,
                ),
                strictEvidence = listOf(
                    R.string.evidence_id_ultraviolet
                )
            ),GhostResource(
                id = R.string.ghost_id_oni,
                name = R.string.ghost_type_oni,
                info = R.string.ghost_info_oni,
                strengthData = R.string.ghost_strengths_oni,
                weaknessData = R.string.ghost_weaknesses_oni,
                huntData = R.string.ghost_huntingdata_oni,
                normalEvidence = listOf(
                    R.string.evidence_id_dots,
                    R.string.evidence_id_emf,
                    R.string.evidence_id_temperatures,
                ),
                strictEvidence = emptyList()
            ),
            GhostResource(
                id = R.string.ghost_id_onryo,
                name = R.string.ghost_type_onryo,
                info = R.string.ghost_info_onryo,
                strengthData = R.string.ghost_strengths_onryo,
                weaknessData = R.string.ghost_weaknesses_onryo,
                huntData = R.string.ghost_huntingdata_onryo,
                normalEvidence = listOf(
                    R.string.evidence_id_temperatures,
                    R.string.evidence_id_orbs,
                    R.string.evidence_id_box,
                ),
                strictEvidence = emptyList()
            ),GhostResource(
                id = R.string.ghost_id_phantom,
                name = R.string.ghost_type_phantom,
                info = R.string.ghost_info_phantom,
                strengthData = R.string.ghost_strengths_phantom,
                weaknessData = R.string.ghost_weaknesses_phantom,
                huntData = R.string.ghost_huntingdata_phantom,
                normalEvidence = listOf(
                    R.string.evidence_id_dots,
                    R.string.evidence_id_ultraviolet,
                    R.string.evidence_id_box,
                ),
                strictEvidence = emptyList()
            ),
            GhostResource(
                id = R.string.ghost_id_poltergeist,
                name = R.string.ghost_type_poltergeist,
                info = R.string.ghost_info_poltergeist,
                strengthData = R.string.ghost_strengths_poltergeist,
                weaknessData = R.string.ghost_weaknesses_poltergeist,
                huntData = R.string.ghost_huntingdata_poltergeist,
                normalEvidence = listOf(
                    R.string.evidence_id_ultraviolet,
                    R.string.evidence_id_book,
                    R.string.evidence_id_box,
                ),
                strictEvidence = emptyList()
            ),GhostResource(
                id = R.string.ghost_id_raiju,
                name = R.string.ghost_type_raiju,
                info = R.string.ghost_info_raiju,
                strengthData = R.string.ghost_strengths_raiju,
                weaknessData = R.string.ghost_weaknesses_raiju,
                huntData = R.string.ghost_huntingdata_raiju,
                normalEvidence = listOf(
                    R.string.evidence_id_dots,
                    R.string.evidence_id_emf,
                    R.string.evidence_id_orbs,
                ),
                strictEvidence = emptyList()
            ),
            GhostResource(
                id = R.string.ghost_id_revenant,
                name = R.string.ghost_type_revenant,
                info = R.string.ghost_info_revenant,
                strengthData = R.string.ghost_strengths_revenant,
                weaknessData = R.string.ghost_weaknesses_revenant,
                huntData = R.string.ghost_huntingdata_revenant,
                normalEvidence = listOf(
                    R.string.evidence_id_temperatures,
                    R.string.evidence_id_orbs,
                    R.string.evidence_id_book,
                ),
                strictEvidence = emptyList()
            ),GhostResource(
                id = R.string.ghost_id_shade,
                name = R.string.ghost_type_shade,
                info = R.string.ghost_info_shade,
                strengthData = R.string.ghost_strengths_shade,
                weaknessData = R.string.ghost_weaknesses_shade,
                huntData = R.string.ghost_huntingdata_shade,
                normalEvidence = listOf(
                    R.string.evidence_id_emf,
                    R.string.evidence_id_temperatures,
                    R.string.evidence_id_book,
                ),
                strictEvidence = emptyList()
            ),
            GhostResource(
                id = R.string.ghost_id_spirit,
                name = R.string.ghost_type_spirit,
                info = R.string.ghost_info_spirit,
                strengthData = R.string.ghost_strengths_spirit,
                weaknessData = R.string.ghost_weaknesses_spirit,
                huntData = R.string.ghost_huntingdata_spirit,
                normalEvidence = listOf(
                    R.string.evidence_id_emf,
                    R.string.evidence_id_box,
                    R.string.evidence_id_book,
                ),
                strictEvidence = emptyList()
            ),GhostResource(
                id = R.string.ghost_id_thaye,
                name = R.string.ghost_type_thaye,
                info = R.string.ghost_info_thaye,
                strengthData = R.string.ghost_strengths_thaye,
                weaknessData = R.string.ghost_weaknesses_thaye,
                huntData = R.string.ghost_huntingdata_thaye,
                normalEvidence = listOf(
                    R.string.evidence_id_book,
                    R.string.evidence_id_dots,
                    R.string.evidence_id_orbs,
                ),
                strictEvidence = emptyList()
            ),
            GhostResource(
                id = R.string.ghost_id_thetwins,
                name = R.string.ghost_type_thetwins,
                info = R.string.ghost_info_thetwins,
                strengthData = R.string.ghost_strengths_thetwins,
                weaknessData = R.string.ghost_weaknesses_thetwins,
                huntData = R.string.ghost_huntingdata_thetwins,
                normalEvidence = listOf(
                    R.string.evidence_id_emf,
                    R.string.evidence_id_temperatures,
                    R.string.evidence_id_box,
                ),
                strictEvidence = emptyList()
            ),GhostResource(
                id = R.string.ghost_id_themimic,
                name = R.string.ghost_type_themimic,
                info = R.string.ghost_info_themimic,
                strengthData = R.string.ghost_strengths_themimic,
                weaknessData = R.string.ghost_weaknesses_themimic,
                huntData = R.string.ghost_huntingdata_themimic,
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
            GhostResource(
                id = R.string.ghost_id_wraith,
                name = R.string.ghost_type_wraith,
                info = R.string.ghost_info_wraith,
                strengthData = R.string.ghost_strengths_wraith,
                weaknessData = R.string.ghost_weaknesses_wraith,
                huntData = R.string.ghost_huntingdata_wraith,
                normalEvidence = listOf(
                    R.string.evidence_id_emf,
                    R.string.evidence_id_box,
                    R.string.evidence_id_dots,
                ),
                strictEvidence = emptyList()
            ),GhostResource(
                id = R.string.ghost_id_yokai,
                name = R.string.ghost_type_yokai,
                info = R.string.ghost_info_yokai,
                strengthData = R.string.ghost_strengths_yokai,
                weaknessData = R.string.ghost_weaknesses_yokai,
                huntData = R.string.ghost_huntingdata_yokai,
                normalEvidence = listOf(
                    R.string.evidence_id_dots,
                    R.string.evidence_id_orbs,
                    R.string.evidence_id_box,
                ),
                strictEvidence = emptyList()
            ),
            GhostResource(
                id = R.string.ghost_id_yurei,
                name = R.string.ghost_type_yurei,
                info = R.string.ghost_info_yurei,
                strengthData = R.string.ghost_strengths_yurei,
                weaknessData = R.string.ghost_weaknesses_yurei,
                huntData = R.string.ghost_huntingdata_yurei,
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

        ghostResource.forEach { resDto ->
            result.add(resDto.toGhostDto())
        }
        return Result.success(emptyList())

    }

    private data class GhostResource(
        val id: Int,
        @StringRes val name: Int,
        @StringRes val info: Int,
        @StringRes val strengthData: Int,
        @StringRes val weaknessData: Int,
        @StringRes val huntData: Int,
        val normalEvidence: List<Int>,
        val strictEvidence: List<Int>,
    )

    private fun List<GhostResource>.toGhostDto() = map { it.toGhostDto() }

    private fun GhostResource.toGhostDto() = GhostDto(
        id = applicationContext.getString(id),
        name = name,
        normalEvidence = normalEvidence.map { applicationContext.getString(it) },
        strictEvidence = strictEvidence.map { applicationContext.getString(it) },
        info = info,
        strengthData = strengthData,
        weaknessData = weaknessData,
        huntData = huntData
    )

}