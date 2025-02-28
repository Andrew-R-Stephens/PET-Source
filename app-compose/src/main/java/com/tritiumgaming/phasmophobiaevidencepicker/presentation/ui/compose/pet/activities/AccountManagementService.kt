package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.pet.activities

import android.app.Activity

interface AccountManagementService: CredentialManagerService {

    fun deleteAccount(
        activity: Activity,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {},
        onComplete: () -> Unit = {}
    ) {



    }

}
