package ls.yylx.lscodestore.algorithms;

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

        if (!is_valid_part(node.left, node.val) | !is_valid_part(node.right, node.val)) return false;
        count++;
        return node.val == val;
    }

    public int countUnivalSubtrees(TreeNode root) {
        is_valid_part(root, 0);
        return count;
    }

}
