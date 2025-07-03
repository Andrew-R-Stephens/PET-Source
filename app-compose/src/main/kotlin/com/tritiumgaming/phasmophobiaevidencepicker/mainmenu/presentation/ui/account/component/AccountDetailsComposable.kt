package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.account.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.icon.AccountIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.ClassicTypography

@Composable
@Preview
private fun AccountDetailsPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        AccountBanner()
    }
}

@Composable
fun AccountBanner(
    modifier: Modifier = Modifier,
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

        AccountIcon()
    }

}