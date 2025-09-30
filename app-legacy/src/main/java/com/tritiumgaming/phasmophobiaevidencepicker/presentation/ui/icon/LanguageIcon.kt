package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.icon

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.app.mappers.ToComposable
import com.tritiumgaming.shared.core.domain.icons.IconResources.IconResource

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
