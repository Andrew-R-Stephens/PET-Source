package com.tritiumgaming.shared.data.wearable.repository

import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources
import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceIdentifier
import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.operation.model.EvidenceValidationType
import com.tritiumgaming.shared.data.wearable.model.WearableOperationData
import kotlinx.coroutines.flow.Flow

interface WearableRepository {
    fun observeOperationData(): Flow<WearableOperationData>
    suspend fun pushOperationData(data: WearableOperationData)
    suspend fun sendToggleMessage(evidenceType: EvidenceIdentifier, newState: EvidenceValidationType)
    suspend fun sendSanityUpdateMessage(sanityLevel: Float)
}
