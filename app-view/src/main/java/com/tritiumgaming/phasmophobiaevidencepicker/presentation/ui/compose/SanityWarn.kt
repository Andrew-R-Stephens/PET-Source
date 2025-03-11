package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.util.ColorUtils
import com.tritiumgaming.phasmophobiaevidencepicker.util.ColorUtils.getColorFromAttribute

@Composable
@Preview
fun FlashBackground(
    state: FlashState = FlashState.ACTIVE_STEADY
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val offColor = getColorFromAttribute(LocalContext.current, R.attr.light_off)
    val inactiveColor = getColorFromAttribute(LocalContext.current, R.attr.light_inactive)
    val activeColor= getColorFromAttribute(LocalContext.current, R.attr.light_active)

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

