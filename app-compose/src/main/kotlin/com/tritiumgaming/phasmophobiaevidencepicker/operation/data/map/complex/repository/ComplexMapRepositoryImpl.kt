package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.repository

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.local.model.toMapList
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.worldmaps.MapListModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.repository.ComplexMapRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.source.ComplexMapDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ComplexMapRepositoryImpl(
    override val localSource: ComplexMapDataSource
): ComplexMapRepository {

    override suspend fun fetchMaps(): Result<MapListModel> = withContext(Dispatchers.IO) {

        try {
            val result = localSource.fetchWorldMaps()
            Log.d("Maps", "Complex Maps:\n${result.getOrNull()?.maps?.size}")

            result.map { it.toMapList() }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(Exception("Failed to fetch World Maps", e))
        }

    }

}