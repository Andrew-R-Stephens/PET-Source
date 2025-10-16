package com.tritiumgaming.data.evidence.source.local


import com.tritiumgaming.data.evidence.dto.EvidenceDto
import com.tritiumgaming.data.evidence.dto.EvidenceTierDto
import com.tritiumgaming.data.evidence.source.EvidenceDataSource
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceAnimation
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceDescription
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceIcon
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceIdentifier
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceTierAnimation
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceTitle

class EvidenceLocalDataSource: EvidenceDataSource {

    private val evidenceResource: List<EvidenceResourceDto>
        get() = listOf(
            EvidenceResourceDto(
                id = EvidenceIdentifier.DOTS,
                name = EvidenceTitle.DOTS,
                description = EvidenceDescription.DOTS,
                icon = EvidenceIcon.DOTS,
                defaultAnimation = EvidenceAnimation.DOTS,
                defaultDescription = EvidenceDescription.DOTS,
                tiers = listOf(
                    EvidenceResourceTierDto(
                        animation = EvidenceTierAnimation.DOTS_1
                    ),
                    EvidenceResourceTierDto(
                        animation = EvidenceTierAnimation.DOTS_2
                    ),
                    EvidenceResourceTierDto(
                        animation = EvidenceTierAnimation.DOTS_3,
                    ),
                )
            ),
            EvidenceResourceDto(
                id = EvidenceIdentifier.EMF_5,
                name = EvidenceTitle.EMF_5,
                description = EvidenceDescription.EMF_5,
                icon = EvidenceIcon.EMF_5,
                defaultAnimation = EvidenceAnimation.EMF_5,
                defaultDescription = EvidenceDescription.EMF_5,
                tiers = listOf(
                    EvidenceResourceTierDto(
                        animation = EvidenceTierAnimation.EMF_5_1,
                    ),
                    EvidenceResourceTierDto(
                        animation = EvidenceTierAnimation.EMF_5_2,
                    ),
                    EvidenceResourceTierDto(
                        animation = EvidenceTierAnimation.EMF_5_3,
                    ),
                )
            ),
            EvidenceResourceDto(
                id = EvidenceIdentifier.ULTRAVIOLET_LIGHT,
                name = EvidenceTitle.ULTRAVIOLET_LIGHT,
                description = EvidenceDescription.ULTRAVIOLET_LIGHT,
                icon = EvidenceIcon.ULTRAVIOLET_LIGHT,
                defaultAnimation = EvidenceAnimation.ULTRAVIOLET_LIGHT,
                defaultDescription = EvidenceDescription.ULTRAVIOLET_LIGHT,
                tiers = listOf(
                    EvidenceResourceTierDto(
                        animation = EvidenceTierAnimation.ULTRAVIOLET_LIGHT_1,
                    ),
                    EvidenceResourceTierDto(
                        animation = EvidenceTierAnimation.ULTRAVIOLET_LIGHT_2,
                    ),
                    EvidenceResourceTierDto(
                        animation = EvidenceTierAnimation.ULTRAVIOLET_LIGHT_3,
                    ),
                )
            ),
            EvidenceResourceDto(
                id = EvidenceIdentifier.FREEZING_TEMPERATURE,
                name = EvidenceTitle.FREEZING_TEMPERATURE,
                description = EvidenceDescription.FREEZING_TEMPERATURE,
                icon = EvidenceIcon.FREEZING_TEMPERATURE,
                defaultAnimation = EvidenceAnimation.FREEZING_TEMPERATURE,
                defaultDescription = EvidenceDescription.FREEZING_TEMPERATURE,
                tiers = listOf(
                    EvidenceResourceTierDto(
                        animation = EvidenceTierAnimation.FREEZING_TEMPERATURE_1,
                    ),
                    EvidenceResourceTierDto(
                        animation = EvidenceTierAnimation.FREEZING_TEMPERATURE_2,
                    ),
                    EvidenceResourceTierDto(
                        animation = EvidenceTierAnimation.FREEZING_TEMPERATURE_3,
                    ),
                )
            ),
            EvidenceResourceDto(
                id = EvidenceIdentifier.GHOST_ORBS,
                name = EvidenceTitle.GHOST_ORBS,
                description = EvidenceDescription.GHOST_ORBS,
                icon = EvidenceIcon.GHOST_ORBS,
                defaultAnimation = EvidenceAnimation.GHOST_ORBS,
                defaultDescription = EvidenceDescription.GHOST_ORBS,
                tiers = listOf(
                    EvidenceResourceTierDto(
                        animation = EvidenceTierAnimation.GHOST_ORBS_1,
                    ),
                    EvidenceResourceTierDto(
                        animation = EvidenceTierAnimation.GHOST_ORBS_2,
                    ),
                    EvidenceResourceTierDto(
                        animation = EvidenceTierAnimation.GHOST_ORBS_3,
                    ),
                )
            ),
            EvidenceResourceDto(
                id = EvidenceIdentifier.GHOST_WRITING,
                name = EvidenceTitle.GHOST_WRITING,
                description = EvidenceDescription.GHOST_WRITING,
                icon = EvidenceIcon.GHOST_WRITING,
                defaultAnimation = EvidenceAnimation.GHOST_WRITING,
                defaultDescription = EvidenceDescription.GHOST_WRITING,
                tiers = listOf(
                    EvidenceResourceTierDto(
                        animation = EvidenceTierAnimation.GHOST_WRITING_1,
                    ),
                    EvidenceResourceTierDto(
                        animation = EvidenceTierAnimation.GHOST_WRITING_2,
                    ),
                    EvidenceResourceTierDto(
                        animation = EvidenceTierAnimation.GHOST_WRITING_3,
                    ),
                )
            ),
            EvidenceResourceDto(
                id = EvidenceIdentifier.SPIRIT_BOX,
                name = EvidenceTitle.SPIRIT_BOX,
                description = EvidenceDescription.SPIRIT_BOX,
                icon = EvidenceIcon.SPIRIT_BOX,
                defaultAnimation = EvidenceAnimation.SPIRIT_BOX,
                defaultDescription = EvidenceDescription.SPIRIT_BOX,
                tiers = listOf(
                    EvidenceResourceTierDto(
                        animation = EvidenceTierAnimation.SPIRIT_BOX_1,
                    ),
                    EvidenceResourceTierDto(
                        animation = EvidenceTierAnimation.SPIRIT_BOX_2,
                    ),
                    EvidenceResourceTierDto(
                        animation = EvidenceTierAnimation.SPIRIT_BOX_3,
                    ),
                )
            ),

            )

    override fun get(): Result<List<EvidenceDto>> =
        Result.success(evidenceResource.toLocal())

    private data class EvidenceResourceDto(
        val id: EvidenceIdentifier,
        val name: EvidenceTitle,
        val description: EvidenceDescription,
        val icon: EvidenceIcon,
        val defaultAnimation: EvidenceAnimation,
        val defaultDescription: EvidenceDescription,
        val tiers: List<EvidenceResourceTierDto> = emptyList()
    )

    private data class EvidenceResourceTierDto(
        val animation: EvidenceTierAnimation
    )

    private fun List<EvidenceResourceDto>.toLocal() = map { it.toLocal() }

    private fun EvidenceResourceDto.toLocal(): EvidenceDto =
        EvidenceDto(
            id = id,
            name = name,
            description = description,
            icon = icon,
            tiers = tiers.toLocal()
        )

    @JvmName("EvidenceResourceTierDtoToEvidenceTierDto")
    private fun EvidenceResourceTierDto.toLocal() =
        EvidenceTierDto(
            animation = animation
        )

    @JvmName("EvidenceResourceTierDtoListToEvidenceTierDtoList")
    private fun List<EvidenceResourceTierDto>.toLocal() = map { it.toLocal() }

}