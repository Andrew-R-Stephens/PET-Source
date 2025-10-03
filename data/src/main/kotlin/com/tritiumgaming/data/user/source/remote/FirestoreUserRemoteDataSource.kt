package com.tritiumgaming.data.user.source.remote

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreUserRemoteDataSource(
    private val firestore: FirebaseFirestore
) {

    private val userCollectionRef: CollectionReference
        get() = firestore.collection(COLLECTION_USERS)

    fun getUserDocumentRef(uid: String): DocumentReference? {
        return userCollectionRef.document(uid)
    }

    private companion object {
        private const val COLLECTION_USERS = "Users"
    }

}
