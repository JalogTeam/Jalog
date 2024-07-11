// Pred_file_str.java
      // file_str(String Filename, String StringVariable) - (i,i),(i,o)

package io.github.JalogTeam.jalog;

public class Pred_file_str extends Pred
{
  private static Pro_TermData_String nl = 
      Pro_TermData_String_simple.make("\n");
  
  public static Pred first_call(Pro_TermData_Compound data) {
// System.out.println("Pred_file_str.first_call 1");
// if(x)throw new Error();
// System.out.println("Pred_file_str.first_call 2");
    if(data.subterm[0].getType() == Typenames.STRING){
      String filename = data.subterm[0].getData().image();
      String symbolic_filename = "**1";
      
      FileManager.openread(symbolic_filename, filename);
      if(FileManager.exit_value != 0) {
        Pred.exit_value = Pro_Term.m_integer(FileManager.exit_value);
      } else { // Open OK
        String olddevice = FileManager.get_readdevice();
        String line;
        FileManager.set_readdevice(symbolic_filename);
        if(FileManager.exit_value != 0) {
          Pred.exit_value = Pro_Term.m_integer(FileManager.exit_value);
        } else { // set_readdevice OK
          Pro_TermData_String content = Pro_TermData_String_simple.make("");
          line = FileManager.readln();
          while(line != null) {
            content = Pro_TermData_String_concat.make(content, 
                Pro_TermData_String_concat.make(
                Pro_TermData_String_simple.make(line), nl));
            line = FileManager.readln();
          }

          Pro_Term so = new Pro_Term(content);
          forward = so.unify(data.subterm[1], trail);
        }
        FileManager.set_readdevice(olddevice);
      }
    } else {
      Pred.exit_value = Pro_Term.m_integer(2200); // Type error
    }
    return null;
  }
}
