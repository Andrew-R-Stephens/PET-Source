package com.tritiumgaming.feature.investigation.ui.tool.sanity


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.common.util.ColorUtils
import com.tritiumgaming.core.common.util.FormatterUtils.toPercentageString
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.investigation.ui.common.sanitymeter.SanityMeter

@Composable
internal fun SanityMeterComponent(
    modifier: Modifier = Modifier,
    sanityLevel: Float,
    insanityLevel: Float,
    onSanityChange: (Float) -> Unit
) {
    val sanityPercentString = sanityLevel.toPercentageString()

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SanityMeter(
            modifier = Modifier
                .aspectRatio(1f),
            sanityLevel = sanityLevel,
            showText = false,
            showProgress = false
        )


        Text(
            modifier = Modifier
                .wrapContentSize(),
            text = sanityPercentString,
            maxLines = 1,
            style = LocalTypography.current.tertiary.bold.copy(
                textAlign = TextAlign.Center
            ),
            color = Color(ColorUtils.interpolate(
                LocalPalette.current.onSurface.toArgb(),
                endColor = LocalPalette.current.primary.toArgb(),
                sanityLevel)
            ),
            fontSize = 14.sp,
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight()
        ) {
            SanitySeekbar(
                modifier = Modifier
                    .fillMaxWidth(),
                state = insanityLevel,
                onValueChange = {
                    onSanityChange(it)
                },
                containerColor = Color.Transparent,
                inactiveTrackColor = LocalPalette.current.onSurface,
                activeTrackColor = LocalPalette.current.primary,
                thumbOutlineColor = LocalPalette.current.primaryContainer,
                thumbInnerColor = LocalPalette.current.primaryContainer,
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SanitySeekbar(
    modifier: Modifier = Modifier,
    state: Float,
    containerColor: Color = Color.White,
    inactiveTrackColor: Color = Color.White,
    activeTrackColor: Color = Color.White,
    thumbOutlineColor: Color = Color.White,
    thumbInnerColor: Color = Color.White,
    onValueChange: (Float) -> Unit = {}
) {

    var rememberSliderPosition by remember { mutableFloatStateOf(state) }

    val rememberSliderState =
        rememberSliderState(
            value = state,
            valueRange = 0f..1f,
            steps = 100,
            onValueChangeFinished = {
                onValueChange(rememberSliderPosition)
                Log.d("Slider", "Slider finished $rememberSliderPosition $state")
            }
        )

    LaunchedEffect(state) {
        rememberSliderPosition = state
        rememberSliderState.value = state
    }

    val interactionSource = remember { MutableInteractionSource() }

    Card(
        modifier = modifier
            .wrapContentHeight(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {

        Slider(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            value = rememberSliderPosition,
            onValueChange = {
                rememberSliderPosition = it
                rememberSliderState.value = it
                onValueChange(rememberSliderPosition)
            },
            onValueChangeFinished = {
                onValueChange(rememberSliderPosition)
                Log.d("Slider",
                    "Slider finished $rememberSliderPosition $state")
            },
            valueRange = 0f..1f,
            interactionSource = interactionSource,
            thumb = {
                SeekbarThumb(
                    outlineColor = thumbOutlineColor,
                    innerColor = thumbInnerColor
                )
            },
            track = {
                SliderDefaults.Track(
                    modifier = Modifier
                        .height(3.dp),
                    colors = SliderDefaults.colors(
                        activeTrackColor = activeTrackColor,
                        inactiveTrackColor = inactiveTrackColor,
                        activeTickColor = Color.Transparent,
                        inactiveTickColor = Color.Transparent,
                        disabledInactiveTickColor = Color.Transparent,
                        disabledThumbColor = Color.Transparent,
                        disabledActiveTrackColor = Color.Transparent,
                        disabledInactiveTrackColor = Color.Transparent,
                        disabledActiveTickColor = Color.Transparent,
                        thumbColor = Color.Transparent
                    ),
                    sliderState = rememberSliderState,
                    thumbTrackGapSize = 0.dp
                )
            },
            colors = SliderDefaults.colors(
                activeTrackColor = activeTrackColor,
                inactiveTrackColor = inactiveTrackColor,
                activeTickColor = Color.Transparent,
                inactiveTickColor = Color.Transparent
            )
        )


    }
}

@Composable
private fun SeekbarThumb(
    modifier: Modifier = Modifier
        .size(ButtonDefaults.IconSize),
    outlineColor: Color = Color.White,
    innerColor: Color = Color.White
) {
    Box(
        modifier = modifier
            .border(2.dp, outlineColor, CircleShape)
            .padding(2.dp)
            .clip(CircleShape)
            .background(innerColor)
    )
}

internal data class SanitySeekbarUiState(
    val value: Float
)

internal data class SanitySeekbarComponentActions(
    val onSanityChange: (Float) -> Unit
)
