package com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.remote

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.Transaction
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.dto.AccountCreditTransactionDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.dto.AccountCreditsDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.dto.AccountPaletteDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.dto.AccountTypographyDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.dto.MarketAgreementDto
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreAccountRemoteDataSource(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {
    val currentAuthUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    private val userCollectionRef: CollectionReference
        get() = firestore.collection(COLLECTION_USERS)

    fun getUserDocumentRef(
        uid: String? = currentAuthUser?.uid
    ): DocumentReference? {
        return uid?.let { userCollectionRef.document(it) }
    }

    private val accountCollectionRef: CollectionReference?
        get() = getUserDocumentRef()
            ?.collection(COLLECTION_ACCOUNT)

    private val creditsDocumentRef: DocumentReference?
        get() = accountCollectionRef
            ?.document(DOCUMENT_CREDITS)

    private val preferencesDocumentRef: DocumentReference?
        get() = accountCollectionRef
            ?.document(DOCUMENT_PREFERENCES)

    private val transactionHistoryDocumentRef: DocumentReference?
        get() = accountCollectionRef
            ?.document(DOCUMENT_TRANSACTION_HISTORY)

    private val purchaseHistoryCollectionRef: CollectionReference?
        get() = transactionHistoryDocumentRef
            ?.collection(COLLECTION_PURCHASE_HISTORY)

    private val purchaseDocumentRef: DocumentReference?
        get() = purchaseHistoryCollectionRef
            ?.document(DOCUMENT_PURCHASED_ITEM)

    private val unlockHistoryCollectionRef: CollectionReference?
        get() = transactionHistoryDocumentRef
            ?.collection(COLLECTION_UNLOCK_HISTORY)

    private val unlockedDocumentRef: DocumentReference?
        get() = unlockHistoryCollectionRef
            ?.document(DOCUMENT_UNLOCKED_ITEM)

    suspend fun setMarketplaceAgreementState(
        marketAgreementDto: MarketAgreementDto
    ): Result<MarketAgreementDto> {

        return try {

            val docRef = purchaseDocumentRef

            if(docRef == null)
                return Result.failure(Exception("User Marketplace Preferences document null!"))

            firestore.runTransaction { transaction ->

                val snapshot = transaction.get(docRef)
                if(!snapshot.exists()) {
                    val data: MutableMap<String, Any> = HashMap()
                    data[FIELD_MARKETPLACE_AGREEMENT_SHOWN] = marketAgreementDto.isAgreementShown

                    transaction.set(docRef, data)

                    marketAgreementDto
                }

            }.await()

            return Result.success(marketAgreementDto)

        } catch (e: Exception) {
            Log.e("Firestore", "Error marketplace agreement modification unsuccessful.", e)
            Result.failure(e)
        }

    }

    suspend fun getMarketplaceAgreementState(): Result<MarketAgreementDto> {

        return try {

            val docRef = preferencesDocumentRef

            if(docRef == null)
                return Result.failure(Exception("User Marketplace Preferences document null!"))

            val agreementDto = firestore.runTransaction { transaction ->

                val snapshot = transaction.get(docRef)
                if(snapshot.exists()) {
                    MarketAgreementDto(
                        isAgreementShown =
                            snapshot.getBoolean(FIELD_MARKETPLACE_AGREEMENT_SHOWN) == true
                    )
                } else { MarketAgreementDto() }

            }.await()

            Result.success(agreementDto)

        } catch (e: Exception) {
            Log.e("Firestore", "Error marketplace agreement modification unsuccessful.", e)
            Result.failure(e)
        }

    }

    suspend fun addCredits(
        creditTransaction: AccountCreditTransactionDto
    ): Result<AccountCreditsDto> {

        return try {

            val docRef = creditsDocumentRef

            if (docRef == null)
                return Result.failure(Exception("User Account Credits document null!"))

            // Update document
            firestore.runTransaction { transaction ->

                transaction.getOrCreateCreditsDocument(docRef)

                val updates = hashMapOf<String, Any>(
                    FIELD_CREDITS_EARNED to FieldValue.increment(creditTransaction.credits)
                )
                transaction.update(docRef, updates)

            }.await()

            // Get updated document
            val updatedSnapshot = docRef.get().await()
            if (!updatedSnapshot.exists()) {
                return Result.failure(
                    FirebaseFirestoreException(
                        "Document ${docRef.path} unexpectedly not found after update.",
                        FirebaseFirestoreException.Code.NOT_FOUND
                    )
                )
            }
            val updatedDto = AccountCreditsDto(
                earnedCredits = updatedSnapshot.getLong(FIELD_CREDITS_EARNED) ?: 0L,
                spentCredits = updatedSnapshot.getLong(FIELD_CREDITS_SPENT) ?: 0L
            )
            Result.success(updatedDto)

        } catch (e: Exception) {
            Log.e("Firestore", "Error adding credits", e)
            Result.failure(e)
        }

    }

    suspend fun removeCredits(
        creditTransaction: AccountCreditTransactionDto
    ): Result<AccountCreditsDto> {

        return try {

            val docRef = creditsDocumentRef

            if (docRef == null)
                return Result.failure(Exception("User Account Credits document null!"))

            // Update document
            firestore.runTransaction { transaction ->

                val snapshot = transaction.getOrCreateCreditsDocument(docRef)

                val currentCredits = AccountCreditsDto(
                    earnedCredits = snapshot.getLong(FIELD_CREDITS_EARNED) ?: 0L,
                    spentCredits = snapshot.getLong(FIELD_CREDITS_SPENT) ?: 0L
                )

                // Check if there are enough credits to remove
                if (currentCredits.earnedCredits >= creditTransaction.credits) {
                    val updates = hashMapOf<String, Any>(
                        FIELD_CREDITS_EARNED to FieldValue.increment(-creditTransaction.credits),
                        FIELD_CREDITS_SPENT to FieldValue.increment(creditTransaction.credits)
                    )
                    transaction.update(docRef, updates)
                }

            }.await()

            // Get updated document
            val updatedSnapshot = docRef.get().await()

            if (!updatedSnapshot.exists()) {
                return Result.failure(
                    FirebaseFirestoreException(
                        "Document ${docRef.path} unexpectedly not found after update.",
                        FirebaseFirestoreException.Code.NOT_FOUND
                    )
                )
            }
            val updatedDto = AccountCreditsDto(
                earnedCredits = updatedSnapshot.getLong(FIELD_CREDITS_EARNED) ?: 0L,
                spentCredits = updatedSnapshot.getLong(FIELD_CREDITS_SPENT) ?: 0L
            )
            Result.success(updatedDto)

        } catch (e: Exception) {
            Log.e("Firestore", "Error adding credits", e)
            Result.failure(e)
        }

    }

    private fun Transaction.getOrCreateCreditsDocument(
        reference: DocumentReference
    ): DocumentSnapshot {

        val snapshot = get(reference)

        if (!snapshot.exists()) {
            println("Credits document ${reference.path} not found. Creating with default values.")
            val initialCreditsData = AccountCreditsDto(
                earnedCredits = 0L,
                spentCredits = 0L
            )
            set(reference, initialCreditsData) // Create the document in the transaction
        }

        return snapshot
    }

    fun observeCreditsDocument(): Flow<Result<AccountCreditsDto>> =
        callbackFlow {

            creditsDocumentRef?.addSnapshotListener { snapshot, error ->
                error?.let {
                    this.close(it)
                }

                snapshot?.let { it ->
                    val data = AccountCreditsDto(
                        earnedCredits = it.getLong(FIELD_CREDITS_EARNED) ?: 0L,
                        spentCredits = it.getLong(FIELD_CREDITS_SPENT) ?: 0L
                    )
                    this.trySend(Result.success(data))
                }

                this.trySend(Result.failure(Exception("Document not found or deleted.")))

            }

            awaitClose { this.cancel() }
        }

    suspend fun addUnlockedDocuments(
        unlockUUIDs: ArrayList<String>?,
        type: String
    ): Result<String> {

        val docRef = unlockHistoryCollectionRef

        if(docRef == null)
            return Result.failure(Exception("Unlock history collection not found!"))
        if(unlockUUIDs == null)
            return Result.failure(Exception("No UUIDs found!"))

        return try {


            val documentData: MutableMap<String, Any> = HashMap()
            documentData[FIELD_TYPE] = type
            documentData[FIELD_DATE_UNLOCKED] = Timestamp.Companion.now()

            for (uuid in unlockUUIDs) {
                val purchasedDocument = docRef.document(uuid)
                purchasedDocument.set(documentData, SetOptions.merge()).await()
            }
            Result.success("Unlocked documents GENERATED / LOCATED!")
        } catch (e: Exception) {
            Log.e("Firestore", "Unlocked document of could NOT be GENERATED / LOCATED!")
            Result.failure(e)
        }

    }

    suspend fun fetchUnlockedPaletteDocuments(): Result<List<AccountPaletteDto>> {

        val docRef = unlockHistoryCollectionRef

        if(docRef == null)
            return Result.failure(Exception("Unlock history collection not found!"))

        val accountPaletteDtoList = mutableListOf<AccountPaletteDto>()

        val snapshot = docRef.get().await()
        snapshot.documents.forEach { documentSnapshot ->

            val reference = documentSnapshot.reference
            val uid = reference.id

            accountPaletteDtoList.add(AccountPaletteDto(uid))
        }

        return Result.success(accountPaletteDtoList)
    }

    suspend fun fetchUnlockedTypographyDocuments(): Result<List<AccountTypographyDto>> {

        val docRef = unlockHistoryCollectionRef

        if(docRef == null)
            return Result.failure(Exception("Unlock history collection not found!"))

        val accountTypographyDtoList = mutableListOf<AccountTypographyDto>()

        val snapshot = docRef.get().await()
        snapshot.documents.forEach { documentSnapshot ->

            val reference = documentSnapshot.reference
            val uid = reference.id

            accountTypographyDtoList.add(AccountTypographyDto(uid))
        }

        return Result.success(accountTypographyDtoList)
    }

    fun observeUnlockedPaletteDocuments(): Flow<Result<List<AccountPaletteDto>>> =

        callbackFlow {

            val docRef = unlockHistoryCollectionRef

            if (docRef == null) {
                trySend(Result.failure(Exception("Unlock history collection not found!")))
                close()
                return@callbackFlow
            }

            // Keep track of the listener registration to remove it when the flow is cancelled
            var listenerRegistration: ListenerRegistration? = null

            try {
                listenerRegistration = docRef
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) {
                            val customError = FirebaseFirestoreException(
                                "Error listening to unlocked palette documents: ${error.message}",
                                error.code
                            )
                            trySend(Result.failure(customError))

                            return@addSnapshotListener
                        }

                        if (snapshot != null) {
                            val accountPaletteDtoList = mutableListOf<AccountPaletteDto>()

                            snapshot.documents.forEach { documentSnapshot ->
                                val uid = documentSnapshot.id
                                accountPaletteDtoList.add(AccountPaletteDto(uuid = uid))
                            }

                            trySend(Result.success(accountPaletteDtoList))

                        } else {
                            trySend(Result.success(emptyList()))
                        }
                    }
            } catch (e: Exception) {
                trySend(Result.failure(Exception("Failed to attach snapshot listener: ${e.message}")))
                close(e)
            }

            awaitClose {
                listenerRegistration?.remove()
                println("SnapshotListener for UnlockedPaletteDocuments removed.")
            }
    }

    fun observeUnlockedTypographyDocuments(): Flow<Result<List<AccountTypographyDto>>> =

        callbackFlow {

            val docRef = unlockHistoryCollectionRef

            if (docRef == null) {
                trySend(Result.failure(Exception("Unlock history collection not found!")))
                close()
                return@callbackFlow
            }

            // Keep track of the listener registration to remove it when the flow is cancelled
            var listenerRegistration: ListenerRegistration? = null

            try {
                listenerRegistration = docRef
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) {
                            val customError = FirebaseFirestoreException(
                                "Error listening to unlocked typography documents: ${error.message}",
                                error.code
                            )
                            trySend(Result.failure(customError))

                            return@addSnapshotListener
                        }

                        if (snapshot != null) {
                            val accountTypographyDtoList = mutableListOf<AccountTypographyDto>()

                            snapshot.documents.forEach { documentSnapshot ->
                                val uid = documentSnapshot.id
                                accountTypographyDtoList.add(AccountTypographyDto(uuid = uid))
                            }

                            trySend(Result.success(accountTypographyDtoList))

                        } else {
                            trySend(Result.success(emptyList()))
                        }
                    }
            } catch (e: Exception) {
                trySend(Result.failure(Exception("Failed to attach snapshot listener: ${e.message}")))
                close(e)
            }

            awaitClose {
                listenerRegistration?.remove()
                println("SnapshotListener for UnlockedTypographyDocuments removed.")
            }
    }

    suspend fun addPurchaseDocument(
        orderID: String
    ): Result<String> {

        val docRef = purchaseHistoryCollectionRef

        if(docRef == null)
            return Result.failure(Exception("Purchase history collection not found!"))

        val purchaseReferenceDoc = purchaseDocumentRef
        if(purchaseReferenceDoc == null)
            return Result.failure(Exception("Purchase history document not found!"))

        val documentData: MutableMap<String, Any> = HashMap()
        documentData[FIELD_PURCHASE_REFERENCE] = purchaseReferenceDoc.id
        documentData[FIELD_ORDER_ID] = orderID
        documentData[FIELD_DATE_PURCHASED] = Timestamp.Companion.now()

        return try {
            docRef.add(documentData).await()
            Result.success("Purchase document of ${ purchaseReferenceDoc.id } GENERATED / LOCATED!")
        } catch (e: Exception) {
            Log.e("Firestore", "Purchase document of ${ purchaseReferenceDoc.id } " +
                    "could NOT be GENERATED / LOCATED!", e)
            Result.failure(e)
        }

    }

    /*private fun buildAccountCreditDocument(): DocumentReference {

        creditsDocumentRef
            .get()
            .addOnSuccessListener { documentSnapshot: DocumentSnapshot ->

                val creditsMap: MutableMap<Any, Any> = HashMap()

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

    }*/

    /*private fun buildAccountTransactionHistoryDocument(): DocumentReference {

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
    }*/

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

        // User Collection
        private const val COLLECTION_USERS = "Users"

        // Account Collection
        private const val COLLECTION_ACCOUNT = "Account"

        // Account Credits Document
        private const val DOCUMENT_CREDITS: String = "Credits"
        private const val FIELD_CREDITS_EARNED: String = "earnedCredits"
        private const val FIELD_CREDITS_SPENT: String = "spentCredits"

    }

}
