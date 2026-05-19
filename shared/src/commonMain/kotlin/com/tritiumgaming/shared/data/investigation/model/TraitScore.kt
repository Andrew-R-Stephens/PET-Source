package com.tritiumgaming.shared.data.investigation.model

data class TraitScore(
    val confirm: Int = 0,
    val probableConfirm: Int = 0,
    val reject: Int = 0,
    val probableReject: Int = 0,
)
