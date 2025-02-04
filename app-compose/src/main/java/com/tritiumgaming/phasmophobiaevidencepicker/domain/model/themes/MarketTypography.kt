package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.themes

import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.ExtendedTypography

data class MarketTypography (
    val marketTypographyEntity: MarketTypographyEntity? = null,
    private val uuid: String = marketTypographyEntity?.uuid ?: "",
    val typography: ExtendedTypography
) {
    fun getAsPair(): Pair<String, MarketTypography> {
        return Pair(uuid, this)
    }
}