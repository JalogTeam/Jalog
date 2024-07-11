// Pred_deletefile.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Pred_deletefile extends Pred
{
  public static Pred first_call(Pro_TermData_Compound data) {
    int errorcode = 0;

    Pro_Term dosFileName = data.subterm[0];
    String paramtype = dosFileName.getType();
    if (paramtype == Typenames.STRING) {
      FileManager.deletefile(dosFileName.image());
      if ( FileManager.exit_value != 0 ) {
        Pred.exit_value = Pro_Term.m_integer(FileManager.exit_value);
      }
    } else {
      if (paramtype == Typenames.OPEN) {
        errorcode = 1020; // Free variables are not allowed here
      } else {
        errorcode = 2200; // Type error
      }
      Pred.exit_value = Pro_Term.m_integer(errorcode);
    }
    return null;
  }
}
      
