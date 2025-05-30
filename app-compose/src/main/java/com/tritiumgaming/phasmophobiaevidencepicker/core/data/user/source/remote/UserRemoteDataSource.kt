package com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.remote

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.SetOptions
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.api.AccountCollection
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.NullFirebaseUserException
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.api.UserCollection
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.api.UserCollection.Companion.currentFirebaseUser
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.api.UserDocument.Companion.userDocument
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.source.UserDataSource

class UserRemoteDataSource: UserDataSource {

    @Throws(Exception::class)
    override fun buildUserDocument(): DocumentReference {

        val user = currentFirebaseUser ?:
            throw NullFirebaseUserException(
                "Aborting creation of new User in database."
            )

        userDocument.set(HashMap<String, Any>(), SetOptions.merge())
            .addOnSuccessListener {
                Log.d("Firestore",
                    "User document ${ user.uid } successfully FOUND!")
            }
            .addOnFailureListener { e: Exception ->
                Log.d("Firestore",
                    "User document ${ user.uid } could NOT be GENERATED / LOCATED!")
                e.printStackTrace()
            }
            .addOnCompleteListener {
                Log.d("Firestore",
                    "User document ${ user.uid } process complete.")
                userDocument.get().addOnSuccessListener { docSnap: DocumentSnapshot ->
                    if (docSnap.exists()) {
                        try { AccountCollection.Companion.init() }
                        catch (e: Exception) { e.printStackTrace() }
                        Log.d("Firestore",
                            "User document ${ user.uid } successfully INITIALIZED!")
                    }
                }
            }

        return userDocument

    }

}