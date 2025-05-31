package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.source

import com.google.firebase.firestore.DocumentReference

interface UserDataSource {

    fun buildUserDocument(): DocumentReference
    
}