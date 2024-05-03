package com.TritiumGaming.phasmophobiaevidencepicker.views.composables

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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.TritiumGaming.phasmophobiaevidencepicker.R

@Composable
@Preview
fun NewsAlert(isActive: Boolean = false) {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    Box(
        modifier = Modifier.size(48.dp)
    ) {
        Image(
            painterResource(id = R.drawable.icon_ts_news),
            contentDescription = ""
        )

        val opacity =
            if (isActive) {
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

        Image(
            painterResource(id = R.drawable.icon_ts_notify),
            contentDescription = "",
            modifier = Modifier
                .alpha(opacity)
                .fillMaxSize(fraction = .5f)
                .align(Alignment.BottomEnd)
        )
    }
}

fun setNewsAlertComposable(
    recipientView: ComposeView?,
    isActive: Boolean = false
) {
    recipientView?.setContent {
        NewsAlert(isActive)
    }
}