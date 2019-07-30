/* SimpleSyntax.java */

package io.github.JalogTeam.parser;

public abstract class SimpleSyntax extends Syntax {

  public static final int START = 0;
  public static final int END = -1; // no next state
  public static final int NONE = -1; // no substate

  public static String ANY = NIL; // for catchall rules

  public abstract ScanRule getScanRule(int state, char c);

  public abstract ParseRule getParseRule(int state, String tokenType);

}