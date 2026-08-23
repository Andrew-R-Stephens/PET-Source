package com.tritiumgaming.shared.data.wearable.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources
import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.operation.model.EvidenceValidationType
import com.tritiumgaming.shared.data.wearable.WearablePaths
import com.tritiumgaming.shared.data.wearable.model.WearableOperationData
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.json.Json

class WearableRepositoryImpl(private val context: Context) : WearableRepository {

    private val dataClient: DataClient by lazy { Wearable.getDataClient(context) }
    private val messageClient by lazy { Wearable.getMessageClient(context) }
    private val nodeClient by lazy { Wearable.getNodeClient(context) }
    private val TAG = "WearableRepository"

    override fun observeOperationData(): Flow<WearableOperationData> = callbackFlow {
        val listener = DataClient.OnDataChangedListener { dataEvents ->
            dataEvents.forEach { event ->
                if (event.type == DataEvent.TYPE_CHANGED && event.dataItem.uri.path == WearablePaths.OPERATION_STATE) {
                    val dataMap = DataMapItem.fromDataItem(event.dataItem).dataMap
                    val json = dataMap.getString(WearablePaths.DATA_KEY)
                    if (json != null) {
                        try {
                            val data = Json.decodeFromString<WearableOperationData>(json)
                            trySend(data)
                        } catch (e: Exception) {
                            Log.e(TAG, "JSON decode error", e)
                        }
                    }
                }
            }
        }

        dataClient.addListener(listener)

        // Initial fetch
        val uri = Uri.Builder().scheme("wear").path(WearablePaths.OPERATION_STATE).build()
        dataClient.getDataItem(uri).addOnSuccessListener { item ->
            if (item != null) {
                val dataMap = DataMapItem.fromDataItem(item).dataMap
                val json = dataMap.getString(WearablePaths.DATA_KEY)
                if (json != null) {
                    try {
                        val data = Json.decodeFromString<WearableOperationData>(json)
                        trySend(data)
                    } catch (e: Exception) {
                        Log.e(TAG, "Initial JSON decode error", e)
                    }
                }
            }
        }

        awaitClose { dataClient.removeListener(listener) }
    }

    override suspend fun pushOperationData(data: WearableOperationData) {
        try {
            val json = Json.encodeToString(data)
            val putDataMapReq = PutDataMapRequest.create(WearablePaths.OPERATION_STATE)
            putDataMapReq.dataMap.putString(WearablePaths.DATA_KEY, json)
            putDataMapReq.dataMap.putLong("timestamp", System.currentTimeMillis())
            val putDataReq = putDataMapReq.asPutDataRequest()
            
            dataClient.putDataItem(putDataReq).await()
            Log.d(TAG, "Pushed operation data to Data Layer")
        } catch (e: Exception) {
            Log.e(TAG, "Error pushing operation data", e)
        }
    }

    override suspend fun sendToggleMessage(
        evidenceType: EvidenceResources.EvidenceIdentifier,
        newState: EvidenceValidationType
    ) {
        val message = "$evidenceType:$newState"
        try {
            val nodes = nodeClient.connectedNodes.await()
            nodes.forEach { node ->
                messageClient.sendMessage(
                    node.id,
                    WearablePaths.EVIDENCE_TOGGLE,
                    message.toByteArray()).await()
            }
            Log.d(TAG, "Sent toggle message: $message")
        } catch (e: Exception) {
            Log.e(TAG, "Error sending toggle message", e)
        }
    }

    override suspend fun sendSanityUpdateMessage(sanityLevel: Float) {
        val message = sanityLevel.toString()
        try {
            val nodes = nodeClient.connectedNodes.await()
            nodes.forEach { node ->
                messageClient.sendMessage(
                    node.id,
                    WearablePaths.SANITY_UPDATE,
                    message.toByteArray()).await()
            }
            Log.d(TAG, "Sent sanity update message: $message")
        } catch (e: Exception) {
            Log.e(TAG, "Error sending sanity update message", e)
        }
    }
}
