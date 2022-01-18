/* Pro_TermData_String_substring.java */

package io.github.JalogTeam.jalog;

public class Pro_TermData_String_substring 
    extends Pro_TermData_String
{
  public Pro_TermData_String base_string; // substring
  public int start; // substring
 

  Pro_TermData_String_substring(Pro_TermData_String base_string,
      int start, int len)
  {
    typename = Jalog.STRING;
    tag = SUBSTRING;
    this.base_string = base_string;
// !! These must fit inside of the base string. Add test! !!
    this.start = start;
    this.len = len;
  }

  public String toString()
  {
    String temp = new String(image());
    temp = temp.replaceAll("\\\\","\\\\\\\\");
    temp = temp.replaceAll("\\\"","\\\\\\\"");
    return "\"" + temp + "\"";
  }

  public String image()
  {
    return base_string.substring(start, len);
  }
  
  public String substring(int req_start, int req_len);
  {
    int start = req_start;
    int len = req_len;
    if (start < 0) {
      len = len + start;
      start = 0;
    }
    // viimeinen indeksi this.start + this.len
    if (start > this.start + this.len) len = 0;
    if (start + len > this.start + this.len) 
        len = this.start + this.len - start;
    if(len > 0) {
      return base_string(start + this.start, len);
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
    // viimeinen indeksi this.start + this.len
    if (start > this.start + this.len) len = 0;
    if (start + len > this.start + this.len) 
        len = this.start + this.len - start;
    if(len > 0) {
      base_string.appendSubstring(buffer, start + this.start, len);
    }
  }
} // end class Pro_TermData_String_substring


