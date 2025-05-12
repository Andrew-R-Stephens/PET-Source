package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype

import androidx.annotation.StringRes

abstract class JournalItemModel(
    var id: Int = 0,
    @StringRes var name: Int = 0,
)