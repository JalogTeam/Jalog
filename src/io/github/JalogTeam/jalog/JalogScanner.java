/* JalogScanner.java */

package io.github.JalogTeam.jalog;

import java.util.*;

public class JalogScanner extends io.github.JalogTeam.parser.SimpleScanner
{

  private boolean inComment = false;

  public static JalogSyntax syntax = new JalogSyntax();

  JalogScanner() {
    super(syntax);
  }

  public String getToken() {
    if (tokenType == syntax.NE) {
      return "!=";
    } else {
      return super.getToken();
    }
  }

  public void setLine(String line) {
    super.setLine(line);
  }

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
          int p = line.indexOf(JalogSyntax.COMMENT_END_MARK, tokenPos);
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
      String name = line.substring(tokenPos, nextPos);
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
