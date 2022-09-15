/* Pro_TermData_String_substring.java */

package io.github.JalogTeam.jalog;

public class Pro_TermData_String_substring 
    extends Pro_TermData_String
{
  public final Pro_TermData_String base_string; // substring
  public final long start; // substring

  static private long true_len( long base_len, long req_start, long req_len) {
    long len = req_len;
    long start = req_start;
    long max_len;
    
    if (start < 0) {
      len = len + start;
      start = 0;
    }
    // viimeinen indeksi this.start + this.len
    max_len = base_len - start;
    if ( max_len > 0) {
      if ( len > max_len) len = max_len;
    } else {
      len = 0;
    }
    return len;     
  }

  Pro_TermData_String_substring(Pro_TermData_String base_string,
      long req_start, long req_len)
  {
    super(true_len(base_string.len, req_start, req_len));

    long start = req_start;
    
    this.base_string = base_string;
// !! These must fit inside of the base string. Add test! !!

    if (start < 0) {
      start = 0;
    }
    this.start = start;
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
      
  public String structure() {
    return "ss(" + len + "," + base_string.structure() + "," + start + ")";
  }

  protected void get_string_part(long p)
  {
    base_string.get_string_part(p + start);
    if (part_len > len - p) part_len = len - p;
  }
  
 

} // end class Pro_TermData_String_substring


