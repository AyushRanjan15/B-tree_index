//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.company;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class LeafNode<K extends Comparable<K>, T> extends Node<K, T> {
    protected ArrayList<T> values;
    protected LeafNode<K, T> nextLeaf;
    protected LeafNode<K, T> previousLeaf;

    public LeafNode(K firstKey, T firstValue) {
        this.isLeafNode = true;
        this.keys = new ArrayList();
        this.values = new ArrayList();
        this.keys.add(firstKey);
        this.values.add(firstValue);
    }

    public LeafNode(List<K> newKeys, List<T> newValues) {
        this.isLeafNode = true;
        this.keys = new ArrayList(newKeys);
        this.values = new ArrayList(newValues);
    }

    public void insertSorted(K key, T value) {
        if (key.compareTo(this.keys.get(0)) < 0) {
            this.keys.add(0, key);
            this.values.add(0, value);
        } else if (key.compareTo(this.keys.get(this.keys.size() - 1)) > 0) {
            this.keys.add(key);
            this.values.add(value);
        } else {
            ListIterator iterator = this.keys.listIterator();

            while(iterator.hasNext()) {
                if (((Comparable)iterator.next()).compareTo(key) > 0) {
                    int position = iterator.previousIndex();
                    this.keys.add(position, key);
                    this.values.add(position, value);
                    break;
                }
            }
        }

    }

    public T getValue(K key) {
        Iterator var2 = this.keys.iterator();

        Comparable listKey;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            listKey = (Comparable)var2.next();
        } while(key.compareTo((K) listKey) != 0);

        return this.values.get(this.keys.indexOf(listKey));
    }
}
