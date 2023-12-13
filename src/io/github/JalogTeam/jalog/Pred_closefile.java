// Pred_closefile.java
      // closefile(String symbolic_filename) - (i)

package io.github.JalogTeam.jalog;

public class Pred_closefile extends Pred
{
  public static Pred first_call(Pro_TermData_Compound data) {
    String symbolic_filename;
    Pro_Term symbolic_filename_term;
    
    if(data.subterm[0].getType() != Typenames.OPEN) {
      
      symbolic_filename = data.subterm[0].getData().image();
// System.out.println("*** Pred_closefile 1, symbolic_filename = \"" + symbolic_filename + "\"");    
      FileManager.closefile(symbolic_filename);
      
    }
    return null;
  }
}
