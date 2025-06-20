package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.setup

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDatastore.LanguagePreferences
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository.MarketTypographyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source.MarketTypographyDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source.MarketTypographyDatastore.TypographyPreferences
import kotlinx.coroutines.flow.Flow

class InitFlowTypographyUseCase(
    private val repository: MarketTypographyRepository
) {
    suspend operator fun invoke(onUpdate: (TypographyPreferences) -> Unit) =
        repository.initFlow(onUpdate)
}
