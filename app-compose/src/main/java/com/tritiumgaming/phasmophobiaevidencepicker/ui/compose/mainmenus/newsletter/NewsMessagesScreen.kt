package com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.mainmenus.newsletter

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.mainmenus.MainMenuScreen


@Composable
@Preview
private fun NewsMessagesScreenPreview() {
    NewsMessagesScreen(0)
}

@Composable
fun NewsMessagesScreen(inboxID: Int) {

    MainMenuScreen (
        content = { NewsMessagesContent(inboxID) }
    )

}


@Composable
private fun NewsMessagesContent(
    inboxID: Int = 0
) {

    Text(
        text = "$inboxID"
    )

}