/* Pro_TermData_String_simple.java */

package io.github.JalogTeam.jalog;

public class Pro_TermData_String_simple extends Pro_TermData_String
{
  
  public final String value; // simple
 
  Pro_TermData_String_simple(String iniVal)
  {
    super(SIMPLE, iniVal.length());
    
    this.value = iniVal;
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
  
  public String substring(long req_start, long req_len) {
    long start = req_start;
    long len = req_len;
    if (start < 0) {
      len = len + start;
      start = 0;
    }

    if (start > this.len) len = 0;
    if (start + len > this.len) len = this.len - start;
    if(len > 0) {
      return value.substring((int)start, (int)(start + len));
    } else {
      return "";
    }
  }
  
  protected void appendSubstring(StringBuilder buffer, long req_start, 
      long req_len) {
    long start = req_start;
    long len = req_len;
    if (start < 0) {
      len = len + start;
      start = 0;
    }
    if (start > this.start + this.len) len = 0;
    if (start + len > this.start + this.len) 
        len = this.start + this.len - start;
    if(len > 0) {
      buffer.append(value, (int)start, (int)(start + len));
    }
  }
} // end class Pro_TermData_String_simple


