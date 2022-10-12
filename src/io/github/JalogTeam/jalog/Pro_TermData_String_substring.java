/* Pro_TermData_String_substring.java */

package io.github.JalogTeam.jalog;

public class Pro_TermData_String_substring 
    extends Pro_TermData_String
{
  public Pro_TermData_String base_string; // substring
  public long start; // substring

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

  public static Pro_TermData_String make(Pro_TermData_String base_string,
      long req_start, long req_len)  
  {
    long len = true_len(base_string.len, req_start, req_len);
    Pro_TermData_String ans;
    
    if (len > 0) {
      ans = new Pro_TermData_String_substring();
      ans.init(len);
      ((Pro_TermData_String_substring)ans).base_string = base_string;
      if (req_start < 0) {
        ((Pro_TermData_String_substring)ans).start = 0;
      } else {
        ((Pro_TermData_String_substring)ans).start = req_start;
      }
    } else {
      ans = Pro_TermData_String_simple.empty;
    }
    
    return ans;
  }

/*
  public String toString()
  {
    String temp = new String(image());
    temp = temp.replaceAll("\\\\","\\\\\\\\");
    temp = temp.replaceAll("\\\"","\\\\\\\"");
    return "\"" + temp + "\"";
  }
*/

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


