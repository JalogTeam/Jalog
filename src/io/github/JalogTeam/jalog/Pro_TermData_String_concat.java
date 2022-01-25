/* Pro_TermData_String_concat.java */

package io.github.JalogTeam.jalog;

public class Pro_TermData_String_concat extends Pro_TermData_String
{
  
  public Pro_TermData_String left; // concat
  public Pro_TermData_String right; // concat

  Pro_TermData_String_concat(Pro_TermData_String left, 
      Pro_TermData_String right)
  {
    typename = Jalog.STRING;
    tag = CONCATENATED;
    this.left = left;
    this.right = right;
    this.len = left.len + right.len;
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
  
  public String substring(int req_start, int req_len)
  {   
    int start = req_start;
    int len = req_len;
    StringBuilder buffer;
    
    if (start < 0) {
      len = len + start;
      start = 0;
    }
    // viimeinen indeksi this.start + this.len
    if (start > this.start + this.len) len = 0;
    if (start + len > this.start + this.len) 
        len = this.start + this.len - start;
    if(len > 0) {
      if (start + len < left.len) {
        return left.substring(start, len);
      } else if(start >= left.len) {
        return right.substring(start - left.len , len);
      } else {
        buffer = new StringBuilder(len);
        left.appendSubstring(buffer, start, left.len - start);
        right.appendSubstring(buffer, 0, len - left.len + start);
        return buffer.toString();
      }
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
} // end class Pro_TermData_String_concat


