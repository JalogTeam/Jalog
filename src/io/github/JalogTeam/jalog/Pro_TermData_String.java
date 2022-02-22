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
  
} // end class Pro_TermData_String


