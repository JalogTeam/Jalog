// Pred_searchchar.java

package io.github.JalogTeam.jalog;

import java.io.*;

// searchchar(SourceStr, SearchChar, Pos)


public class Pred_searchchar extends Pred
{
  private Pro_TermData_String Str_in;
  private Pro_Term Pos;
  private long end_pos, cur_pos;
  private String searchstring;
  public static Pred first_call(Pro_TermData_Compound data) {

    Pred result = null;
    Pro_Term source_str_term = data.subterm[0].getRealNode();
    Pro_Term search_char_term = data.subterm[1].getRealNode();
    Pro_Term pos_term = data.subterm[2].getRealNode();

    forward = false;
    result = null;
    

    if (source_str_term.getType() == Typenames.STRING) {

      if (pos_term.getType() == Typenames.INTEGER) {
            
                
        long pos = ((Pro_TermData_Integer)(pos_term.data)).value;
        char c = ((Pro_TermData_String)(source_str_term.data)).charAt(pos);
        
        forward = search_char_term.unify(Pro_Term.m_char(c), trail);
        
      } else if (search_char_term.getType() == Typenames.CHAR) {
        result = new Pred_searchchar(data);
        forward = true;
        result.call();
      }
     
    }
    return result;
  }

  Pred_searchchar(Pro_TermData_Compound data)
  {
    searchstring = Character.toString(((Pro_TermData_Char)
       (data.subterm[1].getData())).value);

    Str_in = (Pro_TermData_String)(data.subterm[0].getData());
    Pos = data.subterm[2];
    
    end_pos = Str_in.len;
    
    cur_pos = -1;
    trail.mark(Mark);
    
  }

  public void call()
  {

    if(!forward){
      trail.backtrack(Mark);
    }
    forward = false;
    cur_pos++;
    cur_pos = Str_in.indexOf(searchstring, cur_pos) ;
    
    forward = cur_pos > -1;
    
    if (cur_pos > -1) {
      forward = Pos.unify(Pro_Term.m_integer(cur_pos), trail, Mark);
    }
     
  }
}
