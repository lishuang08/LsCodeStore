package ls.yylx.lscodestore.algorithms


class tree {

//    class Trie() {
//        val map = hashMapOf<Trie, Boolean>()
//
//        fun insert(word: String) {
//            if (word.isEmpty()) return
//            if (map[word[0]] != null) {
//
//            }
//
//        }
//
//        fun search(word: String): Boolean {
//
//        }
//
//        fun startsWith(prefix: String): Boolean {
//
//        }
//
//    }


    fun distributeCandies(candies: Int, num_people: Int): IntArray {
        var base = 0
        (0 until num_people).forEach {
            base += it + 1
        }


        var back = IntArray(num_people)
        var candiesleft = candies
        if (base < candies) {
            var count = 0
            while (true) {

                val levelCount = base + count * num_people * num_people
                if (candiesleft > levelCount) {
                    count += 1
                    candiesleft -= levelCount
                } else {
                    break
                }
            }


            var totalCommon = 0
            (0 until count).forEach {
                totalCommon += it * num_people
            }

            (0 until num_people).forEach {
                back[it] = (it + 1) * count + totalCommon
                if (candiesleft > 0) {
                    val nextCandies = it + 1 + num_people * count
                    if (candiesleft > nextCandies) {
                        back[it] += nextCandies
                        candiesleft -= nextCandies
                    } else {
                        back[it] += candiesleft
                        candiesleft = 0
                    }
                }
            }
        } else if (base < candies) {
            (0 until num_people).forEach {
                back[it] = (it + 1)
            }
        } else {
            (0 until num_people).forEach {
                if (candiesleft > 0) {
                    if (candiesleft > it + 1) {
                        back[it] = it + 1
                        candiesleft -= it + 1
                    } else {
                        back[it] = candiesleft
                        candiesleft = 0

                        return@forEach
                    }
                }
            }
        }

        return back
    }

    fun topKFrequent(nums: IntArray, k: Int): List<Int> {
        val hashMap = hashMapOf<Int, Int>()
        nums.forEach {
            hashMap[it] = (hashMap[it] ?: 0) + 1
        }


        val list = hashMap.toList().sortedByDescending { it.second }.subList(0, k).map { it.first }

        return list
    }


    fun findContinuousSequence(target: Int): Array<IntArray> {
        if (target < 3) {
            return emptyArray()
        }
        var max = 0
        if (target % 2 == 1) {
            max = (target + 1) / 2
        } else {
            max = target / 2 - 1
        }

        val intCount = IntArray(max - 1)
        var count = 0
        (0 until max).forEach {
            count += it + 1
            if (it > 0) intCount[it - 1] = count
        }


        var arrayList = arrayListOf<IntArray>()

        (2..max).forEach {
            if (intCount[it - 2] > target) {
                return@forEach
            }
            if (target % 2 == 1 && target % it == 0 || (target % 2 == 0 && target % it == it / 2)) {
                val addCount = (target - intCount[it - 2]) / it
                val intArray = IntArray(it)
                (1..it).forEach { n ->
                    intArray[n - 1] = n + addCount
                }
                arrayList.add(intArray)
            }
        }

        val backArray = arrayList.toTypedArray().reversedArray()


        return backArray
    }
}