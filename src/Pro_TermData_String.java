public class Pro_TermData_String extends Pro_TermData
{
  public String value;

  Pro_TermData_String(String iniVal)
  {
    typename = Jalog.STRING;
    value = iniVal;
  }

  public String toString()
  {
    String temp = new String(value);
    temp = temp.replaceAll("\\\\","\\\\\\\\");
    temp = temp.replaceAll("\\\"","\\\\\\\"");
    return "\"" + temp + "\"";
  }

  public String image()
  {
    return value;
  }
} // end class Pro_TermData_String


