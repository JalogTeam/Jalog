// Pred_readdevice.java
      // readdevice(String symbolic_filename) - (i), (0)

package io.github.JalogTeam.jalog;

public class Pred_readdevice extends Pred {
  
  static Pro_Term[] empty = {};

  public static Pred first_call(Pro_TermData_Compound data) {
    String symbolic_filename;
    Pro_Term symbolic_filename_term;
    
    if(data.subterm[0].getType() != Typenames.OPEN) {
      
      symbolic_filename = data.subterm[0].getData().image();
// System.out.println("*** Pred_readdevice 1, symbolic_filename = \"" + symbolic_filename + "\"");    
      FileManager.set_readdevice(symbolic_filename);
      
      if(FileManager.exit_value != 0) {
        Pred.exit_value = Pro_Term.m_integer(FileManager.exit_value);
      }
    } else {
      
      symbolic_filename = FileManager.get_readdevice();
// System.out.println("*** Pred_readdevice 2, symbolic_filename = \"" + symbolic_filename + "\"");    
      symbolic_filename_term = Pro_Term.m_compound(symbolic_filename, empty);
      forward = data.subterm[0].unify(symbolic_filename_term, trail); 
    }
    return null;
  }
}
