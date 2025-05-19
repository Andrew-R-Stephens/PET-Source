package com.tritiumgaming.phasmophobiaevidencepicker.core.data.firestore.accountproperty.source.remote

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.firestore.accountproperty.reference.FirestoreAccountPreferencesDocument.Companion.FIELD_MARKETPLACE_AGREEMENT_SHOWN
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.firestore.accountproperty.reference.FirestoreAccountPreferencesDocument.Companion.preferencesDocument
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.api.OnFirestoreProcessListener

class FirestoreAccountPreferencesRemoteDataSource {

    @JvmOverloads
    @Throws(Exception::class)
    fun setMarketplaceAgreementState(
        shown: Boolean,
        callback: OnFirestoreProcessListener? = null) {

        val preferenceDocument = preferencesDocument

        val data: MutableMap<String, Any> = HashMap()
        data[FIELD_MARKETPLACE_AGREEMENT_SHOWN] = shown

        preferenceDocument.update(data)
            .addOnSuccessListener {
                callback?.onSuccess()
            }
            .addOnFailureListener {
                callback?.onFailure()
            }
            .addOnCompleteListener {
                callback?.onComplete()
            }
    }

}