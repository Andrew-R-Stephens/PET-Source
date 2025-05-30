package com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.api

import com.google.firebase.firestore.DocumentReference
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.NullFirebaseUserException
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.api.UserCollection.Companion.currentFirebaseUser
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.api.UserCollection.Companion.userCollection

class UserDocument {
    companion object {

        val userDocument: DocumentReference
            @Throws(Exception::class) get() {
                if (currentFirebaseUser == null) { throw NullFirebaseUserException() }
                return userCollection.document(currentFirebaseUser!!.uid)
            }

    }

}
