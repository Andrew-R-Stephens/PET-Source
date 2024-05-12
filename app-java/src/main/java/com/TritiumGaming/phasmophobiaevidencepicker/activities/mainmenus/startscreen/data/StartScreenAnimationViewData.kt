package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data

import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.AnimatedGraphic
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.AnimatedGraphicQueue

/**
 * AnimationData class
 *
 * @author TritiumGamingStudios
 */
class StartScreenAnimationViewData {
    var queue: AnimatedGraphicQueue = AnimatedGraphicQueue(10, 500)

    val allPool: ArrayList<AnimatedGraphic> = ArrayList()
    val currentPool: ArrayList<AnimatedGraphic> = ArrayList()

    var selectedWriting: Int = -1
    var selectedHand: Int = -1

    var rotWriting: Float = 0f
    var rotHand: Float = 0f

    val lastFromAllPool: AnimatedGraphic
        get() = allPool[allPool.size - 1]

    val allPoolSize: Int
        get() = allPool.size

    val currentPoolSize: Int
        get() = currentPool.size

    @get:Throws(IndexOutOfBoundsException::class)
    val lastFromCurrentPool: AnimatedGraphic?
        get() {
            if (currentPool.isEmpty()) { return null }

            return currentPool[currentPool.size - 1]
        }

    fun addToAllPool(animatedItem: AnimatedGraphic) {
        allPool.add(animatedItem)
    }

    fun setToAllPool(pos: Int, animated: AnimatedGraphic) {
        allPool[pos] = animated
    }

    fun getFromAllPool(i: Int): AnimatedGraphic {
        return allPool[i]
    }

    fun addToCurrentPool(animatedItem: AnimatedGraphic) {
        currentPool.add(animatedItem)
    }

    fun getFromCurrentPool(i: Int): AnimatedGraphic {
        return currentPool[i]
    }

    fun removeFromCurrentPool(animated: AnimatedGraphic) {
        currentPool.remove(animated)
    }

    fun hasQueue(): Boolean {
        return true
    }

    fun doTick() {
        queue.doTick()
    }

    fun hasData(): Boolean {
        return allPool.isNotEmpty()
    }
}
