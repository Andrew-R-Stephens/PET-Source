package com.tritiumgaming.shared.data.market.typography.source

import com.tritiumgaming.shared.data.datastore.DatastoreDataSource
import com.tritiumgaming.shared.data.market.typography.source.TypographyDatastore.TypographyPreferences

interface TypographyDatastore:
    DatastoreDataSource<TypographyPreferences> {

    suspend fun saveTypography(uuid: String)

    data class TypographyPreferences(
        val uuid: String
    )

}