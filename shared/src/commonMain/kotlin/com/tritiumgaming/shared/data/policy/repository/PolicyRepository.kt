package com.tritiumgaming.shared.data.policy.repository

import com.tritiumgaming.shared.data.datastore.DatastoreRepository
import com.tritiumgaming.shared.data.policy.source.PolicyDatastore.Policy

interface PolicyRepository: DatastoreRepository<Policy> {

    suspend fun setAllowAnalytics(allow: Boolean)

    suspend fun setAllowPersonalizedAds(allow: Boolean)

    fun isPrivacyOptionsRequired(): Boolean?

    fun showPrivacyOptionsForm(activity: Any, onFinished: () -> Unit)

    fun applyPolicy(policy: Policy)

}
