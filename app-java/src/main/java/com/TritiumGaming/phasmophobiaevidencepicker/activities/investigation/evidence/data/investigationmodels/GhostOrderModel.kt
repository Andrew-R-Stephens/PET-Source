package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.investigationmodels

import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel

class GhostOrderModel(
    private val evidenceViewModel: EvidenceViewModel
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
    fun createOrder() {
        createPrevOrder()
        createCurrOrder()
    }

    /**
     * createPrevOrder
     * Initializes both current and previous order of ghosts to default order
     */
    private fun createPrevOrder() {
        prevOrder = IntArray(InvestigationModel.ghostList.list.size)

        for (i in prevOrder!!.indices) {
            prevOrder!![i] = i
        }
    }

    /**
     * createCurrOrder
     * Initializes both current and previous order of ghosts to default order
     */
    private fun createCurrOrder() {
        currOrder = IntArray(InvestigationModel.ghostList.list.size)

        for (i in currOrder!!.indices) {
            currOrder!![i] = i
        }
    }

    fun updateOrder() {
        val newOrder = IntArray(InvestigationModel.ghostList.list.size)

        // Replace previous with current
        if (currOrder == null) {
            createCurrOrder()
        }
        if (prevOrder == null) {
            createPrevOrder()
        }
        System.arraycopy(currOrder, 0, prevOrder, 0, currOrder?.size ?: 0)

        // Set default numbers into placeholder array
        for (i in newOrder.indices) {
            newOrder[i] = i
        }

        // Order placeholder array based on scores
        var i = 0
        while (i < newOrder.size - 1) {
            val ghostList = InvestigationModel.ghostList

            val ratingA = ghostList.getAt(newOrder[i]).evidenceScore
            val ratingB = ghostList.getAt(newOrder[i + 1]).evidenceScore

            if (ratingA < ratingB) {
                val t = newOrder[i + 1]
                newOrder[i + 1] = newOrder[i]
                newOrder[i] = t

                if (i > 0) {
                    i--
                }
            } else {
                i++
            }
        }

        // Replace current with placeholders
        currOrder = newOrder
    }

    fun getPrevOrder(): IntArray? {
        if (prevOrder == null) {
            createPrevOrder()
        }

        return prevOrder
    }

    fun hasChanges(): Boolean {
        if (prevOrder == null) {
            createPrevOrder()
        }
        if (currOrder == null) {
            createCurrOrder()
        }

        for (i in currOrder!!.indices) {
            if (currOrder!![i] != prevOrder!![i]) return true
        }

        return false
    }
}