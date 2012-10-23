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
 * Console implementation.<br>
 * This class is usable if no special progress monitor is used, especially in tests. It is recommended to use
 * an instance of this class instead of null.
 * 
 * @author heb
 * 
 */
public final class ProgressMonitorConsole implements IProgressMonitor {
  private static ProgressMonitorConsole instance = null;
  private int steps;
  private int count;

  private ProgressMonitorConsole() {
  }

  public static ProgressMonitorConsole getInstance() {
    if (null == instance) {
      instance = new ProgressMonitorConsole();
    }
    return instance;
  }

  public void begin(String title, int steps) {
    this.steps = steps;
    this.count = 0;
    System.out.println("begin '" + title + "' with " + steps + "\n");
  }

  public void log(String comment, LogLevel level) {
    if (level == LogLevel.ERROR)
      System.err.println(comment);
    else
      System.out.println(comment);
  }

  public void step(int units) {
    count += units;
    if (steps == 0)
      System.err.println("Called step without begin!             \r");
    else
      System.out.println("" + count * 100 / steps + "%             \r");
  }

  @Override
  public IProgressMonitor getSubProgress(int stepCount) {
    return new SubProgressMonitorAdapter(this, stepCount);
  }

  @Override
  public void done() {
    System.out.println("done.\n");
  }
}
