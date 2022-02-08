/* Pro_TermData_String_substring.java */

package io.github.JalogTeam.jalog;

public class Pro_TermData_String_substring 
    extends Pro_TermData_String
{
  public Pro_TermData_String base_string; // substring
  public long start; // substring
 

  Pro_TermData_String_substring(Pro_TermData_String base_string,
      long req_start, long req_len)
  {
    long max_len;
    
    typename = Jalog.STRING;
    tag = SUBSTRING;
    this.base_string = base_string;
// !! These must fit inside of the base string. Add test! !!
    this.start = req_start;
    this.len = req_len;

    if (this.start < 0) {
      this.len = this.len + this.start;
      this.start = 0;
    }
    // viimeinen indeksi this.start + this.len
    max_len = base_string.len - this.start;
    if ( max_len > 0) {
      if ( this.len > max_len) this.len = max_len;
    } else {
      this.len = 0;
    }
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
  
  public String substring(long req_start, long req_len)
  {
    long start = req_start;
    long len = req_len;
    long max_len;
    
System.out.println("** substring(" + req_start + ", " + req_len + ")");
System.out.println("** base_string.len = " + base_string.len);
System.out.println("** base_string.image() = |" + base_string.image() + "|");
    if (start < 0) {
      len = len + start;
      start = 0;
    }
    // viimeinen indeksi this.start + this.len
    max_len = this.len - start;
    if((len > 0) && (max_len > 0)) {
      if (len > max_len)  len = max_len;
  
      return base_string.substring((int)(start + this.start), (int)len);
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
    // viimeinen indeksi this.start + this.len
    if (start > this.start + this.len) len = 0;
    if (start + len > this.start + this.len) 
        len = this.start + this.len - start;
    if(len > 0) {
      base_string.appendSubstring(buffer, start + this.start, len);
    }
  }
} // end class Pro_TermData_String_substring


