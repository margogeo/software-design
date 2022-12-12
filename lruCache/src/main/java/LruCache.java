import java.util.*;

interface Cache<K, V> {
  Integer put(K key, V value);
  V get(K key);
  int size();
  Set<K> getList();
}

public class LruCache<K, V> implements Cache<K, V> {
  private class Node<T, E> {
    Node<T, E> next;
    Node<T, E> previous;
    T key;
    E value;

    Node(Node<T, E> prev, Node<T, E> next, T key, E value) {
      this.previous = prev;
      this.next = next;
      this.key = key;
      this.value = value;
    }

    Node() {
      this.previous = null;
      this.next = null;
      this.key = null;
      this.value = null;
    }
  }

  private int maxSize;
  private Node<K, V> top;
  private Node<K, V> bottom;
  private HashMap<K, Node<K, V>> cache;

  public LruCache(int maxSize) {
    this.maxSize = maxSize;
    this.cache = new HashMap<>();
    this.bottom = new Node<>();
    this.top = this.bottom;
  }

  public V get(K key) {
    if (!cache.containsKey(key))
      return null;

    Node<K, V> result = cache.get(key);
    if (result.key != top.key) {
      Node<K, V> nextNode = result.next;
      Node<K, V> prevNode = result.previous;

      nextNode.previous = prevNode;
      if (result.key == bottom.key)
        bottom = nextNode;
      else
        prevNode.next = nextNode;

      result.previous = top;
      top.next = result;
      top = result;
      top.next = null;
    }
    return result.value;
  }

  public Integer put(K key, V value) {
    if (maxSize < 1)
      return null;

    if (cache.containsKey(key)) {
      get(key);
      cache.remove(key);
      top = top.previous;
      top.next = null;
    }

    Node<K, V> newNode = new Node<>(top, null, key, value);
    top.next = newNode;
    top = newNode;
    if (cache.size() == 0)
      bottom = top;

    cache.put(key, newNode);

    if (cache.size() > maxSize) {
      cache.remove(bottom.key);
      bottom = bottom.next;
      bottom.previous = null;
    }
    return cache.size();
  }

  public int size() {
    return cache.size();
  }

  public Set<K> getList() {
    return cache.keySet();
  }
}
