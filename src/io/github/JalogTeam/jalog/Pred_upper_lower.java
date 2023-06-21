// Pred_upper_lower.java

package io.github.JalogTeam.jalog;

import java.io.*;
import java.util.*;

// upper_lower(Str_U, Str_l)


public class Pred_upper_lower extends Pred
{
  public static Pred first_call(Pro_TermData_Compound params) {

    Pro_Term upper = params.subterm[0].getRealNode();
    Pro_Term lower = params.subterm[1].getRealNode();

    String upper_image;
    String lower_image;
    
    forward = false; // default fail

    // Identify flow pattern and data types, and act accordingly.

    String upper_type = upper.getType();
    String lower_type = lower.getType();

    if ((upper_type == Jalog.STRING) && (lower_type == Jalog.OPEN)) {
      upper_image = upper.image();
      lower_image = upper_image.toLowerCase(Locale.ENGLISH);
      
      // Unify result_value and lower
      forward = Pro_Term.m_string(lower_image).unify(lower, trail);

    } else if ((upper_type == Jalog.OPEN) && (lower_type == Jalog.STRING)) {
      // first_param is open and lower is bound

      lower_image = lower.image();
      upper_image = lower_image.toUpperCase(Locale.ENGLISH);
      
      // Unify result_value and lower
      forward = Pro_Term.m_string(upper_image).unify(upper, trail);

    } else if ((upper_type == Jalog.STRING) && (lower_type == Jalog.STRING)) {
    
      upper_image = upper.image();
      lower_image = lower.image();
      
      forward = (upper_image.compareToIgnoreCase(lower_image) == 0);
    }
    // fails if something else
    
    return null;
    
  }

}
