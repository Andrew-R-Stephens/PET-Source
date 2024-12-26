package com.tritiumgaming.phasmophobiaevidencepicker.data.model.startscreen

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.startscreen.graphicsdata.AnimatedFrostModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.startscreen.graphicsdata.AnimatedHandModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.startscreen.graphicsdata.AnimatedMirrorModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.startscreen.graphicsdata.AnimatedOrbModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.startscreen.graphicsdata.AnimatedWritingModel
import com.tritiumgaming.phasmophobiaevidencepicker.utils.BitmapUtils

class AnimationModel {
    
    companion object {
        const val ORB_COUNT: Short = 3
        const val HAND_COUNT: Short = 1
        const val WRITING_COUNT: Short = 1
        const val MIRROR_COUNT: Short = 1
        const val FROST_COUNT: Short = 1
    }

    private var writingResIds = ArrayList<Int>()
    private var handResIds = ArrayList<Int>()

    private var bitmapOrb: Bitmap? = null
    private var bitmapHand: Bitmap? = null
    private var bitmapWriting: Bitmap? = null
    private var bitmapFrost: Bitmap? = null
    private var bitmapMirror: Bitmap? = null
    private var bitmapHandRot: Bitmap? = null
    private var bitmapWritingRot: Bitmap? = null
    
    private val bitmapUtils = BitmapUtils()
    
    private var queue: AnimatedQueueModel = AnimatedQueueModel(10, 500)
    private val allPool: ArrayList<AAnimatedModel> = ArrayList()
    private val currentPool: ArrayList<AAnimatedModel> = ArrayList()

    private var selectedWriting: Int = -1
    private var selectedHand: Int = -1

    var rotWriting: Float = 0f
    var rotHand: Float = 0f

    private val allPoolSize: Int
        get() = allPool.size

    private val currentPoolSize: Int
        get() = currentPool.size

    @get:Throws(IndexOutOfBoundsException::class)
    val lastFromCurrentPool: AAnimatedModel?
        get() {
            if (currentPool.isEmpty()) { return null }

            return currentPool[currentPool.size - 1]
        }

    fun hasData(): Boolean {
        return allPool.isNotEmpty()
    }
    
    fun initImages(context: Context) {
        try { bitmapOrb = bitmapUtils.setResource(
            R.drawable.anim_ghostorb).compileBitmaps(context)
        } catch (e: Exception) { e.printStackTrace() }

        try { bitmapFrost = bitmapUtils.setResource(
            R.drawable.anim_frost).compileBitmaps(context)
        } catch (e: Exception) { e.printStackTrace() }

        try { bitmapHand = bitmapUtils.setResource(
            handResIds[selectedHand]).compileBitmaps(context)
        } catch (e: Exception) { e.printStackTrace() }

        try { bitmapWriting = bitmapUtils.setResource(
            writingResIds[selectedWriting]).compileBitmaps(context)
        } catch (e: Exception) { e.printStackTrace() }

        try { bitmapMirror = bitmapUtils.setResource(R.drawable.anim_mirror_crack)
            .addResource(R.drawable.anim_mirror_gradient, PorterDuff.Mode.MULTIPLY)
            .addResource(R.drawable.anim_mirror_crack, PorterDuff.Mode.MULTIPLY)
            .compileBitmaps(context)
        } catch (e: Exception) { e.printStackTrace() }
    }

    fun createQueue() {
        val screenW = Resources.getSystem().displayMetrics.widthPixels
        val screenH = Resources.getSystem().displayMetrics.heightPixels

        for (g in currentPool) { g.initDims(screenW, screenH) }

        if (allPool.isNotEmpty()) {
            for (animated in allPool) {
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
                allPool.add(AnimatedOrbModel(screenW, screenH))
            }
        }
        //Add hands
        for (i in 0 until HAND_COUNT) {
            if (BitmapUtils.bitmapExists(bitmapHand)) {
                bitmapHand?.let { bitmapHand ->
                    val bW = bitmapHand.width
                    val bH = bitmapHand.height
                    val data = AnimatedHandModel(screenW, screenH, bW, bH)
                    allPool.add(data)
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
                    val data = AnimatedWritingModel(screenW, screenH, bW, bH, this)
                    allPool.add(data)
                    try { bitmapWritingRot = data.rotateBitmap(bitmapWriting) }
                    catch (e: IllegalStateException) { e.printStackTrace() }
                }
            }
        }

        //Add Frost
        for (i in 0 until FROST_COUNT) {
            if (BitmapUtils.bitmapExists(bitmapFrost)) {
                allPool.add(AnimatedFrostModel(screenW, screenH))
            }
        }

        //Add Mirror
        for (i in 0 until MIRROR_COUNT) {
            if (BitmapUtils.bitmapExists(bitmapMirror)) {
                allPool.add(AnimatedMirrorModel(screenW, screenH))
            }
        }

        //Create Queue
        queue = AnimatedQueueModel(allPoolSize, 750)

    }

    fun tick(context: Context) {
        val screenW = Resources.getSystem().displayMetrics.widthPixels
        val screenH = Resources.getSystem().displayMetrics.heightPixels

        queue.doTick()

        val maxQueue = 3
        if (queue.canDequeue() && (currentPoolSize < maxQueue)) {

            val animationQueue = queue

            var index = 0
            var aTemp: AAnimatedModel? = null
            try {
                index = animationQueue.dequeue()
                aTemp = allPool[index]
                currentPool.add(aTemp)
            } catch (e: ArrayIndexOutOfBoundsException) {
                animationQueue.enqueue(index)
                e.printStackTrace()
            }
            aTemp ?: return

            val lastAnimInList = lastFromCurrentPool
            lastAnimInList?.let {
                when(lastAnimInList) {
                    is AnimatedOrbModel -> {
                        if (BitmapUtils.bitmapExists(bitmapOrb)) {
                            allPool[index] = AnimatedOrbModel(screenW, screenH)
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

                                allPool[index] = AnimatedHandModel(
                                    screenW, screenH, bitmapW, bitmapH)

                                bitmapHandRot =
                                    (lastFromCurrentPool as AnimatedHandModel)
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

                                allPool[index] = AnimatedWritingModel(
                                    screenW, screenH, bitmapW, bitmapH, this)

                                bitmapWritingRot =
                                    (lastFromCurrentPool as AnimatedWritingModel)
                                        .rotateBitmap(bitmapWriting)
                            }

                        }
                    }
                    is AnimatedFrostModel -> {
                        if (BitmapUtils.bitmapExists(bitmapFrost)) {
                            allPool[index] = AnimatedFrostModel(screenW, screenH)
                        }
                    }
                    is AnimatedMirrorModel -> {
                        if (BitmapUtils.bitmapExists(bitmapMirror)) {
                            allPool[index] = AnimatedMirrorModel(screenW, screenH)
                        }
                    }
                }
            }
        }

        var i = 0
        while (i < (currentPoolSize)) {

            val currentAnim = currentPool[i]
            currentAnim.doTick()

            /*
             * If the chosen AnimatedGraphic is not alive, then remove it from the list
             * If removed, replace it with a modified item of the same type
             * Then try the next AnimatedGraphic
             */
            if (!currentAnim.isAlive) {
                when(currentAnim) {
                    is AnimatedHandModel -> {
                        selectedHand = (Math.random() * handResIds.size).toInt()

                        bitmapHand = null
                        bitmapHandRot = null

                        bitmapUtils.setResource(handResIds[selectedHand])
                        bitmapHand = bitmapUtils.compileBitmaps(context)

                        if (BitmapUtils.bitmapExists(bitmapHand)) {
                            bitmapHandRot = currentAnim.rotateBitmap(bitmapHand) }
                    }
                    is AnimatedWritingModel -> {
                        selectedWriting =
                            (Math.random() * writingResIds.size).toInt()

                        bitmapWriting = null
                        bitmapWritingRot = null

                        bitmapUtils.setResource(
                            writingResIds[selectedWriting]
                        )
                        bitmapWriting = bitmapUtils.compileBitmaps(context)

                        if (BitmapUtils.bitmapExists(bitmapWriting)) {
                            bitmapWritingRot = currentAnim.rotateBitmap(bitmapWriting)
                        }
                    }
                }

                try {
                    currentPool.remove(currentAnim)
                    i--
                } catch (e: IndexOutOfBoundsException) { e.printStackTrace() }

                System.gc()
            }
            i++
        }
    }

    fun init(context: Context, resources: Resources = context.resources) {
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

        if (selectedWriting == -1) {
            selectedWriting = (Math.random() * writingResIds.size).toInt() }
        if (selectedHand == -1) {
            selectedHand = (Math.random() * handResIds.size).toInt() }

        initImages(context)
        createQueue()
    }

    fun draw(canvas: Canvas, paint: Paint) {
        paint.style = Paint.Style.FILL

        try { currentPool.forEach { a ->
            a.filter?.let { filter -> paint.setColorFilter(filter) }
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

}