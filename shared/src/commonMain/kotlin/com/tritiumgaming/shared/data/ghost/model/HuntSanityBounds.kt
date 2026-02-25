package com.tritiumgaming.shared.data.ghost.model

data class SanityBounds(
    val normal: Int? = UNSET,
    val suppressed: Int? = UNSET,
    val empowered: Int? = UNSET
) {
    companion object {
        const val STANDARD: Int = 50
        val UNSET = null
    }
}
