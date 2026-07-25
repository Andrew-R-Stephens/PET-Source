package com.tritiumgaming.shared.data.wearable.repository

import com.tritiumgaming.shared.data.wearable.model.WearableOperationData
import kotlinx.coroutines.flow.Flow

interface WearableRepository {
    fun observeOperationData(): Flow<WearableOperationData>
    suspend fun pushOperationData(data: WearableOperationData)
    suspend fun sendToggleMessage(evidenceId: String, newState: String)
}
