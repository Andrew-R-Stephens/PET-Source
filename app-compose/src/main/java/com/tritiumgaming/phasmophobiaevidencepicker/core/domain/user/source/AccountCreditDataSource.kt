package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.source

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.api.OnFirestoreProcessListener

interface AccountCreditDataSource {

    fun addCredits(creditAmount: Long, callback: OnFirestoreProcessListener? = null)
    fun removeCredits(creditAmount: Long, callback: OnFirestoreProcessListener? = null)

}