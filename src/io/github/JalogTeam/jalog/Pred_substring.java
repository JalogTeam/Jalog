// Pred_substring.java

package io.github.JalogTeam.jalog;

import java.io.*;

// substring(Str_in,Pos,Len,Str_out)


public class Pred_substring extends Pred
{
/* #arity_info_test           
public static final int z_number = 5;  
*/  
  private Pro_TermData_String Str_in, Str_out;
  private Pro_Term Pos, Len;
  private long end_pos, cur_pos;

  public static Pred first_call(Pro_TermData_Compound data) {

    Pred result = null;
    Pro_Term str_in_term = data.subterm[0].getRealNode();
    Pro_Term pos_term = data.subterm[1].getRealNode();
    Pro_Term len_term = data.subterm[2].getRealNode();
    Pro_Term str_out_term = data.subterm[3].getRealNode();

    if ( (str_in_term.data == null) || 
        (str_in_term.data.typename != Jalog.STRING) ) 
    {
      Pred.forward = false;
    } else if ( pos_term.data != null) {
            
              
      long pos = Pro_Term.eval_integer(pos_term);
      long len = Pro_Term.eval_integer(len_term);
   
      Pro_Term so = Pro_Term.m_string_fragment(
          (Pro_TermData_String)(str_in_term.data), pos, len);

      forward = so.unify(str_out_term, trail);
      
    } else {
      if (str_out_term.data instanceof Pro_TermData_String) {
        result = new Pred_substring(data);
        if(Pred.forward) result.call();
      }
    }
    return result;
  }

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
