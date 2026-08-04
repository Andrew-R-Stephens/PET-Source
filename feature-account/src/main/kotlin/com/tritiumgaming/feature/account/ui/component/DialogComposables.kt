package com.tritiumgaming.feature.account.ui.component

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.LocalPalette

//@Preview
@Composable
fun Dialog(
    title: String = "",
    content: @Composable () -> Unit = { },
    confirmButton: @Composable () -> Unit = { },
    cancelButton: @Composable () -> Unit = { },
    backgroundColor: Color = Color.Transparent,
    scrimColor: Color = Color.Transparent,
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(scrimColor)
        .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
                .background(backgroundColor)
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
                                LocalPalette.current.onSurface,
                                CircleShape)
                            .padding(8.dp)
                            .align(Alignment.Center)
                    )
                }

                Text(
                    text = title,
                    maxLines = 1,
                    style = TextStyle(
                        fontSize = 24.sp,
                        color =  LocalPalette.current.primary
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
