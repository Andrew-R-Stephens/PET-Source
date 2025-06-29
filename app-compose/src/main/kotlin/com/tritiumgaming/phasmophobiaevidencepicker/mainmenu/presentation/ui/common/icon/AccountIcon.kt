package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.common.icon

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.account.component.AccountIcon
import org.jetbrains.annotations.TestOnly

@Preview
@Composable
@TestOnly
fun TestAccountIcon() {

    SelectiveTheme(
        palette = ClassicPalette
    ) {
        AccountIcon()
    }
}
