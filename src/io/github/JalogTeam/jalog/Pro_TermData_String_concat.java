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

  public char charAt(long index) {
    char ans;
    if ((index < 0) || (index >= len)) throw new IndexOutOfBoundsException();
    if (index < left.len) {
      ans = left.charAt(index);
    } else {
      ans = right.charAt(index - left.len);
    }
    return ans;
  }

  public long indexOf(String str, long fromIndex) {
    long ans = -1;
    
    if (fromIndex < left.len) {
    
    
      ans = left.indexOf(str, fromIndex);
      
      if (ans < 0) {
        for (long i = fromIndex; (i < left.len) && (ans < 0); i++) {
          ans = i;
          for (int j = 0; (j < str.length()) && (ans == i); j++) {
            if (charAt(i + j) != str.charAt(j)) ans = -1;
          }
        }
      }
    }
    if (ans < 0) {
      ans = left.len + right.indexOf(str, fromIndex - left.len);
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
    return this.fragment(0, len);
  }
  
  public String fragment(long req_start, long req_len)
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
        return left.fragment(start, len);
      } else if(start >= left.len) {
        return right.fragment(start - left.len , len);
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


