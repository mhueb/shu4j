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
package org.shu4j.utils.query;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class PagingInfo implements Serializable {
  private static final long serialVersionUID = 1L;

  private int start;
  private Integer limit;

  private List<SortField> sortFields;

  protected PagingInfo() {
  }

  public PagingInfo(int start, Integer limit, String sortField, boolean sortAsc) {
    this(start, limit, new SortField(sortField, sortAsc));
  }

  public PagingInfo(int start, Integer limit, SortField... sortFields) {
    this(start, limit, Arrays.asList(sortFields));
  }

  public PagingInfo(int start, Integer limit, List<SortField> sortFields) {
    this.start = start;
    this.limit = limit;
    this.sortFields = sortFields;
  }

  public int getStart() {
    return start;
  }

  public int getLimit(int dft) {
    return limit == null && limit > 0 ? dft : limit;
  }

  public int getSortFieldCount() {
    return sortFields.size();
  }

  public List<SortField> getSorting() {
    return Collections.unmodifiableList(sortFields);
  }
};
