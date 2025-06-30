package com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source

import android.app.Activity
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CredentialOption
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AccountManagerService {

    suspend fun signInWithCredential(
        activity: Activity ? = null,
        credentialOption: CredentialOption
    ): Result<GetCredentialResponse> = withContext(Dispatchers.Main) {

        activity ?: return@withContext Result.failure(Exception("Activity is null."))

        Log.e("FirebaseAuth", "Attempting to obtain credentials.")

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(credentialOption)
            .build()

        try {

            val credentialManager = CredentialManager.create(context = activity)

            val credentialResponse = credentialManager.getCredential(
                request = request,
                context = activity
            )

            Result.success(credentialResponse)

        } catch (e: GetCredentialException) {
            e.printStackTrace()

            Result.failure(Exception("Failure obtaining credentials.", e))
        }

    }


}