package com.tritiumgaming.phasmophobiaevidencepicker.wear.presentation.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.Wearable
import com.tritiumgaming.shared.data.wearable.WearablePaths
import com.tritiumgaming.shared.data.wearable.model.WearableOperationData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.json.Json

class WearableViewModel(application: Application) : AndroidViewModel(application), DataClient.OnDataChangedListener {

    private val _uiState = MutableStateFlow(WearableOperationData())
    val uiState = _uiState.asStateFlow()

    private val dataClient by lazy { Wearable.getDataClient(application) }
    private val TAG = "WearableViewModel"

    init {
        dataClient.addListener(this)
        refreshData()
    }

    private fun refreshData() {
        viewModelScope.launch {
            try {
                val uri = Uri.Builder().scheme("wear").path(WearablePaths.OPERATION_STATE).build()
                val item = dataClient.getDataItem(uri).await()
                item?.let { handleDataItem(it) }
            } catch (e: Exception) {
                Log.e(TAG, "Error refreshing data", e)
            }
        }
    }

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        dataEvents.forEach { event ->
            if (event.type == DataEvent.TYPE_CHANGED && event.dataItem.uri.path == WearablePaths.OPERATION_STATE) {
                handleDataItem(event.dataItem)
            }
        }
    }

    private fun handleDataItem(dataItem: com.google.android.gms.wearable.DataItem) {
        val dataMap = DataMapItem.fromDataItem(dataItem).dataMap
        val json = dataMap.getString(WearablePaths.DATA_KEY)
        if (json != null) {
            try {
                val data = Json.decodeFromString<WearableOperationData>(json)
                _uiState.value = data
            } catch (e: Exception) {
                Log.e(TAG, "JSON decode error", e)
            }
        }
    }

    fun toggleEvidence(evidenceId: String, currentState: String) {
        val nextState = when (currentState) {
            "NEUTRAL" -> "POSITIVE"
            "POSITIVE" -> "NEGATIVE"
            else -> "NEUTRAL"
        }
        
        val message = "$evidenceId:$nextState"
        viewModelScope.launch {
            try {
                val nodes = Wearable.getNodeClient(getApplication<Application>()).connectedNodes.await()
                nodes.forEach { node ->
                    Wearable.getMessageClient(getApplication<Application>())
                        .sendMessage(node.id, WearablePaths.EVIDENCE_TOGGLE, message.toByteArray())
                        .await()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error sending toggle message", e)
            }
        }
    }

    override fun onCleared() {
        dataClient.removeListener(this)
        super.onCleared()
    }
}
