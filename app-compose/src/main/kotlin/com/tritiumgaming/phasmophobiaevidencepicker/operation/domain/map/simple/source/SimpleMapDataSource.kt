package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.source

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.dto.LocalWorldMapDto

interface SimpleMapDataSource {

    fun fetchMaps(): Result<List<LocalWorldMapDto>>

}