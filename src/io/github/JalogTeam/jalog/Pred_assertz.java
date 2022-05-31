// Pred_assertz.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Pred_assertz extends Pred
{

  public static Pred first_call(Pro_TermData_Compound data) {

    Pro_Term term0 = data.subterm[0];

    Pro_TermData data0 = term0.getData();
    if((data0 instanceof Pro_TermData_Compound) && !((Pro_TermData_Compound)data0).name.equals(":-")) {
      Database.assertz(term0);
    } else {
      Pred.forward = false;
    }

    return null;
  }

}
