package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.EaseOutQuad
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.R

@Composable
@Preview
fun NewsAlert(
    modifier: Modifier = Modifier,
    isActive: Boolean = false,
    @DrawableRes baseDrawableId: Int? = R.drawable.ic_news,
    @DrawableRes alertDrawableRes: Int = R.drawable.ic_notify,
    onClick: () -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition()

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clickable {
                onClick()
            }
    ) {

        baseDrawableId?.let {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = baseDrawableId),
                contentDescription = "Background Image"
            )
        }

        val opacity by infiniteTransition.animateFloat(
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

        val alertScale = if(baseDrawableId != null) .5f else 1f

        Image(
            modifier = Modifier
                .alpha(if(isActive) { opacity } else 0f )
                .fillMaxSize(fraction = alertScale)
                .align(Alignment.BottomEnd),
            painter = painterResource(id = alertDrawableRes),
            contentDescription = "Inbox Symbol"
        )

    }
}