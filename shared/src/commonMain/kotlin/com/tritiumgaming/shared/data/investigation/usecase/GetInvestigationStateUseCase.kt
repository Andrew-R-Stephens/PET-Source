package com.tritiumgaming.shared.data.investigation.usecase

import com.tritiumgaming.shared.data.investigation.InvestigationRepository
import com.tritiumgaming.shared.data.investigation.model.InvestigationData
import kotlinx.coroutines.flow.StateFlow

class GetInvestigationStateUseCase(
    private val repository: InvestigationRepository
) {
    operator fun invoke(): StateFlow<InvestigationData> = repository.state
}