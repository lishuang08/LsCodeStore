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
        var  size = arr .size/2



        return arr.sorted().subList(0, k).toIntArray()
    }
//    fun getLeastNumbers(arr: IntArray, k: Int): IntArray {
//
//        return arr.sorted().subList(0, k).toIntArray()
//    }
}