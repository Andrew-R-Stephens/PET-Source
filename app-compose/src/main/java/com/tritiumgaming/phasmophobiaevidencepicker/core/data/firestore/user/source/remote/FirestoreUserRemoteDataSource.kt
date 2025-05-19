package com.tritiumgaming.phasmophobiaevidencepicker.core.data.firestore.user.source.remote

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.SetOptions
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.firestore.account.source.model.FirestoreAccountCollection
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.firestore.user.NullFirebaseUserException
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.firestore.user.reference.FirestoreUserCollection
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.firestore.user.reference.FirestoreUserDocument.Companion.userDocument

class FirestoreUserRemoteDataSource {

    @Throws(Exception::class)
    fun buildUserDocument(): DocumentReference {

        val user = FirestoreUserCollection.Companion.currentFirebaseUser ?:
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
                        try { FirestoreAccountCollection.Companion.init() }
                        catch (e: Exception) { e.printStackTrace() }
                        Log.d("Firestore",
                            "User document ${ user.uid } successfully INITIALIZED!")
                    }
                }
            }

        return userDocument

    }

}