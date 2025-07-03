package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.account.component

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.modifiers.progressGradient
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.util.ColorUtils
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.util.ColorUtils.getColorFromAttribute
import kotlinx.coroutines.delay

@Composable
fun MarketplaceDialog(
    onConfirm: () -> Unit = { }
) {

    val title = stringResource(id = R.string.marketplace_acknowledgement_title)

    val content: @Composable () -> Unit = {

        Text(
            text = stringResource(id = R.string.marketplace_acknowledgement_warning),
            style = TextStyle(
                fontSize = 14.sp,
                color = Color(
                    getColorFromAttribute(
                        LocalContext.current, R.attr.textColorBody
                    )
                )
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
                    color = Color(
                        getColorFromAttribute(
                            LocalContext.current, R.attr.textColorBody
                        )
                    )
                )
            )

            Text(
                text = stringResource(id = R.string.marketplace_acknowledgement_warning_list_2),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color(
                        getColorFromAttribute(
                            LocalContext.current, R.attr.textColorBody
                        )
                    )
                )
            )

            Text(
                text = stringResource(id = R.string.marketplace_acknowledgement_warning_list_3),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color(
                        getColorFromAttribute(
                            LocalContext.current, R.attr.textColorBody
                        )
                    )
                )
            )

            Text(
                text = stringResource(id = R.string.marketplace_acknowledgement_warning_list_4),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color(
                        getColorFromAttribute(
                            LocalContext.current, R.attr.textColorBody
                        )
                    )
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
                contentColor = Color(
                    getColorFromAttribute(
                        LocalContext.current,
                        R.attr.backgroundColorOnBackground
                    )
                ),
                containerColor = Color(
                    getColorFromAttribute(
                        LocalContext.current,
                        R.attr.textColorBody
                    )
                ),
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
fun LogoutDialog(
    onConfirm: () -> Unit = { },
    onCancel: () -> Unit = { }
) {

    val title = stringResource(id = R.string.account_logout_title)

    val content: @Composable () -> Unit = {
        Text(
            text = stringResource(id = R.string.account_logout_warning),
            style = TextStyle(
                fontSize = 18.sp,
                color = Color(
                    getColorFromAttribute(
                        LocalContext.current, R.attr.textColorBody
                    )
                )
            )
        )
    }

    val cancelButton: @Composable () -> Unit = {
        TextButton(
            content = {
                Text(
                    text = stringResource(id = R.string.account_logout_button_cancel),
                    maxLines = 1,
                    style = TextStyle(fontSize = 18.sp)
                )
            },
            contentPadding = PaddingValues(8.dp),
            colors = ButtonColors(
                contentColor = Color(
                    getColorFromAttribute(
                        LocalContext.current,
                        R.attr.backgroundColorOnBackground
                    )
                ),
                containerColor = Color(
                    getColorFromAttribute(
                        LocalContext.current,
                        R.attr.textColorBody
                    )
                ),
                disabledContentColor = Color.Blue,
                disabledContainerColor = Color.Green
            ),
            shape = RoundedCornerShape(percent = 20),
            onClick = { onCancel() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        )
    }

    val confirmButton: @Composable () -> Unit = {
        TextButton(
            content = {
                Text(
                    text = stringResource(id = R.string.account_logout_button_confirm),
                    maxLines = 1,
                    style = TextStyle(fontSize = 18.sp)
                )
            },
            contentPadding = PaddingValues(8.dp),
            colors = ButtonColors(
                contentColor =
                    LocalPalette.current.background.color,
                containerColor = LocalPalette.current.coreFamily.primary,
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
        cancelButton = { cancelButton() },
        confirmButton = { confirmButton() }
    )
}

@Composable
fun DeleteAccountDialog(
    onConfirm: () -> Unit = { },
    onCancel: () -> Unit = { }
) {

    val title = stringResource(id = R.string.account_deactivate_title)

    val content: @Composable () -> Unit = {

        Text(
            text = stringResource(id = R.string.account_deactivate_warning),
            style = TextStyle(
                fontSize = 14.sp,
                color = Color(
                    getColorFromAttribute(
                        LocalContext.current, R.attr.textColorBody
                    )
                )
            )
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.account_deactivate_warning_list_1),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color(
                        getColorFromAttribute(
                            LocalContext.current, R.attr.textColorBody
                        )
                    )
                )
            )

            Text(
                text = stringResource(id = R.string.account_deactivate_warning_list_2),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color(
                        getColorFromAttribute(
                            LocalContext.current, R.attr.textColorBody
                        )
                    )
                )
            )

            Text(
                text = stringResource(id = R.string.account_deactivate_warning_list_3),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color(
                        getColorFromAttribute(
                            LocalContext.current, R.attr.textColorBody
                        )
                    )
                )
            )
        }
    }

    val cancelButton: @Composable () -> Unit = {
        TextButton(
            content = {
                Text(
                    text = stringResource(id = R.string.account_deactivate_button_cancel),
                    maxLines = 1,
                    style = TextStyle(fontSize = 18.sp)
                )
            },
            contentPadding = PaddingValues(8.dp),
            colors = ButtonColors(
                contentColor = Color(
                    getColorFromAttribute(
                        LocalContext.current,
                        R.attr.backgroundColorOnBackground
                    )
                ),
                containerColor = Color(
                    getColorFromAttribute(
                        LocalContext.current,
                        R.attr.textColorBody
                    )
                ),
                disabledContentColor = Color.Blue,
                disabledContainerColor = Color.Green
            ),
            shape = RoundedCornerShape(percent = 20),
            onClick = { onCancel() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        )
    }

    val confirmButton: @Composable () -> Unit = {
        TextButton(
            content = {
                Text(
                    text = stringResource(id = R.string.account_deactivate_button_confirm),
                    maxLines = 1,
                    style = TextStyle(fontSize = 18.sp)
                )
            },
            contentPadding = PaddingValues(8.dp),
            colors = ButtonColors(
                contentColor = LocalPalette.current.background.color,
                containerColor = LocalPalette.current.coreFamily.primary,
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
        cancelButton = { cancelButton() },
        confirmButton = { confirmButton() }
    )

}

//@Preview
@Composable
fun Dialog(
    title: String = "",
    content: @Composable () -> Unit = { },
    confirmButton: @Composable () -> Unit = { },
    cancelButton: @Composable () -> Unit = { }
) {
    val backgroundColor = ColorUtils.interpolate(
        LocalPalette.current.background.color.toArgb(),
        Color.Transparent.hashCode(), .75f)

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(backgroundColor))
        .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
                .background(
                    Color(
                        getColorFromAttribute(
                            LocalContext.current,
                            R.attr.backgroundColorOnBackground
                        )
                    )
                )
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
                            .background(
                                LocalPalette.current.background.color,
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
                        color =  LocalPalette.current.coreFamily.primary
                    )
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
fun EquipConfirmationDialog (
    targetTitle: String = "<theme>",
    onConfirm: () -> Unit = {},
    timeout: Long = 1000L,
    isReset: Boolean = true,
    isInit: Boolean = true
) {
    var title by remember { mutableStateOf("") }
    title = targetTitle
    var isVisible by remember { mutableStateOf(true) }
    var timeLeft by remember { mutableLongStateOf(timeout) }

    LaunchedEffect(title) {
        timeLeft = timeout
        isVisible = true
        while (timeLeft > 0L && isVisible) {
            val delayTime = 5L
            delay(delayTime)
            timeLeft -= delayTime
        }
        timeLeft = 0L
        isVisible = false
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        AnimatedVisibility(
            isVisible,
            enter = slideIn(tween(500, easing = LinearOutSlowInEasing)) { fullSize ->
                IntOffset(0, fullSize.height)
            },
            exit = slideOut(tween(250, easing = LinearOutSlowInEasing)) { fullSize ->
                IntOffset(0, fullSize.height)
            }
        ) {
            Box(modifier = Modifier
                .clip(RoundedCornerShape(25))
                .progressGradient(
                    progress = timeLeft / (timeout.toFloat()),
                    gradientColor =  LocalPalette.current.coreFamily.primary,
                    gradientAlpha = 1f
                )
                .padding(0.dp, 1.dp, 0.dp, 1.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(
                            Color(
                                getColorFromAttribute(
                                    LocalContext.current, R.attr.backgroundColorOnBackground
                                )
                            ),
                            RoundedCornerShape(24)
                        )
                        .padding(8.dp)
                        .height(48.dp)
                ) {
                    Text(
                        text = "$targetTitle?",
                        fontSize = 18.sp,
                        color = Color(getColorFromAttribute(LocalContext.current, R.attr.textColorBody)),
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                            .padding(8.dp),
                        textAlign = TextAlign.Start
                    )

                    Button(
                        contentPadding = PaddingValues(8.dp),
                        colors = ButtonColors(
                            contentColor =  LocalPalette.current.background.color,
                            containerColor =  LocalPalette.current.coreFamily.primary,
                            disabledContentColor = Color.Blue,
                            disabledContainerColor = Color.Green
                        ),
                        shape = RoundedCornerShape(percent = 50),
                        onClick = {
                            onConfirm()
                            timeLeft = timeout
                            isVisible = false
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .wrapContentWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_arrow_chevron_right),
                            colorFilter = ColorFilter.tint(Color(getColorFromAttribute(
                                LocalContext.current, R.attr.textColorBody))),
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }
}