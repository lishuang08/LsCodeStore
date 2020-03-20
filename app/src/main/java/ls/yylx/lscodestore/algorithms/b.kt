package ls.yylx.lscodestore.algorithms

import java.util.*


//fun coinChange(coins: IntArray, amount: Int): Int {
//    val size = coins.size
//
//    val coinsCount = IntArray(size)
//    val coinsLeft = IntArray(size)
//
//
//    val linked = LinkedList<Pair<Int, Int>>()
//
//    coins.forEachIndexed { index, i ->
//        coinsCount[index] = amount / i
//        coinsLeft[index] = amount / i
//    }
//    if (coinsCount.size == 1) {
//        if (coinsLeft[0] != 0) {
//            return -1
//        } else {
//            return coinsCount[0]
//        }
//    }
//    if (coinsLeft[size - 1] == 0) {
//        return coinsCount[size - 1]
//    }
//
//    val max = coinsCount[0]
//    val min = coinsCount[size - 1]
//
//    (min + 1..max).forEach {
//        var count = 0
//        (it downTo 0).forEach {
//
//
//        }
//    }
//
//
//}
