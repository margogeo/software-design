import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class LRUCacheTest {
  LruCache<Integer, Integer> cache;

  public LRUCacheTest() {
    cache = new LruCache<>(5);
  }

  @Test
  public void EmptyCacheTest() {
    assertNull(cache.get(0));
  }

  @Test
  public void SimpleTest() {
    assertEquals(cache.put(3, 4), Integer.valueOf(cache.size()));
    assertEquals(Integer.valueOf(4), cache.get(3));
  }

  @Test
  public void OverflowTest() {
    cache.put(3, 4);
    cache.put(4, 5);
    cache.put(5, 6);
    cache.put(6, 7);
    cache.put(7, 8);
    cache.get(3);
    cache.put(8, 9);

    assertEquals(Integer.valueOf(4), cache.get(3));
    assertEquals(Integer.valueOf(9), cache.get(8));
    assertNull(cache.get(4));
  }

  @Test
  public void RewriteTest() {
    cache.put(3, 4);
    assertEquals(Integer.valueOf(4), cache.get(3));
    cache.put(3, 5);
    assertEquals(Integer.valueOf(5), cache.get(3));
  }
}
