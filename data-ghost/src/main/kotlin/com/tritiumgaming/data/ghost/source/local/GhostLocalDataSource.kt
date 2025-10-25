package com.tritiumgaming.data.ghost.source.local

import com.tritiumgaming.data.ghost.dto.GhostDto
import com.tritiumgaming.data.ghost.source.GhostDataSource
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceIdentifier
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostDescription
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostHuntInfo
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostIcon
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostIdentifier
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostStrength
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostTitle
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostWeakness

class GhostLocalDataSource: GhostDataSource {

    private val ghostResourceDto: List<GhostResourceDto>
        get() = listOf(
            GhostResourceDto(
                id = GhostIdentifier.BANSHEE,
                name = GhostTitle.BANSHEE,
                icon = GhostIcon.BANSHEE,
                info = GhostDescription.BANSHEE,
                strengthData = GhostStrength.BANSHEE,
                weaknessData = GhostWeakness.BANSHEE,
                huntData = GhostHuntInfo.BANSHEE,
                normalEvidence = listOf(
                    EvidenceIdentifier.DOTS,
                    EvidenceIdentifier.ULTRAVIOLET_LIGHT,
                    EvidenceIdentifier.GHOST_ORBS,
                ),
                strictEvidence = emptyList()
            ),
            GhostResourceDto(
                id = GhostIdentifier.DEMON,
                name = GhostTitle.DEMON,
                icon = GhostIcon.DEMON,
                info = GhostDescription.DEMON,
                strengthData = GhostStrength.DEMON,
                weaknessData = GhostWeakness.DEMON,
                huntData = GhostHuntInfo.DEMON,
                normalEvidence = listOf(
                    EvidenceIdentifier.ULTRAVIOLET_LIGHT,
                    EvidenceIdentifier.FREEZING_TEMPERATURE,
                    EvidenceIdentifier.GHOST_WRITING,
                ),
                strictEvidence = emptyList()
            ),
            GhostResourceDto(
                id = GhostIdentifier.DEOGEN,
                name = GhostTitle.DEOGEN,
                icon = GhostIcon.DEOGEN,
                info = GhostDescription.DEOGEN,
                strengthData = GhostStrength.DEOGEN,
                weaknessData = GhostWeakness.DEOGEN,
                huntData = GhostHuntInfo.DEOGEN,
                normalEvidence = listOf(
                    EvidenceIdentifier.GHOST_WRITING,
                    EvidenceIdentifier.SPIRIT_BOX,
                    EvidenceIdentifier.DOTS,
                ),
                strictEvidence = listOf(
                    EvidenceIdentifier.SPIRIT_BOX
                )
            ),
            GhostResourceDto(
                id = GhostIdentifier.GORYO,
                name = GhostTitle.GORYO,
                icon = GhostIcon.GORYO,
                info = GhostDescription.GORYO,
                strengthData = GhostStrength.GORYO,
                weaknessData = GhostWeakness.GORYO,
                huntData = GhostHuntInfo.GORYO,
                normalEvidence = listOf(
                    EvidenceIdentifier.DOTS,
                    EvidenceIdentifier.EMF_5,
                    EvidenceIdentifier.ULTRAVIOLET_LIGHT,
                ),
                strictEvidence = listOf(
                    EvidenceIdentifier.DOTS
                )
            ),GhostResourceDto(
                id = GhostIdentifier.HANTU,
                name = GhostTitle.HANTU,
                icon = GhostIcon.HANTU,
                info = GhostDescription.HANTU,
                strengthData = GhostStrength.HANTU,
                weaknessData = GhostWeakness.HANTU,
                huntData = GhostHuntInfo.HANTU,
                normalEvidence = listOf(
                    EvidenceIdentifier.ULTRAVIOLET_LIGHT,
                    EvidenceIdentifier.FREEZING_TEMPERATURE,
                    EvidenceIdentifier.GHOST_ORBS,
                ),
                strictEvidence = listOf(
                    EvidenceIdentifier.FREEZING_TEMPERATURE
                )
            ),
            GhostResourceDto(
                id = GhostIdentifier.JINN,
                name = GhostTitle.JINN,
                icon = GhostIcon.JINN,
                info = GhostDescription.JINN,
                strengthData = GhostStrength.JINN,
                weaknessData = GhostWeakness.JINN,
                huntData = GhostHuntInfo.JINN,
                normalEvidence = listOf(
                    EvidenceIdentifier.EMF_5,
                    EvidenceIdentifier.ULTRAVIOLET_LIGHT,
                    EvidenceIdentifier.FREEZING_TEMPERATURE,
                ),
                strictEvidence = emptyList()
            ),GhostResourceDto(
                id = GhostIdentifier.MARE,
                name = GhostTitle.MARE,
                icon = GhostIcon.MARE,
                info = GhostDescription.MARE,
                strengthData = GhostStrength.MARE,
                weaknessData = GhostWeakness.MARE,
                huntData = GhostHuntInfo.MARE,
                normalEvidence = listOf(
                    EvidenceIdentifier.GHOST_ORBS,
                    EvidenceIdentifier.GHOST_WRITING,
                    EvidenceIdentifier.SPIRIT_BOX,
                ),
                strictEvidence = emptyList()
            ),
            GhostResourceDto(
                id = GhostIdentifier.MOROI,
                name = GhostTitle.MOROI,
                icon = GhostIcon.MOROI,
                info = GhostDescription.MOROI,
                strengthData = GhostStrength.MOROI,
                weaknessData = GhostWeakness.MOROI,
                huntData = GhostHuntInfo.MOROI,
                normalEvidence = listOf(
                    EvidenceIdentifier.SPIRIT_BOX,
                    EvidenceIdentifier.FREEZING_TEMPERATURE,
                    EvidenceIdentifier.GHOST_WRITING,
                ),
                strictEvidence = listOf(
                    EvidenceIdentifier.SPIRIT_BOX
                )
            ),GhostResourceDto(
                id = GhostIdentifier.MYLING,
                name = GhostTitle.MYLING,
                icon = GhostIcon.MYLING,
                info = GhostDescription.MYLING,
                strengthData = GhostStrength.MYLING,
                weaknessData = GhostWeakness.MYLING,
                huntData = GhostHuntInfo.MYLING,
                normalEvidence = listOf(
                    EvidenceIdentifier.EMF_5,
                    EvidenceIdentifier.ULTRAVIOLET_LIGHT,
                    EvidenceIdentifier.GHOST_WRITING,
                ),
                strictEvidence = emptyList()
            ),
            GhostResourceDto(
                id = GhostIdentifier.OBAKE,
                name = GhostTitle.OBAKE,
                icon = GhostIcon.OBAKE,
                info = GhostDescription.OBAKE,
                strengthData = GhostStrength.OBAKE,
                weaknessData = GhostWeakness.OBAKE,
                huntData = GhostHuntInfo.OBAKE,
                normalEvidence = listOf(
                    EvidenceIdentifier.EMF_5,
                    EvidenceIdentifier.ULTRAVIOLET_LIGHT,
                    EvidenceIdentifier.GHOST_ORBS,
                ),
                strictEvidence = listOf(
                    EvidenceIdentifier.ULTRAVIOLET_LIGHT
                )
            ),GhostResourceDto(
                id = GhostIdentifier.ONI,
                name = GhostTitle.ONI,
                icon = GhostIcon.ONI,
                info = GhostDescription.ONI,
                strengthData = GhostStrength.ONI,
                weaknessData = GhostWeakness.ONI,
                huntData = GhostHuntInfo.ONI,
                normalEvidence = listOf(
                    EvidenceIdentifier.DOTS,
                    EvidenceIdentifier.EMF_5,
                    EvidenceIdentifier.FREEZING_TEMPERATURE,
                ),
                strictEvidence = emptyList()
            ),
            GhostResourceDto(
                id = GhostIdentifier.ONRYO,
                name = GhostTitle.ONRYO,
                icon = GhostIcon.ONRYO,
                info = GhostDescription.ONRYO,
                strengthData = GhostStrength.ONRYO,
                weaknessData = GhostWeakness.ONRYO,
                huntData = GhostHuntInfo.ONRYO,
                normalEvidence = listOf(
                    EvidenceIdentifier.FREEZING_TEMPERATURE,
                    EvidenceIdentifier.GHOST_ORBS,
                    EvidenceIdentifier.SPIRIT_BOX,
                ),
                strictEvidence = emptyList()
            ),GhostResourceDto(
                id = GhostIdentifier.PHANTOM,
                name = GhostTitle.PHANTOM,
                icon = GhostIcon.PHANTOM,
                info = GhostDescription.PHANTOM,
                strengthData = GhostStrength.PHANTOM,
                weaknessData = GhostWeakness.PHANTOM,
                huntData = GhostHuntInfo.PHANTOM,
                normalEvidence = listOf(
                    EvidenceIdentifier.DOTS,
                    EvidenceIdentifier.ULTRAVIOLET_LIGHT,
                    EvidenceIdentifier.SPIRIT_BOX,
                ),
                strictEvidence = emptyList()
            ),
            GhostResourceDto(
                id = GhostIdentifier.POLTERGEIST,
                name = GhostTitle.POLTERGEIST,
                icon = GhostIcon.POLTERGEIST,
                info = GhostDescription.POLTERGEIST,
                strengthData = GhostStrength.POLTERGEIST,
                weaknessData = GhostWeakness.POLTERGEIST,
                huntData = GhostHuntInfo.POLTERGEIST,
                normalEvidence = listOf(
                    EvidenceIdentifier.ULTRAVIOLET_LIGHT,
                    EvidenceIdentifier.GHOST_WRITING,
                    EvidenceIdentifier.SPIRIT_BOX,
                ),
                strictEvidence = emptyList()
            ),GhostResourceDto(
                id = GhostIdentifier.RAIJU,
                name = GhostTitle.RAIJU,
                icon = GhostIcon.RAIJU,
                info = GhostDescription.RAIJU,
                strengthData = GhostStrength.RAIJU,
                weaknessData = GhostWeakness.RAIJU,
                huntData = GhostHuntInfo.RAIJU,
                normalEvidence = listOf(
                    EvidenceIdentifier.DOTS,
                    EvidenceIdentifier.EMF_5,
                    EvidenceIdentifier.GHOST_ORBS,
                ),
                strictEvidence = emptyList()
            ),
            GhostResourceDto(
                id = GhostIdentifier.REVENANT,
                name = GhostTitle.REVENANT,
                icon = GhostIcon.REVENANT,
                info = GhostDescription.REVENANT,
                strengthData = GhostStrength.REVENANT,
                weaknessData = GhostWeakness.REVENANT,
                huntData = GhostHuntInfo.REVENANT,
                normalEvidence = listOf(
                    EvidenceIdentifier.FREEZING_TEMPERATURE,
                    EvidenceIdentifier.GHOST_ORBS,
                    EvidenceIdentifier.GHOST_WRITING,
                ),
                strictEvidence = emptyList()
            ),GhostResourceDto(
                id = GhostIdentifier.SHADE,
                name = GhostTitle.SHADE,
                icon = GhostIcon.SHADE,
                info = GhostDescription.SHADE,
                strengthData = GhostStrength.SHADE,
                weaknessData = GhostWeakness.SHADE,
                huntData = GhostHuntInfo.SHADE,
                normalEvidence = listOf(
                    EvidenceIdentifier.EMF_5,
                    EvidenceIdentifier.FREEZING_TEMPERATURE,
                    EvidenceIdentifier.GHOST_WRITING,
                ),
                strictEvidence = emptyList()
            ),
            GhostResourceDto(
                id = GhostIdentifier.SPIRIT,
                name = GhostTitle.SPIRIT,
                icon = GhostIcon.SPIRIT,
                info = GhostDescription.SPIRIT,
                strengthData = GhostStrength.SPIRIT,
                weaknessData = GhostWeakness.SPIRIT,
                huntData = GhostHuntInfo.SPIRIT,
                normalEvidence = listOf(
                    EvidenceIdentifier.EMF_5,
                    EvidenceIdentifier.SPIRIT_BOX,
                    EvidenceIdentifier.GHOST_WRITING,
                ),
                strictEvidence = emptyList()
            ),GhostResourceDto(
                id = GhostIdentifier.THAYE,
                name = GhostTitle.THAYE,
                icon = GhostIcon.THAYE,
                info = GhostDescription.THAYE,
                strengthData = GhostStrength.THAYE,
                weaknessData = GhostWeakness.THAYE,
                huntData = GhostHuntInfo.THAYE,
                normalEvidence = listOf(
                    EvidenceIdentifier.GHOST_WRITING,
                    EvidenceIdentifier.DOTS,
                    EvidenceIdentifier.GHOST_ORBS,
                ),
                strictEvidence = emptyList()
            ),
            GhostResourceDto(
                id = GhostIdentifier.THE_TWINS,
                name = GhostTitle.THE_TWINS,
                icon = GhostIcon.THE_TWINS,
                info = GhostDescription.THE_TWINS,
                strengthData = GhostStrength.THE_TWINS,
                weaknessData = GhostWeakness.THE_TWINS,
                huntData = GhostHuntInfo.THE_TWINS,
                normalEvidence = listOf(
                    EvidenceIdentifier.EMF_5,
                    EvidenceIdentifier.FREEZING_TEMPERATURE,
                    EvidenceIdentifier.SPIRIT_BOX,
                ),
                strictEvidence = emptyList()
            ),GhostResourceDto(
                id = GhostIdentifier.THE_MIMIC,
                name = GhostTitle.THE_MIMIC,
                icon = GhostIcon.THE_MIMIC,
                info = GhostDescription.THE_MIMIC,
                strengthData = GhostStrength.THE_MIMIC,
                weaknessData = GhostWeakness.THE_MIMIC,
                huntData = GhostHuntInfo.THE_MIMIC,
                normalEvidence = listOf(
                    EvidenceIdentifier.ULTRAVIOLET_LIGHT,
                    EvidenceIdentifier.FREEZING_TEMPERATURE,
                    EvidenceIdentifier.SPIRIT_BOX,
                    EvidenceIdentifier.GHOST_ORBS,
                ),
                strictEvidence = listOf(
                    EvidenceIdentifier.GHOST_ORBS
                )
            ),
            GhostResourceDto(
                id = GhostIdentifier.WRAITH,
                name = GhostTitle.WRAITH,
                icon = GhostIcon.WRAITH,
                info = GhostDescription.WRAITH,
                strengthData = GhostStrength.WRAITH,
                weaknessData = GhostWeakness.WRAITH,
                huntData = GhostHuntInfo.WRAITH,
                normalEvidence = listOf(
                    EvidenceIdentifier.EMF_5,
                    EvidenceIdentifier.SPIRIT_BOX,
                    EvidenceIdentifier.DOTS,
                ),
                strictEvidence = emptyList()
            ),GhostResourceDto(
                id = GhostIdentifier.YOKAI,
                name = GhostTitle.YOKAI,
                icon = GhostIcon.YOKAI,
                info = GhostDescription.YOKAI,
                strengthData = GhostStrength.YOKAI,
                weaknessData = GhostWeakness.YOKAI,
                huntData = GhostHuntInfo.YOKAI,
                normalEvidence = listOf(
                    EvidenceIdentifier.DOTS,
                    EvidenceIdentifier.GHOST_ORBS,
                    EvidenceIdentifier.SPIRIT_BOX,
                ),
                strictEvidence = emptyList()
            ),
            GhostResourceDto(
                id = GhostIdentifier.YUREI,
                name = GhostTitle.YUREI,
                icon = GhostIcon.YUREI,
                info = GhostDescription.YUREI,
                strengthData = GhostStrength.YUREI,
                weaknessData = GhostWeakness.YUREI,
                huntData = GhostHuntInfo.YUREI,
                normalEvidence = listOf(
                    EvidenceIdentifier.DOTS,
                    EvidenceIdentifier.FREEZING_TEMPERATURE,
                    EvidenceIdentifier.GHOST_ORBS
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
        id = id,
        name = name,
        icon = icon,
        info = info,
        strengthData = strengthData,
        weaknessData = weaknessData,
        huntData = huntData,
        normalEvidence = normalEvidence.map { it },
        strictEvidence = strictEvidence.map { it }
    )

    private data class GhostResourceDto(
        val id: GhostIdentifier,
        val name: GhostTitle,
        val icon: GhostIcon,
        val info: GhostDescription,
        val strengthData: GhostStrength,
        val weaknessData: GhostWeakness,
        val huntData: GhostHuntInfo,
        val normalEvidence: List<EvidenceIdentifier>,
        val strictEvidence: List<EvidenceIdentifier>,
    )

}