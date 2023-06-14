// Pred_retractall.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Pred_retractall extends Pred
{
  // This data is used when backtracking to enable finding the next solution.
  Pro_Term param_1;
  
  public static Pred first_call(Pro_TermData_Compound params) {

    Pred state = null;

    state = Pred_retract.first_call(params);

    while(forward) {
      state.call();
    }
    forward = true;
    return null;
  }
}

    