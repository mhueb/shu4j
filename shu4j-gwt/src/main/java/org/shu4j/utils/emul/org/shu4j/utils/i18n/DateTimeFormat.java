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
