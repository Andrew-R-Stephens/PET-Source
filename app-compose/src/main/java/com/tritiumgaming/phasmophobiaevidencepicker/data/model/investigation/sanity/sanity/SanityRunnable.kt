package com.tritiumgaming.phasmophobiaevidencepicker.data.model.investigation.sanity.sanity

import android.content.Context
import android.media.MediaPlayer
import com.tritiumgaming.phasmophobiaevidencepicker.data.jobs.DeltaRunnable

/**
 * Contains references to all Views for Sanity-specific UI and updates them via Runnable.
 * @author TritiumGamingStudios
 */
abstract class SanityRunnable (
    context: Context, fpsMax: Float = 24f, fpsTarget: Float = 24f
) : DeltaRunnable(context, fpsMax, fpsTarget) {

    var running = true

    fun start() {
        running = true
        run()
    }

    fun stop() {
        running = false
    }

    var huntWarningAudioListener: HuntWarningAudioListener? = null
    abstract class HuntWarningAudioListener {
        var lang: String? = null
        var mediaPlayer: MediaPlayer? = null
        abstract fun init()
        abstract fun play()
        abstract fun stop()
    }
}