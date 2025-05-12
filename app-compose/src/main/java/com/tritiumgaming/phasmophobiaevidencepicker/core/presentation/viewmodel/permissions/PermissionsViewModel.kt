package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.permissions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class PermissionsViewModel: ViewModel() {

    var isRecordAudioAllowed: Boolean = false

    class PermissionsFactory : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PermissionsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PermissionsViewModel(

                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    val Factory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            PermissionsViewModel()
        }
    }

}