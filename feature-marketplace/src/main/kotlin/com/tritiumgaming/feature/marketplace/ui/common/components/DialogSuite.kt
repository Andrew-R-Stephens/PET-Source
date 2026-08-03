package com.tritiumgaming.feature.marketplace.ui.common.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.modifier.progressGradient
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalThemeProvider
import kotlinx.coroutines.delay

@Composable
fun EquipConfirmationDialog(
    targetTitle: String = "<theme>",
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {},
    timeout: Long = 5000L
) {
    var isVisible by remember(targetTitle) { mutableStateOf(true) }
    val progress = remember(targetTitle) { Animatable(1f) }

    LaunchedEffect(targetTitle) {
        progress.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = timeout.toInt(), easing = LinearEasing)
        )
        isVisible = false
    }

    LaunchedEffect(isVisible) {
        if (!isVisible) {
            delay(300) // Allow exit animation to finish
            onDismiss()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = slideIn(tween(
                500, easing = LinearOutSlowInEasing)) { fullSize ->
                IntOffset(0, fullSize.height)
            },
            exit = slideOut(tween(
                250, easing = LinearOutSlowInEasing)) { fullSize ->
                IntOffset(0, fullSize.height)
            }
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(25))
                    .progressGradient(
                        progress = progress.value,
                        gradientColor = LocalPalette.current.primary,
                        gradientAlpha = 1f
                    )
                    .padding(vertical = 1.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(
                            LocalPalette.current.surfaceContainer,
                            RoundedCornerShape(24)
                        )
                        .padding(8.dp)
                        .height(48.dp)
                ) {
                    Text(
                        text = "$targetTitle?",
                        fontSize = 18.sp,
                        color = LocalPalette.current.onSurface,
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                            .padding(8.dp),
                        textAlign = TextAlign.Start
                    )

                    Button(
                        contentPadding = PaddingValues(8.dp),
                        colors = ButtonColors(
                            contentColor = LocalPalette.current.surface,
                            containerColor = LocalPalette.current.primary,
                            disabledContentColor = Color.Blue,
                            disabledContainerColor = Color.Green
                        ),
                        shape = RoundedCornerShape(percent = 50),
                        onClick = {
                            onConfirm()
                            isVisible = false
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .wrapContentWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_arrow_chevron_right),
                            colorFilter = ColorFilter.tint(LocalPalette.current.onSurface),
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    LocalThemeProvider {
        EquipConfirmationDialog(
            targetTitle = String.format(stringResource(R.string.marketplace_purchase_equip), stringResource(LocalPalette.current.extrasFamily.title)),
            onConfirm = {},
            timeout = 5000L
        )
    }
}