package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.setup

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository.LanguageDatastoreRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository.PaletteDatastoreRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source.PaletteDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository.TypographyDatastoreRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source.TypographyDatastore
import kotlinx.coroutines.flow.Flow

class InitFlowPaletteUseCase(
    private val datastoreRepository: PaletteDatastoreRepository
) {
    suspend operator fun invoke(): Flow<PaletteDatastore.PalettePreferences> =
        datastoreRepository.initFlow()
}
