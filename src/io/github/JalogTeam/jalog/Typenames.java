// Typenames.java

package io.github.JalogTeam.jalog;

public class Typenames
{
  public static final String OPEN = "open";
  public static final String INTEGER = "integer";
  public static final String SYMBOL = "symbol";
  public static final String REAL = "real";
  public static final String CHAR = "character";
  public static final String CHARACTER = CHAR;
  public static final String STRING = "string";
  public static final String LIST = "list";
  public static final String COMPOUND = "compound";
  
  public static char identifyOperator(String name, int arity) {
    char Op = ' ';

    if (arity == 1) {
      if(name.equals("-")) { Op = '-'; }
      else if(name.equals("+")) { Op = '+'; }
    } else if (arity == 2) {
      if(name.equals("+")) { Op = '+'; }
      else if(name.equals("-")) { Op = '-'; }
      else if(name.equals("*")) { Op = '*'; }
      else if(name.equals("/")) { Op = '/'; }
      else if(name.equals("mod_")) { Op = 'm'; }
      else if(name.equals("div_")) { Op = 'd'; }
    }

    return Op;
  }
}
