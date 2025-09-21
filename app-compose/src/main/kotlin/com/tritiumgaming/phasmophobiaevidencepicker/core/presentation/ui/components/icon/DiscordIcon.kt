package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.icon

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
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.mappers.ToComposable
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.shared.core.domain.icons.IconResources
import org.jetbrains.annotations.TestOnly

@Composable
fun DiscordIcon(
    modifier: Modifier = Modifier,
    colors: IconVectorColors = IconVectorColors.defaults()
) {

    Box(
        modifier = modifier
            .size(48.dp)
    ) {
        IconResources.IconResource.DISCORD.ToComposable(
            modifier = Modifier
                .fillMaxSize(),
            colors = colors
        )

        IconResources.IconResource.OPEN_IN_NEW.ToComposable(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize(.4f)
                .align(Alignment.BottomEnd),
            colors = colors
        )
    }

}

@Preview
@Composable
@TestOnly
fun DiscordIconPreview(
    colors: IconVectorColors = IconVectorColors.defaults(
        fillColor = LocalPalette.current.background.color,
        strokeColor = LocalPalette.current.textFamily.primary
    )
) {

    SelectiveTheme(
        palette = ClassicPalette
    ) {
        DiscordIcon(
            colors = colors
        )
    }
}
