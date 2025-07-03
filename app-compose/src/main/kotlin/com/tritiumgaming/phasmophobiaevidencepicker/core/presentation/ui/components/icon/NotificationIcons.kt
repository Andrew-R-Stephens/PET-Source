package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.icon

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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.icons.IconResources.IconResource
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.mappers.ToComposable
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.mapper.NewsletterResources.NewsletterIcon
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.app.mappers.toIconResource
import org.jetbrains.annotations.TestOnly

@Composable
@Preview
@TestOnly
private fun NotificationIndicatorPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        Column {
            NotificationIndicator(
                isActive = true,
                baseIcon = IconResource.NOTIFY
            )
            NotificationIndicator(
                isActive = true,
                baseDrawableId = null
            )
        }
    }

}

@Composable
fun NotificationIndicator(
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
                painter = painterResource(baseDrawableId),
                contentDescription = "Background Image",
                colorFilter = ColorFilter.tint(
                    color = LocalPalette.current.textFamily.primary,
                    blendMode = BlendMode.DstOut
                )
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
                .alpha(
                    if (isActive) {
                        opacity
                    } else 0f
                )
                .fillMaxSize(fraction = alertScale)
                .align(Alignment.BottomEnd),
            painter = painterResource(id = alertDrawableRes),
            contentDescription = "Inbox Symbol",
            colorFilter = ColorFilter.tint(LocalPalette.current.inboxNotification)
        )

    }
}

@Composable
fun NotificationIndicator(
    modifier: Modifier = Modifier,
    isActive: Boolean = false,
    baseIcon: IconResource? = NewsletterIcon.GENERAL_NEWS.toIconResource(),
    alertIcon: IconResource = IconResource.NOTIFY,
    onClick: () -> Unit = {}
) {

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clickable {
                onClick()
            }
    ) {

        baseIcon?.ToComposable(
            modifier = Modifier
                .fillMaxSize()
        )

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

        val alertScale = if(baseIcon != null) .5f else 1f

        Box(
            modifier = Modifier
                .alpha(
                    if (isActive) {
                        opacity
                    } else 0f
                )
                .fillMaxSize(fraction = alertScale)
                .align(Alignment.BottomEnd)
        ) {
            alertIcon.ToComposable()
        }

    }
}