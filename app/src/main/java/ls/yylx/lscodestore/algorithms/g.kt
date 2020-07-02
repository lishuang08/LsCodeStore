package ls.yylx.lscodestore.algorithms


class g {
//    fun alienOrder(words: Array<String>): String {
//        var list = LinkedList<Char>()
//
//        words.forEach {
//            it.toCharArray().forEach {
//                val had = hashMap[it] ?: -1
//                if (had != -1) {
//
//                }
//            }
//        }
//
//    }


    fun getLeastNumbers(arr: IntArray, k: Int): IntArray {
        var size = arr.size / 2



        return arr.sorted().subList(0, k).toIntArray()
    }
//    fun getLeastNumbers(arr: IntArray, k: Int): IntArray {
//
//        return arr.sorted().subList(0, k).toIntArray()
//    }


    fun findLength(A: IntArray, B: IntArray): Int {
        val m = A.size
        val n = B.size
        var intArray = Array(m + 1) { IntArray(n + 1) }
        var max = 0

        ((m - 1) downTo 0).forEach { i ->
            ((n - 1) downTo 0).forEach { j ->
                intArray[i][j] = if (A[i] == B[j]) (intArray[i + 1][j + 1] + 1) else 0
                max = maxOf(max, intArray[i][j])
            }
        }

        return max

    }


    fun findBestValue(arr: IntArray, target: Int): Int {
        if (arr.isEmpty()) return 0
        if (target == 0) return 0

        val n = arr.size

        val m = target / n

        var totalless = 0
        var count = 0
        arr.filter { it <= m }.forEach {
            totalless += it
            count++
        }
        if (totalless == 0 ) {
            return if (target % m <= ((m + 1) * n - target)) m else {
                m + 1
            }
        } else {
            val s = (n - count)
            val tLess = target - totalless
            if (s ==0 ){
            }
            val d = tLess / s

            return if (tLess % d <= (d + 1) * s - tLess) d else {
                d + 1
            }
        }
    }

}