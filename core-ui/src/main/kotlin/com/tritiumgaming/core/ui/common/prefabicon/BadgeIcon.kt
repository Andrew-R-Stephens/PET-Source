package com.tritiumgaming.core.ui.common.prefabicon

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BadgeIcon(
    modifier: Modifier = Modifier,
    baseComponent: @Composable BoxScope.(modifier: Modifier) -> Unit = {},
    badgeComponent: @Composable BoxScope.() -> Unit = {}
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            baseComponent(Modifier
                .fillMaxSize()
                .aspectRatio(1f)
            )
        }

        Box(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize(.4f)
                .align(Alignment.BottomEnd)
        ) {
            badgeComponent()
        }
    }

}
