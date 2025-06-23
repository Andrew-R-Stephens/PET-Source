package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.icons.IconResources
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.icons.IconResources.IconResource
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.mappers.ToComposable
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.LocalPalette
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

@Preview
@Composable
@TestOnly
fun TestDiscordIcon() {

    SelectiveTheme(
        palette = ClassicPalette
    ) {
        DiscordIcon()
    }
}

@Preview
@Composable
@TestOnly
fun TestLanguageIcon() {

    SelectiveTheme(
        palette = ClassicPalette
    ) {
        LanguageIcon()
    }
}

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

@Composable
fun DiscordIcon(
    modifier: Modifier = Modifier
        .size(48.dp)
) {

    Box(
        modifier = modifier
    ) {
        IconResource.DISCORD.ToComposable(
            modifier = Modifier
                .fillMaxSize(),
            colors = listOf(
                LocalPalette.current.background.color,
                LocalPalette.current.textFamily.primary
            )
        )

        IconResource.OPEN_IN_NEW.ToComposable(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize(.4f)
                .align(Alignment.BottomEnd)
        )
    }

}
