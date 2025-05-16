package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.local

import android.content.Context
import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.local.model.WorldMaps
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class ComplexMapLocalDataSource(
    val service: ComplexMapLocalService,
) {

    @Throws(Exception::class)
    suspend fun fetchWorldMaps(context: Context): WorldMaps {
        var worldMaps = WorldMaps()
        try {
            return CoroutineScope(Dispatchers.IO).async {
                val result = service.readFile(
                    assets = context.assets, context.getString(R.string.mapsJson))
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