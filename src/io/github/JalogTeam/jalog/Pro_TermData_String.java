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

  public int len;
  public int tag;
  
  public Pro_TermData_String base_string; // substring
  public int start; // substring
  
  public Pro_TermData_String left; // concat
  public Pro_TermData_String right; // concat
 
  public abstract String toString();

  public abstract String image();
  
  public abstract String substring(int start, int len);
  
  protected abstract void appendSubstring(StringBuilder buffer, int start, 
      int len);
} // end class Pro_TermData_String


