package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source.TypographyDatastore
import kotlinx.coroutines.flow.Flow

interface TypographyDatastoreRepository {

    fun initialSetupEvent()
    suspend fun initFlow(): Flow<TypographyDatastore.TypographyPreferences>

    suspend fun saveCurrentTypography(uuid: String)

}