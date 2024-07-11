// Pred_write.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Pred_write extends Pred
{
  public static Pred first_call(Pro_TermData_Compound data) {

    for(int i = 0; i < data.arity; i++){
//      Jalog.out.print(data.subterm[i].image());
      FileManager.write(data.subterm[i].image());
    }

    return null;
  }

}
