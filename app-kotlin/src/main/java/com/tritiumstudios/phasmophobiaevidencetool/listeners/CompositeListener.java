package com.tritiumstudios.phasmophobiaevidencetool.listeners;

import android.view.View;

import java.util.ArrayList;

public class CompositeListener implements View.OnClickListener {

    private final ArrayList<View.OnClickListener> registeredListeners = new ArrayList<>();

    public void registerListener (View.OnClickListener listener) {
        registeredListeners.add(listener);
    }

    @Override
    public void onClick(View view) {
        for(View.OnClickListener listener:registeredListeners) {
            listener.onClick(view);
        }
    }
}