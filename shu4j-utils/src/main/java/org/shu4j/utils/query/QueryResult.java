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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QueryResult<T extends Serializable> implements Serializable {
  private static final long serialVersionUID = 1;

  private static final QueryResult<Serializable> EMPTY = new QueryResult<Serializable>();

  @SuppressWarnings("unchecked")
  public static <T extends Serializable> QueryResult<T> emptyResult() {
    return (QueryResult<T>) EMPTY;
  }

  private int totalCount;

  private List<T> result;

  private List<SortField> sorting;

  private int start;

  public QueryResult() {
    result = new ArrayList<T>();
  }

  public QueryResult(List<T> result, int start, int totalCount) {
    this.result = result;
    this.start = start;
    this.totalCount = totalCount;
  }

  public QueryResult(List<T> result, int start, int totalCount, List<SortField> sorting) {
    this.result = result;
    this.start = start;
    this.totalCount = totalCount;
    this.sorting = sorting;
  }

  public QueryResult(List<T> result) {
    this(result, 0, result.size());
  }

  public int getStart() {
    return start;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public List<T> getResult() {
    return result;
  }

  public List<SortField> getSorting() {
    return Collections.unmodifiableList(sorting);
  }
}
