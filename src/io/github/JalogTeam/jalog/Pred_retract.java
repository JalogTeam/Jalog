// Pred_retract.java

package io.github.JalogTeam.jalog;

import java.io.*;
import java.util.*;

public class Pred_retract extends Pred
{
  // This data is used when backtracking to enable finding the next solution.
  Pro_Term param_1;
  Vector<Database_Table> factClass_list = null;
  int factClass_index = 0;
  int factClass_count = 0;
  
  public static Pred first_call(Pro_TermData_Compound params) {
    String dbasename = null;
    String indicator = null;
    Pred_retract state = null;
    Database_Table factClass;

    // You may want to take parameters to local variables:
    Pro_Term first_param = params.subterm[0].getRealNode();
    Pro_Term second_param = null;
    if (params.subterm.length > 1) {
      second_param = params.subterm[1].getRealNode();
    }
    
    forward = false; // default fail may be useful

    // Identify flow pattern and data types and act accordingly.

    String first_type = first_param.getType();
    if (((first_type == Jalog.COMPOUND) || (first_type == Jalog.SYMBOL) ||
        (first_type == Jalog.OPEN)) &&
        ((second_param == null) || (second_param.getType() == Jalog.SYMBOL)))
    { // (i) or (i,i)
      // Backtrack may produce another result, state = new object
      state = new Pred_retract();
      state.param_1 = first_param;
      if (second_param != null) {
        dbasename = ((Pro_TermData_Compound)(second_param.data)).name;
      }
     
      state.factClass_index = 0;
      if (first_type == Jalog.OPEN) {
        
        state.factClass_list = Database.find_dynamicFactClasses(dbasename);
        
      } else {
        
          
        indicator = state.param_1.getIndicator();
        if (second_param != null) {
          dbasename = ((Pro_TermData_Compound)(second_param.data)).name;
        }
        factClass = Database.find_by_string(indicator, dbasename);
        if ((factClass != null) && (factClass.dynamic)) {
          // initialize other state fields
          state.factClass_list = new Vector<Database_Table>();
          state.factClass_list.add(factClass);
        }
      }  
      
      if (state.factClass_list != null) {
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
    Database_Table factClass;
    // This is called on backtrack
//    int status;

    trail.backtrack(Mark);
    forward = false;
//    forward = false; // default fail
    while( (!forward) && (factClass_index < factClass_count) ) {

      factClass = factClass_list.get(factClass_index);
      forward = factClass.retract(param_1, Mark);
      if (!forward) {
        factClass_index ++;
      }
  
    }

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
