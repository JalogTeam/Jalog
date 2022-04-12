// Pred_assertz.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Pred_assertz extends Pred
{

  public static Pred first_call(Pro_TermData_Compound data) {

    Pro_Term term0 = data.subterm[0];

    if(term0.getData() instanceof Pro_TermData_Compound) {
      Database.assertz(term0);
    } else {
      Pred.forward = false;
    }

    return null;
  }

}
