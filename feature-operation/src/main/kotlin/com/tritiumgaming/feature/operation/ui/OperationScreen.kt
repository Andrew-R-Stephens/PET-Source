package com.tritiumgaming.feature.operation.ui

import android.media.MediaPlayer
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.core.resources.R

@Composable
@Preview
private fun OperationScreenPreview() {
    OperationScreen {}
}

@Composable
fun OperationScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    shouldTriggerAudio: Boolean = false,
    onAudioTriggered: () -> Unit = {},
    content: @Composable (modifier: Modifier) -> Unit
) {

    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    LaunchedEffect(shouldTriggerAudio) {
        if (shouldTriggerAudio) {
            val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                configuration.locales[0]
            } else {
                @Suppress("DEPRECATION")
                configuration.locale
            }
            val audioRes = when (locale.language) {
                "de" -> R.raw.huntwarning_de
                "es" -> R.raw.huntwarning_es
                "fr" -> R.raw.huntwarning_fr
                else -> R.raw.huntwarning_en
            }
            val mediaPlayer = MediaPlayer.create(context, audioRes)
            mediaPlayer?.apply {
                setOnCompletionListener { it.release() }
                start()
            }
            onAudioTriggered()
        }
    }

    OperationNavigationBar(
        navController = navController,
    ) {
        content(modifier)
    }

}
