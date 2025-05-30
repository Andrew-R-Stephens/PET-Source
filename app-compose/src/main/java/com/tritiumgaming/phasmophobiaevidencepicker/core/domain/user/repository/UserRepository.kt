package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.repository

import com.google.firebase.firestore.DocumentReference
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.api.OnFirestoreProcessListener

interface UserRepository {

    fun addCredits(creditAmount: Long, listener: OnFirestoreProcessListener?)
    fun removeCredits(creditAmount: Long, listener: OnFirestoreProcessListener?)

    fun setMarketplaceAgreementState(shown: Boolean, callback: OnFirestoreProcessListener?)

    fun addUnlockDocument(
        unlockUUID: String?, type: String, callback: OnFirestoreProcessListener)
    fun addUnlockedDocuments(
        unlockUUIDs: ArrayList<String>?, type: String, callback: OnFirestoreProcessListener)

    fun addPurchaseDocument(
        purchaseReferenceDoc: DocumentReference?, orderID: String?, callback: OnFirestoreProcessListener?)

}