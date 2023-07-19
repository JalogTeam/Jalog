/* Scanner.java */

package io.github.JalogTeam.parser;

public abstract class Scanner
{
  /* basic usage:
        Scanner s = new A_subclass_of_Scanner(args_if_needed);
        while (s.tokenType != Syntax.EOF) {
          String line = next_input_line_or_null_if_no_more_lines(from_source);
          s.setLine(line);
          while (s.tokenType != Syntax.EOL && s.tokenType != Syntax.EOF)
            s.advance();
            process_token_according_to(s.tokenType, other_variables);
          }
        }
     this pattern can be varied according to particular needs;
     e.g., it is possible to terminate when s.tokenType == Syntax.ERR or
     an unexpected type for current situation;
  */

  public final Syntax syntax;
  public VirtualString line = null;
  // token description
  public String tokenType = Syntax.EOL;
  public long tokenPos = 0; // position where the token starts
  public long nextPos = 1; // next position after the token
  // subclasses may define more description fields

  public abstract String getToken();
      // should return the current token or null

  public Scanner(Syntax syntax) {
    this.syntax = syntax;
  }

  public void reset() {
      // should reset the scanner to the initial state but
      // should keep the current line and scan position
  }

  public void setScannerLine(VirtualString line) {
      // should be called when tokenType == Syntax.EOL
      // call with null when no more lines
      // subclasses should override
// System.out.println("===== Scanner.setScannerLine: " + line);
    this.line = line;
    this.tokenType = Syntax.SOL;
    this.tokenPos = -1;
    this.nextPos = 0;
  }

  public void moveTo(long nextPos) {
      // set starting position for the next advance
    this.tokenType = Syntax.SOL;
    this.tokenPos = -1;
    this.nextPos = nextPos;
  }

  public abstract void advance();
      // Find the next token; description of the token is in tokenType and
      // other public variables.
      // After every setLine call, including the last one with null,
      // advance should be repeatedly called until the token type is
      // either EOL or EOF.

}
