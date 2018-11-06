/* SimpleScanner.java */

/* This scanner assumes that all tokens are fully contained on a single line.
   Whitespace (comments) may span several lines.
*/

public abstract class SimpleScanner extends Scanner
{
  public final SimpleSyntax syntax;

  // special states
  public static final int START = SimpleSyntax.START;
  public static final int END = SimpleSyntax.END;

  // special tokens
  public static final String NIL = SimpleSyntax.NIL;
  public static final String ERR = SimpleSyntax.ERR;
  public static final String SOL = SimpleSyntax.SOL;
  public static final String EOL = SimpleSyntax.EOL;
  public static final String EOF = SimpleSyntax.EOF;

  // line being scanned
  public String line = ""; // line to be scanned
  public int lineLen = 0; // length of line

  protected SimpleScanner(SimpleSyntax syntax) {
    super(syntax);
    this.syntax = syntax;
    line = "";
    lineLen = 0;
  }

  public String getToken() {
    if (line != null) {
      int fst = tokenPos;
      if (tokenPos < 0) fst = 0;
      int lst = nextPos;
      if (lst > lineLen) lst = lineLen;
      if (fst <= lst) {
        return line.substring(fst, lst);
      }
    }
    return "";
  }

  public void setLine(String line) {
    super.setLine(line);
    this.line = line;
    lineLen = (line != null ? line.length() : 0);
  }

  public void advance()
  {
    if (line == null) {
      tokenPos = 0;
      nextPos = 1;
      tokenType = EOF;
      return;
    }
    int scanPos = nextPos;
    tokenType = ERR;
    tokenPos = scanPos;
    int state = START;
    while (scanPos < lineLen && state != END) {
      char c = line.charAt(scanPos);
      scanPos++;
      ScanRule rule = syntax.getScanRule(state, c);
      if (rule != null) { // rule found
        String newType = rule.tokenType;
        if (newType != ERR) {
          tokenType = newType;
          nextPos = scanPos;
        }
        state = rule.nextState;
      } else { // no rule found
        state = END;
        scanPos--;
      }
      if (tokenType == NIL && state == END) {
        // found whitespace, try again
        tokenType = ERR;
        tokenPos = scanPos;
        nextPos = scanPos;
        state = START;
      }
    } // while (scanPos < lineLen && state != END)
    if (tokenPos >= lineLen) { // at the end of the line
      tokenType = EOL;
      tokenPos = lineLen;
    } else if (scanPos == tokenPos) { // unexpected start of token
      nextPos = tokenPos + 1;
      tokenType = ERR;
    } else if (tokenType == ERR) {
      nextPos = scanPos;
    }
    if (tokenType == EOL) {
      nextPos = lineLen + 1;
    }
  }

}
