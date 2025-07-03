package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.icon

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.icons.IconResources.IconResource
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.mappers.ToComposable
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.ClassicPalette
import org.jetbrains.annotations.TestOnly

@Composable
fun LanguageIcon(
    onClick: () -> Unit = {}
) {
    val size = 48.dp

    Box(
        modifier = Modifier
            .size(size)
            .clickable(true) { onClick() }
    ) {

        IconResource.GLOBE.ToComposable(
            modifier = Modifier
            .fillMaxSize()
        )

        IconResource.TRANSLATE.ToComposable(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize(.5f)
                .align(Alignment.BottomEnd)
        )

    }
}

@Preview
@Composable
@TestOnly
fun LanguageIconPreview() {

    SelectiveTheme(
        palette = ClassicPalette
    ) {
        LanguageIcon()
    }
}
