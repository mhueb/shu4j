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
package org.shu4j.utils.exception;

import org.shu4j.utils.message.IMessageSource;
import org.shu4j.utils.message.Message;
import org.shu4j.utils.message.MessageLevel;
import org.shu4j.utils.message.MessageList;

public class MessageException extends Exception {
  private static final long serialVersionUID = 1L;

  private MessageList messages;

  public MessageException() {
  }

  public MessageException(Message message) {
    addMessage(message);
  }

  public MessageException(Message message, Throwable e) {
    super(e);
    addMessage(message);
  }

  public MessageException(Throwable e) {
    this(new Message(e.getMessage(), MessageLevel.FATAL), e);
  }

  public MessageException(String msg) {
    this(msg, MessageLevel.FATAL);
  }

  public MessageException(String msg, MessageLevel level) {
    this(new Message(msg, level));
  }

  public MessageException(String msg, Throwable e, MessageLevel level) {
    this(new Message(msg, level), e);
  }

  public MessageException(String msg, Throwable e) {
    this(new Message(msg, MessageLevel.FATAL), e);
  }

  public MessageException(IMessageSource messages) {
    this.messages = new MessageList(messages);
  }

  public MessageException addMessage(Message message) {
    if (messages == null)
      messages = new MessageList();
    messages.add(message);
    return this;
  }

  public MessageException addMessages(IMessageSource add) {
    if (add != null) {
      if (messages == null)
        messages = new MessageList();
      messages.add(add);
    }
    return this;
  }

  @Override
  public String getMessage() {
    StringBuilder buff = new StringBuilder();
    if (messages != null) {
      for (Message msg : messages)
        buff.append(msg.getLevel().name()).append(": ").append(msg.getText()).append("\n");
    }
    return buff.toString();
  }

  public String getMainMessage() {
    if (messages != null && messages.hasMessages())
      return messages.getMessages().get(0).getText();
    else
      return super.getMessage();
  }

  public IMessageSource getMessages() {
    return messages;
  }

  @Override
  public String toString() {
    return "ValidateException [messages=" + messages + ", cause=" + getCause() + "]";
  }
}
