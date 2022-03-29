// Pred_substring.java

package io.github.JalogTeam.jalog;

import java.io.*;

public class Pred_substring extends Pred
{
  private Pro_TermData_String Str_in, Str_out;
  private Pro_Term Pos, Len;
  private long end_pos, cur_pos;

  Pred_substring(Pro_TermData_Compound data)
  {
    long Stri_in_len, Str_out_len;

    Str_in = (Pro_TermData_String)(data.subterm[0].getData());
    Pos = data.subterm[1];
    Len = data.subterm[2];
    Str_out = (Pro_TermData_String)(data.subterm[3].getData());
    
    end_pos = Str_in.len - Str_out.len;
    
    cur_pos = 0;
    
    forward = Len.unify(Pro_Term.m_integer(Str_out.len), trail, Mark);

  }

  public void call()
  {

    if(!forward){
      trail.backtrack(Mark);
    }
    forward = false;
    
    
    while ((cur_pos < end_pos) && !forward) {
      forward = Pro_TermData_String.contains_at(Str_in, cur_pos, Str_out);
      cur_pos++;      
    }

    if (forward) {
      forward = Pos.unify(Pro_Term.m_integer(cur_pos - 1), trail, Mark);
    }
     
  }
}
