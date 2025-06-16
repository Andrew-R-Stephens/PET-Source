package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.worldmaps.simple

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.dto.WorldMapDto
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.dto.toDomain
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.worldmaps.complex.WorldMap
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.worldmaps.complex.WorldMaps

class LocalWorldMaps(
    internal var maps: List<LocalWorldMap>
)
