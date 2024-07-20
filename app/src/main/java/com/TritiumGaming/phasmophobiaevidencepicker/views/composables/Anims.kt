package com.TritiumGaming.phasmophobiaevidencepicker.views.composables

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.EaseOutQuad
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.TritiumGaming.phasmophobiaevidencepicker.R

@Composable
@Preview
fun NewsAlert(
    isActive: Boolean = false,
    @DrawableRes baseDrawableId: Int? = R.drawable.ic_news,
    @DrawableRes alertDrawableRes: Int = R.drawable.ic_notify
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    baseDrawableId?.let {
        Image(
            painterResource(id = baseDrawableId),
            contentDescription = "Background Image"
        )
    }

    Box(
        modifier = Modifier.size(48.dp)
    ) {
        val opacity = if (isActive) {
            infiniteTransition.animateFloat(
                initialValue = .4f,
                targetValue = .9f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 1000,
                        easing = EaseOutQuad
                    ),
                    repeatMode = RepeatMode.Reverse
                ), label = ""
            ).value
        } else 0f

        val alertScale = if(baseDrawableId != null) .5f else 1f

        Image(
            painterResource(id = alertDrawableRes),
            contentDescription = "Inbox Symbol",
            modifier = Modifier
                .alpha(opacity)
                .fillMaxSize(fraction = alertScale)
                .align(Alignment.BottomEnd)
        )

    }
}