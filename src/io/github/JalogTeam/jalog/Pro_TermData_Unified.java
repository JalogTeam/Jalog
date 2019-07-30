/* Pro_TermData_Unified.java */

package io.github.JalogTeam.jalog;

public class Pro_TermData_Unified extends Pro_TermData
{
  public Pro_Term pValue;

  Pro_TermData_Unified(Pro_Term iniVal)
  {
    pValue = iniVal;
  }
  
  public String toString()
  {
      return "{" + pValue.toString() + "}";
  }
  
  public String image()
  {
      return pValue.image();
  }

  
} // end class Pro_TermData_Unified

