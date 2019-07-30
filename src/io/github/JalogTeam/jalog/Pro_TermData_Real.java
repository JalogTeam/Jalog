/* Pro_TermData_Real.java */

package io.github.JalogTeam.jalog;

public class Pro_TermData_Real extends Pro_TermData
{
  public double value;
  
  Pro_TermData_Real(double iniVal)
  {
    typename = Jalog.REAL;
    value = iniVal;
  }
  public String toString()
  {
    return "" + value;
  }
} // end class Pro_TermData_Real

