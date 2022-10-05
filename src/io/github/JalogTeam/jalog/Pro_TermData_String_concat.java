/* Pro_TermData_String_concat.java */

package io.github.JalogTeam.jalog;

public class Pro_TermData_String_concat extends Pro_TermData_String
{
  
  public Pro_TermData_String left; // concat
  public Pro_TermData_String right; // concat

  public static Pro_TermData_String make(Pro_TermData_String left, 
      Pro_TermData_String right)  
  {
    long len = left.len + right.len;
    Pro_TermData_String ans;
    
    if (len > 0) {
      if (left.len == 0) {
        ans = right;
      } else if (right.len == 0) {
        ans = left;
      } else {
        ans = new Pro_TermData_String_concat();
        ans.init(len);
        ((Pro_TermData_String_concat)ans).left = left;
        ((Pro_TermData_String_concat)ans).right = right;
      }
    } else {
      ans = Pro_TermData_String_simple.empty;
    }
    
    return ans;
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
    return this.substring(0, len);
  }
  
  public String substring(long req_start, long req_len)
  {   
    long start = req_start;
    long len = req_len;
    StringBuilder buffer;
    
    if (start < 0) {
      len = len + start;
      start = 0;
    }
    // viimeinen indeksi this.start + this.len
    if (start > this.len) len = 0;
    if (start + len > this.len) 
        len = this.len - start;
    if(len > 0) {
      if (start + len < left.len) {
        return left.substring(start, len);
      } else if(start >= left.len) {
        return right.substring(start - left.len , len);
      } else {
        buffer = new StringBuilder((int)len);
        left.appendSubstring(buffer, start, left.len - start);
        right.appendSubstring(buffer, 0, len - left.len + start);
        return buffer.toString();
      }
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
    if (start > this.len) len = 0;
    if (start + len > this.len) 
        len = this.len - start;
    if(len > 0) {
      if (start + len < left.len) {
        left.appendSubstring(buffer, start, len);
      } else if(start >= left.len) {
        right.appendSubstring(buffer, start - left.len , len);
      } else {
        left.appendSubstring(buffer, start, left.len - start);
        right.appendSubstring(buffer, 0, len - left.len + start);
      }
    }
  }
      
  public String structure() {
    return "c(" + len + "," + left.structure() + "," + 
        right.structure() + ")";
  }

  protected void get_string_part(long p)
  {
    if (p < left.len) {
      left.get_string_part(p);
    } else {
      right.get_string_part(p - left.len);
    }
  }
  
} // end class Pro_TermData_String_concat


