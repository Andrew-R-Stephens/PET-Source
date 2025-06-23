package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.mainmenus.account

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.mainmenus.MainMenuFirebaseScreen

@Composable
@Preview
private fun AccountScreenPreview() {
    AccountScreen()
}

@Composable
fun AccountScreen(
    //content: @Composable () -> Unit
) {

    MainMenuFirebaseScreen(
        content = { AccountContent() }
    )

}


@Composable
private fun AccountContent() {



}