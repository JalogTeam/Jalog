// Pred_retract.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Pred_retract extends Pred
{
  // This data is used when backtracking to enable finding the next solution.
  Pro_Term param_1;
  Database_Table factClass = null;
  
  public static Pred first_call(Pro_TermData_Compound params) {
    String dbasename = null;
    String indicator = null;
    Pred_retract state = null;

    // You may want to take parameters to local variables:
    Pro_Term first_param = params.subterm[0].getRealNode();
    Pro_Term second_param = null;
    if (params.subterm.length > 1) {
      second_param = params.subterm[1].getRealNode();
    }
    
    forward = false; // default fail may be useful

    // Identify flow pattern and data types and act accordingly.

    String first_type = first_param.getType();
    if (((first_type == Jalog.COMPOUND) || (first_type == Jalog.SYMBOL)) &&
        ((second_param == null) || (second_param.getType() == Jalog.SYMBOL)))
    { // (i) or (i,i)
      // Backtrack may produce another result, state = new object
      state = new Pred_retract();
      state.param_1 = first_param;
      indicator = state.param_1.getIndicator();
      if (second_param != null) {
        dbasename = ((Pro_TermData_Compound)(second_param.data)).name;
      }
      state.factClass = Database.find_by_string(indicator, dbasename);
      if ((state.factClass != null) && (state.factClass.dynamic)) {
        // initialize other state fields
        trail.mark(state.Mark); // set backtrack point
        
        state.call(); // if the first result is computed the same way as
                        // other results, otherwise compute it here
      } else {
        Pred.exit_value = Pro_Term.m_integer(9506);
      }
    }

    return state;
    
  }

  public void call()
  {
    // This is called on backtrack
//    int status;

    trail.backtrack(Mark);
//    forward = false; // default fail

//    status = Database.retract(param_1, Mark);
    forward = factClass.retract(param_1, Mark);
/*
    switch (status) {
      case Database.SUCCEEDED: {
        forward = true;
      } break;
      case Database.NOT_DYNAMIC: {
        System.err.println("*** cannot retract non-dynamic " + 
            param_1.getIndicator()); 
      } break;

      default: {
        
      } break;
    }
*/
      
          
  }
}
