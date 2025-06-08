package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.repository

interface PopupRepository<K, T> {
    val localSource: K

    fun getPopups(): Result<List<T>>
}