package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.appsettings.content

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.GlobalPreferencesViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.viewmodel.InvestigationViewModel.Companion.percentAsTime
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.viewmodel.InvestigationViewModel.Companion.timeAsPercent
import org.jetbrains.annotations.TestOnly
import java.lang.String.format
import java.util.Locale

@Composable
@Preview
@TestOnly
private fun HuntSeekbarPreview() {
    SelectiveTheme(
        palette = ClassicPalette
    ) {
        Column {
            SeekbarThumb()

            HuntTimeoutPreferenceSeekbar()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HuntTimeoutPreferenceSeekbar(
    globalPreferencesViewModel: GlobalPreferencesViewModel =
        viewModel ( factory = GlobalPreferencesViewModel.Factory )
) {
    val timeoutState = globalPreferencesViewModel.huntWarnDurationPreference.collectAsStateWithLifecycle()
    var rememberSliderPosition by remember { mutableFloatStateOf(
        timeAsPercent(timeoutState.value)
    ) }

    LaunchedEffect(timeoutState.value) {
        rememberSliderPosition = timeAsPercent(timeoutState.value)
    }

    val rememberSliderState =
        rememberSliderState(
            value = timeAsPercent(timeoutState.value),
            valueRange = 0f..1f,
            steps = 100,
            onValueChangeFinished = {
                globalPreferencesViewModel.setHuntWarnDurationPreference(
                    percentAsTime(rememberSliderPosition)
                )
                //rememberSliderPosition = PhaseHandler.timeAsPercent(timeoutState.value)
                Log.d("Slider", "Slider finished $rememberSliderPosition ${timeoutState.value}")
            }
        )

    val interactionSource = remember { MutableInteractionSource() }

    val colors = SliderDefaults.colors(
        activeTrackColor = LocalPalette.current.progressBarColorStart,
        inactiveTrackColor = LocalPalette.current.progressBarColorThumbGradientStart,
        activeTickColor = Color.Transparent,
        inactiveTickColor = Color.Transparent,
        disabledInactiveTickColor = Color.Transparent,
        disabledThumbColor = Color.Transparent,
        disabledActiveTrackColor = Color.Transparent,
        disabledInactiveTrackColor = Color.Transparent,
        disabledActiveTickColor = Color.Transparent,
        thumbColor = Color.Transparent
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = LocalPalette.current.surface.onColor
        )
    ) {

        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(4.dp)
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .align(Alignment.CenterHorizontally)
                    .padding(4.dp),
                text = stringResource(R.string.settings_huntwarningflashtimeout),
                color = LocalPalette.current.textFamily.body,
                style = LocalTypography.current.primary.regular,
                textAlign = TextAlign.Center,
                maxLines = 1,
                fontSize = 16.sp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val millis = percentAsTime(rememberSliderPosition)
                val minutes = millis / 1000 / 60
                val seconds = millis / 1000 % 60
                val time = format(Locale.US, "%2dm %2ds", minutes, seconds)

                BasicText(
                    modifier = Modifier
                        .fillMaxWidth(.2f)
                        .fillMaxHeight(),
                    text = "$time",
                    style = LocalTypography.current.quaternary.regular.copy(
                        color = LocalPalette.current.textFamily.body,
                        textAlign = TextAlign.Center
                    ),
                    autoSize = TextAutoSize.StepBased(12.sp, 18.sp, 1.sp),
                    maxLines = 1,
                )

                Slider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    value = rememberSliderPosition,
                    onValueChange = {
                        rememberSliderPosition = it
                        rememberSliderState.value = it
                    },
                    onValueChangeFinished = {
                        globalPreferencesViewModel.setHuntWarnDurationPreference(
                            percentAsTime(rememberSliderPosition)
                        )
                        Log.d("Slider", "Slider finished $rememberSliderPosition ${timeoutState.value}")
                    },
                    valueRange = 0f..1f,
                    interactionSource = interactionSource,
                    thumb = {
                        SeekbarThumb()
                    },
                    track = {
                        SliderDefaults.Track(
                            modifier = Modifier
                                .height(3.dp),
                            colors = colors,
                            sliderState = rememberSliderState,
                            thumbTrackGapSize = 0.dp
                        )
                    },
                    colors = SliderDefaults.colors(
                        activeTrackColor = LocalPalette.current.progressBarColorStart,
                        inactiveTrackColor = LocalPalette.current.progressBarColorEnd,
                        activeTickColor = Color.Transparent,
                        inactiveTickColor = Color.Transparent
                    )
                )
            }
        }
    }
}

@Composable
private fun SeekbarThumb(
    modifier: Modifier = Modifier
        .size(ButtonDefaults.IconSize)
) {
    Box(
        modifier = modifier
            .border(2.dp, LocalPalette.current.background.color, CircleShape)
            .padding(2.dp)
            .clip(CircleShape)
            .background(LocalPalette.current.progressBarColorThumbOutline)
    )
}