// Pred_writeln.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Pred_writeln extends Pred
{
  public static Pred first_call(Pro_TermData_Compound data) {

    for(int i = 0; i < data.arity; i++){
      Jalog.out.print(data.subterm[i].image());
    }
    Jalog.out.println("");

    return null;
  }

}
