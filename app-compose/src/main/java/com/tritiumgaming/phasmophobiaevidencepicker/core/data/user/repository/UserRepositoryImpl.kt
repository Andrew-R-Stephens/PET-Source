package com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.repository

import com.google.firebase.firestore.DocumentReference
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.api.OnFirestoreProcessListener
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.remote2.FirestoreUserRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.repository.UserRepository

class UserRepositoryImpl(
    private val userRemoteDataSource: FirestoreUserRemoteDataSource
): UserRepository {

    @Throws(Exception::class)
    override fun addCredits(creditAmount: Long, listener: OnFirestoreProcessListener?) =
        userRemoteDataSource.addCredits(creditAmount, listener)

    @Throws(Exception::class)
    override fun removeCredits(creditAmount: Long, listener: OnFirestoreProcessListener?) =
        userRemoteDataSource.removeCredits(creditAmount, listener)

    @Throws(Exception::class)
    override fun setMarketplaceAgreementState(
        shown: Boolean, callback: OnFirestoreProcessListener?) =
        userRemoteDataSource.setMarketplaceAgreementState(shown, callback)

    @Throws(Exception::class)
    override fun addUnlockDocument(
        unlockUUID: String?, type: String, callback: OnFirestoreProcessListener) =
        userRemoteDataSource.addUnlockDocument(unlockUUID, type, callback)

    @Throws(Exception::class)
    override fun addUnlockedDocuments(
        unlockUUIDs: ArrayList<String>?, type: String, callback: OnFirestoreProcessListener) =
        userRemoteDataSource.addUnlockedDocuments(unlockUUIDs, type, callback)

    @Throws(Exception::class)
    override fun addPurchaseDocument(
        purchaseReferenceDoc: DocumentReference?, orderID: String?, callback: OnFirestoreProcessListener?) =
        userRemoteDataSource.addPurchaseDocument(purchaseReferenceDoc, orderID, callback)

}