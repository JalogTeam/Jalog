// Pred_frontstr.java
// Pred_frontstr(string String, string Token, string RestString) - 
//                      (i,o,o) (i,i,o) (i,o,i) (i,i,i) (o,i,i)

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
    
    String number_of_chars_param_type = string_param.getType();
    String string_param_type = string_param.getType();
    String startstr_param_type = token_param.getType();
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
      if (s1.len >= ((Pro_TermData_Integer)(number_of_chars_param.data)).value)
      {
        int start = 
                forward = reststring_param.unify(
              Pro_Term.m_string_fragment(s1, 1, s1.len), trail);

      
      
      Pro_TermData_String s1 = (Pro_TermData_String)string_param.getData();
      scanner = new JalogScanner();
      scanner.setScannerLine((Pro_TermData_String)(s1));
      scanner.advance();
      if (scanner.tokenType != scanner.EOL) {
        Pro_Term s1_first_token = Pro_Term.m_string_fragment(s1,
            scanner.tokenPos, scanner.nextPos-scanner.tokenPos);
        Pro_Term s1_rest_string = Pro_Term.m_string_fragment(s1,
            scanner.nextPos, s1.len-scanner.nextPos);
        forward = token_param.unify(s1_first_token, trail) 
            && reststring_param.unify(s1_rest_string, trail);
      }
    } else if (string_param_type == Typenames.OPEN) {
      // string_param is open and char_param is bound
      // check type
      if ((token_param_type == Typenames.STRING) 
          && (reststring_param_type == Typenames.STRING)) 
      {
        forward = string_param.unify(Pro_Term.m_string_concat(
            (Pro_TermData_String)(token_param.getData()), 
            (Pro_TermData_String)(reststring_param.getData())), trail);
      }
    
    }
    
    return null;
    
  }

}
