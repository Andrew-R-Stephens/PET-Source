package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.views

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.os.Build
import android.util.AttributeSet
import android.view.View
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.pet.PETActivity
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MainMenuViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.startscreen.AAnimatedModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.startscreen.AnimatedQueueModel
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
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.lang.Thread.sleep
import java.util.concurrent.CancellationException

class StartScreenAnimationView : View {
    private var mainMenuViewModel: MainMenuViewModel? = null

    private var bitmapUtils: BitmapUtils? = null

    private var threadInitAnim: Thread? = null
    private var threadTickAnim: Thread? = null
    private var threadRenderAnim: Thread? = null
    private var threadInitReady: Thread? = null

    private var canAnimate = true

    private var writingResIds = ArrayList<Int>()
    private var handResIds = ArrayList<Int>()

    private val paint = Paint()

    private var bitmapOrb: Bitmap? = null
    private var bitmapHand: Bitmap? = null
    private var bitmapWriting: Bitmap? = null
    private var bitmapFrost: Bitmap? = null
    private var bitmapMirror: Bitmap? = null
    private var bitmapHandRot: Bitmap? = null
    private var bitmapWritingRot: Bitmap? = null

    private var scope: CoroutineScope? = null
    private var tickCoroutine: Job? = null

    constructor(context: Context?) :
            super(context)
    constructor(context: Context?, attrs: AttributeSet?) :
            super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    fun init(
        mainMenuViewModel: MainMenuViewModel?,
        bitmapUtils: BitmapUtils?
    ) {
        this.mainMenuViewModel = mainMenuViewModel
        this.bitmapUtils = bitmapUtils

        //Set writing resources
        val bookwritingArray = resources.obtainTypedArray(R.array.anim_titlescreen_images)
        for (i in 0 until bookwritingArray.length()) {
            writingResIds.add(bookwritingArray.getResourceId(i, 0))
        }
        bookwritingArray.recycle()

        //Set hand resources
        val handUVArray = resources.obtainTypedArray(R.array.anim_hand_images)
        for (i in 0 until handUVArray.length()) {
            handResIds.add(handUVArray.getResourceId(i, 0))
        }
        handUVArray.recycle()

        mainMenuViewModel?.animationModel?.let { animationModel ->
            if (animationModel.selectedWriting == -1) {
                animationModel.selectedWriting = (Math.random() * writingResIds.size).toInt()
            }
            if (animationModel.selectedHand == -1) {
                animationModel.selectedHand = (Math.random() * handResIds.size).toInt()
            }
        }

        // Starts a new coroutine on Dispatchers.Default
        scope?.cancel(CancellationException("Graceful termination"))
        scope = CoroutineScope(Job() + Dispatchers.Default)
        scope?.launch(
            Dispatchers.Default + CoroutineName("ImagesCoroutine")) {
            initImages()
            buildImages()
        }?.start()

    }

    private fun initImages() {
        mainMenuViewModel?.animationModel?.let { animationModel ->
            bitmapOrb = bitmapUtils?.setResource(R.drawable.anim_ghostorb)?.compileBitmaps(context)

            bitmapFrost = bitmapUtils?.setResource(R.drawable.anim_frost)?.compileBitmaps(context)

            bitmapHand = bitmapUtils?.setResource(handResIds[animationModel.selectedHand])
                ?.compileBitmaps(context)
            bitmapWriting = bitmapUtils?.setResource(writingResIds[animationModel.selectedWriting])
                ?.compileBitmaps(context)

            bitmapMirror = bitmapUtils?.setResource(R.drawable.anim_mirror_crack)
                ?.addResource(R.drawable.anim_mirror_gradient, PorterDuff.Mode.MULTIPLY)
                ?.addResource(R.drawable.anim_mirror_crack, PorterDuff.Mode.MULTIPLY)
                ?.compileBitmaps(context)
        }
    }

    private fun buildImages() {
        mainMenuViewModel?.let { mainMenuViewModel ->
            val screenW = Resources.getSystem().displayMetrics.widthPixels
            val screenH = Resources.getSystem().displayMetrics.heightPixels

            val animationData = mainMenuViewModel.animationModel

            for (g in animationData.currentPool) {
                g.initDims(screenW, screenH)
            }

            if (animationData.hasData()) {
                for (animated in animationData.allPool) {
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

            val orbCount: Short = 3
            val handCount: Short = 1
            val writingCount: Short = 1
            val mirrorCount: Short = 1
            val frostCount: Short = 1

            //Add orbs
            for (i in 0 until orbCount) {
                if (BitmapUtils.bitmapExists(bitmapOrb)) {
                    animationData.addToAllPool(AnimatedOrbModel(screenW, screenH))
                }
            }
            //Add hands
            for (i in 0 until handCount) {
                if (BitmapUtils.bitmapExists(bitmapHand)) {
                    bitmapHand?.let { bitmapHand ->
                        val bW = bitmapHand.width
                        val bH = bitmapHand.height
                        val data = AnimatedHandModel(screenW, screenH, bW, bH)
                        animationData.addToAllPool(data)
                        try { bitmapHandRot = data.rotateBitmap(bitmapHand) }
                        catch (e: IllegalStateException) { e.printStackTrace() }
                    }
                }
            }

            //Add writing
            for (i in 0 until writingCount) {
                if (BitmapUtils.bitmapExists(bitmapWriting)) {
                    bitmapWriting?.let { bitmapWriting ->
                        val bW = bitmapWriting.width
                        val bH = bitmapWriting.height
                        val data = AnimatedWritingModel(screenW, screenH, bW, bH, animationData)
                        animationData.addToAllPool(data)
                        try { bitmapWritingRot = data.rotateBitmap(bitmapWriting) }
                        catch (e: IllegalStateException) { e.printStackTrace() }
                    }
                }
            }

            //Add Frost
            for (i in 0 until frostCount) {
                if (BitmapUtils.bitmapExists(bitmapFrost)) {
                    animationData.addToAllPool(AnimatedFrostModel(screenW, screenH))
                }
            }

            //Add Mirror
            for (i in 0 until mirrorCount) {
                if (BitmapUtils.bitmapExists(bitmapMirror)) {
                    animationData.addToAllPool(AnimatedMirrorModel(screenW, screenH))
                }
            }

            //Create Queue
            animationData.queue = AnimatedQueueModel(animationData.allPoolSize, 750)
        }

    }

    fun tick() {
        val screenW = Resources.getSystem().displayMetrics.widthPixels
        val screenH = Resources.getSystem().displayMetrics.heightPixels

        mainMenuViewModel?.animationModel?.let { animationModel ->

            animationModel.doTick()

            val maxQueue = 3
            if (animationModel.queue.canDequeue() && (animationModel.currentPoolSize < maxQueue)) {

                val animationQueue = animationModel.queue

                var index = 0
                var aTemp: AAnimatedModel? = null
                try {
                    index = animationQueue.dequeue()
                    aTemp = animationModel.getFromAllPool(index)
                    animationModel.addToCurrentPool(aTemp)
                } catch (e: ArrayIndexOutOfBoundsException) {
                    animationQueue.enqueue(index)
                    e.printStackTrace()
                }
                aTemp ?: return

                val lastAnimInList = animationModel.lastFromCurrentPool
                lastAnimInList?.let {
                    when(lastAnimInList) {
                        is AnimatedOrbModel -> {
                            if (BitmapUtils.bitmapExists(bitmapOrb)) {
                                animationModel.setToAllPool(
                                    index, AnimatedOrbModel(screenW, screenH)
                                )
                            }
                        }
                        is AnimatedHandModel -> {
                            if (BitmapUtils.bitmapExists(bitmapHand)) {
                                bitmapHand?.let { bitmapHand ->
                                    var bitmapW = 0
                                    var bitmapH = 0
                                    try {
                                        bitmapW = bitmapHand.width
                                        bitmapH = bitmapHand.height
                                    } catch (e: NullPointerException) { e.printStackTrace() }

                                    animationModel.setToAllPool(
                                        index, AnimatedHandModel(
                                            screenW, screenH, bitmapW, bitmapH)
                                    )

                                    bitmapHandRot =
                                        (animationModel.lastFromCurrentPool as AnimatedHandModel)
                                            .rotateBitmap(bitmapHand)
                                }

                            }
                        }
                        is AnimatedWritingModel -> {
                            if (BitmapUtils.bitmapExists(bitmapWriting)) {
                                bitmapWriting?.let { bitmapWriting ->
                                    var bitmapW = 0
                                    var bitmapH = 0
                                    try {
                                        bitmapW = bitmapWriting.width
                                        bitmapH = bitmapWriting.height
                                    } catch (e: NullPointerException) {
                                        e.printStackTrace()
                                    }

                                    animationModel.setToAllPool(
                                        index, AnimatedWritingModel(
                                            screenW, screenH, bitmapW, bitmapH, animationModel)
                                    )

                                    bitmapWritingRot =
                                        (animationModel.lastFromCurrentPool as AnimatedWritingModel)
                                            .rotateBitmap(bitmapWriting)
                                }

                            }
                        }
                        is AnimatedFrostModel -> {
                            if (BitmapUtils.bitmapExists(bitmapFrost)) {
                                animationModel.setToAllPool(
                                    index, AnimatedFrostModel(screenW, screenH)
                                )
                            }
                        }
                        is AnimatedMirrorModel -> {
                            if (BitmapUtils.bitmapExists(bitmapMirror)) {
                                animationModel.setToAllPool(
                                    index, AnimatedMirrorModel(screenW, screenH)
                                )
                            }
                        }
                    }
                }
            }

            var i = 0
            while (i < (animationModel.currentPoolSize)) {

                val currentAnim = animationModel.getFromCurrentPool(i)
                currentAnim.doTick()

                /*
                 * If the chosen AnimatedGraphic is not alive, then remove it from the list
                 * If removed, replace it with a modified item of the same type
                 * Then try the next AnimatedGraphic
                 */
                if (!currentAnim.isAlive) {
                    when(currentAnim) {
                        is AnimatedHandModel -> {
                            animationModel.selectedHand = (Math.random() * handResIds.size).toInt()

                            bitmapHand = null
                            bitmapHandRot = null

                            bitmapUtils?.setResource(handResIds[animationModel.selectedHand])
                            bitmapHand = bitmapUtils?.compileBitmaps(context)

                            if (BitmapUtils.bitmapExists(bitmapHand)) {
                                bitmapHandRot = currentAnim.rotateBitmap(bitmapHand) }
                        }
                        is AnimatedWritingModel -> {
                            animationModel.selectedWriting =
                                (Math.random() * writingResIds.size).toInt()

                            bitmapWriting = null
                            bitmapWritingRot = null

                            bitmapUtils?.setResource(
                                writingResIds[animationModel.selectedWriting]
                            )
                            bitmapWriting = bitmapUtils?.compileBitmaps(context)

                            if (BitmapUtils.bitmapExists(bitmapWriting)) {
                                bitmapWritingRot = currentAnim.rotateBitmap(bitmapWriting)
                            }
                        }
                    }

                    try {
                        animationModel.removeFromCurrentPool(currentAnim)
                        i--
                    } catch (e: IndexOutOfBoundsException) { e.printStackTrace() }

                    System.gc()
                }
                i++
            }
        }
    }

    override fun onDraw(canvas: Canvas) {

        super.onDraw(canvas)

        paint.style = Paint.Style.FILL

        try {
            mainMenuViewModel?.animationModel?.currentPool?.forEach { a ->
                paint.setColorFilter(a.filter)
                try {
                    when(a) {
                        is AnimatedMirrorModel -> a.draw(canvas, paint, bitmapMirror)
                        is AnimatedWritingModel -> a.draw(canvas, paint, bitmapWritingRot)
                        is AnimatedHandModel -> a.draw(canvas, paint, bitmapHandRot)
                        is AnimatedOrbModel -> a.draw(canvas, paint, bitmapOrb)
                        is AnimatedFrostModel -> a.draw(canvas, paint, bitmapFrost)
                    }
                } catch (e: Exception) { e.printStackTrace() }
            }
        } catch (ex: ConcurrentModificationException) { ex.printStackTrace() }

    }

    fun recycleBitmaps() {
        bitmapOrb = null
        bitmapFrost = null
        bitmapHand = null
        bitmapWriting = null
        bitmapMirror = null
        bitmapHandRot = null
        bitmapWritingRot = null

        System.gc()
    }

    fun startAnimInitThreads(titleScreenViewModel: MainMenuViewModel?, bitmapUtils: BitmapUtils) {
        init(titleScreenViewModel, bitmapUtils)
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

    // @Deprecated(message = "Replaced by coroutine")
    private fun startAnimTickThread() {
        if (threadTickAnim == null) {
            canAnimate = true

            threadTickAnim = Thread(
                object : DeltaRunnable(context, fpsTarget = 30f, fpsMax = 60f) {
                override fun runCondition(): Boolean { return canAnimate }

                override fun onTick() {
                    try { this@StartScreenAnimationView.tick() }
                    catch (e: Exception) { e.printStackTrace() }
                }
            })
            threadTickAnim?.start()
        }
    }

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

    private abstract class DeltaRunnable(
        context: Context? = null, val fpsMax: Float = 24f, var fpsTarget: Float = 60f )
        : Runnable {

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
}
