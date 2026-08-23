package com.tritiumgaming.phasmophobiaevidencepicker.core.ui.wearable

import android.util.Log
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import com.tritiumgaming.phasmophobiaevidencepicker.core.ui.app.PETApplication
import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceIdentifier
import com.tritiumgaming.shared.data.operation.model.EvidenceValidationType
import com.tritiumgaming.shared.data.wearable.WearablePaths
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class WearableSyncService : WearableListenerService() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val TAG = "WearableSyncService"

    override fun onMessageReceived(messageEvent: MessageEvent) {
        if (messageEvent.path == WearablePaths.EVIDENCE_TOGGLE) {
            val message = String(messageEvent.data)
            val parts = message.split(":")
            if (parts.size == 2) {
                val idName = parts[0]
                val stateName = parts[1]
                updateEvidenceOnHost(EvidenceIdentifier.valueOf(idName), stateName)
            }
        } else if (messageEvent.path == WearablePaths.SANITY_UPDATE) {
            val sanityString = String(messageEvent.data)
            updateSanityOnHost(sanityString)
        }
    }

    private fun updateSanityOnHost(sanityString: String) {
        val app = application as PETApplication
        val investigationUseCaseBundle = app.investigationContainer.investigationUseCaseBundle

        scope.launch {
            try {
                val newSanityLevel = sanityString.toFloat()
                val currentState = investigationUseCaseBundle.getOperationStateUseCase().value
                val newSanityData = currentState.sanity.copy(sanityLevel = newSanityLevel)
                investigationUseCaseBundle.updateOperationSanityUseCase(newSanityData)
            } catch (e: Exception) {
                Log.e(TAG, "Error updating sanity from Wearable", e)
            }
        }
    }

    private fun updateEvidenceOnHost(evidenceIdentifier: EvidenceIdentifier, stateName: String) {
        val app = application as PETApplication
        val investigationUseCaseBundle = app.investigationContainer.investigationUseCaseBundle
        
        scope.launch {
            try {
                val newState = EvidenceValidationType.valueOf(stateName)
                
                val currentState = investigationUseCaseBundle.getOperationStateUseCase().value
                val newEvidenceStates = currentState.evidenceStates.map { 
                    if (it.evidence.id == evidenceIdentifier) it.copy(state = newState) else it
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
