package com.TritiumGaming.phasmophobiaevidencepicker.views.composables

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
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.utils.ColorUtils
import com.TritiumGaming.phasmophobiaevidencepicker.utils.ColorUtils.getColorFromAttribute

@Preview
@Composable
fun LogoutDialog(
    onConfirm: () -> Unit = { },
    onCancel: () -> Unit = { }
) {
    val backgroundColor = ColorUtils.interpolate(
        getColorFromAttribute(LocalContext.current, R.attr.backgroundColor),
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
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_logo_app),
                        contentDescription = "Phasmophobia Evidence Tool Logo",
                        modifier = Modifier
                            .size(64.dp)
                            .background(
                                Color(
                                    getColorFromAttribute(
                                        LocalContext.current,
                                        R.attr.backgroundColor
                                    )
                                ),
                                CircleShape
                            )
                            .padding(8.dp)
                            .align(Alignment.Center)
                    )
                }

                Text(
                    text = stringResource(id = R.string.account_logout_title),
                    maxLines = 1,
                    style = TextStyle(
                        fontSize = 24.sp,
                        color = Color(getColorFromAttribute(
                            LocalContext.current, R.attr.theme_colorPrimary)))
                )

                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                ) {

                    Column(
                        verticalArrangement = Arrangement.spacedBy(18.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = stringResource(id = R.string.account_logout_warning),
                            style = TextStyle(
                                fontSize = 18.sp,
                                color = Color(getColorFromAttribute(
                                    LocalContext.current, R.attr.textColorBody)))
                        )

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
                                        contentColor = Color(
                                            getColorFromAttribute(
                                                LocalContext.current,
                                                R.attr.textColorBody
                                            )
                                        ),
                                        containerColor = Color(
                                            getColorFromAttribute(
                                                LocalContext.current,
                                                R.attr.theme_colorPrimary
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
                        }

                        else -> {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(24.dp),
                                modifier = Modifier
                                    .padding(PaddingValues(top = 8.dp))
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                            ) {
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
                                        .weight(1f)
                                        .height(48.dp)
                                )

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
                                        contentColor = Color(
                                            getColorFromAttribute(
                                                LocalContext.current,
                                                R.attr.textColorBody
                                            )
                                        ),
                                        containerColor = Color(
                                            getColorFromAttribute(
                                                LocalContext.current,
                                                R.attr.theme_colorPrimary
                                            )
                                        ),
                                        disabledContentColor = Color.Blue,
                                        disabledContainerColor = Color.Green
                                    ),
                                    shape = RoundedCornerShape(percent = 20),
                                    onClick = { onConfirm() },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .height(48.dp)
                                )

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
fun DeleteAccountDialog(
    onConfirm: () -> Unit = { },
    onCancel: () -> Unit = { }
) {

    val backgroundColor = ColorUtils.interpolate(
        getColorFromAttribute(LocalContext.current, R.attr.backgroundColor),
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
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_logo_app),
                        contentDescription = "Phasmophobia Evidence Tool Logo",
                        modifier = Modifier
                            .size(64.dp)
                            .background(
                                Color(
                                    getColorFromAttribute(
                                        LocalContext.current,
                                        R.attr.backgroundColor
                                    )
                                ),
                                CircleShape
                            )
                            .padding(8.dp)
                            .align(Alignment.Center)
                    )
                }

                Text(
                    text = stringResource(id = R.string.account_deactivate_title),
                    maxLines = 1,
                    style = TextStyle(
                        fontSize = 24.sp,
                        color = Color(getColorFromAttribute(
                            LocalContext.current, R.attr.theme_colorPrimary)))
                )

                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                ) {

                    Column(
                        verticalArrangement = Arrangement.spacedBy(18.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = stringResource(id = R.string.account_deactivate_warning),
                            style = TextStyle(
                                fontSize = 18.sp,
                                color = Color(getColorFromAttribute(
                                    LocalContext.current, R.attr.textColorBody)))
                        )

                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .align(Alignment.CenterHorizontally)
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

                    val configuration = LocalConfiguration.current
                    when (configuration.orientation) {
                        Configuration.ORIENTATION_PORTRAIT -> {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(24.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(PaddingValues(top = 8.dp))
                            ) {
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
                                        contentColor = Color(
                                            getColorFromAttribute(
                                                LocalContext.current,
                                                R.attr.textColorBody
                                            )
                                        ),
                                        containerColor = Color(
                                            getColorFromAttribute(
                                                LocalContext.current,
                                                R.attr.theme_colorPrimary
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
                        }

                        else -> {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(24.dp),
                                modifier = Modifier
                                    .padding(PaddingValues(top = 8.dp))
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                            ) {
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
                                        .weight(1f)
                                        .height(48.dp)
                                )

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
                                        contentColor = Color(
                                            getColorFromAttribute(
                                                LocalContext.current,
                                                R.attr.textColorBody
                                            )
                                        ),
                                        containerColor = Color(
                                            getColorFromAttribute(
                                                LocalContext.current,
                                                R.attr.theme_colorPrimary
                                            )
                                        ),
                                        disabledContentColor = Color.Blue,
                                        disabledContainerColor = Color.Green
                                    ),
                                    shape = RoundedCornerShape(percent = 20),
                                    onClick = { onConfirm() },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .height(48.dp)
                                )

                            }
                        }
                    }
                }

            }
        }

    }

}