/* Test_Parser.java */

import java.io.*;

public class Test_Parser {

  private static final String initInfo = "0 !EOL [0 .. 0] EOL ||";
  private static final String solInfo = "[-1 .. -1] SOL ||";
  private static final String eolInfo = "] EOL |";

  private static JalogParser parser;

  private static String line = null;
  private static int lineCnt = 0;
  private static int mainLno = 0;

  private static int checkCnt = 0;
  private static int errorCnt = 0;
  private static int errorLno = -1;

  private static int stackCnt = 0;
  private static int[] stack = new int[100];
  private static int minLevel = 0;

  private static void check (String expInfo) {
    checkCnt++;
    int level = parser.level;
    String info = level+" !"+parser.action;
    if (parser.action == JalogSyntax.INT) {
      long iVal = parser.iValue();
      if (iVal == Long.MIN_VALUE) {
        info += " # 9223372036854775808";
      } else if (iVal < 0) {
        double rVal = parser.rValue();
        if (rVal < 0.0) {
          info += " ***";
        } else {
          info += " * "+rVal;
        }
      } else {
        info += " "+iVal;
      }
    } else if (parser.action == JalogSyntax.REAL) {
      double rVal = parser.rValue();
      if (rVal < 0.0) {
        info += " ***";
      } else {
        info += " "+rVal;
      }
    } else if (parser.action == JalogSyntax.CHAR) {
      info += " $"+Long.toHexString(parser.iValue());
    } else if ( parser.action == JalogSyntax.BGN_STRING
             || parser.action == JalogSyntax.STRING
             || parser.action == JalogSyntax.END_STRING )
    {
      info += " "+JalogSyntax.quote(parser.sValue());
    } else if ( parser.action == JalogSyntax.BGN_UNOP
             || parser.action == JalogSyntax.BGN_BINOP
             || parser.action == JalogSyntax.BGN_STRUCT
             || parser.action == JalogSyntax.SYM
             || parser.action == JalogSyntax.NAME
             || parser.action == JalogSyntax.VARIABLE )
    {
      info += " "+parser.sValue();
    } else if (parser.action == JalogSyntax.ERR) {
      info += " ["+parser.errPos+"]";
    }
    info += " ["+parser.tokenPos+" .. "+(parser.nextPos-1)+"] ";
    if (parser.tokenType == JalogSyntax.NIL) {
      info += "NIL ||";
    } else {
      String token = parser.scanner.getToken();
      info += parser.tokenType+(token != null ? " |"+token+"|" : " null");
    }
    boolean error = false;
    boolean strict = false;
    boolean pop = false;

    if (expInfo.indexOf("!end_") >= 0) {
      stackCnt--;
      if (stackCnt < 0) {
        stackCnt = 0;
        minLevel = 0;
        error = true;
      } else if (stackCnt < stack.length) {
        pop = true;
        minLevel--;
      }
      strict = true;
    }

    if (expInfo.indexOf("!complete ") >= 0) {
      if (stackCnt !=  0 || minLevel != 0) {
        error = true;
      }
      stackCnt = 0;
      minLevel = 0;
      strict = true;
    }

    if ((strict && level != minLevel) || level < minLevel) {
      error = true;
    }

    if (info == null || info.indexOf(expInfo) < 0) {
      error = true;
    }

    if (error) {
      if (mainLno != errorLno) {
        System.out.println("  <*><*><*> error <*><*><*>");
      }
      System.out.println("      found:");
      System.out.println(info);
      System.out.println("      expected:");
      char c = expInfo.charAt(0);
      if (c >= '0' && c <= '9') {
        System.out.println(expInfo);
      } else if (strict) {
        System.out.println(minLevel+" "+expInfo);
      } else {
        System.out.println(minLevel+"+ "+expInfo);
      }
      System.out.println("=========");
      errorCnt++;
      errorLno = mainLno;
    }

    if (pop) {
      minLevel = stack[stackCnt];
    } else if (expInfo.indexOf("!bgn_") >= 0) {
      if (stackCnt < stack.length) {
        stack[stackCnt] = minLevel;
        minLevel = level + 1;
      }
      stackCnt++;
    }
  }

  private static void reset(String arg) {
    boolean error = false;
    int p = arg.indexOf('[');
    if (p != 0) {
      String w = (p < 0 ? arg : arg.substring(0, p).trim());
      int initState = ( w.equals("SPACE") ? JalogSyntax.SPACE
                      : w.equals("CLAUSES") ? JalogSyntax.CLAUSES
                      : w.equals("CLAUSE") ? JalogSyntax.CLAUSE
                      : w.equals("FACTS") ? JalogSyntax.FACTS
                      : w.equals("FACT") ? JalogSyntax.FACT
                      : w.equals("TERM") ? JalogSyntax.TERM
                      : w.equals("EXPR") ? JalogSyntax.EXPR
                      : w.equals("TOKENS") ? JalogSyntax.TOKENS
                      : -1 );
      if (initState < 0) {
        error = true;
      } else {
        parser.reset(initState);
      }
    }
    if (p >= 0) {
      int newPos = -1;
      int q = arg.indexOf(']');
      if (p < q) {
        try {
          newPos = Integer.parseInt(arg.substring(p+1, q).trim());
        } catch (Exception e) {
          error = true;
        }
      }
      if (newPos >= 0) {
        parser.moveTo(newPos);
      } else {
        error = true;
      }
    }
    if (error) {
      System.out.println("  <*><*><*> script error <*><*><*>");
      errorCnt++;
    }
  }

  public static void run (String folderName, String scriptName) {
    try {
      System.out.println("-- Opening script "+folderName+scriptName+" --");
      RandomAccessFile testScript
              = new RandomAccessFile(folderName+scriptName, "r");
      parser = new JalogParser(JalogSyntax.CLAUSES);
      check(initInfo);
      parser.advance();
      check(initInfo);
      parser.advance();
      check(initInfo);
      System.out.println("0 SOF");
      int lno = 0;
      mainLno = 0;
      while (testScript != null) {
        lno++;
        String cmd = testScript.readLine();
        if (cmd == null) {
          System.out.println("  <*><*><*> Unexpected end of input");
          errorCnt++;
          cmd = ".";
        }
        int h = 0;
        for (int i = 0; i < cmd.length() && cmd.charAt(i) != '|'; i++) {
          int c = cmd.codePointAt(i);
          if (c < ' ' || c > '~') {
            if (h == 0) {
              System.out.println("  <*><*><*> Invalid character <*><*><*>");
              System.out.printf("%d ", lno);
            }
            if (c < 0x10000) {
              System.out.printf("%s\\u%04X",cmd.substring(h,i),c);
            } else {
              System.out.printf("%s\\U%08X",cmd.substring(h,i),c);
              i++;
            }
            h = i + 1;
          }
        }
        if (h > 0) {
          System.out.println(cmd.substring(h));
          errorCnt++;
        }
        char key = (cmd.length() > 0 ? cmd.charAt(0) : ' ');
        String arg = (cmd.length() > 2 ? cmd.substring(2) : "");
        if (key == '+') {
          key = '|';
          arg = null;
        }
        switch (key) {
          case '*': {
            reset(arg.trim());
          } break;
            case '|': {
            if (line != null) {
              for ( int i = 0; i < line.length() 
                    && parser.action != JalogSyntax.EOL; i++)
              {
                parser.advance();
                check(eolInfo);
              }
            }
            mainLno = lno;
            line = arg;
            lineCnt++;
            if (line != null) {
              System.out.println(lno+" |"+line+"|");
            } else {
              System.out.println(lno+" EOF");
            }
            parser.setLine(line);
            check(solInfo);
          } break;
          case '=': {
            parser.advance();
            check(arg.trim());
          } break;
          case '.': {
            System.out.println("-- Closing script "+folderName+scriptName
                    +" --");
            testScript.close();
            testScript = null;
          } break;
          case '@': {
            JalogParser oldParser = parser;
            run(folderName, arg.trim());
            parser = oldParser;
            line = null;
            mainLno = lno;
          }
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("          <*><*><*> File not found <*><*><*>");
      errorCnt++;
    } catch (IOException e) {
      System.out.println("          <*><*><*> File error <*><*><*>");
      errorCnt++;
    }
  }

  public static void main (String[] args) {
    if (args.length != 1) {
      System.err.println("Specify input file as command line argument");
    } else {
      System.out.println("== Starting test ==");
      String scriptName = args[0];
      String folderName;
      int p = scriptName.lastIndexOf('/') + 1;
      folderName = scriptName.substring(0, p);
      scriptName = scriptName.substring(p);
      run(folderName, scriptName);
      System.out.println("== Test completed ==");
      System.out.printf("%10d lines\n", lineCnt);
      System.out.printf("%10d checks\n", checkCnt);
      if (errorCnt > 1) {
        System.out.printf("<*> %6d errors <*><*><*><*><*><*><*>\n", errorCnt);
      } else if (errorCnt > 0) {
        System.out.printf("<*>      1 error <*><*><*><*><*><*><*>\n");
      } else {
        System.out.printf("        no errors\n");
      }
    }
  }
}