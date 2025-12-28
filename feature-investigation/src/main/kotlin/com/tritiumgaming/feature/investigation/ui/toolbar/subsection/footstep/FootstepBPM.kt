package com.tritiumgaming.feature.investigation.ui.toolbar.subsection.footstep

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette

@Composable
private fun Display() {

    var rememberTaps by remember {
        mutableIntStateOf(0)
    }

    var rememberStartTime by remember {
        mutableLongStateOf(0L)
    }

    var tempo by remember {
        mutableFloatStateOf(0f)
    }

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val metronomeValue by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Surface(
        color = LocalPalette.current.surface
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {

            Text(
                text = "Taps: $rememberTaps",
                color = LocalPalette.current.onSurface
            )

        }
    }


    /*Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {



    }*/


}

@Composable
@Preview
private fun TestDisplay() {

    SelectiveTheme {
        Display()
    }

}