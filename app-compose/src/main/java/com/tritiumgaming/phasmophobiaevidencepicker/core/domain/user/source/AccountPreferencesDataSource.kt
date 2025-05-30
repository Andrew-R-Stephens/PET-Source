package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.source

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.api.OnFirestoreProcessListener

interface AccountPreferencesDataSource {

    fun setMarketplaceAgreementState(shown: Boolean, callback: OnFirestoreProcessListener? = null)
    
}