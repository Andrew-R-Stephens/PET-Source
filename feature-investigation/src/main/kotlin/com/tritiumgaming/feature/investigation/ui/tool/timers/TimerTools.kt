package com.tritiumgaming.feature.investigation.ui.tool.timers

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.impl.base.BloodMoonIcon
import com.tritiumgaming.core.ui.icon.impl.base.GridIcon
import com.tritiumgaming.core.ui.icon.impl.base.TrophyIcon
import com.tritiumgaming.core.ui.icon.impl.composite.FingerprintDurationIcon
import com.tritiumgaming.core.ui.icon.impl.composite.HuntDurationIcon
import com.tritiumgaming.feature.investigation.app.mappers.difficulty.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.map.toStringResource
import com.tritiumgaming.core.ui.mapper.toStringResource
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalTypography
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources

@Composable
internal fun TimerTools(
    modifier: Modifier = Modifier,
    isCursedInvestigation: Boolean = false,
    difficultyTitle: DifficultyResources.DifficultyTitle = DifficultyResources.DifficultyTitle.AMATEUR,
    mapSize: MapModifierResources.MapSize = MapModifierResources.MapSize.SMALL,
    huntDuration: DifficultySettingResources.HuntDuration = DifficultySettingResources.HuntDuration.MEDIUM,
    fingerprintDuration: DifficultySettingResources.FingerprintDuration = DifficultySettingResources.FingerprintDuration.DURATION_120,
    component: @Composable ColumnScope.(Modifier) -> Unit = {},
) {
    val scrollState = rememberScrollState()

    Column (
        modifier = modifier
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically) {

            HorizontalDivider(
                modifier = Modifier
                    .weight(1f),
                color = LocalPalette.current.onSurfaceVariant,
                thickness = Dp.Hairline
            )

            Text(
                modifier = Modifier
                    .wrapContentWidth(),
                text = stringResource(R.string.investigation_label_tool_timers).uppercase(),
                color = LocalPalette.current.onSurfaceVariant,
                style = LocalTypography.current.quaternary.bold.copy(
                    textAlign = TextAlign.Start
                ),
                fontSize = 18.sp,
                maxLines = 1
            )

            HorizontalDivider(
                modifier = Modifier
                    .weight(1f),
                color = LocalPalette.current.onSurfaceVariant,
                thickness = Dp.Hairline
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.general_label_modifiers).uppercase(),
                color = LocalPalette.current.onSurfaceVariant,
                style = LocalTypography.current.quaternary.bold.copy(
                    textAlign = TextAlign.Start
                ),
                fontSize = 14.sp,
                maxLines = 1
            )

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                verticalArrangement = Arrangement.Center,
                itemVerticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Image(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(R.drawable.ic_trophy),
                        colorFilter = ColorFilter.tint(LocalPalette.current.onSurfaceVariant),
                        contentDescription = ""
                    )
                    Text(
                        text = stringResource(difficultyTitle.toStringResource()),
                        style = LocalTypography.current.quaternary.regular,
                        fontSize = 12.sp,
                        color = LocalPalette.current.onSurfaceVariant
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Image(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(R.drawable.icon_nav_mapmenu2),
                        colorFilter = ColorFilter.tint(LocalPalette.current.onSurfaceVariant),
                        contentDescription = ""
                    )
                    Text(
                        text = stringResource(mapSize.toStringResource()),
                        style = LocalTypography.current.quaternary.regular,
                        fontSize = 12.sp,
                        color = LocalPalette.current.onSurfaceVariant
                    )
                }

                if (true) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Image(
                            modifier = Modifier.size(16.dp),
                            painter = painterResource(R.drawable.ic_map_cp_ouija),
                            colorFilter = ColorFilter.tint(LocalPalette.current.onSurfaceVariant),
                            contentDescription = ""
                        )
                        Text(
                            text = stringResource(R.string.tool_timer_label_cursed),
                            style = LocalTypography.current.quaternary.regular,
                            fontSize = 12.sp,
                            color = LocalPalette.current.onSurfaceVariant
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    HuntDurationIcon(
                        modifier = Modifier.size(16.dp),
                        colors = IconVectorColors.defaults().copy(
                            fillColor = LocalPalette.current.onSurfaceVariant
                        )
                    )
                    Text(
                        text = stringResource(huntDuration.toStringResource()),
                        style = LocalTypography.current.quaternary.regular,
                        fontSize = 12.sp,
                        color = LocalPalette.current.onSurfaceVariant
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    FingerprintDurationIcon(
                        modifier = Modifier.size(16.dp),
                        colors = IconVectorColors.defaults().copy(
                            fillColor = LocalPalette.current.onSurfaceVariant
                        )
                    )
                    Text(
                        text = stringResource(fingerprintDuration.toStringResource()),
                        style = LocalTypography.current.quaternary.regular,
                        fontSize = 12.sp,
                        color = LocalPalette.current.onSurfaceVariant
                    )
                }

            }
        }


        component(Modifier)
    }
}

