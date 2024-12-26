package com.tritiumgaming.phasmophobiaevidencepicker.listeners.firestore

abstract class OnFirestoreProcessListener {
    open fun onFailure() { }

    open fun onSuccess() { }

    open fun onComplete() { }
}
