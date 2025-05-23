package com.tritiumgaming.phasmophobiaevidencepicker.core.data.account.accountproperty.source.remote

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.account.accountproperty.reference.FirestoreAccountPreferencesDocument.Companion.FIELD_MARKETPLACE_AGREEMENT_SHOWN
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.account.accountproperty.reference.FirestoreAccountPreferencesDocument.Companion.preferencesDocument
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.api.OnFirestoreProcessListener
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.firestore.source.AccountPreferencesDataSource

class AccountPreferencesRemoteDataSource: AccountPreferencesDataSource {

    @Throws(Exception::class)
    override fun setMarketplaceAgreementState(
        shown: Boolean,
        callback: OnFirestoreProcessListener?) {

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
