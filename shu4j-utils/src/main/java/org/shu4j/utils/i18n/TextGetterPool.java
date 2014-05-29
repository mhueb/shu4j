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
package org.shu4j.utils.i18n;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class TextGetterPool {
  private final ILocaleGetter localeGetter;

  private final Map<Class<?>, Object> textGetterMap = new HashMap<Class<?>, Object>();

  public TextGetterPool(ILocaleGetter localeGetter) {
    this.localeGetter = localeGetter;
  }

  @SuppressWarnings("unchecked")
  public <T> T create(Class<T> cls) {
    synchronized (textGetterMap) {
      Object t = textGetterMap.get(cls);
      if (t == null) {
        t = Proxy.newProxyInstance(cls.getClassLoader(), new Class[] { cls }, new TextGetterAdapter(cls, localeGetter));
        textGetterMap.put(cls, t);
      }
      return (T) t;
    }
  }
}
