/* Pro_TermData_Char.java */

package io.github.JalogTeam.jalog;

public class Pro_TermData_Char extends Pro_TermData
{
  public char value;
  
  public Pro_TermData_Char(char iniVal)
  {
    typename = Jalog.CHARACTER;
    value = iniVal;
  }
  
  public String toString()
  {
    return JalogSyntax.quote((int)value, '\'');
  }

  public String image()
  {
    return String.valueOf(value);
  }
} // end class Pro_TermData_Char

