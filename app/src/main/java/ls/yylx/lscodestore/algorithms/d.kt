package ls.yylx.lscodestore.algorithms

import java.util.*
import kotlin.collections.HashMap

class d {
    fun canThreePartsEqualSum(A: IntArray): Boolean {
        var total = 0L
        var hashMap = HashMap<Long, Int>()

        A.forEachIndexed { index, i ->
            total += i
            if (total == 0L) {
                val last = hashMap[total] ?: 0
                hashMap[total] = last + 1
            } else {
                hashMap[total] = index
            }
        }

        if (total % 3 == 0L) {
            if (total == 0L) {
                if (hashMap[0L]!! >= 3) return true
            }
            val average = total / 3

            val first = hashMap.get(average)
            val second = hashMap.get(average * 2)

            if (first != null && second != null && first < second) {
                return true
            } else {
                return false
            }
        } else {
            return false
        }

    }

    fun gcdOfStrings(str1: String, str2: String): String {
        if (str1 + str2 != str2 + str1) {
            return ""
        }

        return str1.substring(0, gcd(str1.length, str2.length))
    }

    fun gcd(l0: Int, l1: Int): Int {
        if (l1 == 0) {
            return l0
        } else {
            return gcd(l1, l0 % l1)
        }
    }


    class TreeNode(var `val`: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null
    }


    val arrlist = arrayListOf<Int>()
    fun rightSideView(root: TreeNode?): List<Int> {
        getFirst(root, 0)
        return arrlist
    }

    fun getFirst(root: TreeNode?, size: Int) {
        if (root == null) return
        if (size == arrlist.size) arrlist.add(root.`val`)
        getFirst(root.right, size + 1)
        getFirst(root.left, size + 1)
    }


    fun majorityElement(nums: IntArray): Int {
        var atLeast = nums.size / 2

        var hashMap = HashMap<Int, Int>()

        nums.forEach {
            var had = hashMap[it] ?: 0
            had += 1
            if (had > atLeast) return it
            hashMap[it] = had
        }
        return nums[0]
    }

//    fun lengthOfLIS(nums: IntArray?): Int {
//        val hashMap = HashMap<Int, Int>()
//
//        nums?.forEachIndexed { index, i ->
//
//
//        }
//
//    }


    fun compressString(S: String): String {
        var s = ""
        var k: Char? = null
        var c = 0

        S.forEach {
            if (k == it) {
                c += 1
            } else {
                if (k != null) {
                    s += "$k$c"
                }
                k = it
                c = 1
            }
        }
        s += "$k$c"


        return if (s.length < S.length) s else S
    }


}