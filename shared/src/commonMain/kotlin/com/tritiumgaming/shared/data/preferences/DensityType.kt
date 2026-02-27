package com.tritiumgaming.shared.data.preferences

enum class DensityType(val value: Int) {
    COMFORTABLE(0), // Default component sizes
    COMPACT(1), // Shrunken components, less white space
    RELAXED(2) // Extra whitespace and larger real-estate for each component
}