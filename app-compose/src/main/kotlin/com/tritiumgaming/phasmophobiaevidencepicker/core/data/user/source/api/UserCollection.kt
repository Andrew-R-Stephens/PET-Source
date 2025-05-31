package com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.api

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class UserCollection {
    companion object {
        private const val COLLECTION_USERS = "Users"

        val currentFirebaseUser: FirebaseUser?
            get() = FirebaseAuth.getInstance().currentUser

        val userCollection: CollectionReference
            @Throws(Exception::class)
            get() = FirebaseFirestore.getInstance().collection(COLLECTION_USERS)

    }

}
