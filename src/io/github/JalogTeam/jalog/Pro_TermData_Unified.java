/* Pro_TermData_Unified.java */

package io.github.JalogTeam.jalog;

public class Pro_TermData_Unified extends Pro_TermData
{
  public Pro_Term pValue;

  public Pro_TermData_Unified(Pro_Term target)
  {
    pValue = target;
    typename = null;
  }
  
  public String toString()
  {
      return pValue.toString();
  }
  
  public String image()
  {
      return pValue.image();
  }

  
} // end class Pro_TermData_Unified

