/* Pro_TermData_String_simple.java */

package io.github.JalogTeam.jalog;

public class Pro_TermData_String_simple extends Pro_TermData_String
{
  
  public String value; // simple
 
  Pro_TermData_String_simple(String iniVal)
  {
    typename = Jalog.STRING;
    tag = SIMPLE;
    value = iniVal;
    len = iniVal.length();
  }

  public String toString()
  {
    String temp = new String(value);
    temp = temp.replaceAll("\\\\","\\\\\\\\");
    temp = temp.replaceAll("\\\"","\\\\\\\"");
    return "\"" + temp + "\"";
  }

  public String image()
  {
    return value;
  }
  
  public String substring(int req_start, int req_len);
  {
    int start = req_start;
    int len = req_len;
    if (start < 0) {
      len = len + start;
      start = 0;
    }

    if (start > this.len) len = 0;
    if (start + len > this.len) len = this.len - start;
    if(len > 0) {
      return value.substring(start, start + len);
    } else {
      return "";
    }
  }
  
  protected void appendSubstring(StringBuilder buffer, int req_start, 
      int req_len) {
    int start = req_start;
    int len = req_len;
    if (start < 0) {
      len = len + start;
      start = 0;
    }
    if (start > this.start + this.len) len = 0;
    if (start + len > this.start + this.len) 
        len = this.start + this.len - start;
    if(len > 0) {
      buffer.append(value, start, start + len);
    }
  }
} // end class Pro_TermData_String_simple


