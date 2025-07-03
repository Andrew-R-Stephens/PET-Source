package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.icon

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.icons.IconResources.IconResource
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.mappers.ToComposable
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import org.jetbrains.annotations.TestOnly

@Composable
fun DiscordIcon(
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(
        LocalPalette.current.background.color,
        LocalPalette.current.textFamily.primary
    )
) {

    Box(
        modifier = modifier
            .size(48.dp)
    ) {
        IconResource.DISCORD.ToComposable(
            modifier = Modifier
                .fillMaxSize(),
            colors = colors
        )

        IconResource.OPEN_IN_NEW.ToComposable(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize(.4f)
                .align(Alignment.BottomEnd)
        )
    }

}

@Preview
@Composable
@TestOnly
fun DiscordIconPreview() {

    SelectiveTheme(
        palette = ClassicPalette
    ) {
        DiscordIcon()
    }
}
