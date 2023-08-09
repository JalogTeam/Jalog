// Pred_frontstr.java
//
// frontstr(integer NumberOfChars, string String, string StartStr, 
//               string RestString) - 
//                      (i,i,o,o)

package io.github.JalogTeam.jalog;

public class Pred_frontstr extends Pred
{
  public static Pred first_call(Pro_TermData_Compound params) {

/*
    Parameters to local variables:
*/    
    Pro_Term number_of_chars_param = params.subterm[0].getRealNode();
    Pro_Term string_param = params.subterm[1].getRealNode();
    Pro_Term startstr_param = params.subterm[2].getRealNode();
    Pro_Term reststring_param = params.subterm[3].getRealNode();

    Pro_TermData_String str;
    char c;
    String s;
    
    String number_of_chars_param_type = number_of_chars_param.getType();
    String string_param_type = string_param.getType();
    String startstr_param_type = startstr_param.getType();
    String reststring_param_type = reststring_param.getType();

    JalogScanner scanner;

    forward = false; // default fail
/*
    Identify flow pattern and act accordingly.
    _param.data == null means an open variable.
*/    
    // check types
    if ((number_of_chars_param_type == Typenames.INTEGER) &&
        (string_param_type == Typenames.STRING)) {
      //  (i,i,o,o), (i,i,o,i), (i,i,i,o), (i,i,i,i)
      // string_param is bound
      Pro_TermData_String s1 = (Pro_TermData_String)string_param.getData();
      long number_of_chars = 
          ((Pro_TermData_Integer)(number_of_chars_param.data)).value;
      if (s1.len >= number_of_chars) {
        forward = startstr_param.unify(
            Pro_Term.m_string_fragment(s1, 0, number_of_chars), trail) 
            &&  reststring_param.unify(
            Pro_Term.m_string_fragment(s1, number_of_chars, s1.len), trail);
      }
    }
    
    return null;
    
  }

}
