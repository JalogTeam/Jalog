/* SimpleParser.java */

package io.github.JalogTeam.parser;

/* This parser assumes that all tokens are fully contained on a single line.
   Whitespace (comments) may span several lines.
*/

import java.util.Stack;

public abstract class SimpleParser extends Parser
{
  // line being parsed
  public String line = null; // line to be parsed
  public long lineLen = 0; // length of line

  // syntax error description
  public long errPos = -1;

  // last token description
  public String tokenType;
  public long tokenPos = 0; // position where the token starts
  public long nextPos = 0; // next position after the end of the token
  // subclasses may define more description fields

  // scanner
  protected Scanner scanner;

  // syntax
  protected SimpleSyntax syntax;

  // current parse rule
  private ParseRule rule = null;

  // parser state
  private int state;

  // parse stack
  Stack<ParseRule> stack = new Stack<ParseRule>();
  public int level = 0;

  protected SimpleParser(SimpleSyntax syntax, Scanner scanner, int initState)
  {
    super();
    line = null;
    lineLen = 0;
    this.syntax = syntax;
    this.scanner = scanner;
    tokenType = Syntax.EOL;
    tokenPos = 0;
    nextPos = 1;
    state = initState;
  }

  public void reset(int initState) {
    scanner.reset();
    scanner.moveTo(nextPos);
    errPos = -1;
    tokenType = Syntax.SOL;
    action = Syntax.SOL;
    stack.clear();
    level = 0;
    rule = null;
    state = initState;
  }

  public void setLine(String line) {
// System.out.println("===== SimpleParser.setLine: " + line);
    scanner.setScannerLine(JavaString.make(line));
    errPos = -1;
    tokenType = scanner.tokenType;
    tokenPos = scanner.tokenPos;
    nextPos = scanner.nextPos;
    action = tokenType;
  }

  public void moveTo(long nextPos) {
    scanner.moveTo(nextPos);
    errPos = -1;
    tokenType = Syntax.SOL;
    tokenPos = 0;
    this.nextPos = nextPos;
    action = Syntax.SOL;
  }

  public void advance()
  {
    action = Syntax.NIL;

    while (action == Syntax.NIL) {

      if (tokenType != Syntax.NIL) {
        scanner.advance();
        tokenType = Syntax.NIL;
      }

      if (rule == null) {

        if (state < 0) {
          if (!stack.isEmpty()) {
            rule = stack.pop();
            action = rule.postAction;
            state = rule.nextState;
            rule = null;
          } else {
            action = Syntax.COMPLETE;
            if (scanner.tokenType == Syntax.EOF) {
              tokenType = Syntax.EOF;
            }
          }
          level = stack.size();
        } else { // state > 0
          if (scanner.tokenType == Syntax.EOL) {
            tokenType = Syntax.EOL;
            action = Syntax.EOL;
          } else if (scanner.tokenType == Syntax.ERR) {
            errPos = scanner.tokenPos;
            tokenType = Syntax.ERR;
            action = Syntax.ERR;
          } else {
            rule = syntax.getParseRule(state, scanner.tokenType);
            if (rule != null) { // rule found
              if (errPos == scanner.tokenPos && rule.tokenType == Syntax.NIL) {
                // error recovery, reject catch-all rule
                rule = null;
                state = SimpleSyntax.END;
              } else if (rule.preAction == Syntax.USE_TOKEN) {
                if (scanner.tokenType == Syntax.ERR) {
                  errPos = scanner.tokenPos;
                } else {
                  errPos = -1;
                }
                tokenType = scanner.tokenType;
                action = tokenType;
              } else {
                errPos = -1;
                tokenType = rule.tokenType;
                action = rule.preAction;
              }
            } else { // no rule found
              if (errPos < 0) {
                action = Syntax.ERR;
              }
              errPos = scanner.tokenPos;
              tokenType = scanner.tokenType;
              if (tokenType == Syntax.EOF) { // unexpected EOF
                state = SimpleSyntax.END;
                action = Syntax.ERR;
              } else {
                for (int k = stack.size(); k-- > 0 && rule == null; ) {
                  rule = stack.get(k);
                  rule = syntax.getParseRule(rule.nextState, scanner.tokenType);
                  if (rule != null && rule.tokenType == Syntax.NIL) {
                    rule = null;
                  }
                }
                if (rule != null) { // assume missing token
                  tokenType = Syntax.NIL;
                  rule = null;
                  state = SimpleSyntax.END;
                }
              }
            }
          }
        }

      } else { // preaction processed

        if (rule.subState > 0) {
          stack.push(rule);
          level = stack.size();
          action = Syntax.NIL;
          state = rule.subState;
        } else {
          action = rule.postAction;
          state = rule.nextState;
        }
        rule = null;

      }

    } // end loop

    tokenPos = scanner.tokenPos;
    nextPos = (tokenType != Syntax.NIL ? scanner.nextPos : tokenPos);

  }

}
