package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.listeners

abstract class OnFirestoreProcessListener {
    open fun onFailure() { }

    open fun onSuccess() { }

    open fun onComplete() { }
}
