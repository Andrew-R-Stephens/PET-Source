package com.tritiumgaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.tritiumgaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.FirestoreAccount
import org.jetbrains.annotations.TestOnly

class FirestoreUser {
    companion object {
        private const val COLLECTION_USERS = "Users"

        val currentFirebaseUser: FirebaseUser?
            get() = FirebaseAuth.getInstance().currentUser

        val userDocument: DocumentReference
            @Throws(Exception::class) get() {
                if (currentFirebaseUser == null) { throw NullFirebaseUserException() }

                val db = FirebaseFirestore.getInstance()
                val userCollection = db.collection(COLLECTION_USERS)
                return userCollection.document(currentFirebaseUser!!.uid)
            }

        @Throws(Exception::class)
        fun buildUserDocument(): DocumentReference {
            val currentUserDoc = userDocument

            val user = currentFirebaseUser ?:
                throw NullFirebaseUserException("Aborting creation of new User in database.")

            currentUserDoc.set(HashMap<String, Any>(), SetOptions.merge())
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
                    currentUserDoc.get().addOnSuccessListener { docSnap: DocumentSnapshot ->
                        if (docSnap.exists()) {
                            try { FirestoreAccount.init() }
                            catch (e: Exception) { e.printStackTrace() }
                            Log.d("Firestore",
                                "User document ${ user.uid } successfully INITIALIZED!")
                        }
                    }
                }

            return currentUserDoc
        }

        @TestOnly
        fun getCurrentFirebaseUserDisplayNameInitials(displayName: String?): String {
            val displayInitials = StringBuilder()
            if (displayName != null) {
                val names =
                    displayName.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

                for (name in names) {
                    val trimmedName = name.trim { it <= ' ' }
                    if (trimmedName.isNotEmpty()) {
                        val initial = trimmedName[0]
                        displayInitials.append(initial)
                        if (displayInitials.length >= 2) { break }
                    }
                }
            }
            return displayInitials.toString()
        }

    }

    class NullFirebaseUserException : Exception {
        constructor() : super(ERROR_MESSAGE)

        constructor(extendedMessage: String) : super("$ERROR_MESSAGE $extendedMessage")

        companion object {
            private const val ERROR_MESSAGE = "FirebaseUser is null."
        }
    }
}
