package com.tritiumgaming.phasmophobiaevidencepicker.core.ui.wearable

import android.util.Log
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import com.google.android.gms.wearable.WearableListenerService
import com.tritiumgaming.phasmophobiaevidencepicker.core.ui.app.PETApplication
import com.tritiumgaming.shared.data.operation.model.OperationData
import com.tritiumgaming.shared.data.wearable.WearablePaths
import com.tritiumgaming.shared.data.wearable.model.WearableEvidenceState
import com.tritiumgaming.shared.data.wearable.model.WearableOperationData
import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceIdentifier
import com.tritiumgaming.shared.data.operation.model.EvidenceValidationType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class WearableSyncService : WearableListenerService() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val TAG = "WearableSyncService"

    override fun onCreate() {
        super.onCreate()
        val app = application as PETApplication
        val investigationUseCaseBundle = app.investigationContainer.investigationUseCaseBundle
        
        scope.launch {
            investigationUseCaseBundle.getOperationStateUseCase().collectLatest { data ->
                syncOperationData(data)
            }
        }
    }

    private fun syncOperationData(data: OperationData) {
        val wearableData = WearableOperationData(
            mapName = data.map.name.name,
            difficultyName = data.difficulty.title.name,
            setupTimeRemaining = data.phase.maxFlashTime - data.phase.elapsedFlashTime,
            sanityLevel = data.sanity.sanityLevel,
            evidenceStates = data.evidenceStates.map { 
                WearableEvidenceState(it.evidence.id.name, it.state.name, it.enabled)
            }
        )
        
        try {
            val json = Json.encodeToString(wearableData)
            val putDataMapReq = PutDataMapRequest.create(WearablePaths.OPERATION_STATE)
            putDataMapReq.dataMap.putString(WearablePaths.DATA_KEY, json)
            putDataMapReq.dataMap.putLong("timestamp", System.currentTimeMillis())
            val putDataReq = putDataMapReq.asPutDataRequest()
            
            Wearable.getDataClient(this).putDataItem(putDataReq)
                .addOnSuccessListener { Log.d(TAG, "Synced operation data to Wear OS") }
                .addOnFailureListener { e -> Log.e(TAG, "Failed to sync operation data", e) }
        } catch (e: Exception) {
            Log.e(TAG, "Serialization error", e)
        }
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {
        if (messageEvent.path == WearablePaths.EVIDENCE_TOGGLE) {
            val message = String(messageEvent.data)
            val parts = message.split(":")
            if (parts.size == 2) {
                val idName = parts[0]
                val stateName = parts[1]
                updateEvidenceOnHost(idName, stateName)
            }
        }
    }

    private fun updateEvidenceOnHost(idName: String, stateName: String) {
        val app = application as PETApplication
        val investigationUseCaseBundle = app.investigationContainer.investigationUseCaseBundle
        
        scope.launch {
            try {
                val identifier = EvidenceIdentifier.valueOf(idName)
                val newState = EvidenceValidationType.valueOf(stateName)
                
                val currentState = investigationUseCaseBundle.getOperationStateUseCase().value
                val newEvidenceStates = currentState.evidenceStates.map { 
                    if (it.evidence.id == identifier) it.copy(state = newState) else it
                }
                
                investigationUseCaseBundle.updateOperationEvidenceUseCase(newEvidenceStates)
            } catch (e: Exception) {
                Log.e(TAG, "Error updating evidence from Wearable", e)
            }
        }
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }
}
