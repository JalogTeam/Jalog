// Copy_Test.java
import java.io.*;

public class Copy_Test
{
  static void run(String FileName)
  {
    Parser Pr1 = new Parser();
    Pro_Term T;
    Pro_Term[] Apu = new Pro_Term[10];
    int ApuCnt = 0;
    String line;
    RandomAccessFile file1;

    try {
    
      System.out.println("Consulting " + FileName);
      file1 = new RandomAccessFile(FileName,"r");
      
      do {
        line = file1.readLine();
        if (line != null) {
          System.out.println("");
          System.out.println("Line: " + line);
          Pr1.SetString(line);
          do
          {
            System.out.println("   ---");
            T = Pr1.NextClause();
            Pro_Term T_copy = T.copy();
            System.out.println("   Term: " + T);
            System.out.println("   Copy: " + T_copy);
            System.out.println("");
//            process_clause(T);
            if (ApuCnt < 9) {
              Apu[ApuCnt] = T;
              ApuCnt++;
            }
          } while(T != null);
        }
      } while (line != null);
    
/*    
    Parser Pr1 = new Parser();
    Pro_Term T;
    System.out.println("Consulting " + FileName);
    Pr1.SetString(S);
    do
    {
      T = Pr1.NextTerm();
      System.out.println("Term: " + T);
    } while(T != null);
*/
    } catch (Exception e) {
			System.out.println(e);
		}
    int I;
    for (I = 0; I < ApuCnt; I++) {
      System.out.println("Term[" + I + "]:" + Apu[I]);
    }
  }

  private static void process_clause(Pro_Term T){
    if(T != null && T.data instanceof Pro_TermData_Compound){
      Pro_TermData_Compound data = (Pro_TermData_Compound) T.data;
      
      if(data.name.equals(":-") && (data.arity == 2)) { // is clause
        if((data.subterm[0] == null) && (data.subterm[1] != null)){
          // Directive to be executed immediately
          // :- <list of predicate calls>.
          Pro_Trail trail = new Pro_Trail();
          Inference I = new Inference(trail);

          I.success = true;
          I.run_body(data.subterm[1]);
          if(I.success){
            System.out.println("*Yes*");
          } else {
            System.out.println("*No*");
          }

        }
      
      } 

    }
  }
  public static void main(String args[])
  {
    run("testi3.pro");
  }

}
