package com.somelogs.algorithm;

/**
 * 二叉排序树（Binary Sort Tree）
 *
 * @author LBG - 2019/1/14
 */
public class BinarySortTree<K extends Comparable<K>, V> {

    private Node root;  // 二叉查找树的根结点

    private class Node {
        private K key;      // 键
        private V value;    // 值
        private Node left;  // 左子树
        private Node right; // 右子树
        private int count;  // 以该节点为根的子树中的结点总数

        private Node(K key, V value, int count) {
            this.key = key;
            this.value = value;
            this.count = count;
        }
    }

    public int size() {
        return size(root);
    }

    private int size(Node node) {
        return node != null ? node.count : 0;
    }

    public V get(K key) {
        return get(root, key);
    }

    private V get(Node node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return get(node.left, key);
        } else if (cmp > 0) {
            return get(node.right, key);
        }
        return node.value;
    }

    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private Node put(Node node, K key, V value) {
        if (node == null) {
            return new Node(key, value, 1);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else if (cmp > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
        }
        node.count = size(node.left) + size(node.right) + 1;
        return node;
    }
}
