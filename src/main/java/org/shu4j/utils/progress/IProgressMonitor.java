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

public interface IProgressMonitor {
  /**
   * Exception to indicate a user interruption.
   * 
   */
  static public class Cancel extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public Cancel() {
      super("Cancelation");
    }
  }

  /**
   * 
   * @param title
   * @param stepCount amount of steps necessary to fulfill this Progress
   */
  void begin(String title, int stepCount);

  /**
   * 
   * @param steps amount of work solved as part of the stepCount parameter handed into {@link begin}
   * @throws Cancel
   */
  void step(int steps);

  /**
   * setting this progress to completed
   */
  void done();

  public static enum LogLevel {
    INFO,
    WARNING,
    ERROR,
    STDOUT,
    STDERR,
    DEBUG
  }

  /**
   * @param comment The comment to add to the log
   * @param level With {@link LogLevel#INFO} the user will see the given comment as current activity.
   */
  void log(String comment, LogLevel level);

  /**
   * 
   * @param stepCount part of the steps of this progress (see {@link begin}) delegated to a sub-progress. The
   *          sub-progress has to be started by {@link begin} with its own arbitrary amount of steps.
   * @return
   */
  IProgressMonitor getSubProgress(int stepCount);
}
