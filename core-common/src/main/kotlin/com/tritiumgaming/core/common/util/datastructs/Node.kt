package com.tritiumgaming.core.common.util.datastructs

data class Node<T>(
    var data: T, var next: Node<T>? = null, var prev: Node<T>? = null
)
