package com.TritiumGaming.phasmophobiaevidencepicker.listeners.firestore

abstract class OnFirestoreProcessListener {
    open fun onFailure() { }

    open fun onSuccess() { }

    open fun onComplete() { }
}
