package ls.yylx.lscodestore.algorithms

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.max

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}


fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
    var header: ListNode? = null
    var list: ListNode? = ListNode(0)

    var list0 = l1
    var list1 = l2

    var added = 0
    while (list0 != null || list1 != null) {

        var total = (list0?.`val` ?: 0) + (list1?.`val` ?: 0) + added

        if (total >= 10) {
            list?.`val` = total % 10
            added = 1
        } else {
            list?.`val` = total
            added = 0
        }
        if (header == null) header = list

        list0 = list0?.next
        list1 = list1?.next
        if (list0 != null && list1 != null) {
            list = list?.next
        }
    }

    return header
}


//fun maxProfit(prices: IntArray): Int {
//    var max = 0
//    val linklist = LinkedList<Int>().apply {
//        add(Int.MAX_VALUE)
//        addLast(Int.MIN_VALUE)
//    }
//
//    prices.forEach {
//
//    }
//}

class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}


private var total = 0

fun diameterOfBinaryTree(root: TreeNode?): Int {
    total = 1
    countChild(root)
    return total - 1
}

fun countChild(root: TreeNode?): Int {
    if (root == null) {
        return 0
    }
    val l = countChild(root.left)
    val r = countChild(root.right)
    total = max(total, l + r + 1)
    return max(l, r) + 1
}


fun containsNearbyDuplicate(nums: IntArray, k: Int): Boolean {
    val hashMap = HashMap<Int, Int>()
    nums.forEachIndexed { index, i ->
        if (hashMap[i] != null) {
            val last = hashMap[i] ?: 0
            if (index - last > k) {
                hashMap[i] = index
            } else {
                return true
            }

        } else {
            hashMap[i] = index
        }
    }
    return false
}

fun containsNearbyAlmostDuplicate(nums: IntArray, k: Int, t: Int): Boolean {
    if (nums.size == 1 || t < 0 || k <= 0) return false
    val hashMap = HashMap<Int, Long >(k)

    nums.forEachIndexed { index, i ->
        (0..k).forEach {
            val num = hashMap[it]
            if (num != null) {
                if (Math.abs(num - i.toLong()) <= t.toLong()) {
                    return true
                }
            }
        }
        hashMap[index % k] = i.toLong()
    }
    return false
}