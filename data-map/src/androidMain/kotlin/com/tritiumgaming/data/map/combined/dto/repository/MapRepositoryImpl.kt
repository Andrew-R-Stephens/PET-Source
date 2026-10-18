package com.tritiumgaming.data.map.combined.dto.repository

import android.util.Log
import com.tritiumgaming.data.map.combined.model.WorldMap
import com.tritiumgaming.data.map.combined.model.WorldMaps
import com.tritiumgaming.data.map.combined.repository.MapRepository
import com.tritiumgaming.data.map.complex.dto.toDomain
import com.tritiumgaming.data.map.complex.mappers.toMapList
import com.tritiumgaming.data.map.complex.model.ComplexWorldMap
import com.tritiumgaming.data.map.complex.model.ComplexWorldMaps
import com.tritiumgaming.data.map.complex.source.ComplexMapDataSource
import com.tritiumgaming.data.map.simple.dto.toDomain
import com.tritiumgaming.data.map.simple.model.SimpleWorldMap
import com.tritiumgaming.data.map.simple.model.SimpleWorldMaps
import com.tritiumgaming.data.map.simple.source.local.SimpleMapDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class MapRepositoryImpl(
    val localSource: SimpleMapDataSource,
    val assetSource: ComplexMapDataSource
): MapRepository {

    val simpleMaps: MutableStateFlow<List<SimpleWorldMap>> = MutableStateFlow(emptyList())
    val complexMaps: MutableStateFlow<List<ComplexWorldMap>> = MutableStateFlow(emptyList())

    override val mapFlow: Flow<WorldMaps> = combine(
        simpleMaps,
        complexMaps
    ) { simpleList, complexList ->

        // Map assets by ID for O(1) lookup speed
        val assetMap = complexList.associateBy { it.mapId }

        // Fold asset properties into each local item
        val worldMapsList = simpleList.mapNotNull { simple ->
            val asset = assetMap[simple.mapId] ?: return@mapNotNull null

            WorldMap(
                simpleMap = simple,
                complexMap = asset
            )
        }

        WorldMaps(worldMaps = worldMapsList)
    }

    override suspend fun fetchMaps(): Result<WorldMaps> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchSimpleMaps(): Result<SimpleWorldMaps> {
        if(simpleMaps.value.isEmpty()) {
            syncLocal()
        }

        return Result.success(SimpleWorldMaps(simpleMaps.value))
    }

    override suspend fun fetchComplexMaps(): Result<ComplexWorldMaps> = withContext(Dispatchers.IO) {

        try {
            val result = assetSource.fetchWorldMaps()
            Log.d("Maps", "Complex Maps:\n${result.getOrNull()?.maps?.size}")

            result.map { it.toMapList().toDomain() }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(Exception("Failed to fetch World Maps in Repository", e))
        }

    }

    private fun syncLocal() {
        val data = localSource.fetchMaps().getOrDefault(emptyList()).toDomain()
        simpleMaps.update {
            data
        }

        Log.d("SimpleMapRepositoryImpl", "simpleMaps: $simpleMaps")
    }

}