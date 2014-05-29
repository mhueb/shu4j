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
package org.shu4j.utils.i18n;

import java.util.Date;

/**
 * Delegate to make it work in GWT too
 * 
 * @author Matt
 * 
 */
public class DateTimeFormat {

	public enum PredefinedFormat {
		DATE_MEDIUM, TIME_SHORT
	}

	// TODO

	public static DateTimeFormat getFormat(PredefinedFormat format) {
		switch (format) {
		case DATE_MEDIUM:
			return getFormat(com.google.gwt.i18n.shared.DateTimeFormat.PredefinedFormat.DATE_MEDIUM);
		case TIME_SHORT:
			return getFormat(com.google.gwt.i18n.shared.DateTimeFormat.PredefinedFormat.TIME_SHORT);
		}
		throw new IllegalArgumentException("Unexpected argument " + format);
	}

	public static DateTimeFormat getFormat(com.google.gwt.i18n.shared.DateTimeFormat.PredefinedFormat format) {
		return new DateTimeFormat(com.google.gwt.i18n.shared.DateTimeFormat.getFormat(format));
	}

	private final com.google.gwt.i18n.shared.DateTimeFormat inst;

	protected DateTimeFormat(com.google.gwt.i18n.shared.DateTimeFormat inst) {
		this.inst = inst;
	}

	public String format(Date date) {
		return inst.format(date);
	}
}
