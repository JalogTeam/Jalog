/* JalogParser.java */

public class JalogParser extends SimpleParser
{
  private String sValueP = null;

  public JalogParser(int initState)
  {
    super(JalogScanner.syntax, new JalogScanner(), initState);
  }

  public void reset(int initState) {
    super.reset(initState);
    sValueP = null;
  }

  public String sValue() {
    return sValueP;
  }

  public long iValue() {
    long ans = -1;
    if (tokenType == JalogSyntax.INT) {
      try {
        ans = - JalogSyntax.parseInt("-"+sValueP);
      } catch (Exception e) {
        ans = -1;
      }
    } else if (tokenType == JalogSyntax.CHAR) {
      ans = sValueP.codePointAt(0);
    }
    return ans;
  }

  public double rValue() {
    double ans = -1.0;
    try {
      ans = JalogSyntax.parseReal(sValueP);
    } catch (Exception e) {
      ans = -1.0;
    }
    return ans;
  }

  public void advance() {
    action = null;
    while (action == null) {
      super.advance();
      if (action == JalogSyntax.MORE_STRING) {
        sValueP += JalogSyntax.unquote(scanner.getToken());
        action = null;
      } else if (action == JalogSyntax.FUNCTOR) {
        sValueP = scanner.getToken();
        action = null;
      }
    }
    if ( action == JalogSyntax.BGN_STRING
      || action == JalogSyntax.STRING
      || action == JalogSyntax.CHAR )
    {
      sValueP = JalogSyntax.unquote(scanner.getToken());
    } else if ( action != JalogSyntax.EOL   // EOL and ERR possible
             && action != JalogSyntax.ERR   // between string parts
             && action != JalogSyntax.BGN_STRUCT // STRUCT and SYM use
             && action != JalogSyntax.SYM        // sValueP from FUNCTOR
             && action != JalogSyntax.END_STRING )
    {
      sValueP = scanner.getToken();
    }
  }

}
