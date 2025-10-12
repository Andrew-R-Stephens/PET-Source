package com.tritiumgaming.shared.core.domain.market.typography.source

import com.tritiumgaming.shared.core.domain.datastore.DatastoreDataSource
import com.tritiumgaming.shared.core.domain.market.typography.source.TypographyDatastore.TypographyPreferences

interface TypographyDatastore: DatastoreDataSource<TypographyPreferences> {

    suspend fun saveTypography(uuid: String)

    data class TypographyPreferences(
        val uuid: String
    )

}