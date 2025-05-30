package com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.remote

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.api.AccountPreferencesDocument.Companion.FIELD_MARKETPLACE_AGREEMENT_SHOWN
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.api.AccountPreferencesDocument.Companion.preferencesDocument
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.api.OnFirestoreProcessListener
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.source.AccountPreferencesDataSource

class AccountPreferencesRemoteDataSource: AccountPreferencesDataSource {

    @Throws(Exception::class)
    override fun setMarketplaceAgreementState(
        shown: Boolean,
        callback: OnFirestoreProcessListener?) {

        val data: MutableMap<String, Any> = HashMap()
        data[FIELD_MARKETPLACE_AGREEMENT_SHOWN] = shown

        preferencesDocument.update(data)
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
