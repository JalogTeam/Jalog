// Pred_readln.java
      // openread(String symbolic_filename, String filename) - (i,i)

package io.github.JalogTeam.jalog;

public class Pred_readln extends Pred {

  public static Pred first_call(Pro_TermData_Compound data) {

    String line = FileManager.readln();

    if(FileManager.exit_value != 0) {
      Pred.exit_value = Pro_Term.m_integer(FileManager.exit_value);
    } else if (line == null) {
      forward = false;
    } else {
      Pro_Term so = Pro_Term.m_string(line);
      forward = so.unify(data.subterm[0], trail);
    }
    return null;
  }
}
