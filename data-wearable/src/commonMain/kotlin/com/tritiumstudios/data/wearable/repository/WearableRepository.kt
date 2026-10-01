package com.tritiumstudios.data.wearable.repository

import com.tritiumgaming.data.evidence.mapper.EvidenceResources
import com.tritiumgaming.data.journal.model.EvidenceValidationType
import com.tritiumstudios.data.wearable.model.WearableOperationData
import kotlinx.coroutines.flow.Flow

interface WearableRepository {
    fun observeOperationData(): Flow<WearableOperationData>
    suspend fun pushOperationData(data: WearableOperationData)
    suspend fun sendToggleMessage(evidenceType: EvidenceResources.EvidenceIdentifier, newState: EvidenceValidationType)
    suspend fun sendSanityUpdateMessage(sanityLevel: Float)
}
