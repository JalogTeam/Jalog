/* Pro_TermData_String_simple.java */

package io.github.JalogTeam.jalog;

public class Pro_TermData_String_simple extends Pro_TermData_String
{
  
  public String value; // simple
  
  public static Pro_TermData_String_simple empty = 
      new Pro_TermData_String_simple();
  static {
    empty.init(0);
    empty.value = "";
  }

  public static Pro_TermData_String make(String iniVal)
  {
    Pro_TermData_String_simple ans;

    if(iniVal.length() > 0) {
      ans = new Pro_TermData_String_simple();
      ans.init(iniVal.length());
      ans.value = iniVal;
    } else {
      ans = empty;
    }
    
    return ans;
  }

/*
  public String toString()
  {
    String temp = new String(value);
    temp = temp.replaceAll("\\\\","\\\\\\\\");
    temp = temp.replaceAll("\\\"","\\\\\\\"");
    return "\"" + temp + "\"";
  }
*/

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
// System.out.println("### simple substring");
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
    if (start > this.len) len = 0;
    if (start + len > this.len) 
        len = this.len - start;
    if(len > 0) {
      buffer.append(value, (int)start, (int)(start + len));
    }
  }
      
  public String structure() {
    return "s(" + len + ",|" + value + "|)";
  }

  protected void get_string_part(long p)
  {
    Pro_TermData_String_concat cs;

    string_found = value;
    start_pos = p;
    part_len = len - start_pos;
      
  }
  
} // end class Pro_TermData_String_simple


