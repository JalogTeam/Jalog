// Pred_assertz.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Pred_assertz extends Pred
{

  public static Pred first_call(Pro_TermData_Compound data) {
    String dbasename = null;
    Pro_Term term0 = data.subterm[0];
    Pro_Term second_param = null;
    Pro_TermData data0 = term0.getData();

    if (data.subterm.length > 1) {
      second_param = data.subterm[1].getRealNode();
      if (second_param.getType() == Jalog.SYMBOL) {
        dbasename = ((Pro_TermData_Compound)(second_param.data)).name;
      } else {
        Pred.exit_value = Pro_Term.m_integer(2200); // Type error
      }
        
    }

    if((data0 instanceof Pro_TermData_Compound) && 
        !((Pro_TermData_Compound)data0).name.equals(":-")) 
    {
      Database.assertz(term0, dbasename);
      if (Database.exit_value != 0) {
        Pred.exit_value = Pro_Term.m_integer(Database.exit_value);
      }
    } else {
      Pred.exit_value = Pro_Term.m_integer(2200); // Type error
    }

    return null;
  }

}
