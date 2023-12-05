// Pred_exit.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Pred_exit extends Pred
{
  public static Pred first_call(Pro_TermData_Compound data) {

//    Pred.exception = true;
//    Pred.exit_value = null;
    if(data.arity > 0) {
      Pred.exit_value = Pro_Term.m_integer(
          Pro_Term.eval_integer(data.subterm[0]));
    } else {
      Pred.exit_value = Pro_Term.m_integer(0);
    }

    return null;
  }

}
