package com.tritiumgaming.core.common.util.datastructs

class CircularQueueLinkedList<T>(
    private val maxSize: Int
) {
    var head: Node<T>? = null
    var tail: Node<T>? = null
    var size: Int = 0
    private var count = 0

    fun enqueue(data: T) {
        if (isFull()) {
            dequeue()
        }
        val newNode = Node(data)
        if (isEmpty()) {
            head = newNode
            tail = newNode
        } else {
            tail?.next = newNode
            tail = newNode
        }
        size++
        count++
    }

    fun dequeue(): T? {
        if (isEmpty()) {
            return null
        }
        val top = head?.data
        head = head?.next
        size--
        count--
        return top
    }

    fun peek(): T? {
        return head?.data
    }

    fun isFull(): Boolean {
        return count == maxSize
    }

    fun isEmpty(): Boolean {
        return count == 0
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
        count = 0
    }

    fun asList(): List<T> {
        val list = mutableListOf<T>()
        var current = head
        while (current != null) {
            list.add(current.data)
            current = current.next
        }
        return list
    }
}