package com.tritiumgaming.shared.operation.domain.map.complex.model

data class ComplexWorldPoint(
    val x: Float,
    val y: Float
) {
    override fun toString(): String {
        return "[P1: $x P2: $y]"
    }
}