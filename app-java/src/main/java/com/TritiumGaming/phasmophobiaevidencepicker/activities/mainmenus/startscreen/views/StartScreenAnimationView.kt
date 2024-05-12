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
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.AnimatedGraphic
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.AnimatedGraphicQueue
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.graphicsdata.AnimatedFrostData
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.graphicsdata.AnimatedHandData
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.graphicsdata.AnimatedMirrorData
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.graphicsdata.AnimatedOrbData
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.graphicsdata.AnimatedWritingData
import com.TritiumGaming.phasmophobiaevidencepicker.activities.pet.PETActivity
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.BitmapUtils
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MainMenuViewModel

/**
 * TitleScreenAnimationView class
 *
 * @author TritiumGamingStudios
 */
class StartScreenAnimationView : View {
    private var mainMenuViewModel: MainMenuViewModel? = null

    private var bitmapUtils: BitmapUtils? = null

    private var thread_initAnima: Thread? = null
    private var thread_tickAnim: Thread? = null
    private var thread_renderAnim: Thread? = null
    private var thread_initReady: Thread? = null

    private var canAnimate = true

    private var writingResIds = ArrayList<Int>()
    private var handResIds = ArrayList<Int>()

    private val paint = Paint()
    private var bitmap_orb: Bitmap? = null
    private var bitmap_hand: Bitmap? = null
    private var bitmap_writing: Bitmap? = null
    private var bitmap_frost: Bitmap? = null
    private var bitmap_mirror: Bitmap? = null
    private var bitmap_handRot: Bitmap? = null
    private var bitmap_writingRot: Bitmap? = null

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(
        context: Context?, attrs: AttributeSet?, defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

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

        val data =
            this.mainMenuViewModel!!.animationData

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

        bitmap_orb = bitmapUtils!!.setResource(R.drawable.anim_ghostorb).compileBitmaps(context)

        bitmap_frost = bitmapUtils!!.setResource(R.drawable.anim_frost).compileBitmaps(context)

        bitmap_hand = bitmapUtils!!.setResource(
            handResIds[data.selectedHand]
        ).compileBitmaps(context)
        bitmap_writing = bitmapUtils!!.setResource(
            writingResIds[data.selectedWriting]
        ).compileBitmaps(context)

        bitmap_mirror = bitmapUtils!!.setResource(R.drawable.anim_mirror_crack)
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
            g?.initDims(screenW, screenH)
        }

        if (animationData.hasData()) {
            for (animated in animationData.allPool) {
                if (animated is AnimatedHandData) {
                    try {
                        bitmap_handRot = animated.rotateBitmap(bitmap_hand)
                    } catch (e: IllegalStateException) {
                        e.printStackTrace()
                    }
                } else if (animated is AnimatedWritingData) {
                    try {
                        bitmap_writingRot = animated.rotateBitmap(bitmap_writing)
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
            if (BitmapUtils.bitmapExists(bitmap_orb)) {
                val data = AnimatedOrbData(screenW, screenH)
                animationData.addToAllPool(data)
            }
        }
        //Add hands
        for (i in 0 until HAND_COUNT) {
            if (BitmapUtils.bitmapExists(bitmap_hand)) {
                val bW = bitmap_hand!!.width
                val bH = bitmap_hand!!.height
                val data = AnimatedHandData(
                    screenW, screenH, bW, bH
                )
                animationData.addToAllPool(data)
                try {
                    bitmap_handRot = data.rotateBitmap(bitmap_hand!!)
                } catch (e: IllegalStateException) {
                    e.printStackTrace()
                }
            }
        }

        //Add writing
        for (i in 0 until WRITING_COUNT) {
            if (BitmapUtils.bitmapExists(bitmap_writing)) {
                val bW = bitmap_writing!!.width
                val bH = bitmap_writing!!.height
                val data = AnimatedWritingData(
                    screenW, screenH, bW, bH, animationData
                )
                animationData.addToAllPool(data)
                try {
                    bitmap_writingRot = data.rotateBitmap(bitmap_writing!!)
                } catch (e: IllegalStateException) {
                    e.printStackTrace()
                }
            }
        }

        //Add Frost
        for (i in 0 until FROST_COUNT) {
            if (BitmapUtils.bitmapExists(bitmap_frost)) {
                val data = AnimatedFrostData(screenW, screenH)
                animationData.addToAllPool(data)
            }
        }

        //Add Mirror
        for (i in 0 until MIRROR_COUNT) {
            if (BitmapUtils.bitmapExists(bitmap_mirror)) {
                val data = AnimatedMirrorData(screenW, screenH)
                animationData.addToAllPool(data)
            }
        }

        //Create Queue
        animationData.queue = AnimatedGraphicQueue(animationData.allPoolSize, 750)
    }

    fun tick() {
        if (mainMenuViewModel == null) return

        val screenW = Resources.getSystem().displayMetrics.widthPixels
        val screenH = Resources.getSystem().displayMetrics.heightPixels

        val animationData = mainMenuViewModel!!.animationData
        animationData.tick()

        val maxQueue = 3
        if ((animationData.hasQueue() && animationData.queue.canDequeue()) &&
            animationData.currentPoolSize < maxQueue
        ) {
            val animationQueue = animationData.queue

            var index = 0
            var aTemp: AnimatedGraphic? = null
            try {
                index = animationQueue.dequeue()
                aTemp = animationData.getFromAllPool(index)
                animationData.addToCurrentPool(aTemp)
            } catch (e: IndexOutOfBoundsException) {
                animationQueue.enqueue(index)
                e.printStackTrace()
            }
            if (aTemp != null) {
                val lastAnimInList = animationData.lastFromCurrentPool
                if (lastAnimInList != null) {
                    when(lastAnimInList) {
                        is AnimatedOrbData -> {
                            if (BitmapUtils.bitmapExists(bitmap_orb)) {
                                animationData.setToAllPool(
                                    index, AnimatedOrbData(
                                        screenW,
                                        screenH
                                    )
                                )
                            }
                        }
                        is AnimatedHandData -> {
                            if (BitmapUtils.bitmapExists(bitmap_hand)) {
                                var bitmapW = 0
                                var bitmapH = 0
                                try {
                                    bitmapW = bitmap_hand!!.width
                                    bitmapH = bitmap_hand!!.height
                                } catch (e: NullPointerException) {
                                    e.printStackTrace()
                                }

                                animationData.setToAllPool(
                                    index, AnimatedHandData(
                                        screenW,
                                        screenH,
                                        bitmapW,
                                        bitmapH
                                    )
                                )

                                bitmap_handRot =
                                    (animationData.lastFromCurrentPool as AnimatedHandData?)!!.rotateBitmap(
                                        bitmap_hand!!
                                    )
                            }
                        }
                        is AnimatedWritingData -> {
                            if (BitmapUtils.bitmapExists(bitmap_writing)) {
                                var bitmapW = 0
                                var bitmapH = 0
                                try {
                                    bitmapW = bitmap_writing!!.width
                                    bitmapH = bitmap_writing!!.height
                                } catch (e: NullPointerException) {
                                    e.printStackTrace()
                                }

                                animationData.setToAllPool(
                                    index, AnimatedWritingData(
                                        screenW,
                                        screenH,
                                        bitmapW,
                                        bitmapH,
                                        animationData
                                    )
                                )

                                bitmap_writingRot =
                                    (animationData.lastFromCurrentPool as AnimatedWritingData?)!!.rotateBitmap(
                                        bitmap_writing!!
                                    )
                            }
                        }
                        is AnimatedFrostData -> {
                            if (BitmapUtils.bitmapExists(bitmap_frost)) {
                                animationData.setToAllPool(
                                    index, AnimatedFrostData(
                                        screenW,
                                        screenH
                                    )
                                )
                            }
                        }
                        is AnimatedMirrorData -> {
                            if (BitmapUtils.bitmapExists(bitmap_mirror)) {
                                animationData.setToAllPool(
                                    index, AnimatedMirrorData(
                                        screenW,
                                        screenH
                                    )
                                )
                            }
                        }
                    }
                    /*
                    if (lastAnimInList is AnimatedOrbData) {
                        if (BitmapUtils.bitmapExists(bitmap_orb)) {
                            animationData.setToAllPool(
                                index, AnimatedOrbData(
                                    screenW,
                                    screenH
                                )
                            )
                        }
                    } else if (lastAnimInList is AnimatedHandData) {
                        if (BitmapUtils.bitmapExists(bitmap_hand)) {
                            var bitmapW = 0
                            var bitmapH = 0
                            try {
                                bitmapW = bitmap_hand!!.width
                                bitmapH = bitmap_hand!!.height
                            } catch (e: NullPointerException) {
                                e.printStackTrace()
                            }

                            animationData.setToAllPool(
                                index, AnimatedHandData(
                                    screenW,
                                    screenH,
                                    bitmapW,
                                    bitmapH
                                )
                            )

                            bitmap_handRot =
                                (animationData.lastFromCurrentPool as AnimatedHandData?)!!.rotateBitmap(
                                    bitmap_hand!!
                                )
                        }
                    } else if (lastAnimInList is AnimatedWritingData) {
                        if (BitmapUtils.bitmapExists(bitmap_writing)) {
                            var bitmapW = 0
                            var bitmapH = 0
                            try {
                                bitmapW = bitmap_writing!!.width
                                bitmapH = bitmap_writing!!.height
                            } catch (e: NullPointerException) {
                                e.printStackTrace()
                            }

                            animationData.setToAllPool(
                                index, AnimatedWritingData(
                                    screenW,
                                    screenH,
                                    bitmapW,
                                    bitmapH,
                                    animationData
                                )
                            )

                            bitmap_writingRot =
                                (animationData.lastFromCurrentPool as AnimatedWritingData?)!!.rotateBitmap(
                                    bitmap_writing!!
                                )
                        }
                    } else if (lastAnimInList is AnimatedFrostData) {
                        if (BitmapUtils.bitmapExists(bitmap_frost)) {
                            animationData.setToAllPool(
                                index, AnimatedFrostData(
                                    screenW,
                                    screenH
                                )
                            )
                        }
                    } else if (lastAnimInList is AnimatedMirrorData) {
                        if (BitmapUtils.bitmapExists(bitmap_mirror)) {
                            animationData.setToAllPool(
                                index, AnimatedMirrorData(
                                    screenW,
                                    screenH
                                )
                            )
                        }
                    }*/
                }
            }
        }

        var i = 0
        while (i < animationData.currentPoolSize) {
            val currentAnim = animationData.getFromCurrentPool(i)
            if (currentAnim != null) {
                currentAnim.tick()

                /*
                 * If the chosen Animated is not alive
                 * remove it from the list
                 * Replace it with a modified item of the same type
                 * Try the next Animated
                 */
                if (!currentAnim.isAlive) {
                    when(currentAnim) {
                        is AnimatedHandData -> {
                            animationData.selectedHand = (Math.random() * handResIds.size).toInt()

                            BitmapUtils.destroyBitmap(
                                bitmap_hand!!
                            )
                            BitmapUtils.destroyBitmap(
                                bitmap_handRot!!
                            )

                            bitmapUtils!!.setResource(
                                handResIds[animationData.selectedHand]
                            )
                            bitmap_hand = bitmapUtils!!.compileBitmaps(context)

                            if (BitmapUtils.bitmapExists(bitmap_hand)) {
                                bitmap_handRot = currentAnim.rotateBitmap(bitmap_hand)
                            }
                        }
                        is AnimatedWritingData -> {
                            animationData.selectedWriting = (Math.random() * writingResIds.size).toInt()

                            BitmapUtils.destroyBitmap(
                                bitmap_writing!!
                            )
                            BitmapUtils.destroyBitmap(
                                bitmap_writingRot!!
                            )

                            bitmapUtils!!.setResource(
                                writingResIds[animationData.selectedWriting]
                            )
                            bitmap_writing = bitmapUtils!!.compileBitmaps(context)

                            if (BitmapUtils.bitmapExists(bitmap_writing)) {
                                bitmap_writingRot = currentAnim.rotateBitmap(bitmap_writing)
                            }
                        }
                    }
                    /*if (currentAnim is AnimatedHandData) {
                        animationData.selectedHand = (Math.random() * handResIds.size).toInt()

                        BitmapUtils.destroyBitmap(
                            bitmap_hand!!
                        )
                        BitmapUtils.destroyBitmap(
                            bitmap_handRot!!
                        )

                        bitmapUtils!!.setResource(
                            handResIds[animationData.selectedHand]
                        )
                        bitmap_hand = bitmapUtils!!.compileBitmaps(context)

                        if (BitmapUtils.bitmapExists(bitmap_hand)) {
                            bitmap_handRot = currentAnim.rotateBitmap(bitmap_hand)
                        }

                    } else if (currentAnim is AnimatedWritingData) {
                        animationData.selectedWriting = (Math.random() * writingResIds.size).toInt()

                        BitmapUtils.destroyBitmap(
                            bitmap_writing!!
                        )
                        BitmapUtils.destroyBitmap(
                            bitmap_writingRot!!
                        )

                        bitmapUtils!!.setResource(
                            writingResIds[animationData.selectedWriting]
                        )
                        bitmap_writing = bitmapUtils!!.compileBitmaps(context)

                        if (BitmapUtils.bitmapExists(bitmap_writing)) {
                            bitmap_writingRot = currentAnim.rotateBitmap(bitmap_writing)
                        }
                    }*/
                    try {
                        animationData.removeFromCurrentPool(currentAnim)
                        i--
                    } catch (e: IndexOutOfBoundsException) {
                        e.printStackTrace()
                    }

                    System.gc()
                }
            }
            i++
        }
    }

    /**
     * @param canvas The cavas
     */
    override fun onDraw(canvas: Canvas) {
        if (mainMenuViewModel == null) {
            return
        }

        super.onDraw(canvas)

        paint.style = Paint.Style.FILL

        try {
            for (a in mainMenuViewModel!!.animationData.currentPool) {
                paint.setColorFilter(a.filter)
                try {
                    when(a) {
                        is AnimatedMirrorData -> a.draw(canvas, paint, bitmap_mirror)
                        is AnimatedWritingData -> a.draw(canvas, paint, bitmap_writingRot)
                        is AnimatedHandData -> a.draw(canvas, paint, bitmap_handRot)
                        is AnimatedOrbData -> a.draw(canvas, paint, bitmap_orb)
                        is AnimatedFrostData -> a.draw(canvas, paint, bitmap_frost)
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
        BitmapUtils.destroyBitmap(
            bitmap_orb!!
        )
        BitmapUtils.destroyBitmap(
            bitmap_frost!!
        )
        BitmapUtils.destroyBitmap(
            bitmap_hand!!
        )
        BitmapUtils.destroyBitmap(
            bitmap_writing!!
        )
        BitmapUtils.destroyBitmap(
            bitmap_mirror!!
        )
        BitmapUtils.destroyBitmap(
            bitmap_handRot!!
        )
        BitmapUtils.destroyBitmap(
            bitmap_writingRot!!
        )

        bitmap_orb = null
        bitmap_frost = null
        bitmap_hand = null
        bitmap_writing = null
        bitmap_mirror = null
        bitmap_handRot = null
        bitmap_writingRot = null

        System.gc()
    }


    fun startAnimInitThreads(
        titleScreenViewModel: MainMenuViewModel?, bitmapUtils: BitmapUtils?
    ) {
        if (thread_initAnima == null) {
            thread_initAnima = object : Thread() {
                override fun run() {
                    init(titleScreenViewModel, bitmapUtils)
                }
            }
            thread_initAnima?.start()
        }

        if (thread_initReady == null) {
            thread_initReady = Thread {
                while (!canAnimate) {
                    try {
                        Thread.sleep(100)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
                startAnimThreads()
            }

            thread_initReady!!.start()
        }
    }

    private fun startAnimTickThread() {
        if (thread_tickAnim == null) {
            canAnimate = true
            thread_tickAnim = object : Thread() {
                override fun run() {
                    while (canAnimate) {
                        update()
                        try {
                            tick()
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }
                }

                @Throws(InterruptedException::class)
                private fun tick() {
                    val now = System.nanoTime()
                    val updateTime = System.nanoTime() - now
                    var TARGET_FPS = 30.0
                    val MAX_FPS = 60.0

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        try {
                            TARGET_FPS = context.display!!.refreshRate.toDouble()
                            if (TARGET_FPS > MAX_FPS) {
                                TARGET_FPS = MAX_FPS
                            }
                        } catch (e: IllegalStateException) {
                            e.printStackTrace()
                        }
                    }
                    //TARGET_FPS = 200;
                    val OPTIMAL_TIME = (1000000000 / /*TARGET_FPS*/ 300.0).toLong()
                    var wait = ((OPTIMAL_TIME - updateTime) / 1000000.0).toLong()

                    if (wait < 0) {
                        wait = 1
                    }

                    sleep(wait)
                }

                private fun update() {
                    try {
                        this@StartScreenAnimationView.tick()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            thread_tickAnim?.start()
        }
    }

    private fun startAnimDrawThread() {
        if (thread_renderAnim != null) {
            return
        }

        thread_renderAnim = object : Thread() {
            override fun run() {
                while (canAnimate) {
                    invalidate()
                    try {
                        tick()
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }

            @Throws(InterruptedException::class)
            private fun tick() {
                val now = System.nanoTime()
                val updateTime = System.nanoTime() - now
                var TARGET_FPS = 24.0
                val MAX_FPS = 60.0

                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R &&
                        (context.display != null)
                    ) {
                        TARGET_FPS = context.display!!.refreshRate.toDouble()
                        if (TARGET_FPS > MAX_FPS) {
                            TARGET_FPS = MAX_FPS
                        }
                    }

                    val OPTIMAL_TIME = (1000000000 / TARGET_FPS).toLong()
                    var wait = ((OPTIMAL_TIME - updateTime) / 1000000.0).toLong()

                    if (wait < 0) {
                        wait = 1
                    }

                    sleep(wait)
                } catch (e: IllegalStateException) {
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
        thread_renderAnim?.start()
    }

    fun startAnimThreads() {
        startAnimTickThread()
        startAnimDrawThread()
    }

    fun stopAnimInitThreads() {
        if (thread_initAnima != null) {
            thread_initAnima!!.interrupt()
            thread_initAnima = null
        }
        if (thread_initReady != null) {
            thread_initReady!!.interrupt()
            thread_initReady = null
        }
    }

    fun stopAnimTickThread() {
        if (thread_tickAnim != null) {
            thread_tickAnim!!.interrupt()
            thread_tickAnim = null
        }
    }

    fun stopAnimDrawThread() {
        if (thread_renderAnim != null) {
            thread_renderAnim!!.interrupt()
            thread_renderAnim = null
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
