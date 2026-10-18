package com.tritiumgaming.data.map.combined.repository

import com.tritiumgaming.data.map.combined.model.WorldMaps
import com.tritiumgaming.data.map.complex.model.ComplexWorldMaps
import com.tritiumgaming.data.map.simple.model.SimpleWorldMaps
import kotlinx.coroutines.flow.Flow

interface MapRepository {

    val mapFlow: Flow<WorldMaps>

    suspend fun fetchMaps(): Result<WorldMaps>

    suspend fun fetchSimpleMaps(): Result<SimpleWorldMaps>

    suspend fun fetchComplexMaps(): Result<ComplexWorldMaps>

}