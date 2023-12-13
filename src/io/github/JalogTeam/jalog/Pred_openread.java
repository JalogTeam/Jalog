// Pred_openread.java
      // openread(String symbolic_filename, String filename) - (i,i)

package io.github.JalogTeam.jalog;

public class Pred_openread extends Pred
{

public static boolean x = true;

  public static Pred first_call(Pro_TermData_Compound data) {
// System.out.println("Pred_openread.first_call 1");
// if(x)throw new Error();
// System.out.println("Pred_openread.first_call 2");
    if((data.subterm[0].getType() != Typenames.OPEN) &&
        (data.subterm[1].getType() != Typenames.OPEN)) {
// System.out.println("Pred_openread.first_call 3");
    
      String symbolic_filename = data.subterm[0].getData().image();
      String filename = data.subterm[1].getData().image();
    
      FileManager.openread(symbolic_filename, filename);
      
      if(FileManager.exit_value != 0) {
        Pred.exit_value = Pro_Term.m_integer(FileManager.exit_value);
      }
    } else {
      Pred.exit_value = Pro_Term.m_integer(1020); 
          // Free variables are not allowed here
    }
// System.out.println("Pred_openread.first_call: FileManager.open_files=" + FileManager.open_files); 
// System.out.println("Pred_openread.first_call returning");
    return null;
  }
}
