// Pred_not.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Pred_not extends Pred
{
  public static Pred first_call(Pro_TermData_Compound data) {

    Pro_Term[] items = {data.subterm[0]};
// Debug_times.enter(2);
    Pred_not result = new Pred_not();
// Debug_times.leave(2);
    result.called_body = Pro_Term.m_list(items);

    return result;
  }

  public void post_call()
  {
    forward = !forward;
    sub_activation = null;
    called_body = null;
  }
}
