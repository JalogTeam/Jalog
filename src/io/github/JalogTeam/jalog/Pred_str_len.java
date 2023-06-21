// Pred_str_len.java

package io.github.JalogTeam.jalog;

import java.io.*;

// str_len(Str, Len)


public class Pred_str_len extends Pred
{
  public static Pred first_call(Pro_TermData_Compound params) {

    Pro_Term str = params.subterm[0].getRealNode();
    Pro_Term len = params.subterm[1].getRealNode();

    long length; 
    
    forward = false; // default fail

    // Identify flow pattern and data types, and act accordingly.

    String str_type = str.getType();
    String len_type = len.getType();

    if (str_type == Jalog.STRING) {
      
      length = ((Pro_TermData_String)(str.getData())).len;
      // Unify result_value and len
      forward = Pro_Term.m_integer(length).unify(len, trail);

    }
    // fails if something else
    
    return null;
    
  }

}
