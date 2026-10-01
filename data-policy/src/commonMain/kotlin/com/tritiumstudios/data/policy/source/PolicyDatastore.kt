package com.tritiumstudios.data.policy.source

import com.tritiumgaming.core.common.datastore.DatastoreDataSource
import com.tritiumstudios.data.policy.source.PolicyDatastore.Policy

interface PolicyDatastore: DatastoreDataSource<Policy> {

    suspend fun setAllowAnalytics(allow: Boolean)

    suspend fun setAllowPersonalizedAds(allow: Boolean)

    data class Policy(
        val allowAnalytics: Boolean = false,
        val allowPersonalizedAds: Boolean = false
    )

}
