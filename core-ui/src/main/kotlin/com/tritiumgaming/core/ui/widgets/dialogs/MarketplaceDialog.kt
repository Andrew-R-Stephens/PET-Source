package com.tritiumgaming.core.ui.widgets.dialogs

import android.content.res.Configuration
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
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalThemeProvider

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
        modifier = modifier,
        title = title,
        content = { content() },
        confirmButton = { confirmButton() }
    )
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
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
                .background(LocalPalette.current.surfaceContainer)
                .padding(16.dp)
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
                            .weight(1f, fill = false)
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
private fun MarketplaceDialogPreview() {
    LocalThemeProvider {
        MarketplaceDialog(
            modifier = Modifier
                .fillMaxSize()
                .background(LocalPalette.current.scrim.copy(alpha = .5f))
                .padding(8.dp)
        )
    }
}
