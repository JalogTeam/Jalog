/* Pro_TermData_Integer.java */

package io.github.JalogTeam.jalog;

public class Pro_TermData_Integer extends Pro_TermData
{
  public long value;
  
  public Pro_TermData_Integer(long iniVal)
  {
    typename = Jalog.INTEGER;
    value = iniVal;
  }
  public String toString()
  {
    return "" + value;
  }
} // end class Pro_TermData_Integer

