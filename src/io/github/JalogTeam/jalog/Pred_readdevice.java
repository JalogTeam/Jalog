// Pred_readdevice.java
      // readdevice(String symbolic_filename) - (i), (0)

package io.github.JalogTeam.jalog;

public class Pred_readdevice extends Pred
{
  static Pro_Term[] empty = {};
  public static Pred first_call(Pro_TermData_Compound data) {
    String symbolic_filename;
    FileManager.FileInfo current_readdevice;
    
    if(data.subterm[0].getType() != Typenames.OPEN) {
      
      symbolic_filename = data.subterm[0].getData().image();
    
      FileManager.readdevice(symbolic_filename);
      
      if(FileManager.exit_value != 0) {
        Pred.exit_value = Pro_Term.m_integer(FileManager.exit_value);
      }
    } else {
      if (FileManager.current_readdevice != null) {
        
//        Pro_Term.m_compound(FileManager.current_readdevice, empty);

//        forward = frontchar.unify(symbolic_filename_param, trail);        
          // Free variables are not allowed here
      }
    }
    return null;
  }
}
