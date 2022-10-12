/* Pro_TermData_String.java */
/*
  Entäpä jos stringejä ei luotaisikaan konstruktoreilla vaan funktioilla, jotka
  antavat arvokseen Pro_TermData_String referenssin?
    - Silloin voisi ainakin triviaalitapaukset helposti saada käyttämään
      samaa dataa:
      - Kaikki tyhjät stringit jakaisivat saman paikan.
      - Entä lyhyet simple-stringit? Hashtableen tms., joka yhdistäisi tuplat?
      - Jos concat operaation toinen argumentti olisi tyhjä, tuloksena olisi
        suoraan se toinen.
      - Jos substring määrittäisi koko argumenttisitringin, tuloksena olisi
        suoraan argumenttistringi.
*/
package io.github.JalogTeam.jalog;

public abstract class Pro_TermData_String extends Pro_TermData
{

  public long len;
  
/*
  protected Pro_TermData_String(long len) {
    this.typename = Jalog.STRING;
    this.len = len;
  }
*/
  public void init(long len) {
    this.typename = Jalog.STRING;
    this.len = len;
  }
  
  public String toString() {
    return JalogSyntax.quote(image(), '"');
  }
  
  public abstract String image();
  
  public abstract String substring(long start, long len);
  
  protected abstract void appendSubstring(StringBuilder buffer, long start, 
      long len);
      
  public abstract String structure(); // Debugging tool
  
  public static int compare_strings(Pro_TermData_String s1, 
      Pro_TermData_String s2) 
  {
    long p = 0;
    int result = 0;
    String string1_found;
    long start_pos1, len1, wlen;
    int i;
    long max_len;
    
    wlen = (s1.len < s2.len ? s1.len : s2.len);
    while ((result == 0) && (p < wlen)) {
      s1.get_string_part(p);
      string1_found = string_found;
      start_pos1 = start_pos;
      len1 = part_len;
      s2.get_string_part(p);
      if (part_len > len1) {
        part_len = len1;
      }
      for ( i = 0; (i < part_len) && (result == 0); i++) {
        result = (int)(string1_found.charAt((int)start_pos1 + i)) - 
                 (int)(string_found.charAt((int)start_pos + i));
      }                
      p = p + part_len;
    }
    if ((result == 0) && (s1.len != s2.len)) {
      result = (s1.len < s2.len ? -1 : 1);
    }        
    return result;      
  }

  public static boolean contains_at(Pro_TermData_String s1, long pos, 
      Pro_TermData_String s2) 
  {
    long p1, p2 = 0;
    boolean result = true;
    String string1_found;
    long start_pos1, len1;
    int i;
    long max_len;

    if ((s1.len - pos < s2.len) && (pos < 0)) {
      result = false;
    } else {
      p1 = pos;
      while (result && (p2 < s2.len)) {
        s1.get_string_part(p1);
        string1_found = string_found;
        start_pos1 = start_pos;
        len1 = part_len;
        s2.get_string_part(p2);
        if (part_len > len1) {
          part_len = len1;
        }
        for ( i = 0; (i < part_len) && result; i++) {
          result = (string1_found.charAt((int)start_pos1 + i)) == 
                   (string_found.charAt((int)start_pos + i));
        }                
        p1 = p1 + part_len;
        p2 = p2 + part_len;
      }
      if (p2 < s2.len) {
        result = false;
      }  
    }      
    return result;      
  }

// Temporary results from get_string_part
  protected static String string_found;
  protected static long start_pos, part_len; 

//protected static String indent = "";

  abstract protected void get_string_part(long p);

} // end class Pro_TermData_String


