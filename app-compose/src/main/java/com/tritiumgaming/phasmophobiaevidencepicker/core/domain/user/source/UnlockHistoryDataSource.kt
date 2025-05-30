package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.source

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.api.OnFirestoreProcessListener

interface UnlockHistoryDataSource {

    fun addUnlockDocument(
        unlockUUID: String?, type: String, callback: OnFirestoreProcessListener
    )

    fun addUnlockedDocuments(
        unlockUUIDs: ArrayList<String>?, type: String, callback: OnFirestoreProcessListener
    )
    
}