package ls.yylx.lscodestore.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

class j {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    int count = 0;

    boolean is_valid_part(TreeNode node, int val) {
        if (node == null) return true;

        if (!is_valid_part(node.left, node.val) | !is_valid_part(node.right, node.val))
            return false;
        count++;
        return node.val == val;
    }

    public int countUnivalSubtrees(TreeNode root) {
        is_valid_part(root, 0);
        return count;
    }


    ArrayList<String> result = new ArrayList<String>();

    public List<String> binaryTreePaths(TreeNode root) {
        if (root != null) backString(root, root.val + "");
        return result;
    }

    public void backString(TreeNode root, String nowStr) {
        if (root.left == null && root.right == null) {
            result.add(nowStr);
            return;
        }
        nowStr += "->";
        if (root.left != null) {
            backString(root.left, nowStr + root.left.val);
        }
        if (root.right != null) {
            backString(root.right, nowStr + root.right.val);
        }
    }


    int nowState = 0;
    private final Object object = new Object();


    public void first(Runnable printFirst) throws InterruptedException {
        synchronized (object) {
            while (nowState != 0) {
                object.wait();
            }
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            nowState = 1;
            object.notifyAll();
        }
    }

    public void second(Runnable printSecond) throws InterruptedException {
        synchronized (object) {
            while (nowState != 1) {
                object.wait();
            }
            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            nowState = 2;
            object.notifyAll();
        }
    }

    public void third(Runnable printThird) throws InterruptedException {
        synchronized (object) {
            while (nowState != 2) {
                object.wait();
            }
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
            nowState = 0;
            object.notifyAll();
        }
    }


    List<Integer> t = new ArrayList<Integer>();
    List<List<Integer>> resultSub = new ArrayList<>();

    public List<List<Integer>> subsets(int[] nums) {
        dfs(0, nums);
        return resultSub;
    }

    public void dfs(int s, int[] nums) {
        if (s == nums.length) {
            resultSub.add(new ArrayList<>(t));
            return;
        }
        t.add(nums[s]);
        dfs(s + 1, nums);
        t.remove(t.size() - 1);
        dfs(s + 1, nums);
    }


}

