package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.TritiumGaming.phasmophobiaevidencepicker.activities.pet.PETActivity
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.sharedpreferences.MainMenuViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.jobs.DeltaRunnable
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class StartScreenAnimationView : View {

    constructor(context: Context?) :
            super(context)
    constructor(context: Context?, attrs: AttributeSet?) :
            super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    private var mainMenuViewModel: MainMenuViewModel? = null

    var canAnimate = true

    private val paint = Paint()

    private val animationScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    private var tickJob: Job? = null
        get() {
            return field ?: animationScope.launch {
                object : DeltaRunnable(context, 24f, 24f) {
                    override fun runCondition(): Boolean { return canAnimate }
                    override fun onTick() {
                        try { mainMenuViewModel?.animationModel?.tick(context) }
                        catch (e: Exception) { e.printStackTrace() }
                    }
                }.run()
            }
        }
    private var drawJob: Job? = null
        get() {
            return field ?: animationScope.launch {
                object : DeltaRunnable(context, 24f, 24f) {
                    override fun runCondition(): Boolean { return canAnimate }
                    override fun onTick() {
                        try { (context as PETActivity).runOnUiThread {
                            this@StartScreenAnimationView.invalidate() } }
                        catch (e: IllegalStateException) { e.printStackTrace() }
                    }
                }.run()
            }
        }

    fun init(mainMenuViewModel: MainMenuViewModel?) {
        this.mainMenuViewModel = mainMenuViewModel

        CoroutineScope(Dispatchers.Default).launch(CoroutineName("InitAnimationModel")) {
            mainMenuViewModel?.animationModel?.init(context)
        }.start()

        startAnimation()
    }

    private fun startAnimation() {
        canAnimate = true
        tickJob?.start()
        drawJob?.start()
    }

    fun stopAnimation() {
        canAnimate = false
        drawJob?.cancel("Cancelling drawJob")
        tickJob?.cancel("Cancelling tickJob")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.style = Paint.Style.FILL
        mainMenuViewModel?.animationModel?.draw(canvas, paint)
    }

}
