/* Test_Scanner.java */

import java.io.*;

public class Test_Scanner {

  private static final String initInfo = "[0 .. 0] EOL ||";
  private static final String solInfo = "[-1 .. -1] SOL ||";
  private static final String eolInfo = "] EOL |";

  private static JalogScanner scanner;

  private static String line = null;
  private static int lineCnt = 0;
  private static int mainLno = 0;

  private static int checkCnt = 0;
  private static int errorCnt = 0;
  private static int errorLno = -1;

  private static void check (String expInfo) {
    checkCnt++;
    String token = scanner.getToken();
    String info = "["+scanner.tokenPos+" .. "+(scanner.nextPos-1)+"] "
          +scanner.tokenType+(token != null ? " |"+token+"|" : " null");
    int p;
    if (expInfo.endsWith("\"|")) {
      p = expInfo.lastIndexOf(" |\"");
    } else if (expInfo.endsWith("'|")) {
      p = expInfo.lastIndexOf(" |'");
    } else if (expInfo.endsWith("|")) {
      p = expInfo.lastIndexOf(" |", expInfo.length() - 1);
    } else {
      p = expInfo.length() - 1;
    }
    p = expInfo.lastIndexOf("] ", p);
    if (p > 0) p = expInfo.lastIndexOf(" [", p);
    if (info.indexOf(expInfo.substring(p+1)) < 0) {
      if (mainLno != errorLno) {
        System.out.println("<*><*><*> error <*><*><*>");
      }
      System.out.println("      found:");
      System.out.println(info);
      System.out.println("      expected:");
      System.out.println(expInfo);
      System.out.println("=========");
      errorCnt++;
      errorLno = mainLno;
    }
  }

  private static void reset(String arg) {
    boolean error = false;
    int p = arg.indexOf('[');
    if (p != 0) {
      scanner.reset();
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
        scanner.moveTo(newPos);
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
      scanner = new JalogScanner();
      check(initInfo);
      scanner.advance();
      check(initInfo);
      scanner.advance();
      check(initInfo);
      System.out.println("0 SOF");
      int lno = 0;
      mainLno = 0;
      while (testScript != null) {
        lno++;
        String cmd = testScript.readLine();
        if (cmd == null) {
          System.out.println("<*><*><*> Unexpected end of input");
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
                    && scanner.tokenType != JalogSyntax.EOL; i++)
              {
                scanner.advance();
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
            scanner.setLine(line);
            check(solInfo);
          } break;
          case '-':
          case '=': {
            if (key == '-' || arg.indexOf(" NIL ") < 0) {
              scanner.advance();
              check(arg.trim());
            }
          } break;
          case '.': {
            System.out.println("-- Closing script "+folderName+scriptName
                    +" --");
            testScript.close();
            testScript = null;
          } break;
          case '@': {
            JalogScanner oldScanner = scanner;
            run(folderName, arg.trim());
            scanner = oldScanner;
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