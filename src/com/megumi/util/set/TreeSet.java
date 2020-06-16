package com.megumi.util.set;

import com.megumi.util.map.Map;
import com.megumi.util.map.TreeMap;

public class TreeSet<E> implements  Set<E> {

    private  Map<E,Object> map  =new TreeMap<>();

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public void clear() {
          map.clear();
    }

    @Override
    public boolean contains(E element) {
        return map.containsKey(element);
    }

    @Override
    public void add(E element) {
         map.put(element,null);
    }

    @Override
    public void remove(E element) {
         map.remove(element);
    }

    @Override
    public void traversal(Visitor<E> visitor) {
            map.traversal(new Map.Visitor<E, Object>() {

                @Override
                protected boolean visit(E key, Object value) {
                    return  visitor.visit(key);
                }
            });
    }
}
