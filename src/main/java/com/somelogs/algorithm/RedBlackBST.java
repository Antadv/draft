package com.somelogs.algorithm;

/**
 * 红黑树：
 * 自平衡查找树中的2-3查找树，这种数据结构在插入之后能够进行自平衡操作，
 * 从而保证了树的高度在一定的范围内进而能够保证最坏情况下的时间复杂度。
 * 但是2-3查找树实现起来比较困难，红黑树是2-3树的一种简单高效的实现，
 * 他巧妙地使用颜色标记来替代2-3树中比较难处理的3-node节点问题
 *
 * 参考<a href = 'https://www.cnblogs.com/yangecnu/p/Introduce-Red-Black-Tree.html'></a>
 *
 * @author LBG - 2019/1/15
 */
public class RedBlackBST<K extends Comparable<K>, V> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;

    private class Node {
        private K key;          // 键
        private V value;        // 值
        private Node left;      // 左子树
        private Node right;     // 右子树
        private int count;      // 以该节点为根的子树中的结点总数
        private boolean color;  // 由父结点指向它的链接的颜色

        private Node(K key, V value, int count, boolean color) {
            this.key = key;
            this.value = value;
            this.count = count;
            this.color = color;
        }
    }

    private Node put(Node node, K key, V value) {
        if (node == null) {
            // 标准的插入操作，和父结点用红链接相连
            return new Node(key, value, 1, RED);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else if (cmp > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
        }

        /*
         * 平衡化操作:
         * 如果节点的右子节点为红色，且左子节点位黑色，则进行左旋操作
         * 如果节点的左子节点为红色，并且左子节点的左子节点也为红色，则进行右旋操作
         * 如果节点的左右子节点均为红色，则执行FlipColor操作，提升中间结点。
         */
        if (isRed(node.right) && !isRed(node.left)) node = rotateLeft(node);
        if (isRed(node.right) && isRed(node.left.left)) node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right)) flipColor(node);

        node.count = size(node.left) + size(node.right) + 1;
        return node;

    }

    private boolean isRed(Node node) {
        return node != null && node.color == RED;
    }

    private void flipColor(Node node) {
        node.left.color = BLACK;
        node.right.color = BLACK;
        node.color = RED;
    }

    /**
     * 左旋
     */
    private Node rotateLeft(Node node) {
        Node x = node.right;
        node.right = x.left;
        x.left = node;
        x.color = node.color;
        node.color = RED;
        return x;
    }

    /**
     * 右旋
     */
    private Node rotateRight(Node node) {
        Node x = node.left;
        node.left = x.right;
        x.right = node;
        x.color = node.color;
        node.color = RED;
        return x;
    }

    public int size() {
        return size(root);
    }

    private int size(Node node) {
        return node != null ? node.count : 0;
    }
}
