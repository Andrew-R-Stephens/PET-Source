package com.tritiumgaming.phasmophobiaevidencepicker.listeners

import android.view.View

class CompositeListener : View.OnClickListener {
    private val registeredListeners = ArrayList<View.OnClickListener>()

    fun registerListener(listener: View.OnClickListener) {
        registeredListeners.add(listener)
    }

    override fun onClick(view: View) {
        for (listener in registeredListeners) {
            listener.onClick(view)
        }
    }
}