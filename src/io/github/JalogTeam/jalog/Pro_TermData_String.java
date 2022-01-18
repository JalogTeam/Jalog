/* Pro_TermData_String.java */

package io.github.JalogTeam.jalog;

public class Pro_TermData_String extends Pro_TermData
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
 
 

  Pro_TermData_String(String iniVal)
  {
    typename = Jalog.STRING;
    value = iniVal;
  }

  public virtual String toString();

  public virtual String image();
} // end class Pro_TermData_String


