// Pred_frontchar.java
// fronttoken(string String, string Token, string RestString) - 
//                      (i,o,o) (i,i,o) (i,o,i) (i,i,i) (o,i,i)

package io.github.JalogTeam.jalog;

public class Pred_frontchar extends Pred
{
  public static Pred first_call(Pro_TermData_Compound params) {

/*
    Parameters to local variables:
*/    
    Pro_Term string_param = params.subterm[0].getRealNode();
    Pro_Term frontchar_param = params.subterm[1].getRealNode();
    Pro_Term reststring_param = params.subterm[2].getRealNode();

    Pro_TermData_String str;
    char c;
    String s;
    
    Pro_Term frontchar;
    
    String string_param_type = string_param.getType();
    String frontchar_param_type = frontchar_param.getType();
    String reststring_param_type = reststring_param.getType();

    forward = false; // default fail
/*
    Identify flow pattern and act accordingly.
    _param.data == null means an open variable.
*/    
    
    // check type
    if (string_param_type == Typenames.STRING) {
      // (i,o,o) (i,i,o) (i,o,i) (i,i,i)
      // string_param is bound
      Pro_TermData_String s1 = (Pro_TermData_String)string_param.getData();
      
      if (s1.len > 0) {
        c = s1.charAt(0);
        frontchar = Pro_Term.m_char(c);
        forward = frontchar.unify(frontchar_param, trail);
        if (forward) {
          forward = reststring_param.unify(
              Pro_Term.m_string_fragment(s1, 1, s1.len), trail);
        }
      }
    } else if (string_param_type == Typenames.OPEN) {
      // string_param is open and char_param is bound
      // check type
      if ((frontchar_param_type == Typenames.CHARACTER) 
          && (reststring_param_type == Typenames.STRING)) 
      {
        c = ((Pro_TermData_Char)frontchar_param.data).value;
        forward = string_param.unify(Pro_Term.m_string_concat(
            Pro_TermData_String_simple.make(Character.toString(c)), 
            (Pro_TermData_String)(reststring_param.getData())), trail);
      }
    }
    
    return null;
    
  }

}
