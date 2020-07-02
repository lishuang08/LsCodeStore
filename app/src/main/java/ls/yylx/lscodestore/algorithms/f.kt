package ls.yylx.lscodestore.algorithms

class f {

//    fun numDecodings(s: String): Int {
//        if (s[0] == '0') return 0
//        val c = s.toCharArray()
//
//        return count(c, c.size - 1)
//    }

//    fun count(c: CharArray, index: Int): Int {
//        if (index <= 0) return 1
//        var b = 0
//        var cur = c[index]
//        var pre = c[index - 1]
//
//        if (cur != '*' && cur > '0') {
//            b = count(c, index - 1)
//        }else {
//           if (cur !="")
//        }
//
//
//
//    }


    fun isRectangleOverlap(rec1: IntArray, rec2: IntArray): Boolean {
        return !(rec1[0] >= rec2[2] ||
                rec1[3] <= rec2[1] ||
                rec1[2] <= rec2[0] ||
                rec1[1] >= rec2[3])
    }


    fun xorQueries(arr: IntArray, queries: Array<IntArray>): IntArray {
        val back = IntArray(queries.size)

        queries.forEachIndexed { index, ints ->
            (ints[0]..ints[1]).forEach {
                if (back[index] == 0) {
                    back[index] = arr[it]
                } else {
                    back[index] = back[index].xor(arr[it])
                }
            }
        }
        return back
    }

    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
        var node = ListNode(0)
        var head = node
        var nodel1 = l1
        var nodel2 = l2

        var sign = 0
        while (nodel1 != null || nodel2 != null || sign == 1) {
            node.next = ListNode(0)
            var add = sign + (nodel1?.`val` ?: 0) + (nodel2?.`val` ?: 0)
            sign = add / 10
            node.next?.`val` = add % 10
            nodel1 = nodel1?.next
            nodel2 = nodel2?.next
            node = node.next!!
        }

        return head.next
    }


    fun longestPalindrome(s: String): Int {
        var hashMap = HashMap<Char, Int>()
        s.toCharArray().forEach {
            val count = hashMap[it] ?: 0
            hashMap[it] = count + 1
        }
        var total = 0
        var single = 0
        hashMap.forEach {
            if (it.value % 2 == 0) {
                total += it.value
            } else {
                total += it.value - 1
                single = 1
            }
        }


        return (total + single)

    }


//    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
//        val total = nums1.size + nums2.size
//
//        var p1 = nums1.size / 2
//        var p2 = nums2.size / 2
//        if (total % 2 == 0) {
//            val left = total / 2
//            if (nums1.isEmpty()) return (nums2[left - 1] + nums2[left]) / 2.0
//            if (nums2.isEmpty()) return (nums1[left - 1] + nums1[left]) / 2.0
//
//            while ((p1 + p2) != left+1) {
//
//            }
//
//        } else {
//
//        }
//
//
//    }


}