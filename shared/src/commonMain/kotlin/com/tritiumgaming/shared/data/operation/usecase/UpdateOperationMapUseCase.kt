package com.tritiumgaming.shared.data.operation.usecase

import com.tritiumgaming.shared.data.operation.OperationRepository
import com.tritiumgaming.shared.data.operation.model.MapData


class UpdateOperationMapUseCase(private val repository: OperationRepository) {
    operator fun invoke(map: MapData) = repository.updateMap(map)
}
