/* JalogScanner.java */

package io.github.JalogTeam.jalog;

import java.util.*;

import io.github.JalogTeam.parser.*;

public class JalogScanner extends io.github.JalogTeam.parser.SimpleScanner
{

  private boolean inComment = false;

  public static JalogSyntax syntax = new JalogSyntax();
  
//  private JavaString line = new JavaString();
  private VirtualString line = null;

  public JalogScanner() {
    super(syntax);
  }

  public String getToken() {
    if (tokenType == syntax.NE) {
      return "!=";
    } else {
      return super.getToken();
    }
  }

  public void setScannerLine(VirtualString line) {
//    this.line.set(line);
/*
if(line == null) {
  System.out.println("==== JalogScanner.setLine: line = null");
} else {
  System.out.println("==== JalogScanner.setLine: line != null");
  System.out.println("==== JalogScanner.setLine: line = \"" + line + "\"");
} 
*/
    this.line = line;
    super.setScannerLine(this.line);
/*
    if (line != null) {
      super.setScannerLine(this.line);
    } else {
      super.setScannerLine(null);
    }
*/
  }
/*
  public void setLineTerm(Pro_Term line) {
    super.setLine(this);
  }
*/
  public void advance() {
    if (tokenType == EOL) return;
    tokenType = NIL;
    while (tokenType == NIL) {
      if (inComment) {
        if (line == null) {
          tokenType = ERR;
          tokenPos = 0;
          nextPos = 1;
          inComment = false;
        } else {
          long p = line.indexOf(JalogSyntax.COMMENT_END_MARK, tokenPos);
          if (p < 0) {
            // comment does not end on this line
            tokenType = EOL;
            if (tokenPos < 0) tokenPos = 0;
            nextPos = lineLen + 1;
          } else {
            // comment ends on this line
            inComment = false;
            tokenPos = p + 2;
            nextPos = tokenPos;
          }
        }
      } else {
        super.advance();
        if (tokenType == JalogSyntax.COMMENT_MARK) {
          tokenType = NIL;
          inComment = true;
        }
      }
    }
    if (tokenType == JalogSyntax.NAME) {
/*
if(line == null) {
  System.out.println("==== JalogScanner.advance: line = null");
} else {
  System.out.println("==== JalogScanner.advance: line != null");
  System.out.println("==== JalogScanner.advance: line = \"" + line + "\"");
} 
*/
      String name = line.fragment(tokenPos, nextPos - tokenPos);
      String uppername = name.toUpperCase(Locale.US);
      String keyname = uppername + "_";
      String[] keyword = JalogSyntax.keyword;
      int n = keyword.length;
      for (int i = 0; i < n; i++) {
        if (keyword[i].equals(keyname)) {
          tokenType = keyword[i];
          break;
        }
      }
      if (tokenType == JalogSyntax.NAME) {
        if (line.charAt(tokenPos) < 'a') {
          tokenType = JalogSyntax.VARIABLE;
        }
      }
    }
  }

}
