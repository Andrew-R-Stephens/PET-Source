package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.repository

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.dto.toDomain
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.mappers.toMapList
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.ComplexMapDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.repository.ComplexMapRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ComplexMapRepositoryImpl(
    val localSource: ComplexMapDataSource
): ComplexMapRepository {

    override suspend fun fetchMaps(): Result<ComplexWorldMaps> = withContext(Dispatchers.IO) {

        try {
            val result = localSource.fetchWorldMaps()
            Log.d("Maps", "Complex Maps:\n${result.getOrNull()?.maps?.size}")

            result.map { it.toMapList().toDomain() }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(Exception("Failed to fetch World Maps in Repository", e))
        }

    }

}