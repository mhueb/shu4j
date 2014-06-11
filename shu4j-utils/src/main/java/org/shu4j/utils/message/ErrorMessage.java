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
package org.shu4j.utils.message;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.shu4j.utils.message.Message;

@XmlRootElement
public class ErrorMessage implements Serializable {
  private static final long serialVersionUID = 1L;

  private int errorCode;

  private String message;

  private List<Message> details;

  public ErrorMessage() {
  }

  public ErrorMessage(int errorCode, String message) {
    this.errorCode = errorCode;
    this.message = message;
  }

  public ErrorMessage(String message) {
    this.message = message;
  }

  public ErrorMessage(int errorCode) {
    this.errorCode = errorCode;
  }

  public int getErrorCode() {
    return errorCode;
  }

  public String getMessage() {
    return message;
  }
  
  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<Message> getDetails() {
    return details;
  }

  public void setDetails(List<Message> details) {
    this.details = details;
  }
}
