package com.tritiumgaming.phasmophobiaevidencepicker.core.data.account.user.reference

import com.google.firebase.firestore.DocumentReference
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.account.user.NullFirebaseUserException
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.account.user.reference.FirestoreUserCollection.Companion.currentFirebaseUser
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.account.user.reference.FirestoreUserCollection.Companion.userCollection

class FirestoreUserDocument {
    companion object {

        val userDocument: DocumentReference
            @Throws(Exception::class) get() {
                if (currentFirebaseUser == null) { throw NullFirebaseUserException()
                }
                return userCollection.document(currentFirebaseUser!!.uid)
            }

    }

}
