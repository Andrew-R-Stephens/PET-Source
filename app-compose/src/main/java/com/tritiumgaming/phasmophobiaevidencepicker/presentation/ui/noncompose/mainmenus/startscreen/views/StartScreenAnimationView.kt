package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.noncompose.mainmenus.startscreen.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.jobs.DeltaRunnable
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.startscreen.AnimationModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.noncompose.pet.PETActivity
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@Deprecated("Migrate to Jetpack Compose")
class StartScreenAnimationView : View {

    constructor(context: Context?) :
            super(context)
    constructor(context: Context?, attrs: AttributeSet?) :
            super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    private var animationModel: AnimationModel? = null

    var canAnimate = true

    private val paint = Paint()

    private val animationScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    private var tickJob: Job? = null
        get() {
            return field ?: animationScope.launch {
                object : DeltaRunnable(context, 24f, 24f) {
                    override fun runCondition(): Boolean { return canAnimate }
                    override fun onTick() {
                        try { animationModel?.tick(context) }
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

    fun init(animationModel: AnimationModel) {
        this.animationModel = animationModel

        CoroutineScope(Dispatchers.Default).launch(CoroutineName("InitAnimationModel")) {
            animationModel.init(context)
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
        animationModel?.draw(canvas, paint)
    }

}
