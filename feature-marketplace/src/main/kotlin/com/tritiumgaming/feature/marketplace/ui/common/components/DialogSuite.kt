package com.tritiumgaming.feature.marketplace.ui.common.components

import android.content.res.Configuration
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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun MarketplaceDialog(
    modifier: Modifier = Modifier,
    onConfirm: () -> Unit = { }
) {

    val title = stringResource(id = R.string.marketplace_acknowledgement_title)

    val content: @Composable () -> Unit = {

        Text(
            text = stringResource(id = R.string.marketplace_acknowledgement_warning),
            style = TextStyle(
                fontSize = 14.sp,
                color = LocalPalette.current.onSurface
            )
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.marketplace_acknowledgement_warning_list_1),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = LocalPalette.current.onSurface
                )
            )

            Text(
                text = stringResource(id = R.string.marketplace_acknowledgement_warning_list_2),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = LocalPalette.current.onSurface
                )
            )

            Text(
                text = stringResource(id = R.string.marketplace_acknowledgement_warning_list_3),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = LocalPalette.current.onSurface
                )
            )

            Text(
                text = stringResource(id = R.string.marketplace_acknowledgement_warning_list_4),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = LocalPalette.current.onSurface
                )
            )
        }
    }


    val confirmButton: @Composable () -> Unit = {
        TextButton(
            content = {
                Text(
                    text = stringResource(id = R.string.marketplace_acknowledgement_button_confirm),
                    maxLines = 1,
                    style = TextStyle(fontSize = 18.sp)
                )
            },
            contentPadding = PaddingValues(8.dp),
            colors = ButtonColors(
                contentColor = LocalPalette.current.surfaceContainer,
                containerColor = LocalPalette.current.onSurface,
                disabledContentColor = Color.Blue,
                disabledContainerColor = Color.Green
            ),
            shape = RoundedCornerShape(percent = 20),
            onClick = { onConfirm() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        )
    }


    Dialog(
        title = title,
        content = { content() },
        confirmButton = { confirmButton() }
    )
}

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
            delay(300.milliseconds) // Allow exit animation to finish
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
                        text = String.format(
                            stringResource(R.string.marketplace_purchase_equip),
                            stringResource(LocalPalette.current.extrasFamily.title)
                        ),
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

@Composable
private fun Dialog(
    modifier: Modifier = Modifier,
    title: String = "",
    content: @Composable () -> Unit = { },
    confirmButton: @Composable () -> Unit = { },
    cancelButton: @Composable () -> Unit = { }
) {
    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
                .background(LocalPalette.current.surfaceContainer)
                .padding(16.dp)
                .align(Alignment.Center)
                .wrapContentWidth()
                .wrapContentHeight()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_logo_app),
                        contentDescription = "Phasmophobia Evidence Tool Logo",
                        modifier = Modifier
                            .size(64.dp)
                            .background(LocalPalette.current.surface,
                                CircleShape
                            )
                            .padding(8.dp)
                            .align(Alignment.Center)
                    )
                }

                Text(
                    text = title,
                    maxLines = 1,
                    style = TextStyle(
                        fontSize = 24.sp,
                        color = LocalPalette.current.primary)
                )

                Column(
                    modifier = Modifier.wrapContentHeight()
                ) {

                    Column(
                        verticalArrangement = Arrangement.spacedBy(18.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                    ) {
                        content()
                    }

                    val configuration = LocalConfiguration.current
                    when (configuration.orientation) {
                        Configuration.ORIENTATION_PORTRAIT -> {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(24.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(PaddingValues(top = 8.dp))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                ) {
                                    cancelButton()
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                ) {
                                    confirmButton()
                                }
                            }
                        }
                        else -> {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(24.dp),
                                modifier = Modifier
                                    .padding(PaddingValues(top = 8.dp))
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .height(48.dp)
                                ) {
                                    cancelButton()
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .height(48.dp)
                                ) {
                                    confirmButton()
                                }

                            }
                        }
                    }
                }

            }
        }

    }
}

@Preview
@Composable
private fun Preview1() {
    LocalThemeProvider {
        EquipConfirmationDialog(
            targetTitle = String.format(stringResource(R.string.marketplace_purchase_equip), stringResource(LocalPalette.current.extrasFamily.title)),
            onConfirm = {},
            timeout = 5000L
        )
    }
}

@Preview
@Composable
private fun Preview2() {
    LocalThemeProvider {
        MarketplaceDialog(
            modifier = Modifier
                .fillMaxSize()
                .background(LocalPalette.current.scrim.copy(alpha = .5f))
                .padding(8.dp)
        )
    }
}