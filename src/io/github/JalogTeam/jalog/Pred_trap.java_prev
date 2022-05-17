// Pred_trap.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Pred_trap extends Pred
{
  boolean catched;
  Activation prev_trap_activation;
  Pro_Term exit_var;
  Pro_Term catch_body;


  public void post_call()
  {
    super.post_call();

//System.out.print("<Pred_trap 1>");
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
        called_body = null;
      }
    } else {
//System.out.print("<Pred_trap 12>");
      forward = false;
      sub_activation = null;
      called_body = null;
    }
//System.out.print("<Pred_trap z>");
  }
}
