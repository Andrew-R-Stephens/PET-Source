package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.firestore.source

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.api.OnFirestoreProcessListener

interface CreditDataSource {

    fun addCredits(creditAmount: Long, callback: OnFirestoreProcessListener? = null)
    fun removeCredits(creditAmount: Long, callback: OnFirestoreProcessListener? = null)

}