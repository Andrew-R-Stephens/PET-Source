package com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.mainmenus.account

import android.accounts.Account
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.mainmenus.MainMenuFirebaseScreen

@Composable
@Preview
private fun AccountScreenPreview() {
    AccountScreen()
}

@Composable
fun AccountScreen(
    //content: @Composable () -> Unit
) {

    MainMenuFirebaseScreen (
        content = { AccountContent() }
    )

}


@Composable
private fun AccountContent() {}