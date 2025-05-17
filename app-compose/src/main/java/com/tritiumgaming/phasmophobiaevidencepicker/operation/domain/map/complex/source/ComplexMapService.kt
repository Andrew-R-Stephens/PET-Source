package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.source

import android.content.res.AssetManager
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.local.model.WorldMaps

interface ComplexMapService {

    fun readFile(assets: AssetManager, fileName: String): Result<WorldMaps>

}