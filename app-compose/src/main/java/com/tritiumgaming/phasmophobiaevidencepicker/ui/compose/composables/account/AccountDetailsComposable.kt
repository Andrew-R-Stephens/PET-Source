package com.tritiumgaming.phasmophobiaevidencepicker.ui.views.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseUser
import com.tritiumgaming.phasmophobiaevidencepicker.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Non_Colorblind
import com.tritiumgaming.phasmophobiaevidencepicker.theme.types.Classic

@Composable
@Preview
private fun AccountDetailsPreview() {
    SelectiveTheme(
        theme = Non_Colorblind,
        typography = Classic
    ) {
        AccountDetails()
    }
}

@Composable
fun AccountDetails(
    modifier: Modifier = Modifier,
    firebaseUser: FirebaseUser? = null,
    credits: Int = 100
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            AccountCredits(
                credits = credits
            )
        }

        AccountIcon(
            authUser = firebaseUser
        )
    }

}