package com.tritiumgaming.data.map.combined.dto.repository

import android.util.Log
import com.tritiumgaming.data.map.complex.dto.toDomain
import com.tritiumgaming.data.map.complex.mappers.toMapList
import com.tritiumgaming.data.map.complex.source.ComplexMapDataSource
import com.tritiumgaming.data.map.dto.simple.toDomain
import com.tritiumgaming.data.map.model.complex.ComplexWorldMap
import com.tritiumgaming.data.map.model.complex.ComplexWorldMaps
import com.tritiumgaming.data.map.model.simple.SimpleWorldMap
import com.tritiumgaming.data.map.model.simple.SimpleWorldMaps
import com.tritiumgaming.data.map.model.worldmap.WorldMap
import com.tritiumgaming.data.map.model.worldmap.WorldMaps
import com.tritiumgaming.data.map.repository.map.MapRepository
import com.tritiumgaming.data.map.source.simple.local.SimpleMapDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
    ) { simples, complexes ->

        val simpleMapById = simples.associateBy { it.mapId }
        val complexMapById = complexes.associateBy { it.mapId }

        val allIds = (simples.map { it.mapId } + complexes.map { it.mapId })
            .distinct()
            .filter { it.isNotEmpty() }

        val worldMapsList: Map<String, WorldMap> = allIds.associateWith { id ->
            WorldMap(
                id = id,
                simpleMap = simpleMapById[id] ?: WorldMap.simpleDefault,
                complexMap = complexMapById[id] ?: WorldMap.complexDefault
            )
        }

        Log.d(
            "MapRepositoryImpl",
            "worldMapsList: ${worldMapsList.map { "${it.value.id} ${it.value.simpleMap.mapId} && ${it.value.complexMap.mapId}" }}"
        )

        WorldMaps(worldMaps = worldMapsList)
    }

    override suspend fun fetchSimpleMaps(): Result<SimpleWorldMaps> {
        if(simpleMaps.value.isEmpty()) {
            syncLocal()
        }

        return Result.success(
            SimpleWorldMaps(
                simpleMaps.value
            )
        )
    }

    override suspend fun fetchComplexMaps(): Result<ComplexWorldMaps> = withContext(IO) {

        try {
            val result = assetSource.fetchWorldMaps()

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

        Log.d("MapRepositoryImpl",
            "SimpleMaps:\n${simpleMaps.value.map { "${ it.mapId }, " }}")
    }

    init {
        CoroutineScope(IO).launch {
            val simple = fetchSimpleMaps()
            simpleMaps.update {
                simple.getOrDefault(SimpleWorldMaps(emptyList())).maps
            }

            val complex = fetchComplexMaps()
            complexMaps.update {
                complex.getOrDefault(ComplexWorldMaps(emptyList())).maps
            }
        }
    }

}