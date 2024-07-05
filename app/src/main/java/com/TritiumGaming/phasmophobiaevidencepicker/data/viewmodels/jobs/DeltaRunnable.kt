package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.jobs

import android.content.Context
import android.os.Build
import java.lang.Thread.sleep

abstract class DeltaRunnable(
    context: Context? = null, val fpsMax: Float = 24f, var fpsTarget: Float = 60f
) : Runnable {
    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                context?.display?.let { display ->
                    fpsTarget = (display.refreshRate).coerceAtMost(fpsMax)
                }
            } catch (e: IllegalStateException) { e.printStackTrace() }
        }
    }

    override fun run() {
        while (runCondition()) {
            onTick()
            try { doWait() }
            catch (e: Exception) {
                when(e) {
                    is IllegalStateException, is InterruptedException ->
                        e.printStackTrace() }
            }
        }
    }

    private fun doWait() {
        val now = System.nanoTime()
        val updateTime = System.nanoTime() - now

        val optimalTime = (1000000000 / fpsTarget).toLong()
        var wait = ((optimalTime - updateTime) / 1000000.0).toLong()

        if (wait < 0) { wait = 1 }

        try { sleep(wait) }
        catch (e: Exception) { e.printStackTrace() }
    }

    abstract fun runCondition(): Boolean
    abstract fun onTick()
    //abstract fun onTick()
}