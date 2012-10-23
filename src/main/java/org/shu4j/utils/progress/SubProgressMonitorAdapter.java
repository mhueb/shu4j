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
package org.shu4j.utils.progress;

/**
 *  
 */
public final class SubProgressMonitorAdapter implements IProgressMonitor {

  private final IProgressMonitor parent;
  private final int parentStepCount;
  private int parentStepCounter;
  private String title;
  private Double parentInc;
  private double parentCounter;

  public SubProgressMonitorAdapter(IProgressMonitor parent, int parentStepCount) {
    if (parent == null)
      throw new IllegalArgumentException("parent must not be null!");
    if (parentStepCount < 1)
      throw new IllegalArgumentException("parentStepSize must be greater than 0, but is=" + parentStepCount);
    this.parent = parent;
    this.parentStepCount = parentStepCount;
  }

  @Override
  public void begin(String title, int stepCount) {
    if (title == null || title.isEmpty())
      throw new IllegalArgumentException("invalid title='" + title + "'");
    if (parentInc != null)
      throw new IllegalStateException("Not allowed to call twice!");
    this.title = title;
    parent.log(this.title, LogLevel.INFO);

    if (stepCount < 1)
      this.parentInc = 0.0;
    else {
      this.parentInc = (double) parentStepCount / (double) stepCount;
      if (Double.isNaN(this.parentInc) || Double.isInfinite(this.parentInc))
        this.parentInc = 0.0;
    }
  }

  @Override
  public void log(String comment, LogLevel level) {
    parent.log(comment, level);
  }

  @Override
  public void done() {
    try {
      parent.step(parentStepCount - parentStepCounter);
      parentStepCounter = parentStepCount;
      parentCounter = 0.0;
    }
    catch (CancelationException e) {
      // all done
    }
  }

  @Override
  public void step(int steps) {
    if (parentInc == null)
      return;

    parentCounter += parentInc * steps;
    int inc = (int) parentCounter; // inc may be 0
    if (parentStepCounter + inc > parentStepCount) {
      inc = parentStepCount - parentStepCounter;
      parentStepCounter = parentStepCount;
      parentCounter = 0.0;
    }
    else {
      parentCounter -= inc;
      parentStepCounter += inc;
    }
    parent.step(inc);
  }

  @Override
  public IProgressMonitor getSubProgress(int steps) {
    return new SubProgressMonitorAdapter(this, steps);
  }
}
