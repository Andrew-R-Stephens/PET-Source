package com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.remote2

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.api.OnFirestoreProcessListener
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.NullFirebaseUserException

class FirestoreUserRemoteDataSource(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
): FirestoreUserDataSource {

    private val userCollection: CollectionReference
        get() = firestore.collection(COLLECTION_USERS)

    private val currentFirebaseAuthUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    private val userDocument: DocumentReference?
        get() = currentFirebaseAuthUser?.uid?.let { uid -> userCollection.document(uid) }

    private val accountCollection: CollectionReference?
        get() = userDocument?.collection(COLLECTION_ACCOUNT)

    private val creditsDocument: DocumentReference?
        get() = accountCollection?.document(DOCUMENT_CREDITS)

    private val preferencesDocument: DocumentReference?
        get() = accountCollection?.document(DOCUMENT_PREFERENCES)

    private val transactionHistoryDocument: DocumentReference?
        get() = accountCollection?.document(DOCUMENT_TRANSACTION_HISTORY)

    private val purchaseHistoryCollection: CollectionReference?
        get() = transactionHistoryDocument?.collection(COLLECTION_PURCHASE_HISTORY)

    private val purchaseDocument: DocumentReference?
        get() = purchaseHistoryCollection?.document(DOCUMENT_PURCHASED_ITEM)

    private val unlockHistoryCollection: CollectionReference?
        get() = transactionHistoryDocument?.collection(COLLECTION_UNLOCK_HISTORY)

    private val unlockedDocument: DocumentReference?
        get() = unlockHistoryCollection?.document(DOCUMENT_UNLOCKED_ITEM)

    @Throws(Exception::class)
    override fun setMarketplaceAgreementState(
        shown: Boolean,
        listener: OnFirestoreProcessListener?) {

        val preferencesDocument = preferencesDocument
        if(preferencesDocument == null) { listener?.onFailure(); return }

        val data: MutableMap<String, Any> = HashMap()
        data[FIELD_MARKETPLACE_AGREEMENT_SHOWN] = shown

        preferencesDocument
            .update(data)
            .addOnSuccessListener { listener?.onSuccess() }
            .addOnFailureListener { listener?.onFailure() }
            .addOnCompleteListener { listener?.onComplete() }
    }

    @Throws(Exception::class)
    override fun addCredits(creditAmount: Long, listener: OnFirestoreProcessListener?) {

        val creditsDocument = creditsDocument
        if(creditsDocument == null) {
            listener?.onFailure(); return }

        val data: MutableMap<String, Any> = HashMap()
        data[FIELD_CREDITS_EARNED] = FieldValue.increment(creditAmount)

        creditsDocument
            .update(data)
            .addOnSuccessListener { listener?.onSuccess() }
            .addOnFailureListener { listener?.onFailure() }
            .addOnCompleteListener { listener?.onComplete() }
    }

    @Throws(Exception::class)
    override fun removeCredits(creditAmount: Long, listener: OnFirestoreProcessListener?) {

        val creditsDocument = creditsDocument
        if(creditsDocument == null) { listener?.onFailure(); return }

        creditsDocument
            .get()
            .addOnCompleteListener { task: Task<DocumentSnapshot> ->
                val storedCredits = task.result.getLong(FIELD_CREDITS_EARNED)
                if (storedCredits != null && storedCredits < creditAmount) {
                    listener?.onFailure()

                    return@addOnCompleteListener
                }

                val data: MutableMap<String, Any> = HashMap()
                data[FIELD_CREDITS_EARNED] = FieldValue.increment(-creditAmount)
                data[FIELD_CREDITS_SPENT] = FieldValue.increment(creditAmount)

                task.result.reference.update(data)
                    .addOnSuccessListener { result: Void? ->
                        listener?.onSuccess() }
                    .addOnFailureListener { error: Exception ->
                        listener?.onFailure()
                        error.printStackTrace() }
                    .addOnCompleteListener { result: Task<Void?>? ->
                        listener?.onComplete() }
                listener?.onComplete()
            }
    }

    @Throws(Exception::class)
    override fun addPurchaseDocument(
        purchaseReferenceDoc: DocumentReference?,
        orderID: String?,
        listener: OnFirestoreProcessListener?,
    ) {
        val purchaseHistoryCollection = purchaseHistoryCollection
        if(purchaseHistoryCollection == null || purchaseReferenceDoc == null || orderID == null) {
            listener?.onFailure(); return }

        val documentData: MutableMap<String, Any> = HashMap()
        documentData[FIELD_PURCHASE_REFERENCE] = purchaseReferenceDoc
        documentData[FIELD_ORDER_ID] = orderID
        documentData[FIELD_DATE_PURCHASED] = Timestamp.Companion.now()

        purchaseHistoryCollection.add(documentData)
            .addOnSuccessListener {
                listener?.onSuccess()
                Log.d("Firestore",
                    "Purchase document of ${ purchaseReferenceDoc.id } GENERATED / LOCATED!") }
            .addOnFailureListener { e: Exception ->
                listener?.onFailure()
                Log.d("Firestore",
                    "Purchase document of ${ purchaseReferenceDoc.id } could NOT be GENERATED / LOCATED!")
                e.printStackTrace() }
            .addOnCompleteListener {
                listener?.onComplete()
                Log.d("Firestore",
                    "Purchase document of ${ purchaseReferenceDoc.id } process complete.") }
    }

    @Throws(Exception::class)
    override fun addUnlockDocument(
        unlockUUID: String?, type: String, listener: OnFirestoreProcessListener
    ) {

        val unlockHistoryCollection = unlockHistoryCollection
        if(unlockHistoryCollection == null || unlockUUID == null) {
            listener.onFailure(); return }

        val unlockDocument = unlockHistoryCollection.document(unlockUUID)

        val documentData: MutableMap<String, Any> = HashMap()
        documentData[FIELD_TYPE] = type
        documentData[FIELD_DATE_UNLOCKED] = Timestamp.Companion.now()

        unlockDocument.set(documentData, SetOptions.merge())
            .addOnSuccessListener {
                listener.onSuccess()
                Log.d("Firestore",
                    "Unlocked document of $unlockUUID GENERATED / LOCATED!") }
            .addOnFailureListener { e: Exception ->
                listener.onFailure()
                Log.d("Firestore",
                    "Unlocked document of $unlockUUID could NOT be GENERATED / LOCATED!")
                e.printStackTrace() }
            .addOnCompleteListener {
                listener.onComplete()
                Log.d("Firestore",
                    "Unlocked document of $unlockUUID process complete.") }
    }

    @Throws(Exception::class)
    override fun addUnlockedDocuments(
        unlockUUIDs: ArrayList<String>?, type: String, listener: OnFirestoreProcessListener
    ) {
        val unlockHistoryCollection = unlockHistoryCollection
        if(unlockHistoryCollection == null || unlockUUIDs == null) {
            listener.onFailure(); return }

        val documentData: MutableMap<String, Any> = HashMap()
        documentData[FIELD_TYPE] = type
        documentData[FIELD_DATE_UNLOCKED] = Timestamp.Companion.now()

        for (uuid in unlockUUIDs) {
            val purchasedDocument = unlockHistoryCollection.document(uuid)
            purchasedDocument.set(documentData, SetOptions.merge())
                .addOnSuccessListener {
                    listener.onSuccess()
                    Log.d("Firestore",
                        "Unlocked document of $uuid GENERATED / LOCATED!") }
                .addOnFailureListener { e: Exception ->
                    listener.onFailure()
                    Log.d("Firestore",
                        "Unlocked document of $uuid could NOT be GENERATED / LOCATED!")
                    e.printStackTrace() }
                .addOnCompleteListener {
                    listener.onComplete()
                    Log.d("Firestore",
                        "Unlocked document of $uuid process complete.") }
        }
    }

    @Throws(Exception::class)
    fun buildUserDocument(): DocumentReference? {
        val user = currentFirebaseAuthUser ?: throw NullFirebaseUserException(
            "Aborting creation of new User in database.")
        val userDocument = userDocument ?: return null

        userDocument.set(HashMap<String, Any>(), SetOptions.merge())
            .addOnSuccessListener {
                Log.d("Firestore",
                    "User document ${ user.uid } successfully FOUND!") }
            .addOnFailureListener { e: Exception ->
                Log.d("Firestore",
                    "User document ${ user.uid } could NOT be GENERATED / LOCATED!")
                e.printStackTrace() }
            .addOnCompleteListener {
                Log.d("Firestore",
                    "User document ${ user.uid } process complete.")
                userDocument.get().addOnSuccessListener { docSnap: DocumentSnapshot ->
                    if (docSnap.exists()) {
                        try { initAccountCollection() }
                        catch (e: Exception) { e.printStackTrace() }
                        Log.d("Firestore",
                            "User document ${ user.uid } successfully INITIALIZED!") }
                }
            }

        return userDocument

    }

    @Throws(Exception::class)
    private fun initAccountCollection() {
        buildAccountCreditDocument()
        buildAccountPreferencesDocument()
        buildAccountTransactionHistoryDocument()
    }

    @Throws(Exception::class)
    private fun buildAccountCreditDocument() {
        val creditsDocument = creditsDocument ?: return

        val creditsMap: MutableMap<Any, Any> = HashMap()

        creditsDocument
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
                        Log.d("Firestore",
                            "$DOCUMENT_CREDITS failed INITIALIZATION")
                        e.printStackTrace() }
                    .addOnCompleteListener { task: Task<Void?>? ->
                        Log.d("Firestore",
                            "$DOCUMENT_CREDITS INITIALIZATION process complete!") }
            }
            .addOnFailureListener { obj: Exception -> obj.printStackTrace() }
    }

    @Throws(Exception::class)
    private fun buildAccountPreferencesDocument() {
        val preferencesDocument = preferencesDocument ?: return

        val preferencesMap: MutableMap<Any, Any> = HashMap()

        preferencesDocument
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
                        Log.d("Firestore",
                            "$DOCUMENT_PREFERENCES failed INITIALIZATION")
                        e.printStackTrace() }
                    .addOnCompleteListener { task: Task<Void?>? ->
                        Log.d("Firestore",
                            "$DOCUMENT_PREFERENCES INITIALIZATION process complete!") }
            }
            .addOnFailureListener { obj: Exception -> obj.printStackTrace() }
    }

    @Throws(Exception::class)
    private fun buildAccountTransactionHistoryDocument() {
        val transactionHistoryDocument = transactionHistoryDocument ?: return

        val emptyMap: Map<Any, Any> = HashMap()

        transactionHistoryDocument
            .get()
            .addOnSuccessListener { documentSnapshot: DocumentSnapshot ->
                documentSnapshot.reference.set(emptyMap, SetOptions.merge())
                    .addOnSuccessListener { unused: Void? ->
                        Log.d("Firestore",
                            "$DOCUMENT_TRANSACTION_HISTORY successfully INITIALIZED!") }
                    .addOnFailureListener { e: Exception ->
                        Log.d("Firestore",
                            "$DOCUMENT_TRANSACTION_HISTORY failed INITIALIZATION")
                        e.printStackTrace() }
                    .addOnCompleteListener { task: Task<Void?>? ->
                        Log.d("Firestore",
                            "$DOCUMENT_TRANSACTION_HISTORY INITIALIZATION process complete!") }
            }
            .addOnFailureListener { obj: Exception -> obj.printStackTrace() }
    }

    private companion object {
        private const val COLLECTION_USERS = "Users"
        private const val COLLECTION_ACCOUNT = "Account"
        private const val COLLECTION_PURCHASE_HISTORY = "PurchaseHistory"
        private const val COLLECTION_UNLOCK_HISTORY = "UnlockHistory"

        private const val DOCUMENT_CREDITS: String = "Credits"
        private const val DOCUMENT_PREFERENCES: String = "Preferences"
        private const val DOCUMENT_TRANSACTION_HISTORY = "TransactionHistory"
        private const val DOCUMENT_PURCHASED_ITEM = "PurchaseItem"
        private const val DOCUMENT_UNLOCKED_ITEM = "UnlockedItem"

        private const val FIELD_CREDITS_SPENT: String = "spentCredits"
        private const val FIELD_CREDITS_EARNED: String = "earnedCredits"
        private const val FIELD_MARKETPLACE_AGREEMENT_SHOWN: String = "marketplaceAgreementShown"
        private const val FIELD_DATE_PURCHASED = "datePurchased"
        private const val FIELD_PURCHASE_REFERENCE = "product_ref"
        private const val FIELD_ORDER_ID = "order_id"
        private const val FIELD_DATE_UNLOCKED = "dateUnlocked"
        private const val FIELD_TYPE = "type"

    }
}

interface FirestoreUserDataSource {

    fun setMarketplaceAgreementState(shown: Boolean, listener: OnFirestoreProcessListener?)
    fun addCredits(creditAmount: Long, listener: OnFirestoreProcessListener?)
    fun removeCredits(creditAmount: Long, listener: OnFirestoreProcessListener?)
    fun addPurchaseDocument(
        purchaseReferenceDoc: DocumentReference?, orderID: String?, listener: OnFirestoreProcessListener?, )
    fun addUnlockDocument(
        unlockUUID: String?, type: String, listener: OnFirestoreProcessListener)
    fun addUnlockedDocuments(
        unlockUUIDs: ArrayList<String>?, type: String, listener: OnFirestoreProcessListener)

}