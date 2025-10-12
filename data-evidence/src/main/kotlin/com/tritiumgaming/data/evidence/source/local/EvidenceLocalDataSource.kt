package com.tritiumgaming.data.evidence.source.local


import android.content.Context
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.data.evidence.dto.EvidenceDto
import com.tritiumgaming.data.evidence.dto.EvidenceTierDto
import com.tritiumgaming.data.evidence.source.EvidenceDataSource
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources

class EvidenceLocalDataSource(
    private val applicationContext: Context
): EvidenceDataSource {

    private val evidenceResource: List<EvidenceResourceDto>
        get() = listOf(
            EvidenceResourceDto(
                id = R.string.evidence_id_dots,
                name = EvidenceResources.EvidenceTitle.DOTS,
                icon = EvidenceResources.EvidenceIcon.DOTS,
                buyCost = EvidenceResources.EvidenceCost.DOTS,
                defaultAnimation = EvidenceResources.EvidenceAnimation.DOTS,
                defaultDescription = EvidenceResources.EvidenceDescription.DOTS,
                tiers = listOf(
                    EvidenceResourceTierDto(
                        description = EvidenceResources.EvidenceTierDescription.DOTS_1,
                        animation = EvidenceResources.EvidenceTierAnimation.DOTS_1,
                        levelRequirement = EvidenceResources.EvidenceTierRequiredLevel.DOTS_1
                    ),
                    EvidenceResourceTierDto(
                        description = EvidenceResources.EvidenceTierDescription.DOTS_2,
                        animation = EvidenceResources.EvidenceTierAnimation.DOTS_2,
                        levelRequirement = EvidenceResources.EvidenceTierRequiredLevel.DOTS_2
                    ),
                    EvidenceResourceTierDto(
                        description = EvidenceResources.EvidenceTierDescription.DOTS_3,
                        animation = EvidenceResources.EvidenceTierAnimation.DOTS_3,
                        levelRequirement = EvidenceResources.EvidenceTierRequiredLevel.DOTS_3
                    ),
                )
            ),
            EvidenceResourceDto(
                id = R.string.evidence_id_emf,
                name = EvidenceResources.EvidenceTitle.EMF_5,
                icon = EvidenceResources.EvidenceIcon.EMF_5,
                buyCost = EvidenceResources.EvidenceCost.EMF_5,
                defaultAnimation = EvidenceResources.EvidenceAnimation.EMF_5,
                defaultDescription = EvidenceResources.EvidenceDescription.EMF_5,
                tiers = listOf(
                    EvidenceResourceTierDto(
                        description = EvidenceResources.EvidenceTierDescription.EMF_5_1,
                        animation = EvidenceResources.EvidenceTierAnimation.EMF_5_1,
                        levelRequirement = EvidenceResources.EvidenceTierRequiredLevel.EMF_5_1
                    ),
                    EvidenceResourceTierDto(
                        description = EvidenceResources.EvidenceTierDescription.EMF_5_2,
                        animation = EvidenceResources.EvidenceTierAnimation.EMF_5_2,
                        levelRequirement = EvidenceResources.EvidenceTierRequiredLevel.EMF_5_2
                    ),
                    EvidenceResourceTierDto(
                        description = EvidenceResources.EvidenceTierDescription.EMF_5_3,
                        animation = EvidenceResources.EvidenceTierAnimation.EMF_5_3,
                        levelRequirement = EvidenceResources.EvidenceTierRequiredLevel.EMF_5_3
                    ),
                )
            ),
            EvidenceResourceDto(
                id = R.string.evidence_id_ultraviolet,
                name = EvidenceResources.EvidenceTitle.ULTRAVIOLET_LIGHT,
                icon = EvidenceResources.EvidenceIcon.ULTRAVIOLET_LIGHT,
                buyCost = EvidenceResources.EvidenceCost.ULTRAVIOLET_LIGHT,
                defaultAnimation = EvidenceResources.EvidenceAnimation.ULTRAVIOLET_LIGHT,
                defaultDescription = EvidenceResources.EvidenceDescription.ULTRAVIOLET_LIGHT,
                tiers = listOf(
                    EvidenceResourceTierDto(
                        description = EvidenceResources.EvidenceTierDescription.ULTRAVIOLET_LIGHT_1,
                        animation = EvidenceResources.EvidenceTierAnimation.ULTRAVIOLET_LIGHT_1,
                        levelRequirement = EvidenceResources.EvidenceTierRequiredLevel.ULTRAVIOLET_LIGHT_1
                    ),
                    EvidenceResourceTierDto(
                        description = EvidenceResources.EvidenceTierDescription.ULTRAVIOLET_LIGHT_2,
                        animation = EvidenceResources.EvidenceTierAnimation.ULTRAVIOLET_LIGHT_2,
                        levelRequirement = EvidenceResources.EvidenceTierRequiredLevel.ULTRAVIOLET_LIGHT_2
                    ),
                    EvidenceResourceTierDto(
                        description = EvidenceResources.EvidenceTierDescription.ULTRAVIOLET_LIGHT_3,
                        animation = EvidenceResources.EvidenceTierAnimation.ULTRAVIOLET_LIGHT_3,
                        levelRequirement = EvidenceResources.EvidenceTierRequiredLevel.ULTRAVIOLET_LIGHT_3
                    ),
                )
            ),
            EvidenceResourceDto(
                id = R.string.evidence_id_temperatures,
                name = EvidenceResources.EvidenceTitle.FREEZING_TEMPERATURE,
                icon = EvidenceResources.EvidenceIcon.FREEZING_TEMPERATURE,
                buyCost = EvidenceResources.EvidenceCost.FREEZING_TEMPERATURE,
                defaultAnimation = EvidenceResources.EvidenceAnimation.FREEZING_TEMPERATURE,
                defaultDescription = EvidenceResources.EvidenceDescription.FREEZING_TEMPERATURE,
                tiers = listOf(
                    EvidenceResourceTierDto(
                        description = EvidenceResources.EvidenceTierDescription.FREEZING_TEMPERATURE_1,
                        animation = EvidenceResources.EvidenceTierAnimation.FREEZING_TEMPERATURE_1,
                        levelRequirement = EvidenceResources.EvidenceTierRequiredLevel.FREEZING_TEMPERATURE_1
                    ),
                    EvidenceResourceTierDto(
                        description = EvidenceResources.EvidenceTierDescription.FREEZING_TEMPERATURE_2,
                        animation = EvidenceResources.EvidenceTierAnimation.FREEZING_TEMPERATURE_2,
                        levelRequirement = EvidenceResources.EvidenceTierRequiredLevel.FREEZING_TEMPERATURE_2
                    ),
                    EvidenceResourceTierDto(
                        description = EvidenceResources.EvidenceTierDescription.FREEZING_TEMPERATURE_3,
                        animation = EvidenceResources.EvidenceTierAnimation.FREEZING_TEMPERATURE_3,
                        levelRequirement = EvidenceResources.EvidenceTierRequiredLevel.FREEZING_TEMPERATURE_3
                    ),
                )
            ),
            EvidenceResourceDto(
                id = R.string.evidence_id_orbs,
                name = EvidenceResources.EvidenceTitle.GHOST_ORBS,
                icon = EvidenceResources.EvidenceIcon.GHOST_ORBS,
                buyCost = EvidenceResources.EvidenceCost.GHOST_ORBS,
                defaultAnimation = EvidenceResources.EvidenceAnimation.GHOST_ORBS,
                defaultDescription = EvidenceResources.EvidenceDescription.GHOST_ORBS,
                tiers = listOf(
                    EvidenceResourceTierDto(
                        description = EvidenceResources.EvidenceTierDescription.GHOST_ORBS_1,
                        animation = EvidenceResources.EvidenceTierAnimation.GHOST_ORBS_1,
                        levelRequirement = EvidenceResources.EvidenceTierRequiredLevel.GHOST_ORBS_1
                    ),
                    EvidenceResourceTierDto(
                        description = EvidenceResources.EvidenceTierDescription.GHOST_ORBS_2,
                        animation = EvidenceResources.EvidenceTierAnimation.GHOST_ORBS_2,
                        levelRequirement = EvidenceResources.EvidenceTierRequiredLevel.GHOST_ORBS_2
                    ),
                    EvidenceResourceTierDto(
                        description = EvidenceResources.EvidenceTierDescription.GHOST_ORBS_3,
                        animation = EvidenceResources.EvidenceTierAnimation.GHOST_ORBS_3,
                        levelRequirement = EvidenceResources.EvidenceTierRequiredLevel.GHOST_ORBS_3
                    ),
                )
            ),
            EvidenceResourceDto(
                id = R.string.evidence_id_book,
                name = EvidenceResources.EvidenceTitle.GHOST_WRITING,
                icon = EvidenceResources.EvidenceIcon.GHOST_WRITING,
                buyCost = EvidenceResources.EvidenceCost.GHOST_WRITING,
                defaultAnimation = EvidenceResources.EvidenceAnimation.GHOST_WRITING,
                defaultDescription = EvidenceResources.EvidenceDescription.GHOST_WRITING,
                tiers = listOf(
                    EvidenceResourceTierDto(
                        description = EvidenceResources.EvidenceTierDescription.GHOST_WRITING_1,
                        animation = EvidenceResources.EvidenceTierAnimation.GHOST_WRITING_1,
                        levelRequirement = EvidenceResources.EvidenceTierRequiredLevel.GHOST_WRITING_1
                    ),
                    EvidenceResourceTierDto(
                        description = EvidenceResources.EvidenceTierDescription.GHOST_WRITING_2,
                        animation = EvidenceResources.EvidenceTierAnimation.GHOST_WRITING_2,
                        levelRequirement = EvidenceResources.EvidenceTierRequiredLevel.GHOST_WRITING_2
                    ),
                    EvidenceResourceTierDto(
                        description = EvidenceResources.EvidenceTierDescription.GHOST_WRITING_3,
                        animation = EvidenceResources.EvidenceTierAnimation.GHOST_WRITING_3,
                        levelRequirement = EvidenceResources.EvidenceTierRequiredLevel.GHOST_WRITING_3
                    ),
                )
            ),
            EvidenceResourceDto(
                id = R.string.evidence_id_box,
                name = EvidenceResources.EvidenceTitle.SPIRIT_BOX,
                icon = EvidenceResources.EvidenceIcon.SPIRIT_BOX,
                buyCost = EvidenceResources.EvidenceCost.SPIRIT_BOX,
                defaultAnimation = EvidenceResources.EvidenceAnimation.SPIRIT_BOX,
                defaultDescription = EvidenceResources.EvidenceDescription.SPIRIT_BOX,
                tiers = listOf(
                    EvidenceResourceTierDto(
                        description = EvidenceResources.EvidenceTierDescription.SPIRIT_BOX_1,
                        animation = EvidenceResources.EvidenceTierAnimation.SPIRIT_BOX_1,
                        levelRequirement = EvidenceResources.EvidenceTierRequiredLevel.SPIRIT_BOX_1
                    ),
                    EvidenceResourceTierDto(
                        description = EvidenceResources.EvidenceTierDescription.SPIRIT_BOX_2,
                        animation = EvidenceResources.EvidenceTierAnimation.SPIRIT_BOX_2,
                        levelRequirement = EvidenceResources.EvidenceTierRequiredLevel.SPIRIT_BOX_2
                    ),
                    EvidenceResourceTierDto(
                        description = EvidenceResources.EvidenceTierDescription.SPIRIT_BOX_3,
                        animation = EvidenceResources.EvidenceTierAnimation.SPIRIT_BOX_3,
                        levelRequirement = EvidenceResources.EvidenceTierRequiredLevel.SPIRIT_BOX_3
                    ),
                )
            ),

            )

    override fun get(): Result<List<EvidenceDto>> =
        Result.success(evidenceResource.toLocal())

    private data class EvidenceResourceDto(
        val id: Int,
        val name: EvidenceResources.EvidenceTitle,
        val icon: EvidenceResources.EvidenceIcon,
        val buyCost: EvidenceResources.EvidenceCost,
        val defaultAnimation: EvidenceResources.EvidenceAnimation,
        val defaultDescription: EvidenceResources.EvidenceDescription,
        val tiers: List<EvidenceResourceTierDto> = emptyList()
    )

    private data class EvidenceResourceTierDto(
        val description: EvidenceResources.EvidenceTierDescription,
        val animation: EvidenceResources.EvidenceTierAnimation,
        val levelRequirement: EvidenceResources.EvidenceTierRequiredLevel
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