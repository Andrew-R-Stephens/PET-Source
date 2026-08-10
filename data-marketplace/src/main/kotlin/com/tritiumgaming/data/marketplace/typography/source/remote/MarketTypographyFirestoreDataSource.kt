package com.tritiumgaming.data.marketplace.typography.source.remote

import com.google.firebase.functions.FirebaseFunctions
import com.tritiumgaming.data.marketplace.typography.dto.MarketTypographyDto
import com.tritiumgaming.shared.data.market.common.source.MarketFirestoreDataSource
import com.tritiumgaming.shared.data.market.typography.model.TypographyQueryOptions
import kotlinx.coroutines.tasks.await
import kotlin.collections.get

class MarketTypographyFirestoreDataSource(
    private val firebaseFunctions: FirebaseFunctions,
): MarketFirestoreDataSource<MarketTypographyDto, TypographyQueryOptions> {

    override suspend fun fetch(
        queryOptions: TypographyQueryOptions,
        version: Int
    ): Result<List<MarketTypographyDto>> {

        return try {
            val data = hashMapOf(
                "filterField" to queryOptions.filterField.value,
                "filterValue" to queryOptions.filterValue.value,
                "orderField" to queryOptions.orderField.value,
                "orderDirection" to queryOptions.orderDirection.name,
                "limit" to queryOptions.limit.value,
                "version" to version
            )

            val result = firebaseFunctions
                .getHttpsCallable("fetchTypographies")
                .call(data)
                .await()

            val typographies = mutableListOf<MarketTypographyDto>()

            val resultList = result.data as? List<*> ?: emptyList<Any>()

            resultList.forEach { item ->
                (item as? Map<*, *>)?.let { map ->
                    val uuid = (map["uuid"] as? String) ?: ""
                    val name = (map["name"] as? String) ?: ""
                    val group = (map["group"] as? String) ?: ""
                    val buyCredits = (map["buyCredits"] as? Number)?.toLong() ?: 0L

                    typographies.add(
                        MarketTypographyDto(
                            uuid = uuid,
                            name = name,
                            group = group,
                            buyCredits = buyCredits
                        )
                    )
                }
            }

            Result.success(typographies)

        } catch (e: Exception) {
            Result.failure(Exception("Error obtaining remote typographies!", e))
        }

    }

}