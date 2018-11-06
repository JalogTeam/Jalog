public class Pro_TermData_Char extends Pro_TermData
{
  public char value;
  
  Pro_TermData_Char(char iniVal)
  {
    value = iniVal;
  }
  
  public String toString()
  {
    return "'" + value + "'";
  }
} // end class Pro_TermData_Char

