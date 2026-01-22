package com.tritiumgaming.phasmophobiaevidencepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.ui.common.prefabicon.BadgeIcon
import com.tritiumgaming.core.ui.icon.LogoPatreonIcon
import com.tritiumgaming.core.ui.icon.OpenInNewIcon
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.mappers.ToComposable
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalettesMap
import com.tritiumgaming.shared.core.ui.mappers.IconResources
import org.jetbrains.annotations.TestOnly

@Composable
@TestOnly
@Preview(showBackground = true)
fun PreviewPatreonBranding() {

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        LocalPalettesMap.toList().forEach { (_, palette) ->

            SelectiveTheme(
                palette = palette,
            ) {

                Surface(
                    color = palette.surface,
                ) {
                    Column {

                        Text(
                            text = stringResource(palette.extrasFamily.title),
                            fontSize = 18.sp,
                            color = LocalPalette.current.onSurface
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .wrapContentHeight(),
                            horizontalArrangement = Arrangement.spacedBy(32.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                modifier = Modifier
                                    .width(24.dp)
                                    .height(48.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            color = palette.coreFamily.primary
                                        )
                                ){}
                            }

                            BadgeIcon(
                                modifier = Modifier
                                    .size(48.dp)
                                    .padding(4.dp),
                                baseComponent = {
                                    IconResources.IconResource.DISCORD.ToComposable(
                                        colors = IconVectorColors.defaults(
                                            fillColor = palette.discordColor.color,
                                            strokeColor = palette.discordColor.onColor,
                                        )
                                    )
                                }
                            ) {
                                OpenInNewIcon(
                                    colors = IconVectorColors.defaults(
                                        fillColor = palette.textFamily.body,
                                        strokeColor = palette.surface,
                                    )
                                )
                            }

                            LogoPatreonIcon(
                                modifier = Modifier
                                    .size(48.dp),
                                colors = IconVectorColors.defaults(
                                    fillColor = palette.patreonColor.color
                                )
                            )
                        }
                    }
                }
            }

        }
    }

}