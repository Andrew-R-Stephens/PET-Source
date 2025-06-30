package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.activities.impl

import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Deprecated("Replaced by CredentialsRepository")
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
