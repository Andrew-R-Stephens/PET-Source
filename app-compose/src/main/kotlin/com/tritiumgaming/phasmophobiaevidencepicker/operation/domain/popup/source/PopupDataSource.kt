package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.source

interface PopupDataSource<T> {

    fun fetchPopups(): List<T>

}