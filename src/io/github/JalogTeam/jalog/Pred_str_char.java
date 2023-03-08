// Pred_str_char.java
// str_char(string, char) - (i,o) (o,i) (i,i)

package io.github.JalogTeam.jalog;

public class Pred_str_char extends Pred
{
  public static Pred first_call(Pro_TermData_Compound params) {

/*
    Parameters to local variables:
*/    
    Pro_Term string_param = params.subterm[0].getRealNode();
    Pro_Term char_param = params.subterm[1].getRealNode();

    Pro_TermData_String str;
    char c;
    String s;

    forward = false; // default fail
/*
    Identify flow pattern and act accordingly.
    _param.data == null means an open variable.
*/    
    if (string_param.data != null) {
      // string_param is bound
      // check type
      if (string_param.data.typename == Jalog.STRING) {
        Pro_TermData_String s1 = (Pro_TermData_String)string_param.data;
        if (s1.len == 1) { // only length 1 is possible
          // compute result_value for char_param
          c = s1.substring(0, 1).charAt(0);

          Pro_Term result_value = Pro_Term.m_char(c);
          
          // Unify result_value and char_param
          forward = result_value.unify(char_param, trail);
        }
      }

    } else if (char_param.data != null) {
      // string_param is open and char_param is bound
      // check type
      if (char_param.data.typename == Jalog.CHARACTER) {
        
        // compute result_value for string_param
        s = Character.toString(((Pro_TermData_Char)char_param.data).value);
        Pro_Term result_value = Pro_Term.m_string(s);
        
        // Unify result_value and string_param
        forward = result_value.unify(string_param, trail);
      }
    
    }
    
    return null;
    
  }

}
