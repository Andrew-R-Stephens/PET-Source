package com.tritiumgaming.feature.investigation.ui.tool.phase

import android.media.MediaPlayer
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.intl.Locale
import com.tritiumgaming.core.resources.R

@Composable
fun HuntAlertAudioComponent(
    enabled: Boolean
) {
    val context = LocalContext.current

    val mediaPlayer = remember(Locale.current.platformLocale.language) {
        val audioFile = when (Locale.current.platformLocale.language) {
            "es" -> R.raw.huntwarning_es
            "fr" -> R.raw.huntwarning_fr
            "de" -> R.raw.huntwarning_de
            else -> R.raw.huntwarning_en
        }

        MediaPlayer.create(context, audioFile)
    }
    if(enabled && !mediaPlayer.isPlaying) {
        mediaPlayer.start()
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }
}