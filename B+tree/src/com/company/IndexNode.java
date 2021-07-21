//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class IndexNode<K extends Comparable<K>, T> extends Node<K, T> {
    protected ArrayList<Node<K, T>> children;
    protected Boolean visited;

    public void getAllPageNumber() {
        for(int i = 0; i < this.children.size(); ++i) {
            System.out.println(((Node)this.children.get(i)).getPage_no());
        }

    }

    public IndexNode(K key, Node<K, T> child0, Node<K, T> child1, int id) {
        this.isLeafNode = false;
        this.keys = new ArrayList();
        this.keys.add(key);
        this.children = new ArrayList();
        this.children.add(child0);
        this.children.add(child1);
        this.page_no = id;
        this.visited = false;
    }

    public IndexNode(List<K> newKeys, List<Node<K, T>> newChildren, int id) {
        this.isLeafNode = false;
        this.keys = new ArrayList(newKeys);
        this.children = new ArrayList(newChildren);
        this.page_no = id;
        this.visited = false;
    }

    public void insertSorted(Entry<K, Node<K, T>> e, int index) {
        K key = (K) e.getKey();
        Node<K, T> child = (Node)e.getValue();
        if (index >= this.keys.size()) {
            this.keys.add(key);
            this.children.add(child);
        } else {
            this.keys.add(index, key);
            this.children.add(index + 1, child);
        }

    }
}
