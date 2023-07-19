/* JavaString.java */

package io.github.JalogTeam.parser;

public class JavaString implements VirtualString
{
  private String value;

  private JavaString(String S) {
    value = S;
  }
  
  private JavaString() {
  }
  
  public static JavaString make(String S) {
    if(S != null) {
      return new JavaString(S);
    } else {
      return null;
    }
  }
  
  public char charAt(long index) {
    if (index < value.length()) {
      return value.charAt((int)index);
    } else {
      throw new IndexOutOfBoundsException();
    }
  }

  public long indexOf(String str, long fromIndex) {
    if (fromIndex < value.length()) {
      return value.indexOf(str, (int)fromIndex);
    } else {
      return -1;
    }
  }


  public long length() {
    return value.length();
  }
  
/*
  public String substring(long start, long end) {
    return value.substring(start, end);
  }
*/

  public String fragment(long req_start, long req_len) {
    long start = req_start;
    long len = req_len;
    if (start < 0) {
      len = len + start;
      start = 0;
    }
    if (start > value.length()) len = 0;
    if (start + len > value.length()) len = value.length() - start;
    if(len > 0) {
      return value.substring((int)start, (int)(start + len));
    } else {
      return "";
    }
  }
  
  public String toString() {
    return value;
  }
}
