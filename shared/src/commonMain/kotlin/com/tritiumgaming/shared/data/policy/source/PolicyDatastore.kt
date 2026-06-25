package com.tritiumgaming.shared.data.policy.source

import com.tritiumgaming.shared.data.datastore.DatastoreDataSource
import com.tritiumgaming.shared.data.policy.source.PolicyDatastore.Policy

interface PolicyDatastore: DatastoreDataSource<Policy> {

    suspend fun setAllowAnalytics(allow: Boolean)

    suspend fun setAllowPersonalizedAds(allow: Boolean)

    data class Policy(
        val allowAnalytics: Boolean = false,
        val allowPersonalizedAds: Boolean = false
    )

}
