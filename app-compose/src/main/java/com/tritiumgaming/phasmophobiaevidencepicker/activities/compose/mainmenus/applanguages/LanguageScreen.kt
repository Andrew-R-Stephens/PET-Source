package com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.mainmenus.applanguages

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.mainmenus.MainMenuFirebaseScreen
import com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.mainmenus.MainMenuScreen
import com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.mainmenus.marketplace.billing.MarketplaceBillingScreen


@Composable
@Preview
private fun LanguageScreenPreview() {
    LanguageScreen()
}

@Composable
fun LanguageScreen(
    //content: @Composable () -> Unit
) {

    MainMenuScreen (
        content = { LanguageContent() }
    )

}


@Composable
private fun LanguageContent() {}