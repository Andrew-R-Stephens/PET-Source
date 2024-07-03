package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.views

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.View
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.pet.PETActivity
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MainMenuViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.jobs.DeltaRunnable
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.startscreen.AAnimatedModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.startscreen.graphicsdata.AnimatedFrostModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.startscreen.graphicsdata.AnimatedHandModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.startscreen.graphicsdata.AnimatedMirrorModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.startscreen.graphicsdata.AnimatedOrbModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.startscreen.graphicsdata.AnimatedWritingModel
import com.TritiumGaming.phasmophobiaevidencepicker.utils.BitmapUtils
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class StartScreenAnimationView : View {
    private var mainMenuViewModel: MainMenuViewModel? = null

    private var bitmapUtils: BitmapUtils = BitmapUtils()

    @Deprecated("Replaced by coroutine")
    private var threadInitAnim: Thread? = null
    @Deprecated("Replaced by coroutine")
    private var threadTickAnim: Thread? = null
    @Deprecated("Replaced by coroutine")
    private var threadRenderAnim: Thread? = null
    @Deprecated("Replaced by coroutine")
    private var threadInitReady: Thread? = null

    private var canAnimate = true

    private val paint = Paint()

    private var scope: CoroutineScope? = CoroutineScope(Dispatchers.Default)
    private var tickCoroutine: Job? = null

    constructor(context: Context?) :
            super(context)
    constructor(context: Context?, attrs: AttributeSet?) :
            super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    fun init(mainMenuViewModel: MainMenuViewModel?) {
        this.mainMenuViewModel = mainMenuViewModel

        mainMenuViewModel?.animationModel?.init(context)
    }

    /*
    private fun initImages() {
        mainMenuViewModel?.animationModel?.let { animationModel ->
            try { bitmapOrb = bitmapUtils.setResource(
                R.drawable.anim_ghostorb).compileBitmaps(context)
            } catch (e: Exception) { e.printStackTrace() }

            try { bitmapFrost = bitmapUtils.setResource(
                R.drawable.anim_frost).compileBitmaps(context)
            } catch (e: Exception) { e.printStackTrace() }

            try { bitmapHand = bitmapUtils.setResource(
                handResIds[animationModel.selectedHand]).compileBitmaps(context)
            } catch (e: Exception) { e.printStackTrace() }

            try { bitmapWriting = bitmapUtils.setResource(
                writingResIds[animationModel.selectedWriting]).compileBitmaps(context)
            } catch (e: Exception) { e.printStackTrace() }

            try { bitmapMirror = bitmapUtils.setResource(R.drawable.anim_mirror_crack)
                    .addResource(R.drawable.anim_mirror_gradient, PorterDuff.Mode.MULTIPLY)
                    .addResource(R.drawable.anim_mirror_crack, PorterDuff.Mode.MULTIPLY)
                    .compileBitmaps(context)
            } catch (e: Exception) { e.printStackTrace() }
        }
    }*/

    /*

    private fun createQueue() {
        mainMenuViewModel?.animationModel?.let { animationModel ->
            val screenW = Resources.getSystem().displayMetrics.widthPixels
            val screenH = Resources.getSystem().displayMetrics.heightPixels

            for (g in animationModel.currentPool) { g.initDims(screenW, screenH) }

            if (animationModel.allPool.isNotEmpty()) {
                for (animated in animationModel.allPool) {
                    try {
                        when(animated) {
                            is AnimatedHandModel ->
                                bitmapHandRot = animated.rotateBitmap(bitmapHand)
                            is AnimatedWritingModel ->
                                bitmapWritingRot = animated.rotateBitmap(bitmapWriting)
                        }
                    } catch (e: IllegalStateException) { e.printStackTrace() }
                }
                return
            }

            //Add orbs
            for (i in 0 until ORB_COUNT) {
                if (BitmapUtils.bitmapExists(bitmapOrb)) {
                    animationModel.allPool.add(AnimatedOrbModel(screenW, screenH))
                }
            }
            //Add hands
            for (i in 0 until HAND_COUNT) {
                if (BitmapUtils.bitmapExists(bitmapHand)) {
                    bitmapHand?.let { bitmapHand ->
                        val bW = bitmapHand.width
                        val bH = bitmapHand.height
                        val data = AnimatedHandModel(screenW, screenH, bW, bH)
                        animationModel.allPool.add(data)
                        try { bitmapHandRot = data.rotateBitmap(bitmapHand) }
                        catch (e: IllegalStateException) { e.printStackTrace() }
                    }
                }
            }

            //Add writing
            for (i in 0 until WRITING_COUNT) {
                if (BitmapUtils.bitmapExists(bitmapWriting)) {
                    bitmapWriting?.let { bitmapWriting ->
                        val bW = bitmapWriting.width
                        val bH = bitmapWriting.height
                        val data = AnimatedWritingModel(screenW, screenH, bW, bH, animationModel)
                        animationModel.allPool.add(data)
                        try { bitmapWritingRot = data.rotateBitmap(bitmapWriting) }
                        catch (e: IllegalStateException) { e.printStackTrace() }
                    }
                }
            }

            //Add Frost
            for (i in 0 until FROST_COUNT) {
                if (BitmapUtils.bitmapExists(bitmapFrost)) {
                    animationModel.allPool.add(AnimatedFrostModel(screenW, screenH))
                }
            }

            //Add Mirror
            for (i in 0 until MIRROR_COUNT) {
                if (BitmapUtils.bitmapExists(bitmapMirror)) {
                    animationModel.allPool.add(AnimatedMirrorModel(screenW, screenH))
                }
            }

            //Create Queue
            animationModel.queue = AnimatedQueueModel(animationModel.allPoolSize, 750)
        }

    }
*/

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.style = Paint.Style.FILL

        mainMenuViewModel?.animationModel?.draw(canvas, paint)
    }

    fun startAnimCoroutine(titleScreenViewModel: MainMenuViewModel?) {
        init(titleScreenViewModel)
        scope?.launch(Dispatchers.Default + CoroutineName("InitCoroutine")) {
            startAnimThreads()
            //startAnimTickCoroutine()
        }?.start()
    }

    /*
    fun startAnimTickCoroutine() {
        tickCoroutine =
            scope.launch (Dispatchers.Default + CoroutineName("TickCoroutine")) {
            repeat(calcWait()) {

            }
        }
        tickCoroutine?.start()
    }

    private fun calcWait(fpsTarget: Float = 30f, fpsMax: Float = 60f) {
        var fpsTarget = fpsTarget
        val fpsMax = fpsMax

        val now = System.nanoTime()
        val updateTime = System.nanoTime() - now

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                context.display?.let { display ->
                    fpsTarget = (display.refreshRate).coerceAtMost(fpsMax)
                }
            } catch (e: IllegalStateException) { e.printStackTrace() }
        }
        //TARGET_FPS = 200;
        val optimalTime = (1000000000 / fpsTarget).toLong()
        var wait = ((optimalTime - updateTime) / 1000000.0).toLong()

        if (wait < 0) { wait = 1 }

        try { sleep(wait) }
        catch (e: Exception) { e.printStackTrace() }
    }

    private fun update() {
        try {
            this@StartScreenAnimationView.tick()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    */

    fun startAnimInitThreads(titleScreenViewModel: MainMenuViewModel?) {
        init(titleScreenViewModel)
        scope?.launch(Dispatchers.Default + CoroutineName("InitCoroutine")) {
            startAnimThreads()
            //startAnimTickCoroutine()
        }?.start()
    }

    @Deprecated(message = "Replaced by coroutine")
    private fun startAnimTickThread() {
        if (threadTickAnim == null) {
            canAnimate = true

            threadTickAnim = Thread(
                object : DeltaRunnable(context, fpsTarget = 30f, fpsMax = 60f) {
                    override fun runCondition(): Boolean { return canAnimate }

                    override fun onTick() {
                        try { mainMenuViewModel?.animationModel?.tick(context) }
                        catch (e: Exception) { e.printStackTrace() }
                    }
                })
            threadTickAnim?.start()
        }
    }

    @Deprecated("Replaced by coroutine")
    private fun startAnimDrawThread() {
        if (threadRenderAnim != null) { return }

        threadRenderAnim = Thread(object : DeltaRunnable(context, 24f, 60f) {
            override fun runCondition(): Boolean { return canAnimate }

            override fun onTick() {
                try { (context as PETActivity).runOnUiThread {
                    this@StartScreenAnimationView.invalidate() } }
                catch (e: IllegalStateException) { e.printStackTrace() }
            }
        })
        threadRenderAnim?.start()
    }

    fun startAnimThreads() {
        startAnimTickThread()
        startAnimDrawThread()
    }

    fun stopAnimInitThreads() {
        threadInitAnim?.interrupt()
        threadInitAnim = null

        threadInitReady?.interrupt()
        threadInitReady = null
    }

    private fun stopAnimTickThread() {
        threadTickAnim?.interrupt()
        threadTickAnim = null
    }

    private fun stopAnimDrawThread() {
        threadRenderAnim?.interrupt()
        threadRenderAnim = null
    }

    fun stopAnimThreads() {
        stopAnimDrawThread()
        stopAnimTickThread()
    }

    fun canAnimateBackground(canAnimateBackground: Boolean) {
        this.canAnimate = canAnimateBackground
    }
}
