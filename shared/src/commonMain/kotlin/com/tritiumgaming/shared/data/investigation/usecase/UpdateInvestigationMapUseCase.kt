package com.tritiumgaming.shared.data.investigation.usecase

import com.tritiumgaming.shared.data.investigation.InvestigationRepository
import com.tritiumgaming.shared.data.investigation.model.MapData


class UpdateInvestigationMapUseCase(private val repository: InvestigationRepository) {
    operator fun invoke(map: MapData) = repository.updateMap(map)
}
