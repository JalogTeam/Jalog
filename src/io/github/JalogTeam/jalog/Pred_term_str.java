// Pred_term_str.java
// term_str(Symbol, Term, String) - (i,i,o) (i,o,i) (i,i,i)

package io.github.JalogTeam.jalog;

import java.io.*;
import java.util.*;

public class Pred_term_str extends Pred
{

  public static Pred first_call(Pro_TermData_Compound data) {

    Pred result = null;
//    Pro_Term first_param = data.subterm[0].getRealNode();
    Pro_Term term_term = data.subterm[1].getRealNode();
    Pro_Term string_term = data.subterm[2].getRealNode();

    long len_left, len_right, len;
    Pro_TermData_Compound compare_data;
    Pro_Term right_part, left_part;
    
    if ((term_term.data != null) && (string_term.data == null)) {
      Pro_Term.m_string(term_term.toString());
      forward = string_term.unify(Pro_Term.m_string(term_term.toString()), 
          trail);      
    } else if (string_term.data != null) {
      JalogTerms JT = new JalogTerms(JalogTerms.TERM);
      JT.SetLine((Pro_TermData_String)(string_term.data));
      forward = term_term.unify(JT.NextPart(), trail);
    } else {
      Pred.forward = false;  
    }
    return null;
  }

}
