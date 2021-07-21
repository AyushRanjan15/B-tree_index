//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.company;

import java.util.ArrayList;

public class Node<K extends Comparable<K>, T> {
    protected boolean isLeafNode;
    protected ArrayList<K> keys;
    protected IndexNode<K, T> parent;
    protected int indexInParent;
    protected int page_no;

    public Node() {
    }

    public int getPage_no() {
        return this.page_no;
    }

    public boolean isOverflowed() {
        return this.keys.size() > 400;
    }

    public boolean isUnderflowed() {
        return this.keys.size() < 200;
    }

    public void setParent(IndexNode<K, T> parent) {
        this.parent = parent;

        for(int i = 0; i < parent.children.size(); ++i) {
            if (((Node)parent.children.get(i)).equals(this)) {
                this.indexInParent = i;
                break;
            }
        }

    }

    public IndexNode<K, T> getParent() {
        return this.parent;
    }

    public int getIndexInParent() {
        return this.indexInParent;
    }
}
