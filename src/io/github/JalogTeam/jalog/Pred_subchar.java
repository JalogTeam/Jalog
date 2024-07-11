// Pred_subchar.java

package io.github.JalogTeam.jalog;

import java.io.*;

// substring(String,Position,Char)


public class Pred_subchar extends Pred
{

  public static Pred first_call(Pro_TermData_Compound data) {

    Pro_Term str_in_term = data.subterm[0].getRealNode();
    Pro_Term pos_term = data.subterm[1].getRealNode();
    Pro_Term char_out_term = data.subterm[2].getRealNode();

    if ( (str_in_term.data != null) && 
        (str_in_term.data.typename == Jalog.STRING) && ( pos_term.data != null)) 
    {
//      long pos = Pro_Term.eval_integer(pos_term);
      long pos = Pro_Term.eval_integer(pos_term) - 1; // Prolog indexing from 1
   
      try {
        Pro_Term co = Pro_Term.m_char(
            ((Pro_TermData_String)(str_in_term.data)).charAt(pos));
        forward = co.unify(char_out_term, trail);
      } catch (IndexOutOfBoundsException e) {
        forward = false;
      }
    } else {
      Pred.forward = false;
    }
    return null;
  }
}
