package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.repository

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.dto.toDomain
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.source.SimpleMapDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model.SimpleWorldMap
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository.SimpleMapRepository

class SimpleMapRepositoryImpl(
    val localSource: SimpleMapDataSource
): SimpleMapRepository {

    var simpleMaps: List<SimpleWorldMap> = emptyList()

    fun sync() {
        simpleMaps = localSource.fetchMaps().getOrDefault(emptyList()).toDomain()

        Log.d("SimpleMapRepositoryImpl", "simpleMaps: $simpleMaps")
    }

    override fun getMaps(): Result<List<SimpleWorldMap>> {
        if(simpleMaps.isEmpty()) {
            sync()
        }

        return Result.success(simpleMaps)
    }

}
