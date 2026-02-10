package com.tritiumgaming.core.ui.widgets.collapsebutton

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Spring.DampingRatioNoBouncy
import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette

@Composable
fun CollapseButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int = R.drawable.ic_arrow_chevron_right,
    disabledRotationVertical: Int = 0,
    disabledRotationHorizontal: Int = 0,
    enabledRotationAddition: Int = 0,
    tint: Color = LocalPalette.current.onSurface,
    isCollapsed: Boolean = false,
    onClick: (() -> Unit)? = null
) {

    val rotation by animateFloatAsState(
        targetValue = if(isCollapsed) 1f else 0f,
        animationSpec = spring(
            stiffness = StiffnessLow,
            dampingRatio = DampingRatioNoBouncy
        ),
        label = "",
    )

    Box(
        modifier = modifier
            .then(if(onClick != null) Modifier.clickable { onClick() } else Modifier)
    ) {

        val orientationRotate = when(LocalConfiguration.current.orientation) {
            ORIENTATION_PORTRAIT -> disabledRotationVertical
            ORIENTATION_LANDSCAPE -> disabledRotationHorizontal
            else -> 0
        }

        Image(
            painterResource(id = icon),
            contentDescription = "Reset Drawable",
            colorFilter = ColorFilter.tint(tint),
            modifier = Modifier
                .fillMaxSize(/*.55f*/)
                .align(Alignment.Center)
                .clip(RectangleShape)
                .rotate(orientationRotate + (rotation * enabledRotationAddition))
        )
    }
}