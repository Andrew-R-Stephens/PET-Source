package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.startscreen.model

/**
 * AdnimationQueue class
 *
 * @author TritiumGamingStudios
 */
class AnimatedQueueModel(private val maxSize: Int, private val maxTimeout: Int) {
    private var tick: Int
    private var timeout = 0

    private val queue = ArrayList<Int>()

    init {
        setTimeout(this.maxTimeout)
        tick = (timeout * .75).toInt()
    }

    fun doTick() {
        tick++
    }

    fun canDequeue(): Boolean {
        val canDequeue = (tick >= timeout)
        if (canDequeue) {
            tick = 0
            setTimeout(this.maxTimeout)
        }
        return canDequeue
    }

    private fun setTimeout(timeout: Int) {
        this.timeout = ((Math.random() * timeout) + (timeout * .5)).toInt()
    }

    private fun refill() {
        for (i in 0 until maxSize) {
            enqueueRandomPos(i)
        }
    }

    fun enqueue(num: Int) {
        if (queue.size < maxSize) {
            if (num >= 0) {
                try {
                    queue.add(num)
                } catch (e: ArrayIndexOutOfBoundsException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun enqueue(index: Int, num: Int) {
        if (queue.size < maxSize) {
            queue.add(index, num)
        }
    }

    private fun enqueueRandomPos(num: Int) {
        enqueue((Math.random() * queue.size).toInt(), num)
    }

    fun dequeue(): Int {
        if (queue.isEmpty()) {
            refill()
        }
        return queue.removeAt(0)
    }

    override fun toString(): String {
        val s = StringBuilder("Queue List (" + queue.size + ") [")
        for (i in queue.indices) {
            s.append(queue[i]).append(" ")
        }
        return "$s]"
    }
}
