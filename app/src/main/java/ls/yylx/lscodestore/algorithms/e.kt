package ls.yylx.lscodestore.algorithms

class e {
    fun numDecodings(s: String): Int {
        if (s.isEmpty() || s[0] == '0') return 0
        val a = s.toCharArray()
        var c = count(a, a.size - 1)

        return c
    }

    fun count(c: CharArray, i: Int): Int {
        if (i <= 0) return 1
        var b = 0

        val pre = c[i - 1]
        val cur = c[i]

        if (cur > '0') {
            b = count(c, i - 1)
        }



        if (pre == '1' || (pre == '2' && cur <= '6')) {
            b += count(c, i - 2)
        }

        return b
    }







}
