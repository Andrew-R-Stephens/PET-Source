package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.local

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.local.model.WorldMaps
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.source.ComplexMapDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.source.ComplexMapService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class ComplexMapLocalDataSource(
    private val applicationContext: Context,
    override val service: ComplexMapService,
): ComplexMapDataSource {

    @Throws(Exception::class)
    override suspend fun fetchWorldMaps(): WorldMaps {

        var worldMaps = WorldMaps()
        try {
            return CoroutineScope(Dispatchers.IO).async {
                val result = service.readFile(
                    assets = applicationContext.assets,
                    fileName = applicationContext.getString(R.string.mapsJson)
                )
                if(result.isSuccess) {
                    return@async result.getOrNull() ?: WorldMaps()
                } else {
                    throw Exception("Failed to fetch world maps")
                }
            }.await()
        }
        catch (e: Exception) { e.printStackTrace() }
        return worldMaps
    }

}