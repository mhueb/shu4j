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
package org.shu4j.utils.search;

import java.io.Serializable;

public final class SortField implements Serializable {
  private static final long serialVersionUID = 1L;

  private String sortField;
  private boolean sortAsc;

  protected SortField() {
  }

  public SortField(String sortField, boolean sortAsc) {
    this.sortField = sortField;
    this.sortAsc = sortAsc;
  }

  public String getFieldName() {
    return sortField;
  }

  public boolean isSortAsc() {
    return sortAsc;
  }

  public void setSortAsc(boolean sortAsc) {
    this.sortAsc = sortAsc;
  }
}
