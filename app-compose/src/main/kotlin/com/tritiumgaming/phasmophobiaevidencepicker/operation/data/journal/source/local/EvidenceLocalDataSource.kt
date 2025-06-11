package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.source.local

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto.EvidenceDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto.EvidenceTierDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.source.EvidenceDataSource

class EvidenceLocalDataSource(
    private val applicationContext: Context
): EvidenceDataSource {

    private val evidenceResource: List<EvidenceResourceDto>
        get() = listOf(
            EvidenceResourceDto(
                id = R.string.evidence_id_dots,
                name = R.string.evidence_type_dots,
                icon = R.drawable.ic_ev_dots,
                buyCost = R.integer.equipment_requirement_buycost_dots,
                defaultAnimation = R.drawable.example_dots_0,
                defaultDescription = R.string.evidence_info_overview_dots,
                tiers = listOf(
                    EvidenceResourceTierDto(
                        description = R.string.shop_equipment_dots_data_info_1,
                        animation = R.drawable.anim_dots_1,
                        levelRequirement = R.integer.equipment_requirement_level_dots_1
                    ),
                    EvidenceResourceTierDto(
                        description = R.string.shop_equipment_dots_data_info_2,
                        animation = R.drawable.anim_dots_2,
                        levelRequirement = R.integer.equipment_requirement_level_dots_2
                    ),
                    EvidenceResourceTierDto(
                        description = R.string.shop_equipment_dots_data_info_3,
                        animation = R.drawable.anim_dots_3,
                        levelRequirement = R.integer.equipment_requirement_level_dots_3
                    ),
                )
            ),
            EvidenceResourceDto(
                id = R.string.evidence_id_emf,
                name = R.string.evidence_type_emf,
                icon = R.drawable.ic_ev_emf,
                buyCost = R.integer.equipment_requirement_buycost_emf,
                defaultAnimation = R.drawable.example_emf5_0,
                defaultDescription = R.string.evidence_info_overview_emf,
                tiers = listOf(
                    EvidenceResourceTierDto(
                        description = R.string.shop_equipment_emf_data_info_1,
                        animation = R.drawable.anim_emf_1,
                        levelRequirement = R.integer.equipment_requirement_level_emf_1
                    ),
                    EvidenceResourceTierDto(
                        description = R.string.shop_equipment_emf_data_info_2,
                        animation = R.drawable.anim_emf_2,
                        levelRequirement = R.integer.equipment_requirement_level_emf_2
                    ),
                    EvidenceResourceTierDto(
                        description = R.string.shop_equipment_emf_data_info_3,
                        animation = R.drawable.anim_emf_3,
                        levelRequirement = R.integer.equipment_requirement_level_emf_3
                    ),
                )
            ),
            EvidenceResourceDto(
                id = R.string.evidence_id_ultraviolet,
                name = R.string.evidence_type_ultraviolet,
                icon = R.drawable.ic_ev_uv,
                buyCost = R.integer.equipment_requirement_buycost_ultraviolet,
                defaultAnimation = R.drawable.example_fingerprints_normal_cropped_0,
                defaultDescription = R.string.evidence_info_overview_ultraviolet,
                tiers = listOf(
                    EvidenceResourceTierDto(
                        description = R.string.shop_equipment_uvlight_data_info_1,
                        animation = R.drawable.anim_uv_1,
                        levelRequirement = R.integer.equipment_requirement_level_ultraviolet_1
                    ),
                    EvidenceResourceTierDto(
                        description = R.string.shop_equipment_uvlight_data_info_2,
                        animation = R.drawable.anim_uv_2,
                        levelRequirement = R.integer.equipment_requirement_level_ultraviolet_2
                    ),
                    EvidenceResourceTierDto(
                        description = R.string.shop_equipment_uvlight_data_info_3,
                        animation = R.drawable.anim_uv_3,
                        levelRequirement = R.integer.equipment_requirement_level_ultraviolet_3
                    ),
                )
            ),
            EvidenceResourceDto(
                id = R.string.evidence_id_temperatures,
                name = R.string.evidence_type_temperatures,
                icon = R.drawable.ic_ev_freezing,
                buyCost = R.integer.equipment_requirement_buycost_thermometer,
                defaultAnimation = R.drawable.example_thermometer_freezing_cropped_0,
                defaultDescription = R.string.evidence_info_overview_thermometer,
                tiers = listOf(
                    EvidenceResourceTierDto(
                        description = R.string.shop_equipment_thermometer_data_info_1,
                        animation = R.drawable.anim_thermo_1,
                        levelRequirement = R.integer.equipment_requirement_level_thermometer_1
                    ),
                    EvidenceResourceTierDto(
                        description = R.string.shop_equipment_thermometer_data_info_2,
                        animation = R.drawable.anim_thermo_2,
                        levelRequirement = R.integer.equipment_requirement_level_thermometer_2
                    ),
                    EvidenceResourceTierDto(
                        description = R.string.shop_equipment_thermometer_data_info_3,
                        animation = R.drawable.anim_thermo_3,
                        levelRequirement = R.integer.equipment_requirement_level_thermometer_3
                    ),
                )
            ),
            EvidenceResourceDto(
                id = R.string.evidence_id_orbs,
                name = R.string.evidence_type_orbs,
                icon = R.drawable.ic_ev_orbs,
                buyCost = R.integer.equipment_requirement_buycost_vcam,
                defaultAnimation = R.drawable.example_ghost_orbs_0,
                defaultDescription = R.string.evidence_info_overview_vcam,
                tiers = listOf(
                    EvidenceResourceTierDto(
                        description = R.string.shop_equipment_videocamera_data_info_1,
                        animation = R.drawable.anim_orbs_1,
                        levelRequirement = R.integer.equipment_requirement_level_vcam_1
                    ),
                    EvidenceResourceTierDto(
                        description = R.string.shop_equipment_videocamera_data_info_2,
                        animation = R.drawable.anim_orbs_2,
                        levelRequirement = R.integer.equipment_requirement_level_vcam_2
                    ),
                    EvidenceResourceTierDto(
                        description = R.string.shop_equipment_videocamera_data_info_3,
                        animation = R.drawable.anim_orbs_3,
                        levelRequirement = R.integer.equipment_requirement_level_vcam_3
                    ),
                )
            ),
            EvidenceResourceDto(
                id = R.string.evidence_id_book,
                name = R.string.evidence_type_book,
                icon = R.drawable.ic_ev_writing,
                buyCost = R.integer.equipment_requirement_buycost_book,
                defaultAnimation = R.drawable.example_ghost_writing_cropped_0,
                defaultDescription = R.string.evidence_info_overview_book,
                tiers = listOf(
                    EvidenceResourceTierDto(
                        description = R.string.shop_equipment_ghostwritingbook_data_info_1,
                        animation = R.drawable.anim_writing_1,
                        levelRequirement = R.integer.equipment_requirement_level_book_1
                    ),
                    EvidenceResourceTierDto(
                        description = R.string.shop_equipment_ghostwritingbook_data_info_2,
                        animation = R.drawable.anim_writing_2,
                        levelRequirement = R.integer.equipment_requirement_level_book_2
                    ),
                    EvidenceResourceTierDto(
                        description = R.string.shop_equipment_ghostwritingbook_data_info_3,
                        animation = R.drawable.anim_writing_3,
                        levelRequirement = R.integer.equipment_requirement_level_book_3
                    ),
                )
            ),
            EvidenceResourceDto(
                id = R.string.evidence_id_box,
                name = R.string.evidence_type_box,
                icon = R.drawable.ic_ev_spiritbox,
                buyCost = R.integer.equipment_requirement_buycost_box,
                defaultAnimation = R.drawable.example_spirit_box_cropped_0,
                defaultDescription = R.string.evidence_info_overview_sbox,
                tiers = listOf(
                    EvidenceResourceTierDto(
                        description = R.string.shop_equipment_spiritbox_data_info_1,
                        animation = R.drawable.example_spirit_box_cropped_0,
                        levelRequirement = R.integer.equipment_requirement_level_box_1
                    ),
                    EvidenceResourceTierDto(
                        description = R.string.shop_equipment_spiritbox_data_info_2,
                        animation = R.drawable.example_spirit_box_cropped_0,
                        levelRequirement = R.integer.equipment_requirement_level_box_2
                    ),
                    EvidenceResourceTierDto(
                        description = R.string.shop_equipment_spiritbox_data_info_3,
                        animation = R.drawable.example_spirit_box_cropped_0,
                        levelRequirement = R.integer.equipment_requirement_level_box_3
                    ),
                )
            ),

        )


    override fun get(): Result<List<EvidenceDto>> {

        val result: MutableList<EvidenceDto> = mutableListOf()

        evidenceResource.forEach { resDto ->
            val evidenceDto = EvidenceDto(
                id = applicationContext.getString(resDto.id),
                name = resDto.name,
                icon = resDto.icon,
                buyCost = resDto.buyCost,
                tiers = resDto.tiers.toLocal()
            )
            result.add(evidenceDto)
        }
        return Result.success(emptyList())

    }

    private data class EvidenceResourceDto(
        val id: Int,
        @StringRes val name: Int,
        @DrawableRes val icon: Int,
        @IntegerRes val buyCost: Int,
        @DrawableRes val defaultAnimation: Int,
        @StringRes val defaultDescription: Int,
        val tiers: List<EvidenceResourceTierDto> = emptyList()
    )

    private data class EvidenceResourceTierDto(
        @StringRes val description: Int,
        @DrawableRes val animation: Int,
        @IntegerRes val levelRequirement: Int
    )

    private fun EvidenceResourceDto.toLocal(): EvidenceDto =
        EvidenceDto(
            id = applicationContext.getString(this.id),
            name = this.name,
            icon = this.icon,
            buyCost = this.buyCost,
            tiers = this.tiers.toLocal()
        )

    @JvmName("EvidenceResourceTierDtoToEvidenceTierDto")
    private fun EvidenceResourceTierDto.toLocal() =
        EvidenceTierDto(
            description = this.description,
            animation = this.animation,
            levelRequirement = this.levelRequirement
        )

    @JvmName("EvidenceResourceTierDtoListToEvidenceTierDtoList")
    private fun List<EvidenceResourceTierDto>.toLocal() = map { it.toLocal() }

}