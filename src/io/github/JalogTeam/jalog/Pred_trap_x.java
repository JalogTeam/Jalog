// Pred_trap_x.java
/*
    NOTE: The logic of Pred_trap is partially in Inference.java .
*/

package io.github.JalogTeam.jalog;

import java.io.*;

public class Pred_trap_x extends Pred_trap
{
  /*
  boolean catched;
  Activation prev_trap_activation; // used in Inference
  Pro_Term exit_var;
  Pro_Term catch_body;
*/

  public static Pred first_call(Pro_TermData_Compound data) {

    Pro_Term[] items = {data.subterm[0]};

    Pro_Term[] catch_items = {data.subterm[2]};

// Debug_times.enter(2);
    Pred_trap_x result = new Pred_trap_x();
// Debug_times.leave(2);
    result.called_body = Pro_Term.m_list(items);

    result.exit_var = data.subterm[1];
    result.catch_body = Pro_Term.m_list(catch_items);
    result.catched = false;

    return result;
  }

  public void post_call()
  {
    called_body = null;
//System.out.println("<Pred_trap_x 1> catched=" + catched +
//", exception=" + exception + ", forward=" + forward);
    if(!catched) {
//System.out.print("<Pred_trap 11>");
      if(exception) {
//System.out.print("<Pred_trap 111>");
        // got exception
        catched = true;
        exception = false;
        // unify exit_value to exit_var
        if(exit_var.unify(exit_value, Pred.trail, Mark)) {
//System.out.print("<Pred_trap 1111>");
          forward = true;
          called_body = catch_body;

        }
        catch_body = null;
      } else if(!forward) {  // failed
//System.out.print("<Pred_trap 112>");
        sub_activation = null;
      }
    } else {
//System.out.print("<Pred_trap 12>");
//      forward = false;
      if(!forward) {
        sub_activation = null;
      }
    }
//System.out.print("<Pred_trap z>");
  }
}
