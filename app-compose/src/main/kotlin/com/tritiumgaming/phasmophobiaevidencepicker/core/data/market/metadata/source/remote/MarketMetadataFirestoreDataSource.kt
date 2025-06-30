package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.metadata.source.remote

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.metadata.dto.MarketMetadataDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MarketMetadataFirestoreDataSource(
    private val firestore: FirebaseFirestore
): MarketMetadataDataSource {

    val storeCollectionRef: CollectionReference
        get() = firestore.collection(COLLECTION_STORE)

    private val metadataDocumentRef: DocumentReference = storeCollectionRef
        .document(DOCUMENT_METADATA)

    override suspend fun fetch(): Result<MarketMetadataDto> = withContext(Dispatchers.IO) {

        metadataDocumentRef.get(/*Source.SERVER*/)
            .await()
            .let { documentSnapshot ->
                try {
                    var versionCode = 0

                    documentSnapshot.data?.let { map ->
                        map[FIELD_VERSION_CODE]?.let { versionCode = it as Int }
                    }

                    Result.success(
                        MarketMetadataDto(
                            versionCode = versionCode
                        )
                    )

                } catch (e: Exception) {
                    Log.d("Firestore", "Error obtaining Metadata!")
                    e.printStackTrace()
                    Result.failure(Exception("Error obtaining Metadata!"))
                }
            }

    }

    companion object {

        private const val COLLECTION_STORE = "Store"
        private const val DOCUMENT_METADATA: String = "Metadata"

        private const val FIELD_VERSION_CODE: String = "versionCode"

    }

}