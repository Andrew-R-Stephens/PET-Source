package com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirestoreAuthRemoteDataSource(
    private val firebaseAuth: FirebaseAuth
) {
    val currentAuthUser: FirebaseUser?
        get() = firebaseAuth.currentUser
}
