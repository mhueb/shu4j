/* 
 * Copyright 2012 Matthias Huebner
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.shu4j.utils.reference;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Thread safe weak reference set.
 * <p>
 * Entries are ordered as they where added.
 * 
 * @author Matthias Huebner
 * 
 * @param <E> Type of elements
 */
public final class WeakReferenceSet<E> implements Set<E> {

  private static final class WeakIterator<T> implements Iterator<T> {
    private final WeakReferenceSet<T> set;
    private final List<WeakReference<T>> copy;
    private T next;
    private T last;
    private int pos = 0;

    public WeakIterator(WeakReferenceSet<T> set) {
      this.set = set;
      this.copy = set.set;
      adjust();
      last = next;
    }

    @Override
    public boolean hasNext() {
      return next != null;
    }

    @Override
    public T next() {
      last = next;
      adjust();
      return last;
    }

    private void adjust() {
      next = null;
      while (next == null && pos < set.size()) {
        next = copy.get(pos++).get();
      }
    }

    @Override
    public void remove() {
      if (last == null)
        throw new IllegalStateException("Unexpected remove");
      set.remove(last);
    }
  }

  private List<WeakReference<E>> set = new ArrayList<WeakReference<E>>();

  @Override
  public boolean add(E e) {
    if (e == null)
      return false;

    synchronized (set) {
      if (exists(e, set))
        return false;

      List<WeakReference<E>> copy = new ArrayList<WeakReference<E>>(set);
      copy.add(new WeakReference<E>(e));
      collectGarbage(copy);
      set = copy;
      return true;
    }
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    if (c == null)
      return false;

    synchronized (set) {
      List<WeakReference<E>> copy = new ArrayList<WeakReference<E>>(set);

      boolean changed = false;
      for (E e : c) {
        if (!exists(e, copy)) {
          copy.add(new WeakReference<E>(e));
          changed = true;
        }
      }

      if (changed) {
        collectGarbage(copy);
        set = copy;
        return true;
      }
      return false;
    }
  }

  @Override
  public void clear() {
    synchronized (set) {
      set = new ArrayList<WeakReference<E>>();
    }
  }

  @Override
  public boolean contains(Object o) {
    return exists(o, set);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    List<WeakReference<E>> copy = set;
    for (Object o : c) {
      if (!exists(o, copy))
        return false;
    }
    return true;
  }

  @Override
  public boolean isEmpty() {
    return set.isEmpty() || size() == 0;
  }

  @Override
  public Iterator<E> iterator() {
    return new WeakIterator<E>(this);
  }

  @Override
  public boolean remove(Object o) {
    if (o == null)
      return false;

    synchronized (set) {
      List<WeakReference<E>> copy = new ArrayList<WeakReference<E>>(set);

      boolean changed = false;
      Iterator<WeakReference<E>> it = copy.iterator();
      while (it.hasNext()) {
        E i = it.next().get();
        if (i != null && o.equals(i)) {
          it.remove();
          changed = true;
          break;
        }
      }

      if (changed) {
        collectGarbage(copy);
        set = copy;
        return true;
      }

      return false;
    }
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    if (c == null)
      return false;

    synchronized (set) {
      List<WeakReference<E>> copy = new ArrayList<WeakReference<E>>(set);

      boolean changed = false;
      Iterator<WeakReference<E>> it = copy.iterator();
      while (it.hasNext()) {
        E i = it.next().get();
        if (i != null && c.contains(i)) {
          it.remove();
          changed = true;
        }
      }

      if (changed) {
        collectGarbage(copy);
        set = copy;
        return true;
      }

      return false;
    }
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    if (c == null)
      return false;

    synchronized (set) {
      List<WeakReference<E>> copy = new ArrayList<WeakReference<E>>(set);

      boolean changed = false;
      Iterator<WeakReference<E>> it = copy.iterator();
      while (it.hasNext()) {
        E i = it.next().get();
        if (i != null && !c.contains(i)) {
          it.remove();
          changed = true;
        }
      }

      if (changed) {
        collectGarbage(copy);
        set = copy;
        return true;
      }

      return false;
    }
  }

  @Override
  public int size() {
    int size = 0;
    for (WeakReference<E> s : set) {
      if (s.get() != null)
        size++;
    }
    return size;
  }

  @Override
  public Object[] toArray() {
    return makeList().toArray();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return makeList().toArray(a);
  }

  private boolean exists(Object e, List<WeakReference<E>> copy) {
    for (WeakReference<E> we : copy) {
      E i = we.get();
      if (i != null && i.equals(e))
        return true;
    }
    return false;
  }

  private List<E> makeList() {
    List<E> result = new ArrayList<E>();
    for (WeakReference<E> s : set) {
      E e = s.get();
      if (e != null)
        result.add(e);
    }
    return result;
  }

  private static <T> void collectGarbage(Collection<WeakReference<T>> weakColl) {
    Iterator<WeakReference<T>> it = weakColl.iterator();
    while (it.hasNext()) {
      if (it.next().get() == null)
        it.remove();
    }
  }
}
