package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels;

import androidx.lifecycle.ViewModel;

public class PermissionsViewModel extends ViewModel {

    private boolean recordAudioAllowed = false;

    public boolean isRecordAudioAllowed() {
        return recordAudioAllowed;
    }

    public void setRecordAudioAllowed(boolean recordAudioAllowed) {
        this.recordAudioAllowed = recordAudioAllowed;
    }
}
