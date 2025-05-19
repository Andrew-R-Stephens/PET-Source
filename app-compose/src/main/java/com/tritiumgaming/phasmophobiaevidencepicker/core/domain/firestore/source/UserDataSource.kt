package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.firestore.source

import com.google.firebase.firestore.DocumentReference

interface UserDataSource {

    fun buildUserDocument(): DocumentReference
    
}