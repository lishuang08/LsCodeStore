package ls.yylx.lscodestore.algorithms

import java.util.*

object Solution {
    class ListNode(var `val`: Int) {
        var next: ListNode? = null
    }

    fun swapPairs(head: ListNode?): ListNode? {

        val node2 = head?.next ?: return head

        val node3 = node2.next

        node2.next = head
        if (node3 != null) {
            head.next = swapPairs(node3)
        } else {
            head.next = null
        }
        return node2
    }


    fun reverseKGroup(head: ListNode?, k: Int): ListNode? {
        var n = k
        var nodeC: ListNode? = head
        while (n-- > 0) {
            if (nodeC?.next == null && n != 0) return head
            nodeC = nodeC?.next
        }

        var nodeP: ListNode? = null
        nodeC = head
        var nodeN: ListNode? = null

        n = k

        while (nodeC != null && n-- > 0) {
            nodeN = nodeC.next
            nodeC.next = nodeP
            nodeP = nodeC
            nodeC = nodeN
        }


        head?.next = reverseKGroup(nodeC, k)
        return nodeP

    }


    fun isValid(s: String): Boolean {
        if (s.isEmpty()) return true
        if (s.length == 1) return false
        class CharNode(val str: Char?) {
            var nextNode: CharNode? = null
        }

        var oldHeader: CharNode? = CharNode(null)

        s.forEach {
            if (it == ')') {
                if (oldHeader?.str == '(') {
                    oldHeader = oldHeader?.nextNode
                } else {
                    return false
                }
            } else if (it == ']') {
                if (oldHeader?.str == '[') {
                    oldHeader = oldHeader?.nextNode
                } else {
                    return false
                }
            } else if (it == '}') {
                if (oldHeader?.str == '{') {
                    oldHeader = oldHeader?.nextNode
                } else {
                    return false
                }
            } else {
                val newHead = CharNode(it)
                newHead.nextNode = oldHeader
                oldHeader = newHead
            }
        }

        return oldHeader?.str == null
    }


//    fun dailyTemperatures(T: IntArray): IntArray {
//        val size = T.size
//        if (size == 1) {
//            T[0] = 0
//            return T
//        }
//
//        T.forEachIndexed { index , tmp  ->
//            println("index:$tmp   tmp:$tmp")
//            if (tmp == 100 || (index == size - 1)) {
//                T[index] = 0
//
//            } else {
//                val n = (index + 1 until size).indexOfFirst { n ->
//                    tmp < T[n]
//                }
//                if (n == -1) {
//                    T[index] = 0
//                } else {
//                    T[index] = n + 1
//                }
//            }
//        }
//        return T
//    }

    fun dailyTemperatures(T: IntArray): IntArray {


        val size = T.size
        if (size == 1) {
            T[0] = 0
            return T
        }

        class CharNode(val n: Int, val tmp: Int) {
            var nextNode: CharNode? = null
        }

        var oldHeader: CharNode? = null


        T.forEachIndexed { i, tmp ->
            while (oldHeader != null && oldHeader!!.tmp < tmp) {
                T[oldHeader!!.n] = i - oldHeader!!.n
                oldHeader = oldHeader?.nextNode
            }
            val newHead = CharNode(i, tmp)
            newHead.nextNode = oldHeader
            oldHeader = newHead
        }
        while (oldHeader != null) {
            T[oldHeader!!.n] = 0
            oldHeader = oldHeader?.nextNode
        }


        return T
    }


//    fun maxSlidingWindow(nums: IntArray, k: Int): IntArray {
//
//        if (k <= 1) {
//            return nums
//        }
//        val size = nums.size
//        if (size == k) {
//            return intArrayOf(nums.max()!!)
//        }
//
//        class IntNode(val num: Int, val lastDrop: Int) {
//            var nextNode: IntNode? = null
//        }
//
//        var oldHeader: IntNode? = null
//
//
//        val count = size - k + 1
//
//        var arr = arrayListOf<Int>()
//        (0 until count).forEachIndexed { index, num ->
//            var max: Int? = null
//            (index until index + k).forEach {
//                if (max == null || max!! < nums[it]) {
//                    max = nums[it]
//                }
//            }
//            arr.add(max!!)
//        }
//
//
//
//        return arr.toIntArray()
//    }



    var arrDeque: ArrayDeque<Int> = ArrayDeque()
    var mainNums: IntArray = intArrayOf()

    fun clearDeque(index: Int, k: Int) {

        if (arrDeque.isNotEmpty() && arrDeque.first == index - k) {
            arrDeque.removeFirst()
        }
        while (arrDeque.isNotEmpty() && mainNums[arrDeque.last] < mainNums[index]) {
            arrDeque.removeLast()
        }

    }

    fun maxSlidingWindow(nums: IntArray, k: Int): IntArray {
        if (k < 2) return nums
        val size = nums.size
        if (size == k) return intArrayOf(nums.max()!!)
        val count = size - k + 1
        mainNums = nums
        val backArray = IntArray(count)

        nums.forEachIndexed { index, i ->

            clearDeque(index, k)
            arrDeque.addLast(index)

            if (index < k) {
                if (index == k - 1) backArray[0] = mainNums[arrDeque.first]
            } else {
                backArray[index - k + 1] = mainNums[arrDeque.first]
            }
        }


        return backArray
    }

}