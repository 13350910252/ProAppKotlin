package com.yinp.proappkotlin.recreation.game.tanchishe

/**
 * @author   :yinpeng
 * date      :2021/10/18
 * package   :com.yinp.proappkotlin.recreation.game.tanchishe
 * describe  :
 */
class ALinkList {
    var first: Node? = null
    var last: Node? = null
    var size = 0

    fun insertFirst(x: Int, y: Int) {
        size++
        val node = Node(x, y)
        if (first == null) {
            first = node
            last = first
            return
        }
        node.next = first
        first!!.previous = node
        first = node
    }

    fun insertLast(x: Int, y: Int) {
        size++
        val node = Node(x, y)
        if (first == null) {
            first = node
            last = first
            return
        }
        node.previous = last
        last!!.next = node
        last = node
    }

    fun isFirstRepetition(): Boolean {
        var node: Node? = first?.next
        while (node != null) {
            if (node.x == first?.x && node.y == first?.y) {
                return true
            }
            node = node.next
        }
        return false
    }

    fun removeLast() {
        if (last == null) {
            return
        }
        if (first?.equals(last) == true) {
            first = null
            last = null
            return
        }
        last = last!!.previous
        last?.next = null
        size--
    }

    fun getAll(): FloatArray? {
        if (first == null) return null
        var curNode: Node? = first
        val value = FloatArray(size * 2)
        var i = 0
        while (curNode != null) {
            value[i] = curNode.x.toFloat()
            value[i + 1] = curNode.y.toFloat()
            i += 2
            curNode = curNode.next
        }
        return value
    }

    fun contains(x: Int, y: Int): Boolean {
        var node: Node? = first
        while (node != null) {
            if (node.x == x && node.y == y) {
                return true
            }
            node = node.next
        }
        return false
    }

    fun clearAll() {
        first = null
        last = null
        size = 0
    }

    fun traverse() {
        var curNode: Node? = first
        while (curNode != null) {
            print(curNode.x.toString() + "=" + curNode.y + "++")
            curNode = curNode.next
        }
        println()
    }
}