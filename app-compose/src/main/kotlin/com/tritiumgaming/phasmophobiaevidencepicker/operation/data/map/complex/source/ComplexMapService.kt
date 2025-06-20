package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source

import android.content.res.AssetManager
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.mappers.WorldMapsSerializerDto

interface ComplexMapService {

    fun readFile(assets: AssetManager, fileName: String): Result<WorldMapsSerializerDto>

}