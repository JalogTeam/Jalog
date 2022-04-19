// Pred_is_integer.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Pred_is_integer extends Pred
{
  public static Pred first_call(Pro_TermData_Compound data) {

    Pro_TermData data1 = data.subterm[0].getData();

    if((data1 == null) || !(data1 instanceof Pro_TermData_Integer)) {
      Pred.forward = false;
    }

    return null;
  }
}
