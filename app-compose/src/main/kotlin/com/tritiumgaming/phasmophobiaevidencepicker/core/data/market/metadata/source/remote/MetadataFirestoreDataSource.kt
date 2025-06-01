package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.metadata.source.remote

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class MetadataFirestoreDataSource(
    private val firestore: FirebaseFirestore
) {

    val storeCollectionRef: CollectionReference
        get() = firestore.collection(COLLECTION_STORE)

    private val metadataDocumentRef: DocumentReference = storeCollectionRef
        .document(DOCUMENT_METADATA)

    suspend fun buildMetadataDocument(
        storeCollectionRef: CollectionReference
    ): Result<String> {

        return try {
            metadataDocumentRef
                .get()
                .addOnSuccessListener { documentSnapshot: DocumentSnapshot ->

                    val creditsMap: MutableMap<Any, Any> = HashMap()
                    if (documentSnapshot[FIELD_VERSION_CODE] == null) {
                        creditsMap[FIELD_VERSION_CODE] = 0
                    }

                    documentSnapshot.reference.set(creditsMap, SetOptions.merge())
                        .addOnSuccessListener {
                            Log.d("Firestore", "Database versionCode successfully INITIALIZED!")
                        }
                        .addOnFailureListener { e: Exception ->
                            Log.e("Firestore", "Database versionCode failed INITIALIZATION")
                            e.printStackTrace()
                        }
                        .addOnCompleteListener {
                            Log.d("Firestore", "Database versionCode  INITIALIZATION process complete!")
                        }
                }
                .addOnFailureListener { e: Exception ->
                    e.printStackTrace()
                }
                .await()

            Result.success("Database versionCode successfully INITIALIZED!")
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(Exception("Database versionCode INITIALIZATION process complete!", e))
        }

    }

    companion object {
        private const val COLLECTION_STORE = "Store"
        private const val DOCUMENT_METADATA: String = "Metadata"
        private const val FIELD_VERSION_CODE: String = "versionCode"

    }
}