package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.pet.activities

import com.google.firebase.Firebase
import com.google.firebase.auth.auth

interface AccountManagementService: CredentialManagerService {

    fun delete(
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {},
        onComplete: () -> Unit = {}
    ) {

        Firebase.auth.currentUser?.let {

            it.delete()
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener {
                    onFailure()
                }
                .addOnCompleteListener {
                    onComplete()
                }

        }

    }

}
