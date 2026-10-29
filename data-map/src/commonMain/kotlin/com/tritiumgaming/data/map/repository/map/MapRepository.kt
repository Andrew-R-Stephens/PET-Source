package com.tritiumgaming.data.map.repository.map

import com.tritiumgaming.data.map.model.complex.ComplexWorldMaps
import com.tritiumgaming.data.map.model.simple.SimpleWorldMaps
import com.tritiumgaming.data.map.model.worldmap.WorldMaps
import kotlinx.coroutines.flow.Flow

interface MapRepository {

    val mapFlow: Flow<WorldMaps>

    suspend fun fetchSimpleMaps(): Result<SimpleWorldMaps>

    suspend fun fetchComplexMaps(): Result<ComplexWorldMaps>

}