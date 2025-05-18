package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.api.firestore

abstract class OnFirestoreProcessListener {
    open fun onFailure() { }

    open fun onSuccess() { }

    open fun onComplete() { }
}
