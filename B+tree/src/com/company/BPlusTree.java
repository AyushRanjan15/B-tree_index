package com.company;

import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

public class BPlusTree<K extends Comparable<K>, T> {
    public Node<K, T> root;
    public static final int D = 200;
    public int pageId = 0;
    int depth = 0;

    public BPlusTree() {
    }

    public int pageIdAssigner() {
        ++this.pageId;
        return this.pageId;
    }

    public T search(K key) {
        LeafNode<K, T> leaf = this.searchHelper(this.root, key);
        return leaf.getValue(key);
    }

    private LeafNode<K, T> searchHelper(Node<K, T> root, K key) {
        if (root == null) {
            return null;
        } else if (root.isLeafNode) {
            return (LeafNode)root;
        } else {
            IndexNode<K, T> index = (IndexNode)root;
            if (key.compareTo(index.keys.get(0)) < 0) {
                return this.searchHelper((Node)index.children.get(0), key);
            } else if (key.compareTo(index.keys.get(index.keys.size() - 1)) >= 0) {
                return this.searchHelper((Node)index.children.get(index.children.size() - 1), key);
            } else {
                for(int i = 1; i < index.keys.size(); ++i) {
                    if (((Comparable)index.keys.get(i)).compareTo(key) > 0) {
                        return this.searchHelper((Node)index.children.get(i), key);
                    }
                }

                return null;
            }
        }
    }

    public ArrayList<String> searchRange_(LeafNode<K, T> leaf, K key, K range) {
        ArrayList returnArray;
        for(returnArray = new ArrayList(); ((Comparable)leaf.keys.get(0)).compareTo(range) < 0; leaf = leaf.nextLeaf) {
            for(int i = 0; i < leaf.keys.size(); ++i) {
                if (((Comparable)leaf.keys.get(i)).compareTo(key) >= 0 & ((Comparable)leaf.keys.get(i)).compareTo(range) <= 0) {
                    returnArray.add(leaf.values.get(i).toString());
                }
            }
        }

        return returnArray;
    }

    public ArrayList<String> searchRange(K key, K range) {
        new ArrayList();
        LeafNode<K, T> leaf = this.searchHelperRange(this.root, key);
        ArrayList<String> returnArray = this.searchRange_(leaf, key, range);
        return returnArray;
    }

    public String searchRangeClustered(K key) {
        LeafNode<K, T> leaf = this.searchHelperRange(this.root, key);
        return leaf.values.get(0).toString();
    }

    private LeafNode<K, T> searchHelperRange(Node<K, T> root, K key) {
        if (root == null) {
            return null;
        } else if (root.isLeafNode) {
            return (LeafNode)root;
        } else {
            IndexNode<K, T> index = (IndexNode)root;
            if (key.compareTo(index.keys.get(0)) < 0) {
                return this.searchHelper((Node)index.children.get(0), key);
            } else if (key.compareTo(index.keys.get(index.keys.size() - 1)) >= 0) {
                return this.searchHelper((Node)index.children.get(index.children.size() - 1), key);
            } else {
                for(int i = 1; i < index.keys.size(); ++i) {
                    if (((Comparable)index.keys.get(i)).compareTo(key) > 0) {
                        return this.searchHelper((Node)index.children.get(i), key);
                    }
                }

                return null;
            }
        }
    }

    public void insert(K key, T value) {
        if (this.root == null) {
            this.root = new LeafNode(key, value);
        }

        Entry<K, Node<K, T>> overflow = this.insertHelper(this.root, key, value);
        if (overflow != null) {
            this.root = new IndexNode((Comparable)overflow.getKey(), this.root, (Node)overflow.getValue(), this.pageIdAssigner());
        }

    }

    private Entry<K, Node<K, T>> insertHelper(Node<K, T> root, K key, T value) {
        Entry<K, Node<K, T>> overflow = null;
        IndexNode index;
        if (root.isLeafNode) {
            ((LeafNode)root).insertSorted(key, value);
            if (((LeafNode)root).isOverflowed()) {
                return this.splitLeafNode((LeafNode)root);
            }
        } else {
            index = (IndexNode)root;
            if (key.compareTo((K) index.keys.get(0)) < 0) {
                overflow = this.insertHelper((Node)index.children.get(0), key, value);
            } else if (key.compareTo((K) index.keys.get(index.keys.size() - 1)) >= 0) {
                overflow = this.insertHelper((Node)index.children.get(index.children.size() - 1), key, value);
            } else {
                for(int i = 1; i < index.keys.size(); ++i) {
                    if (((Comparable)index.keys.get(i)).compareTo(key) > 0) {
                        overflow = this.insertHelper((Node)index.children.get(i), key, value);
                        break;
                    }
                }
            }
        }

        if (overflow != null && root instanceof IndexNode) {
            index = (IndexNode)root;
            K ofkey = (K) overflow.getKey();
            if (ofkey.compareTo((K) index.keys.get(0)) < 0) {
                index.insertSorted(overflow, 0);
            } else if (ofkey.compareTo((K) index.keys.get(index.keys.size() - 1)) > 0) {
                index.insertSorted(overflow, index.keys.size());
            } else {
                for(int i = 1; i < index.keys.size(); ++i) {
                    if (((Comparable)index.keys.get(i)).compareTo(ofkey) > 0) {
                        index.insertSorted(overflow, i);
                        break;
                    }
                }
            }

            return index.isOverflowed() ? this.splitIndexNode(index) : null;
        } else {
            return overflow;
        }
    }

    public Entry<K, Node<K, T>> splitLeafNode(LeafNode<K, T> leaf) {
        K splitKey = (K) leaf.keys.get(201);
        ArrayList<K> rightKeys = new ArrayList();
        ArrayList<T> rightValues = new ArrayList();
        rightKeys.addAll(leaf.keys.subList(201, leaf.keys.size()));
        rightValues.addAll(leaf.values.subList(201, leaf.values.size()));
        LeafNode<K, T> right = new LeafNode(rightKeys, rightValues);
        leaf.keys.subList(201, leaf.keys.size()).clear();
        leaf.values.subList(201, leaf.values.size()).clear();
        if (leaf.nextLeaf != null) {
            leaf.nextLeaf.previousLeaf = right;
            right.nextLeaf = leaf.nextLeaf;
        }

        right.previousLeaf = leaf;
        leaf.nextLeaf = right;
        return new SimpleEntry(splitKey, right);
    }

    public Entry<K, Node<K, T>> splitIndexNode(IndexNode<K, T> index) {
        K splitKey = (K) index.keys.get(200);
        ArrayList<K> rightKeys = new ArrayList();
        ArrayList<Node<K, T>> rightChildren = new ArrayList();
        rightKeys.addAll(index.keys.subList(201, index.keys.size()));
        rightChildren.addAll(index.children.subList(201, index.children.size()));
        IndexNode<K, T> right = new IndexNode(rightKeys, rightChildren, this.pageIdAssigner());
        index.keys.subList(200, index.keys.size()).clear();
        index.children.subList(201, index.children.size()).clear();
        return new SimpleEntry(splitKey, right);
    }
}
