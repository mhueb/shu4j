package org.shu4j.utils.util;

import java.io.Serializable;

public final class Pair<F, S> implements Serializable {
  private static final long serialVersionUID = 1L;

  public static <F, S> Pair<F, S> create(F f, S s) {
    return new Pair<F, S>(f, s);
  }

  private F first;
  private S second;

  public Pair(F first, S second) {
    this.first = first;
    this.second = second;
  }

  public F getFirst() {
    return first;
  }

  public S getSecond() {
    return second;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((first == null) ? 0 : first.hashCode());
    result = prime * result + ((second == null) ? 0 : second.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Pair<?, ?> other = (Pair<?, ?>) obj;
    if (first == null) {
      if (other.first != null)
        return false;
    }
    else if (!first.equals(other.first))
      return false;
    if (second == null) {
      if (other.second != null)
        return false;
    }
    else if (!second.equals(other.second))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Pair [first=" + first + ", second=" + second + "]";
  }

}
