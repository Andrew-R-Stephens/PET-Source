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
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.AAnimatedModel
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.AnimatedQueueModel
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.graphicsdata.AnimatedFrostModel
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.graphicsdata.AnimatedHandModel
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.graphicsdata.AnimatedMirrorModel
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.graphicsdata.AnimatedOrbModel
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.graphicsdata.AnimatedWritingModel
import com.TritiumGaming.phasmophobiaevidencepicker.activities.pet.PETActivity
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MainMenuViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.utils.BitmapUtils

/**
 * TitleScreenAnimationView class
 *
 * @author TritiumGamingStudios
 */
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

    constructor(context: Context?) :
            super(context)
    constructor(context: Context?, attrs: AttributeSet?) :
            super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    /**
     * @param mainMenuViewModel The TitleScreenViewModel which contains necessary Animation data
     * @param bitmapUtils The BitmapUtils data which is used across all animations
     */
    fun init(
        mainMenuViewModel: MainMenuViewModel?,
        bitmapUtils: BitmapUtils?
    ) {
        this.mainMenuViewModel = mainMenuViewModel
        this.bitmapUtils = bitmapUtils

        //Set writing resources
        val bookwritingArray =
            resources.obtainTypedArray(R.array.anim_titlescreen_images)
        writingResIds = ArrayList()
        for (i in 0 until bookwritingArray.length()) {
            writingResIds.add(bookwritingArray.getResourceId(i, 0))
        }
        bookwritingArray.recycle()

        //Set hand resources
        val handUVArray =
            resources.obtainTypedArray(R.array.anim_hand_images)
        handResIds = ArrayList()
        for (i in 0 until handUVArray.length()) {
            handResIds.add(handUVArray.getResourceId(i, 0))
        }
        handUVArray.recycle()

        val data = this.mainMenuViewModel!!.animationData

        if (data.selectedWriting == -1) {
            data.selectedWriting = (Math.random() * writingResIds.size).toInt()
        }

        if (data.selectedHand == -1) {
            data.selectedHand = (Math.random() * handResIds.size).toInt()
        }

        buildImages()
        buildData()
    }

    fun buildImages() {

        val data = mainMenuViewModel?.animationData ?: return

        bitmapOrb = bitmapUtils!!.setResource(R.drawable.anim_ghostorb).compileBitmaps(context)

        bitmapFrost = bitmapUtils!!.setResource(R.drawable.anim_frost).compileBitmaps(context)

        bitmapHand = bitmapUtils!!.setResource(
            handResIds[data.selectedHand]
        ).compileBitmaps(context)
        bitmapWriting = bitmapUtils!!.setResource(
            writingResIds[data.selectedWriting]
        ).compileBitmaps(context)

        bitmapMirror = bitmapUtils!!.setResource(R.drawable.anim_mirror_crack)
            .addResource(R.drawable.anim_mirror_gradient, PorterDuff.Mode.MULTIPLY)
            .addResource(R.drawable.anim_mirror_crack, PorterDuff.Mode.MULTIPLY)
            .compileBitmaps(context)
    }

    fun buildData() {
        if (mainMenuViewModel == null) {
            return
        }

        val screenW = Resources.getSystem().displayMetrics.widthPixels
        val screenH = Resources.getSystem().displayMetrics.heightPixels

        val animationData = mainMenuViewModel!!.animationData

        for (g in animationData.currentPool) {
            g.initDims(screenW, screenH)
        }

        if (animationData.hasData()) {
            for (animated in animationData.allPool) {
                if (animated is AnimatedHandModel) {
                    try {
                        bitmapHandRot = animated.rotateBitmap(bitmapHand)
                    } catch (e: IllegalStateException) {
                        e.printStackTrace()
                    }
                } else if (animated is AnimatedWritingModel) {
                    try {
                        bitmapWritingRot = animated.rotateBitmap(bitmapWriting)
                    } catch (e: IllegalStateException) {
                        e.printStackTrace()
                    }
                }
            }
            return
        }

        val ORB_COUNT: Short = 3
        val HAND_COUNT: Short = 1
        val WRITING_COUNT: Short = 1
        val MIRROR_COUNT: Short = 1
        val FROST_COUNT: Short = 1

        //Add orbs
        for (i in 0 until ORB_COUNT) {
            if (BitmapUtils.bitmapExists(bitmapOrb)) {
                val data = AnimatedOrbModel(screenW, screenH)
                animationData.addToAllPool(data)
            }
        }
        //Add hands
        for (i in 0 until HAND_COUNT) {
            if (BitmapUtils.bitmapExists(bitmapHand)) {
                val bW = bitmapHand?.width ?: 0
                val bH = bitmapHand?.height ?: 0
                val data = AnimatedHandModel(
                    screenW, screenH, bW, bH
                )
                animationData.addToAllPool(data)
                try {
                    bitmapHandRot = data.rotateBitmap(bitmapHand!!)
                } catch (e: IllegalStateException) {
                    e.printStackTrace()
                }
            }
        }

        //Add writing
        for (i in 0 until WRITING_COUNT) {
            if (BitmapUtils.bitmapExists(bitmapWriting)) {
                val bW = bitmapWriting?.width ?: 0
                val bH = bitmapWriting?.height ?: 0
                val data = AnimatedWritingModel(
                    screenW, screenH, bW, bH, animationData
                )
                animationData.addToAllPool(data)
                try {
                    bitmapWritingRot = data.rotateBitmap(bitmapWriting!!)
                } catch (e: IllegalStateException) {
                    e.printStackTrace()
                }
            }
        }

        //Add Frost
        for (i in 0 until FROST_COUNT) {
            if (BitmapUtils.bitmapExists(bitmapFrost)) {
                val data = AnimatedFrostModel(screenW, screenH)
                animationData.addToAllPool(data)
            }
        }

        //Add Mirror
        for (i in 0 until MIRROR_COUNT) {
            if (BitmapUtils.bitmapExists(bitmapMirror)) {
                val data = AnimatedMirrorModel(screenW, screenH)
                animationData.addToAllPool(data)
            }
        }

        //Create Queue
        animationData.queue = AnimatedQueueModel(animationData.allPoolSize, 750)
    }

    fun tick() {
        mainMenuViewModel ?: return

        val screenW = Resources.getSystem().displayMetrics.widthPixels
        val screenH = Resources.getSystem().displayMetrics.heightPixels

        val animationData = mainMenuViewModel?.animationData
        animationData?.doTick()

        val maxQueue = 3
        if ((animationData?.queue?.canDequeue() == true) &&
            (animationData.currentPoolSize < maxQueue)) {

            val animationQueue = animationData.queue

            var index = 0
            var aTemp: AAnimatedModel? = null
            try {
                index = animationQueue.dequeue()
                aTemp = animationData.getFromAllPool(index)
                animationData.addToCurrentPool(aTemp)
            } catch (e: ArrayIndexOutOfBoundsException) {
                animationQueue.enqueue(index)
                e.printStackTrace()
            }

            aTemp ?: return

            val lastAnimInList = animationData.lastFromCurrentPool
            if (lastAnimInList != null) {
                when(lastAnimInList) {
                    is AnimatedOrbModel -> {
                        if (BitmapUtils.bitmapExists(bitmapOrb)) {
                            animationData.setToAllPool(
                                index, AnimatedOrbModel(
                                    screenW, screenH
                                )
                            )
                        }
                    }
                    is AnimatedHandModel -> {
                        if (BitmapUtils.bitmapExists(bitmapHand)) {
                            var bitmapW = 0
                            var bitmapH = 0
                            try {
                                bitmapW = bitmapHand?.width ?: 0
                                bitmapH = bitmapHand?.height ?: 0
                            } catch (e: NullPointerException) {
                                e.printStackTrace()
                            }

                            animationData.setToAllPool(
                                index, AnimatedHandModel(
                                    screenW, screenH,
                                    bitmapW, bitmapH
                                )
                            )

                            bitmapHandRot =
                                (animationData.lastFromCurrentPool as AnimatedHandModel?)!!.rotateBitmap(
                                    bitmapHand!!
                                )
                        }
                    }
                    is AnimatedWritingModel -> {
                        if (BitmapUtils.bitmapExists(bitmapWriting)) {
                            var bitmapW = 0
                            var bitmapH = 0
                            try {
                                bitmapW = bitmapWriting?.width ?: 0
                                bitmapH = bitmapWriting?.height ?: 0
                            } catch (e: NullPointerException) {
                                e.printStackTrace()
                            }

                            animationData.setToAllPool(
                                index, AnimatedWritingModel(
                                    screenW, screenH,
                                    bitmapW, bitmapH,
                                    animationData
                                )
                            )

                            bitmapWritingRot =
                                (animationData.lastFromCurrentPool as AnimatedWritingModel?)!!.rotateBitmap(
                                    bitmapWriting!!
                                )
                        }
                    }
                    is AnimatedFrostModel -> {
                        if (BitmapUtils.bitmapExists(bitmapFrost)) {
                            animationData.setToAllPool(
                                index, AnimatedFrostModel(
                                    screenW, screenH
                                )
                            )
                        }
                    }
                    is AnimatedMirrorModel -> {
                        if (BitmapUtils.bitmapExists(bitmapMirror)) {
                            animationData.setToAllPool(
                                index, AnimatedMirrorModel(
                                    screenW, screenH
                                )
                            )
                        }
                    }
                }
            }
        }

        var i = 0
        while (i < (animationData?.currentPoolSize ?: 0)) {

            val currentAnim = animationData?.getFromCurrentPool(i)
            currentAnim?.doTick()

            /*
             * If the chosen AnimatedGraphic is not alive, then remove it from the list
             * If removed, replace it with a modified item of the same type
             * Then try the next AnimatedGraphic
             */
            if ((currentAnim?.isAlive) == false) {
                when(currentAnim) {
                    is AnimatedHandModel -> {
                        animationData.selectedHand = (Math.random() * handResIds.size).toInt()

                        bitmapHand = null
                        bitmapHandRot = null

                        bitmapUtils?.setResource(handResIds[animationData.selectedHand])
                        bitmapHand = bitmapUtils?.compileBitmaps(context)

                        if (BitmapUtils.bitmapExists(bitmapHand)) {
                            bitmapHandRot = currentAnim.rotateBitmap(bitmapHand) }
                    }
                    is AnimatedWritingModel -> {
                        animationData.selectedWriting =
                            (Math.random() * writingResIds.size).toInt()

                        bitmapWriting = null
                        bitmapWritingRot = null

                        bitmapUtils?.setResource(
                            writingResIds[animationData.selectedWriting]
                        )
                        bitmapWriting = bitmapUtils?.compileBitmaps(context)

                        if (BitmapUtils.bitmapExists(bitmapWriting)) {
                            bitmapWritingRot = currentAnim.rotateBitmap(bitmapWriting)
                        }
                    }
                }

                try {
                    animationData.removeFromCurrentPool(currentAnim)
                    i--
                } catch (e: IndexOutOfBoundsException) {
                    e.printStackTrace()
                }

                System.gc()
            }
            i++
        }
    }

    /**
     * @param canvas The cavas
     */
    override fun onDraw(canvas: Canvas) {

        super.onDraw(canvas)

        paint.style = Paint.Style.FILL

        try {
            mainMenuViewModel?.animationData?.currentPool?.forEach { a ->
                paint.setColorFilter(a.filter)
                try {
                    when(a) {
                        is AnimatedMirrorModel -> a.draw(canvas, paint, bitmapMirror)
                        is AnimatedWritingModel -> a.draw(canvas, paint, bitmapWritingRot)
                        is AnimatedHandModel -> a.draw(canvas, paint, bitmapHandRot)
                        is AnimatedOrbModel -> a.draw(canvas, paint, bitmapOrb)
                        is AnimatedFrostModel -> a.draw(canvas, paint, bitmapFrost)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } catch (ex: ConcurrentModificationException) {
            ex.printStackTrace()
        }

    }

    /**
     *
     */
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


    fun startAnimInitThreads(
        titleScreenViewModel: MainMenuViewModel?, bitmapUtils: BitmapUtils
    ) {
        if (threadInitAnim == null) {
            threadInitAnim = object : Thread() {
                override fun run() {
                    init(titleScreenViewModel, bitmapUtils)
                }
            }
            threadInitAnim?.start()
        }

        if (threadInitReady == null) {
            threadInitReady = Thread {
                while (!canAnimate) {
                    try {
                        Thread.sleep(100)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                startAnimThreads()
            }

            threadInitReady!!.start()
        }
    }

    private fun startAnimTickThread() {
        if (threadTickAnim == null) {
            canAnimate = true
            threadTickAnim = object : Thread() {
                override fun run() {
                    while (canAnimate) {
                        update()
                        tick()
                    }
                }

                private fun tick() {
                    val now = System.nanoTime()
                    val updateTime = System.nanoTime() - now
                    var fpsTarget = 30.0
                    val fpsMax = 60.0

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        try {
                            fpsTarget = context.display!!.refreshRate.toDouble()
                            if (fpsTarget > fpsMax) {
                                fpsTarget = fpsMax
                            }
                        } catch (e: IllegalStateException) {
                            e.printStackTrace()
                        }
                    }
                    //TARGET_FPS = 200;
                    val OPTIMAL_TIME = (1000000000 / fpsTarget /*300.0*/).toLong()
                    var wait = ((OPTIMAL_TIME - updateTime) / 1000000.0).toLong()

                    if (wait < 0) {
                        wait = 1
                    }

                    try {
                        sleep(wait)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                private fun update() {
                    try {
                        this@StartScreenAnimationView.tick()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            threadTickAnim?.start()
        }
    }

    private fun startAnimDrawThread() {
        if (threadRenderAnim != null) {
            return
        }

        threadRenderAnim = object : Thread() {
            override fun run() {
                while (canAnimate) {
                    invalidate()
                    try {
                        tick()
                    } catch (e: IllegalStateException) {
                        e.printStackTrace()
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }

            private fun tick() {
                val now = System.nanoTime()
                val updateTime = System.nanoTime() - now
                var fpsTarget = 24.0
                val fpsMax = 60.0

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R &&
                    (context.display != null)
                ) {
                    fpsTarget = context.display!!.refreshRate.toDouble()
                    if (fpsTarget > fpsMax) {
                        fpsTarget = fpsMax
                    }
                }

                val OPTIMAL_TIME = (1000000000 / fpsTarget).toLong()
                var wait = ((OPTIMAL_TIME - updateTime) / 1000000.0).toLong()

                if (wait < 0) {
                    wait = 1
                }

                try {
                    sleep(wait)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            private fun invalidate() {
                try {
                    (context as PETActivity).runOnUiThread { this@StartScreenAnimationView.invalidate() }
                } catch (e: IllegalStateException) {
                    e.printStackTrace()
                }
            }
        }
        threadRenderAnim?.start()
    }

    fun startAnimThreads() {
        startAnimTickThread()
        startAnimDrawThread()
    }

    fun stopAnimInitThreads() {
        if (threadInitAnim != null) {
            threadInitAnim!!.interrupt()
            threadInitAnim = null
        }
        if (threadInitReady != null) {
            threadInitReady!!.interrupt()
            threadInitReady = null
        }
    }

    fun stopAnimTickThread() {
        if (threadTickAnim != null) {
            threadTickAnim!!.interrupt()
            threadTickAnim = null
        }
    }

    fun stopAnimDrawThread() {
        if (threadRenderAnim != null) {
            threadRenderAnim!!.interrupt()
            threadRenderAnim = null
        }
    }

    fun stopAnimThreads() {
        stopAnimDrawThread()
        stopAnimTickThread()
    }

    fun canAnimateBackground(canAnimateBackground: Boolean) {
        this.canAnimate = canAnimateBackground
    }
}
