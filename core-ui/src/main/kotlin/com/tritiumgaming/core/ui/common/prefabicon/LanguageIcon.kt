package com.tritiumgaming.core.ui.common.prefabicon

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
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.mappers.ToComposable
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.palette.LocalPalette
import com.tritiumgaming.shared.core.domain.icons.IconResources.IconResource
import org.jetbrains.annotations.TestOnly

@Composable
fun LanguageIcon(
    modifier: Modifier = Modifier,
    colors: IconVectorColors = IconVectorColors.defaults(),
    onClick: () -> Unit = {}
) {

    Box(
        modifier = modifier
            .clickable(true) { onClick() }
    ) {

        IconResource.GLOBE.ToComposable(
            modifier = Modifier
                .fillMaxSize(),

            colors = colors
        )

        IconResource.TRANSLATE.ToComposable(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize(.5f)
                .align(Alignment.BottomEnd),
            colors = colors
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
        LanguageIcon(
            modifier = Modifier
                .size(48.dp),
            colors = IconVectorColors.defaults(
                fillColor = LocalPalette.current.background.color,
                strokeColor = LocalPalette.current.textFamily.body
            )
        )
    }
}
