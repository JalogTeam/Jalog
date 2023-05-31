// Pred_retract.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Pred_retract extends Pred
{
  // This data is used when backtracking to enable finding the next solution.
  Pro_Term param_1;
  
  public static Pred first_call(Pro_TermData_Compound params) {

    Pred_retract state = null;

    // You may want to take parameters to local variables:
    Pro_Term first_param = params.subterm[0].getRealNode();
    
    forward = false; // default fail may be useful

    // Identify flow pattern and data types and act accordingly.

    String first_type = first_param.getType();
    if ((first_type == Jalog.COMPOUND) || (first_type == Jalog.SYMBOL)) { // (i)
      
      
      // Backtrack may produce another result, state = new object
      state = new Pred_retract();
      state.param_1 = first_param;
      // initialize other state fields
      trail.mark(state.Mark); // set backtrack point
      
      state.call(); // if the first result is computed the same way as
                      // other results, otherwise compute it here
    }

    return state;
    
  }

  public void call()
  {
    // This is called on backtrack

    trail.backtrack(Mark);
    forward = false; // default fail

    forward = (Database.retract(param_1, Mark) != null);
          
  }
}
