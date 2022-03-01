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
  public static final int SIMPLE = 1;
  public static final int SUBSTRING = 2;
  public static final int CONCATENATED = 3;

  public final long len;
  public final int tag;
  
  public Pro_TermData_String base_string; // substring
  public int start; // substring
  
  public Pro_TermData_String left; // concat
  public Pro_TermData_String right; // concat
 
  Pro_TermData_String(int tag, long len) {
    this.typename = Jalog.STRING;
    this.tag = tag;
    this.len = len;
  }
  
  public abstract String toString();

  public abstract String image();
  
  public abstract String substring(long start, long len);
  
  protected abstract void appendSubstring(StringBuilder buffer, long start, 
      long len);
      
  public abstract String structure();
  
  public static int compare_strings(Pro_TermData_String s1, 
      Pro_TermData_String s2) 
  {
    long p = 0;
    int result = 0;
    String string1_found;
    long start_pos1, len1, wlen;
    int i;
    long max_len;
// System.out.println("____________");    
// System.out.println("CS:  s1.structure=" + s1.structure());
// System.out.println("CS:  s2.structure=" + s2.structure());

/*
    if (s1.len == s2.len) {
*/
      wlen = (s1.len < s2.len ? s1.len : s2.len);
      while ((result == 0) && (p < wlen)) {
// System.out.println("\nCS: p = " + p);
// System.out.println("CS: to get_string_part, p = " + p + ", s1 = \"" + s1 + "\"");
        get_string_part(p, s1);
// System.out.println("CS: from get_string_part, string_found = \"" + string_found + "\", start_pos = " + start_pos + ", len = " + len);
        string1_found = string_found;
        start_pos1 = start_pos;
        len1 = part_len;
// System.out.println("\nCS: to get_string_part, p = " + p + ", s2 = \"" + s2 + "\"");
        get_string_part(p, s2);
// System.out.println("CS: from get_string_part, string_found = \"" + string_found + "\", start_pos = " + start_pos + ", len = " + len);
        if (part_len > len1) {
          part_len = len1;
        }
        for ( i = 0; (i < part_len) && (result == 0); i++) {
          result = (int)(string1_found.charAt((int)start_pos1 + i)) - 
                   (int)(string_found.charAt((int)start_pos + i));
        }                
        p = p + part_len;
// System.out.println("CS: result = " + result + ", p = " + p);
      }
      if ((result == 0) && (s1.len != s2.len)) {
        result = (s1.len < s2.len ? -1 : 1);
      }        
/*
    } else {
      result =  false;
    }
*/
// System.out.println("CS: result = " + result);    
// System.out.println("____________");    
    return result;      
  }

  private static String string_found;
  private static long start_pos, part_len;
private static String indent = "";

  private static void get_string_part(long p, Pro_TermData_String s)
  {
    Pro_TermData_String_substring ss;
    Pro_TermData_String_concat cs;
// System.out.println(indent + "_____");
// System.out.println(indent + "GSP: p = " + p + ", s = " + s.structure());    
indent += "  ";

    switch (s.tag) {
      case Pro_TermData_String.SIMPLE : {
        string_found = ((Pro_TermData_String_simple)s).value;
        start_pos = p;
        part_len = s.len - start_pos;
// System.out.println(indent + "GSP/SIMPLE: string_found = \"" + string_found + "\", start_pos = " + start_pos + ", len = " + len);
      }; break;
      case Pro_TermData_String.SUBSTRING : {
// System.out.println(indent + "GSP/SUBSTRING: ");
        ss = (Pro_TermData_String_substring)s;
// System.out.println(indent + "GSP/SUBSTRING ss: base_string = \"" + ss.base_string + "\", start = " + ss.start + ", len = " + ss.len);
        get_string_part(p + ss.start, ss.base_string);
// System.out.println(indent + "GSP/SUBSTRING: string_found = \"" + string_found + "\", start_pos = " + start_pos + ", len = " + len);
//        if (len > ss.len - start_pos) len = s.len/* - start_pos*/;
        if (part_len > ss.len - p) part_len = s.len - p;
// System.out.println(indent + "GSP/SUBSTRING: len = " + len);
        
      }; break;
      case Pro_TermData_String.CONCATENATED : {
        cs = (Pro_TermData_String_concat)s;
        if (p < cs.left.len) {
// System.out.println(indent + "GSP/CONCATENATED left: ");
          get_string_part(p, cs.left);
        } else {
// System.out.println(indent + "GSP/CONCATENATED right: ");
          get_string_part(p - cs.left.len, cs.right);
        }
      }; break;
    }

// System.out.println(indent + "GSP: string_found=|" + string_found + "|," +
// "start_pos=" + start_pos + ",len=" + len);
indent = indent.substring(2);
// System.out.println(indent + "_____");

  }

} // end class Pro_TermData_String


