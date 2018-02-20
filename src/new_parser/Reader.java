// Reader.java
import java.io.*;

public class Reader
{
  static Pro_Term exit_value = null;

  static void run(String FileName)
  {
    exit_value = null;
    Parser Pr1 = new Parser(Parser.CLAUSE);
    Pro_Term T;
    Pro_Term[] Apu = new Pro_Term[10];
    int ApuCnt = 0;
    String line;
    RandomAccessFile file1;
    int LineNmbr = 0;

    /*try*/ {
    
//      System.out.println("Consulting " + FileName);
      try {
        file1 = new RandomAccessFile(FileName,"r");
      } catch (Exception e) {
        System.out.println("*** Error: " + e);
        file1 = null;
        exit_value = Pro_Term.m_integer(1); // File not found
      }
      if(file1 != null) {
        do {
          try {
            line = file1.readLine();
            LineNmbr = LineNmbr + 1;
          } catch (Exception e) {
            System.out.println("*** Error: " + e);
            line = null;
          }
          if (line != null) {
  // System.out.println("");
  // System.out.println("Line: " + line);
            Pr1.SetString(line);
          } else {
            Pr1.SetEOF();
  // System.out.println("Reader:EOF");
          }
          do
          {
  // System.out.println("   ---");
            T = Pr1.NextPart();
            if(Pr1.Error != 0)
            {
              exit_value = Pro_Term.m_integer(1); // Syntax error
              System.err.println("*** Error in file " + FileName + " Line: " + LineNmbr + " Pos: " + Pr1.ErrorPos);
              System.err.println("    " + line);
              for(int i = -3; i < Pr1.ErrorPos; i++) {
                System.err.print(" ");
              }
              System.err.println("^");

              T = null;
              line = null;
            } else {
//System.out.println("   Term: " + T);
//System.out.println("");
              process_clause(T);
              if(exit_value != null) {
                T = null;
                line = null;
              }
            }
            if (ApuCnt < 9) {
              Apu[ApuCnt] = T;
              ApuCnt++;
            }
          } while(T != null);
        } while (line != null);
      }
//System.out.println("Consulted");
    
/*    } catch (Exception e) {
      System.out.println(e); */
    }
/*
    int I;
    for (I = 0; I < ApuCnt; I++) {
      System.out.println("Term[" + I + "]:" + Apu[I]);
    }
*/
  }

  private static void process_clause(Pro_Term T){
    
System.out.println("\n--Reader: process_clause:" + T );
  }
}
