public class Pro_TermData_Char extends Pro_TermData
{
  public char value;
  
  Pro_TermData_Char(char iniVal)
  {
    typename = Jalog.CHARACTER;
    value = iniVal;
  }
  
  public String toString()
  {
    return "'" + value + "'";
  }

  public String image()
  {
    return String.valueOf(value);
  }
} // end class Pro_TermData_Char

