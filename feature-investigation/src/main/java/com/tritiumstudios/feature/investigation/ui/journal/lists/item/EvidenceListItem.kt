package com.tritiumstudios.feature.investigation.ui.journal.lists.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalettesMap
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.shared.data.evidence.model.RuledEvidence.Ruling
import org.jetbrains.annotations.TestOnly

@Composable
fun EvidenceListItem(
    modifier: Modifier = Modifier,
    state: Ruling,
    label: String = "",
    onToggle: (ruling: Ruling) -> Unit = {},
    onNameClick: () -> Unit = {}
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Top)
            .padding(2.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.Transparent,
            contentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp)
    ) {

        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
        val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

        when(deviceConfiguration) {
            DeviceConfiguration.MOBILE_PORTRAIT -> {
                EvidenceColumn(
                    modifier = modifier,
                    label = label,
                    state = state,
                    onToggle = { ruling ->
                        onToggle(ruling)
                    },
                    onNameClick = {
                        onNameClick()
                    }
                )
            }
            DeviceConfiguration.MOBILE_LANDSCAPE,
            DeviceConfiguration.TABLET_PORTRAIT,
            DeviceConfiguration.TABLET_LANDSCAPE,
            DeviceConfiguration.DESKTOP -> {
                EvidenceRow(
                    modifier = modifier,
                    label = label,
                    state = state,
                    onToggle = { ruling ->
                        onToggle(ruling)
                    },
                    onNameClick = {
                        onNameClick()
                    }
                )
            }
        }

    }

}

@Composable
private fun EvidenceRow(
    modifier: Modifier = Modifier,
    label: String,
    state: Ruling?,
    onToggle: (ruling: Ruling) -> Unit,
    onNameClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Top),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Box(
            modifier = Modifier
                .height(48.dp)
                .weight(1f)
                .clickable(onClick = {
                    onNameClick()
                })
                .padding(vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = label,
                style = LocalTypography.current.primary.regular.copy(
                    color = LocalPalette.current.onSurface,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(minFontSize = 1.sp, stepSize = 5.sp)
            )

        }

        Row(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            for (ruling in Ruling.entries) {

                Button(
                    modifier = Modifier
                        .weight(1f, false)
                        .sizeIn(maxWidth = 48.dp, maxHeight = 48.dp)
                        .aspectRatio(1f)
                        .wrapContentSize(),
                    onClick = {
                        onToggle(ruling)
                    },
                    colors = ButtonDefaults.buttonColors().copy(
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.Transparent,
                    ),
                    contentPadding = PaddingValues(horizontal = 2.dp, vertical = 0.dp),
                    shape = CircleShape,
                    content = {
                        RulingIcon(
                            ruling = ruling,
                            isSelected = state?.ordinal == ruling.ordinal
                        )
                    }
                )
            }


        }
    }
}

@Composable
private fun EvidenceColumn(
    modifier: Modifier,
    label: String,
    state: Ruling,
    onToggle: (ruling: Ruling) -> Unit = { },
    onNameClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Box(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clickable(onClick = {
                    onNameClick()
                }),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = label,
                style = LocalTypography.current.primary.regular.copy(
                    color = LocalPalette.current.onSurface,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(
                    minFontSize = 1.sp,
                    maxFontSize = 36.sp,
                    stepSize = 5.sp
                )
            )

        }

        Row(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .padding(2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            for (ruling in Ruling.entries) {

                Button(
                    modifier = Modifier
                        .weight(1f, false)
                        .aspectRatio(1f)
                        .wrapContentSize(),
                    onClick = {
                        onToggle(ruling)
                    },
                    colors = ButtonDefaults.buttonColors().copy(
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.Transparent,
                    ),
                    contentPadding = PaddingValues(2.dp),
                    shape = CircleShape,
                    content = {
                        RulingIcon(
                            modifier = Modifier
                                .size(48.dp),
                            ruling = ruling,
                            isSelected = state.ordinal == ruling.ordinal,
                        )
                    }
                )
            }


        }
    }
}

@Composable
private fun RowScope.RulingIcon(
    modifier: Modifier = Modifier,
    ruling: Ruling = Ruling.NEUTRAL,
    isSelected: Boolean = false
) {

    val onSelectedColor = when (ruling) {
        Ruling.NEGATIVE -> LocalPalette.current.primary
        Ruling.NEUTRAL -> LocalPalette.current.onSurface
        Ruling.POSITIVE -> LocalPalette.current.tertiary
    }
    val onUnselectedColor = LocalPalette.current.onSurface

    ruling.let { ruling ->
        Box(
            modifier = modifier
                .align(Alignment.CenterVertically)
        ) {
            Image(
                modifier = Modifier
                    .aspectRatio(1f)
                    .padding(4.dp),
                painter = painterResource(
                    id = when(ruling) {
                        Ruling.NEGATIVE -> R.drawable.ic_selector_neg_unsel
                        Ruling.NEUTRAL -> R.drawable.ic_selector_inc_unsel
                        Ruling.POSITIVE -> R.drawable.ic_selector_pos_unsel
                    }
                ),
                contentScale = ContentScale.Fit,
                contentDescription = "Evidence Icon",
                colorFilter = ColorFilter.tint(
                    when (isSelected) {
                        true -> onSelectedColor
                        false -> onUnselectedColor
                    }
                )
            )
            if(isSelected) {
                Image(
                    modifier = Modifier
                        .aspectRatio(1f),
                    painter = painterResource(id = R.drawable.ic_selector_selected),
                    contentScale = ContentScale.Fit,
                    contentDescription = "Evidence Icon",
                    colorFilter = ColorFilter.tint(onSelectedColor)
                )
            }
        }
    }

}

@Preview
@Composable
private fun RulingIconPreview() {

    SelectiveTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            RulingIcon(
                modifier = Modifier
                    .weight(1f),
                ruling = Ruling.NEGATIVE,
                isSelected = true
            )
            RulingIcon(
                modifier = Modifier
                    .weight(1f),
                ruling = Ruling.NEUTRAL,
                isSelected = false
            )
            RulingIcon(
                modifier = Modifier
                    .weight(1f),
                ruling = Ruling.POSITIVE,
                isSelected = false
            )
        }
    }

}

@Composable
@Preview
@TestOnly
private fun EvidenceItemPreview() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LocalPalettesMap.forEach {
            SelectiveTheme(
                palette = it.value,
                typography = ClassicTypography
            ) {
                Surface(
                    color = LocalPalette.current.surface
                ) {
                    Column {
                        EvidenceListItem(
                            label = "Test",
                            state = Ruling.NEGATIVE
                        )
                        EvidenceListItem(
                            label = "Test",
                            state = Ruling.NEUTRAL
                        )
                        EvidenceListItem(
                            label = "Test",
                            state = Ruling.POSITIVE
                        )
                    }
                }
            }
        }

    }
}