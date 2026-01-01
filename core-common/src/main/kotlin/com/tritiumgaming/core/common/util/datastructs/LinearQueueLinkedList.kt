package com.tritiumgaming.core.common.util.datastructs

class LinearQueueLinkedList<T> {
    var head: Node<T>? = null
    var tail: Node<T>? = null
    var size: Int = 0

    fun enqueue(data: T) {
        val newNode = Node(data)
        if (isEmpty()) {
            head = newNode
            tail = newNode
        } else {
            tail?.next = newNode
            tail = newNode
        }
        size++
    }

    fun dequeue(): T? {
        if (isEmpty()) {
            return null
        }
        val top = head?.data
        head = head?.next
        size--
        return top
    }

    fun peek(): T? {
        return head?.data
    }

    fun isEmpty(): Boolean {
        return head == null
    }

    fun size(): Int {
        return size
    }

    fun printQueue() {
        var current = head
        while (current != null) {
            print("${current.data} ")
            current = current.next
        }
        println()
    }
    fun clear(){
        head = null
        tail = null
        size = 0
    }
}