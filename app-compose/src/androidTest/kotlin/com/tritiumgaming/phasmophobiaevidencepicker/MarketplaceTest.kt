package com.tritiumgaming.phasmophobiaevidencepicker

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.core.ui.mapper.toPaletteResource
import com.tritiumgaming.feature.marketplace.ui.store.palettes.PaletteCard
import com.tritiumgaming.shared.data.market.palette.model.MarketPalette
import com.tritiumgaming.shared.data.market.palette.mappers.PaletteResources

/*@Composable
@Preview
private fun Test() {

    LazyColumn (
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(PaletteResources.PaletteType.entries) { type ->

            PaletteCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                marketPalette = MarketPalette(
                    uuid = "4324132",
                    name = "Test",
                    palette = type,
                    buyCredits = 69
                )
            )
        }
    }
}*/


@Composable
@Preview
private fun Test1() {

    val palette = PaletteResources.PaletteType.CONTENT_CREATOR.toPaletteResource()

    PaletteCard(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        buyCredits = 69,
        badgeRes = palette.extrasFamily.badge,
        title = stringResource(palette.extrasFamily.title),
        surfaceContainerHigh = palette.surfaceContainerHigh,
        onSurfaceVariant = palette.onSurfaceVariant,
        onSurface = palette.onSurface,
        primary = palette.primary,
        secondary = palette.secondary,
        tertiary = palette.tertiary,
        surfaceContainer = palette.surfaceContainer,
        primaryContainer = palette.primaryContainer,
        secondaryContainer = palette.secondaryContainer,
        tertiaryContainer = palette.tertiaryContainer,
        onBuyClick = {},
    )
}


@Composable
@Preview
private fun Test2() {

    val palette = PaletteResources.PaletteType.COMMISSIONER.toPaletteResource()

    PaletteCard(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        buyCredits = 69,
        badgeRes = palette.extrasFamily.badge,
        title = stringResource(palette.extrasFamily.title),
        surfaceContainerHigh = palette.surfaceContainerHigh,
        onSurfaceVariant = palette.onSurfaceVariant,
        onSurface = palette.onSurface,
        primary = palette.primary,
        secondary = palette.secondary,
        tertiary = palette.tertiary,
        surfaceContainer = palette.surfaceContainer,
        primaryContainer = palette.primaryContainer,
        secondaryContainer = palette.secondaryContainer,
        tertiaryContainer = palette.tertiaryContainer,
        onBuyClick = {},
    )
}
