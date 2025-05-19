package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.api

abstract class OnFirestoreProcessListener {
    open fun onFailure() { }

    open fun onSuccess() { }

    open fun onComplete() { }
}