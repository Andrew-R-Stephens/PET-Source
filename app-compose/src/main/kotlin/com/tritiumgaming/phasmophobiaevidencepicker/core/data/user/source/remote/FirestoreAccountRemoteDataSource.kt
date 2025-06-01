package com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.remote

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class FirestoreAccountRemoteDataSource {

    private fun getAccountCollectionRef(
        userDocumentRef: DocumentReference?
    ): CollectionReference? {
        return userDocumentRef
            ?.collection(COLLECTION_ACCOUNT)
    }

    private fun getCreditsDocumentRef(
        userDocumentRef: DocumentReference?
    ): DocumentReference? {
        return getAccountCollectionRef(userDocumentRef)
            ?.document(DOCUMENT_CREDITS)
    }

    private fun getPreferencesDocumentRef(
        userDocumentRef: DocumentReference?
    ): DocumentReference? {
        return getAccountCollectionRef(userDocumentRef)
            ?.document(DOCUMENT_PREFERENCES)
    }

    private fun getTransactionHistoryDocumentRef(
        userDocumentRef: DocumentReference?
    ): DocumentReference? {
        return getAccountCollectionRef(userDocumentRef)
            ?.document(DOCUMENT_TRANSACTION_HISTORY)
    }

    private fun getPurchaseHistoryCollectionRef(
        userDocumentRef: DocumentReference?
    ): CollectionReference? {
        return getTransactionHistoryDocumentRef(userDocumentRef)
            ?.collection(COLLECTION_PURCHASE_HISTORY)
    }

    private fun getPurchaseDocumentRef(
        userDocumentRef: DocumentReference?,
    ): DocumentReference? {
        return getPurchaseHistoryCollectionRef(userDocumentRef)
            ?.document(DOCUMENT_PURCHASED_ITEM)
    }

    private fun getUnlockHistoryCollectionRef(
        userDocumentRef: DocumentReference?,
    ): CollectionReference? {
        return getTransactionHistoryDocumentRef(userDocumentRef)
            ?.collection(COLLECTION_UNLOCK_HISTORY)
    }

    private fun getUnlockedDocumentRef(
        userDocumentRef: DocumentReference?,
    ): DocumentReference? {
        return getUnlockHistoryCollectionRef(userDocumentRef)
            ?.document(DOCUMENT_UNLOCKED_ITEM)
    }

    suspend fun setMarketplaceAgreementState(
        userDocumentRef: DocumentReference,
        shown: Boolean
    ): Result<String> {

        val preferencesDocument = getPreferencesDocumentRef(userDocumentRef)
            ?: buildAccountPreferencesDocument(userDocumentRef)

        val data: MutableMap<String, Any> = HashMap()
        data[FIELD_MARKETPLACE_AGREEMENT_SHOWN] = shown

        return try {
            preferencesDocument.update(data).await()
            Result.success("Marketplace Agreement set successfully!")
        } catch (e: Exception) {
            Log.e("Firestore", "Error marketplace agreement modification unsuccessful.", e)
            Result.failure(e)
        }

    }

    suspend fun addCredits(
        userDocumentRef: DocumentReference,
        creditAmount: Long
    ): Result<String> {

        val creditsDocument = getCreditsDocumentRef(userDocumentRef)
            ?: buildAccountCreditDocument(userDocumentRef)

        val data: MutableMap<String, Any> = HashMap()
        data[FIELD_CREDITS_EARNED] = FieldValue.increment(creditAmount)

        return try {
            creditsDocument.update(data).await()
            Result.success("Credits added successfully!")
        } catch (e: Exception) {
            Log.e("Firestore", "Error adding credits", e)
            Result.failure(e)
        }
    }

    suspend fun removeCredits(
        userDocumentRef: DocumentReference,
        creditAmount: Long
    ): Result<String> {

        val creditsDocument = getCreditsDocumentRef(userDocumentRef)
            ?: buildAccountCreditDocument(userDocumentRef)

        return try {
            creditsDocument
                .get()
                .addOnCompleteListener { task: Task<DocumentSnapshot> ->
                    val storedCredits = task.result.getLong(FIELD_CREDITS_EARNED)
                    if (storedCredits != null && storedCredits < creditAmount) {
                        return@addOnCompleteListener
                    }

                    val data: MutableMap<String, Any> = HashMap()
                    data[FIELD_CREDITS_EARNED] = FieldValue.increment(-creditAmount)
                    data[FIELD_CREDITS_SPENT] = FieldValue.increment(creditAmount)

                    task.result.reference.update(data)
                }.await()
            Result.success("Credits added successfully!")
        } catch (e: Exception) {
            Log.e("Firestore", "Error adding credits", e)
            Result.failure(e)
        }

    }

    suspend fun addPurchaseDocument(
        userDocumentRef: DocumentReference,
        orderID: String
    ): Result<String> {
        val purchaseHistoryCollection = getPurchaseHistoryCollectionRef(userDocumentRef)
        if(purchaseHistoryCollection == null)
            return Result.failure(Exception("Purchase history collection not found!"))

        val purchaseReferenceDoc = getPurchaseDocumentRef(userDocumentRef)
        if(purchaseReferenceDoc == null)
            return Result.failure(Exception("Purchase history document not found!"))

        val documentData: MutableMap<String, Any> = HashMap()
        documentData[FIELD_PURCHASE_REFERENCE] = purchaseReferenceDoc.id
        documentData[FIELD_ORDER_ID] = orderID
        documentData[FIELD_DATE_PURCHASED] = Timestamp.Companion.now()

        return try {
            purchaseHistoryCollection.add(documentData).await()
            Result.success("Purchase document of ${ purchaseReferenceDoc.id } GENERATED / LOCATED!")
        } catch (e: Exception) {
            Log.e("Firestore", "Purchase document of ${ purchaseReferenceDoc.id } " +
                    "could NOT be GENERATED / LOCATED!", e)
            Result.failure(e)
        }

    }

    suspend fun addUnlockedDocument(
        userDocumentRef: DocumentReference?,
        unlockUUID: String?,
        type: String
    ): Result<String> {

        val unlockHistoryCollection = getUnlockHistoryCollectionRef(userDocumentRef)
        if(unlockHistoryCollection == null)
            return Result.failure(Exception("Unlock history collection not found!"))
        if(unlockUUID == null)
            return Result.failure(Exception("No UUIDs found!"))

        val unlockDocument = unlockHistoryCollection.document(unlockUUID)

        val documentData: MutableMap<String, Any> = HashMap()
        documentData[FIELD_TYPE] = type
        documentData[FIELD_DATE_UNLOCKED] = Timestamp.Companion.now()

        return try {
            unlockDocument.set(documentData, SetOptions.merge()).await()
            Result.success("Unlocked document of $unlockUUID GENERATED / LOCATED!")
        } catch (e: Exception) {
            Log.e("Firestore",
                "Unlocked document of $unlockUUID could NOT be GENERATED / LOCATED!")
            Result.failure(e)
        }

    }

    suspend fun addUnlockedDocuments(
        userDocumentRef: DocumentReference?,
        unlockUUIDs: ArrayList<String>?,
        type: String
    ): Result<String> {

        val unlockHistoryCollection = getUnlockHistoryCollectionRef(userDocumentRef)
        if(unlockHistoryCollection == null)
            return Result.failure(Exception("Unlock history collection not found!"))
        if(unlockUUIDs == null)
            return Result.failure(Exception("No UUIDs found!"))

        val documentData: MutableMap<String, Any> = HashMap()
        documentData[FIELD_TYPE] = type
        documentData[FIELD_DATE_UNLOCKED] = Timestamp.Companion.now()

        return try {
            for (uuid in unlockUUIDs) {
                val purchasedDocument = unlockHistoryCollection.document(uuid)
                purchasedDocument.set(documentData, SetOptions.merge()).await()
            }
            Result.success("Unlocked documents GENERATED / LOCATED!")
        } catch (e: Exception) {
            Log.e("Firestore", "Unlocked document of could NOT be GENERATED / LOCATED!")
            Result.failure(e)
        }

    }

    private fun buildAccountCreditDocument(
        creditsDocumentRef: DocumentReference
    ): DocumentReference {
        val creditsMap: MutableMap<Any, Any> = HashMap()

        creditsDocumentRef
            .get()
            .addOnSuccessListener { documentSnapshot: DocumentSnapshot ->
                if (documentSnapshot[FIELD_CREDITS_EARNED] == null) {
                    creditsMap[FIELD_CREDITS_EARNED] = 0
                }
                if (documentSnapshot[FIELD_CREDITS_SPENT] == null) {
                    creditsMap[FIELD_CREDITS_SPENT] = 0
                }
                documentSnapshot.reference.set(creditsMap, SetOptions.merge())
                    .addOnSuccessListener { unused: Void? ->
                        Log.d("Firestore",
                            "$DOCUMENT_CREDITS successfully INITIALIZED!") }
                    .addOnFailureListener { e: Exception ->
                        Log.e("Firestore",
                            "$DOCUMENT_CREDITS failed INITIALIZATION")
                        e.printStackTrace() }
                    .addOnCompleteListener { task: Task<Void?>? ->
                        Log.d("Firestore",
                            "$DOCUMENT_CREDITS INITIALIZATION process complete!") }
            }
            .addOnFailureListener { obj: Exception -> obj.printStackTrace() }

        return creditsDocumentRef
    }

    private fun buildAccountPreferencesDocument(
        preferencesDocumentRef: DocumentReference
    ): DocumentReference {
        val preferencesMap: MutableMap<Any, Any> = HashMap()

        preferencesDocumentRef
            .get()
            .addOnSuccessListener { documentSnapshot: DocumentSnapshot ->
                if (documentSnapshot[FIELD_MARKETPLACE_AGREEMENT_SHOWN] == null) {
                    preferencesMap[FIELD_MARKETPLACE_AGREEMENT_SHOWN] = false
                }
                documentSnapshot.reference.set(preferencesMap, SetOptions.merge())
                    .addOnSuccessListener {
                        Log.d("Firestore",
                            "$DOCUMENT_PREFERENCES successfully INITIALIZED!") }
                    .addOnFailureListener { e: Exception ->
                        Log.e("Firestore",
                            "$DOCUMENT_PREFERENCES failed INITIALIZATION")
                        e.printStackTrace() }
                    .addOnCompleteListener { task: Task<Void?>? ->
                        Log.d("Firestore",
                            "$DOCUMENT_PREFERENCES INITIALIZATION process complete!") }
            }
            .addOnFailureListener { obj: Exception -> obj.printStackTrace() }

        return preferencesDocumentRef
    }

    private fun buildAccountTransactionHistoryDocument(
        transactionHistoryDocumentRef: DocumentReference
    ): DocumentReference {

        transactionHistoryDocumentRef
            .get()
            .addOnSuccessListener { documentSnapshot: DocumentSnapshot ->

                val emptyMap: Map<Any, Any> = HashMap()

                documentSnapshot.reference.set(emptyMap, SetOptions.merge())
                    .addOnSuccessListener { unused: Void? ->
                        Log.d("Firestore",
                            "$DOCUMENT_TRANSACTION_HISTORY successfully INITIALIZED!") }
                    .addOnFailureListener { e: Exception ->
                        Log.e("Firestore",
                            "$DOCUMENT_TRANSACTION_HISTORY failed INITIALIZATION")
                        e.printStackTrace() }
                    .addOnCompleteListener { task: Task<Void?>? ->
                        Log.d("Firestore",
                            "$DOCUMENT_TRANSACTION_HISTORY INITIALIZATION process complete!") }
            }
            .addOnFailureListener { obj: Exception -> obj.printStackTrace() }

        return transactionHistoryDocumentRef
    }

    private companion object {
        private const val COLLECTION_PURCHASE_HISTORY = "PurchaseHistory"

        private const val DOCUMENT_PREFERENCES: String = "Preferences"
        private const val DOCUMENT_TRANSACTION_HISTORY = "TransactionHistory"

        private const val COLLECTION_UNLOCK_HISTORY = "UnlockHistory"
        private const val DOCUMENT_PURCHASED_ITEM = "PurchaseItem"
        private const val DOCUMENT_UNLOCKED_ITEM = "UnlockedItem"

        private const val FIELD_MARKETPLACE_AGREEMENT_SHOWN: String = "marketplaceAgreementShown"
        private const val FIELD_DATE_PURCHASED = "datePurchased"
        private const val FIELD_PURCHASE_REFERENCE = "product_ref"
        private const val FIELD_ORDER_ID = "order_id"
        private const val FIELD_DATE_UNLOCKED = "dateUnlocked"
        private const val FIELD_TYPE = "type"



        // Account Collection
        private const val COLLECTION_ACCOUNT = "Account"

        // Account Credits Document
        private const val DOCUMENT_CREDITS: String = "Credits"
        private const val FIELD_CREDITS_SPENT: String = "spentCredits"
        private const val FIELD_CREDITS_EARNED: String = "earnedCredits"
    }
}
