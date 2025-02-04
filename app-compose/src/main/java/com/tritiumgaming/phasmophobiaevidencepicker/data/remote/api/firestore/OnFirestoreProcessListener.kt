package com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.firestore

abstract class OnFirestoreProcessListener {
    open fun onFailure() { }

    open fun onSuccess() { }

    open fun onComplete() { }
}
