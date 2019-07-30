/* Syntax.java */

package io.github.JalogTeam.parser;

public abstract class Syntax {

// reserved token types
  public static final String NIL = "NIL"; // no token
  public static final String SOL = "SOL"; // start of line
  public static final String EOF = "EOF"; // no more tokens
  public static final String EOL = "EOL"; // setLine should be called
  public static final String ERR = "ERR"; // error

// reserved action types
     // reserved token types can also be used as action types
  public static final String COMPLETE = "complete";

// additional reserved action types, mainly for parser's internal use
  public static final String NONE = "none";
  public static final String USE_TOKEN = "use_token";
}