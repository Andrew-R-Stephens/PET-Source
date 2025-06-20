package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.preference

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository.MarketTypographyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ExtendedTypography

class GetTypographyByUUIDUseCase(
    private val repository: MarketTypographyRepository
) {

    operator fun invoke(
        uuid: String, defaultTypography: ExtendedTypography
    ): ExtendedTypography {
        Log.d("GetTypographyByUUIDUseCase", "Finding cached palette: $uuid")
        val typographyCache = repository.get().getOrDefault(emptyList())
        val cachedTypography = typographyCache.find { it.uuid == uuid }

        Log.d("GetTypographyByUUIDUseCase", "Cached palette found $cachedTypography")
        Log.d("GetTypographyByUUIDUseCase", "ExtendedPalette: ${cachedTypography?.typography}")

        return cachedTypography?.typography ?: defaultTypography
    }

}