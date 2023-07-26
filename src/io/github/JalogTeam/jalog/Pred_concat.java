// Pred_concat.java
// concat(String, String, String) - (i,i,i) (i,i,o) (i,o,i) (o,i,i)

package io.github.JalogTeam.jalog;

import java.io.*;
import java.util.*;

public class Pred_concat extends Pred
{

  public static Pred first_call(Pro_TermData_Compound data) {

    Pred result = null;
    Pro_Term left_term = data.subterm[0].getRealNode();
    Pro_Term right_term = data.subterm[1].getRealNode();
    Pro_Term result_term = data.subterm[2].getRealNode();

    long len_left, len_right, len;
    Pro_TermData_Compound compare_data;
    Pro_Term right_part, left_part;
    
    if (left_term.data != null && right_term.data != null) {
      Pro_Term so = Pro_Term.m_string_concat(
          (Pro_TermData_String)left_term.data, 
          (Pro_TermData_String)right_term.data);
    
  Pro_Term.debug = 0;
      result = new Pred__eq_(
          new Pro_TermData_Compound("=", so, result_term));
      result.call();
  Pro_Term.debug = 0;
    } else if (left_term.data != null && result_term.data != null) {
      len_left = ((Pro_TermData_String)left_term.data).len;
      len = ((Pro_TermData_String)result_term.data).len;
      left_part = Pro_Term.m_string_fragment(
          (Pro_TermData_String)result_term.data, 0, len_left);
      result = new Pred__eq_(
          new Pro_TermData_Compound("=", left_term, left_part));
      result.call();
      if (Pred.forward) {
        right_part = Pro_Term.m_string_fragment(
            (Pro_TermData_String)result_term.data, len_left, 
            len - len_left);
        result = new Pred__eq_(
            new Pro_TermData_Compound("=", right_term, right_part));
        result.call();
      }
    } else if (right_term.data != null && result_term.data != null) {
      len_right = ((Pro_TermData_String)right_term.data).len;
      len = ((Pro_TermData_String)result_term.data).len;
      right_part = Pro_Term.m_string_fragment(
          (Pro_TermData_String)result_term.data, len - len_right, 
          len_right);
      result = new Pred__eq_(
          new Pro_TermData_Compound("=", right_term, right_part));
      result.call();
      if (Pred.forward) {
        left_part = Pro_Term.m_string_fragment(
            (Pro_TermData_String)result_term.data, 0, len - len_right);
        result = new Pred__eq_(
            new Pro_TermData_Compound("=", left_term, left_part));
        result.call();
      }
    } else {
      Pred.forward = false;  // invalid flow pattern (o,o,o) (i,o,o),(o,i,o),(o,o,i)
    }
    return null;
  }

}
