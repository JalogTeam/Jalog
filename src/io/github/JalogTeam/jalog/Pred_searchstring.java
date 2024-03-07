// Pred_searchstring.java

package io.github.JalogTeam.jalog;

import java.io.*;

// searchstring(SourceStr, SearchStr, Pos)


public class Pred_searchstring extends Pred
{
  private Pro_TermData_String source_str, search_str;
  private Pro_Term pos_term, 
  Pos, Len;
  private long end_pos, cur_pos;

  public static Pred first_call(Pro_TermData_Compound data) {

    Pred_searchstring result = null;
    Pro_Term source_str_term = data.subterm[0].getRealNode();
    Pro_Term search_str_term = data.subterm[1].getRealNode();
    Pro_Term pos_term = data.subterm[2].getRealNode();
    String pos_term_type = pos_term.getType();

    forward = false;
    result = null;
    
    if ( (source_str_term.getType() == Typenames.STRING) 
        && (search_str_term.getType() == Typenames.STRING))
    {
      Pro_TermData_String source_str_data = 
          (Pro_TermData_String)(source_str_term.getData());
      Pro_TermData_String search_str_data = 
          (Pro_TermData_String)(search_str_term.getData());
      if (pos_term_type == Typenames.OPEN) {
      
        result = new Pred_searchstring();

        result.source_str = source_str_data;
        result.search_str = search_str_data;
        result.pos_term = pos_term;
        result.cur_pos = -1;
        result.end_pos = result.source_str.len - result.search_str.len;

        trail.mark(result.Mark);
        result.call();
      } else if (pos_term_type == Typenames.INTEGER) {
        forward = Pro_TermData_String.contains_at(source_str_data, 
            ((Pro_TermData_Integer)(pos_term.data)).value - 1, search_str_data);
      }
    }
    return result;
  }

  public void call()
  {

    if(!forward){
      trail.backtrack(Mark);
    }
    forward = false;
    
    
    while ((cur_pos < end_pos) && !forward) {
      cur_pos++;      
      forward = Pro_TermData_String.contains_at(source_str, cur_pos, search_str);
    }

    if (forward) {
      forward = pos_term.unify(Pro_Term.m_integer(cur_pos + 1), trail, Mark);
    }
     
  }
}
