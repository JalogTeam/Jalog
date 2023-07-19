/* Pro_TermData_Real.java */

package io.github.JalogTeam.jalog;

public class Pro_TermData_Real extends Pro_TermData
{
  public double value;
  
  public Pro_TermData_Real(double iniVal)
  {
    typename = Typenames.REAL;
    value = iniVal;
  }
  public String toString()
  {
    return "" + value;
  }
} // end class Pro_TermData_Real

