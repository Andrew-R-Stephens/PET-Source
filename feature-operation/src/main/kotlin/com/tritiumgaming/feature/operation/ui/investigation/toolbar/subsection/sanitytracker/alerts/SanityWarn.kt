package com.tritiumgaming.feature.operation.ui.investigation.toolbar.subsection.sanitytracker.alerts

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.common.util.ColorUtils
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette

@Composable
@Preview
fun FlashBackground(
    state: FlashState = FlashState.ACTIVE_STEADY
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val offColor = LocalPalette.current.light_off.toArgb()
    val inactiveColor = LocalPalette.current.light_inactive.toArgb()
    val activeColor= LocalPalette.current.light_active.toArgb()

    val ratio = if (state == FlashState.ACTIVE_ANIMATED) {
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1000,
                    easing = EaseInOut
                ),
                repeatMode = RepeatMode.Reverse
            ), label = ""
        ).value
    } else 0f

    val color =
        when(state) {
            FlashState.OFF -> offColor
            FlashState.INACTIVE -> inactiveColor
            FlashState.ACTIVE_ANIMATED -> ColorUtils.interpolate(activeColor, inactiveColor, ratio)
            FlashState.ACTIVE_STEADY -> activeColor
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .border(2.dp, Color(color), RoundedCornerShape(35))
            .background(Color.Transparent)
    )
}

enum class FlashState {
    OFF, INACTIVE, ACTIVE_ANIMATED, ACTIVE_STEADY
}

