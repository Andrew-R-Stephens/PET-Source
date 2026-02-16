package com.tritiumgaming.core.ui.icon.impl.composite

import androidx.compose.animation.core.EaseOutQuad
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

@Composable
fun NotificationIndicator(
    modifier: Modifier = Modifier,
    isActive: Boolean = false,
    baseComponent: (@Composable (modifier: Modifier) -> Unit)? = null,
    badgeComponent: @Composable (modifier: Modifier) -> Unit = {},
    onClick: () -> Unit = {}
) {

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clickable { onClick() }
    ) {

        baseComponent?.let { component: @Composable (modifier: Modifier) -> Unit ->
            component(modifier
                .fillMaxSize())
        }

        val opacity by rememberInfiniteTransition().animateFloat(
                initialValue = .4f,
                targetValue = .9f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 1000,
                        easing = EaseOutQuad
                    ),
                    repeatMode = RepeatMode.Reverse
                ), label = ""
            )

        val alertScale = if(baseComponent != null) .5f else 1f

        Box(
            modifier = Modifier
                .alpha(if (isActive) { opacity } else 0f)
                .fillMaxSize(fraction = alertScale)
                .align(Alignment.BottomEnd)
        ) {
            badgeComponent(modifier
                .fillMaxSize())
        }

    }
}
