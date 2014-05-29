/* 
 * Copyright 2012 SHU4J
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
package org.shu4j.utils.util;

public final class Hasher {
  public static final int HASHSEED = 7;

  private int hash = HASHSEED;

  public Hasher() {
  }

  public Hasher add(int val) {
    hash = hash * 31 + val;
    return this;
  }

  public Hasher(Object obj) {
    add(obj);
  }

  public Hasher add(Object obj) {
    if (obj != null)
      hash = hash * 31 + obj.hashCode();
    return this;
  }

  public int getHash() {
    return hash;
  }

  public static int add(int hash, int val) {
    return hash * 31 + val;
  }

  public static int add(int hash, Object obj) {
    if (obj != null)
      return hash * 31 + obj.hashCode();
    return hash;
  }
}
