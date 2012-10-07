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
public abstract class AbstractProgressMonitor implements IProgressMonitor {
  private int stepCount;
  private int progressCounter;
  private String title;
  private boolean fireCancel;

  public void begin(String title, int stepCount) {
    if (title==null||title.isEmpty())
      throw new IllegalArgumentException("Title is null or empty!");
    if (stepCount < 1)
      throw new IllegalArgumentException("stepCount must not be less than 1, but is=" + stepCount);
    this.title = title;
    this.stepCount = stepCount;
    log(title, LogLevel.INFO);
    onBegin();
  }

  public String getTitle() {
    return title;
  }

  protected void onBegin() {
  }

  public void done() {
    progressCounter = stepCount;
    onDone();
  }

  protected void onDone() {
  }

  public void step(int inc) throws CancelationException {
    if (fireCancel)
      throw new CancelationException();

    if (inc > 0) {
      int next = progressCounter + inc;
      if (next > stepCount)
        next = stepCount;
      progressCounter = next;
    }

    onProgress();
  }

  public void triggerCancel() {
    fireCancel = true;
  }

  protected void onProgress() {
  }

  @Override
  public IProgressMonitor getSubProgress(int steps) {
    return new SubProgressMonitorAdapter(this, steps);
  }

  public boolean isCancelTriggered() {
    return fireCancel;
  }

  public int getStepCount() {
    return stepCount;
  }

  /**
   * @return Progress value between 0 and {@link #getStepCount()}.
   */
  public int getProgress() {
    return progressCounter;
  }

  /**
   * @return Progress value between 0 and 100.
   */
  public int getProgress100() {
    return stepCount == 0 ? 0 : progressCounter * 100 / stepCount;
  }
}
