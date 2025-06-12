package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.local

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.mappers.WorldMapsSerializerDto
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
    override suspend fun fetchWorldMaps(): Result<WorldMapsSerializerDto> {
        return try {
            val result = CoroutineScope(Dispatchers.IO).async {
                service.readFile(
                    assets = applicationContext.assets,
                    fileName = applicationContext.getString(R.string.mapsJson)
                )
            }.await()

            result
        } catch (e: Exception) {
            e.printStackTrace()

            Result.failure(Exception("Failed to fetch World Maps", e))
        }
    }

}