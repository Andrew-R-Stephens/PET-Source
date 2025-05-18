package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.api.firestore.transactions.store.metadata

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.SetOptions
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.api.firestore.transactions.store.FirestoreMarketplace.Companion.storeCollection

class FirestoreMetadataProperties {
    companion object {

        private const val DOCUMENT_METADATA: String = "Metadata"

        private const val FIELD_VERSION_CODE: String = "versionCode"

        val metadataDocument: DocumentReference
            @Throws(Exception::class)
            get() = storeCollection.document(DOCUMENT_METADATA)

        @Throws(Exception::class)
        fun init() {
            val creditsMap: MutableMap<Any, Any> = HashMap()

            metadataDocument
                .get()
                .addOnSuccessListener { documentSnapshot: DocumentSnapshot ->
                    if (documentSnapshot[FIELD_VERSION_CODE] == null) {
                        creditsMap[FIELD_VERSION_CODE] = 0
                    }

                    documentSnapshot.reference.set(creditsMap, SetOptions.merge())
                        .addOnSuccessListener {
                            Log.d(
                                "Firestore",
                                "Database versionCode successfully INITIALIZED!"
                            )
                        }
                        .addOnFailureListener { e: Exception ->
                            Log.d("Firestore",
                                "Database versionCode failed INITIALIZATION")
                            e.printStackTrace()
                        }
                        .addOnCompleteListener {
                            Log.d(
                                "Firestore",
                                "Database versionCode  INITIALIZATION process complete!"
                            )
                        }
                }
                .addOnFailureListener { obj: Exception -> obj.printStackTrace() }
        }

    }
}
