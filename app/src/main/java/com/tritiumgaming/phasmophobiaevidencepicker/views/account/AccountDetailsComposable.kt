package com.tritiumgaming.phasmophobiaevidencepicker.views.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tritiumgaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser


@Composable
fun AccountDetailsComposable(
    modifier: Modifier = Modifier,
    credits: Int = 100
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AccountCreditsComposable()

        AccountIconComposable(FirestoreUser.currentFirebaseUser)
    }

}