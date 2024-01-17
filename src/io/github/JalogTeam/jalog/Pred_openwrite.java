// Pred_openwrite.java
// openwrite(String symbolic_filename, String filename) - (i,i)

package io.github.JalogTeam.jalog;

public class Pred_openwrite extends Pred
{

public static boolean x = false;
  public static Pred first_call(Pro_TermData_Compound data) {
if(x)throw new Error();
    if((data.subterm[0].getType() != Typenames.OPEN) &&
        (data.subterm[1].getType() != Typenames.OPEN)) {
    
      String symbolic_filename = data.subterm[0].getData().image();
      String filename = data.subterm[1].getData().image();
    
      FileManager.openwrite(symbolic_filename, filename);
      
      if(FileManager.exit_value != 0) {
        Pred.exit_value = Pro_Term.m_integer(FileManager.exit_value);
      }
    } else {
      Pred.exit_value = Pro_Term.m_integer(1020); 
          // Free variables are not allowed here
    }
    return null;
  }
}
