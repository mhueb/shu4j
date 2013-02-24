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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SearchResult<T> implements Serializable {
  private static final long serialVersionUID = 1;

  private static final SearchResult<Object> EMPTY = new SearchResult<Object>(Collections.emptyList(), 0);

  @SuppressWarnings("unchecked")
  public static <T> SearchResult<T> emptyResult() {
    return (SearchResult<T>) EMPTY;
  }

  private int totalCount;

  private List<T> result;

  private List<SortField> sorting;

  public SearchResult() {
    result = new ArrayList<T>();
  }

  public SearchResult(List<T> result, int totalCount) {
    this.result = result;
    this.totalCount = totalCount;
  }

  public SearchResult(List<T> result, int totalCount, List<SortField> sorting) {
    this.result = result;
    this.totalCount = totalCount;
    this.sorting = sorting;
  }

  public SearchResult(List<T> result) {
    this(result, result.size());
  }

  public int getTotalCount() {
    return totalCount;
  }

  public Collection<T> getResult() {
    return result;
  }

  public List<SortField> getSorting() {
    return sorting;
  }
}
