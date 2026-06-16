package com.tritiumgaming.shared.data.operation.usecase

import com.tritiumgaming.shared.data.operation.OperationRepository
import com.tritiumgaming.shared.data.operation.model.MissionData

class UpdateOperationMissionDataUseCase(private val repository: OperationRepository) {
    operator fun invoke(missionData: MissionData) = repository.updateMissionData(missionData)
}
