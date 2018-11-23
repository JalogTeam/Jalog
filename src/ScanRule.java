/* ScanRule.java */

// Rule type for scan table of SimpleScanner

public class ScanRule
{
  public final char fst;
  public final char lst;
  public final String tokenType;
  public final int nextState;

  public ScanRule(char fst, char lst, String tokenType, int nextState)
  {
    this.fst = fst;
    this.lst = lst;
    this.tokenType = tokenType;
    this.nextState = nextState;
  }

}
