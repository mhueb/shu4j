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
package org.shu4j.utils.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.shu4j.utils.resource.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TextGetterAdapter implements InvocationHandler {
  private static final String PROPERTIES = ".properties";
  private static final String UTF_8 = "utf-8";

  private final Logger logger = LoggerFactory.getLogger(TextGetterAdapter.class);

  private final Class<?> textGetterInterfaceClass;
  private final Map<String, Properties> properties = new HashMap<String, Properties>();

  private final ILocaleGetter localeGetter;

  public TextGetterAdapter(Class<?> textGetterInterfaceClass, ILocaleGetter localeGetter) {
    this.textGetterInterfaceClass = textGetterInterfaceClass;
    this.localeGetter = localeGetter;
  }

  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    Properties props = getProperties();
    String result = find(props, method.getName());
    if (args != null) {
      for (int i = 0; i < args.length; ++i)
        result = result.replace("{" + i + "}", args[i] == null ? "" : args[i].toString());
    }
    return result;
  }

  private String find(Properties props, String name) {
    String result = props.getProperty(name);
    if (result == null) {
      logger.error("Missing property '" + name + "' in property file of class " + textGetterInterfaceClass.getSimpleName() );
      result = "##" + name + "##";
      props.setProperty(name, result);
    }
    return result;
  }

  private Properties getProperties() {
    String language = localeGetter.getLocale().getLanguage().toLowerCase();
    synchronized (properties) {
      Properties props = properties.get(language);
      if (props == null) {
        props = loadProperties(language);
        properties.put(language, props);
      }
      return props;
    }
  }

  private Properties loadProperties(String language) {
    Properties props = new Properties();
    String resourceName = textGetterInterfaceClass.getSimpleName();
    InputStream resourceAsStream = openResourceStream(resourceName + "_" + language + PROPERTIES);
    if (resourceAsStream == null)
      resourceAsStream = openResourceStream(resourceName + PROPERTIES);
    if (resourceAsStream != null) {
      try {
        props.load(resourceAsStream);
      }
      catch (IOException e) {
        logger.warn("Failed to load i18n properties", e);
      }
      try {
        resourceAsStream.close();
      }
      catch (IOException e) {
        if (logger.isInfoEnabled())
          logger.info("Failed to close i18n property file", e);
      }
    }
    return props;
  }

  private InputStream openResourceStream(String file) {
    try {
      return ResourceUtil.openResourceStream(textGetterInterfaceClass, file, UTF_8);
    }
    catch (IOException e) {
      if (logger.isDebugEnabled())
        logger.debug("I18N property file not available: " + file, e);
      return null;
    }
  }
}