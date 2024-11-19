package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels

import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.investigationtype.ghost.GhostRepository

class GhostOrderModel(
    private val ghostListModel: GhostRepository
) {

    private var prevOrder: IntArray? = null
    var currOrder: IntArray? = null
        get() {
            field ?: createCurrOrder()
            return field
        }

    /**
     * createOrder
     * Initializes both current and previous order of ghosts to default order
     */
    private fun createOrder() {
        createPrevOrder()
        createCurrOrder()
    }

    /**
     * createPrevOrder
     * Initializes both current and previous order of ghosts to default order
     */
    private fun createPrevOrder() {
        prevOrder = IntArray(GhostRepository.count)

        prevOrder?.let { prevOrder ->
            for (i in prevOrder.indices) {
                prevOrder[i] = i
            }
        }

    }

    /**
     * createCurrOrder
     * Initializes both current and previous order of ghosts to default order
     */
    private fun createCurrOrder() {
        currOrder = IntArray(GhostRepository.count)

        currOrder?.let { currOrder ->
            for (i in currOrder.indices) {
                currOrder[i] = i
            }
        }

    }

    fun updateOrder() {
        val newOrder = IntArray(GhostRepository.count)

        // Replace previous with current
        if (currOrder == null) {
            createCurrOrder()
        }
        if (prevOrder == null) {
            createPrevOrder()
        }
        //System.arraycopy(currOrder, 0, prevOrder, 0, currOrder?.size ?: 0)
        prevOrder = IntArray(currOrder?.size ?: 0)
        currOrder?.forEachIndexed { item, index ->
            prevOrder?.set(index, item)
        }

        // Set default numbers into placeholder array
        for (i in newOrder.indices) {
            newOrder[i] = i
        }

        // Order placeholder array based on scores
        var i = 0
        while (i < newOrder.size - 1) {
            val ratingA = ghostListModel.getAt(newOrder[i]).evidenceScore
            val ratingB = ghostListModel.getAt(newOrder[i + 1]).evidenceScore

            if (ratingA < ratingB) {
                val t = newOrder[i + 1]
                newOrder[i + 1] = newOrder[i]
                newOrder[i] = t

                if (i > 0) { i-- }
            } else { i++ }
        }

        // Replace current with placeholders
        currOrder = newOrder
    }

    fun hasChanges(): Boolean {
        if (prevOrder == null) {
            createPrevOrder()
        }
        if (currOrder == null) {
            createCurrOrder()
        }

        currOrder?.let { currOrder ->
            prevOrder?.let { prevOrder ->
                for (i in currOrder.indices) {
                    if (currOrder[i] != prevOrder[i]) return true
                }
            }
        }

        return false
    }

    fun reset() {
        createOrder()
    }
}
