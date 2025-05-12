package DSA;

import java.util.Map;
import java.util.Objects;

public class MapLinkedList<K, V> {
    private LinkedList<Pair<K, V>> list;

    public MapLinkedList() {
        list = new LinkedList<>(LinkedList.ListType.SINGLY);
    }

    public void put(K key, V value) {
        for (int i = 0; i < list.size(); i++) {
            Pair<K, V> pair = list.get(i);
            if (pair.getKey().equals(key)) {
                pair.setValue(value);
                return;
            }
        }
        list.add(new Pair<>(key, value));
    }

    public V get(K key) {
        for (int i = 0; i < list.size(); i++) {
            Pair<K, V> pair = list.get(i);
            if (pair.getKey().equals(key)) {
                return pair.getValue();
            }
        }
        return null;
    }

    public boolean isEmpty() {
        return list.size() == 0;
    }

    public V getOrDefault(K key, V defaultValue) {
        for (int i = 0; i < list.size(); i++) {
            Pair<K, V> pair = list.get(i);
            if (pair.getKey().equals(key)) {
                return pair.getValue();
            }
        }
        return defaultValue;
    }

    public boolean containsKey(K key) {
        for (int i = 0; i < list.size(); i++) {
            Pair<K, V> pair = list.get(i);
            if (pair.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return list.size();
    }

    public void remove(K key) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getKey().equals(key)) {
                list.removeAt(i);
                break;
            }
        }
    }

    // Method to get all the values stored in the MapLinkedList
    public Iterable<V> values() {
        LinkedList<V> valuesList = new LinkedList<>(LinkedList.ListType.SINGLY);
        for (int i = 0; i < list.size(); i++) {
            Pair<K, V> pair = list.get(i);
            valuesList.add(pair.getValue());
        }
        return (Iterable<V>) valuesList; // No need for casting anymore
    }

    // Implement entrySet() method to return key-value pairs
    public Iterable<Pair<K, V>> entrySet() {
        LinkedList<Pair<K, V>> entrySet = new LinkedList<>(LinkedList.ListType.SINGLY);
        for (int i = 0; i < list.size(); i++) {
            entrySet.add(list.get(i)); // Add the Pair directly
        }
        return (Iterable<Pair<K, V>>) entrySet;
    }
    

    // Inner Pair class now implements Map.Entry
    public static class Pair<K, V> implements Map.Entry<K, V> {
        private K key;
        private V value;
    
        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    
        @Override
        public K getKey() {
            return key;
        }
    
        @Override
        public V getValue() {
            return value;
        }
    
        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair<?, ?> pair = (Pair<?, ?>) o;
            return Objects.equals(key, pair.key) && Objects.equals(value, pair.value);
        }
    
        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }
    }
    
}
