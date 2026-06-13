package com.tritiumgaming.feature.settings.ui

import com.tritiumgaming.core.ui.mapper.toPaletteResource
import com.tritiumgaming.core.ui.theme.palette.ExtendedPalette
import com.tritiumgaming.shared.data.market.palette.model.LocalDefaultPalette
import com.tritiumgaming.shared.data.market.palette.model.asUuid

data class PaletteUiState(
    val uuid: String = LocalDefaultPalette.asUuid(),
    val palette: ExtendedPalette = LocalDefaultPalette.toPaletteResource()
)
