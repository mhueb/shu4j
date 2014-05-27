package org.shu4j.utils.util;

import java.io.Serializable;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public final class VersionInfo implements Serializable {
  private static final long serialVersionUID = 1L;

  private static final Pattern NUMERIC = Pattern.compile("(\\d+)$");
  private static final Pattern FORMAT = Pattern.compile("\\s*(\\d+)\\.(\\d+)\\.(\\d+)(-([\\w\\d]+))?\\s*");
  
  public enum Likeness {
    UNDEFINED,
    OLDER_RELEASE,
    OLDER_VERSION,
    EQUAL,
    NEWER_VERSION,
    NEWER_RELEASE
  }

  private final int majorNumber;
  private final int minorNumber;
  private final int incrementalNumber;
  private final String qualifier;
  private final Integer buildNumber;

  public VersionInfo(int release, int version, int incremental) {
    this(release, version, incremental, null);
  }

  public VersionInfo(int release, int version, int incremental, String qualifier) {
    this.majorNumber = release;
    this.minorNumber = version;
    this.incrementalNumber = incremental;
    this.qualifier = qualifier;
    this.buildNumber = parseBuildNumber(qualifier);
  }

  public int getMajorNumber() {
    return majorNumber;
  }

  public int getMinorNumber() {
    return minorNumber;
  }

  public int getIncrementalNumber() {
    return incrementalNumber;
  }

  public String getQualifier() {
    return qualifier;
  }

  public Integer getBuildNumber() {
    return buildNumber;
  }

  public Likeness compare(VersionInfo o) {
    if (o == null)
      return Likeness.UNDEFINED;
    else if (getMajorNumber() < o.getMajorNumber() || getMajorNumber() == o.getMajorNumber() && getMinorNumber() < o.getMinorNumber())
      return Likeness.OLDER_RELEASE;
    else if (getMajorNumber() > o.getMajorNumber() || getMajorNumber() == o.getMajorNumber() && getMinorNumber() > o.getMinorNumber())
      return Likeness.NEWER_RELEASE;
    else if (getIncrementalNumber() == o.getIncrementalNumber())
      return Likeness.EQUAL;
    else if (getIncrementalNumber() > o.getIncrementalNumber())
      return Likeness.NEWER_VERSION;
    else
      return Likeness.OLDER_VERSION;
  }

  @Override
  public String toString() {
    StringBuilder buff = new StringBuilder();
    buff.append(majorNumber).append('.').append(minorNumber).append('.').append(incrementalNumber);
    if (qualifier != null)
      buff.append('-').append(qualifier);
    return buff.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + majorNumber;
    result = prime * result + minorNumber;
    result = prime * result + incrementalNumber;
    result = prime * result + ((buildNumber == null) ? 0 : buildNumber.hashCode());
    result = prime * result + ((qualifier == null) ? 0 : qualifier.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    VersionInfo other = (VersionInfo) obj;
    if (buildNumber == null) {
      if (other.buildNumber != null)
        return false;
    }
    else if (!buildNumber.equals(other.buildNumber))
      return false;
    if (incrementalNumber != other.incrementalNumber)
      return false;
    if (majorNumber != other.majorNumber)
      return false;
    if (minorNumber != other.minorNumber)
      return false;
    if (qualifier == null) {
      if (other.qualifier != null)
        return false;
    }
    else if (!qualifier.equals(other.qualifier))
      return false;
    return true;
  }

  public static Integer parseBuildNumber(String qualifier) {
    if (qualifier != null) {
      Matcher matcher = NUMERIC.matcher(qualifier);
      if (matcher.matches())
        return Integer.parseInt(matcher.group(1));
    }
    return null;
  }

  public static VersionInfo valueOf(String version) throws ParseException {
    if (StringUtils.isBlank(version))
      return null;
    Matcher matcher = FORMAT.matcher(version);
    if (matcher.matches()) {
      String modifier = matcher.groupCount() > 3 ? matcher.group(5) : null;
      return new VersionInfo(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)), modifier);
    }
    throw new ParseException("Invalid version format '" + version + "'", 0);
  }
}
