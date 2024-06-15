package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.startscreen

/**
 * AnimationData class
 *
 * @author TritiumGamingStudios
 */
class AnimationModel {
    var queue: AnimatedQueueModel = AnimatedQueueModel(10, 500)

    val allPool: ArrayList<AAnimatedModel> = ArrayList()
    val currentPool: ArrayList<AAnimatedModel> = ArrayList()

    var selectedWriting: Int = -1
    var selectedHand: Int = -1

    var rotWriting: Float = 0f
    var rotHand: Float = 0f

    val lastFromAllPool: AAnimatedModel
        get() = allPool[allPool.size - 1]

    val allPoolSize: Int
        get() = allPool.size

    val currentPoolSize: Int
        get() = currentPool.size

    @get:Throws(IndexOutOfBoundsException::class)
    val lastFromCurrentPool: AAnimatedModel?
        get() {
            if (currentPool.isEmpty()) { return null }

            return currentPool[currentPool.size - 1]
        }

    fun addToAllPool(animatedItem: AAnimatedModel) {
        allPool.add(animatedItem)
    }

    fun setToAllPool(pos: Int, animated: AAnimatedModel) {
        allPool[pos] = animated
    }

    fun getFromAllPool(i: Int): AAnimatedModel {
        return allPool[i]
    }

    fun addToCurrentPool(animatedItem: AAnimatedModel) {
        try {
            currentPool.add(animatedItem)
        } catch (ex: ArrayIndexOutOfBoundsException) {
            ex.printStackTrace()
        }
    }

    fun getFromCurrentPool(i: Int): AAnimatedModel {
        return currentPool[i]
    }

    fun removeFromCurrentPool(animated: AAnimatedModel) {
        try {
            currentPool.remove(animated)
        } catch (ex: ArrayIndexOutOfBoundsException) {
            ex.printStackTrace()
        }
    }

    fun doTick() {
        queue.doTick()
    }

    fun hasData(): Boolean {
        return allPool.isNotEmpty()
    }
}
