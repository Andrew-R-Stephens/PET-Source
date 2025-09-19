package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.source.local

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto.EvidenceDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto.EvidenceTierDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.source.EvidenceDataSource
import com.tritiumgaming.shared.operation.domain.journal.mapper.EvidenceResources.EvidenceAnimation
import com.tritiumgaming.shared.operation.domain.journal.mapper.EvidenceResources.EvidenceCost
import com.tritiumgaming.shared.operation.domain.journal.mapper.EvidenceResources.EvidenceDescription
import com.tritiumgaming.shared.operation.domain.journal.mapper.EvidenceResources.EvidenceIcon
import com.tritiumgaming.shared.operation.domain.journal.mapper.EvidenceResources.EvidenceTierAnimation
import com.tritiumgaming.shared.operation.domain.journal.mapper.EvidenceResources.EvidenceTierDescription
import com.tritiumgaming.shared.operation.domain.journal.mapper.EvidenceResources.EvidenceTierRequiredLevel
import com.tritiumgaming.shared.operation.domain.journal.mapper.EvidenceResources.EvidenceTitle

class EvidenceLocalDataSource(
    private val applicationContext: Context
): EvidenceDataSource {

    private val evidenceResource: List<EvidenceResourceDto>
        get() = listOf(
            EvidenceResourceDto(
                id = R.string.evidence_id_dots,
                name = EvidenceTitle.DOTS,
                icon = EvidenceIcon.DOTS,
                buyCost = EvidenceCost.DOTS,
                defaultAnimation = EvidenceAnimation.DOTS,
                defaultDescription = EvidenceDescription.DOTS,
                tiers = listOf(
                    EvidenceResourceTierDto(
                        description = EvidenceTierDescription.DOTS_1,
                        animation = EvidenceTierAnimation.DOTS_1,
                        levelRequirement = EvidenceTierRequiredLevel.DOTS_1
                    ),
                    EvidenceResourceTierDto(
                        description = EvidenceTierDescription.DOTS_2,
                        animation = EvidenceTierAnimation.DOTS_2,
                        levelRequirement = EvidenceTierRequiredLevel.DOTS_2
                    ),
                    EvidenceResourceTierDto(
                        description = EvidenceTierDescription.DOTS_3,
                        animation = EvidenceTierAnimation.DOTS_3,
                        levelRequirement = EvidenceTierRequiredLevel.DOTS_3
                    ),
                )
            ),
            EvidenceResourceDto(
                id = R.string.evidence_id_emf,
                name = EvidenceTitle.EMF_5,
                icon = EvidenceIcon.EMF_5,
                buyCost = EvidenceCost.EMF_5,
                defaultAnimation = EvidenceAnimation.EMF_5,
                defaultDescription = EvidenceDescription.EMF_5,
                tiers = listOf(
                    EvidenceResourceTierDto(
                        description = EvidenceTierDescription.EMF_5_1,
                        animation = EvidenceTierAnimation.EMF_5_1,
                        levelRequirement = EvidenceTierRequiredLevel.EMF_5_1
                    ),
                    EvidenceResourceTierDto(
                        description = EvidenceTierDescription.EMF_5_2,
                        animation = EvidenceTierAnimation.EMF_5_2,
                        levelRequirement = EvidenceTierRequiredLevel.EMF_5_2
                    ),
                    EvidenceResourceTierDto(
                        description = EvidenceTierDescription.EMF_5_3,
                        animation = EvidenceTierAnimation.EMF_5_3,
                        levelRequirement = EvidenceTierRequiredLevel.EMF_5_3
                    ),
                )
            ),
            EvidenceResourceDto(
                id = R.string.evidence_id_ultraviolet,
                name = EvidenceTitle.ULTRAVIOLET_LIGHT,
                icon = EvidenceIcon.ULTRAVIOLET_LIGHT,
                buyCost = EvidenceCost.ULTRAVIOLET_LIGHT,
                defaultAnimation = EvidenceAnimation.ULTRAVIOLET_LIGHT,
                defaultDescription = EvidenceDescription.ULTRAVIOLET_LIGHT,
                tiers = listOf(
                    EvidenceResourceTierDto(
                        description = EvidenceTierDescription.ULTRAVIOLET_LIGHT_1,
                        animation = EvidenceTierAnimation.ULTRAVIOLET_LIGHT_1,
                        levelRequirement = EvidenceTierRequiredLevel.ULTRAVIOLET_LIGHT_1
                    ),
                    EvidenceResourceTierDto(
                        description = EvidenceTierDescription.ULTRAVIOLET_LIGHT_2,
                        animation = EvidenceTierAnimation.ULTRAVIOLET_LIGHT_2,
                        levelRequirement = EvidenceTierRequiredLevel.ULTRAVIOLET_LIGHT_2
                    ),
                    EvidenceResourceTierDto(
                        description = EvidenceTierDescription.ULTRAVIOLET_LIGHT_3,
                        animation = EvidenceTierAnimation.ULTRAVIOLET_LIGHT_3,
                        levelRequirement = EvidenceTierRequiredLevel.ULTRAVIOLET_LIGHT_3
                    ),
                )
            ),
            EvidenceResourceDto(
                id = R.string.evidence_id_temperatures,
                name = EvidenceTitle.FREEZING_TEMPERATURE,
                icon = EvidenceIcon.FREEZING_TEMPERATURE,
                buyCost = EvidenceCost.FREEZING_TEMPERATURE,
                defaultAnimation = EvidenceAnimation.FREEZING_TEMPERATURE,
                defaultDescription = EvidenceDescription.FREEZING_TEMPERATURE,
                tiers = listOf(
                    EvidenceResourceTierDto(
                        description = EvidenceTierDescription.FREEZING_TEMPERATURE_1,
                        animation = EvidenceTierAnimation.FREEZING_TEMPERATURE_1,
                        levelRequirement = EvidenceTierRequiredLevel.FREEZING_TEMPERATURE_1
                    ),
                    EvidenceResourceTierDto(
                        description = EvidenceTierDescription.FREEZING_TEMPERATURE_2,
                        animation = EvidenceTierAnimation.FREEZING_TEMPERATURE_2,
                        levelRequirement = EvidenceTierRequiredLevel.FREEZING_TEMPERATURE_2
                    ),
                    EvidenceResourceTierDto(
                        description = EvidenceTierDescription.FREEZING_TEMPERATURE_3,
                        animation = EvidenceTierAnimation.FREEZING_TEMPERATURE_3,
                        levelRequirement = EvidenceTierRequiredLevel.FREEZING_TEMPERATURE_3
                    ),
                )
            ),
            EvidenceResourceDto(
                id = R.string.evidence_id_orbs,
                name = EvidenceTitle.GHOST_ORBS,
                icon = EvidenceIcon.GHOST_ORBS,
                buyCost = EvidenceCost.GHOST_ORBS,
                defaultAnimation = EvidenceAnimation.GHOST_ORBS,
                defaultDescription = EvidenceDescription.GHOST_ORBS,
                tiers = listOf(
                    EvidenceResourceTierDto(
                        description = EvidenceTierDescription.GHOST_ORBS_1,
                        animation = EvidenceTierAnimation.GHOST_ORBS_1,
                        levelRequirement = EvidenceTierRequiredLevel.GHOST_ORBS_1
                    ),
                    EvidenceResourceTierDto(
                        description = EvidenceTierDescription.GHOST_ORBS_2,
                        animation = EvidenceTierAnimation.GHOST_ORBS_2,
                        levelRequirement = EvidenceTierRequiredLevel.GHOST_ORBS_2
                    ),
                    EvidenceResourceTierDto(
                        description = EvidenceTierDescription.GHOST_ORBS_3,
                        animation = EvidenceTierAnimation.GHOST_ORBS_3,
                        levelRequirement = EvidenceTierRequiredLevel.GHOST_ORBS_3
                    ),
                )
            ),
            EvidenceResourceDto(
                id = R.string.evidence_id_book,
                name = EvidenceTitle.GHOST_WRITING,
                icon = EvidenceIcon.GHOST_WRITING,
                buyCost = EvidenceCost.GHOST_WRITING,
                defaultAnimation = EvidenceAnimation.GHOST_WRITING,
                defaultDescription = EvidenceDescription.GHOST_WRITING,
                tiers = listOf(
                    EvidenceResourceTierDto(
                        description = EvidenceTierDescription.GHOST_WRITING_1,
                        animation = EvidenceTierAnimation.GHOST_WRITING_1,
                        levelRequirement = EvidenceTierRequiredLevel.GHOST_WRITING_1
                    ),
                    EvidenceResourceTierDto(
                        description = EvidenceTierDescription.GHOST_WRITING_2,
                        animation = EvidenceTierAnimation.GHOST_WRITING_2,
                        levelRequirement = EvidenceTierRequiredLevel.GHOST_WRITING_2
                    ),
                    EvidenceResourceTierDto(
                        description = EvidenceTierDescription.GHOST_WRITING_3,
                        animation = EvidenceTierAnimation.GHOST_WRITING_3,
                        levelRequirement = EvidenceTierRequiredLevel.GHOST_WRITING_3
                    ),
                )
            ),
            EvidenceResourceDto(
                id = R.string.evidence_id_box,
                name = EvidenceTitle.SPIRIT_BOX,
                icon = EvidenceIcon.SPIRIT_BOX,
                buyCost = EvidenceCost.SPIRIT_BOX,
                defaultAnimation = EvidenceAnimation.SPIRIT_BOX,
                defaultDescription = EvidenceDescription.SPIRIT_BOX,
                tiers = listOf(
                    EvidenceResourceTierDto(
                        description = EvidenceTierDescription.SPIRIT_BOX_1,
                        animation = EvidenceTierAnimation.SPIRIT_BOX_1,
                        levelRequirement = EvidenceTierRequiredLevel.SPIRIT_BOX_1
                    ),
                    EvidenceResourceTierDto(
                        description = EvidenceTierDescription.SPIRIT_BOX_2,
                        animation = EvidenceTierAnimation.SPIRIT_BOX_2,
                        levelRequirement = EvidenceTierRequiredLevel.SPIRIT_BOX_2
                    ),
                    EvidenceResourceTierDto(
                        description = EvidenceTierDescription.SPIRIT_BOX_3,
                        animation = EvidenceTierAnimation.SPIRIT_BOX_3,
                        levelRequirement = EvidenceTierRequiredLevel.SPIRIT_BOX_3
                    ),
                )
            ),

        )

    override fun get(): Result<List<EvidenceDto>> =
        Result.success(evidenceResource.toLocal())

    private data class EvidenceResourceDto(
        val id: Int,
        val name: EvidenceTitle,
        val icon: EvidenceIcon,
        val buyCost: EvidenceCost,
        val defaultAnimation: EvidenceAnimation,
        val defaultDescription: EvidenceDescription,
        val tiers: List<EvidenceResourceTierDto> = emptyList()
    )

    private data class EvidenceResourceTierDto(
        val description: EvidenceTierDescription,
        val animation: EvidenceTierAnimation,
        val levelRequirement: EvidenceTierRequiredLevel
    )

    private fun List<EvidenceResourceDto>.toLocal() = map { it.toLocal() }

    private fun EvidenceResourceDto.toLocal(): EvidenceDto =
        EvidenceDto(
            id = applicationContext.getString(id),
            name = name,
            icon = icon,
            buyCost = buyCost,
            tiers = tiers.toLocal()
        )

    @JvmName("EvidenceResourceTierDtoToEvidenceTierDto")
    private fun EvidenceResourceTierDto.toLocal() =
        EvidenceTierDto(
            description = description,
            animation = animation,
            levelRequirement = levelRequirement
        )

    @JvmName("EvidenceResourceTierDtoListToEvidenceTierDtoList")
    private fun List<EvidenceResourceTierDto>.toLocal() = map { it.toLocal() }

}