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

    @JvmField
    var rotWriting: Float = 0f
    var rotHand: Float = 0f

    fun addToAllPool(animatedItem: AnimatedGraphic) {
        allPool.add(animatedItem)
    }

    fun setToAllPool(pos: Int, animated: AnimatedGraphic) {
        allPool[pos] = animated
    }

    fun getFromAllPool(i: Int): AnimatedGraphic {
        return allPool[i]
    }

    val lastFromAllPool: AnimatedGraphic
        get() = allPool[allPool.size - 1]

    val allPoolSize: Int
        get() = allPool.size

    fun addToCurrentPool(animatedItem: AnimatedGraphic) {
        currentPool.add(animatedItem)
    }

    fun getFromCurrentPool(i: Int): AnimatedGraphic {
        return currentPool[i]
    }

    @get:Throws(IndexOutOfBoundsException::class)
    val lastFromCurrentPool: AnimatedGraphic?
        get() {
            if (currentPool.size == 0) {
                return null
            }
            val index = currentPool.size - 1

            return currentPool[index]
        }

    fun removeFromCurrentPool(
        animated: AnimatedGraphic
    ) {
        currentPool.remove(animated)
    }

    val currentPoolSize: Int
        get() = currentPool.size

    fun hasQueue(): Boolean {
        return true
    }

    fun tick() {
        queue.tick()
    }

    fun hasData(): Boolean {
        return allPool.isNotEmpty()
    }
}
