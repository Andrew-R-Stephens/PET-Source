package com.tritiumgaming.data.marketplace.bundle.source.remote

import com.google.firebase.functions.FirebaseFunctions
import com.tritiumgaming.data.marketplace.bundle.dto.MarketBundleDto
import com.tritiumgaming.shared.data.market.bundle.model.BundleQueryOptions
import kotlinx.coroutines.tasks.await

class MarketBundleFirestoreDataSourceImpl(
    private val firebaseFunctions: FirebaseFunctions
): MarketBundleFirestoreDataSource {

    override suspend fun fetch(
        options: BundleQueryOptions
    ): Result<List<MarketBundleDto>> {

        return try {
            val data = hashMapOf(
                "filterField" to options.filterField.value,
                "filterValue" to options.filterValue.value,
                "orderField" to options.orderField.value,
                "orderDirection" to options.orderDirection.name,
                "limit" to options.limit.value
            )

            val result = firebaseFunctions
                .getHttpsCallable("fetchBundles")
                .call(data)
                .await()

            val bundles = mutableListOf<MarketBundleDto>()

            val resultList = result.data as? List<*> ?: emptyList<Any>()

            resultList.forEach { item ->
                (item as? Map<*, *>)?.let { map ->
                    val uuid = (map["uuid"] as? String) ?: ""
                    val name = (map["name"] as? String) ?: ""
                    val buyCredits = (map["buyCredits"] as? Number)?.toLong() ?: 0L
                    val themeUUIDs = (map["items"] as? List<*>)?.filterIsInstance<String>() ?: emptyList()

                    bundles.add(
                        MarketBundleDto(
                            uuid = uuid,
                            name = name,
                            buyCredits = buyCredits,
                            items = themeUUIDs
                        )
                    )
                }
            }

            Result.success(bundles)

        } catch (e: Exception) {
            Result.failure(Exception("Error obtaining remote bundles!", e))
        }

    }

}
