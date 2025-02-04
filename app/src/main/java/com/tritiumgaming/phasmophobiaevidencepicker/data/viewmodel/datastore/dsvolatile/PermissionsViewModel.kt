package com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.datastore.dsvolatile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PermissionsViewModel: ViewModel() {

    var isRecordAudioAllowed: Boolean = false

    class PermissionsFactory(

    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PermissionsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PermissionsViewModel(

                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}
