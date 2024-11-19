package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.sharedpreferences

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class PermissionsViewModel(application: Application): AndroidViewModel(application) {
    var isRecordAudioAllowed: Boolean = false
}
