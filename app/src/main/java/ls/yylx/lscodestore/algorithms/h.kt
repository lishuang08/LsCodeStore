package ls.yylx.lscodestore.algorithms

import java.util.*

fun breakfastNumber(staple: IntArray, drinks: IntArray, x: Int): Int {
    staple.sort()
    drinks.sort()

    if (staple[0] > x || drinks[0] > x || (staple[0] + drinks[0] > x)) return 0
    var result = 0

    (0..staple.size).forEach { i ->
        val less = x - i
        if (less <= 0) return result
        ((drinks.size - 1).downTo(0)).forEach { j ->
            if (drinks[j] <= less) {
                result += j
                return@forEach
            }
        }
    }




    return result
}



fun sumOfLeftLeaves(root: TreeNode?): Int {
    if (root == null) return 0
    var sum = 0
    if (root.left == null) {
        sum = 0
    } else {
        if (root.left!!.left == null && root.left!!.right == null) {
            sum = root.left!!.`val`
        }else {
            sum += sumOfLeftLeaves(root.left)
        }
    }
    sum += sumOfLeftLeaves(root.right)


    return sum
}


var result = ArrayList<String>()

fun binaryTreePaths(root: TreeNode?): List<String>? {
    if (root != null) backString(root, root.`val`.toString() + "")
    return result
}

fun backString(root:  TreeNode, nowStr: String) {
    var nowStr = nowStr
    if (root.left == null && root.right == null) {
        result.add(nowStr)
        return
    }
    nowStr += "->"
    if (root.left != null) {
        backString(root.left!!, nowStr + root.left!!.`val`)
    }
    if (root.right != null) {
        backString(root.right!!, nowStr + root.right!!.`val`)
    }
}

